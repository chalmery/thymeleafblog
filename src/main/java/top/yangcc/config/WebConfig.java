package top.yangcc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yangcc.interceptor.LoginInterceptor;

/**
 * MVC配置器
 * @author yangcc
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //自定义拦截规则
        registry.addInterceptor(new LoginInterceptor())
        .addPathPatterns("/back/**")
        .excludePathPatterns("/back")
        .excludePathPatterns("/back/login");
    }
}
