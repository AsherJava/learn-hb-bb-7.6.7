/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.ResultVO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 */
package com.jiuqi.budget.masterdata.basedata;

import com.jiuqi.budget.common.domain.ResultVO;
import com.jiuqi.budget.masterdata.basedata.BaseDataDefine;
import com.jiuqi.budget.masterdata.basedata.BaseDataObj;
import com.jiuqi.budget.masterdata.basedata.BaseDataObjDTO;
import com.jiuqi.budget.masterdata.intf.AuthType;
import com.jiuqi.budget.masterdata.intf.FBaseDataDefine;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.MasterDataVersion;
import com.jiuqi.budget.page.PageList;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import java.util.List;

public interface BaseDataService {
    public List<FBaseDataDefine> listBaseDataDefine(String var1);

    public FBaseDataDefine getBaseDataDefine(BaseDataDefine var1);

    public PageList<FBaseDataObj> listPagedBaseDataObject(BaseDataObjDTO var1);

    public FBaseDataObj getBaseDataObject(BaseDataObjDTO var1);

    public ResultVO addBaseDataObj(BaseDataObj var1);

    public boolean isExist(BaseDataObj var1);

    public boolean isLeaf(String var1, String var2, String var3, AuthType var4);

    public boolean updateBaseDataObj(BaseDataObj var1);

    public boolean deleteBaseDataObj(String var1, String var2, String var3);

    public List<FBaseDataObj> listBaseDataObject(BaseDataObjDTO var1);

    public List<FBaseDataObj> listBaseDataObjsByCodeList(BaseDataObjDTO var1, List<String> var2);

    public List<FBaseDataObj> listBaseDataObjsByKeyList(BaseDataObjDTO var1, List<String> var2);

    public ResultVO<?> addBaseDataDefine(BaseDataDefine var1);

    public ResultVO<?> addBaseDataGroup(BaseDataGroupDTO var1);

    public BaseDataGroupDO getDataGroup(BaseDataGroupDTO var1);

    public boolean isExist(BaseDataGroupDTO var1);

    public ResultVO<?> updateBaseDataDefine(BaseDataDefine var1);

    public int count(BaseDataObjDTO var1);

    public MasterDataVersion getVerInfoById(String var1, String var2);
}

