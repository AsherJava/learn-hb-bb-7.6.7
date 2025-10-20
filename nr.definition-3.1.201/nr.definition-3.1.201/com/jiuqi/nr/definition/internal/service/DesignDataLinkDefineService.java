/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.internal.dao.DesignDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignDataRegionDefineDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignDataLinkDefineService {
    @Autowired
    private DesignDataLinkDefineDao dataLinkDao;
    @Autowired
    private DesignDataRegionDefineDao regionDao;
    @Autowired
    private IDataDefinitionDesignTimeController designController;

    public void setDatalinkDao(DesignDataLinkDefineDao dataLinkDao) {
        this.dataLinkDao = dataLinkDao;
    }

    public String insertDatLinkDefine(DesignDataLinkDefine define) throws Exception {
        if (define.getKey() == null) {
            define.setKey(UUIDUtils.getKey());
        }
        this.dataLinkDao.insert(define);
        return define.getKey();
    }

    public List<String> insert(DesignDataLinkDefine[] dataLinkDefines) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        for (DesignDataLinkDefine define : dataLinkDefines) {
            String key = define.getKey();
            if (key == null) {
                key = UUIDUtils.getKey();
                define.setKey(key);
            }
            list.add(key);
        }
        this.dataLinkDao.insert(dataLinkDefines);
        return list;
    }

    public void updateDataLinkDefine(DesignDataLinkDefine define) throws Exception {
        this.dataLinkDao.update(define);
    }

    public DesignDataLinkDefine queryDataLinkDefine(String id) throws Exception {
        return this.dataLinkDao.getDefineByKey(id);
    }

    public DesignDataLinkDefine queryDataLinkDefineByCode(String code) throws Exception {
        return this.dataLinkDao.queryDefinesByCode(code);
    }

    public List<DesignDataLinkDefine> queryAllDataLinkDefine() throws Exception {
        return this.dataLinkDao.list();
    }

    public List<DesignDataLinkDefine> listRelDataLinks(String filterTemplateID) {
        return this.dataLinkDao.ListRelDataLinks(filterTemplateID);
    }

    public List<String> listAllRegionRelFilterTemplate(String filterTemplateID) {
        return this.dataLinkDao.listAllRegionRelFilterTemplate(filterTemplateID);
    }

    public void delete(String[] keys) throws Exception {
        this.dataLinkDao.delete(keys);
    }

    public void deleteByRegion(String dataRegionkey) throws Exception {
        this.dataLinkDao.deleteByRegion(dataRegionkey);
    }

    public void deleteByField(String fieldKey) throws Exception {
        this.dataLinkDao.deleteByField(fieldKey);
    }

    public void deleteByFields(String[] fieldKeys) throws Exception {
        for (String id : fieldKeys) {
            this.dataLinkDao.deleteByField(id);
        }
    }

    public void deleteDataLinkDefine(String key) throws Exception {
        this.dataLinkDao.delete(key);
    }

    public DesignDataLinkDefine queryDataLinkDefinesByCode(String code) throws Exception {
        return this.dataLinkDao.queryDefinesByCode(code);
    }

    public List<String> insertDataLinkDefines(DesignDataLinkDefine[] defines) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < defines.length; ++i) {
            String key = UUIDUtils.getKey();
            defines[i].setKey(key);
            result.add(key);
        }
        this.dataLinkDao.insert(defines);
        return result;
    }

    public void updateDataLinkDefines(DesignDataLinkDefine[] defines) throws Exception {
        this.dataLinkDao.update(defines);
    }

    public List<DesignDataLinkDefine> queryDataLinkDefines(String[] keys) throws Exception {
        ArrayList<DesignDataLinkDefine> list = new ArrayList<DesignDataLinkDefine>();
        for (String key : keys) {
            DesignDataLinkDefine define = this.dataLinkDao.getDefineByKey(key);
            if (define == null) continue;
            list.add(define);
        }
        return list;
    }

    public List<DesignDataLinkDefine> getAllLinks() throws Exception {
        return this.dataLinkDao.list();
    }

    public List<DesignDataLinkDefine> getAllLinksInRegion(String dataRegionKey) throws Exception {
        return this.dataLinkDao.getAllLinksInRegion(dataRegionKey);
    }

    public List<DataLinkDefine> getAllLinksInForm(String formKey) throws Exception {
        ArrayList<DataLinkDefine> defines = new ArrayList<DataLinkDefine>();
        List<DesignDataRegionDefine> regions = this.regionDao.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : regions) {
            defines.addAll(this.dataLinkDao.getAllLinksInRegion(dataRegionDefine.getKey()));
        }
        return defines;
    }

    public List<DesignDataLinkDefine> getLinksInRegionAndPos(String dataRegionKey, int posX, int posY) throws Exception {
        return this.dataLinkDao.getLinksInRegionAndPos(dataRegionKey, posX, posY);
    }

    public List<DesignFieldDefine> getAllFieldsByLinksInRegion(String dataRegionKey) throws Exception {
        List<DesignDataLinkDefine> links = this.getAllLinksInRegion(dataRegionKey);
        String[] fieldKeys = (String[])links.stream().filter(l -> l.getType() == DataLinkType.DATA_LINK_TYPE_FIELD || l.getType() == DataLinkType.DATA_LINK_TYPE_FMDM).map(l -> l.getLinkExpression()).toArray(String[]::new);
        return this.designController.queryFieldDefines(fieldKeys);
    }

    public List<DesignDataLinkDefine> getDefinesByFieldKey(String fieldKey) throws Exception {
        return this.dataLinkDao.getDefinesByFieldKey(fieldKey);
    }

    public List<DesignDataLinkDefine> getDefinesByFieldKeys(List<String> fieldKeys) {
        return this.dataLinkDao.getDefinesByFieldKeys(fieldKeys);
    }

    public List<DesignDataLinkDefine> getDefinesByFieldAndRegion(String fieldKey, String regionKey) throws Exception {
        return this.dataLinkDao.getDefinesByFieldAndRegionKey(fieldKey, regionKey);
    }

    public DesignDataLinkDefine queryDataLinkDefineByColRow(String reportKey, int colIndex, int rowIndex) {
        return this.dataLinkDao.queryDataLinkDefineByColRow(reportKey, colIndex, rowIndex);
    }

    public DesignDataLinkDefine queryDataLinkDefineByUniquecode(String reportKey, String dataLinkCode) {
        return this.dataLinkDao.queryDataLinkDefineByUniquecode(reportKey, dataLinkCode);
    }

    public List<DesignDataLinkDefine> getLinksInFormByField(String formKey, String fieldKey) {
        return this.dataLinkDao.getLinksInFormByField(formKey, fieldKey);
    }

    public List<DesignDataLinkDefine> listGhostLink() {
        return this.dataLinkDao.listGhostLink();
    }

    public List<DesignDataLinkDefine> listLinkNotHaveField(String regionKey) {
        return this.dataLinkDao.listLinkNotHaveField(regionKey);
    }

    public List<String> listLinkKeyPhysicalCoordinatesDuplicate(String regionKey) {
        return this.dataLinkDao.listLinkKeyPhysicalCoordinatesDuplicate(regionKey);
    }

    public List<String> listLinkKeyDataCoordinatesDuplicate(String regionKey) {
        return this.dataLinkDao.listLinkKeyDataCoordinatesDuplicate(regionKey);
    }

    public List<String> listLinkKeyRefuseView(String regionKey) {
        return this.dataLinkDao.listLinkKeyRefuseView(regionKey);
    }

    public List<String> listLinkKeyViewQuoteError(String regionKey) {
        return this.dataLinkDao.listLinkKeyViewQuoteError(regionKey);
    }

    public List<String> listLinkKeyNoneView(String regionKey) {
        return this.dataLinkDao.listLinkKeyNoneView(regionKey);
    }

    public List<String> getAllTableKeysInRegion(String dataRegionKey) {
        return this.dataLinkDao.getAllTableKeysInRegion(dataRegionKey);
    }

    public List<String> listFieldKeyByDataRegion(String dataRegionKey) throws Exception {
        List<DesignDataLinkDefine> links = this.getAllLinksInRegion(dataRegionKey);
        Set collect = links.stream().filter(l -> DataLinkType.DATA_LINK_TYPE_FIELD.equals((Object)l.getType()) || DataLinkType.DATA_LINK_TYPE_INFO.equals((Object)l.getType())).map(l -> l.getLinkExpression()).collect(Collectors.toSet());
        return new ArrayList<String>(collect);
    }

    public List<String> listDataTableByForm(Set<String> forms) {
        return this.dataLinkDao.listDataTableByForm(forms);
    }
}

