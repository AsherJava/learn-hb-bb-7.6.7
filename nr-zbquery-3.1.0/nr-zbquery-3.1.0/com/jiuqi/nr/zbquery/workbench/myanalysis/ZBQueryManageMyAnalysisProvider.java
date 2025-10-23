/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.category.ResourceType
 *  com.jiuqi.nvwa.workbench.base.bean.WorkbenchCustomConfigItem
 *  com.jiuqi.nvwa.workbench.myanalysis.view.extend.INvwaMyAnalysisExtendProvider
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisCopyTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisSaveAsDataSetTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.toolbar.action.MyAnalysisNewDataToolbarAction
 */
package com.jiuqi.nr.zbquery.workbench.myanalysis;

import com.jiuqi.nr.zbquery.workbench.myanalysis.action.ZBQueryCopyDataTableAction;
import com.jiuqi.nr.zbquery.workbench.myanalysis.action.ZBQueryEditTableAction;
import com.jiuqi.nr.zbquery.workbench.myanalysis.action.ZBQueryNewDataToolbarAction;
import com.jiuqi.nr.zbquery.workbench.myanalysis.action.ZBQueryPreviewTableAction;
import com.jiuqi.nr.zbquery.workbench.myanalysis.action.ZBQuerySaveAsDataSetTableAction;
import com.jiuqi.nvwa.resourceview.category.ResourceType;
import com.jiuqi.nvwa.workbench.base.bean.WorkbenchCustomConfigItem;
import com.jiuqi.nvwa.workbench.myanalysis.view.extend.INvwaMyAnalysisExtendProvider;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisCopyTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisSaveAsDataSetTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.toolbar.action.MyAnalysisNewDataToolbarAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZBQueryManageMyAnalysisProvider
implements INvwaMyAnalysisExtendProvider {
    @Autowired
    private ZBQueryEditTableAction zbQueryEditTableAction;

    public ResourceType getResourceType() {
        return new ResourceType("com.jiuqi.nvwa.myanalysis.zbquery", "\u67e5\u8be2");
    }

    public String getIcon() {
        return "#icon-16_DH_A_NR_guoluchaxun";
    }

    public String getResourceClickAction() {
        return "myanalysis_zbquery_table_edit";
    }

    public MyAnalysisEditTableAction getEditTableAction() {
        return this.zbQueryEditTableAction;
    }

    public MyAnalysisPreviewTableAction getPreviewTableAction() {
        return new ZBQueryPreviewTableAction();
    }

    public MyAnalysisCopyTableAction getCopyTableAction() {
        return new ZBQueryCopyDataTableAction();
    }

    public MyAnalysisNewDataToolbarAction getNewToolbarAction() {
        return new ZBQueryNewDataToolbarAction();
    }

    public MyAnalysisSaveAsDataSetTableAction getSaveAsDataSetTableAction() {
        return new ZBQuerySaveAsDataSetTableAction();
    }

    public List<WorkbenchCustomConfigItem> getCustomConfigItems() {
        ArrayList<WorkbenchCustomConfigItem> configItems = new ArrayList<WorkbenchCustomConfigItem>();
        configItems.add(new WorkbenchCustomConfigItem("showSourceTree", "\u663e\u793a\u8d44\u6e90\u6811", "checkbox", "true", null, Collections.emptyList()));
        configItems.add(new WorkbenchCustomConfigItem("showZbSelector", "\u663e\u793a\u6309\u8868\u6837\u6dfb\u52a0", "checkbox", "true", null, Collections.emptyList()));
        configItems.add(new WorkbenchCustomConfigItem("autoRefresh", "\u81ea\u52a8\u5237\u65b0", "checkbox", "false", null, Collections.emptyList()));
        configItems.add(new WorkbenchCustomConfigItem("hiddenDimensions", "\u9690\u85cf\u7ef4\u5ea6", "textarea", null, "\u793a\u4f8b\uff1a[{\"name\":\"MD_BZ\", \"value\":\"01\"},{\"name\":\"MD_KM\"}]", Collections.emptyList()));
        return configItems;
    }
}

