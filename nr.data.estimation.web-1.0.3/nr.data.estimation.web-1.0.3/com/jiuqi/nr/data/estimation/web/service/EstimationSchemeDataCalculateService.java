/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService
 *  com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.data.logic.facade.extend.FmlExeContextProvider
 *  com.jiuqi.nr.data.logic.facade.param.input.CalculateParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.service.ICalculateService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService;
import com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.data.estimation.web.ext.dataengine.DataEntryCalculateContextExt;
import com.jiuqi.nr.data.estimation.web.request.ActionOfCalculateParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfEstimationParam;
import com.jiuqi.nr.data.logic.facade.extend.FmlExeContextProvider;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class EstimationSchemeDataCalculateService {
    @Resource
    private ICalculateService calculateService;
    @Resource
    private IRunTimeViewController rtvService;
    @Resource
    private IEstimationSchemeUserService schemeUserService;
    @Resource
    private IEstimationSubDatabaseHelper subDatabaseHelper;

    public String calculateWitOneForm(ActionOfCalculateParam actionParameter) {
        CalculateParam calculateParam = this.getCalculateParam(actionParameter.getContext());
        calculateParam.setFormulaSchemeKey(actionParameter.getFormulaSchemeKey());
        calculateParam.setRangeKeys(Collections.singletonList(actionParameter.getContext().getFormKey()));
        return this.calculateService.calculate(calculateParam);
    }

    public String calculateWitAllForm(ActionOfCalculateParam actionParameter) {
        CalculateParam calculateParam = this.getCalculateParam(actionParameter.getContext());
        calculateParam.setFormulaSchemeKey(actionParameter.getFormulaSchemeKey());
        IEstimationScheme estimationScheme = this.getEstimationScheme(actionParameter.getContext(), calculateParam);
        List rangFormKeys = estimationScheme.getEstimationForms().stream().filter(esForm -> esForm.getFormType() == EstimationFormType.inputForm).map(esForm -> esForm.getFormDefine().getKey()).collect(Collectors.toList());
        rangFormKeys.add("00000000-0000-0000-0000-000000000000");
        calculateParam.setRangeKeys(rangFormKeys);
        return this.calculateService.calculate(calculateParam);
    }

    public String doDataCalculate(ActionOfEstimationParam actionParameter) {
        CalculateParam calculateParam = this.getCalculateParam(actionParameter.getContext());
        IEstimationScheme estimationScheme = this.getEstimationScheme(actionParameter.getContext(), calculateParam);
        FormSchemeDefine formSchemeDefine = estimationScheme.getFormSchemeDefine();
        List formDefines = this.rtvService.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
        List rangFormKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        rangFormKeys.add("00000000-0000-0000-0000-000000000000");
        calculateParam.setRangeKeys(rangFormKeys);
        List calcFormulaSchemes = estimationScheme.getCalcFormulaSchemes();
        for (FormulaSchemeDefine formulaScheme : calcFormulaSchemes) {
            calculateParam.setFormulaSchemeKey(formulaScheme.getKey());
            this.calculateService.calculate(calculateParam);
        }
        return "";
    }

    private CalculateParam getCalculateParam(JtableContext context) {
        CalculateParam calculateParam = new CalculateParam();
        calculateParam.setMode(Mode.FORM);
        calculateParam.setVariableMap(context.getVariableMap());
        calculateParam.setDimensionCollection(this.getDimensionCollection(context));
        calculateParam.setFmlExeContextProvider((FmlExeContextProvider)new DataEntryCalculateContextExt(this.schemeUserService, this.rtvService, this.subDatabaseHelper));
        calculateParam.setFmlJIT(true);
        return calculateParam;
    }

    private DimensionCollection getDimensionCollection(JtableContext context) {
        Map dimensionSet = context.getDimensionSet();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (Map.Entry entry : dimensionSet.entrySet()) {
            String name = (String)entry.getKey();
            String value = ((DimensionValue)entry.getValue()).getValue();
            builder.setValue(name, new Object[]{value});
        }
        return builder.getCollection();
    }

    private String getEstimationSchemeKey(JtableContext context) {
        return context.getVariableMap().get("estimationScheme").toString();
    }

    private IEstimationScheme getEstimationScheme(JtableContext context, CalculateParam calculateParam) {
        DimensionValueSet dimValueSet = calculateParam.getDimensionCollection().combineDim();
        String estimationSchemeKey = this.getEstimationSchemeKey(context);
        return this.schemeUserService.findEstimationScheme(estimationSchemeKey, dimValueSet);
    }
}

