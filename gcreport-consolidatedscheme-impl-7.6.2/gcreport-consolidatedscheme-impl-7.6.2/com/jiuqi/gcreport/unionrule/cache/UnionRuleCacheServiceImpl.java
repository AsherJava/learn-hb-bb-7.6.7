/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.np.cache.NedisCacheManager
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.transaction.support.TransactionSynchronizationManager
 */
package com.jiuqi.gcreport.unionrule.cache;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.unionrule.cache.UnionRuleCacheService;
import com.jiuqi.gcreport.unionrule.cache.UnionRuleChangedEvent;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import com.jiuqi.np.cache.NedisCacheManager;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class UnionRuleCacheServiceImpl
implements ApplicationListener<UnionRuleChangedEvent>,
UnionRuleCacheService {
    @Autowired
    private NedisCacheManager cacheManger;
    @Autowired
    private UnionRuleDao unionRuleDao;
    private final String CACHE_NAME = "UNION_RULE_CACHE";

    @Override
    public void onApplicationEvent(UnionRuleChangedEvent event) {
        String systemId = event.getUnionRuleChangedInfo().getSystemId();
        this.clearCache(systemId);
    }

    @Override
    public List<AbstractUnionRule> getRulesBySystemId(String systemId) {
        return (List)this.cacheManger.getCache("UNION_RULE_CACHE").get(systemId, () -> this.valueLoader(systemId));
    }

    private List<AbstractUnionRule> valueLoader(String systemId) {
        List<UnionRuleEO> rules = this.unionRuleDao.findRuleListByReportSystem(systemId);
        if (CollectionUtils.isEmpty(rules)) {
            return CollectionUtils.newArrayList();
        }
        return rules.stream().map(UnionRuleConverter::convert).collect(Collectors.toList());
    }

    @Override
    public void clearCache(final String systemId) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization((TransactionSynchronization)new TransactionSynchronization(){

                public void afterCommit() {
                    UnionRuleCacheServiceImpl.this.cacheManger.getCache("UNION_RULE_CACHE").evict(systemId);
                }
            });
        } else {
            this.cacheManger.getCache("UNION_RULE_CACHE").evict(systemId);
        }
    }
}

