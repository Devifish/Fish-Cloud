package cn.devifish.cloud.common.webmvc;

import cn.devifish.cloud.common.webmvc.config.WebMvcConfiguration;
import cn.devifish.cloud.common.webmvc.handler.RestfulResponseMethodProcessor;
import cn.devifish.cloud.common.webmvc.handler.WebMvcExceptionAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * WebMvcAutoConfiguration
 * 公共 WebMvc 自动装载配置
 *
 * @author Devifish
 * @date 2020/8/25 17:16
 */
@Import({WebMvcConfiguration.class, WebMvcExceptionAdvice.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class WebMvcAutoConfiguration {

    @Bean
    @ConditionalOnBean(RequestMappingHandlerAdapter.class)
    public InitializingBean restfulResponseMethodProcessorBean(RequestMappingHandlerAdapter adapter) {
        return () -> {
            var returnValueHandlers = adapter.getReturnValueHandlers();
            var messageConverters = adapter.getMessageConverters();
            var processor = new RestfulResponseMethodProcessor(messageConverters);

            //构建新的returnValueHandlers集合
            returnValueHandlers = new ArrayList<>(Objects.requireNonNull(returnValueHandlers));
            returnValueHandlers.replaceAll(processor::replaceDefault);
            adapter.setReturnValueHandlers(returnValueHandlers);
        };
    }

}
