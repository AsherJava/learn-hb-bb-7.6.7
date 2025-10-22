/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.configuration.controller.ISystemOptionManager
 *  com.jiuqi.nr.data.engine.gather.GatherEntityMap
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.UUIDUtil
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.configuration.controller.ISystemOptionManager;
import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IBatchDataSumHandler;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.UUIDUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchDataSumHandler
implements IBatchDataSumHandler {
    @Autowired
    private ISystemOptionManager systemOptionManager;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private IJtableParamService jtableParamService;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void afterBatchDataSum(String targetKey, BatchDataSumInfo batchDataSumInfo, GatherEntityMap gatherEntityMap) {
        JtableContext jtableContext = batchDataSumInfo.getContext();
        Object formulaScheme = this.systemOptionManager.getObject("CALCULATION_AFTER_DATASUM", jtableContext.getTaskKey(), jtableContext.getFormSchemeKey());
        List<String> gatherEntitys = this.getGatherEntitys(targetKey, batchDataSumInfo, gatherEntityMap);
        if (formulaScheme == null || StringUtils.isEmpty((String)formulaScheme.toString()) || gatherEntitys.size() <= 0) {
            return;
        }
        String formulaSchemeKey = formulaScheme.toString();
        FormulaSchemeDefine schemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (schemeDefine == null) {
            return;
        }
        JtableContext calcContext = new JtableContext(batchDataSumInfo.getContext());
        calcContext.setFormulaSchemeKey(formulaSchemeKey);
        BatchCalculateInfo calcInfo = new BatchCalculateInfo();
        calcInfo.setContext(calcContext);
        calcInfo.setFormSchemeKey(calcContext.getFormSchemeKey());
        calcInfo.setFormulaSchemeKey(formulaSchemeKey);
        calcInfo.setTaskKey(calcContext.getTaskKey());
        Map<String, List<String>> formulas = this.getFormulas(batchDataSumInfo.getFormKeys());
        Map<String, DimensionValue> dimensionSet = this.getDimensionSet(batchDataSumInfo.getContext(), gatherEntitys);
        calcInfo.setFormulas(formulas);
        calcInfo.setDimensionSet(dimensionSet);
        this.batchCalculateService.batchCalculateForm(calcInfo);
    }

    private List<String> getGatherEntitys(String targetKey, BatchDataSumInfo batchDataSumInfo, GatherEntityMap gatherEntityMap) {
        ArrayList<String> gatherEntitys = new ArrayList<String>();
        if (!batchDataSumInfo.isRecursive() && !batchDataSumInfo.isDifference()) {
            gatherEntitys.add(targetKey);
        } else if (gatherEntityMap != null && gatherEntityMap.getGatherEntitys() != null) {
            gatherEntitys.addAll(gatherEntityMap.getGatherEntitys());
        }
        return gatherEntitys;
    }

    private Map<String, DimensionValue> getDimensionSet(JtableContext context, List<String> gatherEntitys) {
        Map dimensionSet = context.getDimensionSet();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
        String entityList = String.join((CharSequence)";", gatherEntitys);
        DimensionValue dimensionValue = (DimensionValue)dimensionSet.get(dwEntity.getDimensionName());
        dimensionValue.setValue(entityList);
        return dimensionSet;
    }

    private Map<String, List<String>> getFormulas(String formKeys) {
        String[] formList;
        HashMap<String, List<String>> formulas = new HashMap<String, List<String>>();
        if (StringUtils.isEmpty((String)formKeys)) {
            return formulas;
        }
        for (String formKey : formList = formKeys.split(";")) {
            formulas.put(formKey, new ArrayList());
        }
        formulas.put(UUIDUtil.emptyID.toString(), new ArrayList());
        return formulas;
    }
}

