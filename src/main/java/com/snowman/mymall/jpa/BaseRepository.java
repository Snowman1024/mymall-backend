package com.snowman.mymall.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 13:34
 * @Version 1.0
 **/
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {


    List<T> exeQueryIdxParm(String namedQuery, Map<Integer, Object> params);


    List<T> exeQueryNameParm(String namedQuery, Map<String, Object> params);


    List<T> exeQueryIdxParm(String namedQuery, Map<Integer, Object> params, int pageNum, int pageSize);


    List<T> exeQueryNameParm(String namedQuery, Map<String, Object> params, int pageNum, int pageSize);


    List<T> exeQueryCustIdxParm(String jpql, Map<Integer, Object> params);


    List<T> exeQueryCustNameParm(String jpql, Map<String, Object> params);


    List<T> exeQueryCustIdxParm(String jpql, Map<Integer, Object> params, int pageNum, int pageSize);


    List<T> exeQueryCustNameParm(String jpql, Map<String, Object> params, int pageNum, int pageSize);




}