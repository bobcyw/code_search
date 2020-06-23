env里有一套dock-compose，可以拉起整套环境在本地进行开发

com.caoyawen.code_search.domain.job.GrepJinshanTest.testAllInOne
全套扫描更新金山云相关项目的结果，输出在
/Users/caoyawen/data/jinshan/ret.txt
/Users/caoyawen/data/jinshan/exist.txt

* 一次常规时间分析(总耗时4m12s850ms)
* 2020-06-04 11:29:30.330  -  2020-06-04 11:29:41.476   11s  初始化项目
* 2020-06-04 11:29:41.476  -  2020-06-04 11:30:12.778   31s  拉取gitlab元信息
* 2020-06-04 11:30:12.778  -  2020-06-04 11:32:25.979  133s  clone项目
* 2020-06-04 11:32:25.979  -  2020-06-04 11:33:50.623   85s  扫描项目
* 2020-06-04 11:33:50.623  -  2020-06-04 11:33:54.458    4s  合并报表（时间不一定是这么长）

## 使用时需要修改的参数
### application.properties
```$config
#数据库
spring.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/code_search?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=${MYSQL_USER}
spring.datasource.password=${MYSQL_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#Redis 相关，用于检索提示
spring.redis.host=${REDIS_HOST}
spring.redis.port=${REDIS_PORT}
#Gitlab专用字段
#Gitlab的api
gitlab.api-url=http://somegithub.com
#gitlab的access_token
gitlab.token=${GITLAB_ACCESS_TOKEN}
#项目名称
gitlab.groups[0]=someproject
#扫描项目所存储的目录
gitlab.sink-path=/Users/caoyawen/java/codescan
#默认的分支
gitlab.default-branch=master
#克隆项目时，同时开几个线程
code-search-job.clone-project-thread-pool=4
```
