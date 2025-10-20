/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataUpdate
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.bill.BillDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.feign.client.BillBusinessClient
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.http.HttpHeaders
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.bill.BillDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.feign.client.BillBusinessClient;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
@Lazy(value=false)
public class BillUtils
implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(BillUtils.class);
    public static BillUtils billUtil;
    @Autowired
    private BillCodeClient billCodeClient;
    @Autowired
    private BillDataService billDataService;
    @Autowired
    private MetaDataClient metaDataClient;

    public static String valueToString(Object value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    }

    public static String dateStrConvert(String datetime, String pattern1, String pattern2) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern1);
        LocalDateTime ldt = LocalDateTime.parse(datetime, dtf);
        DateTimeFormatter fa = DateTimeFormatter.ofPattern(pattern2);
        return ldt.format(fa);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        billUtil = this;
    }

    public static DataModelClient getDataModelClient() {
        return (DataModelClient)ApplicationContextRegister.getBean(DataModelClient.class);
    }

    public static boolean isFeignClient(HttpHeaders headers) {
        List list = headers.get((Object)"feignclient");
        return list != null && list.size() > 0 && Boolean.valueOf((String)list.get(0)) != false;
    }

    public static String getDefineCodeByBillCode(String billCode) {
        HashMap<String, String> extInfo = new HashMap<String, String>();
        extInfo.put("billCode", billCode);
        TenantDO tenant = new TenantDO();
        tenant.setExtInfo(extInfo);
        R r = BillUtils.billUtil.billCodeClient.getUniqueCodeByBillCode(tenant);
        if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
            throw new RuntimeException(r.get((Object)"msg").toString());
        }
        String value = r.get((Object)"value").toString();
        if (!StringUtils.hasText(value)) {
            throw new RuntimeException(BillCoreI18nUtil.getMessage("va.billcore.billclient.findfailedbybillcode"));
        }
        return value;
    }

    public String getModuleByBillCode(String billCode) {
        String defineCode = BillUtils.getDefineCodeByBillCode(billCode);
        return defineCode.substring(0, defineCode.indexOf("_"));
    }

    public static R update(BillDataDTO params) {
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(billCode)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"billCode"}));
        }
        String defineCode = params.getDefineCode();
        try {
            if (!StringUtils.hasText(defineCode)) {
                defineCode = BillUtils.getDefineCodeByBillCode(billCode);
            }
            BillContextImpl contextImpl = new BillContextImpl();
            contextImpl.setDisableVerify(true);
            contextImpl.setVerifyCode(params.getVerifyCode());
            Map<String, List<Map<String, Object>>> data = BillUtils.billUtil.billDataService.save(contextImpl, defineCode, false, params.getBillData());
            return R.ok(Stream.of(data).collect(Collectors.toMap(o -> "data", o -> data)));
        }
        catch (BillException e) {
            log.error(e.getMessage(), e);
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public static R load(BillDataDTO params) {
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(billCode)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"billCode"}));
        }
        String defineCode = params.getDefineCode();
        try {
            if (!StringUtils.hasText(defineCode)) {
                defineCode = BillUtils.getDefineCodeByBillCode(billCode);
            }
            BillContextImpl contextImpl = new BillContextImpl();
            contextImpl.setDisableVerify(true);
            contextImpl.setVerifyCode(params.getVerifyCode());
            Map<String, List<Map<String, Object>>> data = BillUtils.billUtil.billDataService.load(contextImpl, defineCode, billCode);
            return R.ok(Stream.of(data).collect(Collectors.toMap(o -> "data", o -> data)));
        }
        catch (BillException e) {
            log.error(e.getMessage(), e);
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public static BillDefine getBillDefineByCode(TenantDO tenant) {
        return BillUtils.billUtil.billDataService.getBillDefineByCode(tenant);
    }

    public static Map<String, Object> getMap(Object object) {
        if (object instanceof Map) {
            return (Map)object;
        }
        return Collections.emptyMap();
    }

    public static <T> List<T> getList(Object object) {
        if (object instanceof List) {
            return (List)object;
        }
        return Collections.emptyList();
    }

    public BillBusinessClient getBillBusinessClientByDefineCode(String definecode) {
        ModuleDTO moduleDTO = new ModuleDTO();
        moduleDTO.setFunctionType("BILL");
        moduleDTO.setModuleName(definecode.split("_")[0]);
        moduleDTO.setTraceId(Utils.getTraceId());
        R module = this.metaDataClient.getModuleByName(moduleDTO);
        String server = String.valueOf(module.get((Object)"server"));
        String path = String.valueOf(module.get((Object)"path"));
        return (BillBusinessClient)FeignUtil.getDynamicClient(BillBusinessClient.class, (String)server, (String)path);
    }

    public static String getStringDate(Date date, String targetZone) {
        Instant instant = date.toInstant();
        ZoneId defaultZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime defaultTime = instant.atZone(defaultZoneId);
        ZoneId targetZoneId = ZoneId.of(targetZone);
        ZonedDateTime targetTime = defaultTime.withZoneSameInstant(targetZoneId);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return targetTime.format(dateTimeFormatter);
    }

    public static String convertToShanghaiTime(String novosibirskTimeStr, String fromZone) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime novosibirskTime = ZonedDateTime.parse(novosibirskTimeStr, inputFormatter.withZone(ZoneId.of(fromZone)));
        ZoneId shanghaiZoneId = ZoneId.of("Asia/Shanghai");
        ZonedDateTime shanghaiTime = novosibirskTime.withZoneSameInstant(shanghaiZoneId);
        return shanghaiTime.format(inputFormatter);
    }

    public static List<DataModelColumn> getDataModelColumnList(Map<String, List<DataModelColumn>> cacheMap, String key) {
        if (cacheMap.containsKey(key)) {
            return cacheMap.get(key);
        }
        DataModelDTO dataModelDto = new DataModelDTO();
        dataModelDto.setName(key);
        DataModelDO dataModelDO = BillUtils.getDataModelClient().get(dataModelDto);
        if (dataModelDO == null) {
            throw new RuntimeException("\u6570\u636e\u5efa\u6a21\u4e0d\u5b58\u5728\uff1a" + key);
        }
        List columns = dataModelDO.getColumns();
        cacheMap.put(key, columns);
        return columns;
    }

    public static Map<String, Map<String, Object>> getUpdateData(Map<String, DataUpdate> dataUpdateMap, DataTableNodeContainerImpl<DataTableImpl> tables) {
        HashMap<String, Map<String, Object>> changeRecord = new HashMap<String, Map<String, Object>>();
        for (Map.Entry<String, DataUpdate> entry : dataUpdateMap.entrySet()) {
            List update;
            List delete;
            String tableName = entry.getKey();
            HashMap<String, Cloneable> recordMap = new HashMap<String, Cloneable>();
            DataUpdate dataUpdate = entry.getValue();
            List insert = dataUpdate.getInsert();
            if (!CollectionUtils.isEmpty(insert)) {
                ArrayList insertRows = new ArrayList();
                for (Map row : insert) {
                    insertRows.add(row.get("ID"));
                }
                recordMap.put("insert", insertRows);
            }
            if (!CollectionUtils.isEmpty(delete = dataUpdate.getDelete())) {
                DataTableImpl dataTable = (DataTableImpl)tables.find(tableName);
                List deletedRows = dataTable.getDeletedRows().collect(Collectors.toList());
                ArrayList<Map> delRows = new ArrayList<Map>();
                for (Object row : deletedRows) {
                    delRows.add(row.getData(false));
                }
                recordMap.put("delete", delRows);
            }
            if (!CollectionUtils.isEmpty(update = dataUpdate.getUpdate())) {
                DataTableImpl dataTable = (DataTableImpl)tables.find(tableName);
                HashMap updateRecord = new HashMap();
                for (Object row : update) {
                    Object id = row.get("ID");
                    ArrayList values = new ArrayList();
                    updateRecord.put(id, values);
                    DataRow curRow = dataTable.getRowById(id);
                    DataRow originRow = curRow.getOriginRow();
                    row.keySet().forEach(o -> {
                        if (o.startsWith("CALC_") || o.equals("ID")) {
                            return;
                        }
                        Object[] fieldValus = new Object[]{o, originRow.getValue(o), curRow.getValue(o)};
                        values.add(fieldValus);
                    });
                }
                recordMap.put("update", updateRecord);
            }
            if (recordMap.isEmpty()) continue;
            changeRecord.put(tableName, recordMap);
        }
        return changeRecord;
    }
}

