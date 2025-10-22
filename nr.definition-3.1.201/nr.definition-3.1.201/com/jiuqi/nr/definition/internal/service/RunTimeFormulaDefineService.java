/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeFormulaDefineService {
    @Autowired
    private RunTimeFormulaDefineDao formulaDao;

    public List<FormulaDefine> queryFormulaDefineByGroupId(String id) throws Exception {
        return this.formulaDao.listByGroup(id);
    }

    public FormulaDefine queryFormulaDefine(String id) throws Exception {
        return this.formulaDao.getDefineByKey(id);
    }

    public FormulaDefine queryFormulaDefineByCode(String code) throws Exception {
        return this.formulaDao.queryDefineByCode(code);
    }

    public List<FormulaDefine> queryFormulaDefines(String code) throws Exception {
        return this.formulaDao.queryDefinesByCode(code);
    }

    public FormulaDefine queryFormulaDefineBySchemeAndCode(String formulaDefineCode, String formulaSchemeKey) throws Exception {
        return this.formulaDao.queryFormulaDefineBySchemeAndCode(formulaDefineCode, formulaSchemeKey);
    }

    public FormulaDefine queryFormulaDefineByFormAndCode(String formulaDefineCode, String formKey) throws Exception {
        return this.formulaDao.queryFormulaDefineBySchemeAndCode(formulaDefineCode, formKey);
    }

    public List<FormulaDefine> queryFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.formulaDao.queryFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey);
    }

    public List<FormulaDefine> queryFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        return this.formulaDao.queryFormulaDefineByScheme(formulaSchemeKey);
    }

    public List<FormulaDefine> queryAllFormulaDefine() throws Exception {
        return this.formulaDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.formulaDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.formulaDao.delete(key);
    }

    public void deleteByForm(String formKey) throws Exception {
        this.formulaDao.deleteByForm(formKey);
    }

    public void deleteBySchemeAndForm(String formSchemeKey, String formKey) throws Exception {
        this.formulaDao.deleteBySchemeAndForm(formSchemeKey, formKey);
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
        this.formulaDao.deleteByScheme(formSchemeKey);
    }

    public FormulaDefine queryFormulaDefinesByCode(String code) throws Exception {
        return this.formulaDao.queryDefineByCode(code);
    }

    public void updateFormulaDefines(FormulaDefine[] defines) throws Exception {
        this.formulaDao.update(defines);
    }

    public List<FormulaDefine> queryFormulaDefines(String[] keys) throws Exception {
        ArrayList<FormulaDefine> list = new ArrayList<FormulaDefine>();
        for (String key : keys) {
            FormulaDefine define = this.formulaDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public void updateFormulaDefine(FormulaDefine define) throws Exception {
        this.formulaDao.update(define);
    }

    public void insertFormulaDefine(FormulaDefine formulaDefine) throws Exception {
        this.formulaDao.insert(formulaDefine);
    }

    public void insertFormulaDefines(FormulaDefine[] formulaDefines) throws Exception {
        this.formulaDao.insert(formulaDefines);
    }

    public List<FormulaDefine> listGhostFormula() {
        return this.formulaDao.listGhostFormula();
    }
}

