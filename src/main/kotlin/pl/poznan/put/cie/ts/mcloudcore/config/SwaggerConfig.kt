package pl.poznan.put.cie.ts.mcloudcore.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact


/** Annotated controller is exposed to the public api */
@Target(AnnotationTarget.CLASS)
annotation class PublicAPI

@Configuration
@EnableSwagger2
class SwaggerConfig {

    companion object {
        const val NEVER_RETURNS = "Never returns"
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(PublicAPI::class.java))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(info())
    }

    private fun info(): ApiInfo {
        return ApiInfoBuilder().title("mcloud REST")
                .description("Simple cloud for MongoDB")
                .contact(Contact("Jakub Riegel", "www.jrie.eu", "jakub.riegel@student.put.poznan.pl"))
                .license("MIT")
                .licenseUrl("#")
                .version("1.0.0")
                .build()
    }
}