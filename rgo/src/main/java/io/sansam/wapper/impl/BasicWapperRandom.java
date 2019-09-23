package io.sansam.wapper.impl;

import io.sansam.generate.Generate;
import io.sansam.utils.SelfUtils;
import io.sansam.wapper.RandomDataWapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * BasicWapper
 * </p>
 *
 * @author houcb
 * @since 2019-09-11 13:52
 */
public class BasicWapperRandom implements RandomDataWapper {

    private Logger logger = LoggerFactory.getLogger(BasicWapperRandom.class);

    @Override
    public <T> void wapper(Field field, Method method, T t, Generate generate, Object target,
                           Map<String, String> fieldMappingMap) throws Exception {
        try {
            //如果是字节类型(包含基本类型和包装类)
            if (field.getType() == byte.class || field.getType() == Byte.class) {
                method.invoke(t, SelfUtils.getByteValue());
            }
            //如果是short类型(包含基本类型和包装类)
            else if (field.getType() == short.class || field.getType() == Short.class) {
                method.invoke(t, SelfUtils.getShortValue());
            }
            //如果是char类型(包含基本类型和包装类)
            else if (field.getType() == char.class || field.getType() == Character.class) {
                method.invoke(t, SelfUtils.getCharValue());
            }
            //如果是整型(包含基本类型和包装类)
            else if (field.getType() == int.class || field.getType() == Integer.class) {
                method.invoke(t, SelfUtils.getIntValue());
            }
            //如果是float(包含基本类型和包装类)
            else if (field.getType() == float.class || field.getType() == Float.class) {
                method.invoke(t, SelfUtils.getFloatValue());
            }
            //如果是double(包含基本类型和包装类)
            else if (field.getType() == double.class || field.getType() == Double.class) {
                method.invoke(t, SelfUtils.getDoubleValue());
            }
            //如果是long(包含基本类型和包装类)
            else if (field.getType() == long.class || field.getType() == Long.class) {
                method.invoke(t, SelfUtils.getDoubleValue());
            }
            //如果是boolean(包含基本类型和包装类)
            else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                method.invoke(t, SelfUtils.getBooleanValue());
            }
            //如果是String(包含基本类型和包装类)
            else if (field.getType() == String.class) {
                method.invoke(t, SelfUtils.getRamdomString(8));
            }
            //如果是日期类型
            else if (field.getType() == Date.class) {
                method.invoke(t, SelfUtils.getDateValue());
            }

            //这里默认处理的是自定义对象
            else {
                Object obj = generate.product(field.getType());
                method.invoke(t, obj);
            }
        } catch (Exception e) {
            logger.error("BasicWapper wapper failed!", e);
            e.printStackTrace();
            throw new Exception("BasicWapper wapper failed!");
        }
    }
}
