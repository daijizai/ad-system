package asia.daijizai.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/3 17:40
 * @description 方便使用所有的索引。
 * Aware：对…有兴趣的 ApplicationContextAware：想要使用ApplicationContext
 * 通过应用程序上下文得到当前系统中spring容器中给我们初始化的各个组件或者说各个bean
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    public static final Map<Class, Object> dataTableMap = new ConcurrentHashMap<>();//保存所有的index服务
    private static ApplicationContext applicationContext;

    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz) {

        T instance = (T) dataTableMap.get(clazz);
        if (null != instance) {//命中
            return instance;
        }

        dataTableMap.put(clazz, bean(clazz));
        return (T) dataTableMap.get(clazz);
    }

    //通过bean的名字获取到容器中的bean
    @SuppressWarnings("all")
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    //通过java类的类型获取到容器中的bean
    @SuppressWarnings("all")
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    //定义初始化的优先级
    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

}
