# ValenbiciAPI26T8 - Migración de Base de Datos a AWS

Este proyecto es una evolución de la Tarea 3, donde se ha migrado la base de datos local de Valenbisi a un entorno Cloud utilizando AWS RDS (Relational Database Service) dentro del Learning Lab.

## Tecnologías Utilizadas
- **Java SE** con interfaz gráfica Swing
- **Maven** para la gestión de dependencias 
- **AWS RDS (MySQL)** para el almacenamiento de datos en la nube
- **MySQL Workbench** para la administración y ejecución de scripts SQL

## Cambios Realizados
- Creación y configuración de una instancia MySQL pública en AWS.
- Configuración de las reglas de entrada (Inbound Rules) en el Grupo de Seguridad de AWS para permitir el acceso por el puerto 3306 (`0.0.0.0/0`).
- Actualización de la URL de conexión en `ConexionBDD.java` 
- Ejecución del script de creación de la tabla `historico` directamente en la instancia de la nube mediante MySQL Workbench.