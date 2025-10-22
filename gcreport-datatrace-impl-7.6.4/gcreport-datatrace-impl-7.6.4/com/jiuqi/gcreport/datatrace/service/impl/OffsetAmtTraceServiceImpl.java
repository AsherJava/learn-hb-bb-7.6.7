/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO
 *  com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO$CheckItem
 *  com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO$OriginItem
 *  com.jiuqi.gcreport.datatrace.enums.ExperessionTypeEnum
 *  com.jiuqi.gcreport.datatrace.event.GcDataTraceRebuildSrcGroupIdsQueryParamsEvent
 *  com.jiuqi.gcreport.datatrace.event.GcDataTraceRebuildSrcGroupIdsQueryParamsEvent$QueryParamsInfo
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceOffsetItemCondi
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO
 *  com.jiuqi.gcreport.offsetitem.caculate.service.GcCaculateOffsetItemService
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl
 *  com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil
 *  com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.InventoryRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO
 *  com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO
 *  com.jiuqi.gcreport.unionrule.service.UnionRuleService
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.datatrace.service.impl;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.datatrace.context.GcDataTracerContext;
import com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO;
import com.jiuqi.gcreport.datatrace.enums.ExperessionTypeEnum;
import com.jiuqi.gcreport.datatrace.event.GcDataTraceRebuildSrcGroupIdsQueryParamsEvent;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTraceProcessor;
import com.jiuqi.gcreport.datatrace.ruletracer.UnionRuleTracer;
import com.jiuqi.gcreport.datatrace.service.GcDataTracerService;
import com.jiuqi.gcreport.datatrace.service.OffsetAmtTraceService;
import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceOffsetItemCondi;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO;
import com.jiuqi.gcreport.offsetitem.caculate.service.GcCaculateOffsetItemService;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import com.jiuqi.gcreport.offsetitem.utils.OffsetConvertUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.dto.AbstractInvestmentRule;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.FinancialCheckRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FixedAssetsRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FixedTableRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FlexibleRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.FloatLineRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.InventoryRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.LeaseRuleDTO;
import com.jiuqi.gcreport.unionrule.dto.PublicValueAdjustmentRuleDTO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OffsetAmtTraceServiceImpl
implements OffsetAmtTraceService {
    private final Logger logger = LoggerFactory.getLogger(OffsetAmtTraceServiceImpl.class);
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private UnionRuleService unionRuleService;
    @Autowired
    private List<UnionRuleTracer> allUnionRuleTracers;
    @Autowired
    private ReportFormulaParseUtil reportFormulaParseUtil;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRuntimeTaskService taskService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private GcOffSetItemAdjustCoreServiceImpl offSetItemAdjustService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private GcDataTracerService gcDataTraceService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private GcCaculateOffsetItemService gcCaculateOffsetItemService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OffsetAmtTraceServiceImpl.class);
    private static ThreadLocal<NumberFormat> decimalFormatThreadLocal = ThreadLocal.withInitial(() -> {
        NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
        nf.setGroupingUsed(true);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf;
    });

    public static String formatBigDecimal(BigDecimal bigDecimal) {
        if (BigDecimal.ZERO.compareTo(bigDecimal) == 0) {
            return "0.00";
        }
        return decimalFormatThreadLocal.get().format(bigDecimal);
    }

    @Override
    public List<OffsetAmtTraceResultVO> traceOffsetAmt(String offsetItemId, GcTaskBaseArguments taskArg) {
        GcOffSetVchrItemAdjustEO offSetItem;
        if (StringUtils.isEmpty((String)offsetItemId)) {
            return Collections.emptyList();
        }
        if (StringUtils.isEmpty((String)taskArg.getSelectAdjustCode())) {
            taskArg.setSelectAdjustCode("0");
        }
        if ((offSetItem = OffsetConvertUtil.getInstance().convertDTO2EO(this.offsetCoreService.getGcOffSetVchrItemDTO(offsetItemId))) == null) {
            return Collections.emptyList();
        }
        AbstractUnionRule unionRule = this.unionRuleService.selectUnionRuleDTOById(offSetItem.getRuleId());
        if (unionRule == null) {
            return Collections.emptyList();
        }
        UnionRuleTracer unionRuleTracer = this.getRuleTracerByType(unionRule.getRuleType());
        if (unionRuleTracer == null) {
            throw new BusinessRuntimeException(unionRule.getRuleType() + "\u4e0d\u652f\u6301\u62b5\u9500\u5206\u5f55\u91d1\u989d\u8ffd\u6eaf\u3002");
        }
        UnionRuleTraceProcessor unionRuleTraceProcessor = unionRuleTracer.newProcessorInstance(offSetItem, unionRule, taskArg);
        return this.trace(unionRuleTraceProcessor);
    }

    @Override
    public List<OffsetTraceResultVO> traceOffsetGroupAmt(String mrecid, GcTaskBaseArguments taskArg) {
        List offSetItems = this.offsetCoreService.listByWhere(new String[]{"mrecid"}, new Object[]{mrecid});
        return this.listOffsetTraceResultVOS(taskArg, offSetItems);
    }

    @Override
    public List<OffsetTraceResultVO> listOffsetTraceResultVOS(GcTaskBaseArguments taskArg, List<GcOffSetVchrItemAdjustEO> offSetVchrItemAdjustEOS) {
        AbstractUnionRule unionRule;
        if (CollectionUtils.isEmpty(offSetVchrItemAdjustEOS)) {
            return Collections.emptyList();
        }
        if (StringUtils.isEmpty((String)taskArg.getSelectAdjustCode())) {
            taskArg.setSelectAdjustCode("0");
        }
        if ((unionRule = this.unionRuleService.selectUnionRuleDTOById(((GcOffSetVchrItemAdjustEO)(offSetVchrItemAdjustEOS = offSetVchrItemAdjustEOS.stream().sorted(OffsetItemComparatorUtil.eoUniversalComparator()).collect(Collectors.toList())).get(0)).getRuleId())) == null) {
            return Collections.emptyList();
        }
        return offSetVchrItemAdjustEOS.stream().map(offSetItem -> this.traceOffsetAmt((GcOffSetVchrItemAdjustEO)offSetItem, taskArg, unionRule)).collect(Collectors.toList());
    }

    private UnionRuleTracer getRuleTracerByType(String ruleType) {
        Optional<UnionRuleTracer> unionRuleTracer = this.allUnionRuleTracers.stream().filter(runTracer -> runTracer.getRuleType().equals(ruleType)).findFirst();
        return unionRuleTracer.orElse(null);
    }

    private OffsetTraceResultVO traceOffsetAmt(GcOffSetVchrItemAdjustEO offsetItem, GcTaskBaseArguments taskArg, AbstractUnionRule unionRule) {
        if (StringUtils.isEmpty((String)taskArg.getSelectAdjustCode())) {
            taskArg.setSelectAdjustCode("0");
        }
        OffsetTraceResultVO offsetTraceResult = new OffsetTraceResultVO();
        this.setOffsetTraceResultInfo(offsetItem, offsetTraceResult, taskArg.getOrgType());
        UnionRuleTracer unionRuleTracer = this.getRuleTracerByType(unionRule.getRuleType());
        if (unionRuleTracer == null) {
            throw new BusinessRuntimeException(unionRule.getRuleType() + "\u4e0d\u652f\u6301\u62b5\u9500\u5206\u5f55\u91d1\u989d\u8ffd\u6eaf\u3002");
        }
        UnionRuleTraceProcessor unionRuleTraceProcessor = unionRuleTracer.newProcessorInstance(offsetItem, unionRule, taskArg);
        List<OffsetAmtTraceResultVO> offsetAmtTraceResults = this.trace(unionRuleTraceProcessor);
        offsetTraceResult.setOffsetAmtTraces(offsetAmtTraceResults);
        if (!CollectionUtils.isEmpty(offsetAmtTraceResults)) {
            offsetTraceResult.setFetchFormula(offsetAmtTraceResults.stream().map(OffsetAmtTraceResultVO::getFunction).collect(Collectors.joining("\n")));
        }
        return offsetTraceResult;
    }

    private List<OffsetAmtTraceResultVO> trace(UnionRuleTraceProcessor unionRuleTraceProcessor) {
        List<FetchItemDTO> fetchItems = unionRuleTraceProcessor.getFetchItem();
        GcOffSetVchrItemAdjustEO offSetItem = unionRuleTraceProcessor.getOffSetItem();
        if (CollectionUtils.isEmpty(fetchItems)) {
            return Collections.emptyList();
        }
        ArrayList<OffsetAmtTraceResultVO> allTraces = new ArrayList<OffsetAmtTraceResultVO>();
        ArrayList<OffsetAmtTraceResultVO> equalWithOffsetAmtTraces = new ArrayList<OffsetAmtTraceResultVO>();
        fetchItems.forEach(item -> {
            if (!CollectionUtils.isEmpty(equalWithOffsetAmtTraces)) {
                return;
            }
            unionRuleTraceProcessor.initFetchItem((FetchItemDTO)item);
            OffsetAmtTraceResultVO offsetAmtTraceResult = unionRuleTraceProcessor.takeOverTrace((FetchItemDTO)item) ? unionRuleTraceProcessor.traceByTakeOver() : this.traceByFormula(unionRuleTraceProcessor, item.getFormula());
            if (Objects.nonNull(offsetAmtTraceResult)) {
                allTraces.add(offsetAmtTraceResult);
                if (offSetItem.getOffsetAmt().equals(offsetAmtTraceResult.getAmt())) {
                    equalWithOffsetAmtTraces.add(offsetAmtTraceResult);
                }
            }
        });
        if (CollectionUtils.isEmpty(equalWithOffsetAmtTraces)) {
            return allTraces;
        }
        return equalWithOffsetAmtTraces;
    }

    private OffsetAmtTraceResultVO traceByFormula(UnionRuleTraceProcessor unionRuleTraceProcessor, String formula) {
        IExpression expression;
        GcOffSetVchrItemAdjustEO offSetItem = unionRuleTraceProcessor.getOffSetItem();
        ExecutorContext executorContext = unionRuleTraceProcessor.getExecutorContext();
        OffsetAmtTraceResultVO offsetAmtTraceResult = new OffsetAmtTraceResultVO();
        ArrayList<OffsetAmtTraceItemVO> offsetAmtTraceItems = new ArrayList<OffsetAmtTraceItemVO>();
        try {
            expression = this.reportFormulaParseUtil.parseFormula(executorContext, formula);
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u8ffd\u6eaf\u516c\u5f0f\u89e3\u6790\u5f02\u5e38\u3002", e);
            throw new BusinessRuntimeException("\u516c\u5f0f\u89e3\u6790\u5f02\u5e38\uff0c" + e.getMessage());
        }
        for (IASTNode iastNode : expression) {
            OffsetAmtTraceItemVO traceItem = this.traceNode(iastNode, executorContext, unionRuleTraceProcessor, offSetItem);
            if (traceItem == null) continue;
            offsetAmtTraceItems.add(traceItem);
        }
        offsetAmtTraceResult.setFunction(formula);
        offsetAmtTraceResult.setOffsetAmtTraceItems(offsetAmtTraceItems);
        try {
            AbstractData offsetAmt = unionRuleTraceProcessor.formulaEval(formula);
            offsetAmtTraceResult.setAmt(Double.valueOf(GcAbstractData.getDoubleValue((AbstractData)offsetAmt)));
        }
        catch (Exception e) {
            this.logger.error("\u62b5\u9500\u91d1\u989d\u8ffd\u6eaf\uff0c\u516c\u5f0f\u6267\u884c\u5f02\u5e38\u3002", e);
            throw new BusinessRuntimeException("\u516c\u5f0f\u6267\u884c\u5f02\u5e38\uff0c" + e.getMessage());
        }
        return offsetAmtTraceResult;
    }

    private OffsetAmtTraceItemVO traceNode(IASTNode node, ExecutorContext context, UnionRuleTraceProcessor processor, GcOffSetVchrItemAdjustEO offSetItem) {
        OffsetAmtTraceItemVO offsetAmtTraceItem;
        ASTNodeType nodeType = node.getNodeType();
        switch (nodeType) {
            case DYNAMICDATA: {
                if (node instanceof DynamicDataNode) {
                    offsetAmtTraceItem = this.traceDynamicDataNode((DynamicDataNode)node, context);
                    break;
                }
                offsetAmtTraceItem = this.traceCommonIASTNode(node, context);
                break;
            }
            case FUNCTION: {
                if (node instanceof FunctionNode) {
                    offsetAmtTraceItem = this.traceFunctionNode((FunctionNode)node, context);
                    break;
                }
                offsetAmtTraceItem = this.traceCommonIASTNode(node, context);
                break;
            }
            default: {
                return null;
            }
        }
        try {
            if (!StringUtils.isEmpty((String)offsetAmtTraceItem.getExpression())) {
                if ("PHS()".equalsIgnoreCase(offsetAmtTraceItem.getExpression())) {
                    offsetAmtTraceItem.setValue((Object)NumberUtils.doubleToString((double)offSetItem.getOffsetAmt(), (int)10, (int)2, (boolean)true));
                } else {
                    AbstractData abstractData = processor.formulaEval(offsetAmtTraceItem);
                    if (offsetAmtTraceItem.isExpressionExtendInfoShow()) {
                        return offsetAmtTraceItem;
                    }
                    if (abstractData.dataType == 3 || abstractData.dataType == 4 || abstractData.dataType == 10) {
                        offsetAmtTraceItem.setValue((Object)NumberUtils.doubleToString((double)GcAbstractData.getDoubleValue((AbstractData)abstractData, (int)10), (int)10, (int)2, (boolean)true));
                    } else {
                        offsetAmtTraceItem.setValue((Object)GcAbstractData.getStringValue((AbstractData)abstractData));
                    }
                }
            }
        }
        catch (Exception e) {
            offsetAmtTraceItem.setValue((Object)"\u6267\u884c\u5f02\u5e38");
            this.logger.error("\u62b5\u9500\u91d1\u989d\u8ffd\u6eaf\uff0c\u516c\u5f0f\u6267\u884c\u5f02\u5e38\u3002", e);
        }
        return offsetAmtTraceItem;
    }

    private OffsetAmtTraceItemVO traceCommonIASTNode(IASTNode node, ExecutorContext context) {
        OffsetAmtTraceItemVO offsetAmtTraceItem = new OffsetAmtTraceItemVO();
        try {
            String formula = node.interpret((IContext)context, Language.FORMULA, null);
            offsetAmtTraceItem.setExpression(formula);
        }
        catch (InterpretException e) {
            this.logger.error("\u62b5\u9500\u91d1\u989d\u8ffd\u6eaf\uff0c\u516c\u5f0f\u89e3\u6790\u5f02\u5e38\u3002", e);
            offsetAmtTraceItem.setValue((Object)"\u516c\u5f0f\u89e3\u6790\u5f02\u5e38");
        }
        return offsetAmtTraceItem;
    }

    private OffsetAmtTraceItemVO traceDynamicDataNode(DynamicDataNode node, ExecutorContext context) {
        OffsetAmtTraceItemVO offsetAmtTraceItem = new OffsetAmtTraceItemVO();
        try {
            String formula = node.interpret((IContext)context, Language.FORMULA, null);
            offsetAmtTraceItem.setExpression(formula);
        }
        catch (InterpretException e) {
            this.logger.error("\u62b5\u9500\u91d1\u989d\u8ffd\u6eaf\uff0c\u516c\u5f0f\u89e3\u6790\u5f02\u5e38\u3002", e);
            offsetAmtTraceItem.setValue((Object)"\u516c\u5f0f\u89e3\u6790\u5f02\u5e38");
        }
        String tableName = node.getQueryField().getTableName();
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(tableName);
        DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(tableModelDefine.getCode());
        if (dataTable != null) {
            offsetAmtTraceItem.setExpressionType(ExperessionTypeEnum.NRFIELD.getTitle());
        } else {
            offsetAmtTraceItem.setExpressionType(ExperessionTypeEnum.VABILL.getTitle());
        }
        ColumnModelDefine column = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), node.getQueryField().getFieldCode());
        offsetAmtTraceItem.setDescription(tableModelDefine.getTitle() + "\u3010" + column.getTitle() + "\u3011");
        return offsetAmtTraceItem;
    }

    private OffsetAmtTraceItemVO traceFunctionNode(FunctionNode node, ExecutorContext context) {
        OffsetAmtTraceItemVO offsetAmtTraceItem = new OffsetAmtTraceItemVO();
        try {
            String formula = node.interpret((IContext)context, Language.FORMULA, null);
            offsetAmtTraceItem.setExpression(formula);
        }
        catch (InterpretException e) {
            this.logger.error("\u62b5\u9500\u91d1\u989d\u8ffd\u6eaf\uff0c\u516c\u5f0f\u8f6c\u6362\u5f02\u5e38\u3002", e);
            offsetAmtTraceItem.setValue((Object)"\u516c\u5f0f\u8f6c\u6362\u5f02\u5e38");
        }
        offsetAmtTraceItem.setExpressionType(ExperessionTypeEnum.AUTOCALC.getTitle());
        offsetAmtTraceItem.setDescription(node.getDefine().title());
        return offsetAmtTraceItem;
    }

    private void setOffsetTraceResultInfo(GcOffSetVchrItemAdjustEO offset, OffsetTraceResultVO offSetPenetrateVO, String orgType) {
        BeanUtils.copyProperties(offset, offSetPenetrateVO);
        ConsolidatedSubjectEO subjectEO = this.consolidatedSubjectService.getSubjectByCode(offset.getSystemId(), offset.getSubjectCode());
        if (subjectEO != null) {
            offSetPenetrateVO.setSubjectName(subjectEO.getTitle());
        }
        YearPeriodObject yp = new YearPeriodObject(null, offset.getDefaultPeriod());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO unitVO = tool.getOrgByCode(offset.getUnitId());
        offSetPenetrateVO.setUnitName(unitVO.getCode() + "|" + unitVO.getTitle());
        GcOrgCacheVO oppUnitVo = tool.getOrgByCode(offset.getOppUnitId());
        offSetPenetrateVO.setOppUnitName(oppUnitVo.getCode() + "|" + oppUnitVo.getTitle());
        Double diffD = offset.getDiffd();
        Double diffC = offset.getDiffc();
        offSetPenetrateVO.setDiff(NumberUtils.doubleToString((double)NumberUtils.sum((Double)diffD, (Double)diffC)));
        if (offset.getOrient().equals(OrientEnum.D.getValue())) {
            offSetPenetrateVO.setDebitStr(NumberUtils.doubleToString((Double)offset.getOffsetAmt()));
        } else {
            offSetPenetrateVO.setCreditStr(String.valueOf(NumberUtils.doubleToString((Double)offset.getOffsetAmt())));
        }
    }

    @Override
    public Pagination<Map<String, Object>> queryDataTraceOffsetEntry(GcDataTraceCondi condi) {
        QueryParamsVO paramsVO = this.buildQueryParamsByDataTraceCondi(condi);
        paramsVO.setPageNum(condi.getPageNum().intValue());
        paramsVO.setPageSize(condi.getPageSize().intValue());
        ArrayList<String> srcGroupIds = new ArrayList<String>();
        srcGroupIds.add(condi.getSrcId());
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcDataTraceRebuildSrcGroupIdsQueryParamsEvent((Object)this, new GcDataTraceRebuildSrcGroupIdsQueryParamsEvent.QueryParamsInfo(condi, srcGroupIds)));
        Pagination<Map<String, Object>> offsetPage = this.getOffsetEntryBySrcOffsetGroupIds(paramsVO, new HashSet<String>(srcGroupIds));
        List offsetDatas = offsetPage.getContent();
        Set mRecids = offsetDatas.stream().map(offset -> (String)offset.get("MRECID")).collect(Collectors.toSet());
        offsetPage.setTotalElements(Integer.valueOf(mRecids.size()));
        List unSortedRecords = offsetDatas.stream().filter(offset -> mRecids.contains(offset.get("MRECID"))).collect(Collectors.toList());
        List sortOffsetDatas = this.offSetItemAdjustService.setRowSpanAndSort(unSortedRecords);
        offsetPage.setContent(sortOffsetDatas);
        return offsetPage;
    }

    private QueryParamsVO buildQueryParamsByDataTraceCondi(GcDataTraceCondi condi) {
        ConsolidatedTaskVO consolidatedTaskVO;
        TaskDefine taskDefine;
        PeriodType periodType;
        if (CollectionUtils.isEmpty(condi.getRuleIds())) {
            throw new BusinessRuntimeException("\u5408\u5e76\u89c4\u5219\u6761\u4ef6\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        QueryParamsVO paramsVO = new QueryParamsVO();
        paramsVO.setOrgType(condi.getOrgType());
        YearPeriodObject yp = new YearPeriodObject(null, condi.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condi.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO inputUnit = tool.getOrgByCode(condi.getInputUnitId());
        if (inputUnit != null && GcOrgKindEnum.UNIONORG.equals((Object)inputUnit.getOrgKind())) {
            paramsVO.setOrgId(condi.getInputUnitId());
        } else {
            GcOrgCacheVO oppUnit;
            GcOrgCacheVO unit = tool.getOrgByCode(condi.getUnitId());
            GcOrgCacheVO commonUnit = tool.getCommonUnit(unit, oppUnit = tool.getOrgByCode(condi.getOppUnitId()));
            if (commonUnit != null) {
                paramsVO.setOrgId(commonUnit.getId());
            }
        }
        paramsVO.setUnitIdList(Arrays.asList(condi.getUnitId()));
        paramsVO.setOppUnitIdList(Arrays.asList(condi.getOppUnitId()));
        paramsVO.setAcctYear(condi.getAcctYear());
        paramsVO.setAcctPeriod(condi.getAcctPeriod());
        paramsVO.setPeriodStr(condi.getPeriodStr());
        paramsVO.setSelectAdjustCode(condi.getSelectAdjustCode());
        paramsVO.setTaskId(condi.getTaskId());
        try {
            FormSchemeDefine formSchemeDefine;
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(condi.getPeriodStr(), condi.getTaskId());
            if (schemePeriodLinkDefine != null && (formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey())) != null) {
                paramsVO.setSchemeId(formSchemeDefine.getKey());
            }
        }
        catch (Exception e) {
            LOGGER.error("\u6570\u636e\u6eaf\u6e90\u53c2\u6570\u83b7\u53d6\u4e0d\u5230\u65b9\u6848ID:" + e.getMessage(), e);
        }
        paramsVO.setCurrency(condi.getCurrency());
        paramsVO.setFilterDisableItem(true);
        if (!StringUtils.isEmpty((String)condi.getSrcId())) {
            paramsVO.setSrcOffsetGroupIds(Arrays.asList(condi.getSrcId()));
        }
        if ((periodType = (taskDefine = this.taskService.queryTaskDefine(condi.getTaskId())).getPeriodType()) != null) {
            paramsVO.setPeriodType(Integer.valueOf(periodType.type()));
        }
        if ((consolidatedTaskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(condi.getTaskId(), condi.getPeriodStr())) == null) {
            LOGGER.error("\u62b5\u9500\u5206\u5f55\u6570\u636e\u6eaf\u6e90-\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5408\u5e76\u4f53\u7cfb\uff0ctaskId:{}, periodStr:{}", (Object)condi.getTaskId(), (Object)condi.getPeriodStr());
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5408\u5e76\u4f53\u7cfb");
        }
        paramsVO.setSystemId(consolidatedTaskVO.getSystemId());
        paramsVO.setRules(condi.getRuleIds());
        List otherShowColumnNames = condi.getOtherShowColumnNames();
        if (!CollectionUtils.isEmpty(otherShowColumnNames)) {
            paramsVO.setOtherShowColumns(otherShowColumnNames);
        }
        return paramsVO;
    }

    @Override
    public Pagination<DataTraceCheckInfoDTO> dataTraceOffsetCheckList(GcDataTraceCondi condi) {
        QueryParamsVO queryParamsVO;
        List rules = this.unionRuleService.selectUnionRuleDTOByIdList((Collection)condi.getRuleIds());
        if (!CollectionUtils.isEmpty(rules)) {
            List ruleIdsFilterInitType = rules.stream().filter(rule -> {
                Boolean initTypeFlag = rule.getInitTypeFlag();
                return !Boolean.TRUE.equals(initTypeFlag);
            }).map(AbstractUnionRule::getId).collect(Collectors.toList());
            condi.setRuleIds(ruleIdsFilterInitType);
        }
        if (CollectionUtils.isEmpty((queryParamsVO = this.buildQueryParamsByDataTraceCondi(condi)).getRules())) {
            throw new BusinessRuntimeException("\u8be5\u4efb\u52a1\u6240\u5c5e\u4f53\u7cfb\u4e0b\u65e0\u89c4\u5219");
        }
        List unionRules = this.unionRuleService.selectUnionRuleDTOByIdList((Collection)queryParamsVO.getRules());
        if (CollectionUtils.isEmpty(unionRules)) {
            return new Pagination(Collections.emptyList(), Integer.valueOf(0), condi.getPageNum(), condi.getPageSize());
        }
        Map ruleIdCode2DataMap = unionRules.stream().collect(Collectors.toMap(AbstractUnionRule::getId, vo -> vo, (v1, v2) -> v1, LinkedHashMap::new));
        List subjectVOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(queryParamsVO.getSystemId());
        Map subjectCode2DataMap = subjectVOS.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, vo -> vo, (v1, v2) -> v1, LinkedHashMap::new));
        Map<String, List<DataTraceCheckInfoDTO>> ruleId2dataTraceCheckInfoDTOsMap = this.buildBlankDataTraceDTOs(unionRules, subjectCode2DataMap, queryParamsVO.getRules());
        ArrayList<String> srcGroupIds = new ArrayList<String>();
        srcGroupIds.add(condi.getSrcId());
        this.applicationEventPublisher.publishEvent((ApplicationEvent)new GcDataTraceRebuildSrcGroupIdsQueryParamsEvent((Object)this, new GcDataTraceRebuildSrcGroupIdsQueryParamsEvent.QueryParamsInfo(condi, srcGroupIds)));
        Pagination<Map<String, Object>> offsetPage = this.getOffsetEntryBySrcOffsetGroupIds(queryParamsVO, new HashSet<String>(srcGroupIds));
        offsetPage.getContent().stream().forEach(originOffsetData -> {
            String ruleid = ConverterUtils.getAsString(originOffsetData.get("RULEID"));
            String subjcetCode = ConverterUtils.getAsString(originOffsetData.get("SUBJECTCODE"));
            List ruleDataTraceCheckInfoDTOS = (List)ruleId2dataTraceCheckInfoDTOsMap.get(ruleid);
            DataTraceCheckInfoDTO dataTraceCheckInfoDTO = this.getDataTraceCheckInfoDTOByRuleIdAndSubject(ruleDataTraceCheckInfoDTOS, subjcetCode);
            if (dataTraceCheckInfoDTO == null) {
                dataTraceCheckInfoDTO = this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)ruleIdCode2DataMap.get(ruleid), subjcetCode, null);
                if (dataTraceCheckInfoDTO == null) {
                    return;
                }
                ruleDataTraceCheckInfoDTOS.add(dataTraceCheckInfoDTO);
            }
            BigDecimal offsetDebit = ConverterUtils.getAsBigDecimal(originOffsetData.get("OFFSETDEBIT"), (BigDecimal)BigDecimal.ZERO);
            BigDecimal offsetCredit = ConverterUtils.getAsBigDecimal(originOffsetData.get("OFFSETCREDIT"), (BigDecimal)BigDecimal.ZERO);
            BigDecimal diff = ConverterUtils.getAsBigDecimal(originOffsetData.get("DIFF"), (BigDecimal)BigDecimal.ZERO);
            dataTraceCheckInfoDTO.getOriginItems().add(new DataTraceCheckInfoDTO.OriginItem(offsetDebit, offsetCredit, diff));
        });
        List preCalcOffSetItems = this.gcCaculateOffsetItemService.getPreCalcOffSetItems(queryParamsVO);
        HashSet allUnitIds = new HashSet();
        allUnitIds.addAll(queryParamsVO.getUnitIdList());
        allUnitIds.addAll(queryParamsVO.getOppUnitIdList());
        if (!CollectionUtils.isEmpty(preCalcOffSetItems)) {
            preCalcOffSetItems.stream().forEach(preCalcOffSetItem -> {
                String ruleid = ConverterUtils.getAsString((Object)preCalcOffSetItem.getRuleId());
                String subjcetCode = ConverterUtils.getAsString((Object)preCalcOffSetItem.getSubjectCode());
                String unitId = ConverterUtils.getAsString((Object)preCalcOffSetItem.getUnitId(), (String)"");
                String oppUnitId = ConverterUtils.getAsString((Object)preCalcOffSetItem.getOppUnitId(), (String)"");
                String srcOffsetGroupId = ConverterUtils.getAsString((Object)preCalcOffSetItem.getSrcOffsetGroupId(), (String)"");
                if (!CollectionUtils.isEmpty(allUnitIds) && !allUnitIds.contains(unitId)) {
                    return;
                }
                if (!CollectionUtils.isEmpty(allUnitIds) && !allUnitIds.contains(oppUnitId)) {
                    return;
                }
                if (CollectionUtils.isEmpty(srcGroupIds) || srcGroupIds.indexOf(srcOffsetGroupId) == -1) {
                    return;
                }
                List ruleDataTraceCheckInfoDTOS = (List)ruleId2dataTraceCheckInfoDTOsMap.get(ruleid);
                DataTraceCheckInfoDTO dataTraceCheckInfoDTO = this.getDataTraceCheckInfoDTOByRuleIdAndSubject(ruleDataTraceCheckInfoDTOS, subjcetCode);
                if (dataTraceCheckInfoDTO == null) {
                    dataTraceCheckInfoDTO = this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)ruleIdCode2DataMap.get(ruleid), subjcetCode, null);
                    if (dataTraceCheckInfoDTO == null) {
                        return;
                    }
                    ruleDataTraceCheckInfoDTOS.add(dataTraceCheckInfoDTO);
                }
                BigDecimal checkOffsetDebit = ConverterUtils.getAsBigDecimal((Object)preCalcOffSetItem.getOffSetDebit(), (BigDecimal)BigDecimal.ZERO);
                BigDecimal checkOffsetCredit = ConverterUtils.getAsBigDecimal((Object)preCalcOffSetItem.getOffSetCredit(), (BigDecimal)BigDecimal.ZERO);
                dataTraceCheckInfoDTO.getCheckItems().add(new DataTraceCheckInfoDTO.CheckItem(checkOffsetDebit, checkOffsetCredit));
            });
        }
        ArrayList filterDataTraceCheckInfoDTOs = new ArrayList();
        ruleId2dataTraceCheckInfoDTOsMap.forEach((ruleId, dataTraceCheckInfoDTOS) -> {
            List filterRuleDataTraceCheckInfoDTOs = dataTraceCheckInfoDTOS.stream().filter(dataTraceCheckInfoDTO -> {
                List originItems = dataTraceCheckInfoDTO.getOriginItems();
                List checkItems = dataTraceCheckInfoDTO.getCheckItems();
                if (checkItems.size() == 0 && originItems.size() == 0) {
                    return false;
                }
                StringBuilder offsetDebitInfoBuilder = new StringBuilder();
                StringBuilder offsetCreditInfoBuilder = new StringBuilder();
                StringBuilder diffInfoBuilder = new StringBuilder();
                StringBuilder checkOffsetDebitInfoBuilder = new StringBuilder();
                StringBuilder checkOffsetCreditInfoBuilder = new StringBuilder();
                boolean isOffsetDebitAllZero = true;
                boolean isOffsetCreditAllZero = true;
                if (originItems.size() == 1) {
                    DataTraceCheckInfoDTO.OriginItem originItem = (DataTraceCheckInfoDTO.OriginItem)originItems.get(0);
                    offsetDebitInfoBuilder.append(OffsetAmtTraceServiceImpl.formatBigDecimal(originItem.getOffsetDebit()));
                    offsetCreditInfoBuilder.append(OffsetAmtTraceServiceImpl.formatBigDecimal(originItem.getOffsetCredit()));
                    diffInfoBuilder.append(OffsetAmtTraceServiceImpl.formatBigDecimal(originItem.getDiff()));
                    if (BigDecimal.ZERO.compareTo(originItem.getOffsetDebit()) != 0) {
                        isOffsetDebitAllZero = false;
                    }
                    if (BigDecimal.ZERO.compareTo(originItem.getOffsetCredit()) != 0) {
                        isOffsetCreditAllZero = false;
                    }
                } else {
                    for (int i = 0; i < originItems.size(); ++i) {
                        DataTraceCheckInfoDTO.OriginItem originItem = (DataTraceCheckInfoDTO.OriginItem)originItems.get(i);
                        offsetDebitInfoBuilder.append("\u91d1\u989d").append(i + 1).append("\uff1a").append(OffsetAmtTraceServiceImpl.formatBigDecimal(originItem.getOffsetDebit())).append("\n");
                        offsetCreditInfoBuilder.append("\u91d1\u989d").append(i + 1).append("\uff1a").append(OffsetAmtTraceServiceImpl.formatBigDecimal(originItem.getOffsetCredit())).append("\n");
                        diffInfoBuilder.append("\u91d1\u989d").append(i + 1).append("\uff1a").append(OffsetAmtTraceServiceImpl.formatBigDecimal(originItem.getDiff())).append("\n");
                        if (BigDecimal.ZERO.compareTo(originItem.getOffsetDebit()) != 0) {
                            isOffsetDebitAllZero = false;
                        }
                        if (BigDecimal.ZERO.compareTo(originItem.getOffsetCredit()) == 0) continue;
                        isOffsetCreditAllZero = false;
                    }
                }
                if (isOffsetDebitAllZero) {
                    offsetDebitInfoBuilder = offsetDebitInfoBuilder.delete(0, offsetDebitInfoBuilder.length());
                }
                if (isOffsetCreditAllZero) {
                    offsetCreditInfoBuilder = offsetCreditInfoBuilder.delete(0, offsetCreditInfoBuilder.length());
                }
                boolean isCheckOffsetDebitAllZero = true;
                boolean isCheckOffsetCreditAllZero = true;
                if (checkItems.size() == 1) {
                    DataTraceCheckInfoDTO.CheckItem checkItem = (DataTraceCheckInfoDTO.CheckItem)checkItems.get(0);
                    checkOffsetDebitInfoBuilder.append(OffsetAmtTraceServiceImpl.formatBigDecimal(checkItem.getCheckOffsetDebit()));
                    checkOffsetCreditInfoBuilder.append(OffsetAmtTraceServiceImpl.formatBigDecimal(checkItem.getCheckOffsetCredit()));
                    if (BigDecimal.ZERO.compareTo(checkItem.getCheckOffsetDebit()) != 0) {
                        isCheckOffsetDebitAllZero = false;
                    }
                    if (BigDecimal.ZERO.compareTo(checkItem.getCheckOffsetCredit()) != 0) {
                        isCheckOffsetCreditAllZero = false;
                    }
                } else {
                    for (int i = 0; i < checkItems.size(); ++i) {
                        DataTraceCheckInfoDTO.CheckItem checkItem = (DataTraceCheckInfoDTO.CheckItem)checkItems.get(i);
                        checkOffsetDebitInfoBuilder.append("\u91d1\u989d").append(i + 1).append("\uff1a").append(OffsetAmtTraceServiceImpl.formatBigDecimal(checkItem.getCheckOffsetDebit())).append("\n");
                        checkOffsetCreditInfoBuilder.append("\u91d1\u989d").append(i + 1).append("\uff1a").append(OffsetAmtTraceServiceImpl.formatBigDecimal(checkItem.getCheckOffsetCredit())).append("\n");
                        if (BigDecimal.ZERO.compareTo(checkItem.getCheckOffsetDebit()) != 0) {
                            isCheckOffsetDebitAllZero = false;
                        }
                        if (BigDecimal.ZERO.compareTo(checkItem.getCheckOffsetCredit()) == 0) continue;
                        isCheckOffsetCreditAllZero = false;
                    }
                }
                if (isCheckOffsetDebitAllZero) {
                    checkOffsetDebitInfoBuilder = checkOffsetDebitInfoBuilder.delete(0, checkOffsetDebitInfoBuilder.length());
                }
                if (isCheckOffsetCreditAllZero) {
                    checkOffsetCreditInfoBuilder = checkOffsetCreditInfoBuilder.delete(0, checkOffsetCreditInfoBuilder.length());
                }
                String offsetCreditInfo = offsetCreditInfoBuilder.toString();
                String offsetDebitInfo = offsetDebitInfoBuilder.toString();
                String checkOffsetCreditInfo = checkOffsetCreditInfoBuilder.toString();
                String checkOffsetDebitInfo = checkOffsetDebitInfoBuilder.toString();
                dataTraceCheckInfoDTO.setOffsetCreditInfo(offsetCreditInfo);
                dataTraceCheckInfoDTO.setOffsetDebitInfo(offsetDebitInfo);
                dataTraceCheckInfoDTO.setCheckOffsetCreditInfo(checkOffsetCreditInfo);
                dataTraceCheckInfoDTO.setCheckOffsetDebitInfo(checkOffsetDebitInfo);
                dataTraceCheckInfoDTO.setDiffInfo(diffInfoBuilder.toString());
                String checkInfo = offsetCreditInfo.equals(checkOffsetCreditInfo) && offsetDebitInfo.equals(checkOffsetDebitInfo) ? "\u68c0\u67e5\u4e00\u81f4" : "\u68c0\u67e5\u4e0d\u4e00\u81f4\uff1a\u91d1\u989d\u4e0d\u76f8\u7b49";
                if (originItems.size() == 0 && checkItems.size() > 0) {
                    checkInfo = "\u672a\u6267\u884c\u5408\u5e76\u8ba1\u7b97";
                }
                if (originItems.size() > 0 && checkItems.size() == 0) {
                    checkInfo = "\u53f0\u8d26\u6216\u5408\u5e76\u89c4\u5219\u53d1\u751f\u53d8\u5316\uff0c\u53f0\u8d26\u6570\u636e\u4e0d\u7b26\u5408\u5408\u5e76\u89c4\u5219";
                }
                dataTraceCheckInfoDTO.setCheckInfo(checkInfo);
                return true;
            }).collect(Collectors.toList());
            List sortedData = filterRuleDataTraceCheckInfoDTOs.stream().sorted(Comparator.comparing(dto -> StringUtils.isEmpty((String)dto.getOffsetDebitInfo())).thenComparing(dto -> OffsetAmtTraceServiceImpl.parseDouble(dto.getOffsetDebitInfo())).thenComparing(dto -> OffsetAmtTraceServiceImpl.parseDouble(dto.getOffsetCreditInfo()))).collect(Collectors.toList());
            filterDataTraceCheckInfoDTOs.addAll(sortedData);
        });
        return new Pagination(filterDataTraceCheckInfoDTOs, Integer.valueOf(filterDataTraceCheckInfoDTOs.size()), condi.getPageNum(), condi.getPageSize());
    }

    private static double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Double.MAX_VALUE;
        }
        try {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            return Double.MAX_VALUE;
        }
    }

    private Map<String, List<DataTraceCheckInfoDTO>> buildBlankDataTraceDTOs(List<AbstractUnionRule> unionRules, Map<String, ConsolidatedSubjectEO> subjectCode2DataMap, Collection<String> ruleIds) {
        LinkedHashMap<String, List<DataTraceCheckInfoDTO>> ruleId2dataTraceCheckInfoDTOsMap = new LinkedHashMap<String, List<DataTraceCheckInfoDTO>>();
        unionRules.stream().forEach(rule -> {
            ArrayList dataTraceCheckInfoDTOS = new ArrayList();
            switch (rule.getRuleType()) {
                case "FIXED_TABLE": {
                    FixedTableRuleDTO fixedTableRuleDTO = (FixedTableRuleDTO)rule;
                    List fixedTableDebitItemList = fixedTableRuleDTO.getDebitItemList();
                    List fixedTableCreditItemList = fixedTableRuleDTO.getCreditItemList();
                    fixedTableDebitItemList.stream().forEach(debitItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitItem.getSubjectCode(), debitItem.getFetchFormula())));
                    fixedTableCreditItemList.stream().forEach(creditItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, creditItem.getSubjectCode(), creditItem.getFetchFormula())));
                    break;
                }
                case "FIXED_ASSETS": {
                    FixedAssetsRuleDTO fixedAssetsRuleDTO = (FixedAssetsRuleDTO)rule;
                    List fixedAssetsDebitItemList = fixedAssetsRuleDTO.getDebitItemList();
                    List fixedAssetsCreditItemList = fixedAssetsRuleDTO.getCreditItemList();
                    fixedAssetsDebitItemList.stream().forEach(debitItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitItem.getSubjectCode(), debitItem.getFetchFormula())));
                    fixedAssetsCreditItemList.stream().forEach(creditItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, creditItem.getSubjectCode(), creditItem.getFetchFormula())));
                    break;
                }
                case "INVENTORY": {
                    InventoryRuleDTO inventoryRuleDTO = (InventoryRuleDTO)rule;
                    List inventoryDebitItemList = inventoryRuleDTO.getDebitItemList();
                    List inventoryCreditItemList = inventoryRuleDTO.getCreditItemList();
                    inventoryDebitItemList.stream().forEach(debitItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitItem.getSubjectCode(), debitItem.getFetchFormula())));
                    inventoryCreditItemList.stream().forEach(creditItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, creditItem.getSubjectCode(), creditItem.getFetchFormula())));
                    break;
                }
                case "DIRECT_INVESTMENT": 
                case "DIRECT_INVESTMENT_SEGMENT": 
                case "INDIRECT_INVESTMENT": 
                case "INDIRECT_INVESTMENT_SEGMENT": {
                    AbstractInvestmentRule investmentRule = (AbstractInvestmentRule)rule;
                    List investmentDebitItemList = investmentRule.getDebitItemList();
                    List investmentCreditItemList = investmentRule.getCreditItemList();
                    investmentDebitItemList.stream().forEach(debitItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitItem.getSubjectCode(), debitItem.getFetchFormula())));
                    investmentCreditItemList.stream().forEach(creditItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, creditItem.getSubjectCode(), creditItem.getFetchFormula())));
                    break;
                }
                case "PUBLIC_VALUE_ADJUSTMENT": {
                    PublicValueAdjustmentRuleDTO adjustmentRuleDTO = (PublicValueAdjustmentRuleDTO)rule;
                    List adjustmentRuleDebitItemList = adjustmentRuleDTO.getDebitItemList();
                    List adjustmentRuleCreditItemList = adjustmentRuleDTO.getCreditItemList();
                    adjustmentRuleDebitItemList.stream().forEach(debitItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitItem.getSubjectCode(), debitItem.getFetchFormula())));
                    adjustmentRuleCreditItemList.stream().forEach(creditItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, creditItem.getSubjectCode(), creditItem.getFetchFormula())));
                    break;
                }
                case "FLOAT_LINE": {
                    FloatLineRuleDTO floatLineRuleDTO = (FloatLineRuleDTO)rule;
                    List floatLineRuleDebitItemList = floatLineRuleDTO.getDebitItemList();
                    List floatLineRuleCreditItemList = floatLineRuleDTO.getCreditItemList();
                    floatLineRuleDebitItemList.stream().forEach(debitItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitItem.getSubject(), "")));
                    floatLineRuleCreditItemList.stream().forEach(creditItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, creditItem.getSubject(), "")));
                    break;
                }
                case "LEASE": {
                    LeaseRuleDTO leaseRuleDTO = (LeaseRuleDTO)rule;
                    List debitItemList = leaseRuleDTO.getDebitItemList();
                    List creditItemList = leaseRuleDTO.getCreditItemList();
                    debitItemList.stream().forEach(debitItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitItem.getSubjectCode(), debitItem.getFetchFormula())));
                    creditItemList.stream().forEach(creditItem -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, creditItem.getSubjectCode(), creditItem.getFetchFormula())));
                    break;
                }
                case "FLEXIBLE": 
                case "RELATE_TRANSACTIONS": {
                    FlexibleRuleDTO flexibleRuleDTO = (FlexibleRuleDTO)rule;
                    List fetchConfigList = flexibleRuleDTO.getFetchConfigList();
                    if (CollectionUtils.isEmpty(fetchConfigList)) break;
                    fetchConfigList.stream().forEach(fetchConfig -> {
                        List debitConfigList = fetchConfig.getDebitConfigList();
                        List crebitConfigList = fetchConfig.getCreditConfigList();
                        if (!CollectionUtils.isEmpty(debitConfigList)) {
                            debitConfigList.stream().forEach(debitConfig -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitConfig.getSubjectCode(), debitConfig.getFetchFormula())));
                        }
                        if (!CollectionUtils.isEmpty(crebitConfigList)) {
                            crebitConfigList.stream().forEach(crebitConfig -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, crebitConfig.getSubjectCode(), crebitConfig.getFetchFormula())));
                        }
                    });
                    break;
                }
                case "FINANCIAL_CHECK": {
                    FinancialCheckRuleDTO financialCheckRuleDTO = (FinancialCheckRuleDTO)rule;
                    List fetchConfigList1 = financialCheckRuleDTO.getFetchConfigList();
                    if (CollectionUtils.isEmpty(fetchConfigList1)) break;
                    fetchConfigList1.stream().forEach(fetchConfig -> {
                        List debitConfigList = fetchConfig.getDebitConfigList();
                        List crebitConfigList = fetchConfig.getCreditConfigList();
                        if (!CollectionUtils.isEmpty(debitConfigList)) {
                            debitConfigList.stream().forEach(debitConfig -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, debitConfig.getSubjectCode(), debitConfig.getFetchFormula())));
                        }
                        if (!CollectionUtils.isEmpty(crebitConfigList)) {
                            crebitConfigList.stream().forEach(crebitConfig -> dataTraceCheckInfoDTOS.add(this.buildBlankDataTraceDTO(subjectCode2DataMap, (AbstractUnionRule)rule, crebitConfig.getSubjectCode(), crebitConfig.getFetchFormula())));
                        }
                    });
                    break;
                }
                default: {
                    throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u89c4\u5219\u7c7b\u578b");
                }
            }
            List ruleDataTraceCheckInfoDTOs = dataTraceCheckInfoDTOS.stream().filter(Objects::nonNull).collect(Collectors.toList());
            ruleId2dataTraceCheckInfoDTOsMap.put(rule.getId(), ruleDataTraceCheckInfoDTOs);
        });
        return ruleId2dataTraceCheckInfoDTOsMap;
    }

    private DataTraceCheckInfoDTO buildBlankDataTraceDTO(Map<String, ConsolidatedSubjectEO> subjectCode2DataMap, AbstractUnionRule rule, String subjectCode, String fetchFormula) {
        if (StringUtils.isEmpty((String)subjectCode) || rule == null) {
            return null;
        }
        DataTraceCheckInfoDTO dataTraceCheckInfoDTO = new DataTraceCheckInfoDTO(rule.getId(), subjectCode, fetchFormula);
        dataTraceCheckInfoDTO.setRuleTitle(rule.getLocalizedName());
        ConsolidatedSubjectEO consolidatedSubjectEO = subjectCode2DataMap.get(subjectCode);
        if (consolidatedSubjectEO != null) {
            dataTraceCheckInfoDTO.setSubjectTitle(consolidatedSubjectEO.getTitle());
        }
        return dataTraceCheckInfoDTO;
    }

    private DataTraceCheckInfoDTO getDataTraceCheckInfoDTOByRuleIdAndSubject(List<DataTraceCheckInfoDTO> dataTraceCheckInfoDTOS, String subjectCode) {
        Optional<DataTraceCheckInfoDTO> dataTraceCheckInfoDTOOptional = dataTraceCheckInfoDTOS.stream().filter(dataTraceCheckInfoDTO -> dataTraceCheckInfoDTO.getSubjectCode().equals(subjectCode)).findAny();
        if (!dataTraceCheckInfoDTOOptional.isPresent()) {
            return null;
        }
        return dataTraceCheckInfoDTOOptional.get();
    }

    @Override
    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTraceOffsetItemCondi condi) {
        GcOffSetVchrItemDTO gcOffSetVchrItemDTO = this.offsetCoreService.getGcOffSetVchrItemDTO(condi.getOffsetItemId());
        if (gcOffSetVchrItemDTO == null) {
            return null;
        }
        GcDataTracerContext gcDataTracerContext = new GcDataTracerContext();
        gcDataTracerContext.setGcOffSetVchrItemDTO(gcOffSetVchrItemDTO);
        gcDataTracerContext.setCondition(condi.getCondition());
        GcDataTraceCondi gcDataTraceCondi = this.gcDataTraceService.queryGcDataTraceCondi(gcDataTracerContext);
        return gcDataTraceCondi;
    }

    public Pagination<Map<String, Object>> getOffsetEntryBySrcOffsetGroupIds(QueryParamsVO queryParamsVO, Set<String> srcOffsetGroupIds) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        Pagination pagination = new Pagination(this.offsetCoreService.listWithFullGroupBySrcOffsetGroupIdsAndSystemId(queryParamsDTO, srcOffsetGroupIds), Integer.valueOf(0), Integer.valueOf(queryParamsVO.getPageNum()), Integer.valueOf(queryParamsVO.getPageSize()));
        return this.offSetItemAdjustService.assembleOffsetEntry(pagination, queryParamsVO);
    }

    @Override
    public Map<String, Object> getCurrentPeriodStrByTaskId(String taskId, String periodStr) {
        TaskDefine taskDefine = this.taskService.queryTaskDefine(taskId);
        HashMap<String, Object> periodInfo = new HashMap<String, Object>();
        if (!StringUtils.isEmpty((String)periodStr)) {
            PeriodWrapper periodWrapper = PeriodUtil.currentPeriod((GregorianCalendar)PeriodUtil.period2Calendar((String)periodStr), (int)taskDefine.getPeriodType().type(), (int)taskDefine.getTaskPeriodOffset());
            String periodStrByTask = periodWrapper.toString();
            DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
            String periodTitleByTask = defaultPeriodAdapter.getPeriodTitle(periodWrapper);
            periodInfo.put("periodStr", periodStrByTask);
            periodInfo.put("periodTitle", periodTitleByTask);
        }
        return periodInfo;
    }
}

