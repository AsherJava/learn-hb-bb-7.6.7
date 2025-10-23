/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.inner.DelTableAction
 */
package com.jiuqi.nr.multcheck2.view.table.action;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.DelTableAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCDelAction
extends DelTableAction {
    protected final Logger logger = LoggerFactory.getLogger(MCDelAction.class);
    public static final String ID = "multcheck2_table_del";
    public static final String TITLE = "\u5220\u9664";
    IMCSchemeService schemeService = (IMCSchemeService)SpringBeanUtils.getBean(IMCSchemeService.class);

    public MCDelAction() {
        this.interactSetting = this.getDefaultInteractSetting(this.getTitle());
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        try {
            this.schemeService.deleteSchemeByKey(actionContext.getCurrOperTableNode().getId());
        }
        catch (Exception e) {
            return ActionResult.error(null, (String)e.getMessage());
        }
        ActionResult actionResult = ActionResult.success(null, (String)(this.getTitle() + "\u6210\u529f\uff01"));
        actionResult.setRefresh(true);
        return actionResult;
    }

    public ActionInteractSetting getDefaultInteractSetting(String modalTitle) {
        NormalActionInteractSetting interactSetting = new NormalActionInteractSetting();
        interactSetting.setConfirmMessage("\u786e\u5b9a\u8981\u5220\u9664{currDataTitle}\u5417\uff1f");
        return interactSetting;
    }
}

