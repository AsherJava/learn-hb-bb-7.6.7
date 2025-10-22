/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 */
package com.jiuqi.nr.system.options.adaptation;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseNormalOptionDeclare
implements ISystemOptionDeclare {
    @Autowired
    protected SystemOptionOperator systemOptionOperator;

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.NORMAL;
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    public int getOrdinal() {
        return 2;
    }
}

