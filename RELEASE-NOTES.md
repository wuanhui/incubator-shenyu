## 2.6.0

### New Features

1. Support shenyu-admin expose prometheus metrics
2. Add shenyu Level-1 cache and Level-2 cache
3. Save extend plugin jar to shenyu admin
4. Support shenyu upload plugin hot load in gateway
5. Support apollo sync data and register data
6. Initializes client information collection
7. Support spring-boot-client auto config in shenyu client
8. Add TCP plugin
9. Super admin forces password change
10. Spring-mvc(boot) client support collect api-meta
11. Add zookeeper discovery sync
12. Initializes Shenyu ingress controller
13. Add discovery upstream and proxy selector proxy
14. Expose shenyu actuator endpoint
15. Add naocs discovery sync
16. Add apollo discovery sync
17. Add HttpLongPolling discovery sync
18. Add consul discovery sync
19. Add huawei cloud lts loging plugin
20. Support openGauss database for shenyu admin
21. Support polaris config sync and register center

### API Changes

### Enhancement

1. Add tags for shenyu api doc client
2. Add brpc integrated test
3. Brpc support shared thread pool
4. Add mapType to cryptor request and response plugin
5. Crypto plugin supports multi field names
6. Add p2c loadbalancer
7. Modify plugin jar to Base64-string in plugin data
8. Add shortest response load balancer
9. Add hash load balancer test case
10. Add DetailSerivice test case
11. Tolerant path slash for shenyu
12. Add shenyu-common enums test case
13. Add shenyu-common dto test case
14. Add shenyu-admin model test case
15. Add shenyu match cache test case
16. Support k8s probes
17. Add shenyu-admin service test case
18. Add document json data in api doc
19. The SPEL in the mock plugin is secure by default
20. Add test cases for ShenyuClientApiDocExecutorSubscriber
21. Add test case for shenyu-client-sofa module
22. Add tag relation for shenyu api doc
23. Support shenyu admin, bootstrap service stop script bat in windows
24. Add test case for ShenyuSdkClientFactory
25. Add websocket synchronization method for e2e-springcloud
26. Support divide plugin active offline
27. Add springcloud service instance cache
28. Change password support i18n
29. Add websocket sync for shenyu discovery
30. Update springboot to 2.7.13
31. Add other syn method e2e-spring-cloud
32. Add api doc client generated annotation attribute
33. Update zookeeper client register repository active offline
34. Update apollo client register repository active offline
35. Storage adjustment for swagger type API documents, change from local cache to database
36. Support nacos client offline
37. Add e2e alibaba dubbo test case
38. Add e2e apache dubbo test case
39. Add shenyu-spring-sdk test cases
40. Add e2e sofa test cases
41. Add apollo config sync test case
42. Add database connection pool
43. Add idea icon for shenyu project

### Refactor

1. Optimize shenyu-admin
2. Refactor least active balance algorithm
3. Optimized version-one for sign-plugin
4. Optimize upstream check service
5. Resolve shenyu project global version
6. Refactor ShenyuConsulConfigWatch code
7. Refactor shenyu trie codes
8. Check uri condition of rule when saving
9. Optimize shenyu-client code for shenyu-client-websocket
10. Add license for admin dependency micrometer
11. Update maven-assembly-plugin to 3.5.0
12. Optimize global plugin sorting
13. Use BearerToken replace StatelessToken in shenyu-admin
14. Refactor shenyu-logging module
15. Add validation for api doc
16. Optimize Trie code and improve wildcard * supporting
17. Refactor the custom plugin support hot load
18. Refactor ShenyuWebHandler putPlugin methods
19. Refactor Shenyu webfilter
20. Reactor oauth2 plguin
21. Refactor shenyu selector data continued field
22. Refactor shenyu selector and rule cache
23. Removed unused generics in shenyu client
24. Refactor shenyu-plugin-sentinel plugin
25. Refactor cache and add endpoint to expose cache
26. Refactor checkUserPassword, not print known error log when startup
27. Add some parameters for log
28. Refactor shenyu global exception handler
29. Add shenyu upload plugin integrated test case
30. Optimize some syntactic sugar
31. Change discovery_upstream discovery_handler_id
32. Refactor shenyu global exception handlers
33. Refactor shenyu plugin module
34. Refactor AlibabaDubboConfigCache
35. Remove hutool from dependency
36. Refactor ShenyuClientShutdownHook
37. Extractor add BaseAnnotationApiBeansExtractor
38. Support multi-client registration
39. Refactor shenyu-e2e support shenyu check style
40. Refactor shenyu client register base
41. Add domain test for shenyu divide plugin
42. Update other rpc_ext for the same service
43. Optimize consul connect operation
44. Refactor shenyu e2e springcloud yaml change
45. Add integrated test for k8s ingress controller
46. Split the document field of the apidoc detail interface,and add fields such as requestHeaders and responseParameters
47. Add swagger sample project to test the relevant functionality of the API documentation
48. Optimize the display of form fields in json format
49. Refactor shenyu log observability
50. Add bootstrap start log
51. Refactor api document for swagger
52. Upgrade grpc version to 1.53.0
53. Refactor api meta data process function
54. polish master code and pom

### Bug Fix

1. Smart h2 path
2. Fix crypto-response plugin
3. Fix jdk8 Map computeIfAbsent performance bug
4. Fix zombieRemovalTimes code
5. Fix the upgraded sql file
6. Remove detectOfflineLinks tag
7. Ignore flattened-pom
8. Fix LOG invoke method
9. Fix NPE for shenyu-example-springcloud with nacos
10. Fix shenyu-admin names for arguement of type
11. Fix loadbalance spi resource
12. Fix sql script error
13. Fix to 24-hour format and timeZone for jackson
14. Fix JwtUtils error
15. Fix dubbo invoker cache bug
16. Fix missing HOST delete operation
17. Fix SpringMvcClientEventListener test case
18. Fix pass update PENDING_SYNC for zombie
19. Fix Memory leak
20. Fix rule query failed because there are too many rules
21. Fix missing actuator dependency and port error in examples http
22. Fix UpstreamCheckUtils http and https
23. Fix FileFilter make memory leak
24. Fix zookeeper sync error
25. Fix MemorySafeWindowTinyLFUMap memory leak error
26. Fix lack separator of path of ApiDoc
27. Fix NPE for shenyu trie
28. Fix plugin skip error
29. Fix oracle sql error
30. Fix shenyu icon can't load in shenyu admin
31. Fix hystrix fallback bug
32. Fix warm up time for divide and springcloud
33. Fix springcloud service chooser
34. Fix shenyu-spring-boot-starter-plugin-mock add spring.factories
35. Fix shenyu-client-mvc and shenyu-client-springcloud lose ip
36. Fix empty rule data and selector data in cache
37. Fix api document module update api detail error
38. Fix get topic from config in KafkaLogCollectClient
39. Fix logging console thread safety
40. Fix brpc integration testing response size
41. Fix selector update gray remove cache for plugn-dubbo-common
42. Fix shenyu admin menu name bug
43. Fix shenyu admin cannot configure consul port
44. Fix shenyu client metadata and uri cannot sync to admin with apollo
45. Fix PathVariable annotation url cannot match
46. Fix could not update uri in PathPattern mode
47. Fix client shutdown method call twice
48. Fix shenyu mishandle consul configuration
49. Remove unused configuration from Request, modifyResponse plugin
50. Fix http registration metadata
51. Fix websocket lost the user-defined clost status
52. Fix consul register lose the prop of meta-path when special symbol
53. Fix etcd sync error
54. Fix admin sync error
55. Fix shenyu motan plugin execute error

## 2.5.1

### New Features
1. Add brpc example
2. Add spring boot starter plugin brpc&admin
3. Add brpc-plugin
4. Add shenyu-client-api-doc
5. Add sdk duplicate class check
6. Support diff nacos namespace
7. Add array method of expression in mock plugin
8. Support generation of mock data on request
9. Support user specify http request domain
10. Add MockRequestRecord
11. Development shenyu-register-instance-eureka
12. Support API document Api doc detail mapper
13. Add api doc ddl
14. Add TagMapper and TagRelationMapper
15. Add api and api_rule_relation mapper
16. Not config rule
17. Refactor message readers
18. Add sentinel rule handle parameter
19. Add shenyu-e2e test engine
20. Make an Apache Shenyu SSO authentication plugin based on casdoor
21. Add logging-tencent-cls plugin
22. Support clickhouse-logging-pugin
23. Add logging-pulsar plugin
24. Add new plugin: key-auth
25. Fix sign plugin DataBufferLimitException error
26. Fix context-path error

### API Changes

### Enhancement
1. Add simpler client annotations for motan
2. Add simpler client annotations for websocket
3. Add configuration in starter for motan plugin
4. Add convenience annotation for shenyu-client-springcloud and shenyu-client-springmvc

### Refactor
1. Refactor some code for mock request of api doc
2. Refactor logging-clickhouse
3. Polish maven dependencies of dubbo
4. Refactor sign plugin
5. Update ShenyuExtConfiguration
6. Remove unnecessary singleton
7. Fix generating mock data in multithreading
8. Refactor sdk test and processArgument
9. Refactor DefaultSignService
10. Fix shenyu-admin rule
11. Optimized ShaUtil
12. Fix cache too large
13. Fix ConcurrentModificationException
14. Fix sync data in etcd
15. Refactor shenyu sdk client
16. Optimize request timeout response
17. Refactor log module
18. Refactor shenyu-client-springcloud
19. Refactor MotanServiceEventListener
20. Refactor shenyu-admin sync data listener
21. Refactor shenyu-client-tars
22. Refactor client sdks alibaba dubbo
23. Refactor springmvc client
24. Refactor admin mapper config
25. Refactor shenyu-plugin-logging
26. Optimize random algorithm
27. Refactor random loadbalancer
28. Refactor logging-kafka

### Bug Fix
1. Remove redundant cookie setting
2. Fix appAuth delete
3. fix Cryptor-Request Plugin
4. To avoid load the same ext plugins repeatedly
5. Fix the TagRelationQuery
6. Fix upgrade sql
7. Fix Nacos register NPE
8. Fix sandbox json parsing
9. Prevent the first time from failing to load
10. Fix plugin update bug by modifying config field setter
11. Fix postgresql sql
12. Fix the postgresql error during ShenYu-Admin startup
13. Fix sentinel can't fuse
14. Fix TencentClsLogCollectClient
15. Fix change password error
16. Fix selector page
17. Fix request plugin can't replaceCookie
18. Fix RateLimiterPlugin concurrent handler error

## 2.5.0

### New Features
1. Add logging-aliyun-sls plugin 
2. Add mock plugin 
3. Add logging-es plugin 
4. Add logging-rocketmq plugin 
5. Add logging-kafka plugin 
6. Add custom message writer in response plugin 
7. Add record log in admin 
8. Add apache dubbo http 
9. Add nacos register 
10. add the logic of annotation on the splicing class for sofa client 
11. Add the logic of annotation on the splicing class for motan client 
12. Add netty http server parameters 
13. Add the logic of annotation on the splicing class for apache dubbo client 
14. Add alert module 
15. Add support configurable timeout for MotanPlugin 
16. Add api document
17. Add user permissions Exclude admin
18. Add springBoot upgrade to 2.6.8
19. Add support regsiter instance to consul
20. Add admin Support oracle database

### API Changes

### Enhancement

1. Enhancement cache pluign
2. Enhancement divide plugin

### Refactor
1. Refactor spring cloud loadbalancer
2. Refactor thread pool
4. Refactor max memory config logic
5. Refactor cors logic
6. Refactor selector match
7. Refactor fixed and elastic connection provider pool
8. Refactor uri register
9. Refactor zk client delete logic
10. Refactor IpUtils
11. Refactor result wrap
13. Refactor app auth
14. Refactor http client
15. Refactor proxy and webclient remove host
16. Refactor shared thread pool

### Bug Fix
1. Fix divide has nullpointerexception
2. Fix body maxInMemorySize
3. Fix admin delay update handle in selector
4. Fix register-client loop error
5. Fix the error of combination plugin
6. Fix sofa and websocket client lossless registration
7. Fix grpc client lossless registration
8. Fix springcloud client lossless registration
9. Fix spring cloud dubbo example
10. Fix NPE repair of admin module caused by spring MVC example synchronization
11. Fix curator version incompatible in bootstrap
12. Fix hiden logic bug
13. Fix failure to load local plugins
14. Fix pg script error
15. Fix hystrix-plugin tests failure
16. Fix client registration by consul only register 1 metadata
17. Fix websocket datasync can chose allow origin to avoid CSRF

## 2.4.3

### New Features

1. Add http register client retry.
2. Support octet-stream context-type.
3. Support redirecting to URIs outside of bootstrap and refactoring code.
4. Add local API authorization.
5. Support config dubbo consumer pool.
6. Support DividePlugin failover retry.
7. Support websocket client configuration.
8. Support config netty thread pool in HttpClient.
9. Support MemoryLimitedLinkedBlockingQueue.
10. Support alibaba dubbo plugin shared thread pool.
11. Support grpc plugin shared thread pool.
12. Add Metrics Plugin.
13. Add Cache Plugin.
14. Add logging rocketmq plugin.

### API Changes

### Enhancement

1. Test combination of Param mapping, Rewrite plugin,
2. Test combination of Param mapping and Redirect plugin.
3. Test combination of RateLimiter and Rewrite plugin.
4. Test combination of RateLimiter and Redirect plugin.
5. Test combination of Request and Redirect plugin.
6. Test combination of Request and Rewrite plugin.
7. Test combination of JWT and RateLimiter plugin.
8. Test combination of JWT and Redirect plugin.
9. Test combination of JWT and Rewrite plugin.
10. Add integrated test of Resilience4j plugin.
11. Add integrated test of Hystrix plugin.
12. Update junit4 to junit5.
13. Add shenyu-examples-springmvc-tomcat.
14. Optimize the password encryption.
15. Optimize and verify shenyu-admin module interface parameters.
16. Optimize the configurable Shenyu agent log collection.
17. Optimize code about data init when sync data.
18. Add unit test for LoggingRocketMQPlugin

### Refactor

1. Used Wheel-Timer instead of ScheduledExecutorService class.
2. Remove DisruptorProvider#onData(final Consumer<DataEvent> function)
3. Synchronized instance rather than class in MetadataExecutorSubscriber.
4. Refactor admin buildHandle about register uri.
5. Spring cloud client auto set port.
6. Refactor jwt support multi-level tokens.
7. Remove monitor plugin.
8. Change logback theme.
9. remove shenyu-agent.

### Bug Fix

1. Fix init CommonUpstreamUtils NPE.
2. Make a judgment on the failure of Nacos registration.
3. Fix NPE when login with non-existent user.
4. Fix double log.
5. Fix misspelled token.
6. Fix retryCount not work bug.
7. Fix token parse error.
8. Fix the trouble of big data in Websocket.
9. Fix NettyHttpClientPlugin did not retry when failed.
10. Fix CVE-2021-41303.
11. Fix judgment of the contains condition of all plugins does not work.
12. Fix http headers lose bug.
13. Fix Bug The Rewrite Plugin should support {PathVariable} request.
14. Fix Bug about data sync with Nacos.
15. Fix Nacos namespace config.
16. Fix NPE or websocket proxy fails when the context-path plug-in is opened.
17. Fix http registers the client plug-in port occupancy detection.

## 2.4.2

### New Features

1. Add Mqtt plugin
2. Add Shenyu-Agent module support observability
3. Add opentelemetry plugin on Shenyu-Agent module
4. Add jaeger plugin on Shenyu-Agent module
5. Add zipkin plugin on Shenyu-Agent module
6. Support Shenyu instance register by zookeeper
7. Support Shenyu response data custom format
8. Support https for upstream check
9. Add RpcContextPlugin to transmit header to rpc context
10. Support cluster model for dubbo plugin
11. Support Shenyu instance register by ETCD

### API Changes

1.Add configuration properties for HTTP synchronization data
2.Remove'/shenyu-client/**','/configs/**','/plugin'interface from the whitelist interface of Shenyu admin

### Enhancement

1. Optimize global error handler for flexible processing
2. Optimized the database access in the loop
3. Optimize result media type and reset response header
4. Enhancement crossfilter filter the same headers
5. Optimize shenyu context module data
6. Optimize dubbo plugin
7. Optimize admin db operation
8. Refactor Response and Cryptor plugin
9. Optimize Admin Resource Permission loader
10. Add authentication on shenyu admin when register by http
11. Optimize netty config.
12. Optimize SQL files for resource,permission
13. Add ExcludeOperatorJudge for selector and rule
14. Add docker-compose on Shenyu-dist
15. Enhance the ability of jwt plugin

### Refactor

1. Remove SpEL and Groovy plugins
2. Optimization prompt of ExtensionLoader
3. Add http client strategy property
4. Refactor shenyu client

### Bug Fix

1. Fix sentinel Plugin-exception number is not effective
2. Fix HttpClientProperties.javaresponseTimeout can not config in yaml
3. Fix Connection reset by peer Exception on webclient
4. Fix register metadata and uri order
5. Fix Admin when press the Add button
6. Fix Spi config
7. Support Dubbo Plugin Single Parameter Primitive Type
8. Fix using etcd cluster to sync data init failed
9. Fix Shiro get white list is null bug
10. Fix zookeeper sync error handling event bug
11. Fix modify-response-plugin and cryptor-response-plugin are used in combination, and no information is returned
12. Fix the bug of missing some field in cryptor rule handler using h2


## 2.4.1

### New Features

1. Support PostgreSQL for admin
1. Support dynamic loading plugin
1. Support local modification data mode
1. Add Websocket plugin
1. Add CryptorRequest plugin
1. Add CryptorResponse plugin
1. Support Grayscale Release for SpringCloud
1. Support Grayscale Release for Apache Dubbo
1. Implement the async dubbo invoking for alibaba-dubbo
1. Support external cross filter config
1. Support sign plugin custom dynamic sign provider

### API Changes

1. Refactor shenyu config in yaml

### Enhancement

1. Optimze code about dubbo async call
1. Add loadbalancer common module
1. Optimize sql init
1. Refactor Admin PageHelper to query list
1. Optimize GlobalErrorHandler
1. Optimize the return value of the'skip' method interface of'ShenyuPlugin' to boolean
1. Optimize register rules
1. Modify dubbo and sofa param resolve service
1. Refactor sign plugin api
1. Remove websocket filter

### Refactor

1. Remove lombok dependency
1. Remove mapstruct dependency
1. Support JDK8 ~ JDK15
1. Add missing plugin_handle sql for plugin motan

### Bug Fix

1. Fix JsonSyntaxException in jwt plugin
1. Fix sql miss for resilience4j plugin handler
1. Fix disruptor problem of hold event data in consume event
1. Fix deadlock bug of HealthCheckTask
1. Fix client retry the connection add log and increase sleep time
1. Fix the default_group of nacos
1. Fix maven ignore and docker entrypoint
1. Fix admin Return password question
1. Fix LDAP query built from user-controlled source
1. Fix the IP address retrieval error
1. Fix Gson toJson is null
1. Fix the index out of range bug for context path.
1. Fix monitor init metrics label bug
1. Fix GlobalErrorHandler error object to map bug by JacksonUtils.toMap
1. Fix modify response plugin order bug
1. Fix the bug of register
1. Fix sofa plugin register metadata and parameters resolve
1. Fix motan ,dubbo, sofa plugin metadata init bug


## 2.4.0

### New Features

1. Support reading init_script file which is not under resource/directory
1. Display the plugin menus in categories
1. Admin add execute Multi-path sql script
1. IpUtils add a parameter to select the network ip
1. Add parameter-mapping plugin
1. Support Consul as shenyu-register-center
1. Support Etcd as shenyu-sync-data-center
1. Add sentinel customized fallbackhandler
1. Add response plugin
1. Add JWT plugin
1. Add Request plugin
1. Add Motan plugin
1. Add Logging plugin
1. Add Modify-response plugin
1. Add Oauth2 plugin
1. Add Menu Resource Permissions
1. Add Data Permissions

### API Changes

1. Change the project name from Soul to ShenYu
1. Change the group id from org.dromara to org.apache.shenyu

### Enhancement

1. H2 support insert ingore into in Mysql model
1. Improvements For the Apache Dubbo plugin
1. Optimization of GRPC plugin

### Refactor

1. Refactor code about "async invoke" is not supported in Dubbo lower than 2.7.3
1. Replace the term Operator by Predicate
1. Refine judge conditions operator
1. Refactor PredicateJudge module using SPI
1. Refactor code about client register

### Bug Fix

1. Fix the JwtUtil.getUserId method bug
1. Fix the  shenyu-spring-boot-starter bug
1. The encoded urlPath will be re-encoded in WebClientPlugin
1. Replace The Risky Cryptographic Algorithm "AES/ECB/NoPadding"
1. ReadTimeoutHandler on a channel which in a PooledConnectionProvider would cause an unexpected ReadTimeoutException
1. Got ClassNotFoundException while start my Gateway in 2.4.8 spring boot


2.3.0（2021-04-02）
------------------
### soul-admin

* Add 'open' field  to allow app path authentication or not in sign plugin. #1168
* Optimize divide plugin to use common plugin template in soul-dashboard. #1163
* Add  default values and rule checks in plugin handler. #1112
* Add resource management to allow user to add plugin, adjust menu and button resource and so on  in  soul-dashboard and soul-admin.  #1034
* Add menu and data permission in soul-admin. #917
* Add H2 store for soul-admin #918

### soul-bootstrap

* Add tars plugin #863
* Add sentinel plugin #331
* Add sofa plugin #384
* Add Resilience4j plugin for soul-plugin. #434
* Add Context path mapping plugin for soul-plugin. #894
* Add Grpc plugin supports grpc protocol. #1081
* support form submission for dubbo plugin.#339
* feat(plugin handle): #307
* Add dist package module #320
* Add test cases for soul-admin #500
* Add register center for consul #1148
* Add register center for etcd #1161
* Add register center for nacos #1182
* Add register center for zookeeper #1141 #1139
