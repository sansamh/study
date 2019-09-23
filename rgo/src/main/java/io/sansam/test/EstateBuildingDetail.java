package io.sansam.test;

import io.sansam.anno.DateStringPattern;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * <p>
 * EstateBuildingDetail
 * </p>
 *
 * @author houcb
 * @since 2019-07-30 10:29
 */
public class EstateBuildingDetail implements Serializable {

    /**
     * 楼栋id
     */
    private String buildingId;

    /**
     * 楼栋名
     */
    private String buildingName;

    /**
     * 房间长名称
     */
    private String houseLongName;

    /**
     * 销控状态(待售/认筹/认购)
     */
    private String houseSalesControlStatus;

    /**
     * 是否可收取预收款
     */
    private String isEnable;

    /**
     * 已预收客户
     */
    @DateStringPattern
    private String customerName;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getHouseLongName() {
        return houseLongName;
    }

    public void setHouseLongName(String houseLongName) {
        this.houseLongName = houseLongName;
    }

    public String getHouseSalesControlStatus() {
        return houseSalesControlStatus;
    }

    public void setHouseSalesControlStatus(String houseSalesControlStatus) {
        this.houseSalesControlStatus = houseSalesControlStatus;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EstateBuildingDetail.class.getSimpleName() + "[", "]")
                .add("buildingId='" + buildingId + "'")
                .add("buildingName='" + buildingName + "'")
                .add("houseLongName='" + houseLongName + "'")
                .add("houseSalesControlStatus='" + houseSalesControlStatus + "'")
                .add("isEnable='" + isEnable + "'")
                .add("customerName='" + customerName + "'")
                .toString();
    }
}
