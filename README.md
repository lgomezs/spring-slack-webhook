Webhook con Spring Boot
==========================

# Webhook

Un Webhook es una mecanismo que permite ser notificado cuando un evento ha ocurrido en tu aplicacion o la de un tercero. Es básicamente una solicitud POST que se envía a una URL específica. Esa URL está configurada para recibir el cuerpo de la solicitud POST y procesarla de alguna manera.
Utilizada generalmente para cuando se producen eventos en tu aplicativo y poder comunicar al grupo de slack sobre sobre estos eventos.

Requisitos:
  - Java 8
  - Maven 3
  - Docker version 18.09.2 o superior
  - Crear una cuenta en slack (https://evris.slack.com)
  
# Generar cuenta en slack

 - Crear una cuenta el slack  (https://evris.slack.com)
 - Crea y escoge un canal para enviar las notificaciones
 - Agregar Incoming Webhooks integration:

![Screenshot from running application](img/config-webhook.png?raw=true "Screenshot JWT Spring Security Demo")

 - Obtener la URL de Webhook.

# Generar Libreria

 ### Generate Library slack-core-commons  
  
    cd slack-core-commons  
    mvn clean install
  
 ### Configure
 
  Cambiar la URL en application.properties y pegar la generada aqui:
  
  commons.slack.webhook.endpoint = 
 
  ![Screenshot from running application](img/URL.png?raw=true "Screenshot SLACK Spring  Demo")
  
  
 ### Compile and execute app
 
    cd app-slack-webhook
    mvn clean install
    
    cd src/main/docker/ 
    docker-compose build slack-webhook
    docker-compose up slack-webhook
    
  ![Screenshot from running application](img/docker-up.png?raw=true "Screenshot SLACK")
  
  Send data to URI: http://localhost:8085/sendSlack
  
  example:
  
  ![Screenshot from running application](img/message-slack.png?raw=true "Screenshot SLACK")
  
  
  ![Screenshot from running application](img/POST.png?raw=true "Screenshot SLACK")
  
 