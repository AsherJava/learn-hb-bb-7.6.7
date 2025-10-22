/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  javax.annotation.Resource
 */
package nr.single.data.datacopy.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.sql.DataSource;
import nr.single.data.bean.TaskCopyContext;
import nr.single.data.datacopy.ITaskDataCopyCheckInfoService;
import nr.single.data.datain.service.ITaskFileBatchImportDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskDataCopyCheckInfoServiceImpl
implements ITaskDataCopyCheckInfoService {
    private static final Logger logger = LoggerFactory.getLogger(TaskDataCopyCheckInfoServiceImpl.class);
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Resource
    private IFormulaCheckDesService formulaCheckService;
    @Autowired
    private IFormulaRunTimeController formulaController;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ITaskFileBatchImportDataService batchImportService;

    @Override
    public String copyDataChecInfos(TaskCopyContext context, String formSchemeKey, String periodCode, String oldformScheme, String oldPeriod, String copyErrorInfoforms, AsyncTaskMonitor monitor) throws Exception {
        String errorInfo = "";
        ArrayList<FormDefine> copyForms = new ArrayList<FormDefine>();
        List allForms = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        HashMap<String, FormDefine> formMap = new HashMap<String, FormDefine>();
        for (FormDefine form : allForms) {
            formMap.put(form.getFormCode(), form);
        }
        if (StringUtils.isNotEmpty((String)copyErrorInfoforms)) {
            String[] forms;
            for (String formCode : forms = copyErrorInfoforms.split(";")) {
                if (!formMap.containsKey(formCode)) continue;
                copyForms.add((FormDefine)formMap.get(formCode));
            }
        } else {
            copyForms.addAll(allForms);
        }
        try {
            this.copyDataErrorInfoByForms(context, formSchemeKey, periodCode, oldformScheme, oldPeriod, copyForms, monitor);
            logger.info("\u590d\u5236\u51fa\u9519\u8bf4\u660e\u5b8c\u6210");
        }
        catch (Exception ex) {
            logger.info("\u590d\u5236\u51fa\u9519\u8bf4\u660e\u51fa\u9519\uff1a" + ex.getMessage());
            logger.error(ex.getMessage(), ex);
        }
        return errorInfo;
    }

    private void copyDataErrorInfoByForms(TaskCopyContext context, String formSchemeKey, String periodCode, String oldformScheme, String oldPeriod, List<FormDefine> copyForms, AsyncTaskMonitor monitor) throws Exception {
        FormulaCheckDesQueryInfo formulaCheckInfo = this.getFormulaCheckInfo(context, formSchemeKey, context.getEntityCompanyType(), periodCode);
        List checkInfos = this.formulaCheckService.queryFormulaCheckDes(formulaCheckInfo);
        FormulaCheckDesQueryInfo oldFormulaCheckInfo = this.getFormulaCheckInfo(context, oldformScheme, context.getOldEntityCompanyType(), oldPeriod);
        List oldCheckInfos = this.formulaCheckService.queryFormulaCheckDes(oldFormulaCheckInfo);
        FormulaCheckDesBatchSaveInfo formulaCheckDesBatchSaveInfo = new FormulaCheckDesBatchSaveInfo();
        List fmlSchemeDefins = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (null == fmlSchemeDefins || fmlSchemeDefins.size() <= 0) {
            return;
        }
        HashMap<String, FormulaSchemeDefine> fmlSchemeDic = new HashMap<String, FormulaSchemeDefine>();
        StringBuilder sp = new StringBuilder();
        FormulaSchemeDefine defFormulaScheme = null;
        for (FormulaSchemeDefine formulaScheme : fmlSchemeDefins) {
            if (formulaScheme.isDefault() && FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT == formulaScheme.getFormulaSchemeType()) {
                defFormulaScheme = formulaScheme;
            }
            fmlSchemeDic.put(formulaScheme.getTitle(), formulaScheme);
            if (sp.length() > 0) {
                sp.append(";");
            }
            sp.append(formulaScheme.getKey());
        }
        if (defFormulaScheme != null && !fmlSchemeDic.containsKey("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848")) {
            fmlSchemeDic.put("\u9ed8\u8ba4\u516c\u5f0f\u65b9\u6848", defFormulaScheme);
        }
        formulaCheckDesBatchSaveInfo.setQueryInfo(formulaCheckInfo);
        HashMap<String, Map<String, String>> allFormulaCache = new HashMap<String, Map<String, String>>();
        HashMap<String, Map<String, FormulaDefine>> allFormulaDic = new HashMap<String, Map<String, FormulaDefine>>();
        ArrayList<FormulaCheckDesInfo> desInfos = new ArrayList<FormulaCheckDesInfo>(oldCheckInfos.size());
        for (FormulaCheckDesInfo oldInfo : oldCheckInfos) {
            FormDefine formDefine;
            FormulaSchemeDefine formulaScheme;
            String orgCode = null;
            if (oldInfo.getDimensionSet().containsKey(context.getEntityCompanyType())) {
                orgCode = ((DimensionValue)oldInfo.getDimensionSet().get(context.getEntityCompanyType())).getValue();
            }
            FormulaSchemeDefine oldFormulaScheme = this.formulaController.queryFormulaSchemeDefine(oldInfo.getFormulaSchemeKey());
            FormDefine oldForm = this.runTimeAuthViewController.queryFormById(oldInfo.getFormKey(), orgCode, context.getEntityId());
            if (oldForm == null) continue;
            FormulaDefine oldFormula = this.formulaController.queryFormulaDefine(oldInfo.getFormulaKey());
            String formulaCode = oldInfo.getFormulaCode();
            if (oldFormula != null) {
                formulaCode = oldFormula.getCode();
            }
            if ((formulaScheme = (FormulaSchemeDefine)fmlSchemeDic.get(oldFormulaScheme.getTitle())) == null || (formDefine = this.runTimeAuthViewController.queryFormByCodeInScheme(formSchemeKey, oldForm.getFormCode(), orgCode, context.getEntityId())) == null || !copyForms.contains(formDefine)) continue;
            Map<String, FormulaDefine> formFormulas = null;
            String aSchemeFormKey = formulaScheme.getKey() + "_" + formDefine.getKey();
            if (allFormulaDic.containsKey(aSchemeFormKey)) {
                formFormulas = (Map)allFormulaDic.get(aSchemeFormKey);
            } else {
                formFormulas = new HashMap();
                List formulas = this.formulaController.getAllFormulasInForm(formulaScheme.getKey(), formDefine.getKey());
                for (FormulaDefine formula : formulas) {
                    formFormulas.put(formula.getCode(), formula);
                }
                allFormulaDic.put(aSchemeFormKey, formFormulas);
            }
            FormulaDefine curFormula = (FormulaDefine)formFormulas.get(formulaCode);
            if (curFormula == null) continue;
            String curformKey = null;
            curformKey = formDefine != null ? formDefine.getKey() : "00000000-0000-0000-0000-000000000000";
            Map<String, String> curFormulaCache = null;
            if (allFormulaCache.containsKey(formulaScheme.getKey() + ";" + curformKey)) {
                curFormulaCache = (Map)allFormulaCache.get(formulaScheme.getKey() + ";" + curformKey);
            } else {
                curFormulaCache = new HashMap();
                List parsedExpressions = null;
                parsedExpressions = StringUtils.isNotEmpty((String)curformKey) ? this.formulaController.getParsedExpressionByForm(formulaScheme.getKey(), curformKey, DataEngineConsts.FormulaType.CHECK) : this.formulaController.getParsedExpressionBetweenTable(formulaScheme.getKey(), DataEngineConsts.FormulaType.CHECK);
                if (parsedExpressions != null) {
                    for (IParsedExpression expression : parsedExpressions) {
                        String code = expression.getSource().getCode();
                        String key = expression.getKey();
                        curFormulaCache.put(code, key);
                    }
                }
                allFormulaCache.put(formulaScheme.getKey() + ";" + curformKey, curFormulaCache);
            }
            String formulaKey = (String)curFormulaCache.get(formulaCode);
            FormulaCheckDesInfo formulaCheckDesInfo = new FormulaCheckDesInfo();
            formulaCheckDesInfo.setFormSchemeKey(formSchemeKey);
            formulaCheckDesInfo.setFormulaSchemeKey(formulaScheme.getKey());
            formulaCheckDesInfo.setFormKey(curformKey);
            formulaCheckDesInfo.setFormulaKey(formulaKey);
            formulaCheckDesInfo.setFormulaCode(oldInfo.getFormulaCode());
            formulaCheckDesInfo.setGlobCol(oldInfo.getGlobCol());
            formulaCheckDesInfo.setGlobRow(oldInfo.getGlobRow());
            formulaCheckDesInfo.setDimensionSet(oldInfo.getDimensionSet());
            formulaCheckDesInfo.getDescriptionInfo().setDescription(oldInfo.getDescriptionInfo().getDescription());
            desInfos.add(formulaCheckDesInfo);
        }
        formulaCheckDesBatchSaveInfo.setDesInfos(desInfos);
        if (desInfos.size() > 0) {
            this.formulaCheckService.batchSaveFormulaCheckDes(formulaCheckDesBatchSaveInfo);
        }
    }

    private FormulaCheckDesQueryInfo getFormulaCheckInfo(TaskCopyContext context, String formSchemeKey, String entityDwDimName, String periodCode) {
        FormulaCheckDesQueryInfo formulaCheckInfo = new FormulaCheckDesQueryInfo();
        formulaCheckInfo.setFormSchemeKey(formSchemeKey);
        List formulaSchemeDefines = this.formulaController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        StringBuilder sp = new StringBuilder();
        for (FormulaSchemeDefine formulaSc : formulaSchemeDefines) {
            if (sp.length() > 0) {
                sp.append(";");
            }
            sp.append(formulaSc.getKey());
        }
        formulaCheckInfo.setFormulaSchemeKey(sp.toString());
        Map<String, DimensionValue> dimensionSet = this.getDimensionValueMap(context, entityDwDimName, periodCode);
        formulaCheckInfo.setDimensionSet(dimensionSet);
        return formulaCheckInfo;
    }

    private Map<String, DimensionValue> getDimensionValueMap(TaskCopyContext context, String entityDwDimName, String periodCode) {
        HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
        String Units = this.getUnitsFromList(context);
        if (StringUtils.isNotEmpty((String)Units)) {
            DimensionValue newDim = new DimensionValue();
            newDim.setName(entityDwDimName);
            newDim.setValue("");
            dimensionValueMap.put(entityDwDimName, newDim);
        }
        DimensionValue dateDim = new DimensionValue();
        dateDim.setName("DATATIME");
        dateDim.setValue(periodCode);
        dimensionValueMap.put("DATATIME", dateDim);
        return dimensionValueMap;
    }

    private String getUnitsFromList(TaskCopyContext context) {
        StringBuilder units = new StringBuilder();
        for (String code : context.getCopyUnitCodes()) {
            units.append(code);
            units.append(",");
        }
        if (units.length() > 0) {
            units.delete(units.length() - 1, units.length());
        }
        return units.toString();
    }
}

