/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.dao.MappingSchemeDao
 *  org.springframework.transaction.annotation.Transactional
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.nr.mapping2.service.impl;

import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.dao.FormulaMappingDao;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nvwa.mapping.dao.MappingSchemeDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class FormulaMappingServiceImpl
implements FormulaMappingService {
    @Autowired
    private FormulaMappingDao dao;
    @Autowired
    private MappingSchemeDao msDao;

    @Override
    public List<FormulaMapping> findByMSFormulaForm(String msKey, String formulaScheme, String formCode) {
        return this.dao.findByMSFormulaForm(msKey, formulaScheme, formCode);
    }

    @Override
    public Map<String, List<FormulaMapping>> findByMSFormula(String msKey, String formulaScheme) {
        return this.dao.findByMSFormula(msKey, formulaScheme);
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
            this.batchAdd(saveMappings);
        }
        this.msDao.modifyTime(msKey);
    }

    @Override
    public void batchAdd(List<FormulaMapping> mps) {
        int size = mps.size();
        if (size <= 1000) {
            this.dao.batchAdd(mps);
        } else {
            for (int i = 0; i < size; i += 1000) {
                int end = Math.min(i + 1000, size);
                this.dao.batchAdd(mps.subList(i, end));
            }
        }
    }

    @Override
    public void clear(String msKey, String formulaScheme, String formCode) {
        this.dao.deleteByMsFsFc(msKey, formulaScheme, formCode);
    }

    @Override
    public void deleteByMS(String msKey) {
        this.dao.deleteByMS(msKey);
    }
}

