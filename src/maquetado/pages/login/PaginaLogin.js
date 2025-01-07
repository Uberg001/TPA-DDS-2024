document.addEventListener('DOMContentLoaded', function () {
    let active = false;
    let personaType = null;
    let tipoDNI = 'DNI';
    const contactos = {
        whatsapp: false,
        email: false,
        telegram: false
    };
    const valoresContacto = {
        telefono: '',
        email: '',
        telegram: ''
    };
    const contactosVisibles = {
        telefono: false,
        email: false,
        telegram: false
    };

    const container = document.getElementById('container');
    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');

    window.setActive = function(value) {
        active = value;
        container.classList.toggle('active', active);
    }

    window.setPersonaType = function(type) {
        personaType = type;
        updateForms();
    }

    window.toggleContacto = function(tipo) {
        contactosVisibles[tipo] = !contactosVisibles[tipo];
        updateForms();
    }

    window.handleRegister = function(event) {
        event.preventDefault();
        if (!Object.values(valoresContacto).some(value => value.trim() !== '')) {
            alert('Por favor, complete al menos un contacto.');
            return;
        }
        // Procesar el formulario de registro
    }

    // window.handleLogin = function(event) {
    //     event.preventDefault();
    //     const form = event.target;
    //     const cuitOrDoc = form.querySelector('input[name="cuit"], input[name="documento"]').value;
    //     const password = form.querySelector('input[name="password"]').value;
    //
    //     if ((personaType === 'juridica' && cuitOrDoc === '1' && password === '1') ||
    //         (personaType === 'humana' && cuitOrDoc === '1' && password === '1') ||
    //         (personaType === 'vulnerable' && cuitOrDoc === '1' && password === '1')) {
    //         // Guardar el estado de autenticación en localStorage
    //         localStorage.setItem('isLoggedIn', 'true');
    //         localStorage.setItem('userType', personaType);
    //         // Redirigir a PaginaPrincipal
    //         window.location.href = '/front_html_css/pages/principal/PaginaPrincipal.html';
    //     } else {
    //         alert('Credenciales incorrectas');
    //     }
    // }

    function updateForms() {
        registerForm.innerHTML = '';
        loginForm.innerHTML = '';

        if (personaType === 'juridica') {
            registerForm.innerHTML = `
                <input type="number" placeholder="N° CUIT" required/>
                <input type="text" placeholder="Nombre de usuario" required/>
                ${createContactoField('telefono')}
                ${createContactoField('email')}
                ${createContactoField('telegram')}
                <input type="password" placeholder="Contraseña" required/>
                <a href="#">Olvidaste la contraseña?</a>
                <button type="submit" class="btn">Registrarse</button>
            `;
        } else if (personaType === 'humana') {
            registerForm.innerHTML = `
                <div>
                    <select value="${tipoDNI}" onchange="setTipoDNI(event)" class="select selectDNI">
                        <option value="DNI">DNI</option>
                        <option value="Pasaporte">Pasaporte</option>
                        <option value="LC">LC</option>
                        <option value="LE">LE</option>
                    </select>
                    <input type="number" placeholder="Número" required/>
                </div>
                <input type="text" placeholder="Nombre de usuario" required/>
                ${createContactoField('telefono')}
                ${createContactoField('email')}
                ${createContactoField('telegram')}
                <input type="password" placeholder="Contraseña" required/>
                <a href="#">Olvidaste la contraseña?</a>
                <button type="submit" class="btn">Registrarse</button>
            `;
        } else if (personaType === 'vulnerable') {
            registerForm.innerHTML = `
                <input type="text" placeholder="Nombre de usuario" required/>
                <input type="email" placeholder="Email" required/>
                <input type="password" placeholder="Contraseña" required/>
                <a href="#">Olvidaste la contraseña?</a>
                <button type="submit" class="btn">Registrarse</button>
            `;
        }

        if (personaType === 'juridica') {
            loginForm.innerHTML = `
                <input type="number" name="cuit" placeholder="N° CUIT" required/>
                <input type="password" name="password" placeholder="Contraseña" required/>
                <button type="submit" class="btn">Acceder</button>
            `;
        } else if (personaType === 'humana' || personaType === 'vulnerable') {
            loginForm.innerHTML = `
                <input type="number" name="documento" placeholder="N° de documento" required/>
                <input type="password" name="password" placeholder="Contraseña" required/>
                <a href="#">Olvidaste la contraseña?</a>
                <button type="submit" class="btn">Acceder</button>
            `;
        }
    }

    function createContactoField(tipo) {
        return `
            <label class="contacto-container">
                <button type="button" onclick="toggleContacto('${tipo}')" class="plusButton">+</button>
                ${capitalizeFirstLetter(tipo)}
                ${contactosVisibles[tipo] ? `<input type="text" name="${tipo}" placeholder="${capitalizeFirstLetter(tipo)}" value="${valoresContacto[tipo]}" oninput="handleInputChange(event)"/>` : ''}
            </label>
        `;
    }

    window.setTipoDNI = function(event) {
        tipoDNI = event.target.value;
        updateForms();
    }

    window.handleInputChange = function(event) {
        valoresContacto[event.target.name] = event.target.value;
    }

    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    updateForms();
});