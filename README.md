# W2M Superheroes Challenge

## Instrucciones para probar la app

Ejecutar los siguientes comandos:

 - "mvn clean package"
 - "docker build -t superheroes ."
 - "docker run -p 8080:8080 superheroes"
 - Se puede consultar la documentación de la API y realizar pruebas ingresando a *localhost:8080/swagger-ui*

***Nota:*** La aplicación solicitará autenticarse, el usuario/pass utilizado de ejemplo para esta aplicación es: 

 - Usuario: admin
 - Pass: admin

Si se prueba desde Postman, colocar los datos en el tab Authorization. Seleccionar la opción "Basic Auth" y completar user y pass.
