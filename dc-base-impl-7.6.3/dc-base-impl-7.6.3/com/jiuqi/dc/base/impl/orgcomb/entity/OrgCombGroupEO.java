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

@DBTable(name="DC_DEFINE_ORGCOMBGROUP", title="\u5355\u4f4d\u7ec4\u5408\u5206\u7ec4\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class OrgCombGroupEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 5100100396898131731L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true)
    private Long ver;
    @DBColumn(nameInDB="TITLE", title="\u6807\u9898", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true)
    private String title;
    @DBColumn(nameInDB="NODETYPE", title="\u8282\u70b9\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true)
    private String nodeType;
    @DBColumn(nameInDB="SORTNUM", title="\u7b80\u79f0", dbType=DBColumn.DBType.Int, length=10)
    private String sortNum;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getSortNum() {
        return this.sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }
}

