package Pizzeria.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ModelAttributeInterceptor modelAttributeInterceptor;

    public WebMvcConfig(ModelAttributeInterceptor modelAttributeInterceptor) {
        this.modelAttributeInterceptor = modelAttributeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(modelAttributeInterceptor);
    }
}

