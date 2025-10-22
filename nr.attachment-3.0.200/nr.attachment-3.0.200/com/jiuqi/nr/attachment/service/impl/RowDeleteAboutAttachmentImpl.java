/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.event.DeleteAllRowEvent
 *  com.jiuqi.np.dataengine.event.DeleteRowEvent
 *  com.jiuqi.np.dataengine.event.InsertRowEvent
 *  com.jiuqi.np.dataengine.event.OperateRowEventHandler
 *  com.jiuqi.np.dataengine.event.RevokeRowEvent
 *  com.jiuqi.np.dataengine.event.UpdateRowEvent
 *  com.jiuqi.np.dataengine.exception.OperateRowException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.event.DeleteAllRowEvent;
import com.jiuqi.np.dataengine.event.DeleteRowEvent;
import com.jiuqi.np.dataengine.event.InsertRowEvent;
import com.jiuqi.np.dataengine.event.OperateRowEventHandler;
import com.jiuqi.np.dataengine.event.RevokeRowEvent;
import com.jiuqi.np.dataengine.event.UpdateRowEvent;
import com.jiuqi.np.dataengine.exception.OperateRowException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.HashMap;
import java.util.HashSet;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RowDeleteAboutAttachmentImpl
implements OperateRowEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(RowDeleteAboutAttachmentImpl.class);
    @Autowired
    public IColumnModelFinder columnModelFinder;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private FilePoolService filePoolService;

    public void beforeDelete(DeleteRowEvent deleteRowEvent) {
        boolean isAttachment = false;
        try {
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            for (ColumnModelDefine columnModelDefine : deleteRowEvent.getAllFields()) {
                if (!columnModelDefine.getColumnType().equals((Object)ColumnModelType.ATTACHMENT)) continue;
                isAttachment = true;
                FieldDefine fieldDefine = this.columnModelFinder.findFieldDefine(columnModelDefine);
                dataQuery.addColumn(fieldDefine);
            }
            if (isAttachment) {
                HashSet<String> groupKeys = new HashSet<String>();
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                dataQuery.setMasterKeys((DimensionValueSet)deleteRowEvent.getDeleteRows().get(0));
                IDataTable dataTable = dataQuery.executeQuery(executorContext);
                for (int i = 0; i < dataTable.getTotalCount(); ++i) {
                    for (int j = 0; j < dataQuery.getColumnSize(); ++j) {
                        String groupKey = dataTable.getItem(i).getAsString(j);
                        if (!StringUtils.isNotEmpty((String)groupKey)) continue;
                        groupKeys.add(groupKey);
                    }
                }
                this.filePoolService.markDelFileByGroupKey(groupKeys);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void afterDelete(DeleteRowEvent deleteRowEvent) throws OperateRowException {
    }

    public void beforeAllDelete(DeleteAllRowEvent deleteAllRowEvent) {
        boolean isAttachment = false;
        try {
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            for (ColumnModelDefine columnModelDefine : deleteAllRowEvent.getAllFields()) {
                if (!columnModelDefine.getColumnType().equals((Object)ColumnModelType.ATTACHMENT)) continue;
                isAttachment = true;
                FieldDefine fieldDefine = this.columnModelFinder.findFieldDefine(columnModelDefine);
                dataQuery.addColumn(fieldDefine);
            }
            if (isAttachment) {
                HashSet<String> groupKeys = new HashSet<String>();
                ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
                dataQuery.setMasterKeys(deleteAllRowEvent.getMasterKeys());
                IDataTable dataTable = dataQuery.executeQuery(executorContext);
                for (int i = 0; i < dataTable.getTotalCount(); ++i) {
                    for (int j = 0; j < dataQuery.getColumnSize(); ++j) {
                        String groupKey = dataTable.getItem(i).getAsString(j);
                        if (!StringUtils.isNotEmpty((String)groupKey)) continue;
                        groupKeys.add(groupKey);
                    }
                }
                this.filePoolService.markDelFileByGroupKey(groupKeys);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void beforeUpdate(UpdateRowEvent updateRowEvent) {
        HashSet<String> groupKeys = new HashSet<String>();
        for (int i = 0; i < updateRowEvent.getUpdateRows().size(); ++i) {
            DataRowImpl dataRow = (DataRowImpl)updateRowEvent.getUpdateRows().get(i);
            HashMap modifiedDatas = dataRow.getModifiedDatas();
            IFieldsInfo fieldsInfo = ((IDataRow)updateRowEvent.getUpdateRows().get(i)).getFieldsInfo();
            for (Integer integer : modifiedDatas.keySet()) {
                String groupKey;
                FieldDefine fieldDefine;
                if (null != modifiedDatas.get(integer) || !(fieldDefine = fieldsInfo.getFieldDefine(integer.intValue())).getType().equals((Object)FieldType.FIELD_TYPE_FILE) && !fieldDefine.getType().equals((Object)FieldType.FIELD_TYPE_PICTURE) || !StringUtils.isNotEmpty((String)(groupKey = (String)dataRow.getRowDatas().get(integer)))) continue;
                groupKeys.add(groupKey);
            }
        }
        this.filePoolService.markDelFileByGroupKey(groupKeys);
    }

    public void afterUpdate(UpdateRowEvent updateRowEvent) throws OperateRowException {
    }

    public void beforeInsert(InsertRowEvent insertRowEvent) {
    }

    public void afterInsert(InsertRowEvent insertRowEvent) throws OperateRowException {
    }

    public void afterRevoke(RevokeRowEvent revokeRowEvent) {
    }
}

