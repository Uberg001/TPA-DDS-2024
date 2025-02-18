function togglePuntosDropdown() {
    const dropdown = document.querySelector('.puntos-dropdown');
    dropdown.classList.toggle('hidden');
}

// Para manejar el click y la navegacion de las paginas en los botones del navbar

function handleConsultarPuntosClick() {
    window.location.href = '../consultar-puntos/PaginaConsultarPuntos.html';
}

function handleCanjearPuntosClick() {
    window.location.href = '../canjear-puntos/PaginaCanjearPuntos.html';

}

function handleInicioClick() {
    window.location.href = '../principal/PaginaPrincipal.html';
}

function handleAlertas() {
    window.location.href = '../alertas/PaginaAlertas.html';
}

function handleMapaClick() {
    window.location.href = '../mapa/PaginaMapa.html';
}

function handleCargarColaboradoresClick() {
    window.location.href = '../cargar-colaboradores/PaginaCargarColaboradores.html';
}

function handleLogoutClick() {
    window.location.href = 'Login.html';
}

function handleColaborarClick() {
    window.location.href = 'Colaborar.html';
}

function handleSolicitarClick() {
    window.location.href = 'Solicitar.html';
}


function handlerDonarDinero() {
    window.location.href = 'DonarDinero.html';
}

function handlerHacerseCargoHeladera() {
    window.location.href = 'OpcionesHeladera.html';

}

function handlerDistribuirVianda() {
    window.location.href = 'OpcionesPunto.html';
}


function handlerDonarVianda() {


}
