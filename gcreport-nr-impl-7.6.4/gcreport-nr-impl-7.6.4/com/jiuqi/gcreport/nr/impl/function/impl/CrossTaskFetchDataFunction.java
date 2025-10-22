/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.gcreport.nr.impl.function.impl;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import com.jiuqi.gcreport.nr.impl.function.impl.util.CrossTaskFunctionUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.text.DecimalFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CrossTaskFetchDataFunction
extends NrFunction {
    private static final transient Logger logger = LoggerFactory.getLogger(CrossTaskFetchDataFunction.class);
    private static final DecimalFormat df = new DecimalFormat("00");
    public final String FUNCTION_NAME = "FetchData";
    private static final long serialVersionUID = 1L;

    public CrossTaskFetchDataFunction() {
        this.parameters().add(new Parameter("zb", 0, "\u53d6\u6570\u6307\u6807"));
        this.parameters().add(new Parameter("periodOffset", 3, "\u65f6\u671f\u504f\u79fb\u91cf", true));
        this.parameters().add(new Parameter("period", 3, "\u6307\u5b9a\u65f6\u671f", true));
        this.parameters().add(new Parameter("orgCode", 6, "\u6307\u5b9a\u5355\u4f4d", true));
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        if (parameters.size() < 3) {
            return super.validate(context, parameters);
        }
        CrossTaskFunctionUtil.valiatePeriodParamter((QueryContext)context, parameters.get(0), parameters.get(2));
        return super.validate(context, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) {
        QueryContext queryContext = (QueryContext)context;
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            AbstractData abstractData;
            int period;
            CellDataNode sourceZb = (CellDataNode)parameters.get(0);
            String linkTaskAlias = ((DynamicDataNode)sourceZb.getChild(0)).getRelateTaskItem();
            FormSchemeDefine destSchemeDefine = CrossTaskFunctionUtil.getDestFormSchemeDefine(env, linkTaskAlias);
            String destZbCode = this.getDestSchemeZbCode(sourceZb, destSchemeDefine);
            if (StringUtils.isEmpty((String)destZbCode)) {
                throw new BusinessRuntimeException("\u5173\u8054\u4efb\u52a1\u672a\u627e\u5230\u5bf9\u5e94\u6307\u6807\uff1a" + sourceZb.getDataModelLinkColumn().toString());
            }
            int periodOffset = parameters.size() > 1 && parameters.get(1) != null ? Convert.toInt((Object)parameters.get(1).evaluate((IContext)queryContext)) : 0;
            String orgCode = parameters.size() >= 4 ? (String)parameters.get(3).evaluate(context) : null;
            DimensionValueSet destDs = this.getDimensionValueSet(queryContext, destSchemeDefine, env.getFormSchemeKey(), periodOffset, orgCode);
            if (null == destDs) {
                return null;
            }
            if (parameters.size() >= 3 && (period = Convert.toInt((Object)parameters.get(2).evaluate(null))) > 0) {
                String periodStr = ((String)destDs.getValue("DATATIME")).substring(0, 7).concat(df.format(period));
                destDs.setValue("DATATIME", (Object)periodStr);
            }
            return null == (abstractData = NrTool.getZbValue((DimensionValueSet)destDs, (String)destZbCode)) ? null : abstractData.getAsObject();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private String getDestSchemeZbCode(CellDataNode sourceZbNode, FormSchemeDefine destSchemeDefine) {
        DataModelLinkColumn dataLinkColumn = sourceZbNode.getDataModelLinkColumn();
        if (dataLinkColumn == null) {
            return null;
        }
        String reportName = dataLinkColumn.getReportInfo().getReportName();
        int row = dataLinkColumn.getDataPosition().row();
        int col = dataLinkColumn.getDataPosition().col();
        try {
            FormDefine formDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).queryFormByCodeInScheme(destSchemeDefine.getKey(), reportName);
            DataLinkDefine dataLinkDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).queryDataLinkDefineByColRow(formDefine.getKey(), col, row);
            if (dataLinkDefine == null) {
                return null;
            }
            IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
            DataField fieldDefine = runtimeDataSchemeService.getDataField(dataLinkDefine.getLinkExpression());
            if (fieldDefine == null) {
                return null;
            }
            String zbCode = runtimeDataSchemeService.getDataTable(fieldDefine.getDataTableKey()).getCode() + "[" + fieldDefine.getCode() + "]";
            return zbCode;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private DimensionValueSet getDimensionValueSet(QueryContext queryContext, FormSchemeDefine destSchemeDefine, String sourceFormSchemeKey, int periodOffset, String orgCode) {
        DimensionValueSet destDs = new DimensionValueSet(queryContext.getCurrentMasterKey());
        if (!StringUtils.isEmpty((String)orgCode)) {
            destDs.setValue("MD_ORG", (Object)orgCode);
        }
        PeriodType destPeriodType = destSchemeDefine.getPeriodType();
        FormSchemeDefine sourceSchemeDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).getFormScheme(sourceFormSchemeKey);
        PeriodType sourcePeriodType = sourceSchemeDefine.getPeriodType();
        String sourcePeriodStr = (String)destDs.getValue("DATATIME");
        if (periodOffset != 0) {
            sourcePeriodStr = CrossTaskFunctionUtil.getSrcPeriod(sourcePeriodStr, periodOffset, String.valueOf(sourcePeriodStr.charAt(4))).toString();
        }
        String destPeriodStr = CrossTaskFunctionUtil.convertPeriod(sourcePeriodStr, sourcePeriodType, destPeriodType);
        destDs.setValue("DATATIME", (Object)destPeriodStr);
        String orgType = CrossTaskFunctionUtil.getOrgTypeBySchemeId(destSchemeDefine);
        YearPeriodObject yp = new YearPeriodObject(null, (String)destDs.getValue("DATATIME"));
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO mergeOrg = orgTool.getOrgByCode((String)destDs.getValue("MD_ORG"));
        if (mergeOrg == null) {
            logger.error("\u672a\u627e\u5230\u5bf9\u5e94\u5355\u4f4d\uff1a{}", destDs.getValue("MD_ORG"));
            return null;
        }
        destDs.setValue("MD_GCORGTYPE", (Object)mergeOrg.getOrgTypeId());
        return destDs;
    }

    public String name() {
        return "FetchData";
    }

    public String title() {
        return "\u8de8\u4efb\u52a1\u53d6\u56fa\u5b9a\u6307\u6807\u6570\u636e\u51fd\u6570";
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append("\uff1b").append("\u6307\u6807\u503c").append(StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u8de8\u4efb\u52a1\u53d6\u56fa\u5b9a\u6307\u6807\u6570\u636e ").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("FetchData(BA001[2,1]@NJ)").append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

