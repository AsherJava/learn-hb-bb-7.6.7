/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.subdatabase.controller.ISplitTableHelperExecutor
 *  com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider
 *  com.jiuqi.nr.subdatabase.facade.SubDataBase
 */
package com.jiuqi.nr.batch.summary.database.provider.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.batch.summary.database.provider.impl.GatherDataBaseTableProviderImpl;
import com.jiuqi.nr.batch.summary.database.service.impl.GatherSubDataBaseServiceImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.subdatabase.controller.ISplitTableHelperExecutor;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class GatherDataBaseSplitTableImpl
implements ISplitTableHelperExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GatherDataBaseTableProviderImpl.class);
    @Autowired
    private SubDataBaseInfoProvider subDataBaseInfoProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private GatherSubDataBaseServiceImpl gatherSubDataBaseService;

    public boolean isEnable(ExecutorContext context, String tableName) {
        if (context.getVariableManager() == null) {
            return false;
        }
        Variable variable = context.getVariableManager().find("batchGatherSchemeCode");
        try {
            if (variable == null) {
                return false;
            }
            String gatherSchemeCode = variable.getVarValue((IContext)context).toString();
            if (!StringUtils.isEmpty(gatherSchemeCode)) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public int getOrder() {
        return 1;
    }

    public String getCurrentSplitTableName(ExecutorContext context, String tableName) {
        Set<String> checkTables;
        String dataSchemeKeyFormContext = this.getDataSchemeKeyFormContext(context);
        String taskKey = this.getTasKey(context);
        SubDataBase curDataBase = this.subDataBaseInfoProvider.getCurDataBase();
        Set<Object> allTables = new HashSet();
        if (!StringUtils.isEmpty(dataSchemeKeyFormContext)) {
            allTables = this.gatherSubDataBaseService.getTablesFromDataScheme(dataSchemeKeyFormContext);
        }
        if (!CollectionUtils.isEmpty(checkTables = this.gatherSubDataBaseService.getCheckTables(taskKey))) {
            allTables.addAll(checkTables);
        }
        if (!allTables.contains(tableName)) {
            return tableName;
        }
        if (curDataBase == null) {
            return tableName + "_G_";
        }
        return curDataBase.getCode() + tableName + "_G_";
    }

    public Map<String, String> getSumSchemeKey(ExecutorContext context, String splitTableName) {
        HashMap<String, String> result = new HashMap<String, String>();
        Variable variable = context.getVariableManager().find("batchGatherSchemeCode");
        try {
            String gatherSchemeCode = variable.getVarValue((IContext)context).toString();
            result.put("GATHER_SCHEME_CODE", gatherSchemeCode);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private String getDataSchemeKeyFormContext(ExecutorContext context) {
        Variable dataSchemeVar = context.getVariableManager().find("NR.var.dataScheme");
        String dataSchemeKey = null;
        if (dataSchemeVar != null) {
            try {
                dataSchemeKey = (String)dataSchemeVar.getVarValue(null);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (!StringUtils.isEmpty(dataSchemeKey)) {
            return dataSchemeKey;
        }
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
        if (formSchemeDefine != null) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
            dataSchemeKey = taskDefine.getDataScheme();
        }
        return dataSchemeKey;
    }

    private String getTasKey(ExecutorContext context) {
        String taskKey = null;
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
        if (formSchemeDefine != null) {
            taskKey = formSchemeDefine.getTaskKey();
        }
        return taskKey;
    }
}

