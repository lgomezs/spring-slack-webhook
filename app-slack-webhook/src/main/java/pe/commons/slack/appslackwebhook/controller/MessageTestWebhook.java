package pe.commons.slack.appslackwebhook.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.commons.slack.service.SlackService;


@RestController
@EnableAutoConfiguration
@Slf4j
public class MessageTestWebhook {
    @Autowired
    private SlackService slackService;

    @PostMapping(value = "/sendSlack", produces = "application/json")
    public  String  getMessagesDB(@RequestBody String data) {
        try{
            //code aqui
            //si genera un error enviar al slack, generalmente desoues de un retry de forma asincrona
            throw new Exception("Error al obetner mensajes de la BD .");
        }catch (Exception exception){
            slackService.sendSlack(exception,"app-slack-webhook", data);
        }
        return "error";
    }


}




