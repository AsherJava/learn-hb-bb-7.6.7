/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.carryover.vo;

import com.jiuqi.gcreport.carryover.vo.CarryOverConfigOptionBaseVO;
import java.util.Date;

public class CarryOverConfigVO
extends CarryOverConfigOptionBaseVO {
    private String id;
    private String typeTitle;
    private String creator;
    private Date createTime;
    private Double ordinal;
    private String parentId;
    private Boolean leafFlag;
    private String optionData;
    private String pluginName;
    private String boundSystemTitle;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeTitle() {
        return this.typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public String getOptionData() {
        return this.optionData;
    }

    public void setOptionData(String optionData) {
        this.optionData = optionData;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getBoundSystemTitle() {
        return this.boundSystemTitle;
    }

    public void setBoundSystemTitle(String boundSystemTitle) {
        this.boundSystemTitle = boundSystemTitle;
    }
}

