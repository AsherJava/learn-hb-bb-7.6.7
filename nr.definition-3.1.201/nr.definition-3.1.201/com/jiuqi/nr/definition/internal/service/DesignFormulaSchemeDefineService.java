/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.impl.EFDCPeriodSettingDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import com.jiuqi.nr.definition.internal.service.EFDCPeriodSettingService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignFormulaSchemeDefineService {
    @Autowired
    private DesignFormulaSchemeDefineDao formulaSchemeDao;
    @Autowired
    private EFDCPeriodSettingService efdcPeriodSettingService;
    @Autowired
    private IRuntimeFormulaSchemeService runtimeFormulaSchemeService;

    public void setFormulaSchemedao(DesignFormulaSchemeDefineDao formulaSchemeDao) {
        this.formulaSchemeDao = formulaSchemeDao;
    }

    public String insertFormulaSchemeDefine(DesignFormulaSchemeDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formulaSchemeDao.insert(define);
        return define.getKey();
    }

    public void updateFormulaSchemeDefine(DesignFormulaSchemeDefine define) throws Exception {
        if (define.getUpdateTime() == null) {
            define.setUpdateTime(new Date());
        }
        this.formulaSchemeDao.update(define);
        this.saveEFDCPeriodSettingDefineImpl(define, define.getKey());
    }

    public List<DesignFormulaSchemeDefine> queryFormulaSchemeDefineByFormScheme(String formSchemeKey) throws Exception {
        return this.formulaSchemeDao.listByFormScheme(formSchemeKey);
    }

    public DesignFormulaSchemeDefine queryFormulaSchemeDefine(String id) throws Exception {
        DesignFormulaSchemeDefine define = this.formulaSchemeDao.getDefineByKey(id);
        if (define != null) {
            define.setEFDCPeriodSettingDefineImpl(this.efdcPeriodSettingService.queryByFormulaSchemeKey(id));
            return define;
        }
        return null;
    }

    public DesignFormulaSchemeDefine queryFormulaSchemeDefineByCode(String code) throws Exception {
        return this.formulaSchemeDao.queryDefinesByCode(code);
    }

    public List<DesignFormulaSchemeDefine> queryAllFormulaSchemeDefine() throws Exception {
        return this.formulaSchemeDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.formulaSchemeDao.delete(keys);
        if (keys != null) {
            for (String key : keys) {
                this.deleteEFDCPeriodSettingDefineImpl(key);
            }
        }
    }

    public void delete(String key) throws Exception {
        this.formulaSchemeDao.delete(key);
        this.deleteEFDCPeriodSettingDefineImpl(key);
    }

    public DesignFormulaSchemeDefine queryFormulaSchemeDefinesByCode(String code) throws Exception {
        return this.formulaSchemeDao.queryDefinesByCode(code);
    }

    public List<String> insertFormulaSchemeDefines(DesignFormulaSchemeDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            if (defines[i].getKey() == null) {
                defines[i].setKey(UUIDUtils.getKey());
            }
            if (defines[i].getUpdateTime() == null) {
                defines[i].setUpdateTime(new Date());
            }
            result.add(defines[i].getKey());
        }
        this.formulaSchemeDao.insert(defines);
        return result;
    }

    public void updateFormulaSchemeDefines(DesignFormulaSchemeDefine[] defines) throws Exception {
        for (DesignFormulaSchemeDefine define : defines) {
            if (define.getUpdateTime() != null) continue;
            define.setUpdateTime(new Date());
        }
        this.formulaSchemeDao.update(defines);
    }

    public List<DesignFormulaSchemeDefine> queryFormulaSchemeDefines(String[] keys) throws Exception {
        ArrayList<DesignFormulaSchemeDefine> list = new ArrayList<DesignFormulaSchemeDefine>();
        for (String key : keys) {
            DesignFormulaSchemeDefine define = this.formulaSchemeDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DesignFormulaSchemeDefine> listGhostScheme() {
        return this.formulaSchemeDao.listGhostScheme();
    }

    private void saveEFDCPeriodSettingDefineImpl(DesignFormulaSchemeDefine define, String formulaSchemeKey) throws Exception {
        EFDCPeriodSettingDefineImpl efdcPeriodSettingDefine = this.efdcPeriodSettingService.queryByFormulaSchemeKey(define.getKey());
        if (define.getEfdcPeriodSettingDefineImpl() != null) {
            if (efdcPeriodSettingDefine != null) {
                this.efdcPeriodSettingService.updateEFDCPeriodSetting(define.getEfdcPeriodSettingDefineImpl());
            } else {
                this.efdcPeriodSettingService.insertEFDCPeriodSetting(define.getEfdcPeriodSettingDefineImpl());
            }
            this.refreshCache(define);
        } else if (efdcPeriodSettingDefine != null) {
            this.efdcPeriodSettingService.deleteEFDCPeriodSetting(efdcPeriodSettingDefine.getKey());
            this.refreshCache(define);
        }
    }

    private void deleteEFDCPeriodSettingDefineImpl(String formulaSchemeKey) throws Exception {
        this.efdcPeriodSettingService.deleteEFDCPeriodSettingByFSKey(formulaSchemeKey);
        this.runtimeFormulaSchemeService.refreshObjCache(formulaSchemeKey, null);
    }

    private void refreshCache(DesignFormulaSchemeDefine define) {
        this.runtimeFormulaSchemeService.refreshObjCache(define.getKey(), define.getEfdcPeriodSettingDefineImpl());
    }
}

