/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DoubleKeyMap
 *  com.jiuqi.np.cache.message.MessagePublisher
 *  com.jiuqi.np.cache.message.Subscriber
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.consolidatedsystem.cache.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DoubleKeyMap;
import com.jiuqi.gcreport.consolidatedsystem.cache.SubjectCache;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.np.cache.message.MessagePublisher;
import com.jiuqi.np.cache.message.Subscriber;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Subscriber(channels={"com.jiuqi.gcreport.consolidatedsystem.conSubject"})
public class SubjectCacheImpl
implements SubjectCache {
    @Autowired
    private MessagePublisher messagePublisher;
    private Object mutex = new Object();
    private DoubleKeyMap<String, String, ConsolidatedSubjectEO> code2EntityMapCache = new DoubleKeyMap();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    static final String CHANNEL = "com.jiuqi.gcreport.consolidatedsystem.conSubject";
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public void clearCache(String systemId) {
        this.exeClearCache(systemId);
    }

    @Override
    public void clearCache() {
        HashSet systemIds = new HashSet(this.code2EntityMapCache.keySet());
        for (String systemId : systemIds) {
            this.clearCache(systemId);
        }
    }

    @Override
    public List<ConsolidatedSubjectEO> listSubjectsBySystemId(@NotNull String systemId) {
        Map code2SubjectMap;
        if (!this.code2EntityMapCache.containsKey((Object)systemId)) {
            this.loadCache(systemId);
        }
        return (code2SubjectMap = this.code2EntityMapCache.get((Object)systemId)) == null ? new ArrayList<ConsolidatedSubjectEO>() : new ArrayList(code2SubjectMap.values());
    }

    @Override
    public ConsolidatedSubjectEO getSubjectByCode(String systemId, String subjectCode) {
        if (!this.code2EntityMapCache.containsKey((Object)systemId)) {
            this.loadCache(systemId);
        }
        return (ConsolidatedSubjectEO)this.code2EntityMapCache.get((Object)systemId, (Object)subjectCode);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void exeClearCache(String systemId) {
        Object object = this.mutex;
        synchronized (object) {
            this.code2EntityMapCache.remove((Object)systemId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadCache(String systemId) {
        Object object = this.mutex;
        synchronized (object) {
            List<ConsolidatedSubjectEO> allSubjects = this.valueLoader(systemId);
            this.setIsLeafNodeStatus(allSubjects);
            for (ConsolidatedSubjectEO subject : allSubjects) {
                if (null == subject) continue;
                this.code2EntityMapCache.put((Object)subject.getSystemId(), (Object)subject.getCode(), (Object)subject);
            }
        }
    }

    private void setIsLeafNodeStatus(List<ConsolidatedSubjectEO> allSubjects) {
        if (CollectionUtils.isEmpty(allSubjects)) {
            return;
        }
        HashSet<String> parentSubjectCodes = new HashSet<String>();
        for (ConsolidatedSubjectEO subject : allSubjects) {
            if (subject.getParentCode() == null) continue;
            parentSubjectCodes.add(subject.getParentCode());
        }
        for (ConsolidatedSubjectEO subject : allSubjects) {
            subject.setLeafFlag(!parentSubjectCodes.contains(subject.getCode()));
        }
    }

    private List<ConsolidatedSubjectEO> valueLoader(String systemId) {
        return this.cacheAllSubjects(systemId);
    }

    private List<ConsolidatedSubjectEO> cacheAllSubjects(String systemId) {
        List<ConsolidatedSubjectEO> allSubjects = this.findAllSubjects(systemId);
        if (CollectionUtils.isEmpty(allSubjects)) {
            this.logger.warn("\u672a\u52a0\u8f7d\u5230\u79d1\u76ee");
        }
        return allSubjects;
    }

    private List<ConsolidatedSubjectEO> findAllSubjects(String systemId) {
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_GCSUBJECT");
        param.put("systemid", (Object)systemId);
        ArrayList sortList = new ArrayList();
        param.setOrderBy(sortList);
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO pageVO = this.baseDataClient.list(param);
        if (pageVO.getTotal() == 0) {
            return Collections.emptyList();
        }
        List subjects = pageVO.getRows();
        ArrayList<ConsolidatedSubjectEO> subjectEOs = new ArrayList<ConsolidatedSubjectEO>();
        for (BaseDataDO subject : subjects) {
            ConsolidatedSubjectEO entity = SubjectConvertUtil.convertBdoToGcSubjectEO(subject);
            subjectEOs.add(entity);
        }
        this.setIsLeafNodeStatus(subjectEOs);
        return subjectEOs;
    }
}

