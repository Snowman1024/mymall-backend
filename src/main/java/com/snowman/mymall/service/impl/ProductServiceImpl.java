package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.utils.ConvertUtil;
import com.snowman.mymall.repository.ProductRepository;
import com.snowman.mymall.service.ProductService;
import com.snowman.mymall.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:27
 * @Version 1.0
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * 通过商品id查产品信息
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<ProductVO> queryByGoodsId(Integer goodsId) {

        List<Object> productObjectList = productRepository.queryByGoodsId(goodsId);
        List<ProductVO> productVOList = entityToVo(productObjectList);
        return productVOList;
    }

    /**
     * 通过id查产品
     * @param id
     * @return
     */
    @Override
    public ProductVO queryById(Integer id){
        List<Object> productObjectList = productRepository.queryById(id);
        List<ProductVO> productVOList = entityToVo(productObjectList);
        return productVOList.get(0);
    }

    /**
     * entity转vo
     * @param productObjectList
     * @return
     */
    private List<ProductVO> entityToVo(List<Object> productObjectList){
        List<ProductVO> productVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(productObjectList)) {
            return productVOList;
        }
        Object[] productObjectArr = productObjectList.toArray();

        for (int i = 0; i < productObjectArr.length; i++) {
            Object[] item = (Object[]) productObjectArr[1];

            ProductVO productVO = new ProductVO();
            productVO.setId(ConvertUtil.toInteger(item[0]));
            productVO.setGoodsId(ConvertUtil.toInteger(item[1]));
            productVO.setGoodsSpecificationIds(ConvertUtil.toString(item[2]));
            productVO.setGoodsSn(ConvertUtil.toString(item[3]));
            productVO.setGoodsNumber(ConvertUtil.toInteger(item[4]));
            productVO.setRetailPrice(ConvertUtil.toBigDecimal(item[5]));
            productVO.setMarketPrice(ConvertUtil.toBigDecimal(item[6]));
            productVO.setGoodsName(ConvertUtil.toString(item[7]));
            productVO.setListPicUrl(ConvertUtil.toString(item[8]));

            productVOList.add(productVO);
        }
        return productVOList;
    }
}
