# Sistema de Gestion de Usuarios - PROYECTO GIMNASIO

## Estudiante
Nombre: Guillermo Corrales Giammattei

---

## Como Ejecutar el Proyecto

Asegurate de tener instalado Java 17 (o superior) y Maven. Puedes ejecutar el proyecto desde la terminal usando el wrapper de Maven o directamente desde tu entorno de desarrollo (IntelliJ IDEA, Eclipse, etc.):

1. Abre una terminal en la carpeta raiz del proyecto.
2. Ejecuta el siguiente comando para compilar y levantar la aplicacion:
   ```bash
   mvn spring-boot:run

   Abre tu navegador web e ingresa a: http://localhost:8080

   Credenciales de prueba predeterminadas:

Administrador: admin / admin123

Usuario estandar (Rol USER): maria / maria123

Como Correr las Pruebas
Para ejecutar las pruebas unitarias y de integracion configuradas en el proyecto mediante Maven, utiliza el siguiente comando en la terminal:
##  "mvn test"

Lista de Archivos Modificados y Creados por Ejercicio
A continuacion se detalla la traza de los componentes y vistas intervenidos a lo largo de los ejercicios:

## Ejercicio 1: Nuevos campos en el modelo
Usuario.java: Agregados los campos telefono (con validaciones @NotBlank y @Pattern(regexp = "\\d{8}")) y activo (booleano con valor por defecto true).

lista.html: Incorporada la visualizacion del telefono y una insignia dinamica (badge verde para "Activo" o gris para "Inactivo").

formulario.html: Agregado el campo de texto para el telefono (con su respectiva validacion visual de errores) y un checkbox para el estado activo.

DataInitializer.java: Actualizada la inicializacion de datos para incluir telefonos validos en los usuarios predeterminados.

## Ejercicio 2: Regla de negocio: correo unico
UsuarioRepository.java: Agregado el metodo de consulta existsByEmail(String email).

UsuarioService.java: Implementada la validacion en los metodos crear y actualizar para lanzar una excepcion si el correo ya esta registrado, permitiendo que un usuario conserve su propio correo durante la edicion.

## Ejercicio 3: Busqueda y Filtrado de Usuarios
UsuarioRepository.java: Agregado el metodo de consulta por nombre o username ignorando mayusculas/minusculas (findByNombreContainingIgnoreCaseOrUsernameContainingIgnoreCase).

UsuarioService.java: Modificado el metodo listar(String q) para soportar la busqueda dinamica condicional.

UsuarioController.java: Actualizado el metodo GET /usuarios para recibir el parametro opcional q mediante @RequestParam.

lista.html: Incorporado el formulario de busqueda con su respectivo input que conserva el valor (th:value) y el boton "Limpiar".

## Ejercicio 4: Seguridad, Usuarios Inactivos y Auto-eliminacion
UsuarioDetailsService.java: Anadida la regla .disabled(!u.isActivo()) para denegar el acceso a sesion de usuarios inactivos.

login.html: Actualizado el texto de la alerta de error a "Usuario o contrasena incorrectos, o cuenta inactiva".

UsuarioController.java: Implementada la validacion en el metodo POST /usuarios/{id}/eliminar utilizando Principal para impedir que un administrador elimine su propia cuenta, mostrando una alerta de error en la vista.

## Ejercicio 5: Pagina de Detalle por Rol
UsuarioController.java: Creada la ruta GET /usuarios/{id} para consultar un usuario especifico y manejada la excepcion para mostrar un mensaje amigable en caso de IDs inexistentes (evitando la Whitelabel Error Page).

lista.html: Modificada la columna del nombre en la tabla principal para convertirlo en un enlace interactivo hacia el detalle de cada usuario.

detalle.html (Creado): Nueva vista en src/main/resources/templates/usuarios/detalle.html para mostrar la informacion completa del usuario (ocultando credenciales) y protegiendo el boton "Editar" mediante Spring Security (sec:authorize="hasRole('ADMIN')").

SecurityConfig.java: Ajustadas las reglas de autorizacion HTTP (authorizeHttpRequests) para permitir que cualquier usuario autenticado acceda a las vistas de lectura (/usuarios y /usuarios/{id}), manteniendo restringidas las acciones administrativas.