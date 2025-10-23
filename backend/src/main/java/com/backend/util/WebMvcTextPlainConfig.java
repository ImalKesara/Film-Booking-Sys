package com.backend.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcTextPlainConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jacksonConverter) {
                List<MediaType> supported = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
                if (!supported.contains(MediaType.TEXT_PLAIN)) {
                    supported.add(MediaType.TEXT_PLAIN);
                    jacksonConverter.setSupportedMediaTypes(supported);
                }
            }
        }
    }
}
