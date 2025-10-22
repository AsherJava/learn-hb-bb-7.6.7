/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.financialcheckcore.item.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.item.RelatedItemSaveParam;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.item.monitor.GcRelatedItemMonitor;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcRelatedItemCommandService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GcRelatedItemCommandServiceImpl
implements GcRelatedItemCommandService {
    @Autowired
    private GcRelatedItemDao gcRelatedItemDao;
    @Autowired
    private GcDbLockService gcDbLockService;
    @Autowired
    private GcRelatedItemMonitor monitor;

    private void batchAdd(List<GcRelatedItemEO> items) {
        if (!CollectionUtils.isEmpty(items)) {
            this.gcRelatedItemDao.addBatch(items);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public void batchDelete(List<String> ids) {
        String lockId = this.gcDbLockService.tryLock(ids, "\u5220\u9664\u6570\u636e", "checkCenter");
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.gcDbLockService.queryUserNameByInputItemId(ids, "checkCenter");
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        try {
            this.monitor.beforeDelete(ids);
            this.gcRelatedItemDao.deleteByIds(ids);
        }
        finally {
            this.gcDbLockService.unlock(lockId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void batchUpdateBaseInfo(List<GcRelatedItemEO> items) {
        List<String> ids = items.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        String lockId = this.gcDbLockService.tryLock(ids, "\u4fee\u6539\u6570\u636e\u4fe1\u606f", "checkCenter");
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.gcDbLockService.queryUserNameByInputItemId(ids, "checkCenter");
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        try {
            List<GcRelatedItemEO> needDeleteItems = this.gcRelatedItemDao.queryByIds(ids);
            if (CollectionUtils.isEmpty(needDeleteItems) || needDeleteItems.stream().anyMatch(item -> CheckStateEnum.CHECKED.name().equals(item.getChkState()))) {
                throw new BusinessRuntimeException("\u5b58\u5728\u5df2\u5bf9\u8d26\u7684\u6570\u636e,\u8bf7\u786e\u8ba4\u72b6\u6001\u540e\u91cd\u8bd5");
            }
            long recordTimestamp = this.gcRelatedItemDao.updateItemBaseInfo(items);
            Set<String> updatedIds = this.gcRelatedItemDao.countByIdAndRecordTimestamp(items.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()), recordTimestamp);
            if (updatedIds.size() != items.size()) {
                throw new BusinessRuntimeException("\u6570\u636e\u5df2\u88ab\u5176\u5b83\u64cd\u4f5c\u53d8\u66f4\uff0c\u8bf7\u91cd\u8bd5\u3002");
            }
            for (GcRelatedItemEO item2 : items) {
                item2.setRecordTimestamp(recordTimestamp);
            }
        }
        finally {
            this.gcDbLockService.unlock(lockId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateCheckInfo(List<GcRelatedItemEO> items, String dbCheckState, boolean isChangeScheme) {
        List<String> ids = items.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        this.monitor.beforeCheckInfoUpdate(items, dbCheckState);
        String lockId = this.gcDbLockService.tryLock(ids, "\u4fee\u6539\u5bf9\u8d26\u4fe1\u606f", "checkCenter");
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.gcDbLockService.queryUserNameByInputItemId(ids, "checkCenter");
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        try {
            long recordTimestamp = isChangeScheme ? this.gcRelatedItemDao.updateCheckInfoAndScheme(items, dbCheckState) : this.gcRelatedItemDao.updateCheckInfo(items, dbCheckState);
            Set<String> updatedIds = this.gcRelatedItemDao.countByIdAndRecordTimestamp(items.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()), recordTimestamp);
            if (updatedIds.size() != items.size()) {
                throw new BusinessRuntimeException("\u6570\u636e\u5df2\u88ab\u5176\u5b83\u64cd\u4f5c\u53d8\u66f4\uff0c\u8bf7\u91cd\u8bd5\u3002");
            }
            this.monitor.afterCheckInfoUpdate(items, dbCheckState);
            items.forEach(item -> item.setRecordTimestamp(recordTimestamp));
        }
        finally {
            this.gcDbLockService.unlock(lockId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public void updateCheckSchemeInfo(List<GcRelatedItemEO> items) {
        List<String> ids = items.stream().map(DefaultTableEntity::getId).collect(Collectors.toList());
        String lockId = this.gcDbLockService.tryLock(ids, "\u4fee\u6539\u5bf9\u8d26\u65b9\u6848\u4fe1\u606f", "checkCenter");
        if (StringUtils.isEmpty(lockId)) {
            String userName = this.gcDbLockService.queryUserNameByInputItemId(ids, "checkCenter");
            throw new RuntimeException("\u6570\u636e\u6b63\u5728\u88ab\u7528\u6237\uff1a" + userName + "\u64cd\u4f5c\uff0c\u8bf7\u7a0d\u540e\u91cd\u8bd5");
        }
        try {
            long recordTimestamp = this.gcRelatedItemDao.updateCheckSchemeInfo(items);
            Set<String> updatedIds = this.gcRelatedItemDao.countByIdAndRecordTimestamp(items.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()), recordTimestamp);
            if (updatedIds.size() != items.size()) {
                throw new BusinessRuntimeException("\u6570\u636e\u5df2\u88ab\u5176\u5b83\u64cd\u4f5c\u53d8\u66f4\uff0c\u8bf7\u91cd\u8bd5\u3002");
            }
            items.forEach(item -> item.setRecordTimestamp(recordTimestamp));
        }
        finally {
            this.gcDbLockService.unlock(lockId);
        }
    }

    @Override
    public void cancelCheck(List<GcRelatedItemEO> items, boolean isChangeScheme) {
        this.setCancelCheckInfo(items);
        this.updateCheckInfo(items, CheckStateEnum.CHECKED.name(), isChangeScheme);
    }

    private void setCancelCheckInfo(List<GcRelatedItemEO> vchrItems) {
        if (org.springframework.util.CollectionUtils.isEmpty(vchrItems)) {
            return;
        }
        vchrItems.forEach(vchrItem -> {
            vchrItem.setChkCurr(null);
            vchrItem.setChkState(CheckStateEnum.UNCHECKED.name());
            vchrItem.setChkAmtD(0.0);
            vchrItem.setChkAmtC(0.0);
            vchrItem.setCheckId(null);
            vchrItem.setCheckTime(null);
            vchrItem.setCheckYear(null);
            vchrItem.setCheckPeriod(null);
            vchrItem.setChecker(null);
            vchrItem.setCheckType(null);
            vchrItem.setCheckMode(null);
        });
    }

    @Override
    @OuterTransaction
    public void doCheck(List<GcRelatedItemEO> items, boolean isChangeScheme) {
        this.updateCheckInfo(items, CheckStateEnum.UNCHECKED.name(), isChangeScheme);
    }

    @Override
    @OuterTransaction
    public void batchSave(RelatedItemSaveParam param) {
        List<GcRelatedItemEO> needDeleteItems;
        List<GcRelatedItemEO> needUpdateItems = param.getNeedUpdateItems();
        List<GcRelatedItemEO> newUpdateItems = this.monitor.beforeSave(needUpdateItems);
        newUpdateItems.forEach(newItem -> needUpdateItems.forEach(oldItem -> {
            if (oldItem.getId().equals(newItem.getId())) {
                oldItem.setRecordTimestamp(newItem.getRecordTimestamp());
            }
        }));
        param.setNeedUpdateItems(needUpdateItems);
        List<GcRelatedItemEO> needAddItems = param.getNeedAddItems();
        if (!CollectionUtils.isEmpty(needAddItems)) {
            this.batchAdd(needAddItems);
        }
        if (!CollectionUtils.isEmpty(needDeleteItems = param.getNeedDeleteItems())) {
            this.batchDelete(needDeleteItems.stream().map(DefaultTableEntity::getId).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(needUpdateItems)) {
            this.batchUpdateBaseInfo(needUpdateItems);
        }
        ArrayList<GcRelatedItemEO> needCheckItems = new ArrayList<GcRelatedItemEO>();
        needCheckItems.addAll(param.getNeedAddItems());
        needCheckItems.addAll(param.getNeedUpdateItems());
        if (CollectionUtils.isEmpty(needCheckItems)) {
            return;
        }
        this.monitor.afterSave(needCheckItems);
    }
}

