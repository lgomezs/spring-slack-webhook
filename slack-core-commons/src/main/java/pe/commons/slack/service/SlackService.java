package pe.commons.slack.service;

import java.util.Map;

public interface SlackService {

    String sendSlack(Throwable cause, Object... args);

    String sendSlack(Map<String, String> properties, byte[] message);
}
