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
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCheckBoxOption
implements ISystemOptionItem {
    public SystemOptionConst.EditMode getEditMode() {
        return SystemOptionConst.EditMode.CHECK_BOX;
    }

    public String getDefaultValue() {
        return "[\"b\"]";
    }

    public List<ISystemOptionalValue> getOptionalValues() {
        ArrayList<ISystemOptionalValue> values = new ArrayList<ISystemOptionalValue>();
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u9009\u9879A";
            }

            public String getValue() {
                return "a";
            }
        });
        values.add(new ISystemOptionalValue(){

            public String getTitle() {
                return "\u9009\u9879B";
            }

            public String getValue() {
                return "b";
            }
        });
        return values;
    }
}

