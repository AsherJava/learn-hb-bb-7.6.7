/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.table.inner.RenameTableAction
 *  org.json.JSONObject
 */
package com.jiuqi.nr.mapping.view.table.action;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.RenameTableAction;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MSRenameAction
extends RenameTableAction {
    protected final Logger logger = LoggerFactory.getLogger(MSRenameAction.class);
    public static final String ID = "mapping_table_rename";
    private final MappingSchemeService msService;

    public MSRenameAction() {
        this.interactSetting = this.getDefaultInteractSetting(this.getTitle());
        this.msService = (MappingSchemeService)SpringBeanUtils.getBean(MappingSchemeService.class);
    }

    public String getId() {
        return ID;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        JSONObject jsonObject = new JSONObject(param);
        String title = jsonObject.optString("title");
        MappingScheme ms = this.msService.getByKey(jsonObject.optString("id"));
        ms.setTitle(title);
        try {
            this.msService.rename(ms);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return ActionResult.error(null, (String)e.getMessage());
        }
        ActionResult actionResult = ActionResult.success(null, (String)"\u91cd\u547d\u540d\u6210\u529f\uff01");
        actionResult.setRefresh(true);
        return actionResult;
    }

    public ActionInteractSetting getDefaultInteractSetting(String modalTitle) {
        ModalActionInteractSetting interactSetting = new ModalActionInteractSetting();
        interactSetting.setPluginName("nr-mapping-scheme-plugin");
        interactSetting.setPluginType("nr-mapping-scheme-plugin");
        interactSetting.setExpose("RenameScheme");
        interactSetting.setModalTitle(StringUtils.hasLength(modalTitle) ? modalTitle : this.getTitle());
        return interactSetting;
    }
}

