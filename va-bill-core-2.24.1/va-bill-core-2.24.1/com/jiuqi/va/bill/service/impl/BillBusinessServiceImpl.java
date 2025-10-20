/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.ModelNode
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.business.ConditionGroupRow
 *  com.jiuqi.va.domain.workflow.business.ConditionView
 *  com.jiuqi.va.domain.workflow.business.Formula
 *  com.jiuqi.va.domain.workflow.business.GroupInfo
 *  com.jiuqi.va.domain.workflow.business.TargetObject
 *  com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO
 *  com.jiuqi.va.domain.workflow.business.WorkflowVariable
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.bill.handler.BizProcessNodeProcessor;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.service.BillBusinessService;
import com.jiuqi.va.bill.service.MetaInfoService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.business.ConditionGroupRow;
import com.jiuqi.va.domain.workflow.business.ConditionView;
import com.jiuqi.va.domain.workflow.business.Formula;
import com.jiuqi.va.domain.workflow.business.GroupInfo;
import com.jiuqi.va.domain.workflow.business.TargetObject;
import com.jiuqi.va.domain.workflow.business.WorkflowBusinessDistributeDTO;
import com.jiuqi.va.domain.workflow.business.WorkflowVariable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class BillBusinessServiceImpl
implements BillBusinessService,
InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(BillBusinessServiceImpl.class);
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired
    private MetaInfoService metaInfoService;
    @Autowired(required=false)
    private List<BizProcessNodeProcessor> processNodeProcessors;
    private Map<String, BizProcessNodeProcessor> bizProcessNodeProcessorsMap;

    @Override
    public Map<String, WorkflowBusinessDistributeDTO> handleDistributeParams(WorkflowBusinessDistributeDTO distributeDTO) {
        Integer distributeType = distributeDTO.getDistributeType();
        if (distributeType == 0) {
            return this.handleAdaptCondition(distributeDTO);
        }
        if (distributeType == 1) {
            return this.handleParamValue(distributeDTO);
        }
        return Collections.emptyMap();
    }

    @Override
    public List<ProcessNodeDO> businessProcessNodeProcessed(List<ProcessNodeDO> processNodes) {
        if (CollectionUtils.isEmpty(this.bizProcessNodeProcessorsMap)) {
            return processNodes;
        }
        Set sysCodes = processNodes.stream().map(ProcessNodeDO::getSyscode).collect(Collectors.toSet());
        for (String sysCode : sysCodes) {
            BizProcessNodeProcessor processor = this.bizProcessNodeProcessorsMap.get(sysCode);
            if (processor == null) continue;
            processor.processed(processNodes);
        }
        return processNodes;
    }

    @Override
    public void afterPropertiesSet() {
        this.bizProcessNodeProcessorsMap = new HashMap<String, BizProcessNodeProcessor>();
        if (this.processNodeProcessors == null) {
            return;
        }
        for (BizProcessNodeProcessor processor : this.processNodeProcessors) {
            this.bizProcessNodeProcessorsMap.put(processor.getSysCode(), processor);
        }
    }

    private Map<String, WorkflowBusinessDistributeDTO> handleAdaptCondition(WorkflowBusinessDistributeDTO distributeDTO) {
        Formula adaptCondition = distributeDTO.getAdaptCondition();
        String sourceBizDefine = distributeDTO.getSourceBizDefine();
        ModelDefine modelDefine = this.modelDefineService.getDefine(sourceBizDefine);
        String masterTableName = this.getTableNameByBizDefine(sourceBizDefine);
        List<Integer> masterIndexList = this.getMasterIndexList(adaptCondition, modelDefine, masterTableName);
        HashMap<String, List<DataModelColumn>> dataModelColumnCacheMap = new HashMap<String, List<DataModelColumn>>();
        Map<String, DataModelColumn> fieldMap = this.getFieldMapByTableName(dataModelColumnCacheMap, masterTableName);
        ConditionView conditionView = distributeDTO.getConditionView();
        Set targetDefineList = distributeDTO.getTargetObjectList().stream().map(TargetObject::getBizName).collect(Collectors.toSet());
        HashMap<String, WorkflowBusinessDistributeDTO> resultMap = new HashMap<String, WorkflowBusinessDistributeDTO>(targetDefineList.size());
        for (String targetBizDefine : targetDefineList) {
            WorkflowBusinessDistributeDTO resultDTO = new WorkflowBusinessDistributeDTO();
            resultDTO.setSourceBizDefine(targetBizDefine);
            resultDTO.setAdaptCondition(adaptCondition);
            resultDTO.setConditionView(conditionView);
            resultMap.put(targetBizDefine, resultDTO);
            try {
                String targetTableName = this.getTableNameByBizDefine(targetBizDefine);
                if (sourceBizDefine.equals(targetBizDefine) || targetTableName.equals(masterTableName)) continue;
                StringBuilder sb = new StringBuilder(adaptCondition.getExpression());
                int length = masterTableName.length();
                for (Integer index : masterIndexList) {
                    sb.replace(index, index + length, targetTableName);
                }
                Formula newAdaptCondition = new Formula();
                newAdaptCondition.setExpression(sb.toString());
                newAdaptCondition.setFormulaType(adaptCondition.getFormulaType());
                resultDTO.setAdaptCondition(newAdaptCondition);
                Map<String, DataModelColumn> targetFieldMap = this.getFieldMapByTableName(dataModelColumnCacheMap, targetTableName);
                resultDTO.setConditionView(this.createNewConditionView(conditionView, fieldMap, targetFieldMap));
            }
            catch (Exception e) {
                log.error("\u9002\u5e94\u6761\u4ef6\u6216\u6761\u4ef6\u7ec4\u4e0b\u53d1\u7ed9\u5355\u636e\u5b9a\u4e49{}\u65f6\u51fa\u9519\uff0c", (Object)targetBizDefine, (Object)e);
                resultDTO.setFailedMessage(BillCoreI18nUtil.getMessage("va.bill.core.handleadaptconditionfailed"));
            }
        }
        return resultMap;
    }

    private Map<String, WorkflowBusinessDistributeDTO> handleParamValue(WorkflowBusinessDistributeDTO distributeDTO) {
        List workflowVariableList = distributeDTO.getWorkflowVariableList();
        String sourceBizDefine = distributeDTO.getSourceBizDefine();
        String masterTableName = this.getTableNameByBizDefine(sourceBizDefine);
        Map<Object, List<Integer>> masterIndexListMap = this.getMasterIndexListMap(workflowVariableList, sourceBizDefine, masterTableName);
        Set targetDefineList = distributeDTO.getTargetObjectList().stream().map(TargetObject::getBizName).collect(Collectors.toSet());
        HashMap<String, WorkflowBusinessDistributeDTO> resultMap = new HashMap<String, WorkflowBusinessDistributeDTO>(targetDefineList.size());
        for (String targetBizDefine : targetDefineList) {
            WorkflowBusinessDistributeDTO resultDTO = new WorkflowBusinessDistributeDTO();
            resultMap.put(targetBizDefine, resultDTO);
            resultDTO.setSourceBizDefine(targetBizDefine);
            resultDTO.setWorkflowVariableList(workflowVariableList);
            try {
                String targetTableName = this.getTableNameByBizDefine(targetBizDefine);
                if (sourceBizDefine.equals(targetBizDefine) || masterTableName.equals(targetTableName)) continue;
                List<WorkflowVariable> newWorkflowVariableList = BillBusinessServiceImpl.createNewVariableMap(workflowVariableList, masterIndexListMap, masterTableName, targetTableName);
                resultDTO.setWorkflowVariableList(newWorkflowVariableList);
            }
            catch (Exception e) {
                log.error("\u53c2\u6570\u53d6\u503c\u4e0b\u53d1\u7ed9\u5355\u636e\u5b9a\u4e49{}\u65f6\u51fa\u9519\uff0c", (Object)targetBizDefine, (Object)e);
                resultDTO.setFailedMessage(BillCoreI18nUtil.getMessage("va.bill.core.handleparamvaluefailed"));
            }
        }
        return resultMap;
    }

    private String getTableNameByBizDefine(String sourceBizDefine) {
        Map<String, Object> tablesNameMap = this.metaInfoService.getTablesName(sourceBizDefine);
        return (String)tablesNameMap.get("name");
    }

    private List<Integer> getMasterIndexList(Formula formula, ModelDefine modelDefine, String masterTableName) {
        IExpression iExpression;
        ModelDataContext context = new ModelDataContext(modelDefine);
        String adaptConditionExpression = formula.getExpression();
        if (!StringUtils.hasText(adaptConditionExpression)) {
            return new ArrayList<Integer>();
        }
        FormulaType formulaType = FormulaType.valueOf((String)formula.getFormulaType());
        try {
            iExpression = ModelFormulaHandle.getInstance().parse(context, adaptConditionExpression, formulaType);
        }
        catch (ParseException e) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.bill.core.checkformula"));
        }
        ArrayList<Integer> masterIndex = new ArrayList<Integer>();
        ArrayDeque<IASTNode> stack = new ArrayDeque<IASTNode>();
        IASTNode child = iExpression.getChild(0);
        stack.push(child);
        while (!stack.isEmpty()) {
            IASTNode curNode = (IASTNode)stack.pop();
            if (curNode instanceof ModelNode) {
                ModelNode modelNode = (ModelNode)curNode;
                String tableName = modelNode.getTableName();
                if (!masterTableName.equals(tableName)) {
                    stack.clear();
                    throw new BillException(BillCoreI18nUtil.getMessage("va.bill.core.notallowdistribute"));
                }
                Token token = modelNode.getToken();
                int index = token.index();
                masterIndex.add(index - masterTableName.length());
            }
            for (int i = 0; i < curNode.childrenSize(); ++i) {
                stack.push(curNode.getChild(i));
            }
        }
        return masterIndex;
    }

    private Map<String, DataModelColumn> getFieldMapByTableName(Map<String, List<DataModelColumn>> dataModelColumnCacheMap, String masterTableName) {
        List<DataModelColumn> dataModelColumnList = BillUtils.getDataModelColumnList(dataModelColumnCacheMap, masterTableName);
        return dataModelColumnList.stream().collect(Collectors.toMap(DataModelColumn::getColumnName, o -> o));
    }

    private ConditionView createNewConditionView(ConditionView conditionView, Map<String, DataModelColumn> fieldMap, Map<String, DataModelColumn> targetFieldMap) {
        ConditionView newConditionView = new ConditionView();
        newConditionView.setGroupRelation(conditionView.getGroupRelation());
        List groupInfoList = conditionView.getGroupInfoList();
        if (!CollectionUtils.isEmpty(groupInfoList)) {
            ArrayList<GroupInfo> newGroupInfoList = new ArrayList<GroupInfo>(groupInfoList.size());
            newConditionView.setGroupInfoList(newGroupInfoList);
            for (GroupInfo groupInfo : groupInfoList) {
                GroupInfo newGroupInfo = this.createNewGroupInfo(groupInfo, fieldMap, targetFieldMap);
                if (newGroupInfo == null) continue;
                newGroupInfoList.add(newGroupInfo);
            }
        }
        return newConditionView;
    }

    private GroupInfo createNewGroupInfo(GroupInfo groupInfo, Map<String, DataModelColumn> fieldMap, Map<String, DataModelColumn> targetFieldMap) {
        GroupInfo newGroupInfo = new GroupInfo();
        newGroupInfo.setName(groupInfo.getName());
        List infoList = groupInfo.getInfo();
        if (!CollectionUtils.isEmpty(infoList)) {
            ArrayList<ConditionGroupRow> newInfoList = new ArrayList<ConditionGroupRow>(infoList.size());
            newGroupInfo.setInfo(newInfoList);
            for (ConditionGroupRow info : infoList) {
                ConditionGroupRow newInfo = this.createNewInfo(info, fieldMap, targetFieldMap);
                if (newInfo == null) continue;
                newInfoList.add(newInfo);
            }
            if (newInfoList.isEmpty()) {
                return null;
            }
        }
        return newGroupInfo;
    }

    private ConditionGroupRow createNewInfo(ConditionGroupRow info, Map<String, DataModelColumn> fieldMap, Map<String, DataModelColumn> targetFieldMap) {
        String name = info.getBizField().getName();
        DataModelColumn targetDataModel = targetFieldMap.get(name);
        if (targetDataModel == null) {
            return null;
        }
        DataModelColumn sourceDataModel = fieldMap.get(name);
        DataModelType.ColumnType sourceColumnType = sourceDataModel.getColumnType();
        Integer mappingType = sourceDataModel.getMappingType();
        String mapping = sourceDataModel.getMapping();
        DataModelType.ColumnType targetColumnType = targetDataModel.getColumnType();
        Integer targetMappingType = targetDataModel.getMappingType();
        String targetMapping = targetDataModel.getMapping();
        if (sourceColumnType == targetColumnType && Objects.equals(mappingType, targetMappingType) && Objects.equals(mapping, targetMapping)) {
            ConditionGroupRow newInfo = new ConditionGroupRow();
            BeanUtils.copyProperties(info, newInfo);
            newInfo.getBizField().setTitle(targetDataModel.getColumnTitle());
            return newInfo;
        }
        return null;
    }

    private Map<Object, List<Integer>> getMasterIndexListMap(List<WorkflowVariable> workflowVariableList, String sourceBizDefine, String masterTableName) {
        HashMap<Object, List<Integer>> masterIndexListMap = new HashMap<Object, List<Integer>>(workflowVariableList.size());
        ModelDefine modelDefine = this.modelDefineService.getDefine(sourceBizDefine);
        for (WorkflowVariable workflowVariable : workflowVariableList) {
            Formula valueFormula = workflowVariable.getFormula();
            if (valueFormula == null) continue;
            try {
                List<Integer> masterIndexList = this.getMasterIndexList(valueFormula, modelDefine, masterTableName);
                masterIndexListMap.put(workflowVariable.getParamName(), masterIndexList);
            }
            catch (Exception e) {
                String param = workflowVariable.getParamTitle() + "(" + workflowVariable.getParamName() + ")";
                throw new BillException(param + e.getMessage());
            }
        }
        return masterIndexListMap;
    }

    private static List<WorkflowVariable> createNewVariableMap(List<WorkflowVariable> workflowVariableList, Map<Object, List<Integer>> masterIndexListMap, String masterTableName, String targetTableName) {
        ArrayList<WorkflowVariable> newWorkflowVariableList = new ArrayList<WorkflowVariable>(workflowVariableList.size());
        for (WorkflowVariable workflowVariable : workflowVariableList) {
            WorkflowVariable newWorkflowVariable = new WorkflowVariable();
            BeanUtils.copyProperties(workflowVariable, newWorkflowVariable);
            List<Integer> masterIndexList = masterIndexListMap.get(workflowVariable.getParamName());
            Formula oldFormula = workflowVariable.getFormula();
            if (oldFormula != null) {
                StringBuilder sb = new StringBuilder(oldFormula.getExpression());
                for (Integer index : masterIndexList) {
                    sb.replace(index, index + masterTableName.length(), targetTableName);
                }
                Formula newFormula = new Formula();
                newFormula.setExpression(sb.toString());
                newFormula.setFormulaType(oldFormula.getFormulaType());
                newWorkflowVariable.setFormula(newFormula);
            }
            newWorkflowVariableList.add(newWorkflowVariable);
        }
        return newWorkflowVariableList;
    }
}

