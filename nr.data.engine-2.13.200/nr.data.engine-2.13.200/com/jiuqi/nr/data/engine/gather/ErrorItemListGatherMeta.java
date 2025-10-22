/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.data.engine.gather.GatherAssistantTable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ErrorItemListGatherMeta
extends BaseTempTableDefine {
    public static final String ERROR_ITEM_LIST_TEMP = "NR_GTLE";

    public boolean isDynamic() {
        return false;
    }

    public String getType() {
        return ERROR_ITEM_LIST_TEMP;
    }

    public String getTypeTitle() {
        return "\u51fa\u9519\u8bf4\u660e\u6c47\u603b\u4e34\u65f6\u8868";
    }

    public List<LogicField> getLogicFields() {
        ArrayList<LogicField> res = new ArrayList<LogicField>();
        GatherAssistantTable.getErrorItemListGatherFiled(res);
        return res;
    }

    public List<String> getPrimaryKeyFields() {
        ArrayList<String> primaryKeys = new ArrayList<String>();
        primaryKeys.add("CHILDREN_BIZKEY");
        primaryKeys.add("PARENT_BIEKEY");
        return primaryKeys;
    }

    public String getModuleName() {
        return "\u6c47\u603b\u4e34\u65f6\u8868\u6a21\u5757";
    }

    public String getDesc() {
        return "\u51fa\u9519\u8bf4\u660e\u6c47\u603b\u4e34\u65f6\u8868";
    }
}

