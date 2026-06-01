package br.edu.ifto.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static br.edu.ifto.ecommerce.utils.Rotas.*;
import static br.edu.ifto.ecommerce.utils.Roles.*;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration //classe de configuração
@EnableWebSecurity //indica ao Spring que serão definidas configurações personalizadas de segurança
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        customizer ->
                                customizer
                                        .requestMatchers("/" + CADASTRO_CLIENTE).anonymous()
                                        .requestMatchers(POST, "/" + CLIENTES + SAVE + "/**").anonymous()

                                        .requestMatchers("/" + ADMIN_CLIENTES + "/**").hasAnyRole(ADMIN)
                                        .requestMatchers("/" + ADMIN_PRODUTOS + "/**").hasAnyRole(ADMIN)
                                        .requestMatchers("/" + ADMIN_VENDAS + "/**").hasAnyRole(ADMIN)

                                        .requestMatchers("/" + CARRINHO + "/**").hasAnyRole(USER)
                                        .requestMatchers("/" + PEDIDOS + "/**").hasAnyRole(USER)
                                        .requestMatchers("/" + PRODUTOS + "/**").hasAnyRole("ANONYMOUS", USER)

                                        .requestMatchers("/css/**").permitAll()
                                        .requestMatchers("/images/**").permitAll()
                                        .requestMatchers("/js/**").permitAll()

                                        .anyRequest() //define que a configuração é válida para qualquer requisição.
                                        .authenticated() //define que o usuário precisa estar autenticado.
                )
                .formLogin(customizer ->
                        customizer
                                .loginPage(LOGIN) //passamos como parâmetro a URL para acesso à página de login que criamos
                                .permitAll() //define que essa página pode ser acessada por todos, independentemente do usuário estar autenticado ou não.
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .successHandler(authSuccessHandler())
                )
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                //.headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .exceptionHandling(customizer ->
                        customizer.accessDeniedHandler(acessoNegadoHandler()))
                .logout(LogoutConfigurer::permitAll) //configura a funcionalidade de logout no Spring Security.
                .rememberMe(withDefaults()); //permite que os usuários permaneçam autenticados mesmo após o fechamento do navegador
        return http.build();
    }

    @Bean
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Bean
    public AcessoNegadoHandler acessoNegadoHandler() {
        return new AcessoNegadoHandler();
    }

    /**
     * Com o método, instanciamos uma instância do encoder BCrypt e deixando o controle dessa instância como responsabilidade do Spring.
     * Agora, sempre que o Spring Security necessitar condificar um senha, ele já terá o que precisa configurado.
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
