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

@DBTable(name="DC_DEFINE_ORGCOMBITEM", title="\u5355\u4f4d\u7ec4\u5408\u660e\u7ec6\u5b9a\u4e49\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class OrgCombItemDefineEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -2602969151769160273L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true)
    private Long ver;
    @DBColumn(nameInDB="SCHEMEID", title="\u8282\u70b9ID", dbType=DBColumn.DBType.NVarchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(nameInDB="ORGCODE", title="\u5355\u4f4d\u8303\u56f4", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true)
    private String orgCode;
    @DBColumn(nameInDB="EXCLUDEORGCODES", title="\u6392\u9664\u8303\u56f4", dbType=DBColumn.DBType.NVarchar, length=500)
    private String excludeOrgCodes;
    @DBColumn(nameInDB="NODETYPE", title="\u8282\u70b9\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=100)
    private String nodeType;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getExcludeOrgCodes() {
        return this.excludeOrgCodes;
    }

    public void setExcludeOrgCodes(String excludeOrgCodes) {
        this.excludeOrgCodes = excludeOrgCodes;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }
}

