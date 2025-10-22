/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treestore.systemconfig;

import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component(value="uselector-system-config")
public class UselectorSystemOptions
implements ISystemOptionDeclare {
    public static final String KEY = "uselector_system_config";
    public static final String USELECTOR_QUICK_CHECK_MODE = "uselector_quick_check_mode";
    public static final String USELECTOR_ACTION_OPT_MODE = "uselector_action_opt_mode";
    @Resource
    private ISystemOptionOperator systemOptionOperator;

    public String getId() {
        return KEY;
    }

    public String getTitle() {
        return "\u5355\u4f4d\u9009\u62e9\u5668";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public String getPluginName() {
        return "uselector-config-plugin";
    }

    public int getOrdinal() {
        return 0;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new UnitTreeSystemOptionItem(USELECTOR_QUICK_CHECK_MODE, "\u5355\u4f4d\u9009\u62e9\u5668-\u5feb\u901f\u7b5b\u9009\u6761\u4ef6\u9009\u62e9\u6a21\u5f0f", "single"));
        optionItems.add(new UnitTreeSystemOptionItem(USELECTOR_ACTION_OPT_MODE, "\u5355\u4f4d\u9009\u62e9\u5668-\u52fe\u9009\u52a8\u4f5c\u6a21\u5f0f", "NR"));
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

