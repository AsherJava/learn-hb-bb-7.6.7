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
package com.jiuqi.nr.mapping.view.table.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.mapping.bean.MappingConfig;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.service.MappingSchemeConfigService;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.ModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.table.inner.CopyTableAction;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class MSCopyTableAction
extends CopyTableAction {
    protected final Logger logger = LoggerFactory.getLogger(MSCopyTableAction.class);
    public static final String ID = "mapping_table_copy";
    protected MappingSchemeService schemeService;
    protected MappingSchemeConfigService configService;

    public MSCopyTableAction() {
        this.interactSetting = this.getDefaultInteractSetting(this.getTitle());
        this.schemeService = (MappingSchemeService)SpringBeanUtils.getBean(MappingSchemeService.class);
        this.configService = (MappingSchemeConfigService)SpringBeanUtils.getBean(MappingSchemeConfigService.class);
    }

    public String getId() {
        return ID;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MappingScheme scheme = (MappingScheme)objectMapper.readValue(param, MappingScheme.class);
            String oldKey = scheme.getKey();
            scheme.setKey(UUID.randomUUID().toString());
            this.schemeService.add(scheme);
            MappingConfig mappingConfig = this.configService.query(oldKey);
            if (mappingConfig != null) {
                this.configService.update(scheme.getKey(), mappingConfig);
            }
            this.schemeService.copyMapping(oldKey, scheme.getKey());
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

