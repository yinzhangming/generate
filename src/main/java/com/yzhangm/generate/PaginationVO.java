package com.yzhangm.generate;

//import io.swagger.annotations.ApiModelProperty;

/**
 * 分页实体类
 *
 * @author yzhangm
 * @date 2020-06-03
 */
public class PaginationVO {

//    @ApiModelProperty(value = "当前页码,不传值默认为第1页")
    private Integer current = 1;

//    @ApiModelProperty(value = "查询多少条数据,不传值默认为只查询10条数据")
    private Integer size = 10;

//    @ApiModelProperty(value = "不传值默认为false倒序,传true为正序查询")
    private Boolean order = false;

//    @ApiModelProperty(value = "不传值默认按id列进行排序")
    private String sort = "id";

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
