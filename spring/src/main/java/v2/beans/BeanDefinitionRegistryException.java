package v2.beans;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 14:23 2018/11/27
 */
public class BeanDefinitionRegistryException extends Exception {

    private static final long serialVersionUID = 659454567861L;

    public BeanDefinitionRegistryException (String mess) {
        super(mess);
    }

    public BeanDefinitionRegistryException (String mess, Throwable cause) {
        super(mess, cause);
    }
}
