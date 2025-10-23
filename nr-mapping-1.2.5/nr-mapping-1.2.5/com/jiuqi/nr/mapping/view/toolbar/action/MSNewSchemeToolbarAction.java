/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 *  com.jiuqi.nvwa.resourceview.toolbar.ToolbarInnerActionType
 *  com.jiuqi.nvwa.workbench.common.IErrorMessageEnum
 *  com.jiuqi.nvwa.workbench.common.WorkbenchError
 *  com.jiuqi.nvwa.workbench.myanalysis.common.ErrorMessageEnum
 */
package com.jiuqi.nr.mapping.view.toolbar.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nvwa.resourceview.action.AbstractToolbarAction;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import com.jiuqi.nvwa.resourceview.toolbar.ToolbarInnerActionType;
import com.jiuqi.nvwa.workbench.common.IErrorMessageEnum;
import com.jiuqi.nvwa.workbench.common.WorkbenchError;
import com.jiuqi.nvwa.workbench.myanalysis.common.ErrorMessageEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MSNewSchemeToolbarAction
extends AbstractToolbarAction {
    protected final Logger logger = LoggerFactory.getLogger(MSNewSchemeToolbarAction.class);
    private static final String ID = "mapping_toolbar_newScheme";
    private static final String TITLE = "\u65b0\u5efa\u65b9\u6848";
    private static final String ICON = "#icon-16_DH_A_NR_wannengtubiao";
    @Autowired
    private MappingSchemeService schemeService;

    public MSNewSchemeToolbarAction() {
        this.interactSetting = this.getDefaultInteractSetting(this.getTitle());
        this.enable = true;
        this.setInnerType(ToolbarInnerActionType.TOOLBAR_NEW_RESOURCE);
    }

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getIcon() {
        return ICON;
    }

    public Boolean actionState(ActionContext actionContext) {
        ResourceNode treeNode = actionContext.getSelectTreeNode();
        if (treeNode == null) {
            return false;
        }
        boolean isGroup = treeNode.getId().indexOf("G@") > -1;
        return !isGroup;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return ActionResult.error(null, (String)WorkbenchError.getInstance((IErrorMessageEnum)ErrorMessageEnum.MA_ADD_GROUP_ERROR, (Object[])new Object[0]).getMessage());
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MappingScheme scheme = (MappingScheme)objectMapper.readValue(param, MappingScheme.class);
            this.schemeService.add(scheme);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return ActionResult.error(null, (String)e.getMessage());
        }
        ActionResult actionResult = ActionResult.success(null, (String)(this.getTitle() + "\u6210\u529f\uff01"));
        actionResult.setRefresh(true);
        return actionResult;
    }

    public ActionInteractSetting getDefaultInteractSetting(String modalTitle) {
        ModalActionInteractSetting interactSetting = new ModalActionInteractSetting();
        interactSetting.setPluginName("nr-mapping-scheme-plugin");
        interactSetting.setPluginType("nr-mapping-scheme-plugin");
        interactSetting.setExpose("NewScheme");
        interactSetting.setModalTitle(StringUtils.hasLength(modalTitle) ? modalTitle : this.getTitle());
        return interactSetting;
    }
}

