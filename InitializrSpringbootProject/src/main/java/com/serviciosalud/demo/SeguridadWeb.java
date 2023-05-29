package com.serviciosalud.demo;

import com.serviciosalud.demo.servicios.PacienteServicio;
import com.serviciosalud.demo.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {
    
    @Autowired
    public PacienteServicio pacienteServicio;
    
    @Autowired
    public ProfesionalServicio profesionalServicio;
    
    
    @Autowired
    public void configureGlobalPaciente(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pacienteServicio)
                .passwordEncoder(new BCryptPasswordEncoder());

    }
    
    @Autowired
    public void configureGlobalProfesional(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(profesionalServicio)
                .passwordEncoder(new BCryptPasswordEncoder());

    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers("/admin/*").hasRole("ADMIN")
                        .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                        .permitAll()
                .and().formLogin()
                        .loginPage("/login") //url donde se va a encontrar el formulario de login
                        .loginProcessingUrl("/logincheck") // url con la cual Spring Security va a autenticar un usuario 
                                                           // >> debe coincidir con el action del form de logueo
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio") //pagina a la que se redirecciona por default si todo sale bien
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout") // url con la cual se cierra sesion
                        .logoutSuccessUrl("/login") //url si se cierra exitosamente la session
                        .permitAll()
                .and().csrf()
                        .disable();
                
    }
    
}
