package v2.beans;

/**
 * 用于依赖注入中描述bean依赖 v2
 */
public class BeanReference {
    private String beanName;

    public BeanReference(String beanName) {
        super();
        this.beanName = beanName;
    }

    /**
     * 获得引用的beanName
     *
     */
    public String getBeanName() {
        return this.beanName;
    }
}
