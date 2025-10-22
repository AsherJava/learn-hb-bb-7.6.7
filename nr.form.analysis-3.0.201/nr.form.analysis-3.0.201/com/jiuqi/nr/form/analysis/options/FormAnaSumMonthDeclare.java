/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.form.analysis.options;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormAnaSumMonthDeclare
implements ISystemOptionDeclare {
    @Autowired
    private SystemOptionOperator systemOptionOperator;

    public String getId() {
        return "form-ana-sum-month-options-id";
    }

    public String getTitle() {
        return "\u5d4c\u5165\u5f0f\u5206\u6790\u8868";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new ISystemOptionItem(){

            public String getId() {
                return "FORMULA_VAR_SUM_MONTH";
            }

            public String getTitle() {
                return "\u516c\u5f0f\u53d8\u91cf\u2014\u2014\u6c47\u603b\u6708\u4efd";
            }

            public String getDefaultValue() {
                return "2020-07-01";
            }

            public SystemOptionConst.EditMode getEditMode() {
                return SystemOptionConst.EditMode.INPUT;
            }
        });
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    public int getOrdinal() {
        return 6;
    }
}

