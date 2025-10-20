/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.biz.front.FrontModelDefine
 *  com.jiuqi.va.biz.impl.data.DataFieldImpl
 *  com.jiuqi.va.biz.impl.data.DataImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.impl.model.ModelContextImpl
 *  com.jiuqi.va.biz.inc.intf.DataRecord
 *  com.jiuqi.va.biz.intf.ActionCategory
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.action.ActionReturnConfirmMessage
 *  com.jiuqi.va.biz.intf.action.ActionReturnConfirmTemplate
 *  com.jiuqi.va.biz.intf.action.ActionReturnObject
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataRowState
 *  com.jiuqi.va.biz.intf.data.DataState
 *  com.jiuqi.va.biz.intf.model.BillFrontDefineService
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelContext
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefineService
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.biz.view.impl.CompositeImpl
 *  com.jiuqi.va.biz.view.impl.ViewImpl
 *  com.jiuqi.va.biz.view.intf.ExternalViewDefine
 *  com.jiuqi.va.biz.view.intf.ExternalViewManager
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.util.LogUtil
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.dao.DataAccessException
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bill.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.bill.config.VaBillCoreConfig;
import com.jiuqi.va.bill.domain.SublistExportDTO;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.inc.intf.BillDataCacheManager;
import com.jiuqi.va.bill.inc.intf.BillDataCacheProvider;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillIncEditService;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.bill.utils.ExcelUtil;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.inc.intf.DataRecord;
import com.jiuqi.va.biz.intf.ActionCategory;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.ActionReturnConfirmMessage;
import com.jiuqi.va.biz.intf.action.ActionReturnConfirmTemplate;
import com.jiuqi.va.biz.intf.action.ActionReturnObject;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.model.BillFrontDefineService;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelDefineService;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.view.impl.CompositeImpl;
import com.jiuqi.va.biz.view.impl.ViewImpl;
import com.jiuqi.va.biz.view.intf.ExternalViewDefine;
import com.jiuqi.va.biz.view.intf.ExternalViewManager;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class BillIncEditServiceImpl
implements BillIncEditService,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BillIncEditServiceImpl.class);
    private String curUrl;
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private ActionManager actionManager;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private ModelDefineService modelDefineService;
    @Autowired(required=false)
    private ExternalViewManager externalViewManager;
    @Autowired
    private BillFrontDefineService billFrontDefineService;
    @Value(value="${biz.cache.mode:1}")
    private int cacheMode;
    @Value(value="${biz.cache.node:1}")
    private int cacheNode;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BillDataCacheManager billDataCacheManager;
    private BillDataCacheProvider billDataCacheProvider;

    @Override
    public void afterPropertiesSet() {
        this.billDataCacheProvider = BillDataCacheManager.get(this.cacheMode);
    }

    public static void loadBill(BillModel model, Object dataId) {
        UUID id;
        if (dataId == null) {
            return;
        }
        try {
            id = (UUID)Convert.cast((Object)dataId, UUID.class);
        }
        catch (Exception e) {
            id = null;
        }
        if (id == null || !id.toString().equals(dataId.toString())) {
            model.loadByCode(dataId.toString());
        } else {
            model.loadById(id);
        }
    }

    public static void loadBillByCode(BillModel model, Object billCode) {
        if (billCode == null) {
            return;
        }
        model.loadByCode(billCode.toString());
    }

    public static void loadBillById(BillModel model, Object billId) {
        if (billId == null) {
            return;
        }
        model.loadById(Convert.cast((Object)billId, UUID.class));
    }

    private Map<String, Object> wrap(BillModelImpl model, String viewName, long defineVer, String schemeCode, boolean needCache) {
        FrontModelDefine define = this.billFrontDefineService.getDefine((Model)model, schemeCode, viewName);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", UUID.randomUUID());
        if (needCache) {
            UUID requID = UUID.randomUUID();
            UUID cacheID = UUID.randomUUID();
            map.put("data", this.buildFrontTablesData(model, cacheID, requID, define));
            map.put("reqID", requID);
            map.put("cacheID", cacheID);
            map.put("cacheNode", this.cacheNode);
        } else {
            map.put("data", this.buildFrontViewData(model, define));
        }
        map.put("state", model.getData().getState().getValue());
        if (defineVer == 0L || defineVer != model.getDefine().getMetaInfo().getVersion()) {
            map.put("define", define);
        }
        if (model.getContext().getVerifyCode() != null) {
            map.put("verifyCode", model.getContext().getVerifyCode());
        }
        return map;
    }

    private Map<String, Object> wrap(BillModelImpl model, String viewName, long defineVer, Object executeReturn, String schmecode) {
        FrontModelDefine define = this.billFrontDefineService.getDefine((Model)model, schmecode, viewName);
        UUID requID = UUID.randomUUID();
        UUID cacheID = UUID.randomUUID();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", UUID.randomUUID());
        map.put("data", this.buildFrontTablesData(model, cacheID, requID, define));
        map.put("state", model.getData().getState().getValue());
        if (defineVer == 0L || defineVer != model.getDefine().getMetaInfo().getVersion()) {
            map.put("define", define);
        }
        if (model.getContext().getVerifyCode() != null) {
            map.put("verifyCode", model.getContext().getVerifyCode());
        }
        map.put("result", executeReturn);
        map.put("reqID", requID);
        map.put("cacheID", cacheID);
        map.put("cacheNode", this.cacheNode);
        return map;
    }

    @Override
    public Map<String, Object> add(BillContext context, String defineCode, long defineVer, String viewName, String schemeCode, Map<String, Object> param) {
        UUID requID = UUID.randomUUID();
        UUID cacheID = UUID.randomUUID();
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel(context, defineCode);
        this.executeModelAdd(param, model);
        DataRow master = model.getMaster();
        if (master != null) {
            LogUtil.add((String)"\u5355\u636e", (String)"\u65b0\u5efa", (String)model.getDefine().getName(), (String)master.getString("BILLCODE"), null);
        }
        FrontModelDefine define = this.billFrontDefineService.getDefine((Model)model, schemeCode, viewName);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", UUID.randomUUID());
        map.put("data", this.buildFrontTablesData(model, cacheID, requID, define));
        map.put("state", model.getData().getState().getValue());
        if (defineVer == 0L || defineVer != model.getDefine().getMetaInfo().getVersion()) {
            map.put("define", define);
        }
        if (model.getContext().getVerifyCode() != null) {
            map.put("verifyCode", model.getContext().getVerifyCode());
        }
        map.put("reqID", requID);
        map.put("cacheID", cacheID);
        map.put("cacheNode", this.cacheNode);
        return map;
    }

    private Map<String, Object[]> buildIncTablesDataWithoutCache(BillModelImpl model, FrontModelDefine billDefine) {
        boolean hasRecord = model.getData().hasRecorded();
        LinkedHashMap<String, Object[]> tablesData = new LinkedHashMap<String, Object[]>();
        Map needTableFields = billDefine.getTableFields();
        DataImpl data = model.getData();
        DataTableNodeContainerImpl tables = data.getTables();
        if (hasRecord) {
            DataRecord recordUpdate = model.getData().getRecordUpdate();
            Map insert = recordUpdate.getInsert();
            Map update = recordUpdate.getUpdate();
            Map delete = recordUpdate.getDelete();
            tables.forEach((index, t) -> {
                String tableName = t.getName();
                NamedContainer fields = t.getFields();
                int length = fields.size();
                Set needFields = (Set)needTableFields.get(tableName);
                List rowList = t.getRowList();
                int rowLength = rowList.size();
                Object[] rowsData = new Object[4];
                if (needFields != null && needFields.size() != 0) {
                    List uuids;
                    Set insertIds = (Set)insert.get(tableName);
                    Map uuidMapMap = (Map)update.get(tableName);
                    tablesData.put(tableName, rowsData);
                    ArrayList<String> frontFields = new ArrayList<String>();
                    ArrayList frontFieldIndexs = new ArrayList();
                    fields.forEachIndex((i, f) -> {
                        String fieldName = f.getName();
                        if (needFields.contains(fieldName)) {
                            frontFields.add(fieldName);
                            frontFieldIndexs.add(f.getIndex());
                        }
                    });
                    frontFields.add("$UNSET");
                    ArrayList addRows = new ArrayList();
                    HashMap frontUpdate = new HashMap();
                    for (DataRowImpl dataRow : rowList) {
                        ArrayList<Object> rowVal = new ArrayList<Object>();
                        boolean isNew = insertIds != null && insertIds.contains(dataRow.getId());
                        boolean isUpdate = uuidMapMap != null && uuidMapMap.containsKey(dataRow.getId());
                        for (int j = 0; j < length; ++j) {
                            if (!isNew || !frontFieldIndexs.contains(j)) continue;
                            rowVal.add(dataRow.getViewValue(j));
                        }
                        if (isUpdate) {
                            HashMap<String, Object> updateResult = new HashMap<String, Object>();
                            Map updateMap = (Map)uuidMapMap.get(dataRow.getId());
                            Set keys = updateMap.keySet();
                            for (String key : keys) {
                                if (!frontFields.contains(key)) continue;
                                updateResult.put(key, dataRow.getViewValue(key));
                            }
                            frontUpdate.put((UUID)dataRow.getId(), updateResult);
                        }
                        if (rowVal.size() <= 0) continue;
                        rowVal.add(dataRow.isUnset());
                        addRows.add(rowVal);
                    }
                    rowsData[0] = frontFields.toArray();
                    if (addRows.size() > 0) {
                        rowsData[1] = addRows;
                    }
                    if (frontUpdate.size() > 0) {
                        rowsData[2] = frontUpdate;
                    }
                    if ((uuids = (List)delete.get(tableName)) != null) {
                        rowsData[3] = uuids.toArray();
                    }
                }
            });
        } else {
            tables.forEach((index, t) -> {
                String tableName = t.getName();
                NamedContainer fields = t.getFields();
                int length = fields.size();
                Set needFields = (Set)needTableFields.get(tableName);
                List rowList = t.getRowList();
                Object[] rowsData = new Object[4];
                if (needFields != null && needFields.size() != 0) {
                    tablesData.put(tableName, rowsData);
                    ArrayList<String> frontFields = new ArrayList<String>();
                    ArrayList frontFieldIndexs = new ArrayList();
                    fields.forEachIndex((i, f) -> {
                        String fieldName = f.getName();
                        if (needFields.contains(fieldName)) {
                            frontFields.add(fieldName);
                            frontFieldIndexs.add(f.getIndex());
                        }
                    });
                    frontFields.add("$UNSET");
                    ArrayList addRows = new ArrayList();
                    for (DataRowImpl dataRow : rowList) {
                        ArrayList<Object> rowVal = new ArrayList<Object>();
                        for (int j = 0; j < length; ++j) {
                            if (!frontFieldIndexs.contains(j)) continue;
                            rowVal.add(dataRow.getViewValue(j));
                        }
                        if (rowVal.size() <= 0) continue;
                        rowVal.add(dataRow.isUnset());
                        addRows.add(rowVal);
                    }
                    rowsData[0] = frontFields.toArray();
                    if (addRows.size() > 0) {
                        rowsData[1] = addRows;
                    }
                }
            });
        }
        return tablesData;
    }

    private Map<String, Object[]> buildFrontViewData(BillModelImpl model, FrontModelDefine billDefine) {
        LinkedHashMap<String, Object[]> tablesData = new LinkedHashMap<String, Object[]>();
        Map needTableFields = billDefine.getTableFields();
        DataImpl data = model.getData();
        DataTableNodeContainerImpl tables = data.getTables();
        tables.forEach((index, t) -> {
            String tableName = t.getName();
            Set needFields = (Set)needTableFields.get(tableName);
            List rowList = t.getRowList();
            int rowLength = rowList.size();
            Object[] rowsData = new Object[4];
            if (needFields != null && needFields.size() != 0) {
                tablesData.put(tableName, rowsData);
                NamedContainer fields = t.getFields();
                int length = fields.size();
                ArrayList<String> frontFields = new ArrayList<String>();
                ArrayList frontFieldIndexs = new ArrayList();
                fields.forEachIndex((i, f) -> {
                    String fieldName = f.getName();
                    if (needFields.contains(fieldName)) {
                        frontFields.add(fieldName);
                        frontFieldIndexs.add(f.getIndex());
                    }
                });
                frontFields.add("$UNSET");
                rowsData[0] = frontFields.toArray();
                ArrayList<Object[]> insertRows = new ArrayList<Object[]>();
                for (DataRowImpl dataRow : rowList) {
                    ArrayList<Object> result = new ArrayList<Object>();
                    for (int j = 0; j < length; ++j) {
                        if (!frontFieldIndexs.contains(j)) continue;
                        result.add(dataRow.getViewValue(j));
                    }
                    result.add(dataRow.isUnset());
                    insertRows.add(result.toArray());
                }
                rowsData[1] = insertRows;
            }
        });
        return tablesData;
    }

    private Map<String, Object[]> buildFrontTablesData(BillModelImpl model, UUID cacheID, UUID requID, FrontModelDefine billDefine) {
        String url;
        boolean canRecord = model.getData().hasRecorded();
        LinkedHashMap<String, Object[]> tablesData = new LinkedHashMap<String, Object[]>();
        HashMap define = new HashMap();
        HashMap tableState = new HashMap();
        HashMap cacheTablesData = new HashMap();
        Map needTableFields = billDefine.getTableFields();
        DataImpl data = model.getData();
        DataTableNodeContainerImpl tables = data.getTables();
        tables.forEach((index, t) -> {
            String tableName = t.getName();
            NamedContainer fields = t.getFields();
            int length = fields.size();
            Set needFields = (Set)needTableFields.get(tableName);
            List rowList = t.getRowList();
            List deleteRows = t.getDeletedRows().collect(Collectors.toList());
            int rowLength = rowList.size();
            int delRowLength = deleteRows.size();
            Object[] rowsData = new Object[4];
            tableState.put(tableName, t.getStatus());
            if (needFields == null || needFields.size() == 0) {
                int j;
                Object[] cacheData;
                int i2;
                List fieldNames = fields.stream().map(DataFieldImpl::getName).collect(Collectors.toList());
                fieldNames.add("$UNSET");
                fieldNames.add("$STATE");
                define.put(tableName, fieldNames.toArray());
                Object[] tableRows = new Object[rowLength + delRowLength];
                cacheTablesData.put(tableName, tableRows);
                for (i2 = 0; i2 < rowLength; ++i2) {
                    DataRowImpl dataRow = (DataRowImpl)rowList.get(i2);
                    cacheData = new Object[length + 2];
                    for (j = 0; j < length; ++j) {
                        boolean[] modified = dataRow.getModified();
                        if (modified != null && modified[j]) {
                            DataRowImpl originRow = dataRow.getOriginRow();
                            Object[] valAndModify = new Object[]{dataRow.getRawValue(j), originRow.getRawValue(j)};
                            cacheData[j] = valAndModify;
                            continue;
                        }
                        cacheData[j] = dataRow.getRawValue(j);
                    }
                    cacheData[length] = dataRow.isUnset();
                    cacheData[length + 1] = dataRow.getState();
                    tableRows[i2] = cacheData;
                }
                for (i2 = 0; i2 < delRowLength; ++i2) {
                    DataRowImpl delRow = (DataRowImpl)deleteRows.get(i2);
                    cacheData = new Object[length + 2];
                    for (j = 0; j < length; ++j) {
                        cacheData[j] = delRow.getRawValue(j);
                    }
                    cacheData[length] = delRow.isUnset();
                    cacheData[length + 1] = DataRowState.DELETED;
                    tableRows[rowLength + i2] = cacheData;
                }
            } else {
                tablesData.put(tableName, rowsData);
                ArrayList addRows = new ArrayList();
                HashMap frontUpdate = new HashMap();
                Object[] cacheFields = new Object[length + 2];
                ArrayList<String> frontFields = new ArrayList<String>();
                ArrayList frontFieldIndexs = new ArrayList();
                fields.forEachIndex((i, f) -> {
                    String fieldName = f.getName();
                    cacheFields[i.intValue()] = fieldName;
                    if (needFields.contains(fieldName)) {
                        frontFields.add(fieldName);
                        frontFieldIndexs.add(f.getIndex());
                    }
                });
                cacheFields[length] = "$UNSET";
                cacheFields[length + 1] = "$STATE";
                frontFields.add("$UNSET");
                define.put(tableName, cacheFields);
                Object[] tableRows = new Object[rowLength + delRowLength];
                cacheTablesData.put(tableName, tableRows);
                if (canRecord) {
                    Object[] cacheData;
                    int i3;
                    DataRecord recordUpdate = data.getRecordUpdate();
                    Map insert = recordUpdate.getInsert();
                    Map update = recordUpdate.getUpdate();
                    Map delete = recordUpdate.getDelete();
                    Set insertIds = (Set)insert.get(tableName);
                    Map uuidMapMap = (Map)update.get(tableName);
                    for (i3 = 0; i3 < rowLength; ++i3) {
                        DataRowImpl dataRow = (DataRowImpl)rowList.get(i3);
                        cacheData = new Object[length + 2];
                        ArrayList<Object> rowVal = new ArrayList<Object>();
                        boolean isNew = insertIds != null && insertIds.contains(dataRow.getId());
                        boolean isUpdate = uuidMapMap != null && uuidMapMap.containsKey(dataRow.getId());
                        for (int j = 0; j < length; ++j) {
                            boolean[] modified = dataRow.getModified();
                            if (modified != null && modified[j]) {
                                DataRowImpl originRow = dataRow.getOriginRow();
                                Object[] valAndModify = new Object[]{dataRow.getRawValue(j), originRow.getRawValue(j)};
                                cacheData[j] = valAndModify;
                            } else {
                                cacheData[j] = dataRow.getRawValue(j);
                            }
                            if (!isNew || !frontFieldIndexs.contains(j)) continue;
                            rowVal.add(dataRow.getViewValue(j));
                        }
                        if (isUpdate) {
                            HashMap<String, Object> updateResult = new HashMap<String, Object>();
                            Map updateMap = (Map)uuidMapMap.get(dataRow.getId());
                            Set keys = updateMap.keySet();
                            for (String key : keys) {
                                if (!frontFields.contains(key)) continue;
                                updateResult.put(key, dataRow.getViewValue(key));
                            }
                            frontUpdate.put((UUID)dataRow.getId(), updateResult);
                        }
                        if (rowVal.size() > 0) {
                            rowVal.add(dataRow.isUnset());
                            addRows.add(rowVal);
                        }
                        cacheData[length] = dataRow.isUnset();
                        cacheData[length + 1] = dataRow.getState();
                        tableRows[i3] = cacheData;
                    }
                    for (i3 = 0; i3 < delRowLength; ++i3) {
                        DataRowImpl delRow = (DataRowImpl)deleteRows.get(i3);
                        cacheData = new Object[length + 2];
                        for (int j = 0; j < length; ++j) {
                            cacheData[j] = delRow.getRawValue(j);
                        }
                        cacheData[length] = delRow.isUnset();
                        cacheData[length + 1] = DataRowState.DELETED;
                        tableRows[rowLength + i3] = cacheData;
                    }
                    List uuids = (List)delete.get(tableName);
                    if (uuids != null) {
                        rowsData[3] = uuids.toArray();
                    }
                } else {
                    for (int i4 = 0; i4 < rowLength; ++i4) {
                        DataRowImpl dataRow = (DataRowImpl)rowList.get(i4);
                        Object[] cacheData = new Object[length + 2];
                        ArrayList<Object> rowVal = new ArrayList<Object>();
                        for (int j = 0; j < length; ++j) {
                            cacheData[j] = dataRow.getRawValue(j);
                            if (!frontFieldIndexs.contains(j)) continue;
                            rowVal.add(dataRow.getViewValue(j));
                        }
                        if (rowVal.size() > 0) {
                            rowVal.add(dataRow.isUnset());
                            addRows.add(rowVal);
                        }
                        cacheData[length] = dataRow.isUnset();
                        cacheData[length + 1] = dataRow.getState();
                        tableRows[i4] = cacheData;
                    }
                }
                rowsData[0] = frontFields.toArray();
                if (addRows.size() > 0) {
                    rowsData[1] = addRows;
                }
                if (frontUpdate.size() > 0) {
                    rowsData[2] = frontUpdate;
                }
            }
        });
        HashMap<String, Object> cacheData = new HashMap<String, Object>();
        cacheData.put("cacheID", cacheID);
        cacheData.put("reqID", requID);
        cacheData.put("ver", model.getDefine().getMetaInfo().getVersion());
        cacheData.put("state", data.getState().getValue());
        cacheData.put("define", define);
        cacheData.put("data", cacheTablesData);
        cacheData.put("tableState", tableState);
        this.billDataCacheProvider.put(cacheID.toString(), cacheData);
        if (this.cacheMode == 1 && VaBillCoreConfig.isRedisEnable() && (url = this.getUrl()) != null) {
            this.stringRedisTemplate.opsForValue().set((Object)cacheID.toString(), (Object)url, 3L, TimeUnit.MINUTES);
        }
        return tablesData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeModelAdd(Map<String, Object> param, BillModelImpl model) {
        model.getRuler().getRulerExecutor().setEnable(true);
        try {
            DataImpl data = model.getData();
            data.startRecord();
            Action action = (Action)this.actionManager.get("bill-add");
            if (action == null) {
                if (param != null) {
                    model.add(param);
                } else {
                    model.add();
                }
            } else {
                ActionRequest request = new ActionRequest();
                if (param != null) {
                    request.setParams(param);
                }
                ActionResponse response = new ActionResponse();
                model.executeAction(action, request, response);
            }
            data.resetIncOrdinal();
            data.stopRecord();
        }
        finally {
            model.getRuler().getRulerExecutor().setEnable(false);
        }
    }

    @Override
    public Map<String, Object> add(BillContextImpl context, String defineCode, long defineVer, String viewName, Map<String, Object> param, String externalViewName) {
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)context, defineCode, externalViewName);
        this.executeModelAdd(param, model);
        LogUtil.add((String)"\u5355\u636e", (String)"\u65b0\u5efa", (String)model.getDefine().getName(), (String)model.getMaster().getString("BILLCODE"), null);
        ExternalViewDefine externalViewDefine = (ExternalViewDefine)this.externalViewManager.find(externalViewName);
        if (externalViewDefine != null) {
            return this.externalViewWrap(model, viewName, defineVer, externalViewDefine, true);
        }
        return this.wrap(model, viewName, defineVer, null, true);
    }

    private Map<String, Object> externalViewWrap(BillModelImpl model, String viewName, long defineVer, ExternalViewDefine externalViewDefine, boolean needCache) {
        FrontModelDefine externalDefine = this.billFrontDefineService.getExternalDefine((Model)model, externalViewDefine.getName(), viewName);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", UUID.randomUUID());
        if (needCache) {
            UUID requID = UUID.randomUUID();
            UUID cacheID = UUID.randomUUID();
            map.put("data", this.buildFrontTablesData(model, cacheID, requID, externalDefine));
            map.put("reqID", requID);
            map.put("cacheID", cacheID);
            map.put("cacheNode", this.cacheNode);
        } else {
            map.put("data", this.buildFrontViewData(model, externalDefine));
        }
        map.put("data", model.getData().getFrontTablesData());
        map.put("state", model.getData().getState().getValue());
        if (defineVer == 0L || defineVer != model.getDefine().getMetaInfo().getVersion()) {
            map.put("define", externalDefine);
        }
        if (model.getContext().getVerifyCode() != null) {
            map.put("verifyCode", model.getContext().getVerifyCode());
        }
        return map;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, Object> edit(BillContext context, String defineCode, long defineVer, String dataId, String viewName, String schemeCode) {
        BillModelImpl model;
        block7: {
            model = (BillModelImpl)this.billDefineService.createModel(context, defineCode);
            BillIncEditServiceImpl.loadBillByCode(model, dataId);
            model.getRuler().getRulerExecutor().setEnable(true);
            try {
                boolean billChange;
                Action action = null;
                boolean bl = billChange = model.getContext().getContextValue("X--loadChangeData") != null;
                if (billChange) {
                    action = (Action)this.actionManager.get("va-billChange-edit");
                } else {
                    action = (Action)this.actionManager.get("bill-edit");
                    Object sceneKey = context.getContextValue("scene_key");
                    if (sceneKey != null && sceneKey.equals("billlist")) {
                        model.getContext().setContextValue("X--consistency", false);
                    }
                }
                if (action == null) {
                    model.edit();
                    break block7;
                }
                ActionRequest request = new ActionRequest();
                ActionResponse response = new ActionResponse();
                model.executeAction(action, request, response);
                Object executeReturn = response.getReturnValue() != null ? response.getReturnValue() : response.getReturnMessage();
                Map<String, Object> map = this.wrap(model, viewName, defineVer, executeReturn, schemeCode);
                return map;
            }
            finally {
                model.getRuler().getRulerExecutor().setEnable(false);
            }
        }
        return this.wrap(model, viewName, defineVer, schemeCode, true);
    }

    @Override
    public Map<String, Object> load(BillContext context, String defineCode, long defineVer, String dataId, String viewName) {
        BillDefine define = this.billDefineService.getDefine(defineCode);
        return this.load(context, define, defineVer, dataId, viewName, null);
    }

    @Override
    public Map<String, Object> load(BillContext context, String defineCode, long defineVer, String dataId, String viewName, String schemeCode) {
        BillDefine define = this.billDefineService.getDefine(defineCode);
        if (StringUtils.hasText(schemeCode)) {
            return this.load(context, define, defineVer, dataId, viewName, schemeCode, null);
        }
        return this.load(context, define, defineVer, dataId, viewName, null);
    }

    @Override
    public Map<String, Object> load(BillContext context, String defineCode, long defineVer, String dataId, String viewName, String schemeCode, String externalViewName) {
        BillDefine define = this.billDefineService.getDefine(defineCode, externalViewName);
        if (StringUtils.hasText(schemeCode)) {
            return this.load(context, define, defineVer, dataId, viewName, schemeCode, externalViewName);
        }
        return this.load(context, define, defineVer, dataId, viewName, externalViewName);
    }

    @Override
    public Map<String, Object> load(BillContext context, BillDefine define, String dataId, String viewName) {
        return this.load(context, define, 0L, dataId, viewName, null);
    }

    public Map<String, Object> load(BillContext context, BillDefine define, long defineVer, String dataId, String viewName, String externalViewName) {
        ExternalViewDefine externalViewDefine;
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel(context, define);
        BillIncEditServiceImpl.loadBillByCode(model, dataId);
        model.getRuler().getRulerExecutor().setEnable(true);
        model.getRuler().getRulerExecutor().setEnable(false);
        if (StringUtils.hasText(externalViewName) && (externalViewDefine = (ExternalViewDefine)this.externalViewManager.find(externalViewName)) != null) {
            return this.externalViewWrap(model, viewName, defineVer, externalViewDefine, false);
        }
        return this.wrap(model, viewName, defineVer, null, false);
    }

    public Map<String, Object> load(BillContext context, BillDefine define, long defineVer, String dataId, String viewName, String schemeCode, String externalViewName) {
        ExternalViewDefine externalViewDefine;
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel(context, define);
        BillIncEditServiceImpl.loadBillByCode(model, dataId);
        model.getRuler().getRulerExecutor().setEnable(true);
        model.getRuler().getRulerExecutor().setEnable(false);
        if (StringUtils.hasText(externalViewName) && (externalViewDefine = (ExternalViewDefine)this.externalViewManager.find(externalViewName)) != null) {
            return this.externalViewWrap(model, viewName, defineVer, externalViewDefine, false);
        }
        return this.wrap(model, viewName, defineVer, schemeCode, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, Object> sync(BillContext context, String cacheID, String reqID, String defineCode, long defineVer, String dataId, String viewName, Map<String, Object> data, String externalViewName) {
        String type = (String)data.get("type");
        Map modelParams = (Map)data.get("model");
        Map params = (Map)data.get("params");
        List subActions = (List)data.get("actions");
        Action action = (Action)this.actionManager.get(type);
        Map updateData = (Map)modelParams.get("update");
        Map insertData = (Map)modelParams.get("insert");
        Map deleteData = (Map)modelParams.get("delete");
        DataState modelState = (DataState)Convert.cast(modelParams.get("state"), DataState.class);
        String modelBillCode = (String)modelParams.get("master.BILLCODE");
        long modelBillVer = (Long)Convert.cast(modelParams.get("master.VER"), Long.TYPE);
        BillModelImpl model = this.buildModel(context, defineCode, defineVer, data, externalViewName);
        model.getContext().setContextValue("ActionType", type);
        if (modelState == DataState.BROWSE) {
            Object executeReturn;
            if (modelBillCode != null) {
                BillIncEditServiceImpl.loadBillByCode(model, modelBillCode);
                long oldVer = model.getMaster().getVersion();
                if (!type.equals("bill-reload") && oldVer != modelBillVer) {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.datachangerefresh"));
                }
            }
            ArrayList executeSubReturns = new ArrayList();
            model.getRuler().getRulerExecutor().setEnable(true);
            try {
                try {
                    ActionRequest request = new ActionRequest();
                    request.setParams(params);
                    ActionResponse response = new ActionResponse();
                    model.executeAction(action, request, response);
                    if (!response.isSuccess() && response.getCheckMessages() != null && response.getCheckMessages().size() > 0) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.executefailed"), response.getCheckMessages());
                    }
                    executeReturn = response.getReturnValue() != null ? response.getReturnValue() : response.getReturnMessage();
                }
                catch (BillException e) {
                    throw e;
                }
                catch (DataAccessException e) {
                    BillException billException = new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.executeexception"), e);
                    billException.setInvisible(true);
                    throw billException;
                }
                catch (Exception e) {
                    BillException billException = new BillException(e.getMessage(), e);
                    billException.setInvisible(true);
                    throw billException;
                }
            }
            finally {
                model.getRuler().getRulerExecutor().setEnable(false);
            }
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("result", executeReturn);
            result.put("subActionResults", executeSubReturns);
            DataState state = model.getData().getState();
            if (state != DataState.NONE) {
                result.put("id", model.getMaster().getId());
            }
            if (!(executeReturn instanceof ActionReturnObject) || ((ActionReturnObject)executeReturn).isSuccess()) {
                FrontModelDefine billDefine = this.billFrontDefineService.getDefine((Model)model, null, viewName, defineVer);
                if (state == DataState.EDIT || state == DataState.NEW) {
                    UUID newCacheID = UUID.randomUUID();
                    UUID newReqID = UUID.randomUUID();
                    result.put("cacheID", newCacheID);
                    result.put("reqID", newReqID);
                    result.put("cacheNode", this.cacheNode);
                    result.put("data", this.buildFrontTablesData(model, newCacheID, newReqID, billDefine));
                } else {
                    result.put("data", this.buildIncTablesDataWithoutCache(model, billDefine));
                }
                result.put("state", model.getData().getState().getValue());
            }
            if (model.getContext().getVerifyCode() != null) {
                result.put("verifyCode", model.getContext().getVerifyCode());
            }
            return result;
        }
        if (modelState == DataState.NEW || modelState == DataState.EDIT) {
            Object executeReturn;
            if (!StringUtils.hasText(cacheID)) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.bill.core.inc.cacheId.not.empty"));
            }
            Map cacheData = (Map)this.billDataCacheProvider.get(cacheID);
            if (cacheData == null) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.bill.core.inc.cacheData.expired"));
            }
            String cReqid = String.valueOf(cacheData.get("reqID"));
            if (!reqID.equals(cReqid)) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.bill.core.inc.cacheData.expired"));
            }
            Map tableFields = (Map)cacheData.get("define");
            Map tablesData = (Map)cacheData.get("data");
            Map tableState = (Map)cacheData.get("tableState");
            DataImpl dataImpl = model.getData();
            dataImpl.initCacheData(tableState, tableFields, tablesData, modelState);
            dataImpl.startRecord();
            if (deleteData != null) {
                model.getData().handleDeleteData(deleteData);
            }
            if (insertData != null) {
                model.getData().handleInserData(insertData);
            }
            if (updateData != null) {
                model.getData().handleUpdateData(updateData);
            }
            ArrayList executeSubReturns = new ArrayList();
            Map frontTablesData = null;
            model.getRuler().getRulerExecutor().setEnable(true);
            try {
                if (subActions != null && subActions.size() > 0) {
                    subActions.forEach(o -> {
                        String sub_type = (String)o.get("type");
                        Map sub_params = (Map)o.get("params");
                        Action sub_action = (Action)this.actionManager.get(sub_type);
                        ActionRequest sub_request = new ActionRequest();
                        sub_request.setParams(sub_params);
                        ActionResponse sub_response = new ActionResponse();
                        model.executeAction(sub_action, sub_request, sub_response);
                        if (sub_response.getReturnValue() != null) {
                            executeSubReturns.add(sub_response.getReturnValue());
                        } else {
                            executeSubReturns.add(sub_response.getReturnMessage());
                        }
                    });
                    frontTablesData = model.getData().getFrontTablesData();
                }
                try {
                    ActionRequest request = new ActionRequest();
                    request.setParams(params);
                    if (params != null && params.containsKey("confirms")) {
                        model.getContext().setContextValue("X--confirms", params.get("confirms"));
                    }
                    ActionResponse response = new ActionResponse();
                    model.executeAction(action, request, response);
                    if (!response.isSuccess() && response.getCheckMessages() != null && response.getCheckMessages().size() > 0) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.executefailed"), response.getCheckMessages());
                    }
                    executeReturn = response.getReturnValue() != null ? response.getReturnValue() : response.getReturnMessage();
                    BillIncEditServiceImpl.setConfirms(model, executeReturn);
                }
                catch (BillException e) {
                    if (e.getTablesData() == null) {
                        e.setFrontTablesData(frontTablesData);
                    }
                    throw e;
                }
                catch (DataAccessException e) {
                    BillException billException = new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.executeexception"), e);
                    billException.setFrontTablesData(frontTablesData);
                    billException.setInvisible(true);
                    throw billException;
                }
                catch (Exception e) {
                    BillException billException = new BillException(e.getMessage(), e);
                    billException.setFrontTablesData(frontTablesData);
                    billException.setInvisible(true);
                    throw billException;
                }
            }
            finally {
                model.getRuler().getRulerExecutor().setEnable(false);
            }
            dataImpl.resetIncOrdinal();
            dataImpl.stopRecord();
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("result", executeReturn);
            result.put("subActionResults", executeSubReturns);
            Object contextValue = model.getContext().getContextValue("X--loadChangeData");
            if (contextValue != null && ((Boolean)contextValue).booleanValue() && model.getContext().getContextValue("X--editTableFields") != null) {
                result.put("editTableFields", model.getContext().getContextValue("X--editTableFields"));
            }
            if (model.getData().getState() != DataState.NONE) {
                result.put("id", model.getMaster().getId());
            }
            DataState state = model.getData().getState();
            if (executeReturn instanceof ActionReturnObject && !((ActionReturnObject)executeReturn).isSuccess()) {
                if (tablesData != null) {
                    UUID newReqID = UUID.randomUUID();
                    result.put("cacheID", cacheID);
                    result.put("reqID", newReqID);
                    FrontModelDefine billDefine = this.billFrontDefineService.getDefine((Model)model, null, viewName, defineVer);
                    if (state == DataState.BROWSE) {
                        result.put("data", this.buildIncTablesDataWithoutCache(model, billDefine));
                        this.delCache(cacheID);
                    } else {
                        result.put("data", this.buildFrontTablesData(model, UUID.fromString(cacheID), newReqID, billDefine));
                        result.put("cacheNode", this.cacheNode);
                    }
                }
            } else {
                FrontModelDefine billDefine = this.billFrontDefineService.getDefine((Model)model, null, viewName, defineVer);
                UUID newReqID = UUID.randomUUID();
                result.put("cacheID", cacheID);
                result.put("reqID", newReqID);
                if (state == DataState.BROWSE) {
                    result.put("data", this.buildIncTablesDataWithoutCache(model, billDefine));
                    this.delCache(cacheID);
                } else {
                    result.put("data", this.buildFrontTablesData(model, UUID.fromString(cacheID), newReqID, billDefine));
                    result.put("cacheNode", this.cacheNode);
                }
                result.put("state", model.getData().getState().getValue());
            }
            if (model.getContext().getVerifyCode() != null) {
                result.put("verifyCode", model.getContext().getVerifyCode());
            }
            if (context.getContextValue("X--isModified") != null && !ActionCategory.SAVE.equals((Object)action.getActionCategory())) {
                boolean isModified = false;
                if (DataState.NEW.equals((Object)modelState)) {
                    isModified = true;
                } else {
                    DataTableNodeContainerImpl tables = model.getData().getTables();
                    for (int i = 0; i < tables.size(); ++i) {
                        DataTableImpl dataTable = (DataTableImpl)tables.get(i);
                        if (dataTable.getDeletedRows().findAny().isPresent()) {
                            isModified = true;
                            break;
                        }
                        for (DataRowImpl dataRow : dataTable.getRowList()) {
                            if (!DataRowState.APPENDED.equals((Object)dataRow.getState()) && !DataRowState.MODIFIED.equals((Object)dataRow.getState()) && !DataRowState.DELETED.equals((Object)dataRow.getState())) continue;
                            isModified = true;
                            break;
                        }
                        if (isModified) break;
                    }
                }
                result.put("isModified", isModified);
            }
            return result;
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.billmusteditstate"));
    }

    static void setConfirms(BillModelImpl model, Object executeReturn) {
        ActionReturnConfirmTemplate confirmMessage;
        String confirmFrom;
        if (executeReturn instanceof ActionReturnConfirmMessage) {
            ActionReturnConfirmMessage confirmMessage2 = (ActionReturnConfirmMessage)executeReturn;
            String confirmFrom2 = confirmMessage2.getConfirmFrom();
            if (StringUtils.hasText(confirmFrom2)) {
                confirmMessage2.setConfirms(model.getContext().getContextValue("X--confirms"));
            }
        } else if (executeReturn instanceof ActionReturnConfirmTemplate && StringUtils.hasText(confirmFrom = (confirmMessage = (ActionReturnConfirmTemplate)executeReturn).getConfirmFrom())) {
            confirmMessage.setConfirms(model.getContext().getContextValue("X--confirms"));
        }
    }

    private BillModelImpl buildModel(BillContext context, String defineCode, long defineVer, Map<String, Object> data, String externalViewName) {
        BillModelImpl model;
        Object define = data.get("define");
        if (define != null) {
            ((ModelContextImpl)context).setPreview(true);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            BillDefine billDefine = (BillDefine)mapper.convertValue(define, BillDefine.class);
            String billType = billDefine.getModelType();
            ModelType modelType = (ModelType)this.modelManager.find(billType);
            if (StringUtils.hasText(externalViewName)) {
                modelType.initModelDefine((ModelDefine)billDefine, billDefine.getName(), externalViewName);
            } else {
                modelType.initModelDefine((ModelDefine)billDefine, billDefine.getName());
            }
            model = (BillModelImpl)this.billDefineService.createModel(context, billDefine);
        } else if (StringUtils.hasText(externalViewName)) {
            model = (BillModelImpl)this.billDefineService.createModel(context, defineCode, externalViewName);
            if (defineVer != 0L && defineVer != model.getDefine().getMetaInfo().getVersion()) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.billchangerefresh"));
            }
        } else {
            model = (BillModelImpl)this.billDefineService.createModel(context, defineCode, defineVer);
        }
        return model;
    }

    @Override
    public void refreshCache(String cacheID) {
        this.billDataCacheProvider.refresh(cacheID);
        if (this.cacheMode == 1 && VaBillCoreConfig.isRedisEnable()) {
            this.stringRedisTemplate.expire((Object)cacheID, 3L, TimeUnit.MINUTES);
        }
    }

    @Override
    public void delCache(String cacheID) {
        try {
            this.billDataCacheProvider.clear(cacheID);
        }
        finally {
            if (this.cacheMode == 1 && VaBillCoreConfig.isRedisEnable()) {
                this.stringRedisTemplate.delete((Object)cacheID);
            }
        }
    }

    @Override
    public String getUrl() {
        if (this.curUrl == null) {
            this.curUrl = Utils.getHostName();
        }
        return this.curUrl;
    }

    @Override
    public void export(SublistExportDTO dto, HttpServletResponse response) {
        String defineCode = dto.getDefineCode();
        String billCode = dto.getBillCode();
        String params = dto.getParams();
        BillContextImpl context = new BillContextImpl();
        context.setDisableVerify(true);
        context.setTenantName(ShiroUtil.getTenantName());
        context.setTriggerOrigin("pc");
        context.setContextValue((Map)dto.getModelContext());
        BillModelImpl model = (BillModelImpl)this.modelDefineService.createModel((ModelContext)context, defineCode);
        model.loadByCode(billCode);
        ViewImpl view = (ViewImpl)model.getPlugins().get("view");
        model.getContext().setContextValue("X--timeZone", dto.getCurTimezone());
        CompositeImpl template = (CompositeImpl)view.getDefine().getTemplate();
        LinkedList<Map<String, Object>> queue = new LinkedList<Map<String, Object>>();
        if ("v-wizard".equals(template.getProps().get("type"))) {
            String curView = dto.getCurView();
            if (!StringUtils.hasText(curView)) {
                logger.error("\u5411\u5bfc\u754c\u9762\u5b50\u8868\u5bfc\u51fa\uff0c\u5f53\u524d\u754c\u9762\u4e3a\u7a7a");
                return;
            }
            List schemes = view.getDefine().getSchemes();
            for (Map stringObjectMap : schemes) {
                if (!curView.equals(stringObjectMap.get("code"))) continue;
                Map template1 = (Map)stringObjectMap.get("template");
                List children = (List)template1.get("children");
                children.forEach(value -> queue.offer((Map<String, Object>)value));
                break;
            }
        } else {
            List list = template.getChildren();
            list.forEach(value -> queue.offer(value.getProps()));
        }
        Map map = (Map)JSONUtil.parseObject((String)params, Map.class);
        List tableNames = (List)map.get("tableName");
        if (!CollectionUtils.isEmpty(tableNames)) {
            String s = (String)tableNames.get(0);
            try {
                UUID.fromString(s);
            }
            catch (IllegalArgumentException e) {
                this.oldExport(response, queue, map, model);
                return;
            }
            this.newExport(response, queue, map, model);
        }
    }

    private void newExport(HttpServletResponse response, Queue<Map<String, Object>> queue, Map map, BillModelImpl model) {
        HashMap<String, String> idNames = new HashMap<String, String>();
        HashMap<String, List<Map<String, Object>>> tableFields = new HashMap<String, List<Map<String, Object>>>();
        while (!queue.isEmpty()) {
            Map json;
            Map<String, Object> poll = queue.poll();
            if ("v-grid".equals(poll.get("type")) && (json = JSONUtil.parseMap((String)JSONUtil.toJSONString((Object)poll.get("binding")))).containsKey("fields")) {
                String id = poll.get("id").toString();
                idNames.put(id, json.get("tableName").toString());
                List fields = JSONUtil.parseMapArray((String)JSONUtil.toJSONString(json.get("fields")));
                tableFields.put(id, fields);
                continue;
            }
            this.addQueue(queue, poll);
        }
        ExcelUtil.exportExcel(response, model, map, tableFields, idNames);
    }

    private void oldExport(HttpServletResponse response, Queue<Map<String, Object>> queue, Map map, BillModelImpl model) {
        HashMap<String, List<Map<String, Object>>> tableFields = new HashMap<String, List<Map<String, Object>>>();
        while (!queue.isEmpty()) {
            Map json;
            Map<String, Object> poll = queue.poll();
            if (poll.containsKey("binding") && (json = JSONUtil.parseMap((String)JSONUtil.toJSONString((Object)poll.get("binding")))).containsKey("fields")) {
                String tableName = BillUtils.valueToString(json.get("tableName"));
                List fields = JSONUtil.parseMapArray((String)JSONUtil.toJSONString(json.get("fields")));
                List collect = null;
                collect = tableFields.containsKey(tableName) ? (List)tableFields.get(tableName) : new ArrayList();
                collect.addAll(fields);
                tableFields.put(tableName, collect);
                continue;
            }
            this.addQueue(queue, poll);
        }
        ExcelUtil.exportExcel(response, model, map, tableFields, null);
    }

    private void addQueue(Queue<Map<String, Object>> queue, Map<String, Object> poll) {
        List mapList = (List)poll.get("children");
        if (mapList != null && mapList.size() > 0) {
            mapList.forEach(value -> queue.add((Map<String, Object>)value));
        }
    }
}

