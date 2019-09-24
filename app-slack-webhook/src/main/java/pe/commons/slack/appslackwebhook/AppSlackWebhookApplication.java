package pe.commons.slack.appslackwebhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"pe.commons.slack"})
@SpringBootApplication(scanBasePackages = {"pe.commons.slack"})
public class AppSlackWebhookApplication {

	public static void main(String[] args) {
	    SpringApplication.run(AppSlackWebhookApplication.class, args);
	}

}
