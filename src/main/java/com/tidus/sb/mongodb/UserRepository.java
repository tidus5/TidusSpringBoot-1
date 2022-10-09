package com.tidus.sb.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * https://chenyejun.github.io/blog/mongoDB/mongodbAddUser.html
 * https://juejin.cn/post/7011436589360349215
 */
public interface UserRepository extends MongoRepository<User, Integer> {
}
