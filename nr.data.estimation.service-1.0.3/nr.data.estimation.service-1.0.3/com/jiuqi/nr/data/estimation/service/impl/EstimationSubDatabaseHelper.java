/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase
 *  com.jiuqi.nr.data.estimation.sub.database.intf.IDataSchemeSubDatabaseHelper
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.service.impl;

import com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabase;
import com.jiuqi.nr.data.estimation.sub.database.intf.IDataSchemeSubDatabaseHelper;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EstimationSubDatabaseHelper
implements IEstimationSubDatabaseHelper {
    @Resource
    private IRunTimeViewController nrModelService;
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;
    @Resource
    public DataModelService dataModelService;
    @Resource
    private IDataSchemeSubDatabaseHelper schemeSubDatabaseHelper;

    @Override
    public IDataSchemeSubDatabase getSubDatabaseDefine(String formSchemeKey) {
        FormSchemeDefine formScheme = this.nrModelService.getFormScheme(formSchemeKey);
        if (formScheme != null) {
            return this.getSubDatabaseDefine(formScheme);
        }
        return null;
    }

    @Override
    public IDataSchemeSubDatabase getSubDatabaseDefine(FormSchemeDefine formSchemeDefine) {
        TaskDefine taskDefine = this.nrModelService.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        return this.schemeSubDatabaseHelper.querySubDatabaseDefine(dataScheme.getKey(), "_DE_");
    }

    @Override
    public List<TableModelDefine> getTableModelsByFormScheme(String formSchemeKey) {
        FormSchemeDefine formScheme = this.nrModelService.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.nrModelService.queryTaskDefine(formScheme.getTaskKey());
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        List dataFields = this.dataSchemeService.getDeployInfoBySchemeKey(dataScheme.getKey());
        List tableModelIds = dataFields.stream().map(DataFieldDeployInfo::getTableModelKey).distinct().collect(Collectors.toList());
        return this.dataModelService.getTableModelDefinesByIds(tableModelIds);
    }

    @Override
    public List<String> getTableNamesByFormScheme(String formSchemeKey) {
        List<TableModelDefine> tableModels = this.getTableModelsByFormScheme(formSchemeKey);
        return tableModels.stream().map(TableModelDefine::getName).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getOri2CopiedTableMap(IEstimationScheme estimationScheme) {
        IDataSchemeSubDatabase subDatabaseDefine = this.getSubDatabaseDefine(estimationScheme.getFormSchemeDefine());
        List<String> tableNames = this.getTableNamesByFormScheme(estimationScheme.getFormSchemeDefine().getKey());
        HashMap<String, String> oriTable2CopiedTable = new HashMap<String, String>();
        tableNames.forEach(oriTableName -> oriTable2CopiedTable.put((String)oriTableName, subDatabaseDefine.getSubTableCode(oriTableName)));
        return oriTable2CopiedTable;
    }

    @Override
    public Map<String, String> getOtherPrimaryMap(IEstimationScheme estimationScheme) {
        HashMap<String, String> primaryCondition = new HashMap<String, String>();
        primaryCondition.put("ESTIMATION_SCHEME", estimationScheme.getKey());
        return primaryCondition;
    }
}

