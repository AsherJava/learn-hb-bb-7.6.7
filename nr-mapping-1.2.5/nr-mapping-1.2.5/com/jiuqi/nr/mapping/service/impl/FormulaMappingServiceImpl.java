/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.Transactional
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.nr.mapping.service.impl;

import com.jiuqi.nr.mapping.bean.FormulaMapping;
import com.jiuqi.nr.mapping.dao.FormulaMappingDao;
import com.jiuqi.nr.mapping.dao.MappingSchemeDao;
import com.jiuqi.nr.mapping.service.FormulaMappingService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class FormulaMappingServiceImpl
implements FormulaMappingService {
    protected final Logger logger = LoggerFactory.getLogger(FormulaMappingServiceImpl.class);
    @Autowired
    private FormulaMappingDao dao;
    @Autowired
    private MappingSchemeDao msDao;

    @Override
    public List<FormulaMapping> findByMSFormulaForm(String msKey, String formulaScheme, String formCode) {
        return this.dao.findByMSFormulaForm(msKey, formulaScheme, formCode);
    }

    @Override
    public List<FormulaMapping> findByMS(String msKey) {
        return this.dao.findByMS(msKey);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void save(String msKey, String formulaScheme, String formCode, List<FormulaMapping> fms) {
        ArrayList<FormulaMapping> saveMappings = new ArrayList<FormulaMapping>();
        HashSet<String> FormulaSet = new HashSet<String>();
        for (FormulaMapping fm : fms) {
            if (StringUtil.isEmpty((String)fm.getmFormulaCode()) || !FormulaSet.add(fm.getFormulaCode())) continue;
            if (StringUtil.isEmpty((String)fm.getKey())) {
                fm.setKey(UUID.randomUUID().toString());
            }
            fm.setFormCode(formCode);
            fm.setFormulaScheme(formulaScheme);
            fm.setMappingScheme(msKey);
            saveMappings.add(fm);
        }
        this.dao.deleteByMsFsFc(msKey, formulaScheme, formCode);
        if (!CollectionUtils.isEmpty(saveMappings)) {
            this.dao.batchAdd(saveMappings);
        }
        this.msDao.updateTime(msKey);
    }

    @Override
    public void clear(String msKey, String formulaScheme, String formCode) {
        this.dao.deleteByMsFsFc(msKey, formulaScheme, formCode);
    }
}

