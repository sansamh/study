package sansam.v1.beans;

/**
 * @version 1.0
 * @description: bean工厂
 * @author: 侯春兵
 * @Date: 11:16 2018/11/27
 */
public interface BeanFactory {

    /**
     * 通过beanName 获取 对象
     */
    Object getBean(String beanName) throws Exception;
}
