package com.rasmoo.cliente.escola.grade_curricular.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class OAuthConfiguration {

    public static final String RESOURCE_ID = "financesControll";

    @EnableAuthorizationServer
    public static class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("cliente-web")
                    .secret("$2a$10$XFNAXsGXXyiKA7OD2jpq3ulKYOtr2VGEscFr/I7xdg1uUZkSHR4j6")
                    .authorizedGrantTypes("password", "client_credentials")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .and()
                    .withClient("cliente-canva")
                    .secret("$2a$10$XFNAXsGXXyiKA7OD2jpq3ulKYOtr2VGEscFr/I7xdg1uUZkSHR4j6")
                    .authorizedGrantTypes("authorization_code", "implicit")
                    .redirectUris("https://www.canva.com/pt_br/")
                    .scopes("read")
                    .resourceIds(RESOURCE_ID);
        }
    }

    @EnableResourceServer
    public static class ResourceServer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().authenticated().and()
                    .requestMatchers()
                    .antMatchers("/api/v2/materias");

        }
    }

}
