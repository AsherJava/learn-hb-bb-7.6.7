/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.masterdata.intf;

import com.jiuqi.budget.components.SysDim;
import com.jiuqi.budget.masterdata.basedata.QueryDataType;
import com.jiuqi.budget.masterdata.basedata.RangeType;
import com.jiuqi.budget.masterdata.intf.AuthType;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.MasterDataVersion;
import com.jiuqi.budget.masterdata.organization.OrgCategoryDTO;
import com.jiuqi.budget.masterdata.organization.OrgDataObjDTO;
import com.jiuqi.budget.masterdata.organization.service.OrgDataService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component(value="budOrgDataCenter")
public class OrgDataCenter {
    private final OrgDataService orgDataService;

    @Autowired
    public OrgDataCenter(OrgDataService orgDataService) {
        this.orgDataService = orgDataService;
    }

    public static OrgDataCenter getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public List<FBaseDataObj> getOrgDataList(OrgDataObjDTO orgDataObjDTO) {
        return this.orgDataService.getOrgDataList(orgDataObjDTO);
    }

    public boolean isLeaf(String key, String categoryName, AuthType authType) {
        OrgDataObjDTO param = new OrgDataObjDTO();
        param.setParentCode(key);
        param.setTableName(categoryName);
        return this.orgDataService.isLeaf(param, authType);
    }

    public List<FBaseDataObj> listOrgData(String categoryName, String orgKey, RangeType rangeType, AuthType authType) {
        OrgDataObjDTO orgDataObjDTO = new OrgDataObjDTO();
        orgDataObjDTO.setKey(orgKey);
        orgDataObjDTO.setTableName(categoryName);
        orgDataObjDTO.setRangeType(rangeType);
        orgDataObjDTO.setAuthType(authType);
        return this.orgDataService.getOrgDataList(orgDataObjDTO);
    }

    public int count(OrgDataObjDTO orgDataObjDTO) {
        return this.orgDataService.count(orgDataObjDTO);
    }

    public List<FBaseDataObj> listOrgData(String categoryName, AuthType authType) {
        OrgDataObjDTO orgDataObjDTO = new OrgDataObjDTO();
        if (SysDim.MDCODE.alias().equals(categoryName)) {
            orgDataObjDTO.setTableName("MD_ORG");
        } else {
            orgDataObjDTO.setTableName(categoryName);
        }
        orgDataObjDTO.setAuthType(authType);
        orgDataObjDTO.setRangeType(RangeType.NONE);
        return this.orgDataService.getOrgDataList(orgDataObjDTO);
    }

    public FBaseDataObj getOrgData(OrgDataObjDTO orgDataObj) {
        return this.orgDataService.findOrgData(orgDataObj);
    }

    public FBaseDataObj findOrgDataByKey(OrgDataObjDTO orgDataObj) {
        return this.orgDataService.findOrgDataByKey(orgDataObj);
    }

    public FBaseDataObj findOrgDataById(String categoryName, String orgId) {
        OrgDataObjDTO orgDataObj = new OrgDataObjDTO();
        orgDataObj.setCategoryName(categoryName);
        orgDataObj.setId(orgId);
        return this.orgDataService.findOrgDataById(orgDataObj);
    }

    public FBaseDataObj findOrgByCode(String categoryName, String orgCode) {
        return this.findOrgByCode(categoryName, orgCode, AuthType.ACCESS);
    }

    public FBaseDataObj findOrgByCode(String categoryName, String orgCode, AuthType authType) {
        OrgDataObjDTO orgDataObj = new OrgDataObjDTO();
        orgDataObj.setCategoryName(categoryName);
        orgDataObj.setCode(orgCode);
        orgDataObj.setAuthType(authType);
        return this.getOrgData(orgDataObj);
    }

    public FBaseDataObj findBaseDataObjAllFieldByKey(String tableName, String key) {
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        OrgDataObjDTO orgDataObjDTO = new OrgDataObjDTO();
        orgDataObjDTO.setTableName(tableName);
        orgDataObjDTO.setKey(key);
        orgDataObjDTO.setQueryDataType(QueryDataType.ALL);
        return this.orgDataService.getOrgDataObject(orgDataObjDTO);
    }

    public FBaseDataObj findOrgByKey(String categoryName, String key) {
        OrgDataObjDTO orgDataObj = new OrgDataObjDTO();
        orgDataObj.setCategoryName(categoryName);
        orgDataObj.setKey(key);
        return this.findOrgDataByKey(orgDataObj);
    }

    public FBaseDataObj findOrgByKey(String categoryName, String key, AuthType access) {
        List<FBaseDataObj> fBaseDataObjs = this.listOrgData(categoryName, key, RangeType.NONE, access);
        if (fBaseDataObjs == null || fBaseDataObjs.isEmpty()) {
            return null;
        }
        Assert.isTrue(fBaseDataObjs.size() == 1, "key\u91cd\u590d");
        return fBaseDataObjs.get(0);
    }

    public List<MasterDataVersion> listVersions(String categoryName) {
        return this.orgDataService.listVersions(categoryName);
    }

    public MasterDataVersion getVerInfoById(String categoryName, String id) {
        return this.orgDataService.getVerInfoById(categoryName, id);
    }

    public List<OrgCategoryDTO> getOrgCategoryList() {
        return this.orgDataService.getOrgCategoryList();
    }

    private static class InstanceHolder {
        static final OrgDataCenter INSTANCE = (OrgDataCenter)ApplicationContextRegister.getBean(OrgDataCenter.class);

        private InstanceHolder() {
        }
    }
}

