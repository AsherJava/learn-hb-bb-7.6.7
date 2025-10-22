/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultData
 *  com.jiuqi.nr.data.logic.facade.param.output.FormulaData
 *  com.jiuqi.nr.data.logic.facade.param.output.FormulaNode
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultData;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaData;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaNode;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.SearchFormulaData;
import com.jiuqi.nr.jtable.params.output.DescriptionInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.FormulaNodeInfo;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CheckTransformUtil {
    private static final Logger logger = LoggerFactory.getLogger(CheckTransformUtil.class);
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;

    public FormulaCheckReturnInfo transformCheckResult(CheckResult checkResult, DimensionValueSet dimensionValueSet, JtableContext context, List<String> formKeys) {
        FormulaCheckReturnInfo formulaCheckReturnInfo = new FormulaCheckReturnInfo();
        formulaCheckReturnInfo.setMessage(checkResult.getMessage());
        formulaCheckReturnInfo.setTotalCount(checkResult.getTotalCount());
        formulaCheckReturnInfo.setDimensionList(checkResult.getDimensionList());
        Map checkTypeCountMap = checkResult.getCheckTypeCountMap();
        if (checkTypeCountMap.containsKey("\u9519\u8bef\u578b")) {
            formulaCheckReturnInfo.setErrorCount((Integer)checkTypeCountMap.get("\u9519\u8bef\u578b"));
        }
        if (checkTypeCountMap.containsKey("\u8b66\u544a\u578b")) {
            formulaCheckReturnInfo.setWarnCount((Integer)checkTypeCountMap.get("\u8b66\u544a\u578b"));
        }
        if (checkTypeCountMap.containsKey("\u63d0\u793a\u578b")) {
            formulaCheckReturnInfo.setHintCount((Integer)checkTypeCountMap.get("\u63d0\u793a\u578b"));
        }
        ArrayList<FormulaCheckResultInfo> resultInfos = new ArrayList<FormulaCheckResultInfo>();
        formulaCheckReturnInfo.setResults(resultInfos);
        for (CheckResultData resultData : checkResult.getResultData()) {
            FormulaCheckResultInfo resultInfo = new FormulaCheckResultInfo();
            resultInfo.setDimensionIndex(resultData.getDimensionIndex());
            DescriptionInfo descriptionInfo = this.transformDescInfo(resultData.getCheckDescription());
            resultInfo.setDescriptionInfo(descriptionInfo);
            resultInfo.setDimensionTitle(resultData.getDimensionTitle());
            resultInfo.setUnitCode(resultData.getUnitCode());
            resultInfo.setUnitKey(resultData.getUnitKey());
            resultInfo.setUnitTitle(resultData.getUnitTitle());
            resultInfo.setKey(resultData.getRecordId());
            resultInfo.setLeft(resultData.getLeft());
            resultInfo.setRight(resultData.getRight());
            resultInfo.setDifference(resultData.getDifference());
            resultInfo.setDesKey(descriptionInfo.getDescription() == null ? null : resultData.getRecordId());
            resultInfo.setFormula(this.transformFormulaData(resultData.getFormulaData()));
            resultInfo.setNodes(this.getFormulaNodeList(resultData.getFormulaNodeList()));
            resultInfo.setCkdDescIndexList(resultData.getCkdDescIndexList());
            ArrayList<String> formulaType = new ArrayList<String>();
            FormulaDefine formulaDefine = this.iFormulaRunTimeController.queryFormulaDefine(resultInfo.getFormula().getId());
            if (formulaDefine.getUseCalculate()) {
                formulaType.add("CALCULATE");
            }
            if (formulaDefine.getUseCheck()) {
                formulaType.add("CHECK");
            }
            if (formulaDefine.getUseBalance()) {
                formulaType.add("BALANCE");
            }
            resultInfo.setFormulaType(formulaType);
            resultInfos.add(resultInfo);
        }
        formulaCheckReturnInfo.setCkdDescList(checkResult.getCkdDescList());
        Map<Integer, Map<String, String>> uploadStateMap = dimensionValueSet != null ? this.getUploadStateMap(context, dimensionValueSet, formKeys, checkResult) : this.getUploadStateMap(context, formKeys, checkResult);
        formulaCheckReturnInfo.setUploadStateMap(uploadStateMap);
        return formulaCheckReturnInfo;
    }

    public FormulaCheckReturnInfo transformCheckResult(CheckResult checkResult, JtableContext context, List<String> formKeys) {
        return this.transformCheckResult(checkResult, null, context, formKeys);
    }

    private Map<Integer, Map<String, String>> getUploadStateMap(JtableContext context, List<String> formKeys, CheckResult checkResult) {
        Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        return this.getUploadStateMap(context, dimensionValueSet, formKeys, checkResult);
    }

    private Map<Integer, Map<String, String>> getUploadStateMap(JtableContext context, DimensionValueSet dimensionValueSet, List<String> formKeys, CheckResult checkResult) {
        WorkFlowType workFlowType = this.dataFlowService.queryStartType(context.getFormSchemeKey());
        ArrayList<String> groupKeys = new ArrayList<String>();
        if (workFlowType.equals((Object)WorkFlowType.GROUP)) {
            if (formKeys.isEmpty()) {
                List allFormGroupsInFormScheme = this.runTimeViewController.getAllFormGroupsInFormScheme(context.getFormSchemeKey());
                Iterator iterator = allFormGroupsInFormScheme.iterator();
                while (iterator.hasNext()) {
                    FormGroupDefine formGroupDefine = (FormGroupDefine)iterator.next();
                    groupKeys.add(formGroupDefine.getKey());
                }
            } else {
                for (String formKey : formKeys) {
                    List formGroupsByFormKey = this.runTimeViewController.getFormGroupsByFormKey(formKey);
                    Iterator iterator = formGroupsByFormKey.iterator();
                    while (iterator.hasNext()) {
                        FormGroupDefine formGroupDefine = (FormGroupDefine)iterator.next();
                        if (!formGroupDefine.getFormSchemeKey().equals(context.getFormSchemeKey())) continue;
                        groupKeys.add(formGroupDefine.getKey());
                    }
                }
            }
        } else if (workFlowType.equals((Object)WorkFlowType.FORM) && formKeys.isEmpty()) {
            formKeys = this.runTimeViewController.queryAllFormKeysByFormScheme(context.getFormSchemeKey());
        }
        List uploadStateNews = this.queryUploadStateService.queryUploadStates(context.getFormSchemeKey(), dimensionValueSet, formKeys, groupKeys);
        LinkedHashSet<Object> indexNames = null;
        if (!CollectionUtils.isEmpty(uploadStateNews)) {
            for (UploadStateNew uploadStateNew : uploadStateNews) {
                DimensionSet dimensionSet;
                if (indexNames != null) break;
                DimensionValueSet entities = uploadStateNew.getEntities();
                if (entities == null || (dimensionSet = entities.getDimensionSet()) == null) continue;
                ENameSet dimensions = dimensionSet.getDimensions();
                indexNames = new LinkedHashSet<Object>();
                for (int i = 0; i < dimensions.size(); ++i) {
                    String name = dimensions.get(i);
                    if (name == null || "PROCESSKEY".equals(name) || "FORMID".equals(name)) continue;
                    indexNames.add(name);
                }
            }
        }
        HashMap<Integer, Map<String, String>> uploadStateMap = new HashMap<Integer, Map<String, String>>();
        List dimensionList = checkResult.getDimensionList();
        HashMap map = new HashMap();
        if (indexNames != null) {
            for (int i = 0; i < dimensionList.size(); ++i) {
                Map dimMap = (Map)dimensionList.get(i);
                DimensionValueSet valueSet = new DimensionValueSet();
                for (String string : indexNames) {
                    DimensionValue dimensionValue = (DimensionValue)dimMap.get(string);
                    if (dimensionValue == null) continue;
                    valueSet.setValue(string, (Object)dimensionValue.getValue());
                }
                if (map.containsKey(valueSet)) {
                    ((List)map.get(valueSet)).add(i);
                    continue;
                }
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(i);
                map.put(valueSet, list);
            }
        }
        for (UploadStateNew uploadStateNew : uploadStateNews) {
            String code = uploadStateNew.getActionStateBean().getCode();
            if (StringUtils.isEmpty((String)code) || !code.equals(UploadStateEnum.UPLOADED.getCode()) && !code.equals(UploadStateEnum.CONFIRMED.getCode()) && !code.equals(UploadStateEnum.SUBMITED.getCode())) continue;
            DimensionValueSet entities = uploadStateNew.getEntities();
            entities.clearValue("PROCESSKEY");
            entities.clearValue("FORMID");
            List list = (List)map.get(entities);
            if (list == null) continue;
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                String formId;
                int index = (Integer)iterator.next();
                HashMap<String, String> formUploadMap = (HashMap<String, String>)uploadStateMap.get(index);
                if (formUploadMap == null) {
                    formUploadMap = new HashMap<String, String>();
                }
                if (com.jiuqi.bi.util.StringUtils.isEmpty((String)(formId = uploadStateNew.getFormId()))) {
                    formId = "00000000-0000-0000-0000-000000000000";
                }
                if (workFlowType.equals((Object)WorkFlowType.GROUP)) {
                    try {
                        List allFormsInGroup = this.runTimeViewController.getAllFormsInGroupWithoutOrder(formId, true);
                        for (FormDefine formDefine : allFormsInGroup) {
                            formUploadMap.put(formDefine.getKey(), code);
                        }
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                } else {
                    formUploadMap.put(formId, code);
                }
                uploadStateMap.put(index, formUploadMap);
            }
        }
        return uploadStateMap;
    }

    public DescriptionInfo transformDescInfo(CheckDescription checkDescription) {
        DescriptionInfo descriptionInfo = new DescriptionInfo();
        descriptionInfo.setDescription(checkDescription.getDescription());
        descriptionInfo.setUpdateTime(checkDescription.getUpdateTime());
        descriptionInfo.setUserId(checkDescription.getUserId());
        descriptionInfo.setUserTitle(checkDescription.getUserNickName());
        descriptionInfo.setState(checkDescription.getState());
        return descriptionInfo;
    }

    public com.jiuqi.nr.jtable.params.base.FormulaData transformFormulaData(FormulaData formulaData) {
        com.jiuqi.nr.jtable.params.base.FormulaData result = new com.jiuqi.nr.jtable.params.base.FormulaData();
        result.setKey(formulaData.getParsedExpressionKey());
        result.setId(formulaData.getKey());
        result.setCode(formulaData.getCode());
        result.setFormulaSchemeKey(formulaData.getFormulaSchemeKey());
        result.setFormKey(formulaData.getFormKey());
        result.setFormTitle(formulaData.getFormTitle());
        result.setGlobCol(formulaData.getGlobCol());
        result.setGlobRow(formulaData.getGlobRow());
        result.setChecktype(formulaData.getCheckType());
        result.setOrder(formulaData.getOrder());
        result.setMeanning(formulaData.getMeaning());
        result.setFormula(formulaData.getFormula());
        return result;
    }

    public FormulaNodeInfo transformFormulaNode(FormulaNode formulaNode) {
        FormulaNodeInfo formulaNodeInfo = new FormulaNodeInfo();
        formulaNodeInfo.setId(formulaNode.getDataId());
        formulaNodeInfo.setNodeShow(formulaNode.getNodeShow());
        formulaNodeInfo.setValue(formulaNode.getValue());
        formulaNodeInfo.setFormKey(formulaNode.getFormKey());
        formulaNodeInfo.setRegionKey(formulaNode.getRegionKey());
        formulaNodeInfo.setDataLinkKey(formulaNode.getDataLinkKey());
        formulaNodeInfo.setFieldTitle(formulaNode.getFieldTitle());
        formulaNodeInfo.setFieldType(formulaNode.getFieldType());
        formulaNodeInfo.setFieldKey(formulaNode.getFieldKey());
        formulaNodeInfo.setFormTitle(formulaNode.getFormTitle());
        formulaNodeInfo.setComplex(formulaNode.isComplex());
        formulaNodeInfo.setRelatedTaskInfo(formulaNode.getRelatedTaskInfo());
        return formulaNodeInfo;
    }

    public List<FormulaNodeInfo> getFormulaNodeList(List<FormulaNode> formulaNodeList) {
        ArrayList<FormulaNodeInfo> result = new ArrayList<FormulaNodeInfo>();
        for (FormulaNode formulaNode : formulaNodeList) {
            result.add(this.transformFormulaNode(formulaNode));
        }
        return result;
    }

    public SearchFormulaData transformSearchFormulaData(com.jiuqi.nr.jtable.params.base.FormulaData formulaData) {
        SearchFormulaData result = new SearchFormulaData();
        result.setKey(formulaData.getKey());
        result.setId(formulaData.getId());
        result.setCode(formulaData.getCode());
        result.setFormulaSchemeKey(formulaData.getFormulaSchemeKey());
        result.setFormKey(formulaData.getFormKey());
        result.setFormTitle(formulaData.getFormTitle());
        result.setChecktype(formulaData.getChecktype());
        result.setOrder(formulaData.getOrder());
        result.setAfterDivideText(formulaData.getMeanning());
        return result;
    }
}

