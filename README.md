# RadishMVC
这是一款类似于struts的`轻量级`框架，使用简单效率高，非常适合`小型网站`使用。

### 下载

当前最新版本`V0.0.1`，下载地址：

https://github.com/wuguohu/radishmvc-demo/raw/master/src/main/webapp/WEB-INF/lib/radish-mvc-0.0.1-SNAPSHOT.jar

### 使用说明

1，web.xml添加filter

```xml
  <filter>
    <filter-name>radish-mvc</filter-name>
    <filter-class>win.dord.open.radishmvc.RadishFilter</filter-class>
    <init-param>
      <param-name>actionParam</param-name>
      <param-value>method</param-value>
    </init-param>
    <init-param>
      <param-name>view</param-name>
      <param-value>/</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>radish-mvc</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

2, 业务类(Service)实现ActionService

```java
package com.radishmvc.demo.service;

import win.dord.open.radishmvc.service.ActionService;

public abstract class BaseService extends ActionService{

}
```

```java
package com.radishmvc.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import win.dord.open.radishmvc.annotation.Action;
import win.dord.open.radishmvc.annotation.Param;
import win.dord.open.radishmvc.annotation.Service;

@Service("/user")
public class UserService extends BaseService {
	Logger logger = LogManager.getLogger(UserService.class);
	@Param("username")
	private String user;
	@Param
	private String password;

	@Override
	public String execute() {
		return null;
	}
	
	@Action
	public String login() {
		logger.info("login");
		//do something
		getRequest().setAttribute("user", user);
		getRequest().setAttribute("password", password);
		return "result.jsp";
	}
	

}
```

3, 注解说明

@Service 注解标识业务类的访问路径，如@Service("/home")。此注解必须传入访问路径，否则不做解析

@Action 注解标识业务类的实际操作方法，可以传参标识别名。支持@Action或@Action("newname")

@Param 注解标识自动绑定入参。支持@Param或@Param("newname")

### 依赖

项目依赖于`apache log4j` 2.0及以上版本





