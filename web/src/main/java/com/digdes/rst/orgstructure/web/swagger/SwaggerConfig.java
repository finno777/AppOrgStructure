package com.digdes.rst.orgstructure.web.swagger;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        Docket docket =  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo("1.0"));
        return docket;
    }
    private ApiInfo getApiInfo(String version) {
        return new ApiInfo(
                "REST API ОргСтруктура",  // title
                "Сервисы ОргСтруктуры",     // description
                version,
                "",     // terms of service url
                new Contact("Yaroslavtsev.I", "", "yaroslavtsev.i@digdes.com"),
                "©DigDes",     // licence
                "http://digdes.com"      // licence url
        );
    }
}
