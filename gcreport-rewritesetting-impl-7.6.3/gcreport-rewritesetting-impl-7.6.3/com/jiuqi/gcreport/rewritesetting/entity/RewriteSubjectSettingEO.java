/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.rewritesetting.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_REWRITE_SETTING_SUBJECT", title="\u56de\u5199\u8bbe\u7f6e\u79d1\u76ee\u8bbe\u7f6e\u8868")
public class RewriteSubjectSettingEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_REWRITE_SETTING_SUBJECT";
    @DBColumn(title="\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String schemeId;
    @DBColumn(title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, length=500)
    private String originSubjectCodes;
    @DBColumn(title="\u8f6c\u6362\u540e\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar)
    private String convertedSubjectCode;
    @DBColumn(title="\u62b5\u9500\u91d1\u989d\u56de\u5199\u5b57\u6bb5", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String rewriteFieldCode;
    @DBColumn(title="\u62b5\u9500\u91d1\u989d\u56de\u5199\u6761\u4ef6", dbType=DBColumn.DBType.NVarchar, length=500)
    private String rewriteFilter;
    @DBColumn(title="\u63cf\u8ff0", dbType=DBColumn.DBType.NVarchar, length=200)
    private String memo;

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

    public String getOriginSubjectCodes() {
        return this.originSubjectCodes;
    }

    public void setOriginSubjectCodes(String originSubjectCodes) {
        this.originSubjectCodes = originSubjectCodes;
    }

    public String getConvertedSubjectCode() {
        return this.convertedSubjectCode;
    }

    public void setConvertedSubjectCode(String convertedSubjectCode) {
        this.convertedSubjectCode = convertedSubjectCode;
    }

    public String getRewriteFieldCode() {
        return this.rewriteFieldCode;
    }

    public void setRewriteFieldCode(String rewriteFieldCode) {
        this.rewriteFieldCode = rewriteFieldCode;
    }

    public String getRewriteFilter() {
        return this.rewriteFilter;
    }

    public void setRewriteFilter(String rewriteFilter) {
        this.rewriteFilter = rewriteFilter;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

