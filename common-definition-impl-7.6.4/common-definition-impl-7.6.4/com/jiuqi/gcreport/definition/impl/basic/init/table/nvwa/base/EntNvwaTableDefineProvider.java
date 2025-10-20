/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.nvwa.base;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntFieldDefine;
import com.jiuqi.gcreport.definition.impl.basic.base.provider.impl.EntTableDefine;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.intf.IEntTableDefineProvider;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EntNvwaTableDefineProvider
implements IEntTableDefineProvider {
    @Autowired
    private DataModelService service;

    @Override
    public EntTableDefine getTableDefine(String tableName) {
        String dataSource = EntityTableCollector.getInstance().getDataSourceByName(tableName);
        TableModelDefine table = this.service.getTableModelDefineByName(tableName, dataSource);
        if (table != null) {
            List columns = this.service.getColumnModelDefinesByTable(table.getID());
            EntTableDefine etable = this.convert(table, columns);
            return etable;
        }
        return null;
    }

    @Override
    public EntTableDefine getTableDefineByType(Class<? extends BaseEntity> type) {
        DBTable dbTable = type.getAnnotation(DBTable.class);
        if (dbTable == null) {
            throw new BusinessRuntimeException("" + type.getName() + "\u6ca1\u6709\u8868\u5b9a\u4e49\u6ce8\u89e3\uff0c\u65e0\u6cd5\u52a0\u8f7d\u5176\u8868\u5b9a\u4e49\u3002");
        }
        return this.getTableDefine(dbTable.name().toUpperCase());
    }

    private EntTableDefine convert(TableModelDefine define, List<ColumnModelDefine> columns) {
        EntTableDefine table = new EntTableDefine();
        table.setCode(define.getCode());
        table.setName(define.getName());
        table.setTitle(define.getTitle());
        table.setBizKey(define.getBizKeys());
        table.setType(0);
        if (columns != null) {
            columns.forEach(c -> this.convert(table, (ColumnModelDefine)c));
        }
        return table;
    }

    private EntFieldDefine convert(EntTableDefine table, ColumnModelDefine define) {
        EntFieldDefine field = new EntFieldDefine();
        field.setCode(define.getCode());
        field.setName(define.getName());
        field.setTitle(define.getTitle());
        field.setPrecision(define.getPrecision());
        field.setScale(define.getDecimal());
        field.setOrder(define.getOrder());
        field.setType(define.getColumnType().getValue());
        field.setTableName(table.getName());
        if (!StringUtils.isEmpty(define.getReferTableID())) {
            TableModelDefine refTable = this.service.getTableModelDefineById(define.getReferTableID());
            if (refTable == null) {
                throw new BusinessRuntimeException("\u8868\u3010" + table.getCode() + "\u3011,\u5b57\u6bb5\u3010" + define.getCode() + "\u3011\u5173\u8054\u8868\u4e22\u5931\u3002");
            }
            field.setRefTable(refTable.getName());
            if (!StringUtils.isEmpty(define.getReferColumnID())) {
                ColumnModelDefine refField = this.service.getColumnModelDefineByID(define.getReferColumnID());
                if (refField == null) {
                    throw new BusinessRuntimeException("\u8868\u3010" + table.getCode() + "\u3011,\u5b57\u6bb5\u3010" + define.getCode() + "\u3011\u5173\u8054\u5b57\u6bb5\u4e22\u5931\u3002");
                }
                field.setRefField(refField.getName());
            }
        }
        table.addField(field);
        return field;
    }
}

