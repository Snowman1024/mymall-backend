package com.snowman.mymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.enumeration.CouponSentType;
import com.snowman.mymall.common.enumeration.CouponStatus;
import com.snowman.mymall.common.enumeration.SearchFrom;
import com.snowman.mymall.common.utils.*;
import com.snowman.mymall.entity.*;
import com.snowman.mymall.repository.CategotyRepository;
import com.snowman.mymall.repository.GoodsRepository;
import com.snowman.mymall.service.*;
import com.snowman.mymall.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Autowired
    private GoodsSpecificationService goodsSpecificationService;

    @Autowired
    private ProductService productService;

    @Autowired
    private GoodsGalleryService goodsGalleryService;

    @Autowired
    private AttributeService attributeService;

    @Autowired
    private GoodsIssueService goodsIssueService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentPictureService commentPictureService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private UserFootprintService userFootprintService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private RelatedGoodsService relatedGoodsService;

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
        Long totalCount = (Long) objArr[0];
        PageUtils goodsData = null;
        if (null != totalCount && totalCount > 0) {

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
            if (order.equalsIgnoreCase("asc")) {
                jsql.append(" order by g.retailPrice asc");
            } else {
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
        searchHistory.setAddTime(new Date());
        searchHistory.setKeyword(keyword);
        searchHistory.setUserId(null != userVO ? userVO.getUserId().toString() : "");
        searchHistory.setFrom(SearchFrom.MINIPROGRAM.getKey());
        searchHistoryService.saveSearchHistory(searchHistory);
    }


    /**
     * 商品详情
     *
     * @param userId
     * @param goodsId
     * @param referrer
     * @return
     */
    @Override
    public Map<String, Object> detail(Integer userId, Integer goodsId, Integer referrer) {

        //查商品
        GoodsEntity goodsEntity = goodsRepository.queryById(goodsId);
        //查规格
        List<GoodsSpecificationVO> goodsSpecificationVOList = goodsSpecificationService.queryByGoodsId(goodsId);

        List<Map> specificationList = new ArrayList();
        //按规格名称分组
        for (int i = 0; i < goodsSpecificationVOList.size(); i++) {
            GoodsSpecificationVO specItem = goodsSpecificationVOList.get(i);
            //
            List<GoodsSpecificationVO> tempList = null;
            for (int j = 0; j < specificationList.size(); j++) {
                if (specificationList.get(j).get("specification_id").equals(specItem.getSpecificationId())) {
                    tempList = (List<GoodsSpecificationVO>) specificationList.get(j).get("valueList");
                    break;
                }
            }
            //
            if (null == tempList) {
                Map temp = new HashMap();
                temp.put("specification_id", specItem.getSpecificationId());
                temp.put("name", specItem.getName());
                tempList = new ArrayList();
                tempList.add(specItem);
                temp.put("valueList", tempList);
                specificationList.add(temp);
            } else {
                tempList.add(specItem);
            }
        }

        //查产品信息
        List<ProductVO> productList = productService.queryByGoodsId(goodsId);
        //轮播图
        List<GoodsGalleryVO> galleryList = goodsGalleryService.queryByGoodsId(goodsId);
        //属性
        List<AttributeVO> attributeList = attributeService.queryByGoodsId(goodsId);
        //常见问题
        List<GoodsIssueVO> issueList = goodsIssueService.queryGoodsIssueList();
        //品牌
        BrandVO brand = brandService.queryById(goodsEntity.getBrandId());
        //查评论
        Integer typeId = 0;
        Integer commentCount = commentService.queryTotal(typeId, goodsId);
        List<CommentVO> hotComment = commentService.queryByTypeIdAndValueId(typeId, goodsId);

        Map commentInfo = new HashMap();
        if (null != hotComment && hotComment.size() > 0) {
            UserVO commentUser = userService.queryByUserId(hotComment.get(0).getUserId());
            commentInfo.put("content", Base64Util.decode(hotComment.get(0).getContent()));
            commentInfo.put("addTime", DateUtils.format(hotComment.get(0).getAddTime()));
            commentInfo.put("nickname", commentUser.getNickName());
            commentInfo.put("avatar", commentUser.getAvatar());
            Integer commentId = hotComment.get(0).getId();
            List<CommentPictureVO> commentPictureList = commentPictureService.queryByComementId(commentId);
            commentInfo.put("picList", commentPictureList);
        }
        Map comment = new HashMap();
        comment.put("count", commentCount);
        comment.put("data", commentInfo);
        //当前用户是否收藏
        Integer userHasCollect = 0;
        if(null != userId){
            userHasCollect = collectService.queryTotal(userId, goodsId, typeId);
            if (userHasCollect > 0) {
                userHasCollect = 1;
            }

            //记录用户的足迹
            UserFootprintEntity footprintEntity = new UserFootprintEntity();
            footprintEntity.setUserId(userId);
            footprintEntity.setGoodsId(goodsEntity.getId());
            footprintEntity.setAddTime(new Date());
            if (null != referrer) {
                footprintEntity.setReferrer(referrer);
            } else {
                footprintEntity.setReferrer(0);
            }
            userFootprintService.save(footprintEntity);

            // 记录推荐人是否可以领取红包，用户登录时校验
            try {
                // 是否已经有可用的转发红包
                List<CouponVO> enableCouponList = couponService.queryUserCoupons(userId, CouponSentType.GOOD_TRANFER.getKey());
                if (CollectionUtils.isEmpty(enableCouponList) && null != referrer ) {
                    // 获取优惠信息
                    List<Integer> sendTypeList = new ArrayList<>();
                    sendTypeList.add(CouponSentType.GOOD_TRANFER.getKey());
                    List<CouponVO> couponList = couponService.queryCouponBySendType(sendTypeList);
                    if (!CollectionUtils.isEmpty(couponList)) {
                        CouponVO couponVo = couponList.get(0); //TODO
                        Integer footprintNum = userFootprintService.queryTotalByGoodsId(goodsId, referrer);
                        if (null != footprintNum && null != couponVo.getMinTransmitNum()
                                && footprintNum > couponVo.getMinTransmitNum()) {
                            UserCouponEntity userCoupon = new UserCouponEntity();
                            userCoupon.setCouponId(couponVo.getId());
                            userCoupon.setCouponNo(CharUtil.getRandomString(12));
                            userCoupon.setUserId(userId);
                            userCoupon.setAddTime(new Date());
                            userCoupon.setCouponStatus(CouponStatus.USUSE.getKey());
                            userCouponService.save(userCoupon);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("记录推荐人是否可以领取红包异常:goodsId:{},userId:{},referrer:{},异常信息:{}",
                        goodsId, userId, referrer, e);
            }
        }
        //
        Map<String, Object> resultObj = new HashMap();
        resultObj.put("info", goodsEntity);
        resultObj.put("gallery", galleryList);
        resultObj.put("attribute", attributeList);
        resultObj.put("userHasCollect", userHasCollect);
        resultObj.put("issue", issueList);
        resultObj.put("comment", comment);
        resultObj.put("brand", brand);
        resultObj.put("specificationList", specificationList);
        resultObj.put("productList", productList);

        return resultObj;
    }


    /**
     * 商品详情页的大家都在看的商品
     * @param id
     * @return
     */
    @Override
    public Map<String,Object> related(Integer id){
        List<RelatedGoodsVO> relatedGoodsEntityList = relatedGoodsService.queryRelatedGoods();

        List<Integer> relatedGoodsIds = new ArrayList();
        for (RelatedGoodsVO relatedGoodsEntity : relatedGoodsEntityList) {
            relatedGoodsIds.add(relatedGoodsEntity.getRelatedGoodsId());
        }

        List<GoodsVO> relatedGoods = new ArrayList<GoodsVO>();
        if (CollectionUtils.isEmpty(relatedGoodsIds)) {
            //查找同分类下的商品
            GoodsEntity categoryGoods = goodsRepository.queryById(id);
            relatedGoods = this.queryByCategoryId(categoryGoods.getCategoryId());
        } else {
            relatedGoods = this.queryByGoodsIds(relatedGoodsIds);
        }

        Map<String, Object> resultObj = new HashMap();
        resultObj.put("goodsList", relatedGoods);
        return resultObj;
    }

    /**
     * 通过品类id查商品
     * @param categoryId
     * @return
     */
    private List<GoodsVO> queryByCategoryId(Integer categoryId){
        if(null == categoryId){
            logger.error("商品品类是空");
            return null;
        }
        List<GoodsEntity> goodsEntityList = goodsRepository.queryByCategoryId(categoryId);
        List<GoodsVO> voList = new ArrayList<>();
        if(CollectionUtils.isEmpty(goodsEntityList)){
            return voList;
        }
        BeanCopier copier = BeanCopier.create(GoodsEntity.class, GoodsVO.class, false);
        for (GoodsEntity entity : goodsEntityList) {
            GoodsVO vo = new GoodsVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 通过商品id列表查商品
     * @param goodsIdList
     * @return
     */
    private List<GoodsVO> queryByGoodsIds(List<Integer> goodsIdList){
        if(CollectionUtils.isEmpty(goodsIdList)){
            logger.error("goodsIdList是空");
            return null;
        }
        List<GoodsEntity> goodsEntityList = goodsRepository.queryByGoodsIds(goodsIdList);
        List<GoodsVO> voList = new ArrayList<>();
        if(CollectionUtils.isEmpty(goodsEntityList)){
            return voList;
        }
        BeanCopier copier = BeanCopier.create(GoodsEntity.class, GoodsVO.class, false);
        for (GoodsEntity entity : goodsEntityList) {
            GoodsVO vo = new GoodsVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 通过id查商品
     * @param goodsId
     * @return
     */
    @Override
    public GoodsVO queryById(Integer goodsId){
       GoodsEntity entity = goodsRepository.queryById(goodsId);
        GoodsVO vo = new GoodsVO();
       if(null == entity){
           return vo;
       }
        BeanCopier copier = BeanCopier.create(GoodsEntity.class, GoodsVO.class, false);
        copier.copy(entity, vo, null);
        return vo;
    }
}
