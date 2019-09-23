package io.sansam.wapper.impl;

import io.sansam.anno.DateStringPattern;
import io.sansam.common.exception.RgoInitFailedException;
import io.sansam.generate.Generate;
import io.sansam.wapper.RandomDataWapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * DateStringPatternWapper
 * </p>
 *
 * @author houcb
 * @since 2019-09-11 19:38
 */
public class DateStringPatternWapperRandom implements RandomDataWapper {

    @Override
    public <T> void wapper(Field field, Method method, T t, Generate generate, Object target,
                           Map<String, String> fieldMappingMap) throws Exception {
        Class<?> type = field.getType();
        if (type != String.class) {
            throw new RgoInitFailedException(String.format("DateStringPattern must used in String filed, truth is " +
                    "[%s]", type.getName()));
        }
        DateStringPattern annotation = field.getAnnotation(DateStringPattern.class);
        String pattern = annotation.pattern();
        method.invoke(t, new SimpleDateFormat(pattern).format(new Date()));
    }
}
