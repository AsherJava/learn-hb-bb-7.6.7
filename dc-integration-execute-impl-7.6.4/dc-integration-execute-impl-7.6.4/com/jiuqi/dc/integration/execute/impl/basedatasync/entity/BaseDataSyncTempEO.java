/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex$TableIndexType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.entity;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.env.Environment;

@DBTable(name="DC_TEMP_BASEDATASYNC", title="\u57fa\u7840\u6570\u636e\u540c\u6b65\u4e34\u65f6\u8868", kind=TableModelKind.SYSTEM_EXTEND, indexs={@DBIndex(name="IDX_TEMP_BASEDATASYNC_COMB", type=DBIndex.TableIndexType.TABLE_INDEX_NORMAL, columnsFields={"BASEDATACODE", "CODE"})}, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"), dataSource="jiuqi.gcreport.mdd.datasource")
public class BaseDataSyncTempEO
extends DcDefaultTableEntity
implements ITableExtend {
    private static final long serialVersionUID = -4956038298562386244L;
    @DBColumn(nameInDB="CODE", title="\u57fa\u7840\u6570\u636e\u503c", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=1)
    private String code;
    @DBColumn(nameInDB="NAME", title="\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=60, order=2)
    private String name;
    @DBColumn(nameInDB="BASEDATACODE", title="\u57fa\u7840\u6570\u636eCODE", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=3)
    private String baseDataCode;

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

    public String getBaseDataCode() {
        return this.baseDataCode;
    }

    public void setBaseDataCode(String baseDataCode) {
        this.baseDataCode = baseDataCode;
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        ArrayList fieldVList = CollectionUtils.newArrayList();
        Environment environment = (Environment)SpringContextUtils.getBean(Environment.class);
        int filedNum = Integer.parseInt(environment.getProperty("jiuqi.gcreport.basedatasync.filednum", "20"));
        for (int i = 1; i <= filedNum; ++i) {
            DefinitionFieldV fieldV = new DefinitionFieldV();
            fieldV.setKey(UUIDUtils.newUUIDStr());
            fieldV.setNullable(true);
            fieldV.setCode("EXT_STR" + i);
            fieldV.setTitle("\u6269\u5c55\u5b57\u6bb5" + i);
            fieldV.setEntityFieldName("EXT_STR" + i);
            fieldV.setFieldName("EXT_STR" + i);
            fieldV.setFieldValueType(DBColumn.DBType.NVarchar.getType());
            fieldV.setDbType(DBColumn.DBType.NVarchar);
            fieldV.setSize(60);
            fieldV.setDescription("\u6269\u5c55\u5b57\u6bb5" + i);
            fieldVList.add(fieldV);
        }
        return fieldVList;
    }
}

