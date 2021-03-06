# Trabajo final de Sistemas Distribuidos y Programacion Paralela

## En curso: Desarrollo de propuesta

[Propuesta de trabajo final]

### Instalacion y ejecucion 

Necesitamos tener instalados los siguiente: 

* [JDK 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Node JS (LTS preferible)](https://nodejs.org/es/download/)
* [Maven 3.5](https://maven.apache.org/download.cgi)
* [MongoDB 4.2.0](https://www.mongodb.com/download-center/community)

Desde CMD ejecutar el siguiente comando 
<pre>
  npm install @angular/cli -g
</pre>


Sobre las carpetas **../node/frontend** y **../balancer/frontend** ejecutar el siguiente comando 

<pre>
  npm install
</pre>

Sobre las carpetas **../node/backend** y **../balancer/backend** ejecutar el siguiente comando

<pre>
  mvn package
</pre>
 
En las carpetas **../node/backend/target** y **../balancer/backend/target** encontraremos los jar que podemos ejecutar con el siguiente comando

<pre>
  java -jar nombre-del-jar.jar --server.port=puerto
</pre>

Luego desde el navegador podemos acceder al frontend de cada aplicacion desde <pre>http://localhost:puerto/</pre> donde localhost puede ser cambiado por la ip correspondiente y el puerto corresponde al que se selecciona al iniciar la aplicacion
