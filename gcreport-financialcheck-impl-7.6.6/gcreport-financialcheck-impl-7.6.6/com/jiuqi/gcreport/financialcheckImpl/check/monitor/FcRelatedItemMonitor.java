/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.monitor.GcRelatedItemMonitor
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gcreport.financialcheckImpl.check.monitor;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.OffsetRelatedItemDao;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.FinancialCheckRTOffsetExecutorImpl;
import com.jiuqi.gcreport.financialcheckImpl.offsetvchr.service.GcRelatedOffsetVoucherItemService;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeService;
import com.jiuqi.gcreport.financialcheckImpl.taskscheduling.param.RealTimeCheckOrOffsetParam;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.monitor.GcRelatedItemMonitor;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FcRelatedItemMonitor
implements GcRelatedItemMonitor {
    private static final Logger logger = LoggerFactory.getLogger(FcRelatedItemMonitor.class);
    @Autowired
    private FinancialCheckSchemeService schemeService;
    @Autowired
    TaskHandlerFactory taskHandlerFactory;
    @Autowired
    FinancialCheckRTOffsetExecutorImpl offsetExecutor;
    @Autowired
    GcRelatedItemQueryService itemQueryService;
    @Autowired
    OffsetRelatedItemDao offsetRelatedItemDao;

    public List<GcRelatedItemEO> beforeSave(List<GcRelatedItemEO> needUpdateItems) {
        ArrayList<GcRelatedItemEO> allItems = new ArrayList<GcRelatedItemEO>();
        ArrayList<GcRelatedItemEO> needCancelCheckSchemeItems = new ArrayList<GcRelatedItemEO>();
        needUpdateItems.forEach(item -> {
            if (StringUtils.hasText(item.getCheckRuleId())) {
                needCancelCheckSchemeItems.add((GcRelatedItemEO)item);
            } else {
                allItems.add((GcRelatedItemEO)item);
            }
        });
        if (!CollectionUtils.isEmpty(needCancelCheckSchemeItems)) {
            List<GcRelatedItemEO> itemEOS = this.schemeService.cancelCheckScheme(needCancelCheckSchemeItems);
            allItems.addAll(itemEOS);
        }
        return allItems;
    }

    public void afterSave(List<GcRelatedItemEO> eoList) {
        GcRelatedItemEO firstItem;
        String dataTime = "";
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            firstItem = eoList.get(0);
            PeriodWrapper periodWrapper = new PeriodWrapper(firstItem.getAcctYear().intValue(), 4, firstItem.getAcctPeriod().intValue());
            dataTime = periodWrapper.toString();
        } else {
            firstItem = eoList.get(0);
            Calendar calendar = Calendar.getInstance();
            int accPeriod = calendar.get(2) + 1;
            int currYear = calendar.get(1);
            if (currYear > firstItem.getAcctYear()) {
                accPeriod = 12;
            }
            PeriodWrapper periodWrapper = new PeriodWrapper(firstItem.getAcctYear().intValue(), 4, accPeriod);
            dataTime = periodWrapper.toString();
        }
        List<String> ids = eoList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        try {
            this.taskHandlerFactory.getMainTaskHandlerClient().startTask("FinancialCheckRealTimeCheckHandler", JsonUtils.writeValueAsString((Object)new RealTimeCheckOrOffsetParam(ids, dataTime)));
        }
        catch (Exception e) {
            logger.error("\u5173\u8054\u4ea4\u6613\u5206\u5f55\u4fdd\u5b58\u540e\u4e8b\u4ef6\u6267\u884c\u5931\u8d25,\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public void beforeCheckInfoUpdate(List<GcRelatedItemEO> eoList, String dbCheckState) {
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            if (CheckStateEnum.CHECKED.name().equals(dbCheckState)) {
                List<String> ids = eoList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
                this.offsetExecutor.deleteOffsetAndRel(ids);
            }
        } else if (CheckStateEnum.CHECKED.name().equals(dbCheckState)) {
            List ids = eoList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
            List item = this.itemQueryService.queryByIds(ids);
            Map periodToCheckIds = item.stream().filter(obj -> StringUtils.hasText(obj.getCheckId())).collect(Collectors.groupingBy(GcRelatedItemEO::getCheckId, Collectors.collectingAndThen(Collectors.toList(), list -> list.stream().mapToInt(GcRelatedItemEO::getCheckPeriod).min().orElse(Integer.MAX_VALUE)))).entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
            periodToCheckIds.forEach((period, checkIdList) -> ((GcRelatedOffsetVoucherItemService)SpringBeanUtils.getBean(GcRelatedOffsetVoucherItemService.class)).deleteByCheckIdAndOffsetPeriod((Collection<String>)checkIdList, (Integer)period));
        }
    }

    @OuterTransaction
    public void afterCheckInfoUpdate(List<GcRelatedItemEO> eoList, String dbCheckState) {
        GcRelatedItemEO firstItem;
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            GcRelatedItemEO firstItem2 = eoList.get(0);
            PeriodWrapper periodWrapper = new PeriodWrapper(firstItem2.getAcctYear().intValue(), 4, firstItem2.getAcctPeriod().intValue());
            String dataTime = periodWrapper.toString();
            List<String> ids = eoList.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
            try {
                this.taskHandlerFactory.getMainTaskHandlerClient().startTask("FinancialCheckRealTimeOffsetHandler", JsonUtils.writeValueAsString((Object)new RealTimeCheckOrOffsetParam(ids, dataTime)));
            }
            catch (Exception e) {
                logger.error("\u5173\u8054\u4ea4\u6613\u5206\u5f55\u5bf9\u8d26\u540e\u89e6\u53d1\u5b9e\u65f6\u62b5\u9500\u6267\u884c\u5931\u8d25,\u5931\u8d25\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else if (CheckStateEnum.UNCHECKED.name().equals(dbCheckState) && !"\u624b\u5de5\u6838\u5bf9".equals((firstItem = eoList.get(0)).getCheckType())) {
            ((GcRelatedOffsetVoucherItemService)SpringBeanUtils.getBean(GcRelatedOffsetVoucherItemService.class)).addByCheckGroup(eoList);
        }
    }

    public void beforeDelete(List<String> ids) {
        List items = this.itemQueryService.queryByIds(ids);
        this.beforeSave(items);
        if (ReconciliationModeEnum.BALANCE.equals((Object)FinancialCheckConfigUtils.getCheckWay())) {
            this.offsetRelatedItemDao.batchDeleteByRelatedItemId(ids);
        }
    }
}

