/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeFormulaSchemeDefineService {
    @Autowired
    private RunTimeFormulaSchemeDefineDao formulaSchemeDao;

    public void setFormulaSchemeDao(RunTimeFormulaSchemeDefineDao formulaSchemeDao) {
        this.formulaSchemeDao = formulaSchemeDao;
    }

    public List<FormulaSchemeDefine> queryFormulaSchemeDefineByFormScheme(String formSchemeKey) {
        return this.formulaSchemeDao.listByFormScheme(formSchemeKey);
    }

    public FormulaSchemeDefine queryFormulaSchemeDefine(String id) throws Exception {
        return this.formulaSchemeDao.getDefineByKey(id);
    }

    public FormulaSchemeDefine queryFormulaSchemeDefineByCode(String code) throws Exception {
        return this.formulaSchemeDao.queryDefinesByCode(code);
    }

    public List<FormulaSchemeDefine> queryAllFormulaSchemeDefine() throws Exception {
        return this.formulaSchemeDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.formulaSchemeDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.formulaSchemeDao.delete(key);
    }

    public FormulaSchemeDefine queryFormulaSchemeDefinesByCode(String code) throws Exception {
        return this.formulaSchemeDao.queryDefinesByCode(code);
    }

    public void updateFormulaSchemeDefines(FormulaSchemeDefine[] defines) throws Exception {
        this.formulaSchemeDao.update(defines);
    }

    public List<FormulaSchemeDefine> queryFormulaSchemeDefines(String[] keys) throws Exception {
        ArrayList<FormulaSchemeDefine> list = new ArrayList<FormulaSchemeDefine>();
        for (String key : keys) {
            FormulaSchemeDefine define = this.formulaSchemeDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public void updateFormulaSchemeDefine(FormulaSchemeDefine define) throws Exception {
        this.formulaSchemeDao.update(define);
    }

    public void insertFormulaSchemeDefine(FormulaSchemeDefine formulaSchemeDefine) throws Exception {
        this.formulaSchemeDao.insert(formulaSchemeDefine);
    }

    public void insertFormulaSchemeDefines(FormulaSchemeDefine[] formulaSchemeDefines) throws Exception {
        this.formulaSchemeDao.insert(formulaSchemeDefines);
    }

    public List<FormulaSchemeDefine> listGhostScheme() {
        return this.formulaSchemeDao.listGhostScheme();
    }
}

