package br.com.uol.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.fasterxml.classmate.TypeResolver;

import br.com.uol.api.exceptionhandler.Problem;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SpringFoxConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.uol.api"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalResponseMessages())
				.globalResponses(HttpMethod.POST, globalResponseMessages())
				.globalResponses(HttpMethod.PUT, globalResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalResponseMessages())
				.additionalModels(new TypeResolver().resolve(Problem.class))
				.tags(new Tag("cidades", "Cadastro de cidades"), 
					  new Tag("clientes", "Cadastro de clientes"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("API Busca Clientes")
				.description("API demo para buscar clientes e suas cidades")
				.version("1")
				.contact(new Contact("Victor", "https://github.com/victorhgoli", "victorhgoli@gmail.com"))
				.build();
	}
	
	
	private List<Response> globalResponseMessages(){
		
		return Arrays.asList(
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
					.description("Requisicao inválida (erro do cliente)")
					.build(),
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.description("Erro Interno do servidor")
					.build(),
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
					.description("Recurso não possui representação que poderia ser aceito pelo consumidor")
					.build()
				);
	}

}
