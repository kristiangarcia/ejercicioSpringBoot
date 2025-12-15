document.addEventListener('DOMContentLoaded', cargarEnemigos)

async function cargarEnemigos(){
    try{
        const response = await fetch('api/enemigo')
        const enemigos = await response.json()
        mostrarEnemigos(enemigos)
    }catch(error){
        console.error("Error al cargar usuarios "+error)
    }// Fin catch
}// Fin cargar enemigos

function mostrarEnemigos(enemigos){
    const tbody = document.getElementById('enemigosBody')
    const table = document.getElementById('enemigosTable')

    tbody.innerHTML = ''

    if (enemigos.length === 0){
        console.log("no hay enemigos")
        return
    }

    enemigos.forEach(enemigo => {
        const tr = document.createElement('tr')
        tr.innerHTML = `
            <td>${enemigo.id}</td>
            <td>${enemigo.nombre}</td>
            <td>${enemigo.pais}</td>
            <td>${enemigo.afiliacion_politica}</td>
            <td>
                <button class="btn-editar" onclick="editarEnemigo('${enemigo.id}', '${enemigo.nombre}', '${enemigo.pais}', '${enemigo.afiliacion_politica}')">Editar</button>
                <button class="btn-eliminar" onclick="eliminarEnemigo('${enemigo.id}')">Eliminar</button>
            </td>
        `
        tbody.appendChild(tr)
    })

    table.style.display = 'table'

}//Fin mostrar

//Aquí empezamos con la parte de insertar

document.getElementById('formInsertarEnemigo').addEventListener('submit', insertarEnemigo)

async function insertarEnemigo(event) {
    event.preventDefault()

    const id = document.getElementById('enemigoId').value
    const nombre = document.getElementById('nombre').value
    const pais = document.getElementById('pais').value
    const afiliacion = document.getElementById('afiliacion').value
    const btnEnviar = document.getElementById('btnSubmit')

    const isUpdate = id !== ''
    const url = isUpdate ? `api/enemigo/${id}` : 'api/enemigo'
    const method = isUpdate ? 'PUT' : 'POST'

    //Esto es mientras se procesa
    btnEnviar.disabled = true
    btnEnviar.textContent = isUpdate ? 'Actualizando...' : 'Enviando a Francia...'

    try{
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nombre: nombre,
                pais: pais,
                afiliacion_politica: afiliacion
            })
        })

        if(response.ok){
            alert(isUpdate ? 'Enemigo actualizado con éxito' : 'Enemigo agregado con éxito')
            document.getElementById('formInsertarEnemigo').reset()
            document.getElementById('enemigoId').value = ''
            document.getElementById('formTitle').textContent = 'Agregar nuevo enemigo'
            await cargarEnemigos()
        }else{
            const error = await response.text()
            console.log(error)
            alert('Error al guardar enemigo')
        }
    }catch(error){
        console.error(error)
        alert('Error al guardar enemigo')
    }finally{
        btnEnviar.disabled = false
        btnEnviar.textContent = 'Agregar Enemigo'
    }//Fin try catch
}//Fin insertar

// Función para editar enemigo - rellena el formulario
function editarEnemigo(id, nombre, pais, afiliacion_politica){
    document.getElementById('enemigoId').value = id
    document.getElementById('nombre').value = nombre
    document.getElementById('pais').value = pais
    document.getElementById('afiliacion').value = afiliacion_politica
    document.getElementById('formTitle').textContent = 'Actualizar enemigo'
    document.getElementById('btnSubmit').textContent = 'Actualizar Enemigo'
}

// Función para eliminar enemigo
async function eliminarEnemigo(id){
    if(!confirm('¿Estás seguro de que quieres eliminar este enemigo?')){
        return
    }

    try{
        const response = await fetch(`api/enemigo/${id}`, {
            method: 'DELETE'
        })

        if(response.ok){
            alert('Enemigo eliminado con éxito')
            await cargarEnemigos()
        }else{
            alert('Error al eliminar enemigo')
        }
    }catch(error){
        console.error('Error al eliminar:', error)
        alert('Error al eliminar enemigo')
    }
}