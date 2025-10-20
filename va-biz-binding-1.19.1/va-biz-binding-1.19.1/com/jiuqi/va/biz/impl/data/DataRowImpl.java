/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.bill.BillVerifyDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryParentType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.cache.BaseDataDefineCache;
import com.jiuqi.va.biz.domain.CheckFieldResult;
import com.jiuqi.va.biz.impl.data.DataEventImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldImpl;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.CheckFieldValueEnum;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataException;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelConsts;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ref.intf.RefData;
import com.jiuqi.va.biz.ref.intf.RefDataFilter;
import com.jiuqi.va.biz.ruler.ModelParamNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.CheckException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.Ruler;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.ruler.intf.RulerDefine;
import com.jiuqi.va.biz.utils.BaseDataUtils;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.utils.VerifyUtils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.bill.BillVerifyDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class DataRowImpl
implements DataRow {
    private final DataTableImpl table;
    final Object[] values;
    DataRowState state;
    DataRowImpl orginRow;
    boolean[] modified;
    private int index;
    boolean isCacheInit;
    boolean unset;
    private BaseDataClient baseDataClient;
    private OrgDataClient orgDataClient;
    private final List<String> filedTypes = Arrays.asList("CODE", "SHOWTITLE", "OBJECTCODE", "NAME", "TITLE");
    private final List<String> filedCheckTypes = Arrays.asList(RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE, RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2, RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT);
    private static final Logger logger = LoggerFactory.getLogger(DataRowImpl.class);

    DataRowImpl(DataTableImpl table, Object[] values) {
        this.table = table;
        this.values = values;
        this.state = DataRowState.UNUSED;
    }

    DataRowImpl(DataTableImpl table, Object[] values, DataRowState state) {
        this.table = table;
        int len = values.length;
        this.values = new Object[len];
        for (int i = 0; i < len; ++i) {
            Object value = values[i];
            if (DataRowState.MODIFIED.equals((Object)state)) {
                if (this.orginRow == null) {
                    this.orginRow = this.table.createRow();
                    this.orginRow.setState(DataRowState.LOCKED);
                }
                if (value != null && value.getClass().isArray()) {
                    if (this.modified == null) {
                        this.modified = new boolean[len];
                    }
                    this.modified[i] = true;
                    Object[] obj = (Object[])value;
                    this.values[i] = obj[0];
                    this.orginRow.values[i] = obj[1];
                    continue;
                }
                this.values[i] = values[i];
                this.orginRow.values[i] = values[i];
                continue;
            }
            this.values[i] = values[i];
        }
        this.state = state;
    }

    private BaseDataClient getBaseDataClient() {
        if (this.baseDataClient == null) {
            this.baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        }
        return this.baseDataClient;
    }

    private OrgDataClient getOrgDataClient() {
        if (this.orgDataClient == null) {
            this.orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
        }
        return this.orgDataClient;
    }

    public boolean[] getModified() {
        return this.modified;
    }

    @Override
    public Object getId() {
        return this.values[this.table.F_ID.getIndex()];
    }

    @Override
    public long getVersion() {
        if (this.table.F_VERSION == null) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.verfieldnotexist"));
        }
        return Convert.cast(this.values[this.table.F_VERSION.getIndex()], Long.TYPE);
    }

    @Override
    public Object getMasterId() {
        if (this.table.getTableType() == DataTableType.REFER) {
            return this.table.data.getMasterTable().getRows().get(0).getId();
        }
        if (this.table.F_MASTERID == null) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.masteridfieldnotexist"));
        }
        return this.values[this.table.F_MASTERID.getIndex()];
    }

    @Override
    public Object getGroupId() {
        if (this.table.F_GROUPID == null) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.groupidnotexist"));
        }
        return this.values[this.table.F_GROUPID.getIndex()];
    }

    @Override
    public double getOrdinal() {
        if (this.table.F_ORDINAL == null) {
            throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.ordinalnotexist"));
        }
        return (Double)this.values[this.table.F_ORDINAL.getIndex()];
    }

    @Override
    public DataRowState getState() {
        return this.state;
    }

    void setState(DataRowState state) {
        this.state = state;
    }

    @Override
    public DataRowImpl getOriginRow() {
        switch (this.state) {
            case NORMAL: {
                return this;
            }
            case LOCKED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.lockdatanotsupportoperation"));
            }
            case INITIAL: 
            case APPENDED: {
                return this;
            }
            case MODIFIED: {
                return this.orginRow;
            }
            case DELETED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.deldatanotsupportoperation"));
            }
            case UNUSED: {
                return null;
            }
        }
        throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.datastatusempty"));
    }

    public boolean isUnset() {
        return this.unset;
    }

    @Override
    public boolean isNull(String name) {
        return this.getValue(name) == null;
    }

    @Override
    public boolean getBoolean(String name) {
        return Convert.cast(this.getValue(name), Boolean.TYPE);
    }

    @Override
    public byte getByte(String name) {
        return Convert.cast(this.getValue(name), Byte.TYPE);
    }

    @Override
    public short getShort(String name) {
        return Convert.cast(this.getValue(name), Short.TYPE);
    }

    @Override
    public int getInt(String name) {
        return Convert.cast(this.getValue(name), Integer.TYPE);
    }

    @Override
    public long getLong(String name) {
        return Convert.cast(this.getValue(name), Long.TYPE);
    }

    @Override
    public float getFloat(String name) {
        return Convert.cast(this.getValue(name), Float.TYPE).floatValue();
    }

    @Override
    public double getDouble(String name) {
        return Convert.cast(this.getValue(name), Double.TYPE);
    }

    @Override
    public char getChar(String name) {
        return Convert.cast(this.getValue(name), Character.TYPE).charValue();
    }

    @Override
    public String getString(String name) {
        return Convert.cast(this.getValue(name), String.class);
    }

    @Override
    public Date getDate(String name) {
        return Convert.cast(this.getValue(name), Date.class);
    }

    @Override
    public UUID getUUID(String name) {
        return Convert.cast(this.getValue(name), UUID.class);
    }

    @Override
    public BigDecimal getBigDecimal(String name) {
        return Convert.cast(this.getValue(name), BigDecimal.class);
    }

    @Override
    public byte[] getBytes(String name) {
        return Convert.cast(this.getValue(name), byte[].class);
    }

    @Override
    public Object getValue(String name) {
        return this.getValue(this.table.getFields().get(name).getIndex());
    }

    @Override
    public <T> T getValue(String name, Class<T> valueClass) {
        return this.getValue(this.table.getFields().get(name).getIndex(), valueClass);
    }

    @Override
    public Object getRawValue(String name) {
        return this.getRawValue(this.table.getFields().get(name).getIndex());
    }

    @Override
    public boolean isNull(int index) {
        return this.getValue(index) == null;
    }

    @Override
    public boolean getBoolean(int index) {
        return this.getValue(index, Boolean.TYPE);
    }

    @Override
    public byte getByte(int index) {
        return this.getValue(index, Byte.TYPE);
    }

    @Override
    public short getShort(int index) {
        return this.getValue(index, Short.TYPE);
    }

    @Override
    public int getInt(int index) {
        return this.getValue(index, Integer.TYPE);
    }

    @Override
    public long getLong(int index) {
        return this.getValue(index, Long.TYPE);
    }

    @Override
    public float getFloat(int index) {
        return this.getValue(index, Float.TYPE).floatValue();
    }

    @Override
    public double getDouble(int index) {
        return this.getValue(index, Double.TYPE);
    }

    @Override
    public char getChar(int index) {
        return this.getValue(index, Character.TYPE).charValue();
    }

    @Override
    public String getString(int index) {
        return this.getValue(index, String.class);
    }

    @Override
    public Date getDate(int index) {
        return this.getValue(index, Date.class);
    }

    @Override
    public UUID getUUID(int index) {
        return this.getValue(index, UUID.class);
    }

    @Override
    public BigDecimal getBigDecimal(int index) {
        return this.getValue(index, BigDecimal.class);
    }

    @Override
    public byte[] getBytes(int index) {
        return this.getValue(index, byte[].class);
    }

    @Override
    public Object getValue(int index) {
        Object value = this.values[index];
        DataFieldDefineImpl fieldDefine = this.table.getFields().get(index).getDefine();
        if (value instanceof Map) {
            return this.getIdentify(fieldDefine.getValueType(), (Map)value);
        }
        if (value instanceof List && DataFieldType.CALC.equals((Object)fieldDefine.getFieldType())) {
            List resultList = (List)value;
            if (CollectionUtils.isEmpty(resultList)) {
                return new ArrayData(6, (Collection)resultList);
            }
            Optional<Object> optional = resultList.stream().filter(o -> !ObjectUtils.isEmpty(o)).findFirst();
            if (!optional.isPresent()) {
                return new ArrayData(6, (Collection)resultList);
            }
            Object value1 = optional.get();
            if (value1 instanceof String || value1 instanceof UUID) {
                return new ArrayData(6, (Collection)resultList);
            }
            if (value1 instanceof BigDecimal) {
                return new ArrayData(10, (Collection)resultList);
            }
            if (value1 instanceof Number) {
                return new ArrayData(3, (Collection)resultList);
            }
            if (value1 instanceof Boolean) {
                return new ArrayData(1, (Collection)resultList);
            }
            if (value1 instanceof Date) {
                List calendars = resultList.stream().map(o -> {
                    if (o == null) {
                        return null;
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime((Date)o);
                    return calendar;
                }).collect(Collectors.toList());
                return new ArrayData(2, calendars);
            }
            return new ArrayData(6, (Collection)resultList);
        }
        return value;
    }

    @Override
    public <T> T getValue(int index, Class<T> valueClass) {
        Object value = this.getValue(index);
        return Convert.cast(value, valueClass);
    }

    @Override
    public Object getRawValue(int index) {
        return this.values[index];
    }

    public Object getViewValue(String name) {
        return this.getViewValue(this.table.getFields().get(name).getIndex());
    }

    public Object getViewValue(int index) {
        DataFieldImpl field = this.table.getFields().get(index);
        DataFieldDefineImpl fieldDefine = field.getDefine();
        Map<String, Object> value = this.values[index];
        if (value == null) {
            return value;
        }
        if (value instanceof Map) {
            if (fieldDefine.getMaskFlag() || StringUtils.hasText(fieldDefine.getMask())) {
                String mask = this.getMask(fieldDefine);
                HashMap<String, String> result = new HashMap<String, String>();
                result.putAll((Map)value);
                result.put("showTitle", mask);
                return result;
            }
            return value;
        }
        Model model = this.table.data.getModel();
        int refTableType = fieldDefine.getRefTableType();
        if (refTableType != 0) {
            if (!fieldDefine.isMultiChoiceStore()) {
                String identify = Convert.cast(value, String.class);
                value = RefData.isEnabled(refTableType) ? this.getRefDataObject(model, fieldDefine, identify, this) : this.getViewValue(model, fieldDefine, identify, model.getDimValues(fieldDefine, this));
                this.values[index] = value;
            }
        } else if (fieldDefine.isBillPenetrate()) {
            value = this.getBillRefValue(model, Convert.cast(value, String.class), fieldDefine.getPenetrateType());
            this.values[index] = value;
        } else if (this.isMultiValue(field.getName())) {
            value = this.getMultiViewValue(value, field);
            this.values[index] = value;
        } else if (fieldDefine.getValueType() == ValueType.DATE || fieldDefine.getValueType() == ValueType.DATETIME) {
            return this.getString(index);
        }
        if (fieldDefine.getMaskFlag() || StringUtils.hasText(fieldDefine.getMask())) {
            String mask = this.getMask(fieldDefine);
            HashMap<String, Object> result = new HashMap<String, Object>();
            if (!this.table.data.securityEnhanced || this.isModified(index) || fieldDefine.getRefTableType() != 0 || fieldDefine.isBillPenetrate()) {
                if (value instanceof Map) {
                    result.putAll(value);
                } else {
                    result.put("name", value);
                }
            }
            result.put("showTitle", mask);
            return result;
        }
        return value;
    }

    private String getMask(DataFieldDefineImpl fieldDefine) {
        DataImpl data = this.table.data;
        String tableName = data.getDefine().getMaskFieldTableMap().get(fieldDefine.getId());
        if (!StringUtils.hasText(tableName)) {
            return fieldDefine.getMask();
        }
        DataTableImpl dataTable = (DataTableImpl)((DataTableNodeContainerImpl)data.getTables()).find(tableName);
        DataTableImpl masterTable = data.getMasterTable();
        if (dataTable.getName().equals(masterTable.getName())) {
            return masterTable.getRows().get(0).getString(fieldDefine.getMaskName());
        }
        DataFieldImpl dataField = this.table.getFields().find(fieldDefine.getMaskName());
        if (dataField != null) {
            return (String)this.values[dataField.getIndex()];
        }
        return null;
    }

    private Object getRefDataObject(Model model, DataFieldDefine fieldDefine, String bizCode, DataRow dataRow) {
        RefDataFilter filter = new RefDataFilter();
        filter.setShowType(fieldDefine.getShowType());
        filter.setShowFullPath(fieldDefine.isShowFullPath());
        filter.setTenantName(model.getContext().getTenantName());
        filter.setBizType(fieldDefine.getRefTableName());
        filter.setBizCode(bizCode);
        filter.setContext(model.getRefDataContext(fieldDefine, dataRow));
        return RefData.ref(fieldDefine.getRefTableType(), filter);
    }

    private Map<String, Object> getViewValue(Model model, DataFieldDefine fieldDefine, String identify, Map<String, Object> dimValues) {
        if (fieldDefine.getRefTableType() == 1 && ((DataFieldDefineImpl)fieldDefine).isShowFullPath()) {
            String unitCode = (String)dimValues.get("UNITCODE");
            if (Utils.isEmpty(unitCode)) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.mustsetorg"));
            }
            Date bizDate = (Date)dimValues.get("BIZDATE");
            if (bizDate == null) {
                throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.mustsetdate"));
            }
            BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
            BaseDataDTO basedataDTO = new BaseDataDTO();
            basedataDTO.setTableName(fieldDefine.getRefTableName());
            basedataDTO.setStopflag(Integer.valueOf(-1));
            basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            basedataDTO.setVersionDate(bizDate);
            basedataDTO.setTenantName(Env.getTenantName());
            for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
                if (entry.getKey().equals("BIZDATE")) continue;
                basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
            }
            basedataDTO.setObjectcode(identify);
            basedataDTO.setShowType(fieldDefine.getShowType());
            basedataDTO.setShowFullPath(Boolean.valueOf(true));
            PageVO pageVO = baseDataClient.list(basedataDTO);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", identify);
            if (pageVO.getRows().size() == 1) {
                BaseDataDO baseDataDO = (BaseDataDO)pageVO.getRows().get(0);
                map.put("title", baseDataDO.getName());
                map.put("showTitle", baseDataDO.getShowTitle());
            }
            return map;
        }
        RefTableDataMap refTableMap = model.getRefDataBuffer().getRefTableMap(fieldDefine.getRefTableType(), fieldDefine.getRefTableName(), dimValues);
        return refTableMap.toViewValue(fieldDefine.getShowType(), refTableMap.find(identify));
    }

    private Map<String, Object> getUserValue(Model model, DataFieldDefine fieldDefine, String identify) {
        RefTableDataMap refTableMap = model.getRefDataBuffer().getRefTableMap(fieldDefine.getRefTableType(), fieldDefine.getRefTableName(), new HashMap<String, Object>());
        return refTableMap.toViewValue(fieldDefine.getShowType(), refTableMap.find(identify));
    }

    private Object getMultiViewValue(Object value, DataFieldImpl field) {
        DataRow groupRow;
        UUID groupId = this.getUUID("GROUPID");
        UUID bindingId = this.getUUID("BINDINGID");
        UUID masterId = this.getUUID("MASTERID");
        int refTableType = field.getDefine().getRefTableType();
        String tableName = field.getDefine().getTable().getName();
        DataTableImpl pTable = (DataTableImpl)((DataTableNodeContainerImpl)this.table.data.getTables()).find(tableName.substring(0, tableName.length() - 2));
        try {
            groupRow = pTable.getRowById(groupId == null ? masterId : groupId);
        }
        catch (Exception e) {
            return value;
        }
        DataFieldImpl bindingField = pTable.getFields().stream().filter(f -> f.getDefine().isMultiChoiceStore() && bindingId.equals(groupRow.getUUID(f.getIndex()))).findFirst().orElse(null);
        if (bindingField == null) {
            return null;
        }
        Model model = this.table.data.getModel();
        value = RefData.isEnabled(refTableType) ? this.getRefDataObject(model, bindingField.getDefine(), Convert.cast(value, String.class), groupRow) : this.getViewValue(model, bindingField.getDefine(), Convert.cast(value, String.class), model.getDimValues(bindingField.getDefine(), groupRow));
        return value;
    }

    private boolean isMultiValue(String name) {
        return "BINDINGVALUE".equals(name) && this.table.getName().endsWith("_M");
    }

    public List<String> getMultiValue(String name) {
        return this.getMultiValue(this.table.getFields().find(name).getIndex());
    }

    public List<String> getMultiValue(int index) {
        List<DataRowImpl> multiRows = this.getMultiRows(index);
        return this.getMultiValue(multiRows);
    }

    private List<String> getMultiValue(List<DataRowImpl> multiRows) {
        return multiRows.stream().map(row -> row.getString("BINDINGVALUE")).collect(Collectors.toList());
    }

    private List<DataRowImpl> getMultiRows(int index) {
        Object value = this.values[index];
        if (value == null) {
            return Collections.emptyList();
        }
        DataFieldDefineImpl fieldDefine = this.table.getFields().get(index).getDefine();
        DataTableImpl table = this.getMultiTable(fieldDefine);
        return table.getRows().stream().filter(row -> value.equals(row.getValue("BINDINGID"))).collect(Collectors.toList());
    }

    private List<DataRowImpl> getMultiRows(DataTableImpl table, int index) {
        Object value = this.values[index];
        if (value == null) {
            return Collections.emptyList();
        }
        return table.getRows().stream().filter(row -> value.equals(row.getValue("BINDINGID"))).collect(Collectors.toList());
    }

    private DataTableImpl getMultiTable(DataFieldDefineImpl fieldDefine) {
        String refTableName = fieldDefine.getTable().getMultiName();
        DataTableImpl table = (DataTableImpl)((DataTableNodeContainerImpl)this.table.data.getTables()).get(refTableName);
        return table;
    }

    private void setMultiValue(DataFieldDefineImpl fieldDefine, int index, Object value) {
        List<Object> newValue = value instanceof List ? (List<Object>)value : Arrays.asList(value);
        DataTableImpl multiTable = this.getMultiTable(fieldDefine);
        int indexORDERNUM = multiTable.getFields().get("ORDERNUM").getIndex();
        int indexBINDINGID = multiTable.getFields().get("BINDINGID").getIndex();
        int indexBINDINGVALUE = multiTable.getFields().get("BINDINGVALUE").getIndex();
        List<DataRowImpl> multiRows = this.getMultiRows(multiTable, index);
        if (newValue.isEmpty()) {
            this.setValue(index, null);
            return;
        }
        if (this.sameValue(newValue, multiRows, indexBINDINGVALUE)) {
            return;
        }
        for (int i = multiRows.size() - 1; i >= 0; --i) {
            multiTable.removeRow(multiRows.get(i));
        }
        Object groupId = this.getId();
        UUID bindingId = UUID.randomUUID();
        Map<String, Object> map = Stream.of("GROUPID").collect(Collectors.toMap(o -> o, o -> groupId));
        int j = newValue.size();
        for (int i = 0; i < j; ++i) {
            DataRow row = multiTable.appendRow((Map)map);
            ((DataRowImpl)row).setValue(indexORDERNUM, (Object)i);
            ((DataRowImpl)row).setValue(indexBINDINGID, (Object)bindingId);
            ((DataRowImpl)row).setValue(indexBINDINGVALUE, newValue.get(i));
        }
        this.setValue(index, (Object)bindingId);
    }

    private boolean sameValue(List newValue, List<DataRowImpl> multiRows, int indexBINDINGVALUE) {
        boolean sameValue = true;
        if (newValue.size() == multiRows.size()) {
            for (int i = multiRows.size() - 1; i >= 0; --i) {
                Object o1 = multiRows.get((int)i).values[indexBINDINGVALUE];
                o1 = Convert.cast(o1 instanceof Map ? this.getIdentify(ValueType.STRING, (Map)o1) : o1, String.class);
                Object o2 = newValue.get(i);
                if (Objects.equals(o1, o2 = Convert.cast(o2 instanceof Map ? this.getIdentify(ValueType.STRING, (Map)o2) : o2, String.class))) continue;
                sameValue = false;
                break;
            }
        } else {
            sameValue = false;
        }
        return sameValue;
    }

    private Object getBillRefValue(Model model, String code, int penetrateType) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("value", code);
        if (penetrateType == 2) {
            String username = ShiroUtil.getUser().getUsername();
            BillVerifyDTO billVerifyDTO = new BillVerifyDTO();
            billVerifyDTO.setBillCode(code);
            ArrayList<String> userIds = new ArrayList<String>();
            userIds.add(username);
            billVerifyDTO.setUserIds(userIds);
            billVerifyDTO.setAuth(1);
            Map<String, String> verifyCodeByUserName = VerifyUtils.genVerifyCodeForUsers(billVerifyDTO);
            result.put("verifyCode", verifyCodeByUserName.get(username));
        } else {
            result.put("verifyCode", model.createVerifyCode(code));
        }
        return result;
    }

    private Object getIdentify(ValueType valueType, Map value) {
        Object idValue = value.get("id");
        if (idValue != null) {
            return valueType.cast(idValue);
        }
        Object nameValue = value.get("name");
        if (nameValue != null) {
            return valueType.cast(nameValue);
        }
        Object code = value.get("value");
        return valueType.cast(code);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setValueWithCheck(int index, Object value) {
        DataFieldImpl field = this.table.getFields().get(index);
        if (field.getDefine().isReadonly()) {
            return;
        }
        Object oldVal = this.values[index];
        boolean listening = this.table.data.listening;
        try {
            this.table.data.listening = false;
            try {
                this.setValue(index, value);
            }
            catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        finally {
            this.table.data.listening = listening;
        }
        if (listening) {
            this.table.data.afterSetValue(this.table, this, field, value, oldVal);
        }
    }

    @Override
    public List<CheckResult> setValueWithFormulaCheck(String name, Object value) {
        return this.setValueWithFormulaCheck(this.table.getFields().get(name).getIndex(), value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<CheckResult> setValueWithFormulaCheck(int index, Object value) {
        Model model = this.table.data.getModel();
        ArrayList<CheckResult> checkMessages = new ArrayList<CheckResult>();
        DataFieldImpl field = this.table.getFields().get(index);
        UUID fieldID = field.getDefine().getId();
        RulerDefine rulerDefine = model.getDefine().getPlugins().get(RulerDefine.class);
        Object oldVal = this.values[index];
        boolean listening = this.table.data.listening;
        try {
            this.table.data.listening = false;
            try {
                this.setValue(index, value);
                rulerDefine.getFormulas().forEach((i, item) -> {
                    if (!(item.isUsed() && fieldID.equals(item.getObjectId()) && this.filedCheckTypes.contains(item.getPropertyType()))) {
                        return;
                    }
                    ArrayList<DataEventImpl> events = new ArrayList<DataEventImpl>();
                    events.add(new DataEventImpl("after-set-value", this.table, this, field));
                    try {
                        FormulaRulerItem formulaRulerItem = new FormulaRulerItem((FormulaImpl)item);
                        formulaRulerItem.execute(model, events.stream());
                    }
                    catch (CheckException e) {
                        checkMessages.addAll(e.getCheckMessages());
                    }
                });
            }
            catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
            if (!checkMessages.isEmpty()) {
                this.values[index] = oldVal;
            }
        }
        finally {
            this.table.data.listening = listening;
        }
        if (listening) {
            this.table.data.afterSetValue(this.table, this, field, value, oldVal);
        }
        return checkMessages;
    }

    public static Object getZeroValue(ValueType valueType) {
        switch (valueType) {
            case BOOLEAN: {
                return false;
            }
            case DECIMAL: {
                return BigDecimal.ZERO;
            }
            case INTEGER: {
                return 0;
            }
            case LONG: {
                return 0L;
            }
        }
        return null;
    }

    private void setCalcValue(int index, List<Object> value) {
        List oldValueList;
        DataFieldImpl field = this.table.getFields().get(index);
        Object oldValue = this.values[index];
        if (oldValue instanceof List && this.sameCalcValue(value, oldValueList = (List)oldValue)) {
            return;
        }
        this.handleState(index);
        this.values[index] = value;
        this.table.data.afterSetValue(this.table, this, field, value, oldValue);
    }

    private boolean sameCalcValue(List<Object> value, List<Object> oldValue) {
        if (value.size() == oldValue.size()) {
            for (int i = value.size() - 1; i >= 0; --i) {
                if (Objects.equals(value.get(i), oldValue.get(i))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void setValue(int index, Object value) {
        ModelContextImpl context;
        Object detailEnableFilter;
        if (value instanceof String && ((String)value).length() == 0) {
            value = null;
        }
        DataFieldImpl field = this.table.getFields().get(index);
        DataFieldDefineImpl fieldDefine = field.getDefine();
        if (this.getState() != DataRowState.INITIAL && this.getState() != DataRowState.APPENDED && this.table.data.listening && !"SYSTEM".equals(fieldDefine.getColumnAttr()) && (detailEnableFilter = (context = (ModelContextImpl)this.table.data.getModel().getContext()).getContextValue("X--detailFilterDataId")) != null) {
            Map tableList = (Map)detailEnableFilter;
            if (tableList.containsKey(this.table.getName())) {
                Map detailFilterDataId = (Map)context.getContextValue("X--detailFilterDataId");
                Map list = (Map)detailFilterDataId.get(this.table.getName());
                if (CollectionUtils.isEmpty(list) || !list.containsKey(this.getId().toString())) {
                    if (this.table.data.isEnableRule()) {
                        logger.debug("\u5b50\u8868\u8fc7\u6ee4\uff1a\u8bbe\u7f6e\u5b57\u6bb5\u503c\u5931\u8d25\u5f53\u524d\u884c\u4e0d\u5141\u8bb8\u7f16\u8f91\uff1a{}\uff1a{}[{}]", this.table.getName(), fieldDefine.getName(), value);
                    }
                    return;
                }
            } else {
                if (this.table.data.isEnableRule()) {
                    logger.debug("\u5b50\u8868\u8fc7\u6ee4\uff1a\u8bbe\u7f6e\u5b57\u6bb5\u503c\u5931\u8d25\u5f53\u524d\u8868\u4e0d\u5141\u8bb8\u7f16\u8f91\uff1a{}\uff1a{}[{}]", this.table.getName(), fieldDefine.getName(), value);
                }
                return;
            }
        }
        if (value == null && !fieldDefine.isNullable()) {
            value = DataRowImpl.getZeroValue(fieldDefine.getValueType());
        }
        if (fieldDefine.isMultiChoiceStore()) {
            if (value instanceof Map || value instanceof List) {
                this.setMultiValue(fieldDefine, index, value);
                return;
            }
            if (value == null) {
                Object oldValue = this.values[index];
                if (oldValue != null) {
                    DataTableImpl multiTable = this.getMultiTable(fieldDefine);
                    List<DataRowImpl> multiRows = multiTable.getRowList();
                    for (int i = multiRows.size() - 1; i >= 0; --i) {
                        DataRowImpl dataRow = multiRows.get(i);
                        if (!oldValue.equals(dataRow.getValue("BINDINGID"))) continue;
                        multiTable.deleteRow(dataRow);
                    }
                    this.handleState(index);
                    this.values[index] = null;
                    this.table.data.afterSetValue(this.table, this, field, null, oldValue);
                }
                return;
            }
        }
        ValueType valueType = fieldDefine.getValueType();
        boolean valueIsMap = value instanceof Map;
        if (valueIsMap) {
            Object o = ((Map)value).get("name");
            if (o == null && (fieldDefine.getMaskFlag() || StringUtils.hasText(fieldDefine.getMask())) && !fieldDefine.isBillPenetrate()) {
                return;
            }
            if ("maskMode".equalsIgnoreCase(String.valueOf(o))) {
                return;
            }
        }
        if (value != null && !valueIsMap) {
            value = this.convertValue(field, valueType, value);
        }
        Object oldValue = this.values[index];
        boolean oldValueIsMap = oldValue instanceof Map;
        if (valueIsMap || oldValueIsMap) {
            Object oldValue1;
            Object value1 = valueIsMap ? this.getIdentify(valueType, (Map)value) : value;
            Object object = oldValue1 = oldValueIsMap ? this.getIdentify(valueType, (Map)oldValue) : oldValue;
            if (Objects.equals(value1, oldValue1)) {
                return;
            }
        } else if (Objects.equals(value, oldValue)) {
            return;
        }
        if (ModelConsts.FIXED_FIELDS.contains(fieldDefine.getName())) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.unableeditfield") + fieldDefine.getName());
        }
        this.handleState(index);
        this.values[index] = value;
        this.table.data.afterSetValue(this.table, this, field, value, oldValue);
    }

    @Override
    public List<CheckFieldResult> checkFieldValue(String fieldName, Object fieldValue) {
        DataFieldDefineImpl define = this.table.getFields().get(fieldName).getDefine();
        String refTableName = define.getRefTableName();
        if (!StringUtils.hasText(refTableName) || ObjectUtils.isEmpty(fieldValue)) {
            return null;
        }
        ArrayList<CheckFieldResult> result = new ArrayList<CheckFieldResult>();
        if (define.isMultiChoice()) {
            if (fieldValue instanceof List || fieldValue instanceof Map) {
                ArrayList<Object> values;
                if (fieldValue instanceof List) {
                    values = (ArrayList<Object>)fieldValue;
                } else {
                    values = new ArrayList<Object>();
                    values.add(fieldValue);
                }
                for (Object e : values) {
                    this.getCheckMessage(e, define, result);
                }
            }
        } else {
            this.getCheckMessage(fieldValue, define, result);
        }
        return result.isEmpty() ? null : result;
    }

    private void getCheckMessage(Object value, DataFieldDefineImpl define, List<CheckFieldResult> result) {
        String refCode = this.getRefCode(value);
        if (refCode != null) {
            Model model = this.table.getData().getModel();
            Map<String, Object> refDataValue = this.getRefDataValue(refCode, define, model);
            if (refDataValue == null) {
                result.add(new CheckFieldResult(refCode, CheckFieldValueEnum.NOT_EXIST));
            } else {
                this.checkRefData(define, refDataValue, model, result, refCode);
            }
        }
    }

    private void checkRefData(DataFieldDefineImpl define, Map<String, Object> refDataValue, Model model, List<CheckFieldResult> result, String refCode) {
        boolean filter = true;
        RulerDefineImpl pluginDefine = (RulerDefineImpl)model.getPlugins().get(Ruler.class).getDefine();
        ListContainer<FormulaImpl> formulas = pluginDefine.getFormulas();
        UUID id = define.getId();
        IExpression expression = null;
        List formulaList = formulas.stream().filter(o -> o.getObjectId().equals(id) && "field".equals(o.getObjectType()) && FormulaType.FILTER.equals((Object)o.getFormulaType()) && RulerConsts.FORMULA_OBJECT_PROP_FIELD_FILTER.equals(o.getPropertyType()) && o.isUsed()).collect(Collectors.toList());
        if (!formulaList.isEmpty()) {
            expression = ((FormulaImpl)formulaList.get(0)).getCompiledExpression();
        }
        if (expression != null) {
            try {
                filter = this.checkRefDataFilter(expression, model, define, refDataValue);
            }
            catch (Exception e) {
                result.add(new CheckFieldResult(refCode, CheckFieldValueEnum.OTHER_ERROR));
                logger.error(e.getMessage(), e);
                return;
            }
        }
        if (!filter) {
            result.add(new CheckFieldResult(refCode, CheckFieldValueEnum.EXPRESSION_FILTER));
            return;
        }
        this.checkLeaf(define, refDataValue, result, refCode);
    }

    private void checkLeaf(DataFieldDefineImpl define, Map<String, Object> refDataValue, List<CheckFieldResult> result, String refCode) {
        String mdControl = define.getMdControl();
        if (!StringUtils.hasText(mdControl) || "ALL".equals(mdControl)) {
            return;
        }
        boolean isOnlyLeaf = mdControl.equals("ONLYLEAF");
        Object isLeaf = refDataValue.get("isLeaf");
        if (isLeaf == null) {
            result.add(new CheckFieldResult(refCode, CheckFieldValueEnum.OTHER_ERROR));
            return;
        }
        if (((Boolean)isLeaf).booleanValue()) {
            if (!isOnlyLeaf) {
                result.add(new CheckFieldResult(refCode, CheckFieldValueEnum.ONLY_NOT_LEAF));
            }
        } else if (isOnlyLeaf) {
            result.add(new CheckFieldResult(refCode, CheckFieldValueEnum.ONLY_LEAF));
        }
    }

    private String getRefCode(Object value) {
        if (value instanceof String) {
            return (String)value;
        }
        if (value instanceof Map) {
            return (String)((Map)value).get("name");
        }
        return null;
    }

    private boolean checkRefDataFilter(IExpression expression, Model model, DataFieldDefine field, Map<String, Object> refDataValue) {
        ModelDataContext context = new ModelDataContext(model);
        expression.forEach(node -> {
            if (node instanceof ModelParamNode) {
                String name = ((ModelParamNode)((Object)node)).getName().toUpperCase();
                if (this.filedTypes.contains(name) || field.getRefTableType() == 2 && (name.equals("VAL") || name.equals("DESCRIPTION"))) {
                    context.setFieldValueType(name, ValueType.STRING);
                    context.put(name, refDataValue.get(name.toLowerCase()));
                } else {
                    String refTableName = field.getRefTableName();
                    DataModelDO dataModelDO = BaseDataUtils.findBaseDataDefine(refTableName);
                    context.setFieldValueType(name, FormulaUtils.getRefDataFieldType(dataModelDO, refTableName, name));
                    context.put(name, refDataValue.get(name.toLowerCase()));
                }
            }
        });
        Map<String, DataRow> rowMap = Stream.of(this).collect(Collectors.toMap(k -> this.table.getName(), v -> v));
        FormulaUtils.adjustFormulaRows(model.getPlugins().get(Data.class), rowMap);
        for (String s : rowMap.keySet()) {
            context.put(s, rowMap.get(s));
        }
        try {
            return expression.judge((IContext)context);
        }
        catch (SyntaxException e) {
            logger.error("\u516c\u5f0f\u6267\u884c\u5931\u8d25\uff1a" + e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    private Map<String, Object> getRefDataValue(Object value, DataFieldDefineImpl define, Model model) {
        boolean isOnlyNotLeaf;
        Map<String, Object> dimValues = model.getDimValues(define, this);
        String mdControl = define.getMdControl();
        boolean isOnlyLeaf = mdControl != null && mdControl.equals("ONLYLEAF");
        boolean bl = isOnlyNotLeaf = mdControl != null && mdControl.equals("ONLYNOTLEAF");
        if (define.getRefTableType() == 1) {
            BaseDataDTO basedataDTO = new BaseDataDTO();
            basedataDTO.setTableName(define.getRefTableName());
            basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            basedataDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
            basedataDTO.setAuthType(define.isIgnorePermission() ? BaseDataOption.AuthType.NONE : BaseDataOption.AuthType.ACCESS);
            basedataDTO.setLeafFlag(Boolean.valueOf(isOnlyLeaf || isOnlyNotLeaf));
            if (Utils.isEmpty(basedataDTO.getTenantName())) {
                basedataDTO.setTenantName(Env.getTenantName());
            }
            if (define.isQueryStop()) {
                basedataDTO.setStopflag(Integer.valueOf(-1));
            }
            basedataDTO.setObjectcode(value.toString());
            if (define.isCrossOrgSelection()) {
                PageVO list = this.getBaseDataClient().list(basedataDTO);
                if (CollectionUtils.isEmpty(list.getRows())) {
                    return null;
                }
                BaseDataDO baseDataDO = (BaseDataDO)list.getRows().get(0);
                if (define.isIgnoreOrgShareFiledMapping()) {
                    return baseDataDO;
                }
                String unitcode = baseDataDO.getUnitcode();
                OrgDTO orgDTO = new OrgDTO();
                orgDTO.setCode(unitcode);
                orgDTO.setCategoryname("MD_ORG");
                orgDTO.setTenantName(Env.getTenantName());
                orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
                orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
                orgDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
                orgDTO.setStopflag(Integer.valueOf(-1));
                PageVO pageVO = this.getOrgDataClient().list(orgDTO);
                if (CollectionUtils.isEmpty(pageVO.getRows())) {
                    logger.info("\u65e0\u8de8\u7ec4\u7ec7\u673a\u6784\u6743\u9650:{}", (Object)unitcode);
                    return null;
                }
                return baseDataDO;
            }
            for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
                if (entry.getKey().equals("BIZDATE")) continue;
                basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
            }
            PageVO<BaseDataDO> baseDataDOPageVO = this.searchBaseDataByObjectCode(basedataDTO);
            if (CollectionUtils.isEmpty(baseDataDOPageVO.getRows())) {
                return null;
            }
            return (Map)baseDataDOPageVO.getRows().get(0);
        }
        if (define.getRefTableType() == 4) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode(value.toString());
            orgDTO.setCategoryname(define.getRefTableName());
            orgDTO.setTenantName(Env.getTenantName());
            orgDTO.setAuthType(define.isIgnorePermission() ? OrgDataOption.AuthType.NONE : OrgDataOption.AuthType.ACCESS);
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            orgDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
            orgDTO.setLeafFlag(Boolean.valueOf(isOnlyLeaf || isOnlyNotLeaf));
            orgDTO.setStopflag(Integer.valueOf(-1));
            PageVO pageVO = this.getOrgDataClient().list(orgDTO);
            if (CollectionUtils.isEmpty(pageVO.getRows())) {
                return null;
            }
            return (Map)pageVO.getRows().get(0);
        }
        return model.getRefDataBuffer().getRefTableMap(define.getRefTableType(), define.getRefTableName(), dimValues).find(value.toString());
    }

    private PageVO<BaseDataDO> searchBaseDataByObjectCode(BaseDataDTO basedataDTO) {
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine(basedataDTO.getTableName());
        if (baseDataDefine == null) {
            return new PageVO(true);
        }
        if (baseDataDefine.getSharetype() == 0) {
            return this.getBaseDataClient().list(basedataDTO);
        }
        basedataDTO.put("shareForceCheck", (Object)true);
        if (baseDataDefine.getSharetype() == 3) {
            basedataDTO.put("shareUnitcodes", Arrays.asList(basedataDTO.get((Object)"UNITCODE".toLowerCase()), "-"));
        } else if (baseDataDefine.getSharetype() == 2) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode((String)basedataDTO.get((Object)"UNITCODE".toLowerCase()));
            orgDTO.setQueryParentType(OrgDataOption.QueryParentType.ALL_PARENT);
            orgDTO.setStopflag(Integer.valueOf(-1));
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            PageVO list = this.getOrgDataClient().list(orgDTO);
            if (!CollectionUtils.isEmpty(list.getRows())) {
                List unitcodeList = list.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList());
                unitcodeList.add((String)basedataDTO.get((Object)"UNITCODE".toLowerCase()));
                basedataDTO.put("shareUnitcodes", unitcodeList);
            } else {
                basedataDTO.put("shareUnitcodes", Collections.singletonList(basedataDTO.get((Object)"UNITCODE".toLowerCase())));
            }
        } else {
            basedataDTO.put("shareUnitcodes", Collections.singletonList(basedataDTO.get((Object)"UNITCODE".toLowerCase())));
        }
        return this.getBaseDataClient().list(basedataDTO);
    }

    private void handleState(int index) {
        switch (this.state) {
            case NORMAL: {
                if (this.table.data.getState() == DataState.BROWSE) break;
                this.orginRow = this.table.createRow();
                this.orginRow.setState(DataRowState.LOCKED);
                System.arraycopy(this.values, 0, this.orginRow.values, 0, this.values.length);
                this.state = DataRowState.MODIFIED;
                this.modified = new boolean[this.values.length];
                this.modified[index] = true;
                break;
            }
            case LOCKED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.lockdatanotsupportoperation"));
            }
            case APPENDED: {
                break;
            }
            case INITIAL: {
                break;
            }
            case MODIFIED: {
                this.modified[index] = true;
                break;
            }
            case DELETED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.deldatanotsupportoperation"));
            }
            case UNUSED: {
                break;
            }
            default: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.datastatusempty"));
            }
        }
    }

    Object convertValue(DataFieldImpl field, ValueType valueType, Object value) {
        DataFieldDefineImpl fieldDefine = field.getDefine();
        int refTableType = fieldDefine.getRefTableType();
        if (refTableType == 3) {
            try {
                return valueType.cast(Convert.cast(value, UUID.class));
            }
            catch (Exception e) {
                String identify = Convert.cast(value, String.class);
                Model model = this.table.data.getModel();
                Map<String, Object> refValue = this.getUserValue(model, fieldDefine, identify);
                return refValue;
            }
        }
        value = valueType.cast(value);
        switch (valueType) {
            case STRING: {
                value = ((String)value).trim();
                if (((String)value).length() != 0) break;
                value = null;
                break;
            }
            case DECIMAL: {
                value = ((BigDecimal)value).setScale(fieldDefine.getDigits(), 4);
                break;
            }
            case DATE: {
                value = Utils.toDate((Date)value);
                if (((Date)value).getTime() == 0L) {
                    value = null;
                    break;
                }
                if (value.getClass() == Date.class) break;
                value = new Date(((Date)value).getTime());
                break;
            }
            case DATETIME: {
                if (((Date)value).getTime() == 0L) {
                    value = null;
                    break;
                }
                if (value.getClass() == Date.class) break;
                value = new Date(((Date)value).getTime());
                break;
            }
        }
        return value;
    }

    @Override
    public void setValue(String name, Object value) {
        this.setValue(this.table.getFields().get(name).getIndex(), value);
    }

    @Override
    public void setCalcValue(String name, List<Object> value) {
        this.setCalcValue(this.table.getFields().get(name).getIndex(), value);
    }

    @Override
    public void setValueWithCheck(String name, Object value) {
        this.setValueWithCheck(this.table.getFields().get(name).getIndex(), value);
    }

    void applyChange() {
        switch (this.state) {
            case NORMAL: {
                break;
            }
            case LOCKED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.lockdatanotsupportoperation"));
            }
            case APPENDED: {
                this.state = DataRowState.NORMAL;
                break;
            }
            case MODIFIED: {
                this.orginRow.state = DataRowState.UNUSED;
                this.orginRow = null;
                this.state = DataRowState.NORMAL;
                this.modified = null;
                break;
            }
            case DELETED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.deldatanotsupportoperation"));
            }
            case UNUSED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.datarowunable"));
            }
            default: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.datastatusempty"));
            }
        }
    }

    void cancelChange() {
        switch (this.state) {
            case NORMAL: {
                break;
            }
            case LOCKED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.lockdatanotsupportoperation"));
            }
            case APPENDED: {
                this.table.removeRow(this);
                break;
            }
            case MODIFIED: {
                this.orginRow.state = DataRowState.UNUSED;
                System.arraycopy(this.orginRow.values, 0, this.values, 0, this.values.length);
                this.orginRow = null;
                this.state = DataRowState.NORMAL;
                this.modified = null;
                break;
            }
            case DELETED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.deldatanotsupportoperation"));
            }
            case UNUSED: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.datarowunable"));
            }
            default: {
                throw new DataException(BizBindingI18nUtil.getMessage("va.bizbinding.datarowimpl.datastatusempty"));
            }
        }
    }

    @Override
    public Map<String, Object> getUpdateData() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put(this.table.F_ID.getName(), this.getValue(this.table.F_ID.getIndex()));
        for (int i = 0; i < this.values.length; ++i) {
            if (!this.modified[i]) continue;
            DataFieldImpl field = this.table.getFields().get(i);
            map.put(field.getName(), this.getValue(i));
        }
        if (this.table.F_VERSION != null) {
            map.put(this.table.F_VERSION.getName(), this.getValue(this.table.F_VERSION.getIndex()));
        }
        return map;
    }

    @Override
    public Map<String, Object> getData() {
        return this.getData(Utils.isNotEmpty(this.table.data.getModel().getContext().getTriggerOrigin()));
    }

    @Override
    public List<Object> getFrontData() {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < this.values.length; ++i) {
            result.add(this.getViewValue(i));
        }
        result.add(this.unset);
        result.add(this.getState().name());
        return result;
    }

    public Map<String, Object> getData(boolean viewData) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        for (int i = 0; i < this.values.length; ++i) {
            DataFieldImpl field = this.table.getFields().get(i);
            Object value = viewData ? this.getViewValue(i) : this.getValue(i);
            map.put(field.getName(), value);
        }
        if (this.unset) {
            map.put("$UNSET", true);
        }
        return map;
    }

    @Override
    public void setData(Map<String, Object> data) {
        this.table.getFields().stream().forEach(o -> {
            Class<Void> value = data.getOrDefault(o.getName(), Void.class);
            if (value != Void.class) {
                this.setValue(o.getIndex(), value);
            }
        });
        this.unset = Convert.cast(data.get("$UNSET"), Boolean.TYPE);
    }

    public void setIncData(Map<Integer, Object> data) {
        this.table.getFields().stream().forEach(o -> {
            Class<Void> value = data.getOrDefault(o.getIndex() + "", Void.class);
            if (value != Void.class) {
                this.setValue(o.getIndex(), value);
            }
        });
        this.unset = Convert.cast(data.get("$UNSET"), Boolean.TYPE);
    }

    @Override
    public void setData(List<Object> data, List<Object> fieldNames) {
        this.table.getFields().stream().forEach(o -> {
            Object value;
            int fieldIndex = fieldNames.indexOf(o.getName());
            if (fieldIndex != -1 && (value = data.get(fieldIndex)) != null) {
                this.setValue(o.getIndex(), value);
            }
        });
        int unsetIndex = fieldNames.indexOf("$UNSET");
        this.unset = unsetIndex != -1 ? Convert.cast(data.get(unsetIndex), Boolean.TYPE) : false;
    }

    void setValues(Map<String, Object> data) {
        this.table.getFields().stream().forEach(o -> {
            Class<Void> value = data.getOrDefault(o.getName(), Void.class);
            if (value != Void.class) {
                this.values[o.getIndex()] = this.convertValue((DataFieldImpl)o, o.getDefine().getValueType(), value);
            }
        });
        this.unset = Convert.cast(data.get("$UNSET"), Boolean.TYPE);
    }

    @Override
    public void setDataWithCheck(Map<String, Object> data) {
        data.entrySet().stream().forEach(o -> this.setValueWithCheck((String)o.getKey(), o.getValue()));
        this.unset = Convert.cast(data.get("$UNSET"), Boolean.TYPE);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setDataWithCheck(List<Object> data, List<Object> fieldNames) {
        for (int i = 0; i < data.size(); ++i) {
            String fieldName = fieldNames.get(i).toString();
            DataFieldImpl field = this.table.getFields().get(fieldName);
            if (field.getDefine().isReadonly()) {
                return;
            }
            boolean listening = this.table.data.listening;
            Object value = data.get(i);
            Object oldVal = null;
            try {
                this.table.data.listening = false;
                try {
                    oldVal = this.values[field.getIndex()];
                    this.setValue(field.getIndex(), value);
                }
                catch (Exception e) {
                    logger.info(e.getMessage(), e);
                }
            }
            finally {
                this.table.data.listening = listening;
            }
            if (!listening) continue;
            this.table.data.afterSetValue(this.table, this, field, value, oldVal);
        }
        int unsetIndex = fieldNames.indexOf("$UNSET");
        this.unset = unsetIndex != -1 ? Convert.cast(data.get(unsetIndex), Boolean.TYPE) : false;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean isModified(String name) {
        if (this.state == DataRowState.APPENDED) {
            return true;
        }
        if (this.state != DataRowState.MODIFIED) {
            return false;
        }
        return this.modified[this.table.getFields().get(name).getIndex()];
    }

    @Override
    public boolean isModified(int index) {
        if (this.state == DataRowState.APPENDED) {
            return true;
        }
        if (this.state != DataRowState.MODIFIED) {
            return false;
        }
        return this.modified[this.table.getFields().get(index).getIndex()];
    }
}

