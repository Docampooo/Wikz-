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
        case 'editar-perfil':
            cargarEditarPerfil(contenedor);
            break;
        default:
            contenedor.innerHTML = '<h2>Sección no encontrada</h2>';
    }
}

async function cargarExplorar(contenedor) {
    try {
        contenedor.innerHTML = "<div class='loading'>Cargando publicaciones...</div>";

        const response = await fetch(`${API_URL}/getPublicaciones`);
        if (!response.ok) throw new Error("Error en API");

        const publicaciones = await response.json();

        if (!publicaciones || publicaciones.length === 0) {
            contenedor.innerHTML = "<p style='text-align:center; padding:20px;'>No hay publicaciones disponibles.</p>";
            return;
        }

        // Inicio del grid de 3 columnas
        let html = '<div class="explorar-grid">';

        publicaciones.forEach(pub => {
            const imgUrl = `${API_URL}/getImagenPublicacion?id=${pub.id}`;

            html += `
                <div class="post-card">
                    <div class="post-image-container">
                        <img src="${imgUrl}" loading="lazy" onerror="this.src='assets/img/default.jpg'">
                    </div>
                    <div class="post-info">
                        <h3>${pub.titulo}</h3>
                        <p>${pub.descripcion ?? ""}</p> 
                    </div>
                </div>
            `;
        });

        html += "</div>";
        contenedor.innerHTML = html;

    } catch (e) {
        console.error(e);
        contenedor.innerHTML = "<p style='text-align:center; color:red;'>Servidor de contenido fuera de línea.</p>";
    }
}

function cargarFormularioCrear(contenedor) {
    contenedor.innerHTML = `
        <div class="viewport-center">
            <div class="crear-grid-container">
                
                <div class="card-visual" onclick="document.getElementById('input-file').click()">
                    <img id="img-preview" src="" style="display:none;">
                    <div id="upload-placeholder" style="text-align:center;">
                        <span class="material-icons" style="font-size:48px; color:var(--morado-principal);">cloud_upload</span>
                        <p style="font-size:12px; color:var(--texto-gris);">Selecciona tu obra</p>
                    </div>
                </div>
                <input type="file" id="input-file" accept="image/*" style="display:none" onchange="previsualizar(this)">

                <div class="card-info">
                    <h2 class="titulo-gradient">Nueva Publicación</h2>
                    
                    <label style="font-size:12px; color:var(--morado-suave);">Título</label>
                    <input type="text" id="pub-titulo" placeholder="Título de la obra..." class="wikz-input-glass">

                    <label style="font-size:12px; color:var(--morado-suave);">Descripción</label>
                    <textarea id="pub-desc" placeholder="¿Qué significa para ti?" class="wikz-input-glass" rows="4" style="resize:none;"></textarea>

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

    if (!tituloInput.value.trim() || !fileInput.files[0]) {
        alert("⚠️ El título y la imagen son obligatorios.");
        return;
    }

    btn.disabled = true;
    btn.innerHTML = `Publicando...`;

    try {
        const base64String = await imageToBase64(fileInput.files[0]);
        const datos = {
            idUsuario: WIKZ_USER.id,
            titulo: tituloInput.value.trim(),
            descripcion: descInput.value.trim(),
            imagenBase64: base64String
        };

        const response = await fetch(`${API_URL}/addPublicacion`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datos)
        });

        if (response.ok) {
            alert("✅ Publicado con éxito");
            navegar('explorar', document.getElementById('nav-explorar'));
        } else {
            throw new Error(await response.text());
        }
    } catch (error) {
        alert("❌ Error: " + error.message);
    } finally {
        btn.disabled = false;
        btn.innerHTML = "Publicar ahora";
    }
}

function previsualizar(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = (e) => {
            const preview = document.getElementById('img-preview');
            const placeholder = document.getElementById('upload-placeholder');
            if (preview) {
                preview.src = e.target.result;
                preview.style.display = 'block';
            }
            if (placeholder) placeholder.style.display = 'none';
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function imageToBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result.split(',')[1]);
        reader.onerror = error => reject(error);
    });
}

/**
 * Función auxiliar para generar el HTML de las cards en el perfil
 */
function generarHtmlPosts(publicaciones) {
    if (publicaciones.length === 0) {
        return `<p style="grid-column: 1/-1; text-align: center; padding: 40px; color: #888;">Aún no has compartido ninguna obra.</p>`;
    }

    return publicaciones.map(pub => {
        const imgUrl = `${API_URL}/getImagenPublicacion?id=${pub.id}`;
        return `
            <div class="post-card">
                <div class="post-image-container">
                    <img src="${imgUrl}" loading="lazy" onerror="this.src='assets/img/default.jpg'">
                </div>
                <div class="post-info">
                    <h3>${pub.titulo}</h3>
                    <p>${pub.descripcion ?? ""}</p>
                </div>
            </div>
        `;
    }).join('');
}

// Función para el botón de actualización (puedes crear un modal o un simple prompt para empezar)
async function mostrarModalUpdate() {
    const nuevoNombre = prompt("Nuevo nombre de usuario:", WIKZ_USER.nombre);
    const nuevaBio = prompt("Nueva biografía:", WIKZ_USER.biografia || "");

    if (nuevoNombre) {
        try {
            const response = await fetch(`${API_URL}/updateUsuario`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    id: WIKZ_USER.id,
                    nombre: nuevoNombre,
                    biografia: nuevaBio
                })
            });

            if (response.ok) {
                alert("✅ Perfil actualizado");
                // Actualizamos el objeto en memoria y recargamos
                WIKZ_USER.nombre = nuevoNombre;
                WIKZ_USER.biografia = nuevaBio;
                navegar('perfil', document.getElementById('nav-perfil'));
            }
        } catch (e) {
            alert("Error al actualizar");
        }
    }
}

/**
 * Carga la vista de perfil de forma segura adaptada a la API Java
 */
async function cargarPerfil(contenedor) {
    // 1. Cargando...
    contenedor.innerHTML = `<div class="viewport-center"><div class="loading">Cargando perfil...</div></div>`;

    let publicaciones = [];

    try {
        const [respUser, respPosts] = await Promise.all([
            fetch(`${API_URL}/getUsuarioId?id=${WIKZ_USER.id}`).catch(() => ({ ok: false })),
            fetch(`${API_URL}/getPublicacionesUsuario?idUsuario=${WIKZ_USER.id}`).catch(() => ({ ok: false }))
        ]);

        if (respUser && respUser.ok) {
            const datosFrescos = await respUser.json();
            WIKZ_USER.nombre = datosFrescos.nombre || WIKZ_USER.nombre;
            WIKZ_USER.biografia = datosFrescos.biografia || "";
            localStorage.setItem('wikz_session', JSON.stringify(WIKZ_USER));
        }

        if (respPosts && respPosts.ok) {
            publicaciones = await respPosts.json();
        }

    } catch (error) {
        console.warn("Se están usando datos locales por error de conexión.");
    }

    // 2. Pintamos la vista
    contenedor.innerHTML = `
        <div class="perfil-container" style="padding: 20px;">
            <div class="perfil-header" style="text-align: center;">
               <div class="perfil-foto-wrapper">
                <div class="avatar-glow-wrapper" style="width: 110px; height: 110px; margin: 0 auto; border-radius: 50%; padding: 3px; background: linear-gradient(135deg, #6a1bb1, #9b5de5); box-shadow: 0 0 20px rgba(155, 48, 255, 0.3);">
                    <img id="avatar-preview-main" 
                        src="${WIKZ_USER.id > 0 ? API_URL + '/fotoPerfil?id=' + WIKZ_USER.id : 'assets/img/fotoperfil.jpg'}" 
                            onerror="this.onerror=null; this.src='assets/img/fotoperfil.jpg';" 
                            style="width: 100%; height: 100%; border-radius: 50%; object-fit: cover;">
                    </div>
                </div>

                <h2 class="titulo-gradient" style="margin-top:15px; font-weight: 700;">${WIKZ_USER.nombre}</h2>
                
                <p class="bio-text" style="color: #d6b9ff; margin: 10px auto; text-align: center; max-width: 85%; font-size: 14px;">
                    ${WIKZ_USER.biografia || "Sin biografía aún..."}
                </p>
                
                <button onclick="navegar('editar-perfil', this)" style="background: rgba(199, 125, 255, 0.1); border: 1px solid rgba(199, 125, 255, 0.4); color: white; padding: 10px 20px; border-radius: 20px; cursor: pointer;">
                    <span class="material-icons" style="vertical-align: middle; font-size: 18px;">edit</span> Editar Perfil
                </button>
            </div>

            <div class="perfil-divider" style="height: 1px; background: rgba(199, 125, 255, 0.2); margin: 30px 0;"></div>

            <div id="perfil-posts-grid" class="explorar-grid">
                ${publicaciones.length > 0 ?
                    publicaciones.map(pub => `
                        <div class="post-card">
                            <div class="post-image-container">
                                <img src="${API_URL}/getImagenPublicacion?id=${pub.id}" onerror="this.src='assets/img/default.jpg'">
                            </div>
                            <div class="post-info">
                                <h3>${pub.titulo}</h3>
                            </div>
                        </div>
                    `).join('')
                    : `<p style="text-align:center; color: #888; grid-column: 1/-1;">Aún no has publicado nada.</p>`
                }
            </div>
            
            <button class="btn-logout" onclick="cerrarSesion()" 
                style="width: 100%; max-width: 250px; margin: 40px auto; display: block; background: rgba(155, 93, 229, 0.1); border: 1px solid rgba(155, 93, 229, 0.5); color: #d6b9ff; padding: 12px; border-radius: 25px; cursor: pointer; font-weight: 600; transition: 0.3s;">
                <span class="material-icons" style="vertical-align: middle; font-size: 18px; margin-right: 5px;">logout</span>
                Cerrar Sesión
            </button>
        </div>
    `;
}

/**
 * Vista de edición también actualizada con /fotoPerfil
 */
function cargarEditarPerfil(contenedor) {
    // Ajuste de ruta de imagen para la vista de edición
    const fotoUrl = WIKZ_USER.id > 0
        ? `${API_URL}/fotoPerfil?id=${WIKZ_USER.id}`
        : 'assets/img/fotoperfil.jpg';

    contenedor.innerHTML = `
        <div class="viewport-center">
            <div class="login-container" style="max-width: 400px; margin: auto;">
                <h2 class="titulo-gradient">Editar Perfil</h2>
                
                <div class="edit-avatar-section" id="area-foto-perfil" style="cursor:pointer; text-align:center;">
                    <div class="avatar-glow-wrapper" style="width: 110px; height: 110px; margin: 0 auto; border-radius: 50%; padding: 3px; background: linear-gradient(135deg, #6a1bb1, #9b5de5);">
                        <img id="avatar-preview-edit" 
                             src="${fotoUrl}" 
                             onerror="this.onerror=null; this.src='assets/img/fotoperfil.jpg';" 
                             style="width: 100%; height: 100%; border-radius: 50%; object-fit: cover;">
                    </div>
                    <p style="font-size: 12px; color: #d6b9ff; margin-top: 10px;">Click para cambiar foto</p>
                </div>
                
                <input type="file" id="edit-file-input" accept="image/*" style="display:none">

                <div style="text-align: left; margin-top: 20px;">
                    <label>Nombre de usuario</label>
                    <input type="text" id="edit-nombre-val" value="${WIKZ_USER.nombre}" class="wikz-input-glass">
                    
                    <label style="display:block; margin-top:10px;">Biografía</label>
                    <textarea id="edit-bio-val" class="wikz-input-glass" rows="4">${WIKZ_USER.biografia || ""}</textarea>
                </div>

                <button class="btn-publish-glow" id="btn-save-perfil" style="width:100%; margin-top:20px;" onclick="ejecutarActualizacion()">
                    Guardar Cambios
                </button>
            </div>
        </div>
    `;

    // Eventos de selección de imagen
    const fotoArea = document.getElementById('area-foto-perfil');
    const inputFile = document.getElementById('edit-file-input');
    fotoArea.onclick = () => inputFile.click();
    inputFile.onchange = function () {
        if (this.files && this.files[0]) {
            const reader = new FileReader();
            reader.onload = (e) => { document.getElementById('avatar-preview-edit').src = e.target.result; };
            reader.readAsDataURL(this.files[0]);
        }
    };
}

// Función principal de actualización
async function ejecutarActualizacion() {
    const btn = document.getElementById('btn-save-perfil');
    const nuevoNombre = document.getElementById('edit-nombre-val').value;
    const nuevaBio = document.getElementById('edit-bio-val').value;
    const fileInput = document.getElementById('edit-file-input');

    if (!nuevoNombre.trim()) return alert("El nombre es obligatorio");

    btn.disabled = true;
    btn.innerHTML = `<span class="material-icons rotating">sync</span> Procesando...`;

    try {
        let base64Image = null;
        if (fileInput.files[0]) {
            base64Image = await imageToBase64(fileInput.files[0]);
        }

        // ¡ATENCIÓN AQUÍ!: He cambiado el nombre del campo a 'fotoPerfilBase64'
        const datos = {
            id: WIKZ_USER.id,
            nombre: nuevoNombre,
            biografia: nuevaBio,
            fotoPerfilBase64: base64Image // Antes decía imagenBase64
        };

        // 1. Enviamos a la API de Java
        const response = await fetch(`${API_URL}/updateUsuario`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datos)
        });

        if (response.ok) {
            // 2. Sincronizar con la sesión de PHP
            await fetch(`index.php?controller=Usuario&action=actualizarSesionJS`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `nombre=${encodeURIComponent(nuevoNombre)}&biografia=${encodeURIComponent(nuevaBio)}`
            });

            // 3. Actualizar objeto global y LocalStorage
            WIKZ_USER.nombre = nuevoNombre;
            WIKZ_USER.biografia = nuevaBio;
            localStorage.setItem('wikz_session', JSON.stringify(WIKZ_USER));

            alert("✅ ¡Perfil actualizado con éxito!");

            // 4. Volver al perfil
            navegar('perfil', document.getElementById('nav-perfil'));
        } else {
            const errorText = await response.text();
            alert("❌ Error del servidor: " + errorText);
        }
    } catch (e) {
        console.error("Error en update:", e);
        alert("❌ Error de conexión con el servidor");
    } finally {
        if (btn) {
            btn.disabled = false;
            btn.innerHTML = "Guardar Cambios";
        }
    }
}

function cerrarSesion() {
    localStorage.removeItem('wikz_session');
    window.location.href = 'index.php?controller=Usuario&action=login';
}