/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.dao.AsyncTaskDao
 *  com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExportOperation
 *  com.jiuqi.nvwa.core.automation.annotation.ImportOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterValueModeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.DatasetColumnInfo
 *  com.jiuqi.nvwa.framework.automation.result.DatasetResult
 */
package nr.midstore.core.internal.auto.service;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.dao.AsyncTaskDao;
import com.jiuqi.np.asynctask.impl.SimpleAsyncTaskMonitor;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExportOperation;
import com.jiuqi.nvwa.core.automation.annotation.ImportOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterValueModeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.DatasetColumnInfo;
import com.jiuqi.nvwa.framework.automation.result.DatasetResult;
import java.util.ArrayList;
import java.util.UUID;
import nr.midstore.core.asyn.MidstoreBatchController;
import nr.midstore.core.definition.bean.MidstoreParam;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.bean.MistoreWorkFailInfo;
import nr.midstore.core.definition.bean.MistoreWorkFormInfo;
import nr.midstore.core.definition.bean.MistoreWorkResultObject;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.common.ExchangeModeType;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.auto.service.MidstoreGetDataAutoType;
import nr.midstore.core.work.service.IMidstoreExcutePostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

@AutomationType(category="nr-midstore", id="nr-midstore-postdata", title="\u4e2d\u95f4\u5e93\u63a8\u9001\u6570\u636e", icon="icon16_SHU_A_NW_S2")
public class MidstorePostDataAutoType {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreGetDataAutoType.class);
    static final String IMPROT_DATA_PARAM_NAME = "DATASET";
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreExcutePostService postDataSerivce;
    @Autowired
    private MidstoreBatchController batchController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private AsyncTaskDao asyncTaskDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @ExportOperation
    public IOperationInvoker<DatasetResult> exportData() {
        return this.getDatasetResultIOperationInvoker();
    }

    @ImportOperation
    public IOperationInvoker<DatasetResult> importData() {
        return (automationInstance, executeContext) -> {
            throw new AutomationExecuteException("\u6570\u636e\u63a8\u9001\u4e0d\u652f\u6301\u8be5\u64cd\u4f5c");
        };
    }

    private IOperationInvoker<DatasetResult> getDatasetResultIOperationInvoker() {
        return (automationInstance, executeContext) -> {
            try {
                String dataTime = (String)executeContext.getParameterMap().get("DataTime");
                String orgCodes = (String)executeContext.getParameterMap().get("OrgData");
                String formCodes = (String)executeContext.getParameterMap().get("FormCode");
                logger.info("\u81ea\u52a8\u5316\u5bf9\u8c61\u6267\u884c\u7528\u6237\uff1a" + executeContext.getUserGuid());
                logger.info("\u81ea\u52a8\u5316\u5bf9\u8c61\u65f6\u671f:" + dataTime);
                logger.info("\u81ea\u52a8\u5316\u5bf9\u8c61\u5355\u4f4d:" + orgCodes);
                logger.info("\u81ea\u52a8\u5316\u5bf9\u8c61\u8868\u5355:" + formCodes);
                MidstoreSchemeDTO scheme = this.midstoreSchemeSevice.getByKey(automationInstance.getGuid());
                if (scheme != null) {
                    String taskId;
                    SimpleAsyncTaskMonitor monitor;
                    MidstoreResultObject result;
                    MidstoreParam param = new MidstoreParam();
                    param.setMidstoreSchemeId(scheme.getKey());
                    param.getExcuteParams().put("DataTime", dataTime);
                    param.getExcuteParams().put("OrgData", orgCodes);
                    param.getExcuteParams().put("FormCode", formCodes);
                    param.getExcuteParams().put("EXCUTEUSER", executeContext.getUserGuid());
                    if (scheme.getExchangeMode() != ExchangeModeType.EXCHANGE_POST) {
                        new AutomationExecuteException("\u4ea4\u6362\u6a21\u5f0f\u4e0d\u662f\u6570\u636e\u63a5\u6536");
                    }
                    if ((result = this.postDataSerivce.excutePostData(param, (AsyncTaskMonitor)(monitor = new SimpleAsyncTaskMonitor(this.asyncTaskDao, this.applicationEventPublisher, taskId = UUID.randomUUID().toString(), AsynctaskPoolType.ASYNCTASK_MIDSTORE_GETDATA.getName())))).isSuccess()) {
                        MemoryDataSet<DatasetColumnInfo> memory = this.dataResult2Memory(result);
                        return new DatasetResult(memory);
                    }
                    throw new AutomationExecuteException("\u6570\u63d0\u63d0\u4f9b\u5931\u8d25\uff1a" + result.getMessage());
                }
                throw new AutomationExecuteException("\u4ea4\u6362\u65b9\u6848\u4e3a\u7a7a");
            }
            catch (Exception e) {
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
        };
    }

    private MemoryDataSet<DatasetColumnInfo> dataResult2Memory(MidstoreResultObject dataResult) {
        MemoryDataSet dataset = new MemoryDataSet(DatasetColumnInfo.class);
        try {
            DatasetColumnInfo chartColumnInfo = new DatasetColumnInfo();
            chartColumnInfo.setFormatter("");
            Column contextColumn = new Column("\u5185\u5bb9", 6, (Object)chartColumnInfo);
            Column messageColumn = new Column("\u4fe1\u606f", 6, (Object)chartColumnInfo);
            dataset.getMetadata().addColumn(contextColumn);
            dataset.getMetadata().addColumn(messageColumn);
            Object[] rowData = new Object[]{"\u7ed3\u679c\u72b6\u6001\uff1a", dataResult.isSuccess() ? "\u6210\u529f\u5b8c\u6210" : "\u5931\u8d25\u4fe1\u606f," + dataResult.getMessage()};
            dataset.add(rowData);
            rowData = new Object[]{"\u7ed3\u679c\u4fe1\u606f\uff1a", dataResult.getMessage()};
            dataset.add(rowData);
            MistoreWorkResultObject workResult = null;
            if (dataResult.getWorkResults() != null && dataResult.getWorkResults().size() > 0) {
                workResult = dataResult.getWorkResults().get(0);
            }
            if (workResult != null) {
                rowData = new Object[]{"\u6267\u884c\u65f6\u671f\uff1a", "\u62a5\u8868\u65f6\u671f:" + workResult.getNrPeriodCode() + "\uff0c\u4e2d\u95f4\u5e93\u65f6\u671f:" + workResult.getMidstorePeriodCode()};
                dataset.add(rowData);
                rowData = new Object[]{"\u5355\u4f4d\u603b\u6570\uff1a", "" + workResult.getUnitCount()};
                dataset.add(rowData);
            }
            if (workResult != null && workResult.getSuccessUnits() != null) {
                rowData = new Object[]{"\u6210\u529f\u5355\u4f4d\u6570\uff1a", "" + workResult.getSuccessUnits().size()};
                dataset.add(rowData);
            }
            if (workResult != null && workResult.getFailUnits() != null) {
                rowData = new Object[]{"\u5931\u8d25\u5355\u4f4d\u6570\uff1a", "" + workResult.getFailUnits().size()};
                dataset.add(rowData);
            }
            if (workResult != null && workResult.getFailInfoList() != null) {
                for (MistoreWorkFailInfo failInfo : workResult.getFailInfoList()) {
                    rowData = new Object[]{failInfo.getMessage(), ""};
                    dataset.add(rowData);
                    for (MistoreWorkUnitInfo unitInfo : failInfo.getUnitInfos().values()) {
                        rowData = new Object[]{"  " + unitInfo.getUnitCode() + " " + (StringUtils.isNotEmpty((String)unitInfo.getUnitTitle()) ? unitInfo.getUnitTitle() : ""), ""};
                        dataset.add(rowData);
                        if (unitInfo.getFormInfos() != null && unitInfo.getFormInfos().size() > 0) {
                            for (MistoreWorkFormInfo formInfo : unitInfo.getFormInfos().values()) {
                                rowData = new Object[]{"    " + formInfo.getFormCode() + " " + (StringUtils.isNotEmpty((String)formInfo.getFormTitle()) ? formInfo.getFormTitle() : "") + "  " + formInfo.getMessage(), ""};
                                dataset.add(rowData);
                            }
                            continue;
                        }
                        if (unitInfo.getTableInfos() == null || unitInfo.getTableInfos().size() <= 0) continue;
                        for (MistoreWorkFormInfo tableInfo : unitInfo.getTableInfos().values()) {
                            rowData = new Object[]{"    " + tableInfo.getFormCode() + " " + (StringUtils.isNotEmpty((String)tableInfo.getFormTitle()) ? tableInfo.getFormTitle() : "") + "  " + tableInfo.getMessage(), ""};
                            dataset.add(rowData);
                        }
                    }
                }
            }
            if (workResult != null && workResult.getSuccessUnits().size() > 0) {
                rowData = new Object[]{" \u6210\u529f\u5355\u4f4d\u5217\u8868\uff1a", ""};
                dataset.add(rowData);
                for (MistoreWorkUnitInfo unitInfo : workResult.getSuccessUnits().values()) {
                    rowData = new Object[]{"  " + unitInfo.getUnitCode() + " " + (StringUtils.isNotEmpty((String)unitInfo.getUnitTitle()) ? unitInfo.getUnitTitle() : ""), ""};
                    dataset.add(rowData);
                }
            }
        }
        catch (DataSetException e) {
            logger.error(e.getMessage(), e);
        }
        return dataset;
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            AutomationParameter dataTimeParam = new AutomationParameter("DataTime", "\u65f6\u671f", AutomationParameterDataTypeEnum.STRING, null);
            dataTimeParam.setValueMode(AutomationParameterValueModeEnum.DEFAULT);
            AutomationParameter orgDataParam = new AutomationParameter("OrgData", "\u5355\u4f4d", AutomationParameterDataTypeEnum.STRING, null);
            orgDataParam.setValueMode(AutomationParameterValueModeEnum.MULTI_VALUE);
            AutomationParameter formCodeParam = new AutomationParameter("FormCode", "\u62a5\u8868", AutomationParameterDataTypeEnum.STRING, null);
            formCodeParam.setValueMode(AutomationParameterValueModeEnum.MULTI_VALUE);
            ArrayList<AutomationParameter> parameterList = new ArrayList<AutomationParameter>();
            parameterList.add(dataTimeParam);
            parameterList.add(orgDataParam);
            parameterList.add(formCodeParam);
            metaInfo.setParameterList(parameterList);
            return metaInfo;
        };
    }
}

