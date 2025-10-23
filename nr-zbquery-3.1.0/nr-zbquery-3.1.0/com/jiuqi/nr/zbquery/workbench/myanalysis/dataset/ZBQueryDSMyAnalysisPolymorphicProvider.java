/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.nvwa.resourceview.category.ResourceType
 *  com.jiuqi.nvwa.workbench.common.WorkbenchActionType
 *  com.jiuqi.nvwa.workbench.myanalysis.bean.MyAnalysisData
 *  com.jiuqi.nvwa.workbench.myanalysis.dataset.IDataSetMyAnalysisPolymorphicProvider
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction
 *  com.jiuqi.nvwa.workbench.myanalysis.view.toolbar.action.MyAnalysisNewDataToolbarAction
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.workbench.myanalysis.dataset;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.ZBQueryWBDSModel;
import com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.action.ZBQueryDSEditTableAction;
import com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.action.ZBQueryDSNewDataToolbarAction;
import com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.action.ZBQueryDSPreviewTableAction;
import com.jiuqi.nvwa.resourceview.category.ResourceType;
import com.jiuqi.nvwa.workbench.common.WorkbenchActionType;
import com.jiuqi.nvwa.workbench.myanalysis.bean.MyAnalysisData;
import com.jiuqi.nvwa.workbench.myanalysis.dataset.IDataSetMyAnalysisPolymorphicProvider;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisEditTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisPreviewTableAction;
import com.jiuqi.nvwa.workbench.myanalysis.view.toolbar.action.MyAnalysisNewDataToolbarAction;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ZBQueryDSMyAnalysisPolymorphicProvider
implements IDataSetMyAnalysisPolymorphicProvider {
    protected final Logger logger = LoggerFactory.getLogger(ZBQueryDSMyAnalysisPolymorphicProvider.class);

    public ResourceType getResourceType() {
        return new ResourceType("com.jiuqi.nr.dataset.zbquery.workbench", "\u67e5\u8be2\u6570\u636e\u96c6");
    }

    public String getIcon() {
        return "#icon-_GJZchakanshangbao";
    }

    public String getGroupId() {
        return "com.jiuqi.nvwa.workbench.dataset";
    }

    public String getResourceClickAction() {
        return "myanalysis_table_dsedit_zbquery";
    }

    public MyAnalysisEditTableAction getEditTableAction() {
        return new ZBQueryDSEditTableAction();
    }

    public MyAnalysisPreviewTableAction getPreviewTableAction() {
        return new ZBQueryDSPreviewTableAction();
    }

    public MyAnalysisNewDataToolbarAction getNewToolbarAction() {
        return new ZBQueryDSNewDataToolbarAction();
    }

    public DSModel getDSModel(MyAnalysisData maData) {
        ZBQueryWBDSModel dsModel = new ZBQueryWBDSModel();
        try {
            if (StringUtils.hasLength(maData.getData())) {
                JSONObject src = new JSONObject(maData.getData());
                dsModel.fromJSON(src);
            }
            dsModel._setGuid(maData.getId());
            dsModel.setName(maData.getName());
            dsModel.setTitle(maData.getTitle());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return dsModel;
    }

    public boolean beforeAction(WorkbenchActionType actionType, MyAnalysisData maData) {
        try {
            DSModel dsModel = this.getDSModel(maData);
            if (actionType == WorkbenchActionType.ADD) {
                JSONObject result = new JSONObject();
                dsModel.toJSON(result);
                maData.setData(result.toString());
            } else if (actionType == WorkbenchActionType.UPDATE && StringUtils.hasLength(maData.getData())) {
                JSONObject result = new JSONObject();
                dsModel.toJSON(result);
                maData.setData(result.toString());
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public boolean afterAction(WorkbenchActionType actionType, MyAnalysisData maData) {
        return true;
    }
}

