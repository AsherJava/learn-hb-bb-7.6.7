/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentry.paramInfo.FormGroupData
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.etl.utils.EtlEntityUpgraderImpl;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryEntity
extends EtlEntityUpgraderImpl {
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IDataAccessProvider dataAccessProvider;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IEntityDataService entityDataService;
    @Resource
    IRunTimeViewController runtimeView;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;

    public String getFormList(String formSchemeKey, String taskKey, Map<String, DimensionValue> dim) {
        String formKey = "";
        JtableContext context = new JtableContext();
        context.setDimensionSet(dim);
        context.setFormSchemeKey(formSchemeKey);
        context.setTaskKey(taskKey);
        List FormKeyList = this.runtimeView.queryAllFormKeysByFormScheme(formSchemeKey);
        IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskKey, formSchemeKey);
        DimensionCollection dimCollection = DimensionValueSetUtil.buildDimensionCollection(dim, (String)formSchemeKey);
        IBatchAccessResult batchAccessResult = dataAccessService.getWriteAccess(dimCollection, FormKeyList);
        List dimCollectionList = dimCollection.getDimensionCombinations();
        LinkedHashSet<String> forms = new LinkedHashSet<String>();
        for (DimensionCombination dimensionComin : dimCollectionList) {
            for (String key : FormKeyList) {
                IAccessResult accessResult = batchAccessResult.getAccess(dimensionComin, key);
                try {
                    if (!accessResult.haveAccess()) continue;
                    forms.add(key);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (String key : forms) {
            formKey = formKey + key + ";";
        }
        if (formKey.length() == 0) {
            return "";
        }
        return formKey.substring(0, formKey.length() - 1);
    }

    public HashSet<FormData> getFormList(String formSchemeKey, String taskKey, Map<String, DimensionValue> dim, String type) {
        HashSet<FormData> formList = new HashSet<FormData>();
        JtableContext context = new JtableContext();
        context.setDimensionSet(dim);
        context.setFormSchemeKey(formSchemeKey);
        context.setTaskKey(taskKey);
        List runtimeFormList = this.dataEntryParamService.getRuntimeFormList(context);
        for (FormGroupData formGroup : runtimeFormList) {
            if (formGroup.getReports().size() <= 0) continue;
            for (FormData form : formGroup.getReports()) {
                formList.add(form);
            }
        }
        return formList;
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, DimensionValueSet valueSet) {
        IEntityQuery query = this.buildQuery(view, valueSet);
        ExecutorContext context = this.executorContext();
        return this.entityQuerySet(query, context);
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, String period) {
        DimensionValueSet valueSet = new DimensionValueSet();
        if (period != null) {
            valueSet.setValue("DATATIME", (Object)period);
        }
        IEntityQuery query = this.buildQuery(view, valueSet);
        ExecutorContext context = this.executorContext();
        return this.entityQuerySet(query, context);
    }

    public IEntityQuery buildQuery(EntityViewDefine view, DimensionValueSet valueSet) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(view);
        query.setMasterKeys(valueSet);
        return query;
    }

    private ExecutorContext executorContext() {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        return context;
    }

    public String getMainDimName(EntityViewDefine viewDefine) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        return dataAssist.getDimensionName(viewDefine);
    }
}

