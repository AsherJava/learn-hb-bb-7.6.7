/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.cache.internal.redis.IRedisLock
 */
package com.jiuqi.gcreport.definition.impl.sqlutil.temporary.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.temporary.dao.IdTemporaryDao;
import com.jiuqi.np.cache.internal.redis.IRedisLock;
import java.time.Instant;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
public class IdTemporaryClearDaoImpl {
    @Autowired
    private IdTemporaryDao idTemporaryDao;
    @Autowired
    private IRedisLock redisLock;
    private final String LOCKNAME = "DELETE_LOCK_GC_IDTEMPORARY";
    private Logger logger = LoggerFactory.getLogger(IdTemporaryClearDaoImpl.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Scheduled(cron="0 0 0 * * ?")
    public void cleanTimeoutRequest() {
        Instant thirtyMinutesAgo = Instant.now().minusSeconds(1800L);
        Date dateThirtyMinutesAgo = Date.from(thirtyMinutesAgo);
        if (ObjectUtils.isEmpty(this.redisLock) || StringUtils.isEmpty((String)this.redisLock.lock("DELETE_LOCK_GC_IDTEMPORARY"))) {
            return;
        }
        try {
            this.idTemporaryDao.deleteByCreateTime(dateThirtyMinutesAgo);
        }
        catch (Exception e) {
            this.logger.error("\u4e34\u65f6\u8868\u5b9a\u65f6\u6e05\u7406\u5f02\u5e38", e);
        }
        finally {
            this.redisLock.unLock("DELETE_LOCK_GC_IDTEMPORARY");
        }
    }
}

