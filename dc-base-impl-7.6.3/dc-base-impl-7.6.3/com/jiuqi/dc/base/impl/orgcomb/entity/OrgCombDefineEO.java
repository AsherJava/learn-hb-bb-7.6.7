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
package com.jiuqi.dc.base.impl.orgcomb.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import javax.persistence.Column;

@DBTable(name="DC_DEFINE_ORGCOMB", title="\u5355\u4f4d\u7ec4\u5408\u5b9a\u4e49\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class OrgCombDefineEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 1566345373498373278L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true)
    private Long ver;
    @DBColumn(nameInDB="CODE", title="\u5355\u4f4d\u7ec4\u5408\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true)
    private String code;
    @DBColumn(nameInDB="NAME", title="\u5355\u4f4d\u7ec4\u5408\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true)
    private String name;
    @DBColumn(nameInDB="REMARK", title="\u5907\u6ce8", dbType=DBColumn.DBType.NVarchar, length=500)
    private String remark;
    @DBColumn(nameInDB="GROUPID", title="\u5206\u7ec4ID", dbType=DBColumn.DBType.NVarchar, length=100)
    private String groupId;
    @DBColumn(nameInDB="SORTORDER", title="\u6392\u5e8f\u53f7", dbType=DBColumn.DBType.Int, length=10, isRequired=true)
    private String sortOrder;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}

