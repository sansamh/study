package sansam.v2.beans;

/**
 * @version 2.0
 * @description: 依赖注入的属性对象
 * @author: 侯春兵
 * @Date: 11:35 2018/11/29
 */
public class PropertyValue {

    private String name;
    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
