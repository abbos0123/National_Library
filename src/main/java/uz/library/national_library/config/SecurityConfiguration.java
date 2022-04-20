package uz.library.national_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()

                /* these are permissions for user resource*/

                .antMatchers("/api/user/worker/register").hasRole("ADMIN")
                .antMatchers("/api/user/admin/register").hasRole("ADMIN")
                .antMatchers("/api/user/update").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/user/delete/{id}").hasRole("ADMIN")
                .antMatchers("/api/user/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/user/all").hasRole("ADMIN")
                .antMatchers("/api/user/search").hasRole("ADMIN")

                /*this is permissions for book resource*/

                .antMatchers("/api/book/add").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/book/delete/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/book/update").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/book/find_with_id/*").permitAll()
                .antMatchers("/api/book/find_with_name/*").permitAll()
                .antMatchers("/api/book/authors_book/*").permitAll()
                .antMatchers("/api/book/allBook").permitAll()
                .antMatchers("/api/book/category").permitAll()
                .antMatchers("/api/book/search").permitAll()


                /* these are permissions for book files **/

                .antMatchers("/api/file/upload").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/file/upload/pdf_book").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/file/upload/image").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/file/preview/*").permitAll()
                .antMatchers("/api/file/download/*").permitAll()
                .antMatchers("/api/file/delete/*").hasAnyRole("ADMIN", "USER")

                /*these are permissions for news */

                .antMatchers("/api/news/add").hasRole("ADMIN")
                .antMatchers("/api/news/update").hasRole("ADMIN")
                .antMatchers("/api/news/delete").hasRole("ADMIN")
                .antMatchers("/api/news/add_view_count").permitAll()
                .antMatchers("/api/news/allNews").permitAll()
                .antMatchers("/api/news/{id}").permitAll()
                .antMatchers("/api/news/search").permitAll()

                .and()
                .httpBasic();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
