package io.sansam.wapper;

import io.sansam.generate.Generate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>
 * DataWapper
 * </p>
 *
 * @author houcb
 * @since 2019-09-11 11:54
 */
public interface RandomDataWapper {

    /**
     * 组装数据
     *
     * @param field
     * @param method
     * @param t
     * @param generate
     * @param <T>
     * @throws Exception
     */
    <T> void wapper(Field field, Method method, T t, Generate generate) throws Exception;

    /**
     * 组装数据
     *
     * @param field
     * @param method
     * @param t
     * @param generate
     * @param <T>
     * @throws Exception
     */
    <T> void wapperWithSourceObejct(Field field, Method method, T t, Generate generate, Object target,
                    Map<String, String> fieldMappingMap) throws Exception;

}
