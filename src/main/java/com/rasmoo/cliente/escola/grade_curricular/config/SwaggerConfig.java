package com.rasmoo.cliente.escola.grade_curricular.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Value("${host.full.dns.auth.link}")
    private String authLink;

    public static final String CURSO = "Curso";
    public static final String Materia = "Materia";

    @Bean
    public Docket gradeCurricularApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rasmoo.cliente.escola.grade_curricular"))
                .paths(PathSelectors.any()).build().apiInfo(this.metaData())
                .securitySchemes(Collections.singletonList(this.securitySchema()))
                .securityContexts(Arrays.asList(this.securityContext()))
                .tags(new Tag(CURSO, "Operaçoes referentes a manipulaçao da entidade Curso."),
                        new Tag(Materia, "Operaçoes referentes a manipulaçao da entidade Materia"));

    }

    private ApiInfo metaData() {

        return new ApiInfoBuilder().title("GRADE CURRICULAR")
                .description("Api responsavel pela manutençao da grade curricular da Rasmoo School")
                .version("1.0.0").license("").build();
    }
    private OAuth securitySchema() {

        List<AuthorizationScope> authScope = new ArrayList<>();
        authScope.add(new AuthorizationScope("cw_logado", "acesso area logada"));
        authScope.add(new AuthorizationScope("cw_nao_logado", "acesso nao area logada"));

        List<GrantType> grantTypes = new ArrayList<>();
        grantTypes.add(new ResourceOwnerPasswordCredentialsGrant(this.authLink));

        return new OAuth("auth2-Schema", authScope, grantTypes);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(this.defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authScopes = new AuthorizationScope[2];
        authScopes[0] = new AuthorizationScope("cw_logado", "Acesso area logada");
        authScopes[1] = new AuthorizationScope("cw_nao_logado", "Acesso area nao logada");

        return Collections.singletonList(new SecurityReference("auth2-Schema", authScopes));
    }

}
