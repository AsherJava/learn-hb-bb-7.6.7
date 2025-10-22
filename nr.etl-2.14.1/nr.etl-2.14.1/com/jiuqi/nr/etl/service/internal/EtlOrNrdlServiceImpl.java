/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.etl.service.internal;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.etl.common.ETLConfigErrorEnum;
import com.jiuqi.nr.etl.common.ETLTask;
import com.jiuqi.nr.etl.common.EtlExecuteInfo;
import com.jiuqi.nr.etl.common.EtlExecuteParam;
import com.jiuqi.nr.etl.common.EtlInfo;
import com.jiuqi.nr.etl.common.EtlReturnInfo;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.common.ServeType;
import com.jiuqi.nr.etl.common.TreeNodeImpl;
import com.jiuqi.nr.etl.common.UniversalTask;
import com.jiuqi.nr.etl.service.IEtlOrNrdlService;
import com.jiuqi.nr.etl.service.IEtlService;
import com.jiuqi.nr.etl.service.INrDataIntegrationService;
import com.jiuqi.nr.etl.service.internal.ETLTaskExecutor;
import com.jiuqi.nr.etl.service.internal.EtlAsyncTaskErrorException;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nr.etl.service.internal.NrdlTaskExecutor;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtlOrNrdlServiceImpl
implements IEtlOrNrdlService {
    @Resource
    private IEtlService etlService;
    @Resource
    private INrDataIntegrationService nrdlService;
    @Autowired
    private EtlServeEntityDao etlServeEntityDao;

    @Override
    public int testLink(String url, String userName, String passWord) {
        if (this.etlService.testLink(url, userName, passWord)) {
            return 1;
        }
        if (this.nrdlService.testLink(url, userName, passWord)) {
            return 2;
        }
        return 0;
    }

    @Override
    public List<? extends UniversalTask> getAllTask() throws JQException {
        Optional<EtlServeEntity> etlServeEntity = this.etlServeEntityDao.getServerInfo();
        if (etlServeEntity.isPresent()) {
            if (etlServeEntity.get().getType() == ServeType.ETL) {
                return this.etlService.getAllTask();
            }
            if (etlServeEntity.get().getType() == ServeType.DATA_INTEGRATION) {
                return this.nrdlService.getAllTask();
            }
        } else {
            throw new JQException((ErrorEnum)ETLConfigErrorEnum.ETL_CONFIG_ERROR_ENUM);
        }
        return null;
    }

    @Override
    public UniversalTask findTaskByName(String taskName) {
        return this.etlService.findTaskByName(taskName);
    }

    @Override
    public EtlExecuteInfo execute(EtlExecuteParam param) throws JQException {
        Optional<EtlServeEntity> etlServeEntity = this.etlServeEntityDao.getServerInfo();
        if (etlServeEntity.isPresent()) {
            if (etlServeEntity.get().getType() == ServeType.ETL) {
                return this.etlService.execute(param);
            }
            if (etlServeEntity.get().getType() == ServeType.DATA_INTEGRATION) {
                return this.nrdlService.execute(param);
            }
        } else {
            throw new JQException((ErrorEnum)ETLConfigErrorEnum.ETL_CONFIG_ERROR_ENUM);
        }
        return null;
    }

    @Override
    public EtlExecuteInfo executePlanTask(String id, String param, String url, String userName, String passWord, Logger logger) throws JQException {
        Optional<EtlServeEntity> etlServeEntity = this.etlServeEntityDao.getServerInfo();
        if (etlServeEntity.isPresent()) {
            if (etlServeEntity.get().getType() == ServeType.ETL) {
                ETLTaskExecutor etlTaskExecutor = new ETLTaskExecutor();
                return etlTaskExecutor.execute(id, param, url, userName, passWord, logger);
            }
            if (etlServeEntity.get().getType() == ServeType.DATA_INTEGRATION) {
                NrdlTaskExecutor nrdlTaskExecutor = new NrdlTaskExecutor();
                return nrdlTaskExecutor.execute(id, param, url, userName, passWord, logger);
            }
        } else {
            throw new JQException((ErrorEnum)ETLConfigErrorEnum.ETL_CONFIG_ERROR_ENUM);
        }
        return null;
    }

    @Override
    public List<TreeNodeImpl> getReportTask() throws Exception {
        return this.etlService.getReportTask();
    }

    @Override
    public Map<String, EtlReturnInfo> ETLExecute(EtlInfo etlInfo, AsyncTaskMonitor asyncTaskMonito) {
        return this.etlService.ETLExecute(etlInfo, asyncTaskMonito);
    }

    @Override
    public Map<String, EtlReturnInfo> executeTask(EtlInfo etlInfo, AsyncTaskMonitor asyncTaskMonitor) throws JQException {
        String taskName = etlInfo.getEtlTaskKey();
        String taskGuid = null;
        Optional<EtlServeEntity> etlServeEntity = this.etlServeEntityDao.getServerInfo();
        if (etlServeEntity.isPresent()) {
            List<UniversalTask> taskList;
            if (etlServeEntity.get().getType() == ServeType.ETL) {
                taskList = this.etlService.getAllTask();
                for (UniversalTask task : taskList) {
                    if (!((ETLTask)task).getTaskName().equals(taskName)) continue;
                    taskGuid = ((ETLTask)task).getTaskGuid();
                    etlInfo.setEtlTaskKey(taskGuid);
                    return this.etlService.ETLExecute(etlInfo, asyncTaskMonitor);
                }
            }
            if (etlServeEntity.get().getType() == ServeType.DATA_INTEGRATION) {
                taskList = this.nrdlService.getAllTask();
                for (UniversalTask task : taskList) {
                    if (!task.getTaskName().equals(taskName)) continue;
                    taskGuid = task.getTaskGuid();
                    etlInfo.setEtlTaskKey(taskGuid);
                    return this.nrdlService.ETLExecute(etlInfo, asyncTaskMonitor);
                }
                String[] errorMsg = new String[]{"\u4efb\u52a1\u4e0d\u5b58\u5728"};
                throw new EtlAsyncTaskErrorException("500", errorMsg);
            }
        } else {
            throw new JQException((ErrorEnum)ETLConfigErrorEnum.ETL_CONFIG_ERROR_ENUM);
        }
        String[] errorMsg = new String[]{"\u4efb\u52a1\u4e0d\u5b58\u5728"};
        throw new EtlAsyncTaskErrorException("500", errorMsg);
    }
}

