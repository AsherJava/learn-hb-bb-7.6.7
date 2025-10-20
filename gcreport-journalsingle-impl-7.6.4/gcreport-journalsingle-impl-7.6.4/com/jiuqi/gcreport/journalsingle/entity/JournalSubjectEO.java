/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.journalsingle.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.journalsingle.utils.GcNpUtil;
import java.util.Date;

@DBTable(name="GC_JOURNAL_SUBJECT", title="\u65e5\u8bb0\u8d26\u8c03\u6574\u9879\u76ee")
@DBIndex(name="INDEX_JOURNAL_SUBJECT_1", type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE, columnsFields={"CODE", "JRELATESCHEMEID"})
public class JournalSubjectEO
extends DefaultTableEntity {
    public static final String TABLE_NAME = "GC_JOURNAL_SUBJECT";
    @DBColumn(nameInDB="CODE", title="\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=32, isRequired=true)
    private String code;
    @DBColumn(nameInDB="TITLE", title="\u542b\u4e49", dbType=DBColumn.DBType.NVarchar, length=64, isRequired=true)
    private String title;
    @DBColumn(nameInDB="PARENTID", title="\u7236\u8282\u70b9,\u6839\u8282\u70b9\u4e3ajRelateSchemeId", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String parentId;
    @DBColumn(nameInDB="PARENTS", title="\u6240\u6709\u4e0a\u7ea7", dbType=DBColumn.DBType.NVarchar, length=500, isRequired=true)
    private String parents;
    @DBColumn(nameInDB="ORIENT", title="\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer orient;
    @DBColumn(nameInDB="JRELATESCHEMEID", title="GC_JournalRelateScheme\u7684\u4e3b\u952e", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String jRelateSchemeId;
    @DBColumn(length=36, nameInDB="BEFOREZBID", title="\u8c03\u6574\u524d\u6307\u6807id", dbType=DBColumn.DBType.NVarchar)
    private String beforeZbId;
    @DBColumn(length=32, nameInDB="BEFOREZBCODE", title="\u8c03\u6574\u524d\u6307\u6807code,table[code]\u5f62\u5f0f", dbType=DBColumn.DBType.NVarchar)
    private String beforeZbCode;
    @DBColumn(length=36, nameInDB="AFTERZBID", title="\u8c03\u6574\u540e\u6307\u6807id", dbType=DBColumn.DBType.NVarchar)
    private String afterZbId;
    @DBColumn(length=32, nameInDB="AFTERZBCODE", title="\u8c03\u6574\u540e\u6307\u6807code,table[code]\u5f62\u5f0f", dbType=DBColumn.DBType.NVarchar)
    private String afterZbCode;
    @DBColumn(nameInDB="NEEDSHOW", title="\u662f\u5426\u663e\u793a", dbType=DBColumn.DBType.Numeric, precision=1, scale=0, isRequired=true)
    private Integer needShow;
    @DBColumn(nameInDB="SORTORDER", title="\u6392\u5e8f", dbType=DBColumn.DBType.NVarchar, length=20)
    private String sortOrder;
    @DBColumn(nameInDB="CREATETIME", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Date, isRequired=true)
    private Date createTime;
    @DBColumn(nameInDB="MODIFYTIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date modifyTime;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getjRelateSchemeId() {
        return this.jRelateSchemeId;
    }

    public void setjRelateSchemeId(String jRelateSchemeId) {
        this.jRelateSchemeId = jRelateSchemeId;
    }

    public String getBeforeZbId() {
        return this.beforeZbId;
    }

    public void setBeforeZbId(String beforeZbId) {
        this.beforeZbId = beforeZbId;
    }

    public String getAfterZbId() {
        return this.afterZbId;
    }

    public void setAfterZbId(String afterZbId) {
        this.afterZbId = afterZbId;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getBeforeZbCode() {
        return this.beforeZbCode;
    }

    public void setBeforeZbCode(String beforeZbCode) {
        this.beforeZbCode = beforeZbCode;
    }

    public String getAfterZbCode() {
        return this.afterZbCode;
    }

    public void setAfterZbCode(String afterZbCode) {
        this.afterZbCode = afterZbCode;
    }

    public Integer getNeedShow() {
        return this.needShow;
    }

    public void setNeedShow(Integer needShow) {
        this.needShow = needShow;
    }

    public void repairBeforeZbInfo() {
        String fieldKey = GcNpUtil.getInstance().getFieldKey(this.beforeZbCode);
        if (null == fieldKey) {
            this.beforeZbCode = "";
            this.beforeZbId = "";
        } else {
            this.beforeZbId = fieldKey;
        }
    }

    public void repairAfterZbInfo() {
        String fieldKey = GcNpUtil.getInstance().getFieldKey(this.afterZbCode);
        if (null == fieldKey) {
            this.afterZbCode = "";
            this.afterZbId = "";
        } else {
            this.afterZbId = fieldKey;
        }
    }
}

