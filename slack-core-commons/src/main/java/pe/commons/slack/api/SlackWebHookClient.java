package pe.commons.slack.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.commons.slack.config.ConditionalSlack;
import pe.commons.slack.model.SlackMessage;

@ConditionalSlack
@FeignClient(name = "slack", url = "${commons.slack.webhook.endpoint}")
public interface SlackWebHookClient {

    @PostMapping
    String sendMessage(@RequestBody SlackMessage message);

}
