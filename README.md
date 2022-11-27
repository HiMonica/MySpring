# MySpring
## 1、源码体会

1、2022-09-25

目前完成了大致完成了固定的几个beanName对应的实例放入beanFactory中，通过看springBoot的源码发现比spring到prepareBeanFactory(beanFactory)这一步多出了几个固定注入的bean实例。
发现springBoot在run()方法中，在调用refresh()之前就将beanFactory初始化了，所以这里也体现了spring的扩展性。提前初始化beanFactory并不影响后续的操作。

2、2022-11-24

一步一步的完善利用XML文件的形成加载Bean的过程，发现其实原理就是利用一个资源加载器对传入的路径下的xml文件进行解析
