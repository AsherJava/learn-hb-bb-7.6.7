/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.quantity.service.IQuantityService
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.resourceview.quantity.action;

import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.NormalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DeleteQuantityAction
extends AbstractToolbarAction {
    @Autowired
    private IQuantityService quantityService;

    public String getId() {
        return "com.jiuqi.nr.resourceview.quantity.action.DeleteQuantityAction";
    }

    public String getTitle() {
        return "\u5220\u9664";
    }

    public String getIcon() {
        return "#icon16_GJ_A_NW_shanchu";
    }

    public boolean enabled() {
        return false;
    }

    public Boolean actionState(ActionContext actionContext) {
        return !CollectionUtils.isEmpty(actionContext.getCheckTableNodes());
    }

    public ActionInteractSetting getInteractSetting() {
        NormalActionInteractSetting setting = new NormalActionInteractSetting();
        setting.setDynamicConfirmMessageProvider(actionContext -> {
            String confirmMsg = "\u786e\u5b9a\u5220\u9664\u9009\u4e2d\u7684\u6570\u636e\u9879\u53ca\u5176\u6240\u6709\u4e0b\u7ea7\u5417\uff1f";
            return confirmMsg;
        });
        return setting;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        List checkNodes = actionContext.getCheckTableNodes();
        List checkNodeIds = checkNodes.stream().map(ResourceNode::getId).collect(Collectors.toList());
        String firstId = (String)checkNodeIds.get(0);
        List realIds = checkNodeIds.stream().map(id -> id.substring(3)).collect(Collectors.toList());
        if (firstId.startsWith("QI")) {
            this.quantityService.deleteQuantityInfoByIds(realIds);
        } else if (firstId.startsWith("QC")) {
            this.quantityService.deleteQuantityCategoryByIds(realIds);
        } else if (firstId.startsWith("QU")) {
            this.quantityService.deleteQuantityUnitByIds(realIds);
        }
        ActionResult result = ActionResult.success(null, (String)"\u5220\u9664\u6210\u529f");
        result.setRefresh(true);
        return result;
    }
}

