package com.example.springsecurityapplication.config;

import com.example.springsecurityapplication.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{
    private final PersonDetailsService personDetailsService;

    //Внимание шифрование паролей вырубается так: Юзается сугубо для тестинга, в реале это опасно
    //Шифрование используется beecrypt
//    @Bean
//    public PasswordEncoder getPasswordEncode(){
//        return NoOpPasswordEncoder.getInstance();
//    }
    @Bean
    public PasswordEncoder getPasswordEncode(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Конфигурируем работу спринг секурити
            http
        //http.csrf().disable() // csrf - отключаем защиту от межсайтовой подделки запросов
                .authorizeHttpRequests() // указываем что все страницы должны быть защищены аутентификацией
                //указываем что не аутентифицированные пользователи не могут зайти на страницу и на ошибку
                //с помощью permitAll указываем что не аутентифицированные пользователи могут заходить на перечисленные страницы
                .requestMatchers("/admin").hasRole("ADMIN")

                .requestMatchers("/authentication","/registration", "/error","/resources/**", "/static/**","/css/**", "/js/**", "/img/**").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                //.requestMatchers("/authentication", "/error","/registration").permitAll()
                //что касается всех остальных  страниц необходимо вызвать метод authenticated(), который открывает форму аутентификации

                //.anyRequest().authenticated()
                .and() //Соединяет компоненты с настройкой аутентификации
                //Указываем какой url запрос будет отправляться при заходе на защищенные страницы
                .formLogin().loginPage("/authentication")
                .loginProcessingUrl("/process_login") // указываем на какой адрес будет отправляться данные с формы. Нам не нужно создавать метод в контроллере и обрабатывать данные с формы. Мы задали url, который используется по умолчанию для обработки формы аутентификации по средствам Spring Security. Spring Security будет ждать объект с формы аутентифицации и затем сверять логин и пароль с данными БД
                .defaultSuccessUrl("/index", true) //Указываем на какой url необходимо направить пользователя после успешной аутентификации. Вторым аргументом указывается true чтобы перенаправление шло в любом случае после успешной аутентификации
                .failureUrl("/authentication?error")//Указываем куда необходимо перенаправить пользователя при провальной аутентификации. В запрос будет передан объект error, который будет проверяться на форме и при наличии данного объекта в запросе выводится - неправильный лог или пароль
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/authentication");

        return http.build();
    }
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    //private final AuthenticationProvider authenticationProvider;

    //public SecurityConfig(AuthenticationProvider authenticationProvider) {
    //    this.authenticationProvider = authenticationProvider;
    //}
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    //        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        authenticationManagerBuilder.userDetailsService(personDetailsService)
        .passwordEncoder(getPasswordEncode());
    }
}
