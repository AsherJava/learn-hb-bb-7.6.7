/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.option.internal.UnitEdit
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.option.internal.UnitEdit;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.intf.ContextExpendParam;
import com.jiuqi.nr.transmission.data.intf.DataImportMessage;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.service.IImportBeforeService;
import com.jiuqi.nr.transmission.data.service.IReportParamService;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
@Primary
public class ImportBeforeCheckFormAndEntity
implements IImportBeforeService {
    private static final Logger logger = LoggerFactory.getLogger(ImportBeforeCheckFormAndEntity.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IReportParamService reportParamService;
    @Autowired
    private ITaskOptionController taskOptionController;

    @Override
    public Double getOrder() {
        return 0.0;
    }

    @Override
    public String getTitle() {
        return MultilingualLog.checkFormAndEntityMessage(1, "");
    }

    @Override
    public Object beforeImport(ITransmissionContext transmissionContext) throws Exception {
        String checkResult = "";
        DataImportResult dataImportResult = transmissionContext.getDataImportResult();
        ContextExpendParam contextExpendParam = transmissionContext.getContextExpendParam();
        IExecuteParam executeParam = transmissionContext.getExecuteParam();
        ILogHelper logHelper = transmissionContext.getLogHelper();
        AsyncTaskMonitor monitor = transmissionContext.getTransmissionMonitor();
        List<String> srcForms = executeParam.getForms();
        HashSet<String> existForms = new HashSet<String>();
        List formDefines = this.runTimeViewController.queryFormsById(srcForms);
        for (FormDefine formDefine : formDefines) {
            if (formDefine == null) continue;
            existForms.add(formDefine.getKey());
        }
        List<String> noExistForms = srcForms.stream().filter(formKey -> !existForms.contains(formKey)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(noExistForms)) {
            String unKnowMessage = MultilingualLog.checkFormAndEntityMessage(3, "");
            String unKnowFormMessage = MultilingualLog.checkFormAndEntityMessage(4, "");
            StringBuffer noExistMessage = new StringBuffer(MultilingualLog.checkFormAndEntityMessage(2, ""));
            Map<String, List<DataImportMessage>> failForms = dataImportResult.getFailForms();
            noExistForms.forEach(formKey -> {
                noExistMessage.append((String)formKey).append(",");
                failForms.computeIfAbsent((String)formKey, key -> new ArrayList()).add(new DataImportMessage(unKnowMessage, "00000000", (String)formKey, unKnowFormMessage));
            });
            checkResult = noExistMessage.toString() + "\r\n";
            srcForms.clear();
            srcForms.addAll(existForms);
            dataImportResult.setSyncErrorNumInc();
            logger.info(checkResult);
        }
        if (CollectionUtils.isEmpty(existForms)) {
            String log = Utils.getLog(logHelper);
            dataImportResult.setResult(false);
            String emptyMessage = MultilingualLog.checkFormAndEntityMessage(5, "");
            logger.info(log + checkResult + emptyMessage);
            dataImportResult.setLog(log + checkResult + emptyMessage);
            monitor.finish(log, (Object)dataImportResult);
            throw new Exception(log + checkResult + emptyMessage);
        }
        monitor.progressAndMessage(0.02, MultilingualLog.checkFormAndEntityMessage(6, ""));
        String dimensionName = contextExpendParam.getDimensionName();
        List syncEntitys = (List)executeParam.getDimensionValueSet().getValue(dimensionName);
        ArrayList<String> exportUnit = new ArrayList<String>();
        Map<String, List<DataImportMessage>> failUnits = dataImportResult.getFailUnits();
        if (contextExpendParam.getUnits().size() != syncEntitys.size()) {
            Set<String> rightEntity = contextExpendParam.getUnits().keySet();
            StringBuffer noEntityExistMessage = new StringBuffer(MultilingualLog.checkFormAndEntityMessage(7, ""));
            syncEntitys.forEach(entityKey -> {
                if (!rightEntity.contains(entityKey)) {
                    exportUnit.add((String)entityKey);
                    noEntityExistMessage.append((String)entityKey).append("\uff0c");
                }
            });
            logger.info(noEntityExistMessage.toString());
            executeParam.getDimensionValueSet().setValue(dimensionName, new ArrayList<String>(rightEntity));
            checkResult = checkResult + noEntityExistMessage + "\r\n";
        }
        if (exportUnit.size() > 0) {
            DimensionValueSet noExistUnitDimSet = Utils.getDimensionValueSetWithOutDim(executeParam.getDimensionValueSet(), dimensionName);
            noExistUnitDimSet.setValue(dimensionName, exportUnit);
            List<EntityInfoParam> entityInfoParams = this.reportParamService.getEntityList(noExistUnitDimSet, executeParam.getFormSchemeKey(), null, true);
            if (entityInfoParams.size() != exportUnit.size()) {
                String noAuthUnitMessage = MultilingualLog.checkFormAndEntityMessage(8, "");
                entityInfoParams.forEach(a -> failUnits.computeIfAbsent(a.getEntityKeyData(), key -> new ArrayList()).add(new DataImportMessage(a.getTitle(), a.getEntityKeyData(), noAuthUnitMessage)));
                List noAuthUnit = entityInfoParams.stream().map(EntityInfoParam::getEntityKeyData).collect(Collectors.toList());
                if (noAuthUnit.size() > 0) {
                    exportUnit.removeAll(noAuthUnit);
                    dataImportResult.setSyncErrorNumInc();
                }
                contextExpendParam.setNoExistUnit(exportUnit);
            }
        }
        String value = this.taskOptionController.getValue(executeParam.getTaskKey(), ((UnitEdit)SpringBeanUtils.getBean(UnitEdit.class)).getKey());
        if (!(contextExpendParam.getUnits().size() != 0 || exportUnit.size() != 0 && StringUtils.hasLength(transmissionContext.getFmdmForm()) && "1".equals(value))) {
            if (exportUnit.size() > 0 && !"1".equals(value)) {
                Utils.allowEditingUnitError(dataImportResult, logHelper, exportUnit);
            }
            String log = Utils.getLog(logHelper);
            logger.info(log + "\u540c\u6b65\u7684\u5355\u4f4d\u7ecf\u8fc7\u5b58\u5728\u6027\u548c\u6743\u9650\u8fc7\u6ee4\u540e\u4e3a\u7a7a\uff0c\u6267\u884c\u7ed3\u675f");
            dataImportResult.setResult(false);
            String emptyMessage = MultilingualLog.checkFormAndEntityMessage(9, "");
            dataImportResult.setLog(log + checkResult + emptyMessage);
            monitor.finish(log, (Object)dataImportResult);
            throw new Exception(log + checkResult + emptyMessage);
        }
        if (StringUtils.hasText(checkResult)) {
            logHelper.appendLog(checkResult);
        }
        monitor.progressAndMessage(0.03, MultilingualLog.checkFormAndEntityMessage(10, ""));
        return checkResult;
    }
}

