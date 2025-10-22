/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$CategoryType
 */
package com.jiuqi.nr.unit.treestore.systemconfig;

import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="unit-tree-system-config-v2")
public class UnitTreeSystemOptions
implements ISystemOptionDeclare {
    public static final String KEY = "unit_tree_system_config";
    public static final String IS_COUNT_OF_DIFF_UNIT = "count_of_diff_unit";
    public static final String IS_COUNT_OF_LEAVES = "count_of_leaves";
    public static final String NAME_OF_LEAVES = "name_of_leaves";
    public static final String IS_SHOW_NODE_VLINE = "show_node_v_line";
    public static final String IS_SHOW_FILL_REPORT_TIME_SETTINGS_MENU = "show_fill_report_time_settings_menu";
    public static final String IS_COUNT_OF_ALL_CHILDREN_NUM = "count_of_all_children_num";
    public static final String IS_COUNT_ALL_CHILDREN_QUENTITIES = "count_of_all_children_quantities";
    public static final String IS_TREE_EXPAND_ALL_LEVEL = "tree_expand_all_level";
    @Autowired
    private ISystemOptionOperator systemOptionOperator;

    public String getId() {
        return KEY;
    }

    public String getTitle() {
        return "\u5355\u4f4d\u6811";
    }

    public String getNameSpace() {
        return "\u62a5\u8868";
    }

    public SystemOptionConst.CategoryType getCategoryType() {
        return SystemOptionConst.CategoryType.CUSTOM;
    }

    public String getPluginName() {
        return "unit-tree-config-plugin";
    }

    public int getOrdinal() {
        return 0;
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> optionItems = new ArrayList<ISystemOptionItem>();
        optionItems.add(new UnitTreeSystemOptionItem(IS_COUNT_OF_DIFF_UNIT, "\u662f\u5426\u7edf\u8ba1\u5dee\u989d\u5355\u4f4d", "T"));
        optionItems.add(new UnitTreeSystemOptionItem(IS_COUNT_OF_LEAVES, "\u4ec5\u7edf\u8ba1\u53f6\u5b50\u8282\u70b9", "F"));
        optionItems.add(new UnitTreeSystemOptionItem(NAME_OF_LEAVES, "\u7edf\u8ba1\u540d\u79f0", "\u6240\u6709\u4e0b\u7ea7\uff1a"));
        optionItems.add(new UnitTreeSystemOptionItem(IS_SHOW_NODE_VLINE, "\u5207\u6362\u4e3a\u8282\u70b9\u7ad6\u7ebf\u98ce\u683c", "F"));
        optionItems.add(new UnitTreeSystemOptionItem(IS_SHOW_FILL_REPORT_TIME_SETTINGS_MENU, "\u662f\u5426\u663e\u793a\u586b\u62a5\u65f6\u95f4\u8bbe\u7f6e\u548c\u67e5\u770b", "T"));
        optionItems.add(new UnitTreeSystemOptionItem(IS_COUNT_OF_ALL_CHILDREN_NUM, "\u5355\u4f4d\u6811\u8282\u70b9\u540e\u9762\u7684\u6570\u5b57\u662f\u663e\u793a\u76f4\u63a5\u4e0b\u7ea7\uff0c\u8fd8\u662f\u6240\u6709\u4e0b\u7ea7", "T"));
        optionItems.add(new UnitTreeSystemOptionItem(IS_COUNT_ALL_CHILDREN_QUENTITIES, "\u5355\u4f4d\u6811\u8282\u70b9\u662f\u5426\u7edf\u8ba1\u5176\u4e0b\u7ea7\u8282\u70b9\u6570\u76ee", "T"));
        optionItems.add(new UnitTreeSystemOptionItem(IS_TREE_EXPAND_ALL_LEVEL, "\u662f\u5426\u5c55\u5f00\u5355\u4f4d\u6811\u7684\u6240\u6709\u5c42\u7ea7", "F"));
        return optionItems;
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

