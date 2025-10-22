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
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 */
package com.jiuqi.gcreport.invest.formula.function.rule.invest;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LastInvestChangeZbDataFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    public static final String FUNCTION_NAME = "LastInvestChangeZbData";
    private static final Logger log = LoggerFactory.getLogger(LastInvestChangeZbDataFunction.class);

    public LastInvestChangeZbDataFunction() {
        this.parameters().add(new Parameter("zbCode", 6, "\u6307\u6807\u4ee3\u7801"));
        this.parameters().add(new Parameter("changeScenarioCode", 6, "\u53d8\u52a8\u573a\u666f\u4ee3\u7801: \u6709\u53d8\u52a8\u573a\u666f\u7684\u8bdd\uff0c\u53d6\u6700\u65b0\u53d8\u52a8\u573a\u666f\u671f\u95f4\u7684\u6307\u6807\u6570\u636e", true));
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String changeScenario;
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        List items = executorContext.getItems();
        if (CollectionUtils.isEmpty((Collection)items)) {
            return 0.0;
        }
        DefaultTableEntity master = executorContext.getData();
        String investedUnitCode = (String)master.getFieldValue("INVESTEDUNIT");
        if (null == investedUnitCode) {
            return 0.0;
        }
        YearPeriodDO uiPeriodUtil = YearPeriodUtil.transform(null, (String)queryContext.getMasterKeys().getValue("DATATIME").toString());
        Date maxChangeDate = this.getMaxChangeDate(items, uiPeriodUtil, changeScenario = parameters.size() > 1 ? (String)parameters.get(1).evaluate(context) : null);
        if (null == maxChangeDate) {
            return 0.0;
        }
        DimensionValueSet ds = this.getDimensionValueSet(queryContext, investedUnitCode, uiPeriodUtil, maxChangeDate);
        try {
            Object zbCode = parameters.get(0).evaluate(null);
            AbstractData abstractData = NrTool.getZbValue((DimensionValueSet)ds, (String)((String)zbCode));
            return null == abstractData ? 0.0 : abstractData.getAsFloat();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0;
        }
    }

    private DimensionValueSet getDimensionValueSet(QueryContext queryContext, String investedUnitCode, YearPeriodDO uiPeriodUtil, Date maxChangeDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(maxChangeDate);
        YearPeriodDO newPeriodUtil = YearPeriodUtil.transform(null, (int)uiPeriodUtil.getType(), (Calendar)calendar);
        DimensionValueSet ds = new DimensionValueSet(queryContext.getCurrentMasterKey());
        ds.setValue("MD_ORG", (Object)investedUnitCode);
        ds.setValue("DATATIME", (Object)newPeriodUtil.toString());
        ds.setValue("DATATIME", (Object)newPeriodUtil.toString());
        YearPeriodObject yp = new YearPeriodObject(null, queryContext.getMasterKeys().getValue("DATATIME").toString());
        GcOrgCenterService orgCenterTool = GcOrgPublicTool.getInstance((String)((String)ds.getValue("MD_GCORGTYPE")), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO org = orgCenterTool.getUnionUnitByGrade(investedUnitCode);
        if (null != org && null != org.getOrgTypeId()) {
            ds.setValue("MD_GCORGTYPE", (Object)org.getOrgTypeId());
        }
        if (null != org) {
            ds.setValue("MD_ORG", (Object)org.getCode());
        }
        return ds;
    }

    private Date getMaxChangeDate(List<DefaultTableEntity> items, YearPeriodDO uiPeriodUtil, String changeScenario) {
        List<Object> changeScenarioList = new ArrayList();
        if (!StringUtils.isEmpty((String)changeScenario)) {
            String[] split = changeScenario.split(",");
            changeScenarioList = Arrays.asList(split);
        }
        int calcYear = uiPeriodUtil.getYear();
        Date calcDate = uiPeriodUtil.getEndDate();
        Date maxChangeDate = null;
        for (DefaultTableEntity item : items) {
            int changeYear;
            Date changeDate;
            if (!CollectionUtils.isEmpty(changeScenarioList) && !changeScenarioList.contains(item.getFieldValue("CHANGESCENARIO")) || null == (changeDate = (Date)item.getFieldValue("CHANGEDATE")) || calcYear != (changeYear = DateUtils.getDateFieldValue((Date)changeDate, (int)1)) || DateUtils.compare((Date)changeDate, (Date)calcDate, (boolean)true) > 0) continue;
            maxChangeDate = DateUtils.max(maxChangeDate, (Date)changeDate, (boolean)true);
        }
        return maxChangeDate;
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.title()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("zbCode").append(":\u6307\u6807\u4ee3\u7801, \u7c7b\u578b:").append(DataType.toString((int)6)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("changeScenarioCode").append(":\u53d8\u52a8\u573a\u666f\u4ee3\u7801, \u7c7b\u578b:").append(DataType.toString((int)6)).append(",\u975e\u5fc5\u586b\uff0c\u6309\u6708\u589e\u91cf\u62b5\u9500\u573a\u666f\u65e0\u9700\u914d\u7f6e").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6307\u6807\u6570\u636e").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u6307\u6807\u53d8\u52a8\u573a\u666f0103\u5bf9\u5e94\u7684\u6700\u65b0\u671f(\u53d8\u52a8\u65f6\u671f)\u7684\u88ab\u6295\u8d44\u5355\u4f4d\u7684ZCOX_YB01[A06]\u6307\u6807\u6570\u636e").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("LastInvestChangeZbData('ZCOX_YB01[A06]','0103')").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u88ab\u6295\u8d44\u5355\u4f4d\u5f53\u524d\u5e74\u6700\u65b0\u53d8\u52a8\u671f\u6307\u6807\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }
}

