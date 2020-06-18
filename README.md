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

[postman 网页开发测试](https://www.postman.com/)

[Markdown 插件](http://editor.md.ipandao.com/)
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
comment on column comment.parent_id is '父类id';

comment on column comment.type is '父类类型';

comment on column comment.commentator is '评论人id';

comment on column comment.gmt_create is '创建时间';

comment on column comment.like_count is '点赞数';
```
邮箱：605616892@qq.com
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```