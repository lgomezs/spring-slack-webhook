package pe.commons.slack.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pe.commons.slack.api.SlackWebHookClient;
import pe.commons.slack.config.ConditionalSlack;
import pe.commons.slack.model.Message;
import pe.commons.slack.model.SlackMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@ConditionalSlack
@Service
@Slf4j
public class SlackServiceImpl implements  SlackService{

    private final SlackWebHookClient slackWebHookClient;
    private final Environment environment;

    public SlackServiceImpl(SlackWebHookClient slackWebHookClient ,  Environment environment){
        this.slackWebHookClient=slackWebHookClient;
        this.environment = environment;
    }

    @Override
    public String sendSlack(Throwable cause, Object... args) {
        try {
            String strDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            String jsonRequest = null;

            Message message = new Message();
            message.setDate(strDate);
            message.setApplication(environment.getProperty("spring.application.name"));
            message.setComponent(args[0].toString());
            message.setRequest(args[1].toString());

            if (cause instanceof Exception) {
              message.setServiceException(cause.getMessage());
            }

            String jsonErrorMessage = convertToJSONString(message);
            log.info("payload slack webhook: {}", jsonErrorMessage);

            SlackMessage slackMessage = new SlackMessage();
            slackMessage.buildText(message, jsonErrorMessage);
            return slackWebHookClient.sendMessage(slackMessage);

        } catch (Exception ex) {
            log.error("Error slack webhook");
            return null;
        }
    }

    @Override
    public String sendSlack(Map<String, String> properties, byte[] message) {
        try {
            String strDate =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            Message errorMessage = new Message();
            errorMessage.setComponent(properties.get("Component-slack"));
            errorMessage.setApplication(properties.get(environment.getProperty("spring.application.name")));
            errorMessage.setServiceException(properties.get("Original-exception"));
            errorMessage.setDate(strDate);

            String jsonErrorMessage = convertToJSONString(errorMessage);

            log.info("send to slack, payload: {} , properties: {} ", jsonErrorMessage, properties);

            SlackMessage slackMessage = new SlackMessage();
            slackMessage.buildText(errorMessage, jsonErrorMessage);

            return slackWebHookClient.sendMessage(slackMessage);

        } catch (Exception ex) {
            log.error("Error in slack", ex);
            return null;
        }
    }

  /**
   *
   * @param object
   * @return json string
   * @throws JsonProcessingException
   */
    public static String convertToJSONString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.writeValueAsString(object);
    }

  /**
   *
   * @return mapper
   */
  public static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
