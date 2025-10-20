/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.common;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class DataQueryUtil {
    private OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
    AuthUserClient authUserClient = (AuthUserClient)ApplicationContextRegister.getBean(AuthUserClient.class);
    private BaseDataDefineClient baseDataDefineClient = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
    private BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
    private EnumDataClient enumDataClient = (EnumDataClient)ApplicationContextRegister.getBean(EnumDataClient.class);
    private static DataModelClient dataModelClient = (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
    private String tenantName;
    protected Map<String, List<String>> shareFieldsMap = new HashMap<String, List<String>>();
    private Map<String, DataModelDO> modelMap = new HashMap<String, DataModelDO>();
    private Map<String, BaseDataDefineDO> defineMap = new HashMap<String, BaseDataDefineDO>();
    private Map<String, Map<String, Map<String, Map<Object, List<BaseDataDO>>>>> baseDataMap = new HashMap<String, Map<String, Map<String, Map<Object, List<BaseDataDO>>>>>();
    private Map<String, Map<OrgDataOption.AuthType, Map<String, Map<Object, List<OrgDO>>>>> orgMap = new HashMap<String, Map<OrgDataOption.AuthType, Map<String, Map<Object, List<OrgDO>>>>>();
    private Map<String, Map<String, Map<Object, List<EnumDataDO>>>> enumMap = new HashMap<String, Map<String, Map<Object, List<EnumDataDO>>>>();
    private Map<String, Map<Object, List<UserDO>>> userMap = new HashMap<String, Map<Object, List<UserDO>>>();

    public DataQueryUtil(String tenantName) {
        this.tenantName = tenantName;
    }

    private Map<String, Map<Object, List<OrgDO>>> getOrgMap(String category, OrgDataOption.AuthType authType) {
        if (!this.orgMap.containsKey(category)) {
            this.loadOrg(category, authType, false);
        }
        return this.orgMap.get(category).get(authType);
    }

    private Map<Object, List<OrgDO>> getOrgMap(String category, OrgDataOption.AuthType authType, String key) {
        Map<String, Map<Object, List<OrgDO>>> keyMap = this.getOrgMap(category, authType);
        if (!keyMap.containsKey(key)) {
            this.initOrgKeyMap(category, authType, key);
        }
        return keyMap.get(key);
    }

    public void setOrg(String category, OrgDataOption.AuthType authType, String key, OrgDO org, int updataType) {
        Map<String, Map<Object, List<OrgDO>>> keyMap;
        Object value = org.getValueOf(key);
        if (value == null) {
            return;
        }
        if (!this.orgMap.containsKey(category) || !this.orgMap.get(category).containsKey(authType)) {
            this.loadOrg(category, authType, false);
        }
        if (!(keyMap = this.orgMap.get(category).get(authType)).containsKey(key)) {
            this.initOrgKeyMap(category, authType, key);
        }
        Map<Object, List<OrgDO>> valueMap = keyMap.get(key);
        valueMap.computeIfAbsent(value, k -> new ArrayList());
        List<OrgDO> list = valueMap.get(value);
        if (updataType == 0) {
            valueMap.get(value).add(org);
        } else if (updataType == 1) {
            int oldIndex = -1;
            for (int i = 0; i < list.size(); ++i) {
                if (!list.get(i).getId().equals(org.getId())) continue;
                oldIndex = i;
                break;
            }
            if (oldIndex > 0) {
                list.set(oldIndex, org);
            } else {
                valueMap.get(value).add(org);
            }
        }
    }

    public List<OrgDO> getOrg(String category, OrgDataOption.AuthType authType, String key, Object value) {
        if (value == null) {
            return null;
        }
        Map<Object, List<OrgDO>> valueMap = this.getOrgMap(category, authType, key);
        return valueMap.get(value);
    }

    private void initOrgKeyMap(String category, OrgDataOption.AuthType authType, String key) {
        if (!this.orgMap.containsKey(category)) {
            this.loadOrg(category, authType, false);
        }
        Map<Object, List<OrgDO>> codeOrgMap = this.orgMap.get(category).get(authType).get("code");
        HashMap<Object, List> valueMap = new HashMap<Object, List>();
        if (codeOrgMap != null && !codeOrgMap.isEmpty()) {
            for (List<OrgDO> valueList : codeOrgMap.values()) {
                if (valueList == null) continue;
                for (OrgDO org : valueList) {
                    Object value = org.get((Object)key);
                    if (value == null) continue;
                    valueMap.computeIfAbsent(value, k -> new ArrayList());
                    List list = (List)valueMap.get(value);
                    list.add(org);
                }
            }
        }
        this.orgMap.get(category).get(authType).put(key, valueMap);
    }

    private void loadOrg(String category, OrgDataOption.AuthType authType, boolean force) {
        if (this.orgMap == null) {
            this.orgMap = new HashMap<String, Map<OrgDataOption.AuthType, Map<String, Map<Object, List<OrgDO>>>>>();
        }
        if (this.orgMap.get(category) != null && !force) {
            return;
        }
        OrgDTO queryParam = new OrgDTO();
        queryParam.setTenantName(this.tenantName);
        queryParam.setCategoryname(category);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setAuthType(authType);
        queryParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        HashMap<String, List> orgCodeMap = new HashMap<String, List>();
        PageVO temp = this.orgDataClient.list(queryParam);
        if (temp != null && temp.getRows() != null) {
            for (OrgDO org : temp.getRows()) {
                List orgList = (List)orgCodeMap.get(org.getCode());
                orgList = orgList == null ? new ArrayList() : orgList;
                orgList.add(org);
                orgCodeMap.put(org.getCode(), orgList);
            }
        }
        HashMap<String, HashMap<String, List>> keyMap = new HashMap<String, HashMap<String, List>>();
        keyMap.put("code", orgCodeMap);
        HashMap<OrgDataOption.AuthType, HashMap<String, HashMap<String, List>>> authMap = new HashMap<OrgDataOption.AuthType, HashMap<String, HashMap<String, List>>>();
        authMap.put(authType, keyMap);
        this.orgMap.put(category, authMap);
    }

    protected void loadEnumData(String biztype) {
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setTenantName(this.tenantName);
        enumDataDTO.setBiztype(biztype);
        List enumDatas = this.enumDataClient.list(enumDataDTO);
        HashMap<String, List> valueMap = new HashMap<String, List>();
        if (enumDatas != null && !enumDatas.isEmpty()) {
            for (EnumDataDO data : enumDatas) {
                List dataList = (List)valueMap.get(data.getVal());
                dataList = dataList == null ? new ArrayList() : dataList;
                dataList.add(data);
                valueMap.put(data.getVal(), dataList);
            }
        }
        HashMap<String, HashMap<String, List>> keyMap = new HashMap<String, HashMap<String, List>>();
        keyMap.put("val", valueMap);
        this.enumMap.put(biztype, keyMap);
    }

    protected List<EnumDataDO> getEnumData(String biztype, String key, Object value) {
        Map<String, Map<Object, List<EnumDataDO>>> keyMap;
        if (value == null) {
            return null;
        }
        if (!this.enumMap.containsKey(biztype)) {
            this.loadEnumData(biztype);
        }
        if (!(keyMap = this.enumMap.get(biztype)).containsKey(key)) {
            this.initEnumKeyMap(biztype, key);
        }
        return keyMap.get(key).get(value);
    }

    private void initEnumKeyMap(String biztype, String key) {
        if (!this.enumMap.containsKey(biztype)) {
            this.loadEnumData(biztype);
        }
        Map<String, Map<Object, List<EnumDataDO>>> keyMap = this.enumMap.get(biztype);
        Map<Object, List<EnumDataDO>> valValueMap = keyMap.get("val");
        HashMap<Object, List> valueMap = new HashMap<Object, List>();
        for (List<EnumDataDO> valList : valValueMap.values()) {
            if (valList == null) continue;
            for (EnumDataDO data : valList) {
                String value = null;
                if (key.equalsIgnoreCase("val")) {
                    value = data.getVal();
                } else if (key.equalsIgnoreCase("title")) {
                    value = data.getTitle();
                }
                if (value == null) continue;
                valueMap.computeIfAbsent(value, k -> new ArrayList());
                List list = (List)valueMap.get(value);
                list.add(data);
            }
        }
        keyMap.put(key, valueMap);
    }

    protected void loadUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setTenantName(this.tenantName);
        List userRes = this.authUserClient.list(userDTO).getRows();
        HashMap valueMap = new HashMap();
        if (userRes != null && !userRes.isEmpty()) {
            for (UserDO user : userRes) {
                if (!valueMap.containsKey(user.getId())) {
                    valueMap.put(user.getId(), new ArrayList());
                }
                List userlist = (List)valueMap.get(user.getId());
                userlist.add(user);
            }
        }
        this.userMap.put("id", valueMap);
    }

    public List<UserDO> getUser(String key, Object value) {
        if (value == null) {
            return null;
        }
        if (this.userMap.isEmpty()) {
            this.loadUser();
        }
        if (!this.userMap.containsKey(key)) {
            this.initUserKeyMap(key);
        }
        return this.userMap.get(key).get(value);
    }

    private void initUserKeyMap(String key) {
        if (this.userMap.containsKey("id")) {
            this.loadUser();
        }
        HashMap<Object, List> valueMap = new HashMap<Object, List>();
        Map<Object, List<UserDO>> idValueMap = this.userMap.get("id");
        if (!idValueMap.isEmpty()) {
            for (List<UserDO> valueList : idValueMap.values()) {
                if (valueList == null) continue;
                for (UserDO user : valueList) {
                    String value = null;
                    if (key.equalsIgnoreCase("ID")) {
                        value = user.getId();
                    } else if (key.equalsIgnoreCase("USERNAME")) {
                        value = user.getUsername();
                    } else if (key.equalsIgnoreCase("EMAIL")) {
                        value = user.getEmail();
                    } else if (key.equalsIgnoreCase("TELEPHONE")) {
                        value = user.getTelephone();
                    }
                    if (value == null) continue;
                    valueMap.computeIfAbsent(value, k -> new ArrayList());
                    List list = (List)valueMap.get(value);
                    list.add(user);
                }
            }
        }
        this.userMap.put(key, valueMap);
    }

    private void loadBaseData(String tableName, String unitcode) {
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTenantName(this.tenantName);
        queryParam.setTableName(tableName);
        queryParam.setUnitcode(unitcode);
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO queryRes = this.baseDataClient.list(queryParam);
        HashMap dataMap = new HashMap();
        if (queryRes != null && queryRes.getRows() != null) {
            for (BaseDataDO data : queryRes.getRows()) {
                if (!dataMap.containsKey(data.getObjectcode())) {
                    dataMap.put(data.getObjectcode(), new ArrayList());
                }
                List list = (List)dataMap.get(data.getObjectcode());
                list.add(data);
            }
        }
        this.baseDataMap.computeIfAbsent(tableName, k -> new HashMap());
        Map<String, Map<String, Map<Object, List<BaseDataDO>>>> shareMap = this.baseDataMap.get(tableName);
        shareMap.computeIfAbsent(unitcode, k -> new HashMap());
        Map<String, Map<Object, List<BaseDataDO>>> keyMap = shareMap.get(unitcode);
        keyMap.put("objectcode", dataMap);
    }

    public List<BaseDataDO> getBaseData(String tableName, String unitcode, String key, Object value) {
        Map<String, Map<Object, List<BaseDataDO>>> specialKeyMap;
        Map<String, Map<String, Map<Object, List<BaseDataDO>>>> shareMap;
        if (!this.baseDataMap.containsKey(tableName)) {
            this.loadBaseData(tableName, unitcode);
        }
        if (!(shareMap = this.baseDataMap.get(tableName)).containsKey(unitcode)) {
            this.loadBaseData(tableName, unitcode);
        }
        if (!(specialKeyMap = shareMap.get(unitcode)).containsKey(unitcode)) {
            this.initBasedataKeyMap(tableName, unitcode, key);
        }
        return specialKeyMap.get(key).get(value);
    }

    private void initBasedataKeyMap(String tableName, String unitcode, String key) {
        if (!this.baseDataMap.containsKey(tableName) || !this.baseDataMap.get(tableName).containsKey(unitcode)) {
            this.loadBaseData(tableName, unitcode);
        }
        Map<Object, List<BaseDataDO>> objectMap = this.baseDataMap.get(tableName).get(unitcode).get("objectcode");
        HashMap<Object, List> valueMap = new HashMap<Object, List>();
        if (objectMap != null && !objectMap.isEmpty()) {
            for (List<BaseDataDO> list : objectMap.values()) {
                if (list == null) continue;
                for (BaseDataDO data : list) {
                    Object value = data.get((Object)key);
                    if (value == null) continue;
                    valueMap.computeIfAbsent(value, k -> new ArrayList());
                    List dataList = (List)valueMap.get(value);
                    dataList.add(data);
                }
            }
        }
        this.baseDataMap.get(tableName).get(unitcode).put(key, valueMap);
    }

    public List<String> getSharefields(String tableName) {
        List<String> res = this.shareFieldsMap.get(tableName);
        if (this.shareFieldsMap.containsKey(tableName)) {
            return res;
        }
        res = new ArrayList<String>();
        BaseDataDefineDO define = this.getBasedataDefine(tableName);
        String sharefieldString = define.getSharefieldname();
        String unitcode = "unitcode";
        if (define.getSharetype() != 0) {
            if (!StringUtils.hasText(sharefieldString)) {
                res.add(unitcode);
            } else {
                for (String shareField : sharefieldString.split("\\,")) {
                    res.add(shareField.toLowerCase());
                }
                if (!res.contains(unitcode)) {
                    res.add(unitcode);
                }
            }
        }
        this.shareFieldsMap.put(tableName, res);
        return res;
    }

    public BaseDataDefineDO getBasedataDefine(String tableName) {
        if (!this.defineMap.containsKey(tableName)) {
            this.loadBasedataDefine(tableName);
        }
        return this.defineMap.get(tableName);
    }

    protected void loadBasedataDefine(String tableName) {
        BaseDataDefineDTO refDefineParam = new BaseDataDefineDTO();
        refDefineParam.setName(tableName);
        refDefineParam.setDeepClone(Boolean.valueOf(false));
        PageVO res = this.baseDataDefineClient.list(refDefineParam);
        if (res == null || res.getRows() == null || res.getRows().isEmpty()) {
            throw new RuntimeException("\u83b7\u53d6" + tableName + "\u8868\u5b9a\u4e49\u5931\u8d25");
        }
        this.defineMap.put(tableName, (BaseDataDefineDO)res.getRows().get(0));
    }

    public DataModelDO getModel(String tableName) {
        if (!this.modelMap.containsKey(tableName)) {
            this.loadModel(tableName);
        }
        return this.modelMap.get(tableName);
    }

    protected void loadModel(String tableName) {
        DataModelDO refDefineParam = DataQueryUtil.getDataModalDefine(tableName);
        this.modelMap.put(tableName, refDefineParam);
        refDefineParam.setName(tableName);
    }

    public static DataModelDO getDataModalDefine(String tableName) {
        DataModelDTO defineParam = new DataModelDTO();
        defineParam.setName(tableName);
        return dataModelClient.get(defineParam);
    }
}

