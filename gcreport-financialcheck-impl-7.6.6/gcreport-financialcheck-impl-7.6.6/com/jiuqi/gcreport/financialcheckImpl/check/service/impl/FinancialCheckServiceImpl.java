/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.json.JsonMapper
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.gc.financial.status.enums.FinancialStatusEnum
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.common.util.YearPeriodRangeUtils
 *  com.jiuqi.gcreport.common.util.YearPeriodRangeUtils$PeriodRangeType
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckIniDataVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialClbrCodeInfoVO
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.check.vo.UnitCheckParam
 *  com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckShowTypeEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckExecutor
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemUnCheckDescDao
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemUnCheckDescEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.util.LogHelper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.jiuqi.gcreport.financialcheckImpl.check.service.impl;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.common.util.YearPeriodRangeUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.financialcheckImpl.check.FinancialCheckQueryEO2VOHelper;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.AutoCheckProcessDataImpl;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProvider;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckDataProviderImpl;
import com.jiuqi.gcreport.financialcheckImpl.check.checkutil.FinancialCheckUtils;
import com.jiuqi.gcreport.financialcheckImpl.check.consts.TransferConst;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.FinancialCheckImplDao;
import com.jiuqi.gcreport.financialcheckImpl.check.service.FinancialCheckClbrCodeExtendService;
import com.jiuqi.gcreport.financialcheckImpl.check.service.FinancialCheckService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.param.RealTimeCheckOrOffsetParam;
import com.jiuqi.gcreport.financialcheckImpl.util.BillUtils;
import com.jiuqi.gcreport.financialcheckImpl.util.UnitStateUtils;
import com.jiuqi.gcreport.financialcheckapi.check.vo.CheckResult;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckIniDataVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryAmtSumVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryConditionVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialCheckQueryVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.FinancialClbrCodeInfoVO;
import com.jiuqi.gcreport.financialcheckapi.check.vo.ManualCheckParam;
import com.jiuqi.gcreport.financialcheckapi.check.vo.UnitCheckParam;
import com.jiuqi.gcreport.financialcheckapi.common.vo.TableColumnVO;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckShowTypeEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.gather.GcFinancialCheckExecutor;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemUnCheckDescDao;
import com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemUnCheckDescEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.scheme.enums.CheckModeEnum;
import com.jiuqi.gcreport.financialcheckcore.utils.OrgUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.util.LogHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FinancialCheckServiceImpl
implements FinancialCheckService {
    @Autowired
    private GcRelatedItemDao voucherItemDao;
    @Autowired
    private FinancialCheckImplDao financialCheckImplDao;
    @Autowired
    private ProgressService<AutoCheckProcessDataImpl, String> progressService;
    @Autowired
    private GcDbLockService iLockService;
    @Autowired
    private GcFinancialCheckExecutor gcFinancialCheckExecutor;
    @Autowired
    private GcRelatedItemUnCheckDescDao voucherItemUnCheckDescDao;
    @Autowired
    private GcRelatedItemQueryService relatedItemQueryService;
    @Autowired
    private FinancialCheckSchemeService schemeService;
    @Autowired
    private GcRelatedItemCommandService relatedItemCommandService;
    @Autowired
    private FinancialCheckSchemeDao schemeDao;
    @Autowired
    TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private DataModelService dataModelService;
    @Autowired(required=false)
    private FinancialCheckClbrCodeExtendService financialCheckClbrCodeExtendService;

    @Override
    public FinancialCheckIniDataVO getIniData() {
        FinancialCheckIniDataVO iniVo = new FinancialCheckIniDataVO();
        Calendar loginDateCal = Calendar.getInstance();
        iniVo.setLoginYear(Integer.valueOf(DateUtils.getDateFieldValue((Calendar)loginDateCal, (int)1)));
        iniVo.setLoginMonth(Integer.valueOf(DateUtils.getDateFieldValue((Calendar)loginDateCal, (int)2)));
        iniVo.setAcctYearRange(YearPeriodRangeUtils.getYearRange((int)5, (int)5));
        iniVo.setAcctPeriodRange(YearPeriodRangeUtils.getPeriodRange((YearPeriodRangeUtils.PeriodRangeType)YearPeriodRangeUtils.PeriodRangeType.ZEROTOTWELVE));
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS);
        List gcOrgCacheVOS = instance.listOrgBySearch(null);
        if (!CollectionUtils.isEmpty(gcOrgCacheVOS)) {
            iniVo.setLocalUnit((GcOrgCacheVO)gcOrgCacheVOS.get(0));
        }
        List<ColumnModelDefine> extendFields = this.getExtendFields();
        List<DimensionVO> dimensionVOS = this.convertDimVO(extendFields);
        iniVo.setDimensionVOS(dimensionVOS);
        List dimensionColumns = extendFields.stream().map(columnModelDefine -> {
            TableColumnVO tableColumnVO = new TableColumnVO(columnModelDefine.getCode(), columnModelDefine.getTitle(), "left", Integer.valueOf(150));
            if (ColumnModelType.DOUBLE.equals((Object)columnModelDefine.getColumnType()) || ColumnModelType.INTEGER.equals((Object)columnModelDefine.getColumnType()) || ColumnModelType.BIGDECIMAL.equals((Object)columnModelDefine.getColumnType())) {
                tableColumnVO.setAlign("right");
                tableColumnVO.setColumnType(TransferConst.TypeEnum.NUMBER.name());
            }
            return tableColumnVO;
        }).collect(Collectors.toList());
        TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode("GC_RELATED_ITEM", OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource"));
        List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(tableModelDefineByCode.getID());
        Map<String, String> colName2TitleMap = columnModelDefinesByTable.stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getTitle));
        ArrayList<TableColumnVO> allCheckedColumnVOS = new ArrayList<TableColumnVO>(TransferConst.allCheckedColumnVOS);
        allCheckedColumnVOS.forEach(item -> {
            String title = (String)colName2TitleMap.get(item.getKey().toUpperCase(Locale.ROOT));
            if (StringUtils.hasText(title)) {
                item.setLabel(title);
            }
        });
        allCheckedColumnVOS.addAll(dimensionColumns);
        iniVo.setAllCheckedColumns(allCheckedColumnVOS);
        ArrayList<TableColumnVO> allUncheckedColumnVOS = new ArrayList<TableColumnVO>(TransferConst.allUncheckedColumnVOS);
        allUncheckedColumnVOS.forEach(item -> {
            String title = (String)colName2TitleMap.get(item.getKey().toUpperCase(Locale.ROOT));
            if (StringUtils.hasText(title)) {
                item.setLabel(title);
            }
        });
        allUncheckedColumnVOS.addAll(dimensionColumns);
        iniVo.setAllUncheckedColumns(allUncheckedColumnVOS);
        iniVo.setConfig(FinancialCheckConfigUtils.getCheckConfig());
        iniVo.setShowWayList(this.gcFinancialCheckExecutor.listShowTypeForPage());
        return iniVo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CheckResult autoCheck(FinancialCheckQueryConditionVO condition) {
        AutoCheckProcessDataImpl checkProgress = new AutoCheckProcessDataImpl(condition.getSn());
        this.progressService.createProgressData((ProgressData)checkProgress);
        checkProgress.setResult("");
        if (!this.checkCondition(condition)) {
            checkProgress.setProgressValueAndRefresh(1.0);
            return new CheckResult();
        }
        checkProgress.setProgressValueAndRefresh(0.0);
        CheckResult checkResultAll = new CheckResult();
        try {
            List<GcRelatedItemEO> allUncheckedItems = this.getAutoCheckData(condition);
            List allUncheckId = allUncheckedItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
            ArrayList<GcRelatedItemEO> allItems = new ArrayList<GcRelatedItemEO>();
            ArrayList<GcRelatedItemEO> needMatchSchemeItems = new ArrayList<GcRelatedItemEO>();
            allUncheckedItems.forEach(item -> {
                if (StringUtils.hasText(item.getCheckRuleId())) {
                    allItems.add((GcRelatedItemEO)item);
                } else {
                    needMatchSchemeItems.add((GcRelatedItemEO)item);
                }
            });
            this.matchScheme(condition.getAcctYear(), needMatchSchemeItems);
            List<GcRelatedItemEO> schemeChangedItems = needMatchSchemeItems.stream().filter(item -> StringUtils.hasText(item.getCheckRuleId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(schemeChangedItems)) {
                allItems.addAll(schemeChangedItems);
            }
            FinancialCheckDataProviderImpl provider = new FinancialCheckDataProviderImpl();
            provider.setAcctYear(condition.getAcctYear());
            provider.setAcctPeriod(condition.getAcctPeriod());
            provider.setVoucherItems(allItems);
            provider.setCheckProgress((ProgressData<String>)checkProgress);
            provider.setCheckPeriodBaseVoucherPeriod(true);
            if (provider != null) {
                CheckResult checkResult = null;
                try {
                    checkResult = FinancialCheckUtils.autoCheck(provider);
                    checkResultAll.addCheckAllCount(checkResult);
                    List checkedItemIds = checkResult.getCheckedItemIds();
                    if (!CollectionUtils.isEmpty(schemeChangedItems)) {
                        GcRelatedItemEO firstItem = (GcRelatedItemEO)schemeChangedItems.get(0);
                        PeriodWrapper periodWrapper = new PeriodWrapper(firstItem.getAcctYear().intValue(), 4, firstItem.getAcctPeriod().intValue());
                        this.updateSchemeInfoAfterCheck(checkedItemIds, schemeChangedItems, periodWrapper.toString());
                    }
                }
                catch (BusinessRuntimeException e) {
                    checkResultAll.addCheckAllCount(checkResult);
                    checkResultAll.setErrMsg(e.getMessage());
                }
            }
            checkResultAll.setCheckResult(this.disposeCheckResult(this.relatedItemQueryService.queryByIds(allUncheckId)));
            checkProgress.setProgressValueAndRefresh(1.0);
        }
        finally {
            JsonMapper mapper = new JsonMapper();
            ObjectNode node = mapper.createObjectNode();
            node.put("CheckedGroupCount", checkResultAll.getCheckedGroupCount());
            node.put("CheckedItemCount", checkResultAll.getCheckedItemCount());
            checkProgress.setResult(node.toString());
            checkProgress.setProgressValueAndRefresh(1.0);
        }
        return checkResultAll;
    }

    private List<Map<String, Object>> disposeCheckResult(List<GcRelatedItemEO> items) {
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isEmpty(items)) {
            return result;
        }
        Map<Boolean, List<GcRelatedItemEO>> itemGroup = items.stream().collect(Collectors.partitioningBy(item -> Objects.nonNull(item.getCheckId())));
        itemGroup.forEach((isChecked, list) -> result.add(this.disposeCheckResult((List<GcRelatedItemEO>)list, (boolean)isChecked)));
        return result;
    }

    private Map<String, Object> disposeCheckResult(List<GcRelatedItemEO> items, boolean isChecked) {
        HashMap<String, Object> result = new HashMap<String, Object>(4);
        if (isChecked) {
            result.put("title", "\u5df2\u5bf9\u8d26");
        } else {
            result.put("title", "\u672a\u5bf9\u8d26");
        }
        Double debitSum = 0.0;
        Double creditSum = 0.0;
        for (GcRelatedItemEO item2 : items) {
            debitSum = NumberUtils.sum((Double)debitSum, (Double)(isChecked ? item2.getChkAmtD() : item2.getDebitOrig()));
            creditSum = NumberUtils.sum((Double)creditSum, (Double)(isChecked ? item2.getChkAmtC() : item2.getCreditOrig()));
        }
        result.put("debitSum", debitSum);
        result.put("creditSum", creditSum);
        ArrayList schemeItems = new ArrayList();
        Map<String, List<GcRelatedItemEO>> schemeGroup = items.stream().collect(Collectors.groupingBy(item -> item.getCheckRuleId() != null ? item.getCheckRuleId() : "NULL"));
        schemeGroup.forEach((schemeId, list) -> schemeItems.add(this.disposeCheckResult((List<GcRelatedItemEO>)list, (String)schemeId, isChecked)));
        result.put("schemes", schemeItems);
        return result;
    }

    private Map<String, Object> disposeCheckResult(List<GcRelatedItemEO> items, String schemeId, boolean isChecked) {
        HashMap<String, Object> result = new HashMap<String, Object>(4);
        if (Objects.equals(schemeId, "NULL")) {
            result.put("schemeTitle", "\u65e0\u65b9\u6848");
        } else {
            FinancialCheckSchemeEO scheme = this.schemeService.queryById(schemeId);
            result.put("schemeTitle", Objects.isNull(scheme) ? schemeId : scheme.getSchemeName());
        }
        Double debitSum = 0.0;
        Double creditSum = 0.0;
        result.put("count", items.size());
        for (GcRelatedItemEO item : items) {
            debitSum = NumberUtils.sum((Double)debitSum, (Double)(isChecked ? item.getChkAmtD() : item.getDebitOrig()));
            creditSum = NumberUtils.sum((Double)creditSum, (Double)(isChecked ? item.getChkAmtC() : item.getCreditOrig()));
        }
        result.put("debitSum", debitSum);
        result.put("creditSum", creditSum);
        return result;
    }

    private boolean checkCondition(FinancialCheckQueryConditionVO condition) {
        return condition.getUnitId() != null && condition.getAcctYear() != null && condition.getAcctPeriod() != null;
    }

    private List<GcRelatedItemEO> getAutoCheckData(FinancialCheckQueryConditionVO condition) {
        condition.setPageNum(Integer.valueOf(-1));
        ArrayList<GcRelatedItemEO> allUncheckedItems = new ArrayList<GcRelatedItemEO>();
        FinancialCheckQueryConditionDTO financialCheckQueryConditionDTO = new FinancialCheckQueryConditionDTO();
        BeanUtils.copyProperties(condition, financialCheckQueryConditionDTO);
        if (CheckShowTypeEnum.SCHEME.getCode().equals(condition.getShowType())) {
            PageInfo<GcRelatedItemEO> pageInfo = this.financialCheckImplDao.queryUncheckedGroupByScheme(financialCheckQueryConditionDTO, FinancialCheckConfigUtils.getCheckOrgType(), new HashMap<String, String>());
            if (!CollectionUtils.isEmpty(pageInfo.getList())) {
                allUncheckedItems.addAll(pageInfo.getList());
            }
        } else {
            PageInfo<GcRelatedItemEO> oppPageInfo;
            PageInfo<GcRelatedItemEO> localPageInfo = this.financialCheckImplDao.queryUncheckedGroupByUnit(financialCheckQueryConditionDTO, FinancialCheckConfigUtils.getCheckOrgType(), new HashMap<String, String>());
            if (!CollectionUtils.isEmpty(localPageInfo.getList())) {
                allUncheckedItems.addAll(localPageInfo.getList());
            }
            if (!CollectionUtils.isEmpty((oppPageInfo = this.financialCheckImplDao.queryUncheckedGroupByOppUnit(financialCheckQueryConditionDTO, FinancialCheckConfigUtils.getCheckOrgType(), new HashMap<String, String>())).getList())) {
                allUncheckedItems.addAll(oppPageInfo.getList());
            }
        }
        return allUncheckedItems;
    }

    @Override
    @OuterTransaction
    @Deprecated
    public String cancelCheckBySchemeIds(Set<String> checkSchemeIds) {
        if (CollectionUtils.isEmpty(checkSchemeIds)) {
            return "\u6ca1\u6709\u53ef\u53d6\u6d88\u5bf9\u8d26\u7684\u6570\u636e";
        }
        List vchrItems = this.relatedItemQueryService.findByCheckSchemeIds(checkSchemeIds);
        if (CollectionUtils.isEmpty(vchrItems)) {
            return "\u6ca1\u6709\u53ef\u53d6\u6d88\u5bf9\u8d26\u7684\u6570\u636e";
        }
        int size = vchrItems.stream().map(GcRelatedItemEO::getCheckId).collect(Collectors.toSet()).size();
        UnitStateUtils.checkReconciliationPeriodStatus(vchrItems);
        this.relatedItemCommandService.cancelCheck(vchrItems, false);
        return "\u6210\u529f\u53d6\u6d88\u5bf9\u8d26" + size + "\u7ec4\uff0c\u4e0d\u80fd\u53d6\u6d88\u5bf9\u8d26" + 0 + "\u7ec4\u3002";
    }

    @Override
    @OuterTransaction
    public String cancelCheck(Set<String> checkIds) {
        if (CollectionUtils.isEmpty(checkIds)) {
            return "\u6ca1\u6709\u53ef\u53d6\u6d88\u5bf9\u8d26\u7684\u6570\u636e\u3002";
        }
        List vchrItems = this.relatedItemQueryService.findByCheckIds(checkIds);
        if (CollectionUtils.isEmpty(vchrItems)) {
            return "\u6ca1\u6709\u53ef\u53d6\u6d88\u5bf9\u8d26\u7684\u6570\u636e\u3002";
        }
        UnitStateUtils.checkReconciliationPeriodStatus(vchrItems);
        this.relatedItemCommandService.cancelCheck(vchrItems, false);
        return "\u6210\u529f\u53d6\u6d88\u5bf9\u8d26" + checkIds.size() + "\u7ec4\uff0c\u4e0d\u80fd\u53d6\u6d88\u5bf9\u8d26" + 0 + "\u7ec4\u3002";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String saveUnCheckDesc(List<String> uncheckedDataIds, String desc, String type) {
        if (StringUtils.isEmpty(type)) {
            throw new BusinessRuntimeException("\u672a\u5bf9\u8d26\u8bf4\u660e\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty(desc)) {
            throw new BusinessRuntimeException("\u672a\u5bf9\u8d26\u8865\u5145\u8bf4\u660e\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String lockId = this.iLockService.tryLock(uncheckedDataIds, "\u4fdd\u5b58\u672a\u5bf9\u8d26\u8bf4\u660e", "checkCenter");
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.iLockService.queryUserNameByInputItemId(uncheckedDataIds, "checkCenter");
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        try {
            List voucherItems = this.relatedItemQueryService.queryByIds(uncheckedDataIds);
            if (CollectionUtils.isEmpty(voucherItems)) {
                throw new BusinessRuntimeException("\u6570\u636e\u72b6\u6001\u53d1\u751f\u53d8\u5316\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
            }
            if (voucherItems.stream().anyMatch(item -> StringUtils.hasText(item.getCheckId()))) {
                throw new BusinessRuntimeException("\u5b58\u5728\u5df2\u5bf9\u8d26\u7684\u6570\u636e\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
            }
            ((FinancialCheckServiceImpl)SpringBeanUtils.getBean(FinancialCheckServiceImpl.class)).realSaveUnCheckDesc(uncheckedDataIds, desc, type);
        }
        finally {
            this.iLockService.unlock(lockId);
        }
        return "success";
    }

    @OuterTransaction
    public void realSaveUnCheckDesc(List<String> selectUnitIds, String desc, String type) {
        List unExistUnCheckDesc;
        List existVoucherItemUnCheckDescS = this.voucherItemUnCheckDescDao.queryExistUnCheckDesc(selectUnitIds);
        Set existUnCheckVoucherIds = existVoucherItemUnCheckDescS.stream().map(GcRelatedItemUnCheckDescEO::getItemId).collect(Collectors.toSet());
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        String username = Objects.isNull(user) ? "" : user.getName();
        Date updateTime = new Date();
        if (!CollectionUtils.isEmpty(existUnCheckVoucherIds)) {
            List existUnCheckDesc = existUnCheckVoucherIds.stream().map(x -> new GcRelatedItemUnCheckDescEO(x, type, desc, updateTime, username)).collect(Collectors.toList());
            this.voucherItemUnCheckDescDao.updateExistUnCheckDesc(existUnCheckDesc);
        }
        if (!CollectionUtils.isEmpty(unExistUnCheckDesc = selectUnitIds.stream().filter(x -> !existUnCheckVoucherIds.contains(x)).map(x -> new GcRelatedItemUnCheckDescEO(x, type, desc, updateTime, username)).collect(Collectors.toList()))) {
            this.voucherItemUnCheckDescDao.addBatch(unExistUnCheckDesc);
        }
    }

    @Override
    public String authUnCheckDesc(FinancialCheckQueryConditionVO condition, List<String> uncheckedDataIds) {
        List voucherItems = this.relatedItemQueryService.queryByIds(uncheckedDataIds);
        if (CollectionUtils.isEmpty(voucherItems)) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u6709\u6548\u6570\u636e");
        }
        Set selectUnitIds = voucherItems.stream().map(GcRelatedItemEO::getUnitId).collect(Collectors.toSet());
        List allUnitId = OrgUtils.getAllUnitId((String)condition.getOrgVer(), (GcAuthorityType)GcAuthorityType.ACCESS, (String)FinancialCheckConfigUtils.getCheckOrgType());
        if (!allUnitId.containsAll(selectUnitIds)) {
            throw new BusinessRuntimeException("\u5b58\u5728\u65e0\u6743\u9650\u7684\u5355\u4f4d\uff0c\u4e0d\u53ef\u8fdb\u884c\u672a\u5bf9\u8d26\u8bf4\u660e\u586b\u5199\uff0c\u8bf7\u9009\u62e9\u6709\u6743\u9650\u7684\u5355\u4f4d");
        }
        return "success";
    }

    @Override
    @OuterTransaction
    public String deleteUnCheckDesc(List<String> uncheckedDataIds) {
        this.voucherItemUnCheckDescDao.deleteUnCheckDesc(uncheckedDataIds);
        return "success";
    }

    @Override
    public void realTimeCheck(int year, Set<String> allIncrementUnits) {
        Calendar calendar = Calendar.getInstance();
        int accPeriod = calendar.get(2) + 1;
        int curYear = calendar.get(1);
        if (curYear > year) {
            accPeriod = 12;
        }
        String checkOrgType = FinancialCheckConfigUtils.getCheckOrgType();
        if (CollectionUtils.isEmpty(allIncrementUnits)) {
            allIncrementUnits = new HashSet<String>(OrgUtils.getAllUnitId((String)this.getOrgVer(year, accPeriod), (GcAuthorityType)GcAuthorityType.NONE, (String)checkOrgType));
        }
        List oppUnitIds = OrgUtils.getAllUnitId((String)this.getOrgVer(year, accPeriod), (GcAuthorityType)GcAuthorityType.NONE, (String)checkOrgType);
        List voucherItems = this.relatedItemQueryService.queryUncheckedItemByUnitsAndYear(allIncrementUnits, oppUnitIds, year);
        ArrayList<GcRelatedItemEO> allItems = new ArrayList<GcRelatedItemEO>();
        ArrayList<GcRelatedItemEO> needMatchSchemeItems = new ArrayList<GcRelatedItemEO>();
        voucherItems.forEach(item -> {
            if (StringUtils.hasText(item.getCheckRuleId())) {
                allItems.add((GcRelatedItemEO)item);
            } else {
                needMatchSchemeItems.add((GcRelatedItemEO)item);
            }
        });
        this.matchScheme(year, needMatchSchemeItems);
        List<GcRelatedItemEO> schemeChangedItems = needMatchSchemeItems.stream().filter(item -> StringUtils.hasText(item.getCheckRuleId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(schemeChangedItems)) {
            allItems.addAll(schemeChangedItems);
        }
        if (CollectionUtils.isEmpty(allItems)) {
            return;
        }
        FinancialCheckDataProvider provider = this.getDataProvider(allItems);
        CheckResult checkResult = FinancialCheckUtils.autoCheck(provider);
        if (!CollectionUtils.isEmpty(schemeChangedItems)) {
            GcRelatedItemEO firstItem = (GcRelatedItemEO)schemeChangedItems.get(0);
            PeriodWrapper periodWrapper = new PeriodWrapper(firstItem.getAcctYear().intValue(), 4, firstItem.getAcctPeriod().intValue());
            this.updateSchemeInfoAfterCheck(checkResult.getCheckedItemIds(), schemeChangedItems, periodWrapper.toString());
        }
    }

    private String getOrgVer(int year, int accPeriod) {
        String dateStr = String.format("%04d", year) + String.format("%02d", accPeriod) + "01";
        return dateStr;
    }

    private FinancialCheckDataProvider getDataProvider(List<GcRelatedItemEO> voucherItems) {
        GcRelatedItemEO voucherItem = voucherItems.get(0);
        FinancialCheckDataProviderImpl provider = new FinancialCheckDataProviderImpl();
        Calendar dateCal = Calendar.getInstance();
        provider.setCheckPeriodBaseVoucherPeriod(true);
        provider.setAcctYear(voucherItem.getAcctYear());
        provider.setAcctPeriod(DateUtils.getDateFieldValue((Calendar)dateCal, (int)2));
        provider.setVoucherItems(voucherItems);
        return provider;
    }

    @Override
    public List<FinancialCheckQueryVO> manualCheck(ManualCheckParam params) {
        List<GcRelatedItemEO> relatedItemS = this.relatedItemQueryService.queryByIds((Collection)params.getItemIds());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE);
        HashSet units = new HashSet();
        relatedItemS.stream().forEach(item -> {
            units.add(item.getUnitId());
            units.add(item.getOppUnitId());
        });
        long unitCount = relatedItemS.stream().map(GcRelatedItemEO::getUnitId).distinct().count();
        if (units.size() != 2 || unitCount != 2L) {
            throw new BusinessRuntimeException("\u53c2\u4e0e\u624b\u5de5\u5bf9\u8d26\u7684\u6570\u636e\u672c\u5bf9\u65b9\u5355\u4f4d\u53ea\u80fd\u6709\u4e24\u5bb6");
        }
        GcRelatedItemEO firstItem = (GcRelatedItemEO)relatedItemS.get(0);
        UnitStateUtils.checkUnitStateIsOpen(firstItem.getUnitId(), firstItem.getOppUnitId(), params.getYear(), params.getPeriod());
        relatedItemS = this.computeCheckAmt(relatedItemS, params.getManualCheckType());
        return this.handleItemEOsForUnchecked(relatedItemS, params.getYear(), params.getPeriod());
    }

    private List<GcRelatedItemEO> computeCheckAmt(List<GcRelatedItemEO> itemS, int manualCheckType) {
        itemS.forEach(GcRelatedItemEO::initAmtInfo);
        BigDecimal debitSum = BigDecimal.valueOf(0.0);
        BigDecimal creditSum = BigDecimal.valueOf(0.0);
        ArrayList<GcRelatedItemEO> allItems = new ArrayList<GcRelatedItemEO>();
        ArrayList<GcRelatedItemEO> debitItems = new ArrayList<GcRelatedItemEO>();
        ArrayList<GcRelatedItemEO> creditItems = new ArrayList<GcRelatedItemEO>();
        for (GcRelatedItemEO record : itemS) {
            if (record.getAmtOrient() == 1) {
                debitSum = debitSum.add(BigDecimal.valueOf(record.getDebitOrig()));
                debitItems.add(record);
                continue;
            }
            creditSum = creditSum.add(BigDecimal.valueOf(record.getCreditOrig()));
            creditItems.add(record);
        }
        BigDecimal DebitSubtractCredit = debitSum.subtract(creditSum);
        switch (manualCheckType) {
            case 1: {
                if (DebitSubtractCredit.signum() == 1) {
                    debitItems.forEach(item -> {
                        item.setChkAmtD(item.getDebitOrig());
                        item.setChkAmtC(item.getCreditOrig());
                    });
                    for (int i = 0; i < creditItems.size(); ++i) {
                        GcRelatedItemEO currentCreditItem = (GcRelatedItemEO)creditItems.get(i);
                        currentCreditItem.setChkAmtD(currentCreditItem.getDebitOrig());
                        if (i == 0) {
                            currentCreditItem.setChkAmtC(Double.valueOf(BigDecimal.valueOf(currentCreditItem.getCreditOrig()).add(DebitSubtractCredit).doubleValue()));
                            continue;
                        }
                        currentCreditItem.setChkAmtC(currentCreditItem.getCreditOrig());
                    }
                    break;
                }
                for (int i = 0; i < debitItems.size(); ++i) {
                    GcRelatedItemEO currentDebitItem = (GcRelatedItemEO)debitItems.get(i);
                    if (i == 0) {
                        currentDebitItem.setChkAmtD(Double.valueOf(BigDecimal.valueOf(currentDebitItem.getDebitOrig()).add(DebitSubtractCredit.negate()).doubleValue()));
                    } else {
                        currentDebitItem.setChkAmtD(currentDebitItem.getDebitOrig());
                    }
                    currentDebitItem.setChkAmtC(currentDebitItem.getCreditOrig());
                }
                creditItems.forEach(item -> {
                    item.setChkAmtD(item.getDebitOrig());
                    item.setChkAmtC(item.getCreditOrig());
                });
                break;
            }
            case 2: {
                if (DebitSubtractCredit.signum() == 1) {
                    for (int i = 0; i < debitItems.size(); ++i) {
                        GcRelatedItemEO currentDebitItem = (GcRelatedItemEO)debitItems.get(i);
                        if (i == 0) {
                            currentDebitItem.setChkAmtD(Double.valueOf(BigDecimal.valueOf(currentDebitItem.getDebitOrig()).subtract(DebitSubtractCredit).doubleValue()));
                        } else {
                            currentDebitItem.setChkAmtD(currentDebitItem.getDebitOrig());
                        }
                        currentDebitItem.setChkAmtC(currentDebitItem.getCreditOrig());
                    }
                    creditItems.forEach(item -> {
                        item.setChkAmtD(item.getDebitOrig());
                        item.setChkAmtC(item.getCreditOrig());
                    });
                    break;
                }
                debitItems.forEach(item -> {
                    item.setChkAmtD(item.getDebitOrig());
                    item.setChkAmtC(item.getCreditOrig());
                });
                for (int i = 0; i < creditItems.size(); ++i) {
                    GcRelatedItemEO currentCreditItem = (GcRelatedItemEO)creditItems.get(i);
                    currentCreditItem.setChkAmtD(currentCreditItem.getDebitOrig());
                    if (i == 0) {
                        currentCreditItem.setChkAmtC(Double.valueOf(BigDecimal.valueOf(currentCreditItem.getCreditOrig()).add(DebitSubtractCredit).doubleValue()));
                        continue;
                    }
                    currentCreditItem.setChkAmtC(currentCreditItem.getCreditOrig());
                }
                break;
            }
            case 3: {
                debitItems.forEach(item -> {
                    item.setChkAmtD(item.getDebitOrig());
                    item.setChkAmtC(item.getCreditOrig());
                });
                for (int i = 0; i < creditItems.size(); ++i) {
                    GcRelatedItemEO currentCreditItem = (GcRelatedItemEO)creditItems.get(i);
                    currentCreditItem.setChkAmtD(currentCreditItem.getDebitOrig());
                    if (i == 0) {
                        currentCreditItem.setChkAmtC(Double.valueOf(BigDecimal.valueOf(currentCreditItem.getCreditOrig()).add(DebitSubtractCredit).doubleValue()));
                        continue;
                    }
                    currentCreditItem.setChkAmtC(currentCreditItem.getCreditOrig());
                }
                break;
            }
            case 4: {
                for (int i = 0; i < debitItems.size(); ++i) {
                    GcRelatedItemEO currentDebitItem = (GcRelatedItemEO)debitItems.get(i);
                    if (i == 0) {
                        currentDebitItem.setChkAmtD(Double.valueOf(BigDecimal.valueOf(currentDebitItem.getDebitOrig()).subtract(DebitSubtractCredit).doubleValue()));
                    } else {
                        currentDebitItem.setChkAmtD(currentDebitItem.getDebitOrig());
                    }
                    currentDebitItem.setChkAmtC(currentDebitItem.getCreditOrig());
                }
                creditItems.forEach(item -> {
                    item.setChkAmtD(item.getDebitOrig());
                    item.setChkAmtC(item.getCreditOrig());
                });
                break;
            }
        }
        allItems.addAll(creditItems);
        allItems.addAll(debitItems);
        return allItems;
    }

    @Override
    @OuterTransaction
    public void saveManualCheckData(List<FinancialCheckQueryVO> items) {
        BigDecimal debitSum = BigDecimal.valueOf(0.0);
        BigDecimal creditSum = BigDecimal.valueOf(0.0);
        for (FinancialCheckQueryVO record : items) {
            if (record.getCreditOrig() == null || record.getCreditOrig() == 0.0) {
                record.setChkAmtD(record.getChkAmt());
                record.setChkAmtC(Double.valueOf(0.0));
                debitSum = debitSum.add(BigDecimal.valueOf(record.getChkAmt()));
                continue;
            }
            record.setChkAmtD(Double.valueOf(0.0));
            record.setChkAmtC(record.getChkAmt());
            creditSum = creditSum.add(BigDecimal.valueOf(record.getChkAmt()));
        }
        if (debitSum.compareTo(creditSum) != 0) {
            throw new BusinessRuntimeException("\u624b\u5de5\u5bf9\u8d26\u5931\u8d25\uff1a\u501f\u65b9\u5bf9\u8d26\u91d1\u989d\u6c47\u603b\u4e0e\u8d37\u65b9\u5bf9\u8d26\u91d1\u989d\u6c47\u603b\u4e0d\u4e00\u81f4");
        }
        List<GcRelatedItemEO> itemEOS = items.stream().map(this::convertVO2EO).collect(Collectors.toList());
        this.setCheckInfo(itemEOS, CheckModeEnum.BILATERAL.getCode());
        try {
            this.relatedItemCommandService.doCheck(itemEOS, false);
        }
        catch (BusinessRuntimeException e) {
            throw new BusinessRuntimeException("\u624b\u5de5\u5bf9\u8d26\u5931\u8d25,\u5931\u8d25\u539f\u56e0\u4e3a:" + e.getMessage());
        }
    }

    private GcRelatedItemEO convertVO2EO(FinancialCheckQueryVO itemVO) {
        GcRelatedItemEO item = new GcRelatedItemEO();
        BeanUtils.copyProperties(itemVO, item);
        return item;
    }

    @Override
    public PageInfo<FinancialCheckQueryVO> queryChecked(FinancialCheckQueryConditionVO condition) {
        List checkIds;
        int itemtotalSize = 0;
        if (CollectionUtils.isEmpty(condition.getCheckIds())) {
            String checkOrgType;
            PageInfo<GcRelatedItemEO> gcRelatedItemEOPageInfo;
            List list;
            FinancialCheckQueryConditionDTO financialCheckQueryConditionDTO = new FinancialCheckQueryConditionDTO();
            BeanUtils.copyProperties(condition, financialCheckQueryConditionDTO);
            List checkSchemeIds = financialCheckQueryConditionDTO.getSchemeIds();
            if (!CollectionUtils.isEmpty(checkSchemeIds)) {
                List schemeByIds = this.schemeDao.getSchemeByIdsOrParentIds(checkSchemeIds);
                financialCheckQueryConditionDTO.setSchemeIds(schemeByIds.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
            }
            if (CollectionUtils.isEmpty(list = (gcRelatedItemEOPageInfo = this.financialCheckImplDao.queryChecked(financialCheckQueryConditionDTO, checkOrgType = FinancialCheckConfigUtils.getCheckOrgType())).getList())) {
                return PageInfo.empty();
            }
            checkIds = list.stream().map(GcRelatedItemEO::getCheckId).collect(Collectors.toList());
            itemtotalSize = gcRelatedItemEOPageInfo.getSize();
        } else {
            checkIds = condition.getCheckIds();
            itemtotalSize = checkIds.size();
        }
        List byCheckIds = this.voucherItemDao.findByCheckIds((Collection)checkIds);
        Set schemeIds = byCheckIds.stream().map(GcRelatedItemEO::getCheckRuleId).collect(Collectors.toSet());
        Map checkId2Items = byCheckIds.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getCheckId, Collectors.mapping(Function.identity(), Collectors.toList())));
        ArrayList<Map.Entry<String, List<Map.Entry>>> entries = new ArrayList<Map.Entry<String, List<Map.Entry>>>(checkId2Items.entrySet());
        entries.sort((o2, o1) -> ((GcRelatedItemEO)((List)o1.getValue()).get(0)).getCheckTime().compareTo(((GcRelatedItemEO)((List)o2.getValue()).get(0)).getCheckTime()));
        LinkedHashMap<String, List> groupId2ItemLinkedMap = new LinkedHashMap<String, List>(64);
        entries.forEach(entry -> groupId2ItemLinkedMap.put((String)entry.getKey(), (List)entry.getValue()));
        List schemeByIds = this.schemeDao.getSchemeByIds(new ArrayList(schemeIds));
        Map<String, String> schemeId2Self = schemeByIds.stream().collect(Collectors.toMap(DefaultTableEntity::getId, FinancialCheckSchemeEO::getSchemeName, (k1, k2) -> k2));
        List<ColumnModelDefine> extendFields = this.getExtendFields();
        List<DimensionVO> dimensionVOS = this.convertDimVO(extendFields);
        ArrayList financialCheckQueryVOS = new ArrayList(byCheckIds.size());
        Integer[] index = new Integer[]{0};
        FinancialCheckQueryEO2VOHelper helper = FinancialCheckQueryEO2VOHelper.newInstance();
        groupId2ItemLinkedMap.forEach((groupId, items) -> {
            int rowSpan = items.size();
            Integer n = index[0];
            Integer n2 = index[0] = Integer.valueOf(index[0] + 1);
            for (GcRelatedItemEO item : items) {
                FinancialCheckQueryVO financialCheckQueryVO = helper.convertToCheckQueryVO(item);
                financialCheckQueryVO.setCheckRuleId((String)schemeId2Self.get(financialCheckQueryVO.getCheckRuleId()));
                financialCheckQueryVO.setRowspan(Integer.valueOf(rowSpan));
                financialCheckQueryVO.setIndex(index[0]);
                financialCheckQueryVO.setDimensions(this.handleDims(dimensionVOS, item));
                FinancialStatusEnum state = helper.getFinancialStatus(item.getUnitId(), item.getOppUnitId(), item.getCheckYear(), item.getCheckPeriod());
                if (Objects.nonNull(state)) {
                    financialCheckQueryVO.setUnitState(state.getName());
                }
                financialCheckQueryVOS.add(financialCheckQueryVO);
            }
        });
        return PageInfo.of(financialCheckQueryVOS, (int)condition.getPageNum(), (int)condition.getPageSize(), (int)itemtotalSize);
    }

    @Override
    public PageInfo<FinancialCheckQueryVO> queryUncheckedGroupByUnit(FinancialCheckQueryConditionVO condition) {
        String checkOrgType;
        PageInfo<GcRelatedItemEO> gcRelatedItemEOPageInfo;
        List list;
        List checkSchemeIds;
        FinancialCheckQueryConditionDTO financialCheckQueryConditionDTO = new FinancialCheckQueryConditionDTO();
        BeanUtils.copyProperties(condition, financialCheckQueryConditionDTO);
        List sortColumns = condition.getSortColumns();
        LinkedHashMap<String, String> sortField2WayMap = new LinkedHashMap<String, String>();
        if (!CollectionUtils.isEmpty(sortColumns)) {
            for (TableColumnVO sortColumn : sortColumns) {
                sortField2WayMap.put(sortColumn.getKey(), sortColumn.getSortOrder());
            }
        }
        if (!CollectionUtils.isEmpty(checkSchemeIds = financialCheckQueryConditionDTO.getSchemeIds())) {
            List schemeByIds = this.schemeDao.getSchemeByIdsOrParentIds(checkSchemeIds);
            financialCheckQueryConditionDTO.setSchemeIds(schemeByIds.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        }
        if (CollectionUtils.isEmpty(list = (gcRelatedItemEOPageInfo = this.financialCheckImplDao.queryUncheckedGroupByUnit(financialCheckQueryConditionDTO, checkOrgType = FinancialCheckConfigUtils.getCheckOrgType(), sortField2WayMap)).getList())) {
            return PageInfo.empty();
        }
        List<FinancialCheckQueryVO> financialCheckQueryVOS = this.handleItemEOsForUnchecked(list, condition.getAcctYear(), condition.getAcctPeriod());
        return PageInfo.of(financialCheckQueryVOS, (int)gcRelatedItemEOPageInfo.getPageNum(), (int)gcRelatedItemEOPageInfo.getPageSize(), (int)gcRelatedItemEOPageInfo.getSize());
    }

    private List<FinancialCheckQueryVO> handleItemEOsForUnchecked(List<GcRelatedItemEO> list, int year, int period) {
        List itemIds = list.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        Set schemeIds = list.stream().map(GcRelatedItemEO::getCheckRuleId).collect(Collectors.toSet());
        List gcRelatedItemUnCheckDescEOS = this.voucherItemUnCheckDescDao.queryExistUnCheckDesc(itemIds);
        Map itemId2UncheckDesc = gcRelatedItemUnCheckDescEOS.stream().collect(Collectors.toMap(GcRelatedItemUnCheckDescEO::getItemId, Function.identity(), (k1, k2) -> k2));
        List schemeByIds = this.schemeDao.getSchemeByIds(new ArrayList(schemeIds));
        Map<String, String> schemeId2Self = schemeByIds.stream().collect(Collectors.toMap(DefaultTableEntity::getId, FinancialCheckSchemeEO::getSchemeName, (k1, k2) -> k2));
        List<ColumnModelDefine> extendFields = this.getExtendFields();
        List<DimensionVO> dimensionVOS = this.convertDimVO(extendFields);
        ArrayList<FinancialCheckQueryVO> financialCheckQueryVOS = new ArrayList<FinancialCheckQueryVO>(list.size());
        FinancialCheckQueryEO2VOHelper helper = FinancialCheckQueryEO2VOHelper.newInstance();
        for (GcRelatedItemEO gcRelatedItemEO : list) {
            FinancialCheckQueryVO financialCheckQueryVO = helper.convertToCheckQueryVO(gcRelatedItemEO);
            financialCheckQueryVO.setCheckRuleId(schemeId2Self.get(financialCheckQueryVO.getCheckRuleId()));
            GcRelatedItemUnCheckDescEO gcRelatedItemUnCheckDescEO = (GcRelatedItemUnCheckDescEO)itemId2UncheckDesc.get(financialCheckQueryVO.getId());
            if (Objects.nonNull(gcRelatedItemUnCheckDescEO)) {
                String unCheckType = gcRelatedItemUnCheckDescEO.getUnCheckType();
                GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_UNCHECK_REASON_TYPE", unCheckType);
                if (Objects.nonNull(gcBaseData)) {
                    financialCheckQueryVO.setUnCheckTypeCode(unCheckType);
                    financialCheckQueryVO.setUnCheckType(gcBaseData.getTitle());
                }
                financialCheckQueryVO.setUnCheckDesc(gcRelatedItemUnCheckDescEO.getUnCheckDesc());
            }
            financialCheckQueryVO.setDimensions(this.handleDims(dimensionVOS, gcRelatedItemEO));
            FinancialStatusEnum financialStatusEnum = helper.getFinancialStatus(gcRelatedItemEO.getUnitId(), gcRelatedItemEO.getOppUnitId(), year, period);
            if (Objects.nonNull(financialStatusEnum)) {
                financialCheckQueryVO.setUnitState(financialStatusEnum.getName());
            }
            financialCheckQueryVOS.add(financialCheckQueryVO);
        }
        return financialCheckQueryVOS;
    }

    @Override
    public PageInfo<FinancialCheckQueryVO> queryUncheckedGroupByOppUnit(FinancialCheckQueryConditionVO condition) {
        String checkOrgType;
        PageInfo<GcRelatedItemEO> gcRelatedItemEOPageInfo;
        List list;
        List checkSchemeIds;
        FinancialCheckQueryConditionDTO financialCheckQueryConditionDTO = new FinancialCheckQueryConditionDTO();
        BeanUtils.copyProperties(condition, financialCheckQueryConditionDTO);
        List sortColumns = condition.getSortColumns();
        LinkedHashMap<String, String> sortField2WayMap = new LinkedHashMap<String, String>();
        if (!CollectionUtils.isEmpty(sortColumns)) {
            for (TableColumnVO sortColumn : sortColumns) {
                sortField2WayMap.put(sortColumn.getKey(), sortColumn.getSortOrder());
            }
        }
        if (!CollectionUtils.isEmpty(checkSchemeIds = financialCheckQueryConditionDTO.getSchemeIds())) {
            List schemeByIds = this.schemeDao.getSchemeByIdsOrParentIds(checkSchemeIds);
            financialCheckQueryConditionDTO.setSchemeIds(schemeByIds.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        }
        if (CollectionUtils.isEmpty(list = (gcRelatedItemEOPageInfo = this.financialCheckImplDao.queryUncheckedGroupByOppUnit(financialCheckQueryConditionDTO, checkOrgType = FinancialCheckConfigUtils.getCheckOrgType(), sortField2WayMap)).getList())) {
            return PageInfo.empty();
        }
        List<FinancialCheckQueryVO> financialCheckQueryVOS = this.handleItemEOsForUnchecked(list, condition.getAcctYear(), condition.getAcctPeriod());
        return PageInfo.of(financialCheckQueryVOS, (int)gcRelatedItemEOPageInfo.getPageNum(), (int)gcRelatedItemEOPageInfo.getPageSize(), (int)gcRelatedItemEOPageInfo.getSize());
    }

    @Override
    public PageInfo<FinancialCheckQueryVO> queryUncheckedGroupByScheme(FinancialCheckQueryConditionVO condition) {
        PageInfo<GcRelatedItemEO> gcRelatedItemEOPageInfo;
        List list;
        List checkSchemeIds;
        FinancialCheckQueryConditionDTO financialCheckQueryConditionDTO = new FinancialCheckQueryConditionDTO();
        BeanUtils.copyProperties(condition, financialCheckQueryConditionDTO);
        List sortColumns = condition.getSortColumns();
        LinkedHashMap<String, String> sortField2WayMap = new LinkedHashMap<String, String>();
        if (!CollectionUtils.isEmpty(sortColumns)) {
            for (TableColumnVO sortColumn : sortColumns) {
                sortField2WayMap.put(sortColumn.getKey(), sortColumn.getSortOrder());
            }
        }
        if (!CollectionUtils.isEmpty(checkSchemeIds = financialCheckQueryConditionDTO.getSchemeIds())) {
            List schemeByIds = this.schemeDao.getSchemeByIdsOrParentIds(checkSchemeIds);
            financialCheckQueryConditionDTO.setSchemeIds(schemeByIds.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        }
        if (CollectionUtils.isEmpty(list = (gcRelatedItemEOPageInfo = this.financialCheckImplDao.queryUncheckedGroupByScheme(financialCheckQueryConditionDTO, FinancialCheckConfigUtils.getCheckOrgType(), sortField2WayMap)).getList())) {
            return PageInfo.empty();
        }
        List itemIds = list.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        Set schemeIds = list.stream().map(GcRelatedItemEO::getCheckRuleId).collect(Collectors.toSet());
        List gcRelatedItemUnCheckDescEOS = this.voucherItemUnCheckDescDao.queryExistUnCheckDesc(itemIds);
        Map itemId2UncheckDesc = gcRelatedItemUnCheckDescEOS.stream().collect(Collectors.toMap(GcRelatedItemUnCheckDescEO::getItemId, Function.identity(), (k1, k2) -> k2));
        List schemeByIds = this.schemeDao.getSchemeByIds(new ArrayList(schemeIds));
        Map<String, String> schemeId2Self = schemeByIds.stream().collect(Collectors.toMap(DefaultTableEntity::getId, FinancialCheckSchemeEO::getSchemeName, (k1, k2) -> k2));
        ArrayList financialCheckQueryVOS = new ArrayList();
        list.forEach(gcRelatedItemEO -> {
            if (!StringUtils.hasText(gcRelatedItemEO.getCheckRuleId())) {
                gcRelatedItemEO.setCheckRuleId("");
            }
        });
        Map groupItems = list.stream().collect(Collectors.groupingBy(GcRelatedItemEO::getCheckRuleId, LinkedHashMap::new, Collectors.groupingBy(gcRelatedItemEO -> {
            if (gcRelatedItemEO.getUnitId().compareTo(gcRelatedItemEO.getOppUnitId()) > 0) {
                return gcRelatedItemEO.getUnitId() + gcRelatedItemEO.getOppUnitId();
            }
            return gcRelatedItemEO.getOppUnitId() + gcRelatedItemEO.getUnitId();
        }, LinkedHashMap::new, Collectors.toList())));
        List<ColumnModelDefine> extendFields = this.getExtendFields();
        List<DimensionVO> dimensionVOS = this.convertDimVO(extendFields);
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE);
        List numDims = extendFields.stream().filter(columnModelDefine -> ColumnModelType.DOUBLE.equals((Object)columnModelDefine.getColumnType()) || ColumnModelType.INTEGER.equals((Object)columnModelDefine.getColumnType()) || ColumnModelType.BIGDECIMAL.equals((Object)columnModelDefine.getColumnType())).collect(Collectors.toList());
        String outerDataSourceCode = OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource");
        TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode("GC_RELATED_ITEM", outerDataSourceCode);
        groupItems.forEach((schemeId, unitItems) -> unitItems.forEach((unitCom, items) -> {
            Comparator<GcRelatedItemEO> comparing = Comparator.comparing(GcRelatedItemEO::getCheckRuleId);
            for (Map.Entry entry : sortField2WayMap.entrySet()) {
                String columnName = ((String)entry.getKey()).toUpperCase(Locale.ROOT);
                comparing = comparing.thenComparing(this.sortedField(columnName, tableModelDefineByCode.getID()));
                if (!"desc".equals(entry.getValue())) continue;
                comparing = comparing.reversed();
            }
            List sortList = items.stream().sorted(comparing).collect(Collectors.toList());
            double debitOrig = 0.0;
            double creditOrig = 0.0;
            HashMap<String, Object> doubleMap = new HashMap<String, Object>();
            FinancialCheckQueryEO2VOHelper helper = FinancialCheckQueryEO2VOHelper.newInstance();
            for (GcRelatedItemEO item : sortList) {
                FinancialCheckQueryVO financialCheckQueryVO = helper.convertToCheckQueryVO(item);
                financialCheckQueryVO.setCheckRuleId((String)schemeId2Self.get(financialCheckQueryVO.getCheckRuleId()));
                GcRelatedItemUnCheckDescEO gcRelatedItemUnCheckDescEO = (GcRelatedItemUnCheckDescEO)itemId2UncheckDesc.get(financialCheckQueryVO.getId());
                if (Objects.nonNull(gcRelatedItemUnCheckDescEO)) {
                    String unCheckType = gcRelatedItemUnCheckDescEO.getUnCheckType();
                    GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_UNCHECK_REASON_TYPE", unCheckType);
                    if (Objects.nonNull(gcBaseData)) {
                        financialCheckQueryVO.setUnCheckTypeCode(unCheckType);
                        financialCheckQueryVO.setUnCheckType(gcBaseData.getTitle());
                    }
                    financialCheckQueryVO.setUnCheckDesc(gcRelatedItemUnCheckDescEO.getUnCheckDesc());
                }
                this.handleNumDims(numDims, item, doubleMap);
                financialCheckQueryVO.setDimensions(this.handleDims(dimensionVOS, item));
                FinancialStatusEnum financialStatus = helper.getFinancialStatus(item.getUnitId(), item.getOppUnitId(), condition.getAcctYear(), condition.getAcctPeriod());
                financialCheckQueryVO.setUnitState(financialStatus.getName());
                debitOrig += financialCheckQueryVO.getDebitOrig().doubleValue();
                creditOrig += financialCheckQueryVO.getCreditOrig().doubleValue();
                financialCheckQueryVOS.add(financialCheckQueryVO);
            }
            FinancialCheckQueryVO financialCheckQueryVO = new FinancialCheckQueryVO();
            financialCheckQueryVO.setUnitId("\u5c0f\u8ba1");
            financialCheckQueryVO.setDebitOrig(Double.valueOf(debitOrig));
            financialCheckQueryVO.setCreditOrig(Double.valueOf(creditOrig));
            financialCheckQueryVO.setDimensions(doubleMap);
            financialCheckQueryVOS.add(financialCheckQueryVO);
        }));
        return PageInfo.of(financialCheckQueryVOS, (int)gcRelatedItemEOPageInfo.getPageNum(), (int)gcRelatedItemEOPageInfo.getPageSize(), (int)gcRelatedItemEOPageInfo.getSize());
    }

    private void setCheckInfo(List<GcRelatedItemEO> voucherItems, String checkMode) {
        if (CollectionUtils.isEmpty(voucherItems)) {
            return;
        }
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CURRENCY");
        Map<String, String> currTitleMap = baseData.stream().collect(Collectors.toMap(GcBaseData::getTitle, GcBaseData::getCode, (k1, k2) -> k1));
        ContextUser loginUser = NpContextHolder.getContext().getUser();
        String loginUserName = loginUser == null ? "system" : loginUser.getName();
        Date checkTime = Date.from(Instant.now());
        String checkId = UUIDOrderUtils.newUUIDStr();
        Integer maxPeriod = voucherItems.stream().map(GcRelatedItemEO::getAcctPeriod).max(Integer::compareTo).get();
        for (GcRelatedItemEO record : voucherItems) {
            Assert.isNotNull((Object)currTitleMap.get(record.getOriginalCurr()));
            record.setChkCurr(currTitleMap.get(record.getOriginalCurr()));
            record.setChkState(CheckStateEnum.CHECKED.name());
            record.setCheckId(checkId);
            record.setCheckTime(checkTime);
            record.setCheckYear(voucherItems.get(0).getAcctYear());
            record.setCheckPeriod(maxPeriod);
            record.setChecker(loginUserName);
            record.setCheckMode(checkMode);
            record.setCheckType("\u624b\u5de5\u6838\u5bf9");
        }
    }

    @Override
    public CheckResult realTimeCheck(RealTimeCheckOrOffsetParam realTimeCheckOrOffsetParam) {
        List oppUnitCodes;
        String dataTime = realTimeCheckOrOffsetParam.getDataTime();
        Integer acctYear = ConverterUtils.getAsInteger((Object)dataTime.substring(0, 4));
        Integer acctPeriod = ConverterUtils.getAsInteger((Object)dataTime.substring(7));
        List<String> ids = realTimeCheckOrOffsetParam.getItems();
        List incrementItems = this.relatedItemQueryService.queryByIds(ids);
        if (CollectionUtils.isEmpty(incrementItems)) {
            return new CheckResult();
        }
        ArrayList<GcRelatedItemEO> allIncrementItems = new ArrayList<GcRelatedItemEO>();
        ArrayList<GcRelatedItemEO> needMatchSchemeItems = new ArrayList<GcRelatedItemEO>();
        incrementItems.forEach(item -> {
            if (StringUtils.hasText(item.getCheckRuleId())) {
                allIncrementItems.add((GcRelatedItemEO)item);
            } else {
                needMatchSchemeItems.add((GcRelatedItemEO)item);
            }
        });
        this.matchScheme(acctYear, needMatchSchemeItems);
        List<GcRelatedItemEO> schemeChangedItems = needMatchSchemeItems.stream().filter(item -> StringUtils.hasText(item.getCheckRuleId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(schemeChangedItems)) {
            allIncrementItems.addAll(schemeChangedItems);
        }
        Set localSchemes = allIncrementItems.stream().map(GcRelatedItemEO::getCheckRuleId).collect(Collectors.toSet());
        Set unitCodes = allIncrementItems.stream().map(GcRelatedItemEO::getUnitId).collect(Collectors.toSet());
        List relatedItems = this.relatedItemQueryService.queryUncheckedItemByUnitAndDataTime(unitCodes, oppUnitCodes = allIncrementItems.stream().map(GcRelatedItemEO::getOppUnitId).distinct().collect(Collectors.toList()), dataTime, FinancialCheckConfigUtils.getCheckWay().getCode(), localSchemes);
        if (!CollectionUtils.isEmpty(relatedItems)) {
            relatedItems = relatedItems.stream().filter(item -> !ids.contains(item.getId())).collect(Collectors.toList());
            allIncrementItems.addAll(relatedItems);
        }
        FinancialCheckDataProviderImpl provider = new FinancialCheckDataProviderImpl();
        provider.setAcctYear(acctYear);
        provider.setAcctPeriod(acctPeriod);
        provider.setVoucherItems(allIncrementItems);
        provider.setCheckPeriodBaseVoucherPeriod(true);
        CheckResult checkResult = FinancialCheckUtils.autoCheck(provider);
        List checkedItemIds = checkResult.getCheckedItemIds();
        if (!CollectionUtils.isEmpty(schemeChangedItems)) {
            this.updateSchemeInfoAfterCheck(checkedItemIds, schemeChangedItems, dataTime);
        }
        return checkResult;
    }

    private void updateSchemeInfoAfterCheck(List<String> checkedItemIds, List<GcRelatedItemEO> schemeChangedItems, String dataTime) {
        if (!CollectionUtils.isEmpty(schemeChangedItems = schemeChangedItems.stream().filter(item -> !checkedItemIds.contains(item.getId())).collect(Collectors.toList()))) {
            this.relatedItemCommandService.updateCheckSchemeInfo(schemeChangedItems);
            if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
                this.taskHandlerFactory.getMainTaskHandlerClient().startTask("FinancialCheckRealTimeOffsetHandler", JsonUtils.writeValueAsString((Object)new RealTimeCheckOrOffsetParam(schemeChangedItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()), dataTime)));
            }
        }
    }

    private void matchScheme(Integer acctYear, List<GcRelatedItemEO> items) {
        List<FinancialCheckSchemeEO> schemes = this.schemeService.listSchemeByYear(acctYear);
        if (!CollectionUtils.isEmpty(schemes)) {
            List<String> schemeIds = schemes.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
            this.schemeService.matchCheckSchemes(items, schemeIds);
        }
    }

    private Map<String, Object> handleDims(List<DimensionVO> dimFieldsVOByTableName, GcRelatedItemEO item) {
        HashMap<String, Object> dimensions = new HashMap<String, Object>();
        for (DimensionVO dimensionVO : dimFieldsVOByTableName) {
            String fieldCode = dimensionVO.getCode();
            if ("#".equals(item.getFieldValue(fieldCode))) {
                dimensions.put(fieldCode, null);
                continue;
            }
            if (StringUtils.hasText(dimensionVO.getReferField()) && Objects.nonNull(item.getFieldValue(fieldCode))) {
                GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode(dimensionVO.getReferField(), item.getFieldValue(fieldCode).toString());
                if (Objects.isNull(gcBaseData)) {
                    dimensions.put(fieldCode, item.getFieldValue(fieldCode));
                    continue;
                }
                dimensions.put(fieldCode, gcBaseData.getTitle());
                continue;
            }
            dimensions.put(fieldCode, item.getFieldValue(fieldCode));
        }
        return dimensions;
    }

    private void handleNumDims(List<ColumnModelDefine> columnModelDefines, GcRelatedItemEO item, Map<String, Object> dimMap) {
        for (ColumnModelDefine columnModelDefine : columnModelDefines) {
            String code = columnModelDefine.getCode();
            Object fieldValue = item.getFieldValue(code);
            if (Objects.isNull(dimMap.get(code))) {
                if (Objects.isNull(fieldValue)) {
                    dimMap.put(code, new BigDecimal(0));
                    continue;
                }
                dimMap.put(code, new BigDecimal(fieldValue.toString()));
                continue;
            }
            if (Objects.isNull(fieldValue)) continue;
            dimMap.put(code, ((BigDecimal)dimMap.get(code)).add(new BigDecimal(fieldValue.toString())));
        }
    }

    private Function<GcRelatedItemEO, Comparable> sortedField(String columnName, String tableId) {
        ColumnModelDefine columnModelDefineByCode = this.dataModelService.getColumnModelDefineByCode(tableId, columnName);
        ColumnModelType columnType = columnModelDefineByCode.getColumnType();
        if (ColumnModelType.BIGDECIMAL.equals((Object)columnType) || ColumnModelType.INTEGER.equals((Object)columnType) || ColumnModelType.DOUBLE.equals((Object)columnType)) {
            return item -> Objects.isNull(item.getFieldValue(columnName)) ? new Double(Double.MIN_VALUE) : Double.valueOf(item.getFieldValue(columnName).toString());
        }
        if (ColumnModelType.STRING.equals((Object)columnType)) {
            return item -> Objects.isNull(item.getFieldValue(columnName)) ? "" : item.getFieldValue(columnName).toString();
        }
        if (ColumnModelType.DATETIME.equals((Object)columnType)) {
            return item -> Objects.isNull(item.getFieldValue(columnName)) ? new Date(1L) : (Date)item.getFieldValue(columnName);
        }
        return item -> Objects.isNull(item.getFieldValue(columnName)) ? "" : item.getFieldValue(columnName).toString();
    }

    @Override
    public Map<String, String> getExcelColumnTitleMap(String type, TableColumnVO[] selectColumns) {
        LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
        if (null != selectColumns && selectColumns.length != 0) {
            for (TableColumnVO selectColumn : selectColumns) {
                titleMap.put(selectColumn.getKey(), selectColumn.getLabel());
            }
        }
        return titleMap;
    }

    @Override
    public List<Map<String, Object>> exportData(List<FinancialCheckQueryVO> dataList) {
        ArrayList<Map<String, Object>> resultMapList = new ArrayList<Map<String, Object>>();
        for (FinancialCheckQueryVO data : dataList) {
            Field[] fields;
            if (null == data.getId() || data.getId().isEmpty()) continue;
            HashMap<String, Object> resultMap = new HashMap<String, Object>();
            for (Field field : fields = data.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(data);
                    resultMap.put(field.getName(), value);
                }
                catch (IllegalAccessException e) {
                    LogHelper.error((Exception)e);
                }
            }
            Map dimensions = data.getDimensions();
            resultMap.putAll(dimensions);
            resultMapList.add(resultMap);
        }
        return resultMapList;
    }

    @Override
    public FinancialCheckQueryAmtSumVO queryAmtSum(FinancialCheckQueryAmtSumConditionVO condition) {
        FinancialCheckQueryConditionDTO financialCheckQueryConditionDTO = new FinancialCheckQueryConditionDTO();
        financialCheckQueryConditionDTO.setUnitId(condition.getUnitId());
        financialCheckQueryConditionDTO.setCheckLevel(condition.getCheckLevel());
        financialCheckQueryConditionDTO.setOppUnitId(condition.getOppUnitId());
        financialCheckQueryConditionDTO.setAcctYear(condition.getAcctYear());
        financialCheckQueryConditionDTO.setAcctPeriod(condition.getAcctPeriod());
        Map<String, Object> allDataMap = this.financialCheckImplDao.queryAmtSum(financialCheckQueryConditionDTO, FinancialCheckConfigUtils.getCheckOrgType());
        FinancialCheckQueryAmtSumVO financialCheckQueryAmtSumVO = new FinancialCheckQueryAmtSumVO();
        financialCheckQueryAmtSumVO.setDebitSum(Double.valueOf(Objects.nonNull(allDataMap.get("DEBITORIGSUM")) ? (Double)allDataMap.get("DEBITORIGSUM") : 0.0));
        financialCheckQueryAmtSumVO.setCreditSum(Double.valueOf(Objects.nonNull(allDataMap.get("CREDITORIGSUM")) ? (Double)allDataMap.get("CREDITORIGSUM") : 0.0));
        financialCheckQueryAmtSumVO.setDiffSum(Double.valueOf(Math.abs(financialCheckQueryAmtSumVO.getCreditSum() - financialCheckQueryAmtSumVO.getDebitSum())));
        financialCheckQueryAmtSumVO.setChkAmtCSum(Double.valueOf(Objects.nonNull(allDataMap.get("CHKAMTCSUM")) ? (Double)allDataMap.get("CHKAMTCSUM") : 0.0));
        financialCheckQueryAmtSumVO.setChkAmtDSum(Double.valueOf(Objects.nonNull(allDataMap.get("CHKAMTDSUM")) ? (Double)allDataMap.get("CHKAMTDSUM") : 0.0));
        financialCheckQueryAmtSumVO.setChkDiffSum(Double.valueOf(Math.abs(financialCheckQueryAmtSumVO.getChkAmtCSum() - financialCheckQueryAmtSumVO.getChkAmtDSum())));
        financialCheckQueryAmtSumVO.setUncheckCreditSum(Double.valueOf(Objects.nonNull(allDataMap.get("UNCHECKCREDITSUM")) ? (Double)allDataMap.get("UNCHECKCREDITSUM") : 0.0));
        financialCheckQueryAmtSumVO.setUncheckDebitSum(Double.valueOf(Objects.nonNull(allDataMap.get("UNCHECKDEBITSUM")) ? (Double)allDataMap.get("UNCHECKDEBITSUM") : 0.0));
        financialCheckQueryAmtSumVO.setUncheckDiffSum(Double.valueOf(Math.abs(financialCheckQueryAmtSumVO.getUncheckDebitSum() - financialCheckQueryAmtSumVO.getUncheckCreditSum())));
        return financialCheckQueryAmtSumVO;
    }

    @Override
    public FinancialClbrCodeInfoVO queryClbrCodeInfo(String id) {
        FinancialClbrCodeInfoVO financialClbrCodeInfoVO = new FinancialClbrCodeInfoVO();
        GcRelatedItemEO gcRelatedItemEO = new GcRelatedItemEO();
        gcRelatedItemEO.setId(id);
        GcRelatedItemEO relatedItemEO = (GcRelatedItemEO)this.voucherItemDao.selectByEntity((BaseEntity)gcRelatedItemEO);
        if ("SystemDefault".equals(relatedItemEO.getGcNumber())) {
            return new FinancialClbrCodeInfoVO();
        }
        String realGcNumber = relatedItemEO.getRealGcNumber();
        if (StringUtils.hasText(realGcNumber) && !"#".equals(realGcNumber)) {
            financialClbrCodeInfoVO.setClbrCode(realGcNumber);
            Map<String, String> metaInfoByClbrCode = BillUtils.getMetaInfoByClbrCode(realGcNumber);
            financialClbrCodeInfoVO.setDefineCode(metaInfoByClbrCode.get("code"));
            financialClbrCodeInfoVO.setMenuId(UUIDUtils.newUUIDStr());
            financialClbrCodeInfoVO.setMenuTitle(metaInfoByClbrCode.get("title"));
            financialClbrCodeInfoVO.setVerifyCode(BillUtils.getBillVerifyCode(realGcNumber));
        } else {
            if (Objects.isNull(this.financialCheckClbrCodeExtendService)) {
                throw new BusinessRuntimeException("\u534f\u540c\u7801\u4e3a\u7a7a\uff0c\u4e14\u534f\u540c\u7801\u6269\u5c55\u6536\u96c6\u5668\u672a\u6ce8\u518c\u3002");
            }
            List<String> clbrCodes = this.financialCheckClbrCodeExtendService.getClbrCodes(relatedItemEO.getGcNumber());
            if (CollectionUtils.isEmpty(clbrCodes)) {
                throw new BusinessRuntimeException("\u6839\u636e\u6253\u5305\u7801\u672a\u627e\u5230\u534f\u540c\u7801\u3002");
            }
            String clbrCode = clbrCodes.get(0);
            Map<String, String> metaInfoByClbrCode = BillUtils.getMetaInfoByClbrCode(clbrCode);
            String clbrBillDefine = metaInfoByClbrCode.get("code");
            if (clbrCodes.size() == 1) {
                financialClbrCodeInfoVO.setClbrCode(clbrCode);
                financialClbrCodeInfoVO.setDefineCode(clbrBillDefine);
                financialClbrCodeInfoVO.setMenuId(UUIDUtils.newUUIDStr());
                financialClbrCodeInfoVO.setMenuTitle(metaInfoByClbrCode.get("title"));
                financialClbrCodeInfoVO.setVerifyCode(BillUtils.getBillVerifyCode(clbrCode));
            } else {
                String billListDefine = this.financialCheckClbrCodeExtendService.getBillListDefine(clbrBillDefine);
                if (!StringUtils.hasText(billListDefine)) {
                    throw new BusinessRuntimeException("\u5355\u636e\u5b9a\u4e49" + clbrBillDefine + "\u672a\u914d\u7f6e\u8981\u7a7f\u900f\u7684\u5355\u636e\u5217\u8868, \u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u8fdb\u884c\u914d\u7f6e");
                }
                financialClbrCodeInfoVO.setDefineCode(billListDefine);
                financialClbrCodeInfoVO.setClbrCode(String.join((CharSequence)",", clbrCodes));
                financialClbrCodeInfoVO.setMenuId(UUIDUtils.newUUIDStr());
                financialClbrCodeInfoVO.setMenuTitle("\u5355\u636e\u5217\u8868");
            }
        }
        return financialClbrCodeInfoVO;
    }

    @Override
    public boolean checkUnitState(UnitCheckParam unitCheckParam) {
        List groupIds = unitCheckParam.getGroupIds();
        if (!CollectionUtils.isEmpty(groupIds)) {
            List items = this.voucherItemDao.findByCheckIds((Collection)groupIds);
            Integer maxPeriod = items.stream().map(GcRelatedItemEO::getCheckPeriod).max(Integer::compareTo).get();
            GcRelatedItemEO item = (GcRelatedItemEO)items.get(0);
            UnitStateUtils.checkUnitStateIsOpen(item.getUnitId(), item.getOppUnitId(), item.getCheckYear(), maxPeriod);
        }
        return true;
    }

    @Override
    public boolean checkCanDoManualCheck(ManualCheckParam param) {
        List sameGcNumberItems;
        Set sameGcNumberIds;
        Set itemIds = param.getItemIds();
        List items = this.relatedItemQueryService.queryByIds((Collection)itemIds);
        HashSet<String> gcNumbers = new HashSet<String>();
        HashSet<String> units = new HashSet<String>();
        HashSet<String> originalCurrencyS = new HashSet<String>();
        for (GcRelatedItemEO item2 : items) {
            gcNumbers.add(item2.getGcNumber());
            if (item2.getOppUnitId() != null) {
                units.add(item2.getOppUnitId());
            }
            if (item2.getUnitId() != null) {
                units.add(item2.getUnitId());
            }
            originalCurrencyS.add(item2.getOriginalCurr());
        }
        long unitCount = items.stream().map(GcRelatedItemEO::getUnitId).distinct().count();
        if (units.size() != 2 || unitCount != 2L) {
            throw new BusinessRuntimeException("\u53c2\u4e0e\u624b\u5de5\u5bf9\u8d26\u7684\u6570\u636e\u672c\u5bf9\u65b9\u5355\u4f4d\u53ea\u80fd\u6709\u4e24\u5bb6");
        }
        if (gcNumbers.size() > 1) {
            throw new BusinessRuntimeException("\u534f\u540c\u7801\u4e0d\u4e00\u81f4\uff0c\u8bf7\u9009\u62e9\u534f\u540c\u7801\u4e00\u81f4\u7684\u6570\u636e");
        }
        if (originalCurrencyS.size() > 1) {
            throw new BusinessRuntimeException("\u539f\u5e01\u5e01\u79cd\u4e0d\u4e00\u81f4\uff0c\u8bf7\u9009\u62e9\u539f\u5e01\u5e01\u79cd\u76f8\u540c\u7684\u6570\u636e");
        }
        GcRelatedItemEO firstItem = (GcRelatedItemEO)items.get(0);
        UnitStateUtils.checkUnitStateIsOpen(firstItem.getUnitId(), firstItem.getOppUnitId(), param.getYear(), param.getPeriod());
        String gcNumber = firstItem.getGcNumber();
        if (!"SystemDefault".equals(gcNumber) && !itemIds.equals(sameGcNumberIds = (sameGcNumberItems = this.relatedItemQueryService.queryByGcNumberAndUnit(gcNumber, firstItem.getUnitId(), firstItem.getOppUnitId(), firstItem.getAcctYear())).stream().filter(item -> Objects.isNull(item.getCheckId())).map(DefaultTableEntity::getId).collect(Collectors.toSet()))) {
            throw new BusinessRuntimeException("\u5b58\u5728\u672a\u88ab\u9009\u62e9\u7684\u8be5\u534f\u540c\u7801\u6570\u636e,\u8bf7\u5c06\u8be5\u534f\u540c\u7801\u6570\u636e\u5168\u90e8\u9009\u62e9");
        }
        return true;
    }

    private List<ColumnModelDefine> getExtendFields() {
        Field[] fields;
        HashSet<String> excludeFiels = new HashSet<String>();
        Object[] declaredFields = GcRelatedItemEO.class.getDeclaredFields();
        Object[] superDeclaredFields = GcRelatedItemEO.class.getSuperclass().getDeclaredFields();
        for (Field declaredField : fields = (Field[])ArrayUtils.addAll((Object[])declaredFields, (Object[])superDeclaredFields)) {
            DBColumn annotation = declaredField.getAnnotation(DBColumn.class);
            if (!Objects.nonNull(annotation)) continue;
            String nameInDB = annotation.nameInDB();
            if (StringUtils.hasText(nameInDB)) {
                excludeFiels.add(nameInDB.toUpperCase(Locale.ROOT));
                continue;
            }
            excludeFiels.add(declaredField.getName().toUpperCase(Locale.ROOT));
        }
        String outerDataSourceCode = OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource");
        TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode("GC_RELATED_ITEM", outerDataSourceCode);
        List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(tableModelDefineByCode.getID());
        return columnModelDefinesByTable.stream().filter(columnModelDefine -> !excludeFiels.contains(columnModelDefine.getCode())).collect(Collectors.toList());
    }

    private List<DimensionVO> convertDimVO(List<ColumnModelDefine> extendFields) {
        return extendFields.stream().map(item -> {
            DimensionVO dimensionVO = new DimensionVO();
            dimensionVO.setCode(item.getCode());
            dimensionVO.setTitle(item.getTitle());
            String referTableID = item.getReferTableID();
            if (StringUtils.hasText(referTableID)) {
                TableModelDefine tableModelDefineById = this.dataModelService.getTableModelDefineById(referTableID);
                dimensionVO.setDictTableName(tableModelDefineById.getName());
                dimensionVO.setReferField(tableModelDefineById.getName());
            }
            return dimensionVO;
        }).collect(Collectors.toList());
    }
}

