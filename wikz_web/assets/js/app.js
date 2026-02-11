const API_URL = "http://localhost:8080/api/wikz/operaciones";

/**
 * Gestiona el cambio de pantallas en Wikz
 * @param {string} seccion - El nombre de la sección ('explorar', 'crear', 'perfil')
 * @param {HTMLElement} elemento - El botón del menú que fue pulsado
 */
function navegar(seccion, elemento) {
    //Referencia al contenedor principal de index.php
    const contenedor = document.getElementById('app-content');

    //Gestión visual: Quitar 'active' a todos y dárselo al pulsado
    const items = document.querySelectorAll('.nav-item');
    items.forEach(item => item.classList.remove('active'));
    elemento.classList.add('active');

    //Limpiar el contenido actual para cargar el nuevo
    contenedor.innerHTML = '';

    //Decidir qué cargar mediante un Switch
    switch (seccion) {
        case 'explorar':
            cargarExplorar(contenedor);
            break;
        case 'crear':
            cargarFormularioCrear(contenedor);
            break;
        case 'perfil':
            cargarPerfil(contenedor);
            break;
        default:
            contenedor.innerHTML = '<h2>Sección no encontrada</h2>';
    }
}

async function cargarExplorar(contenedor) {
    try {
        const response = await fetch(`${API_URL}/getPublicaciones`);
        if (!response.ok) throw new Error("Error en API");

        const publicaciones = await response.json();
        let html = '<div class="feed">';

        publicaciones.forEach(pub => {
            // Usamos el endpoint de imagen que tienes en Java para mayor fluidez
            const imgUrl = `${API_URL}/getImagenPublicacion?id=${pub.id}`;

            html += `
                <div class="card">
                    <img src="${imgUrl}" class="card-img" onerror="this.src='assets/img/default.jpg'">
                    <div class="card-body">
                        <h3>${pub.titulo}</h3>
                        <p>${pub.descripcion}</p>
                    </div>
                </div>`;
        });
        contenedor.innerHTML = html + '</div>';
    } catch (e) {
        contenedor.innerHTML = "<p>Servidor de contenido fuera de línea.</p>";
    }
}

ipt
function cargarFormularioCrear(contenedor) {
    contenedor.innerHTML = `
        <div class="viewport-center">
            <div class="crear-grid-container">
                
                <div class="card-visual" onclick="document.getElementById('input-file').click()">
                    <img id="img-preview" src="" style="display:none;">
                    <div id="upload-placeholder">
                        <span class="material-icons">cloud_upload</span>
                        <p>Arrastra o selecciona tu imagen</p>
                    </div>
                </div>
                <input type="file" id="input-file" accept="image/*" style="display:none" onchange="previsualizar(this)">

                <div class="card-info">
                    <h2 class="titulo-gradient">Nueva Publicación</h2>
                    
                    <div class="input-wrapper">
                        <label><span class="material-icons">title</span> Título</label>
                        <input type="text" id="pub-titulo" placeholder="¿Cómo se llama tu obra?" class="wikz-input-glass">
                    </div>

                    <div class="input-wrapper">
                        <label><span class="material-icons">description</span> Descripción</label>
                        <textarea id="pub-desc" placeholder="Cuéntanos más sobre esto..." class="wikz-input-glass" rows="5"></textarea>
                    </div>

                    <button class="btn-publish-glow" id="btn-publicar" onclick="enviarPublicacion()">
                        Publicar ahora
                    </button>
                </div>

            </div>
        </div>
    `;
}

async function enviarPublicacion() {
    const tituloInput = document.getElementById('pub-titulo');
    const descInput = document.getElementById('pub-desc');
    const fileInput = document.getElementById('input-file');
    const btn = document.getElementById('btn-publicar');

    //VALIDACIÓN
    const titulo = tituloInput.value.trim();
    const descripcion = descInput.value.trim(); // Puede estar vacía
    const imagenFile = fileInput.files[0];

    if (!titulo) {
        alert("⚠️ El título es obligatorio.");
        return;
    }

    if (!imagenFile) {
        alert("⚠️ Debes seleccionar una imagen para hacer una publicacion!.");
        return;
    }

    // Desactivar botón para evitar múltiples clics
    btn.disabled = true;
    btn.innerHTML = `<span class="material-icons animate-spin">sync</span> Publicando...`;

    try {
        //CONVERSIÓN DE IMAGEN A BASE64
        const base64String = await imageToBase64(imagenFile);
        const datos = {
            idUsuario: WIKZ_USER.id, 
            titulo: titulo,
            descripcion: descripcion,
            imagenBase64: base64String
        };

        //LLAMADA A LA API
        const response = await fetch(`${API_URL}/addPublicacion`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        //MANEJO DE RESPUESTA
        if (response.ok) {
            const mensaje = await response.text();
            alert("✅ " + mensaje);
            // Redirigir a explorar para ver la nueva publicación
            navegar('explorar', document.getElementById('nav-explorar'));
        } else {
            const errorTexto = await response.text();
            throw new Error(errorTexto || "Error desconocido en el servidor");
        }

    } catch (error) {
        console.error("Error en la publicación:", error);
        alert("❌ Error: " + error.message);
    } finally {
        btn.disabled = false;
        btn.innerHTML = "Confirmar y publicar";
    }
}

function previsualizar(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function(e) {
            const imgPreview = document.getElementById('img-preview');
            const placeholder = document.getElementById('upload-placeholder');

            // Establecemos la imagen cargada como fuente
            imgPreview.src = e.target.result;
            
            // Mostramos la imagen y ocultamos el texto/icono de ayuda
            imgPreview.style.display = 'block';
            placeholder.style.display = 'none';
        }

        reader.readAsDataURL(input.files[0]);
    }
}

/*Función auxiliar para convertir archivos a Base64 de forma asíncrona*/

function imageToBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            // El resultado viene como "data:image/png;base64,iVBORw..."
            // Necesitamos quitar el prefijo para enviarlo a Java
            const base64 = reader.result.split(',')[1];
            resolve(base64);
        };
        reader.onerror = error => reject(error);
    });
}

function cargarPerfil(contenedor) {
    // Usamos el objeto WIKZ_USER que creamos en el index.php
    contenedor.innerHTML = `
        <div style="text-align:center; padding: 40px;">
            <span class="material-icons" style="font-size: 100px; color: #9d4edd;">account_circle</span>
            <h2 style="margin-top:10px;">${WIKZ_USER.nombre}</h2>
            <p style="color: #b1a7a6;">ID de Usuario: #${WIKZ_USER.id}</p>
            <button onclick="cerrarSesion()" class="wikz-btn-outline">Cerrar Sesión</button>
        </div>
    `;
}

function cerrarSesion() {
    localStorage.removeItem('wikz_session');
    window.location.href = 'index.php?controller=Usuario&action=login';
}