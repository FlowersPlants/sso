# 简介
* 项目说明：
* 项目前端[sso-front](https://github.com/FlowersPlants/sso-front)
* 目前待解决问题：
>统一异常处理，因为登录失败时前端没有正确的提示，已解决；

>token部分的功能可能需要优化，生成token的几种方式目前无效，修改token相关配置在yml文件中;

>获取用户信息失败，待修改

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
* 项目开发时可以参考[framework](https://gitee.com/sunhan521/framework/tree/master)项目，[git地址](https://gitee.com/sunhan521/framework.git)
* 可以浏览[MP文档](https://blog.csdn.net/helloPurple/article/details/78715508)的优秀案例
* 也可以浏览[优秀案例](http://mp.baomidou.com/guide/#优秀案例)

# 更新日志
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