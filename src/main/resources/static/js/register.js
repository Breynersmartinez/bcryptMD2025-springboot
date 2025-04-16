function registerUser() {
    const id = document.getElementById("regId").value;
    const name = document.getElementById("regName").value;
    const pass = document.getElementById("regPass").value;

    fetch("/usuarios", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            idUsuario: parseInt(id),
            nombreUsuario: name,
            contraseniaUsuario: pass
        })
    })
    .then(async response => {
        if (response.ok) {
            alert("Usuario registrado con éxito");
            window.location.href = "login.html";
        } else {
            const mensaje = await response.text();
            alert("Error al registrar: " + mensaje);
        }
    })
    .catch(error => {
        alert("Error de red o del servidor: " + error.message);
    });
}
