/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.DataModelExchangeTask
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelIndex
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 */
package com.jiuqi.va.datamodel.exchange.task;

import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelFieldDefine;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelIndexDefine;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelTableDefine;
import com.jiuqi.va.datamodel.exchange.nvwa.init.DeployEntiyViewBase;
import com.jiuqi.va.domain.common.DataModelExchangeTask;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelIndex;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Va2NpDataModelExchangeTask
implements DataModelExchangeTask {
    @Autowired(required=false)
    private List<DeployEntiyViewBase> entiyViewServices;

    public void publish(DataModelDO dataModelDO) {
        DataModelTableDefine dmtd = this.convert(dataModelDO);
        if (this.entiyViewServices != null && this.entiyViewServices.size() > 0) {
            this.entiyViewServices.forEach(entiyViewService -> {
                try {
                    entiyViewService.publishTable(dmtd);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private DataModelTableDefine convert(DataModelDO vdata) {
        List vIndexs;
        String name;
        DataModelTableDefine dmtd = null;
        dmtd = vdata.getBiztype() == DataModelType.BizType.BASEDATA ? ((name = vdata.getName().toUpperCase()).equals("MD_ORG") || name.startsWith("MD_ORG_") ? new DataModelTableDefine(vdata, new String[]{"CODE"}) : new DataModelTableDefine(vdata, new String[]{"OBJECTCODE"})) : new DataModelTableDefine(vdata, new String[]{"ID"});
        List columns = vdata.getColumns();
        if (columns != null) {
            int sort = 0;
            for (DataModelColumn col : columns) {
                DataModelFieldDefine field = new DataModelFieldDefine(col.getColumnName().trim(), col.getColumnTitle());
                field.setTableName(vdata.getName());
                field.setOrder(String.format("%05d", ++sort));
                this.convertColumnType(field, col.getColumnType(), col.getLengths());
                field.setNullable(col.isNullable() == null ? true : col.isNullable());
                field.DEFAULT(col.getDefaultVal());
                if (col.getMappingType() != null) {
                    field.MAPPING(col.getMapping(), col.getMappingType());
                }
                if (col.isPkey() != null && col.isPkey().booleanValue()) {
                    dmtd.addPkField(field);
                    continue;
                }
                dmtd.addField(field);
            }
        }
        if ((vIndexs = vdata.getIndexConsts()) != null) {
            for (DataModelIndex vIndex : vIndexs) {
                DataModelIndexDefine index = new DataModelIndexDefine(vIndex.getIndexName());
                index.Columns(vIndex.getColumnList());
                if (vIndex.isUnique() == null) {
                    index.setIndexType(0);
                } else {
                    index.setIndexType(vIndex.isUnique() != false ? 2 : 0);
                }
                dmtd.addIndex(index);
            }
        }
        return dmtd;
    }

    private void convertColumnType(DataModelFieldDefine field, DataModelType.ColumnType columnType, Integer ... lengths) {
        if (DataModelType.ColumnType.UUID.equals((Object)columnType)) {
            field.NVARCHAR(36);
        } else if (DataModelType.ColumnType.CLOB.equals((Object)columnType)) {
            field.CLOB();
        } else if (DataModelType.ColumnType.INTEGER.equals((Object)columnType)) {
            field.INTEGER(lengths == null ? 10 : lengths[0]);
        } else if (DataModelType.ColumnType.NVARCHAR.equals((Object)columnType)) {
            field.NVARCHAR(lengths == null ? 100 : lengths[0]);
        } else if (DataModelType.ColumnType.NUMERIC.equals((Object)columnType)) {
            if (lengths == null) {
                lengths = new Integer[]{19, 4};
            }
            field.NUMERIC(lengths[0], lengths[1]);
        } else if (DataModelType.ColumnType.TIMESTAMP.equals((Object)columnType)) {
            field.TIMESTAMP();
        } else if (DataModelType.ColumnType.DATE.equals((Object)columnType)) {
            field.DATE();
        }
    }
}

