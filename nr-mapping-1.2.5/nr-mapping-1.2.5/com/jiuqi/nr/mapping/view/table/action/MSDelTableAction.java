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
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping.view.table.action;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.DelTableAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class MSDelTableAction
extends DelTableAction {
    protected final Logger logger = LoggerFactory.getLogger(MSDelTableAction.class);
    public static final String ID = "mapping_table_del";
    private final MappingSchemeService msService = (MappingSchemeService)SpringBeanUtils.getBean(MappingSchemeService.class);

    public MSDelTableAction() {
        this.interactSetting = this.getDefaultInteractSetting();
    }

    public String getId() {
        return ID;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    @Transactional(rollbackFor={Exception.class})
    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        String key = actionContext.getCurrOperTableNode().getId();
        try {
            this.msService.deleteByKey(key);
            this.msService.deleteMappingsByKey(key);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return ActionResult.error(null, (String)e.getMessage());
        }
        ActionResult actionResult = ActionResult.success(null, (String)"\u5220\u9664\u6210\u529f\uff01");
        actionResult.setRefresh(true);
        return actionResult;
    }

    public ActionInteractSetting getDefaultInteractSetting() {
        NormalActionInteractSetting interactSetting = new NormalActionInteractSetting();
        interactSetting.setConfirmMessage("\u786e\u5b9a\u8981\u5220\u9664{currDataTitle}\u5417\uff1f");
        return interactSetting;
    }
}

