package com.htsc.cams.common.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/5
 * Time: 15:28
 * Description: No Description
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


     @Bean
     public Docket userApi(){
         return new Docket(DocumentationType.SWAGGER_2).groupName("case").apiInfo(apiInfo()).select()
                 .apis(RequestHandlerSelectors.basePackage("com.htsc.cams.web")).paths(PathSelectors.any()).build();
     }

     private ApiInfo apiInfo(){
         return new ApiInfoBuilder().title("seck kill case").termsOfServiceUrl("http://htsc.com")
                 .contact(new Contact("Net","http://www.baidu.com","163.copm")).version("0.0.1").build();
     }

}
