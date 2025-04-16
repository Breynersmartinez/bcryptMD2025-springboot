function registerUser() {
    const id = document.getElementById("regId").value;
    const name = document.getElementById("regName").value;
    const pass = document.getElementById("regPass").value;

    fetch("/api/usuarios", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            idUsuario: parseInt(id),
            nombreUsuario: name,
            contraseniaUsuario: pass
        })
    })
    .then(() => {
        alert("Usuario registrado con éxito");
        window.location.href = "login.html";
    });
}
