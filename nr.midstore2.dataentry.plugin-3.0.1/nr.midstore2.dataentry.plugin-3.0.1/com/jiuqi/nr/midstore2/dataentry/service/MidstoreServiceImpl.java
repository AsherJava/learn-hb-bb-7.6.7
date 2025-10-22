/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$NoAccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.dataentry.monitor.DataEntryAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject
 *  com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO
 *  com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService
 *  com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeService
 *  com.jiuqi.nvwa.midstore.core.internal.definition.MidstoreOrgDataDO
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletResponse
 *  nr.midstore2.data.service.IReportMidstoreExcuteWorkService
 */
package com.jiuqi.nr.midstore2.dataentry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.monitor.DataEntryAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.midstore2.dataentry.bean.MidstoreParam;
import com.jiuqi.nr.midstore2.dataentry.event.MidstorePullFinishEvent;
import com.jiuqi.nr.midstore2.dataentry.service.IMidstoreService;
import com.jiuqi.nr.midstore2.dataentry.web.vo.ErrorType;
import com.jiuqi.nr.midstore2.dataentry.web.vo.FailedUnit;
import com.jiuqi.nr.midstore2.dataentry.web.vo.Lable;
import com.jiuqi.nr.midstore2.dataentry.web.vo.MidstoreResultVO;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFailInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkFormInfo;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkResultObject;
import com.jiuqi.nvwa.midstore.core.definition.bean.MistoreWorkUnitInfo;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreOrgDataDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeDTO;
import com.jiuqi.nvwa.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreOrgDataService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeInfoService;
import com.jiuqi.nvwa.midstore.core.definition.service.IMidstoreSchemeService;
import com.jiuqi.nvwa.midstore.core.internal.definition.MidstoreOrgDataDO;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import nr.midstore2.data.service.IReportMidstoreExcuteWorkService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MidstoreServiceImpl
implements IMidstoreService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreServiceImpl.class);
    private static final String EORROR_OTHER = "\u5176\u4ed6";
    @Autowired
    private IReportMidstoreExcuteWorkService midstoreExcuteGetService;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    IJtableEntityService jtableEntityService;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    private ApplicationContext applicationContext;
    @Resource
    IRunTimeViewController runtimeView;
    @Autowired
    RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private Workflow workflow;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IMidstoreSchemeInfoService midstoreSchemeInfoSevice;
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    private static final String UNITTAG = ";";

    @Override
    public void midstorePullPush(MidstoreParam midstoreParam, AsyncTaskMonitor asyncTaskMonitor) {
        EntityQueryByKeyInfo entityQueryByKeyInfo;
        FormSchemeDefine formScheme;
        HashMap<DimensionValueSet, List> unitFormKeys = new HashMap<DimensionValueSet, List>();
        DsContextImpl newContext = (DsContextImpl)DsContextHolder.createEmptyContext();
        newContext.setEntityId(midstoreParam.getEntityId());
        DsContextHolder.setDsContext((DsContext)newContext);
        JtableContext jtableContext = midstoreParam.getContext();
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        List<String> allUnits = this.getAllUnits(jtableContext, targetEntityInfo);
        int allCount = allUnits.size();
        HashSet<String> newOrgList = new HashSet<String>();
        String[] midstoreCodes = midstoreParam.getMidstoreCode().split(",");
        HashMap<String, Lable> ignoreUnitMap0 = new HashMap<String, Lable>();
        for (String code : midstoreCodes) {
            MidstoreSchemeDTO midScheme = this.midstoreSchemeSevice.getByCode(code);
            MidstoreSchemeInfoDTO schemeInfo = this.midstoreSchemeInfoSevice.getBySchemeKey(midScheme.getKey());
            this.buildMidstoreUnits(jtableContext, targetEntityInfo, allUnits, midScheme, schemeInfo, newOrgList);
        }
        if (CollectionUtils.isEmpty(newOrgList)) {
            EntityQueryByKeyInfo entityQueryByKeyInfo2 = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo2.setContext(jtableContext);
            entityQueryByKeyInfo2.setEntityViewKey(targetEntityInfo.getKey());
            for (String orgCode : allUnits) {
                entityQueryByKeyInfo2.setEntityKey(orgCode);
                EntityByKeyReturnInfo info = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo2);
                ignoreUnitMap0.put(orgCode, new Lable(orgCode, info.getEntity().getTitle()));
            }
            MidstoreResultVO res = new MidstoreResultVO();
            res.setAllCount(allCount);
            res.setIgnoreCount(allCount);
            res.setIgnoreUnits(new ArrayList<Lable>(ignoreUnitMap0.values()));
            this.returnRes(asyncTaskMonitor, res);
            StringBuilder logStr = new StringBuilder();
            logStr.append(midstoreParam.isPullData() ? "\u53d6\u6570\u5355\u4f4d\u5171" : "\u63a8\u6570\u5355\u4f4d\u5171").append(allCount).append("\u4e2a\uff0c\u6210\u529f0\u4e2a\uff0c\u5931\u8d250\u4e2a\uff0c\u672a\u6267\u884c").append(allCount).append("\u4e2a(\u4e0d\u5728\u4ea4\u6362\u65b9\u6848\u5185)");
            LogHelper.info((String)"\u4e2d\u95f4\u5e93", (String)midstoreParam.getBtnTitle(), (String)logStr.toString());
            return;
        }
        if (newOrgList.size() != allCount) {
            DimensionValue unitDim = (DimensionValue)jtableContext.getDimensionSet().get(targetEntityInfo.getDimensionName());
            unitDim.setValue(String.join((CharSequence)UNITTAG, newOrgList));
            EntityQueryByKeyInfo entityQueryByKeyInfo3 = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo3.setContext(jtableContext);
            entityQueryByKeyInfo3.setEntityViewKey(targetEntityInfo.getKey());
            for (String orgCode : allUnits) {
                if (newOrgList.contains(orgCode)) continue;
                entityQueryByKeyInfo3.setEntityKey(orgCode);
                EntityByKeyReturnInfo info = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo3);
                ignoreUnitMap0.put(orgCode, new Lable(orgCode, info.getEntity().getTitle()));
            }
        }
        IDataAccessFormService dataFormAccess = this.dataAccessServiceProvider.getDataAccessFormService();
        List<String> formKeys = null;
        String formKeyStr = midstoreParam.getFormKeys();
        if (StringUtils.hasText(formKeyStr)) {
            formKeys = Arrays.asList(formKeyStr.split(UNITTAG));
        } else {
            try {
                List formDefines = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(jtableContext.getFormSchemeKey());
                formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            catch (Exception e) {
                logger.warn("\u4e2d\u95f4\u5e93\u83b7\u53d6\u62a5\u8868\u4e3a\u7a7a\uff1a\uff1a" + jtableContext.getFormSchemeKey(), e);
            }
        }
        if (CollectionUtils.isEmpty(formKeys)) {
            StringBuilder logStr = new StringBuilder();
            logStr.append(midstoreParam.isPullData() ? "\u53c2\u4e0e\u53d6\u6570\u7684\u62a5\u8868\u4e3a\u7a7a" : "\u53c2\u4e0e\u63a8\u6570\u7684\u62a5\u8868\u4e3a\u7a7a");
            LogHelper.info((String)"\u4e2d\u95f4\u5e93", (String)midstoreParam.getBtnTitle(), (String)logStr.toString());
            return;
        }
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)jtableContext.getDimensionSet(), (String)jtableContext.getFormSchemeKey()));
        accessFormParam.setTaskKey(jtableContext.getTaskKey());
        accessFormParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(midstoreParam.isPullData() ? AccessLevel.FormAccessLevel.FORM_DATA_WRITE : AccessLevel.FormAccessLevel.FORM_DATA_READ);
        DimensionAccessFormInfo batchDimensionValueFormInfo = dataFormAccess.getBatchAccessForms(accessFormParam);
        String dwDimensionName = this.getDWDimensionName(jtableContext.getTaskKey());
        List accessForms = batchDimensionValueFormInfo.getAccessForms();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : accessForms) {
            Map dimensions = accessFormInfo.getDimensions();
            List accessFormKeys = accessFormInfo.getFormKeys();
            List dimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList((Map)dimensions);
            for (DimensionValueSet dimension : dimensionValueSetList) {
                String unitCode = (String)dimension.getValue(dwDimensionName);
                if (!unitFormKeys.containsKey(dimension)) {
                    unitFormKeys.put(dimension, new ArrayList(accessFormKeys));
                    continue;
                }
                List list = (List)unitFormKeys.get(dimension);
                list.addAll(accessFormKeys);
                unitFormKeys.put(dimension, list);
            }
        }
        if (unitFormKeys.isEmpty()) {
            List noAccessForms = batchDimensionValueFormInfo.getNoAccessForms();
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
            entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setContext(jtableContext);
            entityQueryByKeyInfo.setEntityViewKey(targetEntityInfo.getKey());
            HashMap<String, FailedUnit> errorMap = new HashMap<String, FailedUnit>();
            ArrayList<FailedUnit> errors = new ArrayList<FailedUnit>();
            for (DimensionAccessFormInfo.NoAccessFormInfo noAccessForm : noAccessForms) {
                String unitKey;
                if (((DimensionValue)noAccessForm.getDimensions().get(targetEntityInfo.getDimensionName())).getValue().contains(noAccessForm.getFormKey())) {
                    unitKey = noAccessForm.getFormKey();
                    entityQueryByKeyInfo.setEntityKey(unitKey);
                    EntityByKeyReturnInfo info = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                    FailedUnit failedUnit = this.getFailedUnit(unitKey, info.getEntity().getTitle(), errors, errorMap);
                    failedUnit.setType(ErrorType.UNIT);
                    failedUnit.setMessage(noAccessForm.getReason());
                    continue;
                }
                unitKey = ((DimensionValue)noAccessForm.getDimensions().get(targetEntityInfo.getDimensionName())).getValue();
                String reason = noAccessForm.getReason();
                if ("\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6".equals(reason)) continue;
                entityQueryByKeyInfo.setEntityKey(unitKey);
                EntityByKeyReturnInfo info = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                String unitTitle = info.getEntity().getTitle();
                if (this.workflow.queryStartType(jtableContext.getFormSchemeKey()) == WorkFlowType.ENTITY && reason.equals("\u8be5\u5de5\u4f5c\u6d41\u8282\u70b9\u4e0d\u53ef\u7f16\u8f91")) {
                    FailedUnit failedUnit = this.getFailedUnit(unitKey, unitTitle, errors, errorMap);
                    failedUnit.setType(ErrorType.UNIT);
                    failedUnit.setMessage(reason);
                    continue;
                }
                FormData form = this.jtableParamService.getReport(noAccessForm.getFormKey(), formScheme.getKey());
                FailedUnit failedUnit = this.getFailedUnit(unitKey, unitTitle, errors, errorMap);
                if (StringUtils.hasText(failedUnit.getMessage())) {
                    System.out.println("unitcode=" + failedUnit.getCode() + "message=" + failedUnit.getMessage() + "::\u6709\u51b2\u7a81");
                }
                failedUnit.setType(ErrorType.FORM);
                failedUnit.addForms(form.getCode(), form.getTitle(), reason);
            }
            MidstoreResultVO res = new MidstoreResultVO();
            res.setAllCount(allCount);
            res.setFailedCount(errors.size());
            res.setErrors(errors);
            if (!CollectionUtils.isEmpty(ignoreUnitMap0)) {
                ArrayList<Lable> ignoreUnits = new ArrayList<Lable>(ignoreUnitMap0.values());
                res.setIgnoreCount(ignoreUnits.size());
                res.setIgnoreUnits(ignoreUnits);
            }
            this.returnRes(asyncTaskMonitor, res);
            StringBuilder logStr = new StringBuilder();
            logStr.append(midstoreParam.isPullData() ? "\u53d6\u6570\u5355\u4f4d\u5171" : "\u63a8\u6570\u5355\u4f4d\u5171").append(allCount).append("\u4e2a\uff0c\u6210\u529f0\u4e2a\uff0c\u5931\u8d25").append(errors.size()).append("\u4e2a(\u9009\u62e9\u5355\u4f4d\u6ca1\u6743\u9650)\uff0c\u672a\u6267\u884c").append(res.getIgnoreCount()).append("\u4e2a");
            LogHelper.info((String)"\u4e2d\u95f4\u5e93", (String)midstoreParam.getBtnTitle(), (String)logStr.toString());
            return;
        }
        List results = new ArrayList();
        try {
            DataEntryAsyncProgressMonitor asyncProgressMonitor = new DataEntryAsyncProgressMonitor(asyncTaskMonitor, 0.8, 0.0);
            if (midstoreParam.isPullData()) {
                results = this.midstoreExcuteGetService.excuteDataGetByCodes(jtableContext.getTaskKey(), midstoreParam.getMidstoreCode(), unitFormKeys, midstoreParam.isOverImport(), (AsyncTaskMonitor)asyncProgressMonitor);
                this.calculate(asyncTaskMonitor, jtableContext, formKeys);
            } else {
                results = this.midstoreExcuteGetService.excuteDataPostByCodes(jtableContext.getTaskKey(), midstoreParam.getMidstoreCode(), unitFormKeys, (AsyncTaskMonitor)asyncProgressMonitor);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
            return;
        }
        if (CollectionUtils.isEmpty(results)) {
            logger.error("\u5f55\u5165\u4e2d\u95f4\u5e93\u53d6\u6570\uff0c\u7ed3\u679c\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            asyncTaskMonitor.error("\u5f55\u5165\u4e2d\u95f4\u5e93\u53d6\u6570\uff0c\u7ed3\u679c\u4e0d\u80fd\u4e3a\u7a7a\uff01", (Throwable)new Exception("\u5f55\u5165\u4e2d\u95f4\u5e93\u53d6\u6570\uff0c\u7ed3\u679c\u4e0d\u80fd\u4e3a\u7a7a\uff01"));
            return;
        }
        formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
        entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setContext(jtableContext);
        entityQueryByKeyInfo.setEntityViewKey(targetEntityInfo.getKey());
        HashSet<String> allUnitSet = new HashSet<String>(allUnits);
        ArrayList<String> midstors = new ArrayList<String>();
        ArrayList<String> errorMidstors = new ArrayList<String>();
        HashMap<String, FailedUnit> errorMap = new HashMap<String, FailedUnit>();
        ArrayList<FailedUnit> errors = new ArrayList<FailedUnit>();
        HashMap<String, Lable> successUnitMap = new HashMap<String, Lable>();
        HashMap<String, Lable> ignoreUnitMap = new HashMap<String, Lable>(ignoreUnitMap0);
        for (Object result : results) {
            Map successUnits;
            String string = result.getSchemeTitle();
            midstors.add(string);
            if (!result.isSuccess()) {
                errorMidstors.add(string + " \u5931\u8d25\u539f\u56e0\uff1a" + result.getMessage());
                continue;
            }
            List workResults = result.getWorkResults();
            if (CollectionUtils.isEmpty(workResults)) {
                errorMidstors.add(string + " \u5931\u8d25\u539f\u56e0\uff1a\u8fd4\u56de\u6bcf\u671f\u7ed3\u679c\u4e3a\u7a7a");
                continue;
            }
            MistoreWorkResultObject work = (MistoreWorkResultObject)workResults.get(0);
            if (!work.isSuccess()) {
                errorMidstors.add(string + " \u5931\u8d25\u539f\u56e0\uff1a" + work.getMessage());
                continue;
            }
            List failInfoList = work.getFailInfoList();
            if (!CollectionUtils.isEmpty(failInfoList)) {
                for (MistoreWorkFailInfo info : failInfoList) {
                    String message = info.getMessage();
                    Map unitInfos = info.getUnitInfos();
                    for (Map.Entry entry : unitInfos.entrySet()) {
                        String unitCode = (String)entry.getKey();
                        MistoreWorkUnitInfo unitInfo = (MistoreWorkUnitInfo)entry.getValue();
                        String unitTitle = unitInfo.getUnitTitle();
                        if (!StringUtils.hasText(unitTitle)) {
                            unitTitle = this.getUnitTitle(entityQueryByKeyInfo, errorMap, successUnitMap, unitCode);
                        }
                        this.removeUnit(allUnitSet, unitCode);
                        if (message.equals(EORROR_OTHER)) {
                            for (Map.Entry fentry : unitInfo.getFormInfos().entrySet()) {
                                MistoreWorkFormInfo formInfo = (MistoreWorkFormInfo)fentry.getValue();
                                FailedUnit failedUnit = this.getFailedUnit(unitCode, unitTitle, errors, errorMap);
                                if (StringUtils.hasText(failedUnit.getMessage())) {
                                    System.out.println("unitcode=" + failedUnit.getCode() + "message=" + failedUnit.getMessage() + "::\u6709\u51b2\u7a81");
                                }
                                failedUnit.setType(ErrorType.FORM);
                                failedUnit.addForms(formInfo.getFormCode(), formInfo.getFormTitle(), formInfo.getMessage());
                            }
                            continue;
                        }
                        FailedUnit failedUnit = this.getFailedUnit(unitCode, unitTitle, errors, errorMap);
                        failedUnit.setType(ErrorType.UNIT);
                        failedUnit.setMessage(message);
                    }
                }
            }
            if (CollectionUtils.isEmpty(successUnits = work.getSuccessUnits())) continue;
            for (Map.Entry entry : successUnits.entrySet()) {
                MistoreWorkUnitInfo info = (MistoreWorkUnitInfo)entry.getValue();
                String unitCode = info.getUnitCode();
                String unitTitle = info.getUnitTitle();
                if (!StringUtils.hasText(unitTitle)) {
                    unitTitle = this.getUnitTitle(entityQueryByKeyInfo, errorMap, successUnitMap, unitCode);
                }
                this.removeUnit(allUnitSet, unitCode);
                successUnitMap.put(unitCode, new Lable(unitCode, unitTitle));
            }
        }
        List<String> allUnits1 = this.getAllUnits(jtableContext, targetEntityInfo);
        if (midstors.size() == errorMidstors.size()) {
            for (String string : allUnits1) {
                entityQueryByKeyInfo.setEntityKey(string);
                EntityByKeyReturnInfo info = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                ignoreUnitMap.put(string, new Lable(string, info.getEntity().getTitle()));
            }
        } else {
            List noAccessForms = batchDimensionValueFormInfo.getNoAccessForms();
            this.buildNoAcc(jtableContext, allUnitSet, formScheme, targetEntityInfo, entityQueryByKeyInfo, errorMap, errors, successUnitMap, noAccessForms);
            if (!CollectionUtils.isEmpty(errorMap) && !CollectionUtils.isEmpty(successUnitMap)) {
                for (String unitCode : errorMap.keySet()) {
                    successUnitMap.remove(unitCode);
                }
            }
            if (!CollectionUtils.isEmpty(allUnitSet)) {
                for (String unitCode : allUnitSet) {
                    entityQueryByKeyInfo.setEntityKey(unitCode);
                    EntityByKeyReturnInfo info = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                    ignoreUnitMap.put(unitCode, new Lable(unitCode, info.getEntity().getTitle()));
                }
            }
        }
        ArrayList<FailedUnit> sortedErrors = new ArrayList<FailedUnit>();
        for (FailedUnit e : errors) {
            sortedErrors.add(e.getType() == ErrorType.UNIT ? 0 : sortedErrors.size(), e);
        }
        MidstoreResultVO midstoreResultVO = new MidstoreResultVO();
        midstoreResultVO.setAllCount(allCount);
        ArrayList<Lable> successUnits = new ArrayList<Lable>(successUnitMap.values());
        midstoreResultVO.setSuccessCount(successUnits.size());
        midstoreResultVO.setSuccessUnits(successUnits);
        midstoreResultVO.setFailedCount(errors.size());
        midstoreResultVO.setErrors(sortedErrors);
        ArrayList<Lable> ignoreUnits = new ArrayList<Lable>(ignoreUnitMap.values());
        midstoreResultVO.setIgnoreCount(ignoreUnits.size());
        midstoreResultVO.setIgnoreUnits(ignoreUnits);
        this.returnRes(asyncTaskMonitor, midstoreResultVO);
        this.applicationContext.publishEvent(new MidstorePullFinishEvent(midstoreParam));
        StringBuilder logStr = new StringBuilder();
        logStr.append(midstoreParam.isPullData() ? "\u53d6\u6570\u5355\u4f4d\u5171" : "\u63a8\u6570\u5355\u4f4d\u5171").append(allCount).append("\u4e2a\uff0c\u6210\u529f").append(successUnits.size()).append("\u4e2a\uff0c\u5931\u8d25").append(errors.size()).append("\u4e2a\uff0c\u672a\u6267\u884c").append(ignoreUnits.size());
        logStr.append("\u6267\u884c\u4e2d\u95f4\u5e93\u65b9\u6848").append(midstors.size()).append("\u4e2a:").append(String.join((CharSequence)",", midstors));
        if (!CollectionUtils.isEmpty(errorMidstors)) {
            logStr.append(";\u5176\u4e2d\u6267\u884c\u9519\u8bef").append(errorMidstors.size()).append("\u4e2a:").append(String.join((CharSequence)UNITTAG, errorMidstors));
        }
        LogHelper.info((String)"\u4e2d\u95f4\u5e93", (String)midstoreParam.getBtnTitle(), (String)logStr.toString());
    }

    @Override
    public void exportResult(MidstoreResultVO resultVO, HttpServletResponse response) throws JQException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet failedSheet = MidstoreServiceImpl.buildsheet(workbook, "\u5931\u8d25\u5355\u4f4d", new String[]{"\u5355\u4f4d\u4ee3\u7801", "\u5355\u4f4d\u540d\u79f0", "\u9519\u8bef\u7c7b\u578b", "\u9519\u8bef\u539f\u56e0"});
        if (!CollectionUtils.isEmpty(resultVO.getErrors())) {
            for (FailedUnit failedUnit : resultVO.getErrors()) {
                Row row = failedSheet.createRow(failedSheet.getLastRowNum() + 1);
                Cell cell = row.createCell(0);
                cell.setCellValue(failedUnit.getCode());
                cell = row.createCell(1);
                cell.setCellValue(failedUnit.getTitle());
                cell = row.createCell(2);
                cell.setCellValue(failedUnit.getType().title());
                cell = row.createCell(3);
                if (failedUnit.getType() == ErrorType.UNIT) {
                    cell.setCellValue(failedUnit.getMessage());
                    continue;
                }
                String value = failedUnit.getForms().stream().map(form -> form.getCode() + ' ' + form.getTitle() + '\uff1a' + form.getMessage()).collect(Collectors.joining("\uff1b"));
                cell.setCellValue(value);
            }
        }
        Sheet successSheet = MidstoreServiceImpl.buildsheet(workbook, "\u6210\u529f\u5355\u4f4d", new String[]{"\u5355\u4f4d\u4ee3\u7801", "\u5355\u4f4d\u540d\u79f0"});
        if (!CollectionUtils.isEmpty(resultVO.getSuccessUnits())) {
            for (Lable lable : resultVO.getSuccessUnits()) {
                Row row = successSheet.createRow(successSheet.getLastRowNum() + 1);
                Cell cell = row.createCell(0);
                cell.setCellValue(lable.getCode());
                cell = row.createCell(1);
                cell.setCellValue(lable.getTitle());
            }
        }
        Sheet sheet = MidstoreServiceImpl.buildsheet(workbook, "\u672a\u6267\u884c\u5355\u4f4d", new String[]{"\u5355\u4f4d\u4ee3\u7801", "\u5355\u4f4d\u540d\u79f0"});
        if (!CollectionUtils.isEmpty(resultVO.getIgnoreUnits())) {
            for (Lable org : resultVO.getIgnoreUnits()) {
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);
                Cell cell = row.createCell(0);
                cell.setCellValue(org.getCode());
                cell = row.createCell(1);
                cell.setCellValue(org.getTitle());
            }
        }
        try {
            String string = "\u6267\u884c\u7ed3\u679c.xls";
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(string, "UTF-8"));
            BufferedOutputStream outputStream = new BufferedOutputStream((OutputStream)response.getOutputStream());
            response.setContentType("application/octet-stream");
            workbook.write(outputStream);
            ((OutputStream)outputStream).flush();
        }
        catch (IOException iOException) {
            logger.error(iOException.getMessage(), iOException);
        }
    }

    public static Sheet buildsheet(Workbook workbook, String sheetName, String[] headers) {
        Sheet sheet = workbook.createSheet(sheetName);
        Row head = sheet.createRow(0);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("\u9ed1\u4f53");
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setFont(font);
        for (int i = 0; i < headers.length; ++i) {
            Cell cell = head.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headStyle);
            sheet.setColumnWidth(i, 6000);
        }
        return sheet;
    }

    private void removeUnit(Set<String> allUnitSet, String unitCode) {
        if (!CollectionUtils.isEmpty(allUnitSet)) {
            allUnitSet.remove(unitCode);
        }
    }

    private void buildMidstoreUnits(JtableContext jtableContext, EntityViewData targetEntityInfo, List<String> allUnits, MidstoreSchemeDTO midScheme, MidstoreSchemeInfoDTO schemeInfo, Set<String> newOrgList) {
        if (!schemeInfo.isAllOrgData()) {
            MidstoreOrgDataDTO queryParam = new MidstoreOrgDataDTO();
            queryParam.setSchemeKey(midScheme.getKey());
            List midOrgDatas = this.orgDataService.list(queryParam);
            Map<String, MidstoreOrgDataDTO> orgDataCodeDic = midOrgDatas.stream().collect(Collectors.toMap(MidstoreOrgDataDO::getCode, MidstoreOrgDataDTO2 -> MidstoreOrgDataDTO2));
            for (String orgCode : allUnits) {
                if (!orgDataCodeDic.containsKey(orgCode)) continue;
                newOrgList.add(orgCode);
            }
        } else {
            newOrgList.addAll(allUnits);
        }
    }

    private void returnRes(AsyncTaskMonitor asyncTaskMonitor, MidstoreResultVO res) {
        ObjectMapper mapper = new ObjectMapper();
        String retStr = null;
        try {
            retStr = mapper.writeValueAsString((Object)res);
        }
        catch (JsonProcessingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (res.getFailedCount() > 0 || res.getSuccessCount() == 0) {
            asyncTaskMonitor.error("midstore_pull_push_failed", null, retStr);
        } else {
            asyncTaskMonitor.finish("midstore_pull_push_success", (Object)retStr);
        }
    }

    private List<String> getAllUnits(JtableContext jtableContext, EntityViewData targetEntityInfo) {
        String value = ((DimensionValue)jtableContext.getDimensionSet().get(targetEntityInfo.getDimensionName())).getValue();
        List allUnits = null;
        if (value.equals("")) {
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(jtableContext.getTaskKey());
            allUnits = this.jtableEntityService.getAllEntityKey(taskDefine.getDw(), jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey());
        } else {
            allUnits = Arrays.asList(value.split(UNITTAG));
        }
        return allUnits;
    }

    private void buildNoAcc(JtableContext jtableContext, Set<String> accessUnits, FormSchemeDefine formScheme, EntityViewData targetEntityInfo, EntityQueryByKeyInfo entityQueryByKeyInfo, Map<String, FailedUnit> errorMap, List<FailedUnit> errors, Map<String, Lable> successUnitMap, List<DimensionAccessFormInfo.NoAccessFormInfo> noAccessForms) {
        if (CollectionUtils.isEmpty(noAccessForms)) {
            return;
        }
        for (DimensionAccessFormInfo.NoAccessFormInfo noAccessForm : noAccessForms) {
            String unitKey;
            if (((DimensionValue)noAccessForm.getDimensions().get(targetEntityInfo.getDimensionName())).getValue().contains(noAccessForm.getFormKey())) {
                unitKey = noAccessForm.getFormKey();
                if (!CollectionUtils.isEmpty(accessUnits)) {
                    accessUnits.remove(unitKey);
                }
                String unitTitle = this.getUnitTitle(entityQueryByKeyInfo, errorMap, successUnitMap, unitKey);
                FailedUnit failedUnit = this.getFailedUnit(unitKey, unitTitle, errors, errorMap);
                failedUnit.setType(ErrorType.UNIT);
                failedUnit.setMessage(noAccessForm.getReason());
                continue;
            }
            unitKey = ((DimensionValue)noAccessForm.getDimensions().get(targetEntityInfo.getDimensionName())).getValue();
            String reason = noAccessForm.getReason();
            if ("\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6".equals(reason)) continue;
            if (!CollectionUtils.isEmpty(accessUnits)) {
                accessUnits.remove(unitKey);
            }
            String unitTitle = this.getUnitTitle(entityQueryByKeyInfo, errorMap, successUnitMap, unitKey);
            if (this.workflow.queryStartType(jtableContext.getFormSchemeKey()) == WorkFlowType.ENTITY && reason.equals("\u8be5\u5de5\u4f5c\u6d41\u8282\u70b9\u4e0d\u53ef\u7f16\u8f91")) {
                FailedUnit failedUnit = this.getFailedUnit(unitKey, unitTitle, errors, errorMap);
                failedUnit.setMessage(reason);
                failedUnit.setType(ErrorType.UNIT);
                continue;
            }
            FormData form = this.jtableParamService.getReport(noAccessForm.getFormKey(), formScheme.getKey());
            FailedUnit failedUnit = this.getFailedUnit(unitKey, unitTitle, errors, errorMap);
            if (StringUtils.hasText(failedUnit.getMessage())) {
                System.out.println("unitcode=" + failedUnit.getCode() + "message=" + failedUnit.getMessage() + "::\u6709\u51b2\u7a81");
            }
            failedUnit.setType(ErrorType.FORM);
            failedUnit.addForms(form.getCode(), form.getTitle(), reason);
        }
    }

    private String getUnitTitle(EntityQueryByKeyInfo entityQueryByKeyInfo, Map<String, FailedUnit> failUnitMap, Map<String, Lable> successUnitMap, String unitCode) {
        FailedUnit unitf = failUnitMap.get(unitCode);
        if (unitf != null && StringUtils.hasText(unitf.getTitle())) {
            return unitf.getTitle();
        }
        Lable units = successUnitMap.get(unitCode);
        if (units != null && StringUtils.hasText(units.getTitle())) {
            return units.getTitle();
        }
        entityQueryByKeyInfo.setEntityKey(unitCode);
        EntityByKeyReturnInfo info = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        return info.getEntity().getTitle();
    }

    private FailedUnit getFailedUnit(String unitCode, String unitTitle, List<FailedUnit> errors, Map<String, FailedUnit> errorMap) {
        FailedUnit failedUnit = errorMap.get(unitCode);
        if (failedUnit == null) {
            failedUnit = new FailedUnit(unitCode, unitTitle);
            errorMap.put(unitCode, failedUnit);
            errors.add(failedUnit);
        }
        return failedUnit;
    }

    private void calculate(AsyncTaskMonitor asyncTaskMonitor, JtableContext jtableContext, List<String> formKeys) {
        DataEntryAsyncProgressMonitor dataEntryAsyncProgressMonitor = new DataEntryAsyncProgressMonitor(asyncTaskMonitor, 0.2, 0.8);
        String calculate = this.taskOptionController.getValue(jtableContext.getTaskKey(), "AUTOCALCULATE_AFTER_MIDSTOREPULL");
        if ("1".equals(calculate)) {
            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
            batchCalculateInfo.setDimensionSet(jtableContext.getDimensionSet());
            batchCalculateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
            batchCalculateInfo.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
            batchCalculateInfo.setTaskKey(jtableContext.getTaskKey());
            batchCalculateInfo.setVariableMap(jtableContext.getVariableMap());
            HashMap formulas = new HashMap();
            for (String formKey : formKeys) {
                formulas.put(formKey, new ArrayList());
            }
            batchCalculateInfo.setFormulas(formulas);
            this.batchCalculateService.batchCalculateForm(batchCalculateInfo, (AsyncTaskMonitor)dataEntryAsyncProgressMonitor);
        }
    }

    private String getDWDimensionName(String taskKey) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        String dw = taskDefine.getDw();
        return this.entityMetaService.getDimensionName(dw);
    }
}

