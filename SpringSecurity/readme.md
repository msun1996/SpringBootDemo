## Security基础实现
功能：   
配置实现用户及角色权限信息。
（学习文档：https://blog.csdn.net/u012373815/article/details/54632176）

Security配置角色权限
```
在WebSecurityConfig 中使用 .antMatchers("/admin/**").hasRole("ROLE_ADMIN")
```
前端角色权限判断
```
可使用html的sec:authorize="hasRole('ROLE_ADMIN') 实现角色权限管理
```