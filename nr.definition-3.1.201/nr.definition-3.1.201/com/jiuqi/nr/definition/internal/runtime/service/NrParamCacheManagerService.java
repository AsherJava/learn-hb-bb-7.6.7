/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.concurrent.Daemon
 *  com.jiuqi.bi.core.nodekeeper.DistributionException
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.Node
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.np.cache.message.MessageSubscriber
 *  com.jiuqi.np.cache.message.Subscriber
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshScheme
 *  com.jiuqi.nr.datascheme.common.Consts
 *  com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.springframework.data.redis.core.RedisTemplate
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.bi.concurrent.Daemon;
import com.jiuqi.bi.core.nodekeeper.DistributionException;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.Node;
import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.np.cache.message.MessageSubscriber;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionRefreshListener;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshScheme;
import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.datascheme.internal.service.SchemeRefreshListener;
import com.jiuqi.nr.definition.event.FormSchemePeriodChangeEvent;
import com.jiuqi.nr.definition.exception.NrParamSyncException;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeSchemePeriodLinkDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeTaskLinkDefineDao;
import com.jiuqi.nr.definition.internal.runtime.service.AbstractNrParamCacheExpireService;
import com.jiuqi.nr.definition.internal.runtime.service.NrParamCachePreloadService;
import com.jiuqi.nr.definition.internal.runtime.service.NrParamCacheRefreshService;
import com.jiuqi.nr.definition.option.IReportCacheOptionService;
import com.jiuqi.nr.definition.option.common.ReportCacheCycleType;
import com.jiuqi.nr.definition.option.dto.ReportCacheConfig;
import com.jiuqi.nr.definition.service.IParamCacheManagerService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.io.Serializable;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
@Subscriber(channels={"com.jiuqi.nr.definition.runtime.cache.reload"})
public class NrParamCacheManagerService
implements IParamCacheManagerService,
MessageSubscriber,
RuntimeDefinitionRefreshListener {
    private static final Logger LOGGER = Consts.NR_PARAM_GRAPH_LOGGER;
    private static final long WAIT_INTERVAL = 500L;
    private static final long WAIT_TIMEOUT = 30000L;
    protected static final String CHANNEL = "com.jiuqi.nr.definition.runtime.cache.reload";
    @Autowired
    private NpApplication npApplication;
    @Autowired
    private RunTimeTaskDefineDao taskDefineDao;
    @Autowired
    private RunTimeFormSchemeDefineDao formSchemeDao;
    @Autowired
    private RunTimeTaskLinkDefineDao taskLinkDefineDao;
    @Autowired
    private RunTimeSchemePeriodLinkDao schemePeriodLinkDao;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IReportCacheOptionService reportCacheOptionService;
    @Autowired
    private AbstractNrParamCacheExpireService cacheExpireService;
    @Autowired
    private NrParamCacheRefreshService cacheRefreshService;
    @Autowired(required=false)
    private NrParamCachePreloadService cachePreloadService;
    @Autowired
    private MessagePublisher messageService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void onMessage(String channel, Object message, boolean fromThisInstance) {
        if (fromThisInstance) {
            return;
        }
        if (message instanceof NrParamRefreshInfo) {
            NrParamRefreshInfo info = (NrParamRefreshInfo)message;
            this.refreshCache(info);
        } else if (message instanceof NrParamClearInfo) {
            this.cacheRefreshService.clean();
        } else {
            this.loadSetting();
        }
    }

    public void onClearCache() {
        this.messageService.publishMessage(CHANNEL, (Object)new NrParamClearInfo());
        this.cacheRefreshService.clean();
    }

    @Override
    public void init() {
        new Thread(() -> {
            this.cacheRefreshService.init();
            this.loadSetting();
            this.startSchedule();
        }).start();
    }

    private void startSchedule() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next = now.plusDays(1L).withHour(0).withMinute(0).withSecond(0).withNano(0);
        Duration duration = Duration.ofDays(1L);
        Daemon.getGlobal().atFixedRate(() -> {
            this.parseRules();
            this.logRules();
            if (null != this.cachePreloadService) {
                this.cachePreloadService.preload(NrParamCacheSettings.oldPreloadSettings, NrParamCacheSettings.preloadSettings);
            }
        }, Duration.between(now, next).toMillis(), duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void reloadSetting() {
        this.messageService.publishMessage(CHANNEL, (Object)"\u62a5\u8868\u7f13\u5b58\u7684\u7cfb\u7edf\u914d\u7f6e\u5df2\u4fee\u6539");
        this.npApplication.asyncRun(this::loadSetting);
    }

    private void loadSetting() {
        NrParamCacheSettings.enableObsolete = this.reportCacheOptionService.isCacheAutoExpire();
        int proportion = this.reportCacheOptionService.getMaxProportionOfCache();
        long maxMemory = Runtime.getRuntime().maxMemory();
        NrParamCacheSettings.maxMemSize = maxMemory * (long)proportion / 100L;
        NrParamCacheSettings.expirationTime = (long)this.reportCacheOptionService.getExpirationTime() * 60L * 60L * 1000L;
        if (NrParamCacheSettings.enableObsolete && 0L == NrParamCacheSettings.expirationTime) {
            NrParamCacheSettings.expirationTime = 86400000L;
        }
        NrParamCacheSettings.memThreshold = this.reportCacheOptionService.isEnableMemThreshold() ? (long)((double)maxMemory * NrParamCacheConfig.memThreshold / 100.0) : 0L;
        this.parseRules();
        NrParamCacheSettings.hotUpdate = this.reportCacheOptionService.isHotUpdate();
        this.logSetting(proportion);
        this.cacheExpireService.init();
        if (null != this.cachePreloadService) {
            this.cachePreloadService.preload(NrParamCacheSettings.oldPreloadSettings, NrParamCacheSettings.preloadSettings);
        }
    }

    private void parseRules() {
        HashSet<String> keys = new HashSet<String>();
        ArrayList<PreloadSetting> preloadSettings = new ArrayList<PreloadSetting>();
        List<ReportCacheConfig> rules = this.reportCacheOptionService.getPermanentCacheRules();
        List<ReportCacheConfig> preloadRules = this.reportCacheOptionService.getPreloadCacheRules();
        Set preloadTaskKeys = preloadRules.stream().map(ReportCacheConfig::getTask).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(rules)) {
            for (ReportCacheConfig rule : rules) {
                Collection<Object> collection;
                int endOffset;
                String task = rule.getTask();
                int startOffset = ReportCacheCycleType.SETTING_BEFORE_CYCLE == rule.getCycleBeginType() ? -rule.getCycleBeginDays() : rule.getCycleBeginDays();
                int n = endOffset = ReportCacheCycleType.SETTING_BEFORE_CYCLE == rule.getCycleEndType() ? -rule.getCycleEndDays() : rule.getCycleEndDays();
                if (preloadTaskKeys.contains(task)) {
                    collection = this.parsePreloadRules(task, startOffset, endOffset, new Date());
                    keys.addAll(collection.stream().map(PreloadSetting::getFormSchemeKey).collect(Collectors.toSet()));
                    preloadSettings.addAll(collection);
                    continue;
                }
                collection = this.parseRules(task, startOffset, endOffset, new Date());
                keys.addAll(collection);
            }
        }
        this.parseLinkFormSchemes(keys);
        NrParamCacheSettings.permanent = keys;
        NrParamCacheSettings.oldPreloadSettings = NrParamCacheSettings.preloadSettings;
        NrParamCacheSettings.preloadSettings = preloadSettings;
    }

    private Collection<PreloadSetting> parsePreloadRules(String taskKey, int startOffset, int endOffset, Date now) {
        HashSet<PreloadSetting> rules = new HashSet<PreloadSetting>();
        List<FormSchemeDefine> formSchemes = this.formSchemeDao.listByTask(taskKey);
        if (null == formSchemes) {
            return rules;
        }
        Calendar calendar = Calendar.getInstance();
        TaskDefine task = this.taskDefineDao.getDefineByKey(taskKey);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(task.getDateTime());
        for (FormSchemeDefine formScheme : formSchemes) {
            List<SchemePeriodLinkDefine> periods = this.schemePeriodLinkDao.queryByScheme(formScheme.getKey());
            if (CollectionUtils.isEmpty(periods)) continue;
            PreloadSetting setting = new PreloadSetting(taskKey, formScheme.getKey(), new HashMap<String, Date>());
            for (SchemePeriodLinkDefine period : periods) {
                try {
                    Date[] dates = periodProvider.getPeriodDateRegion(period.getPeriodKey());
                    calendar.setTime(dates[0]);
                    calendar.add(5, startOffset);
                    if (!now.after(calendar.getTime())) continue;
                    calendar.setTime(dates[1]);
                    calendar.add(5, endOffset);
                    if (!now.before(calendar.getTime())) continue;
                    setting.getPeriods().put(period.getPeriodKey(), dates[0]);
                }
                catch (ParseException e) {
                    LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u62a5\u8868\u65b9\u6848\u65f6\u671f\u89e3\u6790\u5931\u8d25\uff0c\u4efb\u52a1\uff1a{}\uff0c\u65f6\u671f\uff1a{}", taskKey, period.getPeriodKey(), e);
                }
            }
            if (CollectionUtils.isEmpty(setting.getPeriods())) continue;
            rules.add(setting);
        }
        return rules;
    }

    private Collection<String> parseRules(String taskKey, int startOffset, int endOffset, Date now) {
        HashSet<String> keys = new HashSet<String>();
        List<FormSchemeDefine> formSchemes = this.formSchemeDao.listByTask(taskKey);
        if (null == formSchemes) {
            return keys;
        }
        Calendar calendar = Calendar.getInstance();
        TaskDefine task = this.taskDefineDao.getDefineByKey(taskKey);
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(task.getDateTime());
        block2: for (FormSchemeDefine formScheme : formSchemes) {
            List<SchemePeriodLinkDefine> periods = this.schemePeriodLinkDao.queryByScheme(formScheme.getKey());
            if (CollectionUtils.isEmpty(periods)) continue;
            for (SchemePeriodLinkDefine period : periods) {
                try {
                    Date[] dates = periodProvider.getPeriodDateRegion(period.getPeriodKey());
                    calendar.setTime(dates[0]);
                    calendar.add(5, startOffset);
                    if (!now.after(calendar.getTime())) continue;
                    calendar.setTime(dates[1]);
                    calendar.add(5, endOffset);
                    if (!now.before(calendar.getTime())) continue;
                    keys.add(formScheme.getKey());
                    continue block2;
                }
                catch (ParseException e) {
                    LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u62a5\u8868\u65b9\u6848\u65f6\u671f\u89e3\u6790\u5931\u8d25\uff0c\u4efb\u52a1\uff1a{}\uff0c\u65f6\u671f\uff1a{}", taskKey, period.getPeriodKey(), e);
                }
            }
        }
        return keys;
    }

    private void parseLinkFormSchemes(Set<String> keys) {
        HashSet linkedFormSchemes = new HashSet();
        for (String key : keys) {
            List<TaskLinkDefine> links = this.taskLinkDefineDao.queryDefinesByCurrentFormScheme(key);
            linkedFormSchemes.addAll(links.stream().map(TaskLinkDefine::getRelatedFormSchemeKey).filter(StringUtils::hasText).collect(Collectors.toSet()));
        }
        keys.addAll(linkedFormSchemes);
    }

    private void logRules() {
        if (NrParamCacheSettings.enableObsolete) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u5e38\u9a7b\u62a5\u8868\u65b9\u6848\uff1a\r\n{}", (Object)StringUtils.collectionToDelimitedString(NrParamCacheSettings.permanent, "\r\n"));
        }
    }

    private void logSetting(int proportion) {
        StringBuilder builder = new StringBuilder("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u52a0\u8f7d\u7cfb\u7edf\u914d\u7f6e");
        builder.append("\n\u542f\u7528\u7f13\u5b58\u70ed\u66f4\u65b0\uff1a").append(NrParamCacheSettings.hotUpdate ? "\u662f" : "\u5426");
        builder.append("\n\u542f\u7528\u7f13\u5b58\u81ea\u52a8\u8fc7\u671f\uff1a").append(NrParamCacheSettings.enableObsolete ? "\u662f" : "\u5426");
        if (NrParamCacheSettings.enableObsolete) {
            builder.append("\n\u7f13\u5b58\u8fc7\u671f\u65f6\u95f4\uff1a").append(NrParamCacheSettings.expirationTime).append("\u6beb\u79d2");
            builder.append("\n\u6309 JVM \u5806\u5185\u5b58\u4f7f\u7528\u7387\u5f3a\u5236\u8fc7\u671f\uff1a");
            if (NrParamCacheSettings.memThreshold > 0L) {
                builder.append("\u662f(").append(NrParamCacheSettings.memThreshold / 1024L / 1024L).append("MB)");
            } else {
                builder.append("\u5426");
            }
            if (CollectionUtils.isEmpty(NrParamCacheSettings.permanent)) {
                builder.append("\n\u5e38\u9a7b\u62a5\u8868\u65b9\u6848\uff1a\u65e0");
            } else {
                builder.append("\n\u5e38\u9a7b\u62a5\u8868\u65b9\u6848\uff1a\n");
                builder.append(StringUtils.collectionToDelimitedString(NrParamCacheSettings.permanent, "\n"));
            }
            builder.append("\n\u9884\u70ed\u62a5\u8868\u65b9\u6848");
            if (null == this.cachePreloadService) {
                builder.append("(\u5df2\u7981\u7528)");
            }
            if (CollectionUtils.isEmpty(NrParamCacheSettings.preloadSettings)) {
                builder.append("\uff1a\u65e0");
            } else {
                builder.append("\uff1a\n");
                builder.append(StringUtils.collectionToDelimitedString(NrParamCacheSettings.preloadSettings, "\n"));
            }
        }
        LOGGER.info(builder.toString());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void refreshCache(Collection<String> formSchemes, Collection<String> formulaSchemes) throws NrParamSyncException {
        NrParamRefreshInfo info = new NrParamRefreshInfo(formSchemes, formulaSchemes);
        this.messageService.publishMessage(CHANNEL, (Object)info);
        this.refreshCache(info);
        try {
            this.checkStatus(info.getMessageId());
        }
        finally {
            this.deleteStatus(info.getMessageId());
        }
    }

    private void refreshCache(NrParamRefreshInfo info) {
        if (NrParamCacheSettings.hotUpdate) {
            this.cacheRefreshService.refresh(info.getFormSchemes(), info.getFormulaSchemes());
        } else {
            this.cacheRefreshService.clean(info.getFormSchemes(), info.getFormulaSchemes());
        }
        this.updateStatus(info.getMessageId());
    }

    private void updateStatus(String messageId) {
        if (!NrParamCacheSettings.hotUpdate) {
            return;
        }
        DistributionManager instance = DistributionManager.getInstance();
        Node self = instance.self();
        this.redisTemplate.opsForSet().add((Object)messageId, (Object[])new String[]{self.getName()});
    }

    private void checkStatus(String messageId) throws NrParamSyncException {
        if (!NrParamCacheSettings.hotUpdate) {
            return;
        }
        DistributionManager instance = DistributionManager.getInstance();
        List nodes = null;
        try {
            nodes = instance.getAllNode();
        }
        catch (DistributionException e) {
            LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u83b7\u53d6\u96c6\u7fa4\u8282\u70b9\u5f02\u5e38", e);
            throw new NrParamSyncException("\u83b7\u53d6\u96c6\u7fa4\u8282\u70b9\u5f02\u5e38");
        }
        Node self = instance.self();
        HashSet<String> names = new HashSet<String>();
        for (Node node : nodes) {
            if (self.getName().equals(node.getName()) || !node.isAlive()) continue;
            names.add(node.getName());
        }
        long start = System.currentTimeMillis();
        while (!names.isEmpty()) {
            names.removeIf(name -> Boolean.TRUE.equals(this.redisTemplate.opsForSet().isMember((Object)messageId, name)));
            if (!names.isEmpty()) {
                NrParamCacheManagerService.sleep();
            }
            if (System.currentTimeMillis() - start <= 30000L) continue;
            LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u83b7\u53d6\u96c6\u7fa4\u8282\u70b9{}\u7684\u7f13\u5b58\u5237\u65b0\u72b6\u6001\u8d85\u65f6", (Object)names);
            throw new NrParamSyncException("\u96c6\u7fa4\u8282\u70b9" + names + "\u7684\u7f13\u5b58\u53ef\u80fd\u672a\u5237\u65b0\uff0c\u8bf7\u68c0\u67e5\u540e\u624b\u52a8\u5237\u65b0");
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u7ebf\u7a0b\u7b49\u5f85\u5f02\u5e38", e);
        }
    }

    private void deleteStatus(String messageId) {
        if (!NrParamCacheSettings.hotUpdate) {
            return;
        }
        this.redisTemplate.delete((Object)messageId);
    }

    @Service
    public class SettingOnFormSchemePeriodListener
    implements ApplicationListener<FormSchemePeriodChangeEvent> {
        @Override
        public void onApplicationEvent(FormSchemePeriodChangeEvent event) {
            List<String> keys = event.getKeys();
            for (String key : keys) {
                FormSchemeDefine define = NrParamCacheManagerService.this.formSchemeDao.getDefineByKey(key);
                if (null == define) continue;
                NrParamCacheManagerService.this.reloadSetting();
                break;
            }
        }
    }

    @Service
    public class DataLinkOnDataSchemeListener
    implements SchemeRefreshListener {
        public void onClearCache() {
        }

        public void onClearCache(RefreshCache refreshTable) {
            Map refreshTables = refreshTable.getRefreshTable();
            if (null == refreshTables) {
                return;
            }
            Set dataSchemeKeys = refreshTables.keySet().stream().map(RefreshScheme::getKey).collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(dataSchemeKeys)) {
                return;
            }
            Set taskKeys = NrParamCacheManagerService.this.taskDefineDao.list().stream().filter(t -> dataSchemeKeys.contains(t.getDataScheme())).map(IBaseMetaItem::getKey).collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(taskKeys)) {
                return;
            }
            Set<String> formSchemeKeys = NrParamCacheManagerService.this.formSchemeDao.list().stream().filter(f -> taskKeys.contains(f.getTaskKey())).map(IBaseMetaItem::getKey).collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(formSchemeKeys)) {
                return;
            }
            NrParamCacheManagerService.this.cacheRefreshService.resetDataLink(formSchemeKeys);
        }
    }

    static class NrParamClearInfo
    implements Serializable {
        private final String messageId = UUIDUtils.getKey();

        NrParamClearInfo() {
        }

        public String getMessageId() {
            return this.messageId;
        }
    }

    static class NrParamRefreshInfo
    implements Serializable {
        private final String messageId = UUIDUtils.getKey();
        private final Collection<String> formSchemes;
        private final Collection<String> formulaSchemes;

        public NrParamRefreshInfo(Collection<String> formSchemes, Collection<String> formulaSchemes) {
            this.formSchemes = formSchemes;
            this.formulaSchemes = formulaSchemes;
        }

        public String getMessageId() {
            return this.messageId;
        }

        public Collection<String> getFormSchemes() {
            return this.formSchemes;
        }

        public Collection<String> getFormulaSchemes() {
            return this.formulaSchemes;
        }
    }

    @Component
    @Lazy(value=false)
    public static class NrParamCacheConfig {
        static Duration safeTime;
        static Duration expirationScheduled;
        static Duration memCheckScheduled;
        static double memThreshold;

        public NrParamCacheConfig(@Value(value="${jiuqi.nr.definition.cache.safe-time:1h}") Duration safeTime, @Value(value="${jiuqi.nr.definition.cache.scheduled.expiration:1h}") Duration expirationScheduled, @Value(value="${jiuqi.nr.definition.cache.scheduled.mem-check:10m}") Duration memCheckScheduled, @Value(value="${jiuqi.nr.definition.cache.mem-threshold:80}") double memThreshold) {
            NrParamCacheConfig.safeTime = safeTime;
            NrParamCacheConfig.expirationScheduled = expirationScheduled;
            NrParamCacheConfig.memCheckScheduled = memCheckScheduled;
            NrParamCacheConfig.memThreshold = memThreshold;
        }
    }

    public static class NrParamCacheSettings {
        static boolean enableObsolete = false;
        static long maxMemSize = 0L;
        static long expirationTime = 0L;
        static long memThreshold = 0L;
        static Collection<String> permanent = null;
        static Collection<PreloadSetting> oldPreloadSettings = null;
        static Collection<PreloadSetting> preloadSettings = null;
        static boolean hotUpdate = false;
    }

    public static class PreloadSetting {
        private final String taskKey;
        private final String formSchemeKey;
        private final Map<String, Date> periods;

        public PreloadSetting(String taskKey, String formSchemeKey, Map<String, Date> periods) {
            this.taskKey = taskKey;
            this.formSchemeKey = formSchemeKey;
            this.periods = periods;
        }

        public String getTaskKey() {
            return this.taskKey;
        }

        public String getFormSchemeKey() {
            return this.formSchemeKey;
        }

        public Map<String, Date> getPeriods() {
            return this.periods;
        }

        public String toString() {
            return this.formSchemeKey + "\uff1a" + this.periods.keySet();
        }
    }
}

