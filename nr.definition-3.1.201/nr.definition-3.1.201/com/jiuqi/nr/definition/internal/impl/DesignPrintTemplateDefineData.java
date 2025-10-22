/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.nr.definition.common.BigDataConsts;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import java.util.Date;

public class DesignPrintTemplateDefineData
implements DesignPrintTemplateDefine {
    private DesignPrintTemplateDefine designPrintTemplateDefine;
    private DesignBigDataService designBigDataService;

    public DesignPrintTemplateDefineData(DesignPrintTemplateDefine designPrintTemplateDefine, DesignBigDataService designBigDataService) {
        this.designPrintTemplateDefine = designPrintTemplateDefine;
        this.designBigDataService = designBigDataService;
    }

    public DesignPrintTemplateDefine getDesignPrintTemplateDefine() {
        return this.designPrintTemplateDefine;
    }

    @Override
    public byte[] getTemplateData() {
        if (null != this.designPrintTemplateDefine.getTemplateData()) {
            return this.designPrintTemplateDefine.getTemplateData();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), BigDataConsts.BIG_PRINT_TEMPLATE_DATA);
            if (null == bigData) {
                return null;
            }
            this.designPrintTemplateDefine.setTemplateData(bigData);
            return this.designPrintTemplateDefine.getTemplateData();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY, (Throwable)e);
        }
    }

    @Override
    public byte[] getLabelData() {
        if (null != this.designPrintTemplateDefine.getLabelData()) {
            return this.designPrintTemplateDefine.getLabelData();
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.getKey(), BigDataConsts.BIG_PRINT_LABLE_DATA);
            if (null == bigData) {
                return null;
            }
            this.designPrintTemplateDefine.setLabelData(bigData);
            return this.designPrintTemplateDefine.getLabelData();
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.PRINT_TEMPLATE_QUERY, (Throwable)e);
        }
    }

    @Override
    public String getComTemCode() {
        return this.designPrintTemplateDefine.getComTemCode();
    }

    @Override
    public void setKey(String key) {
        this.designPrintTemplateDefine.setKey(key);
    }

    @Override
    public void setOrder(String order) {
        this.designPrintTemplateDefine.setOrder(order);
    }

    @Override
    public void setVersion(String version) {
        this.designPrintTemplateDefine.setVersion(version);
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.designPrintTemplateDefine.setOwnerLevelAndId(ownerLevelAndId);
    }

    @Override
    public void setTitle(String title) {
        this.designPrintTemplateDefine.setTitle(title);
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.designPrintTemplateDefine.setUpdateTime(updateTime);
    }

    @Override
    public void setDescription(String desc) {
        this.designPrintTemplateDefine.setDescription(desc);
    }

    @Override
    public void setPrintSchemeKey(String printSchemeKey) {
        this.designPrintTemplateDefine.setPrintSchemeKey(printSchemeKey);
    }

    @Override
    public void setFormKey(String fromKey) {
        this.designPrintTemplateDefine.setFormKey(fromKey);
    }

    @Override
    public void setAutoRefreshForm(boolean autoRefreshForm) {
        this.designPrintTemplateDefine.setAutoRefreshForm(autoRefreshForm);
    }

    @Override
    public void setFormUpdateTime(Date updateTime) {
        this.designPrintTemplateDefine.setFormUpdateTime(updateTime);
    }

    @Override
    public void setTemplateData(byte[] templateData) {
        this.designPrintTemplateDefine.setTemplateData(templateData);
    }

    @Override
    public void setLabelData(byte[] labelData) {
        this.designPrintTemplateDefine.setLabelData(labelData);
    }

    @Override
    public void setComTemCode(String comTemCode) {
        this.designPrintTemplateDefine.setComTemCode(comTemCode);
    }

    @Override
    public String getDescription() {
        return this.designPrintTemplateDefine.getDescription();
    }

    @Override
    public String getPrintSchemeKey() {
        return this.designPrintTemplateDefine.getPrintSchemeKey();
    }

    @Override
    public String getFormKey() {
        return this.designPrintTemplateDefine.getFormKey();
    }

    @Override
    public boolean isAutoRefreshForm() {
        return this.designPrintTemplateDefine.isAutoRefreshForm();
    }

    @Override
    public Date getFormUpdateTime() {
        return this.designPrintTemplateDefine.getFormUpdateTime();
    }

    public Date getUpdateTime() {
        return this.designPrintTemplateDefine.getUpdateTime();
    }

    public String getKey() {
        return this.designPrintTemplateDefine.getKey();
    }

    public String getTitle() {
        return this.designPrintTemplateDefine.getTitle();
    }

    public String getOrder() {
        return this.designPrintTemplateDefine.getOrder();
    }

    public String getVersion() {
        return this.designPrintTemplateDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.designPrintTemplateDefine.getOwnerLevelAndId();
    }
}

