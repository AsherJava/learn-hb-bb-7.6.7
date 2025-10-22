/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  javax.annotation.Resource
 */
package nr.single.data.datacopy.internal;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.List;
import javax.annotation.Resource;
import nr.single.data.bean.NrSingleDataErrorEnum;
import nr.single.data.bean.TaskCopyContext;
import nr.single.data.bean.TaskCopyParam;
import nr.single.data.datacopy.ITaskDataCopyCheckInfoService;
import nr.single.data.datacopy.ITaskDataCopyFMDMService;
import nr.single.data.datacopy.ITaskDataCopyFormsService;
import nr.single.data.datacopy.ITaskDataCopyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskDataCopyServiceImpl
implements ITaskDataCopyService {
    private static final Logger logger = LoggerFactory.getLogger(TaskDataCopyServiceImpl.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Resource
    private IFormulaCheckDesService formulaCheckService;
    @Autowired
    private IFormulaRunTimeController formulaController;
    @Autowired
    private ITaskDataCopyFMDMService fmdmCopyService;
    @Autowired
    private ITaskDataCopyFormsService formCopyService;
    @Autowired
    private ITaskDataCopyCheckInfoService checkInfoCopyService;

    @Override
    public String copyDataByParam(TaskCopyParam copyParam, AsyncTaskMonitor monitor) throws Exception {
        String errorInfo = "";
        TaskCopyContext copyContext = new TaskCopyContext();
        copyContext.setCopyParam(copyParam);
        if (StringUtils.isEmpty((String)copyParam.getTaskKey()) || StringUtils.isEmpty((String)copyParam.getOldTaskKey())) {
            throw new Exception("\u53c2\u6570\u7531\u9519");
        }
        if (StringUtils.isEmpty((String)copyParam.getFormSchemeKey()) || StringUtils.isEmpty((String)copyParam.getOldFormSchemeKey())) {
            if (copyParam.getCopyDirection() == 0) {
                errorInfo = this.copyDataByTask(copyContext, copyParam.getTaskKey(), copyParam.getPeriodCode(), copyParam.getOldTaskKey(), copyParam.getOldPeriodCode(), copyParam.getCopyDataForms(), copyParam.getCopyErrorInfoForms(), monitor);
            } else if (copyParam.getCopyDirection() == 1) {
                errorInfo = this.copyDataByTask(copyContext, copyParam.getOldTaskKey(), copyParam.getOldPeriodCode(), copyParam.getTaskKey(), copyParam.getPeriodCode(), copyParam.getCopyDataForms(), copyParam.getCopyErrorInfoForms(), monitor);
            } else {
                throw new JQException((ErrorEnum)NrSingleDataErrorEnum.NRSINGDATAER_EXCEPTION_002, "\u590d\u5236\u7684\u65b9\u5411\u51fa\u9519");
            }
            this.batchCalcBytask(copyParam.getTaskKey(), copyParam.getPeriodCode(), monitor);
        } else {
            if (copyParam.getCopyDirection() == 0) {
                errorInfo = this.copyDataByFormScheme(copyContext, copyParam.getFormSchemeKey(), copyParam.getPeriodCode(), copyParam.getOldFormSchemeKey(), copyParam.getOldPeriodCode(), copyParam.getCopyDataForms(), copyParam.getCopyErrorInfoForms(), monitor);
            } else if (copyParam.getCopyDirection() == 1) {
                errorInfo = this.copyDataByFormScheme(copyContext, copyParam.getOldFormSchemeKey(), copyParam.getOldPeriodCode(), copyParam.getFormSchemeKey(), copyParam.getPeriodCode(), copyParam.getCopyDataForms(), copyParam.getCopyErrorInfoForms(), monitor);
            } else {
                throw new JQException((ErrorEnum)NrSingleDataErrorEnum.NRSINGDATAER_EXCEPTION_002, "\u590d\u5236\u7684\u65b9\u5411\u51fa\u9519");
            }
            this.batchCalcByFormScheme(copyParam.getFormSchemeKey(), copyParam.getPeriodCode(), monitor);
        }
        if (monitor != null) {
            monitor.progressAndMessage(1.0, "\u590d\u5236\u6570\u636e\u5b8c\u6210\uff01");
        }
        return errorInfo;
    }

    @Override
    public String copyDataByTask(TaskCopyContext context, String taskKey, String periodCode, String oldTask, String oldPeriod, String copyDataforms, String copyErrorInfoforms, AsyncTaskMonitor monitor) throws Exception {
        String errorInfo = "";
        TaskDefine task = this.runTimeAuthViewController.queryTaskDefine(taskKey);
        if (task != null) {
            FormSchemeDefine defaultFormScheme = this.getDefaultFormScheme(taskKey);
            FormSchemeDefine oldDefaultFormSchme = this.getDefaultFormScheme(oldTask);
            if (defaultFormScheme != null && oldDefaultFormSchme != null) {
                this.copyDataByFormScheme(context, defaultFormScheme.getKey(), periodCode, oldDefaultFormSchme.getKey(), oldPeriod, copyDataforms, copyErrorInfoforms, monitor);
            } else {
                throw new JQException((ErrorEnum)NrSingleDataErrorEnum.NRSINGDATAER_EXCEPTION_002, "\u4efb\u52a1\u4e0d\u5b58\u5728\u62a5\u8868\u65b9\u6848");
            }
        }
        return errorInfo;
    }

    @Override
    public String copyDataByFormScheme(TaskCopyContext context, String formSchemeKey, String periodCode, String oldformScheme, String oldPeriod, String copyDataforms, String copyErrorInfoforms, AsyncTaskMonitor monitor) throws Exception {
        String errorInfo = "";
        if (monitor != null) {
            monitor.progressAndMessage(0.1, "\u6b63\u5728\u5904\u7406\u5c01\u9762\u4ee3\u7801");
        }
        errorInfo = errorInfo + this.fmdmCopyService.copyFMDMData(context, formSchemeKey, periodCode, oldformScheme, oldPeriod, monitor);
        if (monitor != null) {
            monitor.progressAndMessage(0.2, "\u6b63\u5728\u5904\u7406\u6570\u636e\u8868\u5355");
        }
        errorInfo = errorInfo + this.formCopyService.copyFormDatas(context, formSchemeKey, periodCode, oldformScheme, oldPeriod, copyDataforms, copyErrorInfoforms, monitor);
        if (monitor != null) {
            monitor.progressAndMessage(0.8, "\u6b63\u5728\u5904\u7406\u51fa\u9519\u8bf4\u660e");
        }
        this.checkInfoCopyService.copyDataChecInfos(context, formSchemeKey, periodCode, oldformScheme, oldPeriod, copyErrorInfoforms, monitor);
        return errorInfo;
    }

    private String getUnitEntityId(String dataSchemekey) {
        List dimList = this.dataSchemeSevice.getDataSchemeDimension(dataSchemekey);
        for (DataDimension dim : dimList) {
            if (dim.getDimensionType() != DimensionType.UNIT) continue;
            return dim.getDimKey();
        }
        return null;
    }

    private FormSchemeDefine getDefaultFormScheme(String taskKey) throws Exception {
        FormSchemeDefine defaultFormScheme = null;
        List formSchemes = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
        if (formSchemes != null && formSchemes.size() > 0) {
            defaultFormScheme = (FormSchemeDefine)formSchemes.get(0);
        }
        return defaultFormScheme;
    }

    private void batchCalcBytask(String taskKey, String netPeriodCode, AsyncTaskMonitor monitor) throws Exception {
        FormSchemeDefine defaultFormScheme = this.getDefaultFormScheme(taskKey);
        if (defaultFormScheme != null) {
            this.batchCalcByFormScheme(defaultFormScheme.getKey(), netPeriodCode, monitor);
        }
    }

    private void batchCalcByFormScheme(String formSchemeKey, String netPeriodCode, AsyncTaskMonitor monitor) {
    }
}

