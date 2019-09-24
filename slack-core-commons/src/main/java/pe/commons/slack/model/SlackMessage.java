package pe.commons.slack.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;

@Getter
@Setter
@Slf4j
public class SlackMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String text;

    public void buildText(Object object, String defaultText) {
        StringBuilder textBuffer = new StringBuilder("");
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                String name = field.getName();
                Object value = field.get(object);
                textBuffer.append("*").append(name).append("*");
                textBuffer.append(": ");
                textBuffer.append("`").append(value).append("`");
                textBuffer.append("\n");
            }
            setText(textBuffer.toString());
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            setText(defaultText);
        }
    }

}
