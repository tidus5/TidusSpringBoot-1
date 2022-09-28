package com.tidus.sb.springDataJdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * https://github.com/dunwu/spring-boot-tutorial
 * https://juejin.cn/post/6887110115959242759
 */
@RestController
@RequestMapping(value = "datajdbc")
public class UserDataJdbcController {

    @Autowired
    UserDaoExecutor userDaoExecutor;

    @Autowired
    UserDao userDao;

    @RequestMapping(path = "getUser")
    public Object getPerson() {
        List<User> list = userDao.list();
        return list;
    }
}
