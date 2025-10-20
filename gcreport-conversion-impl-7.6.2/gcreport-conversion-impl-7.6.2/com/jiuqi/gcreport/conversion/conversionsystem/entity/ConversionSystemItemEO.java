/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.conversion.conversionsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CONV_SYSTEM_ITEM", title="\u6298\u7b97\u4f53\u7cfb\u5173\u8054\u4efb\u52a1", indexs={@DBIndex(name="IDX_GC_CONVSYSTEMITEM_COMID", columnsFields={"SCHEMETASKID", "FORMID", "INDEXID"}, type=DBIndex.TableIndexType.TABLE_INDEX_UNIQUE)})
public class ConversionSystemItemEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONV_SYSTEM_ITEM";
    @DBColumn(nameInDB="SCHEMETASKID", title="\u4efb\u52a1-\u62a5\u8868\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeTaskId;
    @DBColumn(nameInDB="FORMID", title="\u62a5\u8868\u8868\u5355ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String formId;
    @DBColumn(nameInDB="INDEXID", title="\u5173\u8054\u6307\u6807ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String indexId;
    @DBColumn(nameInDB="REGIONID", title="\u5173\u8054\u8868\u5355\u533a\u57dfID", dbType=DBColumn.DBType.Varchar, length=36)
    private String regionId;
    @DBColumn(nameInDB="RATETYPECODE", title="\u6298\u7b97\u6c47\u7387\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String rateTypeCode;
    @DBColumn(nameInDB="RATEFORMULA", title="\u6c47\u7387\u516c\u5f0f", dbType=DBColumn.DBType.NVarchar, length=500)
    private String rateFormula;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createtime;
    @DBColumn(nameInDB="CREATEUSER", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.Varchar, length=36)
    private String createuser;
    @DBColumn(nameInDB="MODIFIEDTIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifiedtime;
    @DBColumn(nameInDB="MODIFIEDUSER", title="\u4fee\u6539\u4eba", dbType=DBColumn.DBType.Varchar, length=36)
    private String modifieduser;

    public String getSchemeTaskId() {
        return this.schemeTaskId;
    }

    public void setSchemeTaskId(String schemeTaskId) {
        this.schemeTaskId = schemeTaskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getIndexId() {
        return this.indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getRateTypeCode() {
        return this.rateTypeCode;
    }

    public void setRateTypeCode(String rateTypeCode) {
        this.rateTypeCode = rateTypeCode;
    }

    public String getRateFormula() {
        return this.rateFormula;
    }

    public void setRateFormula(String rateFormula) {
        this.rateFormula = rateFormula;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return this.createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Date getModifiedtime() {
        return this.modifiedtime;
    }

    public void setModifiedtime(Date modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getModifieduser() {
        return this.modifieduser;
    }

    public void setModifieduser(String modifieduser) {
        this.modifieduser = modifieduser;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}

