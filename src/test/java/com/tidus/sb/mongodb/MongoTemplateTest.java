package com.tidus.sb.mongodb;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

@Slf4j
@SpringBootTest
public class MongoTemplateTest {

    @Autowired
    MongoTemplate mongoTemplate;

    private static final String COLLECTION_NAME = "user";

    /**
     * 删除集合
     */
    @Test
    public void dropCollection() {
        mongoTemplate.dropCollection(COLLECTION_NAME);
    }

    /**
     * 查询集合名称列表
     */
    @Test
    public void getCollection() {
        Set<String> collectionNames = mongoTemplate.getCollectionNames();
        log.info(JSON.toJSONString(collectionNames));
    }

    /**
     * 创建集合
     */
    @Test
    public void createCollection() {
        boolean exists = mongoTemplate.collectionExists(COLLECTION_NAME);
        if (exists) {
            log.info("集合已经存在");
            return;
        }
        CollectionOptions collectionOptions = CollectionOptions.empty()
                .size(6142800) // 为固定集合指定一个最大值，即字节数
                .maxDocuments(10000) // 指定固定集合中包含文档的最大数量。
                //.capped() 如果为 true，则创建固定集合。固定集合是指有着固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。
                .collation(Collation.of(Locale.CHINA.getLanguage()));// 定制中文排序规则
        mongoTemplate.createCollection(COLLECTION_NAME, collectionOptions);
    }

    /**
     * 插入文档
     */
    @Test
    public void insert() {
        User user = new User();
        user.setId(1);
        user.setUsername("张三");
        user.setPhone("131000281912");
        user.setNickname("小三");
        user.setSex("男");
        user.setLastModified(new Date());
        mongoTemplate.insert(user);

        User user1 = new User();
        user1.setId(2);
        user1.setUsername("李四");
        user1.setPhone("132000281912");
        user1.setNickname("小四");
        user1.setSex("男");
        user1.setLastModified(new Date());
        mongoTemplate.insert(user1);
    }

    /**
     * 查询文档
     */
    @Test
    public void findAll() {
        List<User> all = mongoTemplate.findAll(User.class, COLLECTION_NAME);
        log.info(JSON.toJSONString(all));
    }

    /**
     * 根据id查询文档
     */
    @Test
    public void findById() {
        User user = mongoTemplate.findById(1, User.class, COLLECTION_NAME);
        log.info(JSON.toJSONString(user));
    }

    /**
     * 分页条件查询文档
     */
    @Test
    public void findByCondition() {
        // 条件 跟sql写法类似
        Query query = new Query(Criteria
                .where("phone").is("131000281912")
                .and("id").gte(1)
                .and("username").in("张三", "李四"));

        // 排序
        query.with(Sort.by(Sort.Order.asc("username")));

        // 分页
        Pageable pageable = PageRequest.of(0, 10);
        query.with(pageable);

        List<User> users = mongoTemplate.find(query, User.class, COLLECTION_NAME);
        long count = mongoTemplate.count(query, User.class);

        Page<User> page = PageableExecutionUtils.getPage(users, pageable, () -> count);
        log.info(JSON.toJSONString(page));
    }

    @Test
    public void getCount() {
        Query query = new Query(Criteria
                .where("phone").is("131000281912")
                .and("id").gte(1)
                .and("username").in("张三", "李四"));

        long count = mongoTemplate.count(query, User.class, COLLECTION_NAME);
        log.info(JSON.toJSONString(count));
    }

    /**
     * 更新文档
     */
    @Test
    public void update() {
        User user = new User();
        user.setId(2);
        user.setUsername("李四");
        user.setPhone("131000281922");
        user.setNickname("小四");
        user.setSex("女");
        user.setLastModified(new Date());
        mongoTemplate.save(user, COLLECTION_NAME);
    }

    /**
     * 删除文档
     */
    @Test
    public void deleteById() {
        mongoTemplate.remove(new Query(), User.class, COLLECTION_NAME);
    }

    /**
     * 聚合
     */
    @Test
    public void aggregate() {
        // count
        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.group().count().as("count")
        );
        AggregationResults countAggregate = mongoTemplate.aggregate(countAggregation, User.class, HashMap.class);
        log.info(JSON.toJSONString(countAggregate));

        // sum
        Aggregation sumAggregation = Aggregation.newAggregation(
                match(Criteria.where("sex").in("男", "女")),
                group().max("id").as("sum")
        );

        AggregationResults sumAggregate = mongoTemplate.aggregate(sumAggregation, User.class, HashMap.class);
        log.info(JSON.toJSONString(sumAggregate));

        // max
        Aggregation maxAggregation = Aggregation.newAggregation(
                group().max("id").as("max")
        );

        AggregationResults maxAggregate = mongoTemplate.aggregate(maxAggregation, User.class, HashMap.class);
        log.info(JSON.toJSONString(maxAggregate));
    }
}
