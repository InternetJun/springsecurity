package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;

/**
 * swagger访问地址
 * http://localhost:8000/swagger-ui/index.html
 * 一些常用注解说明
 * @Api：用在controller类，描述API接口
 * @ApiOperation：描述接口方法
 * @ApiModel：描述对象
 * @ApiModelProperty：描述对象属性
 * @ApiImplicitParams：描述接口参数
 * @ApiResponses：描述接口响应
 * @ApiIgnore：忽略接口方法
 */
@RequiredArgsConstructor
@EnableOpenApi
@Configuration
public class Swagger3Config {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.OAS_30)     //文档版本
                .pathMapping("/")
                .apiInfo(apiInfo())                     //将api的元信息设置为包含在json ResourceListing响应中。
                .select()                               //选择哪些接口作为swagger的doc发布
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .protocols(new HashSet<>(Arrays.asList("http", "https")))   //支持的通讯协议集合
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(swaggerProperties.getApplicationName() + " Api Doc")
                .description(swaggerProperties.getApplicationDescription())
                .contact(new Contact("lighter", null, "123456@gmail.com"))
                .version("Application Version: " + swaggerProperties.getApplicationVersion())
                .build();
    }

}