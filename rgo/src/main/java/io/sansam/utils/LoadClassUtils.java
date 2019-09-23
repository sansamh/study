package io.sansam.utils;

import io.sansam.wapper.RandomDataWapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * LoadClassUtils
 * </p>
 *
 * @author houcb
 * @since 2019-09-11 15:04
 */
public class LoadClassUtils {

    public static Map<String, RandomDataWapper> loadDataWapper(String pkg, Class<? extends Annotation>... annotationFilter) throws Exception {
        Map<String, RandomDataWapper> map = new ConcurrentHashMap<>();
        List<TypeFilter> typeFilters = new LinkedList();
        if (annotationFilter != null) {
            Class[] var4 = annotationFilter;
            int var5 = annotationFilter.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Class<? extends Annotation> annotation = var4[var6];
                typeFilters.add(new AnnotationTypeFilter(annotation, false));
            }
        }

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String pattern = "classpath*:" + ClassUtils.convertClassNameToResourcePath(pkg) + "/**/*.class";
        Resource[] resources = resourcePatternResolver.getResources(pattern);
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        Resource[] var8 = resources;
        int var9 = resources.length;

        for (int var10 = 0; var10 < var9; ++var10) {
            Resource resource = var8[var10];
            if (resource.isReadable()) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                String className = reader.getClassMetadata().getClassName();
                if (matchesEntityTypeFilter(reader, readerFactory, typeFilters)) {
                    map.put(className, (RandomDataWapper) Class.forName(className).newInstance());
                }
            }
        }

        return map;
    }

    public static Map<String, Class> loadAnnotationMap(String pkg,
                                                       Class<? extends Annotation>... annotationFilter) throws Exception {
        Map<String, Class> map = new ConcurrentHashMap<>();
        List<TypeFilter> typeFilters = new LinkedList();
        if (annotationFilter != null) {
            Class[] var4 = annotationFilter;
            int var5 = annotationFilter.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                Class<? extends Annotation> annotation = var4[var6];
                typeFilters.add(new AnnotationTypeFilter(annotation, false));
            }
        }

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        String pattern = "classpath*:" + ClassUtils.convertClassNameToResourcePath(pkg) + "/**/*.class";
        Resource[] resources = resourcePatternResolver.getResources(pattern);
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        Resource[] var8 = resources;
        int var9 = resources.length;

        for (int var10 = 0; var10 < var9; ++var10) {
            Resource resource = var8[var10];
            if (resource.isReadable()) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                String className = reader.getClassMetadata().getClassName();
                if (matchesEntityTypeFilter(reader, readerFactory, typeFilters)) {
                    map.put(className, Class.forName(className));
                }
            }
        }

        return map;
    }

    private static boolean matchesEntityTypeFilter(MetadataReader reader, MetadataReaderFactory readerFactory, List<TypeFilter> typeFilters) throws IOException {
        if (!typeFilters.isEmpty()) {
            Iterator var3 = typeFilters.iterator();

            while (var3.hasNext()) {
                TypeFilter filter = (TypeFilter) var3.next();
                if (filter.match(reader, readerFactory)) {
                    return true;
                }
            }
        } else {
            return true;
        }

        return false;
    }
}
