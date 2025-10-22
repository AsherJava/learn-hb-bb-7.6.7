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
import com.jiuqi.nr.data.engine.gather.GatherTempTableHandler;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class NotGatherTempTableMeta
extends BaseTempTableDefine {
    public static final String NOT_GATHER_TEMP = "NR_NGTT";

    public boolean isDynamic() {
        return false;
    }

    public String getType() {
        return NOT_GATHER_TEMP;
    }

    public String getTypeTitle() {
        return "\u4e0d\u6c47\u603b\u5355\u4f4d\u4fe1\u606f\u4e34\u65f6\u8868";
    }

    public List<LogicField> getLogicFields() {
        ArrayList<LogicField> res = new ArrayList<LogicField>();
        GatherAssistantTable.getNoGatherField(res);
        List<LogicField> dimCol = GatherTempTableHandler.getDimCols();
        res.addAll(dimCol);
        return res;
    }

    public List<String> getPrimaryKeyFields() {
        ArrayList<String> primaryKeys = new ArrayList<String>();
        primaryKeys.add("NT_ID");
        primaryKeys.add("NT_FID");
        primaryKeys.add("EXECUTION_ID");
        return primaryKeys;
    }

    public String getModuleName() {
        return "\u6c47\u603b\u4e34\u65f6\u8868\u6a21\u5757";
    }

    public String getDesc() {
        return "\u4e0d\u6c47\u603b\u5355\u4f4d\u4fe1\u606f\u4e34\u65f6\u8868";
    }
}

