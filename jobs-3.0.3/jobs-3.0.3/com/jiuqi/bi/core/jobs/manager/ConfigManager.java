/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  org.quartz.JobDetail
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobKey
 */
package com.jiuqi.bi.core.jobs.manager;

import com.jiuqi.bi.core.jobs.BaseFactory;
import com.jiuqi.bi.core.jobs.CompositeJobFactoryManager;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobConfigBean;
import com.jiuqi.bi.core.jobs.bean.SchedulerState;
import com.jiuqi.bi.core.jobs.dao.ConfigDao;
import com.jiuqi.bi.core.jobs.dao.SchedulerStateDao;
import com.jiuqi.bi.core.jobs.manager.JobCategoryConfig;
import com.jiuqi.bi.core.jobs.message.MessageSender;
import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final ConfigManager instance = new ConfigManager();
    private static final String CONFIG_NAME_JOB_EXECUTABLE_NODE = "JOB_EXECUTABLE_NODE";
    private static final String CONFIG_NAME_NODE_MAX_JOBCOUNT = "NODE_MAX_JOBCOUNT";
    private static final String CONFIG_NAME_GLOBAL_MAX_JOBCOUNT = "GLOBAL_MAX_JOBCOUNT";
    private static final String CONFIG_NAME_GLOBAL_MAX_JOBCOUNT_ENABLE = "GLOBAL_MAX_JOBCOUNT_ENABLE";
    private static final String CONFIG_NAME_NODE_SCHEDULER_ENABLE = "NODE_SCHEDULER_ENABLE";
    private static final String CONFIG_NAME_JOB_DISPATCH_TYPE = "JOB_DISPATCH_TYPE";
    public static final String CONFIG_VALUE_JOB_DISPATCH_BY_TYPE = "BY_TYPE";
    public static final String CONFIG_VALUE_JOB_DISPATCH_BY_TAG = "BY_TAG";
    public static final String CONFIG_NAME_JOB_MATCH_TYPE = "JOB_MATCH_TYPE";
    public static final String CONFIG_VALUE_BY_MACHINE_NAME = "BY_MACHINE_NAME";
    public static final String CONFIG_VALUE_BY_APPLICATION_NAME = "BY_APPLICATION_NAME";
    public static final String CONFIG_NAME_CATE_WHITELIST_TAG = "CATE_WHITELIST_TAG";
    public static final String CONFIG_NAME_CATE_BLACKLIST_TAG = "CATE_BLACKLIST_TAG";
    public static final String CONFIG_NAME_CATE_WHITELIST_TYPE = "CATE_WHITELIST_TYPE";
    public static final String CONFIG_NAME_CATE_BLACKLIST_TYPE = "CATE_BLACKLIST_TYPE";
    public static final String CONFIG_NAME_CATE_UNEXECUTABLE = "CATE_UNEXECUTABLE";
    public static final String CONFIG_NAME_CATE_EXECUTABLE = "CATE_EXECUTABLE";
    public static final String CONFIG_NAME_CATE_UNEXECUTABLE_TAG = "TAG_UNEXECUTABLE";
    public static final String CONFIG_NAME_CATE_EXECUTABLE_TAG = "TAG_EXECUTABLE";
    private String applicationName;

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    public void setApplicationName(String value) {
        if (this.applicationName == null) {
            this.applicationName = value;
        }
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getJobDispatchType() {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            List<JobConfigBean> configs = ConfigDao.selectByName(conn, CONFIG_NAME_JOB_DISPATCH_TYPE);
            if (configs.isEmpty()) {
                String string2 = CONFIG_VALUE_JOB_DISPATCH_BY_TYPE;
                return string2;
            }
            String string = configs.get(0).getValue();
            return string;
        }
        catch (SQLException e) {
            this.logger.error("\u83b7\u53d6\u670d\u52a1\u8c03\u5ea6\u7c7b\u578b\u5931\u8d25\uff0c" + e.getMessage(), e);
            return CONFIG_VALUE_JOB_DISPATCH_BY_TYPE;
        }
    }

    public void updateJobDispatchType(String type) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ConfigDao.updateValue(conn, CONFIG_NAME_JOB_DISPATCH_TYPE, CONFIG_VALUE_JOB_DISPATCH_BY_TYPE.equalsIgnoreCase(type) ? CONFIG_VALUE_JOB_DISPATCH_BY_TYPE : CONFIG_VALUE_JOB_DISPATCH_BY_TAG);
        }
        catch (SQLException e) {
            throw new JobsException("\u8bbe\u7f6e\u670d\u52a1\u8c03\u5ea6\u7c7b\u578b\u5931\u8d25" + e.getMessage(), e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getJobMatchType() {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            List<JobConfigBean> configs = ConfigDao.selectByName(conn, CONFIG_NAME_JOB_MATCH_TYPE);
            if (configs.isEmpty()) {
                String string2 = CONFIG_VALUE_BY_MACHINE_NAME;
                return string2;
            }
            String string = configs.get(0).getValue();
            return string;
        }
        catch (SQLException e) {
            this.logger.error("\u83b7\u53d6\u670d\u52a1\u8c03\u5ea6\u7c7b\u578b\u5931\u8d25\uff0c" + e.getMessage(), e);
            return CONFIG_VALUE_BY_MACHINE_NAME;
        }
    }

    public void updateJobMatchType(String type) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ConfigDao.updateValue(conn, CONFIG_NAME_JOB_MATCH_TYPE, CONFIG_VALUE_BY_MACHINE_NAME.equalsIgnoreCase(type) ? CONFIG_VALUE_BY_MACHINE_NAME : CONFIG_VALUE_BY_APPLICATION_NAME);
        }
        catch (SQLException e) {
            throw new JobsException("\u8bbe\u7f6e\u670d\u52a1\u8c03\u5ea6\u7c7b\u578b\u5931\u8d25" + e.getMessage(), e);
        }
    }

    public List<JobConfigBean> getJobConfigValueByApplicationName(String type) {
        List<JobConfigBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = ConfigDao.selectByNameAndNode(conn, type, this.applicationName);
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    this.logger.error("\u83b7\u53d6\u670d\u52a1\u8c03\u5ea6\u7c7b\u578b\u5931\u8d25\uff0c" + e.getMessage(), e);
                    return Collections.emptyList();
                }
            }
            conn.close();
        }
        return list;
    }

    public List<JobConfigBean> getJobConfigValueByMachineName(String type) {
        List<JobConfigBean> list;
        block8: {
            Connection conn = GlobalConnectionProviderManager.getConnection();
            try {
                list = ConfigDao.selectByNameAndNode(conn, type, DistributionManager.getInstance().self().getMachineName());
                if (conn == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (conn != null) {
                        try {
                            conn.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (SQLException e) {
                    this.logger.error("\u83b7\u53d6\u670d\u52a1\u8c03\u5ea6\u7c7b\u578b\u5931\u8d25\uff0c" + e.getMessage(), e);
                    return Collections.emptyList();
                }
            }
            conn.close();
        }
        return list;
    }

    public List<String> getUnexecutableJobGuid() {
        ArrayList<String> unexecutableJobGuid = new ArrayList<String>();
        long startTime = System.currentTimeMillis();
        this.logger.trace("\u3010\u83b7\u53d6\u5f53\u524d\u8282\u70b9\u4e0d\u53ef\u6267\u884c\u7684\u4efb\u52a1guid\u3011\u5f00\u59cb...");
        try {
            Node curNode = DistributionManager.getInstance().self();
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                List<JobConfigBean> curNodeConfigs = ConfigDao.selectByNameAndNode(conn, CONFIG_NAME_JOB_EXECUTABLE_NODE, curNode.getName());
                List<JobConfigBean> configs = ConfigDao.selectByName(conn, CONFIG_NAME_JOB_EXECUTABLE_NODE);
                block7: for (JobConfigBean config : configs) {
                    String jobGuid = config.getValue();
                    if (StringUtils.isEmpty((String)jobGuid) || curNode.getName().equalsIgnoreCase(config.getNode())) continue;
                    for (JobConfigBean curNodeConf : curNodeConfigs) {
                        if (!jobGuid.equals(curNodeConf.getValue())) continue;
                        continue block7;
                    }
                    unexecutableJobGuid.add(jobGuid);
                }
            }
        }
        catch (SQLException e) {
            this.logger.error("\u83b7\u53d6\u4e0d\u53ef\u6267\u884c\u7684\u4efb\u52a1guid\u5931\u8d25\uff0c" + e.getMessage(), e);
            return unexecutableJobGuid;
        }
        this.logger.trace("\u3010\u83b7\u53d6\u5f53\u524d\u8282\u70b9\u4e0d\u53ef\u6267\u884c\u7684\u4efb\u52a1guid\u3011\u7ed3\u675f...\u6b64\u6b21\u83b7\u53d6\u914d\u7f6e\u5171\u8017\u65f6\uff1a{}\u6beb\u79d2", (Object)(System.currentTimeMillis() - startTime));
        return unexecutableJobGuid;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getMaxJobExecuteCount() {
        int maxCount = 30;
        Node curNode = null;
        try {
            curNode = DistributionManager.getInstance().getSelfNode();
        }
        catch (DistributionException e) {
            this.logger.error("\u83b7\u53d6\u5f53\u524d\u8282\u70b9\u5931\u8d25\uff0c" + e.getMessage(), e);
            return maxCount;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            int nodeMaxCount = -1;
            List<JobConfigBean> nodeMaxCountConf = ConfigDao.selectByNameAndNode(conn, CONFIG_NAME_NODE_MAX_JOBCOUNT, curNode.getName());
            if (nodeMaxCountConf.size() == 1) {
                nodeMaxCount = Integer.parseInt(nodeMaxCountConf.get(0).getValue());
            }
            if (nodeMaxCountConf.size() > 1) {
                this.logger.warn("\u8282\u70b9\u3010{}\u3011\u6700\u5927\u4efb\u52a1\u6570\u5b58\u5728\u591a\u6761\u8bb0\u5f55\uff0c\u968f\u673a\u53d6\u5176\u4e2d\u4e00\u6761\u4e3a\u751f\u6548\u8bb0\u5f55\uff0c\u5ffd\u7565\u5176\u4ed6\u3002\u8282\u70b9\u6700\u5927\u4efb\u52a1\u6570\u503c\u4e3a\uff1a{}", (Object)curNode.getName(), (Object)nodeMaxCount);
            }
            int globalMaxCount = 30;
            List<JobConfigBean> globalMaxCountConf = ConfigDao.selectByName(conn, CONFIG_NAME_GLOBAL_MAX_JOBCOUNT);
            if (globalMaxCountConf.size() == 1) {
                globalMaxCount = Integer.parseInt(globalMaxCountConf.get(0).getValue());
            } else {
                this.logger.warn("\u6570\u636e\u5e93\u4e2d\u300a\u65e0/\u5b58\u5728\u591a\u6761\u300b\u5168\u5c40\u6700\u5927\u4efb\u52a1\u5e76\u884c\u6570\uff0c\u6309\u7167\u7cfb\u7edf\u6307\u5b9a\u9ed8\u8ba4\u503c30\u5904\u7406");
            }
            int globalMaxCountEable = 1;
            List<JobConfigBean> globalMaxCountEnableConf = ConfigDao.selectByName(conn, CONFIG_NAME_GLOBAL_MAX_JOBCOUNT_ENABLE);
            if (globalMaxCountEnableConf.size() == 1) {
                globalMaxCountEable = Integer.parseInt(globalMaxCountEnableConf.get(0).getValue());
            } else {
                this.logger.warn("\u6570\u636e\u5e93\u4e2d\u300a\u65e0/\u5b58\u5728\u591a\u6761\u300b\u542f\u7528\u5168\u5c40\u6700\u5927\u4efb\u52a1\u5e76\u884c\u6570\u914d\u7f6e\uff0c\u9ed8\u8ba4\u542f\u7528");
            }
            if (globalMaxCountEable == 1) {
                nodeMaxCount = globalMaxCount;
            } else if (nodeMaxCount == -1) {
                nodeMaxCount = globalMaxCount;
            }
            maxCount = nodeMaxCount;
        }
        catch (SQLException e) {
            this.logger.error("\u83b7\u53d6\u4e0d\u53ef\u6267\u884c\u7684\u4efb\u52a1guid\u5931\u8d25\uff0c" + e.getMessage(), e);
            return maxCount;
        }
        return maxCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setUnexecutableJobCategory(Map<String, List<String>> configMap) throws JobsException {
        try {
            ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
            for (Map.Entry<String, List<String>> stringListEntry : configMap.entrySet()) {
                String nodeName = stringListEntry.getKey();
                List<String> curCates = stringListEntry.getValue();
                for (String cate : curCates) {
                    JobConfigBean config = new JobConfigBean();
                    config.setGuid(Guid.newGuid());
                    config.setName(CONFIG_NAME_CATE_UNEXECUTABLE);
                    config.setNode(nodeName);
                    config.setValue(cate);
                    configs.add(config);
                }
            }
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                conn.setAutoCommit(false);
                try {
                    ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_UNEXECUTABLE);
                    ConfigDao.insertBatch(conn, configs);
                    conn.commit();
                }
                catch (Exception e) {
                    conn.rollback();
                    throw e;
                }
                finally {
                    conn.setAutoCommit(true);
                }
            }
        }
        catch (Exception e) {
            throw new JobsException("\u6309\u8282\u70b9\u8bbe\u7f6e\u4e0d\u53ef\u6267\u884c\u7684\u4efb\u52a1\u5206\u7c7b\u5931\u8d25" + e.getMessage(), e);
        }
    }

    public List<JobCategoryConfig> getJobCategoryWithApplicationNameByTag() throws JobsException {
        HashMap<String, JobCategoryConfig> result = new HashMap<String, JobCategoryConfig>();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
            configs.addAll(ConfigDao.selectByName(conn, CONFIG_NAME_CATE_WHITELIST_TAG));
            configs.addAll(ConfigDao.selectByName(conn, CONFIG_NAME_CATE_BLACKLIST_TAG));
            for (JobConfigBean config : configs) {
                if (StringUtils.isEmpty((String)config.getNode())) continue;
                JobCategoryConfig jobCategoryConfig = (JobCategoryConfig)result.get(config.getNode());
                if (jobCategoryConfig == null) {
                    jobCategoryConfig = new JobCategoryConfig();
                    jobCategoryConfig.setApplicationName(config.getNode());
                    if (CONFIG_NAME_CATE_WHITELIST_TAG.equalsIgnoreCase(config.getName())) {
                        jobCategoryConfig.setConfigType(JobCategoryConfig.ConfigType.WHITELIST);
                    } else {
                        jobCategoryConfig.setConfigType(JobCategoryConfig.ConfigType.BLACKLIST);
                    }
                }
                jobCategoryConfig.getCategoryList().add(config.getValue());
                result.put(config.getNode(), jobCategoryConfig);
            }
        }
        catch (SQLException e) {
            throw new JobsException("\u83b7\u53d6\u670d\u52a1\u6267\u884c\u4efb\u52a1\u5206\u7c7b\u5931\u8d25" + e.getMessage(), e);
        }
        return new ArrayList<JobCategoryConfig>(result.values());
    }

    public List<JobCategoryConfig> getJobCategoryWithApplicationNameByType() throws JobsException {
        HashMap<String, JobCategoryConfig> result = new HashMap<String, JobCategoryConfig>();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
            configs.addAll(ConfigDao.selectByName(conn, CONFIG_NAME_CATE_WHITELIST_TYPE));
            configs.addAll(ConfigDao.selectByName(conn, CONFIG_NAME_CATE_BLACKLIST_TYPE));
            for (JobConfigBean config : configs) {
                if (StringUtils.isEmpty((String)config.getNode())) continue;
                JobCategoryConfig jobCategoryConfig = (JobCategoryConfig)result.get(config.getNode());
                if (jobCategoryConfig == null) {
                    jobCategoryConfig = new JobCategoryConfig();
                    jobCategoryConfig.setApplicationName(config.getNode());
                    if (CONFIG_NAME_CATE_WHITELIST_TYPE.equalsIgnoreCase(config.getName())) {
                        jobCategoryConfig.setConfigType(JobCategoryConfig.ConfigType.WHITELIST);
                    } else {
                        jobCategoryConfig.setConfigType(JobCategoryConfig.ConfigType.BLACKLIST);
                    }
                }
                jobCategoryConfig.getCategoryList().add(config.getValue());
                result.put(config.getNode(), jobCategoryConfig);
            }
        }
        catch (SQLException e) {
            throw new JobsException("\u83b7\u53d6\u670d\u52a1\u6267\u884c\u4efb\u52a1\u5206\u7c7b\u5931\u8d25" + e.getMessage(), e);
        }
        return new ArrayList<JobCategoryConfig>(result.values());
    }

    public List<JobCategoryConfig> getJobCategoryWithMachineNameByTag() throws JobsException {
        HashMap<String, JobCategoryConfig> result = new HashMap<String, JobCategoryConfig>();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
            configs.addAll(ConfigDao.selectByName(conn, CONFIG_NAME_CATE_UNEXECUTABLE_TAG));
            configs.addAll(ConfigDao.selectByName(conn, CONFIG_NAME_CATE_EXECUTABLE_TAG));
            for (JobConfigBean config : configs) {
                if (StringUtils.isEmpty((String)config.getNode())) continue;
                JobCategoryConfig jobCategoryConfig = (JobCategoryConfig)result.get(config.getNode());
                if (jobCategoryConfig == null) {
                    jobCategoryConfig = new JobCategoryConfig();
                    jobCategoryConfig.setApplicationName(config.getNode());
                    if (CONFIG_NAME_CATE_EXECUTABLE_TAG.equalsIgnoreCase(config.getName())) {
                        jobCategoryConfig.setConfigType(JobCategoryConfig.ConfigType.WHITELIST);
                    } else {
                        jobCategoryConfig.setConfigType(JobCategoryConfig.ConfigType.BLACKLIST);
                    }
                }
                jobCategoryConfig.getCategoryList().add(config.getValue());
                result.put(config.getNode(), jobCategoryConfig);
            }
        }
        catch (SQLException e) {
            throw new JobsException("\u83b7\u53d6\u670d\u52a1\u6267\u884c\u4efb\u52a1\u5206\u7c7b\u5931\u8d25" + e.getMessage(), e);
        }
        return new ArrayList<JobCategoryConfig>(result.values());
    }

    public List<JobCategoryConfig> getJobCategoryWithMachineNameByType() throws JobsException {
        HashMap<String, JobCategoryConfig> result = new HashMap<String, JobCategoryConfig>();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
            configs.addAll(ConfigDao.selectByName(conn, CONFIG_NAME_CATE_EXECUTABLE));
            configs.addAll(ConfigDao.selectByName(conn, CONFIG_NAME_CATE_UNEXECUTABLE));
            for (JobConfigBean config : configs) {
                if (StringUtils.isEmpty((String)config.getNode())) continue;
                JobCategoryConfig jobCategoryConfig = (JobCategoryConfig)result.get(config.getNode());
                if (jobCategoryConfig == null) {
                    jobCategoryConfig = new JobCategoryConfig();
                    jobCategoryConfig.setApplicationName(config.getNode());
                    if (CONFIG_NAME_CATE_EXECUTABLE.equalsIgnoreCase(config.getName())) {
                        jobCategoryConfig.setConfigType(JobCategoryConfig.ConfigType.WHITELIST);
                    } else {
                        jobCategoryConfig.setConfigType(JobCategoryConfig.ConfigType.BLACKLIST);
                    }
                }
                jobCategoryConfig.getCategoryList().add(config.getValue());
                result.put(config.getNode(), jobCategoryConfig);
            }
        }
        catch (SQLException e) {
            throw new JobsException("\u83b7\u53d6\u670d\u52a1\u6267\u884c\u4efb\u52a1\u5206\u7c7b\u5931\u8d25" + e.getMessage(), e);
        }
        return new ArrayList<JobCategoryConfig>(result.values());
    }

    public void setJobCategoryWithApplicationName(List<JobConfigBean> jobConfigBeans) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            conn.setAutoCommit(false);
            try {
                ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_WHITELIST_TAG);
                ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_BLACKLIST_TAG);
                ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_WHITELIST_TYPE);
                ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_BLACKLIST_TYPE);
                ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_UNEXECUTABLE);
                ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_EXECUTABLE);
                ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_UNEXECUTABLE_TAG);
                ConfigDao.deleteByName(conn, CONFIG_NAME_CATE_EXECUTABLE_TAG);
                ConfigDao.insertBatch(conn, jobConfigBeans);
                conn.commit();
            }
            catch (Exception e) {
                conn.rollback();
                throw e;
            }
            finally {
                conn.setAutoCommit(true);
            }
        }
        catch (SQLException e) {
            throw new JobsException("\u4fdd\u5b58\u670d\u52a1\u6267\u884c\u4efb\u52a1\u5206\u7c7b\u5931\u8d25" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, List<String>> getUnexecutableJobCategory() throws JobsException {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            List<JobConfigBean> configs = ConfigDao.selectByName(conn, CONFIG_NAME_CATE_UNEXECUTABLE);
            for (JobConfigBean config : configs) {
                if (StringUtils.isEmpty((String)config.getNode())) continue;
                ArrayList<String> cates = (ArrayList<String>)result.get(config.getNode());
                if (cates == null) {
                    cates = new ArrayList<String>();
                }
                cates.add(config.getValue());
                result.put(config.getNode(), cates);
            }
        }
        catch (SQLException e) {
            throw new JobsException("\u83b7\u53d6\u8282\u70b9\u4e0d\u53ef\u6267\u884c\u7684\u4efb\u52a1\u5206\u7c7b\u5931\u8d25" + e.getMessage(), e);
        }
        return result;
    }

    public void setJobOnlyInNode(String jobGuid, String[] nodeNames) throws JobsException {
        try {
            ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
            for (String nodeName : nodeNames) {
                if (StringUtils.isEmpty((String)nodeName)) continue;
                JobConfigBean config = new JobConfigBean();
                config.setGuid(Guid.newGuid());
                config.setName(CONFIG_NAME_JOB_EXECUTABLE_NODE);
                config.setNode(nodeName);
                config.setValue(jobGuid);
                configs.add(config);
            }
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                conn.setAutoCommit(false);
                try {
                    ConfigDao.deleteByNameAndValue(conn, CONFIG_NAME_JOB_EXECUTABLE_NODE, jobGuid);
                    ConfigDao.insertBatch(conn, configs);
                    conn.commit();
                }
                catch (Exception e) {
                    conn.rollback();
                    throw e;
                }
                finally {
                    conn.setAutoCommit(true);
                }
            }
        }
        catch (Exception e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    public void deleteJobOnlyInNode(String jobGuid) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ConfigDao.deleteByNameAndValue(conn, CONFIG_NAME_JOB_EXECUTABLE_NODE, jobGuid);
        }
        catch (SQLException e) {
            throw new JobsException(e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<String> getJobOnlyInNode(String jobGuid) throws JobsException {
        ArrayList<String> result = new ArrayList<String>();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            List<JobConfigBean> configs = ConfigDao.selectByNameAndValue(conn, CONFIG_NAME_JOB_EXECUTABLE_NODE, jobGuid);
            for (JobConfigBean config : configs) {
                result.add(config.getNode());
            }
        }
        catch (SQLException e) {
            throw new JobsException("\u83b7\u53d6\u4efb\u52a1\u53ea\u53ef\u4ee5\u6267\u884c\u7684\u8282\u70b9\u5931\u8d25" + e.getMessage(), e);
        }
        return result;
    }

    private void setGlobalMaxJobExecuteCount(int number) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ConfigDao.updateValue(conn, CONFIG_NAME_GLOBAL_MAX_JOBCOUNT, String.valueOf(number));
        }
        catch (SQLException e) {
            throw new JobsException("\u8bbe\u7f6e\u96c6\u7fa4\u5168\u5c40\u670d\u52a1\u5355\u8282\u70b9\u6700\u5927\u5e76\u884c\u4efb\u52a1\u6570" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getGlobalMaxJobExecuteCount() throws JobsException {
        int maxCount;
        block7: {
            maxCount = 0;
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                List<JobConfigBean> configs = ConfigDao.selectByName(conn, CONFIG_NAME_GLOBAL_MAX_JOBCOUNT);
                if (configs.size() == 1) {
                    maxCount = Integer.parseInt(configs.get(0).getValue());
                    break block7;
                }
                if (configs.isEmpty()) {
                    throw new JobsException("\u6570\u636e\u5e93\u4e2d\u4e0d\u5b58\u5728\u3010\u96c6\u7fa4\u5168\u5c40\u670d\u52a1\u6700\u5927\u53ef\u6267\u884c\u4efb\u52a1\u6570\u914d\u7f6e\u3011\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5");
                }
                throw new JobsException("\u6570\u636e\u5e93\u4e2d\u5b58\u5728\u591a\u6761\u3010\u96c6\u7fa4\u5168\u5c40\u670d\u52a1\u6700\u5927\u53ef\u6267\u884c\u4efb\u52a1\u6570\u914d\u7f6e\u3011\u8bb0\u5f55\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5");
            }
            catch (SQLException e) {
                throw new JobsException("\u83b7\u53d6\u4efb\u52a1\u53ea\u53ef\u4ee5\u6267\u884c\u7684\u8282\u70b9\u5931\u8d25" + e.getMessage(), e);
            }
        }
        return maxCount;
    }

    private void enableGlobalMaxJobExecuteCount(boolean enable) throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ConfigDao.updateValue(conn, CONFIG_NAME_GLOBAL_MAX_JOBCOUNT_ENABLE, enable ? "1" : "0");
        }
        catch (SQLException e) {
            throw new JobsException("\u8bbe\u7f6e\u662f\u5426\u542f\u7528\u96c6\u7fa4\u5168\u5c40\u670d\u52a1\u6700\u5927\u4efb\u52a1\u53ef\u6267\u884c\u4efb\u52a1\u6570\u5931\u8d25" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean getGlobalMaxJobExecuteCountEnable() throws JobsException {
        boolean enable;
        block7: {
            enable = false;
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                List<JobConfigBean> configs = ConfigDao.selectByName(conn, CONFIG_NAME_GLOBAL_MAX_JOBCOUNT_ENABLE);
                if (configs.size() == 1) {
                    enable = "1".equals(configs.get(0).getValue());
                    break block7;
                }
                if (configs.isEmpty()) {
                    throw new JobsException("\u6570\u636e\u5e93\u4e2d\u4e0d\u5b58\u5728\u3010\u662f\u5426\u542f\u7528\u96c6\u7fa4\u5168\u5c40\u670d\u52a1\u6700\u5927\u53ef\u6267\u884c\u4efb\u52a1\u6570\u914d\u7f6e\u3011\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5");
                }
                throw new JobsException("\u6570\u636e\u5e93\u4e2d\u5b58\u5728\u591a\u6761\u3010\u662f\u5426\u542f\u7528\u96c6\u7fa4\u5168\u5c40\u670d\u52a1\u6700\u5927\u53ef\u6267\u884c\u4efb\u52a1\u6570\u914d\u7f6e\u3011\u8bb0\u5f55\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u68c0\u67e5");
            }
            catch (SQLException e) {
                throw new JobsException("\u83b7\u53d6\u662f\u5426\u542f\u7528\u4e86\u96c6\u7fa4\u5168\u5c40\u670d\u52a1\u6700\u5927\u4efb\u52a1\u53ef\u6267\u884c\u4efb\u52a1\u6570\u5931\u8d25" + e.getMessage(), e);
            }
        }
        return enable;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setNodeMaxJobExecuteCount(Map<String, Integer> nodeExecCountMap) throws JobsException {
        try {
            ArrayList<JobConfigBean> configs = new ArrayList<JobConfigBean>();
            for (Map.Entry<String, Integer> stringIntegerEntry : nodeExecCountMap.entrySet()) {
                String nodeName = stringIntegerEntry.getKey();
                Integer maxCount = stringIntegerEntry.getValue();
                if (maxCount == null || maxCount < 0) continue;
                JobConfigBean config = new JobConfigBean();
                config.setGuid(Guid.newGuid());
                config.setName(CONFIG_NAME_NODE_MAX_JOBCOUNT);
                config.setNode(nodeName);
                config.setValue(String.valueOf(maxCount));
                configs.add(config);
            }
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                conn.setAutoCommit(false);
                try {
                    ConfigDao.deleteByName(conn, CONFIG_NAME_NODE_MAX_JOBCOUNT);
                    ConfigDao.insertBatch(conn, configs);
                    conn.commit();
                }
                catch (Exception e) {
                    conn.rollback();
                    throw e;
                }
                finally {
                    conn.setAutoCommit(true);
                }
            }
        }
        catch (Exception e) {
            throw new JobsException("\u8bbe\u7f6e\u8282\u70b9\u6700\u5927\u53ef\u6267\u884c\u4efb\u52a1\u6570\u5931\u8d25" + e.getMessage(), e);
        }
    }

    public void resetNodeMaxJobExecuteCount() throws JobsException {
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            ConfigDao.deleteByName(conn, CONFIG_NAME_NODE_MAX_JOBCOUNT);
        }
        catch (SQLException e) {
            throw new JobsException("\u6e05\u7a7a\u5df2\u8bbe\u7f6e\u7684\u8282\u70b9\u6700\u5927\u4efb\u52a1\u5e76\u884c\u6570\u5931\u8d25" + e.getMessage(), e);
        }
        try {
            MessageSender.sendSchedulerRestartMessage(null);
        }
        catch (Exception e) {
            throw new JobsException("\u914d\u7f6e\u4fdd\u5b58\u6210\u529f\uff0c\u53d1\u9001\u91cd\u542f\u4efb\u52a1\u7ba1\u7406Scheduler\u6d88\u606f\u5931\u8d25\u3002\u8bf7\u91cd\u542f\u6240\u6709\u670d\u52a1\u8282\u70b9\uff0c\u4f7f\u914d\u7f6e\u751f\u6548" + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Map<String, Integer> getNodeMaxJobExecuteCount() throws JobsException {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            List<JobConfigBean> configs = ConfigDao.selectByName(conn, CONFIG_NAME_NODE_MAX_JOBCOUNT);
            if (configs != null) {
                for (JobConfigBean config : configs) {
                    String node = config.getNode();
                    if (StringUtils.isEmpty((String)node)) continue;
                    result.put(node, Integer.parseInt(config.getValue()));
                }
            }
        }
        catch (SQLException e) {
            throw new JobsException("\u83b7\u53d6\u8282\u70b9\u6700\u5927\u53ef\u6267\u884c\u4efb\u52a1\u6570\u5931\u8d25" + e.getMessage(), e);
        }
        return result;
    }

    public void updateExecuteCountConfig(int globalMaxJobExecuteCount, boolean globalMaxJobExecuteCountEnable, Map<String, Integer> nodeMaxJobExecuteCountMap) throws JobsException {
        try {
            this.setGlobalMaxJobExecuteCount(globalMaxJobExecuteCount);
            this.enableGlobalMaxJobExecuteCount(globalMaxJobExecuteCountEnable);
            this.setNodeMaxJobExecuteCount(nodeMaxJobExecuteCountMap);
        }
        catch (Exception e) {
            throw new JobsException("\u4fdd\u5b58\u4efb\u52a1\u7ba1\u7406\u914d\u7f6e\u5931\u8d25" + e.getMessage(), e);
        }
        try {
            MessageSender.sendSchedulerRestartMessage(null);
        }
        catch (Exception e) {
            throw new JobsException("\u914d\u7f6e\u4fdd\u5b58\u6210\u529f\uff0c\u53d1\u9001\u91cd\u542f\u4efb\u52a1\u7ba1\u7406Scheduler\u6d88\u606f\u5931\u8d25\u3002\u8bf7\u91cd\u542f\u6240\u6709\u670d\u52a1\u8282\u70b9\uff0c\u4f7f\u914d\u7f6e\u751f\u6548" + e.getMessage(), e);
        }
    }

    public void startAppointNodeScheduler(String nodeName) throws JobsException {
        try {
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                SchedulerStateDao.updateAllScheduler(conn, nodeName, SchedulerState.STARTING);
            }
            MessageSender.sendSchedulerStartMessage(nodeName);
        }
        catch (Exception e) {
            throw new JobsException("\u542f\u52a8\u8c03\u5ea6\u5668\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    public void shundownAppointNodeScheduler(String nodeName) throws JobsException {
        try {
            try (Connection conn = GlobalConnectionProviderManager.getConnection();){
                SchedulerStateDao.updateAllScheduler(conn, nodeName, SchedulerState.SHUTTINGDOWN);
            }
            MessageSender.sendSchedulerShutdownMessage(nodeName);
        }
        catch (Exception e) {
            throw new JobsException("\u542f\u52a8\u8c03\u5ea6\u5668\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
    }

    @Deprecated
    public boolean executable(JobExecutionContext context) {
        boolean executable;
        JobDetail jobDetail = context.getJobDetail();
        JobKey key = jobDetail.getKey();
        String categoryId = key.getGroup();
        BaseFactory jobFactory = CompositeJobFactoryManager.getJobFactory(categoryId);
        if (jobFactory == null) {
            executable = false;
            this.logger.debug("\u4efb\u52a1\u5206\u7c7b[{}]\u4e0d\u5b58\u5728\uff0c\u8be5\u670d\u52a1\u5c06\u4e0d\u6267\u884c\u8be5\u5206\u7c7b\u7684\u4efb\u52a1", (Object)categoryId);
        } else {
            executable = true;
        }
        return executable;
    }

    public void clearConfigCache() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isNodeSchedulerEnable() {
        Node curNode = null;
        try {
            curNode = DistributionManager.getInstance().getSelfNode();
        }
        catch (DistributionException e) {
            this.logger.error("\u83b7\u53d6\u5f53\u524d\u8282\u70b9\u5931\u8d25\uff0c" + e.getMessage(), e);
            return true;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            List<JobConfigBean> jobConfigBeans = ConfigDao.selectByNameAndNode(conn, CONFIG_NAME_NODE_SCHEDULER_ENABLE, curNode.getName());
            if (jobConfigBeans.isEmpty()) {
                boolean bl = true;
                return bl;
            }
            boolean bl = Integer.parseInt(jobConfigBeans.get(0).getValue()) > 0;
            return bl;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u8c03\u5ea6\u5668\u542f\u7528\u914d\u7f6e\u5f02\u5e38\uff0c" + e.getMessage(), e);
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setNodeSchedulerEnable(boolean enable) {
        Node curNode = null;
        try {
            curNode = DistributionManager.getInstance().getSelfNode();
        }
        catch (DistributionException e) {
            this.logger.error("\u83b7\u53d6\u5f53\u524d\u8282\u70b9\u5931\u8d25\uff0c" + e.getMessage(), e);
            return;
        }
        try (Connection conn = GlobalConnectionProviderManager.getConnection();){
            List<JobConfigBean> jobConfigBeans = ConfigDao.selectByNameAndNode(conn, CONFIG_NAME_NODE_SCHEDULER_ENABLE, curNode.getName());
            if (jobConfigBeans.isEmpty()) {
                JobConfigBean bean = new JobConfigBean();
                bean.setGuid(Guid.newGuid());
                bean.setName(CONFIG_NAME_NODE_SCHEDULER_ENABLE);
                bean.setValue(enable ? "1" : "0");
                bean.setNode(curNode.getName());
                jobConfigBeans.add(bean);
                ConfigDao.insertBatch(conn, jobConfigBeans);
            } else {
                ConfigDao.updateValueByNameAndNode(conn, CONFIG_NAME_NODE_SCHEDULER_ENABLE, curNode.getName(), enable ? "1" : "0");
            }
        }
        catch (Exception e) {
            this.logger.error("\u66f4\u65b0\u8c03\u5ea6\u5668\u542f\u7528\u914d\u7f6e\u5f02\u5e38\uff0c" + e.getMessage(), e);
        }
    }
}

