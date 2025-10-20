/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignRegionSettingDefineDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignRegionSettingDefineService {
    @Autowired
    private DesignRegionSettingDefineDao regionSettingDao;

    public void setRegionSettingDao(DesignRegionSettingDefineDao regionSettingDao) {
        this.regionSettingDao = regionSettingDao;
    }

    public String insertDefine(DesignRegionSettingDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        this.regionSettingDao.insert(define);
        return define.getKey();
    }

    public void updateDefine(DesignRegionSettingDefine define) throws Exception {
        this.regionSettingDao.update(define);
    }

    public List<DesignRegionSettingDefine> queryDefineByGroupId(String id) throws Exception {
        return this.regionSettingDao.listByGroup(id);
    }

    public DesignRegionSettingDefine queryDefine(String id) throws Exception {
        return this.regionSettingDao.getDefineByKey(id);
    }

    public DesignRegionSettingDefine queryDefineByCode(String code) throws Exception {
        return this.regionSettingDao.queryDefinesByCode(code);
    }

    public List<DesignRegionSettingDefine> queryAllDefine() throws Exception {
        return this.regionSettingDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.regionSettingDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.regionSettingDao.delete(key);
    }

    public DesignRegionSettingDefine queryDefinesByCode(String code) throws Exception {
        return this.regionSettingDao.queryDefinesByCode(code);
    }

    public List<String> insertDefines(DesignRegionSettingDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            String key = UUIDUtils.getKey();
            defines[i].setKey(key);
            result.add(key);
        }
        this.regionSettingDao.insert(defines);
        return result;
    }

    public void updateDefines(DesignRegionSettingDefine[] defines) throws Exception {
        this.regionSettingDao.update(defines);
    }

    public List<DesignRegionSettingDefine> queryDefines(String[] keys) {
        return this.queryDefines(Arrays.asList(keys));
    }

    public List<DesignRegionSettingDefine> queryDefines(List<String> keys) {
        return this.regionSettingDao.listByKeys(keys);
    }
}

