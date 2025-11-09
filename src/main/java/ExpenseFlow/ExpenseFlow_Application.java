package ExpenseFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpenseFlow_Application {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseFlow_Application.class, args);
		System.out.println("\n" +
				"╔══════════════════════════════════════════════════════════════╗\n" +
				"║                                                              ║\n" +
				"║            ExpenseFlow - Personal Finance Management         ║\n" +
				"║                        System Started                        ║\n" +
				"║                                                              ║\n" +
				"║  API Documentation: http://localhost:8080/swagger-ui.html    ║\n" +
				"║  Server Running on: http://localhost:8080                    ║\n" +
				"║                                                              ║\n" +
				"╚══════════════════════════════════════════════════════════════╝\n");
	}
}
