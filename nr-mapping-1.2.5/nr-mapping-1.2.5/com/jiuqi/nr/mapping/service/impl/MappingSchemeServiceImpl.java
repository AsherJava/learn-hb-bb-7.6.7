/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nr.mapping.bean.BaseDataMapping;
import com.jiuqi.nr.mapping.bean.FormulaMapping;
import com.jiuqi.nr.mapping.bean.JIOConfig;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.bean.PeriodMapping;
import com.jiuqi.nr.mapping.bean.UnitMapping;
import com.jiuqi.nr.mapping.bean.UnitRule;
import com.jiuqi.nr.mapping.bean.ZBMapping;
import com.jiuqi.nr.mapping.common.MappingErrorEnum;
import com.jiuqi.nr.mapping.dao.BaseDataItemMappingDao;
import com.jiuqi.nr.mapping.dao.BaseDataMappingDao;
import com.jiuqi.nr.mapping.dao.FormulaMappingDao;
import com.jiuqi.nr.mapping.dao.JIOConfigDao;
import com.jiuqi.nr.mapping.dao.MappingSchemeDao;
import com.jiuqi.nr.mapping.dao.PeriodMappingDao;
import com.jiuqi.nr.mapping.dao.UnitMappingDao;
import com.jiuqi.nr.mapping.dao.UnitRuleDao;
import com.jiuqi.nr.mapping.dao.ZBMappingDao;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MappingSchemeServiceImpl
implements MappingSchemeService {
    @Autowired
    private MappingSchemeDao dao;
    @Autowired
    private UnitMappingDao unitDao;
    @Autowired
    private UnitRuleDao ruleDao;
    @Autowired
    private PeriodMappingDao periodDao;
    @Autowired
    private ZBMappingDao zbDao;
    @Autowired
    private BaseDataMappingDao baseDataDao;
    @Autowired
    private BaseDataItemMappingDao baseDataItemDao;
    @Autowired
    private FormulaMappingDao formulaDao;
    @Autowired
    private JIOConfigDao jioDao;

    @Override
    public String add(MappingScheme mappingScheme) throws JQException {
        Assert.notNull((Object)mappingScheme, "mappingScheme must not be null.");
        if (!StringUtils.hasText(mappingScheme.getKey())) {
            mappingScheme.setKey(UUID.randomUUID().toString());
        }
        if (!StringUtils.hasText(mappingScheme.getTitle())) {
            throw new JQException((ErrorEnum)MappingErrorEnum.SCHEME_003);
        }
        if (!StringUtils.hasText(mappingScheme.getCode())) {
            throw new JQException((ErrorEnum)MappingErrorEnum.SCHEME_004);
        }
        if (!StringUtils.hasText(mappingScheme.getTask())) {
            throw new JQException((ErrorEnum)MappingErrorEnum.SCHEME_005);
        }
        if (!StringUtils.hasText(mappingScheme.getFormScheme())) {
            throw new JQException((ErrorEnum)MappingErrorEnum.SCHEME_006);
        }
        this.duplicateCheck(mappingScheme);
        return this.dao.add(mappingScheme);
    }

    @Override
    public MappingScheme getByKey(String key) {
        return this.dao.findByKey(key);
    }

    @Override
    public MappingScheme getByCode(String code) {
        return this.dao.findByCode(code);
    }

    @Override
    public List<MappingScheme> getMappingByTask(String taskKey) {
        return this.dao.findByTask(taskKey);
    }

    @Override
    public List<MappingScheme> getMappingByTaskForm(String taskKey, String formKey) {
        return this.dao.findByTaskForm(taskKey, formKey);
    }

    @Override
    public List<MappingScheme> getJIOMappingByTaskForm(String taskKey, String formKey) {
        return this.dao.findByTaskFormJIO(taskKey, formKey);
    }

    @Override
    public List<MappingScheme> getAllScheme() {
        return this.dao.findAll();
    }

    @Override
    public void rename(MappingScheme mappingScheme) throws JQException {
        Assert.notNull((Object)mappingScheme, "mappingScheme must not be null.");
        if (!StringUtils.hasText(mappingScheme.getTitle())) {
            throw new JQException((ErrorEnum)MappingErrorEnum.SCHEME_003);
        }
        List<MappingScheme> MSList = this.getMappingByTaskForm(mappingScheme.getTask(), mappingScheme.getFormScheme());
        for (MappingScheme m : MSList) {
            if (!m.getTitle().equals(mappingScheme.getTitle())) continue;
            throw new JQException((ErrorEnum)MappingErrorEnum.SCHEME_007);
        }
        this.dao.rename(mappingScheme.getKey(), mappingScheme.getTitle());
    }

    @Override
    public void updateTime(String key) {
        this.dao.updateTime(key);
    }

    @Override
    public void deleteByKey(String key) {
        this.dao.delete(key);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteMappingsByKey(String key) {
        this.unitDao.deleteByMS(key);
        this.ruleDao.deleteByMS(key);
        this.periodDao.deleteByMS(key);
        this.zbDao.deleteByMS(key);
        this.baseDataDao.deleteByMS(key);
        this.baseDataItemDao.deleteByMS(key);
        this.formulaDao.deleteByMS(key);
        this.jioDao.deleteByMS(key);
    }

    @Override
    public void batchDelete(List<String> keys) {
        this.dao.batchDelete(keys);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteMappingsByKey(List<String> keys) {
        this.unitDao.batchDeleteByMS(keys);
        this.ruleDao.batchDeleteByMS(keys);
        this.periodDao.batchDeleteByMS(keys);
        this.zbDao.batchDeleteByMS(keys);
        this.baseDataDao.batchDeleteByMS(keys);
        this.baseDataItemDao.batchDeleteByMS(keys);
        this.formulaDao.batchDeleteByMS(keys);
        this.jioDao.batchDeleteByMS(keys);
    }

    @Override
    public List<MappingScheme> fuzzySearch(String keyword) {
        ArrayList<MappingScheme> res = new ArrayList<MappingScheme>();
        HashSet<String> keys = new HashSet<String>();
        List<MappingScheme> titleRes = this.dao.fuzzySearchTitle(keyword);
        List<MappingScheme> codeRes = this.dao.fuzzySearchCode(keyword);
        if (!CollectionUtils.isEmpty(titleRes)) {
            for (MappingScheme m : titleRes) {
                if (!keys.add(m.getKey())) continue;
                res.add(m);
            }
        }
        if (!CollectionUtils.isEmpty(codeRes)) {
            for (MappingScheme m : codeRes) {
                if (!keys.add(m.getKey())) continue;
                res.add(m);
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void copyMapping(String oldKey, String key) {
        JIOConfig jioconfig;
        List<FormulaMapping> fMappings;
        List<BaseDataItemMapping> baseItemMappings;
        List<BaseDataMapping> baseMappings;
        List<ZBMapping> zbMappings;
        List<PeriodMapping> pMappings;
        List<UnitRule> unitRules;
        List<UnitMapping> uMappings = this.unitDao.findByMS(oldKey);
        if (!CollectionUtils.isEmpty(uMappings)) {
            List<UnitMapping> newMappings = uMappings.stream().map(u -> {
                u.setKey(UUID.randomUUID().toString());
                u.setMsKey(key);
                return u;
            }).collect(Collectors.toList());
            this.unitDao.add(newMappings);
        }
        if (!CollectionUtils.isEmpty(unitRules = this.ruleDao.findByMS(oldKey))) {
            List<UnitRule> newMappings = unitRules.stream().map(u -> {
                u.setKey(UUID.randomUUID().toString());
                u.setMrKey(key);
                return u;
            }).collect(Collectors.toList());
            this.ruleDao.add(newMappings);
        }
        if (!CollectionUtils.isEmpty(pMappings = this.periodDao.findByMS(oldKey))) {
            List<PeriodMapping> newMappings = pMappings.stream().map(u -> {
                u.setKey(UUID.randomUUID().toString());
                u.setMsKey(key);
                return u;
            }).collect(Collectors.toList());
            this.periodDao.add(newMappings);
        }
        if (!CollectionUtils.isEmpty(zbMappings = this.zbDao.findByMS(oldKey))) {
            List<ZBMapping> newMappings = zbMappings.stream().map(u -> {
                u.setKey(UUID.randomUUID().toString());
                u.setMsKey(key);
                return u;
            }).collect(Collectors.toList());
            this.zbDao.batchAdd(newMappings);
        }
        if (!CollectionUtils.isEmpty(baseMappings = this.baseDataDao.findByMS(oldKey))) {
            List<BaseDataMapping> newMappings = baseMappings.stream().map(u -> {
                u.setKey(UUID.randomUUID().toString());
                u.setMsKey(key);
                return u;
            }).collect(Collectors.toList());
            this.baseDataDao.batchAdd(newMappings);
        }
        if (!CollectionUtils.isEmpty(baseItemMappings = this.baseDataItemDao.findByMS(oldKey))) {
            List<BaseDataItemMapping> newMappings = baseItemMappings.stream().map(u -> {
                u.setKey(UUID.randomUUID().toString());
                u.setMsKey(key);
                return u;
            }).collect(Collectors.toList());
            this.baseDataItemDao.batchAdd(newMappings);
        }
        if (!CollectionUtils.isEmpty(fMappings = this.formulaDao.findByMS(oldKey))) {
            List<FormulaMapping> newMappings = fMappings.stream().map(u -> {
                u.setKey(UUID.randomUUID().toString());
                u.setMappingScheme(key);
                return u;
            }).collect(Collectors.toList());
            this.formulaDao.batchAdd(newMappings);
        }
        if ((jioconfig = this.jioDao.findByMS(oldKey)) != null) {
            jioconfig.setKey(UUID.randomUUID().toString());
            jioconfig.setMsKey(key);
            this.jioDao.add(jioconfig);
        }
    }

    private void duplicateCheck(MappingScheme mappingScheme) throws JQException {
        List<MappingScheme> MSList = this.getMappingByTaskForm(mappingScheme.getTask(), mappingScheme.getFormScheme());
        for (MappingScheme m : MSList) {
            if (!m.getTitle().equals(mappingScheme.getTitle())) continue;
            throw new JQException((ErrorEnum)MappingErrorEnum.SCHEME_007);
        }
        List<MappingScheme> allMSList = this.getAllScheme();
        for (MappingScheme m : allMSList) {
            if (!m.getCode().equals(mappingScheme.getCode())) continue;
            throw new JQException((ErrorEnum)MappingErrorEnum.SCHEME_008);
        }
    }
}

