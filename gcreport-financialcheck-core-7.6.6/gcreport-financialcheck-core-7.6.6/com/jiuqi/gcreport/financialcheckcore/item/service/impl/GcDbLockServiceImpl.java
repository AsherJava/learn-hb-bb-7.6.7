/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Propagation
 */
package com.jiuqi.gcreport.financialcheckcore.item.service.impl;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcDbLockDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcDbLockEO;
import com.jiuqi.gcreport.financialcheckcore.item.service.GcDbLockService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.StringUtils;

@Service
public class GcDbLockServiceImpl
implements GcDbLockService {
    private Logger logger = LoggerFactory.getLogger(GcDbLockServiceImpl.class);
    private static ThreadLocal<String> threadIdCache = new ThreadLocal();
    @Autowired
    private GcDbLockDao gcDbLockDao;

    @Override
    public String tryLock(Collection<String> itemIds, String lockSrc, String isolationField) {
        String lockId = this.tryLock(itemIds, 15L, TimeUnit.SECONDS, lockSrc, isolationField);
        if (StringUtils.isEmpty(lockId)) {
            ((GcDbLockServiceImpl)SpringContextUtils.getBean(GcDbLockServiceImpl.class)).deleteExpiredData();
        }
        return lockId;
    }

    @Override
    public String tryLock(Collection<String> itemIds, long timeout, TimeUnit unit, String lockSrc, String isolationField) {
        if (CollectionUtils.isEmpty(itemIds)) {
            return "";
        }
        String ip = DistributionManager.getInstance().getIp();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusNanos(unit.toNanos(timeout));
        while (true) {
            String lockId = UUIDUtils.newUUIDStr();
            try {
                return ((GcDbLockServiceImpl)SpringContextUtils.getBean(GcDbLockServiceImpl.class)).saveILock(itemIds, lockId, lockSrc, ip, isolationField);
            }
            catch (Exception e) {
                String oldLockId = ((GcDbLockServiceImpl)SpringContextUtils.getBean(GcDbLockServiceImpl.class)).tryReentrant(itemIds, isolationField);
                if (StringUtils.hasText(oldLockId)) {
                    return oldLockId;
                }
                ((GcDbLockServiceImpl)SpringContextUtils.getBean(GcDbLockServiceImpl.class)).deleteExpiredData();
                try {
                    TimeUnit.MILLISECONDS.sleep(300L);
                    continue;
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    return null;
                }
                if (LocalDateTime.now().isBefore(endTime)) continue;
                return null;
            }
            break;
        }
    }

    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public String tryReentrant(Collection<String> itemIds, String isolationField) {
        List<GcDbLockEO> gcDbLockS = this.gcDbLockDao.listByItems(itemIds, isolationField);
        if (CollectionUtils.isEmpty(gcDbLockS)) {
            return null;
        }
        if (itemIds.size() > gcDbLockS.size()) {
            return null;
        }
        List threadIds = gcDbLockS.stream().map(GcDbLockEO::getThreadId).distinct().collect(Collectors.toList());
        String threadId = this.getThreadId();
        if (threadIds.size() == 1 && threadId.equals(threadIds.get(0))) {
            List lockIds = gcDbLockS.stream().map(GcDbLockEO::getLockId).collect(Collectors.toList());
            String lockId = (String)lockIds.get(0);
            this.gcDbLockDao.updateReentrantCountByLockId(lockId, true);
            return lockId;
        }
        return null;
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void unlock(String lockId) {
        if (StringUtils.isEmpty(lockId)) {
            return;
        }
        try {
            this.gcDbLockDao.updateReentrantCountByLockId(lockId, false);
            int n = this.gcDbLockDao.deleteByLockId(lockId);
        }
        catch (Exception e) {
            String format = MessageFormat.format("\u89e3\u9501\u5931\u8d25\uff0c\u9501\u6807\u8bc6\u3010{}\u3011", lockId);
            this.logger.error(format, e);
        }
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public void deleteExpiredData() {
        try {
            List<String> lockIds = this.gcDbLockDao.queryExpiredData(LocalDateTime.now().minusMinutes(10L).toInstant(ZoneOffset.of("+8")).toEpochMilli());
            if (lockIds != null && lockIds.size() > 0) {
                lockIds.forEach(lockId -> this.gcDbLockDao.deleteExpiredDataByLockId((String)lockId));
            }
        }
        catch (Exception e) {
            this.logger.error("\u6e05\u9664\u5185\u90e8\u8868\u8fc7\u671f\u9501\u53d1\u751f\u5f02\u5e38", e);
        }
    }

    @Override
    public String queryUserNameByInputItemId(Collection<String> itemIds, String isolationField) {
        List<String> names = this.gcDbLockDao.queryUserNameByInputItemId(itemIds, isolationField);
        for (String name : names) {
            if (name == null || StringUtils.isEmpty(name)) continue;
            return name;
        }
        return "";
    }

    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    public String saveILock(Collection<String> itemIds, String lockId, String lockSrc, String ip, String isolationField) {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        long createTime = System.currentTimeMillis();
        String threadId = this.getThreadId();
        List iLockItems = itemIds.stream().map(itemId -> {
            GcDbLockEO iLock = new GcDbLockEO(lockId, (String)itemId, createTime, lockSrc, ip, Objects.isNull(user) ? "" : user.getName(), isolationField, threadId, 0);
            return iLock;
        }).collect(Collectors.toList());
        this.gcDbLockDao.addBatch(iLockItems);
        return lockId;
    }

    private String getThreadId() {
        String threadId = threadIdCache.get();
        if (!StringUtils.hasText(threadId)) {
            threadId = UUID.randomUUID().toString();
            threadIdCache.set(threadId);
        }
        return threadId;
    }
}

