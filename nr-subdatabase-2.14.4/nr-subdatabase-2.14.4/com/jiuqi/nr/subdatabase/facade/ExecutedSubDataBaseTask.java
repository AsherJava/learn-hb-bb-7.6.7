/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskStepMonitorImpl
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  com.jiuqi.va.organization.dao.VaOrgVersionDao
 *  com.jiuqi.va.organization.service.OrgCategoryService
 */
package com.jiuqi.nr.subdatabase.facade;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskStepMonitorImpl;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nr.subdatabase.facade.SubDataBaseTaskParam;
import com.jiuqi.nr.subdatabase.provider.SubDataBaseCustomSystemTableProvider;
import com.jiuqi.nr.subdatabase.provider.SubDataBaseCustomTableProvider;
import com.jiuqi.nr.subdatabase.service.SubDataBaseService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgVersionClient;
import com.jiuqi.va.organization.dao.VaOrgVersionDao;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ExecutedSubDataBaseTask", groupTitle="\u5e76\u884c\u6267\u884c\u65b0\u5efa\u5206\u5e93\u5b50\u4efb\u52a1")
public class ExecutedSubDataBaseTask
extends NpRealTimeTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SubDataBaseTaskParam.class);

    public void execute(JobContext jobContext) throws JobExecutionException {
        SubDataBaseTaskParam param;
        Map params = jobContext.getRealTimeJob().getParams();
        AsyncTaskStepMonitorImpl monitor = new AsyncTaskStepMonitorImpl(jobContext);
        monitor.startTask(String.format("\u7ebf\u7a0b[%s]\u6267\u884c\u65b0\u5efa\u5206\u5e93\u4efb\u52a1", Thread.currentThread().getName()), 1);
        if (Objects.isNull(params) && Objects.isNull(params.get("NR_ARGS"))) {
            monitor.finishTask(String.format("\u7ebf\u7a0b[%s]\u6267\u884c\u65b0\u5efa\u5206\u5e93\u4efb\u52a1", Thread.currentThread().getName()), "failed", "noparams");
        }
        if ((param = (SubDataBaseTaskParam)SimpleParamConverter.SerializationUtils.deserialize((String)((String)params.get("NR_ARGS")))).getDataScheme().booleanValue()) {
            try {
                SubDataBaseService subDataBaseService = (SubDataBaseService)BeanUtil.getBean(SubDataBaseService.class);
                subDataBaseService.createAndDeploy(param.getTableModelKey(), param.getSubDataBase().getCode(), param.getSubDataBase().getDataScheme());
                monitor.finishTask(String.format("\u7ebf\u7a0b[%s]\u6267\u884c\u65b0\u5efa\u5206\u5e93\u4efb\u52a1", Thread.currentThread().getName()), "success", "");
            }
            catch (Exception e) {
                monitor.finishTask(String.format("\u7ebf\u7a0b[%s]\u6267\u884c\u65b0\u5efa\u5206\u5e93\u4efb\u52a1", Thread.currentThread().getName()), "failed", e.getMessage());
                logger.error("\u65b0\u5efa\u5206\u5e93\u51fa\u9519", e);
                throw new RuntimeException(e);
            }
        }
        try {
            Map<String, SubDataBaseCustomSystemTableProvider> customSystemTableProviders;
            Map<String, SubDataBaseCustomTableProvider> customTableProviders = BeanUtil.getApplicationContext().getBeansOfType(SubDataBaseCustomTableProvider.class);
            if (!customTableProviders.isEmpty()) {
                for (SubDataBaseCustomTableProvider customTableProvider : customTableProviders.values()) {
                    customTableProvider.createCustomTable(param.getTaskKey(), param.getSubDataBase());
                }
            }
            if (!(customSystemTableProviders = BeanUtil.getApplicationContext().getBeansOfType(SubDataBaseCustomSystemTableProvider.class)).isEmpty()) {
                for (SubDataBaseCustomSystemTableProvider customSystemTableProvider : customSystemTableProviders.values()) {
                    customSystemTableProvider.createSystemCustomTable(param.getSubDataBase());
                }
            }
            if (param.isCreateOrgCateGory()) {
                param.getOrgCategoryDO().setId(null);
                param.getOrgCategoryDO().setName(param.getOrgCateGoryName() + param.getSubDataBase().getCode());
                OrgCategoryService orgCategoryService = (OrgCategoryService)BeanUtil.getBean(OrgCategoryService.class);
                R rs = orgCategoryService.add(param.getOrgCategoryDO());
                if (rs.getCode() != 0) {
                    throw new RuntimeException("Failed to CREATE ORGCATEGORY");
                }
                ExecutedSubDataBaseTask.copyOrgVersion(param.getSubDataBase().getCode(), param.getOrgCateGoryName());
            }
            monitor.finishTask(String.format("\u7ebf\u7a0b[%s]\u6267\u884c\u65b0\u5efa\u5206\u5e93\u4efb\u52a1", Thread.currentThread().getName()), "success", "");
        }
        catch (Exception e) {
            monitor.finishTask(String.format("\u7ebf\u7a0b[%s]\u6267\u884c\u65b0\u5efa\u5206\u5e93\u4efb\u52a1", Thread.currentThread().getName()), "failed", e.getMessage());
            logger.error("\u5206\u5e93\u65b0\u5efa\u4efb\u52a1\u76f8\u5173\u8868\u5931\u8d25\uff1a", e);
            throw new RuntimeException(e);
        }
    }

    public static void copyOrgVersion(String suffix, String defaultOrgCateGoryName) {
        OrgVersionDO defaultVersion = new OrgVersionDO();
        defaultVersion.setCategoryname(defaultOrgCateGoryName + suffix);
        VaOrgVersionDao orgVersionDao = (VaOrgVersionDao)BeanUtil.getBean(VaOrgVersionDao.class);
        orgVersionDao.delete((Object)defaultVersion);
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(defaultOrgCateGoryName);
        OrgVersionClient orgVersionClient = (OrgVersionClient)BeanUtil.getBean(OrgVersionClient.class);
        PageVO pageVO = orgVersionClient.list(param);
        List list = pageVO.getRows();
        for (OrgVersionDO orgVersionDO : list) {
            orgVersionDO.setId(UUID.randomUUID());
            orgVersionDO.setCategoryname(defaultOrgCateGoryName + suffix);
            orgVersionDao.insert((Object)orgVersionDO);
        }
    }
}

