/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.nvwa.resourceview.category.ResourceType
 *  com.jiuqi.nvwa.workbench.myanalysis.bean.MyAnalysisData
 *  com.jiuqi.nvwa.workbench.myanalysis.dataset.IDataSetMyAnalysisPolymorphicProvider
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisCopyTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisRenameTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.toolbar.action.MyAnalysisNewDataToolbarAction
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.report.workbench.myanalysis;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.report.builder.ReportDSModelBuilder;
import com.jiuqi.bi.dataset.report.workbench.ReportWBDsModel;
import com.jiuqi.bi.dataset.report.workbench.myanalysis.actions.ReportDSCopyTableAction;
import com.jiuqi.bi.dataset.report.workbench.myanalysis.actions.ReportDSEditorTableAction;
import com.jiuqi.bi.dataset.report.workbench.myanalysis.actions.ReportDSNewToolbarAction;
import com.jiuqi.bi.dataset.report.workbench.myanalysis.actions.ReportDSPreviewTableAction;
import com.jiuqi.bi.dataset.report.workbench.myanalysis.actions.ReportDSRenameTableAction;
import com.jiuqi.nvwa.resourceview.category.ResourceType;
import com.jiuqi.nvwa.workbench.myanalysis.bean.MyAnalysisData;
import com.jiuqi.nvwa.workbench.myanalysis.dataset.IDataSetMyAnalysisPolymorphicProvider;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisCopyTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisRenameTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.toolbar.action.MyAnalysisNewDataToolbarAction;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ReportDSMyAnalysisPolymorphicProvider
implements IDataSetMyAnalysisPolymorphicProvider {
    @Autowired
    private ReportDSModelBuilder modelBuilder;

    public ResourceType getResourceType() {
        return new ResourceType("ReportDataSet_workbench", "\u62a5\u8868\u6570\u636e\u96c6");
    }

    public String getIcon() {
        return "#icon-16_SHU_A_NR_baobiaoshujuji";
    }

    public String getGroupId() {
        return "com.jiuqi.nvwa.myanalysis.dataset";
    }

    public String getResourceClickAction() {
        return "reportds_editor_tableaction";
    }

    public MyAnalysisEditTableAction getEditTableAction() {
        return new ReportDSEditorTableAction();
    }

    public MyAnalysisPreviewTableAction getPreviewTableAction() {
        return new ReportDSPreviewTableAction();
    }

    public MyAnalysisRenameTableAction getRenameTableAction() {
        return new ReportDSRenameTableAction();
    }

    public MyAnalysisCopyTableAction getCopyTableAction() {
        return new ReportDSCopyTableAction();
    }

    public DSModel getDSModel(MyAnalysisData maData) {
        ReportWBDsModel dsModel = new ReportWBDsModel();
        dsModel.setModelBuilder(this.modelBuilder);
        try {
            if (StringUtils.hasLength(maData.getData())) {
                JSONObject src = new JSONObject(maData.getData());
                dsModel.loadExtFromJSON(src.getJSONObject("extData"));
            }
            dsModel._setGuid(maData.getId());
            dsModel.setName(maData.getName());
            dsModel.setTitle(maData.getTitle());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dsModel;
    }

    public MyAnalysisNewDataToolbarAction getNewToolbarAction() {
        return new ReportDSNewToolbarAction();
    }
}

