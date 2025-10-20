/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.dao.DuplicateKeyException
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.va.biz.cache;

import com.jiuqi.va.biz.dao.VaMonitorBillRuleDao;
import com.jiuqi.va.biz.domain.ConcurrentStatsCounter;
import com.jiuqi.va.biz.domain.VaMonitorBillRuleDO;
import com.jiuqi.va.biz.utils.ConcurrentHashMapUtils;
import com.jiuqi.va.biz.utils.Utils;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="billRuleMonitorCache")
public class BillRuleMonitorCache
implements SchedulingConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(BillRuleMonitorCache.class);
    private static final Map<String, Map<String, AtomicReference<ConcurrentStatsCounter>>> ruleStatistics = new ConcurrentHashMap<String, Map<String, AtomicReference<ConcurrentStatsCounter>>>();
    private static final Map<String, Map<String, String>> ruleIdNameMap = new ConcurrentHashMap<String, Map<String, String>>();
    @Autowired
    private VaMonitorBillRuleDao vaMonitorBillRuleDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value(value="${biz.monitor.billrule.period:1}")
    private int period;
    @Value(value="${biz.monitor.billrule.persistence:15}")
    private int persistence;
    @Value(value="${biz.monitor.billrule.enabled:false}")
    private boolean enabled;
    private String hostname;
    private int periodCount = -1;
    private final ReentrantLock lock = new ReentrantLock();
    private boolean firstExecute = true;
    private Date mperiod;
    private boolean debugEnabled = false;

    @Autowired
    public void setDebugEnabled() {
        this.debugEnabled = logger.isDebugEnabled();
    }

    public String getHostname() {
        if (this.hostname == null) {
            this.hostname = Utils.getHostName();
        }
        return this.hostname;
    }

    public static void updateRuleStatistics(String defineName, String ruleid, String ruleName, long time) {
        try {
            Map ruleInfo = ConcurrentHashMapUtils.computeIfAbsent(ruleStatistics, defineName, key -> new ConcurrentHashMap());
            AtomicReference stats = ConcurrentHashMapUtils.computeIfAbsent(ruleInfo, ruleid, key -> new AtomicReference<ConcurrentStatsCounter>(new ConcurrentStatsCounter()));
            ((ConcurrentStatsCounter)stats.get()).record(time);
            Map stringStringMap = ConcurrentHashMapUtils.computeIfAbsent(ruleIdNameMap, defineName, key -> new ConcurrentHashMap());
            ConcurrentHashMapUtils.computeIfAbsent(stringStringMap, ruleid, key -> ruleName);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (!this.enabled) {
            return;
        }
        int period = Math.max(this.period, 1);
        this.mperiod = new Date();
        long l = this.mperiod.getTime();
        l -= l % 3600000L;
        this.mperiod.setTime(l);
        if (this.debugEnabled) {
            logger.debug("\u5f53\u524d\u7cfb\u7edf\u7684\u8d77\u59cb\u65f6\u95f4\uff1a{}", (Object)this.mperiod);
        }
        taskRegistrar.addFixedRateTask(this::periodTask, TimeUnit.HOURS.toMillis(period));
        long interval = this.persistence < 15 ? TimeUnit.MINUTES.toMillis(15L) : (this.period < 1 ? Math.min(TimeUnit.MINUTES.toMillis(60L), TimeUnit.MINUTES.toMillis(this.persistence)) : Math.min(TimeUnit.MINUTES.toMillis((long)this.period * 60L), TimeUnit.MINUTES.toMillis(this.persistence)));
        if (interval > 0L) {
            taskRegistrar.addFixedDelayTask(this::persistenceTask, interval);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void periodTask() {
        try {
            if (!this.lock.tryLock(60L, TimeUnit.SECONDS)) {
                logger.error("\u6267\u884c\u7edf\u8ba1\u5468\u671f\u83b7\u53d6\u9501\u5931\u8d25");
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e.getMessage(), e);
        }
        try {
            ++this.periodCount;
            ArrayList<VaMonitorBillRuleDO> list = new ArrayList<VaMonitorBillRuleDO>();
            for (Map.Entry<String, Map<String, AtomicReference<ConcurrentStatsCounter>>> definecodeMap : ruleStatistics.entrySet()) {
                Map<String, AtomicReference<ConcurrentStatsCounter>> value = definecodeMap.getValue();
                if (value == null) continue;
                for (String ruleId : value.keySet()) {
                    AtomicReference<ConcurrentStatsCounter> concurrentStatsCounterAtomicReference = value.get(ruleId);
                    ConcurrentStatsCounter concurrentStatsCounter = concurrentStatsCounterAtomicReference.get();
                    if (concurrentStatsCounter == null || concurrentStatsCounter.getCount().intValue() == 0) continue;
                    boolean b = concurrentStatsCounterAtomicReference.compareAndSet(concurrentStatsCounter, new ConcurrentStatsCounter());
                    if (!b) {
                        logger.error("\u6709\u5176\u4ed6\u7ebf\u7a0b\u6b63\u5728\u7edf\u8ba1\u89c4\u5219\u76d1\u63a7\u4fe1\u606f");
                        continue;
                    }
                    VaMonitorBillRuleDO vaMonitorBillRuleDO = this.handleDO(this.periodCount, definecodeMap.getKey(), ruleId, concurrentStatsCounter);
                    list.add(vaMonitorBillRuleDO);
                }
            }
            if (this.debugEnabled) {
                logger.debug("\u6267\u884c\u7edf\u8ba1\u5468\u671f");
            }
            this.insertOrUpdate(list);
            this.firstExecute = true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (this.periodCount > 0) {
                this.setMperiod();
            }
            this.lock.unlock();
        }
    }

    private void setMperiod() {
        int period = Math.max(this.period, 1);
        long l = TimeUnit.HOURS.toMillis(period);
        this.mperiod.setTime(l += this.mperiod.getTime());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void persistenceTask() {
        if (!this.lock.tryLock()) {
            if (this.debugEnabled) {
                logger.debug("\u5f53\u524d\u5b58\u5728\u6b63\u5728\u6267\u884c\u7684\u843d\u5e93\u64cd\u4f5c\uff0c\u5468\u671f\uff1a{}", (Object)(this.periodCount + 1));
            }
            return;
        }
        try {
            ArrayList<VaMonitorBillRuleDO> list = new ArrayList<VaMonitorBillRuleDO>();
            for (Map.Entry<String, Map<String, AtomicReference<ConcurrentStatsCounter>>> defineCodeMap : ruleStatistics.entrySet()) {
                Map<String, AtomicReference<ConcurrentStatsCounter>> value = defineCodeMap.getValue();
                if (value == null) continue;
                for (String ruleId : value.keySet()) {
                    AtomicReference<ConcurrentStatsCounter> concurrentStatsCounterAtomicReference = value.get(ruleId);
                    ConcurrentStatsCounter concurrentStatsCounter = concurrentStatsCounterAtomicReference.get();
                    if (concurrentStatsCounter == null || concurrentStatsCounter.getCount().intValue() == 0) continue;
                    ConcurrentStatsCounter snapshot = concurrentStatsCounter.snapshot();
                    VaMonitorBillRuleDO vaMonitorBillRuleDO = this.handleDO(this.periodCount + 1, defineCodeMap.getKey(), ruleId, snapshot);
                    list.add(vaMonitorBillRuleDO);
                }
            }
            if (this.debugEnabled) {
                logger.debug("\u6267\u884c\u843d\u5e93");
            }
            this.insertOrUpdate(list);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            this.lock.unlock();
        }
    }

    private VaMonitorBillRuleDO handleDO(int periodCountSnap, String definecode, String ruleId, ConcurrentStatsCounter snapshot) {
        VaMonitorBillRuleDO vaMonitorBillRuleDO = new VaMonitorBillRuleDO();
        vaMonitorBillRuleDO.setId(UUID.randomUUID().toString());
        vaMonitorBillRuleDO.setHostname(this.getHostname());
        vaMonitorBillRuleDO.setDefinecode(definecode);
        vaMonitorBillRuleDO.setRuleid(ruleId);
        vaMonitorBillRuleDO.setRulename(ruleIdNameMap.get(definecode).get(ruleId));
        vaMonitorBillRuleDO.setUpdatetime(new Date());
        vaMonitorBillRuleDO.setMperiod(this.mperiod);
        long l = snapshot.getTotalTime().longValue();
        int count = snapshot.getCount().intValue();
        vaMonitorBillRuleDO.setExecutecount(count);
        vaMonitorBillRuleDO.setTotaltime(l);
        vaMonitorBillRuleDO.setMaxtime(snapshot.getMaxTime().longValue());
        vaMonitorBillRuleDO.setMintime(snapshot.getMinTime().longValue());
        vaMonitorBillRuleDO.setAvgtime(l / (long)count);
        vaMonitorBillRuleDO.setPeriod(periodCountSnap);
        return vaMonitorBillRuleDO;
    }

    @Scheduled(fixedRate=1L, timeUnit=TimeUnit.DAYS, initialDelay=1L)
    public void clearInvalidData() {
        VaMonitorBillRuleDO vaMonitorBillRuleDO = new VaMonitorBillRuleDO();
        vaMonitorBillRuleDO.setUpdatetime(new Date(System.currentTimeMillis() - 604800000L));
        int i = this.vaMonitorBillRuleDao.deleteByUpdateTime(vaMonitorBillRuleDO);
        if (this.debugEnabled) {
            logger.debug("clearInvalidData \u6e05\u9664\u4e86{}\u6761", (Object)i);
        }
    }

    public void insertOrUpdate(List<VaMonitorBillRuleDO> vaMonitorBillRuleDOS) {
        if (CollectionUtils.isEmpty(vaMonitorBillRuleDOS)) {
            return;
        }
        if (this.firstExecute) {
            this.firstExecute = false;
            if (this.debugEnabled) {
                logger.debug("\u7b2c\u4e00\u6b21\u6267\u884c\u63d2\u5165, \u6279\u91cf\u63d2\u5165");
            }
            ArrayList<VaMonitorBillRuleDO> insert = new ArrayList<VaMonitorBillRuleDO>(500);
            for (int i = 0; i < vaMonitorBillRuleDOS.size(); ++i) {
                if (i == vaMonitorBillRuleDOS.size() - 1) {
                    insert.add(vaMonitorBillRuleDOS.get(i));
                    this.batchInsert(insert);
                    return;
                }
                if (i != 0 && i % 500 == 0) {
                    this.batchInsert(insert);
                    insert.clear();
                    insert.add(vaMonitorBillRuleDOS.get(i));
                    continue;
                }
                insert.add(vaMonitorBillRuleDOS.get(i));
            }
            return;
        }
        for (VaMonitorBillRuleDO vaMonitorBillRuleDO : vaMonitorBillRuleDOS) {
            int i = this.vaMonitorBillRuleDao.updateByCondition(vaMonitorBillRuleDO);
            if (i != 0) continue;
            try {
                i = this.vaMonitorBillRuleDao.insert((Object)vaMonitorBillRuleDO);
            }
            catch (DuplicateKeyException e) {
                int update = this.vaMonitorBillRuleDao.updateByCondition(vaMonitorBillRuleDO);
                if (update != 0) continue;
                logger.error("\u66f4\u65b0\u89c4\u5219\u76d1\u63a7\u4fe1\u606f\u5931\u8d25{}", (Object)vaMonitorBillRuleDO.toString());
                continue;
            }
            if (i != 0) continue;
            logger.error("\u63d2\u5165\u89c4\u5219\u76d1\u63a7\u4fe1\u606f\u5931\u8d25{}", (Object)vaMonitorBillRuleDO.toString());
        }
    }

    public void batchInsert(final List<VaMonitorBillRuleDO> vaMonitorBillRuleDOS) {
        String sql = "INSERT INTO VA_MONITOR_BILL_RULE(ID, HOSTNAME, DEFINECODE, RULEID, RULENAME, UPDATETIME, MPERIOD, EXECUTECOUNT, TOTALTIME, MAXTIME, MINTIME, AVGTIME, PERIOD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    VaMonitorBillRuleDO entity = (VaMonitorBillRuleDO)((Object)vaMonitorBillRuleDOS.get(i));
                    ps.setString(1, entity.getId());
                    ps.setString(2, entity.getHostname());
                    ps.setString(3, entity.getDefinecode());
                    ps.setString(4, entity.getRuleid());
                    ps.setString(5, entity.getRulename());
                    ps.setTimestamp(6, new Timestamp(entity.getUpdatetime().getTime()));
                    ps.setTimestamp(7, new Timestamp(entity.getMperiod().getTime()));
                    ps.setInt(8, entity.getExecutecount());
                    ps.setLong(9, entity.getTotaltime());
                    ps.setLong(10, entity.getMaxtime());
                    ps.setLong(11, entity.getMintime());
                    ps.setLong(12, entity.getAvgtime());
                    ps.setInt(13, entity.getPeriod());
                }

                public int getBatchSize() {
                    return vaMonitorBillRuleDOS.size();
                }
            });
            if (this.debugEnabled) {
                logger.debug("\u6279\u91cf\u63d2\u5165\u89c4\u5219\u76d1\u63a7\u4fe1\u606f\u6210\u529f{}", (Object)vaMonitorBillRuleDOS.size());
            }
        }
        catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }
}

