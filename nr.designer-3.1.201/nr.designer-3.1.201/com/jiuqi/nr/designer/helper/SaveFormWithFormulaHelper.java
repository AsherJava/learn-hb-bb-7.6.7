/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.definition.common.DesignFormulaDTO
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.FormulaDesignTimeController
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.definition.common.DesignFormulaDTO;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.FormulaDesignTimeController;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormulaCheckObj;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.facade.FormulaUpdateRecordVO;
import com.jiuqi.nr.designer.web.rest.vo.FormSaveObject;
import com.jiuqi.nr.designer.web.service.FormulaMonitor;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveFormWithFormulaHelper {
    @Autowired
    private ProgressCacheService progressCacheService;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private FormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private IDataDefinitionDesignTimeController npController;
    @Autowired
    private IDataDefinitionRuntimeController npRuntimeController;
    @Autowired
    private IDesignTimeViewController controller;
    private static final Logger logger = LoggerFactory.getLogger(SaveFormWithFormulaHelper.class);
    private static final String FORMULA_CODE_REPEAT = "\u516c\u5f0f\u7f16\u53f7\u91cd\u590d!";
    private static final String FORMULA_TYPE_NULL = "\u516c\u5f0f\u7c7b\u578b\u4e3a\u7a7a\uff01";

    public List<FormulaUpdateRecordVO> getFormulaUpdateRecordList(List<DesignFormulaDTO> designFormulaDTOList, String formScheme) {
        if (designFormulaDTOList != null && designFormulaDTOList.size() != 0) {
            HashMap<String, String> errorMesMap = new HashMap<String, String>();
            List<FormulaCheckObj> formulaCheckObjs = this.getFormulaCheckObj(designFormulaDTOList);
            try {
                List<FormulaCheckObj> checkResult = this.checkFormula(formulaCheckObjs, formScheme);
                for (FormulaCheckObj formulaCheckObj : checkResult) {
                    errorMesMap.put(formulaCheckObj.getCode(), formulaCheckObj.getErrorMsg());
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            ArrayList<FormulaUpdateRecordVO> recordVOS = new ArrayList<FormulaUpdateRecordVO>();
            HashMap<String, String> formTitleMap = new HashMap<String, String>();
            for (DesignFormulaDTO designFormulaDTO : designFormulaDTOList) {
                String message = (String)errorMesMap.get(designFormulaDTO.getDesignFormulaDefine().getCode());
                if (StringUtils.isNotEmpty((String)message)) {
                    designFormulaDTO.setResultMes(message);
                } else if (!designFormulaDTO.isSuccess()) {
                    designFormulaDTO.setResultMes("\u516c\u5f0f\u89e3\u6790\u5f02\u5e38");
                }
                FormulaUpdateRecordVO formulaUpdateRecordVO = FormulaUpdateRecordVO.toFormulaUpdateRecordVO(designFormulaDTO);
                if (StringUtils.isNotEmpty((String)((String)formTitleMap.get(designFormulaDTO.getDesignFormulaDefine().getFormKey())))) {
                    formulaUpdateRecordVO.setFormTitle((String)formTitleMap.get(designFormulaDTO.getDesignFormulaDefine().getFormKey()));
                } else {
                    DesignFormDefine designFormDefine = this.nrDesignTimeController.querySoftFormDefine(designFormulaDTO.getDesignFormulaDefine().getFormKey());
                    if (null != designFormDefine) {
                        formTitleMap.put(designFormulaDTO.getDesignFormulaDefine().getFormKey(), designFormDefine.getTitle());
                        formulaUpdateRecordVO.setFormTitle((String)formTitleMap.get(designFormulaDTO.getDesignFormulaDefine().getFormKey()));
                    }
                }
                recordVOS.add(formulaUpdateRecordVO);
            }
            return recordVOS.stream().filter(o -> !o.isSuccess() || !o.getFormulaOld().equals(o.getFormulaNew())).sorted(Comparator.comparing(FormulaUpdateRecordVO::getFormulaSchemeOrder).thenComparing(FormulaUpdateRecordVO::getFormulaCode)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public Map<String, List<DesignFormulaDTO>> saveFormWithFormula(String formKey, List<String> formulaSchemeKeys, FormSaveObject formSaveObject, FormObj formObjFin, ProgressItem progressItem) throws Exception {
        progressItem.setCurrentProgess(0);
        progressItem.setMessage("\u6b63\u5728\u89e3\u6790\u516c\u5f0f\uff0c\u5c06\u5750\u6807\u683c\u5f0f\u89e3\u6790\u6210\u94fe\u63a5\u683c\u5f0f......");
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        String formCode = this.nrDesignTimeController.querySoftFormDefine(formKey).getFormCode();
        HashMap<String, List<DesignFormulaDTO>> result = new HashMap<String, List<DesignFormulaDTO>>();
        for (String formulaSchemeKey : formulaSchemeKeys) {
            result.put(formulaSchemeKey, this.formulaDesignTimeController.parseFormulaExpressionBeforeFormSave(formKey, formCode, formulaSchemeKey));
        }
        progressItem.setCurrentProgess(100);
        progressItem.setMessage("\u516c\u5f0f\u89e3\u6790\u5b8c\u6210......");
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        progressItem.nextStep();
        progressItem.setCurrentProgess(0);
        progressItem.setMessage("\u6b63\u5728\u4fdd\u5b58\u62a5\u8868......");
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        this.stepSaveService.stepSaveForm(formObjFin);
        this.stepSaveService.syncGridData(formObjFin.getID(), formObjFin.getLanguageType(), formSaveObject.getSyncActions());
        progressItem.setCurrentProgess(100);
        progressItem.setMessage("\u62a5\u8868\u4fdd\u5b58\u5b8c\u6210");
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        progressItem.nextStep();
        return result;
    }

    private List<FormulaCheckObj> getFormulaCheckObj(List<DesignFormulaDTO> designFormulaDTOList) {
        ArrayList<FormulaCheckObj> result = new ArrayList<FormulaCheckObj>();
        HashMap<String, String> formMap = new HashMap<String, String>();
        for (DesignFormulaDTO designFormulaDTO : designFormulaDTOList) {
            DesignFormDefine formDefine;
            if (designFormulaDTO.isSuccess()) continue;
            FormulaCheckObj formulaCheckObj = new FormulaCheckObj();
            DesignFormulaDefine designFormulaDefine = designFormulaDTO.getDesignFormulaDefine();
            formulaCheckObj.setId(designFormulaDefine.getKey());
            formulaCheckObj.setAutoCalc(false);
            formulaCheckObj.setChecktype(designFormulaDefine.getCheckType());
            formulaCheckObj.setCode(designFormulaDefine.getCode());
            formulaCheckObj.setFormKey(designFormulaDefine.getFormKey());
            formulaCheckObj.setFormula(designFormulaDefine.getExpression());
            formulaCheckObj.setMeanning(designFormulaDefine.getDescription());
            formulaCheckObj.setOrder(designFormulaDefine.getOrder());
            formulaCheckObj.setSchemeKey(designFormulaDefine.getFormulaSchemeKey());
            formulaCheckObj.setUseCheck(designFormulaDefine.getUseCheck());
            formulaCheckObj.setUseCalculate(designFormulaDefine.getUseCalculate());
            formulaCheckObj.setUseBalance(designFormulaDefine.getUseBalance());
            formulaCheckObj.setDescription(designFormulaDefine.getDescription());
            String formulaFormCode = null;
            String formulaFormKey = designFormulaDefine.getFormKey();
            if (StringUtils.isNotEmpty((String)formulaFormKey) && (formulaFormCode = (String)formMap.get(formulaFormKey)) == null && (formDefine = this.controller.querySoftFormDefine(formulaFormKey)) != null) {
                formulaFormCode = formDefine.getFormCode();
                formMap.put(formulaFormKey, formulaFormCode);
            }
            formulaCheckObj.setReportName(formulaFormCode);
            result.add(formulaCheckObj);
        }
        return result;
    }

    private List<FormulaCheckObj> checkFormula(List<FormulaCheckObj> formuObjList, String formSchemeKey) throws Exception {
        if (formuObjList != null) {
            ArrayList<FormulaCheckObj> useCalculateList = new ArrayList<FormulaCheckObj>();
            ArrayList<FormulaCheckObj> useCheckList = new ArrayList<FormulaCheckObj>();
            ArrayList<FormulaCheckObj> useBalanceList = new ArrayList<FormulaCheckObj>();
            List<FormulaCheckObj> formulaCheckObjs = this.formulaTypeClassification(useCalculateList, useCheckList, useBalanceList, formuObjList);
            FormulaMonitor formulaMonitor = new FormulaMonitor();
            if (useCalculateList.size() > 0) {
                ArrayList<Formula> useCalculateLists = new ArrayList<Formula>(useCalculateList);
                this.parseFormulas(formSchemeKey, useCalculateLists, DataEngineConsts.FormulaType.CALCULATE, formulaMonitor);
            }
            if (useCheckList.size() > 0) {
                ArrayList<Formula> useCheckLists = new ArrayList<Formula>(useCheckList);
                this.parseFormulas(formSchemeKey, useCheckLists, DataEngineConsts.FormulaType.CHECK, formulaMonitor);
            }
            Map<String, FormulaCheckObj> checkResultMap = formulaMonitor.getCheckResultMap();
            Collection<FormulaCheckObj> values = checkResultMap.values();
            ArrayList<FormulaCheckObj> checkResultList = new ArrayList<FormulaCheckObj>(values);
            checkResultList.addAll(formulaCheckObjs);
            if (formuObjList.size() == 1) {
                this.realFormulaCodeCheck(checkResultList, formuObjList);
            }
            this.checkFormulaOrder(checkResultList);
            return checkResultList;
        }
        return Collections.emptyList();
    }

    private List<FormulaCheckObj> formulaTypeClassification(List<FormulaCheckObj> useCalculateList, List<FormulaCheckObj> useCheckList, List<FormulaCheckObj> useBalanceList, List<FormulaCheckObj> formuObjList) {
        ArrayList<FormulaCheckObj> formulaTypeNll = new ArrayList<FormulaCheckObj>();
        for (int i = 0; i < formuObjList.size(); ++i) {
            if (formuObjList.get(i).getFormula() == null || formuObjList.get(i).getFormula().startsWith("//")) continue;
            if (!(formuObjList.get(i).isUseCalculate() || formuObjList.get(i).isUseCheck() || formuObjList.get(i).isUseBalance())) {
                formuObjList.get(i).setErrorMsg(FORMULA_TYPE_NULL);
                formulaTypeNll.add(formuObjList.get(i));
                continue;
            }
            if (formuObjList.get(i).isUseCalculate()) {
                useCalculateList.add(formuObjList.get(i));
            }
            if (formuObjList.get(i).isUseCheck()) {
                useCheckList.add(formuObjList.get(i));
            }
            if (!formuObjList.get(i).isUseBalance()) continue;
            useBalanceList.add(formuObjList.get(i));
        }
        return formulaTypeNll;
    }

    private List<IParsedExpression> parseFormulas(String formSchemeKey, List<Formula> formulaList, DataEngineConsts.FormulaType formulaType, FormulaMonitor formulaMonitor) throws JQException {
        List<Object> parsedExpressions = new ArrayList<IParsedExpression>();
        try {
            ExecutorContext context = new ExecutorContext(this.npRuntimeController);
            DataEngineConsts.FormulaShowType showType = this.getFormulaShowTypeByFormScheme(formSchemeKey);
            context.setJQReportModel(showType == DataEngineConsts.FormulaShowType.JQ);
            context.setDesignTimeData(true, this.npController);
            List formulaVariables = this.formulaDesignTimeController.queryAllFormulaVariable(formSchemeKey);
            DesignReportFmlExecEnvironment environment = new DesignReportFmlExecEnvironment(this.controller, this.npController, formSchemeKey, formulaVariables);
            context.setEnv((IFmlExecEnvironment)environment);
            parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulaList, (DataEngineConsts.FormulaType)formulaType, (IMonitor)formulaMonitor);
        }
        catch (ParseException e) {
            logger.error("\u516c\u5f0f\u6821\u9a8c\u540e\u53f0\u5f02\u5e38:" + e.getMessage(), e);
        }
        return parsedExpressions;
    }

    private void realFormulaCodeCheck(List<FormulaCheckObj> checkResultList, List<FormulaCheckObj> formuObjList) throws JQException {
        FormulaObj formula = new FormulaObj();
        formula.setFormKey(formuObjList.get(0).getFormKey());
        formula.setCode(formuObjList.get(0).getCode());
        formula.setSchemeKey(formuObjList.get(0).getSchemeKey());
        List<FormulaCheckObj> formulaCodeCheck = this.formulaCodeCheck(formula);
        checkResultList.addAll(formulaCodeCheck);
    }

    private void checkFormulaOrder(List<FormulaCheckObj> formulaCheckObjs) {
        String regex = "^[a-zA-Z][\\w-]{0,25}$";
        String errMsg = "\u516c\u5f0f\u7f16\u53f7\u4e0d\u7b26\u5408\u5b57\u6bcd\u5f00\u5934,\u5b57\u6bcd\u52a0\u6570\u5b57\u4e0b\u5212\u7ebf\uff0c\u957f\u5ea6\u9650\u523625\u8981\u6c42!";
        for (FormulaCheckObj fco : formulaCheckObjs) {
            if (Pattern.matches(regex, fco.getCode())) continue;
            if (StringUtils.isEmpty((String)fco.getErrorMsg())) {
                fco.setErrorMsg(errMsg);
                continue;
            }
            fco.setErrorMsg(fco.getErrorMsg() + errMsg);
        }
    }

    private List<FormulaCheckObj> formulaCodeCheck(FormulaObj formula) throws JQException {
        FormulaCheckObj formuCodeCheck = new FormulaCheckObj();
        ArrayList<FormulaCheckObj> formuCodeChecks = new ArrayList<FormulaCheckObj>();
        if (formula.getCode() != null && formula.getFormKey() != null && formula.getSchemeKey() != null) {
            List findFormulaDefineInFormulaSchemes = null;
            findFormulaDefineInFormulaSchemes = formula.getFormKey() == null ? this.nrDesignTimeController.findRepeatFormulaDefineFormOutSchemes(formula.getCode(), null, formula.getSchemeKey()) : this.nrDesignTimeController.findRepeatFormulaDefineFormOutSchemes(formula.getCode(), formula.getFormKey(), formula.getSchemeKey());
            if (findFormulaDefineInFormulaSchemes.size() > 0) {
                formuCodeCheck.setCode(((DesignFormulaDefine)findFormulaDefineInFormulaSchemes.get(0)).getCode());
                formuCodeCheck.setErrorMsg(FORMULA_CODE_REPEAT);
                formuCodeChecks.add(formuCodeCheck);
                return formuCodeChecks;
            }
        }
        return Collections.emptyList();
    }

    private DataEngineConsts.FormulaShowType getFormulaShowTypeByFormScheme(String schemeKey) {
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(schemeKey);
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }
}

