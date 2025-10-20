/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  javax.persistence.Column
 */
package com.jiuqi.dc.penetratebill.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import javax.persistence.Column;

@DBTable(name="DC_SCHEME_PENETRATE_BILL", title="\u8054\u67e5\u5355\u636e\u65b9\u6848\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class PenetrateSchemeEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -3786405625851398292L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true, isRequired=true, order=1)
    private Long ver;
    @DBColumn(nameInDB="SCHEMENAME", title="\u65b9\u6848\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true, order=2)
    private String schemeName;
    @DBColumn(nameInDB="SCOPETYPE", title="\u9002\u7528\u8303\u56f4\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true, order=3)
    private String scopeType;
    @DBColumn(nameInDB="SCOPEVALUE", title="\u9002\u7528\u8303\u56f4", dbType=DBColumn.DBType.Text, isRequired=true, order=4)
    private String scopeValue;
    @DBColumn(nameInDB="HANDLERCODE", title="\u7a7f\u900f\u5904\u7406\u5668\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true, order=5)
    private String handlerCode;
    @DBColumn(nameInDB="CUSTOMPARAM", title="\u81ea\u5b9a\u4e49\u53c2\u6570", dbType=DBColumn.DBType.Text, isRequired=true, order=6)
    private String customParam;
    @DBColumn(nameInDB="CREATEDATE", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, isRequired=true, order=7)
    private String createDate;
    @DBColumn(nameInDB="BILLNOFIELD", title="\u5355\u636e\u5355\u53f7\u5b57\u6bb5", dbType=DBColumn.DBType.NVarchar, length=200, order=8)
    private String billNoField;
    @DBColumn(nameInDB="OPENWAY", title="\u5355\u636e\u6253\u5f00\u65b9\u5f0f", dbType=DBColumn.DBType.NVarchar, length=11, isRequired=true, order=9)
    private String openWay;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getScopeType() {
        return this.scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getScopeValue() {
        return this.scopeValue;
    }

    public void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue;
    }

    public String getHandlerCode() {
        return this.handlerCode;
    }

    public void setHandlerCode(String handlerCode) {
        this.handlerCode = handlerCode;
    }

    public String getCustomParam() {
        return this.customParam;
    }

    public void setCustomParam(String customParam) {
        this.customParam = customParam;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBillNoField() {
        return this.billNoField;
    }

    public void setBillNoField(String billNoField) {
        this.billNoField = billNoField;
    }

    public String getOpenWay() {
        return this.openWay;
    }

    public void setOpenWay(String openWay) {
        this.openWay = openWay;
    }
}

