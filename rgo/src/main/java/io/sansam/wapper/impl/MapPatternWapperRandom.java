package io.sansam.wapper.impl;

import io.sansam.anno.MapPattern;
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
public class MapPatternWapperRandom implements RandomDataWapper {


    public static void main(String[] args) throws Exception {
        Class clz = ArrayList.class;
        List list = (List) clz.newInstance();
        list.add("1");
        System.out.println(list.size());
    }

    @Override
    public <T> void wapper(Field field, Method method, T t, Generate generate) throws Exception {
        MapPattern mapPattern = field.getAnnotation(MapPattern.class);
        String length = mapPattern.length();
        Class clz = mapPattern.clz();
        int size;
        if (CommonUtils.isBlank(length)) {
            size = RgoConstant.DEFAULT_LIST_MAP_SIZE;
        } else {
            size = Integer.valueOf(length);
        }
        if (!Map.class.isAssignableFrom(clz)) {
            throw new RgoInitFailedException("MapPattern Type is not a map!");
        }
        Map map = (Map) clz.newInstance();
        // 获取泛型
        Type fx = field.getGenericType();
        if (!Objects.isNull(fx) && fx instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) fx;
            Class<?> genericClazz = (Class) parameterizedType.getActualTypeArguments()[0];
            fillMapWithGenericType(map, size, generate, genericClazz);
        } else {
            fillMapWithString(map, size);
        }
        method.invoke(t, map);

    }

    @Override
    public <T> void wapperWithSourceObejct(Field field, Method method, T t, Generate generate, Object target, Map<String, String> fieldMappingMap) throws Exception {

    }

    private void fillMapWithGenericType(Map map, int size, Generate generate, Class<?> genericClazz) throws Exception {
        for (int i = 0; i < size; i++) {
            map.put(i, generate.product(genericClazz));
        }
    }

    private void fillMapWithString(Map map, int size) {
        for (int i = 0; i < size; i++) {
            map.put(i, i + "");
        }
    }
}
