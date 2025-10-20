/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataPostEventProcessor;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.data.DataUpdateImpl;
import com.jiuqi.va.biz.impl.data.Holder;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginImpl;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.data.DataAccess;
import com.jiuqi.va.biz.intf.data.DataException;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.intf.encrypt.VaSymmetricEncryptService;
import com.jiuqi.va.biz.intf.model.DeclarePlugin;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.model.PluginManager;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.ConcurrentHashMapUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public abstract class DataPluginType
extends PluginTypeBase {
    private static final Logger logger = LoggerFactory.getLogger(DataPluginType.class);
    public static final String NAME = "data";
    public static final String TITLE = "\u6570\u636e";
    @Autowired
    private DataAccess dataAccess;
    @Value(value="${va.biz.data.sensitivity.enhanced:false}")
    private boolean securityEnhanced;
    @Autowired
    private VaSymmetricEncryptService vaSymmetricEncryptService;
    private PluginManager pluginManager;
    @Autowired
    private DataModelClient dataModelClient;

    public PluginManager getPluginManager() {
        if (this.pluginManager == null) {
            this.pluginManager = (PluginManager)ApplicationContextRegister.getBean(PluginManager.class);
        }
        return this.pluginManager;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public Class<? extends PluginDefineImpl> getPluginDefineClass() {
        return DataDefineImpl.class;
    }

    @Override
    public Class<? extends PluginImpl> getPluginClass() {
        return DataImpl.class;
    }

    @Override
    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.initPluginDefine(pluginDefine, modelDefine);
        DataDefineImpl dataDefineImpl = (DataDefineImpl)pluginDefine;
        dataDefineImpl.getTableList().forEach(o -> ((NamedContainerImpl)o.getFields()).stream().forEach(field -> {
            field.setSelected(true);
            field.setTable((DataTableDefineImpl)o);
        }));
        HashMap<UUID, String> multTableNameMap = new HashMap<UUID, String>();
        dataDefineImpl.getTableList().forEach(o -> {
            if (((NamedContainerImpl)o.getFields()).stream().filter(field -> field.getRefTableType() != 0 && field.getRefTableType() != 3 && field.getValueType() == ValueType.IDENTIFY || field.getRefTableType() == 3 && field.isMultiChoiceStore()).count() > 0L) {
                multTableNameMap.put(o.getId(), o.getMultiName());
            }
        });
        multTableNameMap.forEach((pId, tableName) -> this.loadMultTable(dataDefineImpl, (UUID)pId, (String)tableName));
        Map<String, List<String>> encryptedTableFieldMap = dataDefineImpl.getEncryptedTableFieldMap();
        ((ListContainerImpl)((Object)dataDefineImpl.getTables())).forEach((index, o) -> {
            for (int i = 0; i < ((NamedContainerImpl)o.getFields()).size(); ++i) {
                if (!((DataFieldDefineImpl)((NamedContainerImpl)o.getFields()).get(i)).isEncryptedStorage()) continue;
                ConcurrentHashMapUtils.computeIfAbsent(encryptedTableFieldMap, o.getTableName(), k -> new ArrayList()).add(((DataFieldDefineImpl)((NamedContainerImpl)o.getFields()).get(i)).getFieldName());
            }
        });
    }

    private void loadMultTable(DataDefineImpl dataDefineImpl, UUID parentId, String tableName) {
        if (((DataTableNodeContainerImpl)dataDefineImpl.getTables()).find(tableName) != null) {
            return;
        }
        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(tableName);
        DataModelDO dataModel = this.dataModelClient.get(dataModelDTO);
        if (dataModel == null) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.dataplugintype.notfountsubtable", new Object[]{tableName}));
        }
        DataTableDefineImpl tableDefine = new DataTableDefineImpl();
        tableDefine.setName(tableName);
        tableDefine.setId(UUID.randomUUID());
        tableDefine.setReadonly(true);
        tableDefine.setTitle(dataModel.getTitle());
        tableDefine.setTableName(tableName);
        tableDefine.setTableType(DataTableType.DATA);
        tableDefine.setParentId(parentId);
        dataModel.getColumns().forEach(col -> {
            DataFieldDefineImpl dataFieldDefine = new DataFieldDefineImpl();
            dataFieldDefine.setFieldName(col.getColumnName());
            dataFieldDefine.setName(col.getColumnName());
            dataFieldDefine.setFieldType(DataFieldType.DATA);
            dataFieldDefine.setId(UUID.randomUUID());
            dataFieldDefine.setTitle(col.getColumnTitle());
            dataFieldDefine.setValueType(this.toValueType(col.getColumnType()));
            tableDefine.addField(dataFieldDefine);
        });
        dataDefineImpl.addTable(tableDefine);
    }

    private ValueType toValueType(DataModelType.ColumnType columnType) {
        switch (columnType) {
            case UUID: {
                return ValueType.IDENTIFY;
            }
            case CLOB: {
                return ValueType.TEXT;
            }
            case DATE: {
                return ValueType.DATE;
            }
            case INTEGER: {
                return ValueType.INTEGER;
            }
            case NUMERIC: {
                return ValueType.DECIMAL;
            }
            case NVARCHAR: {
                return ValueType.STRING;
            }
            case TIMESTAMP: {
                return ValueType.DATETIME;
            }
        }
        return ValueType.AUTO;
    }

    @Override
    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        super.initPlugin(plugin, pluginDefine, model);
        DataImpl dataImpl = (DataImpl)plugin;
        dataImpl.setDataPluginType((DataPluginType)this.getPluginManager().get(this.getName()));
        DataDefineImpl dataDefineImpl = (DataDefineImpl)pluginDefine;
        List tableList = ((ListContainerImpl)((Object)dataDefineImpl.getTables())).stream().map(o -> new DataTableImpl((DataImpl)plugin, (DataTableDefineImpl)o)).collect(Collectors.toList());
        dataImpl.setTables(new DataTableNodeContainerImpl<DataTableImpl>(tableList));
        dataImpl.securityEnhanced = this.securityEnhanced;
    }

    public Map<String, List<Map<String, Object>>> load(DataImpl dataImpl, Map<String, Object> valueMap) {
        Map<UUID, Formula> conditionMap = dataImpl.getConditionMap();
        if (conditionMap.size() > 0) {
            return this.dataAccess.load(dataImpl.getModel().getDefine(), dataImpl.getDefine().getTables(), valueMap, conditionMap);
        }
        return this.dataAccess.load(dataImpl.getDefine().getTables(), valueMap);
    }

    public List<Map<String, Object>> load(DataTableDefine table, String condition) {
        return this.dataAccess.load(table, condition);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Transactional(rollbackFor={Exception.class})
    public void save(DataImpl dataImpl, Collection<String> fieldNames, DataPostEventProcessor processor, Map<String, DataUpdate> changeData) {
        processor.beforeSave();
        this.checkBillCodeRequired(dataImpl);
        Map<String, DataUpdate> update = DataPluginType.getFilterDataUpdateMap(dataImpl);
        Map<String, List<String>> encryptedTableFieldMap = dataImpl.getDefine().getEncryptedTableFieldMap();
        if (!encryptedTableFieldMap.isEmpty()) {
            this.executeEncryptedTableField(update, encryptedTableFieldMap);
        }
        if (update.values().stream().filter(o -> !o.isEmpty()).count() != 0L) {
            DataUpdate dataUpdate = update.get(dataImpl.getMasterTable().getName());
            if (dataUpdate.isEmpty()) {
                DataRowImpl master = dataImpl.getMasterTable().getRows().get(0);
                dataUpdate.getUpdate().add(Utils.makeMap("ID", master.getId(), "VER", master.getVersion()));
            }
            long ver = this.dataAccess.save(dataImpl.getDefine().getTables(), update, dataImpl.getKeys(Arrays.asList("ID", "VER")));
            update.forEach((n, v) -> {
                DataTableImpl table = (DataTableImpl)((DataTableNodeContainerImpl)dataImpl.getTables()).get((String)n);
                DataFieldDefineImpl find = (DataFieldDefineImpl)((NamedContainerImpl)table.getDefine().getFields()).find("VER");
                if (find == null) {
                    return;
                }
                Map<Object, DataRowImpl> map = table.getRows().stream().collect(Collectors.toMap(o -> o.getId(), o -> o));
                v.getInsert().forEach(o -> {
                    DataRowImpl row = (DataRowImpl)map.get(o.get("ID"));
                    row.setValue("VER", (Object)ver);
                    o.put("VER", ver);
                });
                v.getUpdate().forEach(o -> {
                    DataRowImpl row = (DataRowImpl)map.get(o.get("ID"));
                    row.setValue("VER", (Object)ver);
                    o.put("VER", ver);
                });
            });
        }
        changeData.putAll(update);
        try {
            dataImpl.localData().set(changeData);
            processor.afterSave();
        }
        finally {
            dataImpl.localData().remove();
        }
    }

    private void executeEncryptedTableField(Map<String, DataUpdate> updateData, Map<String, List<String>> encryptedTableFieldMap) {
        ArrayList<Holder<String>> holders = new ArrayList<Holder<String>>();
        for (Map.Entry<String, List<String>> table : encryptedTableFieldMap.entrySet()) {
            DataUpdate dataUpdate = updateData.get(table.getKey());
            List<Map<String, Object>> insert = dataUpdate.getInsert();
            for (Map<String, Object> insertData : insert) {
                for (String field : table.getValue()) {
                    Object o = insertData.get(field);
                    if (ObjectUtils.isEmpty(o)) continue;
                    Holder<String> holder = new Holder<String>((String)o);
                    holders.add(holder);
                    insertData.put(field, holder);
                }
            }
            List<Map<String, Object>> update = dataUpdate.getUpdate();
            for (Map<String, Object> updateDataMap : update) {
                for (String field : table.getValue()) {
                    Object o = updateDataMap.get(field);
                    if (ObjectUtils.isEmpty(o)) continue;
                    Holder<String> holder = new Holder<String>((String)o);
                    holders.add(holder);
                    updateDataMap.put(field, holder);
                }
            }
        }
        if (holders.isEmpty()) {
            return;
        }
        this.encryptFields(holders);
    }

    private void encryptFields(List<Holder<String>> holders) {
        List<String> fieldsToEncrypt = holders.stream().map(Holder::get).collect(Collectors.toList());
        List<String> ciphertexts = this.vaSymmetricEncryptService.doEncrypt(fieldsToEncrypt);
        for (int i = 0; i < holders.size(); ++i) {
            Holder<String> stringHolder = holders.get(i);
            stringHolder.set(ciphertexts.get(i));
        }
    }

    private static Map<String, DataUpdate> getFilterDataUpdateMap(DataImpl dataImpl) {
        Map<String, DataUpdate> update = dataImpl.getUpdate();
        ModelContextImpl context = (ModelContextImpl)dataImpl.getModel().getContext();
        Object detailEnableFilter = context.getContextValue("X--detailFilterDataId");
        if (detailEnableFilter == null) {
            return update;
        }
        Map tableList = (Map)detailEnableFilter;
        for (String s : update.keySet()) {
            DataUpdate remove;
            if (tableList.containsKey(s) || s.equals(dataImpl.getMasterTable().getName()) || (remove = update.put(s, new DataUpdateImpl())).isEmpty()) continue;
            logger.warn("\u975e\u6cd5\u65b0\u589e\u6216\u4fee\u6539\u7684\u6570\u636e:{}", (Object)JSONUtil.toJSONString((Object)remove));
        }
        return update;
    }

    private void checkBillCodeRequired(DataImpl dataImpl) {
        DataRowImpl master = dataImpl.getMasterTable().getRows().get(0);
        if (!StringUtils.hasText(master.getString("BILLCODE"))) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.billcode.required"));
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void delete(DataImpl dataImpl, Collection<String> fieldNames, DataPostEventProcessor processor) {
        ((ListContainerImpl)((Object)dataImpl.getTables())).forEach((i, o) -> o.getRowsData(false));
        processor.beforeDelete();
        List<Map<String, Object>> valueMaps = dataImpl.getMasterTable().getRows().stream().map(r -> fieldNames.stream().collect(Collectors.toMap(o -> o, o -> r.getOriginRow().getValue((String)o)))).collect(Collectors.toList());
        this.dataAccess.delete(dataImpl.getDefine().getTables(), valueMaps);
        processor.afterDelete();
    }

    public List<Map<String, Object>> load(DataImpl dataImpl, DataTableDefineImpl dataTableDefine, Map<String, Object> valueMap) {
        return this.dataAccess.load((DataTableDefine)dataTableDefine, valueMap);
    }

    @Override
    public void declare(DeclarePlugin declarePlugin) {
        declarePlugin.declare();
    }
}

