/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 *  com.jiuqi.nvwa.resourceview.toolbar.inner.DelToolbarAction
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping.view.toolbar.action;

import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import com.jiuqi.nvwa.resourceview.toolbar.inner.DelToolbarAction;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MSDelToolbarAction
extends DelToolbarAction {
    protected final Logger logger = LoggerFactory.getLogger(MSDelToolbarAction.class);
    private static final String ID = "mapping_toolbar_del";
    @Autowired
    private MappingSchemeService msService;

    public MSDelToolbarAction() {
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
        List nodes = actionContext.getCheckTableNodes();
        ArrayList<String> ids = new ArrayList<String>();
        for (ResourceNode node : nodes) {
            ids.add(node.getId());
        }
        try {
            this.msService.batchDelete(ids);
            this.msService.batchDeleteMappingsByKey(ids);
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
        interactSetting.setConfirmMessage("\u8be5\u64cd\u4f5c\u65e0\u6cd5\u64a4\u9500\uff0c\u662f\u5426\u786e\u5b9a\u5220\u9664\uff1f");
        return interactSetting;
    }
}

