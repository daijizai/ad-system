package asia.daijizai.ad.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/7/30 19:01
 * @description
 */

@Target({ElementType.TYPE, ElementType.METHOD})//可以修饰类、可以修饰方法
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}