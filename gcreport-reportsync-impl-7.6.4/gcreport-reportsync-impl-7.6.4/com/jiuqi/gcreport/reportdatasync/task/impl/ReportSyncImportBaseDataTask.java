/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO
 *  com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.ShiroUtil
 */
package com.jiuqi.gcreport.reportdatasync.task.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionValueV;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncImportBaseDataTask
implements IReportSyncImportTask {
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private BaseDataCacheService baseDataCacheService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File[] files;
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        ArrayList<String> msgList = new ArrayList<String>();
        for (File file : files = rootFolder.listFiles()) {
            if (!file.getName().startsWith("MD_")) continue;
            msgList.add(this.importBaseData(file));
        }
        return msgList;
    }

    private String importBaseData(File file) {
        try {
            DataModelService modelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine tableModel = modelService.getTableModelDefineByName(file.getName());
            this.deleteAllData(file.getName());
            int num = ReportDataSyncUtil.readBase64Db(file.getPath(), file.getName());
            this.refreshCache(file.getName());
            return String.format("%1$s[%2$s]\u6210\u529f\u5bfc\u5165%3$s\u884c", tableModel.getTitle(), file.getName(), num);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return e.getMessage();
        }
    }

    private void refreshCache(String tablename) {
        String tenantName = ShiroUtil.getTenantName();
        BaseDataDTO param = new BaseDataDTO();
        param.setTenantName(tenantName);
        param.setTableName(tablename);
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(tenantName);
        bdsc.setBaseDataDTO(param);
        bdsc.setForceUpdate(true);
        this.baseDataCacheService.pushSyncMsg(bdsc);
    }

    public void deleteAllData(String tableName) {
        String sql = "delete  from " + tableName + " ";
        String columns = SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"");
        String formatSQL = String.format(sql, columns);
        EntNativeSqlDefaultDao.newInstance((String)tableName, DefinitionValueV.class).execute(formatSQL);
    }

    public String funcTitle() {
        return "\u57fa\u7840\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.PARAM;
    }
}

