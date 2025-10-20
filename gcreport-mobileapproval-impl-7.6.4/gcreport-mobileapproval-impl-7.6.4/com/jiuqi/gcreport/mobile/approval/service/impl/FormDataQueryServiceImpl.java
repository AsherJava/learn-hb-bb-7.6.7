/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.mobile.approval.vo.ActionDataReturnInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.FieldDataInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.FormDataQueryInfo
 *  com.jiuqi.gcreport.mobile.approval.vo.QueryParamInfo
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.file.FileAreaService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.file.FileStatus
 *  com.jiuqi.nr.file.impl.FileInfoService
 *  com.jiuqi.nr.file.web.FileType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.OrgDataService
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.mobile.approval.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.mobile.approval.service.FormDataQueryService;
import com.jiuqi.gcreport.mobile.approval.vo.ActionDataReturnInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FieldDataInfo;
import com.jiuqi.gcreport.mobile.approval.vo.FormDataQueryInfo;
import com.jiuqi.gcreport.mobile.approval.vo.QueryParamInfo;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.file.FileAreaService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.file.FileStatus;
import com.jiuqi.nr.file.impl.FileInfoService;
import com.jiuqi.nr.file.web.FileType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormDataQueryServiceImpl
implements FormDataQueryService {
    private static final Logger logger = LoggerFactory.getLogger(FormDataQueryServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileService fileService;
    @Autowired
    private IDataentryFlowService iDataentryFlowService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;

    @Override
    public List<List<FieldDataInfo>> formDataQuery(FormDataQueryInfo formDataQueryInfo) {
        ArrayList<List<FieldDataInfo>> fieldDatas = new ArrayList<List<FieldDataInfo>>();
        TaskDefine task = this.runtimeView.queryTaskDefine(formDataQueryInfo.getTaskKey());
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formDataQueryInfo.getFormSchemeKey());
        assert (formScheme != null);
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)formDataQueryInfo.getFormCode())) {
            formDataQueryInfo.setFormKey("FMDM");
        }
        FormDefine form = null;
        try {
            form = this.runtimeView.queryFormByCodeInScheme(formDataQueryInfo.getFormSchemeKey(), formDataQueryInfo.getFormCode());
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        if (form == null) {
            throw new RuntimeException("\u67e5\u8be2\u8868\u5355\u4fe1\u606f\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u662f\u5426\u914d\u7f6e\u5173\u952e\u6307\u6807\u8868\u3002");
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        try {
            String masterEntitiesKey = form.getMasterEntitiesKey();
            if (com.jiuqi.bi.util.StringUtils.isEmpty((String)masterEntitiesKey) && formScheme != null) {
                masterEntitiesKey = formScheme.getMasterEntitiesKey();
            }
            if (com.jiuqi.bi.util.StringUtils.isEmpty((String)masterEntitiesKey) && task != null) {
                masterEntitiesKey = task.getMasterEntitiesKey();
            }
            String[] entityViewKeys = masterEntitiesKey.split(";");
            for (String entityViewKey : entityViewKeys) {
                EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityViewKey);
                String dimensionName = dataAssist.getDimensionName(entityViewDefine);
                if (formScheme.getDw().equals(entityViewKey)) {
                    dimensionValueSet.setValue(dimensionName, (Object)formDataQueryInfo.getUnitKey());
                    continue;
                }
                if (formScheme.getDateTime().equals(entityViewKey)) {
                    dimensionValueSet.setValue(dimensionName, (Object)formDataQueryInfo.getPeriod());
                    continue;
                }
                if ("MD_GCORGTYPE".equals(dimensionName)) {
                    dimensionValueSet.setValue(dimensionName, (Object)"MD_ORG_CORPORATE");
                    continue;
                }
                if (!"MD_CURRENCY".equals(dimensionName)) continue;
                dimensionValueSet.setValue(dimensionName, (Object)"CNY");
            }
            FormDataQueryServiceImpl.dimensionSetAdjTypeValue(dimensionValueSet, formDataQueryInfo.getTaskKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        List regions = this.runtimeView.getAllRegionsInForm(form.getKey());
        DataRegionDefine fixRegion = null;
        for (DataRegionDefine region : regions) {
            if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) continue;
            fixRegion = region;
            break;
        }
        List links = this.runtimeView.getAllLinksInRegion(fixRegion.getKey());
        Collections.sort(links, new Comparator<DataLinkDefine>(){

            @Override
            public int compare(DataLinkDefine link0, DataLinkDefine link1) {
                if (link0.getPosX() != link1.getPosX()) {
                    return link0.getPosX() - link1.getPosX();
                }
                return link0.getPosY() - link1.getPosY();
            }
        });
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(formDataQueryInfo.getFormSchemeKey());
        queryEnvironment.setRegionKey(fixRegion.getKey());
        queryEnvironment.setFormKey(form.getKey());
        queryEnvironment.setFormCode(form.getFormCode());
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setMasterKeys(dimensionValueSet);
        HashMap<String, FieldDataInfo> fieldDataMap = new HashMap<String, FieldDataInfo>();
        HashMap<String, Integer> fieldIndexMap = new HashMap<String, Integer>();
        int rowMin = -1;
        int rowMax = -1;
        int colMin = -1;
        int colMax = -1;
        for (DataLinkDefine link : links) {
            if (link.getPosX() < colMin || colMin < 0) {
                colMin = link.getPosX();
            }
            if (link.getPosX() > colMax) {
                colMax = link.getPosX();
            }
            if (link.getPosY() < rowMin || rowMin < 0) {
                rowMin = link.getPosY();
            }
            if (link.getPosY() > rowMax) {
                rowMax = link.getPosY();
            }
            String linkExpression = link.getLinkExpression();
            FieldDataInfo fieldDataInfo = new FieldDataInfo();
            if (!UUIDUtils.isUUID((String)linkExpression)) continue;
            FieldDefine field = null;
            try {
                field = this.dataDefinitionRuntimeController.queryFieldDefine(linkExpression);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            int fieldQueryIndex = dataQuery.addColumn(field);
            fieldIndexMap.put(linkExpression, fieldQueryIndex);
            fieldDataInfo.setFieldCode(field.getCode());
            fieldDataInfo.setFieldTitle(field.getTitle());
            fieldDataInfo.setFieldType(field.getType().getValue());
            fieldDataMap.put(linkExpression, fieldDataInfo);
        }
        IReadonlyTable readonlyTable = null;
        try {
            readonlyTable = dataQuery.executeReader(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (readonlyTable.getCount() > 0) {
            int rowNum = rowMax - rowMin + 1;
            int colNum = colMax - colMin + 1;
            for (int rowIndex = 0; rowIndex < rowNum; ++rowIndex) {
                ArrayList<FieldDataInfo> rowFieldDatas = new ArrayList<FieldDataInfo>(colNum);
                for (int colIndex = 0; colIndex < colNum; ++colIndex) {
                    FieldDataInfo fieldDataInfo = new FieldDataInfo();
                    rowFieldDatas.add(fieldDataInfo);
                }
                fieldDatas.add(rowFieldDatas);
            }
            IDataRow dataRow = readonlyTable.getItem(0);
            for (DataLinkDefine link : links) {
                String fieldData = dataRow.getAsString(((Integer)fieldIndexMap.get(link.getLinkExpression())).intValue());
                FieldDataInfo fieldDataInfo = (FieldDataInfo)fieldDataMap.get(link.getLinkExpression());
                fieldDataInfo.setData(fieldData);
                List rowFieldDatas = (List)fieldDatas.get(link.getPosY() - rowMin);
                rowFieldDatas.set(link.getPosX() - colMin, fieldDataInfo);
            }
        }
        return fieldDatas;
    }

    @Override
    public void previewFile(String fileKey, HttpServletResponse response) {
        String extension;
        FileInfo fileInfo = this.fileInfoService.getFileInfo(fileKey, "JTABLEAREA", FileStatus.AVAILABLE);
        if (null != fileInfo && null != (extension = fileInfo.getExtension())) {
            FileAreaService fileAreaService = this.fileService.area("JTABLEAREA");
            byte[] downloadFile = fileAreaService.download(fileKey);
            byte[] bytesPdf = null;
            if (extension.endsWith("gif") || extension.endsWith("jpg") || extension.endsWith("jpeg") || extension.endsWith("jpe") || extension.endsWith("jif") || extension.endsWith("pcx") || extension.endsWith("dcx") || extension.endsWith("pic") || extension.endsWith("png") || extension.endsWith("tga") || extension.endsWith("tif") || extension.endsWith("tiff") || extension.endsWith("xif") || extension.endsWith("wmf") || extension.endsWith("jfif")) {
                try {
                    FileType fileType = FileType.valueOfExtension((String)extension);
                    if ("".equals(fileType.getContentType())) {
                        response.setContentType("application/octet-stream");
                    } else {
                        response.setContentType(fileType.getContentType());
                    }
                    fileAreaService.download(fileKey, (OutputStream)response.getOutputStream());
                    response.flushBuffer();
                    return;
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            } else if (extension.endsWith("txt")) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String filecharset = FormDataQueryServiceImpl.getFilecharset(downloadFile);
                    HashMap<String, Object> returnMap = new HashMap<String, Object>();
                    returnMap.put("code", filecharset);
                    returnMap.put("bytes", downloadFile);
                    bytesPdf = mapper.writeValueAsBytes(returnMap);
                    bytesPdf = downloadFile;
                }
                catch (JsonProcessingException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            } else {
                bytesPdf = downloadFile;
            }
            if (null != bytesPdf) {
                try {
                    String filename = "previewFile" + extension;
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-disposition", "attachment;filename=" + filename + "");
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write(bytesPdf);
                    response.flushBuffer();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public ActionDataReturnInfo queryWorkflowDataInfo(QueryParamInfo param) {
        WorkflowDataBean workflowData = new WorkflowDataBean();
        workflowData.setFormKey(param.getFormKey());
        workflowData.setFormGroupKey(param.getFormGroupKey());
        workflowData.setFormSchemeKey(param.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet(param);
        workflowData.setDimSet(dimensionValueSet);
        ActionDataReturnInfo actionDataReturnInfo = new ActionDataReturnInfo();
        actionDataReturnInfo.setWorkflowDataInfos(this.iDataentryFlowService.queryWorkflowDataInfo(workflowData));
        actionDataReturnInfo.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet));
        List allFormulaSchemeDefinesByFormScheme = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(param.getFormSchemeKey());
        List formulaSchemeDefines = allFormulaSchemeDefinesByFormScheme.stream().filter(formulaSchemeDefine -> {
            FormulaSchemeType schemeType = formulaSchemeDefine.getFormulaSchemeType();
            return schemeType.equals((Object)FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT) && formulaSchemeDefine.isDefault();
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(formulaSchemeDefines)) {
            actionDataReturnInfo.setFormulaSchemeKey(((FormulaSchemeDefine)formulaSchemeDefines.get(0)).getKey());
        }
        return actionDataReturnInfo;
    }

    private static String getFilecharset(byte[] sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            ByteArrayInputStream bis = new ByteArrayInputStream(sourceFile);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset;
            }
            if (first3Bytes[0] == -1 && first3Bytes[1] == -2) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == -2 && first3Bytes[1] == -1) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == -17 && first3Bytes[1] == -69 && first3Bytes[2] == -65) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    ++loc;
                    if (read >= 240 || 128 <= read && read <= 191) break;
                    if (192 <= read && read <= 223) {
                        read = bis.read();
                        if (128 > read || read > 191) break;
                        continue;
                    }
                    if (224 > read || read > 239) continue;
                    read = bis.read();
                    if (128 > read || read > 191 || 128 > (read = bis.read()) || read > 191) break;
                    charset = "UTF-8";
                    break;
                }
            }
            bis.close();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return charset;
    }

    private DimensionValueSet getDimensionValueSet(QueryParamInfo param) {
        String[] entityViewKeys;
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(context);
        String masterEntitiesKey = formScheme.getMasterEntitiesKey();
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)masterEntitiesKey) && formScheme != null) {
            masterEntitiesKey = formScheme.getMasterEntitiesKey();
        }
        String mdGcorgtype = "";
        if (!com.jiuqi.bi.util.StringUtils.isEmpty((String)param.getOrgType()) && !"MD_ORG".equals(param.getOrgType())) {
            YearPeriodObject yp;
            String orgType = param.getOrgType().split("@")[0];
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)(yp = new YearPeriodObject(param.getFormSchemeKey(), param.getPeriod())));
            GcOrgCacheVO gcOrgCacheVO = tool.getOrgByCode(param.getUnitKey());
            if (gcOrgCacheVO == null) {
                throw new RuntimeException("\u67e5\u8be2\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0cunitKye=" + param.getUnitKey());
            }
            mdGcorgtype = gcOrgCacheVO.getOrgTypeId();
        } else {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode(param.getUnitKey());
            orgDTO.setCategoryname("MD_ORG");
            orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            OrgDO orgDO = ((OrgDataService)SpringBeanUtils.getBean(OrgDataService.class)).get(orgDTO);
            if (null == orgDO) {
                throw new RuntimeException("\u67e5\u8be2\u5355\u4f4d\u4fe1\u606f\u4e3a\u7a7a\uff0cunitKye=" + param.getUnitKey());
            }
            mdGcorgtype = (String)orgDO.getValueOf("ORGTYPEID");
        }
        for (String entityViewKey : entityViewKeys = masterEntitiesKey.split(";")) {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(entityViewKey);
            String dimensionName = dataAssist.getDimensionName(entityViewDefine);
            if (formScheme.getDw().equals(entityViewKey)) {
                dimensionValueSet.setValue(dimensionName, (Object)param.getUnitKey());
                continue;
            }
            if (formScheme.getDateTime().equals(entityViewKey)) {
                dimensionValueSet.setValue(dimensionName, (Object)param.getPeriod());
                continue;
            }
            if ("MD_GCORGTYPE".equals(dimensionName)) {
                dimensionValueSet.setValue(dimensionName, (Object)mdGcorgtype);
                continue;
            }
            if (!"MD_CURRENCY".equals(dimensionName)) continue;
            dimensionValueSet.setValue(dimensionName, (Object)"CNY");
        }
        FormDataQueryServiceImpl.dimensionSetAdjTypeValue(dimensionValueSet, param.getTaskKey());
        return dimensionValueSet;
    }

    public static String getDwEntitieTableByTaskKey(String taskKey) {
        RuntimeViewController runtimeViewController = (RuntimeViewController)SpringContextUtils.getBean(RuntimeViewController.class);
        TaskDefine taskDefine = runtimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null || StringUtils.isEmpty((String)taskDefine.getDw())) {
            return null;
        }
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
        TableModelDefine tableDefine = entityMetaService.getTableModel(taskDefine.getDw());
        return tableDefine.getName();
    }

    public static void dimensionSetAdjTypeValue(DimensionValueSet dimensionValueSet, String taskId) {
        if (FormDataQueryServiceImpl.isExisAdjType(taskId)) {
            dimensionValueSet.setValue("MD_GCADJTYPE", (Object)"BEFOREADJ");
        }
        if (FormDataQueryServiceImpl.isExistAdjust(taskId)) {
            dimensionValueSet.setValue("ADJUST", (Object)"0");
        }
    }

    public static boolean isExisAdjType(String taskId) {
        RuntimeViewController runtimeViewController = (RuntimeViewController)SpringContextUtils.getBean(RuntimeViewController.class);
        TaskDefine taskDefine = runtimeViewController.queryTaskDefine(taskId);
        String dimes = taskDefine.getDims();
        return !StringUtils.isEmpty((String)dimes) && dimes.indexOf("MD_GCADJTYPE") > -1;
    }

    public static boolean isExistAdjust(String taskId) {
        RuntimeViewController runtimeViewController = (RuntimeViewController)SpringContextUtils.getBean(RuntimeViewController.class);
        TaskDefine taskDefine = runtimeViewController.queryTaskDefine(taskId);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        return runtimeDataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme()) != false;
    }
}

