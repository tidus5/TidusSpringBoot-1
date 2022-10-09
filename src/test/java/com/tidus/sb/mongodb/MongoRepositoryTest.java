package com.tidus.sb.mongodb;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.Date;
import java.util.List;

/**
 * https://juejin.cn/post/7011436589360349215
 */
@Slf4j
@SpringBootTest
public class MongoRepositoryTest {

    @Autowired
    UserRepository userRepository;

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
        user.setLastModified(new Date());
        userRepository.insert(user);
    }

    /**
     * 分页查询文档
     */
    @Test
    public void findAll() {
        Sort sort = Sort.by(Sort.Order.asc("id"));
        PageRequest pageRequest = PageRequest.of(0, 10, sort);
        Page<User> page = userRepository.findAll(pageRequest);
        log.info(JSON.toJSONString(page));
    }

    /**
     * 统计文档个数
     */
    @Test
    public void count() {
        long count = userRepository.count();
        log.info(JSON.toJSONString(count));
    }

    /**
     * id查询
     */
    @Test
    public void findById() {
        User user = userRepository.findById(1).orElse(null);
        log.info(JSON.toJSONString(user));
    }

    /**
     * 条件查询
     */
    @Test
    public void findByCondition() {
        User user = new User();
        user.setUsername("张");
        user.setPhone("131000281912");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id", "nickname", "lastModified")
                .withMatcher("username", ExampleMatcher.GenericPropertyMatcher::endsWith)
                .withMatcher("username", ExampleMatcher.GenericPropertyMatcher::startsWith);

        Example example = Example.of(user, matcher);
        List list = userRepository.findAll(example);
        log.info(JSON.toJSONString(list));
    }

    /**
     * 更新文档
     */
    @Test
    public void update() {
        User user = new User();
        user.setId(1);
        user.setUsername("张三1");
        user.setPhone("131000281922");
        user.setNickname("小三1");
        user.setLastModified(new Date());
        userRepository.save(user);
    }

    /**
     * 删除文档
     */
    @Test
    public void deleteById() {
        userRepository.deleteById(1);
    }
}

