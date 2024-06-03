package dev.vorstuu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VorstuuApplication {
	private static Initializer initiator;
	@Autowired
	public void setInitialLoader(Initializer initiator){
		VorstuuApplication.initiator = initiator;
	}

	public static void main(String[] args) {
		SpringApplication.run(VorstuuApplication.class, args);
		initiator.initial();
	}

}
