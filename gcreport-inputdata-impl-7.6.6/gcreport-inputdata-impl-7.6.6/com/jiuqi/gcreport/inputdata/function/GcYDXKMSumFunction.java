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
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.cache.formula.IFunctionCache
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.cache.formula.IFunctionCache;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.inputdata.formula.GcReportInputDataExceutorContext;
import com.jiuqi.gcreport.nr.impl.function.GcReportSimpleExecutorContext;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class GcYDXKMSumFunction
extends Function
implements IFunctionCache,
IGcFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcYDXKMSumFunction.class);
    private final String FUNCTION_NAME = "YDXKMSUM";
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    private static ThreadLocal<DoubleKeyMap<String, String, Set<String>>> allChildSubjectCodeBySystemIdAndCodeThreadLocal = new ThreadLocal();
    private static ThreadLocal<DoubleKeyMap<String, String, ConsolidatedSubjectEO>> subjectCodeBySystemIdAndCodeThreadLocal = new ThreadLocal();

    public GcYDXKMSumFunction() {
        this.parameters().add(new Parameter("subjectCode", 6, "\u79d1\u76ee\u4ee3\u7801", false));
    }

    public String name() {
        return "YDXKMSUM";
    }

    public String title() {
        return "\u83b7\u53d6\u5df2\u751f\u6210\u7684\u62b5\u9500\u5206\u5f55\u7684\u62b5\u9500\u91d1\u989d";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext queryContext = (QueryContext)context;
        String subjectCode = (String)parameters.get(0).evaluate((IContext)queryContext);
        List<GcOffSetVchrItemDTO> offsetItems = this.listOffsetItems(queryContext);
        return this.getOffSetAmtSum(offsetItems, subjectCode);
    }

    public String toDescription() {
        String supperDescription = super.toDescription();
        StringBuilder buffer = new StringBuilder(supperDescription);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u83b7\u53d6\u5df2\u751f\u6210\u7684\u62b5\u9500\u5206\u5f55\u7684\u62b5\u9500\u91d1\u989d").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u5df2\u751f\u6210\u7684\u62b5\u9500\u5206\u5f55\u7684\u62b5\u9500\u91d1\u989d ").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("YDXKMSUM('\u79d1\u76ee\u4ee3\u7801')").append(com.jiuqi.bi.util.StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public void enableCache() {
        allChildSubjectCodeBySystemIdAndCodeThreadLocal.set((DoubleKeyMap<String, String, Set<String>>)new DoubleKeyMap());
        subjectCodeBySystemIdAndCodeThreadLocal.set((DoubleKeyMap<String, String, ConsolidatedSubjectEO>)new DoubleKeyMap());
    }

    public void releaseCache() {
        allChildSubjectCodeBySystemIdAndCodeThreadLocal.remove();
        subjectCodeBySystemIdAndCodeThreadLocal.remove();
    }

    private Set<String> listAllChildSubjectCodeBySystemIdAndCode(String systemId, String subjectCode) {
        DoubleKeyMap<String, String, Set<String>> threadLocalSubjects = allChildSubjectCodeBySystemIdAndCodeThreadLocal.get();
        if (Objects.isNull(threadLocalSubjects) || CollectionUtils.isEmpty(threadLocalSubjects.get((Object)systemId))) {
            Set loadSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodes(subjectCode, systemId);
            DoubleKeyMap stringStringSetDoubleKeyMap = new DoubleKeyMap();
            stringStringSetDoubleKeyMap.put((Object)systemId, (Object)subjectCode, (Object)loadSubjectCodes);
            allChildSubjectCodeBySystemIdAndCodeThreadLocal.set((DoubleKeyMap<String, String, Set<String>>)stringStringSetDoubleKeyMap);
            return loadSubjectCodes;
        }
        Map subjectCodesGroupBySystemId = threadLocalSubjects.get((Object)systemId);
        Set subjectCodes = (Set)subjectCodesGroupBySystemId.get(subjectCode);
        if (CollectionUtils.isEmpty(subjectCodes)) {
            Set loadSubjectCodes = this.consolidatedSubjectService.listAllChildrenCodes(systemId, subjectCode);
            subjectCodesGroupBySystemId.put(subjectCode, loadSubjectCodes);
            return loadSubjectCodes;
        }
        return subjectCodes;
    }

    private ConsolidatedSubjectEO listConsolidatedSubjectBySubjectCode(String systemId, String subjectCode) {
        DoubleKeyMap<String, String, ConsolidatedSubjectEO> threadLocalSubjects = subjectCodeBySystemIdAndCodeThreadLocal.get();
        if (Objects.isNull(threadLocalSubjects) || CollectionUtils.isEmpty(threadLocalSubjects.get((Object)systemId))) {
            ConsolidatedSubjectEO consolidatedSubject = this.consolidatedSubjectService.getSubjectByCode(systemId, subjectCode);
            DoubleKeyMap stringStringSetDoubleKeyMap = new DoubleKeyMap();
            stringStringSetDoubleKeyMap.put((Object)systemId, (Object)subjectCode, (Object)consolidatedSubject);
            subjectCodeBySystemIdAndCodeThreadLocal.set((DoubleKeyMap<String, String, ConsolidatedSubjectEO>)stringStringSetDoubleKeyMap);
            return consolidatedSubject;
        }
        Map consolidatedSubjectGroupBySystemId = threadLocalSubjects.get((Object)systemId);
        ConsolidatedSubjectEO consolidatedSubject = (ConsolidatedSubjectEO)consolidatedSubjectGroupBySystemId.get(subjectCode);
        if (Objects.isNull(consolidatedSubject)) {
            ConsolidatedSubjectEO subject = this.consolidatedSubjectService.getSubjectByCode(systemId, subjectCode);
            threadLocalSubjects.get((Object)systemId).put(subjectCode, subject);
            return subject;
        }
        return consolidatedSubject;
    }

    private double getOffSetAmtSum(List<GcOffSetVchrItemDTO> offsetItems, String subjectCode) {
        if (StringUtils.isEmpty((String)(subjectCode = subjectCode.trim()))) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u79d1\u76ee\u4ee3\u7801\u4e3a\u7a7a");
        }
        double sumOffSetAmt = 0.0;
        if (!CollectionUtils.isEmpty(offsetItems)) {
            sumOffSetAmt = this.sumOffsetAmt(offsetItems, subjectCode);
        }
        return sumOffSetAmt;
    }

    private double sumOffsetAmt(List<GcOffSetVchrItemDTO> offsetItems, String subjectCode) {
        String systemId = offsetItems.get(0).getSystemId();
        Set<String> subjectCodes = this.listAllChildSubjectCodeBySystemIdAndCode(systemId, subjectCode);
        if (CollectionUtils.isEmpty(subjectCodes)) {
            subjectCodes.add(subjectCode);
        }
        double sumOffSetAmt = 0.0;
        ConsolidatedSubjectEO consolidatedSubject = this.listConsolidatedSubjectBySubjectCode(systemId, subjectCode);
        if (CollectionUtils.isEmpty(subjectCodes) || Objects.isNull(consolidatedSubject)) {
            LOGGER.error("\u5408\u5e76\u516c\u5f0fYDXKMSUM\u83b7\u53d6\u79d1\u76ee\u6570\u636e\u4e3a\u7a7a\uff0csubjectCode:" + subjectCode);
            return sumOffSetAmt;
        }
        for (GcOffSetVchrItemDTO offsetItem : offsetItems) {
            String subject;
            if (!Objects.nonNull(offsetItem) || !subjectCodes.contains(subject = offsetItem.getSubjectCode())) continue;
            if (OrientEnum.D.getValue().equals(consolidatedSubject.getOrient())) {
                sumOffSetAmt = sumOffSetAmt + offsetItem.getOffSetDebit() - offsetItem.getOffSetCredit();
                continue;
            }
            sumOffSetAmt = sumOffSetAmt + offsetItem.getOffSetCredit() - offsetItem.getOffSetDebit();
        }
        return sumOffSetAmt;
    }

    private List<GcOffSetVchrItemDTO> listOffsetItems(QueryContext queryContext) {
        List<? extends AbstractFieldDynamicDeclarator> offSetVchrItems;
        GcReportSimpleExecutorContext exceutorContext;
        ArrayList<GcOffSetVchrItemDTO> offsetItems = new ArrayList<GcOffSetVchrItemDTO>();
        if (queryContext.getExeContext() instanceof GcReportSimpleExecutorContext && (exceutorContext = (GcReportSimpleExecutorContext)queryContext.getExeContext()) instanceof GcReportExceutorContext && !CollectionUtils.isEmpty(offSetVchrItems = ((GcReportInputDataExceutorContext)exceutorContext).getOffSetVchrItems())) {
            offsetItems.addAll(offSetVchrItems);
        }
        return offsetItems;
    }
}

