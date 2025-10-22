/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.system.options.adaptation.option;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.List;

public abstract class AbstractTrueFalseOption
implements ISystemOptionItem {
    public SystemOptionConst.EditMode getEditMode() {
        return SystemOptionConst.EditMode.TRUE_FALSE;
    }

    public String getDefaultValue() {
        return "0";
    }

    public List<ISystemOptionalValue> getOptionalValues() {
        return null;
    }

    public String getVerifyRegex() {
        return null;
    }

    public String getVerifyRegexMessage() {
        return null;
    }
}

