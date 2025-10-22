/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.fmdm.FMDMDataDTO
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.fmdm.IFMDMDataService
 *  com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckVersionObjectInfo;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value="prototype")
public class EntityCheckDWZDMController {
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private static final String CODE = "CODE";
    private static final String ORGCODE = "ORGCODE";
    private static final String NAME = "NAME";

    public String getDwzdmToFmdmDataByMatchingType(EntityCheckVersionObjectInfo versionObjectInfo, String scop) throws ExpressionException {
        String formSchemeKey = versionObjectInfo.getFromSchemeKey();
        IEntityRow row = versionObjectInfo.getEntityTable().findByEntityKey(scop);
        String period = versionObjectInfo.getPeriod();
        int matchingType = versionObjectInfo.getMatchingType();
        IFMDMData fmdmData = versionObjectInfo.getFmdmDataMap().get(scop);
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IExpressionEvaluator evaluator = accessProvider.newExpressionEvaluator();
        EntityViewData masterEntityView = this.jtableParamService.getDwEntity(formSchemeKey);
        EntityViewData periodEntityView = this.jtableParamService.getDataTimeEntity(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(masterEntityView.getDimensionName(), (Object)row.getAsString(CODE));
        dimensionValueSet.setValue(periodEntityView.getDimensionName(), (Object)period);
        JtableContext executorTableContext = new JtableContext();
        executorTableContext.setFormSchemeKey(formSchemeKey);
        executorTableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(executorTableContext);
        String value = "";
        TaskLinkMatchingType type = TaskLinkMatchingType.forValue((int)matchingType);
        switch (type) {
            case MATCHING_TYPE_PRIMARYKEY: {
                value = fmdmData.getValue(CODE).getAsString();
                break;
            }
            case MATCHING_TYPE_CODE: {
                value = fmdmData.getValue(ORGCODE).getAsString();
                break;
            }
            case MATCHING_TYPE_TITLE: {
                value = fmdmData.getValue(NAME).getAsString();
                break;
            }
            case FORM_TYPE_EXPRESSION: {
                value = Convert.toString((Object)evaluator.evalValue(versionObjectInfo.getExpressionFormula(), executorContext, dimensionValueSet));
            }
        }
        return value;
    }

    public Map<String, List<IFMDMData>> getDataMapByMatchingType(String taskKey, String formSchemeKey, String period, int matchingType, String expressionFormula) throws Exception {
        return this.getDataMapByMatchingType(taskKey, formSchemeKey, period, matchingType, expressionFormula, null);
    }

    public Map<String, List<IFMDMData>> getDataMapByMatchingType(String taskKey, String formSchemeKey, String period, int matchingType, String expressionFormula, List<IFMDMData> dataList) throws Exception {
        HashMap<String, List<IFMDMData>> fmdmDataMap = new HashMap<String, List<IFMDMData>>();
        HashMap<String, String> expressFormulaValuesMap = new HashMap<String, String>();
        TaskLinkMatchingType type = TaskLinkMatchingType.forValue((int)matchingType);
        EntityViewData periodEntityView = this.jtableParamService.getDataTimeEntity(formSchemeKey);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(periodEntityView.getDimensionName(), (Object)period);
        if (dataList == null) {
            QueryParamDTO fMDMDataDTO = new QueryParamDTO();
            fMDMDataDTO.setFormSchemeKey(formSchemeKey);
            fMDMDataDTO.setDimensionValueSet(dimensionValueSet);
            fMDMDataDTO.setFilter(true);
            fMDMDataDTO.setAuthorityType(AuthorityType.Read);
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            context.getVariableManager().add(new Variable("associatedTaskKey", "\u5355\u673a\u7248\u83b7\u53d6\u6838\u5bf9\u4efb\u52a1key", 6, (Object)taskKey));
            fMDMDataDTO.setContext((IContext)context);
            dataList = this.fmdmDataService.list((FMDMDataDTO)fMDMDataDTO);
        }
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IExpressionEvaluator evaluator = accessProvider.newExpressionEvaluator();
        JtableContext executorTableContext = new JtableContext();
        executorTableContext.setTaskKey(taskKey);
        executorTableContext.setFormSchemeKey(formSchemeKey);
        executorTableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(executorTableContext);
        executorContext.getVariableManager().add(new Variable("associatedTaskKey", "\u5355\u673a\u7248\u83b7\u53d6\u6838\u5bf9\u4efb\u52a1key", 6, (Object)taskKey));
        ArrayList<String> list = new ArrayList<String>();
        list.add(expressionFormula);
        Map expFormulaValueMap = evaluator.evalBatch(list, executorContext, dimensionValueSet);
        for (Map.Entry entry : expFormulaValueMap.entrySet()) {
            Object obj;
            String key = (String)entry.getKey();
            Object[] objects = (Object[])entry.getValue();
            if (objects == null || objects.length <= 0 || !((obj = objects[0]) instanceof String) || obj == null || !StringUtils.isNotEmpty((String)((String)obj))) continue;
            expressFormulaValuesMap.put(key, (String)obj);
        }
        if (dataList != null && dataList.size() > 0) {
            switch (type) {
                case MATCHING_TYPE_PRIMARYKEY: {
                    ArrayList<IFMDMData> ifmdmDatalist;
                    for (IFMDMData data : dataList) {
                        if (fmdmDataMap.containsKey(data.getValue(CODE).getAsString())) {
                            ((List)fmdmDataMap.get(data.getValue(CODE).getAsString())).add(data);
                            continue;
                        }
                        ifmdmDatalist = new ArrayList<IFMDMData>();
                        ifmdmDatalist.add(data);
                        fmdmDataMap.put(data.getValue(CODE).getAsString(), ifmdmDatalist);
                    }
                    break;
                }
                case MATCHING_TYPE_CODE: {
                    ArrayList<IFMDMData> ifmdmDatalist;
                    for (IFMDMData data : dataList) {
                        if (fmdmDataMap.containsKey(data.getValue(ORGCODE).getAsString())) {
                            ((List)fmdmDataMap.get(data.getValue(ORGCODE).getAsString())).add(data);
                            continue;
                        }
                        ifmdmDatalist = new ArrayList();
                        ifmdmDatalist.add(data);
                        fmdmDataMap.put(data.getValue(ORGCODE).getAsString(), ifmdmDatalist);
                    }
                    break;
                }
                case MATCHING_TYPE_TITLE: {
                    ArrayList<IFMDMData> ifmdmDatalist;
                    for (IFMDMData data : dataList) {
                        if (fmdmDataMap.containsKey(data.getValue(NAME).getAsString())) {
                            ((List)fmdmDataMap.get(data.getValue(NAME).getAsString())).add(data);
                            continue;
                        }
                        ifmdmDatalist = new ArrayList();
                        ifmdmDatalist.add(data);
                        fmdmDataMap.put(data.getValue(NAME).getAsString(), ifmdmDatalist);
                    }
                    break;
                }
                case FORM_TYPE_EXPRESSION: {
                    ArrayList<IFMDMData> ifmdmDatalist;
                    for (IFMDMData data : dataList) {
                        if (fmdmDataMap.containsKey(expressFormulaValuesMap.get(data.getValue(CODE).getAsString()))) {
                            ((List)fmdmDataMap.get(expressFormulaValuesMap.get(data.getValue(CODE).getAsString()))).add(data);
                            continue;
                        }
                        ifmdmDatalist = new ArrayList();
                        ifmdmDatalist.add(data);
                        fmdmDataMap.put((String)expressFormulaValuesMap.get(data.getValue(CODE).getAsString()), (List<IFMDMData>)ifmdmDatalist);
                    }
                    break;
                }
            }
        }
        return fmdmDataMap;
    }
}

