/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class OffsetItemOrientFunction
extends Function
implements IGcFunction {
    public static final String FUNCTION_NAME = "OffsetItemOrient";
    @Resource
    private ConsolidatedSubjectClient subjectClient;

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u62b5\u9500\u5206\u5f55\u501f\u8d37\u65b9\u5411";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> list) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        GcReportSimpleExecutorContext executorContext = (GcReportSimpleExecutorContext)queryContext.getExeContext();
        DefaultTableEntity data = executorContext.getData();
        Double offsetDebit = ConverterUtils.getAsDouble((Object)data.getFieldValue("OFFSETDEBIT"));
        if (offsetDebit != null && offsetDebit != 0.0) {
            return 1;
        }
        Double offsetCredit = ConverterUtils.getAsDouble((Object)data.getFieldValue("OFFSETCREDIT"));
        if (offsetCredit != null && offsetCredit != 0.0) {
            return -1;
        }
        if (offsetDebit == null && offsetCredit == null) {
            return 0;
        }
        if (offsetDebit == null && offsetCredit != null) {
            return -1;
        }
        if (offsetCredit == null && offsetDebit != null) {
            return 1;
        }
        String systemId = ConverterUtils.getAsString((Object)data.getFieldValue("SYSTEMID"));
        if (com.jiuqi.common.base.util.StringUtils.isEmpty((String)systemId)) {
            return 1;
        }
        String subjectCode = ConverterUtils.getAsString((Object)data.getFieldValue("SUBJECTCODE"));
        if (com.jiuqi.common.base.util.StringUtils.isEmpty((String)subjectCode)) {
            return 1;
        }
        ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)this.subjectClient.getConsolidatedSubjectByCode(systemId, subjectCode).getData();
        return consolidatedSubjectVO == null ? 1 : -consolidatedSubjectVO.getOrient().intValue();
    }

    public String toDescription() {
        String description = super.toDescription();
        StringBuilder buffer = new StringBuilder(description);
        buffer.append("\u516c\u5f0f\u793a\u4f8b\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("OffsetItemOrient()").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u4e00\u822c\u5728\u5408\u5e76\u4f53\u7cfb\u516c\u5f0f\u4e2d\u4f7f\u7528\uff0c\u4f5c\u7528\u662f\u83b7\u53d6\u5f53\u524d\u62b5\u9500\u5206\u5f55\u7684\u501f\u8d37\u65b9\u5411\uff1a").append(StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u501f\u65b9\u91d1\u989d\u6709\u503c\u8fd4\u56de1;\u8d37\u65b9\u91d1\u989d\u6709\u503c\u8fd4\u56de-1;\u501f\u8d37\u65b9\u90fd\u4e3a\u7a7a\u65f6\u8fd4\u56de0;\u501f\u8d37\u65b9\u503c\u90fd\u4e3a0\u65f6\u8fd4\u56de\u79d1\u76ee\u65b9\u5411\u7684\u76f8\u53cd\u65b9\u5411");
        return buffer.toString();
    }
}

