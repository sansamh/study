package io.sansam.utils;

import io.sansam.common.exception.RgoInitFailedException;
import io.sansam.wapper.RandomDataWapper;
import io.sansam.wapper.impl.BasicWapperRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * WapperContext
 * </p>
 *
 * @author houcb
 * @since 2019-09-11 15:00
 */
public class WapperContext {

    private static Logger logger = LoggerFactory.getLogger(WapperContext.class);

    private static String DEFAULT_WAPPER_LOCATION = "io.sansam.wapper.impl";

    private static String BASIC_WAPPER_NAME = BasicWapperRandom.class.getName();

    private static String WAPPER_SUFFIX = "Wapper";

    private static String DEFAULT_ANNOTATION_LOCATION = "io.sansam.anno";


    private static Map<String, RandomDataWapper> wapperMap = new ConcurrentHashMap<>(16);

    private static Map<String, Class> patternMap = new ConcurrentHashMap<>(16);

    private static void initWapperMap() throws Exception {

        try {
            wapperMap = LoadClassUtils.loadDataWapper(DEFAULT_WAPPER_LOCATION);
        } catch (Exception e) {
            logger.error("WapperContext - initWapperMap failed!", e);
            throw new RgoInitFailedException("WapperContext - initWapperMap failed!", e);
        }
    }

    public static RandomDataWapper getBasicDataWapper() throws Exception {
        if (CollectionUtils.isEmpty(wapperMap)) {
            synchronized (wapperMap) {
                if (CollectionUtils.isEmpty(wapperMap)) {
                    initWapperMap();
                }
            }
        }
        RandomDataWapper dataWapper = wapperMap.get(BASIC_WAPPER_NAME);
        if (Objects.isNull(dataWapper)) {
            throw new RgoInitFailedException("get BasicDataWapper failed!");
        }
        return dataWapper;
    }

    public static RandomDataWapper getSpecialWapper(String patternName) throws Exception {
        if (CollectionUtils.isEmpty(wapperMap)) {
            synchronized (wapperMap) {
                if (CollectionUtils.isEmpty(wapperMap)) {
                    initWapperMap();
                }
            }
        }
        // patternName contains package path, should replace (0, lastIndexOf('.')) with DEFAULT_WAPPER_LOCATION
        // eg. io.sansam.anno.ListPattern -> io.sansam.wapper.impl.ListPatternWapper
        patternName = patternName.substring(patternName.lastIndexOf('.'));
        RandomDataWapper dataWapper = wapperMap.get(DEFAULT_WAPPER_LOCATION + patternName + WAPPER_SUFFIX);
        if (Objects.isNull(dataWapper)) {
            throw new RgoInitFailedException(String.format("get SpecialWapper [%s] failed!", patternName));
        }
        return dataWapper;
    }

    private static void initPatternMap() throws Exception {
        try {
            patternMap = LoadClassUtils.loadAnnotationMap(DEFAULT_ANNOTATION_LOCATION);
        } catch (Exception e) {
            logger.error("WapperContext - initPatternMap failed!", e);
            throw new RgoInitFailedException("WapperContext - initPatternMap failed!", e);
        }
    }

    public static boolean containsPattern(String className) throws Exception {
        if (CollectionUtils.isEmpty(patternMap)) {
            synchronized (patternMap) {
                if (CollectionUtils.isEmpty(patternMap)) {
                    initPatternMap();
                }
            }
        }
        return patternMap.containsKey(className);
    }

    public static void clearWapperMap() {
        if (CollectionUtils.isEmpty(wapperMap)) {
            return;
        }
        synchronized (wapperMap) {
            wapperMap.clear();
        }
    }

    public static void clearPatternMap() {
        if (CollectionUtils.isEmpty(patternMap)) {
            return;
        }
        synchronized (patternMap) {
            patternMap.clear();
        }
    }

    public static void loadCustomizeWapper(String pkg) throws Exception {
        if (CollectionUtils.isEmpty(wapperMap)) {
            synchronized (wapperMap) {
                if (CollectionUtils.isEmpty(wapperMap)) {
                    initWapperMap();
                }
            }
        }
        Map<String, RandomDataWapper> custMap = LoadClassUtils.loadDataWapper(pkg);
        Map<String, RandomDataWapper> transMap = new HashMap<>(custMap.size());
        custMap.entrySet().forEach(obj -> {
            transMap.put(DEFAULT_WAPPER_LOCATION + obj.getKey().substring(obj.getKey().lastIndexOf('.')) + WAPPER_SUFFIX,
                    obj.getValue());
        });

        wapperMap.putAll(transMap);
    }

    public static void loadCustomizePattern(String pkg) throws Exception {
        if (CollectionUtils.isEmpty(patternMap)) {
            synchronized (patternMap) {
                if (CollectionUtils.isEmpty(patternMap)) {
                    initPatternMap();
                }
            }
        }
        Map<String, Class> custMap = LoadClassUtils.loadAnnotationMap(pkg);
        Map<String, Class> transMap = new HashMap<>(custMap.size());
        custMap.entrySet().forEach(obj -> {
            transMap.put(DEFAULT_WAPPER_LOCATION + obj.getKey().substring(obj.getKey().lastIndexOf('.')) + WAPPER_SUFFIX,
                    obj.getValue());
        });

        patternMap.putAll(transMap);
    }
}
