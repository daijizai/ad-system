package asia.daijizai.ad.advice;

import asia.daijizai.ad.annotation.IgnoreResponseAdvice;
import asia.daijizai.ad.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/7/30 18:58
 * @description
 */

//ResponseBodyAdvice：https://www.jb51.net/article/226704.htm
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    @Override
    @SuppressWarnings("all")//隐藏warnings
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            //拿到类的声明，如果类被IgnoreResponseAdvice注解修饰，代表该类不应该被CommonResponseDataAdvice影响
            return false;
        }
        if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            //拿到方法，如果方法被IgnoreResponseAdvice注解修饰，代表该方法不应该被CommonResponseDataAdvice影响
            return false;
        }

        return true;
    }

    @Override
    @SuppressWarnings("all")//隐藏warnings
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //body：响应对象
        CommonResponse<Object> response = new CommonResponse<>(0, "");
        if (null == body) {
            return response;
        } else if (body instanceof CommonResponse) {
            response = (CommonResponse<Object>) body;
        } else {
            response.setData(body);
        }

        return response;
    }
}
