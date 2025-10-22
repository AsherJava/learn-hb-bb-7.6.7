/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTAdjustor
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaDataLinkPosition
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.syntax.ast.IASTAdjustor;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaDataLinkPosition;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.output.FormulaDataLinkNodeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel(value="FormulaData", description="\u516c\u5f0f\u4fe1\u606f")
public class FormulaData
implements Comparable,
Serializable {
    private static final long serialVersionUID = -6656325869130581826L;
    private static final Logger logger = LoggerFactory.getLogger(FormulaData.class);
    @ApiModelProperty(value="\u516c\u5f0fkey", name="key")
    private String key;
    @ApiModelProperty(value="\u516c\u5f0f\u89e3\u6790\u524did", name="id")
    private String id;
    @ApiModelProperty(value="\u516c\u5f0fcode", name="code")
    private String code;
    @ApiModelProperty(value="\u516c\u5f0f\u8868\u8fbe\u5f0f", name="formula")
    private String formula;
    @ApiModelProperty(value="\u516c\u5f0f\u6240\u5728\u62a5\u8868key", name="formKey")
    private String formKey;
    @ApiModelProperty(value="\u516c\u5f0f\u6240\u5728\u516c\u5f0f\u65b9\u6848key", name="formKey")
    private String formulaSchemeKey;
    @ApiModelProperty(value="\u516c\u5f0f\u6240\u5728\u62a5\u8868\u6807\u9898", name="formTitle")
    private String formTitle;
    @ApiModelProperty(value="\u516c\u5f0f\u542b\u4e49", name="meanning")
    private String meanning;
    @ApiModelProperty(value="\u516c\u5f0f\u901a\u914d\u884c", name="globRow")
    private int globRow;
    @ApiModelProperty(value="\u516c\u5f0f\u901a\u914d\u5217", name="globCol")
    private int globCol;
    @ApiModelProperty(value="\u516c\u5f0f\u7c7b\u578b", name="type")
    private String type;
    @ApiModelProperty(value="\u516c\u5f0f\u5ba1\u6838\u7c7b\u578b", name="checktype")
    private int checktype;
    @ApiModelProperty(value="\u516c\u5f0f\u8d4b\u503c\u94fe\u63a5key", name="assignDataLinkKey")
    private String assignDataLinkKey;
    @ApiModelProperty(value="\u516c\u5f0f\u6392\u5e8f\u5b57\u6bb5", name="order")
    private String order;
    @ApiModelProperty(value="\u516c\u5f0f\u94fe\u63a5\u8282\u70b9\u5217\u8868", name="formula")
    private List<FormulaDataLinkNodeInfo> dataLinkNodes = new ArrayList<FormulaDataLinkNodeInfo>();

    public FormulaData() {
    }

    public FormulaData(Formula formulaObj) {
        this.key = formulaObj.getId();
        this.id = formulaObj.getId();
        this.code = formulaObj.getCode();
        this.formula = formulaObj.getFormula();
        this.formKey = formulaObj.getFormKey();
        this.meanning = formulaObj.getMeanning();
        this.checktype = formulaObj.getChecktype();
        IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
        FormulaDefine formulaDefine = formulaRunTimeController.queryFormulaDefine(this.id);
        if (formulaDefine != null) {
            this.formulaSchemeKey = formulaDefine.getFormulaSchemeKey();
            if (formulaDefine.getUseCalculate()) {
                this.type = this.type == null ? "" + DataEngineConsts.FormulaType.CALCULATE.getValue() : this.type + ";" + DataEngineConsts.FormulaType.CALCULATE.getValue();
            }
            if (formulaDefine.getUseCheck()) {
                this.type = this.type == null ? "" + DataEngineConsts.FormulaType.CHECK.getValue() : this.type + ";" + DataEngineConsts.FormulaType.CHECK.getValue();
            }
            if (formulaDefine.getUseBalance()) {
                this.type = this.type == null ? "" + DataEngineConsts.FormulaType.BALANCE.getValue() : this.type + ";" + DataEngineConsts.FormulaType.BALANCE.getValue();
            }
            this.order = formulaDefine.getOrder();
        }
    }

    public FormulaData(FormulaDefine formulaDefine) {
        this.key = formulaDefine.getKey();
        this.id = formulaDefine.getKey();
        this.code = formulaDefine.getCode();
        this.formula = formulaDefine.getExpression();
        this.formKey = formulaDefine.getFormKey();
        this.meanning = formulaDefine.getDescription();
        this.checktype = formulaDefine.getCheckType();
        this.formulaSchemeKey = formulaDefine.getFormulaSchemeKey();
        if (formulaDefine.getUseCalculate()) {
            this.type = this.type == null ? "" + DataEngineConsts.FormulaType.CALCULATE.getValue() : this.type + ";" + DataEngineConsts.FormulaType.CALCULATE.getValue();
        }
        if (formulaDefine.getUseCheck()) {
            this.type = this.type == null ? "" + DataEngineConsts.FormulaType.CHECK.getValue() : this.type + ";" + DataEngineConsts.FormulaType.CHECK.getValue();
        }
        if (formulaDefine.getUseBalance()) {
            this.type = this.type == null ? "" + DataEngineConsts.FormulaType.BALANCE.getValue() : this.type + ";" + DataEngineConsts.FormulaType.BALANCE.getValue();
        }
        this.order = formulaDefine.getOrder();
    }

    public FormulaData(IParsedExpression farsedExpression, IRunTimeViewController controller, boolean isAnalysis, int showType, String formSchemeKey, List<IASTAdjustor> list, IDataDefinitionRuntimeController dataDefinitionRuntimeController, ReportFmlExecEnvironment environment) {
        this(farsedExpression.getSource());
        DataLinkDefine assignDataLink;
        DataLinkColumn dataLink;
        DynamicDataNode assignNode;
        farsedExpression.getRealExpression().getWildcardCol();
        this.key = farsedExpression.getKey();
        if (StringUtils.isNotEmpty((String)this.formKey) && (assignNode = farsedExpression.getAssignNode()) != null && (dataLink = assignNode.getDataLink()) != null && (assignDataLink = controller.queryDataLinkDefineByUniquecode(this.formKey, dataLink.getDataLinkCode())) != null) {
            this.assignDataLinkKey = assignDataLink.getKey().toString();
        }
        if (isAnalysis) {
            try {
                FormDefine form;
                this.globRow = farsedExpression.getRealExpression().getWildcardRow();
                this.globCol = farsedExpression.getRealExpression().getWildcardCol();
                ExecutorContext executorContext = new ExecutorContext(dataDefinitionRuntimeController);
                executorContext.setEnv((IFmlExecEnvironment)environment);
                if (StringUtils.isNotEmpty((String)this.formKey) && (form = controller.queryFormById(this.formKey)) != null) {
                    executorContext.setDefaultGroupName(form.getFormCode());
                }
                QueryContext queryContext = null;
                try {
                    queryContext = new QueryContext(executorContext, null);
                }
                catch (ParseException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                DataEngineConsts.FormulaShowType formulaShowType = DataEngineConsts.FormulaShowType.JQ;
                if ((long)showType > 0L) {
                    formulaShowType = formulaShowType.getFormulaShowType(showType);
                }
                FormulaShowInfo formulaShowInfo = new FormulaShowInfo(formulaShowType);
                if (null != list && list.size() > 0) {
                    formulaShowInfo.setAdjustors(list);
                }
                formulaShowInfo.setCollectPositions(true);
                formulaShowInfo.getDataLinkPositions().clear();
                this.formula = farsedExpression.getFormula(queryContext, formulaShowInfo);
                List dataLinkPositions = formulaShowInfo.getDataLinkPositions();
                for (FormulaDataLinkPosition position : dataLinkPositions) {
                    FormulaDataLinkNodeInfo node = new FormulaDataLinkNodeInfo();
                    node.setBeginIndex(position.getBeginIndex());
                    node.setEndIndex(position.getEndIndex());
                    DataLinkDefine dataLink2 = controller.queryDataLinkDefineByUniquecode(position.getReportKey(), position.getDataLinkCode());
                    if (dataLink2 != null) {
                        node.setDataLinkKey(dataLink2.getKey());
                        node.setRegionKey(dataLink2.getRegionKey());
                        node.setFormKey(position.getReportKey());
                    }
                    this.dataLinkNodes.add(node);
                }
            }
            catch (InterpretException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getMeanning() {
        return this.meanning;
    }

    public void setMeanning(String meanning) {
        this.meanning = meanning;
    }

    public int getGlobRow() {
        return this.globRow;
    }

    public void setGlobRow(int globRow) {
        this.globRow = globRow;
    }

    public int getGlobCol() {
        return this.globCol;
    }

    public void setGlobCol(int globCol) {
        this.globCol = globCol;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChecktype() {
        return this.checktype;
    }

    public void setChecktype(int checktype) {
        this.checktype = checktype;
    }

    public String getAssignDataLinkKey() {
        return this.assignDataLinkKey;
    }

    public void setAssignDataLinkKey(String assignDataLinkKey) {
        this.assignDataLinkKey = assignDataLinkKey;
    }

    public List<FormulaDataLinkNodeInfo> getDataLinkNodes() {
        return this.dataLinkNodes;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setDataLinkNodes(List<FormulaDataLinkNodeInfo> dataLinkNodes) {
        this.dataLinkNodes = dataLinkNodes;
    }

    public int compareTo(Object arg0) {
        if (arg0 instanceof FormulaData) {
            return this.order.compareTo(((FormulaData)arg0).getOrder());
        }
        return 0;
    }
}

