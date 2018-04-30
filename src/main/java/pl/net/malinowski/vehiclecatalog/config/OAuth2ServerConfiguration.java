package pl.net.malinowski.vehiclecatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2ServerConfiguration {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        private static final String[] AUTH_WHITELIST = {
                // -- swagger ui
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
                // other public endpoints of your API may be appended to this array
        };

        @Autowired
        private JwtAccessTokenConverter jwtAccessTokenConverter;

        @Override
        public void configure(final ResourceServerSecurityConfigurer resources) {
            resources
                    .tokenStore(new JwtTokenStore(jwtAccessTokenConverter));
        }

        @Override
        public void configure(final HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/account/register").permitAll()
                    .and().authorizeRequests()
                    .antMatchers("/route/**").permitAll()
                    .and().authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .and()
                    .authorizeRequests().antMatchers("/**").authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private JwtAccessTokenConverter jwtAccessTokenConverter;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(new JwtTokenStore(jwtAccessTokenConverter))
                    .authenticationManager(authenticationManager)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }

        @Override
        public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("client")
                    .secret(passwordEncoder.encode("secret"))
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("read", "write");
        }
    }
}
