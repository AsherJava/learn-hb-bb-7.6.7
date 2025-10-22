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
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.samecontrol.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.function.SameCtrlFormulaExecutionTool;
import com.jiuqi.gcreport.samecontrol.function.SameCtrlZbValueExecutionProcessor;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlChgOrgService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SameCtrlLastYearAdjustCalThisYearFunction
extends Function
implements INrFunction {
    private static final transient Logger logger = LoggerFactory.getLogger(SameCtrlLastYearAdjustCalThisYearFunction.class);
    @Autowired
    private SameCtrlChgOrgService sameCtrlChgOrgService;

    public SameCtrlLastYearAdjustCalThisYearFunction() {
        this.parameters().add(new Parameter("originalLastYearSamePeriodFormula", 6, "\u539f\u4e0a\u5e74\u540c\u671f\u6570\u516c\u5f0f"));
        this.parameters().add(new Parameter("lastYearSamePeriodZbCode", 6, "\u4e0a\u5e74\u540c\u671f\u6570\u6307\u6807"));
    }

    public String name() {
        return "SNTZSNS";
    }

    public String title() {
        return "\u4e0a\u5e74\u540c\u671f\u6570\u8c03\u6574\u8868\u4e2d\u4e0a\u5e74\u7684\u672c\u5e74\u7d2f\u8ba1\u6570\u6570\u516c\u5f0f\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        QueryContext queryContext = (QueryContext)iContext;
        String originalFormula = (String)list.get(0).evaluate(iContext);
        String lastYearSamePeriodZbCode = (String)list.get(1).evaluate((IContext)queryContext);
        return this.calResult(queryContext, originalFormula, lastYearSamePeriodZbCode);
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder stringBuilder = new StringBuilder(supperDescription);
        stringBuilder.append("\u8fd4\u56de\u503c\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u5982\u679c\u4e0d\u662f\u5dee\u989d\u5355\u4f4d\uff0c\u76f4\u63a5\u8fd4\u56de\u53c2\u65701\uff08\u539f\u4e0a\u5e74\u540c\u671f\u6570\u516c\u5f0f\uff09\u7684\u8fd0\u7b97\u7ed3\u679c;\u5dee\u989d\u5355\u4f4d\u4e0a\u8fd4\u56de\u4e0a\u5e74\u540c\u671f\u6570\u6307\u6807\u7684\u6570\u636e\u7ed3\u679c").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("\u573a\u666f\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("\u4e0a\u5e74\u540c\u671f\u6570\u8c03\u6574\u8868\u4e2d\u4e0a\u5e74\u7684\u672c\u5e74\u7d2f\u8ba1\u6570\u6570\u516c\u5f0f\u51fd\u6570").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("\u516c\u5f0f\uff1a").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("QCTZSNS('originaFormula','ZCOX_YB01[A01]')").append(StringUtils.LINE_SEPARATOR);
        stringBuilder.append("    ").append("    ").append("QCTZSNS('originaFormula','\u62a5\u8868\u6807\u8bc6[2,1]')").append(StringUtils.LINE_SEPARATOR);
        return stringBuilder.toString();
    }

    double calResult(QueryContext queryContext, String originalFormula, String lastYearSamePeriodZbCode) {
        double result = 0.0;
        try {
            DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
            GcOrgCacheVO org = SameCtrlZbValueExecutionProcessor.getGcOrgCache(dimensionValueSet);
            if (!StringUtils.isEmpty((String)originalFormula)) {
                result += this.originalFormulaAmt(queryContext, originalFormula);
            }
            if (GcOrgKindEnum.DIFFERENCE != org.getOrgKind()) {
                return result;
            }
            result += this.evaluateSameCtrlChgOrg(queryContext, lastYearSamePeriodZbCode, org);
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return result;
    }

    private double originalFormulaAmt(QueryContext queryContext, String originalFormula) {
        DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
        try {
            return SameCtrlFormulaExecutionTool.exaluateFormulaAmt(queryContext, dimensionValueSet, originalFormula);
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return 0.0;
        }
    }

    private double evaluateSameCtrlChgOrg(QueryContext queryContext, String lastYearEndZbCode, GcOrgCacheVO org) {
        double result = 0.0;
        try {
            List<SameCtrlChgOrgEO> sameCtrlChangeCodeList;
            DimensionValueSet dimensionValueSet = queryContext.getMasterKeys();
            String dataTime = (String)dimensionValueSet.getValue("DATATIME");
            List<SameCtrlChgOrgEO> sameCtrlVirtualCodeList = this.sameCtrlChgOrgService.listAllYearChgOrgsByDisposOrg(org.getParentId(), dataTime);
            if (!CollectionUtils.isEmpty(sameCtrlVirtualCodeList)) {
                List<String> virtualCodeList = sameCtrlVirtualCodeList.stream().map(SameCtrlChgOrgEO::getVirtualCode).collect(Collectors.toList());
                result += SameCtrlFormulaExecutionTool.evaluateSameCtrlZbValue(queryContext, virtualCodeList, dimensionValueSet, lastYearEndZbCode);
            }
            if (!CollectionUtils.isEmpty(sameCtrlChangeCodeList = this.sameCtrlChgOrgService.listAllYearChgOrgsByAcquisitionOrg(org.getParentId(), dataTime))) {
                List<String> changeCodeList = sameCtrlChangeCodeList.stream().map(SameCtrlChgOrgEO::getChangedCode).collect(Collectors.toList());
                result -= SameCtrlFormulaExecutionTool.evaluateSameCtrlZbValue(queryContext, changeCodeList, dimensionValueSet, lastYearEndZbCode);
            }
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return result;
    }
}

