/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.formula.common.exception.ToSqlException
 *  com.jiuqi.va.formula.tosql.ToSqlHandle
 *  com.jiuqi.va.mapper.config.VaMapperConfig
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataException;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.data.LazyLoadState;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.ListUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.formula.common.exception.ToSqlException;
import com.jiuqi.va.formula.tosql.ToSqlHandle;
import com.jiuqi.va.mapper.config.VaMapperConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class DataTableImpl
implements DataTable {
    private static final Logger logger = LoggerFactory.getLogger(DataTableImpl.class);
    final DataImpl data;
    private final DataTableDefineImpl define;
    private final LinkedHashMap<String, DataFieldImpl> fieldMap = new LinkedHashMap();
    private final List<DataRowImpl> rowList = new ArrayList<DataRowImpl>();
    private final List<DataRowImpl> deleteRowList = new ArrayList<DataRowImpl>();
    public final DataFieldImpl F_ID;
    public final DataFieldImpl F_VERSION;
    public final DataFieldImpl F_MASTERID;
    public final DataFieldImpl F_GROUPID;
    public final DataFieldImpl F_ORDINAL;
    protected LazyLoadState status;
    private NamedContainerImpl<DataFieldImpl> fields;

    public LazyLoadState getStatus() {
        return this.status;
    }

    protected void setStatus(LazyLoadState status) {
        this.status = status;
    }

    DataTableImpl(DataImpl data, DataTableDefineImpl define) {
        this.data = data;
        this.define = define;
        this.status = LazyLoadState.UNLOAD;
        ((NamedContainerImpl)this.define.getFields()).forEachIndex((i, o) -> {
            DataFieldDefineImpl fieldDefine;
            Map<String, String> shareField = o.getShareFieldMapping();
            String unitcodeField = null;
            if (shareField != null) {
                unitcodeField = shareField.get("UNITCODE");
            }
            if (Utils.isNotEmpty(o.getUnitField()) && unitcodeField == null) {
                unitcodeField = o.getUnitField();
            }
            if (Utils.isNotEmpty(unitcodeField) && !this.fieldMap.containsKey(unitcodeField) && (fieldDefine = (DataFieldDefineImpl)((NamedContainerImpl)this.define.getFields()).find(unitcodeField)) != null) {
                DataFieldImpl field = new DataFieldImpl(fieldDefine, this.fieldMap.size());
                this.fieldMap.put(field.getName(), field);
            }
        });
        ((NamedContainerImpl)this.define.getFields()).forEachIndex((i, o) -> {
            if (!this.fieldMap.containsKey(o.getName())) {
                DataFieldImpl field = new DataFieldImpl((DataFieldDefineImpl)o, this.fieldMap.size());
                this.fieldMap.put(field.getName(), field);
            }
        });
        this.F_ID = this.fieldMap.get("ID");
        this.F_VERSION = this.fieldMap.get("VER");
        this.F_MASTERID = this.fieldMap.get("MASTERID");
        this.F_GROUPID = this.fieldMap.get("GROUPID");
        this.F_ORDINAL = this.fieldMap.get("ORDINAL");
    }

    void reset() {
        this.rowList.forEach(o -> this.unuse((DataRowImpl)o));
        this.deleteRowList.forEach(o -> this.unuse((DataRowImpl)o));
        this.rowList.clear();
        this.deleteRowList.clear();
    }

    @Override
    public UUID getId() {
        return this.define.getId();
    }

    @Override
    public String getName() {
        return this.define.getName();
    }

    @Override
    public String getTitle() {
        return this.define.getTitle();
    }

    @Override
    public UUID getParentId() {
        return this.define.getParentId();
    }

    @Override
    public DataTableType getTableType() {
        return this.define.getTableType();
    }

    @Override
    public DataImpl getData() {
        return this.data;
    }

    @Override
    public DataTableDefineImpl getDefine() {
        return this.define;
    }

    public NamedContainer<DataFieldImpl> getFields() {
        if (this.fields == null) {
            this.fields = new NamedContainerImpl<DataFieldImpl>(this.fieldMap);
        }
        return this.fields;
    }

    public ListContainer<DataRowImpl> getRows() {
        this.getLazyRowsData();
        return ListContainerImpl.create(this.rowList);
    }

    public List<DataRowImpl> getRowList() {
        this.getLazyRowsData();
        return this.rowList;
    }

    DataRowImpl createRow() {
        return this.createRow(new Object[this.fieldMap.size()]);
    }

    private DataRowImpl createRow(Object[] values) {
        return new DataRowImpl(this, values);
    }

    private Object[] mapValues(List<Object> values, List<Object> fieldNames) {
        Integer idIndex = fieldNames.indexOf("ID");
        Object[] result = new Object[this.fieldMap.size()];
        Object id = values.get(idIndex);
        if (id == null) {
            id = UUID.randomUUID();
        }
        result[this.F_ID.getIndex()] = this.F_ID.getDefine().getValueType().cast(id);
        if (((DataTableNodeContainerImpl)this.data.getTables()).getMasterTable() != this) {
            Integer masteridIndex = fieldNames.indexOf("MASTERID");
            Integer groupidIndex = fieldNames.indexOf("GROUPID");
            Object masterId = values.get(masteridIndex);
            ListContainer<DataRowImpl> rows = this.data.getMasterTable().getRows();
            if (this.getTableType() == DataTableType.DATA && masterId != null && rows.size() == 1 && !masterId.toString().equals(String.valueOf(rows.get(0).getId()))) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.subtablemasterid"));
            }
            if (masterId == null && this.data.getConditionMap().get(this.getId()) == null) {
                if (rows.size() != 1) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.addrowmasteridrequired"));
                }
                masterId = this.data.getMasterTable().getRows().get(0).getId();
            }
            result[this.F_MASTERID.getIndex()] = this.F_MASTERID.getDefine().getValueType().cast(masterId);
            if (((DataTableNodeContainerImpl)this.data.getTables()).get(this.define.getParentId()) != ((DataTableNodeContainerImpl)this.data.getTables()).getMasterTable()) {
                Object groupId = values.get(groupidIndex);
                if (groupId == null && this.data.getConditionMap().get(this.getId()) == null) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.addrowgroupidrequired"));
                }
                result[this.F_GROUPID.getIndex()] = this.F_GROUPID.getDefine().getValueType().cast(groupId);
            } else if (this.getDefine().getName().endsWith("_M")) {
                Object groupId = values.get(groupidIndex);
                result[this.F_GROUPID.getIndex()] = this.F_GROUPID.getDefine().getValueType().cast(groupId);
            }
        }
        return result;
    }

    private Object[] mapValues(Map<String, Object> values) {
        Object[] result = new Object[this.fieldMap.size()];
        Object id = values.get("ID");
        if (id == null) {
            id = UUID.randomUUID();
        }
        result[this.F_ID.getIndex()] = this.F_ID.getDefine().getValueType().cast(id);
        if (((DataTableNodeContainerImpl)this.data.getTables()).getMasterTable() != this) {
            Object masterId = values.get("MASTERID");
            ListContainer<DataRowImpl> rows = this.data.getMasterTable().getRows();
            if (this.getTableType() == DataTableType.DATA && masterId != null && rows.size() == 1 && !masterId.toString().equals(String.valueOf(rows.get(0).getId()))) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.subtablemasterid"));
            }
            if (masterId == null && this.data.getConditionMap().get(this.getId()) == null) {
                if (rows.size() != 1) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.addrowmasteridrequired"));
                }
                masterId = this.data.getMasterTable().getRows().get(0).getId();
            }
            result[this.F_MASTERID.getIndex()] = this.F_MASTERID.getDefine().getValueType().cast(masterId);
            if (((DataTableNodeContainerImpl)this.data.getTables()).get(this.define.getParentId()) != ((DataTableNodeContainerImpl)this.data.getTables()).getMasterTable()) {
                Object groupId = values.get("GROUPID");
                if (groupId == null && this.data.getConditionMap().get(this.getId()) == null) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.addrowgroupidrequired"));
                }
                result[this.F_GROUPID.getIndex()] = this.F_GROUPID.getDefine().getValueType().cast(groupId);
            } else if (this.getDefine().getName().endsWith("_M")) {
                Object groupId = values.get("GROUPID");
                result[this.F_GROUPID.getIndex()] = this.F_GROUPID.getDefine().getValueType().cast(groupId);
            }
        }
        return result;
    }

    private Object[] mapIncValues(Map<Integer, Object> values) {
        Object[] result = new Object[this.fieldMap.size()];
        Object id = values.get(this.F_ID.getIndex() + "");
        if (id == null) {
            id = UUID.randomUUID();
        }
        result[this.F_ID.getIndex()] = this.F_ID.getDefine().getValueType().cast(id);
        if (((DataTableNodeContainerImpl)this.data.getTables()).getMasterTable() != this) {
            Object masterId = values.get(this.F_MASTERID.getIndex() + "");
            ListContainer<DataRowImpl> rows = this.data.getMasterTable().getRows();
            if (this.getTableType() == DataTableType.DATA && masterId != null && rows.size() == 1 && !masterId.toString().equals(String.valueOf(rows.get(0).getId()))) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.subtablemasterid"));
            }
            if (masterId == null && this.data.getConditionMap().get(this.getId()) == null) {
                if (rows.size() != 1) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.addrowmasteridrequired"));
                }
                masterId = this.data.getMasterTable().getRows().get(0).getId();
            }
            result[this.F_MASTERID.getIndex()] = this.F_MASTERID.getDefine().getValueType().cast(masterId);
            if (((DataTableNodeContainerImpl)this.data.getTables()).get(this.define.getParentId()) != ((DataTableNodeContainerImpl)this.data.getTables()).getMasterTable()) {
                Object groupId = values.get(this.F_GROUPID.getIndex() + "");
                if (groupId == null && this.data.getConditionMap().get(this.getId()) == null) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.addrowgroupidrequired"));
                }
                result[this.F_GROUPID.getIndex()] = this.F_GROUPID.getDefine().getValueType().cast(groupId);
            } else if (this.getDefine().getName().endsWith("_M")) {
                Object groupId = values.get(this.F_GROUPID.getIndex() + "");
                result[this.F_GROUPID.getIndex()] = this.F_GROUPID.getDefine().getValueType().cast(groupId);
            }
        }
        return result;
    }

    @Override
    public DataRowImpl insertRow(int index) {
        return this.insertRow(index, 1, Collections.emptyMap()).get(0);
    }

    @Override
    public DataRow insertRow(int index, Map<String, Object> values) {
        return this.insertRow(index, 1, values).get(0);
    }

    public List<DataRowImpl> insertRow(int index, int count) {
        return this.insertRow(index, count, Collections.emptyMap());
    }

    public List<DataRowImpl> insertRowWithCheck(int index, int count, Map<String, Object> values) {
        this.getLazyRowsData();
        return this.insertRow(index, count, values, true);
    }

    public List<DataRowImpl> insertRow(int index, int count, Map<String, Object> values) {
        Map tableList;
        this.getLazyRowsData();
        ModelContextImpl context = (ModelContextImpl)this.getData().getModel().getContext();
        Object detailEnableFilter = context.getContextValue("X--detailFilterDataId");
        if (detailEnableFilter != null && !(tableList = (Map)detailEnableFilter).containsKey(this.getName())) {
            logger.debug("\u5b50\u8868\u8fc7\u6ee4\uff1a\u63d2\u5165\u5b50\u8868\u884c\u5931\u8d25\uff1a{}\uff0c\u5f53\u524d\u5b50\u8868\u4e0d\u652f\u6301\u63d2\u5165\u64cd\u4f5c", (Object)this.getName());
            return this.rowList;
        }
        return this.insertRow(index, count, values, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<DataRowImpl> insertIncRow(int index, int count, Map<Integer, Object> values) {
        this.getLazyRowsData();
        DataRowImpl[] rows = new DataRowImpl[count];
        for (int i = 0; i < count; ++i) {
            DataRowImpl row = this.createRow(this.mapIncValues(values));
            switch (this.data.getState()) {
                case NONE: {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.currstatusunableinsert"));
                }
                case BROWSE: {
                    row.setState(DataRowState.NORMAL);
                    break;
                }
                default: {
                    row.setState(DataRowState.APPENDED);
                }
            }
            rows[i] = row;
        }
        List<DataRowImpl> result = Arrays.asList(rows);
        this.rowList.addAll(index, result);
        result.forEach(o -> {
            if (o.getState() == DataRowState.APPENDED) {
                o.setState(DataRowState.INITIAL);
            }
        });
        try {
            result.forEach(o -> {
                o.setIncData(values);
                this.data.afterAddRow(this, (DataRow)o);
            });
        }
        finally {
            result.forEach(o -> {
                if (o.getState() == DataRowState.INITIAL) {
                    o.setState(DataRowState.APPENDED);
                }
            });
        }
        return result;
    }

    public List<DataRowImpl> insertRow(int index, int count, List<Object> values, List<Object> fieldNames) {
        return this.insertRow(index, count, values, false, fieldNames);
    }

    public void insertRow(int index, int count, Object[] values) {
        int len = values.length;
        Boolean unset = (Boolean)values[len - 2];
        DataRowState state = (DataRowState)((Object)values[len - 1]);
        Object[] newValues = new Object[len - 2];
        System.arraycopy(values, 0, newValues, 0, newValues.length);
        DataRowImpl row = new DataRowImpl(this, newValues, state);
        row.unset = unset;
        row.isCacheInit = true;
        row.setIndex(index);
        if (state == DataRowState.DELETED) {
            this.deleteRowList.add(row);
        } else {
            this.rowList.add(row);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<DataRowImpl> insertRow(int index, int count, List<Object> values, boolean withCheck, List<Object> fieldNames) {
        this.getLazyRowsData();
        DataRowImpl[] rows = new DataRowImpl[count];
        for (int i = 0; i < count; ++i) {
            DataRowImpl row = this.createRow(this.mapValues(values, fieldNames));
            switch (this.data.getState()) {
                case NONE: {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.currstatusunableinsert"));
                }
                case BROWSE: {
                    row.setState(DataRowState.NORMAL);
                    break;
                }
                default: {
                    row.setState(DataRowState.APPENDED);
                }
            }
            rows[i] = row;
        }
        List<DataRowImpl> result = Arrays.asList(rows);
        this.rowList.addAll(index, result);
        result.forEach(o -> {
            if (o.getState() == DataRowState.APPENDED) {
                o.setState(DataRowState.INITIAL);
            }
        });
        try {
            result.forEach(o -> {
                if (withCheck) {
                    o.setDataWithCheck(values, fieldNames);
                } else {
                    o.setData(values, fieldNames);
                }
                this.data.afterAddRow(this, (DataRow)o);
            });
        }
        finally {
            result.forEach(o -> {
                if (o.getState() == DataRowState.INITIAL) {
                    o.setState(DataRowState.APPENDED);
                }
            });
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<DataRowImpl> insertRow(int index, int count, Map<String, Object> values, boolean withCheck) {
        DataRowImpl[] rows = new DataRowImpl[count];
        for (int i = 0; i < count; ++i) {
            DataRowImpl row = this.createRow(this.mapValues(values));
            switch (this.data.getState()) {
                case NONE: {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.currstatusunableinsert"));
                }
                case BROWSE: {
                    row.setState(DataRowState.NORMAL);
                    break;
                }
                default: {
                    row.setState(DataRowState.APPENDED);
                }
            }
            rows[i] = row;
        }
        List<DataRowImpl> result = Arrays.asList(rows);
        this.rowList.addAll(index, result);
        result.forEach(o -> {
            if (o.getState() == DataRowState.APPENDED) {
                o.setState(DataRowState.INITIAL);
            }
        });
        try {
            result.forEach(o -> {
                if (withCheck) {
                    o.setDataWithCheck(values);
                } else {
                    o.setData(values);
                }
                this.data.afterAddRow(this, (DataRow)o);
            });
        }
        finally {
            result.forEach(o -> {
                if (o.getState() == DataRowState.INITIAL) {
                    o.setState(DataRowState.APPENDED);
                }
            });
        }
        return result;
    }

    @Override
    public DataRowImpl appendRow() {
        this.getLazyRowsData();
        return this.insertRow(this.rowList.size(), 1, Collections.emptyMap()).get(0);
    }

    public DataRowImpl appendIncRow(Map<Integer, Object> rowdata) {
        this.getLazyRowsData();
        return this.insertIncRow(this.rowList.size(), 1, rowdata).get(0);
    }

    @Override
    public DataRowImpl appendRow(Map<String, Object> values) {
        this.getLazyRowsData();
        return this.insertRow(this.rowList.size(), 1, values).get(0);
    }

    @Override
    public DataRowImpl appendRow(List<Object> values, List<Object> fieldNames) {
        this.getLazyRowsData();
        return this.insertRow(this.rowList.size(), 1, values, fieldNames).get(0);
    }

    @Override
    public DataRowImpl appendRowWithCheck(Map<String, Object> values) {
        this.getLazyRowsData();
        return this.insertRowWithCheck(this.rowList.size(), 1, values).get(0);
    }

    @Override
    public List<? extends DataRow> appendRow(int count) {
        this.getLazyRowsData();
        return this.insertRow(this.rowList.size(), count, Collections.emptyMap());
    }

    @Override
    public List<? extends DataRow> appendRow(int count, Map<String, Object> values) {
        this.getLazyRowsData();
        return this.insertRow(this.rowList.size(), count, values);
    }

    @Override
    public void deleteRow(int index) {
        this.deleteRow(index, 1);
    }

    @Override
    public void deleteRow(int index, int count) {
        Map tableList;
        ModelContextImpl context = (ModelContextImpl)this.data.getModel().getContext();
        Object detailEnableFilter = context.getContextValue("X--detailFilterDataId");
        if (detailEnableFilter != null && !(tableList = (Map)detailEnableFilter).containsKey(this.getName())) {
            logger.debug("\u5b50\u8868\u8fc7\u6ee4\uff1a\u5220\u9664\u5b50\u8868\u884c\u5931\u8d25\uff1a{}\uff0c\u5f53\u524d\u5b50\u8868\u4e0d\u652f\u6301\u5220\u9664\u64cd\u4f5c", (Object)this.getName());
            return;
        }
        this.getLazyRowsData();
        for (int i = count - 1; i >= 0; --i) {
            DataRowImpl o = this.rowList.remove(index + i);
            switch (o.getState()) {
                case APPENDED: {
                    o.setState(DataRowState.UNUSED);
                    break;
                }
                case MODIFIED: {
                    DataRowImpl originRow = o.getOriginRow();
                    o.applyChange();
                    o.setState(DataRowState.UNUSED);
                    originRow.setState(DataRowState.DELETED);
                    this.deleteRowList.add(originRow);
                    break;
                }
                case NORMAL: {
                    if (this.data.getState() == DataState.BROWSE) {
                        o.setState(DataRowState.UNUSED);
                        break;
                    }
                    o.setState(DataRowState.DELETED);
                    this.deleteRowList.add(o);
                    break;
                }
                default: {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.statuserror"));
                }
            }
            Object id = o.getId();
            ((DataTableNodeContainerImpl)this.data.getTables()).getDetailTables(this.getId()).stream().forEach(d -> {
                int j = ((DataTableNodeContainerImpl)this.data.getTables()).getMasterTable() == this ? d.F_MASTERID.getIndex() : d.F_GROUPID.getIndex();
                d.getRows().forEachReverse((k, subRow) -> {
                    if (subRow.getValue(j).equals(id)) {
                        d.deleteRow((int)k);
                    }
                });
            });
            DataTableImpl multiTable = (DataTableImpl)((DataTableNodeContainerImpl)this.data.getTables()).find(this.getName() + "_M");
            if (multiTable != null) {
                int j = multiTable.F_GROUPID.getIndex();
                multiTable.getRows().forEachReverse((k, row) -> {
                    if (row.getValue(j).equals(id)) {
                        multiTable.deleteRow((int)k);
                    }
                });
            }
            this.data.afterDelRow(this, o);
        }
    }

    @Override
    public void deleteRowById(Object id) {
        this.getLazyRowsData();
        for (int i = 0; i < this.rowList.size(); ++i) {
            if (!id.equals(this.rowList.get(i).getId())) continue;
            this.deleteRow(i, 1);
            return;
        }
        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.deletenotexistrow"));
    }

    @Override
    public void deleteRow(DataRow row) {
        this.getLazyRowsData();
        int index = this.rowList.indexOf(row);
        if (index < 0) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.deletenotexistrow"));
        }
        this.deleteRow(index, 1);
    }

    @Override
    public void removeRow(int index) {
        this.removeRow(index, 1);
    }

    @Override
    public void removeRow(int index, int count) {
        this.getLazyRowsData();
        List<DataRowImpl> subList = this.rowList.subList(index, index + count);
        ModelContextImpl context = (ModelContextImpl)this.data.getModel().getContext();
        Object detailEnableFilter = context.getContextValue("X--detailFilterDataId");
        if (detailEnableFilter != null) {
            Map tableList = (Map)detailEnableFilter;
            if (tableList.containsKey(this.getName())) {
                Map detailFilterDataId = (Map)context.getContextValue("X--detailFilterDataId");
                Map list = (Map)detailFilterDataId.get(this.getName());
                ArrayList<DataRowImpl> newSubList = new ArrayList<DataRowImpl>();
                for (DataRowImpl dataRow : subList) {
                    if (list.containsKey(dataRow.getId().toString())) {
                        newSubList.add(dataRow);
                        continue;
                    }
                    logger.debug("\u5b50\u8868\u8fc7\u6ee4\uff1a\u5220\u9664\u5b50\u8868\u884c\u5931\u8d25{}\uff1a{}", (Object)this.getName(), dataRow.getId());
                }
                subList = newSubList;
            } else {
                logger.debug("\u5b50\u8868\u8fc7\u6ee4\uff1a\u5220\u9664\u5b50\u8868\u884c\u5931\u8d25\uff1a{}\uff0c\u5f53\u524d\u5b50\u8868\u4e0d\u652f\u6301\u5220\u9664\u64cd\u4f5c", (Object)this.getName());
                return;
            }
        }
        subList.forEach(row -> {
            this.unuse((DataRowImpl)row);
            Object id = row.getId();
            ((DataTableNodeContainerImpl)this.data.getTables()).getDetailTables(this.getId()).stream().forEach(o -> {
                int j = ((DataTableNodeContainerImpl)this.data.getTables()).getMasterTable() == this ? o.F_MASTERID.getIndex() : o.F_GROUPID.getIndex();
                o.getRows().forEachReverse((i, subRow) -> {
                    if (subRow.getValue(j).equals(id)) {
                        o.removeRow((int)i);
                    }
                });
            });
        });
        subList.clear();
    }

    @Override
    public void removeRowById(Object id) {
        this.getLazyRowsData();
        for (int i = 0; i < this.rowList.size(); ++i) {
            if (!id.equals(this.rowList.get(i).getId())) continue;
            this.removeRow(i, 1);
            return;
        }
        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.removenotexistrow"));
    }

    @Override
    public void removeRow(DataRow row) {
        this.getLazyRowsData();
        int index = this.rowList.indexOf(row);
        if (index < 0) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.removenotexistrow"));
        }
        this.removeRow(index, 1);
    }

    private void unuse(DataRowImpl row) {
        row.applyChange();
        row.setState(DataRowState.UNUSED);
    }

    public Stream<DataRowImpl> getDeletedRows() {
        return this.deleteRowList.stream();
    }

    void applyChange() {
        this.rowList.forEach(o -> o.applyChange());
        this.deleteRowList.forEach(o -> o.setState(DataRowState.UNUSED));
        this.deleteRowList.clear();
    }

    void cancelChange() {
        this.rowList.forEach(o -> o.cancelChange());
        this.deleteRowList.forEach(o -> o.setState(DataRowState.NORMAL));
        this.rowList.addAll(this.deleteRowList);
        this.deleteRowList.clear();
    }

    @Override
    public List<Map<String, Object>> getInsertData() {
        return this.rowList.stream().filter(o -> o.getState() == DataRowState.APPENDED).map(o -> o.getData(false)).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getDeleteData() {
        return this.deleteRowList.stream().map(o -> {
            if (this.F_VERSION == null || o.getValue(this.F_VERSION.getIndex()) == null) {
                return Stream.of(o).collect(Collectors.toMap(r -> "ID", r -> r.getId()));
            }
            return Stream.of(this.F_ID, this.F_VERSION).collect(Collectors.toMap(r -> r.getName(), r -> o.getValue(r.getIndex())));
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getUpdateData() {
        return this.rowList.stream().filter(o -> o.getState() == DataRowState.MODIFIED).map(o -> o.getUpdateData()).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getRowsData() {
        this.getLazyRowsData();
        List<Map<String, Object>> list = this.rowList.stream().map(o -> o.getData()).collect(Collectors.toList());
        if (this.getTableType() == DataTableType.REFER) {
            Map<String, Object> map;
            DataRowImpl row;
            int j;
            for (j = 0; j < this.rowList.size(); ++j) {
                row = this.rowList.get(j);
                map = list.get(j);
                map.put("$STATE", row.getState().name());
            }
            for (j = 0; j < this.deleteRowList.size(); ++j) {
                row = this.deleteRowList.get(j);
                map = new HashMap<String, Object>();
                map.put("$STATE", DataRowState.DELETED.name());
                for (int i = 0; i < row.values.length; ++i) {
                    map.put(this.getFields().get(i).getName(), row.getString(i));
                }
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getFilterRowsData() {
        this.getLazyRowsData();
        List<Map<String, Object>> list = this.getTableType() == DataTableType.DATA && this.getParentId() != null ? this.getDetailTableData() : this.rowList.stream().map(DataRowImpl::getData).collect(Collectors.toList());
        if (this.getTableType() == DataTableType.REFER) {
            Map<String, Object> map;
            for (int j = 0; j < this.rowList.size(); ++j) {
                DataRowImpl row = this.rowList.get(j);
                map = list.get(j);
                map.put("$STATE", row.getState().name());
            }
            for (DataRowImpl row : this.deleteRowList) {
                map = new HashMap<String, Object>();
                map.put("$STATE", DataRowState.DELETED.name());
                for (int i = 0; i < row.values.length; ++i) {
                    map.put(this.getFields().get(i).getName(), row.getString(i));
                }
                list.add(map);
            }
        }
        return list;
    }

    private List<Map<String, Object>> getDetailTableData() {
        List<Map<String, Object>> list;
        ModelImpl model = (ModelImpl)this.getData().getModel();
        ModelContextImpl context = (ModelContextImpl)model.getContext();
        Object tableDataIds = context.getContextValue("X--detailFilterDataId");
        if (tableDataIds == null || !((Map)tableDataIds).containsKey(this.getName())) {
            RulerDefineImpl ruler = (RulerDefineImpl)model.getPlugins().get("ruler").getDefine();
            boolean enableFilter = this.isEnableFilter(ruler, model);
            list = enableFilter ? this.getFilterDetailTableData(ruler, context, model, tableDataIds) : this.rowList.stream().map(DataRowImpl::getData).collect(Collectors.toList());
        } else {
            Map tableList = (Map)tableDataIds;
            if (tableList.containsKey(this.getName())) {
                Map ids = (Map)tableList.get(this.getName());
                list = new ArrayList<Map<String, Object>>();
                LinkedHashMap<String, Integer> newIds = new LinkedHashMap<String, Integer>();
                tableList.put(this.getName(), newIds);
                for (DataRowImpl dataRow : this.rowList) {
                    if (DataRowState.APPENDED.equals((Object)dataRow.getState())) {
                        list.add(dataRow.getData());
                        newIds.put(dataRow.getId().toString(), 1);
                        ids.remove(dataRow.getId().toString());
                        continue;
                    }
                    if (!ids.containsKey(dataRow.getId().toString())) continue;
                    list.add(dataRow.getData());
                    newIds.put(dataRow.getId().toString(), 0);
                    ids.remove(dataRow.getId().toString());
                }
                for (String id : ids.keySet()) {
                    if ((Integer)ids.get(id) != 0 && (Integer)ids.get(id) != 2) continue;
                    newIds.put(id, 2);
                }
            } else {
                list = this.rowList.stream().map(DataRowImpl::getData).collect(Collectors.toList());
            }
        }
        return list;
    }

    private List<Map<String, Object>> getFilterDetailTableData(RulerDefineImpl ruler, ModelContextImpl context, ModelImpl model, Object tableDataIds) {
        List<Map<String, Object>> list;
        FormulaImpl filterConditionFormula = ruler.getFormulas().stream().filter(o -> o.isUsed() && o.getObjectId().equals(this.getId()) && "filterCondition".equals(o.getPropertyType())).findFirst().orElse(null);
        LinkedHashMap<String, Integer> ordinalIds = new LinkedHashMap<String, Integer>();
        HashMap<String, LinkedHashMap<String, Integer>> filterDataIdMap = tableDataIds == null ? new HashMap<String, LinkedHashMap<String, Integer>>() : (HashMap<String, LinkedHashMap<String, Integer>>)tableDataIds;
        context.setContextValue("X--detailFilterDataId", filterDataIdMap);
        filterDataIdMap.put(this.getName(), ordinalIds);
        if (filterConditionFormula != null) {
            String propTable = filterConditionFormula.getPropTable();
            if (this.getName().equals(propTable)) {
                list = new ArrayList();
                for (DataRowImpl dataRow : this.rowList) {
                    boolean evaluate;
                    if (dataRow.getState() == DataRowState.APPENDED) {
                        ordinalIds.put(String.valueOf(dataRow.getId()), 1);
                        continue;
                    }
                    Map<String, DataRow> rowMap = Stream.of(dataRow).collect(Collectors.toMap(k -> this.getName(), v -> v));
                    FormulaUtils.adjustFormulaRows(model.getPlugins().get(Data.class), rowMap);
                    try {
                        evaluate = Boolean.TRUE.equals(Convert.cast(FormulaUtils.evaluate(model, filterConditionFormula.getCompiledExpression(), rowMap), Boolean.TYPE));
                    }
                    catch (SyntaxException e) {
                        logger.error(e.getMessage(), e);
                        throw new ModelException("\u516c\u5f0f\u6267\u884c\u5f02\u5e38", e);
                    }
                    if (!evaluate) continue;
                    ordinalIds.put(String.valueOf(dataRow.getId()), 0);
                    list.add(dataRow.getData());
                }
            } else {
                boolean filterCondition;
                try {
                    filterCondition = Boolean.TRUE.equals(Convert.cast(FormulaUtils.evaluate(filterConditionFormula.getExpression(), model), Boolean.class));
                }
                catch (Exception e) {
                    filterCondition = false;
                    logger.error(e.getMessage(), e);
                }
                if (filterCondition) {
                    list = this.rowList.stream().map(DataRowImpl::getData).collect(Collectors.toList());
                    for (DataRowImpl dataRow : this.rowList) {
                        if (dataRow.getState() == DataRowState.APPENDED) {
                            ordinalIds.put(String.valueOf(dataRow.getId()), 1);
                            continue;
                        }
                        ordinalIds.put(String.valueOf(dataRow.getId()), 0);
                    }
                } else {
                    list = new ArrayList();
                }
            }
        } else if (this.define.isFilterCondition()) {
            list = this.rowList.stream().map(DataRowImpl::getData).collect(Collectors.toList());
            for (DataRowImpl dataRow : this.rowList) {
                if (dataRow.getState() == DataRowState.APPENDED) {
                    ordinalIds.put(String.valueOf(dataRow.getId()), 1);
                    continue;
                }
                ordinalIds.put(String.valueOf(dataRow.getId()), 0);
            }
        } else {
            list = new ArrayList<Map<String, Object>>();
        }
        return list;
    }

    private boolean isEnableFilter(RulerDefineImpl ruler, ModelImpl model) {
        FormulaImpl enableFilterFormula = ruler.getFormulas().stream().filter(o -> "enableFilter".equals(o.getPropertyType()) && o.isUsed() && o.getObjectId().equals(this.getId())).findFirst().orElse(null);
        if (enableFilterFormula != null) {
            try {
                return Boolean.TRUE.equals(Convert.cast(FormulaUtils.evaluate(enableFilterFormula.getExpression(), model), Boolean.class));
            }
            catch (Exception e) {
                logger.error("\u5b50\u8868\u8fc7\u6ee4\u516c\u5f0f\u7ed3\u679c\u8f6c\u5316\u5f02\u5e38{}", (Object)e.getMessage(), (Object)e);
            }
        }
        return this.define.isEnableFilter();
    }

    public List<Map<String, Object>> getRowsData(boolean viewData) {
        this.getLazyRowsData();
        List<Map<String, Object>> list = this.rowList.stream().map(o -> o.getData(viewData)).collect(Collectors.toList());
        if (this.getTableType() == DataTableType.REFER) {
            Map<String, Object> map;
            DataRowImpl row;
            int j;
            for (j = 0; j < this.rowList.size(); ++j) {
                row = this.rowList.get(j);
                map = list.get(j);
                map.put("$STATE", row.getState().name());
            }
            for (j = 0; j < this.deleteRowList.size(); ++j) {
                row = this.deleteRowList.get(j);
                map = new HashMap<String, Object>();
                map.put("$STATE", DataRowState.DELETED.name());
                for (int i = 0; i < row.values.length; ++i) {
                    map.put(this.getFields().get(i).getName(), row.getString(i));
                }
                list.add(map);
            }
        }
        return list;
    }

    @Override
    public void updateRows(List<Map<String, Object>> rowsData) {
        this.getLazyRowsData();
        Map map = this.rowList.stream().collect(Collectors.toMap(DataRowImpl::getId, Function.identity()));
        rowsData.stream().forEach(o -> {
            Object id = o.get(this.F_ID.getName());
            if (id == null) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.missingidfield"));
            }
            id = this.F_ID.getDefine().getValueType().cast(id);
            DataRowImpl row = (DataRowImpl)map.get(id);
            if (row == null) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.rownotfound") + id);
            }
            row.setData((Map<String, Object>)o);
        });
    }

    @Override
    public void insertRows(List<Map<String, Object>> rowsData) {
        this.getLazyRowsData();
        Map map = this.rowList.stream().collect(Collectors.toMap(DataRowImpl::getId, Function.identity()));
        rowsData.stream().forEach(o -> {
            Object id = o.get(this.F_ID.getName());
            if (id == null) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.missingidfield"));
            }
            id = this.F_ID.getDefine().getValueType().cast(id);
            DataRow row = (DataRowImpl)map.get(id);
            if (row != null) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.duplicaterow") + id);
            }
            row = this.appendRow((Map)o);
            map.put(id, row);
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public List<DataRowImpl> getLazyRowsData() {
        if (this.getData().getState().equals(DataState.NEW)) return this.rowList;
        if (this.getData().getState().equals(DataState.NONE)) {
            return this.rowList;
        }
        if (!LazyLoadState.UNLOAD.equals((Object)this.status)) return this.rowList;
        DataTableImpl dataTableImpl = this;
        synchronized (dataTableImpl) {
            if (!LazyLoadState.UNLOAD.equals((Object)this.status)) {
                if (!LazyLoadState.LOADING.equals((Object)this.status)) return this.rowList;
                throw new DataException("\u7981\u6b62\u5f02\u6b65\u52a0\u8f7d\u5b50\u8868");
            }
            this.getData().setListening(false);
            try {
                List rows;
                List<Map<String, Object>> load;
                block20: {
                    DataTableImpl masterTable;
                    block19: {
                        this.setStatus(LazyLoadState.LOADING);
                        masterTable = this.getData().getMasterTable();
                        if (masterTable.getRows().size() == 0) {
                            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.dataaccessimpl.datadelete"));
                        }
                        Map<UUID, Formula> conditionMap = this.getData().getConditionMap();
                        load = null;
                        if (conditionMap.size() == 0) break block19;
                        ModelDataContext context = new ModelDataContext(this.getData().getModel().getDefine());
                        context.put(masterTable.getName(), masterTable.getRows().get(0));
                        Formula formula = conditionMap.get(this.getDefine().getId());
                        if (formula != null) {
                            String condition;
                            IDatabase dataBase = DatabaseManager.getInstance().findDatabaseByName(Utils.convertBiDbType(VaMapperConfig.getDbType()));
                            SQLInfoDescr info = new SQLInfoDescr(dataBase, true);
                            try {
                                condition = ToSqlHandle.toSQL((IContext)context, (IASTNode)((IASTNode)formula.getCompiledExpression()), (Object)info);
                            }
                            catch (ToSqlException e) {
                                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.loadsubtablefailed"), e);
                            }
                            load = this.getData().lazyLoadReferData(this.getDefine(), condition);
                            break block20;
                        } else {
                            HashMap<String, Object> value = new HashMap<String, Object>();
                            value.put("MASTERID", masterTable.getRows().get(0).getString("ID"));
                            load = this.getData().lazyLoadData(this.getDefine(), value);
                        }
                        break block20;
                    }
                    HashMap<String, Object> value = new HashMap<String, Object>();
                    value.put("MASTERID", masterTable.getRows().get(0).getString("ID"));
                    load = this.getData().lazyLoadData(this.getDefine(), value);
                }
                HashMap<String, List<Map<String, Object>>> data = new HashMap<String, List<Map<String, Object>>>();
                data.put(this.getDefine().getName(), load);
                List<Map<String, String>> sortFields = this.define.getSortFields();
                if (sortFields != null && sortFields.size() != 0 && (rows = (List)data.get(this.define.getName())) != null && rows.size() != 0) {
                    String[] sortnameArr = new String[sortFields.size()];
                    Boolean[] typeArr = new Boolean[sortFields.size()];
                    Boolean[] nullMaxArr = new Boolean[sortFields.size()];
                    for (int i = 0; i < sortFields.size(); ++i) {
                        sortnameArr[i] = sortFields.get(i).get("name");
                        typeArr[i] = "ASC".equals(sortFields.get(i).get("sort"));
                        nullMaxArr[i] = "true".equals(sortFields.get(i).get("nullMax"));
                    }
                    ListUtils.sort(rows, sortnameArr, typeArr, nullMaxArr);
                }
                if (CollectionUtils.isEmpty(load)) {
                    this.setStatus(LazyLoadState.LOADED);
                    rows = this.rowList;
                    return rows;
                }
                for (int i = 0; i < load.size(); ++i) {
                    Map<String, Object> o = load.get(i);
                    Object id = o.get("ID");
                    if (id == null) {
                        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.missingidfield"));
                    }
                    DataRowImpl dataRow = this.insertRow(this.rowList.size(), 1, o, false).get(0);
                    dataRow.setState(DataRowState.NORMAL);
                    dataRow.setIndex(i);
                }
                this.setStatus(LazyLoadState.LOADED);
            }
            finally {
                this.getData().setListening(true);
            }
            return this.rowList;
        }
    }

    @Override
    public void setRowsData(List<Map<String, Object>> rowsData) {
        this.getLazyRowsData();
        boolean isReferUpdate = this.isReferUpdate(rowsData);
        if (isReferUpdate && this.data.getState() == DataState.NEW) {
            this.data.reload(this);
        }
        Map map = this.rowList.stream().collect(Collectors.toMap(DataRowImpl::getId, Function.identity()));
        this.rowList.clear();
        ArrayList refDeleteRowList = new ArrayList();
        Object enableFilter = ((ModelContextImpl)this.data.getModel().getContext()).getContextValue("X--detailFilterDataId");
        Map idList = null;
        if (enableFilter != null) {
            Map list = (Map)enableFilter;
            idList = list.containsKey(this.getName()) ? (Map)list.get(this.getName()) : new LinkedHashMap();
        }
        LinkedHashMap finalIdList = idList;
        rowsData.stream().forEach(o -> {
            Object id = o.get("ID");
            if (id == null) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.missingidfield"));
            }
            id = this.F_ID.getDefine().getValueType().cast(id);
            DataRowImpl row = (DataRowImpl)map.get(id);
            if (row == null) {
                if (isReferUpdate && !DataRowState.APPENDED.name().equals(o.get("$STATE"))) {
                    return;
                }
                row = this.insertRow(this.rowList.size(), 1, (Map<String, Object>)o, false).get(0);
            } else {
                Object ver = o.get("VER");
                if (ver != null) {
                    long oldVer;
                    long newVer;
                    if (finalIdList == null) {
                        long oldVer2;
                        newVer = Convert.cast(ver, Long.TYPE);
                        if (newVer != (oldVer2 = row.getVersion())) {
                            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datachange"));
                        }
                    } else if (finalIdList.containsKey(id.toString()) && (newVer = Convert.cast(ver, Long.TYPE).longValue()) != (oldVer = row.getVersion())) {
                        o.put("ORDINAL", row.getDouble("ORDINAL"));
                    }
                }
                if (isReferUpdate) {
                    String state = (String)o.get("$STATE");
                    DataRowState dataRowState = DataRowState.valueOf(state);
                    if (dataRowState == DataRowState.DELETED) {
                        refDeleteRowList.add(row);
                    } else {
                        this.rowList.add(row);
                        row.setData((Map<String, Object>)o);
                    }
                } else {
                    map.remove(id);
                    this.rowList.add(row);
                    row.setData((Map<String, Object>)o);
                }
            }
            row.setIndex(this.rowList.size() - 1);
        });
        if (this.getTableType() == DataTableType.DATA) {
            map.values().forEach(o -> {
                if (o.getState() == DataRowState.APPENDED) {
                    return;
                }
                if (o.getState() != DataRowState.NORMAL) {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.datarowstatuserror"));
                }
                o.state = DataRowState.DELETED;
                this.deleteRowList.add((DataRowImpl)o);
            });
        } else if (isReferUpdate) {
            refDeleteRowList.forEach(o -> {
                if (o.getState() == DataRowState.APPENDED) {
                    return;
                }
                if (o.getState() != DataRowState.NORMAL) {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.datarowstatuserror"));
                }
                o.state = DataRowState.DELETED;
                this.deleteRowList.add((DataRowImpl)o);
            });
        } else {
            this.applyChange();
        }
    }

    public void mergeRowsData(List<Map<String, Object>> rowsData) {
        this.getLazyRowsData();
        boolean isReferUpdate = this.isReferUpdate(rowsData);
        if (isReferUpdate && this.data.getState() == DataState.NEW) {
            this.data.reload(this);
        }
        Map map = this.rowList.stream().collect(Collectors.toMap(DataRowImpl::getId, Function.identity()));
        rowsData = this.mergeLoadData(rowsData, new HashMap<Object, DataRowImpl>(map));
        this.rowList.clear();
        ArrayList refDeleteRowList = new ArrayList();
        Object enableFilter = ((ModelContextImpl)this.data.getModel().getContext()).getContextValue("X--detailFilterDataId");
        Map idList = null;
        if (enableFilter != null) {
            Map list = (Map)enableFilter;
            idList = list.containsKey(this.getName()) ? (Map)list.get(this.getName()) : new LinkedHashMap();
        }
        LinkedHashMap finalIdList = idList;
        rowsData.forEach(o -> {
            Object id = o.get("ID");
            if (id == null) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.missingidfield"));
            }
            id = this.F_ID.getDefine().getValueType().cast(id);
            DataRowImpl row = (DataRowImpl)map.get(id);
            if (row == null) {
                if (isReferUpdate && !DataRowState.APPENDED.name().equals(o.get("$STATE"))) {
                    return;
                }
                row = this.insertRow(this.rowList.size(), 1, (Map<String, Object>)o, false).get(0);
            } else {
                Object ver = o.get("VER");
                if (ver != null) {
                    long oldVer;
                    long newVer;
                    if (finalIdList == null) {
                        long oldVer2;
                        newVer = Convert.cast(ver, Long.TYPE);
                        if (newVer != (oldVer2 = row.getVersion())) {
                            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datachange"));
                        }
                    } else if (finalIdList.containsKey(id.toString()) && (newVer = Convert.cast(ver, Long.TYPE).longValue()) != (oldVer = row.getVersion())) {
                        o.put("ORDINAL", row.getDouble("ORDINAL"));
                    }
                }
                if (isReferUpdate) {
                    String state = (String)o.get("$STATE");
                    DataRowState dataRowState = DataRowState.valueOf(state);
                    if (dataRowState == DataRowState.DELETED) {
                        refDeleteRowList.add(row);
                    } else {
                        this.rowList.add(row);
                        row.setData((Map<String, Object>)o);
                    }
                } else {
                    map.remove(id);
                    this.rowList.add(row);
                    row.setData((Map<String, Object>)o);
                }
            }
            row.setIndex(this.rowList.size() - 1);
        });
        if (this.getTableType() == DataTableType.DATA) {
            map.values().forEach(o -> {
                if (o.getState() == DataRowState.APPENDED) {
                    return;
                }
                if (o.getState() != DataRowState.NORMAL) {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.datarowstatuserror"));
                }
                o.state = DataRowState.DELETED;
                this.deleteRowList.add((DataRowImpl)o);
            });
        } else if (isReferUpdate) {
            refDeleteRowList.forEach(o -> {
                if (o.getState() == DataRowState.APPENDED) {
                    return;
                }
                if (o.getState() != DataRowState.NORMAL) {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.datarowstatuserror"));
                }
                o.state = DataRowState.DELETED;
                this.deleteRowList.add((DataRowImpl)o);
            });
        } else {
            this.applyChange();
        }
    }

    private List<Map<String, Object>> mergeLoadData(List<Map<String, Object>> rowsData, Map<Object, DataRowImpl> map) {
        ModelContextImpl context = (ModelContextImpl)this.getData().getModel().getContext();
        Map contextValue = (Map)context.getContextValue("X--detailFilterDataId");
        Map idList = (Map)contextValue.get(this.getName());
        ArrayList<Map<String, Object>> newData = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map2 : rowsData) {
            if (map.containsKey(Convert.cast(map2.get("ID"), UUID.class))) {
                newData.add(map2);
                map.remove(Convert.cast(map2.get("ID"), UUID.class));
                continue;
            }
            Integer i = (Integer)idList.get(map2.get("ID").toString());
            if (i != null && i == 0) {
                idList.remove(map2.get("ID").toString());
                continue;
            }
            newData.add(map2);
        }
        for (Map.Entry entry : map.entrySet()) {
            if (idList.containsKey(entry.getKey().toString())) continue;
            newData.add(((DataRowImpl)entry.getValue()).getData());
        }
        return newData;
    }

    protected void setMasterRowsData(List<Map<String, Object>> rowsData) {
        rowsData.stream().forEach(o -> {
            DataRowImpl dataRow = this.insertRow(this.rowList.size(), 1, (Map<String, Object>)o, false).get(0);
            dataRow.setState(DataRowState.NORMAL);
        });
    }

    @Override
    public void setRowsData(List<Object> fieldNames, List<List<Object>> rowsData) {
        this.getLazyRowsData();
        Integer stateIndex = fieldNames.indexOf("$STATE");
        Integer idIndex = fieldNames.indexOf("ID");
        Integer verIndex = fieldNames.indexOf("VER");
        boolean isReferUpdate = this.isReferUpdate(rowsData, stateIndex);
        if (isReferUpdate && this.data.getState() == DataState.NEW) {
            this.data.reload(this);
        }
        Map map = this.rowList.stream().collect(Collectors.toMap(DataRowImpl::getId, Function.identity()));
        this.rowList.clear();
        ArrayList<DataRowImpl> refDeleteRowList = new ArrayList<DataRowImpl>();
        for (int i = 0; i < rowsData.size(); ++i) {
            List<Object> values = rowsData.get(i);
            Object id = values.get(idIndex);
            if (id == null) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.missingidfield"));
            }
            id = this.F_ID.getDefine().getValueType().cast(id);
            DataRowImpl row = (DataRowImpl)map.get(id);
            if (row == null) {
                if (isReferUpdate && !DataRowState.APPENDED.name().equals(values.get(stateIndex))) {
                    return;
                }
                row = this.insertRow(this.rowList.size(), 1, values, fieldNames).get(0);
            } else {
                long oldVer;
                long newVer;
                Object ver;
                Object object = ver = verIndex == -1 ? null : values.get(verIndex);
                if (ver != null && (newVer = Convert.cast(ver, Long.TYPE).longValue()) != (oldVer = row.getVersion())) {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datachange"));
                }
                if (isReferUpdate) {
                    String state = (String)values.get(stateIndex);
                    DataRowState dataRowState = DataRowState.valueOf(state);
                    if (dataRowState == DataRowState.DELETED) {
                        refDeleteRowList.add(row);
                    } else {
                        this.rowList.add(row);
                        row.setData(values, fieldNames);
                    }
                } else {
                    map.remove(id);
                    this.rowList.add(row);
                    row.setData(values, fieldNames);
                }
            }
            row.setIndex(this.rowList.size() - 1);
        }
        if (this.getTableType() == DataTableType.DATA) {
            map.values().forEach(o -> {
                if (o.getState() == DataRowState.APPENDED) {
                    return;
                }
                if (o.getState() != DataRowState.NORMAL) {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.datarowstatuserror"));
                }
                o.state = DataRowState.DELETED;
                this.deleteRowList.add((DataRowImpl)o);
            });
        } else if (isReferUpdate) {
            refDeleteRowList.forEach(o -> {
                if (o.getState() == DataRowState.APPENDED) {
                    return;
                }
                if (o.getState() != DataRowState.NORMAL) {
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.datarowstatuserror"));
                }
                o.state = DataRowState.DELETED;
                this.deleteRowList.add((DataRowImpl)o);
            });
        } else {
            this.applyChange();
        }
    }

    private boolean isReferUpdate(List<Map<String, Object>> rowsData) {
        boolean isReferUpdate = false;
        if (this.getTableType() == DataTableType.REFER) {
            Object state;
            for (Map<String, Object> rowValues : rowsData) {
                state = rowValues.get("$STATE");
                if (state == null) continue;
                isReferUpdate = true;
                break;
            }
            if (isReferUpdate) {
                for (Map<String, Object> rowValues : rowsData) {
                    state = rowValues.get("$STATE");
                    if (state != null) continue;
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.datarowstatussettingerror"));
                }
            }
        }
        return isReferUpdate;
    }

    private boolean isReferUpdate(List<List<Object>> rowsData, Integer index) {
        if (index == null) {
            return false;
        }
        boolean isReferUpdate = false;
        if (this.getTableType() == DataTableType.REFER) {
            Object state;
            int i;
            for (i = 1; i < rowsData.size(); ++i) {
                state = rowsData.get(i).get(index);
                if (state == null) continue;
                isReferUpdate = true;
                break;
            }
            if (isReferUpdate) {
                for (i = 1; i < rowsData.size(); ++i) {
                    state = rowsData.get(i).get(index);
                    if (state != null) continue;
                    throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.datarowstatussettingerror"));
                }
            }
        }
        return isReferUpdate;
    }

    @Override
    public DataRow getRowById(Object id) {
        this.getLazyRowsData();
        return this.rowList.stream().filter(o -> Objects.equals(o.getId(), id)).findFirst().orElseThrow(() -> new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datatableimpl.notfoundrow") + id));
    }

    public void deleteUnsetRows() {
        for (int i = this.rowList.size() - 1; i >= 0; --i) {
            DataRowImpl row = this.rowList.get(i);
            if (!row.unset || row.getState() != DataRowState.APPENDED) continue;
            this.deleteRow(i);
        }
    }

    @Override
    public void sort(Comparator<? super DataRow> c) {
        Collections.sort(this.rowList, c);
        DataFieldImpl field = this.F_ORDINAL;
        if (field == null) {
            return;
        }
        for (int i = 0; i < this.rowList.size(); ++i) {
            this.rowList.get(i).setValue(field.getIndex(), (Object)i);
        }
    }
}

