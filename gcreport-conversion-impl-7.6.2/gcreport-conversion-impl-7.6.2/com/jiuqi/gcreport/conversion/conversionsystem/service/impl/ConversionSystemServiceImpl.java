/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.rate.client.dto.RateTypeVO
 *  com.jiuqi.common.rate.client.enums.ApplyRangeEnum
 *  com.jiuqi.gcreport.common.util.DataFieldUtils
 *  com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversonSystemFormTreeVo
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskCommonVO
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskFormInfoVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService
 *  com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.conversion.conversionsystem.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.rate.client.dto.RateTypeVO;
import com.jiuqi.common.rate.client.enums.ApplyRangeEnum;
import com.jiuqi.gcreport.common.util.DataFieldUtils;
import com.jiuqi.gcreport.common.util.FormSchemePeriodGcUtils;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemItemDao;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.ConversionSystemExportExecutorImpl;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.common.ConversionSystemImportExportUtils;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.common.ConversionSystemItemExcelModel;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionSystemService;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemItemVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskSchemeVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionSystemTaskVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversonSystemFormTreeVo;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskCommonVO;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.TaskFormInfoVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataRegionService;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class ConversionSystemServiceImpl
implements ConversionSystemService {
    @Autowired
    private ConversionSystemDao dao;
    @Autowired
    private ConversionSystemItemDao itemDao;
    @Autowired
    private ConversionSystemTaskDao taskSchemeDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IFormulaRunTimeController formulaRuntimeTimeController;
    @Autowired
    private IRuntimeDataRegionService regionService;
    @Autowired
    private ConversionSystemImportExportUtils importExportUtils;

    @Override
    public List<ConversionSystemTaskSchemeVO> getSystemTaskSchemes() {
        List<ConversionSystemTaskSchemeVO> allSystemTaskScheme = this.dao.getSystemTaskSchemes();
        for (ConversionSystemTaskSchemeVO vo : allSystemTaskScheme) {
            String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey((String)vo.getSchemeId());
            vo.setStartTime(fromToPeriodByFormSchemeKey[0]);
            vo.setEndTime(fromToPeriodByFormSchemeKey[1]);
        }
        return allSystemTaskScheme;
    }

    private String getTaskTitle(String taskId) {
        try {
            TaskDefine designTaskDefine = this.runTimeViewController.queryTaskDefine(taskId);
            if (designTaskDefine != null) {
                return designTaskDefine.getTitle();
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.taskCode.notfound.error", (Object[])new Object[]{taskId}), (Throwable)e);
        }
        return null;
    }

    private String getSchemeTitle(String schemeId) {
        FormSchemeDefine schemeDefine = this.getSchemeDefine(schemeId);
        if (schemeDefine == null) {
            return null;
        }
        return schemeDefine.getTitle();
    }

    private FormSchemeDefine getSchemeDefine(String schemeId) {
        try {
            FormSchemeDefine schemeDefine = this.runTimeViewController.getFormScheme(schemeId);
            if (schemeDefine != null) {
                return schemeDefine;
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.schemeCode.notfound.error", (Object[])new Object[]{schemeId}), (Throwable)e);
        }
        return null;
    }

    private String getTaskCode(String taskId) {
        TaskDefine designTaskDefine;
        if (taskId != null && (designTaskDefine = this.runTimeViewController.queryTaskDefine(taskId)) != null) {
            return designTaskDefine.getTaskFilePrefix();
        }
        return null;
    }

    @Override
    public List<FormulaSchemeObject> getFormulaSchemeByFromScheme(String fromSchemeKey) {
        ArrayList<FormulaSchemeObject> formulaSchemes = new ArrayList<FormulaSchemeObject>();
        List allFormulaSchemeDefines = this.formulaRuntimeTimeController.getAllFormulaSchemeDefinesByFormScheme(fromSchemeKey);
        if (allFormulaSchemeDefines != null && !allFormulaSchemeDefines.isEmpty()) {
            for (FormulaSchemeDefine formulaSchemeDefine : allFormulaSchemeDefines) {
                if (FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL.equals((Object)formulaSchemeDefine.getFormulaSchemeType())) continue;
                FormulaSchemeObject formulaSchemeObj = new FormulaSchemeObject();
                formulaSchemeObj.setID(formulaSchemeDefine.getKey());
                formulaSchemeObj.setTitle(formulaSchemeDefine.getTitle());
                formulaSchemeObj.setIsDefault(formulaSchemeDefine.isDefault());
                if (formulaSchemeDefine.getFormulaSchemeType() != null) {
                    formulaSchemeObj.setFormulaSchemeType(formulaSchemeDefine.getFormulaSchemeType().getValue());
                }
                if (formulaSchemeDefine.getFormSchemeKey() != null) {
                    formulaSchemeObj.setFormSchemeKey(formulaSchemeDefine.getFormSchemeKey());
                }
                formulaSchemeObj.setIsNew(false);
                formulaSchemeObj.setIsDirty(false);
                formulaSchemeObj.setIsDeleted(false);
                formulaSchemes.add(formulaSchemeObj);
            }
        }
        return formulaSchemes;
    }

    @Override
    public List<TaskCommonVO> getTaskList() {
        List list = this.runTimeViewController.getAllReportTaskDefines();
        if (list == null || list.size() <= 0) {
            return null;
        }
        ArrayList<TaskCommonVO> result = new ArrayList<TaskCommonVO>();
        list.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getDims()) || !item.getDims().contains("MD_CURRENCY@BASE")) {
                return;
            }
            TaskCommonVO vo = new TaskCommonVO();
            vo.setCode(item.getTaskCode());
            vo.setLabel(item.getTitle());
            vo.setValue(item.getKey());
            result.add(vo);
        });
        return result;
    }

    @Override
    public List<TaskCommonVO> getSchemeList(String taskid) {
        List formSchemeList;
        try {
            formSchemeList = this.runTimeViewController.queryFormSchemeByTask(taskid);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.queryFormSchemeByTask.error"), (Throwable)e);
        }
        if (formSchemeList == null || formSchemeList.size() <= 0) {
            return null;
        }
        ArrayList<TaskCommonVO> result = new ArrayList<TaskCommonVO>();
        formSchemeList.forEach(item -> {
            String[] fromToPeriodByFormSchemeDefine = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeDefine((FormSchemeDefine)item);
            TaskCommonVO vo = new TaskCommonVO();
            vo.setLabel(item.getTitle());
            vo.setValue(item.getKey());
            vo.setStartTime(fromToPeriodByFormSchemeDefine[0]);
            vo.setEndTime(fromToPeriodByFormSchemeDefine[1]);
            result.add(vo);
        });
        return result;
    }

    @Override
    public List<TaskFormInfoVO> getFormList(String schemeID) {
        List formGroups = this.runTimeViewController.queryRootGroupsByFormScheme(schemeID);
        if (CollectionUtils.isEmpty(formGroups)) {
            return Collections.emptyList();
        }
        ArrayList<TaskFormInfoVO> resutlList = new ArrayList<TaskFormInfoVO>();
        for (FormGroupDefine formGroup : formGroups) {
            List allForms;
            TaskFormInfoVO groupVo = new TaskFormInfoVO();
            groupVo.setTitle(formGroup.getTitle());
            groupVo.setId(formGroup.getKey());
            groupVo.setCode(formGroup.getCode());
            groupVo.setDescription(formGroup.getDescription());
            try {
                allForms = this.runTimeViewController.getAllFormsInGroup(formGroup.getKey(), true);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.getAllFormsInGroup.error"), (Throwable)e);
            }
            if (CollectionUtils.isEmpty(allForms)) continue;
            ArrayList<TaskFormInfoVO> formChildren = new ArrayList<TaskFormInfoVO>();
            for (FormDefine form : allForms) {
                TaskFormInfoVO formVo = new TaskFormInfoVO();
                formVo.setId(form.getKey());
                formVo.setTitle(form.getTitle());
                formVo.setGroupId(formGroup.getKey());
                formVo.setCode(form.getFormCode());
                formVo.setDescription(form.getDescription());
                formChildren.add(formVo);
            }
            groupVo.setChildren(formChildren);
            resutlList.add(groupVo);
        }
        return resutlList;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ConversionSystemTaskVO> saveTaskScheme(List<ConversionSystemTaskVO> itemVoList) {
        this.checkVOList(itemVoList);
        List<ConversionSystemTaskEO> eoList = this.convertVO2EO(itemVoList);
        if (eoList != null) {
            eoList.forEach(item -> this.doSaveTaskScheme((ConversionSystemTaskEO)((Object)item)));
        }
        return itemVoList;
    }

    private void doSaveTaskScheme(ConversionSystemTaskEO item) {
        if (item.getId() == null) {
            item.setId(UUIDUtils.newUUIDStr());
            item.setCreatetime(new Date());
            this.taskSchemeDao.save(item);
        } else {
            item.setModifiedtime(new Date());
            this.taskSchemeDao.update((BaseEntity)item);
        }
        LogHelper.info((String)"\u5408\u5e76-\u5916\u5e01\u6298\u7b97\u4f53\u7cfb\u8bbe\u7f6e", (String)"\u4f53\u7cfb\u914d\u7f6e-\u4efb\u52a1-\u66f4\u65b0", (String)"\u4efb\u52a1ID:".concat(item.getTaskId()));
    }

    private List<ConversionSystemTaskEO> convertVO2EO(List<ConversionSystemTaskVO> itemVoList) {
        ArrayList<ConversionSystemTaskEO> eoList = null;
        if (itemVoList != null && itemVoList.size() > 0) {
            eoList = new ArrayList<ConversionSystemTaskEO>();
            for (ConversionSystemTaskVO item : itemVoList) {
                ConversionSystemTaskEO eo = new ConversionSystemTaskEO();
                BeanUtils.copyProperties(item, (Object)eo);
                if (eo.getCreatetime() == null) {
                    eo.setCreatetime(new Date());
                }
                eoList.add(eo);
            }
        }
        return eoList;
    }

    private void checkVOList(List<ConversionSystemTaskVO> itemVoList) {
        if (itemVoList == null || itemVoList.size() == 0) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.save.notnull.error"));
        }
        for (ConversionSystemTaskVO item : itemVoList) {
            if (item.getTaskId() == null) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.taskCode.notnull.error"));
            }
            if (item.getSchemeId() == null) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.schemeCode.notnull.error"));
            }
            ConversionSystemTaskEO oldEO = this.taskSchemeDao.queryByTaskAndScheme(item.getTaskId(), item.getSchemeId());
            if (item.getId() == null ? oldEO != null : oldEO != null && !oldEO.getId().equals(item.getId())) {
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.schemeCode.repeat.error"));
            }
            if (item.getTaskCode() != null) continue;
            item.setTaskCode(this.getTaskCode(item.getTaskId()));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionSystemTaskVO deleteTaskScheme(String id) {
        ConversionSystemTaskEO eo = (ConversionSystemTaskEO)this.taskSchemeDao.get((Serializable)((Object)id));
        this.taskSchemeDao.delete((BaseEntity)eo);
        if (eo != null) {
            return this.converEO2VO(eo);
        }
        return null;
    }

    private ConversionSystemTaskVO converEO2VO(ConversionSystemTaskEO eo) {
        ConversionSystemTaskVO vo = new ConversionSystemTaskVO();
        BeanUtils.copyProperties((Object)eo, vo);
        if (vo.getTaskId() != null) {
            vo.setTaskTitle(this.getTaskTitle(vo.getTaskId()));
        }
        if (vo.getSchemeId() != null) {
            vo.setSchemeTitle(this.getSchemeTitle(vo.getSchemeId()));
        }
        try {
            String[] fromToPeriodByFormSchemeKey = FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey((String)vo.getSchemeId());
            vo.setStartTime(fromToPeriodByFormSchemeKey[0]);
            vo.setEndTime(fromToPeriodByFormSchemeKey[1]);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.system.schemeCode.time.setting.error"), (Throwable)e);
        }
        return vo;
    }

    @Override
    @Transactional(readOnly=true, rollbackFor={Exception.class})
    public List<ConversionSystemTaskVO> queryTaskSchemes(String systemId) {
        List<ConversionSystemTaskEO> eoList = this.taskSchemeDao.queryBySystemId(systemId);
        return this.converEO2VO(eoList);
    }

    private List<ConversionSystemTaskVO> converEO2VO(List<ConversionSystemTaskEO> eoList) {
        ArrayList<ConversionSystemTaskVO> voList = null;
        if (eoList != null && eoList.size() > 0) {
            voList = new ArrayList<ConversionSystemTaskVO>();
            for (ConversionSystemTaskEO tempEO : eoList) {
                TaskDefine designTaskDefine = this.runTimeViewController.queryTaskDefine(tempEO.getTaskId());
                if (designTaskDefine == null) continue;
                voList.add(this.converEO2VO(tempEO));
            }
        }
        return voList;
    }

    @Override
    @Transactional(readOnly=true, rollbackFor={Exception.class})
    public ConversionSystemItemVO getSystemItemByFormIdAndIndexId(String formId, String indexId) {
        ConversionSystemItemEO conversionSystemItemEO = this.itemDao.getSystemItemByFormIdAndIndexId(formId, indexId);
        ConversionSystemItemVO vo = new ConversionSystemItemVO();
        if (conversionSystemItemEO != null) {
            BeanUtils.copyProperties((Object)conversionSystemItemEO, vo);
        } else {
            vo.setFormId(formId);
            vo.setIndexId(indexId);
        }
        return vo;
    }

    @Override
    @Transactional(readOnly=true, rollbackFor={Exception.class})
    public List<ConversionSystemItemVO> batchGetSystemItemsByFormIdAndIndexIds(String taskSchemeId, String formId, Set<String> indexIds) {
        ArrayList<ConversionSystemItemVO> returnVos = new ArrayList<ConversionSystemItemVO>();
        if (CollectionUtils.isEmpty(indexIds)) {
            return returnVos;
        }
        List<ConversionSystemItemEO> conversionSystemItemEOs = this.itemDao.batchGetSystemItemsByFormIdAndIndexIds(formId, indexIds);
        ArrayList historyIndexIds = new ArrayList();
        if (!CollectionUtils.isEmpty(conversionSystemItemEOs)) {
            historyIndexIds.addAll(conversionSystemItemEOs.stream().map(ConversionSystemItemEO::getIndexId).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        indexIds.stream().forEach(indexId -> {
            if (historyIndexIds.indexOf(indexId) == -1) {
                ConversionSystemItemVO vo = new ConversionSystemItemVO();
                vo.setFormId(formId);
                vo.setIndexId(indexId);
                returnVos.add(vo);
            }
        });
        conversionSystemItemEOs.stream().forEach(eo -> {
            ConversionSystemItemVO vo = new ConversionSystemItemVO();
            BeanUtils.copyProperties(eo, vo);
            returnVos.add(vo);
        });
        return returnVos;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConversionSystemItemVO saveConversionSystemItemIndexInfo(ConversionSystemItemVO vo) {
        DataRegionDefine regionDefine;
        if (vo == null) {
            return null;
        }
        boolean isSimpleRegion = false;
        if (!StringUtils.isEmpty((String)vo.getRegionKey()) && (regionDefine = this.regionService.queryDataRegion(vo.getRegionKey())) != null) {
            isSimpleRegion = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)regionDefine.getRegionKind());
        }
        String userName = NpContextHolder.getContext().getUserName();
        Objects.requireNonNull(vo.getFormId(), GcI18nUtil.getMessage((String)"gc.coversion.system.formCode.notnull.error"));
        Objects.requireNonNull(vo.getIndexId(), GcI18nUtil.getMessage((String)"gc.coversion.system.indexId.notnull.error"));
        if (isSimpleRegion) {
            this.itemDao.deleteBySchemeTaskIdAndIndexId(vo.getFormId(), vo.getIndexId());
        } else if (!ObjectUtils.isEmpty(vo.getId())) {
            this.itemDao.deleteById(vo.getId());
        }
        if (StringUtils.isEmpty((String)vo.getRateTypeCode())) {
            return vo;
        }
        ConversionSystemItemEO conversionSystemItemEO = new ConversionSystemItemEO();
        conversionSystemItemEO.setId(UUIDUtils.newUUIDStr());
        conversionSystemItemEO.setFormId(vo.getFormId());
        conversionSystemItemEO.setRegionId(vo.getRegionKey());
        conversionSystemItemEO.setIndexId(vo.getIndexId());
        conversionSystemItemEO.setCreatetime(new Date());
        conversionSystemItemEO.setCreateuser(userName);
        conversionSystemItemEO.setRateTypeCode(vo.getRateTypeCode());
        conversionSystemItemEO.setRateFormula(vo.getRateFormula());
        this.itemDao.add((BaseEntity)conversionSystemItemEO);
        BeanUtils.copyProperties((Object)conversionSystemItemEO, vo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ConversionSystemItemVO> batchSaveConversionSystemItemIndexInfo(List<ConversionSystemItemVO> vos) {
        if (CollectionUtils.isEmpty(vos)) {
            return null;
        }
        vos.stream().filter(Objects::nonNull).forEach(vo -> this.saveConversionSystemItemIndexInfo((ConversionSystemItemVO)vo));
        return vos;
    }

    @Override
    public List<ConversonSystemFormTreeVo> queryFormTree(String formSchemeKey) {
        List formGroupDefines = this.runTimeViewController.queryRootGroupsByFormScheme(formSchemeKey);
        List<ConversonSystemFormTreeVo> formTreeVos = formGroupDefines.stream().map(formGroupDefine -> {
            ConversonSystemFormTreeVo formTreeVo = this.buildFormTreeVo((FormGroupDefine)formGroupDefine);
            return formTreeVo;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        if (formTreeVos == null) {
            return Collections.emptyList();
        }
        return formTreeVos;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ConversionSystemItemExcelModel> exportExcel(ConversionSystemExportExecutorImpl.ConversionSystemExportParam exportParam) {
        List<ConversionSystemItemExcelModel> excelModels = this.importExportUtils.exportDatas(exportParam);
        return excelModels;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Object importExcel(List<ConversionSystemItemExcelModel> excelModels) {
        String result = this.importExportUtils.importDatas(excelModels);
        return result;
    }

    private ConversonSystemFormTreeVo buildFormTreeVo(FormGroupDefine formGroupDefine) {
        List formDefines;
        try {
            formDefines = this.runTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        if (formDefines.size() == 0) {
            return null;
        }
        ConversonSystemFormTreeVo formTreeVo = this.convertToFormTreeVo(formGroupDefine);
        List formDefineVos = formDefines.stream().map(define -> this.convertToFormTreeVo((FormDefine)define)).collect(Collectors.toList());
        formTreeVo.setChildren(formDefineVos);
        return formTreeVo;
    }

    @Override
    public String queryFormData(String formKey) {
        try {
            BigDataDefine dataDefine = this.runTimeViewController.getReportDataFromForm(formKey);
            Grid2Data gridData = null;
            if (dataDefine != null) {
                if (dataDefine.getData() != null) {
                    gridData = Grid2Data.bytesToGrid((byte[])dataDefine.getData());
                } else {
                    gridData = new Grid2Data();
                    gridData.setRowCount(10);
                    gridData.setColumnCount(10);
                }
            }
            if (gridData == null) {
                gridData = new Grid2Data();
                gridData.insertRows(0, 1, -1);
                gridData.insertColumns(0, 1);
                gridData.setRowHidden(0, true);
                gridData.setColumnHidden(0, true);
            }
            this.fillZbCellData(formKey, gridData);
            String result = this.serialize(gridData);
            return result;
        }
        catch (Exception ex) {
            throw new BusinessRuntimeException((Throwable)ex);
        }
    }

    private String serialize(Grid2Data griddata) throws JsonProcessingException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)griddata);
    }

    private void fillZbCellData(String formKey, Grid2Data gridData) throws Exception {
        List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
        List rateTypeInfosCache = CommonRateUtils.getShowRateType((ApplyRangeEnum)ApplyRangeEnum.REPORT);
        for (DataRegionDefine region : regions) {
            Set tableIds;
            List tableModelDefines;
            String regionKey = region.getKey();
            List dataLinks = this.runTimeViewController.getAllLinksInRegion(regionKey);
            Set<String> fieldKeys = dataLinks.stream().filter(e -> e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD)).map(DataLinkDefine::getLinkExpression).filter(Objects::nonNull).collect(Collectors.toSet());
            Map linkId2FeildDefineMap = new HashMap();
            Map<Object, Object> tableId2NameMap = new HashMap();
            if (!CollectionUtils.isEmpty(fieldKeys) && !CollectionUtils.isEmpty(tableModelDefines = this.dataModelService.getTableModelDefinesByIds(tableIds = (linkId2FeildDefineMap = DataFieldUtils.getNrFieldKey2NvwaColumnDefineMapByNrFieldKey(fieldKeys)).values().stream().map(ColumnModelDefine::getTableID).collect(Collectors.toSet())))) {
                tableId2NameMap = tableModelDefines.stream().collect(Collectors.toMap(IModelDefineItem::getID, TableModelDefine::getName, (key1, key2) -> key2));
            }
            List<ConversionSystemItemEO> itemEOs = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)region.getRegionKind()) ? this.itemDao.getSystemItemsByIndexIds(fieldKeys) : this.itemDao.batchGetSystemItemsByFormIdAndIndexIds(formKey, fieldKeys);
            Map<Object, Object> indexId2SystemItemMap = new HashMap();
            if (!CollectionUtils.isEmpty(itemEOs)) {
                indexId2SystemItemMap = itemEOs.stream().filter(itemEO -> Objects.nonNull(itemEO.getIndexId())).collect(Collectors.toMap(ConversionSystemItemEO::getIndexId, Function.identity(), (value1, value2) -> value2));
            }
            for (DataLinkDefine link : dataLinks) {
                String rateFormula;
                ColumnModelDefine fieldDefine;
                String nrFeildkey;
                GridCellData cellData = gridData.getGridCellData(link.getPosX(), link.getPosY());
                if (cellData == null || StringUtils.isEmpty((String)(nrFeildkey = link.getLinkExpression())) || (fieldDefine = (ColumnModelDefine)linkId2FeildDefineMap.get(nrFeildkey)) == null) continue;
                String tableName = ConverterUtils.getAsString((Object)tableId2NameMap.get(fieldDefine.getTableID()), (String)"");
                HashMap<String, Object> editTextValueMap = new HashMap<String, Object>();
                editTextValueMap.put("indexId", nrFeildkey);
                editTextValueMap.put("indexTitle", fieldDefine.getTitle());
                editTextValueMap.put("indexCode", fieldDefine.getCode());
                editTextValueMap.put("indexTable", tableName);
                editTextValueMap.put("regionKey", region.getKey());
                editTextValueMap.put("formKey", region.getFormKey());
                boolean isAllowSegmentRate = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)region.getRegionKind()) ? true : (tableName.contains("GC_INPUTDATA") ? true : !region.getAllowDuplicateKey());
                editTextValueMap.put("isAllowSegmentRate", isAllowSegmentRate);
                ColumnModelType fieldType = fieldDefine.getColumnType();
                boolean isNumberField = fieldType == ColumnModelType.BIGDECIMAL || fieldType == ColumnModelType.DOUBLE || fieldType == ColumnModelType.INTEGER;
                editTextValueMap.put("isNumber", isNumberField);
                String editTextValue = JsonUtils.writeValueAsString(editTextValueMap);
                cellData.setEditText(editTextValue);
                ConversionSystemItemEO conversionSystemItemEO = (ConversionSystemItemEO)((Object)indexId2SystemItemMap.get(nrFeildkey));
                if (conversionSystemItemEO == null) continue;
                String showText = "";
                for (RateTypeVO typeVO : rateTypeInfosCache) {
                    if (!conversionSystemItemEO.getRateTypeCode().equals(typeVO.getCode())) continue;
                    showText = typeVO.getName();
                    break;
                }
                if (!StringUtils.isEmpty((String)(rateFormula = conversionSystemItemEO.getRateFormula()))) {
                    showText = showText + "[" + rateFormula + "]";
                }
                cellData.setShowText(showText);
                cellData.setHorzAlign(3);
                cellData.setEditable(true);
                cellData.setForeGroundColor(255);
            }
        }
    }

    private ConversonSystemFormTreeVo convertToFormTreeVo(FormGroupDefine formGroupDefine) {
        ConversonSystemFormTreeVo formTreeVo = new ConversonSystemFormTreeVo();
        formTreeVo.setCode(formGroupDefine.getCode());
        formTreeVo.setId(formGroupDefine.getKey());
        formTreeVo.setTitle(formGroupDefine.getTitle());
        return formTreeVo;
    }

    private ConversonSystemFormTreeVo convertToFormTreeVo(FormDefine formDefine) {
        ConversonSystemFormTreeVo formTreeVo = new ConversonSystemFormTreeVo();
        formTreeVo.setCode(formDefine.getFormCode());
        formTreeVo.setId(formDefine.getKey());
        formTreeVo.setTitle(formDefine.getTitle());
        formTreeVo.setSerialNumber(formDefine.getSerialNumber());
        return formTreeVo;
    }
}

