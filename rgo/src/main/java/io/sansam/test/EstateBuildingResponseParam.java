package io.sansam.test;

import io.sansam.anno.ListPattern;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * <p>
 * EstateBuildingResponseParam 楼栋查询返回对象
 * </p>
 *
 * @author houcb
 * @since 2019-07-30 09:50
 */
public class EstateBuildingResponseParam implements Serializable {

    /**
     * 项目id
     */
    private String projectId;
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 分期id
     */
    private String stageId;
    /**
     * 分期名
     */
    private String stageName;

    /**
     * 楼栋详情
     */
    @ListPattern(length = "2", clz = LinkedList.class)
    private List<EstateBuildingDetail> buildingList;


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public List<EstateBuildingDetail> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<EstateBuildingDetail> buildingList) {
        this.buildingList = buildingList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EstateBuildingResponseParam.class.getSimpleName() + "[", "]")
                .add("projectId='" + projectId + "'")
                .add("projectName='" + projectName + "'")
                .add("stageId='" + stageId + "'")
                .add("stageName='" + stageName + "'")
                .add("buildingList=" + buildingList)
                .toString();
    }
}
