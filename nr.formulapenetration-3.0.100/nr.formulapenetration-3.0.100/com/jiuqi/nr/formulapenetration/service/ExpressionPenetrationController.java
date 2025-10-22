/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.math.Round
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.engine.util.ReportExpressionEvalUtil
 *  com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.util.DataValidationIntepretUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.FieldData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LinkData
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.service.impl.JtableParamServiceImpl
 *  com.jiuqi.nr.jtable.util.DataCrudUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.query.service.impl.QueryHelper
 *  io.netty.util.internal.StringUtil
 *  io.swagger.annotations.Api
 *  javax.annotation.Resource
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.formulapenetration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.math.Round;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.engine.util.ReportExpressionEvalUtil;
import com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.util.DataValidationIntepretUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formulapenetration.defines.BatchEnableParam;
import com.jiuqi.nr.formulapenetration.defines.ExpressionExpandNode;
import com.jiuqi.nr.formulapenetration.defines.ExpressionLink;
import com.jiuqi.nr.formulapenetration.defines.ExpressionNode;
import com.jiuqi.nr.formulapenetration.defines.ExpressionParams;
import com.jiuqi.nr.formulapenetration.defines.FieldObject;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.service.impl.JtableParamServiceImpl;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/query-expression"})
@Api(tags={"\u516c\u5f0f\u7a7f\u900f"})
public class ExpressionPenetrationController {
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    IRunTimeViewController runtimeView;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private JtableParamServiceImpl jtableParamService;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private PeriodEngineService periodEngineService;
    private static final Logger logger = LoggerFactory.getLogger(ExpressionPenetrationController.class);

    @RequestMapping(value={"/query-getChildNodes"}, method={RequestMethod.POST})
    public String getChildNodes(@RequestBody ExpressionParams parmas) {
        if (StringUtil.isNullOrEmpty((String)parmas.parentId)) {
            return HtmlUtils.cleanUrlXSS((String)this.getRootNodes(parmas.field, parmas.parentId, parmas.formula, parmas.masterKeys));
        }
        if (StringUtil.isNullOrEmpty((String)parmas.formula)) {
            return HtmlUtils.cleanUrlXSS((String)this.getExpressNodes(parmas.field, parmas.parentId));
        }
        return HtmlUtils.cleanUrlXSS((String)this.getFieldNodes(parmas.field, parmas.formula, parmas.parentId, parmas.masterKeys));
    }

    @RequestMapping(value={"/query-getFieldExpressions"}, method={RequestMethod.POST})
    public String getFieldExpression(@RequestBody ExpressionParams parmas) {
        return HtmlUtils.cleanUrlXSS((String)this.getExpressNodes(parmas.field, parmas.parentId));
    }

    private List<IParsedExpression> getExpressions(FieldObject field) {
        String datalink = field.getDataLink();
        String formKey = field.getFormKey();
        String formScheme = field.getFormSchemeId();
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        QueryHelper helper = new QueryHelper();
        DataLinkDefine link = helper.queryLinkDefineByKey(datalink);
        FormulaSchemeDefine formulaScheme = formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formScheme);
        List expressions = formulaRunTimeController.getParsedExpressionByDataLink(link.getUniqueCode(), formulaScheme.getKey(), null, DataEngineConsts.FormulaType.CALCULATE);
        List deployInfoList = this.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getCode()});
        List<Object> exps = new ArrayList<IParsedExpression>();
        if (deployInfoList.size() > 0) {
            exps = expressions.stream().filter(idx -> idx.getAssignNode().getQueryField().getUID().equals(((DataFieldDeployInfo)deployInfoList.get(0)).getColumnModelKey())).collect(Collectors.toList());
        }
        return exps;
    }

    @RequestMapping(value={"/query-getExpressions"}, method={RequestMethod.POST})
    public boolean getExpressions(@RequestBody ExpressionParams parmas) {
        FieldObject field = parmas.field;
        LinkData queryLink = this.jtableParamService.getLink(field.getDataLink());
        FieldData queryField = this.jtableParamService.getField(queryLink.getZbid());
        RegionData region = this.jtableParamService.getRegion(queryLink.getRegionKey());
        if (StringUtil.isNullOrEmpty((String)field.getFormKey())) {
            field.setFormKey(region.getFormKey());
        }
        field.setTitle(queryField.getFieldTitle());
        FieldObject selectField = new FieldObject();
        selectField.setCode(queryField.getFieldKey());
        selectField.setDataLink(field.getDataLink());
        selectField.setFormKey(field.getFormKey());
        selectField.setFormSchemeId(field.getFormSchemeId());
        selectField.setReportName(field.getReportName());
        List<Object> expressions = new ArrayList();
        expressions = this.getExpressions(selectField);
        return expressions.size() > 0;
    }

    @RequestMapping(value={"/query-getBatchExpressions"}, method={RequestMethod.POST})
    public boolean[][] getBatchExpressions(@RequestBody BatchEnableParam parmas) {
        int i;
        if (parmas.getDimsList().size() == 0 || parmas.getLinkKeys().size() == 0) {
            return new boolean[0][0];
        }
        boolean[][] booleans = new boolean[parmas.getDimsList().size()][parmas.getLinkKeys().size()];
        List<String> linkKeys = parmas.getLinkKeys();
        for (i = 0; i < linkKeys.size(); ++i) {
            FieldObject selectField = new FieldObject();
            LinkData queryLink = this.jtableParamService.getLink(linkKeys.get(i));
            FieldData queryField = this.jtableParamService.getField(queryLink.getZbid());
            RegionData region = this.jtableParamService.getRegion(queryLink.getRegionKey());
            selectField.setFormKey(region.getFormKey());
            selectField.setCode(queryField.getFieldKey());
            selectField.setDataLink(linkKeys.get(i));
            selectField.setFormSchemeId(parmas.getFormSchemeKey());
            List<Object> expressions = new ArrayList();
            expressions = this.getExpressions(selectField);
            booleans[0][i] = expressions.size() > 0;
        }
        for (i = 1; i < parmas.getDimsList().size(); ++i) {
            int j;
            if (parmas.getDimsList().get(i) != null) {
                for (j = 0; j < booleans[0].length; ++j) {
                    booleans[i][j] = booleans[0][j];
                }
                continue;
            }
            for (j = 0; j < booleans[0].length; ++j) {
                booleans[i][j] = false;
            }
        }
        return booleans;
    }

    private String getRootNodes(FieldObject field, String parentId, String val, String masterKeys) {
        LinkedHashMap<String, ArrayList<Object>> expressionNodes = new LinkedHashMap<String, ArrayList<Object>>();
        try {
            ArrayList<ExpressionNode> nodes = new ArrayList<ExpressionNode>();
            ArrayList<ExpressionLink> linkes = new ArrayList<ExpressionLink>();
            LinkData queryLink = this.jtableParamService.getLink(field.getDataLink());
            FieldData queryField = this.jtableParamService.getField(queryLink.getZbid());
            RegionData region = this.jtableParamService.getRegion(queryLink.getRegionKey());
            if (StringUtil.isNullOrEmpty((String)field.getFormKey())) {
                field.setFormKey(region.getFormKey());
            }
            field.setCode(queryField.getFieldKey());
            field.setTitle(queryField.getFieldTitle());
            JtableContext jtableContext = new JtableContext();
            jtableContext.setFormKey(field.getFormKey());
            DimensionValueSet mKeys = new DimensionValueSet();
            mKeys.parseString(masterKeys);
            HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
            for (int s = 0; s < mKeys.size(); ++s) {
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(mKeys.getName(s));
                dimensionValue.setValue((String)mKeys.getValue(s));
                dimensionSet.put(mKeys.getName(s), dimensionValue);
            }
            jtableContext.setDimensionSet(dimensionSet);
            QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder((JtableContext)jtableContext, (String)region.getKey(), null);
            queryInfoBuilder.select(field.getDataLink());
            queryInfoBuilder.groupBy(field.getDataLink());
            queryInfoBuilder.setDesensitized(true);
            DataValueFormatter formatter = DataCrudUtil.getDataValueFormatter((JtableContext)jtableContext);
            IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
            Object formatData = DataCrudUtil.getFormatData((IDataValue)((IDataValue)((IRowData)iRegionDataSet.getRowData().get(0)).getLinkDataValues().get(0)), (DataValueFormatter)formatter);
            val = (String)formatData;
            String title = field.getTitle();
            DataLinkDefine linkDefine = null;
            if (!field.getCustom()) {
                List<IParsedExpression> expressions = this.getExpressions(field);
                if (expressions == null) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.writeValueAsString(expressionNodes);
                }
                linkDefine = this.runtimeView.queryDataLinkDefine(field.getDataLink());
            } else {
                title = field.getCustomValue();
            }
            this.createNode(field, field.getCode(), title, val, linkDefine, nodes, linkes, parentId, field.getCustom(), title);
            expressionNodes.put("nodes", nodes);
            expressionNodes.put("links", linkes);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(expressionNodes);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    private String getExpressNodes(FieldObject field, String parentId) {
        LinkedHashMap<String, ArrayList<Object>> expressionNodes = new LinkedHashMap<String, ArrayList<Object>>();
        try {
            ArrayList<ExpressionNode> nodes = new ArrayList<ExpressionNode>();
            ArrayList<ExpressionLink> linkes = new ArrayList<ExpressionLink>();
            List<IParsedExpression> expressions = this.getExpressions(field);
            if (expressions == null) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(expressionNodes);
            }
            for (IParsedExpression exp : expressions) {
                Formula source = exp.getSource();
                DataLinkDefine linkDefine = null;
                if (!StringUtil.isNullOrEmpty((String)field.getDataLink())) {
                    linkDefine = this.runtimeView.queryDataLinkDefine(field.getDataLink());
                }
                String title = "[" + source.getCode() + "]\r\n" + exp.toString() + "\r\n" + source.getMeanning();
                this.createNode(field, source.getFormula(), title, null, linkDefine, nodes, linkes, parentId, true, exp.toString());
            }
            expressionNodes.put("nodes", nodes);
            expressionNodes.put("links", linkes);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(expressionNodes);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    @RequestMapping(value={"/query-getExpressionField"}, method={RequestMethod.POST})
    public String getExpressionField(@RequestBody ExpressionParams parmas) {
        return HtmlUtils.cleanUrlXSS((String)this.getFieldNodes(parmas.field, parmas.formula, parmas.parentId, parmas.masterKeys));
    }

    private String getFieldNodes(FieldObject qfield, String formulaStr, String parentId, String masterKeys) {
        if (StringUtil.isNullOrEmpty((String)formulaStr)) {
            return null;
        }
        String formulaRight = formulaStr.substring(formulaStr.indexOf("=") + 1);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, qfield.getFormSchemeId());
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        ReportFormulaParseUtil reportParse = (ReportFormulaParseUtil)BeanUtil.getBean(ReportFormulaParseUtil.class);
        ReportExpressionEvalUtil report = (ReportExpressionEvalUtil)BeanUtil.getBean(ReportExpressionEvalUtil.class);
        ArrayList<ExpressionNode> nodes = new ArrayList<ExpressionNode>();
        ArrayList<ExpressionLink> linkes = new ArrayList<ExpressionLink>();
        DataLinkDefine linkDefine = null;
        if (!StringUtil.isNullOrEmpty((String)qfield.getDataLink())) {
            linkDefine = this.runtimeView.queryDataLinkDefine(qfield.getDataLink());
        }
        try {
            DimensionValueSet mKeys = new DimensionValueSet();
            mKeys.parseString(masterKeys);
            if (!StringUtil.isNullOrEmpty((String)qfield.getFormKey())) {
                FormDefine formDefine = this.runtimeView.queryFormById(qfield.getFormKey());
                if (StringUtils.isEmpty((CharSequence)qfield.getReportName())) {
                    executorContext.setDefaultGroupName(formDefine.getFormCode());
                } else {
                    executorContext.setDefaultGroupName(qfield.getReportName());
                }
            }
            List fieldNodes = reportParse.parseFormulaNodes(executorContext, formulaRight, mKeys);
            for (IASTNode fnode : fieldNodes) {
                String title;
                AbstractData val = report.eval(executorContext, fnode, mKeys);
                if (fnode instanceof FunctionNode) {
                    FunctionNode funcNode = (FunctionNode)fnode;
                    String formula = funcNode.interpret((IContext)executorContext, Language.FORMULA, null);
                    this.createNode(qfield, formula, formula, this.getValue(val, null), null, nodes, linkes, parentId, true, formula);
                    continue;
                }
                if (fnode instanceof CellDataNode) {
                    CellDataNode cnode = (CellDataNode)fnode;
                    DataModelLinkColumn link = cnode.getDataModelLinkColumn();
                    if (link != null) {
                        linkDefine = this.runtimeView.queryDataLinkDefineByUniquecode(link.getReportInfo().getReportKey(), link.getDataLinkCode());
                    }
                    DataFieldDeployInfo deployInfo = this.iRuntimeDataSchemeService.getDeployInfoByColumnKey(link.getColumModel().getID());
                    DataField field2 = this.iRuntimeDataSchemeService.getDataField(deployInfo.getDataFieldKey());
                    title = "[" + field2.getCode() + "]  " + link.toString() + " /r/n " + field2.getTitle();
                    String reportName = link.getReportInfo().getReportName();
                    qfield.setReportName(reportName);
                    this.createNode(qfield, field2.getKey(), title, this.getValue(val, field2), linkDefine, nodes, linkes, parentId, false, title);
                    continue;
                }
                if (!(fnode instanceof DynamicDataNode)) continue;
                DynamicDataNode dnode = (DynamicDataNode)fnode;
                QueryField nodefield = dnode.getQueryField();
                DataField field = this.iRuntimeDataSchemeService.getDataField(nodefield.getUID());
                DataLinkColumn link = dnode.getDataLink();
                if (link != null) {
                    linkDefine = this.runtimeView.queryDataLinkDefineByUniquecode(link.getReportInfo().getReportKey(), link.getDataLinkCode());
                }
                title = dnode.toString() + " /r/n " + field.getTitle();
                this.createNode(qfield, field.getKey(), title, this.getValue(val, field), linkDefine, nodes, linkes, parentId, false, title);
            }
            LinkedHashMap<String, ArrayList<Object>> expressionNodes = new LinkedHashMap<String, ArrayList<Object>>();
            expressionNodes.put("nodes", nodes);
            expressionNodes.put("links", linkes);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(expressionNodes);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    private String getValue(AbstractData val, DataField field) {
        String value = val.getAsString();
        if (val.isNull) {
            if (field == null) {
                return "0.00";
            }
            if (field.getDefaultValue() == null) {
                String digitsValue = "";
                for (int i = 0; i < field.getFractionDigits(); ++i) {
                    digitsValue = i == 0 ? digitsValue + ".0" : digitsValue + "0";
                }
                value = "0" + digitsValue;
            } else {
                value = field.getDefaultValue();
            }
        }
        return value;
    }

    private void createNode(FieldObject field, String fieldKey, String title, String val, DataLinkDefine link, List<ExpressionNode> nodes, List<ExpressionLink> linkes, String parentId, boolean isExpression, String dataVal) throws JsonProcessingException {
        ExpressionNode node;
        LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
        FieldObject selectField = new FieldObject();
        selectField.setCode(fieldKey);
        selectField.setTitle(title);
        if (link != null) {
            selectField.setDataLink(link.getKey());
        }
        selectField.setFormKey(field.getFormKey());
        selectField.setFormSchemeId(field.getFormSchemeId());
        selectField.setReportName(field.getReportName());
        data.put("Value", dataVal);
        data.put("Field", selectField);
        ObjectMapper mapper = new ObjectMapper();
        List<IParsedExpression> expressions = null;
        if (!isExpression && link != null) {
            expressions = this.getExpressions(selectField);
        }
        boolean hasExpression = expressions != null && expressions.size() > 0;
        String id = UUID.randomUUID().toString();
        if (val != null) {
            try {
                FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                val = this.getThousandthValue(fieldDefine, val);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (hasExpression || isExpression) {
            node = new ExpressionExpandNode();
            node.setId(id);
            if (!isExpression) {
                node.setText((StringUtil.isNullOrEmpty((String)val) ? "0.00" : val) + " /r/n " + title);
            } else {
                node.setText(" /r/n " + title + " /r/n ");
            }
            ((ExpressionExpandNode)node).setExpanded(false);
            ((ExpressionExpandNode)node).setExpandHolderPosition(1);
            if (isExpression) {
                try {
                    String[] splits = dataVal.split("=");
                    if (splits.length == 2 && splits[1].trim().equals(splits[0].trim())) {
                        ((ExpressionExpandNode)node).setExpandHolderPosition(0);
                    }
                }
                catch (Exception e) {
                    logger.error("\u516c\u5f0f\u89e3\u6790\u5f02\u5e38!");
                    logger.error(e.getMessage(), e);
                }
            }
            data.put("isExpression", isExpression);
            node.setData(mapper.writeValueAsString(data));
            nodes.add(node);
        } else {
            node = new ExpressionNode();
            node.setId(id);
            node.setText(val + " /r/n " + title);
            data.put("isExpression", isExpression);
            node.setData(mapper.writeValueAsString(data));
            nodes.add(node);
        }
        if (!StringUtil.isNullOrEmpty((String)parentId)) {
            ExpressionLink nodelink = new ExpressionLink();
            nodelink.setFrom(parentId);
            nodelink.setTo(id);
            linkes.add(nodelink);
        }
    }

    private IParsedExpression parseExpressions(ExecutorContext executorContext, String formulaStr) throws ParseException {
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        Formula formula = new Formula();
        formula.setId(formulaStr);
        formula.setFormula(formulaStr);
        formula.setMeanning(DataValidationIntepretUtil.intepret((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController, (String)formulaStr));
        formulas.add(formula);
        List parsedExpressions = DataEngineFormulaParser.parseFormula((ExecutorContext)executorContext, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE);
        if (parsedExpressions != null && parsedExpressions.size() > 0) {
            return (IParsedExpression)parsedExpressions.get(0);
        }
        return null;
    }

    @RequestMapping(value={"/query-getMaster"}, method={RequestMethod.POST})
    public String getMasterTitle(@RequestBody Map<String, Object> param) {
        String title = "";
        try {
            String taskId = param.get("taskId").toString();
            String dims = param.get("dims").toString();
            DimensionValueSet dim = new DimensionValueSet();
            dim.parseString(dims);
            DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(dim, param.get("formSchemeKey").toString());
            DimensionCombination dimensionCombination = (DimensionCombination)dimensionCollection.getDimensionCombinations().get(0);
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskId);
            boolean isCustom = taskDefine.getPeriodType() == PeriodType.CUSTOM;
            String[] masters = taskDefine.getMasterEntitiesKey().split(";");
            StringBuilder builder = new StringBuilder();
            for (String master : masters) {
                boolean periodView = this.periodEntityAdapter.isPeriodEntity(master);
                EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(master);
                if (entityViewDefine == null) continue;
                String dimName = QueryHelper.getDimName((EntityViewDefine)entityViewDefine);
                if (periodView) {
                    IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityViewDefine.getEntityId());
                    if (isCustom) {
                        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(entityViewDefine.getEntityId());
                        String periodTitle = periodProvider.getPeriodTitle(dim.getValue(dimName).toString());
                        builder.append(" " + periodEntity.getTitle() + "\uff1a" + periodTitle + "\uff1b  ");
                        continue;
                    }
                    String periodTitle = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityViewDefine.getEntityId()).getPeriodTitle(dim.getValue(dimName).toString());
                    builder.append("  \u65f6\u671f\uff1a" + periodTitle);
                    continue;
                }
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityViewDefine.getEntityId());
                IEntityTable entityTable = QueryHelper.getEntityTable((EntityViewDefine)entityViewDefine);
                IEntityRow row = entityTable.findByEntityKey(dimensionCombination.getValue(dimName).toString());
                builder.append("  " + entityDefine.getTitle() + "\uff1a" + row.getTitle() + "\uff1b   ");
            }
            title = builder.toString();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return title;
    }

    public String getThousandthValue(FieldDefine curent, String valStr) {
        if (valStr.contains(",")) {
            return valStr;
        }
        int fractionDigits = 2;
        FieldType type = FieldType.FIELD_TYPE_DECIMAL;
        if (curent != null) {
            fractionDigits = curent.getFractionDigits();
            type = curent.getType();
        }
        if (valStr == "\u2014\u2014" || StringUtil.isNullOrEmpty((String)valStr)) {
            return valStr;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0" + this.getDigits(fractionDigits));
        try {
            switch (type) {
                case FIELD_TYPE_INTEGER: {
                    if (valStr != null && valStr.length() > 9) {
                        BigDecimal decimalValue = Convert.toBigDecimal((Object)valStr);
                        return decimalFormat.format(decimalValue);
                    }
                    if (valStr.contains(".")) {
                        return valStr;
                    }
                    Integer iVal = Convert.toInt((String)valStr);
                    return decimalFormat.format(iVal);
                }
                case FIELD_TYPE_DECIMAL: {
                    BigDecimal decimalValue = Convert.toBigDecimal((Object)valStr);
                    BigDecimal deciValue = decimalValue.setScale(fractionDigits, 4);
                    return decimalFormat.format(deciValue);
                }
                case FIELD_TYPE_FLOAT: {
                    double floatValue = Convert.toDouble((String)valStr);
                    Double dbVal = Round.callFunction((Number)floatValue, (int)fractionDigits);
                    return decimalFormat.format(dbVal);
                }
            }
            return valStr;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return valStr;
        }
    }

    private String getDigits(int size) {
        String digitStr = "";
        for (int i = 0; i < size; ++i) {
            digitStr = digitStr + "0";
        }
        if (!StringUtil.isNullOrEmpty((String)digitStr)) {
            return "." + digitStr;
        }
        return digitStr;
    }
}

