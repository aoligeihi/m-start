package com.yy.star.dao;

import com.yy.star.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @program: star
 * @description: User
 * @author: yang
 * @create: 2023-08-07 13:49
 **/
@Repository
public interface UserDao extends JpaRepository<User, Integer>, Serializable {
}
