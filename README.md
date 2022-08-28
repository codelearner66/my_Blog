# my_Blog
个人博客基础框架
后端主要使用到的技术有**SpringBoot**,  **redis**, **Mybatis-plus**,  **Spring Security****,**Thymeleaf**, **lombok** ， jwt，fastjson,Druid连接池，MySQL等...

前端主要使用  JQuery,  [ZUI](https://www.openzui.com/#/), [WangEditor](https://www.wangeditor.com/)等...
前端模块： 博客浏览，发布，修改，评论，用户信息修改头像上传 博客缩略图上传等...
后端模块：文章发布趋势折线图，用户增长直方图， 文章审核，用户管理（封禁，踢下线），管理员管理（添加，删除，封禁，临时授权），新建角色/权限,授权中心，管理员信息修改等...

技术要点：本博客集成security安全框架使用基于角色的访问控制（ RBAC ）模型能够做到按钮级别的权限管理；使用redis + jwt实现单点登录,使用redi缓存热点数据提高相应速度。使用Mybatis-plus简化数据库操作提高效率。


### 1. 项目介绍背景

博客结合了文字，图像，其他博客或者网站的链接及其他与主题相关的媒体，能够让读者以互动的方式留下意见，是许多博客的重要要素。

在博客中可以使用搜索的方式找到自己想要文章，也可以咨询大家的看法，然后自己去实践是否有效。

按照博客文章的分类进行展示文章，根据技术分类，让人们可以更方便的阅读自己喜好的文章。

微型博客，目前最受欢迎的博客类型，作者不需要撰写特别复杂的文章， 只需要书写少量文件即可发表文章（没有字数限制）。

#### 1.1 目的和范围

1．该项目的名称：个人博客系统。

2．该系统主要是为了实现文章发布与搜索，读者之间的互动等。

3．该系统的目的明确是为了帮助博主和读者之间的互动交流提出自己的问题相互交流、发表文章、搜索资源、技术交流等。

#### 1.2 关键词

定义关键词如下：**个人博客**，**redis**；**SpringBoot**；**MySQL**；**Spring Security******Thymeleaf****; *wangEditor**。

### 2.项目模块描述

个人博客系统用户系统有首页展示、博客分类、信息查询与订阅、打赏功能、发博文章、用户交流等六大模块,实现个人博客系统这一核心目标。

个人博客系统成如图所示：

![83BF386BD6AB1F56A8DD5ED88608CFFC](.\image\83BF386BD6AB1F56A8DD5ED88608CFFC.jpg)

​          ![78CF16B674E94D08D5C0C1A87E05684A](.\image\78CF16B674E94D08D5C0C1A87E05684A.jpg)                                                                                        

​                                                                                                      图1 个人博客系统功能框架·

#### 2.1后台系统管理描述

个人博客后台管理系统有对文章审核功能，权限管理功能，管理员crud功能，用户管理功能、数据管理功能五大模块。

 ![Cache_-c03ba275816d86e](.\image\Cache_-c03ba275816d86e.jpg)

​                                                                                              图2 个人博客后台管理系统功能框架

### 需求分析

####  **3.1.1  博客页面展示需求**

需要查询浏览量最高的前10篇文章的信息。要求展示文章标题和浏览量。把能让用户自己点击跳转到具体的文章详情进行浏览。

注意：不能把草稿展示出来，不能把删除了的文章查询出来。要按照浏览量进行降序排序。

#### **3.1.2** 字面值处理需求

实际项目中都不允许直接在代码中使用字面值。都需要定义成常量来使用。这种方式有利于提高代码的可维护性。 

### **3.1.3 分类表需求**

在首页和分类页面都需要查询文章列表。

首页：查询所有的文章

分类页面：查询对应分类下的文章

要求：①只能查询正式发布的文章 ②置顶的文章要显示在最前面 

### **3.1.4 文章详情需求**

要求在文章列表点击阅读全文时能够跳转到文章详情页面，可以让用户阅读文章正文。 

要求：①要在文章详情中展示其分类名。

### **3.1.5 友联查询需求**

在友链页面要查询出所有的审核通过的友链。

### **3.1.6 需要实现登录和注册功能**

有些功能必须登录后才能使用，未登录状态是不能使用的。

要求用户能够在注册界面完成用户的注册。要求用户名，昵称，邮箱不能和数据库中原有的数据重复。如果某项重复了注册失败并且要有对应的提示。并且要求用户名，密码，昵称，邮箱都不能为空。

注意:密码必须密文存储到数据库中。 

### **3.1.7评论列表需求**

（1）展示评论

文章详情页面要展示这篇文章下的评论列表。

（2）发表评论

用户登录后可以对文章发表评论，也可以对评论进行回复。

用户登录后也可以在友链页面进行评论。

### **3.1.8  个人信息查询需求**

进入个人中心的时候需要能够查看当前用户信息。

用户能够修改当前个人信息。

在个人中心点击编辑的时候可以上传头像图片。上传完头像后，可以用于更新个人信息接口。

### 4.详细页面展示

#### ***\*注册登录\****

用户输入账户密码和验证码，即可登录如果忘记密码可以找回密码。如果是未注册的用户，可先进行注册再进行登入。如图3,4所示：

![Cache_-2d49b88fe45b30fa](.\image\Cache_-2d49b88fe45b30fa.jpg)

​                                                                      图3 个人博客登录界面

![Cache_-6bcbc10d8dada16c](.\image\Cache_-6bcbc10d8dada16c.jpg)

​                                                                                                    图4 个人博客注册界面

![Cache_-5617afcb6559563d](.\image\Cache_-5617afcb6559563d.jpg)

​                                                                                                         图4 个人博客忘记密码界面

#### ***\*首页\****

​               个人博客系统有首页展示、博客分类、信息查询与订阅、打赏功能、发博文章、用户交流等六大模块,如图5所示：

![](.\image\83BF386BD6AB1F56A8DD5ED88608CFFC.jpg)

​																										图5个人博客系统首页界面

#### ***\*发布文章\****

该页面可以对文章进行文章发布和查看相应文章还可以查看热门文章排行榜。如图6-1、6-2、6-3所示：

![CC8E9323032DE068D2F1FF7347CD0D43](.\image\73239E49E8DEF8E1839413B42B468A5A.jpg)

​                                图6-1文章浏览页面

![2AF60721E2C22ABEA16E24A73833C3F7](.\image\2AF60721E2C22ABEA16E24A73833C3F7.jpg)

​                                                                 图6-2文章详情查阅页面

![CC8E9323032DE068D2F1FF7347CD0D43](.\image\CC8E9323032DE068D2F1FF7347CD0D43.jpg)

​                                                   图6-3文章新建和修改页面

#### 信息回复

对博主发表的信息进行回复和评论。如图7所示：

![881FA201FA5B871F5BDF5F1D46934CC0](.\image\881FA201FA5B871F5BDF5F1D46934CC0.jpg)

***\*个人信息的查阅和修改\****

用户可以对自已的信息进行修改和查询。如图8所示：

![Cache_51dd9c3cdd7ef8b8](.\image\Cache_51dd9c3cdd7ef8b8.jpg)

### 5. 后台管理说明

####     ***后台登陆成功界面***

​        管理员登陆成功后展示的界面，如图9所示：

![510CE77283D62A2E0D8F5A0834C9C7ED](.\image\510CE77283D62A2E0D8F5A0834C9C7ED.jpg)

​                       											   图9 后台登陆成功页面

#### 管理员管理用户界面

超级管理员对管理员及用户进行权限分配，如图10-1、10-2、10-3、10-4所示：![751F6538CE8BD3E7B27CFA6C50277CF6](C:.\image\751F6538CE8BD3E7B27CFA6C50277CF6.jpg)

![4BFF392AF63D41D000B70F469A6F8DB0](.\image\4BFF392AF63D41D000B70F469A6F8DB0.jpg)

![F89BB8100F8C2C1850063C4DD60CF075](.\image\F89BB8100F8C2C1850063C4DD60CF075.jpg)

![82E0DB204FBF63A47EE8E4ED051B2308](.\image\82E0DB204FBF63A47EE8E4ED051B2308.jpg)

![C41F112C101BBE0A7A661B50A6A94C99](.\image\C41F112C101BBE0A7A661B50A6A94C99.jpg)

![9F0718C354729685B83FE04E6CFFBF49](.\image\9F0718C354729685B83FE04E6CFFBF49.jpg)

![3EF9891B5A8B6305F40144DDF7CD3F7A](.\image\3EF9891B5A8B6305F40144DDF7CD3F7A.jpg)

![3D6568DF68584679FBE99F7178AAB0EE](.\image\3D6568DF68584679FBE99F7178AAB0EE.jpg)

![E7A4761350549C449A523EF58F1AE404](.\image\E7A4761350549C449A523EF58F1AE404.jpg)