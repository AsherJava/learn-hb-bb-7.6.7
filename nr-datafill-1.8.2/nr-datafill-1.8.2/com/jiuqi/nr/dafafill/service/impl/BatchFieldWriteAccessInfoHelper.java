/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchZBAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.FieldReadWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.model.enums.TableType;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchFieldWriteAccessInfoHelper {
    @Autowired
    protected IDFDimensionQueryFieldParser dFDimensionParser;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public FieldReadWriteAccessResultInfo buildAccessResult(DataFillContext context, DimensionValueSet dimensionValueSet, List<String> queryZbIds) {
        TableType tableType = context.getModel().getTableType();
        if (TableType.MASTER == tableType) {
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            QueryField dwQueryField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
            QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
            String dwDimensionName = this.dFDimensionParser.getDimensionNameByField(dwQueryField);
            String periodDimensionName = this.dFDimensionParser.getDimensionNameByField(periodField);
            Object value = dimensionValueSet.getValue(periodDimensionName);
            Date startDate = PeriodUtils.getStartDateOfPeriod((String)value.toString(), (boolean)true);
            Object dwValue = dimensionValueSet.getValue(dwDimensionName);
            HashSet<String> entityKeyDatas = new HashSet<String>();
            if (dwValue instanceof String) {
                entityKeyDatas.add(dwValue.toString());
            } else {
                entityKeyDatas.addAll((Collection)dwValue);
            }
            Map<String, Boolean> result = new HashMap<String, Boolean>();
            try {
                result = this.entityAuthorityService.canEditEntity(dwQueryField.getId(), entityKeyDatas, startDate);
            }
            catch (UnauthorizedEntityException unauthorizedEntityException) {
                // empty catch block
            }
            return new FieldReadWriteAccessResultInfo(result, dwDimensionName);
        }
        if (TableType.FMDM == tableType) {
            HashMap<String, FieldReadWriteAccessResultInfo> result = new HashMap<String, FieldReadWriteAccessResultInfo>();
            Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dFDimensionParser.getFieldTypeQueryFields(context);
            QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
            String periodDimensionName = this.dFDimensionParser.getDimensionNameByField(periodField);
            Object periodValue = dimensionValueSet.getValue(periodDimensionName);
            Map<String, String> extendedData = context.getModel().getExtendedData();
            String taskKey = extendedData.get("TASKKEY");
            if (null == taskKey) {
                taskKey = extendedData.get("taskKey");
            }
            List periodList = new ArrayList<String>();
            if (periodValue instanceof List) {
                periodList = (List)periodValue;
            } else {
                periodList.add(periodValue.toString());
            }
            HashMap<String, String> formSchemeMap = new HashMap<String, String>();
            HashMap<String, String> formKeyMap = new HashMap<String, String>();
            for (String timeValue : periodList) {
                SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(timeValue, taskKey);
                String schemeKey = schemePeriodLinkByPeriodAndTask.getSchemeKey();
                formSchemeMap.put(timeValue, schemeKey);
                formKeyMap.put(timeValue, this.runTimeViewController.getFmdmFormByFormScheme(schemeKey).getKey());
            }
            List hiddenQueryField = (List)context.getCaches().get("hiddenQueryField");
            Map<String, QueryField> queryFieldMap = this.dFDimensionParser.getDimensionNameQueryFieldsMap(context);
            List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
            List<QueryField> zbList = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
            String dataSchemeKey = this.dFDimensionParser.getDataSchemeKey(zbList);
            Set keySet = formSchemeMap.keySet();
            for (String time : keySet) {
                DimensionValueSet temp = new DimensionValueSet(dimensionValueSet);
                temp.setValue(periodDimensionName, (Object)time);
                DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
                if (hiddenQueryField != null && !hiddenQueryField.isEmpty()) {
                    for (QueryField field : hiddenQueryField) {
                        DimensionProviderData dimensionProviderData = new DimensionProviderData();
                        dimensionProviderData.setDataSchemeKey(dataSchemeKey);
                        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", dimensionProviderData);
                        dimensionCollectionBuilder.addVariableDimension(field.getCode(), field.getId(), dimensionProvider);
                        temp.clearValue(field.getCode());
                    }
                }
                for (int i = 0; i < temp.size(); ++i) {
                    if (queryFieldMap.containsKey(temp.getName(i))) {
                        QueryField field;
                        field = queryFieldMap.get(temp.getName(i));
                        dimensionCollectionBuilder.setEntityValue(temp.getName(i), field.getSimplifyFullCode(), new Object[]{temp.getValue(i)});
                        continue;
                    }
                    dimensionCollectionBuilder.setValue(temp.getName(i), new Object[]{temp.getValue(i)});
                }
                String formSchemeKey = (String)formSchemeMap.get(time);
                String formKey = (String)formKeyMap.get(time);
                IDataAccessService dataAccessService = this.dataAccessServiceProvider.getDataAccessService(taskKey, formSchemeKey);
                IBatchAccessResult batchAccessResult = dataAccessService.getWriteAccess(dimensionCollectionBuilder.getCollection(), Collections.singletonList(formKey));
                result.put(time, new FieldReadWriteAccessResultInfo(batchAccessResult, formKey));
            }
            return new FieldReadWriteAccessResultInfo(periodDimensionName, result);
        }
        Map<String, String> extendedData = context.getModel().getExtendedData();
        IDataAccessService dataAccessService = null;
        String taskKey = "";
        if (extendedData != null) {
            taskKey = extendedData.get("TASKKEY");
        }
        if (null == taskKey || taskKey.length() == 0) {
            dataAccessService = this.dataAccessServiceProvider.getZBDataAccessService();
        } else {
            ArrayList<String> taskKeys = new ArrayList<String>();
            taskKeys.add(taskKey);
            dataAccessService = this.dataAccessServiceProvider.getZBDataAccessService(taskKeys);
        }
        List hiddenQueryField = (List)context.getCaches().get("hiddenQueryField");
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        Map<String, QueryField> queryFieldMap = this.dFDimensionParser.getDimensionNameQueryFieldsMap(context);
        if (hiddenQueryField != null && !hiddenQueryField.isEmpty()) {
            List<QueryField> displayCols = this.dFDimensionParser.getDisplayColsQueryFields(context);
            List<QueryField> zbList = displayCols.stream().filter(e -> e.getFieldType() == FieldType.ZB || e.getFieldType() == FieldType.FIELD || e.getFieldType() == FieldType.TABLEDIMENSION).collect(Collectors.toList());
            String dataSchemeKey = this.dFDimensionParser.getDataSchemeKey(zbList);
            for (QueryField field : hiddenQueryField) {
                DimensionProviderData dimensionProviderData = new DimensionProviderData();
                dimensionProviderData.setDataSchemeKey(dataSchemeKey);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", dimensionProviderData);
                dimensionCollectionBuilder.addVariableDimension(field.getCode(), field.getId(), dimensionProvider);
                dimensionValueSet.clearValue(field.getCode());
            }
        }
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            if (queryFieldMap.containsKey(dimensionValueSet.getName(i))) {
                QueryField field = queryFieldMap.get(dimensionValueSet.getName(i));
                dimensionCollectionBuilder.setEntityValue(dimensionValueSet.getName(i), field.getSimplifyFullCode(), new Object[]{dimensionValueSet.getValue(i)});
                continue;
            }
            dimensionCollectionBuilder.setValue(dimensionValueSet.getName(i), new Object[]{dimensionValueSet.getValue(i)});
        }
        IBatchZBAccessResult batchZBAccessResult = dataAccessService.getZBWriteAccess(dimensionCollectionBuilder.getCollection(), queryZbIds);
        return new FieldReadWriteAccessResultInfo(batchZBAccessResult);
    }
}

