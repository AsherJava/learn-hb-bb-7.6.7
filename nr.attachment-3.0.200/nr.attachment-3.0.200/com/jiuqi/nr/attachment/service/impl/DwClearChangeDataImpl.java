/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.datacrud.spi.IDwClearChangeDataListener
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.attachment.service.impl;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.attachment.asynctask.DeleteMarkFileTaskExecutor;
import com.jiuqi.nr.attachment.input.DeleteMarkFileInfo;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.datacrud.spi.IDwClearChangeDataListener;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DwClearChangeDataImpl
implements IDwClearChangeDataListener {
    private static final Logger logger = LoggerFactory.getLogger(DwClearChangeDataImpl.class);
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private FileOperationService fileOperationService;
    public static final String OPRATE_NAME = "\u9644\u4ef6\u6e05\u9664";

    public String name() {
        return OPRATE_NAME;
    }

    public void dataClear(String dataSchemeKey, String entityKeyData, String startPeriod, String endPeriod) {
        ArrayList<String> dws = new ArrayList<String>(Arrays.asList(entityKeyData));
        this.markDeletion(dataSchemeKey, dws, startPeriod, endPeriod);
        DeleteMarkFileInfo deleteMarkFileInfo = new DeleteMarkFileInfo();
        deleteMarkFileInfo.setDataSchemeKey(dataSchemeKey);
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)deleteMarkFileInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new DeleteMarkFileTaskExecutor());
        this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNCTASK_DELETE_MARKFILE");
    }

    public void dataClear(String dataSchemeKey, List<String> entityKeyData, String startPeriod, String endPeriod) {
        this.markDeletion(dataSchemeKey, entityKeyData, startPeriod, endPeriod);
        DeleteMarkFileInfo deleteMarkFileInfo = new DeleteMarkFileInfo();
        deleteMarkFileInfo.setDataSchemeKey(dataSchemeKey);
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)deleteMarkFileInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new DeleteMarkFileTaskExecutor());
        this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNCTASK_DELETE_MARKFILE");
    }

    private void markDeletion(String dataSchemeKey, List<String> dws, String startPeriod, String endPeriod) {
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(dataSchemeKey);
        String tableName = this.fileOperationService.getCurrentSplitTableName(dataScheme);
        TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(tableName);
        if (null == filePoolTable) {
            throw new NotFoundTableDefineException(new String[]{"\u9644\u4ef6\u5173\u8054\u8868\u672a\u521b\u5efa\uff0c\u8bf7\u91cd\u65b0\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff01"});
        }
        DataAccessContext context = new DataAccessContext(this.dataModelService);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
        int index = 0;
        for (ColumnModelDefine filePoolField : filePoolFields) {
            String rowFilter;
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("ISDELETE")) {
                index = filePoolFields.indexOf(filePoolField);
                continue;
            }
            if (filePoolField.getCode().equals("MDCODE")) {
                queryModel.getColumnFilters().put(filePoolField, dws);
                continue;
            }
            if (!filePoolField.getCode().equals("PERIOD")) continue;
            if (StringUtils.isNotEmpty((String)startPeriod) && StringUtils.isNotEmpty((String)endPeriod)) {
                rowFilter = " ( PERIOD >= " + startPeriod + " and " + "PERIOD" + " <= " + endPeriod + " ) ";
                queryModel.setFilter(rowFilter);
                continue;
            }
            if (StringUtils.isNotEmpty((String)startPeriod) && StringUtils.isEmpty((String)endPeriod)) {
                rowFilter = " ( PERIOD >= " + startPeriod + " ) ";
                queryModel.setFilter(rowFilter);
                continue;
            }
            if (!StringUtils.isEmpty((String)startPeriod) || !StringUtils.isNotEmpty((String)endPeriod)) continue;
            rowFilter = " ( PERIOD <= " + endPeriod + " ) ";
            queryModel.setFilter(rowFilter);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                row.setValue(index, (Object)"1");
            }
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }
}

