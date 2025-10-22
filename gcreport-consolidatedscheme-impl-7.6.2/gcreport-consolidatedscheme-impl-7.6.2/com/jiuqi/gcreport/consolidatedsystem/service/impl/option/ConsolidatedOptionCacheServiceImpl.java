/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedOptionChangedEvent
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO
 *  com.jiuqi.np.cache.NedisCacheManager
 *  jcifs.util.Base64
 *  org.json.JSONObject
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl.option;

import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.option.ConsolidatedOptionDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.ConsolidatedOptionEO;
import com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedOptionChangedEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.List;
import jcifs.util.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

@Service
public class ConsolidatedOptionCacheServiceImpl
implements ConsolidatedOptionCacheService,
ApplicationListener<ConsolidatedOptionChangedEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private NedisCacheManager cacheManger;
    private ConsolidatedOptionDao consolidatedOptionDao;
    private ConsolidatedTaskService consolidatedTaskService;
    private PrimaryWorkpaperService primaryWorkpaperService;

    public ConsolidatedOptionCacheServiceImpl(NedisCacheManager cacheManger, ConsolidatedOptionDao consolidatedOptionDao, ConsolidatedTaskService consolidatedTaskService, PrimaryWorkpaperService primaryWorkpaperService) {
        this.cacheManger = cacheManger;
        this.consolidatedOptionDao = consolidatedOptionDao;
        this.consolidatedTaskService = consolidatedTaskService;
        this.primaryWorkpaperService = primaryWorkpaperService;
    }

    @Override
    public void onApplicationEvent(final ConsolidatedOptionChangedEvent event) {
        TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

            public void afterCommit() {
                if (null == event.getConsolidatedOptionChangedInfo()) {
                    return;
                }
                ConsolidatedOptionCacheServiceImpl.this.cacheManger.getCache("gcreport:conOption").evict(event.getConsolidatedOptionChangedInfo().getSystemId());
            }
        });
    }

    @Override
    public ConsolidatedOptionVO getConOptionBySystemId(String systemId) {
        if (StringUtils.isEmpty(systemId)) {
            return null;
        }
        return (ConsolidatedOptionVO)this.cacheManger.getCache("gcreport:conOption").get(systemId, () -> this.valueLoader(systemId));
    }

    @Override
    public ConsolidatedOptionVO getConOptionBySchemeId(String schemeId, String periodString) {
        return this.getConOptionBySystemId(this.consolidatedTaskService.getSystemIdBySchemeId(schemeId, periodString));
    }

    private ConsolidatedOptionVO valueLoader(String systemId) {
        ConsolidatedOptionEO optionEO = this.consolidatedOptionDao.getOptionDataBySystemId(systemId);
        List<PrimaryWorkpaperTypeVO> primaryWorkpapertypes = this.primaryWorkpaperService.listPrimaryWorkpaperTypesBySystemId(systemId);
        if (optionEO == null || null == optionEO.getData()) {
            ConsolidatedOptionVO vo = new ConsolidatedOptionVO();
            vo.setPrimaryWorkpaperTypes(primaryWorkpapertypes);
            return vo;
        }
        String jsonString = new String(Base64.decode((String)optionEO.getData()));
        ConsolidatedOptionVO optionVO = null;
        try {
            optionVO = (ConsolidatedOptionVO)JsonUtils.readValue((String)jsonString, ConsolidatedOptionVO.class);
        }
        catch (Exception e) {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject.has("carryOverSubjectCodeMapping") && jsonObject.get("carryOverSubjectCodeMapping") != null) {
                jsonObject.remove("carryOverSubjectCodeMapping");
                optionVO = (ConsolidatedOptionVO)JsonUtils.readValue((String)jsonObject.toString(), ConsolidatedOptionVO.class);
            }
            this.logger.error(e.getMessage(), e);
        }
        optionVO.setPrimaryWorkpaperTypes(primaryWorkpapertypes);
        return optionVO;
    }
}

