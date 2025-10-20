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
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 */
package com.jiuqi.gcreport.asset.formula.function.rule.asset;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.asset.formula.function.rule.asset.AssetDepreMonth;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CommonAssetDepreFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    public static final String FUNCTION_NAME = "CommonAssetDepre";

    public CommonAssetDepreFunction() {
        this.parameters().add(new Parameter("assetType", 6, "\u8d44\u4ea7\u7c7b\u578b"));
        this.parameters().add(new Parameter("timeType", 6, "\u91c7\u8d2d\u65f6\u95f4\u7c7b\u578b"));
        this.parameters().add(new Parameter("negativeDepreciation", 3, "\u8d1f\u6570\u6298\u65e7\u65b9\u5f0f\uff0c\u7f3a\u7701\u65f6\u8d1f\u6570\u63090\u6298\u65e7", true));
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity master = executorContext.getData();
        String assetType = (String)parameters.get(0).evaluate(null);
        String timeType = (String)parameters.get(1).evaluate(null);
        boolean negativeNotDepreFlag = true;
        if (parameters.size() > 2) {
            Integer negativeDepreciation = ConverterUtils.getAsInteger((Object)parameters.get(2).evaluate(null));
            negativeNotDepreFlag = negativeDepreciation == null || negativeDepreciation == 0;
        }
        YearPeriodDO uiPeriodUtil = YearPeriodUtil.transform(null, (String)queryContext.getMasterKeys().getValue("DATATIME").toString());
        if (!"OGA".equals(assetType)) {
            return this.getDepreAmtByAssetTypeAndTimeType(master, assetType, timeType, uiPeriodUtil, negativeNotDepreFlag);
        }
        double depreAmt = 0.0;
        for (DefaultTableEntity ogMaster : this.splitMaster(master)) {
            depreAmt += this.getDepreAmtByAssetTypeAndTimeType(ogMaster, "FA", timeType, uiPeriodUtil, negativeNotDepreFlag);
        }
        return depreAmt;
    }

    private List<DefaultTableEntity> splitMaster(DefaultTableEntity master) {
        if (master == null) {
            return Collections.emptyList();
        }
        DefaultTableEntity master1 = new DefaultTableEntity();
        DefaultTableEntity master2 = new DefaultTableEntity();
        BeanUtils.copyProperties(master, master1);
        BeanUtils.copyProperties(master, master2);
        master1.getFields().putAll(master.getFields());
        master2.getFields().putAll(master.getFields());
        double unrealizedGainLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"UNREALIZEDGAINLOSS");
        double assetsLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"ASSETSLOSS");
        double initDpcaAmt = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"INITDPCAAMT");
        master1.addFieldValue("UNREALIZEDGAINLOSS", (Object)(unrealizedGainLoss / 2.0));
        master2.addFieldValue("UNREALIZEDGAINLOSS", (Object)(unrealizedGainLoss / 2.0));
        master1.addFieldValue("ASSETSLOSS", (Object)(assetsLoss / 2.0));
        master2.addFieldValue("ASSETSLOSS", (Object)(assetsLoss / 2.0));
        master1.addFieldValue("INITDPCAAMT", (Object)(initDpcaAmt / 2.0));
        master2.addFieldValue("INITDPCAAMT", (Object)(initDpcaAmt / 2.0));
        Date purchaseDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"PURCHASEDATE");
        Calendar cal = Calendar.getInstance();
        cal.setTime(purchaseDate);
        cal.set(6, cal.getActualMaximum(6));
        master1.addFieldValue("PURCHASEDATE", (Object)cal.getTime());
        return Arrays.asList(master1, master2);
    }

    private double getDepreAmtByAssetTypeAndTimeType(DefaultTableEntity master, String assetType, String timeType, YearPeriodDO uiPeriodUtil, boolean negativeNotDepreFlag) {
        double unrealizedGainLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"UNREALIZEDGAINLOSS");
        double assetsLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"ASSETSLOSS");
        double rmValueRate = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"RMVALUERATE");
        double monthlyDepreAmt = this.monthlyDepreAmt(master);
        if (monthlyDepreAmt == 0.0) {
            return 0.0;
        }
        double totalDepreAmt = (unrealizedGainLoss - assetsLoss) * (100.0 - rmValueRate) / 100.0;
        double initDpcaAmt = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"INITDPCAAMT");
        if (negativeNotDepreFlag && totalDepreAmt - initDpcaAmt <= 0.0) {
            return 0.0;
        }
        double priorDepreAmt = (double)new AssetDepreMonth().depreMonth(master, uiPeriodUtil, assetType, "PRIOR") * monthlyDepreAmt;
        if ("PRIOR".equalsIgnoreCase(timeType)) {
            return priorDepreAmt;
        }
        if ("CURR".equalsIgnoreCase(timeType)) {
            double currDepreAmt = (double)new AssetDepreMonth().depreMonth(master, uiPeriodUtil, assetType, "CURR") * monthlyDepreAmt;
            return currDepreAmt;
        }
        return 0.0;
    }

    private double monthlyDepreAmt(DefaultTableEntity master) {
        String dpcaMethod = InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"DPCAMETHOD");
        String averageAgeMethod = "01";
        String comprehensiveDepreciationMethod = "02";
        if ("01".equals(dpcaMethod)) {
            return this.averageAgeMethod(master);
        }
        if ("02".equals(dpcaMethod)) {
            return this.comprehensiveDepreciationMethod(master);
        }
        return 0.0;
    }

    private Double averageAgeMethod(DefaultTableEntity master) {
        double unrealizedGainLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"UNREALIZEDGAINLOSS");
        double assetsLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"ASSETSLOSS");
        double rmValueRate = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"RMVALUERATE");
        double dpcaYear = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCAYEAR");
        if (dpcaYear <= 0.0) {
            return 0.0;
        }
        return (unrealizedGainLoss - assetsLoss) * (100.0 - rmValueRate) / 100.0 / dpcaYear;
    }

    private Double comprehensiveDepreciationMethod(DefaultTableEntity master) {
        double unrealizedGainLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"UNREALIZEDGAINLOSS");
        double assetsLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"ASSETSLOSS");
        double dpcaRate = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCARATE");
        return (unrealizedGainLoss - assetsLoss) * dpcaRate / 100.0;
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
        buffer.append("    ").append("assetType").append("\uff1a").append(DataType.toString((int)6)).append("\uff1bFA(\u56fa\u5b9a\u8d44\u4ea7),IA(\u65e0\u5f62\u8d44\u4ea7),OGA(\u6cb9\u6c14\u8d44\u4ea7)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("timeType").append("\uff1a").append(DataType.toString((int)6)).append("\uff1bCURR\uff08\u5f53\u524d\u5e74\uff09,PRIOR\uff08\u4ee5\u524d\u5e74\uff09").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("negativeDepreciation").append("\uff1a").append(DataType.toString((int)3)).append("\uff1b0\uff08\u6298\u65e7\u91d1\u989d\u5c0f\u4e8e0\u65f6\u63090\u6298\u65e7\uff09,1\uff08\u6298\u65e7\u91d1\u989d\u5c0f\u4e8e0\u65f6\u6309\u8d1f\u6570\u6298\u65e7\uff09 \u7f3a\u7701\u65f6\u63090\u5904\u7406").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u6298\u65e7\u91d1\u989d*\u6298\u65e7\u6708").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u4ee5\u524d\u5e74\u65e0\u5f62\u8d44\u4ea7\u6298\u65e7\u6298\u65e7\u91d1\u989d*\u6298\u65e7\u6708 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("CommonAssetDepre('IA','PRIOR',1)").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u5e38\u89c4\u8d44\u4ea7\u53f0\u8d26\u6298\u65e7";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Integer negativeDepreciation;
        String assetType = (String)parameters.get(0).evaluate(null);
        String timeType = (String)parameters.get(1).evaluate(null);
        if (!("FA".equalsIgnoreCase(assetType) || "IA".equalsIgnoreCase(assetType) || "OGA".equalsIgnoreCase(assetType))) {
            throw new SyntaxException(parameters.get(0).getToken(), "CommonAssetDepre\u51fd\u6570\u7b2c\u4e00\u4e2a\u53c2\u6570\u53ea\u80fd\u4e3a'FA'\u6216\u8005'IA'");
        }
        if (!"CURR".equalsIgnoreCase(timeType) && !"PRIOR".equalsIgnoreCase(timeType)) {
            throw new SyntaxException(parameters.get(1).getToken(), "CommonAssetDepre\u51fd\u6570\u7b2c\u4e8c\u4e2a\u53c2\u6570\u53ea\u80fd\u4e3a'CURR'\u6216\u8005'PRIOR'");
        }
        if (parameters.size() > 2 && (negativeDepreciation = ConverterUtils.getAsInteger((Object)parameters.get(2).evaluate(null))) != null && negativeDepreciation != 0 && negativeDepreciation != 1) {
            throw new SyntaxException(parameters.get(2).getToken(), "CommonAssetDepre\u51fd\u6570\u7b2c\u4e09\u4e2a\u53c2\u6570\u53ea\u80fd\u4e3a0\u6216\u80051");
        }
        return super.validate(context, parameters);
    }
}

