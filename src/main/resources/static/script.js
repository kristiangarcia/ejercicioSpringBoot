document.addEventListener('DOMContentLoaded', cargarEnemigos)

let enemigosActuales = []

async function cargarEnemigos(){
    try{
        const response = await fetch('api/enemigo')
        const enemigos = await response.json()
        enemigosActuales = enemigos
        mostrarEnemigos(enemigos)
    }catch(error){
        console.error("Error al cargar usuarios "+error)
    }// Fin catch
}// Fin cargar enemigos

async function ordenarAlfabeticamente(){
    try{
        const response = await fetch('api/enemigo/ordenado')
        const enemigos = await response.json()
        enemigosActuales = enemigos
        mostrarEnemigos(enemigos)
    }catch(error){
        console.error("Error al ordenar "+error)
    }
}

async function buscarPorNombre(){
    const nombre = document.getElementById('buscarNombre').value.trim()
    if(nombre === ''){
        alert('Introduce un nombre para buscar')
        return
    }

    try{
        const response = await fetch(`api/enemigo/buscar?nombre=${encodeURIComponent(nombre)}`)
        const enemigos = await response.json()
        enemigosActuales = enemigos
        mostrarEnemigos(enemigos)
        if(enemigos.length === 0){
            alert('No se encontraron enemigos con ese nombre')
        }
    }catch(error){
        console.error("Error al buscar "+error)
    }
}

function descargarCSV(){
    if(enemigosActuales.length === 0){
        alert('No hay datos para descargar')
        return
    }

    const headers = ['ID', 'Nombre', 'Pais', 'Afiliacion_Politica']
    const csvContent = [
        headers.join(','),
        ...enemigosActuales.map(e =>
            `"${e.id}","${e.nombre}","${e.pais}","${e.afiliacion_politica}"`
        )
    ].join('\n')

    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' })
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = 'enemigos.csv'
    link.click()
    URL.revokeObjectURL(link.href)
}

function mostrarEnemigos(enemigos){
    const tbody = document.getElementById('enemigosBody')
    const table = document.getElementById('enemigosTable')

    tbody.innerHTML = ''

    if (enemigos.length === 0){
        console.log("no hay enemigos")
        table.style.display = 'none'
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

        const data = await response.json()

        if(response.ok){
            alert(isUpdate ? 'Enemigo actualizado con éxito' : 'Enemigo agregado con éxito')
            document.getElementById('formInsertarEnemigo').reset()
            document.getElementById('enemigoId').value = ''
            document.getElementById('formTitle').textContent = 'Agregar nuevo enemigo'
            await cargarEnemigos()
        }else{
            alert(data.error || 'Error al guardar enemigo')
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

        const data = await response.json()

        if(response.ok){
            alert(data.mensaje || 'Enemigo eliminado con éxito')
            await cargarEnemigos()
        }else{
            alert(data.error || 'Error al eliminar enemigo')
        }
    }catch(error){
        console.error('Error al eliminar:', error)
        alert('Error al eliminar enemigo')
    }
}