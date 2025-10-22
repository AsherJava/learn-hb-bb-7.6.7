/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataRegionDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeNrFieldDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeDataRegionDefineService {
    @Autowired
    private RunTimeDataRegionDefineDao dataRegionDao;
    @Autowired
    private RunTimeNrFieldDefineDao filedNrDao;

    public void setDataRegiondao(RunTimeDataRegionDefineDao dataRegionDao) {
        this.dataRegionDao = dataRegionDao;
    }

    public void setFiledNrDao(RunTimeNrFieldDefineDao filedNrDao) {
        this.filedNrDao = filedNrDao;
    }

    public void delete(String[] keys) throws Exception {
        this.dataRegionDao.delete(keys);
    }

    public void delete(String key) throws Exception {
        this.dataRegionDao.delete(key);
    }

    public List<DataRegionDefine> queryDataRegionDefineByForm(String formKey) {
        return this.dataRegionDao.getAllRegionsInForm(formKey);
    }

    public DataRegionDefine queryDataRegionDefine(String id) {
        return this.dataRegionDao.getDefineByKey(id);
    }

    public List<DataRegionDefine> queryAllDataRegionDefine() {
        return this.dataRegionDao.list();
    }

    public List<DataRegionDefine> queryDataRegionDefines(String[] keys) {
        ArrayList<DataRegionDefine> list = new ArrayList<DataRegionDefine>();
        for (String key : keys) {
            DataRegionDefine define = this.dataRegionDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DataRegionDefine> getAllRegionsInForm(String formKey) {
        return this.dataRegionDao.getAllRegionsInForm(formKey);
    }

    public DataRegionDefine getDataRegion(String formKey, String regionCode) {
        return this.dataRegionDao.getDataRegion(formKey, regionCode);
    }

    public List<FieldDefine> getFieldsFromRegion(String regionKey) {
        return this.filedNrDao.getAllFieldsInRegion(regionKey);
    }

    public List<DataRegionDefine> getAllRegionsFromField(String fieldKey) {
        return this.dataRegionDao.getAllRegionsFromField(fieldKey);
    }

    public List<DataRegionDefine> getAllRegionsFromFieldAndForm(String fieldKey, String formKey) {
        ArrayList<DataRegionDefine> regions = new ArrayList<DataRegionDefine>();
        List<DataRegionDefine> regions2 = this.dataRegionDao.getAllRegionsFromField(fieldKey);
        for (DataRegionDefine region : regions2) {
            if (!region.getFormKey().equals(formKey)) continue;
            regions.add(region);
        }
        return regions;
    }

    public List<DataRegionDefine> listGhostRegion() {
        return this.dataRegionDao.listGhostRegion();
    }
}

