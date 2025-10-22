/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.inner.DelTableAction
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.web.app.BatchSummaryPluginConst;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.DelTableAction;

public class DelShareSchemeTableAction
extends DelTableAction {
    public static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.DelShareSchemeTableAction";
    private final BSShareService shareschemeService;

    public DelShareSchemeTableAction(BSShareService shareschemeService) {
        this.shareschemeService = shareschemeService;
    }

    public ActionInteractSetting getInteractSetting() {
        return new NormalActionInteractSetting("\u662f\u5426\u786e\u5b9a\u8981\u5220\u9664\u5171\u4eab\u65b9\u6848\uff1f");
    }

    public String getId() {
        return ID;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        ShareSummarySchemeDefine shareSummarySchemeDefine = new ShareSummarySchemeDefine();
        shareSummarySchemeDefine.setCode(actionContext.getCurrOperTableNode().getId());
        shareSummarySchemeDefine.setFromUser(BatchSummaryUtils.getCurrentUserID());
        shareSummarySchemeDefine.setToUser(actionContext.getCurrOperTableNode().getName());
        if (this.shareschemeService.removeShareSummaryScheme(shareSummarySchemeDefine).equals((Object)ShareSchemeServiceState.SUCCESS)) {
            return BatchSummaryPluginConst.getSuccessActionResult(ShareSchemeServiceState.SUCCESS.title, BatchSummaryPluginConst.getResourceNode());
        }
        return BatchSummaryPluginConst.getFailActionResult(ShareSchemeServiceState.FAIL.title);
    }
}

