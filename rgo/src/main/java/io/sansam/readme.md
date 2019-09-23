1. 自定义注解 xxx
2. 创建xxxWapper类，实现DataWapper接口，重写wapper方法
3. 调用WapperContext.loadCustomizeWapper(xxxWapper的路径)加载wapper
4. 调用WapperContext.loadCustomizePattern(xxx的路径)加载注解