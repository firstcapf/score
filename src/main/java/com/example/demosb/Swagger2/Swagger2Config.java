package com.example.demosb.Swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demosb.Controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("四川省第三次全国土地调查专业技术培训成绩管理系统")
                .description("注解使用方法，https://www.cnblogs.com/fengli9998/p/7921601.html")
                .termsOfServiceUrl("https://www.cnblogs.com/fengli9998/p/7921601.html")
                .version("1.0")
                .build();
    }

/**
 * @Api()
用于类；表示标识这个类是swagger的资源
tags–表示说明
value–也是说明，可以使用tags替代
但是tags如果有多个值，会生成多个list
 @ApiOperation() 用于方法；表示一个http请求的操作
 value用于方法描述
 notes用于提示内容
 tags可以重新分组（视情况而用）
 @ApiParam() 用于方法，参数，字段说明；表示对参数的添加元数据（说明或是否必填等）
 name–参数名
 value–参数说明
 required–是否必填
 @ApiModel()用于类 ；表示对类进行说明，用于参数用实体类接收
 value–表示对象名
 description–描述
 都可省略
 @ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改
 value–字段说明
 name–重写属性名字
 dataType–重写属性类型
 required–是否必填
 example–举例说明
 hidden–隐藏@ApiIgnore()用于类或者方法上，可以不被swagger显示在页面上
 比较简单, 这里不做举例
 @ApiImplicitParam() 用于方法
 表示单独的请求参数
 @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
 name–参数ming
 value–参数说明
 dataType–数据类型
 paramType–参数类型
 example–举例说明
 */
}
