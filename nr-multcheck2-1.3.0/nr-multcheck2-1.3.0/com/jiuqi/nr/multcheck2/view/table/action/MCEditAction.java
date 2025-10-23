/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.inner.EditTableAction
 */
package com.jiuqi.nr.multcheck2.view.table.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.multcheck2.common.MultcheckUtil;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckSchemeVO;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.EditTableAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCEditAction
extends EditTableAction {
    protected final Logger logger = LoggerFactory.getLogger(MCEditAction.class);
    public static final String ID = "multcheck2_table_edit";
    public static final String TITLE = "\u7f16\u8f91";
    IMCEnvService envService = (IMCEnvService)SpringBeanUtils.getBean(IMCEnvService.class);

    public MCEditAction() {
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
            ObjectMapper objectMapper = new ObjectMapper();
            MultcheckSchemeVO scheme = (MultcheckSchemeVO)objectMapper.readValue(param, MultcheckSchemeVO.class);
            this.envService.modifyScheme(scheme);
        }
        catch (Exception e) {
            return ActionResult.error(null, (String)e.getMessage());
        }
        ActionResult actionResult = ActionResult.success(null, (String)(this.getTitle() + "\u6210\u529f\uff01"));
        actionResult.setRefresh(true);
        return actionResult;
    }

    public ActionInteractSetting getDefaultInteractSetting(String modalTitle) {
        ModalActionInteractSetting interactSetting = MultcheckUtil.getModalActionInteractSetting();
        interactSetting.setExpose("NewScheme");
        interactSetting.setModalTitle(modalTitle);
        return interactSetting;
    }
}

