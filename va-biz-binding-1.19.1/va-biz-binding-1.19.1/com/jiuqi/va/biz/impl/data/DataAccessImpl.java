/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.config.VaMapperConfig
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataSqlBuilder;
import com.jiuqi.va.biz.impl.data.Encrypt;
import com.jiuqi.va.biz.impl.data.Holder;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.intf.data.DataAccess;
import com.jiuqi.va.biz.intf.data.DataException;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.intf.encrypt.VaSymmetricEncryptService;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ValueKind;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.mapper.config.VaMapperConfig;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Lazy(value=false)
public class DataAccessImpl
implements DataAccess,
InitializingBean {
    @Value(value="${spring.datasource.multiTenant}")
    private boolean multiTenant;
    @Value(value="${spring.datasource.schemePrefix}")
    private String schemePrefix = "";
    @Autowired
    private VaSymmetricEncryptService vaSymmetricEncryptService;
    private Boolean stringNullAsEmpty;
    @Autowired
    private JdbcTemplate template;

    public boolean isStringNullAsEmpty() {
        if (this.stringNullAsEmpty == null) {
            String dbType = VaMapperConfig.getDbType();
            this.stringNullAsEmpty = dbType.equals("gauss") || dbType.equals("kingbase") || dbType.equals("vastbase") || dbType.equals("postgresql");
        }
        return this.stringNullAsEmpty;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataSqlBuilder.setMultiTenant(this.multiTenant);
        DataSqlBuilder.setSchemePrefix(this.schemePrefix);
    }

    private String createMap(final List<?> ids) {
        final String result = UUID.randomUUID().toString();
        if (ids.size() > 0) {
            String insertTempValueSQL = DataSqlBuilder.insert("BIZ_TEMP_VALUE", Arrays.asList("ID", "V"));
            this.template.batchUpdate(insertTempValueSQL, new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, result);
                    ps.setObject(2, ids.get(i).toString());
                }

                public int getBatchSize() {
                    return ids.size();
                }
            });
        }
        return result;
    }

    private void deleteMap(final List<String> ids) {
        if (ids.size() == 0) {
            return;
        }
        String deleteTempValueSQL = DataSqlBuilder.delete("BIZ_TEMP_VALUE", Arrays.asList("ID"), Arrays.asList(ValueKind.SINGLE));
        this.template.batchUpdate(deleteTempValueSQL, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setObject(1, ids.get(i));
            }

            public int getBatchSize() {
                return ids.size();
            }
        });
    }

    private Object[] mapValues(Collection<Object> values, List<String> ids) {
        ArrayList list = new ArrayList();
        values.forEach(o -> {
            switch (ValueKind.test(o)) {
                case SINGLE: {
                    if (o instanceof List) {
                        o = ((List)o).get(0);
                    }
                    if (o instanceof UUID) {
                        list.add(o.toString());
                        break;
                    }
                    if (o instanceof Map) {
                        list.add(this.getMapValue((Map)o));
                        break;
                    }
                    list.add(o);
                    break;
                }
                case RANGE: {
                    list.add(Array.get(o, 0));
                    list.add(Array.get(o, 1));
                    break;
                }
                case LIST: {
                    String id = this.createMap((List)o);
                    list.add(id);
                    ids.add(id);
                    break;
                }
                case RANGE_GREATER: {
                    list.add(Array.get(o, 0));
                    break;
                }
                case RANGE_LESSER: {
                    list.add(Array.get(o, 1));
                }
            }
        });
        return list.toArray();
    }

    @Override
    public Map<String, List<Map<String, Object>>> load(DataTableNodeContainer<? extends DataTableDefine> tables, Map<String, Object> valueMap) {
        return this.load(null, tables, valueMap, null);
    }

    @Override
    public Map<String, List<Map<String, Object>>> load(ModelDefine define, DataTableNodeContainer<? extends DataTableDefine> tables, Map<String, Object> valueMap, Map<UUID, Formula> conditionMap) {
        HashMap<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
        DataTableDefine masterTable = tables.getMasterTable();
        List<Map<String, Object>> rows = this.load(masterTable, valueMap);
        map.put(masterTable.getName(), rows);
        DataTableDefine dataTableDefineM = tables.find(masterTable.getName() + "_M");
        if (dataTableDefineM == null) {
            return map;
        }
        List ids = rows.stream().map(o -> o.get("ID")).collect(Collectors.toList());
        if (ids.size() > 0) {
            HashMap<String, Object> detailValueMap = new HashMap<String, Object>();
            if (ids.size() == 1) {
                detailValueMap.put("MASTERID", ids.get(0));
            } else {
                detailValueMap.put("MASTERID", ids);
            }
            map.put(dataTableDefineM.getName(), this.load(dataTableDefineM, detailValueMap));
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public long save(DataTableNodeContainer<? extends DataTableDefine> tables, Map<String, DataUpdate> update, List<Map<String, Object>> updateKeys) {
        DataTableDefine masterTable = tables.getMasterTable();
        DataUpdate masterUpdate = update.get(masterTable.getName());
        if (masterUpdate.getDelete().size() > 0) {
            masterUpdate.getDelete().forEach(row -> {
                if (this.delete(masterTable, (Map<String, Object>)row) != 1) {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datachange"));
                }
            });
        }
        long version = Math.max(System.currentTimeMillis(), masterUpdate.getUpdate().stream().map(o -> Convert.cast(o.get("VER"), Long.TYPE)).max(Long::compare).orElse(0L) + 1L);
        new ListContainerImpl<Map<String, Object>>(masterUpdate.getUpdate()).forEach((i, row) -> {
            Map valueMap = (Map)updateKeys.get((int)i);
            row.put("VER", version);
            if (this.update(masterTable, (Map<String, Object>)row, valueMap) != 1) {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datachange"));
            }
        });
        if (masterUpdate.getInsert().size() > 0) {
            masterUpdate.getInsert().forEach(row -> row.put("VER", version));
            this.insert(masterTable, masterUpdate.getInsert());
        }
        update.forEach((n, v) -> {
            DataTableDefine table = (DataTableDefine)tables.get((String)n);
            if (table != masterTable && (table.getTableType() == DataTableType.DATA || table.getTableType() == DataTableType.REFER)) {
                if (v.getDelete().size() > 0) {
                    List ids = v.getDelete().stream().map(o -> o.get("ID")).collect(Collectors.toList());
                    Map<String, Object> valueMap = Stream.of(ids).collect(Collectors.toMap(o -> "ID", o -> o));
                    this.delete(table, valueMap);
                }
                if (v.getUpdate().size() > 0) {
                    if (table.getFields().find("VER") != null) {
                        v.getUpdate().forEach(o -> o.put("VER", version));
                    }
                    v.getUpdate().forEach(o -> this.update(table, Arrays.asList(o)));
                }
                if (v.getInsert().size() > 0) {
                    if (table.getFields().find("VER") != null) {
                        v.getInsert().forEach(o -> o.put("VER", version));
                    }
                    this.insert(table, v.getInsert());
                }
            }
        });
        return version;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(DataTableNodeContainer<? extends DataTableDefine> tables, List<Map<String, Object>> valueMaps) {
        ArrayList ids = new ArrayList();
        DataTableDefine masterTable = tables.getMasterTable();
        valueMaps.forEach(valueMap -> {
            List<Map<String, Object>> rows = this.load(masterTable, (Map<String, Object>)valueMap);
            if (rows.size() != 1 || this.delete(masterTable, (Map<String, Object>)valueMap) != 1) {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datachange"));
            }
            ids.add(rows.get(0).get("ID"));
        });
        tables.stream().forEach(t -> {
            if (t != masterTable && (t.getTableType() == DataTableType.DATA || t.getTableType() == DataTableType.REFER)) {
                Map<String, Object> tableValueMap = Stream.of(ids).collect(Collectors.toMap(o -> "MASTERID", o -> o));
                this.delete((DataTableDefine)t, tableValueMap);
            }
        });
    }

    @Override
    public List<Map<String, Object>> load(DataTableDefine table, Map<String, Object> valueMap) {
        List<DataFieldDefine> dataFieldList = table.getFields().stream().filter(o -> o.getFieldType() == DataFieldType.DATA).collect(Collectors.toList());
        List<String> dataFieldNames = dataFieldList.stream().map(o -> o.getFieldName()).collect(Collectors.toList());
        return this.load(table, valueMap, dataFieldList, dataFieldNames);
    }

    @Override
    public Long loadVer(DataTableDefine table, Object id) {
        List<Map<String, Object>> result = this.load(table, Utils.makeMap("ID", id), Arrays.asList(table.getFields().get("ID")), Arrays.asList("VER"));
        if (result == null || result.size() == 0) {
            return null;
        }
        return Convert.cast(result.get(0).get("VER"), Long.class);
    }

    private List<Map<String, Object>> load(DataTableDefine table, Map<String, Object> valueMap, List<DataFieldDefine> dataFieldList, List<String> dataFieldNames) {
        String sql = DataSqlBuilder.select(table.getTableName(), dataFieldNames, valueMap.keySet(), valueMap.values().stream().map(o -> ValueKind.test(o)).collect(Collectors.toList()));
        return this.load(table, valueMap, dataFieldList, dataFieldNames, sql);
    }

    @Override
    public List<Map<String, Object>> load(DataTableDefine table, String condition) {
        List<DataFieldDefine> dataFieldList = table.getFields().stream().filter(o -> o.getFieldType() == DataFieldType.DATA).collect(Collectors.toList());
        List<String> dataFieldNames = dataFieldList.stream().map(o -> o.getFieldName()).collect(Collectors.toList());
        String sql = DataSqlBuilder.select(table.getTableName(), dataFieldNames, condition);
        return this.load(table, new HashMap<String, Object>(), dataFieldList, dataFieldNames, sql);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Map<String, Object>> load(DataTableDefine table, Map<String, Object> valueMap, List<DataFieldDefine> dataFieldList, List<String> dataFieldNames, String sql) {
        DataFieldDefine f_ordinal = table.getFields().find("ORDINAL");
        DataFieldDefine f_billcode = table.getTableType() == DataTableType.REFER ? table.getFields().find("BILLCODE") : null;
        ArrayList<String> ids = new ArrayList<String>();
        try {
            Object[] values = this.mapValues(valueMap.values(), ids);
            List list = (List)this.template.query(sql, ps -> {
                for (int i = 0; i < values.length; ++i) {
                    ps.setObject(i + 1, values[i]);
                }
            }, rs -> {
                int i;
                ArrayList<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
                ArrayList<Encrypt> encrypts = new ArrayList<Encrypt>();
                ArrayList<String> ciphertexts = new ArrayList<String>();
                while (rs.next()) {
                    LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
                    for (i = 0; i < dataFieldList.size(); ++i) {
                        DataFieldDefine dataFieldDefine = (DataFieldDefine)dataFieldList.get(i);
                        Object value = dataFieldDefine.getValueType().getValue(rs, i + 1);
                        String fieldName = (String)dataFieldNames.get(i);
                        if (dataFieldDefine.isEncryptedStorage() && value != null) {
                            Encrypt encrypt = new Encrypt(map, fieldName, (String)value);
                            ciphertexts.add((String)value);
                            map.put(fieldName, encrypt);
                            encrypts.add(encrypt);
                            continue;
                        }
                        map.put(fieldName, value);
                    }
                    list.add(map);
                }
                if (f_billcode != null || f_ordinal != null) {
                    Collections.sort(list, (o1, o2) -> {
                        Object v2;
                        Object v1;
                        if (f_billcode != null) {
                            v1 = Convert.cast(o1.get(f_billcode.getFieldName()), String.class);
                            v2 = Convert.cast(o2.get(f_billcode.getFieldName()), String.class);
                            int v = 0;
                            v = v1 == v2 ? 0 : (v1 == null ? -1 : (v2 == null ? 1 : ((String)v1).compareTo((String)v2)));
                            if (v != 0) {
                                return v;
                            }
                        }
                        if (f_ordinal != null) {
                            v1 = o1.get(f_ordinal.getFieldName());
                            v2 = o2.get(f_ordinal.getFieldName());
                            double d1 = Convert.cast(v1, Double.TYPE);
                            double d2 = Convert.cast(v2, Double.TYPE);
                            return Double.compare(d1, d2);
                        }
                        return 0;
                    });
                }
                if (!ciphertexts.isEmpty()) {
                    List<String> plaintexts = this.vaSymmetricEncryptService.doDecrypt(ciphertexts);
                    for (i = 0; i < encrypts.size(); ++i) {
                        Encrypt encrypt = (Encrypt)encrypts.get(i);
                        encrypt.setValue(plaintexts.get(i));
                        encrypt.apply();
                    }
                }
                return list;
            });
            return list;
        }
        finally {
            this.deleteMap(ids);
        }
    }

    private void setNull(PreparedStatement ps, int i, int sqlType) throws SQLException {
        if (sqlType == -9) {
            if (this.isStringNullAsEmpty()) {
                ps.setString(i, "");
            } else {
                ps.setNull(i, 12);
            }
        } else {
            ps.setNull(i, sqlType);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void insert(DataTableDefine table, final List<Map<String, Object>> rows) {
        final List dataFieldList = table.getFields().stream().filter(o -> o.getFieldType() == DataFieldType.DATA).collect(Collectors.toList());
        final List<String> dataFieldNames = dataFieldList.stream().map(o -> o.getFieldName()).collect(Collectors.toList());
        String sql = DataSqlBuilder.insert(table.getTableName(), dataFieldNames);
        this.template.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map row = (Map)rows.get(i);
                for (int j = 0; j < dataFieldList.size(); ++j) {
                    Object value = row.get(dataFieldNames.get(j));
                    if (value instanceof Holder) {
                        value = ((Holder)value).get();
                    }
                    if (value instanceof UUID) {
                        value = value.toString();
                    } else if (value instanceof Map) {
                        value = DataAccessImpl.this.getMapValue((Map)value);
                    } else if (value instanceof Date) {
                        value = new Timestamp(((Date)value).getTime());
                    } else if (value instanceof Boolean) {
                        value = Convert.cast(value, Integer.class);
                    }
                    if (value == null) {
                        DataAccessImpl.this.setNull(ps, j + 1, ((DataFieldDefine)dataFieldList.get(j)).getValueType().getSqlType());
                        continue;
                    }
                    ps.setObject(j + 1, value);
                }
            }

            public int getBatchSize() {
                return rows.size();
            }
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(DataTableDefine table, final List<Map<String, Object>> rows) {
        final List dataFieldList = rows.get(0).keySet().stream().map(o -> table.getFields().get((String)o)).filter(o -> !o.getName().equals("ID") && o.getFieldType() == DataFieldType.DATA).collect(Collectors.toList());
        final List<String> dataFieldNames = dataFieldList.stream().map(o -> o.getFieldName()).collect(Collectors.toList());
        String sql = DataSqlBuilder.update(table.getTableName(), dataFieldNames, Arrays.asList("ID"), Arrays.asList(ValueKind.SINGLE));
        this.template.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Map row = (Map)rows.get(i);
                for (int j = 0; j < dataFieldList.size(); ++j) {
                    Class<Void> value = row.getOrDefault(dataFieldNames.get(j), Void.class);
                    if (value instanceof Holder) {
                        value = ((Holder)((Object)value)).get();
                    }
                    if (value == Void.class) {
                        throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.dataaccessimpl.batchupdatefielddifferent"));
                    }
                    if (value instanceof UUID) {
                        value = value.toString();
                    } else if (value instanceof Map) {
                        value = DataAccessImpl.this.getMapValue((Map)((Object)value));
                    } else if (value instanceof Date) {
                        value = new Timestamp(((Date)((Object)value)).getTime());
                    } else if (value instanceof Boolean) {
                        value = Convert.cast(value, Integer.class);
                    }
                    if (value == null) {
                        DataAccessImpl.this.setNull(ps, j + 1, ((DataFieldDefine)dataFieldList.get(j)).getValueType().getSqlType());
                        continue;
                    }
                    ps.setObject(j + 1, value);
                }
                int offset = dataFieldList.size() + 1;
                Object idValue = row.get("ID");
                if (idValue instanceof UUID) {
                    idValue = idValue.toString();
                } else if (idValue instanceof Map) {
                    idValue = DataAccessImpl.this.getMapValue((Map)idValue);
                }
                ps.setObject(offset, idValue);
            }

            public int getBatchSize() {
                return rows.size();
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public int update(DataTableDefine table, Map<String, Object> row, Map<String, Object> valueMap) {
        List dataFieldList = row.keySet().stream().map(o -> table.getFields().get((String)o)).filter(o -> !o.getName().equals("ID") && o.getFieldType() == DataFieldType.DATA).collect(Collectors.toList());
        List<String> dataFieldNames = dataFieldList.stream().map(o -> o.getFieldName()).collect(Collectors.toList());
        String sql = DataSqlBuilder.update(table.getTableName(), dataFieldNames, valueMap.keySet(), valueMap.values().stream().map(o -> ValueKind.test(o)).collect(Collectors.toList()));
        ArrayList<String> ids = new ArrayList<String>();
        try {
            Object[] values = this.mapValues(valueMap.values(), ids);
            int n = this.template.update(sql, ps -> {
                for (int j = 0; j < dataFieldList.size(); ++j) {
                    Object value = row.get(dataFieldNames.get(j));
                    if (value instanceof Holder) {
                        value = ((Holder)value).get();
                    }
                    if (value instanceof UUID) {
                        value = value.toString();
                    } else if (value instanceof Map) {
                        value = this.getMapValue((Map)value);
                    } else if (value instanceof Date) {
                        value = new Timestamp(((Date)value).getTime());
                    } else if (value instanceof Boolean) {
                        value = Convert.cast(value, Integer.class);
                    }
                    if (value == null) {
                        this.setNull(ps, j + 1, ((DataFieldDefine)dataFieldList.get(j)).getValueType().getSqlType());
                        continue;
                    }
                    ps.setObject(j + 1, value);
                }
                int offset = dataFieldList.size() + 1;
                for (int i = 0; i < values.length; ++i) {
                    ps.setObject(offset + i, values[i]);
                }
            });
            return n;
        }
        finally {
            this.deleteMap(ids);
        }
    }

    private Object getMapValue(Map<String, Object> value) {
        Object v = value.get("id");
        if (v != null) {
            return v.toString();
        }
        return value.get("name");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Transactional(rollbackFor={Exception.class})
    public int delete(DataTableDefine table, Map<String, Object> valueMap) {
        String sql = DataSqlBuilder.delete(table.getTableName(), valueMap.keySet(), valueMap.values().stream().map(o -> ValueKind.test(o)).collect(Collectors.toList()));
        ArrayList<String> ids = new ArrayList<String>();
        try {
            Object[] values = this.mapValues(valueMap.values(), ids);
            int n = this.template.update(sql, ps -> {
                for (int i = 0; i < values.length; ++i) {
                    ps.setObject(i + 1, values[i]);
                }
            });
            return n;
        }
        finally {
            this.deleteMap(ids);
        }
    }
}

