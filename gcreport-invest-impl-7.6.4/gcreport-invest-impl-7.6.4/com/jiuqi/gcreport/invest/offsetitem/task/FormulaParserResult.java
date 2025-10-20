/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.nr.impl.service.impl.GCFormTabSelectServiceImpl
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.FormTableFields
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.invest.common.InvestConst;
import com.jiuqi.gcreport.nr.impl.service.impl.GCFormTabSelectServiceImpl;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormTableFields;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FormulaParserResult {
    private IASTNode node;
    private String nodeFormula;
    private String operateSource;
    private String nodeTitle;
    public boolean billTableFlag;

    FormulaParserResult(IASTNode node, String nodeFormula) {
        this.node = node;
        this.nodeFormula = nodeFormula;
    }

    public ASTNodeType getNodeType() {
        return this.node.getNodeType();
    }

    public String getNodeFormula() {
        return this.nodeFormula;
    }

    public void setNode(IASTNode astNode) {
        this.node = astNode;
    }

    public boolean isBillTableFlag() {
        return this.billTableFlag;
    }

    public void setBillTableFlag(boolean billTableFlag) {
        this.billTableFlag = billTableFlag;
    }

    public String getNodeTitle(String schemeId, String defaultPeriod) throws Exception {
        if (null == this.nodeTitle) {
            this.init(schemeId, defaultPeriod);
        }
        return this.nodeTitle;
    }

    public String getOperateSource(String schemeId, String defaultPeriod) throws Exception {
        if (null == this.operateSource) {
            this.init(schemeId, defaultPeriod);
        }
        return this.operateSource;
    }

    private void init(String schemeId, String defaultPeriod) throws Exception {
        ASTNodeType nodeType = this.getNodeType();
        if (nodeType == ASTNodeType.FUNCTION) {
            FunctionNode function = (FunctionNode)this.node;
            IFunction define = function.getDefine();
            this.nodeTitle = define.title();
            this.operateSource = "\u81ea\u52a8\u8ba1\u7b97";
        } else if (nodeType == ASTNodeType.DYNAMICDATA) {
            Map<String, String> billTableCode2NameMap = InvestConst.tableName2TitleMap;
            DynamicDataNode dynamicDataNode = (DynamicDataNode)this.node;
            QueryField queryField = dynamicDataNode.getQueryField();
            String tableName = queryField.getTableName();
            if (billTableCode2NameMap.containsKey(tableName)) {
                this.billTableFlag = true;
                Map<String, String> fieldCode2TitleMap = NrTool.queryAllColumnsInTable((String)tableName).stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getTitle, (c1, c2) -> c1));
                this.nodeTitle = fieldCode2TitleMap.get(queryField.getFieldName());
                this.operateSource = billTableCode2NameMap.get(tableName);
            } else {
                ConsolidatedTaskVO consolidatedTaskVO = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getTaskBySchemeId(schemeId, defaultPeriod);
                FormSchemeDefine formScheme = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).getFormScheme(schemeId);
                Set FormDefineList = NrTool.getFormDefineByTableName((String)formScheme.getKey(), (String)tableName);
                if (!CollectionUtils.isEmpty((Collection)FormDefineList)) {
                    DimensionValue dimensionValue = new DimensionValue();
                    dimensionValue.setName("DATATIME");
                    dimensionValue.setValue(defaultPeriod);
                    ConcurrentHashMap<String, DimensionValue> dimensionSetMap = new ConcurrentHashMap<String, DimensionValue>();
                    dimensionSetMap.put("DATATIME", dimensionValue);
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue("DATATIME", (Object)defaultPeriod);
                    JtableContext jtableContext = new JtableContext();
                    jtableContext.setFormSchemeKey(formScheme.getKey());
                    jtableContext.setDimensionSet(dimensionSetMap);
                    GCFormTabSelectServiceImpl formTabSelectService = (GCFormTabSelectServiceImpl)SpringContextUtils.getBean(GCFormTabSelectServiceImpl.class);
                    List filteredFormDefines = FormDefineList.stream().filter(formDefine1 -> formTabSelectService.isFormCondition(jtableContext, formDefine1.getFormCondition(), dimensionValueSet)).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(filteredFormDefines)) {
                        FormDefine formDefine = (FormDefine)filteredFormDefines.get(0);
                        FormTableFields formFieldData = ((IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class)).getForm(formDefine.getKey(), queryField.getFieldName());
                        this.nodeTitle = ((FieldData)formFieldData.getFields().get(0)).getFieldTitle();
                        this.operateSource = consolidatedTaskVO.getTaskTitle() + "_" + formScheme.getTitle() + "_" + formDefine.getTitle();
                    }
                }
            }
        }
        if (null == this.nodeTitle) {
            this.nodeTitle = "";
        }
        if (null == this.operateSource) {
            this.operateSource = "";
        }
    }
}

