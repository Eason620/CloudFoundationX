package com.cfx.web.global;

import com.cfx.common.api.DataIMessage;
import com.cfx.common.api.IMessage;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Eason
 * @date 2023/4/23
 */
@RestControllerAdvice
public class GlobalResponseHandlerAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(@Nullable MethodParameter returnType, @Nullable Class converterType) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nullable MethodParameter parameter, @Nullable MediaType mediaType,
                                  @Nullable Class<? extends HttpMessageConverter<?>> converter, @Nullable ServerHttpRequest request, @Nullable ServerHttpResponse response) {
        if (body instanceof IMessage)
            return body;

        return DataIMessage.getSuccessInstance(body);
    }
}