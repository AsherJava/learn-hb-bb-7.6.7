/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaDefineDao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DesignFormulaDefineService {
    @Autowired
    private DesignFormulaDefineDao formulaDao;

    public void setFormulaDao(DesignFormulaDefineDao formulaDao) {
        this.formulaDao = formulaDao;
    }

    public String insertFormulaDefine(DesignFormulaDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        define.setIsPrivate(false);
        if (define.getOrdinal() == null) {
            define.setOrdinal(BigDecimal.valueOf(OrderGenerator.newOrderID()));
        }
        this.formulaDao.insert(define);
        return define.getKey();
    }

    public void updateFormulaDefine(DesignFormulaDefine define) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        define.setIsPrivate(false);
        if (define.getOrdinal() == null) {
            define.setOrdinal(BigDecimal.valueOf(OrderGenerator.newOrderID()));
        }
        this.formulaDao.update(define);
    }

    public List<DesignFormulaDefine> queryFormulaDefineByGroupId(String id) throws Exception {
        return this.formulaDao.listByGroup(id);
    }

    public DesignFormulaDefine queryFormulaDefine(String id) throws Exception {
        return this.formulaDao.getDefineByKey(id);
    }

    public DesignFormulaDefine queryFormulaDefineByCode(String code) throws Exception {
        return this.formulaDao.queryDefineByCode(code);
    }

    public List<DesignFormulaDefine> queryFormulaDefines(String code) throws Exception {
        return this.formulaDao.queryDefinesByCode(code);
    }

    public DesignFormulaDefine queryFormulaDefineBySchemeAndCode(String formulaDefineCode, String formulaSchemeKey) throws Exception {
        return this.formulaDao.queryFormulaDefineBySchemeAndCode(formulaDefineCode, formulaSchemeKey);
    }

    public List<DesignFormulaDefine> queryFormulaDefineBySchemeAndCodes(String formulaDefineCode, String formulaSchemeKey) throws Exception {
        return this.formulaDao.queryFormulaDefineBySchemeAndCodes(formulaDefineCode, formulaSchemeKey);
    }

    public List<DesignFormulaDefine> queryFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey, DesignFormDefine designFormDefine) throws Exception {
        return this.formulaDao.queryFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey, designFormDefine);
    }

    public List<DesignFormulaDefine> queryCalcFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey, DesignFormDefine designFormDefine) throws Exception {
        return this.formulaDao.queryCalcFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey, designFormDefine);
    }

    public List<DesignFormulaDefine> queryCheckFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey, DesignFormDefine designFormDefine) throws Exception {
        return this.formulaDao.queryCheckFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey, designFormDefine);
    }

    public List<DesignFormulaDefine> queryBalanceFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey, DesignFormDefine designFormDefine) throws Exception {
        return this.formulaDao.queryBalanceFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey, designFormDefine);
    }

    public List<DesignFormulaDefine> queryFormulaDefineByScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        return this.formulaDao.queryFormulaDefineByScheme(formulaSchemeKey, forms);
    }

    public List<DesignFormulaDefine> queryCalcFormulaDefineByScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        return this.formulaDao.queryCalcFormulaDefineByScheme(formulaSchemeKey, forms);
    }

    public List<DesignFormulaDefine> queryCheckFormulaDefineByScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        return this.formulaDao.queryCheckFormulaDefineByScheme(formulaSchemeKey, forms);
    }

    public List<DesignFormulaDefine> queryBalanceFormulaDefineByScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        return this.formulaDao.queryBalanceFormulaDefineByScheme(formulaSchemeKey, forms);
    }

    public List<DesignFormulaDefine> queryAllFormulaDefine() throws Exception {
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

    public List<DesignFormulaDefine> listFormulaDefineByForm(String formKey) {
        return this.formulaDao.listFormulaDefineByForm(formKey);
    }

    public void deleteBySchemeAndForm(String formSchemeKey, String formKey) throws Exception {
        this.formulaDao.deleteBySchemeAndForm(formSchemeKey, formKey);
    }

    public void deleteByScheme(String formSchemeKey) throws Exception {
        this.formulaDao.deleteByScheme(formSchemeKey);
    }

    public DesignFormulaDefine queryFormulaDefinesByCode(String code) throws Exception {
        return this.formulaDao.queryDefineByCode(code);
    }

    public List<String> insertFormulaDefines(DesignFormulaDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            DesignFormulaDefine formulaDefine = defines[i];
            String key = formulaDefine.getKey();
            if (key == null) {
                key = UUIDUtils.getKey();
                defines[i].setKey(key);
            }
            if (defines[i].getUpdateTime() == null) {
                defines[i].setUpdateTime(new Date());
            }
            defines[i].setIsPrivate(false);
            if (formulaDefine.getSyntax() == 0) {
                formulaDefine.setSyntax(FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION.getValue());
            }
            if (formulaDefine.getOrdinal() == null) {
                formulaDefine.setOrdinal(BigDecimal.valueOf(OrderGenerator.newOrderID()));
            }
            result.add(key);
        }
        this.formulaDao.insert(defines);
        return result;
    }

    public void updateFormulaDefines(DesignFormulaDefine[] defines) throws Exception {
        for (DesignFormulaDefine define : defines) {
            if (define.getUpdateTime() == null) {
                define.setUpdateTime(new Date());
            }
            if (define.getOrdinal() == null) {
                define.setOrdinal(BigDecimal.valueOf(OrderGenerator.newOrderID()));
            }
            define.setIsPrivate(false);
        }
        this.formulaDao.update(defines);
    }

    public List<DesignFormulaDefine> queryFormulaDefines(String[] keys) throws Exception {
        return this.formulaDao.listByKey(Arrays.asList(keys));
    }

    public List<String> getFormulaCodeInScheme(String formulaSchemeKey, List<DesignFormDefine> forms) throws Exception {
        ArrayList<String> codes = new ArrayList<String>();
        List<DesignFormulaDefine> formulas = this.queryFormulaDefineByScheme(formulaSchemeKey, forms);
        formulas.forEach(formula -> codes.add(formula.getCode()));
        return codes;
    }

    public List<DesignFormulaDefine> listGhostFormula() {
        return this.formulaDao.listGhostFormula();
    }

    public int getBJFormulaCountByFormulaSchemeKey(String formulaSchemeKey, String formKey) {
        return this.formulaDao.getBJFormulaCountByFormulaSchemeKey(formulaSchemeKey, formKey);
    }

    public List<DesignFormulaDefine> querySimpleFormulaDefineByScheme(String formulaScheme) {
        return this.formulaDao.querySimpleFormulaDefineByScheme(formulaScheme);
    }

    public List<DesignFormulaDefine> listFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        return this.formulaDao.listFormulaDefineByScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> listCalcFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        return this.formulaDao.listCalcFormulaDefineByScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> listCheckFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        return this.formulaDao.listCheckFormulaDefineByScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> listBalanceFormulaDefineByScheme(String formulaSchemeKey) throws Exception {
        return this.formulaDao.listBalanceFormulaDefineByScheme(formulaSchemeKey);
    }

    public List<DesignFormulaDefine> listFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.formulaDao.listFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey);
    }

    public List<DesignFormulaDefine> listCalcFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.formulaDao.listCalcFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey);
    }

    public List<DesignFormulaDefine> listCheckFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.formulaDao.listCheckFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey);
    }

    public List<DesignFormulaDefine> listBalanceFormulaDefineBySchemeAndForm(String formulaSchemeKey, String formKey) throws Exception {
        return this.formulaDao.listBalanceFormulaDefineBySchemeAndForm(formulaSchemeKey, formKey);
    }

    public List<DesignFormulaDefine> search(Collection<String> formulaSchemeKeys, String keyword) {
        if (CollectionUtils.isEmpty(formulaSchemeKeys) || !StringUtils.hasLength(keyword)) {
            return Collections.emptyList();
        }
        return this.formulaDao.search(formulaSchemeKeys, keyword);
    }
}

