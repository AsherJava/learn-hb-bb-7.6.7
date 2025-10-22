/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO
 */
package com.jiuqi.gcreport.reportdatasync.entity;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO;
import java.util.Date;

@DBTable(name="GC_DATASYNC_UPLOAD_SCHEME", title="\u56fd\u8d44\u59d4\u6570\u636e\u4e0a\u4f20\u65b9\u6848\u8868")
public class ReportDataSyncUploadSchemeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_DATASYNC_UPLOAD_SCHEME";
    @DBColumn(title="\u6570\u636e\u4e0a\u4f20\u65b9\u6848\u6807\u9898", dbType=DBColumn.DBType.Varchar, isRequired=true, length=60)
    private String title;
    @DBColumn(title="\u7236\u7ea7id", dbType=DBColumn.DBType.Varchar, length=60)
    private String parentId;
    @DBColumn(title="\u5206\u7ec4\u63cf\u8ff0", dbType=DBColumn.DBType.Varchar)
    private String groupDescribe;
    @DBColumn(title="\u662f\u5426\u662f\u65b9\u6848\u5206\u7ec4", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer schemeGroup;
    @DBColumn(title="\u62a5\u8868\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar)
    private String dataScheme;
    @DBColumn(title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.Varchar)
    private String periodType;
    @DBColumn(title="\u6307\u5b9a\u65f6\u671f", dbType=DBColumn.DBType.Varchar)
    private String periodStr;
    @DBColumn(title="\u65f6\u671f\u6807\u9898", dbType=DBColumn.DBType.Varchar)
    private String periodStrTitle;
    @DBColumn(title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.Varchar)
    private String adjustCode;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date modifyTime;
    @DBColumn(title="\u5355\u4f4d\u8303\u56f4", nameInDB="unitCodes", dbType=DBColumn.DBType.Text)
    private String unitCodes;
    @DBColumn(title="\u662f\u5426\u540c\u6b65\u5355\u4f4d", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer syncUnit;
    @DBColumn(title="\u540c\u6b65\u6570\u636e\u53c2\u6570", nameInDB="CONTENT", dbType=DBColumn.DBType.NText)
    private String content;
    @DBColumn(title="\u5e94\u7528\u6a21\u5f0f(\u56fd\u8d44\u59d4:true;\u4f01\u4e1a\u7aef:false)", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer applicationMode;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getContent() {
        if (StringUtils.isEmpty((String)this.content)) {
            this.content = JsonUtils.writeValueAsString((Object)new ReportDataSyncUploadSchemeVO());
        }
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getApplicationMode() {
        return this.applicationMode;
    }

    public void setApplicationMode(Integer applicationMode) {
        this.applicationMode = applicationMode;
    }

    public String getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(String unitCodes) {
        this.unitCodes = unitCodes;
    }

    public Integer getSyncUnit() {
        return this.syncUnit;
    }

    public void setSyncUnit(Integer syncUnit) {
        this.syncUnit = syncUnit;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }

    public String getPeriodStrTitle() {
        return this.periodStrTitle;
    }

    public void setPeriodStrTitle(String periodStrTitle) {
        this.periodStrTitle = periodStrTitle;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getGroupDescribe() {
        return this.groupDescribe;
    }

    public void setGroupDescribe(String groupDescribe) {
        this.groupDescribe = groupDescribe;
    }

    public Integer getSchemeGroup() {
        return this.schemeGroup == null ? 0 : this.schemeGroup;
    }

    public void setSchemeGroup(Integer schemeGroup) {
        this.schemeGroup = schemeGroup;
    }
}

