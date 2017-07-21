package com.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Predicates;
import com.platform.server.contents.FeignPath;

import feign.Request;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Gunnar Hillert
 *
 */
@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class ApiDocApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApiDocApplication.class, args);
	}
	
    @Bean
    Request.Options feignOptions() {
        return new Request.Options(/** connectTimeoutMillis **/
                100 * 1000, /** readTimeoutMillis **/
                100 * 1000);
    }

	@Bean
	public Docket testApi() {
		@SuppressWarnings("unchecked")
		Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("test")
				.genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
				.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select()
				.paths(Predicates.or(PathSelectors.regex("/" + FeignPath.TVPLAY_PATH + ".*"),
						PathSelectors.regex("/" + FeignPath.WEB_SPIDER_PATH + ".*"),
						PathSelectors.regex("/" + FeignPath.MUSIC_PATH + ".*"),
						PathSelectors.regex("/" + FeignPath.MOVIE_PATH + ".*"),
						PathSelectors.regex("/" + FeignPath.USERCENTER_PATH + ".*")))// 过滤的接口
				.build().apiInfo(testApiInfo());
		return docket;
	}

	private ApiInfo testApiInfo() {
		ApiInfo apiInfo = new ApiInfo("Test相关接口", // 大标题
				"Test相关接口，主要用于测试.", // 小标题
				"1.0", // 版本
				"", "", // 作者
				"", // 链接显示文字
				""// 网站链接
		);

		return apiInfo;
	}

}
