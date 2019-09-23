package io.sansam.wapper.impl;

import io.sansam.anno.ListPattern;
import io.sansam.common.constant.RgoConstant;
import io.sansam.common.exception.RgoInitFailedException;
import io.sansam.generate.Generate;
import io.sansam.utils.CommonUtils;
import io.sansam.wapper.RandomDataWapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * ListWapper
 * </p>
 *
 * @author houcb
 * @since 2019-09-11 15:34
 */
public class ListPatternWapperRandom implements RandomDataWapper {


    public static void main(String[] args) throws Exception {
        Class clz = ArrayList.class;
        List list = (List) clz.newInstance();
        list.add("1");
        System.out.println(list.size());
    }

    @Override
    public <T> void wapper(Field field, Method method, T t, Generate generate) throws Exception {
        ListPattern listPattern = field.getAnnotation(ListPattern.class);
        String length = listPattern.length();
        Class clz = listPattern.clz();
        int size;
        if (CommonUtils.isBlank(length)) {
            size = RgoConstant.DEFAULT_LIST_MAP_SIZE;
        } else {
            size = Integer.valueOf(length);
        }
        if (!List.class.isAssignableFrom(clz)) {
            throw new RgoInitFailedException("ListPattern Type is not a List!");
        }
        List list = (List) clz.newInstance();
        // 获取泛型
        Type fx = field.getGenericType();
        if (!Objects.isNull(fx) && fx instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) fx;
            Class<?> genericClazz = (Class) parameterizedType.getActualTypeArguments()[0];
            fillListWithGenericType(list, size, generate, genericClazz);
        } else {
            fillListWithString(list, size);
        }
        method.invoke(t, list);

    }

    @Override
    public <T> void wapperWithSourceObejct(Field field, Method method, T t, Generate generate, Object target,
                                           Map<String, String> fieldMappingMap) throws Exception {

    }

    private void fillListWithGenericType(List list, int size, Generate generate, Class<?> genericClazz) throws Exception {
        for (int i = 0; i < size; i++) {
            list.add(generate.product(genericClazz));
        }
    }

    private void fillListWithString(List list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(i + "");
        }
    }
}
