/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.i18n.AuditTypeI18nHelper;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.dao.AuditTypeDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaDefineDao;
import com.jiuqi.nr.definition.internal.impl.AuditTypeImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component
public class AuditTypeDefineService {
    private static final String CACHE_NAME = "AUDIT_TYPE_CACHE";
    private static final String ALL_CACHE_KEY = "ALL_AUDIT";
    @Autowired
    private AuditTypeDefineDao auditTypeDefineDao;
    @Autowired
    private DesignFormulaDefineDao designFormulaDefineDao;
    @Autowired
    private RunTimeFormulaDefineDao runTimeFormulaDefineDao;
    @Autowired
    private ApplicationContext applicationContext;
    private NedisCache cache;
    private AuditTypeI18nHelper auditTypeI18nHelper = null;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager().getCache(CACHE_NAME);
    }

    public void setAuditTypeDefineDao(AuditTypeDefineDao auditTypeDefineDao) {
        this.auditTypeDefineDao = auditTypeDefineDao;
    }

    public void setDesignFormulaDefineDao(DesignFormulaDefineDao designFormulaDefineDao) {
        this.designFormulaDefineDao = designFormulaDefineDao;
    }

    private boolean isChinese() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        return language == null || language.equals("zh");
    }

    public List<AuditType> queryAllAuditType() throws Exception {
        if (this.auditTypeI18nHelper == null) {
            this.auditTypeI18nHelper = BeanUtil.getBean(AuditTypeI18nHelper.class);
        }
        ArrayList<AuditType> list = new ArrayList<AuditType>();
        for (AuditType auditType2 : this.queryAudit()) {
            AuditTypeImpl copyData = new AuditTypeImpl();
            copyData.setCode(auditType2.getCode());
            copyData.setTitle(auditType2.getTitle());
            copyData.setColor(auditType2.getColor());
            copyData.setOrder(auditType2.getOrder());
            copyData.setFontColor(auditType2.getFontColor());
            copyData.setBackGroundColor(auditType2.getBackGroundColor());
            copyData.setGridColor(auditType2.getGridColor());
            copyData.setIcon(auditType2.getIcon());
            list.add(copyData);
        }
        list.forEach(auditType -> {
            String message = this.auditTypeI18nHelper.getMessage("audit_type" + auditType.getCode());
            if (!this.isChinese() && !"".equals(message)) {
                auditType.setTitle(message);
            }
        });
        return list;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<AuditType> queryAudit() throws Exception {
        List<AuditType> list;
        Cache.ValueWrapper valueWrapper = this.cache.get(ALL_CACHE_KEY);
        if (valueWrapper != null && valueWrapper.get() != null) {
            return (List)valueWrapper.get();
        }
        AuditTypeDefineService auditTypeDefineService = this;
        synchronized (auditTypeDefineService) {
            valueWrapper = this.cache.get(ALL_CACHE_KEY);
            if (valueWrapper != null && valueWrapper.get() != null) {
                return (List)valueWrapper.get();
            }
            list = this.auditTypeDefineDao.list();
            if (list != null) {
                this.cache.put(ALL_CACHE_KEY, list);
            } else {
                this.cache.put(ALL_CACHE_KEY, new ArrayList());
            }
        }
        return list;
    }

    public void deleteAuditType(Integer code, Integer assignCode) throws Exception {
        this.auditTypeDefineDao.delete(code);
        if (assignCode == null) {
            assignCode = 0;
        }
        this.designFormulaDefineDao.updateFormulaCheckType(code, assignCode);
        this.runTimeFormulaDefineDao.updateFormulaCheckType(code, assignCode);
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshDefinitionCacheEvent());
        this.cache.evict(ALL_CACHE_KEY);
    }

    public void insertAuditType(AuditType auditType) throws Exception {
        this.auditTypeDefineDao.insert(auditType);
        this.cache.evict(ALL_CACHE_KEY);
    }

    public void updateAuditType(AuditType auditType) throws Exception {
        this.auditTypeDefineDao.updateAuditType(auditType);
        this.cache.evict(ALL_CACHE_KEY);
    }
}

