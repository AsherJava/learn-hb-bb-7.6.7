/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Transactional
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.nr.mapping.service.impl;

import com.jiuqi.nr.mapping.bean.UnitMapping;
import com.jiuqi.nr.mapping.bean.UnitRule;
import com.jiuqi.nr.mapping.dao.MappingSchemeDao;
import com.jiuqi.nr.mapping.dao.UnitMappingDao;
import com.jiuqi.nr.mapping.dao.UnitRuleDao;
import com.jiuqi.nr.mapping.service.UnitMappingService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class UnitMappingServiceImpl
implements UnitMappingService {
    @Autowired
    private MappingSchemeDao msDao;
    @Autowired
    private UnitMappingDao dao;
    @Autowired
    private UnitRuleDao ruleDao;

    @Override
    public List<UnitMapping> findByMS(String msKey) {
        Assert.notNull((Object)msKey, "\u67e5\u8be2\u65f6\u671f\u6620\u5c04\uff1a\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        return this.dao.findByMS(msKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveByMS(String msKey, List<UnitMapping> mappings) {
        ArrayList<UnitMapping> saveMappings = new ArrayList<UnitMapping>();
        HashSet<String> unitSet = new HashSet<String>();
        int size = mappings.size();
        for (int i = 0; i < size; ++i) {
            UnitMapping um = mappings.get(i);
            if (StringUtil.isEmpty((String)um.getMapping()) || !unitSet.add(um.getUnitCode())) continue;
            if (StringUtil.isEmpty((String)um.getKey())) {
                um.setKey(UUID.randomUUID().toString());
            }
            um.setMsKey(msKey);
            um.setOrder(i);
            saveMappings.add(um);
        }
        this.dao.deleteByMS(msKey);
        if (!CollectionUtils.isEmpty(saveMappings)) {
            this.dao.add(saveMappings);
        }
        this.msDao.updateTime(msKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void clearByMS(String msKey) {
        Assert.notNull((Object)msKey, "\u6e05\u7a7a\u65f6\u671f\u6620\u5c04\uff1a\u65b9\u6848key\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        this.dao.deleteByMS(msKey);
        this.msDao.updateTime(msKey);
    }

    @Override
    public List<UnitRule> findRuleByMS(String msKey) {
        return this.ruleDao.findByMS(msKey);
    }

    @Override
    public void clearRuleByMS(String msKey) {
        this.ruleDao.deleteByMS(msKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveRule(String msKey, List<UnitRule> rules) {
        Assert.noNullElements(rules, "\u4fdd\u5b58\u5355\u4f4d\u6620\u5c04\uff0c\u5bfc\u5165\u5bfc\u51fa\u516c\u5f0f\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        ArrayList<UnitRule> saveRules = new ArrayList<UnitRule>();
        for (UnitRule r : rules) {
            if (StringUtil.isEmpty((String)r.getExpress())) continue;
            if (StringUtil.isEmpty((String)r.getKey())) {
                r.setKey(UUID.randomUUID().toString());
            }
            r.setMrKey(msKey);
            saveRules.add(r);
        }
        this.ruleDao.deleteByMS(msKey);
        if (!CollectionUtils.isEmpty(saveRules)) {
            this.ruleDao.add(saveRules);
        }
        this.msDao.updateTime(msKey);
    }
}

