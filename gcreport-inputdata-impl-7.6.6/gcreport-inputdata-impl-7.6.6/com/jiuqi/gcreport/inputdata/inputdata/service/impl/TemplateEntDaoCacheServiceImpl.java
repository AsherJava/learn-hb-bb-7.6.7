/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableFinishedEvent
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.inputdata.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableFinishedEvent;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TemplateEntDaoCacheServiceImpl
implements TemplateEntDaoCacheService {
    private NedisCacheManager cacheManger;
    private DataModelService dataModelService;
    private Logger logger = LoggerFactory.getLogger(TemplateEntDaoCacheServiceImpl.class);

    public TemplateEntDaoCacheServiceImpl(NedisCacheManager cacheManger, DataModelService dataModelService) {
        this.cacheManger = cacheManger;
        this.dataModelService = dataModelService;
    }

    @Override
    public <T extends BaseEntity> EntNativeSqlDefaultDao<T> getTemplateEntDao(String tableName, Class<T> clzz) {
        return (EntNativeSqlDefaultDao)this.cacheManger.getCache("gcreport:templateEntSqlDao").get(tableName, () -> this.valueLoader(tableName, clzz));
    }

    private <T extends BaseEntity> EntNativeSqlDefaultDao<T> valueLoader(String tableName, Class<T> clzz) {
        EntNativeSqlDefaultDao templateEntityDao = EntNativeSqlDefaultDao.newInstance((String)tableName, clzz);
        return templateEntityDao;
    }

    @EventListener
    public void onApplicationEvent(DeployTableFinishedEvent event) {
        List tableDefines;
        try {
            tableDefines = this.dataModelService.getTableModelDefinesByIds((Collection)event.getTableParams().getTable().getRunTimeKeys());
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u8868\u5b9a\u4e49\u5f02\u5e38\u3002", e);
            return;
        }
        if (CollectionUtils.isEmpty((Collection)tableDefines)) {
            return;
        }
        Set tableNames = tableDefines.stream().filter(tableModelDefine -> tableModelDefine.getName().contains("GC_INPUTDATA")).map(TableModelDefine::getName).collect(Collectors.toSet());
        this.cacheManger.getCache("gcreport:templateEntSqlDao").mEvict(tableNames);
    }
}

