/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.DefaultQueryFilter
 *  com.jiuqi.nr.data.logic.facade.param.input.GroupType
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryCol
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryCondition
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryConditionBuilder
 *  com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.CheckTransformUtil
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.FormulaUtil
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.DefaultQueryFilter;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCol;
import com.jiuqi.nr.data.logic.facade.param.input.QueryCondition;
import com.jiuqi.nr.data.logic.facade.param.input.QueryConditionBuilder;
import com.jiuqi.nr.data.logic.facade.param.input.QueryFilterOperator;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.internal.service.util.CheckResultParamForReportUtil;
import com.jiuqi.nr.dataentry.internal.service.util.CheckTransformUtil2;
import com.jiuqi.nr.dataentry.internal.service.util.UploadCheckFliterUtil;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckGroupReturnInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.CheckTransformUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchCheckResultServiceImpl
implements IBatchCheckResultService {
    private static final Logger logger = LoggerFactory.getLogger(BatchCheckResultServiceImpl.class);
    @Autowired
    private ICheckResultService checkResultService;
    @Autowired
    private CheckTransformUtil checkTransformUtil;
    @Autowired
    private CheckTransformUtil2 checkTransformUtil2;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private CheckResultParamForReportUtil checkResultParamForReportUtil;
    @Autowired
    private UploadCheckFliterUtil uploadCheckFliterUtil;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private ActionMethod actionMethod;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;

    /*
     * WARNING - void declaration
     */
    @Override
    public FormulaCheckReturnInfo batchCheckResult(BatchCheckInfo batchCheckInfo) {
        CheckResult checkResult;
        Iterator<Map.Entry<String, List<String>>> checkCurrencyValue;
        boolean judgeCurrentcyType;
        List<Integer> checkTypes1 = batchCheckInfo.getCheckTypes();
        if (checkTypes1 == null || checkTypes1.isEmpty()) {
            return new FormulaCheckReturnInfo();
        }
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        JtableContext jtableContext = batchCheckInfo.getContext();
        DimensionValueSetUtil.fillDw((JtableContext)jtableContext, (String)batchCheckInfo.getDwScope());
        Map dimensionSet = jtableContext.getDimensionSet();
        if (batchCheckInfo.isWorkFlowCheck() && !(judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(jtableContext.getFormSchemeKey(), dimensionSet))) {
            DUserActionParam dUserActionParam = batchCheckInfo.getdUserActionParam();
            int checkCurrencyType = dUserActionParam.getCheckCurrencyType();
            checkCurrencyValue = dUserActionParam.getCheckCurrencyValue();
            this.uploadCheckFliterUtil.setCheckConditions(jtableContext.getTaskKey(), dimensionSet, checkCurrencyType, (String)((Object)checkCurrencyValue));
        }
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, jtableContext.getFormSchemeKey());
        checkResultQueryParam.setDimensionCollection(dimensionCollection);
        GroupType groupType = GroupType.unit;
        if (StringUtils.isNotEmpty((String)batchCheckInfo.getOrderField())) {
            groupType = GroupType.getByKey((String)batchCheckInfo.getOrderField().toLowerCase());
        }
        checkResultQueryParam.setGroupType(groupType);
        Map<String, List<String>> formulas = batchCheckInfo.getFormulas();
        if (formulas.isEmpty()) {
            checkResultQueryParam.setMode(Mode.FORM);
            checkResultQueryParam.setRangeKeys(new ArrayList());
        } else {
            checkCurrencyValue = formulas.entrySet().iterator();
            if (checkCurrencyValue.hasNext()) {
                Map.Entry entry = (Map.Entry)checkCurrencyValue.next();
                if (((List)entry.getValue()).isEmpty()) {
                    checkResultQueryParam.setMode(Mode.FORM);
                    checkResultQueryParam.setRangeKeys(new ArrayList<String>(formulas.keySet()));
                } else if ("null".equals(entry.getKey()) && (groupType == GroupType.form_formula || groupType == GroupType.checktype_form)) {
                    checkResultQueryParam.setMode(Mode.FORM);
                    checkResultQueryParam.setRangeKeys(new ArrayList((Collection)entry.getValue()));
                } else {
                    checkResultQueryParam.setMode(Mode.FORMULA);
                    ArrayList<String> formulaKeys = new ArrayList<String>();
                    for (List<String> value : formulas.values()) {
                        formulaKeys.addAll(value);
                    }
                    checkResultQueryParam.setRangeKeys(formulaKeys);
                }
            }
        }
        checkResultQueryParam.setVariableMap(batchCheckInfo.getContext().getVariableMap());
        checkResultQueryParam.setPagerInfo(batchCheckInfo.getPagerInfo());
        Map<Object, Object> checkTypes = new HashMap<Integer, Boolean>();
        boolean checkDesNull = batchCheckInfo.isCheckDesNull();
        List<Integer> uploadCheckTypes = batchCheckInfo.getUploadCheckTypes();
        if (batchCheckInfo.isWorkFlowCheck() && batchCheckInfo.isAffectCommit()) {
            Iterator<Integer> noFillErrorExplain = uploadCheckTypes == null || uploadCheckTypes.size() == 0 ? null : Boolean.valueOf(checkDesNull);
            checkTypes = this.checkResultParamForReportUtil.getCheckTypesMapForReport(jtableContext, batchCheckInfo.getChooseTypes(), (Boolean)((Object)noFillErrorExplain), batchCheckInfo.isAffectCommit(), batchCheckInfo.getdUserActionParam());
            if (checkTypes.isEmpty()) {
                return new FormulaCheckReturnInfo();
            }
        } else if (uploadCheckTypes.isEmpty()) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, null);
            }
        } else if (checkDesNull) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, false);
            }
        } else {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, true);
            }
        }
        checkResultQueryParam.setCheckTypes(checkTypes);
        if (batchCheckInfo.isWorkFlowCheck()) {
            DUserActionParam dUserActionParam = batchCheckInfo.getdUserActionParam();
            checkResultQueryParam.setFilterCondition(dUserActionParam.getCheckFilter());
            String checkFormulaValue = dUserActionParam.getCheckFormulaValue();
            String[] split = checkFormulaValue.split(";");
            checkResultQueryParam.setFormulaSchemeKeys(Arrays.asList(split));
            checkResultQueryParam.setFilterCondition(this.checkResultParamForReportUtil.getFilterCondition(jtableContext.getFormSchemeKey()));
        } else {
            List formulaSchemeList = FormulaUtil.getFormulaSchemeList((String)batchCheckInfo.getContext().getFormSchemeKey(), (String)batchCheckInfo.getFormulaSchemeKeys()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            checkResultQueryParam.setFormulaSchemeKeys(formulaSchemeList);
        }
        checkResultQueryParam.setBatchId(batchCheckInfo.getAsyncTaskKey());
        if (!batchCheckInfo.isAllDimResult()) {
            checkResultQueryParam.setQueryDimension(dimensionCollection);
        }
        QueryConditionBuilder queryConditionBuilder = null;
        if (batchCheckInfo.isEffectUpload() || batchCheckInfo.isAffectCommit()) {
            List<Integer> ignoreErrorStatus = new ArrayList();
            List<Object> needCommentErrorStatus = new ArrayList();
            if (batchCheckInfo.getCheckDesCheckTypes() != null) {
                for (Integer n : batchCheckInfo.getCheckDesCheckTypes().keySet()) {
                    if (batchCheckInfo.getCheckDesCheckTypes().get(n) == 1) {
                        ignoreErrorStatus.add(n);
                    }
                    if (batchCheckInfo.getCheckDesCheckTypes().get(n) != 2) continue;
                    needCommentErrorStatus.add(n);
                }
            } else if (batchCheckInfo.isWorkFlowCheck() && batchCheckInfo.isAffectCommit()) {
                Map<String, List<Integer>> map = this.checkResultParamForReportUtil.getFlowFormulaTypeMap(jtableContext, batchCheckInfo.getdUserActionParam());
                needCommentErrorStatus = map.get("NEEDEXPLAIN");
                for (Integer integer : checkTypes1) {
                    if (map.get("AFFECT").contains(integer)) continue;
                    ignoreErrorStatus.add(integer);
                }
            } else {
                logger.error("\u7efc\u5408\u5ba1\u6838\u672a\u4f20\u53c2\u6570CheckDesCheckTypes\uff0c\u6216\u975e\u4e0a\u62a5\u60c5\u51b5\u4e0b\u4e0d\u5e94\u67e5\u8be2\u5f71\u54cd\u4e0a\u62a5\u7684\u5ba1\u6838\u7c7b\u578b");
                ActionParam actionParam = this.actionMethod.getWlrkflowParam(jtableContext.getFormSchemeKey(), "act_upload");
                ignoreErrorStatus = actionParam.getIgnoreErrorStatus();
                needCommentErrorStatus = actionParam.getNeedCommentErrorStatus();
            }
            for (int i = 0; i < batchCheckInfo.getCheckTypes().size(); ++i) {
                void var16_31;
                Object var16_28 = null;
                Integer type = batchCheckInfo.getCheckTypes().get(i);
                if (ignoreErrorStatus.contains(type)) continue;
                if (needCommentErrorStatus.contains(type)) {
                    QueryConditionBuilder queryConditionBuilder2 = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)type);
                    if (batchCheckInfo.isEffectUpload() && batchCheckInfo.isCheckDesMustPass()) {
                        queryConditionBuilder2.andSubQuery((QueryCondition)DefaultQueryFilter.DES_IS_NULL_OR_CHECK_FAIL);
                    } else {
                        queryConditionBuilder2.andSubQuery((QueryCondition)DefaultQueryFilter.DES_IS_NULL);
                    }
                } else {
                    QueryConditionBuilder queryConditionBuilder3 = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)type);
                }
                if (queryConditionBuilder == null) {
                    queryConditionBuilder = new QueryConditionBuilder(var16_31.build());
                    continue;
                }
                queryConditionBuilder.orSubQuery(var16_31.build());
            }
        } else {
            QueryConditionBuilder queryConditionBuilderOR = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)batchCheckInfo.getCheckTypes().get(0));
            for (int i = 1; i < batchCheckInfo.getCheckTypes().size(); ++i) {
                queryConditionBuilderOR.or(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)batchCheckInfo.getCheckTypes().get(i));
            }
            queryConditionBuilder = new QueryConditionBuilder(queryConditionBuilderOR.build());
        }
        if (queryConditionBuilder == null) {
            FormulaCheckReturnInfo formulaCheckReturnInfo = new FormulaCheckReturnInfo();
            formulaCheckReturnInfo.setMessage("success");
            formulaCheckReturnInfo.setResults(new ArrayList());
            formulaCheckReturnInfo.setTotalCount(0);
            formulaCheckReturnInfo.setHintCount(0);
            formulaCheckReturnInfo.setErrorCount(0);
            formulaCheckReturnInfo.setWarnCount(0);
            return formulaCheckReturnInfo;
        }
        QueryConditionBuilder lastQueryConditionBuilder = new QueryConditionBuilder(queryConditionBuilder.build());
        if (batchCheckInfo.getUploadCheckTypes().size() != 0) {
            DefaultQueryFilter fiterCondition = null;
            fiterCondition = !batchCheckInfo.isCheckDesPass() ? DefaultQueryFilter.DES_CHECK_FAIL : (batchCheckInfo.isCheckDesNull() ? DefaultQueryFilter.DES_IS_NULL : DefaultQueryFilter.DES_IS_NOTNULL);
            lastQueryConditionBuilder.andSubQuery((QueryCondition)fiterCondition);
        }
        if (lastQueryConditionBuilder != null) {
            checkResultQueryParam.setQueryCondition(lastQueryConditionBuilder.build());
        }
        EntityViewData masterEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        String mainDimName = masterEntity.getDimensionName();
        CheckResultQueryParam checkResultQueryParam2 = this.buildFilterCondiotion(checkResultQueryParam, batchCheckInfo.getDescriptionFilterType(), batchCheckInfo.getDescriptionFilterContent(), batchCheckInfo.getFormulas());
        if (checkResultQueryParam2 == null) {
            checkResult = this.checkResultService.queryBatchCheckResult(checkResultQueryParam);
            checkResult.setResultData(new ArrayList());
            checkResult.setTotalCount(0);
        } else {
            checkResult = this.checkResultService.queryBatchCheckResult(checkResultQueryParam2);
        }
        List formKeys = batchCheckInfo.getFormulas().keySet().stream().collect(Collectors.toList());
        if (formKeys.contains("null")) {
            formKeys.remove("null");
            formKeys.addAll((Collection)batchCheckInfo.getFormulas().get("null"));
        }
        if ("formula".equals(batchCheckInfo.getOrderField())) {
            formKeys.clear();
        }
        DimensionValueSet dimensionValueSet = dimensionCollection.combineDim();
        if (((DimensionValue)jtableContext.getDimensionSet().get(mainDimName)).getValue().equals("")) {
            StringBuilder mdOrg = new StringBuilder();
            if (dimensionValueSet.getValue(mainDimName) instanceof List) {
                List allDW = (List)dimensionCollection.combineDim().getValue(mainDimName);
                for (String dw : allDW) {
                    mdOrg.append(dw).append(";");
                }
                mdOrg.deleteCharAt(mdOrg.length() - 1);
            } else {
                mdOrg.append(dimensionValueSet.getValue(mainDimName));
            }
            ((DimensionValue)jtableContext.getDimensionSet().get(mainDimName)).setValue(mdOrg.toString());
        }
        return this.checkTransformUtil.transformCheckResult(checkResult, dimensionValueSet, jtableContext, formKeys);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public FormulaCheckGroupReturnInfo batchCheckResultGroup(BatchCheckResultGroupInfo batchCheckResultGroupInfo) {
        CheckResultGroup checkResultGroup;
        CheckResultQueryParam param;
        boolean judgeCurrentcyType;
        List<Integer> checkTypes1 = batchCheckResultGroupInfo.getCheckTypes();
        if (checkTypes1 == null || checkTypes1.isEmpty()) {
            return new FormulaCheckGroupReturnInfo();
        }
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        JtableContext jtableContext = batchCheckResultGroupInfo.getContext();
        Map dimensionSet = jtableContext.getDimensionSet();
        if (batchCheckResultGroupInfo.isBatchUpload() && !(judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(jtableContext.getFormSchemeKey(), dimensionSet))) {
            DUserActionParam dUserActionParam = batchCheckResultGroupInfo.getdUserActionParam();
            int checkCurrencyType = dUserActionParam.getCheckCurrencyType();
            String checkCurrencyValue = dUserActionParam.getCheckCurrencyValue();
            this.uploadCheckFliterUtil.setCheckConditions(jtableContext.getTaskKey(), dimensionSet, checkCurrencyType, checkCurrencyValue);
        }
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, jtableContext.getFormSchemeKey());
        checkResultQueryParam.setDimensionCollection(dimensionCollection);
        checkResultQueryParam.setQueryDimension(dimensionCollection);
        checkResultQueryParam.setVariableMap(batchCheckResultGroupInfo.getContext().getVariableMap());
        checkResultQueryParam.setPagerInfo(batchCheckResultGroupInfo.getPagerInfo());
        HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
        boolean checkDesNull = batchCheckResultGroupInfo.isCheckDesNull();
        List<Integer> uploadCheckTypes = batchCheckResultGroupInfo.getUploadCheckTypes();
        if (uploadCheckTypes.isEmpty()) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, null);
            }
        } else if (checkDesNull) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, false);
            }
        } else {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, true);
            }
        }
        checkResultQueryParam.setCheckTypes(checkTypes);
        if (batchCheckResultGroupInfo.isBatchUpload()) {
            DUserActionParam dUserActionParam = batchCheckResultGroupInfo.getdUserActionParam();
            checkResultQueryParam.setFilterCondition(dUserActionParam.getCheckFilter());
            String checkFormulaValue = dUserActionParam.getCheckFormulaValue();
            String[] split = checkFormulaValue.split(";");
            checkResultQueryParam.setFormulaSchemeKeys(Arrays.asList(split));
            checkResultQueryParam.setFilterCondition(this.checkResultParamForReportUtil.getFilterCondition(jtableContext.getFormSchemeKey()));
        } else {
            List formulaSchemeList = FormulaUtil.getFormulaSchemeList((String)batchCheckResultGroupInfo.getContext().getFormSchemeKey(), (String)batchCheckResultGroupInfo.getFormulaSchemeKeys()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            checkResultQueryParam.setFormulaSchemeKeys(formulaSchemeList);
        }
        GroupType groupType = GroupType.unit;
        if (StringUtils.isNotEmpty((String)batchCheckResultGroupInfo.getOrderField())) {
            groupType = GroupType.getByKey((String)batchCheckResultGroupInfo.getOrderField().toLowerCase());
            if (batchCheckResultGroupInfo.getOrderField().equals("form_formula")) {
                checkResultQueryParam.setPagerInfo(null);
            }
        }
        checkResultQueryParam.setGroupType(groupType);
        checkResultQueryParam.setBatchId(batchCheckResultGroupInfo.getAsyncTaskKey());
        QueryConditionBuilder queryConditionBuilder = null;
        if (batchCheckResultGroupInfo.isEffectUpload() || batchCheckResultGroupInfo.isAffectCommit()) {
            List<Integer> ignoreErrorStatus = new ArrayList();
            List<Object> needCommentErrorStatus = new ArrayList();
            if (batchCheckResultGroupInfo.getCheckDesCheckTypes() != null) {
                for (Integer n : batchCheckResultGroupInfo.getCheckDesCheckTypes().keySet()) {
                    if (batchCheckResultGroupInfo.getCheckDesCheckTypes().get(n) == 1) {
                        ignoreErrorStatus.add(n);
                    }
                    if (batchCheckResultGroupInfo.getCheckDesCheckTypes().get(n) != 2) continue;
                    needCommentErrorStatus.add(n);
                }
            } else if (batchCheckResultGroupInfo.isBatchUpload()) {
                Map<String, List<Integer>> map = this.checkResultParamForReportUtil.getFlowFormulaTypeMap(jtableContext, batchCheckResultGroupInfo.getdUserActionParam());
                needCommentErrorStatus = map.get("NEEDEXPLAIN");
                for (Integer integer : checkTypes1) {
                    if (map.get("AFFECT").contains(integer)) continue;
                    ignoreErrorStatus.add(integer);
                }
            } else {
                logger.error("\u7efc\u5408\u5ba1\u6838\u672a\u4f20\u53c2\u6570CheckDesCheckTypes\uff0c\u6216\u975e\u4e0a\u62a5\u60c5\u51b5\u4e0b\u4e0d\u5e94\u67e5\u8be2\u5f71\u54cd\u4e0a\u62a5\u7684\u5ba1\u6838\u7c7b\u578b");
                ActionParam actionParam = this.actionMethod.getWlrkflowParam(jtableContext.getFormSchemeKey(), "act_upload");
                ignoreErrorStatus = actionParam.getIgnoreErrorStatus();
                needCommentErrorStatus = actionParam.getNeedCommentErrorStatus();
            }
            for (int i = 0; i < batchCheckResultGroupInfo.getCheckTypes().size(); ++i) {
                void var15_30;
                Object var15_27 = null;
                Integer type = batchCheckResultGroupInfo.getCheckTypes().get(i);
                if (ignoreErrorStatus.contains(type)) continue;
                if (needCommentErrorStatus.contains(type)) {
                    QueryConditionBuilder queryConditionBuilder2 = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)type);
                    if (batchCheckResultGroupInfo.isCheckDesMustPass()) {
                        queryConditionBuilder2.andSubQuery((QueryCondition)DefaultQueryFilter.DES_IS_NULL_OR_CHECK_FAIL);
                    } else {
                        queryConditionBuilder2.andSubQuery((QueryCondition)DefaultQueryFilter.DES_IS_NULL);
                    }
                } else {
                    QueryConditionBuilder queryConditionBuilder3 = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)type);
                }
                if (queryConditionBuilder == null) {
                    queryConditionBuilder = new QueryConditionBuilder(var15_30.build());
                    continue;
                }
                queryConditionBuilder.orSubQuery(var15_30.build());
            }
        } else {
            QueryConditionBuilder queryConditionBuilderOR = new QueryConditionBuilder(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)batchCheckResultGroupInfo.getCheckTypes().get(0));
            for (int i = 1; i < batchCheckResultGroupInfo.getCheckTypes().size(); ++i) {
                queryConditionBuilderOR.or(QueryCol.FORMULA_CHECK_TYPE, QueryFilterOperator.EQUALS, (Object)batchCheckResultGroupInfo.getCheckTypes().get(i));
            }
            queryConditionBuilder = new QueryConditionBuilder(queryConditionBuilderOR.build());
        }
        if (queryConditionBuilder == null) {
            FormulaCheckGroupReturnInfo formulaCheckGroupReturnInfo = new FormulaCheckGroupReturnInfo();
            formulaCheckGroupReturnInfo.setMessage("success");
            formulaCheckGroupReturnInfo.setResults(new ArrayList<FormulaCheckResultGroupInfo>());
            formulaCheckGroupReturnInfo.setTotalCount(0);
            formulaCheckGroupReturnInfo.setHintCount(0);
            formulaCheckGroupReturnInfo.setErrorCount(0);
            formulaCheckGroupReturnInfo.setWarnCount(0);
            formulaCheckGroupReturnInfo.setShowCount(0);
            return formulaCheckGroupReturnInfo;
        }
        QueryConditionBuilder lastQueryConditionBuilder = new QueryConditionBuilder(queryConditionBuilder.build());
        if (batchCheckResultGroupInfo.getUploadCheckTypes().size() != 0) {
            DefaultQueryFilter fiterCondition = null;
            fiterCondition = !batchCheckResultGroupInfo.isCheckDesPass() ? DefaultQueryFilter.DES_CHECK_FAIL : (batchCheckResultGroupInfo.isCheckDesNull() ? DefaultQueryFilter.DES_IS_NULL : DefaultQueryFilter.DES_IS_NOTNULL);
            lastQueryConditionBuilder.andSubQuery((QueryCondition)fiterCondition);
        }
        if (lastQueryConditionBuilder != null) {
            checkResultQueryParam.setQueryCondition(lastQueryConditionBuilder.build());
        }
        if (!batchCheckResultGroupInfo.getFormulas().isEmpty()) {
            HashSet<String> formKeys = new HashSet<String>();
            HashSet formulaIds = new HashSet();
            for (String key : batchCheckResultGroupInfo.getFormulas().keySet()) {
                formKeys.add(key);
                formulaIds.addAll(batchCheckResultGroupInfo.getFormulas().get(key));
            }
            if (formulaIds.size() > 0) {
                checkResultQueryParam.setMode(Mode.FORMULA);
                checkResultQueryParam.setRangeKeys(new ArrayList(formulaIds));
            } else {
                checkResultQueryParam.setMode(Mode.FORM);
                checkResultQueryParam.setRangeKeys(new ArrayList(formKeys));
            }
        }
        if ((param = this.buildFilterCondiotion(checkResultQueryParam, batchCheckResultGroupInfo.getDescriptionFilterType(), batchCheckResultGroupInfo.getDescriptionFilterContent(), batchCheckResultGroupInfo.getFormulas())) == null) {
            checkResultGroup = this.checkResultService.queryBatchCheckResultGroup(checkResultQueryParam);
            checkResultGroup.setGroupData(new ArrayList());
            checkResultGroup.setTotalCount(0);
            checkResultGroup.setShowCount(0);
        } else {
            checkResultGroup = this.checkResultService.queryBatchCheckResultGroup(param);
        }
        return this.checkTransformUtil2.transformCheckResultGroup(checkResultGroup);
    }

    @Override
    public CheckResultQueryParam buildFilterCondiotion(CheckResultQueryParam checkResultQueryParam, String filterType, String filterContent, Map<String, List<String>> formulas) {
        if (StringUtils.isEmpty((String)filterContent)) {
            return checkResultQueryParam;
        }
        if ("ERROR_DES".equals(filterType)) {
            QueryCondition queryCondition = checkResultQueryParam.getQueryCondition();
            StringBuffer filter = new StringBuffer();
            filter.append("%").append(filterContent).append("%");
            QueryConditionBuilder queryConditionBuilder = queryCondition == null ? new QueryConditionBuilder(QueryCol.CHECK_ERROR_DES, QueryFilterOperator.LIKE, (Object)filter.toString()) : new QueryConditionBuilder(queryCondition).and(QueryCol.CHECK_ERROR_DES, QueryFilterOperator.LIKE, (Object)filter.toString());
            QueryCondition build = queryConditionBuilder.build();
            checkResultQueryParam.setQueryCondition(build);
        }
        if ("FORMULA_DES".equals(filterType)) {
            ArrayList<String> filterFormula = new ArrayList<String>();
            ArrayList<FormulaDefine> beforeFilterFormula = new ArrayList<FormulaDefine>();
            if (formulas.size() == 0) {
                for (String key : checkResultQueryParam.getFormulaSchemeKeys()) {
                    beforeFilterFormula.addAll(this.iFormulaRunTimeController.getCheckFormulasInScheme(key));
                }
            } else {
                for (String formkey : formulas.keySet()) {
                    List<String> form_formula = formulas.get(formkey);
                    List form_formulaDefine = this.iFormulaRunTimeController.getCheckFormulasInForm((String)checkResultQueryParam.getFormulaSchemeKeys().get(0), formkey);
                    if (form_formula.size() == 0) {
                        beforeFilterFormula.addAll(form_formulaDefine);
                        continue;
                    }
                    for (FormulaDefine formulaDefine : form_formulaDefine) {
                        if (!form_formula.contains(formulaDefine.getKey())) continue;
                        beforeFilterFormula.add(formulaDefine);
                    }
                }
            }
            for (FormulaDefine formulaDefine : beforeFilterFormula) {
                if (formulaDefine.getDescription() == null || !formulaDefine.getDescription().toLowerCase().contains(filterContent.toLowerCase())) continue;
                filterFormula.add(formulaDefine.getKey());
            }
            if (filterFormula.size() == 0) {
                return null;
            }
            checkResultQueryParam.setMode(Mode.FORMULA);
            checkResultQueryParam.setRangeKeys(filterFormula);
        }
        return checkResultQueryParam;
    }
}

