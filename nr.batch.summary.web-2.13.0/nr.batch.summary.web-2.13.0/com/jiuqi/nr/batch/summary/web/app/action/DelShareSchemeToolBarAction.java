/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState
 *  com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.service.enumeration.GroupServiceState;
import com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.web.app.BatchSummaryPluginConst;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;

public class DelShareSchemeToolBarAction
extends AbstractToolbarAction {
    private static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.share.scheme.del";
    private static final String ICON = "#icon16_GJ_A_NW_shanchu";
    private BSShareService bsShareService;

    public DelShareSchemeToolBarAction(BSShareService bsShareService) {
        this.bsShareService = bsShareService;
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u5220\u9664";
    }

    public String getIcon() {
        return ICON;
    }

    public Boolean actionState(ActionContext actionContext) {
        if (actionContext.getCheckTableNodes().size() > 0) {
            return true;
        }
        return false;
    }

    public ActionInteractSetting getInteractSetting() {
        return new NormalActionInteractSetting("\u662f\u5426\u786e\u5b9a\u8981\u5220\u9664\u5171\u4eab\u65b9\u6848\uff1f");
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        for (ResourceNode rn : actionContext.getCheckTableNodes()) {
            ShareSummarySchemeDefine shareSummarySchemeDefine = new ShareSummarySchemeDefine();
            shareSummarySchemeDefine.setCode(rn.getId());
            shareSummarySchemeDefine.setFromUser(BatchSummaryUtils.getCurrentUserID());
            shareSummarySchemeDefine.setToUser(rn.getName());
            if (this.bsShareService.removeShareSummaryScheme(shareSummarySchemeDefine).equals((Object)ShareSchemeServiceState.SUCCESS)) continue;
            return BatchSummaryPluginConst.getFailActionResult(ShareSchemeServiceState.FAIL.title);
        }
        return BatchSummaryPluginConst.getSuccessActionResult(ShareSchemeServiceState.SUCCESS.title, BatchSummaryPluginConst.getResourceNode());
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        for (ResourceNode rn : actionContext.getCheckTableNodes()) {
            ShareSummarySchemeDefine shareSummarySchemeDefine = new ShareSummarySchemeDefine();
            shareSummarySchemeDefine.setCode(rn.getId());
            shareSummarySchemeDefine.setFromUser(BatchSummaryUtils.getCurrentUserID());
            shareSummarySchemeDefine.setToUser(rn.getName());
            if (this.bsShareService.removeShareSummaryScheme(shareSummarySchemeDefine).equals((Object)ShareSchemeServiceState.SUCCESS)) continue;
            return BatchSummaryPluginConst.getFailActionResult(ShareSchemeServiceState.FAIL.title);
        }
        return BatchSummaryPluginConst.getSuccessActionResult(ShareSchemeServiceState.SUCCESS.title, BatchSummaryPluginConst.getResourceNode());
    }

    public static ActionResult returnActionResult(ShareSchemeServiceState ShareSchemeServiceState2) {
        switch (ShareSchemeServiceState2) {
            case SUCCESS: {
                return BatchSummaryPluginConst.getSuccessActionResult(ShareSchemeServiceState2.title, BatchSummaryPluginConst.getResourceNode());
            }
        }
        return BatchSummaryPluginConst.getFailActionResult(GroupServiceState.FAIL.title);
    }
}

