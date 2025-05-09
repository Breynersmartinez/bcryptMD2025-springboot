document.addEventListener('DOMContentLoaded', function() {
    // Verificar si hay un usuario logueado
    const userId = localStorage.getItem("userId");
    const userName = localStorage.getItem("userName");

    if (!userId || !userName) {
        // Redirigir al login si no hay usuario autenticado
        alert('Debes iniciar sesión para acceder a esta página');
        window.location.href = 'index.html';
        return;
    }

    // Mostrar información del usuario logueado
    document.getElementById('loggedUserId').textContent = userId;
    document.getElementById('loggedUserName').textContent = userName;
    document.getElementById('statusBar').textContent = `Usuario Autenticado: ${userName} (ID: ${userId})`;

    // Configurar manejo de pestañas
    setupTabs();

    // Manejar el cierre de sesión
    document.getElementById('logoutBtn').addEventListener('click', function() {
        localStorage.clear();
        alert('Has cerrado sesión correctamente');
        window.location.href = 'index.html';
    });
});

// Configuración de pestañas
function setupTabs() {
    const tabButtons = document.querySelectorAll('.tab-btn');
    const tabContents = document.querySelectorAll('.tab-content');

    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Remover clase active de todos los botones y contenidos
            tabButtons.forEach(btn => btn.classList.remove('active'));
            tabContents.forEach(content => content.classList.remove('active'));

            // Añadir clase active al botón seleccionado
            button.classList.add('active');

            // Mostrar el contenido correspondiente
            const tabId = button.getAttribute('data-tab');
            document.getElementById(`${tabId}-tab`).classList.add('active');

            // Limpiar mensajes al cambiar de pestaña
            hideMessage();
        });
    });
}

// Función para buscar usuario por ID
function searchUser() {
    const userId = document.getElementById('searchUserId').value;

    if (!userId) {
        showMessage('Por favor, ingresa un ID de usuario', 'error');
        return;
    }

    fetch(`/usuarios/${userId}`)
        .then(response => {
            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error('Usuario no encontrado');
                }
                throw new Error('Error al buscar el usuario');
            }
            return response.json();
        })
        .then(user => {
            // Mostrar información del usuario
            document.getElementById('displayUserId').textContent = user.idUsuario;
            document.getElementById('displayUserName').textContent = user.nombreUsuario;
            document.getElementById('userDisplay').style.display = 'block';
            showMessage('Usuario encontrado correctamente', 'success');
        })
        .catch(error => {
            document.getElementById('userDisplay').style.display = 'none';
            showMessage(error.message, 'error');
        });
}

// Función para registrar un nuevo usuario
function registerUser() {
    const userId = document.getElementById('regId').value;
    const userName = document.getElementById('regName').value;
    const userPass = document.getElementById('regPass').value;

    if (!userId || !userName || !userPass) {
        showMessage('Por favor, completa todos los campos', 'error');
        return;
    }

    const userData = {
        idUsuario: parseInt(userId),
        nombreUsuario: userName,
        contraseniaUsuario: userPass
    };

    fetch('/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 409) {
                    throw new Error('Este ID ya está en uso por otro usuario');
                }
                throw new Error('Error al registrar el usuario');
            }
            return response.text();
        })
        .then(data => {
            showMessage('Usuario registrado correctamente', 'success');
            clearForm('register');
        })
        .catch(error => {
            showMessage(error.message, 'error');
        });
}

// Función para actualizar un usuario
function updateUser() {
    const userId = document.getElementById('updateId').value;
    const currentPass = document.getElementById('currentPass').value;
    const newName = document.getElementById('newName').value;
    const newPass = document.getElementById('newPass').value;

    if (!userId || !currentPass) {
        showMessage('Por favor, ingresa el ID y la contraseña actual', 'error');
        return;
    }

    if (!newName && !newPass) {
        showMessage('Debes cambiar al menos el nombre o la contraseña', 'error');
        return;
    }

    const updateData = {
        idUsuario: parseInt(userId),
        contraseniaActual: currentPass,
        nuevoNombreUsuario: newName,
        nuevaContrasenia: newPass
    };

    fetch('/usuarios/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updateData)
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 401) {
                    throw new Error('Contraseña actual incorrecta o usuario no encontrado');
                }
                throw new Error('Error al actualizar el usuario');
            }
            return response.text();
        })
        .then(data => {
            showMessage('Usuario actualizado correctamente', 'success');
            clearForm('update');

            // Si el usuario actualizado es el mismo que está logueado, actualizar localStorage
            const loggedUserId = localStorage.getItem("userId");
            if (loggedUserId && parseInt(loggedUserId) === parseInt(userId) && newName) {
                localStorage.setItem("userName", newName);
                document.getElementById('loggedUserName').textContent = newName;
                document.getElementById('statusBar').textContent = `Usuario Autenticado: ${newName} (ID: ${loggedUserId})`;
            }
        })
        .catch(error => {
            showMessage(error.message, 'error');
        });
}

// Función para eliminar un usuario
function deleteUser() {
    const userId = document.getElementById('deleteId').value;
    const userPass = document.getElementById('deletePass').value;

    if (!userId || !userPass) {
        showMessage('Por favor, completa todos los campos', 'error');
        return;
    }

    const deleteData = {
        idUsuario: parseInt(userId),
        contraseniaUsuario: userPass
    };

    fetch('/usuarios/delete', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(deleteData)
    })
        .then(response => response.json())
        .then(success => {
            if (success) {
                showMessage('Usuario eliminado correctamente', 'success');
                clearForm('delete');

                // Si el usuario eliminado es el mismo que está logueado, cerrar sesión
                const loggedUserId = localStorage.getItem("userId");
                if (loggedUserId && parseInt(loggedUserId) === parseInt(userId)) {
                    setTimeout(() => {
                        localStorage.clear();
                        alert('Has eliminado tu cuenta. Serás redirigido al inicio.');
                        window.location.href = 'index.html';
                    }, 2000);
                }
            } else {
                showMessage('Error al eliminar usuario. Verifica el ID y la contraseña.', 'error');
            }
        })
        .catch(error => {
            showMessage('Error al eliminar el usuario', 'error');
        });
}

// Función para mostrar mensajes
function showMessage(message, type) {
    const messageBox = document.getElementById('messageBox');
    messageBox.textContent = message;
    messageBox.className = 'message';
    messageBox.classList.add(`message-${type}`);
    messageBox.style.display = 'block';

    // Auto-ocultar después de 5 segundos
    setTimeout(() => {
        hideMessage();
    }, 5000);
}

// Función para ocultar mensajes
function hideMessage() {
    const messageBox = document.getElementById('messageBox');
    messageBox.style.display = 'none';
}

// Función para limpiar formularios
function clearForm(formType) {
    switch (formType) {
        case 'register':
            document.getElementById('regId').value = '';
            document.getElementById('regName').value = '';
            document.getElementById('regPass').value = '';
            break;
        case 'update':
            document.getElementById('updateId').value = '';
            document.getElementById('currentPass').value = '';
            document.getElementById('newName').value = '';
            document.getElementById('newPass').value = '';
            break;
        case 'delete':
            document.getElementById('deleteId').value = '';
            document.getElementById('deletePass').value = '';
            break;
    }
}