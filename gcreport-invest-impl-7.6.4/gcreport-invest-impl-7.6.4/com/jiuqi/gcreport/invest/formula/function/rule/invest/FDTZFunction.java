/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FDTZFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "FDTZ";

    public FDTZFunction() {
        this.parameters().add(new Parameter("zbcode", 6, "\u5b57\u6bb5\u4ee3\u7801"));
        this.parameters().add(new Parameter("oneSubSumScale", 1, "true\u4e3a1-(\u7d2f\u8ba1\u6bd4\u4f8b/100)\uff0cfalse\u4e3a\u7d2f\u8ba1\u6bd4\u4f8b\uff0c\u9ed8\u8ba4false", true));
        this.parameters().add(new Parameter("hasHistoryYear", 1, "true\u4e3a\u5305\u542b\u5386\u53f2\u5e74\u4efd\u6570\u636e\uff0cfalse\u4e3a\u4e0d\u5305\u542b\uff0c\u9ed8\u8ba4false", true));
    }

    public String name() {
        return "FDTZ";
    }

    public String title() {
        return "\u5206\u6bb5\u6295\u8d44\u51fd\u6570(\u65e7\u51fd\u6570)";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity masterData = executorContext.getData();
        List items = executorContext.getItems();
        List<DefaultTableEntity> itemEOs = this.getInvestmentItems(masterData, items, parameters);
        Double result = 0.0;
        this.sortItems(itemEOs);
        DimensionValueSet ds = this.getDimensionSet(queryContext);
        try {
            String periodString = ConverterUtils.getAsString((Object)ds.getValue("DATATIME"));
            YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)periodString);
            PeriodType type = PeriodType.fromType((int)periodUtil.getType());
            boolean calcDiff = true;
            String zbCode = calcDiff ? ((String)parameters.get(0).evaluate(null)).toUpperCase() : null;
            IDataQuery dataQuery = null;
            FieldDefine define = null;
            if (calcDiff) {
                define = this.initDataQuery(zbCode);
                IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
                dataQuery = dataAccessProvider.newDataQuery();
                dataQuery.addColumn(define);
            }
            boolean oneSubSumScale = false;
            if (parameters.size() > 1) {
                oneSubSumScale = (Boolean)parameters.get(1).evaluate(null);
            }
            Double zbValue = 0.0;
            Double currTotalEquityRatio = (Double)masterData.getFieldValue("BEGINEQUITYRATIO") / 100.0;
            StringBuffer log = new StringBuffer();
            Calendar calendar = Calendar.getInstance();
            for (DefaultTableEntity gcInvestmentItemEO : itemEOs) {
                calendar.setTime((Date)gcInvestmentItemEO.getFieldValue("CHANGEDATE"));
                periodUtil = YearPeriodUtil.transform(null, (int)type.type(), (Calendar)calendar);
                ds.setValue("DATATIME", (Object)periodUtil.toString());
                if (calcDiff) {
                    Double currTermZbValue = this.evalute(dataQuery, ds, define);
                    result = result + (currTermZbValue - zbValue) * (oneSubSumScale ? 1.0 - currTotalEquityRatio : currTotalEquityRatio);
                    log.append("(").append(currTermZbValue).append("-").append(zbValue).append(")*").append(oneSubSumScale ? 1.0 - currTotalEquityRatio : currTotalEquityRatio).append("+");
                    zbValue = currTermZbValue;
                } else {
                    Object changeAMTObj = gcInvestmentItemEO.getFieldValue("CHANGEAMT");
                    double changeAMT = changeAMTObj == null ? 0.0 : (Double)changeAMTObj;
                    result = result + changeAMT * (oneSubSumScale ? 1.0 - currTotalEquityRatio : currTotalEquityRatio);
                    log.append(changeAMT).append("*").append(oneSubSumScale ? 1.0 - currTotalEquityRatio : currTotalEquityRatio).append("+");
                }
                Object changeRatioObj = gcInvestmentItemEO.getFieldValue("CHANGERATIO");
                double changeRatio = changeRatioObj == null ? 0.0 : (Double)changeRatioObj / 100.0;
                currTotalEquityRatio = currTotalEquityRatio + changeRatio;
            }
            if (calcDiff) {
                ds.setValue("DATATIME", (Object)periodString);
                Double currTermZbValue = this.evalute(dataQuery, ds, define);
                result = result + (currTermZbValue - zbValue) * (oneSubSumScale ? 1.0 - currTotalEquityRatio : currTotalEquityRatio);
                log.append("(").append(currTermZbValue).append("-").append(zbValue).append(")*").append(oneSubSumScale ? 1.0 - currTotalEquityRatio : currTotalEquityRatio).append("+");
            }
            log.setLength(log.length() - 1);
            log.append("=").append(result);
            Logger logger = LoggerFactory.getLogger(FDTZFunction.class);
            logger.info("fdtz:" + log.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private List sortItems(List<DefaultTableEntity> items) {
        if (CollectionUtils.isEmpty(items)) {
            return new ArrayList();
        }
        items.sort((o1, o2) -> {
            Object o2ChangeDate = o2.getFieldValue("CHANGEDATE");
            if (o2ChangeDate == null) {
                return -1;
            }
            Object o1ChangeDate = o1.getFieldValue("CHANGEDATE");
            if (o1ChangeDate == null) {
                return 1;
            }
            return ((Date)o1ChangeDate).compareTo((Date)o2ChangeDate);
        });
        return items;
    }

    private DimensionValueSet getDimensionSet(QueryContext queryContext) {
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity masterData = executorContext.getData();
        DimensionValueSet ds = new DimensionValueSet(queryContext.getCurrentMasterKey());
        String unitId = this.getUnit(masterData, ds);
        ds.setValue("MD_ORG", (Object)unitId);
        return ds;
    }

    private String getUnit(DefaultTableEntity data, DimensionValueSet ds) {
        String unitId;
        String periodString;
        YearPeriodObject yp;
        String orgTypeId = String.valueOf(ds.getValue("MD_GCORGTYPE"));
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgTypeId, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)(yp = new YearPeriodObject(null, periodString = (String)ds.getValue("DATATIME"))));
        GcOrgCacheVO hbOrg = orgCenterService.getUnionUnitByGrade(unitId = StringUtils.toViewString((Object)data.getFieldValue("INVESTEDUNIT")));
        if (null != hbOrg) {
            unitId = hbOrg.getId();
        }
        return unitId;
    }

    private List<DefaultTableEntity> getInvestmentItems(DefaultTableEntity masterData, List<DefaultTableEntity> items, List<IASTNode> parameters) throws SyntaxException {
        if (null == masterData) {
            return new ArrayList<DefaultTableEntity>(0);
        }
        boolean hasHistoryYear = false;
        if (parameters.size() > 2) {
            hasHistoryYear = (Boolean)parameters.get(2).evaluate(null);
        }
        return hasHistoryYear ? items : this.getCurrYearItems(masterData, items);
    }

    private List<DefaultTableEntity> getCurrYearItems(DefaultTableEntity masterData, List<DefaultTableEntity> items) {
        String masterYearStr = StringUtils.toViewString((Object)masterData.getFieldValue("ACCTYEAR"));
        if (StringUtils.isEmpty((String)masterYearStr)) {
            return new ArrayList<DefaultTableEntity>();
        }
        Integer masterYear = ConverterUtils.getAsInteger((Object)masterYearStr);
        Calendar calendar = Calendar.getInstance();
        ArrayList<DefaultTableEntity> currYearItems = new ArrayList<DefaultTableEntity>(16);
        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(item -> {
                if (null == item.getFieldValue("CHANGEDATE")) {
                    return;
                }
                calendar.setTime((Date)item.getFieldValue("CHANGEDATE"));
                int itemYear = calendar.get(1);
                if (masterYear == itemYear) {
                    currYearItems.add((DefaultTableEntity)item);
                }
            });
        }
        return currYearItems;
    }

    private FieldDefine initDataQuery(String tableZbCode) throws Exception {
        IDataDefinitionRuntimeController runtimeCtrl = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        String regex = "(.+)\\[(.+)\\]";
        if (!tableZbCode.matches(regex)) {
            throw new BusinessRuntimeException("\u6307\u6807\u683c\u5f0f\u4e0d\u6b63\u786e\uff1a" + tableZbCode + "\u3002\u53c2\u8003\u683c\u5f0f\uff1a\u8868\u540d[\u6307\u6807\u4ee3\u7801]");
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tableZbCode);
        String tableName = "";
        String zbCode = "";
        if (matcher.find()) {
            tableName = matcher.group(1).trim();
            zbCode = matcher.group(2).trim();
        }
        TableDefine tabldefine = runtimeCtrl.queryTableDefineByCode(tableName);
        return runtimeCtrl.queryFieldByCodeInTable(zbCode, tabldefine.getKey());
    }

    private Double evalute(IDataQuery dataQuery, DimensionValueSet ds, FieldDefine define) throws Exception {
        ExecutorContext context = new ExecutorContext((IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class));
        context.setUseDnaSql(false);
        dataQuery.setMasterKeys(ds);
        IDataTable dataTable = dataQuery.executeQuery(context);
        int rowCount = dataTable.getCount();
        if (rowCount > 0) {
            IDataRow dataRow = dataTable.getItem(0);
            AbstractData data = dataRow.getValue(define);
            return data.getAsFloat();
        }
        return 0.0;
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u8ba1\u7b97\u7ed3\u679c").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u62b5\u9500-\u5206\u6bb5\u6295\u8d44").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("FDTZ('ZCOX_YB02[B43]',true)").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }
}

