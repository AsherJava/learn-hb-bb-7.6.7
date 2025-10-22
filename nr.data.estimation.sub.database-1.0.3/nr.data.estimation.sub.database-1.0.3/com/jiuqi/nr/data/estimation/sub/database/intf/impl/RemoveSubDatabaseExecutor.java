/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.data.estimation.sub.database.intf.impl;

import com.jiuqi.nr.data.estimation.sub.database.common.SubDatabaseLogger;
import com.jiuqi.nr.data.estimation.sub.database.entity.DataSchemeSubDatabaseDefine;
import com.jiuqi.nr.data.estimation.sub.database.intf.DSSubDatabaseExecutor;
import com.jiuqi.nr.data.estimation.sub.database.intf.IMakeSubDatabaseParam;
import com.jiuqi.nr.data.estimation.sub.database.intf.impl.DataSchemeSubDatabaseHelper;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.List;

public class RemoveSubDatabaseExecutor
implements DSSubDatabaseExecutor {
    protected String dataSchemeKey;
    private boolean forceDelete = false;
    protected DataSchemeSubDatabaseHelper helper;

    public RemoveSubDatabaseExecutor(DataSchemeSubDatabaseHelper helper, String dataSchemeKey) {
        this.helper = helper;
        this.dataSchemeKey = dataSchemeKey;
    }

    public RemoveSubDatabaseExecutor(DataSchemeSubDatabaseHelper helper, String dataSchemeKey, boolean forceDelete) {
        this(helper, dataSchemeKey);
        this.forceDelete = forceDelete;
    }

    @Override
    public void execute(IMakeSubDatabaseParam makePara, SubDatabaseLogger logger) {
        double process = 1.0;
        logger.resetProcess();
        logger.logInfo(">>>>>>>\u5f00\u59cb\u5220\u9664\u5206\u5e93", process);
        List<DesignTableModelDefine> tableModels = this.helper.getDesignTableModelsByCopied(this.dataSchemeKey, makePara.getCopiedTableGenerator(this.dataSchemeKey));
        for (DesignTableModelDefine tableModel : tableModels) {
            logger.logInfo("----------------------------------------");
            logger.logInfo("\u6b63\u5728\u5220\u9664\uff1a" + tableModel.getTitle() + "[" + tableModel.getCode() + "] ");
            this.helper.designDataModelService.deleteColumnModelDefineByTable(tableModel.getID());
            this.helper.designDataModelService.deleteIndexsByTable(tableModel.getID());
            this.helper.designDataModelService.deleteTableModelDefine(tableModel.getID());
            this.deployTable(tableModel.getID(), logger);
            logger.addProcess(process / (double)tableModels.size());
        }
        this.deleteSubRecord(makePara);
    }

    private void deployTable(String tableModelId, SubDatabaseLogger logger) {
        try {
            if (this.forceDelete) {
                this.helper.dataModelDeployService.deployTableUnCheck(tableModelId);
            } else {
                this.helper.dataModelDeployService.deployTable(tableModelId);
            }
        }
        catch (Exception e) {
            logger.logError(e.getMessage());
        }
    }

    private void deleteSubRecord(IMakeSubDatabaseParam makePara) {
        String databaseCode = makePara.getSubDatabaseCode();
        DataSchemeSubDatabaseDefine databaseDefine = new DataSchemeSubDatabaseDefine();
        databaseDefine.setDataSchemeKey(this.dataSchemeKey);
        databaseDefine.setDatabaseCode(databaseCode);
        databaseDefine.setDatabaseTitle(makePara.getSubDatabaseTitle(this.dataSchemeKey));
        this.helper.subDatabaseDao.deleteRecord(databaseDefine);
    }
}

