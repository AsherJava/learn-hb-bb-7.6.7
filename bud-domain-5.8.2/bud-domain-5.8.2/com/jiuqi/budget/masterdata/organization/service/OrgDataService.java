/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.masterdata.organization.service;

import com.jiuqi.budget.masterdata.intf.AuthType;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.MasterDataVersion;
import com.jiuqi.budget.masterdata.organization.OrgCategoryDTO;
import com.jiuqi.budget.masterdata.organization.OrgDataObjDTO;
import java.util.List;

public interface OrgDataService {
    public List<OrgCategoryDTO> getOrgCategoryList();

    public OrgCategoryDTO getOrgCategory(OrgCategoryDTO var1);

    public List<FBaseDataObj> getOrgDataList(OrgDataObjDTO var1);

    public FBaseDataObj getOrgDataObject(OrgDataObjDTO var1);

    public boolean isLeaf(OrgDataObjDTO var1, AuthType var2);

    public FBaseDataObj findOrgData(OrgDataObjDTO var1);

    public FBaseDataObj findOrgDataByKey(OrgDataObjDTO var1);

    public FBaseDataObj findOrgDataById(OrgDataObjDTO var1);

    public List<MasterDataVersion> listVersions(String var1);

    public int count(OrgDataObjDTO var1);

    public MasterDataVersion getVerInfoById(String var1, String var2);
}

