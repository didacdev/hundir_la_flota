# Hundir la Flota

El repositorio contiene el proyecto final de la asignatura Sistemas Distribuidos del Grado en Ingeniería Informática.
Consiste en la implementación del juego "Hundir la flota" multijugador, con un módulo base de datos, servidor y cliente, para que varios jugadores puedan iniciar diferentes partidas y jugar de manera concurrente.
Para el desarrollo del proyecto se ha utilizado Java RMI.

## Estructura
- bd: módulo que representa una base de datos en la que almacenar los datos de las partidas iniciadas, en ejecución o terminadas, así como los datos de los jugadores.
- cliente: implementa el servicio cliente que utilizarán los jugadores para crear partidas, unirse a ellas y jugar sus partidas.
- servidor: proporciona el servicio servidor, haciendo de intermediario entre la base de datos y el cliente. También implementa la lógica del juego.
- common: contiene todas las interfaces que representan los objetos remotos, los cuales utilizarán el resto de servicios para comunicarse y enviar información.

## Instalación

1. Descarga los paquetes JAR de la última versión del proyecto
2. Inicia el módulo bd.jar
  ```
  java -jar bd.jar
  ```
3. Inicia el módulo servidor.jar
  ```
  java -jar servidor.jar
  ```
4. Inicia tantos clientes como desees.
  ```
  java -jar cliente.jar
  ```








