/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.midstore.dataexchange.Config
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeEngineFactory
 *  com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException
 *  com.jiuqi.bi.core.midstore.dataexchange.IDataExchangeEngine
 *  com.jiuqi.bi.core.midstore.dataexchange.model.DETaskInfo
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask
 *  com.jiuqi.bi.core.midstore.dataexchange.services.IFileDataExchangeEngine
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.mapping.service.MappingSchemeService
 */
package nr.midstore.core.internal.publish.service;

import com.jiuqi.bi.core.midstore.dataexchange.Config;
import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeEngineFactory;
import com.jiuqi.bi.core.midstore.dataexchange.DataExchangeException;
import com.jiuqi.bi.core.midstore.dataexchange.IDataExchangeEngine;
import com.jiuqi.bi.core.midstore.dataexchange.model.DETaskInfo;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeProvider;
import com.jiuqi.bi.core.midstore.dataexchange.services.IDataExchangeTask;
import com.jiuqi.bi.core.midstore.dataexchange.services.IFileDataExchangeEngine;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import java.util.UUID;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.common.PublishStateType;
import nr.midstore.core.definition.common.StorageModeType;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.publish.service.MidstoreDataExchangeProvider;
import nr.midstore.core.param.service.IMidstoreCheckParamService;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import nr.midstore.core.publish.service.IMidstorePublishBaseDataService;
import nr.midstore.core.publish.service.IMidstorePublishFieldService;
import nr.midstore.core.publish.service.IMidstorePublishOrgDataService;
import nr.midstore.core.publish.service.IMidstorePublishTaskService;
import nr.midstore.core.work.service.org.IMidstoreOrgDataWorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstorePublishTaskServiceImpl
implements IMidstorePublishTaskService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishTaskServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstorePublishFieldService fieldPublishService;
    @Autowired
    private IMidstorePublishOrgDataService orgDataPublishService;
    @Autowired
    private IMidstorePublishBaseDataService baseDataPublishService;
    @Autowired
    private MappingSchemeService mappingService;
    @Autowired
    private IMidstoreCheckParamService checkParamService;
    @Autowired
    private IMistoreExchangeTaskService exchangeTaskService;
    @Autowired
    private IMidstoreOrgDataWorkService orgDataWorkService;

    @Override
    public MidstoreResultObject publishTask(String midstoreSchemeId, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreSchemeDTO midstoreScheme = this.midstoreSchemeSevice.getByKey(midstoreSchemeId);
        if (midstoreScheme != null) {
            MidstoreContext context;
            MidstoreResultObject checkResult;
            if (monitor != null) {
                monitor.progressAndMessage(0.1, "\u8bfb\u53d6\u53c2\u6570");
            }
            if ((checkResult = this.checkParamService.doCheckParamsBeforePulish(context = this.getContext(midstoreSchemeId, monitor))) != null && !checkResult.isSuccess()) {
                return new MidstoreResultObject(false, "\u53c2\u6570\u68c0\u67e5\u6709\u5f02\u5e38:" + checkResult.getMessage());
            }
            try {
                this.publishMidsotreScheme(context, monitor);
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                return new MidstoreResultObject(false, "\u53d1\u5e03\u6709\u5f02\u5e38:" + ex.getMessage());
            }
            if (monitor != null) {
                monitor.progressAndMessage(1.0, "\u53d1\u5e03\u5b8c\u6210");
            }
            logger.info("\u53d1\u5e03\u5b8c\u6210");
            return new MidstoreResultObject(true, "\u53d1\u5e03\u5b8c\u6210", midstoreSchemeId);
        }
        throw new MidstoreException("\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff01");
    }

    private MidstoreContext getContext(String midstoreSchemeId, AsyncTaskMonitor monitor) {
        MidstoreContext context = new MidstoreContext();
        context.setSchemeKey(midstoreSchemeId);
        context.setAsyncMonitor(monitor);
        context.setMidstoreScheme(this.midstoreSchemeSevice.getByKey(midstoreSchemeId));
        context.setSchemeInfo(this.schemeInfoSevice.getBySchemeKey(midstoreSchemeId));
        return context;
    }

    private void publishMidsotreScheme(MidstoreContext context, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreSchemeDTO midstoreScheme = context.getMidstoreScheme();
        MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoSevice.getBySchemeKey(midstoreScheme.getKey());
        if (schemeInfo == null) {
            schemeInfo = new MidstoreSchemeInfoDTO();
            schemeInfo.setSchemeKey(midstoreScheme.getKey());
            schemeInfo.setAllOrgData(true);
            schemeInfo.setAllOrgField(true);
            schemeInfo.setAutoClean(false);
            schemeInfo.setPublishState(PublishStateType.PUBLISHSTATE_NONE);
            this.schemeInfoSevice.add(schemeInfo);
        }
        if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_DATABASE) {
            if (StringUtils.isEmpty((String)midstoreScheme.getDataBaseLink())) {
                throw new MidstoreException("\u672a\u5b9a\u4e49\u6570\u636e\u5e93\u94fe\u63a5\uff01");
            }
            try {
                MidstoreDataExchangeProvider provider;
                MidstoreDataExchangeProvider provider2 = provider = new MidstoreDataExchangeProvider(midstoreScheme.getDataBaseLink());
                if (provider2.getDataSource() == null) {
                    throw new MidstoreException("\u83b7\u53d6\u6570\u636e\u5e93\u94fe\u63a5\u5931\u8d25," + midstoreScheme.getDataBaseLink());
                }
                IDataExchangeEngine dbEngine = DataExchangeEngineFactory.getInstance().getDBEngine((IDataExchangeProvider)provider);
                dbEngine.initDb(false);
                if (StringUtils.isEmpty((String)schemeInfo.getExchangeTaskKey())) {
                    schemeInfo.setExchangeTaskKey(UUID.randomUUID().toString());
                    this.schemeInfoSevice.update(schemeInfo);
                }
                DETaskInfo deTaskInfo = new DETaskInfo(schemeInfo.getExchangeTaskKey(), midstoreScheme.getTablePrefix());
                deTaskInfo.setTitle(midstoreScheme.getTitle());
                deTaskInfo.setDescription(midstoreScheme.getTitle());
                IDataExchangeTask dataExchangeTask = dbEngine.openTask(deTaskInfo, this.getMidstoreConfig(midstoreScheme), false);
                if (monitor != null) {
                    monitor.progressAndMessage(0.2, "\u68c0\u67e5\u53c2\u6570");
                }
                this.checkMidstoreParam(midstoreScheme, dataExchangeTask, monitor);
                if (monitor != null) {
                    monitor.progressAndMessage(0.3, "\u53d1\u5e03\u53c2\u6570");
                }
                this.publishToMidstore(context, dataExchangeTask, monitor);
                logger.info("\u4e2d\u95f4\u5e93\u53d1\u5e03\u7269\u7406\u8868");
                dataExchangeTask.publish();
                logger.info("\u5199\u5165\u6570\u636e");
                this.sateDataToMidstore(context, dataExchangeTask, monitor);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage(), e);
            }
        }
        if (midstoreScheme.getStorageMode() == StorageModeType.STOREMODE_FILE) {
            if (StringUtils.isEmpty((String)midstoreScheme.getStorageInfo())) {
                throw new MidstoreException("\u672a\u5b9a\u4e49\u6587\u4ef6\u8def\u5f84\uff01");
            }
            try {
                IFileDataExchangeEngine fileEngine = DataExchangeEngineFactory.getInstance().getFileEngine(midstoreScheme.getStorageInfo());
                fileEngine.initDb(false);
                if (StringUtils.isEmpty((String)schemeInfo.getExchangeTaskKey())) {
                    schemeInfo.setExchangeTaskKey(UUID.randomUUID().toString());
                    this.schemeInfoSevice.update(schemeInfo);
                }
                DETaskInfo deTaskInfo = new DETaskInfo(schemeInfo.getExchangeTaskKey(), midstoreScheme.getTablePrefix());
                deTaskInfo.setTitle(midstoreScheme.getTitle());
                deTaskInfo.setDescription(midstoreScheme.getTitle());
                IDataExchangeTask dataExchangeTask = fileEngine.openTask(deTaskInfo, this.getMidstoreConfig(midstoreScheme), false);
                logger.info("\u68c0\u67e5\u53c2\u6570");
                if (monitor != null) {
                    monitor.progressAndMessage(0.2, "\u68c0\u67e5\u53c2\u6570");
                }
                this.checkMidstoreParam(midstoreScheme, dataExchangeTask, monitor);
                logger.info("\u53d1\u5e03\u53c2\u6570");
                if (monitor != null) {
                    monitor.progressAndMessage(0.3, "\u53d1\u5e03\u53c2\u6570");
                }
                this.publishToMidstore(context, dataExchangeTask, monitor);
                logger.info("\u4e2d\u95f4\u5e93\u53d1\u5e03\u7269\u7406\u8868");
                dataExchangeTask.publish();
                logger.info("\u5199\u5165\u6570\u636e");
                this.sateDataToMidstore(context, dataExchangeTask, monitor);
            }
            catch (DataExchangeException e) {
                logger.error(e.getMessage(), e);
                throw new MidstoreException(e.getMessage());
            }
        }
        schemeInfo.setPublishState(PublishStateType.PUBLISHSTATE_SUCCESS);
        this.schemeInfoSevice.update(schemeInfo);
    }

    private Config getMidstoreConfig(MidstoreSchemeDTO midstoreScheme) {
        return this.exchangeTaskService.getExchangeConfig(midstoreScheme);
    }

    private void checkMidstoreParam(MidstoreSchemeDTO midstoreScheme, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
    }

    public void publishToMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        logger.info("\u53d1\u5e03\u7ec4\u7ec7\u673a\u6784\u8868");
        if (monitor != null) {
            monitor.progressAndMessage(0.4, "\u53d1\u5e03\u7ec4\u7ec7\u673a\u6784\u8868");
        }
        this.publishOrgDatas(context, dataExchangeTask, monitor);
        logger.info("\u53d1\u5e03\u57fa\u7840\u6570\u636e\u8868");
        if (monitor != null) {
            monitor.progressAndMessage(0.5, "\u53d1\u5e03\u57fa\u7840\u6570\u636e\u8868");
        }
        this.publishBaseDatas(context, dataExchangeTask, monitor);
        logger.info("\u53d1\u5e03\u6307\u6807");
        if (monitor != null) {
            monitor.progressAndMessage(0.6, "\u53d1\u5e03\u6307\u6807");
        }
        this.publishFields(context, dataExchangeTask, monitor);
    }

    public void sateDataToMidstore(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        logger.info("\u5199\u5165\u7ec4\u7ec7\u673a\u6784\u6570\u636e");
        if (monitor != null) {
            monitor.progressAndMessage(0.7, "\u5199\u5165\u7ec4\u7ec7\u673a\u6784\u6570\u636e");
        }
        this.orgDataWorkService.saveOrgDatas(context, dataExchangeTask, monitor);
        logger.info("\u5199\u5165\u57fa\u7840\u6570\u636e");
        if (monitor != null) {
            monitor.progressAndMessage(0.8, "\u5199\u5165\u57fa\u7840\u6570\u636e");
        }
        this.baseDataPublishService.saveBaseDatas(context, dataExchangeTask, monitor);
    }

    private void publishOrgDatas(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        this.orgDataPublishService.publishOrgDataFields(context, dataExchangeTask, monitor);
    }

    private void publishBaseDatas(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        this.baseDataPublishService.publishBaseDatas(context, dataExchangeTask, monitor);
    }

    private void publishFields(MidstoreContext context, IDataExchangeTask dataExchangeTask, AsyncTaskMonitor monitor) throws MidstoreException {
        this.fieldPublishService.publishFields(context, dataExchangeTask, monitor);
    }
}

