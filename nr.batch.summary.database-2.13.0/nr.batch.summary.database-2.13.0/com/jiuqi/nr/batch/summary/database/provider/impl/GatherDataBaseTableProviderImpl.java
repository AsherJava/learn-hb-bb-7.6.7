/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider
 *  com.jiuqi.nr.subdatabase.facade.SubDataBase
 */
package com.jiuqi.nr.batch.summary.database.provider.impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.nr.batch.summary.database.provider.GatherDataBaseTableProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GatherDataBaseTableProviderImpl
implements GatherDataBaseTableProvider {
    private static final Logger logger = LoggerFactory.getLogger(GatherDataBaseTableProviderImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private SubDataBaseInfoProvider subDataBaseInfoProvider;

    @Override
    public String getCurrentSplitTableName(ExecutorContext context, String tableName) {
        SubDataBase curDataBase = this.subDataBaseInfoProvider.getCurDataBase();
        if (curDataBase == null) {
            return tableName + "_G_";
        }
        return curDataBase.getCode() + tableName + "_G_";
    }

    private String getDataSchemeKeyFormContext(ExecutorContext context) {
        IFmlExecEnvironment env = context.getEnv();
        ReportFmlExecEnvironment reportEnv = null;
        if (env instanceof ReportFmlExecEnvironment) {
            reportEnv = (ReportFmlExecEnvironment)env;
        }
        FormSchemeDefine formSchemeDefine = null;
        if (reportEnv != null) {
            try {
                formSchemeDefine = reportEnv.getFormSchemeDefine();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        String dataSchemeKey = null;
        if (formSchemeDefine != null) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
            dataSchemeKey = taskDefine.getDataScheme();
        }
        return dataSchemeKey;
    }
}

