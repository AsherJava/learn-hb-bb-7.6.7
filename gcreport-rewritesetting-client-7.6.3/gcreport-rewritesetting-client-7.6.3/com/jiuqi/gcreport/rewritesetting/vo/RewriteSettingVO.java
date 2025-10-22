/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO
 */
package com.jiuqi.gcreport.rewritesetting.vo;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldMappingVO;
import java.util.List;

public class RewriteSettingVO {
    private String id;
    private String taskId;
    private String schemeId;
    private List<String> subjectCodes;
    private String subjectTitles;
    private List<GcBaseDataDO> subjectVos;
    private String insideReginonKey;
    private String insideRegionTitle;
    private String insideFormKey;
    private String insideTableName;
    private String outsideReginonKey;
    private String outsideRegionTitle;
    private String outsideFormKey;
    private String outsideTableName;
    private String rewSetGroupId;
    private String masterColumnCodes;
    private String masterColumnTitles;
    private List<RewriteFieldMappingVO> fieldMapping;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public String getInsideReginonKey() {
        return this.insideReginonKey;
    }

    public void setInsideReginonKey(String insideReginonKey) {
        this.insideReginonKey = insideReginonKey;
    }

    public String getInsideFormKey() {
        return this.insideFormKey;
    }

    public void setInsideFormKey(String insideFormKey) {
        this.insideFormKey = insideFormKey;
    }

    public String getInsideTableName() {
        return this.insideTableName;
    }

    public void setInsideTableName(String insideTableName) {
        this.insideTableName = insideTableName;
    }

    public String getOutsideReginonKey() {
        return this.outsideReginonKey;
    }

    public void setOutsideReginonKey(String outsideReginonKey) {
        this.outsideReginonKey = outsideReginonKey;
    }

    public String getOutsideFormKey() {
        return this.outsideFormKey;
    }

    public void setOutsideFormKey(String outsideFormKey) {
        this.outsideFormKey = outsideFormKey;
    }

    public String getOutsideTableName() {
        return this.outsideTableName;
    }

    public void setOutsideTableName(String outsideTableName) {
        this.outsideTableName = outsideTableName;
    }

    public String getRewSetGroupId() {
        return this.rewSetGroupId;
    }

    public void setRewSetGroupId(String rewSetGroupId) {
        this.rewSetGroupId = rewSetGroupId;
    }

    public List<GcBaseDataDO> getSubjectVos() {
        return this.subjectVos;
    }

    public void setSubjectVos(List<GcBaseDataDO> subjectVos) {
        this.subjectVos = subjectVos;
    }

    public String getSubjectTitles() {
        return this.subjectTitles;
    }

    public void setSubjectTitles(String subjectTitles) {
        this.subjectTitles = subjectTitles;
    }

    public String getInsideRegionTitle() {
        return this.insideRegionTitle;
    }

    public void setInsideRegionTitle(String insideRegionTitle) {
        this.insideRegionTitle = insideRegionTitle;
    }

    public String getOutsideRegionTitle() {
        return this.outsideRegionTitle;
    }

    public void setOutsideRegionTitle(String outsideRegionTitle) {
        this.outsideRegionTitle = outsideRegionTitle;
    }

    public String getMasterColumnCodes() {
        return this.masterColumnCodes;
    }

    public void setMasterColumnCodes(String masterColumnCodes) {
        this.masterColumnCodes = masterColumnCodes;
    }

    public String getMasterColumnTitles() {
        return this.masterColumnTitles;
    }

    public void setMasterColumnTitles(String masterColumnTitles) {
        this.masterColumnTitles = masterColumnTitles;
    }

    public List<RewriteFieldMappingVO> getFieldMapping() {
        return this.fieldMapping;
    }

    public void setFieldMapping(List<RewriteFieldMappingVO> fieldMapping) {
        this.fieldMapping = fieldMapping;
    }
}

