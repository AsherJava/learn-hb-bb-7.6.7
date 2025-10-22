/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.message.NrTaskDeleteEvent
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.batch.summary.database.listener;

import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.message.NrTaskDeleteEvent;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CheckTableDeleteListener
implements ApplicationListener<NrTaskDeleteEvent> {
    private static final Logger logger = LoggerFactory.getLogger(CheckTableDeleteListener.class);
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;

    @Override
    public void onApplicationEvent(NrTaskDeleteEvent event) {
        List formSchemeDefines = event.getFormSchemeDefines();
        if (CollectionUtils.isEmpty(formSchemeDefines)) {
            return;
        }
        for (FormSchemeDefine scheme : formSchemeDefines) {
            String schemeCode = scheme.getFormSchemeCode() + "_G_";
            this.deployDeleteTableByCode(CheckTableNameUtil.getAllCKRTableName((String)schemeCode));
            this.deployDeleteTableByCode(CheckTableNameUtil.getCKRTableName((String)schemeCode));
            this.deployDeleteTableByCode(CheckTableNameUtil.getCKDTableName((String)schemeCode));
        }
    }

    public void deployDeleteTableByCode(String tableCode) {
        try {
            DesignTableModelDefine table = this.designDataModelService.getTableModelDefineByCode(tableCode);
            if (table == null) {
                return;
            }
            this.designDataModelService.deleteTableModelDefine(table.getID());
            this.dataModelDeployService.deployTable(table.getID());
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u6570\u636e\u8868".concat(tableCode).concat("\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").concat(e.getMessage()), e);
        }
    }
}

