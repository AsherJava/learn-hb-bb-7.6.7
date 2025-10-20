/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeRegionSettingDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeRegionSettingDefineService {
    @Autowired
    private RunTimeRegionSettingDefineDao regionSettingDao;

    public void setRegionSettingDao(RunTimeRegionSettingDefineDao regionSettingDao) {
        this.regionSettingDao = regionSettingDao;
    }

    public void updateDefine(RegionSettingDefine define) throws Exception {
        this.regionSettingDao.update(define);
    }

    public List<RegionSettingDefine> queryDefineByGroupId(String id) throws Exception {
        return this.regionSettingDao.listByGroup(id);
    }

    public RegionSettingDefine queryDefine(String id) throws Exception {
        return this.regionSettingDao.getDefineByKey(id);
    }

    public RegionSettingDefine queryDefineByCode(String code) throws Exception {
        return this.regionSettingDao.queryDefinesByCode(code);
    }

    public List<RegionSettingDefine> queryAllDefine() throws Exception {
        return this.regionSettingDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.regionSettingDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.regionSettingDao.delete(key);
    }

    public RegionSettingDefine queryDefinesByCode(String code) throws Exception {
        return this.regionSettingDao.queryDefinesByCode(code);
    }

    public void updateDefines(RegionSettingDefine[] defines) throws Exception {
        this.regionSettingDao.update(defines);
    }

    public List<RegionSettingDefine> queryDefines(String[] keys) throws Exception {
        ArrayList<RegionSettingDefine> list = new ArrayList<RegionSettingDefine>();
        for (String key : keys) {
            RegionSettingDefine define = this.regionSettingDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public void insertRegionSettingDefine(RegionSettingDefine regionSettingDefine) throws Exception {
        this.regionSettingDao.insert(regionSettingDefine);
    }

    public void insertRegionSettingDefines(RegionSettingDefine[] regionSettingDefines) throws Exception {
        this.regionSettingDao.insert(regionSettingDefines);
    }
}

