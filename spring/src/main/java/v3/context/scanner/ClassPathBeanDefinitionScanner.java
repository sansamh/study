package v3.context.scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import v3.beans.BeanDefinitionRegistry;
import v3.context.reader.BeanDefinitonReader;

/**
 * 扫描class文件
 */
public class ClassPathBeanDefinitionScanner {

    private static Log logger = LogFactory.getLog(ClassPathBeanDefinitionScanner.class);

    private BeanDefinitionRegistry registry;

    private BeanDefinitonReader reader;

    private PathMatcher pathMatcher = new AntPathMatcher();

    private String resourcePatter = "**/*.class";

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, BeanDefinitonReader reader) {
        this.registry = registry;
        this.reader = reader;
    }
}
