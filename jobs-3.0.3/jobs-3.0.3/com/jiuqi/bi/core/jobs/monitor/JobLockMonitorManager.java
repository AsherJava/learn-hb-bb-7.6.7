/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 */
package com.jiuqi.bi.core.jobs.monitor;

import com.jiuqi.bi.core.jobs.IJobCache;
import com.jiuqi.bi.core.jobs.JobCacheProviderManager;
import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobLockMonitorManager {
    private static final Logger logger = LoggerFactory.getLogger(JobLockMonitorManager.class);
    private static final JobLockMonitorManager instance = new JobLockMonitorManager();
    private static final String OBTAIN_LOCK = "OBTAIN_LOCK";
    private static final String OBTAIN_LOCK_FAILED = "OBTAIN_LOCK_FAILED";
    private static final String RELEASE_LOCK = "RELEASE_LOCK";
    private static final String NODE_CACHE_KEY = "LOCK_LOG_";
    private String nodeName;
    private Set<String> instanceList = new HashSet<String>();
    private Set<String> lockNameList = new HashSet<String>();

    public static JobLockMonitorManager getInstance() {
        return instance;
    }

    public void obtainLock(String message, String instanceName, String lockName) {
        LockLog lockLog = new LockLog(this.nodeName, message, OBTAIN_LOCK, System.currentTimeMillis(), lockName, instanceName);
        this.putCache(lockLog);
    }

    public void obtainLockFailed(String message, String instanceName, String lockName) {
        LockLog lockLog = new LockLog(this.nodeName, message, OBTAIN_LOCK_FAILED, System.currentTimeMillis(), lockName, instanceName);
        this.putCache(lockLog);
    }

    public void releaseLock(String message, String instanceName, String lockName) {
        LockLog lockLog = new LockLog(this.nodeName, message, RELEASE_LOCK, System.currentTimeMillis(), lockName, instanceName);
        this.putCache(lockLog);
    }

    private void putCache(LockLog info) {
        this.instanceList.add(info.getInstanceName());
        this.lockNameList.add(info.getLockName());
        IJobCache jobCache = JobCacheProviderManager.getInstance().getLockLogCache();
        jobCache.put(this.getKey(info), info);
        String timestamp = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        logger.debug(String.format("\u8ba1\u5212\u4efb\u52a1\uff1a%s \u8282\u70b9%s\uff0c\u65b9\u6cd5%s \uff1a%s %s:%s", timestamp, this.nodeName, info.getMessage(), info.getInstanceName(), info.getLockName(), info.getType()));
    }

    private String getKey(LockLog lockLog) {
        if (this.nodeName == null) {
            this.nodeName = DistributionManager.getInstance().getMachineName();
        }
        return this.getKey(lockLog, this.nodeName);
    }

    private String getKey(LockLog lockLog, String nodeName) {
        return String.format("%s%s:%s-%s-%s", NODE_CACHE_KEY, nodeName, lockLog.getInstanceName(), lockLog.getLockName(), lockLog.getType());
    }

    private List<String> getKeys(String nodeName, String instanceName, String lockName) {
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(String.format("%s%s:%s-%s-%s", NODE_CACHE_KEY, nodeName, instanceName, lockName, OBTAIN_LOCK));
        keys.add(String.format("%s%s:%s-%s-%s", NODE_CACHE_KEY, nodeName, instanceName, lockName, OBTAIN_LOCK_FAILED));
        keys.add(String.format("%s%s:%s-%s-%s", NODE_CACHE_KEY, nodeName, instanceName, lockName, RELEASE_LOCK));
        return keys;
    }

    public Map<String, Object> getAllNodeInfo() {
        List nodes = null;
        try {
            nodes = DistributionManager.getInstance().getAllNode();
            ArrayList<String> keys = new ArrayList<String>();
            for (Node node : nodes) {
                for (String instanceName : this.instanceList) {
                    for (String lockName : this.lockNameList) {
                        keys.addAll(this.getKeys(node.getMachineName(), instanceName, lockName));
                    }
                }
            }
            IJobCache jobCache = JobCacheProviderManager.getInstance().getLockLogCache();
            return jobCache.gets(keys);
        }
        catch (DistributionException e) {
            throw new RuntimeException(e);
        }
    }

    public static class LockLog
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String nodeName;
        private final String message;
        private final String type;
        private final long time;
        private final String lockName;
        private final String instanceName;

        public String getNodeName() {
            return this.nodeName;
        }

        public String getType() {
            return this.type;
        }

        public long getTime() {
            return this.time;
        }

        public String getMessage() {
            return this.message;
        }

        public String getLockName() {
            return this.lockName;
        }

        public String getInstanceName() {
            return this.instanceName;
        }

        public LockLog(String nodeName, String message, String type, long time, String lockName, String instanceName) {
            this.nodeName = nodeName;
            this.message = message;
            this.type = type;
            this.time = time;
            this.lockName = lockName;
            this.instanceName = instanceName;
        }
    }
}

