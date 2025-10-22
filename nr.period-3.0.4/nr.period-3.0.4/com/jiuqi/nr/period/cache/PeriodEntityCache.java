/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl
 *  com.jiuqi.nvwa.definition.interval.service.cache.RuntimeDefinitionCache
 */
package com.jiuqi.nr.period.cache;

import com.jiuqi.bi.adhoc.model.TimeGranularity;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.cache.PeriodLanguageCache;
import com.jiuqi.nr.period.common.language.LanguageCommon;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.impl.I18nPeriodEntity;
import com.jiuqi.nr.period.service.impl.PeriodAdapterServiceImpl;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.interval.bean.design.DesignColumnModelDefineImpl;
import com.jiuqi.nvwa.definition.interval.service.cache.RuntimeDefinitionCache;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class PeriodEntityCache {
    Logger logger = LoggerFactory.getLogger(PeriodEntityCache.class);
    @Autowired
    private PeriodAdapterServiceImpl periodAdapterService;
    @Autowired
    private PeriodDataDao periodDataDao;
    @Autowired
    private PeriodLanguageCache periodLanguageCache;
    private NedisCacheManager cacheManager;
    private RuntimeDefinitionCache<IPeriodEntity> cache;
    private static final String IDX_NAME_ALL = "allperiodentity";

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager();
        this.cache = new RuntimeDefinitionCache(this.cacheManager, IPeriodEntity.class);
    }

    public void clearCache() {
        this.cache.clear();
    }

    private List<IPeriodEntity> sort(List<IPeriodEntity> iPeriodEntities) {
        ArrayList<IPeriodEntity> sortList = new ArrayList<IPeriodEntity>();
        IPeriodEntity n = null;
        IPeriodEntity h = null;
        IPeriodEntity j = null;
        IPeriodEntity y = null;
        IPeriodEntity x = null;
        IPeriodEntity z = null;
        IPeriodEntity r = null;
        ArrayList<IPeriodEntity> cw = new ArrayList<IPeriodEntity>();
        ArrayList<IPeriodEntity> cus = new ArrayList<IPeriodEntity>();
        for (IPeriodEntity iPeriodEntity : iPeriodEntities) {
            switch (iPeriodEntity.getPeriodType()) {
                case YEAR: {
                    n = iPeriodEntity;
                    break;
                }
                case HALFYEAR: {
                    h = iPeriodEntity;
                    break;
                }
                case SEASON: {
                    j = iPeriodEntity;
                    break;
                }
                case MONTH: {
                    if (PeriodUtils.isPeriod13(iPeriodEntity.getCode(), iPeriodEntity.getPeriodType())) {
                        cw.add(iPeriodEntity);
                        break;
                    }
                    y = iPeriodEntity;
                    break;
                }
                case TENDAY: {
                    x = iPeriodEntity;
                    break;
                }
                case WEEK: {
                    z = iPeriodEntity;
                    break;
                }
                case DAY: {
                    r = iPeriodEntity;
                    break;
                }
                case CUSTOM: {
                    cus.add(iPeriodEntity);
                    break;
                }
            }
        }
        if (null != n) {
            sortList.add(n);
        }
        if (null != h) {
            sortList.add(h);
        }
        if (null != j) {
            sortList.add(j);
        }
        if (null != y) {
            sortList.add(y);
        }
        if (!cw.isEmpty()) {
            List collect = cw.stream().sorted((a, b) -> a.getCreateTime().compareTo(b.getCreateTime())).collect(Collectors.toList());
            sortList.addAll(collect);
        }
        if (null != x) {
            sortList.add(x);
        }
        if (null != z) {
            sortList.add(z);
        }
        if (null != r) {
            sortList.add(r);
        }
        if (!cus.isEmpty()) {
            sortList.addAll(cus);
        }
        return sortList;
    }

    private List<IPeriodEntity> getAllPeriodEntity() {
        List<IPeriodEntity> iPeriodEntities = this.initAllPeriodEntity();
        return iPeriodEntities;
    }

    private String getLanguage() {
        return NpContextHolder.getContext().getLocale().getLanguage();
    }

    private List<IPeriodEntity> initAllPeriodEntity() {
        Cache.ValueWrapper valueWrapper = this.cache.getKVIndexValue(IDX_NAME_ALL);
        if (valueWrapper == null) {
            return (List)this.cache.synchronizedRun(() -> {
                Cache.ValueWrapper revalueWrapper = this.cache.getKVIndexValue(IDX_NAME_ALL);
                if (Objects.nonNull(revalueWrapper)) {
                    return this.getIPeriodEntity(revalueWrapper);
                }
                return this.loadCache();
            });
        }
        return this.getIPeriodEntity(valueWrapper);
    }

    private List<IPeriodEntity> loadCache() {
        List<IPeriodEntity> periodEntitys = this.loadAllIPeriodEntitys();
        ArrayList<String> allPeriodKeys = new ArrayList<String>();
        for (IPeriodEntity period : periodEntitys) {
            String periodKey = period.getKey();
            allPeriodKeys.add(periodKey);
        }
        this.cache.putObjects(periodEntitys);
        this.cache.putKVIndex(IDX_NAME_ALL, allPeriodKeys);
        return periodEntitys;
    }

    private List<IPeriodEntity> loadAllIPeriodEntitys() {
        List<IPeriodEntity> periodEntitys = this.periodAdapterService.getPeriodList();
        return this.sort(periodEntitys);
    }

    private List<IPeriodEntity> getIPeriodEntity(Cache.ValueWrapper revalueWrapper) {
        List allPeriodKeys = (List)revalueWrapper.get();
        Map cachedEntitys = this.cache.getObjects((Collection)allPeriodKeys);
        ArrayList<IPeriodEntity> periodEntitys = new ArrayList<IPeriodEntity>(cachedEntitys.size());
        for (Cache.ValueWrapper v : cachedEntitys.values()) {
            if (v == null) {
                return (List)this.cache.synchronizedRun(() -> this.loadCache());
            }
            periodEntitys.add((IPeriodEntity)v.get());
        }
        return periodEntitys;
    }

    public List<IPeriodEntity> getPeriodList() {
        List<IPeriodEntity> iPeriodEntities = this.getAllPeriodEntity();
        ArrayList<IPeriodEntity> cloneData = new ArrayList<IPeriodEntity>();
        for (IPeriodEntity iPeriodEntity : iPeriodEntities) {
            cloneData.add(this.getI18nPeriodEntity(iPeriodEntity));
        }
        return cloneData;
    }

    public TableModelDefine getPeriodEntity(String entityId) {
        IPeriodEntity periodEntity = this.getPeriodByKey(entityId);
        if (periodEntity != null) {
            return this.periodDataDao.getTableModelDefine(periodEntity.getCode());
        }
        return null;
    }

    public TableModelDefine getPeriodTableByTableKey(String key) {
        return this.periodDataDao.getTableModelDefineByKey(key);
    }

    public IPeriodEntity getPeriodByCode(String code) {
        Cache.ValueWrapper valueWrapper = this.cache.getObject(code);
        if (valueWrapper == null) {
            return this.getPeriodByCodeOld(code);
        }
        return this.getI18nPeriodEntity((IPeriodEntity)valueWrapper.get());
    }

    public IPeriodEntity getPeriodByCodeOld(String code) {
        List<IPeriodEntity> allPeriodEntity = this.getAllPeriodEntity();
        for (IPeriodEntity periodEntity : allPeriodEntity) {
            if (!periodEntity.getCode().equals(code)) continue;
            return this.getI18nPeriodEntity(periodEntity);
        }
        return null;
    }

    public IPeriodEntity getPeriodByKey(String entityId) {
        Cache.ValueWrapper valueWrapper = this.cache.getObject(entityId);
        if (valueWrapper == null) {
            return this.getPeriodByKeyOld(entityId);
        }
        return this.getI18nPeriodEntity((IPeriodEntity)valueWrapper.get());
    }

    public IPeriodEntity getPeriodByKeyOld(String entityId) {
        List<IPeriodEntity> allPeriodEntity = this.getAllPeriodEntity();
        for (IPeriodEntity periodEntity : allPeriodEntity) {
            if (!periodEntity.getKey().equals(entityId)) continue;
            return this.getI18nPeriodEntity(periodEntity);
        }
        return null;
    }

    public List<IPeriodEntity> getPeriodByType(PeriodType periodType) {
        List<IPeriodEntity> allPeriodEntity = this.getAllPeriodEntity();
        ArrayList<IPeriodEntity> list = new ArrayList<IPeriodEntity>();
        for (IPeriodEntity periodEntity : allPeriodEntity) {
            if (periodEntity.getType().type() != periodType.type()) continue;
            list.add(this.getI18nPeriodEntity(periodEntity));
        }
        return list;
    }

    private I18nPeriodEntity getI18nPeriodEntity(IPeriodEntity periodEntity) {
        I18nPeriodEntity i18nPeriodEntity = new I18nPeriodEntity(periodEntity);
        i18nPeriodEntity.setTitle(LanguageCommon.getPeriodEntityTitle(periodEntity.getPeriodType(), periodEntity.getTitle()));
        return i18nPeriodEntity;
    }

    public List<ColumnModelDefine> getPeriodEntityColumnModel(String entityId) {
        TableModelDefine periodEntity = this.getPeriodEntity(entityId);
        return this.periodDataDao.getColumnModelDefinesByTable(periodEntity.getID());
    }

    public List<ColumnModelDefine> getPeriodEntityShowColumnModel(String entityId) {
        IPeriodEntity periodByKey = this.getPeriodByKey(entityId);
        TableModelDefine periodEntity = this.getPeriodEntity(entityId);
        List<ColumnModelDefine> columns = this.periodDataDao.getColumnModelDefinesByTable(periodEntity.getID());
        ArrayList<ColumnModelDefine> showColumns = new ArrayList<ColumnModelDefine>();
        switch (periodByKey.getPeriodType()) {
            case YEAR: {
                this.addShowColumn(showColumns, PeriodTableColumn.TIMEKEY, columns, periodByKey.getPeriodType());
                this.addShowColumn(showColumns, PeriodTableColumn.YEAR, columns, PeriodType.YEAR);
                break;
            }
            case HALFYEAR: {
                this.addShowColumn(showColumns, PeriodTableColumn.TIMEKEY, columns, periodByKey.getPeriodType());
                this.addShowColumn(showColumns, PeriodTableColumn.YEAR, columns, PeriodType.YEAR);
                break;
            }
            case SEASON: {
                this.addShowColumn(showColumns, PeriodTableColumn.TIMEKEY, columns, periodByKey.getPeriodType());
                this.addShowColumn(showColumns, PeriodTableColumn.YEAR, columns, PeriodType.YEAR);
                this.addShowColumn(showColumns, PeriodTableColumn.QUARTER, columns, PeriodType.SEASON);
                break;
            }
            case MONTH: {
                this.addShowColumn(showColumns, PeriodTableColumn.TIMEKEY, columns, periodByKey.getPeriodType());
                this.addShowColumn(showColumns, PeriodTableColumn.YEAR, columns, PeriodType.YEAR);
                this.addShowColumn(showColumns, PeriodTableColumn.QUARTER, columns, PeriodType.SEASON);
                this.addShowColumn(showColumns, PeriodTableColumn.MONTH, columns, PeriodType.MONTH);
                break;
            }
            case TENDAY: {
                this.addShowColumn(showColumns, PeriodTableColumn.TIMEKEY, columns, periodByKey.getPeriodType());
                this.addShowColumn(showColumns, PeriodTableColumn.YEAR, columns, PeriodType.YEAR);
                this.addShowColumn(showColumns, PeriodTableColumn.QUARTER, columns, PeriodType.SEASON);
                this.addShowColumn(showColumns, PeriodTableColumn.MONTH, columns, PeriodType.MONTH);
                break;
            }
            case WEEK: {
                this.addShowColumn(showColumns, PeriodTableColumn.TIMEKEY, columns, periodByKey.getPeriodType());
                this.addShowColumn(showColumns, PeriodTableColumn.YEAR, columns, PeriodType.YEAR);
                this.addShowColumn(showColumns, PeriodTableColumn.QUARTER, columns, PeriodType.SEASON);
                this.addShowColumn(showColumns, PeriodTableColumn.MONTH, columns, PeriodType.MONTH);
                break;
            }
            case DAY: {
                this.addShowColumn(showColumns, PeriodTableColumn.TIMEKEY, columns, periodByKey.getPeriodType());
                this.addShowColumn(showColumns, PeriodTableColumn.YEAR, columns, PeriodType.YEAR);
                this.addShowColumn(showColumns, PeriodTableColumn.QUARTER, columns, PeriodType.SEASON);
                this.addShowColumn(showColumns, PeriodTableColumn.MONTH, columns, PeriodType.MONTH);
                this.addShowColumn(showColumns, PeriodTableColumn.DAY, columns, PeriodType.DAY);
                break;
            }
            case CUSTOM: {
                this.addShowColumn(showColumns, PeriodTableColumn.TITLE, columns, periodByKey.getPeriodType());
                break;
            }
        }
        return showColumns;
    }

    private void addShowColumn(List<ColumnModelDefine> showColumns, PeriodTableColumn year, List<ColumnModelDefine> columns, PeriodType periodType) {
        int idx = 0;
        for (ColumnModelDefine c : columns) {
            if (!c.getCode().equals(year.getCode())) continue;
            DesignColumnModelDefineImpl changeTitle = new DesignColumnModelDefineImpl();
            BeanUtils.copyProperties(c, changeTitle);
            TimeGranularity adaptTimeGranularity = BqlTimeDimUtils.adaptTimeGranularity(periodType);
            if (null != adaptTimeGranularity) {
                changeTitle.setTitle(adaptTimeGranularity.title());
            } else {
                changeTitle.setTitle("");
            }
            if (year == PeriodTableColumn.TIMEKEY || PeriodType.CUSTOM.equals((Object)periodType)) {
                changeTitle.setTitle("\u65f6\u671f");
            }
            changeTitle.setOrder((double)idx++);
            showColumns.add((ColumnModelDefine)changeTitle);
        }
    }
}

