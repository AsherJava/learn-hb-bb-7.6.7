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
package com.jiuqi.dc.base.impl.onlinePeriod.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import javax.persistence.Column;

@DBTable(name="DC_DEFINE_ONLINEPERIOD", title="\u4e0a\u7ebf\u671f\u95f4\u5b9a\u4e49\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class OnlinePeriodDefineEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -6639616683032711170L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true)
    private Long ver;
    @DBColumn(nameInDB="ONLINEPERIOD", title="\u4e0a\u7ebf\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true)
    private String onlinePeriod;
    @DBColumn(nameInDB="ORGCOMBCODES", title="\u5355\u4f4d\u7ec4\u5408", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true)
    private String orgCombCodes;
    @DBColumn(nameInDB="MODULECODE", title="\u6a21\u5757\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true)
    private String moduleCode;
    @DBColumn(nameInDB="REMARK", title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=500)
    private String remark;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getOnlinePeriod() {
        return this.onlinePeriod;
    }

    public void setOnlinePeriod(String onlinePeriod) {
        this.onlinePeriod = onlinePeriod;
    }

    public String getOrgCombCodes() {
        return this.orgCombCodes;
    }

    public void setOrgCombCodes(String orgCombCodes) {
        this.orgCombCodes = orgCombCodes;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

