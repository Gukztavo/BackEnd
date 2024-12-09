package com.example.projeto_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class
ProjetoApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjetoApiApplication.class, args);


		Scanner scanner = new Scanner(System.in);
		System.out.println ("Digite a porta desejada para iniciar o servidor");
		String port = scanner.nextLine();
		if (!port.matches("\\d+")){
			System.out.println("porta invalida, vai usar a porta padrao 8080");
			port = "8080";

		}
		System.setProperty("server.port",port);
		SpringApplication.run(ProjetoApiApplication.class, args);
	}

}
