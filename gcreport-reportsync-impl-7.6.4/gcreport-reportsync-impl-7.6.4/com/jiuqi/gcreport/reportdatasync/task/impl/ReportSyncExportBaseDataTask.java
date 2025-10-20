/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncExportTask
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.reportdatasync.task.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncExportTask;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncExportBaseDataTask
implements IReportSyncExportTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean match(ReportDataSyncParams dataSyncParam) {
        List baseDataList = dataSyncParam.getBaseDataTables();
        String conversionSystemId = dataSyncParam.getConversionSystemId();
        return !CollectionUtils.isEmpty((Collection)baseDataList) || !StringUtils.isEmpty((String)conversionSystemId);
    }

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        ArrayList<String> msgList = new ArrayList<String>();
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ReportDataSyncParams dataSyncParam = reportSyncExportTaskContext.getReportDataSyncParams();
        String filePath = rootFolder.getPath();
        try {
            Object dataList;
            if (!StringUtils.isEmpty((String)dataSyncParam.getConversionSystemId()) && !CollectionUtils.isEmpty(dataList = this.listAllData("MD_RATETYPE"))) {
                ReportDataSyncUtil.writeBase64("MD_RATETYPE", (List<? extends DefaultTableEntity>)dataList, filePath + "/MD_RATETYPE");
            }
            if (!CollectionUtils.isEmpty((Collection)dataSyncParam.getBaseDataTables())) {
                for (String baseDataTable : dataSyncParam.getBaseDataTables()) {
                    if (!StringUtils.isEmpty((String)dataSyncParam.getConversionSystemId()) && baseDataTable.equals("MD_RATETYPE")) continue;
                    List<DefinitionValueV> dataList2 = this.listAllData(baseDataTable);
                    ReportDataSyncUtil.writeBase64(baseDataTable, dataList2, filePath + "/" + baseDataTable);
                }
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            msgList.add(e.getMessage());
        }
        return msgList;
    }

    public List<DefinitionValueV> listAllData(String tableName) {
        String sql = "select %1$s  from " + tableName + " t ";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"t");
        String formatSQL = String.format(sql, columns);
        return EntNativeSqlDefaultDao.newInstance((String)tableName, DefinitionValueV.class).selectEntity(formatSQL, new Object[0]);
    }

    public String funcTitle() {
        return "\u57fa\u7840\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.PARAM;
    }
}

