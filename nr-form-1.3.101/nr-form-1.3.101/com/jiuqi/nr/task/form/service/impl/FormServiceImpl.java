/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.DesignFormulaDTO
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.FormulaConditionDTO
 *  com.jiuqi.nr.definition.common.RegionEnterNext
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.FormulaDesignTimeController
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkMappingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityAssist
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.filterTemplate.service.IFilterTemplateService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 *  com.jiuqi.nr.task.api.save.FormSaveContext
 *  com.jiuqi.nr.task.api.save.IFormSaveSettingProvider
 *  com.jiuqi.nr.task.api.tree.TreeConfig
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeBuilder
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.form.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.DesignFormulaDTO;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormulaConditionDTO;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.controller.FormulaDesignTimeController;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.dao.DesignBigDataTableDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkMappingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityAssist;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.save.FormSaveContext;
import com.jiuqi.nr.task.api.save.IFormSaveSettingProvider;
import com.jiuqi.nr.task.api.tree.TreeConfig;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeBuilder;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.form.controller.dto.CheckFieldCodeBatchDTO;
import com.jiuqi.nr.task.form.controller.dto.DeleteFormParam;
import com.jiuqi.nr.task.form.controller.dto.FormTreeNode;
import com.jiuqi.nr.task.form.controller.vo.CheckFieldCodeBatchVO;
import com.jiuqi.nr.task.form.controller.vo.CopyFormVO;
import com.jiuqi.nr.task.form.controller.vo.EnumFieldVO;
import com.jiuqi.nr.task.form.controller.vo.FindFieldVO;
import com.jiuqi.nr.task.form.controller.vo.FormTreeParam;
import com.jiuqi.nr.task.form.controller.vo.FormUITreeNode;
import com.jiuqi.nr.task.form.controller.vo.FormulaUpdateRecordVO;
import com.jiuqi.nr.task.form.dto.AbstractState;
import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.ConditionStyleDTO;
import com.jiuqi.nr.task.form.dto.DataCore;
import com.jiuqi.nr.task.form.dto.DataSchemeDefineDTO;
import com.jiuqi.nr.task.form.dto.DeleteFormDTO;
import com.jiuqi.nr.task.form.dto.EntityFieldDTO;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;
import com.jiuqi.nr.task.form.dto.FormExtDTO;
import com.jiuqi.nr.task.form.dto.SaveResult;
import com.jiuqi.nr.task.form.dto.SimpleFormDesignerDTO;
import com.jiuqi.nr.task.form.exception.FormRuntimeException;
import com.jiuqi.nr.task.form.ext.FormDefineResourceExtSupport;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.ConfigExt;
import com.jiuqi.nr.task.form.ext.face.IExtendInfo;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.service.ILinkConfigExtService;
import com.jiuqi.nr.task.form.ext.service.IRegionConfigExtService;
import com.jiuqi.nr.task.form.field.dto.DataFieldDTO;
import com.jiuqi.nr.task.form.field.dto.DataFieldSettingDTO;
import com.jiuqi.nr.task.form.field.service.IDataFieldService;
import com.jiuqi.nr.task.form.form.dto.FormDTO;
import com.jiuqi.nr.task.form.form.dto.FormItemDTO;
import com.jiuqi.nr.task.form.form.service.IFormDefineService;
import com.jiuqi.nr.task.form.form.service.IFormGroupService;
import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;
import com.jiuqi.nr.task.form.formstyle.service.IFormStyleService;
import com.jiuqi.nr.task.form.link.dto.DataLinkDTO;
import com.jiuqi.nr.task.form.link.dto.DataLinkSettingDTO;
import com.jiuqi.nr.task.form.link.dto.FilterTemplateDTO;
import com.jiuqi.nr.task.form.link.dto.LinkMappingDTO;
import com.jiuqi.nr.task.form.link.dto.RefEntityLinkConfigDTO;
import com.jiuqi.nr.task.form.link.service.IDataLinkService;
import com.jiuqi.nr.task.form.link.service.ILinkMappingService;
import com.jiuqi.nr.task.form.region.dto.DataRegionSettingDTO;
import com.jiuqi.nr.task.form.region.serivce.IRegionService;
import com.jiuqi.nr.task.form.service.IConditionStyleService;
import com.jiuqi.nr.task.form.service.IFormCheckService;
import com.jiuqi.nr.task.form.service.IFormService;
import com.jiuqi.nr.task.form.service.check.FormStatusCheckService;
import com.jiuqi.nr.task.form.table.IDataTableService;
import com.jiuqi.nr.task.form.table.dto.DataTableDTO;
import com.jiuqi.nr.task.form.util.EntityFieldBeanUtils;
import com.jiuqi.nr.task.form.util.FieldBeanUtils;
import com.jiuqi.nr.task.form.util.SaveFormWithFormulaHelper;
import com.jiuqi.nr.task.form.util.TableBeanUtils;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormServiceImpl
extends FormDefineResourceExtSupport
implements IFormService {
    private static final Logger logger = LoggerFactory.getLogger(FormServiceImpl.class);
    @Autowired
    private IFormGroupService formGroupService;
    @Autowired
    private IFormDefineService formDefineService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IDataLinkService dataLinkService;
    @Autowired
    private IRegionConfigExtService regionConfigExtService;
    @Autowired
    private ILinkConfigExtService linkConfigExtService;
    @Autowired
    private IDataFieldService dataFieldService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    @Qualifier(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private List<IFormCheckService> formCheckServices;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityAssist entityAssist;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private FormStatusCheckService formStatusCheckService;
    @Autowired
    private IFormStyleService formStyleService;
    @Autowired
    private IDataTableService dataTableService;
    @Autowired
    private IConditionStyleService conditionStyleService;
    @Autowired
    private ILinkMappingService linkMappingService;
    @Autowired
    private DesignBigDataTableDao bigDataTableDao;
    @Autowired
    private IFormulaDesignTimeController iFormulaDesignTimeController;
    @Autowired
    private IPrintDesignTimeController iPrintDesignTimeController;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private FormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ProgressCacheService progressCacheService;
    @Autowired
    private IFilterTemplateService filterTemplateService;
    @Autowired
    @Qualifier(value="saveFormWithFormulaHelper2")
    private SaveFormWithFormulaHelper saveFormWithFormulaHelper;
    @Autowired(required=false)
    private IFormSaveSettingProvider formSaveSettingProvider;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private BaseDataClient baseDataClient;
    private NedisCacheManager cacheManager;
    ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<FormType, String> FORM_TYPE_ICON_MAP = new HashMap<FormType, String>();

    @Autowired
    private void initCache(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager("NR:FORM");
    }

    private NedisCache getDeleteFormCache() {
        return this.cacheManager.getCache("NR:FORM:DEL_FORM");
    }

    public FormServiceImpl(List<IFormDefineResourceExt> formDefineResourceExts) {
        super(formDefineResourceExts);
    }

    @Override
    public List<FormExtDTO> queryFormExt(String formSchemeKey) {
        Map<String, IFormDefineResourceExt> formResourceExt = this.getFormDefineResourceExtCache();
        if (CollectionUtils.isEmpty(formResourceExt)) {
            return Collections.emptyList();
        }
        ArrayList<FormExtDTO> formExtList = new ArrayList<FormExtDTO>();
        formResourceExt.forEach((key, resourceExt) -> {
            FormExtDTO formExtDTO = new FormExtDTO();
            formExtDTO.setCode(resourceExt.getCode());
            formExtDTO.setTitle(resourceExt.getTitle());
            formExtDTO.setProductLine(resourceExt.prodLine());
            formExtDTO.setFormType(resourceExt.getFormType());
            IExtendInfo iExtendInfo = resourceExt.extendConfig(formSchemeKey);
            if (iExtendInfo != null) {
                String info = null;
                try {
                    info = this.objectMapper.writeValueAsString((Object)iExtendInfo);
                }
                catch (JsonProcessingException e) {
                    logger.error(e.getMessage(), e);
                }
                formExtDTO.setExtendInfo(info);
            }
            formExtList.add(formExtDTO);
        });
        return formExtList;
    }

    @Override
    public Map<String, String> queryTaskInfo(String formSchemeKey) {
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        HashMap<String, String> result = new HashMap<String, String>(2);
        result.put("taskId", task.getKey());
        result.put("dataSchemeId", task.getDataScheme());
        return result;
    }

    @Override
    public String insertDefaultGroup(String formSchemeKey) {
        return this.formGroupService.insertDefaultGroup(formSchemeKey);
    }

    @Override
    public void deleteGroup(String groupKey) {
        this.formGroupService.deleteGroup(groupKey);
    }

    @Override
    public List<FormItemDTO> queryFormByGroup(String group) {
        return this.formDefineService.queryFormByGroup(group);
    }

    @Override
    @Transactional
    public String insertForm(FormDTO formDTO) {
        this.formDefineService.insertForm(formDTO);
        this.insertDefaultGroupLink(formDTO);
        this.insertDefaultFormStyle(formDTO.getKey(), formDTO.getFormType());
        this.insertDefaultRegion(this.initRegion(formDTO));
        return formDTO.getKey();
    }

    private DataRegionSettingDTO initRegion(FormDTO formDTO) {
        DataRegionSettingDTO regionSettingDTO = new DataRegionSettingDTO();
        regionSettingDTO.setFormKey(formDTO.getKey());
        regionSettingDTO.setTitle(formDTO.getTitle());
        regionSettingDTO.setRegionLeft(1);
        regionSettingDTO.setRegionTop(1);
        if (formDTO.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
            regionSettingDTO.setRegionRight(2);
            regionSettingDTO.setRegionBottom(20);
        } else {
            regionSettingDTO.setRegionRight(13);
            regionSettingDTO.setRegionBottom(18);
        }
        regionSettingDTO.setRowsInFloatRegion(1);
        regionSettingDTO.setRegionEnterNext(RegionEnterNext.BOTTOM);
        regionSettingDTO.setRegionKind(DataRegionKind.DATA_REGION_SIMPLE.getValue());
        return regionSettingDTO;
    }

    private void insertDefaultRegion(DataRegionSettingDTO regionSettingDTO) {
        this.regionService.insertDefaultRegion(regionSettingDTO);
    }

    private void insertDefaultFormStyle(String formKey, FormType formType) {
        if (formType == FormType.FORM_TYPE_NEWFMDM) {
            this.formStyleService.insertDefaultFMDMFormStyle(formKey);
        } else {
            this.formStyleService.insertDefaultFormStyle(formKey);
        }
    }

    private void insertDefaultGroupLink(FormDTO formDTO) {
        this.formGroupService.insertGroupLink(formDTO);
    }

    @Override
    @Transactional
    public void insertDefaultForm(String formSchemeKey, String formGroup) {
        FormDTO formDTO = this.initForm(formSchemeKey, formGroup);
        this.insertForm(formDTO);
    }

    private FormDTO initForm(String formSchemeKey, String formGroup) {
        FormDTO formDTO = new FormDTO();
        formDTO.setFormScheme(formSchemeKey);
        formDTO.setFormGroupKey(formGroup);
        formDTO.setTitle("\u5de5\u4f5c\u88681");
        formDTO.setFormCode(com.jiuqi.util.OrderGenerator.newOrder());
        formDTO.setIsgather(true);
        formDTO.setMeasureUnit(null);
        formDTO.setFormType(FormType.FORM_TYPE_FIX);
        return formDTO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public DeleteFormDTO deleteForm(DeleteFormParam deleteFormParam) {
        if (deleteFormParam.getKey() == null) {
            return null;
        }
        if (deleteFormParam.getDeleteId() == null) {
            this.deleteForm(deleteFormParam.getKey());
            return null;
        }
        NedisCache deleteFormCache = this.getDeleteFormCache();
        CheckResult checkResult = this.formStatusCheckService.doCheck(deleteFormParam.getKey());
        if (checkResult.isSuccess()) {
            List<DesignDataField> deleteDataFields = this.getDeleteFields(deleteFormParam);
            Set<String> deleteFieldKeys1 = deleteDataFields.stream().map(Basic::getKey).collect(Collectors.toSet());
            if (deleteFieldKeys1.isEmpty()) {
                DeleteFormDTO deleteFormDTO = new DeleteFormDTO(deleteFormParam.getKey(), deleteFormParam.getDeleteId());
                deleteFormCache.put(deleteFormParam.getDeleteId(), (Object)deleteFormDTO);
                return deleteFormDTO;
            }
            List<String> tables = deleteDataFields.stream().map(DataField::getDataTableKey).distinct().collect(Collectors.toList());
            List<String> refEntityKeys = this.getDeleteRefEntityKey(deleteDataFields, deleteFieldKeys1);
            DeleteFormDTO deleteFormDTO = new DeleteFormDTO(deleteFormParam.getKey(), deleteFormParam.getDeleteId());
            deleteFormDTO.setTableKeys(tables);
            deleteFormDTO.setKey(deleteFormParam.getKey());
            deleteFormDTO.setFieldKeys(new ArrayList<String>(deleteFieldKeys1));
            deleteFormCache.put(deleteFormParam.getDeleteId(), (Object)deleteFormDTO);
            ArrayList<DeleteFormDTO.DeleteBaseDataDefine> baseDatas = new ArrayList<DeleteFormDTO.DeleteBaseDataDefine>(refEntityKeys.size());
            deleteFormDTO.setBaseDatas(baseDatas);
            for (int i = 0; i < refEntityKeys.size(); ++i) {
                String refEntityKey = refEntityKeys.get(i);
                IEntityDefine queryEntity = this.entityMetaService.queryEntity(refEntityKey);
                baseDatas.add(new DeleteFormDTO.DeleteBaseDataDefine(i + 1, queryEntity.getCode(), queryEntity.getTitle()));
            }
            return deleteFormDTO;
        }
        throw new FormRuntimeException(checkResult.toString());
    }

    private void deleteForm(String key) {
        try {
            this.formDefineService.deleteForm(key);
            logger.debug("\u5220\u9664\u8868\u5355\u3010{}\u3011", (Object)key);
        }
        catch (Exception e) {
            throw new FormRuntimeException("\u5220\u9664\u8868\u5355\u5931\u8d25\uff01", e);
        }
    }

    private void deleteFormAndFieldFromCache(NedisCache deleteFormCache, DeleteFormParam deleteFormParam) {
        try {
            Cache.ValueWrapper valueWrapper = deleteFormCache.get(deleteFormParam.getDeleteId());
            if (valueWrapper != null) {
                DeleteFormDTO deleteFormDTO = (DeleteFormDTO)valueWrapper.get();
                if (deleteFormDTO != null) {
                    if (deleteFormParam.isDeleteRefEntity().booleanValue()) {
                        this.deleteFormAndFields(deleteFormParam.getKey(), deleteFormDTO.getFieldKeys(), deleteFormDTO.getTableKeys());
                    } else {
                        this.deleteForm(deleteFormParam.getKey());
                    }
                }
                deleteFormCache.evict(deleteFormParam.getDeleteId());
            }
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u8868\u5355\u5931\u8d25\uff01", e);
            deleteFormCache.evict(deleteFormParam.getKey());
            throw e;
        }
    }

    private List<String> getDeleteRefEntityKey(List<DesignDataField> deleteDataFields, Set<String> deleteFieldKeys1) {
        Set deleteRefEntityKeys = deleteDataFields.stream().filter(field -> field.getDataFieldType() == DataFieldType.STRING && field.getRefDataEntityKey() != null).map(DataField::getRefDataEntityKey).collect(Collectors.toSet());
        List<DataFieldDTO> refEntityFields = this.dataFieldService.listFieldsByRefEntityKeys(new ArrayList<String>(deleteRefEntityKeys));
        Map refEntityFieldsMap = refEntityFields.stream().collect(Collectors.groupingBy(DataFieldDTO::getRefDataEntityKey, Collectors.mapping(DataCore::getKey, Collectors.toSet())));
        return refEntityFieldsMap.keySet().stream().filter(key -> deleteFieldKeys1.containsAll((Collection)refEntityFieldsMap.get(key))).filter(BaseDataAdapterUtil::isBaseData).collect(Collectors.toList());
    }

    private List<DesignDataField> getDeleteFields(DeleteFormParam deleteFormParam) {
        List<DataLinkDTO> links = this.dataLinkService.listDataLink(deleteFormParam.getKey());
        Set deleteLinks = links.stream().map(DataCore::getKey).collect(Collectors.toSet());
        Set deleteFieldKeys = links.stream().filter(link -> link.getType().intValue() == DataLinkType.DATA_LINK_TYPE_FIELD.getValue()).map(DataLinkDTO::getLinkExpression).collect(Collectors.toSet());
        List deleteDataFields = this.designDataSchemeService.getDataFields(new ArrayList(deleteFieldKeys));
        List<DataLinkDTO> refFieldLinks = this.dataLinkService.listLinksByFieldKeys(new ArrayList<String>(deleteFieldKeys));
        Set refFieldKeys = refFieldLinks.stream().filter(link -> !deleteLinks.contains(link.getKey())).filter(link -> link.getType().intValue() == DataLinkType.DATA_LINK_TYPE_FIELD.getValue()).map(DataLinkDTO::getLinkExpression).collect(Collectors.toSet());
        List runtimeDataFields = this.runtimeDataSchemeService.getDataFields(new ArrayList(deleteFieldKeys));
        Set runDataFieldKeys = runtimeDataFields.stream().map(Basic::getKey).collect(Collectors.toSet());
        return deleteDataFields.stream().filter(dataField -> !runDataFieldKeys.contains(dataField.getKey()) && !refFieldKeys.contains(dataField.getKey())).collect(Collectors.toList());
    }

    private void deleteFormAndFields(String key, Collection<String> deleteFieldKeys1, List<String> tables) {
        try {
            this.formDefineService.deleteForm(key);
            logger.info("\u5220\u9664\u8868\u5355\u3010{}\u3011", (Object)key);
            if (!CollectionUtils.isEmpty(deleteFieldKeys1)) {
                this.designDataSchemeService.deleteDataFields(new ArrayList<String>(deleteFieldKeys1));
                logger.info("\u5220\u9664\u6307\u6807\u6570\u91cf\uff1a{}", (Object)deleteFieldKeys1.size());
            }
            if (!CollectionUtils.isEmpty(tables)) {
                tables = tables.stream().filter(table -> this.designDataSchemeService.getDataFieldByTableKeyAndKind(table, new DataFieldKind[]{DataFieldKind.FIELD, DataFieldKind.FIELD_ZB, DataFieldKind.TABLE_FIELD_DIM}).isEmpty()).collect(Collectors.toList());
                this.designDataSchemeService.deleteDataTables(tables);
                logger.info("\u5220\u9664\u8868\u6570\u91cf\uff1a{}", (Object)tables.size());
            }
        }
        catch (Exception e) {
            throw new FormRuntimeException("\u5220\u9664\u8868\u5355\u5931\u8d25", e);
        }
    }

    private void deleteBaseData(List<String> deleteRefEntityKeys1) {
        for (String entity : deleteRefEntityKeys1) {
            BaseDataBatchOptDTO baseDataBatchOptDTO = new BaseDataBatchOptDTO();
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setTableName(entity);
            PageVO deleteBaseData = this.baseDataClient.list(baseDataDTO);
            baseDataBatchOptDTO.setQueryParam(baseDataDTO);
            baseDataBatchOptDTO.setDataList(deleteBaseData.getRows());
            HashMap<String, Boolean> extInfo = new HashMap<String, Boolean>();
            extInfo.put("forceDelete", true);
            baseDataBatchOptDTO.setExtInfo(extInfo);
            R r = this.baseDataClient.batchRemove(baseDataBatchOptDTO);
            logger.info("\u5220\u9664\u57fa\u7840\u6570\u636e\u3010{}\u3011\u7ed3\u679c\uff1a{}", (Object)entity, (Object)r);
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setName(entity);
            r = this.baseDataDefineClient.remove(baseDataDefineDTO);
            logger.info("\u5220\u9664\u57fa\u7840\u6570\u636e\u5b9a\u4e49\u3010{}\u3011\u7ed3\u679c\uff1a{}", (Object)entity, (Object)r);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteFormAndField(DeleteFormParam deleteFormParam) {
        NedisCache deleteFormCache = this.getDeleteFormCache();
        if (deleteFormCache.exists(deleteFormParam.getDeleteId())) {
            this.deleteFormAndFieldFromCache(deleteFormCache, deleteFormParam);
            deleteFormCache.evict(deleteFormParam.getDeleteId());
        }
    }

    @Override
    public void deleteFormBaseData(DeleteFormParam deleteFormParam) {
        if (!CollectionUtils.isEmpty(deleteFormParam.getBaseDataNames())) {
            this.deleteBaseData(deleteFormParam.getBaseDataNames());
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public SaveResult saveFormData(FormDesignerDTO formDesignerDTO) {
        String formKey = formDesignerDTO.getForm().getKey();
        ProgressItem progressItem = this.initProgress(formDesignerDTO);
        CheckResult checkResult = this.checkFormData(formDesignerDTO, progressItem);
        SaveResult result = new SaveResult(checkResult);
        if (result.isError()) {
            return result;
        }
        Map<String, List<DesignFormulaDTO>> formulaSchemeAndFormulaMap = this.checkFormula(formKey, formDesignerDTO, progressItem);
        this.formStyleService.saveFormStyle(formKey, formDesignerDTO.getFormStyle());
        this.saveRegionSetting(formKey, formDesignerDTO.getRegionSetting());
        this.saveTableSetting(formDesignerDTO.getTableSetting());
        this.saveLinkSetting(formKey, formDesignerDTO.getDataLinkSetting());
        this.saveFilterTemplate(formDesignerDTO);
        this.saveFieldSetting(formKey, formDesignerDTO.getDataFieldSetting());
        this.saveComponentConfigs(formKey, formDesignerDTO.getComponentConfigs());
        this.saveConditionStyle(formKey, formDesignerDTO.getConditionStyle());
        this.saveLinkMapping(formKey, formDesignerDTO.getDataLinkMapping());
        this.designTimeViewController.updateFormTime(formKey);
        this.checkFormType(formKey);
        List<FormulaUpdateRecordVO> updateRecords = this.updateFormula(formulaSchemeAndFormulaMap, formDesignerDTO.getForm().getFormScheme(), progressItem);
        result.setUpdateRecords(updateRecords);
        if (formDesignerDTO.isUpdatePrintTemplate()) {
            this.updatePrintTemplate(formKey, formDesignerDTO);
        }
        return result;
    }

    private void updatePrintTemplate(String formKey, FormDesignerDTO formDesignerDTO) {
        Boolean formStyleChanged;
        FormSaveContext formSaveContext;
        if (this.formSaveSettingProvider != null && this.formSaveSettingProvider.support(formSaveContext = new FormSaveContext(formKey, formStyleChanged = formDesignerDTO.getFormStyle().isFormStyleChanged())).booleanValue()) {
            try {
                this.formSaveSettingProvider.executeSave(formSaveContext);
            }
            catch (Exception e) {
                throw new FormRuntimeException("\u6253\u5370\u6a21\u7248\u66f4\u65b0\u5931\u8d25\uff01", e);
            }
        }
    }

    private void saveFilterTemplate(FormDesignerDTO formDesignerDTO) {
        List<FilterTemplateDTO> modify;
        List<FilterTemplateDTO> inserts;
        List<FilterTemplateDTO> templates = formDesignerDTO.getFilterTemplates();
        Map<Constants.DataStatus, List<FilterTemplateDTO>> filterTemplateMap = templates.stream().collect(Collectors.groupingBy(AbstractState::getStatus));
        List<FilterTemplateDTO> deletes = filterTemplateMap.get(Constants.DataStatus.DELETE);
        if (!CollectionUtils.isEmpty(deletes)) {
            this.filterTemplateService.batchDelete(deletes.stream().map(FilterTemplateDTO::getFilterTemplateID).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(inserts = filterTemplateMap.get(Constants.DataStatus.NEW))) {
            this.filterTemplateService.batchInsert(inserts.stream().map(FilterTemplateDTO::cto).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(modify = filterTemplateMap.get(Constants.DataStatus.MODIFY))) {
            this.filterTemplateService.batchUpdate(modify.stream().map(FilterTemplateDTO::cto).filter(Objects::nonNull).collect(Collectors.toList()));
        }
    }

    private CheckResult checkFormData(FormDesignerDTO formDesignerDTO, ProgressItem progressItem) {
        if (progressItem == null) {
            return this.checkFormData(formDesignerDTO);
        }
        progressItem.setCurrentProgess(0);
        progressItem.setMessage("\u6b63\u5728\u6821\u9a8c\u62a5\u8868\u53c2\u6570......");
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        CheckResult checkResult = this.checkFormData(formDesignerDTO);
        if (checkResult.isError()) {
            progressItem.setCurrentProgess(50);
            progressItem.setMessage("\u6821\u9a8c\u62a5\u8868\u53c2\u6570\u5931\u8d25");
            progressItem.setFailed(true);
            this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        }
        return checkResult;
    }

    private ProgressItem initProgress(FormDesignerDTO formDesignerDTO) {
        if (formDesignerDTO.isUpdateFormula()) {
            ProgressItem progressItem = new ProgressItem();
            progressItem.setProgressId(formDesignerDTO.getProgressId());
            progressItem.addStepTitle("\u5f00\u59cb");
            progressItem.addStepTitle("\u89e3\u6790\u516c\u5f0f");
            progressItem.addStepTitle("\u4fdd\u5b58\u62a5\u8868");
            progressItem.addStepTitle("\u66f4\u65b0\u516c\u5f0f");
            progressItem.addStepTitle("\u7ed3\u675f");
            return progressItem;
        }
        return null;
    }

    private Map<String, List<DesignFormulaDTO>> checkFormula(String formKey, FormDesignerDTO formDesignerDTO, ProgressItem progressItem) {
        Map<String, List<DesignFormulaDTO>> formulaSchemeAndFormulaMap = new HashMap<String, List<DesignFormulaDTO>>();
        if (formDesignerDTO.isUpdateFormula() && progressItem != null) {
            progressItem.setCurrentProgess(100);
            progressItem.setMessage("\u6821\u9a8c\u62a5\u8868\u53c2\u6570\u5b8c\u6210");
            this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
            try {
                formulaSchemeAndFormulaMap = this.saveFormWithFormulaHelper.saveFormWithFormula(formKey, formDesignerDTO, progressItem);
            }
            catch (Exception e) {
                throw new FormRuntimeException(e);
            }
        }
        return formulaSchemeAndFormulaMap;
    }

    private List<FormulaUpdateRecordVO> updateFormula(Map<String, List<DesignFormulaDTO>> formulaSchemeAndFormulaMap, String formScheme, ProgressItem progressItem) {
        if (CollectionUtils.isEmpty(formulaSchemeAndFormulaMap) || progressItem == null) {
            return Collections.emptyList();
        }
        progressItem.setCurrentProgess(100);
        progressItem.setMessage("\u62a5\u8868\u4fdd\u5b58\u5b8c\u6210");
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        progressItem.nextStep();
        progressItem.setCurrentProgess(33);
        progressItem.setMessage("\u6b63\u5728\u89e3\u6790\u516c\u5f0f\uff0c\u5c06\u94fe\u63a5\u683c\u5f0f\u8f6c\u6362\u6210\u5750\u6807\u683c\u5f0f......");
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        try {
            for (Map.Entry<String, List<DesignFormulaDTO>> entry : formulaSchemeAndFormulaMap.entrySet()) {
                this.formulaDesignTimeController.fillExpression(entry.getKey(), entry.getValue());
            }
        }
        catch (Exception e) {
            progressItem.setCurrentProgess(100);
            progressItem.setMessage("\u4fdd\u5b58\u516c\u5f0f\u5931\u8d25\uff0c\u6279\u91cf\u586b\u5145\u516c\u5f0f\u5185\u5bb9\u5931\u8d25");
            progressItem.setFailed(true);
            this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
            throw new RuntimeException("\u4fdd\u5b58\u516c\u5f0f\u5931\u8d25\uff0c\u6279\u91cf\u586b\u5145\u516c\u5f0f\u5185\u5bb9\u5931\u8d25");
        }
        ArrayList<DesignFormulaDefine> needUpdateFormula = new ArrayList<DesignFormulaDefine>();
        ArrayList<DesignFormulaDTO> designFormulaDTOList = new ArrayList<DesignFormulaDTO>();
        ArrayList formulaConditions = new ArrayList();
        for (List<DesignFormulaDTO> formulaDTOS : formulaSchemeAndFormulaMap.values()) {
            for (DesignFormulaDTO formulaDTO : formulaDTOS) {
                if (formulaDTO.isSuccess()) {
                    needUpdateFormula.add(formulaDTO.getDesignFormulaDefine());
                }
                formulaConditions.addAll(formulaDTO.getConditions());
            }
            designFormulaDTOList.addAll(formulaDTOS);
        }
        progressItem.setCurrentProgess(66);
        progressItem.setMessage("\u516c\u5f0f\u89e3\u6790\u5b8c\u6210\uff0c\u6b63\u5728\u4fdd\u5b58\u516c\u5f0f......");
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        try {
            if (!needUpdateFormula.isEmpty()) {
                this.iFormulaDesignTimeController.updateFormulaDefines(needUpdateFormula.toArray(new DesignFormulaDefine[0]));
            }
        }
        catch (Exception e) {
            progressItem.setCurrentProgess(100);
            progressItem.setMessage("\u6279\u91cf\u66f4\u65b0\u516c\u5f0f\u5931\u8d25");
            progressItem.setFailed(true);
            this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
            throw new RuntimeException("\u6279\u91cf\u66f4\u65b0\u516c\u5f0f\u5931\u8d25");
        }
        if (!CollectionUtils.isEmpty(formulaConditions)) {
            List updateList = formulaConditions.stream().filter(FormulaConditionDTO::isNeedUpdate).map(FormulaConditionDTO::getFormulaCondition).collect(Collectors.toList());
            this.formulaDesignTimeController.updateFormulaConditions(updateList);
        }
        progressItem.setCurrentProgess(100);
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        List<FormulaUpdateRecordVO> formulaUpdateRecordList = this.saveFormWithFormulaHelper.getFormulaUpdateRecordList(designFormulaDTOList, formScheme);
        progressItem.nextStep();
        progressItem.setCurrentProgess(100);
        progressItem.setMessage("\u4fdd\u5b58\u62a5\u8868\u5e76\u66f4\u65b0\u516c\u5f0f\u5df2\u5b8c\u6210\uff0c\u70b9\u51fb\"\u67e5\u770b\u8be6\u60c5\"\u53ef\u67e5\u770b\u516c\u5f0f\u66f4\u65b0\u8bb0\u5f55");
        progressItem.setFinished(true);
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
        return formulaUpdateRecordList;
    }

    private void checkFormType(String formKey) {
        DesignFormDefine form = this.designTimeViewController.getForm(formKey);
        if (!FormType.FORM_TYPE_FIX.equals((Object)form.getFormType()) && !FormType.FORM_TYPE_FLOAT.equals((Object)form.getFormType())) {
            return;
        }
        boolean needUpdateForm = false;
        List dataRegions = this.designTimeViewController.listDataRegionByForm(formKey);
        List floatRegions = dataRegions.stream().filter(dataRegion -> DataRegionKind.DATA_REGION_ROW_LIST.equals((Object)dataRegion.getRegionKind()) || DataRegionKind.DATA_REGION_COLUMN_LIST.equals((Object)dataRegion.getRegionKind())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(floatRegions) && FormType.FORM_TYPE_FLOAT.equals((Object)form.getFormType())) {
            needUpdateForm = true;
            form.setFormType(FormType.FORM_TYPE_FIX);
        }
        if (!CollectionUtils.isEmpty(floatRegions) && FormType.FORM_TYPE_FIX.equals((Object)form.getFormType())) {
            needUpdateForm = true;
            form.setFormType(FormType.FORM_TYPE_FLOAT);
        }
        if (needUpdateForm) {
            this.designTimeViewController.updateForm(form);
        }
    }

    private void saveLinkMapping(String formKey, List<LinkMappingDTO> dataLinkMapping) {
        this.linkMappingService.saveLinkMapping(formKey, dataLinkMapping);
    }

    private void saveConditionStyle(String formKey, List<ConditionStyleDTO> conditionStyle) {
        this.conditionStyleService.save(formKey, conditionStyle);
    }

    private void saveTableSetting(List<DataTableDTO> tableSetting) {
        this.dataTableService.saveTableSetting(tableSetting);
    }

    private void saveComponentConfigs(String formKey, List<ConfigDTO> componentConfigs) {
    }

    private void saveRegionSetting(String formKey, List<DataRegionSettingDTO> regionSetting) {
        if (CollectionUtils.isEmpty(regionSetting)) {
            return;
        }
        this.regionService.saveDataRegionSetting(formKey, regionSetting);
        List<ConfigDTO> configDTOList = this.collectConfigExt(regionSetting);
        this.regionConfigExtService.saveConfigs(formKey, configDTOList);
    }

    private <T extends ConfigExt> List<ConfigDTO> collectConfigExt(List<T> configExtList) {
        return configExtList.stream().map(ConfigExt::getConfigData).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
    }

    private void saveLinkSetting(String formKey, List<DataLinkSettingDTO> dataLinkSetting) {
        if (CollectionUtils.isEmpty(dataLinkSetting)) {
            return;
        }
        this.dataLinkService.saveLinks(formKey, dataLinkSetting);
        List<ConfigDTO> configDTOList = this.collectConfigExt(dataLinkSetting);
        this.linkConfigExtService.saveConfigs(formKey, configDTOList);
    }

    private void saveFieldSetting(String formKey, List<DataFieldSettingDTO> dataFieldSetting) {
        if (CollectionUtils.isEmpty(dataFieldSetting)) {
            return;
        }
        this.dataFieldService.saveFields(formKey, dataFieldSetting);
    }

    @Override
    public List<FormItemDTO> queryFormByScheme(String formSchemeKey) {
        return this.formDefineService.queryFormByScheme(formSchemeKey);
    }

    @Override
    public SimpleFormDesignerDTO querySimpleFormData(String formKey, int language) {
        DesignFormDefine form = this.designTimeViewController.getForm(formKey);
        if (form == null) {
            throw new FormRuntimeException("\u4e0d\u5b58\u5728\u7684\u62a5\u8868:" + formKey);
        }
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(form.getFormScheme());
        if (formScheme == null) {
            throw new FormRuntimeException(String.format("\u62a5\u8868:%s[%s]\u6240\u5c5e\u62a5\u8868\u65b9\u6848:%s\u4e0d\u5b58\u5728", form.getTitle(), form.getFormCode(), form.getFormScheme()));
        }
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        FormDTO formdto = this.formDefineService.getForm(formKey);
        SimpleFormDesignerDTO simpleFormDesignerDTO = new SimpleFormDesignerDTO();
        simpleFormDesignerDTO.setForm(formdto);
        simpleFormDesignerDTO.setFormSchemeKey(formScheme.getKey());
        simpleFormDesignerDTO.setTaskKey(formScheme.getTaskKey());
        simpleFormDesignerDTO.setDataSchemeKey(task.getDataScheme());
        simpleFormDesignerDTO.setDataScheme(DataSchemeDefineDTO.valueOf((DataScheme)this.designDataSchemeService.getDataScheme(task.getDataScheme())));
        simpleFormDesignerDTO.setFormStyle(this.getFormStyle(formKey, language));
        simpleFormDesignerDTO.setDataRegions(this.regionService.listDataRegionSetting(formKey));
        simpleFormDesignerDTO.setDataLinks(this.dataLinkService.listDataLinkSettingByForm(formKey));
        simpleFormDesignerDTO.setDataFields(this.getSimpleFieldData(simpleFormDesignerDTO.getDataLinks()));
        simpleFormDesignerDTO.setDataTables(this.getSimpleDataTables(simpleFormDesignerDTO.getDataFields()));
        simpleFormDesignerDTO.setComponentConfigs(this.getComponentConfigs(formKey));
        simpleFormDesignerDTO.setEntityFields(this.getOrgFields(task.getKey()));
        this.addBizKeys(simpleFormDesignerDTO);
        this.fixParam(simpleFormDesignerDTO);
        this.setFieldReferEntityTitle(simpleFormDesignerDTO.getDataFields());
        return simpleFormDesignerDTO;
    }

    private void setFieldReferEntityTitle(Set<DataFieldSettingDTO> dataFields) {
        HashMap<String, IEntityDefine> entityDefineMap = new HashMap<String, IEntityDefine>();
        for (DataFieldSettingDTO field : dataFields) {
            if (!StringUtils.hasLength(field.getRefDataEntityKey())) continue;
            if (null != entityDefineMap.get(field.getRefDataEntityKey())) {
                field.setRefDataEntityTitle(((IEntityDefine)entityDefineMap.get(field.getRefDataEntityKey())).getTitle());
                continue;
            }
            IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(field.getRefDataEntityKey());
            if (null == iEntityDefine) continue;
            entityDefineMap.put(field.getRefDataEntityKey(), iEntityDefine);
            field.setRefDataEntityTitle(iEntityDefine.getTitle());
        }
    }

    private void fixParam(SimpleFormDesignerDTO simpleFormDesignerDTO) {
        FormStyleDTO formStyle;
        CellBook cellBook;
        List sheets;
        List<DataRegionSettingDTO> dataRegions = simpleFormDesignerDTO.getDataRegions();
        boolean match = dataRegions.stream().anyMatch(r -> r.getRegionKind().intValue() == DataRegionKind.DATA_REGION_SIMPLE.getValue());
        if (!match && !(sheets = (cellBook = (formStyle = simpleFormDesignerDTO.getFormStyle()).getCellBook()).getSheets()).isEmpty()) {
            CellSheet cellSheet = (CellSheet)sheets.get(0);
            DataRegionSettingDTO region = this.initRegion(simpleFormDesignerDTO.getForm());
            region.setKey(UUIDUtils.getKey());
            region.setRegionBottom(cellSheet.getRowCount() - 1);
            region.setRegionRight(cellSheet.getColumnCount() - 1);
            region.setState(Constants.DataStatus.NEW.ordinal());
            dataRegions.add(region);
        }
    }

    private void addBizKeys(SimpleFormDesignerDTO simpleFormDesignerDTO) {
        List<DataTableDTO> dataTables = simpleFormDesignerDTO.getDataTables();
        Set<DataFieldSettingDTO> dataFields1 = simpleFormDesignerDTO.getDataFields();
        if (!CollectionUtils.isEmpty(dataTables)) {
            for (DataTableDTO dataTable : dataTables) {
                if (dataTable.getBizKeys() == null) continue;
                List dataFields = this.designDataSchemeService.getDataFields(Arrays.asList(dataTable.getBizKeys()));
                dataFields.forEach(f -> {
                    if (f.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) {
                        dataFields1.add(FieldBeanUtils.toSettingDto(f));
                    }
                });
            }
        }
    }

    private List<EntityFieldDTO> getOrgFields(String taskKey) {
        List taskOrgLinkDefines = this.designTimeViewController.listTaskOrgLinkByTask(taskKey);
        if (!CollectionUtils.isEmpty(taskOrgLinkDefines)) {
            ArrayList<EntityFieldDTO> list = new ArrayList<EntityFieldDTO>();
            List taskOrg = taskOrgLinkDefines.stream().map(TaskOrgLinkDefine::getEntity).collect(Collectors.toList());
            HashMap<String, IEntityRefer> entityReferMap = new HashMap<String, IEntityRefer>();
            for (String s : taskOrg) {
                IEntityModel entityModel = this.entityMetaService.getEntityModel(s);
                TableModelDefine tableModel = this.entityMetaService.getTableModel(s);
                List showFields = entityModel.getShowFields();
                List entityRefer = this.entityMetaService.getEntityRefer(s);
                if (null != entityRefer) {
                    for (IEntityRefer iEntityRefer : entityRefer) {
                        entityReferMap.put(iEntityRefer.getOwnField(), iEntityRefer);
                    }
                }
                for (IEntityAttribute showField : showFields) {
                    EntityFieldDTO dto = EntityFieldBeanUtils.toDTO(showField);
                    dto.setTableName(tableModel.getTitle());
                    if (entityModel.getParentField().getCode().equals(showField.getCode())) {
                        dto.setRefDataEntityKey(entityModel.getEntityId());
                        dto.setRefDataEntityTitle(tableModel.getTitle());
                        list.add(dto);
                        continue;
                    }
                    if (null == showField.getReferTableID() || null == entityReferMap.get(showField.getCode())) {
                        list.add(dto);
                        continue;
                    }
                    IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(((IEntityRefer)entityReferMap.get(showField.getCode())).getReferEntityId());
                    if (null != iEntityDefine) {
                        dto.setRefDataEntityKey(iEntityDefine.getId());
                        dto.setRefDataEntityTitle(iEntityDefine.getTitle());
                    }
                    list.add(dto);
                }
            }
            return list;
        }
        return Collections.emptyList();
    }

    private List<DataTableDTO> getSimpleDataTables(Set<DataFieldSettingDTO> dataFields) {
        Set collect = dataFields.stream().map(DataFieldDTO::getDataTableKey).collect(Collectors.toSet());
        return this.dataTableService.listDataTables(new ArrayList<String>(collect));
    }

    private List<ConfigDTO> getComponentConfigs(String formKey) {
        return this.getFormDefineResourceExts().stream().map(ext -> {
            if (ext.getComponentConfigExt() != null) {
                return ext.getComponentConfigExt().getConfig(formKey);
            }
            return null;
        }).collect(Collectors.toList());
    }

    private Set<DataFieldSettingDTO> getSimpleFieldData(List<? extends DataLinkDTO> dataLinks) {
        List fieldKeys = dataLinks.stream().filter(l -> DataLinkType.DATA_LINK_TYPE_FIELD.getValue() == l.getType().intValue() || DataLinkType.DATA_LINK_TYPE_INFO.getValue() == l.getType().intValue()).map(DataLinkDTO::getLinkExpression).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return new HashSet<DataFieldSettingDTO>();
        }
        List dataFields = this.designDataSchemeService.getDataFields(fieldKeys);
        return dataFields.stream().map(FieldBeanUtils::toSettingDto).collect(Collectors.toSet());
    }

    private FormStyleDTO getFormStyle(String formKey, int language) {
        FormStyleDTO formStyleDTO = new FormStyleDTO();
        formStyleDTO.setKey(formKey);
        formStyleDTO.setCellBook(this.formStyleService.getFormStyle(formKey, language));
        return formStyleDTO;
    }

    @Override
    public List<DataRegionSettingDTO> listRegionSettingByForm(String formKey) {
        List<DataRegionSettingDTO> regionSetting = this.regionService.listDataRegionSetting(formKey);
        List<String> keys = regionSetting.stream().map(DataCore::getKey).collect(Collectors.toList());
        Map<String, List<ConfigDTO>> configs = this.regionConfigExtService.listConfigs(formKey, keys);
        for (DataRegionSettingDTO dto : regionSetting) {
            dto.setConfigData(configs.get(dto.getKey()));
        }
        return regionSetting;
    }

    @Override
    public List<DataLinkSettingDTO> listLinkSettingByForm(String formKey) {
        List<DataLinkSettingDTO> dataLinkSetting = this.dataLinkService.listDataLinkSettingByForm(formKey);
        List<String> collect = dataLinkSetting.stream().map(DataCore::getKey).collect(Collectors.toList());
        Map<String, List<ConfigDTO>> map = this.linkConfigExtService.listConfigs(formKey, collect);
        for (DataLinkSettingDTO dto : dataLinkSetting) {
            dto.setConfigData(map.get(dto.getKey()));
        }
        return dataLinkSetting;
    }

    @Override
    public List<DataFieldSettingDTO> listFieldSettingByForm(String formKey) {
        List dataLinkDefines = this.designTimeViewController.listDataLinkByForm(formKey);
        List fieldKeys = dataLinkDefines.stream().filter(l -> DataLinkType.DATA_LINK_TYPE_FIELD.equals((Object)l.getType())).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return Collections.emptyList();
        }
        List dataFields = this.designDataSchemeService.getDataFields(fieldKeys);
        return FieldBeanUtils.toSettingList(dataFields, this.entityMetaService, this.periodEngineService, this.designDataSchemeService);
    }

    @Override
    public List<ConfigDTO> listComponentSetting(String formKey) {
        return this.getFormDefineResourceExts().stream().map(ext -> {
            if (ext.getComponentConfigExt() != null) {
                return ext.getComponentConfigExt().getConfig(formKey);
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    public DataRegionSettingDTO getRegionSetting(String regionKey) {
        DataRegionSettingDTO regionSettingDTO = this.regionService.getRegionSetting(regionKey);
        if (regionSettingDTO == null) {
            return null;
        }
        List<ConfigDTO> configDTOS = this.regionConfigExtService.listConfigs(regionKey);
        regionSettingDTO.setConfigData(configDTOS);
        return regionSettingDTO;
    }

    @Override
    public DataLinkSettingDTO getLinkSetting(String linkKey) {
        DataLinkSettingDTO linkSettingDTO = this.dataLinkService.getLinkSetting(linkKey);
        if (linkSettingDTO == null) {
            return null;
        }
        List<ConfigDTO> configDTOS = this.linkConfigExtService.listConfigs(linkKey);
        linkSettingDTO.setConfigData(configDTOS);
        return linkSettingDTO;
    }

    @Override
    public DataFieldSettingDTO getFieldSetting(String fieldKey) {
        DataFieldSettingDTO setting = this.dataFieldService.getFieldSetting(fieldKey);
        int type = DataFieldKind.FIELD_ZB.getValue() & DataFieldKind.FIELD.getValue() & DataFieldKind.TABLE_FIELD_DIM.getValue();
        if (setting != null) {
            return (setting.getDataFieldKind() & type) > 0 ? setting : null;
        }
        return null;
    }

    @Override
    public CheckResult checkFormData(FormDesignerDTO formDesignerDTO) {
        CheckResult checkResult = CheckResult.successResult();
        for (IFormCheckService service : this.formCheckServices) {
            CheckResult checkTemp = service.doCheck(formDesignerDTO);
            if (!checkTemp.isError()) continue;
            checkResult.addErrorData(checkTemp.getErrorData());
        }
        return checkResult;
    }

    @Override
    public List<EnumFieldVO> queryShowFields(String key) {
        IEntityModel entityModel;
        if (this.entityAssist.isEntity(key)) {
            entityModel = this.entityMetaService.getEntityModel(key);
        } else {
            TableModelDefine tableModelDefineById = this.dataModelService.getTableModelDefineById(key);
            String entityId = this.entityMetaService.getEntityIdByCode(tableModelDefineById.getCode());
            entityModel = this.entityMetaService.getEntityModel(entityId);
        }
        if (entityModel != null) {
            ArrayList<EnumFieldVO> list = new ArrayList<EnumFieldVO>();
            List showFields = entityModel.getShowFields();
            for (IEntityAttribute field : showFields) {
                EnumFieldVO enumFieldVO = new EnumFieldVO();
                enumFieldVO.setCode(field.getCode());
                enumFieldVO.setTitle(field.getTitle());
                list.add(enumFieldVO);
            }
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public List<DataFieldSettingDTO> listFieldSetting(List<String> keys) {
        int type = DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.FIELD.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
        List<DesignDataField> dataFields = this.designDataSchemeService.getDataFields(keys);
        dataFields = dataFields.stream().filter(field -> (field.getDataFieldKind().getValue() & type) > 0).collect(Collectors.toList());
        return FieldBeanUtils.toSettingList(dataFields, this.entityMetaService, this.periodEngineService, this.designDataSchemeService);
    }

    @Override
    public List<DataLinkSettingDTO> listLinkSetting(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }
        return keys.stream().map(x -> this.dataLinkService.getLinkSetting((String)x)).collect(Collectors.toList());
    }

    @Override
    public FormDTO getFormDefine(String formKey) {
        return this.formDefineService.getForm(formKey);
    }

    @Override
    public List<FormTreeNode> formTreeByScheme(String formScheme) {
        ArrayList<FormTreeNode> result = new ArrayList<FormTreeNode>();
        List designFormGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(formScheme);
        for (DesignFormGroupDefine designFormGroupDefine : designFormGroupDefines) {
            FormTreeNode group = new FormTreeNode();
            group.setKey(designFormGroupDefine.getKey());
            group.setTitle(designFormGroupDefine.getTitle());
            group.setNodeType("group");
            group.setDisabled(true);
            List designFormDefines = this.designTimeViewController.listFormByGroup(designFormGroupDefine.getKey());
            ArrayList<FormTreeNode> children = new ArrayList<FormTreeNode>();
            for (DesignFormDefine designFormDefine : designFormDefines) {
                FormTreeNode form = new FormTreeNode();
                form.setKey(designFormDefine.getKey());
                form.setTitle(designFormDefine.getTitle());
                form.setNodeType("form");
                children.add(form);
            }
            group.setChildren(children);
            result.add(group);
        }
        return result;
    }

    @Override
    public List<UITreeNode<FormUITreeNode>> getFormTreeRoot(FormTreeParam formTreeParam) {
        String formSchemeKey = formTreeParam.getFormSchemeKey();
        if (!StringUtils.hasText(formSchemeKey)) {
            throw new FormRuntimeException("\u62a5\u8868\u6811\u578b\u67e5\u627e\u6839\u8282\u70b9\u5173\u952e\u5b57formSchemeKey\u4e3a\u7a7a");
        }
        List<Object> treeForGroup = new ArrayList();
        if (formTreeParam.getSync()) {
            treeForGroup = this.getSyncFormGroupTree(formSchemeKey);
            if (treeForGroup.size() > 0) {
                ((UITreeNode)treeForGroup.get(0)).setExpand(true);
            }
        } else {
            treeForGroup = this.getFormGroupTree(formSchemeKey);
            if (treeForGroup.size() > 0) {
                UITreeNode firstFromGroupNode = (UITreeNode)treeForGroup.get(0);
                List<UITreeNode<FormUITreeNode>> tree_form = this.getFormChildTree(firstFromGroupNode.getKey());
                firstFromGroupNode.setChildren(tree_form);
                firstFromGroupNode.setExpand(true);
            }
        }
        return treeForGroup;
    }

    private List<UITreeNode<FormUITreeNode>> getSyncFormGroupTree(String formSchemeKey) {
        ArrayList<UITreeNode<FormUITreeNode>> result = new ArrayList<UITreeNode<FormUITreeNode>>();
        List<UITreeNode<FormUITreeNode>> formGroupTree = this.getFormGroupTree(formSchemeKey);
        for (UITreeNode<FormUITreeNode> formUITreeNodeUITreeNode : formGroupTree) {
            List<UITreeNode<FormUITreeNode>> formChildTree = this.getFormChildTree(formUITreeNodeUITreeNode.getKey());
            if (formChildTree.size() <= 0) continue;
            formUITreeNodeUITreeNode.setChildren(formChildTree);
            result.add(formUITreeNodeUITreeNode);
        }
        return result;
    }

    private List<UITreeNode<FormUITreeNode>> getFormGroupTree(String formSchemeKey) {
        ArrayList<UITreeNode<FormUITreeNode>> treeForGroup = new ArrayList<UITreeNode<FormUITreeNode>>();
        List formGroupsDefines = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        if (!CollectionUtils.isEmpty(formGroupsDefines)) {
            formGroupsDefines.forEach(formGroup -> {
                FormUITreeNode formGroupTreeNodeData = new FormUITreeNode((DesignFormGroupDefine)formGroup);
                UITreeNode formGroupTreeNode = new UITreeNode((TreeData)formGroupTreeNodeData);
                formGroupTreeNode.setKey(formGroupTreeNodeData.getKey());
                formGroupTreeNode.setTitle(formGroupTreeNodeData.getTitle());
                formGroupTreeNode.setLeaf(false);
                treeForGroup.add(formGroupTreeNode);
            });
        }
        return treeForGroup;
    }

    @Override
    public List<UITreeNode<FormUITreeNode>> getFormChildTree(FormTreeParam formTreeParam) {
        String formGroupKey = formTreeParam.getFormGroupKey();
        if (!StringUtils.hasText(formGroupKey)) {
            throw new FormRuntimeException("\u67e5\u627e\u5b50\u8282\u6811\u578b\u5173\u952e\u5b57\u4e3a\u7a7a: " + formGroupKey);
        }
        return this.getFormChildTree(formGroupKey);
    }

    private List<UITreeNode<FormUITreeNode>> getFormChildTree(String formGroupKey) {
        ArrayList<UITreeNode<FormUITreeNode>> treeForForm = new ArrayList<UITreeNode<FormUITreeNode>>();
        List designFormDefines = this.designTimeViewController.listFormByGroup(formGroupKey);
        designFormDefines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
        designFormDefines.forEach(form -> treeForForm.add(this.getTreeNode((DesignFormDefine)form, formGroupKey)));
        return treeForForm;
    }

    private UITreeNode<FormUITreeNode> getTreeNode(DesignFormDefine formDefine, String parentId) {
        FormUITreeNode formTreeNode = new FormUITreeNode(formDefine, parentId);
        UITreeNode node = new UITreeNode((TreeData)formTreeNode);
        node.setKey(formTreeNode.getKey());
        node.setTitle(formTreeNode.getTitle());
        node.setParent(parentId);
        node.setLeaf(true);
        return node;
    }

    @Override
    public List<UITreeNode<FormUITreeNode>> locationFormTree(FormTreeParam formTreeParam) {
        String formSchemeKey = formTreeParam.getFormSchemeKey();
        List<String> selectedKeys = formTreeParam.getSelectedKeys();
        if (!StringUtils.hasText(formSchemeKey)) {
            throw new FormRuntimeException("\u5b9a\u4f4d\u62a5\u8868\u6811\u578b\u62a5\u8868\u65b9\u6848\u5173\u952e\u5b57formSchemeKey\u4e3a\u7a7a");
        }
        if (selectedKeys == null || selectedKeys.size() == 0) {
            throw new FormRuntimeException("\u5b9a\u4f4d\u62a5\u8868\u6811\u578b\u5173\u952e\u5b57selectedKeys\u4e3a\u7a7a");
        }
        List<Object> formGroupTree = new ArrayList();
        formGroupTree = selectedKeys.size() == 1 ? this.locationOneForm(formTreeParam) : this.locationMoreForm(formTreeParam);
        return formGroupTree;
    }

    public List<UITreeNode<FormUITreeNode>> locationMoreForm(FormTreeParam formTreeParam) {
        List<Object> formGroupTrees = new ArrayList();
        String formSchemeKey = formTreeParam.getFormSchemeKey();
        List<String> selectedKeys = formTreeParam.getSelectedKeys();
        HashSet<String> selectedKeySets = new HashSet<String>(selectedKeys);
        formGroupTrees = this.getFormGroupTree(formSchemeKey);
        for (UITreeNode uITreeNode : formGroupTrees) {
            ArrayList<UITreeNode<FormUITreeNode>> treeForForm = new ArrayList<UITreeNode<FormUITreeNode>>();
            List designFormDefines = this.designTimeViewController.listFormByGroup(uITreeNode.getKey());
            designFormDefines.sort(Comparator.comparing(IBaseMetaItem::getOrder));
            int num = 0;
            for (DesignFormDefine form : designFormDefines) {
                UITreeNode<FormUITreeNode> formNode = this.getTreeNode(form, uITreeNode.getKey());
                treeForForm.add(formNode);
                if (!selectedKeySets.contains(form.getKey())) continue;
                formNode.setChecked(true);
                uITreeNode.setExpand(true);
                ++num;
            }
            uITreeNode.setChildren(treeForForm);
            if (num != designFormDefines.size()) continue;
            uITreeNode.setChecked(true);
        }
        return formGroupTrees;
    }

    public List<UITreeNode<FormUITreeNode>> locationOneForm(FormTreeParam formTreeParam) {
        String formKey = formTreeParam.getSelectedKeys().get(0);
        if (!StringUtils.hasText(formKey)) {
            throw new FormRuntimeException("\u5b9a\u4f4d\u6a21\u62a5\u8868\u5173\u952e\u5b57formKey\u4e3a\u7a7a");
        }
        DesignFormDefine form = this.designTimeViewController.getForm(formKey);
        if (form == null) {
            throw new FormRuntimeException("\u62a5\u8868 " + formKey + " \u4e0d\u5b58\u5728\uff01");
        }
        List designFormGroupDefines = this.designTimeViewController.listFormGroupByForm(formKey);
        if (designFormGroupDefines == null || designFormGroupDefines.size() == 0) {
            throw new FormRuntimeException("\u5b9a\u4f4d\u62a5\u8868\u6811\u578b\u8be5\u62a5\u8868\u6240\u5c5e\u5206\u7ec4\u67e5\u8be2\u5f02\u5e38\uff1a " + formKey);
        }
        Set formGroupKeys = designFormGroupDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        String formSchemeKey = form.getFormScheme();
        List<UITreeNode<FormUITreeNode>> formGroupTrees = this.getFormGroupTree(formSchemeKey);
        for (UITreeNode<FormUITreeNode> formGroupNode : formGroupTrees) {
            if (!formGroupKeys.contains(formGroupNode.getKey())) continue;
            List<UITreeNode<FormUITreeNode>> formChildTree = this.getFormChildTree(formGroupNode.getKey());
            int num = 0;
            for (UITreeNode<FormUITreeNode> formNode : formChildTree) {
                if (!formNode.getKey().equals(formKey)) continue;
                formNode.setChecked(true);
                if (formChildTree.size() == 1 && formGroupKeys.size() == 1) {
                    formNode.setSelected(true);
                }
                ++num;
            }
            if (formChildTree.size() == num) {
                formGroupNode.setChecked(true);
            }
            formGroupNode.setChildren(formChildTree);
            formGroupNode.setExpand(true);
        }
        return formGroupTrees;
    }

    @Override
    public List<FormItemDTO> fuzzySearch(FormTreeParam formTreeParam) {
        String formSchemeKey = formTreeParam.getFormSchemeKey();
        String keyword = formTreeParam.getKeyword();
        if (!StringUtils.hasText(keyword) || !StringUtils.hasText(formSchemeKey)) {
            throw new FormRuntimeException("\u5173\u952e\u5b57\u4e0d\u80fd\u4e3a\u7a7a");
        }
        ArrayList<FormItemDTO> result = new ArrayList<FormItemDTO>();
        keyword = keyword.trim().toLowerCase(Locale.ROOT);
        List formsForScheme = this.designTimeViewController.listFormByFormScheme(formSchemeKey);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (DesignFormDefine define : formsForScheme) {
            if (!define.getTitle().toLowerCase(Locale.ROOT).contains(keyword) && !define.getFormCode().toLowerCase(Locale.ROOT).contains(keyword)) continue;
            FormItemDTO instance = FormItemDTO.getInstance(define, sdf);
            result.add(instance);
        }
        return result;
    }

    @Override
    public boolean codeCheck(String formSchemeKey, String formCode, String formKey) {
        DesignFormDefine form = this.designTimeViewController.getFormByFormSchemeAndCode(formSchemeKey, formCode);
        if (form != null && form.getKey().equals(formKey)) {
            return true;
        }
        return form == null;
    }

    @Override
    public boolean checkFormTitle(FormDTO formDTO) {
        String title = formDTO.getTitle().trim();
        List designFormDefines = this.designTimeViewController.listFormByFormScheme(formDTO.getFormScheme());
        List filterDesignFormDefines = designFormDefines.stream().filter(a -> a.getTitle().equals(title)).collect(Collectors.toList());
        if (StringUtils.hasText(formDTO.getKey())) {
            if (!CollectionUtils.isEmpty(filterDesignFormDefines)) {
                List sameTitleDifKey = filterDesignFormDefines.stream().filter(a -> !a.getKey().equals(formDTO.getKey())).collect(Collectors.toList());
                return sameTitleDifKey.size() > 0;
            }
            return false;
        }
        return !CollectionUtils.isEmpty(filterDesignFormDefines);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean copyForm(CopyFormVO copyFormVO) {
        DesignFormDefine formDefine = this.designTimeViewController.getForm(copyFormVO.getSourceKey());
        DesignFormGroupDefine formGroupDefine = this.designTimeViewController.getFormGroup(copyFormVO.getGroupKey());
        DesignFormDefineImpl newForm = new DesignFormDefineImpl();
        BeanUtils.copyProperties(formDefine, newForm);
        newForm.setKey(UUIDUtils.getKey());
        newForm.setOrder(com.jiuqi.util.OrderGenerator.newOrder());
        newForm.setFormCode(copyFormVO.getNewCode());
        newForm.setTitle(copyFormVO.getNewTitle());
        newForm.setUpdateTime(new Date());
        newForm.setFormScheme(formGroupDefine.getFormSchemeKey());
        if (!StringUtils.hasText(newForm.getUpdateUser())) {
            newForm.setUpdateUser(NpContextHolder.getContext().getUserName());
        }
        if (formDefine.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS && null != formDefine.getFormExtension()) {
            Map formExtension = formDefine.getFormExtension();
            for (String anaKey : formExtension.keySet()) {
                newForm.addExtensions(anaKey, formExtension.get(anaKey));
            }
        }
        newForm.setFormType(copyFormVO.isCopyStyleOnly() ? FormType.FORM_TYPE_FIX : newForm.getFormType());
        this.designTimeViewController.insertForm((DesignFormDefine)newForm);
        DesignFormGroupLink groupLink = this.designTimeViewController.initFormGroupLink();
        groupLink.setFormKey(newForm.getKey());
        groupLink.setGroupKey(formGroupDefine.getKey());
        DesignFormGroupLink[] groupLinkDefine = new DesignFormGroupLink[]{groupLink};
        this.designTimeViewController.insertFormGroupLink(groupLinkDefine);
        if (copyFormVO.isCopyStyleOnly()) {
            Grid2Data formStyle = this.designTimeViewController.getFormStyle(copyFormVO.getSourceKey());
            this.designTimeViewController.insertFormStyle(newForm.getKey(), formStyle);
            this.insertDefaultRegionByStyle((DesignFormDefine)newForm, formStyle.getColumnCount(), formStyle.getRowCount());
            return true;
        }
        HashMap<String, String> linkMap = new HashMap<String, String>();
        List regionsInForm = this.designTimeViewController.listDataRegionByForm(formDefine.getKey());
        for (DesignDataRegionDefine regionDefine : regionsInForm) {
            Object newRegion = null;
            try {
                newRegion = this.getNewDataRegion(regionDefine, newForm.getKey());
            }
            catch (Exception e) {
                throw new RuntimeException("\u533a\u57df\u590d\u5236\u5931\u8d25");
            }
            List allLinksInRegion = this.designTimeViewController.listDataLinkByDataRegion(regionDefine.getKey());
            ArrayList<DesignDataLinkDefineImpl> insertLink = new ArrayList<DesignDataLinkDefineImpl>();
            ArrayList<DesignBigDataTable> insertLinkData = new ArrayList<DesignBigDataTable>();
            for (DesignDataLinkDefine designDataLinkDefine : allLinksInRegion) {
                DesignDataLinkDefineImpl newLink = new DesignDataLinkDefineImpl();
                BeanUtils.copyProperties(designDataLinkDefine, newLink);
                newLink.setKey(UUIDUtils.getKey());
                newLink.setRegionKey(newRegion.getKey());
                newLink.setUniqueCode(com.jiuqi.util.OrderGenerator.newOrder());
                newLink.setUpdateTime(new Date());
                newLink.setOrder(com.jiuqi.util.OrderGenerator.newOrder());
                insertLink.add(newLink);
                linkMap.put(designDataLinkDefine.getKey(), newLink.getKey());
                try {
                    DesignBigDataTable attachments = this.bigDataTableDao.queryigDataDefine(designDataLinkDefine.getKey(), "ATTACHMENT");
                    if (null == attachments) continue;
                    DesignBigDataTable designBigDataTable = new DesignBigDataTable();
                    designBigDataTable.setKey(newLink.getKey());
                    designBigDataTable.setData(attachments.getData());
                    designBigDataTable.setCode("ATTACHMENT");
                    insertLinkData.add(designBigDataTable);
                }
                catch (Exception e) {
                    throw new RuntimeException("\u94fe\u63a5\u9644\u4ef6\u590d\u5236\u5931\u8d25");
                }
            }
            for (DesignDataLinkDefine designDataLinkDefine : insertLink) {
                if (!StringUtils.hasText(designDataLinkDefine.getEnumLinkage())) continue;
                Object newEnumStr = designDataLinkDefine.getEnumLinkage();
                for (DesignDataLinkDefine l2 : allLinksInRegion) {
                    if (!((String)newEnumStr).contains(l2.getKey())) continue;
                    newEnumStr = ((String)newEnumStr).replace(l2.getKey(), (CharSequence)linkMap.get(l2.getKey()));
                }
                designDataLinkDefine.setEnumLinkage((String)newEnumStr);
            }
            if (!CollectionUtils.isEmpty(insertLink)) {
                this.designTimeViewController.insertDataLink(insertLink.toArray(new DesignDataLinkDefine[0]));
            }
            if (CollectionUtils.isEmpty(insertLinkData)) continue;
            for (DesignBigDataTable designBigDataTable : insertLinkData) {
            }
        }
        List dataLinkMappingDefines = this.designTimeViewController.listDataLinkMappingByForm(formDefine.getKey());
        ArrayList<Object> insertMapping = new ArrayList<Object>();
        for (DesignDataLinkMappingDefine dataLinkMappingDefine : dataLinkMappingDefines) {
            DesignDataLinkMappingDefineImpl newMapping = new DesignDataLinkMappingDefineImpl();
            if (null == linkMap.get(dataLinkMappingDefine.getLeftDataLinkKey()) || null == linkMap.get(dataLinkMappingDefine.getRightDataLinkKey())) continue;
            BeanUtils.copyProperties(dataLinkMappingDefine, newMapping);
            newMapping.setId(UUIDUtils.getKey());
            newMapping.setFormKey(newForm.getKey());
            newMapping.setLeftDataLinkKey((String)linkMap.get(dataLinkMappingDefine.getLeftDataLinkKey()));
            newMapping.setRightDataLinkKey((String)linkMap.get(dataLinkMappingDefine.getRightDataLinkKey()));
            insertMapping.add(newMapping);
        }
        if (!CollectionUtils.isEmpty(insertMapping)) {
            this.designTimeViewController.insertDataLinkMapping(insertMapping.toArray(new DesignDataLinkMappingDefine[0]));
        }
        List<ConditionStyleDTO> styleDTOList = this.conditionStyleService.getByForm(formDefine.getKey());
        ArrayList<ConditionStyleDTO> insertStyle = new ArrayList<ConditionStyleDTO>();
        if (!CollectionUtils.isEmpty(styleDTOList)) {
            for (ConditionStyleDTO designConditionalStyle : styleDTOList) {
                ConditionStyleDTO newStyle = new ConditionStyleDTO();
                BeanUtils.copyProperties(designConditionalStyle, newStyle);
                newStyle.setKey(UUIDUtils.getKey());
                newStyle.setOrder(OrderGenerator.newOrder());
                newStyle.setFormKey(newForm.getKey());
                newStyle.setUpdateTime(new Date());
                String string = (String)linkMap.get(designConditionalStyle.getLinkKey() == null ? designConditionalStyle.getLinkKey() : "");
                newStyle.setLinkKey(string);
                insertStyle.add(newStyle);
            }
        }
        if (!CollectionUtils.isEmpty(insertStyle)) {
            this.conditionStyleService.insert(insertStyle);
        }
        HashMap<String, String> formulaSchemeMap = new HashMap<String, String>();
        boolean isCurrentFormScheme = false;
        HashMap<String, String> printSchemeMap = new HashMap<String, String>();
        if (formDefine.getFormScheme().equals(formGroupDefine.getFormSchemeKey())) {
            isCurrentFormScheme = true;
        } else {
            List list = this.iFormulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formGroupDefine.getFormSchemeKey());
            for (DesignFormulaSchemeDefine targetFormulaScheme : list) {
                formulaSchemeMap.put(targetFormulaScheme.getTitle(), targetFormulaScheme.getKey());
            }
            List printSchemeByFormScheme = null;
            try {
                printSchemeByFormScheme = this.iPrintDesignTimeController.getAllPrintSchemeByFormScheme(formGroupDefine.getFormSchemeKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (printSchemeByFormScheme != null) {
                for (DesignPrintTemplateSchemeDefine printTemplateSchemeDefine : printSchemeByFormScheme) {
                    printSchemeMap.put(printTemplateSchemeDefine.getTitle(), printTemplateSchemeDefine.getKey());
                }
            }
        }
        List list = this.iFormulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formDefine.getFormScheme());
        HashSet<String> formulaCodeSet = new HashSet<String>();
        int number = 1;
        for (DesignFormulaSchemeDefine formulaSchemeDefine : list) {
            ArrayList<DesignFormulaDefineImpl> insertFormula = new ArrayList<DesignFormulaDefineImpl>();
            if (!isCurrentFormScheme && null == formulaSchemeMap.get(formulaSchemeDefine.getTitle())) continue;
            String targetFormulaScheme = "";
            targetFormulaScheme = isCurrentFormScheme ? formulaSchemeDefine.getKey() : (String)formulaSchemeMap.get(formulaSchemeDefine.getTitle());
            List targetFormulas = null;
            try {
                targetFormulas = this.iFormulaDesignTimeController.querySimpleFormulaDefineByScheme(targetFormulaScheme);
            }
            catch (JQException e) {
                throw new RuntimeException(e);
            }
            for (DesignFormulaDefine targetFormula : targetFormulas) {
                formulaCodeSet.add(targetFormula.getCode());
            }
            List formulaDefines = null;
            try {
                formulaDefines = this.iFormulaDesignTimeController.getAllFormulasInForm(formulaSchemeDefine.getKey(), formDefine.getKey());
            }
            catch (JQException e) {
                throw new RuntimeException(e);
            }
            for (DesignFormulaDefine formulaDefine : formulaDefines) {
                DesignFormulaDefineImpl newFormula = new DesignFormulaDefineImpl();
                BeanUtils.copyProperties(formulaDefine, newFormula);
                newFormula.setKey(UUIDUtils.getKey());
                newFormula.setUpdateTime(new Date());
                newFormula.setOrder(OrderGenerator.newOrder());
                newFormula.setFormKey(newForm.getKey());
                newFormula.setFormulaSchemeKey(targetFormulaScheme);
                String newCode = formulaDefine.getCode();
                newCode = newCode.startsWith(formDefine.getFormCode()) ? newCode.replaceFirst(formDefine.getFormCode(), newForm.getFormCode()) : newForm.getFormCode().concat(newCode);
                while (!formulaCodeSet.add(newCode)) {
                    newCode = newCode.concat(number + "");
                    ++number;
                }
                newFormula.setCode(newCode);
                insertFormula.add(newFormula);
            }
            try {
                this.iFormulaDesignTimeController.insertFormulaDefines(insertFormula.toArray(new DesignFormulaDefine[0]));
            }
            catch (JQException e) {
                throw new RuntimeException(e);
            }
            formulaCodeSet.clear();
            number = 1;
        }
        List printSchemeByFormScheme = null;
        try {
            printSchemeByFormScheme = this.iPrintDesignTimeController.getAllPrintSchemeByFormScheme(formDefine.getFormScheme());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine : printSchemeByFormScheme) {
            DesignPrintSettingDefine setting;
            DesignPrintTemplateDefine designPrintTemplateDefine;
            if (!isCurrentFormScheme && null == printSchemeMap.get(designPrintTemplateSchemeDefine.getTitle())) continue;
            String targetPrintScheme = "";
            targetPrintScheme = isCurrentFormScheme ? designPrintTemplateSchemeDefine.getKey() : (String)printSchemeMap.get(designPrintTemplateSchemeDefine.getTitle());
            try {
                designPrintTemplateDefine = this.iPrintDesignTimeController.queryPrintTemplateDefineBySchemeAndForm(designPrintTemplateSchemeDefine.getKey(), formDefine.getKey());
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (null != designPrintTemplateDefine) {
                DesignPrintTemplateDefineImpl newTemp = new DesignPrintTemplateDefineImpl();
                BeanUtils.copyProperties(designPrintTemplateDefine, newTemp);
                newTemp.setKey(UUIDUtils.getKey());
                newTemp.setFormKey(newForm.getKey());
                newTemp.setPrintSchemeKey(targetPrintScheme);
                newTemp.setUpdateTime(new Date());
                newTemp.setOrder(com.jiuqi.util.OrderGenerator.newOrder());
                try {
                    this.iPrintDesignTimeController.insertPrintTemplateDefine((DesignPrintTemplateDefine)newTemp);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (null == (setting = this.nrDesignTimeController.getPrintSettingDefine(designPrintTemplateSchemeDefine.getKey(), formDefine.getKey()))) continue;
            setting.setFormKey(newForm.getKey());
            setting.setPrintSchemeKey(targetPrintScheme);
            setting.setUpdateTime(new Date());
            this.nrDesignTimeController.insertPrintSettingDefine(setting);
        }
        return false;
    }

    private void insertDefaultRegionByStyle(DesignFormDefine newForm, int columnCount, int rowCount) {
        DataRegionSettingDTO regionSettingDTO = new DataRegionSettingDTO();
        regionSettingDTO.setFormKey(newForm.getKey());
        if (newForm.getTitle() != null) {
            regionSettingDTO.setTitle(newForm.getTitle() + "\u9ed8\u8ba4\u56fa\u5b9a\u533a\u57df");
        } else {
            regionSettingDTO.setTitle("\u9ed8\u8ba4\u56fa\u5b9a\u533a\u57df");
        }
        regionSettingDTO.setRegionLeft(1);
        regionSettingDTO.setRegionTop(1);
        regionSettingDTO.setRegionRight(columnCount);
        regionSettingDTO.setRegionBottom(rowCount);
        regionSettingDTO.setRowsInFloatRegion(1);
        regionSettingDTO.setRegionEnterNext(RegionEnterNext.BOTTOM);
        regionSettingDTO.setRegionKind(DataRegionKind.DATA_REGION_SIMPLE.getValue());
        this.regionService.insertDefaultRegion(regionSettingDTO);
    }

    private DesignDataRegionDefine getNewDataRegion(DesignDataRegionDefine srcRegion, String newFormKey) throws Exception {
        DesignRegionSettingDefine regionSetting;
        DesignDataRegionDefineImpl newRegion = new DesignDataRegionDefineImpl();
        BeanUtils.copyProperties(srcRegion, newRegion);
        newRegion.setKey(UUIDUtils.getKey());
        newRegion.setUpdateTime(new Date());
        newRegion.setOrder(OrderGenerator.newOrder());
        newRegion.setFormKey(newFormKey);
        if (null != srcRegion.getRegionSettingKey() && (regionSetting = this.designTimeViewController.getRegionSettingByRegion(srcRegion.getKey())) != null) {
            List entityDefaultValues;
            DesignRegionSettingDefineImpl newSetting = new DesignRegionSettingDefineImpl();
            BeanUtils.copyProperties(regionSetting, newSetting);
            if (CollectionUtils.isEmpty(newSetting.getEntityDefaultValue()) && !CollectionUtils.isEmpty(entityDefaultValues = regionSetting.getEntityDefaultValue())) {
                ObjectMapper mapper = new ObjectMapper();
                newSetting.setEntityDefaultValue(mapper.writeValueAsString((Object)entityDefaultValues));
            }
            newSetting.setKey(UUIDUtils.getKey());
            newRegion.setRegionSettingKey(newSetting.getKey());
            newSetting.setLastRowStyle(regionSetting.getLastRowStyles());
            this.designTimeViewController.insertRegionSetting((DesignRegionSettingDefine)newSetting);
        }
        DesignDataRegionDefine[] newRegions = new DesignDataRegionDefine[]{newRegion};
        this.designTimeViewController.insertDataRegion(newRegions);
        return newRegion;
    }

    @Override
    public List<DataFieldSettingDTO> listFieldSettingByTable(String tableKey) {
        return this.dataFieldService.listFieldsByTable(tableKey);
    }

    @Override
    public ProgressItem getProgress(String progressId) {
        ProgressItem progress = this.progressCacheService.getProgress(progressId);
        return progress;
    }

    @Override
    public Map<String, Boolean> checkTableData(List<String> tableKeys) {
        if (CollectionUtils.isEmpty(tableKeys)) {
            return Collections.emptyMap();
        }
        HashMap<String, Boolean> result = new HashMap<String, Boolean>(tableKeys.size());
        for (String tableKey : tableKeys) {
            result.put(tableKey, this.runtimeDataSchemeService.dataTableCheckData(new String[]{tableKey}));
        }
        return result;
    }

    @Override
    public void getValidationResult(String authorizeConfig, String designerAuthType, Map<String, Object> authInfo) {
        if (authorizeConfig.equals("true")) {
            authInfo.put(designerAuthType, true);
        } else {
            authInfo.put(designerAuthType, false);
        }
    }

    @Override
    public FindFieldVO findFieldByTableCodeAndFieldCodes(List<String> codes) {
        FindFieldVO res = new FindFieldVO();
        if (CollectionUtils.isEmpty(codes)) {
            return res;
        }
        HashMap<String, List> codesMap = new HashMap<String, List>(codes.size());
        for (String code : codes) {
            String[] strings = this.extractParts(code);
            codesMap.computeIfAbsent(strings[0], k -> new ArrayList()).add(strings[1]);
        }
        for (String tableCode : codesMap.keySet()) {
            DesignDataTable table = this.designDataSchemeService.getDataTableByCode(tableCode);
            res.addTable(TableBeanUtils.toDto(table));
            List fields = this.designDataSchemeService.getDataFieldByTableCode(tableCode);
            HashSet fieldCodeSet = new HashSet((Collection)codesMap.get(tableCode));
            fields.stream().filter(f -> fieldCodeSet.contains(f.getCode())).map(FieldBeanUtils::toSettingDto).forEach(f -> res.addField(tableCode, (DataFieldSettingDTO)f));
        }
        return res;
    }

    private String[] extractParts(String str) {
        int start = str.indexOf(91);
        int end = str.indexOf(93);
        if (start != -1 && end != -1 && start < end) {
            return new String[]{str.substring(0, start), str.substring(start + 1, end)};
        }
        throw new FormRuntimeException(String.format("%s\u683c\u5f0f\u9519\u8bef", str));
    }

    @Override
    public List<CheckFieldCodeBatchVO> checkFieldCodes(List<CheckFieldCodeBatchDTO> fieldCodeBatchList) {
        if (CollectionUtils.isEmpty(fieldCodeBatchList)) {
            return Collections.emptyList();
        }
        String dataSchemeKey = fieldCodeBatchList.get(0).getDataSchemeKey();
        Map<String, List<CheckFieldCodeBatchDTO>> fieldsInTable = fieldCodeBatchList.stream().collect(Collectors.groupingBy(CheckFieldCodeBatchDTO::getTableKey));
        List dataTables = this.designDataSchemeService.getDataTables(new ArrayList<String>(fieldsInTable.keySet()));
        Map<String, DesignDataTable> tableMap = dataTables.stream().collect(Collectors.toMap(Basic::getKey, f -> f));
        HashMap<String, Map<String, String>> floatFieldCodeMap = new HashMap<String, Map<String, String>>(tableMap.size());
        Map<String, String> fixFieldCodeMap = null;
        ArrayList<CheckFieldCodeBatchVO> res = new ArrayList<CheckFieldCodeBatchVO>(fieldCodeBatchList.size());
        for (Map.Entry<String, List<CheckFieldCodeBatchDTO>> entry : fieldsInTable.entrySet()) {
            String tableKey = entry.getKey();
            Map originFields = Collections.emptyMap();
            List<CheckFieldCodeBatchDTO> fieldDataList = entry.getValue();
            Optional first = entry.getValue().stream().findFirst();
            try {
                CheckFieldCodeBatchDTO fieldData;
                if (tableMap.containsKey(tableKey)) {
                    DesignDataTable dataTable = tableMap.get(tableKey);
                    if (dataTable.getDataTableType() == DataTableType.TABLE) {
                        if (fixFieldCodeMap == null) {
                            fixFieldCodeMap = this.designDataSchemeService.getAllDataFieldByKind(dataSchemeKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB}).stream().collect(Collectors.toMap(Basic::getCode, Basic::getKey));
                        }
                        originFields = fixFieldCodeMap;
                    } else {
                        if (!floatFieldCodeMap.containsKey(tableKey)) {
                            floatFieldCodeMap.put(tableKey, this.designDataSchemeService.getDataFieldByTable(dataSchemeKey).stream().collect(Collectors.toMap(Basic::getCode, Basic::getKey)));
                        }
                        originFields = (Map)floatFieldCodeMap.get(tableKey);
                    }
                } else if (first.isPresent() && (fieldData = (CheckFieldCodeBatchDTO)first.get()).getTableType().intValue() == DataTableType.TABLE.getValue()) {
                    if (fixFieldCodeMap == null) {
                        fixFieldCodeMap = this.designDataSchemeService.getAllDataFieldByKind(dataSchemeKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB}).stream().collect(Collectors.toMap(Basic::getCode, Basic::getKey));
                    }
                    originFields = fixFieldCodeMap;
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u6570\u636e\u5931\u8d25", e);
            }
            if (originFields.isEmpty()) {
                HashSet<String> codes = new HashSet<String>(fieldDataList.size());
                for (CheckFieldCodeBatchDTO fieldData : fieldDataList) {
                    if (codes.contains(fieldData.getFieldCode())) {
                        res.add(new CheckFieldCodeBatchVO(fieldData, false, "\u6307\u6807\u6807\u8bc6\u91cd\u590d"));
                        continue;
                    }
                    codes.add(fieldData.getFieldCode());
                }
                continue;
            }
            for (CheckFieldCodeBatchDTO fieldData : fieldDataList) {
                if (!originFields.containsKey(fieldData.getFieldCode()) || ((String)originFields.get(fieldData.getFieldCode())).equals(fieldData.getFieldKey())) continue;
                res.add(new CheckFieldCodeBatchVO(fieldData, false, "\u6307\u6807\u6807\u8bc6\u91cd\u590d"));
            }
        }
        return res;
    }

    @Override
    public List<UITreeNode<FormUITreeNode>> locateSelectFormTree(String formSchemeKey, String formKey) {
        ArrayList nodeList = new ArrayList();
        this.formGroupService.queryRoot(formSchemeKey).forEach(formGroupDefine -> {
            FormUITreeNode data = new FormUITreeNode();
            data.setTitle(formGroupDefine.getTitle());
            data.setKey(formGroupDefine.getKey());
            data.setType(FormUITreeNode.NodeType.FORMGROUP);
            UITreeNode node = new UITreeNode((TreeData)data);
            node.setIcon("icon-folder");
            nodeList.add(node);
            List<FormItemDTO> formItemDTOS = this.formDefineService.queryFormByGroup(formGroupDefine.getKey());
            if (CollectionUtils.isEmpty(formItemDTOS)) {
                node.setDisabled(true);
            }
            HashSet<FormType> formTypeSet = new HashSet<FormType>(Arrays.asList(FormType.FORM_TYPE_FIX, FormType.FORM_TYPE_FLOAT, FormType.FORM_TYPE_ACCOUNT, FormType.FORM_TYPE_NEWFMDM));
            formItemDTOS.stream().filter(f -> formTypeSet.contains(f.getType())).forEach(formDefine -> {
                FormUITreeNode formData = new FormUITreeNode();
                formData.setTitle(formDefine.getTitle());
                formData.setKey(formDefine.getKey());
                formData.setCode(formDefine.getCode());
                formData.setParentId(formGroupDefine.getKey());
                formData.setType(FormUITreeNode.NodeType.FORM);
                UITreeNode formNode = new UITreeNode((TreeData)formData);
                formNode.setIcon(this.getIconByFormType(formDefine.getType()));
                nodeList.add(formNode);
            });
        });
        UITreeBuilder builder = new UITreeBuilder(new TreeConfig().selected(new String[]{formKey}));
        builder.add(nodeList);
        return builder.build();
    }

    private String getIconByFormType(FormType type) {
        return FORM_TYPE_ICON_MAP.get(type);
    }

    @Override
    public RefEntityLinkConfigDTO getLinkRefEntityConfig(String entityID) {
        RefEntityLinkConfigDTO settingDTO = new RefEntityLinkConfigDTO();
        if (entityID == null) {
            return settingDTO;
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityID);
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityAttribute nameField = entityModel.getNameField();
        ArrayList<String> cellList = new ArrayList<String>();
        cellList.add(codeField.getCode());
        cellList.add(nameField.getCode());
        settingDTO.setCaptionFieldsString(String.join((CharSequence)";", cellList));
        ArrayList<String> dropList = new ArrayList<String>();
        dropList.add(codeField.getCode());
        dropList.add(nameField.getCode());
        settingDTO.setDropDownFieldsString(String.join((CharSequence)";", dropList));
        LinkedHashMap<String, Object> posMap = new LinkedHashMap<String, Object>();
        posMap.put(codeField.getCode(), "");
        posMap.put(nameField.getCode(), "");
        IEntityAttribute shortName = entityModel.getAttribute("shortname");
        posMap.put(shortName.getCode(), "");
        settingDTO.setEnumPosMap(posMap);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        TableModelDefine tableModel = periodAdapter.isPeriodEntity(entityID) ? periodAdapter.getPeriodEntityTableModel(entityID) : this.entityMetaService.getTableModel(entityID);
        String bizKeys = tableModel.getBizKeys();
        ColumnModelDefine columnModel = this.dataModelService.getColumnModelDefineByID(bizKeys);
        if (null != columnModel) {
            settingDTO.setEntitySize(columnModel.getPrecision());
        } else {
            settingDTO.setEntitySize(60);
        }
        return settingDTO;
    }

    @Override
    public Boolean isPrintTemplateNeedChange(FormSaveContext context) {
        if (this.formSaveSettingProvider != null) {
            return this.formSaveSettingProvider.needChange(context);
        }
        return false;
    }

    static {
        FORM_TYPE_ICON_MAP.put(FormType.FORM_TYPE_FIX, "icon-J_GJ_A_NR_gudingbiao");
        FORM_TYPE_ICON_MAP.put(FormType.FORM_TYPE_FLOAT, "icon-J_GJ_A_NR_fudongbiao");
        FORM_TYPE_ICON_MAP.put(FormType.FORM_TYPE_ACCOUNT, "icon-J_GJ_A_NR_taizhang");
        FORM_TYPE_ICON_MAP.put(FormType.FORM_TYPE_SURVEY, "icon-J_GJ_A_NR_wenjuan");
        FORM_TYPE_ICON_MAP.put(FormType.FORM_TYPE_FMDM, "icon-J_GJ_A_NR_fengmiandaima");
        FORM_TYPE_ICON_MAP.put(FormType.FORM_TYPE_NEWFMDM, "icon-J_GJ_A_NR_fengmiandaima");
        FORM_TYPE_ICON_MAP.put(FormType.FORM_TYPE_INSERTANALYSIS, "icon-J_GJ_A_NR_fenxibiao");
    }
}

