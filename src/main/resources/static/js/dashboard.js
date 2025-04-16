const id = localStorage.getItem("userId");
const name = localStorage.getItem("userName");

if (!id || !name) {
    alert("No has iniciado sesión");
    window.location.href = "login.html";
} else {
    document.getElementById("dashId").textContent = id;
    document.getElementById("dashName").textContent = name;
}

function updateUser() {
    const actual = document.getElementById("actualPass").value;
    const nuevoNombre = document.getElementById("newName").value;
    const nuevaPass = document.getElementById("newPass").value;

    fetch("/api/usuarios/update", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            idUsuario: parseInt(id),
            contraseniaActual: actual,
            nuevoNombreUsuario: nuevoNombre,
            nuevaContrasenia: nuevaPass
        })
    })
    .then(res => res.text())
    .then(msg => {
        alert(msg);
        if (nuevoNombre) {
            localStorage.setItem("userName", nuevoNombre);
            document.getElementById("dashName").textContent = nuevoNombre;
        }
    });
}

function deleteUser() {
    const pass = document.getElementById("deletePass").value;

    fetch("/api/usuarios/delete", {
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
            alert("Cuenta eliminada");
            logout();
        } else {
            alert("Contraseña incorrecta");
        }
    });
}

function logout() {
    localStorage.clear();
}
