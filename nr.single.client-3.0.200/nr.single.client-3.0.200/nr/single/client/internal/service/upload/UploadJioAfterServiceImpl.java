/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.DUserActionParam
 *  com.jiuqi.nr.dataentry.bean.FormulaAuditType
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.dataentry.service.IBatchCheckService
 *  com.jiuqi.nr.dataentry.service.IBatchWorkflowService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.jtable.aop.JLoggerAspect
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.annotation.Resource
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.MappingConfig
 */
package nr.single.client.internal.service.upload;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.FormulaAuditType;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IBatchCheckService;
import com.jiuqi.nr.dataentry.service.IBatchWorkflowService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.jtable.aop.JLoggerAspect;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.service.upload.IUploadJioAfterService;
import nr.single.client.service.upload.extend.IUploadJioCalcFormService;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.MappingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadJioAfterServiceImpl
implements IUploadJioAfterService {
    private static final Logger log = LoggerFactory.getLogger(UploadJioAfterServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    IBatchCalculateService batchCalculateService;
    @Autowired
    IBatchCheckService batchCheckService;
    @Autowired
    private IBatchWorkflowService batchWorkflowService;
    @Resource
    IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private JLoggerAspect jLoggerAspect;
    @Autowired(required=false)
    private IUploadJioCalcFormService calcFormService;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;

    @Override
    public void uploadJioAfterSuccess(JIOImportResultObject res, ISingleMappingConfig mapConfig, UploadParam param, AsyncTaskMonitor asyncTaskMonitor) {
        if (res.getSuccesssUnitNum() > 0) {
            DimensionValue newValue;
            DimensionValue value;
            Object newDimensionSet;
            MappingConfig config = mapConfig.getConfig();
            boolean needClac = false;
            String formulaCalcSchemesKey = "";
            if (StringUtils.isEmpty((String)param.getAutoCacl())) {
                if (config.getArithmetic() != null && config.getArithmetic().size() > 0) {
                    needClac = true;
                    StringBuilder sb = new StringBuilder();
                    for (String formulaSchemeKey : config.getArithmetic()) {
                        sb.append(formulaSchemeKey).append(";");
                    }
                    formulaCalcSchemesKey = sb.toString();
                    formulaCalcSchemesKey = formulaCalcSchemesKey.substring(0, formulaCalcSchemesKey.length() - 1);
                }
            } else if ("1".equalsIgnoreCase(param.getAutoCacl())) {
                if (StringUtils.isNotEmpty((String)param.getFormulaSchemeKey())) {
                    needClac = true;
                    formulaCalcSchemesKey = param.getFormulaSchemeKey();
                } else {
                    needClac = false;
                    log.info("\u6ca1\u6709\u8bbe\u7f6e\u516c\u5f0f\u65b9\u6848\uff0c\u4e0d\u6267\u884c\u8fd0\u7b97");
                }
            } else if ("2".equalsIgnoreCase(param.getAutoCacl())) {
                log.info("\u524d\u7aef\u672a\u8bbe\u7f6e\uff0c\u4e0d\u8fdb\u884c\u8fd0\u7b97");
            }
            List<String> autoCalcFormKeys = null;
            if (!needClac && this.calcFormService != null && (autoCalcFormKeys = this.calcFormService.getAutoCalcFormKeys(param.getTaskKey(), param.getFormSchemeKey())) != null && !autoCalcFormKeys.isEmpty()) {
                if (StringUtils.isEmpty((String)formulaCalcSchemesKey)) {
                    if (StringUtils.isNotEmpty((String)param.getFormulaSchemeKey())) {
                        formulaCalcSchemesKey = param.getFormulaSchemeKey();
                    } else {
                        FormulaSchemeDefine forulaScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(param.getFormSchemeKey());
                        if (forulaScheme != null) {
                            formulaCalcSchemesKey = forulaScheme.getKey();
                        }
                    }
                }
                needClac = true;
            }
            if (needClac) {
                res.setCalFormulaSchemeKey(formulaCalcSchemesKey);
                BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
                Map dimensionSet = res.getDimensionSet();
                newDimensionSet = new HashMap();
                for (Map.Entry entry : dimensionSet.entrySet()) {
                    value = (DimensionValue)entry.getValue();
                    newValue = new DimensionValue();
                    newValue.setName(value.getName());
                    newValue.setType(value.getType());
                    newValue.setValue(value.getValue());
                    newDimensionSet.put(entry.getKey(), newValue);
                }
                batchCalculateInfo.setDimensionSet(newDimensionSet);
                batchCalculateInfo.setFormSchemeKey(param.getFormSchemeKey());
                batchCalculateInfo.setFormulaSchemeKey(formulaCalcSchemesKey);
                batchCalculateInfo.setTaskKey(param.getTaskKey());
                batchCalculateInfo.setVariableMap(param.getVariableMap());
                batchCalculateInfo.setChangeMonitorState(false);
                if (autoCalcFormKeys != null && !autoCalcFormKeys.isEmpty()) {
                    HashMap autoForms = new HashMap();
                    for (String formKey : autoCalcFormKeys) {
                        autoForms.put(formKey, new ArrayList());
                    }
                    batchCalculateInfo.setFormulas(autoForms);
                }
                SimpleAsyncProgressMonitor asyncTaskMonitorSub = new SimpleAsyncProgressMonitor(UUID.randomUUID().toString(), this.cacheObjectResourceRemote);
                asyncTaskMonitorSub.progressAndMessage(0.0, "\u6279\u91cf\u8fd0\u7b97\u9636\u6bb5");
                asyncTaskMonitor.progressAndMessage(asyncTaskMonitor.getLastProgress() + 0.01, "\u6279\u91cf\u8fd0\u7b97\u9636\u6bb5");
                this.jLoggerAspect.log(batchCalculateInfo.getContext(), "\u6570\u636e\u6267\u884c\u81ea\u52a8\u8fd0\u7b97");
                this.batchCalculateService.batchCalculateForm(batchCalculateInfo, (AsyncTaskMonitor)asyncTaskMonitorSub);
                res.setCalForm(true);
                log.info("\u5bfc\u5165JIO\u6570\u636e:\u6279\u91cf\u8fd0\u7b97\u5b8c\u6210");
            }
            if (null != config.getExamine() && !config.getExamine().isEmpty()) {
                String formulaSchemesKey = "";
                if (null != config.getExamine() && !config.getExamine().isEmpty()) {
                    StringBuilder sb2 = new StringBuilder();
                    for (String formulaSchemeKey : config.getExamine()) {
                        sb2.append(formulaSchemeKey).append(";");
                    }
                    formulaSchemesKey = sb2.toString();
                    if (formulaSchemesKey.length() > 0) {
                        formulaSchemesKey = formulaSchemesKey.substring(0, formulaSchemesKey.length() - 1);
                    }
                }
                res.setCheckedFormulaSchemeKey(formulaSchemesKey);
                BatchCheckInfo batchCheckInfo = new BatchCheckInfo();
                JtableContext jtableContext = new JtableContext();
                HashMap dimensionSet = new HashMap();
                dimensionSet.putAll(res.getDimensionSet());
                jtableContext.setDimensionSet((Map)dimensionSet);
                jtableContext.setFormSchemeKey(param.getFormSchemeKey());
                jtableContext.setFormulaSchemeKey(formulaSchemesKey);
                if (formulaSchemesKey.length() == 0) {
                    jtableContext.setFormulaSchemeKey(param.getFormulaSchemeKey());
                }
                jtableContext.setTaskKey(param.getTaskKey());
                batchCheckInfo.setFormulaSchemeKeys(formulaSchemesKey);
                batchCheckInfo.setContext(jtableContext);
                batchCheckInfo.setChangeMonitorState(false);
                res.setFormSchemeKey(param.getFormSchemeKey());
                res.setFormulaSchemeKey(formulaSchemesKey);
                SimpleAsyncProgressMonitor simpleAsyncProgressMonitor = new SimpleAsyncProgressMonitor(UUID.randomUUID().toString(), this.cacheObjectResourceRemote);
                simpleAsyncProgressMonitor.progressAndMessage(0.0, "\u6279\u91cf\u5ba1\u6838\u9636\u6bb5");
                asyncTaskMonitor.progressAndMessage(asyncTaskMonitor.getLastProgress() + 0.01, "\u6279\u91cf\u5ba1\u6838\u9636\u6bb5");
                this.batchCheckService.batchCheckForm(batchCheckInfo, (AsyncTaskMonitor)simpleAsyncProgressMonitor);
                res.setChecked(true);
                log.info("\u5bfc\u5165JIO\u6570\u636e:\u6279\u91cf\u5ba1\u6838\u5b8c\u6210");
            }
            if (config.isUploadStatus()) {
                BatchExecuteTaskParam batchExecuteTaskParam = new BatchExecuteTaskParam();
                Map alldimensionSet = res.getDimensionSet();
                newDimensionSet = new HashMap();
                for (Map.Entry entry : alldimensionSet.entrySet()) {
                    value = (DimensionValue)entry.getValue();
                    newValue = new DimensionValue();
                    newValue.setName(value.getName());
                    newValue.setType(value.getType());
                    newValue.setValue(value.getValue());
                    newDimensionSet.put(entry.getKey(), newValue);
                }
                JtableContext uploadJtableContext = new JtableContext();
                uploadJtableContext.setDimensionSet((Map)newDimensionSet);
                uploadJtableContext.setFormSchemeKey(param.getFormSchemeKey());
                FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(uploadJtableContext.getFormSchemeKey());
                uploadJtableContext.setFormulaSchemeKey(formulaSchemeDefine.getKey());
                uploadJtableContext.setTaskKey(param.getTaskKey());
                FormSchemeDefine formSchemeDefine = null;
                try {
                    formSchemeDefine = this.runtimeView.getFormScheme(param.getFormSchemeKey());
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                if (null == formSchemeDefine) {
                    throw new NotFoundFormSchemeException(new String[]{param.getFormSchemeKey() + "\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230"});
                }
                ArrayList<FormulaAuditType> defaultType = new ArrayList<FormulaAuditType>();
                try {
                    List auditTypes = this.auditTypeDefineService.queryAllAuditType();
                    if (auditTypes == null || auditTypes.size() == 0) {
                        throw new Exception();
                    }
                    for (AuditType auditType : auditTypes) {
                        FormulaAuditType formulaAuditType = new FormulaAuditType();
                        formulaAuditType.setKey(auditType.getCode().intValue());
                        formulaAuditType.setIcon(auditType.getIcon());
                        formulaAuditType.setTitle(auditType.getTitle());
                        defaultType.add(formulaAuditType);
                    }
                }
                catch (Exception e) {
                    FormulaAuditType hintType = new FormulaAuditType();
                    hintType.setKey(1);
                    hintType.setIcon("#icon-_Txiaoxitishi");
                    hintType.setTitle("\u63d0\u793a\u578b");
                    defaultType.add(hintType);
                    FormulaAuditType warningType = new FormulaAuditType();
                    warningType.setKey(2);
                    warningType.setIcon("#icon-_Tjinggaotishi");
                    warningType.setTitle("\u8b66\u544a\u578b");
                    defaultType.add(warningType);
                    FormulaAuditType errorType = new FormulaAuditType();
                    errorType.setKey(4);
                    errorType.setIcon("#icon-_Tcuowutishi");
                    errorType.setTitle("\u9519\u8bef\u578b");
                    defaultType.add(errorType);
                    log.error(e.getMessage(), e);
                }
                TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
                String erroStatus = flowsSetting.getErroStatus();
                ArrayList<Integer> erroStatusList = new ArrayList<Integer>();
                String promptStatus = flowsSetting.getPromptStatus();
                ArrayList<Integer> promptStatusList = new ArrayList<Integer>();
                for (FormulaAuditType formulaAuditType : defaultType) {
                    if (!erroStatus.contains(formulaAuditType.getKey() + "")) {
                        erroStatusList.add(formulaAuditType.getKey());
                    }
                    if (!promptStatus.contains(formulaAuditType.getKey() + "")) continue;
                    promptStatusList.add(formulaAuditType.getKey());
                }
                if (erroStatusList.size() == 0) {
                    erroStatusList.add(-1);
                }
                if (config != null && config.isForceUpload()) {
                    log.info("\u5bfc\u5165JIO\u6570\u636e:\u8bbe\u7f6e\u5f3a\u5236\u4e0a\u62a5\u4e3atrue");
                    batchExecuteTaskParam.setForceCommit(true);
                }
                DUserActionParam userActionParam = new DUserActionParam();
                batchExecuteTaskParam.setUserActionParam(userActionParam);
                batchExecuteTaskParam.setContext(uploadJtableContext);
                batchExecuteTaskParam.setActionId("act_upload");
                batchExecuteTaskParam.setChangeMonitorState(false);
                SimpleAsyncProgressMonitor asyncTaskMonitorSub = new SimpleAsyncProgressMonitor(UUID.randomUUID().toString(), this.cacheObjectResourceRemote);
                asyncTaskMonitorSub.progressAndMessage(0.0, "\u6279\u91cf\u4e0a\u62a5\u9636\u6bb5\u5f00\u59cb");
                asyncTaskMonitor.progressAndMessage(asyncTaskMonitor.getLastProgress() + 0.01, "\u6279\u91cf\u4e0a\u62a5\u9636\u6bb5");
                this.batchWorkflowService.batchExecuteTask(batchExecuteTaskParam, (AsyncTaskMonitor)asyncTaskMonitorSub);
                res.setCommitted(true);
                log.info("\u5bfc\u5165JIO\u6570\u636e:\u6279\u91cf\u4e0a\u62a5\u5b8c\u6210");
            }
        }
    }
}

