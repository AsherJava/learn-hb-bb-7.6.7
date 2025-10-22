/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treestore.systemconfig;

import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeSystemConfigImpl
implements UnitTreeSystemConfig {
    @Resource
    private INvwaSystemOptionService sysOption;

    @Override
    public boolean isCountOfDiffUnit() {
        String value = this.sysOption.get("unit_tree_system_config", "count_of_diff_unit");
        return "T".equals(value);
    }

    @Override
    public boolean isCountOfLeaves() {
        String value = this.sysOption.get("unit_tree_system_config", "count_of_leaves");
        return "T".equals(value);
    }

    @Override
    public String nameOfLeaves() {
        return this.sysOption.get("unit_tree_system_config", "name_of_leaves");
    }

    @Override
    public boolean isShowNodeVline() {
        String value = this.sysOption.get("unit_tree_system_config", "show_node_v_line");
        return "T".equals(value);
    }

    @Override
    public boolean isShowReminder() {
        String showReminder = this.sysOption.get("start-reminder", "START_REMINDER");
        return "1".equals(showReminder);
    }

    @Override
    public boolean isShowFillReportTimeSettingsMenu() {
        String value = this.sysOption.get("unit_tree_system_config", "show_fill_report_time_settings_menu");
        return "T".equals(value);
    }

    @Override
    public boolean isCountOfAllChildrenNum() {
        String value = this.sysOption.get("unit_tree_system_config", "count_of_all_children_num");
        return "F".equals(value);
    }

    @Override
    public boolean isCountOfAllChildrenQuantities() {
        String value = this.sysOption.get("unit_tree_system_config", "count_of_all_children_quantities");
        return "T".equals(value);
    }

    @Override
    public boolean isShowTreeExpandAllLevelMenu() {
        String value = this.sysOption.get("unit_tree_system_config", "tree_expand_all_level");
        return "T".equals(value);
    }
}

