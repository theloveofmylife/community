## 资环社区

## 资料
仅为训练Spring Boot使用

[bootstrap](https://v3.bootcss.com/components/)

[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

[Github 源代码](https://github.com/theloveofmylife/community)

[OKHttp](https://square.github.io/okhttp/)

[flyway](https://flywaydb.org/getstarted/firststeps/maven)

[lombok](https://www.projectlombok.org)

[Spring MVC 文档](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#spring-web)
## 脚本
```sql
create table USER
  (
      ID int auto_increment,
      ACCOUNT_ID VARCHAR(100),
      NAME VARCHAR(50),
      TOKEN CHAR(36),
      GMT_CREATE BIGINT,
      GMT_MODIFIED BIGINT,
      constraint USER_pk
          primary key (ID)
  );
```
邮箱：605616892@qq.com
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```