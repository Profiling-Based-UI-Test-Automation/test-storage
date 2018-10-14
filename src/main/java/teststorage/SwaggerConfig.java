package teststorage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;


/**
 * 
 */
@Configuration
@EnableSwagger2 // 스웨거 설정 가능케함
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("500 message")
                                        .responseModel(new ModelRef("Error"))
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden!")
                                        .build()));
    }
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "test-storage",
                "Application, Apk file, Test, Test Result( 테스트케이스 수, 테스트 케이스별 실패/성공 여부, 로그, 수행시간), Test device log, resource 사용 추이, 스냅 샷 등의 정보를 읽기/저장/삭제/업데이트하기 위한 API이다.",
                "API 1.0",
                "Terms of service",
                "domich.hwang@gmail.com",
                "github link",
                "https://github.com/Profiling-Based-UI-Test-Automation/test-storage");
        return apiInfo;
    }
}


