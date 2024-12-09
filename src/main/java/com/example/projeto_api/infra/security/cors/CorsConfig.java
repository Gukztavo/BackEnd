//package com.example.projeto_api.infra.security.cors;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer {
//    public void addCorsMappings(CorsRegistry registry){
//        System.out.println("---------------------------------------------------------------------------------------");
//        registry.addMapping("auth/**")
//                .allowedOrigins("*")
//                .allowedMethods("POST","GET","OPTIONS","DELETE","PUT")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
//}