![Estado del Proyecto](https://img.shields.io/badge/Proyecto%20terminado-blue)

## Gestión de Personal Escolar - CRUD Java Maven con GUI

### Descripción
Este proyecto es una aplicación hecha con Java Maven donde ofrece funcionalidades CRUD (Crear, Leer, Actualizar, Eliminar) sobre una base de datos. 
Utiliza interfaces gráficas como JFrame y JDialog para brindar a los usuarios una experiencia intuitiva en la gestión de la información.
Para el desarrollo de este proyecto se implementó la arquitectura Model-View-Controller (MVC) para organizar el código de manera que sea más fácil de mantener y escalar. De esta manera, separando las responsabilidades de datos, presentación y control, MVC facilita la reutilización del código.

### Características
- Crear nuevos registros.
- Leer y visualizar registros existentes.
- Actualizar información de registros existentes.
- Eliminar registros de la base de datos.
- Visualizar todos los registros.

### Tecnologías Utilizadas
- Java: Lenguaje de programación principal utilizado en el desarrollo del proyecto.
- NetBeans: Entorno de desarrollo integrado (IDE) utilizado para la creación del proyecto.
- MySQL: Sistema de gestión de bases de datos relacional utilizado para almacenar datos.
- MySQL Workbench: Herramienta utilizada para el diseño y administración de la base de datos MySQL.

### Instalación
Para ejecutar este proyecto, sigue estos pasos:
1. Asegúrate de tener Java instalado en tu máquina. Puedes descargar la última versión de [Java SE](https://www.oracle.com/co/java/technologies/downloads/#java17).
2. Descarga este proyecto clonándolo en un directorio existente o descárgalo como ZIP y descomprime el archivo ZIP si es necesario.
3. Abre tu IDE compatible con JDK 17 y carga el proyecto.
4. Descarga el conector MySQL JDBC desde [aquí](https://dev.mysql.com/downloads/connector/j/).
5. Instala y configura MySQL, un sistema de gestión de bases de datos relacional. Puedes descargar MySQL desde [aquí](https://dev.mysql.com/downloads/file/?id=526408).
6. Instala y configura MySQL Workbench, una herramienta de diseño de bases de datos y administración para MySQL. Puedes descargar MySQL Workbench desde [aquí](https://dev.mysql.com/downloads/workbench/).

### Uso
1. Ejecuta la aplicación desde tu IDE, preferiblemente NetBeans.
2. Ejecuta MySQL Workbench para gestionar los datos y crear la base de datos crud_escuela utilizando el script crud_escuela.sql que se encuenta en 'src/main/resources/database.
3. En el proyecto, dentro de la carpeta src/main, crear una carpeta llamada 'resources' para que se genere un paquete llamado 'Other Sources'.
4. Dentro del paquete Other Sources, en 'src/main/resources' crear un archivo de tipo 'Properties File' llamado 'configuracionDB' donde se ingresan los datos de la base de datos. Por ejemplo:
- driver=com.mysql.cj.jdbc.Driver
- username=root
- password=passwordasignada
- hostname=localhost
- port=3306
- database=crud_escuela
5. Ejecuta la aplicación y utiliza los botones correspondientes para realizar las operaciones CRUD.

### Créditos
Este proyecto fue desarrollado gracias a los conocimientos adquiridos por el curso 'Aprende Programación en Java (de Basico a Avanzado)' por Alejandro Miguel Taboada Sanchez.
