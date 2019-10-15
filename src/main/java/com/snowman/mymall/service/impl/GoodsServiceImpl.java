package com.snowman.mymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.enumeration.SearchFrom;
import com.snowman.mymall.common.utils.PageUtils;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.entity.CategoryEntity;
import com.snowman.mymall.entity.GoodsEntity;
import com.snowman.mymall.entity.SearchHistoryEntity;
import com.snowman.mymall.repository.CategotyRepository;
import com.snowman.mymall.repository.GoodsRepository;
import com.snowman.mymall.service.GoodsService;
import com.snowman.mymall.service.SearchHistoryService;
import com.snowman.mymall.vo.CategoryVO;
import com.snowman.mymall.vo.GoodsVO;
import com.snowman.mymall.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 15:17
 * @Version 1.0
 **/
@Service
public class GoodsServiceImpl implements GoodsService {

    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SearchHistoryService searchHistoryService;

    @Autowired
    private CategotyRepository categotyRepository;

    /**
     * @return
     */
    @Override
    public List<GoodsVO> queryNewGoodsList() {
        logger.info("首页查询新商品信息service开始");

        List<GoodsVO> goodsVOList = new ArrayList<>();

        List<GoodsEntity> goodsEntityList = goodsRepository.queryNewGoodsList();
        if (CollectionUtils.isEmpty(goodsEntityList)) {
            return goodsVOList;
        }
        BeanCopier copier = BeanCopier.create(GoodsEntity.class, GoodsVO.class, false);
        for (GoodsEntity entity : goodsEntityList) {
            GoodsVO goodsVO = new GoodsVO();
            copier.copy(entity, goodsVO, null);
            goodsVOList.add(goodsVO);
        }
        logger.info("首页查询新商品信息service结束");
        return goodsVOList;
    }

    /**
     * 获取商品列表
     *
     * @param loginUser
     * @param goodsVO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result list(UserVO loginUser, GoodsVO goodsVO, Integer pageNum, Integer pageSize) {
        logger.info("获取商品列表service开始,loginUser:{},goodsVO:{},pageNum:{},pageSize:{}",
                JSON.toJSONString(loginUser), JSON.toJSONString(goodsVO), pageNum, pageSize);
        //
        Map<String, Object> goodsConditionMap = goodsCondition(goodsVO);
        StringBuffer jsql = new StringBuffer("select g ");
        jsql.append((String) goodsConditionMap.get("jsql"));
        Map<String, Object> queryMap = (Map<String, Object>) goodsConditionMap.get("queryMap");

        List<GoodsEntity> goodsEntityList = goodsRepository.exeQueryCustNameParm(jsql.toString(), queryMap);

        //筛选的分类
        List<CategoryVO> filterCategory = new ArrayList();
        CategoryVO rootCategory = new CategoryVO();
        rootCategory.setId(0);
        rootCategory.setName("全部");
        rootCategory.setChecked(false);
        filterCategory.add(rootCategory);

        if (!CollectionUtils.isEmpty(goodsEntityList)) {
            List<Integer> goodsCategoryIdList = new ArrayList();
            for (GoodsEntity goodsEntity : goodsEntityList) {
                Integer goodsCategoryId = goodsEntity.getCategoryId();
                if (null != goodsCategoryId) {
                    goodsCategoryIdList.add(goodsCategoryId);
                }
            }
            //查找二级分类的parent_id
            List<Integer> secondCategoryParentIdList = categotyRepository.queryParentIdByIds(goodsCategoryIdList);
            //一级分类
            if (!CollectionUtils.isEmpty(secondCategoryParentIdList)) {
                List<CategoryEntity> categoryEntityList = categotyRepository.queryByIds(secondCategoryParentIdList);
                if (!CollectionUtils.isEmpty(categoryEntityList)) {
                    List<CategoryVO> categoryVOList = new ArrayList<>();
                    for (CategoryEntity entity : categoryEntityList) {
                        CategoryVO categoryVO = new CategoryVO();
                        categoryVO.setId(entity.getId());
                        categoryVO.setName(entity.getName());
                        categoryVOList.add(categoryVO);
                    }
                    filterCategory.addAll(categoryVOList);
                }
            }
        }
        //加入分类条件
        Integer categoryId = goodsVO.getCategoryId();
        if (null != categoryId && categoryId > 0) {

            List<CategoryEntity> childCategoryList = categotyRepository.queryByParentId(categoryId);
            List<Integer> categoryIds = new ArrayList();
            for (CategoryEntity child : childCategoryList) {
                categoryIds.add(child.getId());
            }
            categoryIds.add(categoryId);
            goodsVO.setCategoryIdList(categoryIds);
        }

        //查询列表数据
        goodsConditionMap = goodsCondition(goodsVO);

        StringBuffer goodsCountJsql = new StringBuffer("select count(1) ");
        goodsCountJsql.append((String) goodsConditionMap.get("jsql"));
        queryMap = (Map<String, Object>) goodsConditionMap.get("queryMap");

        Object[] objArr = goodsRepository.exeQueryCustNameParm(goodsCountJsql.toString(), queryMap).toArray();
        Long totalCount = (Long)objArr[0];
        PageUtils goodsData = null;
        if(null != totalCount && totalCount>0){

            StringBuffer goodsJsql = new StringBuffer("select g ");
            goodsJsql.append((String) goodsConditionMap.get("jsql"));
            List<GoodsEntity> goodsList = goodsRepository.exeQueryCustNameParm(goodsJsql.toString(), queryMap, pageNum, pageSize);

            goodsData = new PageUtils(goodsList, totalCount.intValue(), pageSize, pageNum);

            //搜索到的商品
            for (CategoryVO categoryVO : filterCategory) {
                if ((null != categoryId && (categoryVO.getId() == 0 || categoryId.equals(categoryVO.getId())))
                        || (null == categoryId && null == categoryVO.getId())) {
                    categoryVO.setChecked(true);
                } else {
                    categoryVO.setChecked(false);
                }
            }
            goodsData.setFilterCategory(filterCategory);
            goodsData.setGoodsList(goodsList);
        }
        //添加到搜索历史
        saveSearchHistory(loginUser, goodsVO.getKeywords());

        logger.info("获取商品列表service结束");
        return Result.ok(goodsData);
    }


    private Map<String, Object> goodsCondition(GoodsVO goodsVO) {
        Integer isNew = goodsVO.getIsNew();
        Integer isHot = goodsVO.getIsHot();
        Integer brandId = goodsVO.getBrandId();
        String name = goodsVO.getName();
        String keywords = goodsVO.getKeywords();

        String order = goodsVO.getOrder();
        String sort = goodsVO.getSort();

        List<Integer> categoryIdList = goodsVO.getCategoryIdList();

        Map<String, Object> queryMap = new HashMap<>();
        StringBuffer jsql = new StringBuffer(" from GoodsEntity g where g.isDelete=0 and g.isOnSale=1 ");
        if (null != isNew) {
            jsql.append(" and g.isNew = :isNew");
            queryMap.put("isNew", isNew);
        }
        if (null != isHot) {
            jsql.append(" and g.isHot = :isHot");
            queryMap.put("isHot", isHot);
        }
        if (null != brandId) {
            jsql.append(" and g.brandId = :brandId");
            queryMap.put("brandId", brandId);
        }
        if (StringUtils.isNotBlank(name)) {
            jsql.append(" and g.name like :name");
            queryMap.put("name", "%" + name + "%");
        }
        if (!CollectionUtils.isEmpty(categoryIdList)) {
            jsql.append(" and g.categoryId in (:categoryId)");
            queryMap.put("categoryId", categoryIdList);
        }
        if (StringUtils.isNotBlank(keywords)) {
            jsql.append(" and g.keywords like :keywords");
            queryMap.put("keywords", "%" + keywords + "%");
        }
        if (StringUtils.isNotBlank(sort) && sort.equals("price")) {
            if(order.equalsIgnoreCase("asc")){
                jsql.append(" order by g.retailPrice asc");
            }else{
                jsql.append(" order by g.retailPrice desc");
            }
        } else {
            jsql.append(" order by g.id desc");
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("jsql", jsql.toString());
        returnMap.put("queryMap", queryMap);

        return returnMap;
    }

    /**
     * 保存搜索历史
     *
     * @param userVO
     * @param keyword
     */
    private void saveSearchHistory(UserVO userVO, String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return;
        }
        SearchHistoryEntity searchHistory = new SearchHistoryEntity();
        searchHistory.setAddTime(System.currentTimeMillis() / 1000);
        searchHistory.setKeyword(keyword);
        searchHistory.setUserId(null != userVO ? userVO.getUserId().toString() : "");
        searchHistory.setFrom(SearchFrom.MINIPROGRAM.getKey());
        searchHistoryService.saveSearchHistory(searchHistory);
    }
}
