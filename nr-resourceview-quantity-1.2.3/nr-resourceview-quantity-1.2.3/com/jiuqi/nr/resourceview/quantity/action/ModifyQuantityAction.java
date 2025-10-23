/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.quantity.bean.QuantityCategory
 *  com.jiuqi.nr.quantity.bean.QuantityInfo
 *  com.jiuqi.nr.quantity.bean.QuantityUnit
 *  com.jiuqi.nr.quantity.service.IQuantityService
 *  com.jiuqi.nr.quantity.service.KeyCondType
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  org.json.JSONObject
 */
package com.jiuqi.nr.resourceview.quantity.action;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.quantity.bean.QuantityCategory;
import com.jiuqi.nr.quantity.bean.QuantityInfo;
import com.jiuqi.nr.quantity.bean.QuantityUnit;
import com.jiuqi.nr.quantity.service.IQuantityService;
import com.jiuqi.nr.quantity.service.KeyCondType;
import com.jiuqi.nr.resourceview.quantity.bean.QuantityCategoryDTO;
import com.jiuqi.nr.resourceview.quantity.bean.QuantityInfoDTO;
import com.jiuqi.nr.resourceview.quantity.bean.QuantityUnitDTO;
import com.jiuqi.nr.resourceview.quantity.util.QuantityConvert;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import java.util.Date;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModifyQuantityAction
extends AbstractToolbarAction {
    protected final Logger logger = LoggerFactory.getLogger(ModifyQuantityAction.class);
    private final IQuantityService quantityService = (IQuantityService)SpringBeanUtils.getBean(IQuantityService.class);

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONObject jsonObject = new JSONObject(param);
        String id = jsonObject.optString("id");
        if (id.startsWith("QI")) {
            QuantityInfoDTO qiDTO = (QuantityInfoDTO)mapper.readValue(jsonObject.toString(), QuantityInfoDTO.class);
            qiDTO.setModifyTime(new Date());
            QuantityInfo qi = QuantityConvert.DTO2DO(qiDTO);
            this.quantityService.validateQuantityInfo(qi, false);
            this.quantityService.modifyQuantityInfo(qi, KeyCondType.ID);
        } else if (id.startsWith("QC")) {
            QuantityCategoryDTO qcDTO = (QuantityCategoryDTO)mapper.readValue(jsonObject.toString(), QuantityCategoryDTO.class);
            qcDTO.setModifyTime(new Date());
            QuantityCategory qc = QuantityConvert.DTO2DO(qcDTO);
            this.quantityService.validateQuantityCategory(qc, false);
            this.quantityService.modifyQuantityCategory(qc, KeyCondType.ID);
        } else if (id.startsWith("QU")) {
            QuantityUnitDTO quDTO = (QuantityUnitDTO)mapper.readValue(jsonObject.toString(), QuantityUnitDTO.class);
            quDTO.setModifyTime(new Date());
            QuantityUnit qu = QuantityConvert.DTO2DO(quDTO);
            this.quantityService.validateQuantityUnit(qu, false);
            this.quantityService.modifyQuantityUnit(qu, KeyCondType.ID);
        }
        ActionResult actionResult = ActionResult.success(null, (String)"\u4fee\u6539\u6210\u529f\uff01");
        actionResult.setRefresh(true);
        return actionResult;
    }

    public Boolean actionState(ActionContext actionContext) {
        return actionContext.getCheckTableNodes() != null && actionContext.getCheckTableNodes().size() == 1;
    }

    public String getId() {
        return "com.jiuqi.nr.resourceview.quantity.action.ModifyQuantityAction";
    }

    public String getTitle() {
        return "\u4fee\u6539";
    }

    public String getIcon() {
        return "#icon18_T_A_NW_bianji";
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

