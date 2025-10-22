/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataRegionDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeNrFieldDefineDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunTimeDataLinkDefineService {
    @Autowired
    private RunTimeDataLinkDefineDao dataLinkDao;
    @Autowired
    private RunTimeDataRegionDefineDao regionDao;
    @Autowired
    private RunTimeNrFieldDefineDao fieldDao;

    public void setDataLinkDao(RunTimeDataLinkDefineDao dataLinkDao) {
        this.dataLinkDao = dataLinkDao;
    }

    public void setRegionDao(RunTimeDataRegionDefineDao regionDao) {
        this.regionDao = regionDao;
    }

    public void setFieldDao(RunTimeNrFieldDefineDao fieldDao) {
        this.fieldDao = fieldDao;
    }

    public void updateDataLinkDefine(DataLinkDefine define) throws Exception {
        this.dataLinkDao.update(define);
    }

    public List<DataLinkDefine> queryDataLinkDefineByRegionKey(String regionKey) throws Exception {
        return this.dataLinkDao.getAllLinksInRegion(regionKey);
    }

    public List<DataLinkDefine> queryDataLinkDefineByRegionAndPosXY(String regionKey, int posX, int posY) throws Exception {
        return this.dataLinkDao.getAllLinksInRegionAndPosXY(regionKey, posX, posY);
    }

    public List<DataLinkDefine> queryDataLinkDefineByRegionAndEditMode(String regionKey, DataLinkEditMode dataLinkEditMode) throws Exception {
        return this.dataLinkDao.getAllLinksInRegionAndEditMode(regionKey, dataLinkEditMode);
    }

    public List<DataLinkDefine> queryDataLinkDefineByFormAndEditMode(String formKey, DataLinkEditMode dataLinkEditMode) throws Exception {
        ArrayList<DataLinkDefine> defines = new ArrayList<DataLinkDefine>();
        List<DataRegionDefine> regions = this.regionDao.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : regions) {
            defines.addAll(this.queryDataLinkDefineByRegionAndEditMode(region.getKey(), dataLinkEditMode));
        }
        return defines;
    }

    public DataLinkDefine queryDataLinkDefine(String id) throws Exception {
        return this.dataLinkDao.getDefineByKey(id);
    }

    public DataLinkDefine queryDataLinkDefineByCode(String code) throws Exception {
        return this.dataLinkDao.queryDefinesByCode(code);
    }

    public List<DataLinkDefine> queryAllDataLinkDefine() throws Exception {
        return this.dataLinkDao.list();
    }

    public void delete(String[] keys) throws Exception {
        this.dataLinkDao.delete(keys);
    }

    public void deleteByRegion(String dataRegionkey) throws Exception {
        this.dataLinkDao.deleteByRegion(dataRegionkey);
    }

    public void deleteByField(String Fieldkey) throws Exception {
        this.dataLinkDao.deleteByField(Fieldkey);
    }

    public void deleteByFields(String[] fieldKeys) throws Exception {
        for (String id : fieldKeys) {
            this.dataLinkDao.deleteByField(id);
        }
    }

    public void deleteDataLinkDefine(String key) throws Exception {
        this.dataLinkDao.delete(key);
    }

    public DataLinkDefine queryDataLinkDefinesByCode(String code) throws Exception {
        return this.dataLinkDao.queryDefinesByCode(code);
    }

    public void updateDataLinkDefines(DataLinkDefine[] defines) throws Exception {
        this.dataLinkDao.update(defines);
    }

    public List<DataLinkDefine> queryDataLinkDefines(String[] keys) throws Exception {
        ArrayList<DataLinkDefine> list = new ArrayList<DataLinkDefine>();
        for (String key : keys) {
            DataLinkDefine define = this.dataLinkDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DataLinkDefine> getAllLinks() throws Exception {
        return this.dataLinkDao.list();
    }

    public List<DataLinkDefine> getAllLinksInRegion(String dataRegionKey) throws Exception {
        return this.dataLinkDao.getAllLinksInRegion(dataRegionKey);
    }

    public List<DataLinkDefine> getAllLinksInForm(String formKey) throws Exception {
        ArrayList<DataLinkDefine> defines = new ArrayList<DataLinkDefine>();
        List<DataRegionDefine> regions = this.regionDao.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : regions) {
            defines.addAll(this.dataLinkDao.getAllLinksInRegion(region.getKey()));
        }
        return defines;
    }

    public List<FieldDefine> getAllFieldsByLinksInRegion(String dataRegionKey) throws Exception {
        return this.fieldDao.getAllFieldsInRegion(dataRegionKey);
    }

    public List<FieldDefine> getAllFieldsByLinksInForm(String formKey) throws Exception {
        ArrayList<FieldDefine> defines = new ArrayList<FieldDefine>();
        List<DataRegionDefine> regions = this.regionDao.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : regions) {
            defines.addAll(this.fieldDao.getAllFieldsInRegion(region.getKey()));
        }
        return defines;
    }

    public List<DataLinkDefine> getDefinesByFieldKey(String fieldKey) throws Exception {
        return this.dataLinkDao.getDefinesByFieldKey(fieldKey);
    }

    public List<DataRegionDefine> getRegionsByFieldKey(String fieldKey) throws Exception {
        ArrayList<DataRegionDefine> regions = new ArrayList<DataRegionDefine>();
        List<DataLinkDefine> links = this.dataLinkDao.getDefinesByFieldKey(fieldKey);
        ArrayList<String> ids = new ArrayList<String>();
        for (DataLinkDefine link : links) {
            if (ids.contains(link.getKey())) continue;
            ids.add(link.getKey());
            regions.add(this.regionDao.getDefineByKey(link.getKey()));
        }
        return regions;
    }

    public List<DataLinkDefine> getDefinesByFieldAndRegion(String fieldKey, String regionKey) throws Exception {
        return this.dataLinkDao.getDefinesByFieldAndRegionKey(fieldKey, regionKey);
    }

    public void insertDataLinkDefine(DataLinkDefine dataLinkDefine) throws Exception {
        this.dataLinkDao.insert(dataLinkDefine);
    }

    public void insertDataLinkDefines(DataLinkDefine[] dataLinkDefines) throws Exception {
        this.dataLinkDao.insert(dataLinkDefines);
    }

    public List<DataLinkDefine> listGhostLink() {
        return this.dataLinkDao.listGhostLink();
    }
}

