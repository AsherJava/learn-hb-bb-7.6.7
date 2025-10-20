/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO
 *  com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService
 *  com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.gcreport.financialcheckImpl.clbr.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckImpl.clbr.dao.ClbrVoucherItemTempDao;
import com.jiuqi.gcreport.financialcheckImpl.clbr.entity.ClbrVoucherItemTempEO;
import com.jiuqi.gcreport.financialcheckImpl.clbr.service.ClbrCheckService;
import com.jiuqi.gcreport.financialcheckImpl.util.UnitStateUtils;
import com.jiuqi.gcreport.financialcheckapi.clbr.vo.ClbrVoucherItemVO;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemQueryService;
import com.jiuqi.xlib.utils.StringUtil;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ClbrCheckServiceImpl
implements ClbrCheckService {
    @Autowired
    ClbrVoucherItemTempDao clbrVoucherItemTempDao;
    @Autowired
    GcRelatedItemDao voucherItemDao;
    @Autowired
    GcDbLockService iLockService;
    @Autowired
    GcRelatedItemQueryService relatedItemQueryService;
    @Autowired
    GcRelatedItemCommandService gcRelatedItemCommandService;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public void updateVoucherGcNumber(List<ClbrVoucherItemVO> items) {
        String batchId = UUIDUtils.newUUIDStr();
        List tempItems = items.stream().map(item -> {
            ClbrVoucherItemTempEO temp = ClbrVoucherItemTempEO.convertVO2EO(item);
            temp.setBatchId(batchId);
            return temp;
        }).collect(Collectors.toList());
        this.clbrVoucherItemTempDao.addBatch(tempItems);
        try {
            List allItems = this.voucherItemDao.queryNeedCancelClbrItems(batchId);
            if (CollectionUtils.isEmpty((Collection)allItems)) {
                return;
            }
            Set needCancelCheckIds = allItems.stream().filter(voucherItem -> !StringUtil.isEmpty((String)voucherItem.getCheckId())).map(GcRelatedItemEO::getCheckId).collect(Collectors.toSet());
            List needCancelCheckItems = this.relatedItemQueryService.findByCheckIds(needCancelCheckIds);
            Set allIds = allItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
            if (!needCancelCheckIds.isEmpty()) {
                Set ids = needCancelCheckItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toSet());
                allIds.addAll(ids);
            }
            UnitStateUtils.checkReconciliationPeriodStatus(needCancelCheckItems);
            String lockId = this.iLockService.tryLock(allIds, "\u53d6\u6d88\u534f\u540c", "checkCenter");
            if (StringUtils.isEmpty(lockId)) {
                String userName = this.iLockService.queryUserNameByInputItemId(allIds, "checkCenter");
                throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
            }
            try {
                this.gcRelatedItemCommandService.cancelCheck(needCancelCheckItems, false);
                if (!needCancelCheckIds.isEmpty()) {
                    allItems = this.voucherItemDao.queryNeedCancelClbrItems(batchId);
                }
                this.voucherItemDao.deleteGcNumber(allItems);
            }
            finally {
                this.iLockService.unlock(lockId);
            }
        }
        finally {
            this.clbrVoucherItemTempDao.deleteByBatchId(batchId);
        }
    }
}

