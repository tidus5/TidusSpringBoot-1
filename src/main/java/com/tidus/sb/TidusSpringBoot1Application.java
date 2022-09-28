package com.tidus.sb;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.tidus.sb.mybatis.mapper")
public class TidusSpringBoot1Application {

    public static void main(String[] args) {
        SpringApplication.run(TidusSpringBoot1Application.class, args);
    }

}
