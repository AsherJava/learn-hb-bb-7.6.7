/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.workflow.detection.ParamExtract
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.service.BillCoreWorkFlowService;
import com.jiuqi.va.bill.service.BillParamExtractService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.workflow.detection.ParamExtract;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class BillParamExtractServiceImpl
implements BillParamExtractService {
    @Autowired
    private List<ParamExtract> paramExtractList;
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private BillCoreWorkFlowService billCoreWorkFlowService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private AuthUserClient authUserClient;
    private final Map<String, List<ParamExtract>> serviceMap = new HashMap<String, List<ParamExtract>>();
    public static final List<String> TYPE_STRING = new ArrayList<String>(Arrays.asList("UUID", "NVARCHAR", "CLOB", "IDENTIFY", "STRING"));
    public static final List<String> TYPE_DATE = new ArrayList<String>(Arrays.asList("DATE", "TIMESTAMP", "DATETIME"));
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_NUMERIC = "NUMERIC";
    public static final String TYPE_LONG = "LONG";
    public static final String TYPE_DECIMAL = "DECIMAL";
    public static final String TYPE_BOOLEAN = "BOOLEAN";

    @PostConstruct
    public void init() {
        this.paramExtractList.forEach(item -> {
            String module = item.getModule();
            if (this.serviceMap.containsKey(module)) {
                this.serviceMap.get(module).add((ParamExtract)item);
            } else {
                ArrayList<ParamExtract> list = new ArrayList<ParamExtract>();
                list.add((ParamExtract)item);
                this.serviceMap.put(module, list);
            }
        });
    }

    @Override
    public List<ParamExtract> getParamExtractBeanList(String module) {
        return this.serviceMap.get(module);
    }

    @Override
    public Map<String, Object> getWorkflowBusinessParam(Map<String, Object> variableMap, String defineCode, Map<String, Object> workflow) {
        HashMap<String, Object> masterTableData = new HashMap<String, Object>();
        HashMap<String, List<Map<String, Object>>> subTableDataMap = new HashMap<String, List<Map<String, Object>>>();
        String masterId = UUID.randomUUID().toString();
        String billCode = UUID.randomUUID().toString();
        this.getBizTableData(variableMap, subTableDataMap, masterTableData, masterId, billCode);
        long ver = System.currentTimeMillis();
        String billdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        HashMap<String, String> billState = new HashMap<String, String>();
        billState.put("name", "0");
        billState.put("title", "\u5df2\u4fdd\u5b58");
        UserLoginDTO user = ShiroUtil.getUser();
        String userId = user.getId();
        UserDTO dto = new UserDTO();
        dto.setId(userId);
        UserDO userDO = this.authUserClient.get(dto);
        HashMap<String, String> createUser = new HashMap<String, String>();
        createUser.put("id", userId);
        if (userDO != null) {
            createUser.put("title", userDO.getName());
        }
        String loginUnit = user.getLoginUnit();
        OrgDO orgData = this.getOrgData(null, loginUnit);
        HashMap<String, String> unitMap = new HashMap<String, String>(8);
        unitMap.put("name", loginUnit);
        if (orgData != null) {
            unitMap.put("title", orgData.getName());
            unitMap.put("showTitle", orgData.getShowTitle());
        }
        masterTableData.put("ID", masterId);
        masterTableData.put("BILLCODE", billCode);
        masterTableData.put("VER", ver);
        masterTableData.put("BILLDATE", billdate);
        masterTableData.put("BILLSTATE", billState);
        masterTableData.put("CREATETIME", billdate);
        masterTableData.put("CREATEUSER", createUser);
        masterTableData.put("DEFINECODE", defineCode);
        masterTableData.putIfAbsent("UNITCODE", unitMap);
        BillContextImpl contextImpl = new BillContextImpl();
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)contextImpl, defineCode, 0L);
        String masterTableName = model.getMasterTable().getName();
        ArrayList<HashMap<String, Object>> masterTableDataList = new ArrayList<HashMap<String, Object>>();
        masterTableDataList.add(masterTableData);
        subTableDataMap.put(masterTableName, masterTableDataList);
        model.getData().create();
        model.getData().setTablesData(subTableDataMap);
        return this.billCoreWorkFlowService.getWorkFlowParamsValueMap(model, workflow);
    }

    public void getBizTableData(Map<String, Object> variableMap, Map<String, List<Map<String, Object>>> subTableDataMap, Map<String, Object> masterTableData, String masterId, String billCode) {
        for (Map.Entry<String, Object> entry : variableMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            HashMap<String, Object> tableMap = new HashMap<String, Object>();
            if (value instanceof Map) {
                List<Map<String, Object>> list;
                Map valueMap = (Map)value;
                Object paramType = valueMap.get("paramType");
                Object paramValueObj = valueMap.get("code");
                String mapping = (String)valueMap.get("mapping");
                String title = (String)valueMap.get("title");
                Boolean subParamFlag = (Boolean)valueMap.get("subParamFlag");
                Object paramValue = this.handleParamType(paramType, paramValueObj);
                if (subParamFlag == null || !subParamFlag.booleanValue()) {
                    if (mapping != null || "UNITCODE".equals(key)) {
                        tableMap.put("name", paramValue);
                        tableMap.put("title", title);
                        masterTableData.put(key, tableMap);
                        continue;
                    }
                    masterTableData.put(key, paramValue);
                    continue;
                }
                String subTableName = (String)valueMap.get("subTableName");
                if (!subTableDataMap.containsKey(subTableName)) {
                    subTableDataMap.put(subTableName, new ArrayList());
                }
                if ((list = subTableDataMap.get(subTableName)).isEmpty()) {
                    HashMap<String, String> e = new HashMap<String, String>();
                    e.put("MASTERID", masterId);
                    e.put("BILLCODE", billCode);
                    e.put("ID", UUID.randomUUID().toString());
                    list.add(e);
                }
                if (mapping == null) {
                    list.get(0).put(key, paramValue);
                    continue;
                }
                tableMap.put("name", paramValue);
                tableMap.put("title", title);
                list.get(0).put(key, tableMap);
                continue;
            }
            if (value instanceof List) {
                Map map;
                Boolean subParamFlag;
                List values = (List)value;
                if (CollectionUtils.isEmpty(values)) continue;
                ArrayList subParam = new ArrayList();
                Iterator iterator = values.iterator();
                while (iterator.hasNext() && (subParamFlag = (Boolean)(map = (Map)iterator.next()).get("subParamFlag")) != null && subParamFlag.booleanValue()) {
                    Object paramType = map.get("paramType");
                    Object paramValueObj = map.get("paramType");
                    Object paramValue = this.handleParamType(paramType, paramValueObj);
                    subParam.add(paramType);
                }
                if (subParam.isEmpty()) continue;
            }
            masterTableData.put(key, value);
        }
    }

    public Object handleParamType(Object paramTypeObj, Object paramValueObj) {
        if (paramValueObj == null) {
            return null;
        }
        String paramType = (String)paramTypeObj;
        if (paramType == null) {
            paramType = "default";
        }
        String finalParamType = paramType;
        try {
            if (TYPE_STRING.stream().anyMatch(s -> s.equalsIgnoreCase(finalParamType)) || "default".equals(paramType)) {
                return String.valueOf(paramValueObj);
            }
            if (!StringUtils.hasText(paramValueObj.toString())) {
                return null;
            }
            if (TYPE_DATE.stream().anyMatch(s -> s.equalsIgnoreCase(finalParamType))) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return dateFormat.parse(paramValueObj.toString());
            }
            if (TYPE_INTEGER.equalsIgnoreCase(paramType)) {
                return Integer.parseInt(paramValueObj.toString());
            }
            if (TYPE_LONG.equalsIgnoreCase(paramType)) {
                return Long.parseLong(paramValueObj.toString());
            }
            if (TYPE_BOOLEAN.equalsIgnoreCase(paramType)) {
                return Boolean.parseBoolean(paramValueObj.toString());
            }
            if (TYPE_NUMERIC.equalsIgnoreCase(paramType) || TYPE_DECIMAL.equalsIgnoreCase(paramType)) {
                return new BigDecimal(paramValueObj.toString());
            }
        }
        catch (NumberFormatException e) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.bill.core.paramter.type.error") + e.getMessage());
        }
        catch (ParseException e) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.bill.core.paramter.error"));
        }
        return paramValueObj;
    }

    public OrgDO getOrgData(String tenantName, String code) {
        OrgDO orgDO = null;
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setRecoveryflag(Integer.valueOf(-1));
        orgDTO.setCode(code);
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setTenantName(tenantName);
        PageVO pageVO = this.orgDataClient.list(orgDTO);
        if (Objects.nonNull(pageVO) && !CollectionUtils.isEmpty(pageVO.getRows())) {
            orgDO = (OrgDO)pageVO.getRows().get(0);
        }
        return orgDO;
    }
}

