package org.ko.sigma.rest.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.ko.sigma.rest.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseMapper<UserEntity> {



}