package com.sgbg.common.config;

//import com.sgbg.common.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    private final AuthInterceptor authInterceptor;

    private static final String[] EXCLUDE_PATHS = {
            "/auth/login",
            "/auth/logout",
            "/auth/refresh",
            "/swagger-ui",
    };

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(EXCLUDE_PATHS);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true);
    }
}

