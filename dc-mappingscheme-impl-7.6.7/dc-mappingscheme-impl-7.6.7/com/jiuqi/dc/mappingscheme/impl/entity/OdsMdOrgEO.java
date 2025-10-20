/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  javax.persistence.Column
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 */
package com.jiuqi.dc.mappingscheme.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@DBTable(name="ODS_MD_ORG", title="\u6e90\u6838\u7b97\u5355\u4f4d\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), indexs={@DBIndex(name="I_ODS_MD_DS", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"DATASCHEMECODE"}), @DBIndex(name="I_ODS_MD_C", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"CODE"})}, dataSource="jiuqi.gcreport.mdd.datasource")
public class OdsMdOrgEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 1746758693828244570L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    @DBColumn(title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, isRecid=true, isRequired=true, order=1)
    private String id;
    @DBColumn(nameInDB="DATASCHEMECODE", title="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=1)
    private String dataSchemeCode;
    @DBColumn(nameInDB="CODE", title="\u6e90\u6838\u7b97\u5355\u4f4dCODE", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=2)
    private String code;
    @DBColumn(nameInDB="NAME", title="\u6e90\u6838\u7b97\u5355\u4f4dNAME", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=3)
    private String name;
    @DBColumn(nameInDB="PARENTCODE", title="\u6e90\u6838\u7b97\u5355\u4f4d\u4e0a\u7ea7\u5355\u4f4d", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=3)
    private String parentCode;
    @DBColumn(nameInDB="PARENTS", title="\u6e90\u6838\u7b97\u5355\u4f4d\u4e0a\u7ea7\u5355\u4f4d\u8def\u5f84", dbType=DBColumn.DBType.NVarchar, length=500, isRequired=true, order=3)
    private String parents;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
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

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
    }
}

