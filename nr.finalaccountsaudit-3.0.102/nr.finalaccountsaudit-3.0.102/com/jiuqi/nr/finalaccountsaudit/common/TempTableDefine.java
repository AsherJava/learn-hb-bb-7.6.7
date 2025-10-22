/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 */
package com.jiuqi.nr.finalaccountsaudit.common;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.finalaccountsaudit.common.TmpTableUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TempTableDefine
extends BaseTempTableDefine {
    @Autowired
    private TmpTableUtils tmpTableUtils;
    private final String TYPE_TITLE = "\u7efc\u5408\u5ba1\u6838\u4e34\u65f6\u8868";

    public List<LogicField> getLogicFields() {
        return null;
    }

    public List<String> getPrimaryKeyFields() {
        return null;
    }

    public boolean isDynamic() {
        return true;
    }

    public String getType() {
        return this.tmpTableUtils.getTempTableType();
    }

    public String getTypeTitle() {
        return "\u7efc\u5408\u5ba1\u6838\u4e34\u65f6\u8868";
    }

    public String getModuleName() {
        return "\u7efc\u5408\u5ba1\u6838\u4e34\u65f6\u8868";
    }

    public String getDesc() {
        return "\u7efc\u5408\u5ba1\u6838\u4e34\u65f6\u8868";
    }
}

