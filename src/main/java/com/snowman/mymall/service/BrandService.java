package com.snowman.mymall.service;

import com.snowman.mymall.common.utils.Page;
import com.snowman.mymall.vo.BrandVO;

import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:26
 * @Version 1.0
 **/
public interface BrandService {

    public BrandVO queryById(Integer id);

    public Page list(Integer pageNum, Integer pageSize);
}
