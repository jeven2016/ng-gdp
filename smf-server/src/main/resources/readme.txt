1. 对于同时批量删除的处理：
不删除而是标记为删除，这样删除就变成了更新。某步出错了，就把之前的删除标记改掉。
标记为删除的可以定期物理删除。
或者如果场景允许，就只删一条记录，其他记录定期跑脚本批量删。这个适合不删其他记录也不影响逻辑的情景。

2. 如果使用AOP进行拦截时，出现无法代理某些类的对应方法时，需要使用@AllOpen注解，这样其子类的方法也不会是final

2. 对mongoTemplate的查询条件可以为以下几种
As you can see most methods return the Criteria object to provide a fluent style for the API.
Methods for the Criteria class

Criteria all (Object o) Creates a criterion using the $all operator

Criteria and (String key) Adds a chained Criteria with the specified key to the current Criteria and returns the newly created one

Criteria andOperator (Criteria…​ criteria) Creates an and query using the $and operator for all of the provided criteria (requires MongoDB 2.0 or later)

Criteria elemMatch (Criteria c) Creates a criterion using the $elemMatch operator

Criteria exists (boolean b) Creates a criterion using the $exists operator

Criteria gt (Object o) Creates a criterion using the $gt operator

Criteria gte (Object o) Creates a criterion using the $gte operator

Criteria in (Object…​ o) Creates a criterion using the $in operator for a varargs argument.

Criteria in (Collection<?> collection) Creates a criterion using the $in operator using a collection

Criteria is (Object o) Creates a criterion using field matching ({ key:value }). If the specified value is a document, the order of the fields and exact equality in the document matters.

Criteria lt (Object o) Creates a criterion using the $lt operator

Criteria lte (Object o) Creates a criterion using the $lte operator

Criteria mod (Number value, Number remainder) Creates a criterion using the $mod operator

Criteria ne (Object o) Creates a criterion using the $ne operator

Criteria nin (Object…​ o) Creates a criterion using the $nin operator

Criteria norOperator (Criteria…​ criteria) Creates an nor query using the $nor operator for all of the provided criteria

Criteria not () Creates a criterion using the $not meta operator which affects the clause directly following

Criteria orOperator (Criteria…​ criteria) Creates an or query using the $or operator for all of the provided criteria

Criteria regex (String re) Creates a criterion using a $regex

Criteria size (int s) Creates a criterion using the $size operator

Criteria type (int t) Creates a criterion using the $type operator

There are also methods on the Criteria class for geospatial queries. Here is a listing but look at the section on GeoSpatial Queries to see them in action.

Criteria within (Circle circle) Creates a geospatial criterion using $geoWithin $center operators.

Criteria within (Box box) Creates a geospatial criterion using a $geoWithin $box operation.

Criteria withinSphere (Circle circle) Creates a geospatial criterion using $geoWithin $center operators.

Criteria near (Point point) Creates a geospatial criterion using a $near operation

Criteria nearSphere (Point point) Creates a geospatial criterion using $nearSphere$center operations. This is only available for MongoDB 1.7 and higher.

Criteria minDistance (double minDistance) Creates a geospatial criterion using the $minDistance operation, for use with $near.

Criteria maxDistance (double maxDistance) Creates a geospatial criterion using the $maxDistance operation, for use with $near.

The Query class has some additional methods used to provide options for the query.

Methods for the Query class

Query addCriteria (Criteria criteria) used to add additional criteria to the query

Field fields () used to define fields to be included in the query results

Query limit (int limit) used to limit the size of the returned results to the provided limit (used for paging)

Query skip (int skip) used to skip the provided number of documents in the results (used for paging)

Query with (Sort sort) used to provide sort definition for the results


3. 启动
   debug启动:
   export JAVA_OPTS=-Xmx1024m -XX:MaxPermSize=128M
   java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar target/myproject-0.0.1-SNAPSHOT.jar

   可以激活不同的配置文件
   java -jar xxx.jar --spring.profiles.active=prod


4. 关于httpsession
    使用了spring session redis 方案存储http session, 同时关闭了shiro的session manager.
    需要session时，直接通过SmfWebUtils类获取


5. kotlin
   1) 当我们调用vararg-函数时，我们可以一个接一个地传参，例如asList(1,	2,	3)	，或者，
   如果我们已经有一个数组	并希望将其内容传给该函数，我们使用伸展（spread）操作符（在
   数组前面加	 *）

6. spring
   spring加载配置文件的优先顺序
   pring Boot uses a very particular PropertySource order that is designed to allow sensible overriding of values. Properties are considered in the following order:

   Devtools global settings properties on your home directory (~/.spring-boot-devtools.properties when devtools is active).
   @TestPropertySource annotations on your tests.
   @SpringBootTest#properties annotation attribute on your tests.
   Command line arguments.
   Properties from SPRING_APPLICATION_JSON (inline JSON embedded in an environment variable or system property)
   ServletConfig init parameters.
   ServletContext init parameters.
   JNDI attributes from java:comp/env.
   Java System properties (System.getProperties()).
   OS environment variables.
   A RandomValuePropertySource that only has properties in random.*.
   Profile-specific application properties outside of your packaged jar (application-{profile}.properties and YAML variants)
   Profile-specific application properties packaged inside your jar (application-{profile}.properties and YAML variants)
   Application properties outside of your packaged jar (application.properties and YAML variants).
   Application properties packaged inside your jar (application.properties and YAML variants).
   @PropertySource annotations on your @Configuration classes.
   Default properties (specified using SpringApplication.setDefaultProperties).