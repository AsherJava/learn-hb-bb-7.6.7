/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 */
package com.jiuqi.nr.common.temptable.base;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.common.BaseDynamicTypeEnum;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TwentyColumnTempTable
extends BaseTempTableDefine {
    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public String getType() {
        return BaseDynamicTypeEnum.TWENTY_COLUMN.getType();
    }

    @Override
    public String getTempTableNameRule() {
        return "T_20C_%";
    }

    @Override
    public String getTypeTitle() {
        return "20\u5217\u57fa\u7840\u4e34\u65f6\u8868";
    }

    public List<LogicField> getLogicFields() {
        return Collections.emptyList();
    }

    public List<String> getPrimaryKeyFields() {
        return Collections.emptyList();
    }

    public String getModuleName() {
        return "nr.common";
    }

    public String getDesc() {
        return "20\u5217\u57fa\u7840\u4e34\u65f6\u8868";
    }
}

