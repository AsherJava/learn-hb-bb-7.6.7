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
package com.jiuqi.dc.mappingscheme.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;

@DBTable(name="DC_SCHEME_DATAMAPPINGOPTION", title="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7ba1\u63a7\u9009\u9879\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class DataMappingOptionEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = 1279830656828262570L;
    @DBColumn(nameInDB="CODE", title="\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, length=60, isRequired=true, order=1)
    private String code;
    @DBColumn(nameInDB="DATASCHEMECODE", title="\u6570\u636e\u6620\u5c04\u65b9\u6848\u7f16\u53f7", dbType=DBColumn.DBType.Varchar, length=20, isRequired=true, order=2)
    private String dataSchemeCode;
    @DBColumn(nameInDB="OPTIONVALUE", title="\u7ba1\u63a7\u9009\u9879\u503c", dbType=DBColumn.DBType.Varchar, length=2000, order=3)
    private String optionValue;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getOptionValue() {
        return this.optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}

