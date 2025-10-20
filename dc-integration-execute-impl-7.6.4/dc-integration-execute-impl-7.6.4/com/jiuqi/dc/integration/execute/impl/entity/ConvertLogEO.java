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
 */
package com.jiuqi.dc.integration.execute.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;

@DBTable(name="DC_LOG_DATACONVERT", title="\u6570\u636e\u6574\u5408\u65e5\u5fd7\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), dataSource="jiuqi.gcreport.mdd.datasource")
public class ConvertLogEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 7207681596441414811L;
    @DBColumn(nameInDB="RUNNERID", title="\u4e3b\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, order=1)
    private String runnerId;
    @DBColumn(nameInDB="DATASCHEMECODE", title="\u6570\u636e\u6620\u5c04\u65b9\u6848CODE", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=2)
    private String dataSchemeCode;
    @DBColumn(nameInDB="SCHEMETYPE", title="\u6570\u636e\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=3)
    private String schemeType;
    @DBColumn(nameInDB="DATANAME", title="\u6570\u636e\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=4)
    private String dataName;
    @DBColumn(nameInDB="EXECUTEPARAMS", title="\u6267\u884c\u53c2\u6570\u5185\u5bb9", dbType=DBColumn.DBType.NVarchar, length=1000, order=5)
    private String executeParams;
    @DBColumn(nameInDB="USERNAME", title="\u6267\u884c\u7528\u6237", dbType=DBColumn.DBType.NVarchar, length=20, order=6)
    private String userName;
    @DBColumn(nameInDB="EXECUTESTATE", title="\u6267\u884c\u72b6\u6001", dbType=DBColumn.DBType.Int, length=1, isRequired=true, defaultValue="1", order=7)
    private String executeState;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, order=8)
    private String createTime;
    @DBColumn(nameInDB="MESSAGE", title="\u6267\u884c\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=1000, order=9)
    private String message;

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getSchemeType() {
        return this.schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getDataName() {
        return this.dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getExecuteParams() {
        return this.executeParams;
    }

    public void setExecuteParams(String executeParams) {
        this.executeParams = executeParams;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getExecuteState() {
        return this.executeState;
    }

    public void setExecuteState(String executeState) {
        this.executeState = executeState;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

