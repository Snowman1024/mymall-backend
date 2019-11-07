package com.snowman.mymall.common.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 17:33
 * @Version 1.0
 **/
@Data
public class Page implements Serializable {

    //总记录数
    private int count;
    //每页记录数
    private int numsPerPage;
    //总页数
    private int totalPages;
    //当前页数
    private int currentPage;
    //列表数据
    private List<?> data;
    //扩展
    private Object filterCategory;
    //扩展
    private Object goodsList;


    /**
     * 分页
     *
     * @param list        列表数据
     * @param count       总记录数
     * @param numsPerPage 每页记录数
     * @param currentPage 当前页数
     */
    public Page(List<?> list, int count, int numsPerPage, int currentPage) {
        this.data = list;
        this.count = count;
        this.numsPerPage = numsPerPage;
        this.currentPage = currentPage;
        this.totalPages = (int) Math.ceil((double) count / numsPerPage);
    }


}
