package io.sansam.generate;

import io.sansam.common.exception.RgoInitFailedException;
import io.sansam.utils.WapperContext;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * RandomBuild
 * </p>
 *
 * @author houcb
 * @since 2019-09-04 17:42
 */
public class RandomGenerate implements Generate {


    @Override
    public <T> T product(Class<?> clazz) throws Exception {
        T t = null;
        if (clazz == null) {
            return t;
        }

        if (clazz.isPrimitive()) {
            throw new RgoInitFailedException("Primitive type do not support!");
        }

        //需要有无参的构造器
        try {
            t = (T) clazz.newInstance();
        } catch (Exception e) {
            throw new RgoInitFailedException(String.format("初始化Class[%s]失败，请检查是否有默认无参构造器！", clazz.getName()));
        }


        Field[] fields = clazz.getDeclaredFields();
        //属性字段
        String fileName;
        //符合JavaBean规则的属性
        PropertyDescriptor property;
        //set方法对象
        Method method;

        Field field;

        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            //属性字段
            fileName = field.getName();

            try {
                property = new PropertyDescriptor(fileName, clazz);
            } catch (IntrospectionException e) {
                //没有设置set方法，或者不符合JavaBean规范
                continue;
            }

            //获取set方法对象
            method = property.getWriteMethod();

            Annotation[] annotations = field.getAnnotations();
            List<String> patternList = new ArrayList<>(4);
            String className;
            for (Annotation annotation : annotations) {
                className = annotation.annotationType().getName();
                if (WapperContext.containsPattern(className)) {
                    patternList.add(className);
                }
            }

            int length = patternList.size();

            if (length == 0) {
                WapperContext.getBasicDataWapper().wapper(field, method, t, this);
            } else if (length == 1) {
                WapperContext.getSpecialWapper(patternList.get(0)).wapper(field, method, t, this);
            } else {
                throw new RgoInitFailedException("Field Contains more than 1 Pattern, must less than 2");
            }

        }
        return t;
    }
}
