package com.english.model.request;

import java.util.List;

public class QueryCondition {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private Integer offset;

    private List<String> createTime;

    public List<String> getCreateTime() {
        return createTime;
    }

    public void setCreateTime(List<String> createTime) {
        this.createTime = createTime;
    }

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
}
