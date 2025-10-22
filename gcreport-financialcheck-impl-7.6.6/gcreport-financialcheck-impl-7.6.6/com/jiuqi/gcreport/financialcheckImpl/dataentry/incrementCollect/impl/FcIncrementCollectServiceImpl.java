/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gc.financial.status.enums.FinancialStatusEnum
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.RelatedItemSaveParam
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemTempDao
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemTempEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.dao.FcAbnormalDataDao;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.entity.FcAbnormalDataEO;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.FcIncrementCollectParam;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.FcIncrementCollectService;
import com.jiuqi.gcreport.financialcheckImpl.dataentry.incrementCollect.FcRelatedItemDataChecker;
import com.jiuqi.gcreport.financialcheckImpl.util.UnitStateUtils;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.RelatedItemSaveParam;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemTempDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemTempEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FcIncrementCollectServiceImpl
implements FcIncrementCollectService {
    @Autowired
    private GcRelatedItemTempDao relatedItemTempDao;
    @Autowired
    private GcRelatedItemQueryService relatedItemQueryService;
    @Autowired
    private GcDbLockService gcDbLockService;
    @Autowired
    private GcRelatedItemCommandService commandService;
    @Autowired
    DimensionService dimensionService;
    @Autowired
    FcRelatedItemDataChecker dataChecker;
    @Autowired
    FcAbnormalDataDao fcAbnormalDataDao;

    @Override
    @OuterTransaction
    public void collect(FcIncrementCollectParam param) {
        List<GcRelatedItemEO> incrementItems = param.getIncrementItems();
        String checkOrgType = FinancialCheckConfigUtils.getCheckOrgType();
        if (!CollectionUtils.isEmpty(incrementItems) && param.isDoCheckData()) {
            ArrayList<FcAbnormalDataEO> fcAbnormalDataS = new ArrayList<FcAbnormalDataEO>();
            Iterator<GcRelatedItemEO> iterator = incrementItems.iterator();
            while (iterator.hasNext()) {
                GcRelatedItemEO nextItem = iterator.next();
                String checkMsg = this.dataChecker.saveCheck(nextItem, checkOrgType);
                if (!StringUtils.hasText(checkMsg)) continue;
                FcAbnormalDataEO abnormalData = new FcAbnormalDataEO();
                abnormalData.setId(UUIDUtils.newUUIDStr());
                abnormalData.setInfo(checkMsg);
                abnormalData.setCreateTime(new Date());
                abnormalData.setSrcItemId(nextItem.getSrcItemId());
                abnormalData.setUnitId(nextItem.getUnitId());
                fcAbnormalDataS.add(abnormalData);
                iterator.remove();
            }
            if (!CollectionUtils.isEmpty(fcAbnormalDataS)) {
                this.fcAbnormalDataDao.addBatch(fcAbnormalDataS);
            }
        }
        this.collectWithLock(param);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void collectWithLock(FcIncrementCollectParam param) {
        List<GcRelatedItemEO> incrementItems = param.getIncrementItems();
        List<GcRelatedItemEO> sourceNotExistData = param.getSourceNotExistData();
        StringBuilder log = param.getLog();
        if (CollectionUtils.isEmpty(sourceNotExistData) && CollectionUtils.isEmpty(incrementItems)) {
            return;
        }
        String batchId = UUIDUtils.newUUIDStr();
        List itemTemps = incrementItems.stream().map(item -> new GcRelatedItemTempEO(batchId, item.getRuleChangeHandlerFlag(), item.getUnitId(), item.getSrcIteMassId())).collect(Collectors.toList());
        this.relatedItemTempDao.addBatch(itemTemps);
        try {
            List checkedItems;
            HashSet needLockIds = new HashSet();
            boolean isItemWay = ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay());
            List existRelatedItems = this.relatedItemQueryService.listExistRelatedItemsByBatchId(batchId, isItemWay);
            List existIds = existRelatedItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
            needLockIds.addAll(existIds);
            List existCheckIds = existRelatedItems.stream().filter(item -> StringUtils.hasText(item.getCheckId())).map(GcRelatedItemEO::getCheckId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(sourceNotExistData)) {
                log.append("\u672c\u6b21\u4efb\u52a1\u67e5\u8be2\u5230\u5728\u6e90\u8868\u4e2d\u4e0d\u5b58\u5728\u9700\u8981\u88ab\u5220\u9664\u7684\u6570\u636e\u6709").append(sourceNotExistData.size()).append("\u6761");
                List sourceNotExistDataIds = sourceNotExistData.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
                needLockIds.addAll(sourceNotExistDataIds);
                List sourceNotExistDataCheckIds = sourceNotExistData.stream().filter(item -> StringUtils.hasText(item.getCheckId())).map(GcRelatedItemEO::getCheckId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sourceNotExistDataCheckIds)) {
                    existCheckIds.addAll(sourceNotExistDataCheckIds);
                }
            }
            if (!CollectionUtils.isEmpty(existCheckIds) && !CollectionUtils.isEmpty((Collection)(checkedItems = this.relatedItemQueryService.findByCheckIds(existCheckIds)))) {
                List checkedItemIds = checkedItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
                needLockIds.addAll(checkedItemIds);
            }
            if (CollectionUtils.isEmpty(needLockIds)) {
                existRelatedItems = this.relatedItemQueryService.queryByIds(existIds);
                this.saveVoucherItems(existRelatedItems, incrementItems, sourceNotExistData, log);
                return;
            }
            String lockId = this.gcDbLockService.tryLock(needLockIds, "\u6570\u636e\u91c7\u96c6", "checkCenter");
            if (StringUtils.isEmpty(lockId)) {
                String userName = this.gcDbLockService.queryUserNameByInputItemId(needLockIds, "checkCenter");
                throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
            }
            try {
                existRelatedItems = this.relatedItemQueryService.queryByIds(existIds);
                this.saveVoucherItems(existRelatedItems, incrementItems, sourceNotExistData, log);
            }
            finally {
                this.gcDbLockService.unlock(lockId);
            }
        }
        finally {
            this.relatedItemTempDao.deleteItemTempsByBatchId(batchId);
        }
    }

    private void saveVoucherItems(List<GcRelatedItemEO> existRelatedItems, List<GcRelatedItemEO> incrementItems, List<GcRelatedItemEO> sourceNotExistData, StringBuilder log) {
        incrementItems.forEach(this::addItemInfo);
        ArrayList<GcRelatedItemEO> needUpdateItems = new ArrayList<GcRelatedItemEO>();
        ArrayList<Object> needAddItems = new ArrayList<Object>();
        ArrayList<GcRelatedItemEO> needDeleteItems = new ArrayList<GcRelatedItemEO>();
        ArrayList<GcRelatedItemEO> needRepeatedItems = new ArrayList<GcRelatedItemEO>();
        if (!CollectionUtils.isEmpty(existRelatedItems)) {
            boolean isItemWay = ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay());
            Collector<GcRelatedItemEO, ?, Map<String, List<GcRelatedItemEO>>> groupCondi = isItemWay ? Collectors.groupingBy(item -> item.getUnitId() + "|" + item.getSrcIteMassId() + "|" + item.getRuleChangeHandlerFlag()) : Collectors.groupingBy(GcRelatedItemEO::getSrcItemId);
            Map<String, List<GcRelatedItemEO>> oldExistItemS = existRelatedItems.stream().collect(groupCondi);
            List dims = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
            incrementItems.forEach(item -> {
                String srcItemId;
                String string = srcItemId = isItemWay ? item.getUnitId() + "|" + item.getSrcIteMassId() + "|" + item.getRuleChangeHandlerFlag() : item.getSrcItemId();
                if (oldExistItemS.containsKey(srcItemId)) {
                    GcRelatedItemEO oldItem = (GcRelatedItemEO)((List)oldExistItemS.get(srcItemId)).get(0);
                    if (this.dataChecker.isEqual((GcRelatedItemEO)item, oldItem, dims)) {
                        needRepeatedItems.add((GcRelatedItemEO)item);
                        return;
                    }
                    this.copyNewItem2OldItem((GcRelatedItemEO)item, oldItem);
                    if (!this.checkAmtIsAllZero(oldItem)) {
                        needUpdateItems.add(oldItem);
                    } else {
                        needDeleteItems.add(oldItem);
                    }
                } else if (!this.checkAmtIsAllZero((GcRelatedItemEO)item)) {
                    needAddItems.add(item);
                } else {
                    needRepeatedItems.add((GcRelatedItemEO)item);
                }
            });
        } else {
            incrementItems = incrementItems.stream().filter(item -> !this.checkAmtIsAllZero((GcRelatedItemEO)item)).collect(Collectors.toList());
            needAddItems.addAll(incrementItems);
        }
        if (!CollectionUtils.isEmpty(sourceNotExistData)) {
            needDeleteItems.addAll(sourceNotExistData);
        }
        this.filterClosedStatesDatas(needUpdateItems, needRepeatedItems);
        this.filterClosedStatesDatas(needDeleteItems, needRepeatedItems);
        this.commandService.batchSave(new RelatedItemSaveParam(needAddItems, needUpdateItems, needDeleteItems));
        log.append("\u672c\u6b21\u4efb\u52a1\u65b0\u589e").append(needAddItems.size()).append("\u6761, \u4fee\u6539").append(needUpdateItems.size()).append("\u6761, \u5220\u9664").append(needDeleteItems.size()).append("\u6761, \u8df3\u8fc7").append(needRepeatedItems.size()).append("\u6761\n");
    }

    public void filterClosedStatesDatas(List<GcRelatedItemEO> datas, List<GcRelatedItemEO> needRepeatedItems) {
        if (CollectionUtils.isEmpty(datas)) {
            return;
        }
        Iterator<GcRelatedItemEO> iterator = datas.iterator();
        ArrayList<FcAbnormalDataEO> abnormalDataS = new ArrayList<FcAbnormalDataEO>();
        while (iterator.hasNext()) {
            GcRelatedItemEO item = iterator.next();
            if (!StringUtils.hasText(item.getCheckId())) continue;
            String unitId = item.getUnitId();
            String oppUnitId = item.getOppUnitId();
            Integer year = item.getCheckYear();
            Integer period = item.getCheckPeriod();
            String errorMsg = "";
            YearPeriodDO periodDO = YearPeriodUtil.transform(null, (int)year, (int)4, (int)PeriodUtils.standardPeriod((int)period));
            GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)periodDO);
            GcOrgCacheVO mergeUnit = UnitStateUtils.getMergeUnit(instance, unitId, oppUnitId);
            if (Objects.isNull(mergeUnit)) {
                errorMsg = String.format("\u6570\u636e\u53d8\u66f4\u5931\u8d25\uff1a\u5355\u4f4d%1$s\u4e0e%2$s\u5728%3$s\u5e74%4$s\u6708\u7684\u5408\u5e76\u5355\u4f4d\u67e5\u8be2\u4e0d\u5230", unitId, oppUnitId, year, period);
            } else {
                FinancialStatusEnum state = UnitStateUtils.getState(mergeUnit.getCode(), year, period);
                if (FinancialStatusEnum.CLOSE.equals((Object)state)) {
                    errorMsg = String.format("\u6570\u636e\u53d8\u66f4\u5931\u8d25\uff1a\u5408\u5e76\u5355\u4f4d%1$s\u5728%2$s\u5e74%3$s\u6708\u5df2\u5173\u8d26\uff0c\u65e0\u6cd5\u6267\u884c\u6b64\u64cd\u4f5c\u3002", mergeUnit.getCode(), year, period);
                }
            }
            if (!StringUtils.hasText(errorMsg)) continue;
            FcAbnormalDataEO abnormalData = new FcAbnormalDataEO();
            abnormalData.setId(UUIDUtils.newUUIDStr());
            abnormalData.setInfo(errorMsg);
            abnormalData.setCreateTime(new Date());
            abnormalData.setSrcItemId(item.getSrcItemId());
            abnormalData.setUnitId(item.getUnitId());
            abnormalDataS.add(abnormalData);
            needRepeatedItems.add(item);
            iterator.remove();
        }
        if (!CollectionUtils.isEmpty(abnormalDataS)) {
            this.fcAbnormalDataDao.addBatch(abnormalDataS);
        }
    }

    private boolean checkAmtIsAllZero(GcRelatedItemEO item) {
        return Double.compare(item.getCredit(), 0.0) == 0 && Double.compare(item.getDebit(), 0.0) == 0 && Double.compare(item.getCreditOrig(), 0.0) == 0 && Double.compare(item.getDebitOrig(), 0.0) == 0;
    }

    private void copyNewItem2OldItem(GcRelatedItemEO newItem, GcRelatedItemEO oldItem) {
        oldItem.setAcctYear(newItem.getAcctYear());
        oldItem.setAcctPeriod(newItem.getAcctPeriod());
        oldItem.setUnitId(newItem.getUnitId());
        oldItem.setOppUnitId(newItem.getOppUnitId());
        oldItem.setSubjectCode(newItem.getSubjectCode());
        oldItem.setOriginalCurr(newItem.getOriginalCurr());
        oldItem.setCurrency(newItem.getCurrency());
        oldItem.setDebitOrig(newItem.getDebitOrig());
        oldItem.setCreditOrig(newItem.getCreditOrig());
        oldItem.setDebit(newItem.getDebit());
        oldItem.setCredit(newItem.getCredit());
        oldItem.setBillCode(newItem.getBillCode());
        oldItem.setCreateDate(newItem.getCreateDate());
        oldItem.setDigest(newItem.getDigest());
        oldItem.setGcNumber(newItem.getGcNumber());
        oldItem.setItemOrder(newItem.getItemOrder());
        oldItem.setMemo(newItem.getMemo());
        oldItem.setUpdateTime(newItem.getUpdateTime());
        oldItem.setVchrType(newItem.getVchrType());
        oldItem.setVchrNum(newItem.getVchrNum());
        oldItem.setRealGcNumber(newItem.getRealGcNumber());
        oldItem.setSrcVchrId(newItem.getSrcVchrId());
        oldItem.setVchrSourceType(newItem.getVchrSourceType());
        oldItem.setCfItemCode(newItem.getCfItemCode());
        oldItem.setVchrId(newItem.getVchrId());
        oldItem.setRuleChangeHandlerFlag(newItem.getRuleChangeHandlerFlag());
        oldItem.setReclassifySubjCode(newItem.getReclassifySubjCode());
        oldItem.setSrcItemId(newItem.getSrcItemId());
        oldItem.setSrcIteMassId(newItem.getSrcIteMassId());
        List dimensionS = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        if (!org.springframework.util.CollectionUtils.isEmpty(dimensionS)) {
            List dimensionCodes = dimensionS.stream().map(DimensionEO::getCode).collect(Collectors.toList());
            for (String dimensionCode : dimensionCodes) {
                oldItem.addFieldValue(dimensionCode, newItem.getFieldValue(dimensionCode.toUpperCase()));
            }
        }
    }

    public void addItemInfo(GcRelatedItemEO item) {
        item.setCreateTime(new Date());
        item.setRecver(new Long(0L));
        if (item.getDebit() == null) {
            item.setDebit(Double.valueOf(0.0));
        }
        if (item.getDebitOrig() == null) {
            item.setDebitOrig(Double.valueOf(0.0));
        }
        if (item.getCredit() == null) {
            item.setCredit(Double.valueOf(0.0));
        }
        if (item.getCreditOrig() == null) {
            item.setCreditOrig(Double.valueOf(0.0));
        }
        if (item.getChkAmtC() == null) {
            item.setChkAmtC(Double.valueOf(0.0));
        }
        if (item.getChkAmtD() == null) {
            item.setChkAmtD(Double.valueOf(0.0));
        }
        if (!StringUtils.hasText(item.getGcNumber())) {
            item.setGcNumber("SystemDefault");
        }
        if (!StringUtils.hasText(item.getId())) {
            item.setId(UUIDUtils.newUUIDStr());
        }
        if (!StringUtils.hasText(item.getChkState())) {
            item.setChkState(CheckStateEnum.UNCHECKED.name());
        }
    }

    @Override
    @OuterTransaction
    public void collect(List<GcRelatedItemEO> items) {
        if (!org.springframework.util.CollectionUtils.isEmpty(items)) {
            items.stream().collect(Collectors.groupingBy(item -> {
                if (ReconciliationModeEnum.ITEM.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
                    return item.getAcctYear();
                }
                return item.getAcctYear() + "|" + item.getAcctPeriod();
            })).values().forEach(values -> {
                Map<Boolean, List<GcRelatedItemEO>> group = values.stream().collect(Collectors.partitioningBy(item -> "1".equals(item.getFieldValue("DELETEFLAG"))));
                List sourceNotExistData = group.get(true);
                if (!CollectionUtils.isEmpty(sourceNotExistData)) {
                    sourceNotExistData = this.relatedItemQueryService.queryByIds((Collection)sourceNotExistData.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
                }
                FcIncrementCollectParam param = FcIncrementCollectParam.newInstance(group.get(false), sourceNotExistData, true);
                this.collect(param);
            });
        }
    }
}

