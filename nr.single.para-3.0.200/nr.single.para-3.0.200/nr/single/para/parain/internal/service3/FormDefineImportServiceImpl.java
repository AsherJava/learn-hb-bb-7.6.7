/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ReportTableType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  nr.single.map.common.ImportConsts
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import nr.single.map.common.ImportConsts;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.para.compare.bean.ParaImportInfoResult;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.ISingleCompareDataFormService;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IFormDefineImportService;
import nr.single.para.parain.service.IFormGroupDefineImportService;
import nr.single.para.parain.service.IFormRegionDefineImportService;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormDefineImportServiceImpl
implements IFormDefineImportService {
    private static final Logger log = LoggerFactory.getLogger(FormDefineImportServiceImpl.class);
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IParaImportCommonService paraImportService;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormGroupDefineImportService formGroupService;
    @Autowired
    private IFormRegionDefineImportService formRegionService;
    @Autowired
    private ISingleCompareDataFormService formCompareService;

    @Override
    public String importFormGroupDefines(TaskImportContext importContext, String formSchemeKey) throws Exception {
        return this.formGroupService.importFormGroupDefines(importContext, formSchemeKey);
    }

    @Override
    public void deleteOtherFormGroups(TaskImportContext importContext, String formSchemeKey) throws Exception {
        this.formGroupService.deleteOtherFormgGroups(importContext, formSchemeKey);
    }

    @Override
    public void importFormDefines(TaskImportContext importContext, String formSchemeKey) throws Exception {
        DesignTaskDefine taskDefine;
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        String taskKey = importContext.getTaskKey();
        importContext.getCurContext().setTaskKey(taskKey);
        List<RepInfo> sortRepList = importContext.getSortRepList();
        Map<String, List<DesignFormGroupDefine>> groupFormMap = importContext.getGroupFormMap();
        ParaInfo para = importContext.getParaInfo();
        Map<String, DesignFormDefine> formCache = importContext.getFormCache();
        formCache.clear();
        List oldFormList = this.viewController.queryAllFormDefinesByFormScheme(formSchemeKey);
        if (null != oldFormList && oldFormList.size() > 0) {
            for (DesignFormDefine form : oldFormList) {
                formCache.put(form.getFormCode(), form);
            }
        }
        if (null == (taskDefine = importContext.getTaskDefine())) {
            taskDefine = this.viewController.queryTaskDefine(taskKey);
            importContext.setTaskDefine(taskDefine);
        }
        if (taskDefine != null && StringUtils.isNotEmpty((String)taskDefine.getDw())) {
            if (StringUtils.isEmpty((String)importContext.getEntityTableKey())) {
                importContext.setEntityTableKey(taskDefine.getDw());
            }
            if (StringUtils.isEmpty((String)importContext.getEntityId())) {
                importContext.setEntityId(taskDefine.getDw());
            }
        }
        String dataSchemeKey = taskDefine.getDataScheme();
        this.formGroupService.importDataGroups(importContext);
        Object tableGroup = this.dataSchemeService.getDataGroup(taskDefine.getKey());
        if (tableGroup == null) {
            List groups = this.dataSchemeService.getDataGroupByScheme(dataSchemeKey);
            for (Object group : groups) {
                if (!StringUtils.isNotEmpty((String)group.getTitle()) || !group.getTitle().equalsIgnoreCase(taskDefine.getTitle())) continue;
                tableGroup = group;
            }
        }
        importContext.setTableGroupKey(tableGroup.getKey());
        this.paraImportService.MakeTaskFieldsCache(importContext);
        ArrayList NewEntityList = new ArrayList();
        List dims = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey);
        for (DesignDataDimension dim : dims) {
            String tableKey = dim.getDimKey();
            if (dim.getDimensionType() == DimensionType.UNIT) {
                IEntityDefine entityTable;
                TableModelDefine tableModel;
                if (StringUtils.isEmpty((String)importContext.getEntityTableKey())) {
                    importContext.setEntityTableKey(tableKey);
                    importContext.setEntityId(tableKey);
                }
                if (!StringUtils.isEmpty((String)(tableModel = this.entityMetaService.getTableModel((entityTable = this.entityMetaService.queryEntity(tableKey)).getId())).getBizKeys())) continue;
                log.info("\u4e3b\u4f53\u952e\u503c\u4e0d\u5b58\u5728\uff1a" + entityTable.getTitle());
                continue;
            }
            if (dim.getDimensionType() != DimensionType.PERIOD || !StringUtils.isEmpty((String)importContext.getPeriodTableKey())) continue;
            importContext.setPeriodTableKey(tableKey);
        }
        importContext.setFmdmIsData(true);
        importContext.setOnlyEntityCode(ImportConsts.getCodeStrings(NewEntityList));
        this.paraImportService.doMeasureUnitMap(importContext);
        FMRepInfo singleFmdm = para.getFmRepInfo();
        importContext.getMapScheme().getTableInfos().clear();
        List<DesignFormGroupDefine> groups = null;
        importContext.setUniqueField(this.paraImportService.getUniqueField(singleFmdm, sortRepList));
        ParaImportInfoResult formsLog = null;
        HashMap<String, CompareDataFormDTO> oldFormCompareDic = new HashMap<String, CompareDataFormDTO>();
        if (importContext.getCompareInfo() != null) {
            CompareDataFormDTO formQueryParam = new CompareDataFormDTO();
            formQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            formQueryParam.setDataType(CompareDataType.DATA_FORM);
            List<CompareDataFormDTO> oldFormCompareList = this.formCompareService.list(formQueryParam);
            for (CompareDataFormDTO oldData : oldFormCompareList) {
                oldFormCompareDic.put(oldData.getSingleCode(), oldData);
            }
            if (importContext.getImportResult() != null) {
                formsLog = importContext.getImportResult().getLogInfo(CompareDataType.DATA_FORM, "forms", "\u8868\u5355");
            }
        }
        HashSet<String> importFormKeys = new HashSet<String>();
        double startPos = importContext.getCurProgress();
        for (RepInfo rep : sortRepList) {
            importContext.onProgress(startPos += 0.1 / (double)sortRepList.size(), "\u5bfc\u5165\u8868\u5355\uff1a" + rep.getTitle());
            if (null != groupFormMap && groupFormMap.containsKey(rep.getCode().toUpperCase())) {
                groups = groupFormMap.get(rep.getCode().toUpperCase());
            }
            CompareDataFormDTO formComopare = null;
            if (oldFormCompareDic.containsKey(rep.getCode())) {
                formComopare = (CompareDataFormDTO)oldFormCompareDic.get(rep.getCode());
                if (formsLog != null) {
                    ParaImportInfoResult formLog = new ParaImportInfoResult();
                    formLog.copyForm(formComopare);
                    formLog.setSuccess(true);
                    formsLog.addItem(formLog);
                }
            }
            DesignFormDefine formDefine = null;
            if (formCache.containsKey(rep.getCode())) {
                formDefine = formCache.get(rep.getCode());
            }
            if (singleFmdm == rep || rep.isFMDM() || "FMDM".equalsIgnoreCase(rep.getCode())) {
                List fmdmForms;
                if (formDefine == null && (fmdmForms = this.viewController.queryFormsByTypeInScheme(formSchemeKey, FormType.FORM_TYPE_NEWFMDM)) != null && !fmdmForms.isEmpty()) {
                    formDefine = (DesignFormDefine)fmdmForms.get(0);
                }
                SingleFileFmdmInfo fmdmInfo = importContext.getMapScheme().getFmdmInfo();
                importContext.getParaInfo().getFMReportData();
                if (!"CSFMDM".equalsIgnoreCase(singleFmdm.getCode().toUpperCase())) {
                    formDefine = this.getFormDefine(importContext, (RepInfo)singleFmdm, formSchemeKey, groups, true, (SingleFileTableInfo)fmdmInfo, formDefine, formComopare);
                }
            } else {
                if (rep.getTableType() == ReportTableType.RTT_BLOBTABLE || rep.getTableType() == ReportTableType.RTT_WORDTABLE) {
                    importContext.getParaInfo().getFJReportData(rep);
                }
                SingleFileTableInfo singleTable = importContext.getMapScheme().getNewTableInfo();
                singleTable.setSingleTableCode(rep.getCode());
                singleTable.setSingleTableTitle(rep.getTitle());
                singleTable.setSingleTableType(rep.getTableType().getValue());
                importContext.getMapScheme().getTableInfos().add(singleTable);
                formDefine = this.getFormDefine(importContext, rep, formSchemeKey, groups, false, singleTable, formDefine, formComopare);
                if (formDefine != null && !formCache.containsKey(formDefine.getFormCode())) {
                    formCache.put(formDefine.getFormCode(), formDefine);
                }
            }
            if (formDefine == null) continue;
            importFormKeys.add(formDefine.getKey());
        }
        for (String dataGroupKey : importContext.getSchemeInfoCache().getFormGroupMapDataGroups().values()) {
            importContext.getFormInfoCahche().getDeleteTableGroupKeys().add(dataGroupKey);
        }
        this.formRegionService.updateFormInfoCacheToServer(importContext, true, true, true, true);
        if (importContext.getImportOption().isOverWriteAll()) {
            for (DesignFormDefine formDefine : formCache.values()) {
                if (importFormKeys.contains(formDefine.getKey())) continue;
                this.viewController.deleteFormDefine(formDefine.getKey());
            }
        }
    }

    private DesignFormDefine getFormDefine(TaskImportContext importContext, RepInfo repInfo, String formSchemeKey, List<DesignFormGroupDefine> groups, boolean isFMDM, SingleFileTableInfo singleTable, DesignFormDefine formDefine, CompareDataFormDTO formComopare) throws Exception {
        DesignDataGroup pDataGroup;
        boolean isFormNew;
        Map<String, DesignFormDefine> formCache = importContext.getFormCache();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        String dataSchemeKey = importContext.getDataSchemeKey();
        boolean bl = isFormNew = null == formDefine;
        if (isFMDM && !isFormNew) {
            if (repInfo == null || repInfo.getDefs() == null && repInfo.getDefs().getZbsNoZDM().size() == 0) {
                if (repInfo != null) {
                    log.info("\u62a5\u8868\uff1a" + repInfo.getCode() + ",\u65e0\u6307\u6807\uff0c\u4e0d\u7528\u66f4\u65b0");
                } else {
                    log.info("\u62a5\u8868\uff1a,\u65e0\u6307\u6807\uff0c\u4e0d\u7528\u66f4\u65b0");
                }
                return formDefine;
            }
            FMRepInfo singleFmdm = (FMRepInfo)repInfo;
            if (singleFmdm.isNotDefine()) {
                log.info("\u62a5\u8868\uff1a" + repInfo.getCode() + ",\u65e0\u6307\u6807\uff0c\u4e0d\u7528\u66f4\u65b0");
                return formDefine;
            }
        }
        if (formComopare != null) {
            importContext.getCompareFormDic().put(formComopare.getSingleCode(), formComopare);
            if (formComopare.getUpdateType() == CompareUpdateType.UPDATE_IGNORE) {
                log.info("\u62a5\u8868\uff1a" + repInfo.getCode() + ",\u5ffd\u7565\u5bfc\u5165");
                if (formDefine != null) {
                    this.formGroupService.updateFormGroup(groups, isFMDM, formDefine.getKey());
                }
                return formDefine;
            }
            if (formComopare.getUpdateType() == CompareUpdateType.UPDATE_UNOVER) {
                log.info("\u62a5\u8868\uff1a" + repInfo.getCode() + ",\u4e0d\u8986\u76d6\u5bfc\u5165");
                if (formDefine != null) {
                    this.formGroupService.updateFormGroup(groups, isFMDM, formDefine.getKey());
                }
                return formDefine;
            }
            if (formComopare.getUpdateType() != CompareUpdateType.UPDATE_NEW && formComopare.getUpdateType() != CompareUpdateType.UPDATE_OVER && formComopare.getUpdateType() == CompareUpdateType.UPDATE_APPOINT) {
                if (StringUtils.isNotEmpty((String)formComopare.getNetCode()) && formCache.containsKey(formComopare.getNetCode())) {
                    formDefine = formCache.get(formComopare.getNetCode());
                } else {
                    log.info("\u62a5\u8868\uff1a" + repInfo.getCode() + ",\u6307\u5b9a\u7684\u62a5\u8868\u4e0d\u5b58\u5728\uff1a" + formComopare.getNetCode());
                    return formDefine;
                }
            }
        }
        if (isFormNew) {
            formDefine = this.viewController.createFormDefine();
            formDefine.setOwnerLevelAndId(importContext.getCurServerCode());
            formDefine.setFormScheme(formSchemeKey);
        }
        this.setFormDefineAttr(importContext, formDefine, repInfo, isFMDM, isFormNew, groups, singleTable);
        this.setCreateUserAndTime(formDefine);
        if (isFormNew) {
            this.viewController.insertFormDefine(formDefine);
            if (null != groups) {
                for (DesignFormGroupDefine group : groups) {
                    this.viewController.addFormToGroup(formDefine.getKey(), group.getKey());
                }
            }
        } else {
            this.viewController.updateFormDefine(formDefine);
            this.formGroupService.updateFormGroup(groups, isFMDM, formDefine.getKey());
        }
        String msg = formDefine.getFormCode() + " " + formDefine.getTitle() + " " + formDefine.getKey().toString() + ",\u65f6\u95f4:" + new Date().toString();
        log.info("\u5bfc\u5165\u8868\u5355:{}", (Object)msg);
        String parentGroupKey = null;
        if (null != groups && (pDataGroup = this.dataSchemeService.getDataGroup(parentGroupKey = groups.get(0).getKey())) == null && importContext.getSchemeInfoCache().getFormGroupMapDataGroups().containsKey(parentGroupKey)) {
            parentGroupKey = importContext.getSchemeInfoCache().getFormGroupMapDataGroups().get(parentGroupKey);
        }
        DesignDataGroup dataGroup = this.formGroupService.createNewDataGroup(parentGroupKey, formDefine.getKey(), formDefine.getTitle(), formDefine.getFormCode(), "", OrderGenerator.newOrder(), dataSchemeKey);
        this.formRegionService.importFormRegions(importContext, repInfo, formDefine, isFormNew, isFMDM, singleTable, dataGroup, formComopare);
        this.viewController.updateFormDefine(formDefine);
        if (importContext.getFormInfoCahche().getGroupKeyTableCache().containsKey(dataGroup.getKey())) {
            Map<String, TableInfoDefine> tableKeys = importContext.getFormInfoCahche().getGroupKeyTableCache().get(dataGroup.getKey());
            if (tableKeys.isEmpty()) {
                importContext.getFormInfoCahche().getDeleteTableGroupKeys().add(dataGroup.getKey());
            }
        } else {
            importContext.getFormInfoCahche().getDeleteTableGroupKeys().add(dataGroup.getKey());
        }
        return formDefine;
    }

    private void setCreateUserAndTime(DesignFormDefine formDefine) {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        String userName = "";
        userName = user == null ? "\u7ba1\u7406\u5458" : user.getName();
        formDefine.setUpdateUser(userName);
    }

    private void setFormDefineAttr(TaskImportContext importContext, DesignFormDefine formDefine, RepInfo repInfo, boolean isFMDM, boolean isFormNew, List<DesignFormGroupDefine> groups, SingleFileTableInfo singleTable) {
        formDefine.setFormPeriodType(importContext.getTaskDefine().getPeriodType());
        formDefine.setFormCode(repInfo.getCode());
        formDefine.setTitle(repInfo.getTitle());
        formDefine.setSubTitle(repInfo.getSubTitle());
        formDefine.setFormCondition(repInfo.getFilter());
        if (StringUtils.isEmpty((String)repInfo.getMoneyUnit())) {
            formDefine.setMeasureUnit(importContext.getSchemeInfoCache().getMeasureUnitTableKey() + ";NotDimession");
        } else if (importContext.getSchemeInfoCache().getMeasureCahce().containsKey(repInfo.getMoneyUnit())) {
            String measureUnitCode = importContext.getSchemeInfoCache().getMeasureCahce().get(repInfo.getMoneyUnit()).getCode();
            if (StringUtils.isNotEmpty((String)measureUnitCode) && measureUnitCode.equalsIgnoreCase(importContext.getSchemeInfoCache().getMeasureUnitCode())) {
                formDefine.setMeasureUnit(null);
            } else {
                formDefine.setMeasureUnit(importContext.getSchemeInfoCache().getMeasureUnitTableKey() + ";" + measureUnitCode);
            }
        } else {
            formDefine.setMeasureUnit(importContext.getSchemeInfoCache().getMeasureUnitTableKey() + ";NotDimession");
        }
        formDefine.setSerialNumber(repInfo.getSubNo());
        formDefine.setIsGather(repInfo.getGather());
        formDefine.setOrder(OrderGenerator.newOrder());
        formDefine.setFormType(this.paraImportService.getFormTypeFromSingle(repInfo.getTableType(), isFMDM));
        if (isFMDM) {
            formDefine.setMasterEntitiesKey(null);
        }
        if (null != repInfo.getReportData()) {
            formDefine.setBinaryData(repInfo.getReportData().getGridBytes());
        }
        singleTable.setSingleTableCode(repInfo.getCode());
        singleTable.setSingleTableTitle(repInfo.getTitle());
        singleTable.setSingleTableType(repInfo.getTableType().getValue());
        singleTable.setNetFormCode(formDefine.getFormCode());
        singleTable.setNetFormKey(formDefine.getKey());
    }
}

