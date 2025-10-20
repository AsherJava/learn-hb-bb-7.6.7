/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.DesignEnumLinkageSettingDefine;
import com.jiuqi.nr.definition.internal.dao.DesignEnumLinkageSettingDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignEnumLinkageSettingDefineService {
    @Autowired
    private DesignEnumLinkageSettingDefineDao enumLinkageDao;

    public void setenumLinkageDao(DesignEnumLinkageSettingDefineDao enumLinkageDao) {
        this.enumLinkageDao = enumLinkageDao;
    }

    public String insertDefine(DesignEnumLinkageSettingDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        this.enumLinkageDao.insert(define);
        return define.getKey();
    }

    public void updateDefine(DesignEnumLinkageSettingDefine define) throws Exception {
        this.enumLinkageDao.update(define);
    }

    public List<DesignEnumLinkageSettingDefine> queryDefineByGroupId(String id) throws Exception {
        return this.enumLinkageDao.listByGroup(id);
    }

    public DesignEnumLinkageSettingDefine queryDefine(String id) throws Exception {
        return this.enumLinkageDao.getDefineByKey(id);
    }

    public List<DesignEnumLinkageSettingDefine> queryDefines(String[] ids) throws Exception {
        ArrayList<DesignEnumLinkageSettingDefine> list = new ArrayList<DesignEnumLinkageSettingDefine>();
        for (int i = 0; i < ids.length; ++i) {
            DesignEnumLinkageSettingDefine define = this.enumLinkageDao.getDefineByKey(ids[0]);
            list.add(define);
        }
        return list;
    }

    public DesignEnumLinkageSettingDefine queryDefineByCode(String code) throws Exception {
        return this.enumLinkageDao.queryDefinesByCode(code);
    }

    public List<DesignEnumLinkageSettingDefine> queryAllDefine() throws Exception {
        return this.enumLinkageDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.enumLinkageDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.enumLinkageDao.delete(key);
    }

    public DesignEnumLinkageSettingDefine queryDefinesByCode(String code) throws Exception {
        return this.enumLinkageDao.queryDefinesByCode(code);
    }

    public List<String> insertDefines(DesignEnumLinkageSettingDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            String key = UUIDUtils.getKey();
            defines[i].setKey(key);
            result.add(key);
        }
        this.enumLinkageDao.insert(defines);
        return result;
    }

    public void updateDefines(DesignEnumLinkageSettingDefine[] defines) throws Exception {
        this.enumLinkageDao.update(defines);
    }

    public List<DesignEnumLinkageSettingDefine> queryDefine(String[] keys) throws Exception {
        ArrayList<DesignEnumLinkageSettingDefine> list = new ArrayList<DesignEnumLinkageSettingDefine>();
        for (String key : keys) {
            DesignEnumLinkageSettingDefine define = this.enumLinkageDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }
}

