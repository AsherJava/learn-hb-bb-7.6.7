/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.onekeymerge.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_MERGETASK", title="\u4e00\u952e\u5408\u5e76\u4efb\u52a1", inStorage=true, indexs={@DBIndex(name="IDX_GC_MERGETASK_AFTERTASKID", columnsFields={"afterTaskId"}), @DBIndex(name="IDX_GC_MERGETASK_GROUP_ID", columnsFields={"GROUP_ID"})})
public class MergeTaskEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_MERGETASK";
    @DBColumn(nameInDB="GROUP_ID", title="\u4efb\u52a1\u6240\u5c5e\u5206\u7ec4", dbType=DBColumn.DBType.Varchar, length=36)
    private String groupId;
    @DBColumn(nameInDB="taskCode", title="\u4efb\u52a1\u6807\u8bc6", dbType=DBColumn.DBType.Varchar)
    private String taskCode;
    @DBColumn(nameInDB="orgId", title="\u5408\u5e76\u5355\u4f4dID", dbType=DBColumn.DBType.Varchar, length=36)
    private String orgId;
    @DBColumn(nameInDB="orgTitle", title="\u5408\u5e76\u5355\u4f4d\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=100)
    private String orgTitle;
    @DBColumn(nameInDB="schemeId", title="\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(nameInDB="taskState", title="\u4efb\u52a1\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String taskState;
    @DBColumn(nameInDB="taskData", title="\u5355\u4e2a\u4efb\u52a1\u7684\u7ed3\u679c\u6570\u636e", dbType=DBColumn.DBType.Text)
    private String taskData;
    @DBColumn(nameInDB="createTime", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(nameInDB="startTime", title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date startTime;
    @DBColumn(nameInDB="finishTime", title="\u5b8c\u6210\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date finishTime;
    @DBColumn(nameInDB="afterTaskId", title="\u7236\u7ea7\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String afterTaskId;
    @DBColumn(nameInDB="dataTime", title="\u4efb\u52a1\u65f6\u671f", dbType=DBColumn.DBType.Varchar)
    private String dataTime;
    @DBColumn(nameInDB="dims", title="\u7ef4\u5ea6\u4fe1\u606f", dbType=DBColumn.DBType.Varchar, length=500)
    private String dims;
    @DBColumn(nameInDB="nrTaskId", title="\u62a5\u8868\u4efb\u52a1Id", dbType=DBColumn.DBType.Varchar)
    private String nrTaskId;
    @DBColumn(nameInDB="ordinal", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric)
    private Double ordinal;

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getDims() {
        return this.dims;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }

    public String getNrTaskId() {
        return this.nrTaskId;
    }

    public void setNrTaskId(String nrTaskId) {
        this.nrTaskId = nrTaskId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getTaskData() {
        return this.taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getAfterTaskId() {
        return this.afterTaskId;
    }

    public void setAfterTaskId(String afterTaskId) {
        this.afterTaskId = afterTaskId;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }
}

