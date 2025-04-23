document.addEventListener('DOMContentLoaded', function() {
    // Verificar si hay un usuario logueado
    const userId = localStorage.getItem("userId");
    const userName = localStorage.getItem("userName");

    if (userId && userName) {
        // Usuario logueado
        document.getElementById('userWelcome').style.display = 'flex';
        document.getElementById('userName').textContent = userName;

        // Habilitar botones y enlaces
        document.getElementById('registroLink').classList.remove('btn-disabled');
        document.getElementById('registerBtn').classList.remove('btn-disabled');
        document.getElementById('registerBtn').href = 'register.html';

        // Mostrar botón de logout en lugar de login
        document.getElementById('loginBtn').style.display = 'none';
        document.getElementById('logoutBtn').style.display = 'inline-flex';

        // Actualizar barra de estado
        document.getElementById('statusBar').textContent = `Usuario Autenticado: ${userName} (ID: ${userId})`;
    }

    // Manejar el cierre de sesión
    document.getElementById('logoutBtn').addEventListener('click', function(e) {
        e.preventDefault();
        localStorage.clear();
        alert('Has cerrado sesión correctamente');
        window.location.reload();
    });
});