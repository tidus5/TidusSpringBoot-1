package com.tidus.sb.redis;

import com.tidus.sb.redis.RedisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author okevin
 * @date 2020/2/18 23:14
 */
@SpringBootTest
public class RedisUtilTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testSet() {
        redisUtil.set("redis_util", "1");
        Assertions.assertEquals("1", redisUtil.get("redis_util"));
    }
}
