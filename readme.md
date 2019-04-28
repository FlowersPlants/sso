# 简介
* 项目说明：
* 项目前端[sso-front](https://github.com/FlowersPlants/sso-front)

# 技术栈
0 Kotlin语言
1 SpringBoot v2.0
2 SpringSecurity
3 Redis缓存
4 Vue element-ui

# 规划功能
* 基于Spring Security的权限控制
* 前后端分离，前端基于Vue
...

# 模块
* 用户管理
* 权限管理
* 角色管理
...

# 截图


# 注意事项


# 开发中问题集锦
* 前后端分离跨域问题：
* 登录相关问题：

# 参考
* 强烈建议参考[种子项目](https://github.com/Zoctan/spring-boot-api-seedling)
* [boot security session并发管理](http://www.cnblogs.com/sweetchildomine/p/6932488.html)
* [前后端分离 SpringBoot整合SpringSecurity权限控制（动态拦截url）](https://blog.csdn.net/weixin_39792935/article/details/84541194)
* 项目开发时可以参考[framework](https://gitee.com/sunhan521/framework/tree/master)项目，[git地址](https://gitee.com/sunhan521/framework.git)
* 可以浏览[MP文档](https://blog.csdn.net/helloPurple/article/details/78715508)的优秀案例
* 也可以浏览[优秀案例](http://mp.baomidou.com/guide/#优秀案例)

# 更新日志

### 2019-04-29
* 优化 查询时返回结果为null时忽略
* spring-boot 升级到`2.1.3.RELEASE`, mybatis-plus升级到`3.1.1`
* 删除`ResponseDto`类的使用，不确定该类的优势
* 完善认证中心，尝试添加session共享、实现单点登录功能
* 完成swagger2 接口文档功能，[本地访问地址](http://localhost:8899/swagger-ui.html)

### 2019-01-12
* 字典功能完成

### 2019-01-04
* 角色授权成功，缓存问题解决
* 修复菜单树构建bug，一些其他的bug
* 角色管理完成，菜单管理功能部分完成
* 分配权限待完成，构建菜单树问题（树结构不完整）待解决

### 2018-12-07
* 修复已知bug
* 用户信息管理功能完成
* 角色管理接口完成、菜单管理接口完成
* 菜单树和角色赋权功能待实现

### 2018-12-03
* 修复redis缓存问题
* 修复菜单相关问题

### 2018-12-02
* redis缓存功能完成，把用户等不常修改信息放在缓存中

### 2018-12-01
* 修复登录成功获取不到用户信息问题
* 修改token保存数据为用户账号

### 2018-11-30
* 修改登录登出bug，自定义登出处理器
* 剔除redis缓存AOP配置，保留一个配置文件

### 2018-11-22
* redis缓存功能待实现，获取权限正在实现中

### 2018-11-13
* jwt token登录和鉴权完成

### 2018-11-07 
* 添加mybatis plus插件，修改代码实现方式
* 目前该插件有如下问题未解决：字段自动填充失败；逻辑删除无效（junit测试）
