/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.table.inner.MoveDownTableAction
 */
package com.jiuqi.nr.multcheck2.view.table.action;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.table.inner.MoveDownTableAction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCMoveDownAction
extends MoveDownTableAction {
    protected final Logger logger = LoggerFactory.getLogger(MCMoveDownAction.class);
    IMCSchemeService schemeService = (IMCSchemeService)SpringBeanUtils.getBean(IMCSchemeService.class);
    IMCEnvService envService = (IMCEnvService)SpringBeanUtils.getBean(IMCEnvService.class);

    public ActionResult run(ActionContext actionContext) throws Exception {
        String nodeId = actionContext.getSelectTreeNode().getId();
        List<Object> schemes = new ArrayList();
        if (nodeId.contains("T@")) {
            schemes = this.envService.getSchemeListByTask(nodeId.replaceFirst("T@", ""));
        } else if (nodeId.contains("F@")) {
            String formScheme = actionContext.getSelectTreeNode().getId().replaceFirst("F@", "");
            schemes = this.schemeService.getSchemeByForm(formScheme);
        }
        String schemeKey = actionContext.getCurrOperTableNode().getId();
        int ctIndex = schemes.stream().map(MultcheckScheme::getKey).collect(Collectors.toList()).indexOf(schemeKey);
        MultcheckScheme ctScheme = (MultcheckScheme)schemes.get(ctIndex);
        MultcheckScheme downScheme = (MultcheckScheme)schemes.get(ctIndex + 1);
        if (!ctScheme.getFormScheme().equals(downScheme.getFormScheme())) {
            return ActionResult.error(null, (String)"\u62a5\u8868\u65b9\u6848\u4e0d\u540c\uff0c\u65e0\u6cd5\u79fb\u52a8");
        }
        try {
            this.schemeService.moveScheme(ctScheme, downScheme);
        }
        catch (Exception e) {
            this.logger.error("\u4e0b\u79fb\u5931\u8d25", e);
            return ActionResult.error(null, (String)"\u4e0b\u79fb\u5931\u8d25");
        }
        ActionResult res = new ActionResult(true, null, null);
        res.setShowSuccessTip(false);
        return res;
    }
}

