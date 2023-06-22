/*
package com.sgbg.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@OpenAPIDefinition(
        info = @Info(title = "싱글벙글 API 명세서",
                description = "블록체인 기반 단기 모임 서비스 API",
                version = "v1")
)
@Configuration
@EnableWebMvc
public class SwaggerConfig {

    */
/*    @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.sgbg.api"))
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(getApiInfo());
        }

        private ApiInfo getApiInfo() {
            return new ApiInfoBuilder()
                    .title("BACKEND APIs")
                    .description("For API SERVER descriptions.")
                    .termsOfServiceUrl("http://localhost:8080/")
                    .version("1.0")
                    .build();
        }*//*


*/
/*    @Bean
    public GroupedOpenApi sgbgOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("싱글벙글 API v1")
                .pathsToMatch(paths)
                .build();
    }*//*

}
*/
