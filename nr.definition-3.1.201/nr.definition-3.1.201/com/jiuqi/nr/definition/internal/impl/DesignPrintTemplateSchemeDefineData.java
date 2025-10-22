/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import java.util.Date;

public class DesignPrintTemplateSchemeDefineData
implements DesignPrintTemplateSchemeDefine {
    private DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine;
    private DesignBigDataService designBigDataService;

    public DesignPrintTemplateSchemeDefineData(DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine, DesignBigDataService designBigDataService) {
        this.designPrintTemplateSchemeDefine = designPrintTemplateSchemeDefine;
        this.designBigDataService = designBigDataService;
    }

    public DesignPrintTemplateSchemeDefine getDesignPrintTemplateSchemeDefine() {
        return this.designPrintTemplateSchemeDefine;
    }

    @Override
    public byte[] getCommonAttribute() {
        if (null != this.designPrintTemplateSchemeDefine.getCommonAttribute()) {
            return this.designPrintTemplateSchemeDefine.getCommonAttribute();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "PRINTS_ATTR_DATA");
            if (null == bigData) {
                return null;
            }
            this.designPrintTemplateSchemeDefine.setCommonAttribute(bigData);
            return this.designPrintTemplateSchemeDefine.getCommonAttribute();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public byte[] getGatherCoverData() {
        if (null != this.designPrintTemplateSchemeDefine.getGatherCoverData()) {
            return this.designPrintTemplateSchemeDefine.getGatherCoverData();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), "PRINTS_GATTHER_DATA");
            if (null == bigData) {
                return null;
            }
            this.designPrintTemplateSchemeDefine.setGatherCoverData(bigData);
            return this.designPrintTemplateSchemeDefine.getGatherCoverData();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_SCHEME_QUERY, (Throwable)e);
        }
    }

    @Override
    public void setKey(String key) {
        this.designPrintTemplateSchemeDefine.setKey(key);
    }

    @Override
    public void setOrder(String order) {
        this.designPrintTemplateSchemeDefine.setOrder(order);
    }

    @Override
    public void setVersion(String version) {
        this.designPrintTemplateSchemeDefine.setVersion(version);
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.designPrintTemplateSchemeDefine.setOwnerLevelAndId(ownerLevelAndId);
    }

    @Override
    public void setTitle(String title) {
        this.designPrintTemplateSchemeDefine.setTitle(title);
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.designPrintTemplateSchemeDefine.setUpdateTime(updateTime);
    }

    @Override
    public void setDescription(String desc) {
        this.designPrintTemplateSchemeDefine.setDescription(desc);
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.designPrintTemplateSchemeDefine.setTaskKey(taskKey);
    }

    @Override
    public void setFormSchemeKey(String formSchemeKey) {
        this.designPrintTemplateSchemeDefine.setFormSchemeKey(formSchemeKey);
    }

    @Override
    public void setCommonAttribute(byte[] commonAttribute) {
        this.designPrintTemplateSchemeDefine.setCommonAttribute(commonAttribute);
    }

    @Override
    public void setGatherCoverData(byte[] gatherCoverData) {
        this.designPrintTemplateSchemeDefine.setGatherCoverData(gatherCoverData);
    }

    @Override
    public String getDescription() {
        return this.designPrintTemplateSchemeDefine.getDescription();
    }

    @Override
    public String getTaskKey() {
        return this.designPrintTemplateSchemeDefine.getTaskKey();
    }

    @Override
    public String getFormSchemeKey() {
        return this.designPrintTemplateSchemeDefine.getFormSchemeKey();
    }

    public Date getUpdateTime() {
        return this.designPrintTemplateSchemeDefine.getUpdateTime();
    }

    public String getKey() {
        return this.designPrintTemplateSchemeDefine.getKey();
    }

    public String getTitle() {
        return this.designPrintTemplateSchemeDefine.getTitle();
    }

    public String getOrder() {
        return this.designPrintTemplateSchemeDefine.getOrder();
    }

    public String getVersion() {
        return this.designPrintTemplateSchemeDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.designPrintTemplateSchemeDefine.getOwnerLevelAndId();
    }
}

