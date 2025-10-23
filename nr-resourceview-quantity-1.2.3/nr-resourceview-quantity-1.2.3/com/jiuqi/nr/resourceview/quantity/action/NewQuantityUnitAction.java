/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.quantity.bean.QuantityUnit
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
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nr.resourceview.quantity.bean.QuantityUnitDTO;
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

public class NewQuantityUnitAction
extends AbstractToolbarAction {
    protected final Logger logger = LoggerFactory.getLogger(NewQuantityUnitAction.class);
    private final IQuantityService quantityService = (IQuantityService)SpringBeanUtils.getBean(IQuantityService.class);

    public String getId() {
        return "com.jiuqi.nr.resourceview.quantity.action.NewQuantityUnitAction";
    }

    public String getTitle() {
        return "\u65b0\u5efa\u91cf\u7eb2\u5355\u4f4d";
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            QuantityUnitDTO quDTO = (QuantityUnitDTO)mapper.readValue(param, QuantityUnitDTO.class);
            quDTO.setId(QuantityConvert.getFakeId("QU", UUID.randomUUID().toString()));
            quDTO.setModifyTime(new Date());
            quDTO.setOrder(OrderGenerator.newOrder());
            QuantityUnit qu = QuantityConvert.DTO2DO(quDTO);
            this.quantityService.validateQuantityUnit(qu, true);
            this.quantityService.addQuantityUnit(qu);
            ActionResult actionResult = ActionResult.success((ResourceNode)QuantityConvert.DTO2RD(quDTO), (String)(this.getTitle() + "\u6210\u529f\uff01"));
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
            return id.startsWith("QC");
        }
        return false;
    }

    public ActionInteractSetting getInteractSetting() {
        ModalActionInteractSetting interactSetting = new ModalActionInteractSetting();
        interactSetting.setPluginName("nr-quantity-plugin");
        interactSetting.setPluginType("nr-quantity-plugin");
        interactSetting.setExpose("NewQuantityView");
        interactSetting.setModalTitle(this.getTitle());
        interactSetting.setHeight(220);
        return interactSetting;
    }
}

