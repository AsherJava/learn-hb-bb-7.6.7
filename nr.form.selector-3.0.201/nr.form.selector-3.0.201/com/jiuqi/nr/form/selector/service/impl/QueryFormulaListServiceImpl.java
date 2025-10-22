/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.form.selector.service.impl;

import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.form.selector.entity.FormulaData;
import com.jiuqi.nr.form.selector.entity.FormulaDataInputParam;
import com.jiuqi.nr.form.selector.entity.FormulaTableDataSet;
import com.jiuqi.nr.form.selector.entity.OneAuditTypeData;
import com.jiuqi.nr.form.selector.entity.OneFormFormulaOperator;
import com.jiuqi.nr.form.selector.service.IQueryAllAuditTypeService;
import com.jiuqi.nr.form.selector.service.IQueryFormulaListService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryFormulaListServiceImpl
implements IQueryFormulaListService {
    public static final String CALCULATE = "calculate";
    public static final String CHECK = "check";
    @Autowired
    IFormulaRunTimeController iFormulaRunTimeController;
    @Autowired
    IQueryAllAuditTypeService queryAllAuditTypeServiceImpl;
    @Resource
    private IRunTimeViewController rtViewService;

    @Override
    public FormulaTableDataSet queryFormulaListOfForms(FormulaDataInputParam formulaDataInputParam) throws Exception {
        FormulaTableDataSet tableDataSet = new FormulaTableDataSet();
        ArrayList<FormulaData> formulaDataListOfReturn = new ArrayList();
        LinkedList<OneAuditTypeData> allAuditType = this.queryAllAuditTypeServiceImpl.queryAllAuditType();
        HashMap<Integer, String> allAuditTypeMap = new HashMap<Integer, String>();
        for (OneAuditTypeData oneAuditTypeData : allAuditType) {
            allAuditTypeMap.put(oneAuditTypeData.getValue(), oneAuditTypeData.getLabel());
        }
        formulaDataListOfReturn = this.queryAllFormFormulas(formulaDataInputParam, this.iFormulaRunTimeController, allAuditTypeMap);
        tableDataSet.setFormFormulas(this.getFormFormulas(formulaDataListOfReturn));
        formulaDataListOfReturn = this.splitPage(formulaDataListOfReturn, formulaDataInputParam);
        tableDataSet.setPageData(formulaDataListOfReturn);
        return tableDataSet;
    }

    public List<FormulaData> queryAllFormFormulas(FormulaDataInputParam formulaDataInputParam, IFormulaRunTimeController iFormulaRunTimeController, Map<Integer, String> allAuditTypeMap) {
        List<FormulaData> returnFormulaDatas = new ArrayList<FormulaData>();
        OneFormFormulaOperator oneFormFormulaOperator = new OneFormFormulaOperator(formulaDataInputParam.getFormulaSchemeKey(), formulaDataInputParam.getSearch(), formulaDataInputParam.getFormulaType(), formulaDataInputParam.getCheckType());
        if (formulaDataInputParam.getSearch() == null && formulaDataInputParam.getCheckType().size() == 0) {
            for (String formKey : formulaDataInputParam.getFormKeys()) {
                List<FormulaDefine> resultFormulaDefine = oneFormFormulaOperator.notCheckTypesAndSearchInfoFilterAll(formKey, iFormulaRunTimeController);
                returnFormulaDatas = this.convoertAndAddFormulaDefinesToFormulaDatas(returnFormulaDatas, resultFormulaDefine, formKey, allAuditTypeMap);
            }
        } else {
            for (String formKey : formulaDataInputParam.getFormKeys()) {
                List<FormulaDefine> resultFormulaDefine = oneFormFormulaOperator.filterAll(formKey, iFormulaRunTimeController);
                returnFormulaDatas = this.convoertAndAddFormulaDefinesToFormulaDatas(returnFormulaDatas, resultFormulaDefine, formKey, allAuditTypeMap);
            }
        }
        return returnFormulaDatas;
    }

    List<FormulaData> convoertAndAddFormulaDefinesToFormulaDatas(List<FormulaData> formuladatas, List<FormulaDefine> formulaDefines, String formKey, Map<Integer, String> allAuditTypeMap) {
        for (FormulaDefine formulaDefine : formulaDefines) {
            FormulaData formulaData = new FormulaData(formulaDefine, allAuditTypeMap);
            formulaData.setFormKey(formKey);
            formuladatas.add(formulaData);
        }
        return formuladatas;
    }

    private List<FormulaData> splitPage(List<FormulaData> formulaDataListOfReturn, FormulaDataInputParam formulaDataInputParam) {
        if (!formulaDataListOfReturn.isEmpty() && formulaDataInputParam.getOffset() > 0 && formulaDataInputParam.getLimit() > 0) {
            int beginInfo;
            int endInfo = (beginInfo = (formulaDataInputParam.getOffset() - 1) * formulaDataInputParam.getLimit()) + formulaDataInputParam.getLimit();
            formulaDataListOfReturn = formulaDataListOfReturn.subList(beginInfo, endInfo < formulaDataListOfReturn.size() ? endInfo : formulaDataListOfReturn.size());
        }
        return formulaDataListOfReturn;
    }

    private Map<String, List<String>> getFormFormulas(List<FormulaData> formulaData) {
        HashMap<String, List<String>> formFormulas = new HashMap<String, List<String>>();
        for (FormulaData formula : formulaData) {
            String formKey = formula.getFormKey();
            if (!formFormulas.containsKey(formKey)) {
                formFormulas.put(formKey, new ArrayList());
            }
            ((List)formFormulas.get(formKey)).add(formula.getKey());
        }
        return formFormulas;
    }

    @Override
    public int getFormulaNumByType(String formulaSchemeKey, String formKey, String formulaType) {
        int num = 0;
        List formulaDefines = this.iFormulaRunTimeController.getAllFormulasInForm(formulaSchemeKey, formKey);
        for (FormulaDefine formulaDefine : formulaDefines) {
            if (CALCULATE.equals(formulaType) && formulaDefine.getUseCalculate()) {
                ++num;
            }
            if (!CHECK.equals(formulaType) || !formulaDefine.getUseCheck()) continue;
            ++num;
        }
        return num;
    }
}

