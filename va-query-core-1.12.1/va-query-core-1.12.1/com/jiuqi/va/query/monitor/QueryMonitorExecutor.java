/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.Utils
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.va.query.monitor;

import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.query.monitor.LogEventHandler;
import com.jiuqi.va.query.monitor.QueryLogEvent;
import com.jiuqi.va.query.monitor.QueryStateInfo;
import com.jiuqi.va.query.monitor.VaMonitorQueryDTO;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import com.jiuqi.va.query.util.QueryUtils;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class QueryMonitorExecutor
implements SchedulingConfigurer {
    @Value(value="${biz.monitor.query.period:1}")
    private int period;
    @Value(value="${biz.monitor.query.persistence:15}")
    private int persistence;
    @Value(value="${biz.monitor.query.enabled:false}")
    private boolean enabled;
    private final ReentrantLock lock = new ReentrantLock();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(LogEventHandler.class);
    private Date mperiod = new Date();
    private String hostname = "";
    Map<String, Map<String, AtomicReference<QueryStateInfo>>> queryStatistics = new ConcurrentHashMap<String, Map<String, AtomicReference<QueryStateInfo>>>();
    private boolean firstExecute = false;
    private int periodCount = -1;

    public String getHostname() {
        if (!StringUtils.hasText(this.hostname)) {
            this.hostname = Utils.getHostName();
        }
        if (!StringUtils.hasText(this.hostname)) {
            this.hostname = "unknow hostname";
        }
        return this.hostname;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.period = Math.max(1, this.period);
        this.persistence = Math.max(15, Math.min(this.persistence, this.period * 60 - 1));
        if (this.enabled) {
            this.mperiod = new Date();
            long l = this.mperiod.getTime();
            l -= l % 3600000L;
            this.mperiod.setTime(l);
            logger.info("\u67e5\u8be2\u76d1\u63a7\u6267\u884c\u5668\u542f\u52a8\uff0c\u7edf\u8ba1\u5468\u671f\u4e3a{}\u5c0f\u65f6\uff0c\u843d\u5e93\u5468\u671f\u4e3a{}\u5206\u949f", (Object)this.period, (Object)this.persistence);
            taskRegistrar.addFixedRateTask(this::periodTask, TimeUnit.HOURS.toMillis(this.period));
            taskRegistrar.addFixedDelayTask(this::persistenceTask, TimeUnit.MINUTES.toMillis(this.persistence));
        }
    }

    private List<VaMonitorQueryDTO> collectStatistics() {
        ArrayList<VaMonitorQueryDTO> stats = new ArrayList<VaMonitorQueryDTO>();
        for (Map.Entry<String, Map<String, AtomicReference<QueryStateInfo>>> entry : this.queryStatistics.entrySet()) {
            Map<String, AtomicReference<QueryStateInfo>> value = entry.getValue();
            if (value == null) continue;
            for (Map.Entry<String, AtomicReference<QueryStateInfo>> referenceEntry : value.entrySet()) {
                QueryStateInfo current = referenceEntry.getValue().get();
                if (current.getCount().intValue() <= 0) continue;
                VaMonitorQueryDTO vaMonitorQueryDTO = this.getVaMonitorQueryDTO(current);
                stats.add(vaMonitorQueryDTO);
            }
        }
        return stats;
    }

    private VaMonitorQueryDTO getVaMonitorQueryDTO(QueryStateInfo current) {
        VaMonitorQueryDTO vaMonitorQueryDTO = new VaMonitorQueryDTO();
        QueryStateInfo snapshot = current.snapshot();
        QueryLogEvent logEvent = snapshot.getLogEvent();
        vaMonitorQueryDTO.setId(DCQueryUUIDUtil.getUUIDStr());
        vaMonitorQueryDTO.setExecuteCount(snapshot.getCount().intValue());
        vaMonitorQueryDTO.setMaxTime(snapshot.getMaxTime().longValue());
        vaMonitorQueryDTO.setMinTime(snapshot.getMinTime().longValue());
        vaMonitorQueryDTO.setTotalTime(snapshot.getTotalTime().longValue());
        vaMonitorQueryDTO.setAvgTime(snapshot.getTotalTime().longValue() / snapshot.getCount().longValue());
        vaMonitorQueryDTO.setArgs(this.getArgs(logEvent.getArgs()));
        vaMonitorQueryDTO.setCode(logEvent.getCode());
        vaMonitorQueryDTO.setHostName(this.getHostname());
        vaMonitorQueryDTO.setBizName(logEvent.getBizName());
        vaMonitorQueryDTO.setEventTime(logEvent.getEventTime());
        return vaMonitorQueryDTO;
    }

    private String getArgs(Object[] args) {
        try {
            String argsStr = Arrays.toString(args);
            return argsStr.length() > 2000 ? argsStr.substring(0, 2000) : argsStr;
        }
        catch (Exception e) {
            logger.error("\u53c2\u6570\u8f6c\u5b57\u7b26\u4e32\u5931\u8d25", e);
            return null;
        }
    }

    private void saveStats(List<VaMonitorQueryDTO> stats, Date period) {
        if (stats.isEmpty()) {
            return;
        }
        if (this.firstExecute) {
            this.firstExecute = false;
            for (int i = 0; i < stats.size(); i += 500) {
                List<VaMonitorQueryDTO> batch = stats.subList(i, Math.min(i + 500, stats.size()));
                this.batchInsertStats(batch, period);
            }
        } else {
            this.inertOrUpdate(stats, period);
        }
    }

    private void inertOrUpdate(List<VaMonitorQueryDTO> stats, Date period) {
        for (VaMonitorQueryDTO stat : stats) {
            int i = this.updateStat(stat, period);
            if (i != 0) continue;
            try {
                this.batchInsertStats(Collections.singletonList(stat), period);
            }
            catch (Exception e) {
                logger.error("\u65b0\u589e\u76d1\u63a7\u6570\u636e\u5931\u8d25", e);
            }
        }
    }

    private void batchInsertStats(final List<VaMonitorQueryDTO> stats, final Date period) {
        if (stats.isEmpty()) {
            return;
        }
        String sql = "INSERT INTO va_monitor_query (id,hostname, code, bizname, args, mperiod, executecount, maxtime, mintime, totaltime,avgtime, createtime, eventtime) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                VaMonitorQueryDTO stat = (VaMonitorQueryDTO)stats.get(i);
                int index = 1;
                ps.setString(index++, stat.getId());
                ps.setString(index++, QueryMonitorExecutor.this.getHostname());
                ps.setString(index++, stat.getCode());
                ps.setString(index++, stat.getBizName());
                ps.setString(index++, stat.getArgs());
                ps.setTimestamp(index++, new Timestamp(period.getTime()));
                ps.setInt(index++, stat.getExecuteCount());
                ps.setLong(index++, stat.getMaxTime());
                ps.setLong(index++, stat.getMinTime());
                ps.setLong(index++, stat.getTotalTime());
                ps.setDouble(index++, stat.getAvgTime().longValue());
                ps.setTimestamp(index++, new Timestamp(new Date().getTime()));
                ps.setTimestamp(index, new Timestamp(stat.getEventTime().getTime()));
            }

            public int getBatchSize() {
                return stats.size();
            }
        });
        logger.info("\u6210\u529f\u63d2\u5165{}\u6761\u76d1\u63a7\u6570\u636e", (Object)stats.size());
    }

    private int updateStat(VaMonitorQueryDTO stat, Date period) {
        String sql = "UPDATE va_monitor_query SET executecount = ?, maxtime = ?, mintime = ?, totaltime = ?, avgtime = ?, eventtime = ? WHERE  mperiod = ? and hostname = ? and code = ? and bizname = ?";
        Object[] args = new Object[]{stat.getExecuteCount(), stat.getMaxTime(), stat.getMinTime(), stat.getTotalTime(), stat.getAvgTime(), new Timestamp(stat.getEventTime().getTime()), new Timestamp(period.getTime()), this.getHostname(), stat.getCode(), stat.getBizName()};
        return this.jdbcTemplate.update(sql, args);
    }

    private Date getLastPeriodInDatabase() {
        String sql = "SELECT max(mperiod) FROM va_monitor_query";
        try {
            return (Date)this.jdbcTemplate.queryForObject(sql, Date.class);
        }
        catch (Exception e) {
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void periodTask() {
        try {
            ++this.periodCount;
            if (this.lock.tryLock(60L, TimeUnit.SECONDS)) {
                ArrayList<VaMonitorQueryDTO> stats = new ArrayList<VaMonitorQueryDTO>();
                for (Map.Entry<String, Map<String, AtomicReference<QueryStateInfo>>> entry : this.queryStatistics.entrySet()) {
                    Map<String, AtomicReference<QueryStateInfo>> referenceMap = entry.getValue();
                    if (referenceMap == null) continue;
                    for (Map.Entry<String, AtomicReference<QueryStateInfo>> referenceEntry : referenceMap.entrySet()) {
                        AtomicReference<QueryStateInfo> reference = referenceEntry.getValue();
                        QueryStateInfo queryStateInfo = reference.get();
                        if (queryStateInfo == null || queryStateInfo.getCount().intValue() == 0) continue;
                        boolean compareAndSet = reference.compareAndSet(queryStateInfo, new QueryStateInfo());
                        if (!compareAndSet) {
                            logger.error("\u6709\u5176\u4ed6\u7ebf\u7a0b\u6b63\u5728\u7edf\u8ba1\u89c4\u5219\u76d1\u63a7\u4fe1\u606f");
                            continue;
                        }
                        stats.add(this.getVaMonitorQueryDTO(queryStateInfo));
                    }
                }
                this.inertOrUpdate(stats, this.mperiod);
                Date lastPeriod = this.getLastPeriodInDatabase();
                this.firstExecute = lastPeriod != null && lastPeriod.compareTo(this.mperiod) != 0;
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        finally {
            this.lock.unlock();
            if (this.periodCount > 0) {
                this.setMPeriod();
            }
        }
    }

    private void persistenceTask() {
        if (this.lock.isLocked()) {
            return;
        }
        if (this.lock.tryLock()) {
            try {
                List<VaMonitorQueryDTO> stats = this.collectStatistics();
                this.saveStats(stats, this.mperiod);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            finally {
                this.lock.unlock();
            }
        }
    }

    public void processCalculate(QueryLogEvent event) {
        QueryLogEvent myEvent = event;
        String code = myEvent.getCode();
        String defineCode = myEvent.getBizName();
        long duration = myEvent.getDuration();
        try {
            Map codeReference = QueryUtils.computeIfAbsent(this.queryStatistics, code, key -> new ConcurrentHashMap());
            AtomicReference reference = QueryUtils.computeIfAbsent(codeReference, defineCode, key -> new AtomicReference<QueryStateInfo>(new QueryStateInfo()));
            ((QueryStateInfo)reference.get()).record(duration, myEvent);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Scheduled(fixedRate=1L, timeUnit=TimeUnit.DAYS, initialDelay=1L)
    public void clearQueryMonitorInvalidData() {
        try {
            int update = this.jdbcTemplate.update("DELETE FROM va_monitor_query WHERE mperiod < ?", new Object[]{new Timestamp(System.currentTimeMillis() - 604800000L)});
            logger.info("\u6210\u529f\u5220\u9664{}\u6761\u6570\u636e", (Object)update);
        }
        catch (Exception e) {
            logger.error("\u5220\u96647\u5929\u524d\u7684\u6570\u636e\u5931\u8d25", e);
        }
    }

    private void setMPeriod() {
        long l = TimeUnit.HOURS.toMillis(Math.max(this.period, 1));
        this.mperiod.setTime(l += this.mperiod.getTime());
    }
}

