package com.snowman.mymall.service;

import com.snowman.mymall.common.vo.GoodsVO;
import com.snowman.mymall.entity.GoodsEntity;
import com.snowman.mymall.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 15:04
 * @Version 1.0
 **/
public interface GoodsService {

    public List<GoodsVO> queryNewGoodsList();

}
