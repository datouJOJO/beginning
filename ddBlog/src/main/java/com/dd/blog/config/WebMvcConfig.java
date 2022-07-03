package com.dd.blog.config;


import com.dd.blog.handler.LoginHandler;
import com.dd.blog.handler.PageableHandlerInterceptor;
import com.dd.blog.handler.TokenHandler;
import com.dd.blog.handler.WebSecurityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc配置
 *
 * @author dd
 * @date 2022/04/06
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public WebSecurityHandler getWebSecurityHandler() {
        return new WebSecurityHandler();
    }

    @Bean
    public TokenHandler getTokenHandler() {return new TokenHandler();}

    @Bean
    public LoginHandler getLoginHandler() {return new LoginHandler();}

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedMethods("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageableHandlerInterceptor());
        registry.addInterceptor(getWebSecurityHandler());
        registry.addInterceptor(getLoginHandler());
        registry.addInterceptor(getTokenHandler());
    }
}
