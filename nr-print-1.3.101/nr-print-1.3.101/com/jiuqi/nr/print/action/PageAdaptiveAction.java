/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject
 *  com.jiuqi.nr.definition.print.common.PrintElementUtils
 *  com.jiuqi.print.designer.PrintDesigner
 *  com.jiuqi.print.designer.command.ModifyPropertyCommand
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xlib.AbstractAction
 *  com.jiuqi.xlib.ICommand
 *  org.json.JSONObject
 */
package com.jiuqi.nr.print.action;

import com.jiuqi.nr.definition.facade.print.core.ReportTemplateObject;
import com.jiuqi.nr.definition.print.common.PrintElementUtils;
import com.jiuqi.print.designer.PrintDesigner;
import com.jiuqi.print.designer.command.ModifyPropertyCommand;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xlib.AbstractAction;
import com.jiuqi.xlib.ICommand;
import org.json.JSONObject;

public class PageAdaptiveAction
extends AbstractAction {
    private final PrintDesigner designer;

    public PageAdaptiveAction(String name, PrintDesigner designer) {
        super(name);
        this.designer = designer;
    }

    public void refreshStatus() {
        this.setEnable(true);
    }

    public void dispose() {
    }

    protected boolean doRun() {
        ReportTemplateObject table = PrintElementUtils.getReportTemplate((ITemplateDocument)this.designer.getDocument());
        if (null == table) {
            return false;
        }
        JSONObject oldPaginateConfig = table.getPaginateConfig().serialize();
        JSONObject oldResizeConfig = table.getResizeConfig().serialize();
        String resizeConfigValue = "{\"verticalResizeType\":\"5\",\"isVerticalScaleLocked\":\"false\",\"horizonResizeType\":\"5\",\"isHorizonScaleLocked\":\"false\"}";
        ModifyPropertyCommand resizeCommand = new ModifyPropertyCommand((Object)table, "resizeConfig", (Object)oldResizeConfig.toString(), (Object)resizeConfigValue);
        this.designer.getViewer().getCommandStack().execute((ICommand)resizeCommand);
        String paginateValue = "{\"rowPaginateType\":\"-1\",\"rowCountPerPage\":\"19\",\"rowPageCount\":\"1\",\"isShowRowHeaderInAllPages\":\"true\",\"colPaginateType\":\"-1\",\"colCountPerPage\":\"10\",\"colPageCount\":\"2\",\"isShowColHeaderInAllPages\":\"true\"}";
        ModifyPropertyCommand paginateCommand = new ModifyPropertyCommand((Object)table, "paginateConfig", (Object)oldPaginateConfig.toString(), (Object)paginateValue);
        this.designer.getViewer().getCommandStack().execute((ICommand)paginateCommand);
        return true;
    }
}

