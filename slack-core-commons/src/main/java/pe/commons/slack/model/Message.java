package pe.commons.slack.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Message implements Serializable {
    private String component;
    private String serviceException;
    private String request;
    private String application;
    private String date;

}
