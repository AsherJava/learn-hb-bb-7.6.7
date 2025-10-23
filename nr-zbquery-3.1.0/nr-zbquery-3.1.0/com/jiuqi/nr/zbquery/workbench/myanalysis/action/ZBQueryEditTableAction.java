/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.TableInnerActionType
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction
 */
package com.jiuqi.nr.zbquery.workbench.myanalysis.action;

import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.CoverActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.TableInnerActionType;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction;
import org.springframework.stereotype.Component;

@Component
public class ZBQueryEditTableAction
extends MyAnalysisEditTableAction {
    public static final String ID = "myanalysis_zbquery_table_edit";

    public ZBQueryEditTableAction() {
        this.interactSetting = this.getEditInteractSetting();
        this.setInnerType(TableInnerActionType.TABLE_EDIT);
    }

    public String getId() {
        return ID;
    }

    public ActionInteractSetting getEditInteractSetting() {
        CoverActionInteractSetting interactSetting = new CoverActionInteractSetting();
        interactSetting.setPluginName("nr-zbquery-myanalysis");
        interactSetting.setPluginType("nr-zbquery-myanalysis-plugin");
        interactSetting.setExpose("ZBQueryMyAnalysisEditor");
        return interactSetting;
    }
}

