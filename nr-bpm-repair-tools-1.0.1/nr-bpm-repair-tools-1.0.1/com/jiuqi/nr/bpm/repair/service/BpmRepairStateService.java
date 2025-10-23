/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.bpm.repair.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.repair.jobs.env.BpmRepairToolsEnv;
import com.jiuqi.nr.bpm.repair.jobs.monitor.IBpmRepairTaskMonitor;
import com.jiuqi.nr.bpm.repair.jobs.temp.table.StateErrorRecordsTempTable;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class BpmRepairStateService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    DimensionUtil dimensionUtil;
    @Autowired
    IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    ProcessStateHistoryDao processStateHistoryDao;

    public void batchUpdateState(IBpmRepairTaskMonitor monitor, BpmRepairToolsEnv env, StateErrorRecordsTempTable errorRecords) {
        FormSchemeDefine formScheme = env.getFormScheme();
        String period = env.getPeriod();
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
        List<String> unitIds = this.queryUnits(errorRecords);
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)period);
        dim.setValue(dwMainDimName, unitIds);
        monitor.setJobProgress(5.0);
        List uploadStates = this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dim, null);
        if (uploadStates != null && !uploadStates.isEmpty()) {
            UploadStateNew uploadState = (UploadStateNew)uploadStates.stream().findAny().get();
            boolean forceUpload = uploadState.getActionStateBean() != null && uploadState.getActionStateBean().isForceUpload();
            this.processStateHistoryDao.updateUnitState(formScheme.getKey(), dim, uploadState.getTaskId(), uploadState.getPreEvent(), forceUpload);
            monitor.setJobProgress(5.0);
        }
    }

    private List<String> queryUnits(StateErrorRecordsTempTable errorRecords) {
        ArrayList<String> units = new ArrayList<String>();
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct ");
        sql.append(errorRecords.getMdCode());
        sql.append(" from ");
        sql.append(errorRecords.getTableName());
        this.jdbcTemplate.query(sql.toString(), rs -> {
            String unitCode = rs.getString(errorRecords.getMdCode());
            units.add(unitCode);
        });
        return units;
    }
}

