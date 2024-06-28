package com.english.model.request;

import com.english.model.enums.QueryOrder;

public class QueryCondition {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer offset;

    private String orderType;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return pageSize * (pageNo - 1);
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
