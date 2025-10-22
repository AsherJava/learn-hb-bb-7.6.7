/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.service.BSShareService
 *  com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummaryParamDefine
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.batch.summary.web.app.action;

import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummaryParamDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.ShareSummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.batch.summary.web.app.BatchSummaryPluginConst;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class ShareSchemeToolBarAction
extends AbstractToolbarAction {
    private static final String ID = "com.jiuqi.nr.batch.summary.web.app.action.share.scheme.toolbar";
    private static final String ICON = "#icon16_GJ_A_NW_xinzeng";
    private BSShareService bsShareService;

    public ShareSchemeToolBarAction(BSShareService bsShareService) {
        this.bsShareService = bsShareService;
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u5171\u4eab";
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
        ModalActionInteractSetting modal = new ModalActionInteractSetting();
        modal.setWidth(960);
        modal.setModalTitle("\u9009\u62e9\u7528\u6237");
        modal.setProdLine("@nr");
        modal.setPluginName("batch-summary-plugin");
        modal.setPluginType("batch-summary-plugin");
        modal.setExpose("shareView");
        return modal;
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        if (actionContext.getCheckTableNodes().size() > 0) {
            ShareSummaryParamDefine shareSummaryparamDefine = (ShareSummaryParamDefine)JsonUtil.toObject((String)param, ShareSummaryParamDefine.class);
            for (ResourceNode rn : actionContext.getCheckTableNodes()) {
                ShareSchemeServiceState shareSchemeServiceState;
                ShareSummarySchemeDefine shareSummarySchemeDefine;
                Set alreadyHaveToUsers = this.bsShareService.findToUsers(shareSummaryparamDefine.getTask(), new String[]{rn.getId()});
                ArrayList<String> newToUsers = new ArrayList<String>();
                for (String toUser : shareSummaryparamDefine.getSelectList()) {
                    if (alreadyHaveToUsers.contains(toUser)) continue;
                    newToUsers.add(toUser);
                }
                ArrayList<String> delToUsers = new ArrayList<String>();
                for (String toUser : alreadyHaveToUsers) {
                    if (shareSummaryparamDefine.getSelectList().contains(toUser)) continue;
                    delToUsers.add(toUser);
                }
                for (String toUser : delToUsers) {
                    shareSummarySchemeDefine = new ShareSummarySchemeDefine();
                    shareSummarySchemeDefine.setCode(rn.getId());
                    shareSummarySchemeDefine.setFromUser(BatchSummaryUtils.getCurrentUserID());
                    shareSummarySchemeDefine.setToUser(toUser);
                    shareSummarySchemeDefine.setTask(shareSummaryparamDefine.getTask());
                    shareSchemeServiceState = this.bsShareService.removeShareSummaryScheme(shareSummarySchemeDefine);
                    if (!shareSchemeServiceState.equals((Object)ShareSchemeServiceState.FAIL)) continue;
                    return BatchSummaryPluginConst.getFailActionResult(ShareSchemeServiceState.FAIL.title);
                }
                for (String toUser : newToUsers) {
                    shareSummarySchemeDefine = new ShareSummarySchemeDefine();
                    shareSummarySchemeDefine.setCode(rn.getId());
                    shareSummarySchemeDefine.setFromUser(BatchSummaryUtils.getCurrentUserID());
                    shareSummarySchemeDefine.setToUser(toUser);
                    shareSummarySchemeDefine.setShareTime(new Date());
                    shareSummarySchemeDefine.setTask(shareSummaryparamDefine.getTask());
                    shareSchemeServiceState = this.bsShareService.addShareSummaryScheme(shareSummarySchemeDefine);
                    if (!shareSchemeServiceState.equals((Object)ShareSchemeServiceState.FAIL)) continue;
                    return BatchSummaryPluginConst.getFailActionResult(ShareSchemeServiceState.FAIL.title);
                }
            }
            return BatchSummaryPluginConst.getSuccessActionResult(ShareSchemeServiceState.SUCCESS.title, BatchSummaryPluginConst.getResourceNode());
        }
        return BatchSummaryPluginConst.getFailActionResult(ShareSchemeServiceState.NO_SELECTED.title);
    }
}

