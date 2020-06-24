package mum.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}password").roles("USER")
//                .and()
//                .withUser("admin").password("{noop}password").roles("ADMIN");

        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder()
                );

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/fonts/**","/font-awesome-4.7.0/**","/less/**","/scss/**",
                "/iconic/**","/linearicons-v1.0.0/**","/Montserrat/**","/PlayfairDisplay/**","/Poppins/**","/icons/**","/vendor/**",
                "/animate/**","/animsition/**","/bootstrap/**","/countdowntime/**","/css-hamburgers/**","/daterangepicker/**","/isotop/**",
                "/jquery/**","/jqueryui/**","/lightbox2/**","/MagnificPopup/**","/parallax100/**","/perfect-scrollbar/**","/select2/**",
                "/slick/**","/sweetalert/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
//
//        http.authorizeRequests().antMatchers("/", "/login", "/registration","/admin/**", "/h2-console/**").permitAll()
//
//                .antMatchers("/register","/","/about","/login","/resources/**","/static/**","/css/**","/js/**","/images/**","/webjars/**").permitAll()
//                //.and().authorizeRequests().antMatchers("/allTenant").permitAll()
//                .and().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
//               .and().authorizeRequests().antMatchers("/admin/product").hasAnyRole("SELLER")
//              //  .and().authorizeRequests().antMatchers("/addApartment").hasAnyRole("ADMIN")
//               // .and().authorizeRequests().antMatchers("/addContract").hasAnyRole("ADMIN")
//                //.and().authorizeRequests().antMatchers("/addPayment").hasAnyRole("ADMIN")
//                .and()
//                .formLogin().loginPage("/login")
//                .failureUrl("/login?error=true")
//                .usernameParameter("name")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/")
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();

        http.authorizeRequests()
                .antMatchers("/**", "/login", "/registration","/admin/**", "/h2-console/**").permitAll()
               // .antMatchers("/admin/**").permitAll()//.hasAuthority("ADMIN")
                .antMatchers("/admin/**").permitAll()//.hasAuthority("ADMIN")
                .antMatchers("/public/**").permitAll()//.hasAuthority("ADMIN")
                .antMatchers("/dba/**").permitAll()//.hasAuthority("DBA")
                .antMatchers("/user/**").permitAll()//.hasAuthority("USER")
                .anyRequest().authenticated() //all other urls can be access by any authenticated role

                .antMatchers("/getRegistrations/**").permitAll()//.hasAuthority("DBA")

                .and()
                .formLogin() //enable form login instead of basic login
                .loginPage("/login")
                .failureUrl("/login-error")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().csrf()
                .ignoringAntMatchers("/h2-console/**") //don't apply CSRF protection to /h2-console
                .and()
                .exceptionHandling().accessDeniedPage("/error/access-denied")
                .and().rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository())
        ;
        http.rememberMe().rememberMeParameter("remember-me").key("uniqueAndSecret");
        http.headers().frameOptions().disable();

    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);

        return jdbcTokenRepositoryImpl;
    }

}
