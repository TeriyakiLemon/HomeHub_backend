package com.tianchen.homehub_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class appConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/register","/api/login").permitAll() // 允许未认证用户访问 /api/register
                .anyRequest().authenticated() // 其他请求都需要认证
                .and()
                .logout()
                    .logoutUrl("/api/logout") // 设置登出的URL
                    .invalidateHttpSession(true)// 使HttpSession失效
                    .deleteCookies("JSESSIONID") // 删除cookie
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"message\": \"Logged out successfully\"}");
                        response.setStatus(200);
                }) // 登出成功后返回 JSON 格式的消息
                .and()
                .formLogin() // 启用基于表单的登录页面
                .and()
                .httpBasic(); // 启用基本身份验证
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

}
