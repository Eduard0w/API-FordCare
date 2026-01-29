package com.FordCare.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}


/*
TODO: Proteger informações do usuario (Email, veiculo cadastrado, senha, id);
 Bloquear possibilidade de invasores terem acesso a informações que não são deles;
 Proteger endpoints;
 evitar que invasor consiga fazer alterações indevidas.
 */

/*
* TODO: O usuario só pode ver os veiculos que pertencem a ele
*  */