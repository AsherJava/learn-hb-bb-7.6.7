/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.internal.dao.DesignDataRegionDefineDao;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DesignDataRegionDefineService {
    @Autowired
    private DesignDataRegionDefineDao dataRegionDao;

    public void setDataRegionDao(DesignDataRegionDefineDao dataRegionDao) {
        this.dataRegionDao = dataRegionDao;
    }

    public String insertDataRegionDefine(DesignDataRegionDefine define) throws Exception {
        if (!StringUtils.hasText(define.getKey())) {
            define.setKey(UUIDUtils.getKey());
        }
        if (!StringUtils.hasText(define.getCode())) {
            define.setCode(OrderGenerator.newOrder());
        }
        this.dataRegionDao.insert(define);
        return define.getKey();
    }

    public void updateDataRegionDefine(DesignDataRegionDefine define) throws Exception {
        if (!StringUtils.hasText(define.getCode())) {
            DesignDataRegionDefine old = this.dataRegionDao.getDefineByKey(define.getKey());
            define.setCode(old.getCode());
            if (!StringUtils.hasText(define.getCode())) {
                define.setCode(OrderGenerator.newOrder());
            }
        }
        this.dataRegionDao.update(define);
    }

    public List<DesignDataRegionDefine> queryAllDataRegionDefine() throws Exception {
        return this.dataRegionDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.dataRegionDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.dataRegionDao.delete(key);
    }

    public List<String> insertDataRegionDefines(DesignDataRegionDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            String key = defines[i].getKey();
            if (!StringUtils.hasText(key)) {
                key = UUIDUtils.getKey();
                defines[i].setKey(key);
            }
            if (!StringUtils.hasText(defines[i].getCode())) {
                defines[i].setCode(OrderGenerator.newOrder());
            }
            result.add(key);
        }
        this.dataRegionDao.insert(defines);
        return result;
    }

    public void updateDataRegionDefines(DesignDataRegionDefine[] defines) throws Exception {
        for (DesignDataRegionDefine define : defines) {
            if (!StringUtils.hasText(define.getCode())) {
                DesignDataRegionDefine old = this.dataRegionDao.getDefineByKey(define.getKey());
                define.setCode(old.getCode());
            }
            if (StringUtils.hasText(define.getCode())) continue;
            define.setCode(OrderGenerator.newOrder());
        }
        this.dataRegionDao.update(defines);
    }

    public DesignDataRegionDefine queryDataRegionDefine(String id) throws Exception {
        return this.dataRegionDao.getDefineByKey(id);
    }

    public List<DesignDataRegionDefine> queryDataRegionDefines(String[] keys) {
        ArrayList<DesignDataRegionDefine> list = new ArrayList<DesignDataRegionDefine>();
        for (String key : keys) {
            DesignDataRegionDefine define = this.dataRegionDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DesignDataRegionDefine> getAllRegionsInForm(String formKey) {
        return this.dataRegionDao.getAllRegionsInForm(formKey);
    }

    public DesignDataRegionDefine getDataRegion(String formKey, String regionCode) {
        return this.dataRegionDao.getDataRegion(formKey, regionCode);
    }

    public List<DesignDataRegionDefine> listGhostRegion() {
        return this.dataRegionDao.listGhostRegion();
    }
}

