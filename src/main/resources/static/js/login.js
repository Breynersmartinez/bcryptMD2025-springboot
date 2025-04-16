function loginUser() {
    const id = document.getElementById("loginId").value;
    const pass = document.getElementById("loginPass").value;

    fetch("/api/usuarios/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ idUsuario: parseInt(id), contraseniaUsuario: pass })
    })
    .then(res => res.json())
    .then(success => {
        if (success) {
            localStorage.setItem("userId", id);
            // Obtener nombre
            fetch(`/api/usuarios/${id}`)
                .then(res => res.json())
                .then(data => {
                    localStorage.setItem("userName", data.nombreUsuario);
                    window.location.href = "dashboard.html";
                });
        } else {
            alert("Credenciales incorrectas");
        }
    });
}
