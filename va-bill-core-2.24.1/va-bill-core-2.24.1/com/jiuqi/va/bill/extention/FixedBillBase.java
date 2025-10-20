/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataDeclare
 *  com.jiuqi.va.biz.impl.data.DataFieldDeclare
 *  com.jiuqi.va.biz.impl.data.DataTableDeclare
 *  com.jiuqi.va.biz.intf.data.DataFieldType
 *  com.jiuqi.va.biz.intf.data.DataTableType
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.va.bill.extention;

import com.jiuqi.va.bill.impl.BillDeclare;
import com.jiuqi.va.bill.impl.BillTypeBase;
import com.jiuqi.va.bill.utils.TypeUtils;
import com.jiuqi.va.biz.impl.data.DataDeclare;
import com.jiuqi.va.biz.impl.data.DataFieldDeclare;
import com.jiuqi.va.biz.impl.data.DataTableDeclare;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableType;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.util.StringUtils;

public abstract class FixedBillBase
extends BillTypeBase {
    @Override
    public String[] getDependPlugins() {
        String[] dependPlugins = super.getDependPlugins();
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(dependPlugins));
        list.add("attachment");
        return list.toArray(new String[list.size()]);
    }

    @Override
    public final void declare(BillDeclare<?> billDeclare) {
        DataDeclare<BillDeclare<?>> dataDeclare = billDeclare.declareData();
        this.declareData(dataDeclare);
        dataDeclare.endDeclare();
    }

    protected Map<String, Set<String>> getSolidifiedFields() {
        return null;
    }

    protected abstract void declareData(DataDeclare<?> var1);

    protected final void declareMasterTable(DataDeclare<?> dataDeclare, DataModelDO dataModel, String tableTitle) {
        DataTableDeclare tableDeclare = dataDeclare.declareMasterTable();
        tableDeclare.setSolidified(true);
        this.declareTable(tableDeclare, dataModel, tableTitle).endDeclare();
    }

    protected final void declareDetailTable(DataDeclare<?> dataDeclare, String parentName, DataModelDO tableModel, String tableTitle) {
        DataTableDeclare tableDeclare = dataDeclare.declareDetailTable().setParentId(UUID.nameUUIDFromBytes(parentName.getBytes(Charset.forName("UTF-8"))));
        tableDeclare.setSolidified(true);
        this.declareTable(tableDeclare, tableModel, tableTitle).endDeclare();
    }

    protected final void declareAssistTable(DataDeclare<?> dataDeclare, String parentName, DataModelDO tableModel, String tableTitle) {
        DataTableDeclare tableDeclare = dataDeclare.declareAssistTable().setParentId(UUID.nameUUIDFromBytes(parentName.getBytes(Charset.forName("UTF-8"))));
        tableDeclare.setSolidified(true);
        this.declareTable(tableDeclare, tableModel, tableTitle).endDeclare();
    }

    protected final DataTableDeclare<?> declareTable(DataTableDeclare<?> tableDeclare, DataModelDO dataModel, String tableTitle) {
        tableDeclare.setId(UUID.nameUUIDFromBytes(dataModel.getName().getBytes(Charset.forName("UTF-8")))).setName(dataModel.getName()).setTitle(dataModel.getTitle()).setTableType(DataTableType.DATA).setTableName(dataModel.getName());
        Map<String, Set<String>> map = this.getSolidifiedFields();
        Set<String> fixedFields = map == null || !map.containsKey(dataModel.getName()) ? null : map.get(dataModel.getName());
        dataModel.getColumns().forEach(o -> {
            DataFieldDeclare fieldDeclare = tableDeclare.declareField();
            if (fixedFields != null && fixedFields.contains(o.getColumnName())) {
                fieldDeclare.setSolidified(true);
            } else {
                fieldDeclare.setSolidified(false);
            }
            this.declareField((DataFieldDeclare<?>)fieldDeclare, dataModel, (DataModelColumn)o).endDeclare();
        });
        return tableDeclare;
    }

    protected final DataFieldDeclare<?> declareField(DataFieldDeclare<?> fieldDeclare, DataModelDO dataModel, DataModelColumn dataModelColumn) {
        Integer mappingType;
        fieldDeclare.setId(UUID.nameUUIDFromBytes((dataModel.getName() + '.' + dataModelColumn.getColumnName()).getBytes(Charset.forName("UTF-8")))).setName(dataModelColumn.getColumnName()).setTitle(dataModelColumn.getColumnTitle()).setFieldType(DataFieldType.DATA).setFieldName(dataModelColumn.getColumnName()).setValueType(TypeUtils.map(dataModelColumn.getColumnType()));
        if (fieldDeclare.getValueType() == ValueType.DECIMAL) {
            fieldDeclare.setLength(dataModelColumn.getLengths()[0].intValue());
            fieldDeclare.setDigits(dataModelColumn.getLengths().length > 1 ? dataModelColumn.getLengths()[1] : 0);
            if (fieldDeclare.getDigits() == 0) {
                if (fieldDeclare.getLength() == 19) {
                    fieldDeclare.setValueType(ValueType.LONG);
                } else if (fieldDeclare.getLength() == 10) {
                    fieldDeclare.setValueType(ValueType.INTEGER);
                } else if (fieldDeclare.getLength() == 1) {
                    fieldDeclare.setValueType(ValueType.BOOLEAN);
                }
            }
        }
        if (fieldDeclare.getValueType() == ValueType.STRING) {
            fieldDeclare.setLength(dataModelColumn.getLengths()[0].intValue());
        }
        if ((mappingType = dataModelColumn.getMappingType()) != null) {
            if (DataModelType.ColumnType.INTEGER.equals((Object)dataModelColumn.getColumnType()) && mappingType == 0) {
                fieldDeclare.setValueType(ValueType.BOOLEAN);
            } else if (StringUtils.hasText(dataModelColumn.getMapping())) {
                fieldDeclare.setRefTableType(mappingType.intValue());
                String mapping = dataModelColumn.getMapping();
                String[] refs = mapping.split("\\.", -1);
                String refTableName = refs[refs.length - 2];
                fieldDeclare.setRefTableName(refTableName);
            }
        }
        return fieldDeclare;
    }
}

