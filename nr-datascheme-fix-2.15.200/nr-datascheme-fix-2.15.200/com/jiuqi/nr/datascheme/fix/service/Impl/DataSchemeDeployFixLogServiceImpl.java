/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.exception.DBParaException
 */
package com.jiuqi.nr.datascheme.fix.service.Impl;

import com.jiuqi.nr.datascheme.exception.DBParaException;
import com.jiuqi.nr.datascheme.fix.dao.DeployFailFixLogDao;
import com.jiuqi.nr.datascheme.fix.entity.DeployFailFixLogDO;
import com.jiuqi.nr.datascheme.fix.service.IDataSchemeDeployFixLogService;
import com.jiuqi.nr.datascheme.fix.utils.DeployFixUtils;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSchemeDeployFixLogServiceImpl
implements IDataSchemeDeployFixLogService {
    @Autowired
    private DeployFailFixLogDao logDao;
    @Autowired
    private DeployFixUtils deployFixUtils;

    @Override
    public int insertFixLog(DeployFailFixLogDO failFixLog) {
        return this.logDao.insert(failFixLog);
    }

    @Override
    public List<DeployFailFixLogDO> getFixLogByTable(String tableKey) {
        List<DeployFailFixLogDO> fixLogs = this.logDao.getByTable(tableKey);
        return this.sorted(fixLogs);
    }

    @Override
    public DeployFailFixLogDO getNewestLogByTable(String tableKey) {
        return this.logDao.getNewestByTable(tableKey);
    }

    @Override
    public List<DeployFailFixLogDO> getFixLogByScheme(String dataSchemeKey) {
        List<DeployFailFixLogDO> fixLogs = this.logDao.getByDataScheme(dataSchemeKey);
        return this.sorted(fixLogs);
    }

    @Override
    public List<DeployFailFixLogDO> getFixLogByDsAndSelectedTime(Instant selectedTime, String dataSchemeKey) {
        return this.logDao.getByDataSchemeAndTime(dataSchemeKey, selectedTime);
    }

    @Override
    public DeployFailFixLogDO getFixLogByDtAndSelectedTime(Instant selectedTime, String dataTableKey) {
        return this.logDao.getByTimeAndTableKey(selectedTime, dataTableKey);
    }

    @Override
    public int updateFixLog(DeployFailFixLogDO fixLog) {
        return this.logDao.update(fixLog);
    }

    @Override
    public int deleteFixLog(DeployFailFixLogDO fixlog) throws DBParaException {
        return this.logDao.delete(fixlog);
    }

    int deleteFixLogs(List<DeployFailFixLogDO> fixLogs) {
        int ret = 0;
        for (DeployFailFixLogDO fixLog : fixLogs) {
            this.deleteFixLog(fixLog);
            ++ret;
        }
        return ret;
    }

    void dropBackUpTableByLog(List<DeployFailFixLogDO> fixLogs) throws Exception {
        for (DeployFailFixLogDO fixLog : fixLogs) {
            if (fixLog.getNewTableName() == null) continue;
            if (fixLog.getNewTableName().length > 1) {
                for (String tableName : fixLog.getNewTableName()) {
                    this.deployFixUtils.deleteLogicTable(tableName);
                }
                continue;
            }
            this.deployFixUtils.deleteLogicTable(fixLog.getNewTableName()[0]);
        }
    }

    void dropNewTable(String[] newTableNames) throws Exception {
        for (String tableName : newTableNames) {
            this.deployFixUtils.deleteLogicTable(tableName);
        }
    }

    @Override
    public void deleteAllBackUpTablesAndLogs() throws Exception {
        List<DeployFailFixLogDO> allFixLogs = this.logDao.getAllFixLogs();
        this.dropBackUpTableByLog(allFixLogs);
        this.deleteFixLogs(allFixLogs);
    }

    @Override
    public void deleteAllBackUpTables() throws Exception {
        List<DeployFailFixLogDO> fixLogs = this.logDao.getAllFixLogs();
        this.dropBackUpTableByLog(fixLogs);
        for (DeployFailFixLogDO fixLog : fixLogs) {
            fixLog.setNewTableName(null);
            this.updateFixLog(fixLog);
        }
    }

    @Override
    public int deleteAllLogs() {
        List<DeployFailFixLogDO> allFixLogs = this.logDao.getAllFixLogs();
        return this.deleteFixLogs(allFixLogs);
    }

    @Override
    public void deleteAllBackUpTablesByScheme(String dataSchemeKey) throws Exception {
        List<DeployFailFixLogDO> fixLogs = this.getFixLogByScheme(dataSchemeKey);
        this.dropBackUpTableByLog(fixLogs);
        for (DeployFailFixLogDO fixLog : fixLogs) {
            fixLog.setNewTableName(null);
            this.updateFixLog(fixLog);
        }
    }

    @Override
    public void deleteAllBackUpTablesAndLogsByScheme(String dataSchemeKey) throws Exception {
        List<DeployFailFixLogDO> fixLogs = this.getFixLogByScheme(dataSchemeKey);
        this.dropBackUpTableByLog(fixLogs);
        this.deleteFixLogs(fixLogs);
    }

    @Override
    public void deleteAllLogsByScheme(String dataSchemeKey) {
        List<DeployFailFixLogDO> fixLogs = this.getFixLogByScheme(dataSchemeKey);
        this.deleteFixLogs(fixLogs);
    }

    @Override
    public void deleteBackUpTablesBySchemeAndTime(Instant selectedTime, String dataSchemeKey) throws Exception {
        List<DeployFailFixLogDO> fixLogs = this.getFixLogByDsAndSelectedTime(selectedTime, dataSchemeKey);
        this.dropBackUpTableByLog(fixLogs);
        for (DeployFailFixLogDO fixLog : fixLogs) {
            fixLog.setNewTableName(null);
            this.updateFixLog(fixLog);
        }
    }

    @Override
    public void deleteBackUpTablesAndLogsBySchemeAndTime(Instant selectedTime, String dataSchemeKey) throws Exception {
        List<DeployFailFixLogDO> fixLogs = this.getFixLogByDsAndSelectedTime(selectedTime, dataSchemeKey);
        this.dropBackUpTableByLog(fixLogs);
        this.deleteFixLogs(fixLogs);
    }

    @Override
    public void deleteLogsBySchemeAndTime(Instant selectedTime, String dataSchemeKey) {
        List<DeployFailFixLogDO> fixLogs = this.getFixLogByDsAndSelectedTime(selectedTime, dataSchemeKey);
        this.deleteFixLogs(fixLogs);
    }

    @Override
    public void deleteBackUpTablesByTableAndTime(Instant selectedTime, String dataTableKey) throws Exception {
        DeployFailFixLogDO fixLog = this.getFixLogByDtAndSelectedTime(selectedTime, dataTableKey);
        if (fixLog.getNewTableName() != null) {
            this.dropNewTable(fixLog.getNewTableName());
        }
        fixLog.setNewTableName(null);
        this.updateFixLog(fixLog);
    }

    @Override
    public void deleteBackUpTablesAndLogsByTableAndTime(Instant selectedTime, String dataTableKey) throws Exception {
        DeployFailFixLogDO fixLog = this.getFixLogByDtAndSelectedTime(selectedTime, dataTableKey);
        if (fixLog.getNewTableName() != null) {
            this.dropNewTable(fixLog.getNewTableName());
        }
        this.logDao.delete(fixLog);
    }

    @Override
    public void deleteLogsByTableAndTime(Instant selectedTime, String dataTableKey) {
        DeployFailFixLogDO fixLog = this.getFixLogByDtAndSelectedTime(selectedTime, dataTableKey);
        this.logDao.delete(fixLog);
    }

    @Override
    public List<DeployFailFixLogDO> getAllFixLogs() {
        return this.logDao.getAllFixLogs();
    }

    private List<DeployFailFixLogDO> sorted(List<DeployFailFixLogDO> fixLogs) {
        return fixLogs.stream().sorted(Comparator.comparing(DeployFailFixLogDO::getDeployFailFixTime).reversed()).collect(Collectors.toList());
    }
}

