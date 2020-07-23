package cn.devifish.cloud.common.core.config;

import cn.devifish.cloud.common.core.constant.DateTimeConstant;
import cn.devifish.cloud.common.core.convert.ConverterEnumFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

import java.time.format.DateTimeFormatter;

/**
 * WebConfigurerAdapter
 * 公共 Web 配置适配器
 *
 * @author Devifish
 * @date 2020/7/23 17:32
 */
public abstract class WebConfigurerAdapter {

    /**
     * 注册公共的数据格式化组件
     * 包含
     * 日期时间转换格式
     * 枚举转换格式
     *
     * @param registry FormatterRegistry
     */
    public void addFormatters(FormatterRegistry registry) {
        //日期时间转换格式
        var registrar = new DateTimeFormatterRegistrar();
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.TIME_PATTERN));
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_PATTERN));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DateTimeConstant.DATE_TIME_PATTERN));
        registrar.registerFormatters(registry);

        //枚举转换格式
        var converterEnumFactory = new ConverterEnumFactory();
        registry.addConverterFactory(converterEnumFactory);
    }

}
