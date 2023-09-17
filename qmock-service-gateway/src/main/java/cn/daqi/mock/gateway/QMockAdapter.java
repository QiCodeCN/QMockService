package cn.daqi.mock.gateway;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.*;

@SpringBootConfiguration
public class QMockAdapter implements WebMvcConfigurer {


    @Bean
    public QMockInterceptor mockInterceptor(){
        return new QMockInterceptor();
    }

    /* 拦截器配置 */
    public void addInterceptors(InterceptorRegistry registry){
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        // RequestInterceptor()为自己定义的拦截器
//        registry.addInterceptor(new QMockInterceptor()).excludePathPatterns("/index.html","/static/**","/qmock/**");
//        registry.addInterceptor(new QMockInterceptor()).addPathPatterns("/**"); -> 错误用法导致@Autowired为null
        registry.addInterceptor(mockInterceptor()).addPathPatterns("/**");
    }

    /* 视图跳转控制器 */
    public void addViewControllers(ViewControllerRegistry registry){}

    /* 静态资源处理 */
    public void addResourceHandlers(ResourceHandlerRegistry registry){}

    /* 默认静态资源处理器 */
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){}

    /* 这里配置视图解析器 */
    public void configureViewResolvers(ViewResolverRegistry registry){}


}
