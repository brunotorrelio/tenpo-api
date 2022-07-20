# Challenge Tenpo

## Pasos para levantar y probar el sistema

### 1 - Ejecutar docker compose
``` shell
> docker-compose up
```
Este comando realiza las siguientes acciones:
- Levanta una base de datos PostgreSQL.
- Crea el esquema de tablas para el sistema.
- Inserta un usuario (username: Admin, password: Admin)
- Compila y levanta la aplicación en localhost:8080

### 2 - Ingresar a Swagger
http://localhost:8080/api/swagger-ui/index.html

    1 - Crear un usuario usando el servicio POST /user/signup o usar el usuario ya existente (username: Admin, password: Admin)

    2 - Iniciar sesión con el usuario creado o el usuario Admin usando el servicio POST /user/login
 
    3 - El response del servicio de login devolverá el token del usuario. Copiar el token y agregarlo en el dialog Authorize de Swagger

### Servicios públicos
    - POST /user/signup
    - POST /user/login

### Servicios que requieren autenticación a través del token
    - GET /calculator/addition
    - GET /history
    - POST /user/signoff

## Consideraciones técnicas

- Se utiliza Bearer JWT como método de autenticación.
- El sistema almacena todos los response de todos los endpoints que fueron ejecutados exitosamente (a excepción del endpoint GET /history, que es el que se utiliza para consultar esta información)
- Para el cierre de sesión del usuario se utiliza como estrategia una blacklist de tokens en la base de datos. Al cerrar sesión con
 un token, este es agregado en la blacklist para que quede inutilizable. Si el usuario quiere acceder con el mismo token,
 luego de haber cerrado sesión, el sistema responderá que el token es inválido.
- El código tuene un 100 % de coverage de test unitarios a nivel clases y 82% a nivel métodos. 