/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.system.options.adaptation.option;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;

public abstract class AbstractDropDownSingleOption
implements ISystemOptionItem {
    public SystemOptionConst.EditMode getEditMode() {
        return SystemOptionConst.EditMode.DROP_DOWN_SINGLE;
    }

    public String getVerifyRegex() {
        return null;
    }

    public String getVerifyRegexMessage() {
        return null;
    }
}

