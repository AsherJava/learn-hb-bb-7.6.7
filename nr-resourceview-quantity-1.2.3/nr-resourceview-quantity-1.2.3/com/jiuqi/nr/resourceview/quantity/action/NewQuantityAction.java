/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.quantity.bean.QuantityInfo
 *  com.jiuqi.nr.quantity.service.IQuantityService
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 */
package com.jiuqi.nr.resourceview.quantity.action;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nr.resourceview.quantity.bean.QuantityInfoDTO;
import com.jiuqi.nr.resourceview.quantity.util.QuantityConvert;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewQuantityAction
extends AbstractToolbarAction {
    protected final Logger logger = LoggerFactory.getLogger(NewQuantityAction.class);
    private final IQuantityService quantityService = (IQuantityService)SpringBeanUtils.getBean(IQuantityService.class);

    public String getId() {
        return "com.jiuqi.nr.resourceview.quantity.action.NewQuantityAction";
    }

    public String getTitle() {
        return "\u65b0\u5efa\u91cf\u7eb2";
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            QuantityInfoDTO qiDTO = (QuantityInfoDTO)mapper.readValue(param, QuantityInfoDTO.class);
            qiDTO.setId(QuantityConvert.getFakeId("QI", UUID.randomUUID().toString()));
            qiDTO.setModifyTime(new Date());
            qiDTO.setOrder(OrderGenerator.newOrder());
            QuantityInfo qi = QuantityConvert.DTO2DO(qiDTO);
            this.quantityService.validateQuantityInfo(qi, true);
            this.quantityService.addQuantityInfo(qi);
            ActionResult actionResult = ActionResult.success((ResourceNode)QuantityConvert.DTO2RD(qiDTO), (String)(this.getTitle() + "\u6210\u529f\uff01"));
            actionResult.setRefresh(true);
            return actionResult;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return ActionResult.error(null, (String)e.getMessage());
        }
    }

    public Boolean actionState(ActionContext actionContext) {
        if (actionContext.getSelectTreeNode() != null) {
            String id = actionContext.getSelectTreeNode().getId();
            if (id.startsWith("QI")) {
                return false;
            }
            if (id.startsWith("QC")) {
                return false;
            }
            if (id.startsWith("QU")) {
                return false;
            }
        }
        return true;
    }

    public ActionInteractSetting getInteractSetting() {
        ModalActionInteractSetting interactSetting = new ModalActionInteractSetting();
        interactSetting.setPluginName("nr-quantity-plugin");
        interactSetting.setPluginType("nr-quantity-plugin");
        interactSetting.setExpose("NewQuantityView");
        interactSetting.setModalTitle(this.getTitle());
        interactSetting.setHeight(125);
        return interactSetting;
    }
}

