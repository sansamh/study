* java.net.MalformedURLException 使用URL类时 URL url = new URL(String urlText); 通常是urlText有问题抛出的异常
* UnsupportedOperationException Java中List.remove(removeRange,clear类似) 报出 UnsupportedOperationException 的错误。原来该List是一个AbstractList，不支持增删改操作。
  一般情况下我们会使用 LinkedList 和 ArrayList ，什么情况下出现 AbstractList 呢？
  
  通过 Arrays.asList() 函数得到的 List 就是 AbstractList。该AbstractList只是简单地在已有的元素数组上套了一层List 的接口，所以不支持增删改操作。
  
  如果希望能增删改，必须 new 一个LinkedList 或ArrayList。
  ```
  int [] arr = {8,7,6,9,4,5,3,1,2};
  
  List<int[]> list = Arrays.asList(arr);
  System.out.println(list instanceof AbstractList); //true
  System.out.println(list instanceof ArrayList);    //false
  System.out.println(list instanceof List); //true
  ```