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
 *  com.jiuqi.nvwa.resourceview.table.inner.CopyTableAction
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
import com.jiuqi.nvwa.resourceview.table.inner.CopyTableAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MCCopyAction
extends CopyTableAction {
    protected final Logger logger = LoggerFactory.getLogger(MCCopyAction.class);
    public static final String ID = "multcheck2_table_copy";
    public static final String TITLE = "\u521b\u5efa\u526f\u672c";
    IMCEnvService envService = (IMCEnvService)SpringBeanUtils.getBean(IMCEnvService.class);

    public MCCopyAction() {
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
            String oldScheme = scheme.getKey();
            this.envService.copyScheme(oldScheme, scheme);
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
        interactSetting.setModalTitle(StringUtils.hasLength(modalTitle) ? modalTitle : this.getTitle());
        return interactSetting;
    }
}

