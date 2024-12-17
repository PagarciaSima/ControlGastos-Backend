package com.pgs.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info (
				contact = @Contact (
						name = "Pablo",
						email = "pgarcsim2334@hotmail.com",
						url = "https://www.linkedin.com/in/pablo-garc%C3%ADa-simavilla-756469222/"
				),
				description = "OpenApi Documentation for Control de Gastos",
				title = "OpenApi specification - Pablo García Simavilla",
				version = "1.0"
		),
		servers  = {
				@Server(
						description = "Local ENV",
						url = "http://localhost:8081"
				)
		}
)

@SecurityScheme(
		name = "bearerAuth",
		description = "Jwt authentication",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat =  "JWT",
		in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {

}
