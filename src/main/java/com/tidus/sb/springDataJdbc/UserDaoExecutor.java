package com.tidus.sb.springDataJdbc;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-10-11
 */
@Slf4j
@Component
public class UserDaoExecutor {

    private final UserDao userDao;

    //在 Spring4.x 中增加了新的特性：如果类只提供了一个带参数的构造方法，则不需要对对其内部的属性写 @Autowired 注解，Spring 会自动为你注入属性
    public UserDaoExecutor(UserDao userDao) {
        this.userDao = userDao;
    }

//    @PostConstruct       // 项目启动时执行，打印user里的所有数据
    public void method() {
        if (userDao != null) {
            log.info("Connect to datasource success.");
        } else {
            log.error("Connect to datasource failed!");
            return;
        }

        List<User> list = userDao.list();
        list.forEach(item -> log.info(item.toString()));
    }

}
