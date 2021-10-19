package com.rasmoo.cliente.escola.grade_curricular.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket gradeCurricularApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rasmoo.cliente.escola.grade_curricular")).build()
                .apiInfo(this.metaData());

    }

    private ApiInfo metaData() {

        return new ApiInfoBuilder().title("GRADE CURRICULAR")
                .description("Api responsavel pela manutençao da grade curricular da Rasmoo School")
                .version("1.0.0").license("").build();
    }

}
