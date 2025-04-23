// login.js - Script para el inicio de sesión
function loginUser() {
    const id = document.getElementById("loginId").value;
    const pass = document.getElementById("loginPass").value;

    // Validación básica
    if (!id || !pass) {
        alert("Por favor, completa todos los campos");
        return;
    }

    // Llamada al API de login
    fetch("/usuarios/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            idUsuario: parseInt(id),
            contraseniaUsuario: pass
        })
    })
        .then(res => res.json())
        .then(success => {
            if (success) {
                // Guardar ID en localStorage
                localStorage.setItem("userId", id);

                // Obtener el nombre del usuario
                fetch(`/usuarios/${id}`)
                    .then(res => res.json())
                    .then(data => {
                        // Guardar nombre en localStorage
                        localStorage.setItem("userName", data.nombreUsuario);

                        // Redirigir a la página de inicio
                        window.location.href = "index.html";
                    })
                    .catch(error => {
                        console.error("Error al obtener datos del usuario:", error);
                        alert("Error al obtener datos del usuario");
                    });
            } else {
                alert("Credenciales incorrectas. Por favor, verifica tu ID y contraseña.");
            }
        })
        .catch(error => {
            console.error("Error en la solicitud:", error);
            alert("Error al conectar con el servidor. Por favor, intenta más tarde.");
        });
}

// Añadir evento de tecla "Enter" para facilitar el login
document.addEventListener('DOMContentLoaded', function() {
    const passInput = document.getElementById('loginPass');
    if (passInput) {
        passInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                loginUser();
            }
        });
    }
});