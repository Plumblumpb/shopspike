package com.plumblum.miaosha.dao;


import com.plumblum.miaosha.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MiaoshaUserDao {
	
	@Select("select * from USER where id = #{id}")
	public MiaoshaUser getById(@Param("id") long id);
}
