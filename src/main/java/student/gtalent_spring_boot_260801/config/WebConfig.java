package student.gtalent_spring_boot_260801.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import student.gtalent_spring_boot_260801.interceptor.AuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 會員與訂單 API 先經過 AuthInterceptor 檢查 token。
        // /members/{id} 會額外檢查只能操作自己的會員資料。
        // register/login/logout 是登入流程本身，所以不做攔截。
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(
                        "/members/**",
                        "/api/orders/**",
                        "/books/**",
                        "/payments/**"
                )
                .excludePathPatterns(
                        "/members/register",
                        "/members/login",
                        "/members/logout",
                        "/members/forgot-password",
                        "/members/reset-password",
                        "/payments/newebpay/notify",
                        "/payments/newebpay/return",
                        "/api/line/**"
                     );
    }
}
