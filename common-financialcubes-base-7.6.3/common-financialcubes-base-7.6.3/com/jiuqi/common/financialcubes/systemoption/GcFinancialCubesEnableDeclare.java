/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionTip
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionTip
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.common.financialcubes.systemoption;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionTip;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionTip;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GcFinancialCubesEnableDeclare
implements ISystemOptionDeclare {
    @Autowired
    private SystemOptionOperator systemOptionOperator;
    @Value(value="${jiuqi.nvwa.systemoption.bde.plugin.financialcubes.enabled:false}")
    private boolean financialcubesSystemOptionAllowClose;

    public String getId() {
        return "FINANCIAL_CUBES_ENABLE";
    }

    public String getTitle() {
        return "\u591a\u7ef4\u5f00\u5173";
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }

    public String getNameSpace() {
        return "\u5408\u5e76\u62a5\u8868";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(this.getFinancialCubesEnableOption());
        return optionItems;
    }

    private ISystemOptionItem getFinancialCubesEnableOption() {
        SystemOptionItem systemOptionItem = new SystemOptionItem();
        systemOptionItem.setId("FINANCIAL_CUBES_ENABLE");
        systemOptionItem.setTitle("\u542f\u7528\u591a\u7ef4");
        systemOptionItem.setDefaultValue("0");
        systemOptionItem.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        if (!this.financialcubesSystemOptionAllowClose) {
            systemOptionItem.setOnce(true);
            SystemOptionTip tip = new SystemOptionTip();
            tip.setConfirmMsg("\u786e\u8ba4\u542f\u7528");
            tip.setTipHtml("<p style='font-size: 15px;text-align: center;'>\u542f\u7528\u591a\u7ef4\u4e4b\u540e\uff0c\u4e0d\u80fd\u53d6\u6d88\u542f\u7528\uff0c\u8bf7\u8f93\u5165\u786e\u8ba4\u4fe1\u606f\u7ee7\u7eed\u64cd\u4f5c\uff01</p><br>");
            tip.setTips(true);
            systemOptionItem.setTip((ISystemOptionTip)tip);
        }
        return systemOptionItem;
    }
}

