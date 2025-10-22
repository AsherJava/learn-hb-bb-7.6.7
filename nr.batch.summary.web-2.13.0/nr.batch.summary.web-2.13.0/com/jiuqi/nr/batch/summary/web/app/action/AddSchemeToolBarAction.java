/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSSchemeService
 *  com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nr.batch.summary.storage.utils.DateUtils
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.storage.utils.DateUtils;
import com.jiuqi.nr.batch.summary.web.app.BatchSummaryPluginConst;
import com.jiuqi.nr.batch.summary.web.app.context.BatchSummaryContextData;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import java.util.Date;

public class AddSchemeToolBarAction
extends AbstractToolbarAction {
    private static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.new.scheme";
    private static final String ICON = "#icon16_GJ_A_NW_xinzeng";
    private BSSchemeService schemeService;
    private BatchSummaryContextData contextData;

    public AddSchemeToolBarAction(BatchSummaryContextData contextData, BSSchemeService schemeService) {
        this.contextData = contextData;
        this.schemeService = schemeService;
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u65b0\u589e\u6c47\u603b\u65b9\u6848";
    }

    public String getIcon() {
        return ICON;
    }

    public Boolean actionState(ActionContext actionContext) {
        return this.contextData.isValidContext();
    }

    public ActionInteractSetting getInteractSetting() {
        return BatchSummaryPluginConst.createSchemeModal("\u65b0\u589e\u6c47\u603b\u65b9\u6848");
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        SummarySchemeDefine schemeDefine = (SummarySchemeDefine)BatchSummaryUtils.toJavaBean((String)param, SummarySchemeDefine.class);
        if (schemeDefine != null) {
            ActionResult actionResult = AddSchemeToolBarAction.returnActionResult(this.schemeService.saveSummaryScheme(schemeDefine));
            SummaryScheme scheme = this.schemeService.findScheme(schemeDefine.getKey());
            actionResult.setData((ResourceNode)this.translateSummaryScheme(scheme));
            return actionResult;
        }
        return BatchSummaryPluginConst.getFailActionResult(SchemeServiceState.FAIL.title);
    }

    public String postAction() {
        return "com.jiuqi.nr.batch.summary.web.app.action.show.scheme.data";
    }

    private ResourceData translateSummaryScheme(SummaryScheme scheme) {
        ResourceData rd = new ResourceData();
        rd.setId(scheme.getKey());
        rd.setName(scheme.getCode());
        rd.setTitle(scheme.getTitle());
        rd.setType(NodeType.NODE_DATA);
        rd.setResourceType("com.jiuqi.nr.batch.summary.web.app.scheme.resource.type");
        rd.setGroup(scheme.getGroup());
        rd.setModifyTime(DateUtils.dateToString((Date)scheme.getUpdateTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        rd.setIcon("#icon-_GJZzidingyihuizong");
        return rd;
    }

    public static ActionResult returnActionResult(SchemeServiceState schemeServiceState) {
        switch (schemeServiceState) {
            case INVALID_SCHEME_DEFINE: 
            case INVALID_SCHEME_KEY: 
            case INVALID_TASK: {
                return BatchSummaryPluginConst.getFailActionResult(schemeServiceState.title);
            }
            case SUCCESS: {
                return BatchSummaryPluginConst.getSuccessActionResult(schemeServiceState.title, BatchSummaryPluginConst.getResourceNode());
            }
        }
        return BatchSummaryPluginConst.getFailActionResult(SchemeServiceState.FAIL.title);
    }
}

