/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.BudTenantConfig
 *  com.jiuqi.budget.common.domain.ResultVO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.budget.masterdata.intf;

import com.jiuqi.budget.common.domain.BudTenantConfig;
import com.jiuqi.budget.common.domain.ResultVO;
import com.jiuqi.budget.masterdata.basedata.BaseDataDefine;
import com.jiuqi.budget.masterdata.basedata.BaseDataObj;
import com.jiuqi.budget.masterdata.basedata.BaseDataObjDTO;
import com.jiuqi.budget.masterdata.basedata.BaseDataService;
import com.jiuqi.budget.masterdata.basedata.QueryDataType;
import com.jiuqi.budget.masterdata.basedata.RangeType;
import com.jiuqi.budget.masterdata.intf.AuthType;
import com.jiuqi.budget.masterdata.intf.FBaseDataDefine;
import com.jiuqi.budget.masterdata.intf.FBaseDataObj;
import com.jiuqi.budget.masterdata.intf.MasterDataVersion;
import com.jiuqi.budget.page.PageList;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="budBaseDataCenter")
public class BaseDataCenter {
    private final BaseDataService baseDataService;

    @Autowired
    public BaseDataCenter(BaseDataService baseDataService) {
        this.baseDataService = baseDataService;
    }

    public static BaseDataCenter getInstance() {
        return InstanceHolder.instance;
    }

    public FBaseDataObj findBaseDataObjByCode(String tableName, String code, String org) {
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setUnitCode(org);
        baseDataObjDTO.setCode(code);
        return this.baseDataService.getBaseDataObject(baseDataObjDTO);
    }

    public FBaseDataObj findBaseDataObjByKey(String tableName, String key) {
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setKey(key);
        return this.baseDataService.getBaseDataObject(baseDataObjDTO);
    }

    public FBaseDataObj findBaseDataObjByKey(String tableName, String key, AuthType authType) {
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setKey(key);
        baseDataObjDTO.setAuthType(authType);
        return this.baseDataService.getBaseDataObject(baseDataObjDTO);
    }

    public FBaseDataObj getBaseDataObject(BaseDataObjDTO baseDataObjDTO) {
        return this.baseDataService.getBaseDataObject(baseDataObjDTO);
    }

    public FBaseDataObj findBaseDataObjAllFieldByKey(String tableName, String key) {
        if (!StringUtils.hasLength(key)) {
            return null;
        }
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setKey(key);
        baseDataObjDTO.setQueryDataType(QueryDataType.ALL);
        return this.baseDataService.getBaseDataObject(baseDataObjDTO);
    }

    public FBaseDataObj findBaseDataObjByName(String tableName, String name) {
        if (!StringUtils.hasLength(name)) {
            return null;
        }
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setName(name);
        return this.baseDataService.getBaseDataObject(baseDataObjDTO);
    }

    public PageList<FBaseDataObj> getBaseDataList(BaseDataObjDTO baseDataObjDTO) {
        return this.baseDataService.listPagedBaseDataObject(baseDataObjDTO);
    }

    public FBaseDataDefine findBaseDataDefineByCode(String tableName) {
        BaseDataDefine baseDataDefine = new BaseDataDefine();
        baseDataDefine.setCode(tableName);
        return this.baseDataService.getBaseDataDefine(baseDataDefine);
    }

    public List<FBaseDataDefine> getBaseDataDefineList() {
        return this.baseDataService.listBaseDataDefine(BudTenantConfig.getTenantName());
    }

    public FBaseDataDefine findBaseDataDefineById(String id) {
        BaseDataDefine baseDataDefine = new BaseDataDefine();
        baseDataDefine.setId(id);
        return this.baseDataService.getBaseDataDefine(baseDataDefine);
    }

    public ResultVO addBaseDataObj(BaseDataObj baseDataObj) {
        return this.baseDataService.addBaseDataObj(baseDataObj);
    }

    public boolean updateBaseDataObj(BaseDataObj baseDataObj) {
        return this.baseDataService.updateBaseDataObj(baseDataObj);
    }

    public boolean deleteBaseDataObj(String tableName, String code) {
        return this.baseDataService.deleteBaseDataObj(tableName, code, BudTenantConfig.getTenantName());
    }

    public boolean isExist(String tableName, String code) {
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setCode(code);
        baseDataObjDTO.setTableName(tableName);
        return this.baseDataService.isExist(baseDataObjDTO);
    }

    public boolean isLeaf(String tableName, String code, AuthType authType) {
        return this.baseDataService.isLeaf(tableName, code, null, authType);
    }

    public boolean isLeaf(String tableName, String code, String unitCode, AuthType authType) {
        return this.baseDataService.isLeaf(tableName, code, unitCode, authType);
    }

    public FBaseDataDefine findBaseDataDefine(BaseDataDefine baseDataDefine) {
        return this.baseDataService.getBaseDataDefine(baseDataDefine);
    }

    public List<FBaseDataObj> listBaseDataObject(String tableName, String key, RangeType rangeType) {
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setKey(key);
        baseDataObjDTO.setRangeType(rangeType);
        return this.baseDataService.listBaseDataObject(baseDataObjDTO);
    }

    public List<FBaseDataObj> listBaseDataObject(String tableName, String key, RangeType rangeType, AuthType authType) {
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setKey(key);
        baseDataObjDTO.setRangeType(rangeType);
        baseDataObjDTO.setAuthType(authType);
        return this.baseDataService.listBaseDataObject(baseDataObjDTO);
    }

    public List<FBaseDataObj> listBaseDataObject(String tableName, AuthType authType) {
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setAuthType(authType);
        return this.baseDataService.listBaseDataObject(baseDataObjDTO);
    }

    public int count(BaseDataObjDTO baseDataObjDTO) {
        return this.baseDataService.count(baseDataObjDTO);
    }

    public List<FBaseDataObj> listBaseDataObject(String tableName, String unitCode, AuthType authType) {
        BaseDataObjDTO baseDataObjDTO = new BaseDataObjDTO();
        baseDataObjDTO.setTableName(tableName);
        baseDataObjDTO.setUnitCode(unitCode);
        baseDataObjDTO.setAuthType(authType);
        return this.baseDataService.listBaseDataObject(baseDataObjDTO);
    }

    public List<FBaseDataObj> listBaseDataObject(BaseDataObjDTO baseDataObjDTO) {
        return this.baseDataService.listBaseDataObject(baseDataObjDTO);
    }

    public List<FBaseDataObj> listBaseDataObjsByKeyList(String tableName, List<String> keyList) {
        BaseDataObjDTO dataObjDTO = new BaseDataObjDTO();
        dataObjDTO.setTableName(tableName);
        return this.baseDataService.listBaseDataObjsByKeyList(dataObjDTO, keyList);
    }

    public List<FBaseDataObj> listBaseDataObjsByKeyList(BaseDataObjDTO dataObjDTO, List<String> keyList) {
        return this.baseDataService.listBaseDataObjsByKeyList(dataObjDTO, keyList);
    }

    public ResultVO<?> addBaseDataDefine(BaseDataDefine baseDataDefine) {
        return this.baseDataService.addBaseDataDefine(baseDataDefine);
    }

    public BaseDataGroupDO getBaseDataGroup(BaseDataGroupDTO baseDataGroupDTO) {
        return this.baseDataService.getDataGroup(baseDataGroupDTO);
    }

    public boolean isExist(BaseDataGroupDTO baseDataGroupDTO) {
        return this.baseDataService.isExist(baseDataGroupDTO);
    }

    public ResultVO<?> addBaseDataGroup(BaseDataGroupDTO baseDataGroupDTO) {
        return this.baseDataService.addBaseDataGroup(baseDataGroupDTO);
    }

    public ResultVO<?> updateBaseDataDefine(BaseDataDefine BaseDataDefine2) {
        return this.baseDataService.updateBaseDataDefine(BaseDataDefine2);
    }

    public MasterDataVersion getVerInfoById(String tableName, String id) {
        return this.baseDataService.getVerInfoById(tableName, id);
    }

    private static class InstanceHolder {
        static final BaseDataCenter instance = (BaseDataCenter)ApplicationContextRegister.getBean(BaseDataCenter.class);

        private InstanceHolder() {
        }
    }
}

