/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.utils.BizBindingI18nUtil
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.data.redis.core.SetOperations
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.service.BillCoreWorkFlowService;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class BillCoreWorkFlowServiceImpl
implements BillCoreWorkFlowService {
    private static final Logger logger = LoggerFactory.getLogger(BillCoreWorkFlowServiceImpl.class);
    private static final int MAP_CAPACITY = 16;
    private static final String KEY_PRE_FIX = "BILL_WORKFLOW_RELATION_VARIABLES:";
    private final Map<String, Map<String, Map<String, Map<String, FormulaImpl>>>> tenantBufferMap = new ConcurrentHashMap<String, Map<String, Map<String, Map<String, FormulaImpl>>>>();
    private final Map<String, Map<String, Map<String, Map<String, String>>>> tenantBufferHelperMap = new ConcurrentHashMap<String, Map<String, Map<String, Map<String, String>>>>();
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void clearWorkFowParamsFormulaCache() {
        this.tenantBufferMap.clear();
    }

    @Override
    public Map<String, Object> getWorkFlowParamsValueMap(BillModel model, Map<String, Object> billWorkflowRelation) {
        Set workFlowDefineVersionSet;
        String tenantName = ShiroUtil.getTenantName();
        HashMap<String, Object> workFlowParamMap = new HashMap<String, Object>(16);
        String billDefineName = model.getDefine().getName();
        String workFlowDefineKey = BillUtils.valueToString(billWorkflowRelation.get("workflowdefinekey"));
        String workFlowDefineVersion = BillUtils.valueToString(billWorkflowRelation.get("workflowdefineversion"));
        List workFlowVariables = (List)billWorkflowRelation.get("workflowvariables");
        if (ObjectUtils.isEmpty(workFlowVariables)) {
            return workFlowParamMap;
        }
        String defineKey = workFlowDefineKey + "-" + billDefineName;
        String redisKey = KEY_PRE_FIX + tenantName + ":" + defineKey;
        SetOperations setOperation = this.redisTemplate.opsForSet();
        Map workFlowDefineMap = Optional.ofNullable(this.tenantBufferMap.get(tenantName)).orElse(new ConcurrentHashMap(16));
        this.tenantBufferMap.put(tenantName, workFlowDefineMap);
        Map workFlowDefineOriginalMap = Optional.ofNullable(this.tenantBufferHelperMap.get(tenantName)).orElse(new ConcurrentHashMap(16));
        this.tenantBufferHelperMap.put(tenantName, workFlowDefineOriginalMap);
        Map compiledFormulaMap = null;
        Map originalFormulaMap = null;
        Map workFlowDefineVersionMap = Optional.ofNullable(workFlowDefineMap.get(defineKey)).orElse(new ConcurrentHashMap(16));
        Map workFlowDefineVersionHelperMap = Optional.ofNullable(workFlowDefineOriginalMap.get(defineKey)).orElse(new ConcurrentHashMap(16));
        if (workFlowDefineVersionMap.containsKey(workFlowDefineVersion) && (workFlowDefineVersionSet = Optional.ofNullable(setOperation.members((Object)redisKey)).orElse(Collections.emptySet())).contains(workFlowDefineVersion)) {
            if (Objects.nonNull(workFlowDefineMap.get(defineKey))) {
                compiledFormulaMap = (Map)((Map)workFlowDefineMap.get(defineKey)).get(workFlowDefineVersion);
            }
            if (Objects.nonNull(workFlowDefineOriginalMap.get(defineKey))) {
                originalFormulaMap = (Map)((Map)workFlowDefineOriginalMap.get(defineKey)).get(workFlowDefineVersion);
            }
        }
        compiledFormulaMap = Optional.ofNullable(compiledFormulaMap).orElse(new ConcurrentHashMap());
        originalFormulaMap = Optional.ofNullable(originalFormulaMap).orElse(new ConcurrentHashMap());
        if (CollectionUtils.isEmpty(compiledFormulaMap) || CollectionUtils.isEmpty(originalFormulaMap)) {
            for (Map param : workFlowVariables) {
                this.compileFormulaAddCache(param, compiledFormulaMap, originalFormulaMap, model);
            }
            workFlowDefineVersionMap.put(workFlowDefineVersion, compiledFormulaMap);
            workFlowDefineMap.put(defineKey, workFlowDefineVersionMap);
            workFlowDefineVersionHelperMap.put(workFlowDefineVersion, originalFormulaMap);
            workFlowDefineOriginalMap.put(defineKey, workFlowDefineVersionHelperMap);
            setOperation.add((Object)redisKey, (Object[])new String[]{workFlowDefineVersion});
        }
        for (Map param : workFlowVariables) {
            String paramName = BillUtils.valueToString(param.get("paramName"));
            Map valueFormula = (Map)param.get("valueformula");
            String newExpression = BillUtils.valueToString(valueFormula.get("expression"));
            String cacheExpression = (String)originalFormulaMap.get(paramName);
            if (!StringUtils.hasText(cacheExpression) || !cacheExpression.equalsIgnoreCase(newExpression)) {
                this.compileFormulaAddCache(param, compiledFormulaMap, originalFormulaMap, model);
            }
            if (Objects.isNull(compiledFormulaMap.get(paramName))) continue;
            Object formulaResult = FormulaUtils.executeFormula((FormulaImpl)((FormulaImpl)compiledFormulaMap.get(paramName)), (Model)model);
            if (formulaResult instanceof ArrayData) {
                int baseType = ((ArrayData)formulaResult).baseType();
                HashMap<String, Object> jsonObject = new HashMap<String, Object>();
                jsonObject.put("baseType", baseType);
                jsonObject.put("data", this.getArrayData(formulaResult));
                workFlowParamMap.put(paramName, JSONUtil.toJSONString(jsonObject));
                continue;
            }
            if (!Objects.nonNull(formulaResult)) continue;
            workFlowParamMap.put(paramName, formulaResult);
        }
        return workFlowParamMap;
    }

    private Object getArrayData(Object formulaResult) {
        ArrayList<List> array = new ArrayList<List>();
        ArrayData arrayData = (ArrayData)formulaResult;
        for (Object arrayDatum : arrayData) {
            if (arrayDatum instanceof ArrayData) {
                ArrayData arrayDataChildren = (ArrayData)arrayDatum;
                array.add(arrayDataChildren.toList());
                continue;
            }
            return arrayData.toList();
        }
        return array;
    }

    private void compileFormulaAddCache(Map<String, Object> param, Map<String, FormulaImpl> compiledFormulaMap, Map<String, String> originalFormulaMap, BillModel model) {
        String paramName = BillUtils.valueToString(param.get("paramName"));
        Map valueFormula = (Map)param.get("valueformula");
        String expression = BillUtils.valueToString(valueFormula.get("expression"));
        FormulaType formulaType = FormulaType.valueOf((String)BillUtils.valueToString(valueFormula.get("formulaType")));
        if (!StringUtils.hasText(expression)) {
            compiledFormulaMap.remove(paramName);
            originalFormulaMap.remove(paramName);
            return;
        }
        try {
            IExpression compiledExpression = ModelFormulaHandle.getInstance().parse(new ModelDataContext((Model)model), expression, formulaType);
            FormulaImpl formulaImpl = new FormulaImpl();
            formulaImpl.setCompiledExpression(compiledExpression);
            formulaImpl.setExpression(expression);
            formulaImpl.setFormulaType(formulaType);
            compiledFormulaMap.put(paramName, formulaImpl);
            originalFormulaMap.put(paramName, expression);
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(BizBindingI18nUtil.getMessage((String)"va.bizbinding.formulautils.parseformulaexception", (Object[])new Object[]{expression}));
        }
    }
}

