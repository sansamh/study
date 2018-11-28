### v1说明
1. 自定义beanDefinition beanRegistry beanFactory 实现自定义IOC容器
2. beanFactory获取beanDefinition 通过无参构造函数或者工厂方法或者静态工厂方法 反射实例bean
3. beanDefinition可以设置单例/原型模式 可以自定义初始化/销毁方法（容器销毁的时候执行存放在容器中的单例模式的bean的销毁方法）