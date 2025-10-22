/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.google.gson.Gson
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.bpm.setting.utils.SettingUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.NodeCheckInfo
 *  com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo
 *  com.jiuqi.nr.dataentry.bean.NodeCheckResultItem
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 *  com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo
 *  com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCheckResultService
 *  com.jiuqi.nr.dataentry.service.IBatchDataSumService
 *  com.jiuqi.nr.dataentry.util.Consts$FormAccessLevel
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  javax.annotation.Resource
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.finalaccountsaudit.onekeycheck.internal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultItem;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchDimensionValueFormInfo;
import com.jiuqi.nr.dataentry.paramInfo.DimensionValueFormInfo;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFieldStruct;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFormStruct;
import com.jiuqi.nr.finalaccountsaudit.common.DataQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.finalaccountsaudit.common.INvwaExecuteCallBack;
import com.jiuqi.nr.finalaccountsaudit.common.NvwaDataEngineHelper;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean.ExplainInfoCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityDataInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.AnalysisResultInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.JsonGetUtil;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.onekeycheck.common.OneKeyCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.onekeycheck.common.OneKeyCheckTableConsts;
import com.jiuqi.nr.finalaccountsaudit.onekeycheck.controller.IOneKeyCheckController;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OneKeyCheckController
implements IOneKeyCheckController {
    private static final Logger logger = LoggerFactory.getLogger(OneKeyCheckController.class);
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    private IFormulaRunTimeController runTimeFormulaController;
    @Autowired
    IDataDefinitionDesignTimeController npDesignController;
    @Autowired
    IBatchCheckResultService batchCheckService;
    @Autowired
    private IBatchDataSumService batchDataSumService;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    DimensionUtil dimensionUtil;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IRunTimeViewController viewCtrl;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Resource
    AsyncTaskManager asyncTaskManager;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    @Autowired
    DataQueryHelper dataQueryHelper;

    @Override
    public void invokeMultCheck(Map<String, MultCheckItem> asyncTaskMap, OneKeyCheckInfo oneKeyCheckInfo) {
        try {
            List<OneKeyCheckResultItem> reultList = this.getResultList(asyncTaskMap, oneKeyCheckInfo);
            this.insertCheckResult(oneKeyCheckInfo, reultList);
        }
        catch (Exception e) {
            logger.info("\u5ba1\u6838\u8bb0\u5f55\u4fdd\u5b58\u51fa\u9519", (Object)e.getMessage(), (Object)e.getCause());
        }
    }

    private List<OneKeyCheckResultItem> getResultList(Map<String, MultCheckItem> asyncTaskMap, OneKeyCheckInfo oneKeyCheckInfo) throws Exception {
        ArrayList<OneKeyCheckResultItem> resultList = new ArrayList<OneKeyCheckResultItem>();
        OneKeyCheckResultItem resultItem = null;
        MultCheckItem multCheckItem = null;
        String asyncTaskId = "";
        for (Map.Entry<String, MultCheckItem> entry : asyncTaskMap.entrySet()) {
            multCheckItem = entry.getValue();
            asyncTaskId = entry.getKey();
            switch (multCheckItem.getCheckType()) {
                case "enumCheck": {
                    resultItem = this.getEnumCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "integrityForm": {
                    resultItem = this.getIntegrityFormResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "nodeCheck": {
                    resultItem = this.getNodeCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "entityCheck": {
                    resultItem = this.getEntityCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "errorDescCheck": {
                    resultItem = this.getErrorDescCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "attachmentCheck": {
                    resultItem = this.getAttachmentCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "queryTemplate": {
                    resultItem = this.getQueryCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "GSSH": {
                    resultItem = this.getFormulaCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "entityTreeCheck": {
                    resultItem = this.getEntityTreeCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
                case "zbQueryTemplate": 
                case "dataAnalysis": {
                    resultItem = this.getZbQuueryCheckResultItem(asyncTaskId, multCheckItem, oneKeyCheckInfo);
                    resultList.add(resultItem);
                    break;
                }
            }
        }
        return resultList;
    }

    private OneKeyCheckResultItem getZbQuueryCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        resultItem.setParm1(multCheckItem.getKey());
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuilder infoStr = new StringBuilder();
        int isPass = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            infoStr.append(multCheckItem.getName() + state.getTitle());
        } else {
            AnalysisResultInfo results = (AnalysisResultInfo)new Gson().fromJson(this.asyncTaskManager.queryDetail(asyncTaskId).toString(), AnalysisResultInfo.class);
            if (results.getResult()) {
                infoStr.append("\u5df2\u5ba1\u6838\uff0c\u7ed3\u679c\u53ef\u80fd\u6709\u8bef");
            } else {
                isPass = 1;
                infoStr.append("\u5ba1\u6838\u901a\u8fc7");
            }
        }
        resultItem.setResult(isPass);
        resultItem.setInfo(infoStr.toString());
        return resultItem;
    }

    private OneKeyCheckResultItem getEntityTreeCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuilder infoStr = new StringBuilder();
        int isPass = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            infoStr.append(multCheckItem.getName() + state.getTitle());
        } else {
            int errorCount = (Integer)this.asyncTaskManager.queryDetail(asyncTaskId);
            if (errorCount > 0) {
                infoStr.append("\u68c0\u67e5\u6709\u8bef\uff0c\u6811\u5f62\u68c0\u67e5\u9519\u8bef\u6570\u5171").append(errorCount).append("\u4e2a");
                isPass = 0;
            } else {
                isPass = 1;
                infoStr.append("\u5ba1\u6838\u901a\u8fc7");
            }
        }
        resultItem.setResult(isPass);
        resultItem.setInfo(infoStr.toString());
        return resultItem;
    }

    private OneKeyCheckResultItem getAttachmentCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        resultItem.setParm1(oneKeyCheckInfo.getContext().getFormSchemeKey());
        ArrayList<String> unitKeys = new ArrayList<String>();
        String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        String enityListStr = oneKeyCheckInfo.getSelectedDimensionSet().get(mainDimName).getValue();
        if (!enityListStr.isEmpty()) {
            for (String entityKey : enityListStr.split(";")) {
                unitKeys.add(entityKey);
            }
        }
        resultItem.setParm2(((Object)unitKeys).toString());
        HashMap checkScope = (HashMap)multCheckItem.getItemSetting();
        ArrayList zbList = (ArrayList)checkScope.get("zbList");
        HashMap<String, BlobFormStruct> formMaps = new HashMap<String, BlobFormStruct>();
        if (zbList != null) {
            for (HashMap zb : zbList) {
                FormDefine formDefine = this.viewCtrl.queryFormById(zb.get("formKey").toString());
                FieldDefine fieldDefine = null;
                try {
                    fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(zb.get("fieldkey").toString());
                }
                catch (Exception e) {
                    logger.error(e.getMessage());
                }
                if (formDefine == null || fieldDefine == null) continue;
                BlobFormStruct formStruct = null;
                if (formMaps.containsKey(zb.get("formKey").toString())) {
                    formStruct = (BlobFormStruct)formMaps.get(zb.get("formKey").toString());
                } else {
                    formStruct = new BlobFormStruct();
                    formStruct.setFlag(formDefine.getFormCode());
                    formStruct.setKey(formDefine.getKey());
                    formStruct.setTitle(formDefine.getTitle());
                    formMaps.put(zb.get("formKey").toString(), formStruct);
                }
                BlobFieldStruct fieldStruct = new BlobFieldStruct();
                fieldStruct.setDataLinkKey(zb.get("dataLinkKey").toString());
                fieldStruct.setKey(fieldDefine.getKey());
                fieldStruct.setFlag(fieldDefine.getCode());
                fieldStruct.setFormCode(formDefine.getFormCode());
                fieldStruct.setFormKey(formDefine.getKey());
                fieldStruct.setFormTitle(formDefine.getTitle());
                fieldStruct.setTitle(fieldDefine.getTitle());
                formStruct.getChildren().add(fieldStruct);
            }
        }
        resultItem.setParm3(new ArrayList(formMaps.values()).toString());
        BlobFileSizeCheckReturnInfo blobFileSizeCheckReturnInfo = (BlobFileSizeCheckReturnInfo)this.asyncTaskManager.queryDetail(asyncTaskId);
        resultItem.setEc1(blobFileSizeCheckReturnInfo.getErrUnitCount());
        resultItem.setEc2(blobFileSizeCheckReturnInfo.getErrFileCount());
        StringBuilder info = new StringBuilder();
        if (blobFileSizeCheckReturnInfo.getErrItems().size() <= 0) {
            info.append("\u5ba1\u6838\u901a\u8fc7");
            resultItem.setResult(1);
        } else {
            info.append("\u5ba1\u6838\u6709\u8bef\uff0c").append("\u5355\u4f4d\u5171").append(blobFileSizeCheckReturnInfo.getUnitCount()).append("\u6237\uff0c\u4e0d\u901a\u8fc7").append(blobFileSizeCheckReturnInfo.getErrUnitCount()).append("\u6237\uff0c\u6307\u6807\u5171").append(blobFileSizeCheckReturnInfo.getSelZBCount()).append("\u4e2a\uff0c\u4e0d\u5408\u89c4\u8303").append(blobFileSizeCheckReturnInfo.getErrFileCount()).append("\u4e2a");
            resultItem.setResult(0);
        }
        resultItem.setInfo(info.toString());
        return resultItem;
    }

    private OneKeyCheckResultItem getErrorDescCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        resultItem.setParm1(oneKeyCheckInfo.getContext().getFormSchemeKey());
        ArrayList<String> unitKeys = new ArrayList<String>();
        String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        String enityListStr = oneKeyCheckInfo.getSelectedDimensionSet().get(mainDimName).getValue();
        if (!enityListStr.isEmpty()) {
            for (String entityKey : enityListStr.split(";")) {
                unitKeys.add(entityKey);
            }
        }
        HashMap checkScope = (HashMap)multCheckItem.getItemSetting();
        ArrayList formulaSolution = (ArrayList)checkScope.get("formulaSolution");
        String formulaSchemeKeys = "";
        for (int i = 0; i < formulaSolution.size(); ++i) {
            formulaSchemeKeys = i != formulaSolution.size() - 1 ? formulaSchemeKeys + (String)formulaSolution.get(i) + ";" : formulaSchemeKeys + (String)formulaSolution.get(i);
        }
        resultItem.setParm2(formulaSchemeKeys);
        resultItem.setParm3(((Object)unitKeys).toString());
        resultItem.setParm4(checkScope.get("impactReport").toString());
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuilder infoStr = new StringBuilder();
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            infoStr.append(multCheckItem.getName() + state.getTitle());
            resultItem.setResult(2);
        } else {
            ExplainInfoCheckReturnInfo explainInfoCheckReturnInfo = (ExplainInfoCheckReturnInfo)this.asyncTaskManager.queryDetail(asyncTaskId);
            if (explainInfoCheckReturnInfo.getErrUnitCount() > 0) {
                infoStr.append("\u5ba1\u6838\u6709\u8bef\uff0c\u5355\u4f4d\u5171").append(explainInfoCheckReturnInfo.getTotalUnitCount()).append("\u6237\uff0c\u6709\u8bef").append(explainInfoCheckReturnInfo.getErrUnitCount()).append("\u6237");
                resultItem.setResult(0);
                resultItem.setEc1(explainInfoCheckReturnInfo.getErrUnitCount());
            } else {
                resultItem.setResult(1);
                infoStr.append("\u5ba1\u6838\u901a\u8fc7");
            }
        }
        resultItem.setInfo(infoStr.toString());
        return resultItem;
    }

    private OneKeyCheckResultItem getFormulaCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) throws Exception {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        resultItem.setParm2(multCheckItem.getKey());
        resultItem.setParm1(oneKeyCheckInfo.getContext().getFormSchemeKey());
        List<String> entityKeys = this.getSelectedEntity(oneKeyCheckInfo);
        resultItem.setParm3(entityKeys.toString());
        HashSet<String> errUnits = new HashSet<String>();
        HashSet<String> errorFormula = new HashSet<String>();
        int formulaCount = 0;
        Map itemSetting = (Map)multCheckItem.getItemSetting();
        Map formulas = (Map)itemSetting.get("formulaMap");
        if (formulas == null || formulas.size() == 0) {
            ArrayList forms = new ArrayList();
            String string = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(oneKeyCheckInfo.getContext().getFormSchemeKey()));
            BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(oneKeyCheckInfo.getContext(), string, Consts.FormAccessLevel.FORM_READ);
            List acessFormInfos = batchDimensionValueFormInfo.getAccessFormInfos();
            for (int formInfoIndex = 0; formInfoIndex < acessFormInfos.size(); ++formInfoIndex) {
                DimensionValueFormInfo dimensionValueFormInfo = (DimensionValueFormInfo)acessFormInfos.get(formInfoIndex);
                forms.addAll(dimensionValueFormInfo.getForms());
            }
            forms.add("betweenParsed");
            Iterator iterator = forms.iterator();
            while (iterator.hasNext()) {
                String form = (String)iterator.next();
                List formuls = this.formulaRunTimeController.getCheckFormulasInForm(multCheckItem.getKey(), form.equals("betweenParsed") ? null : form);
                formulaCount += formuls.size();
            }
        } else {
            for (Map.Entry entry : formulas.entrySet()) {
                if (((List)entry.getValue()).size() > 0) {
                    formulaCount += ((List)entry.getValue()).size();
                    continue;
                }
                Iterator formKey = ((String)entry.getKey()).equals("betweenParsed") ? null : (String)entry.getKey();
                List formuls = this.formulaRunTimeController.getCheckFormulasInForm(multCheckItem.getKey(), formKey);
                formulaCount += formuls.size();
            }
        }
        resultItem.setParm4(formulas.toString());
        FormulaCheckReturnInfo batchCheckResult = this.getFormulaCheckReturnInfo(asyncTaskId, oneKeyCheckInfo, multCheckItem);
        List list = batchCheckResult.getResults();
        for (FormulaCheckResultInfo rt : list) {
            if (!errUnits.contains(rt.getUnitKey())) {
                errUnits.add(rt.getUnitKey());
            }
            if (errorFormula.contains(rt.getFormula().getCode())) continue;
            errorFormula.add(rt.getFormula().getCode());
        }
        resultItem.setEc1(errUnits.size());
        resultItem.setEc2(errorFormula.size());
        resultItem.setEc3(batchCheckResult.getTotalCount());
        resultItem.setEc4(batchCheckResult.getErrorCount());
        resultItem.setEc5(batchCheckResult.getWarnCount());
        resultItem.setEc6(batchCheckResult.getHintCount());
        StringBuilder info = new StringBuilder();
        if (batchCheckResult.getTotalCount() <= 0) {
            info.append("\u5ba1\u6838\u901a\u8fc7\u3002").append("\u5355\u4f4d\u5171").append(entityKeys.size()).append("\u6237\uff0c").append("\u516c\u5f0f\u5171").append(formulaCount).append("\u4e2a\u3002");
            resultItem.setResult(1);
        } else {
            info.append("\u5ba1\u6838\u4e0d\u901a\u8fc7\u3002").append("\u5355\u4f4d\u5171").append(entityKeys.size()).append("\u6237\uff0c").append("\u4e0d\u901a\u8fc7").append(errUnits.size()).append("\u6237\u3002").append("\u516c\u5f0f\u5171").append(formulaCount).append("\u4e2a\uff0c").append("\u4e0d\u901a\u8fc7").append(errorFormula.size()).append("\u4e2a\u3002");
            resultItem.setResult(0);
        }
        resultItem.setInfo(info.toString());
        return resultItem;
    }

    private FormulaCheckReturnInfo getFormulaCheckReturnInfo(String asyncTaskId, OneKeyCheckInfo oneKeyCheckInfo, MultCheckItem item) throws JsonProcessingException {
        Gson gson = new Gson();
        JtableContext jtableContext = (JtableContext)gson.fromJson(gson.toJson((Object)oneKeyCheckInfo.getContext()), JtableContext.class);
        BatchCheckInfo batchCheckInfo = new BatchCheckInfo();
        jtableContext.setDimensionSet(oneKeyCheckInfo.getSelectedDimensionSet());
        jtableContext.setFormulaSchemeKey(item.getKey());
        batchCheckInfo.setContext(jtableContext);
        batchCheckInfo.setFormulaSchemeKeys(item.getKey());
        Map itemSetting = (Map)item.getItemSetting();
        HashMap formulas = (HashMap)itemSetting.get("formulaMap");
        batchCheckInfo.setContext(jtableContext);
        if (formulas == null) {
            formulas = new HashMap();
            ArrayList formula = new ArrayList();
            String FormKeys = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(oneKeyCheckInfo.getContext().getFormSchemeKey()));
            BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(oneKeyCheckInfo.getContext(), FormKeys, Consts.FormAccessLevel.FORM_READ);
            List forms = batchDimensionValueFormInfo.getForms();
            for (String form : forms) {
                formulas.put(form, formula);
            }
            batchCheckInfo.setFormulas(formulas);
        } else {
            batchCheckInfo.setFormulas((Map)formulas);
        }
        batchCheckInfo.setOrderField("form_formula");
        List<Integer> erroStatus = this.getFormulaErrState();
        batchCheckInfo.setCheckTypes(erroStatus);
        batchCheckInfo.setAsyncTaskKey(asyncTaskId);
        FormulaCheckReturnInfo batchCheckResult = this.batchCheckService.batchCheckResult(batchCheckInfo);
        return batchCheckResult;
    }

    private List<Integer> getFormulaErrState() {
        ArrayList<Integer> erroStatus = new ArrayList<Integer>();
        ArrayList<Integer> formualTypes = new ArrayList<Integer>();
        try {
            List auditTypes = this.auditTypeDefineService.queryAllAuditType();
            if (auditTypes == null || auditTypes.size() == 0) {
                throw new Exception();
            }
            for (AuditType auditType : auditTypes) {
                formualTypes.add(auditType.getCode());
            }
        }
        catch (Exception e) {
            formualTypes.add(1);
            formualTypes.add(2);
            formualTypes.add(4);
        }
        for (int i = 0; i < formualTypes.size(); ++i) {
            erroStatus.add((Integer)formualTypes.get(i));
        }
        return erroStatus;
    }

    private OneKeyCheckResultItem getQueryCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuilder infoStr = new StringBuilder();
        int isPass = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            infoStr.append(multCheckItem.getName() + state.getTitle()).append("\u3002");
        } else {
            Map result = (Map)this.asyncTaskManager.queryDetail(asyncTaskId);
            if (((Boolean)result.get(multCheckItem.getKey())).booleanValue()) {
                infoStr.append("\u5df2\u5ba1\u6838\uff0c\u7ed3\u679c\u53ef\u80fd\u6709\u8bef\u3002");
            } else {
                isPass = 1;
                infoStr.append("\u5ba1\u6838\u6210\u529f\u3002");
            }
        }
        resultItem.setResult(isPass);
        resultItem.setInfo(infoStr.toString());
        resultItem.setParm1(multCheckItem.getKey());
        return resultItem;
    }

    private OneKeyCheckResultItem getEntityCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        String currentEntity = ((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get(mainDimName)).getValue();
        resultItem.setParm1(oneKeyCheckInfo.getContext().getFormSchemeKey());
        resultItem.setParm2(currentEntity);
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuilder info = new StringBuilder();
        int result = 2;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            String msg = this.asyncTaskManager.queryResult(asyncTaskId);
            info.append(msg.equals("") ? state.getTitle() : msg);
        } else {
            JSONObject multCheckObj = new JSONObject(this.asyncTaskManager.queryDetail(asyncTaskId).toString());
            int jsdwCount = multCheckObj.getInt("jsdwCount");
            int ywdwCount = multCheckObj.getInt("ywdwCount");
            int xzdwCount = multCheckObj.getInt("xzdwCount");
            int dqdwCount = multCheckObj.getInt("dqdwCount");
            int yzdwCount = multCheckObj.getInt("yzdwCount");
            if (ywdwCount > 0) {
                info.append("\u5df2\u6838\u5bf9\uff0c\u5b58\u5728\u5355\u4f4d\u53d8\u52a8").append(multCheckItem.getId()).append("\u5171\u8ba1").append(dqdwCount).append("\u5bb6\u5355\u4f4d\uff0c").append("\u65b0\u589e").append(xzdwCount).append("\u5bb6\uff0c").append("\u51cf\u5c11").append(jsdwCount).append("\u5bb6\uff0c\u6709\u8bef").append(ywdwCount).append("\u5bb6\uff0c\u4fe1\u606f\u4e00\u81f4").append(yzdwCount).append("\u5bb6");
            } else {
                result = 1;
                info.append("\u6838\u5bf9\u65e0\u8bef");
            }
            resultItem.setEc1(jsdwCount);
            resultItem.setEc2(ywdwCount);
            resultItem.setEc3(xzdwCount);
            resultItem.setEc4(dqdwCount);
            resultItem.setEc5(yzdwCount);
        }
        resultItem.setResult(result);
        resultItem.setInfo(info.toString());
        return resultItem;
    }

    private OneKeyCheckResultItem getNodeCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) throws JsonProcessingException {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        JSONObject jsobNodeCheckParms = new JSONObject((Map)multCheckItem.getItemSetting());
        JSONArray formList = (JSONArray)jsobNodeCheckParms.get("formList");
        String contextStr = JsonGetUtil.getString(jsobNodeCheckParms, "context");
        JtableContext jtableContext = (JtableContext)JSONUtil.parseObject((String)contextStr, JtableContext.class);
        if (jtableContext == null) {
            jtableContext = oneKeyCheckInfo.getContext();
        }
        boolean recursive = false;
        if (JsonGetUtil.getBoolean(jsobNodeCheckParms, "tierCheck") || JsonGetUtil.getBoolean(jsobNodeCheckParms, "recursive")) {
            recursive = true;
        }
        BigDecimal errorRange = JsonGetUtil.getBigDecimal(jsobNodeCheckParms, "errorRange");
        String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        resultItem.setParm1(oneKeyCheckInfo.getContext().getFormSchemeKey());
        resultItem.setParm2(((DimensionValue)jtableContext.getDimensionSet().get(mainDimName)).getValue());
        resultItem.setParm3(String.valueOf(recursive));
        resultItem.setParm4(String.valueOf(errorRange));
        String forms = "";
        JSONObject jsonForm = new JSONObject();
        for (int i = 0; i < formList.length(); ++i) {
            jsonForm = formList.getJSONObject(i);
            forms = i != formList.length() - 1 ? forms + jsonForm.getString("formKey") + ";" : forms + jsonForm.getString("formKey");
        }
        resultItem.setParm5(forms);
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        NodeCheckResultInfo nodecheckResult = this.nodecheckResult(asyncTaskId);
        NodeCheckInfo nodeCheckInfo = (NodeCheckInfo)JSONUtil.parseObject((String)multCheckItem.getCheckParams(), NodeCheckInfo.class);
        String currEntityTitle = this.getCurrentEntity(nodeCheckInfo.getContext());
        StringBuilder info = new StringBuilder();
        int result = 0;
        if (nodecheckResult == null) {
            result = 2;
            info.append(multCheckItem.getName() + state.getTitle()).append("\uff1b\u68c0\u67e5\u8282\u70b9\u4e3a\uff1a").append(currEntityTitle);
        } else {
            HashSet<String> errorFields = new HashSet<String>();
            HashSet<String> errorForms = new HashSet<String>();
            int errorCount = 0;
            for (Map.Entry entry : nodecheckResult.getNodeCheckResult().entrySet()) {
                for (NodeCheckResultItem item : (List)entry.getValue()) {
                    if (!errorFields.contains(item.getFieldCode())) {
                        errorFields.add(item.getFieldCode());
                    }
                    if (errorForms.contains(item.getNodeCheckFieldMessage().getFormKey())) continue;
                    errorForms.add(item.getNodeCheckFieldMessage().getFormKey());
                }
                errorCount += ((List)entry.getValue()).size();
            }
            if (nodecheckResult.getNodeCheckResult().size() <= 0) {
                result = 1;
                info.append("\u5ba1\u6838\u901a\u8fc7\uff0c\u6ca1\u6709\u9519\u8bef").append("\u68c0\u67e5\u8282\u70b9\u4e3a").append(currEntityTitle);
            } else {
                info.append("\u5ba1\u6838\u6709\u8bef\uff0c\u5171").append(errorCount).append("\u6761\u9519\u8bef\u8bb0\u5f55\u3002").append("\u68c0\u67e5\u8282\u70b9\u4e3a\uff1a").append(currEntityTitle).append("\u6709\u8bef\u6307\u6807\u5171").append(errorFields.size()).append("\u4e2a\uff0c").append("\u6d89\u53ca\u62a5\u8868").append(errorForms.size()).append("\u5f20");
            }
            resultItem.setEc1(errorFields.size());
            resultItem.setEc2(errorForms.size());
        }
        resultItem.setInfo(info.toString());
        resultItem.setResult(result);
        return resultItem;
    }

    public NodeCheckResultInfo nodecheckResult(String asyncTaskID) throws JsonProcessingException {
        String strResult = this.asyncTaskManager.queryDetail(asyncTaskID).toString();
        if (null != strResult) {
            return (NodeCheckResultInfo)JSONUtil.parseObject((String)this.asyncTaskManager.queryDetail(asyncTaskID).toString(), NodeCheckResultInfo.class);
        }
        return null;
    }

    private String getCurrentEntity(JtableContext context) {
        IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(context.getFormSchemeKey());
        entityQuery.setIgnoreViewFilter(true);
        entityQuery.setMasterKeys(DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet()));
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IEntityTable entityTable = null;
        String curEntityTitle = "";
        try {
            entityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (entityTable.getAllRows().size() > 0) {
            curEntityTitle = ((IEntityRow)entityTable.getAllRows().get(0)).getTitle();
        }
        return curEntityTitle;
    }

    private OneKeyCheckResultItem getIntegrityFormResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        resultItem.setParm1(oneKeyCheckInfo.getContext().getFormSchemeKey());
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuilder info = new StringBuilder();
        int result = 0;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            info.append(multCheckItem.getName() + state.getTitle()).append("\u3002");
            result = 2;
        } else {
            IntegrityDataInfo integrityDataInfo = (IntegrityDataInfo)((Object)this.asyncTaskManager.queryDetail(asyncTaskId));
            String emptyDwCount = integrityDataInfo.getEmptyTabCount().toString();
            Map<String, List<String>> rowData = integrityDataInfo.getRowData();
            ArrayList<Boolean> colEmpty = new ArrayList<Boolean>();
            for (Map.Entry<String, List<String>> entry : rowData.entrySet()) {
                for (int i = 2; i < entry.getValue().size(); ++i) {
                    if (entry.getKey().equals("1")) {
                        colEmpty.add(false);
                    }
                    if (entry.getValue().get(i) == null) continue;
                    colEmpty.set(i - 2, true);
                }
            }
            int emptyColSum = 0;
            for (Boolean isEmptyByCol : colEmpty) {
                if (!isEmptyByCol.booleanValue()) continue;
                ++emptyColSum;
            }
            List<String> list = this.getSelectedEntity(oneKeyCheckInfo);
            List<String> selectedForms = this.getSelectedForms(multCheckItem, oneKeyCheckInfo);
            if (rowData.size() <= 0) {
                result = 1;
                info.append("\u5ba1\u6838\u901a\u8fc7").append("\u5355\u4f4d\u5171").append(list.size()).append("\u6237\uff0c").append("\u7f3a\u8868").append(emptyDwCount).append("\u6237\uff0c").append("\u62a5\u8868\u5171").append(selectedForms.size()).append("\u5f20\uff0c").append("\u7a7a\u8868").append(emptyColSum).append("\u5f20\u3002");
            } else {
                info.append("\u5ba1\u6838\u6709\u8bef").append("\u5355\u4f4d\u5171").append(list.size()).append("\u6237\uff0c").append("\u7f3a\u8868").append(emptyDwCount).append("\u6237\uff0c").append("\u62a5\u8868\u5171").append(selectedForms.size()).append("\u5f20\uff0c").append("\u7a7a\u8868").append(emptyColSum).append("\u5f20\u3002");
            }
            resultItem.setParm2(list.toString());
            resultItem.setParm3(selectedForms.toString());
            resultItem.setEc1(integrityDataInfo.getEmptyTabCount());
            resultItem.setEc2(emptyColSum);
        }
        resultItem.setInfo(info.toString());
        resultItem.setResult(result);
        return resultItem;
    }

    private List<String> getSelectedForms(MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) {
        List<String> selectedForms = new ArrayList<String>();
        HashMap checkScope = (HashMap)multCheckItem.getItemSetting();
        List formList = (List)checkScope.get("formList");
        if (formList.size() > 0) {
            for (int i = 0; i < formList.size(); ++i) {
                selectedForms.add(((HashMap)formList.get(i)).get("formKey").toString());
            }
        } else {
            String FormKeys = String.join((CharSequence)";", this.viewCtrl.queryAllFormKeysByFormScheme(oneKeyCheckInfo.getContext().getFormSchemeKey()));
            BatchDimensionValueFormInfo batchDimensionValueFormInfo = this.formGroupProvider.getForms(oneKeyCheckInfo.getContext(), FormKeys, Consts.FormAccessLevel.FORM_READ);
            selectedForms = batchDimensionValueFormInfo.getForms();
        }
        return selectedForms;
    }

    private OneKeyCheckResultItem getEnumCheckResultItem(String asyncTaskId, MultCheckItem multCheckItem, OneKeyCheckInfo oneKeyCheckInfo) throws JsonProcessingException {
        OneKeyCheckResultItem resultItem = new OneKeyCheckResultItem();
        resultItem.setShlxId(multCheckItem.getId());
        resultItem.setShlxName(multCheckItem.getName());
        resultItem.setShlx(multCheckItem.getCheckType());
        resultItem.setParm1(oneKeyCheckInfo.getContext().getFormSchemeKey());
        List<String> entityKeys = this.getSelectedEntity(oneKeyCheckInfo);
        resultItem.setParm2(entityKeys.toString());
        HashMap checkScope = (HashMap)multCheckItem.getItemSetting();
        ArrayList enumStr = (ArrayList)checkScope.get("enumList");
        String enumSels = "";
        if (enumStr.size() > 0) {
            int jL = enumStr.size();
            for (int j = 0; j < jL; ++j) {
                enumSels = enumSels + ((String)enumStr.get(j)).substring(1, ((String)enumStr.get(j)).indexOf(12305)) + ";";
            }
        }
        resultItem.setParm3(enumSels);
        TaskState state = this.asyncTaskManager.queryTaskState(asyncTaskId);
        StringBuilder info = new StringBuilder();
        int result = 0;
        if (state.getValue() != TaskState.FINISHED.getValue()) {
            result = 2;
            info.append(multCheckItem.getName() + state.getTitle());
        } else {
            EnumDataCheckResultInfo enumDataCheckResultInfo = (EnumDataCheckResultInfo)this.asyncTaskManager.queryDetail(asyncTaskId);
            List<String> enumCheckResult = enumDataCheckResultInfo.getEnumDataCheckResult();
            int selEnumDicCount = enumDataCheckResultInfo.getSelEnumDicCount();
            int selEntityCount = enumDataCheckResultInfo.getSelEntityCount();
            HashSet<String> errorUnitKeys = new HashSet<String>();
            HashSet<String> errorEnumCodes = new HashSet<String>();
            EnumDataCheckResultItem enumResultItem = new EnumDataCheckResultItem();
            ObjectMapper objectMapper = new ObjectMapper();
            for (String item : enumCheckResult) {
                enumResultItem = (EnumDataCheckResultItem)objectMapper.readValue(item, EnumDataCheckResultItem.class);
                if (!errorUnitKeys.contains(enumResultItem.getMasterEntityKey())) {
                    errorUnitKeys.add(enumResultItem.getMasterEntityKey());
                }
                if (errorEnumCodes.contains(enumResultItem.getEnumCode())) continue;
                errorEnumCodes.add(enumResultItem.getEnumCode());
            }
            if (enumCheckResult.size() <= 0) {
                result = 1;
                info.append("\u5ba1\u6838\u901a\u8fc7\uff0c\u6ca1\u6709\u9519\u8bef\u3002").append("\u5355\u4f4d\u5171").append(selEntityCount).append("\u6237\uff0c").append("\u4e0d\u901a\u8fc7").append(errorUnitKeys.size()).append("\u6237\u3002").append("\u679a\u4e3e\u9879\u5171").append(selEnumDicCount).append("\u4e2a\uff0c").append("\u4e0d\u901a\u8fc7").append(errorEnumCodes.size()).append("\u4e2a\u3002");
            } else {
                info.append("\u5ba1\u6838\u6709\u8bef\uff0c\u5171").append(enumCheckResult.size()).append("\u6761\u9519\u8bef\u8bb0\u5f55\u3002").append("\u5355\u4f4d\u5171").append(selEntityCount).append("\u6237\uff0c").append("\u4e0d\u901a\u8fc7").append(errorUnitKeys.size()).append("\u6237\u3002").append("\u679a\u4e3e\u9879\u5171").append(selEnumDicCount).append("\u4e2a\uff0c").append("\u4e0d\u901a\u8fc7").append(errorEnumCodes.size()).append("\u4e2a\u3002");
            }
            resultItem.setEc1(errorUnitKeys.size());
            resultItem.setEc2(errorEnumCodes.size());
            resultItem.setEc3(enumCheckResult.size());
        }
        resultItem.setResult(result);
        resultItem.setInfo(info.toString());
        return resultItem;
    }

    private void deleteCheckResult(OneKeyCheckInfo oneKeyCheckInfo, List<OneKeyCheckResultItem> resultList) throws Exception {
        DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        INvwaDataAccessProvider iNvwaDataAccessProvider = (INvwaDataAccessProvider)BeanUtil.getBean(INvwaDataAccessProvider.class);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = dataModelService.getTableModelDefineByName(OneKeyCheckTableConsts.getTableName(this.nrDesignController.queryFormSchemeDefine(oneKeyCheckInfo.getContext().getFormSchemeKey()).getFormSchemeCode()));
        List columns = dataModelService.getColumnModelDefinesByTable(table.getID());
        HashMap<String, String> dimensionValueSet = new HashMap<String, String>();
        Map dimensionSet = oneKeyCheckInfo.getContext().getDimensionSet();
        String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        for (Map.Entry entry : dimensionSet.entrySet()) {
            String mapKey = (String)entry.getKey();
            String mapValue = ((DimensionValue)entry.getValue()).getValue().toString();
            if (oneKeyCheckInfo.getSingleApp() && mapKey.equals(mainDimName)) {
                mapValue = "default";
            }
            if (dimensionValueSet.containsKey(mapKey)) continue;
            dimensionValueSet.put(mapKey, mapValue);
        }
        if (!dimensionValueSet.containsKey("VERSIONID")) {
            dimensionValueSet.put("VERSIONID", "00000000-0000-0000-0000-000000000000");
        }
        HashMap conditions = new HashMap();
        conditions.put("PERIOD", dimensionValueSet.get("DATATIME"));
        conditions.put("MDCODE", dimensionValueSet.get(mainDimName));
        conditions.put("VERSIONID", dimensionValueSet.get("VERSIONID"));
        for (ColumnModelDefine column : columns) {
            if (conditions.containsKey(column.getCode())) {
                queryModel.getColumnFilters().put(column, conditions.get(column.getCode()));
            }
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        for (OneKeyCheckResultItem resultItem : resultList) {
            StringBuilder rowFilter = new StringBuilder();
            rowFilter.append(table.getCode()).append("[").append("SHLXID").append("]").append("=").append('\"').append(resultItem.getShlxId()).append('\"');
            queryModel.setFilter(rowFilter.toString());
            INvwaUpdatableDataAccess updatableDataAccess = iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(dataModelService);
            INvwaDataUpdator iNvwaDataUpdator = updatableDataAccess.openForUpdate(context);
            iNvwaDataUpdator.addDeleteRow();
            iNvwaDataUpdator.commitChanges(context);
        }
    }

    private boolean insertCheckResult(OneKeyCheckInfo oneKeyCheckInfo, final List<OneKeyCheckResultItem> resultList) throws Exception {
        this.deleteCheckResult(oneKeyCheckInfo, resultList);
        final HashMap<String, String> dimensionValueSet = new HashMap<String, String>();
        Map dimensionSet = oneKeyCheckInfo.getContext().getDimensionSet();
        final String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        for (Map.Entry entry : dimensionSet.entrySet()) {
            String mapKey = (String)entry.getKey();
            String mapValue = ((DimensionValue)entry.getValue()).getValue().toString();
            if (oneKeyCheckInfo.getSingleApp() && mapKey.equals(mainDimName)) {
                mapValue = "default";
            }
            if (dimensionValueSet.containsKey(mapKey)) continue;
            dimensionValueSet.put(mapKey, mapValue);
        }
        if (!dimensionValueSet.containsKey("VERSIONID")) {
            dimensionValueSet.put("VERSIONID", "00000000-0000-0000-0000-000000000000");
        }
        NvwaDataEngineHelper nvwaDataEngineHelper = (NvwaDataEngineHelper)BeanUtil.getBean(NvwaDataEngineHelper.class);
        String tableName = OneKeyCheckTableConsts.getTableName(this.nrDesignController.queryFormSchemeDefine(oneKeyCheckInfo.getContext().getFormSchemeKey()).getFormSchemeCode());
        String libraryTableName = this.dataQueryHelper.getLibraryTableName(oneKeyCheckInfo.getContext().getTaskKey(), tableName);
        nvwaDataEngineHelper.setTableName(libraryTableName);
        return nvwaDataEngineHelper.execute(new INvwaExecuteCallBack(){

            @Override
            public void execute(INvwaUpdatableDataSet iNvwaUpdatableDataSet, INvwaDataUpdator iNvwaDataUpdator, List<ColumnModelDefine> columns) throws Exception {
                for (OneKeyCheckResultItem resultItem : resultList) {
                    INvwaDataRow iNvwaDataRow = iNvwaDataUpdator.addInsertRow();
                    Date date = new Date();
                    block63: for (int i = 0; i < columns.size(); ++i) {
                        ColumnModelDefine columnModelDefine = columns.get(i);
                        switch (columnModelDefine.getCode()) {
                            case "PERIOD": {
                                iNvwaDataRow.setKeyValue(columnModelDefine, dimensionValueSet.get("DATATIME"));
                                continue block63;
                            }
                            case "MDCODE": {
                                iNvwaDataRow.setKeyValue(columnModelDefine, dimensionValueSet.get(mainDimName));
                                continue block63;
                            }
                            case "VERSIONID": {
                                iNvwaDataRow.setKeyValue(columnModelDefine, dimensionValueSet.get("VERSIONID"));
                                continue block63;
                            }
                            case "RECID": {
                                iNvwaDataRow.setKeyValue(columnModelDefine, (Object)UUID.randomUUID().toString());
                                continue block63;
                            }
                            case "SHLXID": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getShlxId());
                                continue block63;
                            }
                            case "SHLX": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getShlx());
                                continue block63;
                            }
                            case "SHLXNAME": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getShlxName());
                                continue block63;
                            }
                            case "RESULTSTATUS": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getResult());
                                continue block63;
                            }
                            case "INFO": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getInfo());
                                continue block63;
                            }
                            case "OPERATOR": {
                                iNvwaDataRow.setValue(i, (Object)SettingUtil.getCurrentUser().getName());
                                continue block63;
                            }
                            case "UPDATETIME": {
                                iNvwaDataRow.setValue(i, (Object)date);
                                continue block63;
                            }
                            case "PARM1": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getParm1());
                                continue block63;
                            }
                            case "PARM2": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getParm2());
                                continue block63;
                            }
                            case "PARM3": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getParm3());
                                continue block63;
                            }
                            case "PARM4": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getParm4());
                                continue block63;
                            }
                            case "PARM5": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getParm5());
                                continue block63;
                            }
                            case "PARM6": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getParm6());
                                continue block63;
                            }
                            case "EC1": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEc1());
                                continue block63;
                            }
                            case "EC2": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEc2());
                                continue block63;
                            }
                            case "EC3": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEc3());
                                continue block63;
                            }
                            case "EC4": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEc4());
                                continue block63;
                            }
                            case "EC5": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEc5());
                                continue block63;
                            }
                            case "EC6": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEc6());
                                continue block63;
                            }
                            case "ECD1": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEcd1());
                                continue block63;
                            }
                            case "ECD2": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEcd2());
                                continue block63;
                            }
                            case "ECD3": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEcd3());
                                continue block63;
                            }
                            case "ECD4": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEcd4());
                                continue block63;
                            }
                            case "ECD5": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEcd5());
                                continue block63;
                            }
                            case "ECD6": {
                                iNvwaDataRow.setValue(i, (Object)resultItem.getEcd6());
                            }
                        }
                    }
                }
            }
        });
    }

    private List<String> getSelectedEntity(OneKeyCheckInfo oneKeyCheckInfo) {
        ArrayList<String> entityData = new ArrayList<String>();
        String mainDimName = this.dimensionUtil.getDwMainDimName(oneKeyCheckInfo.getContext().getFormSchemeKey());
        String enityListStr = oneKeyCheckInfo.getSelectedDimensionSet().get(mainDimName).getValue();
        if (StringUtils.isEmpty((String)enityListStr)) {
            DimensionValueSet valueSet = new DimensionValueSet();
            valueSet.setValue("DATATIME", (Object)((DimensionValue)oneKeyCheckInfo.getContext().getDimensionSet().get("DATATIME")).getValue());
            IEntityQuery entityQuery = this.entityQueryHelper.getEntityQuery(oneKeyCheckInfo.getContext().getFormSchemeKey());
            entityQuery.setMasterKeys(valueSet);
            entityQuery.setIgnoreViewFilter(false);
            IEntityTable entityTable = null;
            try {
                entityTable = this.entityQueryHelper.buildEntityTable(entityQuery, oneKeyCheckInfo.getContext().getFormSchemeKey(), true);
                for (IEntityRow dataRow : entityTable.getAllRows()) {
                    entityData.add(dataRow.getEntityKeyData());
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            for (String entityKey : enityListStr.split(";")) {
                entityData.add(entityKey);
            }
        }
        return entityData;
    }
}

