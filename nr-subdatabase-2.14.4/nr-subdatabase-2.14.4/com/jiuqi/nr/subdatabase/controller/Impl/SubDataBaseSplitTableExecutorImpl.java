/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.subdatabase.controller.Impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.subdatabase.config.SubDataBaseConfiguration;
import com.jiuqi.nr.subdatabase.controller.ISplitTableHelperExecutor;
import com.jiuqi.nr.subdatabase.controller.SubDataBaseInfoProvider;
import com.jiuqi.nr.subdatabase.facade.SubDataBase;
import com.jiuqi.nr.subdatabase.provider.SubDataBaseCustomTableProvider;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class SubDataBaseSplitTableExecutorImpl
implements ISplitTableHelperExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SubDataBaseSplitTableExecutorImpl.class);
    @Autowired
    private SubDataBaseInfoProvider subDataBaseInfoProvider;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private SubDataBaseService subDataBaseService;
    @Autowired(required=false)
    private List<SubDataBaseCustomTableProvider> customTableProviders;
    @Autowired
    private SubDataBaseConfiguration subDataBaseConfiguration;

    @Override
    public String getCurrentSplitTableName(ExecutorContext context, String tableName) {
        String anotherSubDataBaseCode = this.getSubDataBaseCodeFormContext(context);
        if (anotherSubDataBaseCode != null) {
            return anotherSubDataBaseCode + tableName;
        }
        SubDataBase curDataBase = this.subDataBaseInfoProvider.getCurDataBase();
        if (curDataBase != null) {
            if (tableName.equals(curDataBase.getDefaultDBOrgCateGoryName())) {
                return curDataBase.getOrgCateGoryName();
            }
            String dataSchemeInContext = this.getDataSchemeKeyFormContext(context);
            if (dataSchemeInContext != null) {
                if (curDataBase.getDataScheme().equals(dataSchemeInContext)) {
                    Set<String> tableNames = this.subDataBaseService.getTablesFromDataScheme(dataSchemeInContext);
                    if (tableNames.contains(tableName)) {
                        return curDataBase.getCode() + tableName;
                    }
                    String tasKey = this.getTasKey(context);
                    if (StringUtils.hasText(tasKey) && !CollectionUtils.isEmpty(this.customTableProviders)) {
                        for (SubDataBaseCustomTableProvider customTableProvider : this.customTableProviders) {
                            if (!customTableProvider.getCustomTableNames(tasKey).contains(tableName)) continue;
                            return curDataBase.getCode() + tableName;
                        }
                    }
                    return this.getTableNameBySameTitleSDB(tableName, curDataBase);
                }
                return this.getTableNameBySameTitleSDB(tableName, curDataBase);
            }
        }
        return tableName;
    }

    private String getTableNameBySameTitleSDB(String tableName, SubDataBase subDataBase) {
        if (this.subDataBaseConfiguration.isQuerySameTitleSubDBByCode()) {
            return this.getSameCodeSubDataBaseTableName(tableName, subDataBase.getCode());
        }
        return this.getSameTitleSubDataBaseTableName(tableName, subDataBase.getTitle());
    }

    private String getSameCodeSubDataBaseTableName(String tableName, String code) {
        SubDataBase linkSubDataBase;
        String dataSchemeKey = this.getDataSchemeKeyByTableName(tableName);
        if (StringUtils.hasText(dataSchemeKey) && (linkSubDataBase = this.subDataBaseService.getSubDataBaseObjByCode(dataSchemeKey, code)) != null) {
            return code + tableName;
        }
        return tableName;
    }

    private String getSameTitleSubDataBaseTableName(String tableName, String title) {
        SubDataBase linkSubDataBase;
        String dataSchemeKey = this.getDataSchemeKeyByTableName(tableName);
        if (StringUtils.hasText(dataSchemeKey) && (linkSubDataBase = this.subDataBaseService.getSameTitleSubDataBase(dataSchemeKey, title)) != null) {
            return linkSubDataBase.getCode() + tableName;
        }
        return tableName;
    }

    private String getDataSchemeKeyByTableName(String tableName) {
        String dataSchemeKey = "";
        List deployInfoByTableName = this.runtimeDataSchemeService.getDeployInfoByTableName(tableName);
        if (!CollectionUtils.isEmpty(deployInfoByTableName)) {
            dataSchemeKey = ((DataFieldDeployInfo)deployInfoByTableName.get(0)).getDataSchemeKey();
        }
        return dataSchemeKey;
    }

    private String getSubDataBaseCodeFormContext(ExecutorContext context) {
        Variable codeVar = context.getVariableManager().find("NRDT.var.newDatabaseCode");
        String code = null;
        if (codeVar != null) {
            try {
                code = (String)codeVar.getVarValue(null);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return code;
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

    @Override
    public boolean isEnable(ExecutorContext context, String tableName) {
        return true;
    }

    @Override
    public int getOrder() {
        return 5;
    }
}

