package v3.context.scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import v3.beans.BeanDefinitionRegistry;
import v3.context.reader.AnnotationBeanDefinitonReader;
import v3.context.reader.BeanDefinitonReader;
import v3.context.resource.Resource;

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
		this.reader = new AnnotationBeanDefinitonReader(this.registry);
	}

	public void scan(String basePackage) {
		if (StringUtils.isNotBlank(basePackage)) {
			this.scan(new String[]{basePackage});
		}
	}

	public void scan(String... basePackages) {
		if (basePackages != null && basePackages.length > 0) {
			for (String s : basePackages) {
				this.reader.loadBeanDefinition(this.doScan(s));
			}
		}
	}

	private Resource[] doScan(String basePackage) {
		// 扫描包下的类
		// 构造初步匹配模式串，= 给入的包串 + / + **/*.class，替换里面的.为/
		String path = StringUtils.replace(basePackage, ".", "/") + "/" + this.resourcePatter;


		return null;
	}
}
