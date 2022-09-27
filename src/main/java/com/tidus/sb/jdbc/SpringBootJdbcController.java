package com.tidus.sb.jdbc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * https://www.cainiaojc.com/springboot/springboot-jdbc-example.html
 * https://www.cnblogs.com/throwable/p/13326290.html
 * https://www.cnblogs.com/fishpro/p/spring-boot-study-jdbc.html
 *
 * DROP TABLE IF EXISTS `user`;
 * CREATE TABLE `user` (
 *   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 *   `name` varchar(100) DEFAULT NULL,
 *   `email` varchar(100) DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
@RestController
public class SpringBootJdbcController {
    @Autowired
    JdbcTemplate jdbc;

    @RequestMapping("/insert")
    public String index(){
        jdbc.execute("insert into user(name,email)values('nhooo','java@(cainiaojc.com)')");
        return"data inserted Successfully";
    }

    @GetMapping("/users")
    public Object   getUsers(){
        List<Map<String,Object>> list=jdbc.queryForList("select * from user limit 20");
        return  list;
    }
}
