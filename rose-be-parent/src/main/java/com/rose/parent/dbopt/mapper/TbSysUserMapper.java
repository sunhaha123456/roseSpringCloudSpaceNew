package com.rose.parent.dbopt.mapper;

import com.rose.parent.data.entity.TbSysUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbSysUserMapper extends Mapper<TbSysUser> {
    List<TbSysUser> listByUnameAndPwd(@Param("uname") String uname, @Param("upwd") String upwd);
}