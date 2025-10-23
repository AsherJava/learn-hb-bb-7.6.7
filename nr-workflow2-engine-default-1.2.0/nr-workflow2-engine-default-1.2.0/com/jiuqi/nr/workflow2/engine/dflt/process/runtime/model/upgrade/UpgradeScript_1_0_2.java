/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  module.manager.intf.CustomClassExecutor
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.upgrade;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelConstant;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.upgrade.UpgradeScriptBeanUtils;
import com.jiuqi.nr.workflow2.engine.dflt.utils.LogUtils;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import javax.sql.DataSource;
import module.manager.intf.CustomClassExecutor;

public class UpgradeScript_1_0_2
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        IRunTimeViewController viewController = UpgradeScriptBeanUtils.getRunTimeViewController();
        List tasks = viewController.listAllTask();
        for (TaskDefine task : tasks) {
            List fomrSchemes = viewController.listFormSchemeByTask(task.getKey());
            for (FormSchemeDefine formScheme : fomrSchemes) {
                this.upgradeFormScheme(task, formScheme);
            }
        }
    }

    private void upgradeFormScheme(TaskDefine task, FormSchemeDefine formScheme) {
        try {
            DesignDataModelService designDataModelService = UpgradeScriptBeanUtils.getDesignDataModelService();
            String istTableCode = DataModelConstant.getInstanceTableName(formScheme);
            DesignTableModelDefine istTableModel = designDataModelService.getTableModelDefineByCode(istTableCode);
            if (istTableModel == null) {
                return;
            }
            DesignColumnModelDefine lastOperationIdCol = designDataModelService.getColumnModelDefineByCode(istTableModel.getID(), "IST_LASTOPTIONID");
            if (lastOperationIdCol != null) {
                return;
            }
            lastOperationIdCol = designDataModelService.createColumnModelDefine();
            lastOperationIdCol.setTableID(istTableModel.getID());
            DataModelConstant.FIELD_IST_LASTOPTIONID.applyto(lastOperationIdCol);
            designDataModelService.insertColumnModelDefine(lastOperationIdCol);
            DesignIndexModelDefine lastOperationIdIndex = designDataModelService.createIndexModel();
            lastOperationIdIndex.setTableID(istTableModel.getID());
            lastOperationIdIndex.setName(DataModelConstant.getInstanceLastOperationIdIndexName(istTableModel.getName()));
            lastOperationIdIndex.setFieldIDs(lastOperationIdCol.getID());
            lastOperationIdIndex.setType(IndexModelType.NORMAL);
            designDataModelService.addIndexModelDefine(lastOperationIdIndex);
            UpgradeScriptBeanUtils.getDataModelDeployService().deployTable(istTableModel.getID());
        }
        catch (Exception e) {
            LogUtils.LOGGER.error("\u9ed8\u8ba4\u6d41\u7a0b2.0\u6570\u636e\u5e93\u5347\u7ea7\u5931\u8d25\u3002\u4efb\u52a1\uff1a" + task.getTitle() + "[" + task.getTaskCode() + "]\uff0c\u62a5\u8868\u65b9\u6848\uff1a" + formScheme.getTitle() + "[" + formScheme.getFormSchemeCode() + "]\u3002", e);
        }
    }
}

