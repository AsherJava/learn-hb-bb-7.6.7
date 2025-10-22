/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.reportdatasync.entity;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_PARAMSYNC_SCHEME", title="\u6570\u636e\u540c\u6b65\u5408\u5e76\u53c2\u6570", inStorage=true)
public class ReportDataParamsSyncSchemeEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_PARAMSYNC_SCHEME";
    @DBColumn(nameInDB="PARAMPACKAGETITLE", title="\u53c2\u6570\u5305\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=200)
    private String paramPackageTitle;
    @DBColumn(title="\u53c2\u6570\u66f4\u65b0\u8bf4\u660e\u6587\u6863\u9644\u4ef6ID", dbType=DBColumn.DBType.Varchar)
    private String syncDesAttachId;
    @DBColumn(title="\u53c2\u6570\u66f4\u65b0\u8bf4\u660e\u6587\u6863\u9644\u4ef6\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=200)
    private String syncDesAttachTitle;
    @DBColumn(title="\u53ea\u5305\u542b\u5408\u5e76\u53c2\u6570", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer onlyMergeParams;
    @DBColumn(nameInDB="CONTENT", title="\u540c\u6b65\u5185\u5bb9", dbType=DBColumn.DBType.NText)
    private String content;
    @DBColumn(title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric, isRequired=true)
    private Double ordinal;

    public String getParamPackageTitle() {
        return this.paramPackageTitle;
    }

    public void setParamPackageTitle(String paramPackageTitle) {
        this.paramPackageTitle = paramPackageTitle;
    }

    public String getContent() {
        if (StringUtils.isEmpty((String)this.content)) {
            this.content = JsonUtils.writeValueAsString((Object)new ReportDataSyncParams());
        }
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSyncDesAttachId() {
        return this.syncDesAttachId;
    }

    public void setSyncDesAttachId(String syncDesAttachId) {
        this.syncDesAttachId = syncDesAttachId;
    }

    public String getSyncDesAttachTitle() {
        return this.syncDesAttachTitle;
    }

    public void setSyncDesAttachTitle(String syncDesAttachTitle) {
        this.syncDesAttachTitle = syncDesAttachTitle;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getOnlyMergeParams() {
        return this.onlyMergeParams;
    }

    public void setOnlyMergeParams(Integer onlyMergeParams) {
        this.onlyMergeParams = onlyMergeParams;
    }
}

