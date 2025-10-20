/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle
 *  com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.MetaComparator
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormFoldingDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.Data
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignDataLinkMappingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormFoldingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl
 *  com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.service.DesignBigDataService
 *  com.jiuqi.nr.definition.internal.service.DesignFormFoldingService
 *  com.jiuqi.nr.definition.internal.service.TaskOrgLinkService
 *  com.jiuqi.nr.definition.service.IDesignFormSchemeService
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.nr.designer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.conditionalstyle.facade.impl.DesignConditionalStyleImpl;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.MetaComparator;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintSettingDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.formulamapping.facade.Data;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataLinkMappingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignDataRegionDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormFoldingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormulaDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignPrintTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignRegionSettingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.impl.ProgressLoadingImpl;
import com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.service.IDesignFormSchemeService;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.DimsLog;
import com.jiuqi.nr.designer.common.Grid2DataSeralizeToGeGe;
import com.jiuqi.nr.designer.common.IDesignerEntityUpgrader;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.RegionSurveyHelper;
import com.jiuqi.nr.designer.helper.SaveSchemePeriodHelper;
import com.jiuqi.nr.designer.service.IDesignRestService;
import com.jiuqi.nr.designer.service.ITaskObjService;
import com.jiuqi.nr.designer.service.impl.ProgressLoadingServiceImpl;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.util.SchemePeriodObj;
import com.jiuqi.nr.designer.web.facade.EntityObj;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.FormCopyObj;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import com.jiuqi.nr.designer.web.facade.PreloadData;
import com.jiuqi.nr.designer.web.facade.ProgressInfos;
import com.jiuqi.nr.designer.web.facade.RootJson;
import com.jiuqi.nr.designer.web.facade.SaveEntityVO;
import com.jiuqi.nr.designer.web.facade.TaskObj;
import com.jiuqi.nr.designer.web.facade.simple.SimpleFormGroupObj;
import com.jiuqi.nr.designer.web.facade.simple.SimpleFormObj;
import com.jiuqi.nr.designer.web.rest.RegionSurveyController;
import com.jiuqi.nr.designer.web.rest.vo.CheckTaskTitleAvailable;
import com.jiuqi.nr.designer.web.rest.vo.FormTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.ParamToDesigner;
import com.jiuqi.nr.designer.web.rest.vo.PrintAttributeVo;
import com.jiuqi.nr.designer.web.rest.vo.TaskSchemeGroupTreeNode;
import com.jiuqi.nr.designer.web.rest.vo.TaskTreeNode;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import com.jiuqi.nr.designer.web.treebean.FormulaSchemeObject;
import com.jiuqi.nr.designer.web.treebean.PrintSchemeObject;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DesignRestService
implements IDesignRestService {
    private static final Logger log = LoggerFactory.getLogger(DesignRestService.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private ProgressLoadingServiceImpl progressLoadingServiceImpl;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    @Autowired
    private ITaskObjService taskObjService;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private IDesignerEntityUpgrader designerEntityUpgrader;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DesignTimeViewController designTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private SaveSchemePeriodHelper saveSchemePeriodHelper;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDesignFormSchemeService iFormSchemeService;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IDesignConditionalStyleController conditionalStyleController;
    @Autowired
    private IFormulaDesignTimeController iFormulaDesignTimeController;
    @Autowired
    private DesignBigDataService designBigDataService;
    @Autowired
    private IPrintDesignTimeController iPrintDesignTimeController;
    @Autowired
    private DesignFormFoldingService formFoldingService;
    @Autowired
    private RegionSurveyHelper regionSurveyHelper;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;
    @Autowired
    RegionSurveyController regionSurveyController;

    @Override
    public String getFormData(ParamToDesigner paramToDesigner) throws Exception {
        String taskId = paramToDesigner.getActivedTaskId();
        String activedSchemeId = paramToDesigner.getActivedSchemeId();
        PreloadData preloadData = new PreloadData();
        boolean taskParamCheck = this.taskParamCheck(paramToDesigner);
        preloadData.setOwnerLevelAndId(this.serveCodeService.getServeCode());
        preloadData.setIsPublishTask(this.taskObjService.taskIsPublish(taskId));
        preloadData.setProgressInfos(this.getProgressInfo(taskId));
        DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskId);
        TaskObj taskObj = this.initParamObjPropertyUtil.setTaskObjProperty(designTaskDefine);
        taskObj.setTaskParamStatus(taskParamCheck);
        preloadData.setTaskObj(taskObj);
        LinkedHashMap<String, FormSchemeObj> formScheme = new LinkedHashMap<String, FormSchemeObj>();
        List designFormSchemeDefineList = this.nrDesignTimeController.queryFormSchemeByTask(taskId);
        designFormSchemeDefineList = Optional.ofNullable(designFormSchemeDefineList).orElse(Collections.emptyList()).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        if (designFormSchemeDefineList.size() == 0) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_001);
        }
        for (int si = 0; si < designFormSchemeDefineList.size(); ++si) {
            DesignFormSchemeDefine designFormSchemeDefine = (DesignFormSchemeDefine)designFormSchemeDefineList.get(si);
            if (StringUtils.isEmpty((String)activedSchemeId)) {
                activedSchemeId = designFormSchemeDefine.getKey();
            }
            boolean isActivedScheme = activedSchemeId.equalsIgnoreCase(designFormSchemeDefine.getKey());
            FormSchemeObj formSchemeObj = this.initParamObjPropertyUtil.setFormSchemeObjProperty(designFormSchemeDefine, designTaskDefine);
            formScheme.put(designFormSchemeDefine.getKey(), formSchemeObj);
            preloadData.setActivedSchemeId(activedSchemeId);
            if (!isActivedScheme) continue;
            this.fixFormGroupsAndForms(designTaskDefine, designFormSchemeDefine, paramToDesigner, preloadData);
        }
        preloadData.setFormSchemes(formScheme);
        preloadData.setFormulaSchemes(this.getFormulaSchemeByFromScheme(preloadData.getActivedSchemeId()));
        preloadData.setActivedFormulaId(this.getDefaultFormulaSchemeKey(preloadData.getActivedSchemeId()));
        preloadData.setPrintSchemes(this.getPrintSchemeByFormScheme(taskId, preloadData.getActivedSchemeId()));
        RootJson root = new RootJson();
        root.setPreloadData(preloadData);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSeralizeToGeGe());
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)root);
    }

    @Override
    public boolean taskParamCheck(@RequestBody ParamToDesigner paramToDesigner) throws Exception {
        boolean isUpdate;
        DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(paramToDesigner.getActivedTaskId());
        List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(designTaskDefine.getDataScheme());
        dataSchemeDimension.removeIf(x -> AdjustUtils.isAdjust((String)x.getDimKey()));
        List taskOrgLinks = this.taskOrgLinkService.getByTask(designTaskDefine.getKey());
        boolean isAutoUpdate = true;
        boolean unitIsUpdate = false;
        boolean periodIsUpdate = false;
        boolean dimsIsUpdate = false;
        ArrayList<DesignDataDimension> scopeDimension = new ArrayList<DesignDataDimension>();
        ArrayList<DesignDataDimension> rightScopeDimension = new ArrayList<DesignDataDimension>();
        DesignDataDimension periodDimension = null;
        DesignDataDimension unitDimension = null;
        ArrayList<DesignDataDimension> dimsDimension = new ArrayList<DesignDataDimension>();
        DesignDataDimension newScopeDimension = null;
        boolean isUnitScope = false;
        for (DesignDataDimension dimension : dataSchemeDimension) {
            switch (dimension.getDimensionType()) {
                case PERIOD: {
                    periodDimension = dimension;
                    break;
                }
                case UNIT: {
                    unitDimension = dimension;
                    break;
                }
                case UNIT_SCOPE: {
                    isUnitScope = true;
                    scopeDimension.add(dimension);
                    if (!dimension.getDimKey().equals(paramToDesigner.getDimKey())) break;
                    newScopeDimension = dimension;
                    break;
                }
                case DIMENSION: {
                    dimsDimension.add(dimension);
                }
            }
        }
        Assert.notNull(unitDimension, "unitDimension must not be null.");
        Assert.notNull(periodDimension, "periodDimension must not be null.");
        List taskUnitTypes = taskOrgLinks.stream().map(TaskOrgLinkDefine::getEntity).collect(Collectors.toList());
        Map<String, DesignDataDimension> dataschemeUnitMap = scopeDimension.stream().collect(Collectors.toMap(DataDimension::getDimKey, v -> v));
        if (isUnitScope) {
            boolean scopeIsHave = false;
            for (String string : dataschemeUnitMap.keySet()) {
                if (!taskUnitTypes.contains(string)) continue;
                rightScopeDimension.add(dataschemeUnitMap.get(string));
            }
            if (taskUnitTypes.size() == rightScopeDimension.size()) {
                scopeIsHave = true;
            } else if (rightScopeDimension.size() != 0 && taskUnitTypes.size() > rightScopeDimension.size()) {
                scopeIsHave = true;
                unitIsUpdate = true;
            }
            if (!scopeIsHave) {
                unitIsUpdate = true;
                isAutoUpdate = false;
            }
        } else if (taskUnitTypes.size() > 1 || !unitDimension.getDimKey().equals(taskUnitTypes.get(0))) {
            rightScopeDimension.add(unitDimension);
            unitIsUpdate = true;
        }
        if (!PeriodUtils.removeSuffix((String)periodDimension.getDimKey()).equals(designTaskDefine.getDateTime())) {
            periodIsUpdate = true;
        }
        if (StringUtils.isEmpty((String)designTaskDefine.getDims())) {
            if (dimsDimension.size() != 0) {
                dimsIsUpdate = true;
            }
        } else {
            String[] split = designTaskDefine.getDims().split(";");
            if (split.length != dimsDimension.size()) {
                dimsIsUpdate = true;
            } else {
                HashMap<String, String> map = new HashMap<String, String>();
                for (String viewKey : split) {
                    if (!"ADJUST".equals(viewKey)) {
                        IEntityDefine entity = this.iEntityMetaService.queryEntity(viewKey);
                        if (!Objects.nonNull(entity)) continue;
                        map.put(entity.getId(), viewKey);
                        continue;
                    }
                    List formSchemeDefines = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
                    if (null == formSchemeDefines || formSchemeDefines.size() == 0) {
                        throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_001);
                    }
                    Boolean aBoolean = this.iFormSchemeService.enableAdjustPeriod(((DesignFormSchemeDefine)formSchemeDefines.get(0)).getKey());
                    if (aBoolean.booleanValue()) {
                        map.put("ADJUST", "ADJUST");
                        continue;
                    }
                    dimsIsUpdate = true;
                }
                for (DesignDataDimension dim : dimsDimension) {
                    if (null != map.get(dim.getDimKey())) continue;
                    dimsIsUpdate = true;
                }
            }
        }
        boolean bl = isUpdate = unitIsUpdate || periodIsUpdate || dimsIsUpdate;
        if (isUpdate && isAutoUpdate) {
            if (unitIsUpdate) {
                this.printLog(designTaskDefine.getKey(), DimsLog.ENTITY, designTaskDefine.getDw(), unitDimension.getDimKey());
                this.updateDw(designTaskDefine.getKey(), rightScopeDimension, false);
            }
            if (periodIsUpdate) {
                this.printLog(designTaskDefine.getKey(), DimsLog.PERIOD, designTaskDefine.getDateTime(), periodDimension.getDimKey());
                this.updateDateTime(designTaskDefine.getKey(), periodDimension);
            }
            if (dimsIsUpdate) {
                this.printLog(designTaskDefine.getKey(), DimsLog.DIM, designTaskDefine.getDims(), dimsDimension.stream().map(t -> t.getDimKey()).collect(Collectors.toList()).stream().collect(Collectors.joining(";")));
                this.updateDims(designTaskDefine.getKey(), dimsDimension);
            }
            this.updateFlowDefine(designTaskDefine.getKey());
        } else if (isUpdate && !isAutoUpdate) {
            if (!CollectionUtils.isEmpty(paramToDesigner.getOrgList())) {
                for (String string : paramToDesigner.getOrgList()) {
                    rightScopeDimension.add(dataschemeUnitMap.get(string));
                }
            }
            if (!CollectionUtils.isEmpty(rightScopeDimension)) {
                if (unitIsUpdate) {
                    this.printLog(designTaskDefine.getKey(), DimsLog.ENTITY, designTaskDefine.getDw(), newScopeDimension.getDimKey());
                    this.updateDw(designTaskDefine.getKey(), rightScopeDimension, true);
                }
                if (periodIsUpdate) {
                    this.printLog(designTaskDefine.getKey(), DimsLog.PERIOD, designTaskDefine.getDateTime(), periodDimension.getDimKey());
                    this.updateDateTime(designTaskDefine.getKey(), periodDimension);
                }
                if (dimsIsUpdate) {
                    this.printLog(designTaskDefine.getKey(), DimsLog.DIM, designTaskDefine.getDims(), dimsDimension.stream().map(t -> t.getDimKey()).collect(Collectors.toList()).stream().collect(Collectors.joining(";")));
                    this.updateDims(designTaskDefine.getKey(), dimsDimension);
                }
                this.updateFlowDefine(designTaskDefine.getKey());
                isAutoUpdate = true;
            }
        }
        return isAutoUpdate;
    }

    private void printLog(String taskKey, DimsLog type, String oldValue, String newValue) {
        StringBuffer sbf = new StringBuffer();
        switch (type) {
            case ENTITY: {
                sbf.append("\u4efb\u52a1Key:").append(taskKey).append("-\u4e3b\u7ef4\u5ea6\u89e6\u53d1\u81ea\u52a8\u8c03\u6574").append("\u65e7\u4e3b\u7ef4\u5ea6\u89c6\u56fe").append(oldValue).append("->").append("\u65b0\u4e3b\u7ef4\u5ea6").append(newValue);
                break;
            }
            case PERIOD: {
                sbf.append("\u4efb\u52a1Key:").append(taskKey).append("-\u65f6\u671f\u89e6\u53d1\u81ea\u52a8\u8c03\u6574").append("\u65e7\u65f6\u671f\u89c6\u56fe").append(oldValue).append("->").append("\u65b0\u65f6\u671f").append(newValue);
                break;
            }
            case DIM: {
                sbf.append("\u4efb\u52a1Key:").append(taskKey).append("-\u60c5\u666f\u89e6\u53d1\u81ea\u52a8\u8c03\u6574").append("\u65e7\u60c5\u666f\u89c6\u56fe").append(oldValue).append("->").append("\u65b0\u60c5\u666f").append(newValue);
                break;
            }
        }
        log.info(sbf.toString());
    }

    private void updateDw(String taskKey, List<DesignDataDimension> unitDimension, boolean needAdd) throws Exception {
        DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        List taskOrgLinks = this.taskOrgLinkService.getByTask(taskKey);
        if (!needAdd) {
            List rightUnits = unitDimension.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
            ArrayList<TaskOrgLinkDefine> needDeleteTaskOrgLink = new ArrayList<TaskOrgLinkDefine>();
            for (TaskOrgLinkDefine taskOrgLink : taskOrgLinks) {
                if (rightUnits.contains(taskOrgLink.getEntity())) continue;
                needDeleteTaskOrgLink.add(taskOrgLink);
            }
            taskOrgLinks.removeAll(needDeleteTaskOrgLink);
            if (!taskOrgLinks.isEmpty()) {
                if (this.needUpdateTaskDw(designTaskDefine, taskOrgLinks)) {
                    designTaskDefine.setDw(((TaskOrgLinkDefine)taskOrgLinks.get(0)).getEntity());
                    this.designTimeViewController.updateTaskDefine(designTaskDefine);
                }
                this.updateTaskOrgLinkOnlyDelete(needDeleteTaskOrgLink);
                return;
            }
            designTaskDefine.setDw(unitDimension.get(0).getDimKey());
            this.designTimeViewController.updateTaskDefine(designTaskDefine);
            this.updateTaskOrgLink(taskKey, Collections.singletonList(unitDimension.get(0)));
            return;
        }
        List<TaskOrgLinkDefine> taskLinkDefines = this.updateTaskOrgLink(taskKey, unitDimension);
        designTaskDefine.setDw(taskLinkDefines.get(0).getEntity());
        this.designTimeViewController.updateTaskDefine(designTaskDefine);
        List formSchemes = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
        for (DesignFormSchemeDefine scheme : formSchemes) {
            if (null == scheme.getDw()) continue;
            scheme.setDw(null);
            this.designTimeViewController.updateFormSchemeDefine(scheme);
        }
    }

    private boolean needUpdateTaskDw(DesignTaskDefine taskDefine, List<TaskOrgLinkDefine> taskLinkDefines) {
        Set taskOrg = taskLinkDefines.stream().map(TaskOrgLinkDefine::getEntity).collect(Collectors.toSet());
        return !taskOrg.contains(taskDefine.getDw());
    }

    private List<TaskOrgLinkDefine> updateTaskOrgLink(String taskKey, List<DesignDataDimension> rightTaskUnits) {
        ArrayList<TaskOrgLinkDefine> taskLinkOrgs = new ArrayList<TaskOrgLinkDefine>();
        try {
            if (CollectionUtils.isEmpty(rightTaskUnits)) {
                return taskLinkOrgs;
            }
            this.taskOrgLinkService.deleteTaskOrgLinkByTask(taskKey);
            for (DesignDataDimension dimension : rightTaskUnits) {
                TaskOrgLinkDefineImpl orgLinkDefine = new TaskOrgLinkDefineImpl();
                orgLinkDefine.setKey(UUIDUtils.getKey());
                orgLinkDefine.setTask(taskKey);
                orgLinkDefine.setEntity(dimension.getDimKey());
                orgLinkDefine.setEntityAlias(null);
                orgLinkDefine.setOrder(OrderGenerator.newOrder());
                taskLinkOrgs.add((TaskOrgLinkDefine)orgLinkDefine);
            }
            if (!CollectionUtils.isEmpty(taskLinkOrgs)) {
                this.taskOrgLinkService.insertTaskOrgLink(taskLinkOrgs.toArray(new TaskOrgLinkDefine[taskLinkOrgs.size()]));
            }
            return taskLinkOrgs;
        }
        catch (DBParaException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTaskOrgLinkOnlyDelete(List<TaskOrgLinkDefine> needDeleteTaskOrgLink) {
        try {
            if (!CollectionUtils.isEmpty(needDeleteTaskOrgLink)) {
                List<String> needDeleteKeys = needDeleteTaskOrgLink.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                this.taskOrgLinkService.deleteTaskOrgLink(needDeleteKeys.toArray(new String[needDeleteKeys.size()]));
            }
        }
        catch (DBParaException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDateTime(String taskKey, DesignDataDimension periodDimension) throws Exception {
        DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        String[] periodCodeRegion = periodAdapter.getPeriodProvider(periodDimension.getDimKey()).getPeriodCodeRegion();
        designTaskDefine.setDateTime(PeriodUtils.removeSuffix((String)periodDimension.getDimKey()));
        designTaskDefine.setFromPeriod(periodCodeRegion[0]);
        designTaskDefine.setToPeriod(periodCodeRegion[1]);
        this.designTimeViewController.updateTaskDefine(designTaskDefine);
        List formSchemes = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
        ArrayList<SchemePeriodObj> objs = new ArrayList<SchemePeriodObj>();
        for (DesignFormSchemeDefine scheme : formSchemes) {
            if (null != scheme.getDateTime()) {
                scheme.setDateTime(null);
                this.designTimeViewController.updateFormSchemeDefine(scheme);
            }
            SchemePeriodObj obj = new SchemePeriodObj();
            if (!scheme.getKey().equals(((DesignFormSchemeDefine)formSchemes.get(0)).getKey())) continue;
            obj.setScheme(scheme.getKey());
            obj.setStart(designTaskDefine.getFromPeriod());
            obj.setEnd(designTaskDefine.getToPeriod());
            objs.add(obj);
        }
        this.saveSchemePeriodHelper.saveSchemePeriodLinks(objs);
    }

    private void updateDims(String taskKey, List<DesignDataDimension> dimsDimension) throws Exception {
        block6: {
            DesignTaskDefine designTaskDefine;
            block5: {
                designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
                if (!StringUtils.isEmpty((String)designTaskDefine.getDims())) break block5;
                if (dimsDimension.size() == 0) break block6;
                ArrayList<String> defaultViews = new ArrayList<String>();
                for (DesignDataDimension ddd : dimsDimension) {
                    defaultViews.add(ddd.getDimKey());
                }
                designTaskDefine.setDims(defaultViews.stream().collect(Collectors.joining(";")));
                this.designTimeViewController.updateTaskDefine(designTaskDefine);
                break block6;
            }
            if (dimsDimension.size() == 0) {
                designTaskDefine.setDims(null);
                this.designTimeViewController.updateTaskDefine(designTaskDefine);
                List formSchemes = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
                for (DesignFormSchemeDefine scheme : formSchemes) {
                    if (null == scheme.getDims()) continue;
                    scheme.setDims(null);
                    this.designTimeViewController.updateFormSchemeDefine(scheme);
                }
            } else {
                designTaskDefine.setDims(this.fixCheckViewKeys(designTaskDefine.getDims(), dimsDimension));
                this.designTimeViewController.updateTaskDefine(designTaskDefine);
                List formSchemes = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
                for (DesignFormSchemeDefine scheme : formSchemes) {
                    if (null == scheme.getDims()) continue;
                    scheme.setDims(this.fixCheckViewKeys(scheme.getDims(), dimsDimension));
                    this.designTimeViewController.updateFormSchemeDefine(scheme);
                }
            }
        }
    }

    private String fixCheckViewKeys(String dims, List<DesignDataDimension> dimsDimension) throws Exception {
        return dimsDimension.stream().map(DataDimension::getDimKey).collect(Collectors.toList()).stream().collect(Collectors.joining(";"));
    }

    private void updateFlowDefine(String taskKey) throws Exception {
        DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        DesignTaskFlowsDefine taskFlows = (DesignTaskFlowsDefine)designTaskDefine.getFlowsSetting();
        String designTableDefines = taskFlows.getDesignTableDefines();
        if (StringUtils.isNotEmpty((String)designTableDefines)) {
            String[] split = designTableDefines.split(";");
            HashMap<String, String> map = new HashMap<String, String>();
            for (String defview : split) {
                if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(defview)) {
                    IPeriodEntity iPeriodByViewKey = this.periodEngineService.getPeriodAdapter().getPeriodEntity(defview);
                    map.put(iPeriodByViewKey.getKey(), PeriodUtils.removeSuffix((String)defview));
                    continue;
                }
                IEntityDefine entity = this.iEntityMetaService.queryEntity(defview);
                map.put(entity.getId(), defview);
            }
            ArrayList<String> newViews = new ArrayList<String>();
            IEntityDefine entityByViewKey = this.iEntityMetaService.queryEntity(designTaskDefine.getDw());
            if (null == map.get(entityByViewKey.getId())) {
                newViews.add(entityByViewKey.getId());
            } else {
                newViews.add((String)map.get(entityByViewKey.getId()));
            }
            IPeriodEntity iPeriodByViewKey = this.periodEngineService.getPeriodAdapter().getPeriodEntity(designTaskDefine.getDateTime());
            if (null == map.get(iPeriodByViewKey.getKey())) {
                newViews.add(designTaskDefine.getDateTime());
            } else {
                newViews.add((String)map.get(iPeriodByViewKey.getKey()));
            }
            if (StringUtils.isNotEmpty((String)designTaskDefine.getDims())) {
                String[] split2;
                for (String dim : split2 = designTaskDefine.getDims().split(";")) {
                    IEntityDefine entityByViewKey2 = this.iEntityMetaService.queryEntity(dim);
                    if (!Objects.nonNull(entityByViewKey2) || !Objects.nonNull(map.get(entityByViewKey2.getId()))) continue;
                    newViews.add((String)map.get(entityByViewKey2.getId()));
                }
            }
            taskFlows.setDesignTableDefines(newViews.stream().collect(Collectors.joining(";")));
        } else {
            ArrayList<String> newViews = new ArrayList<String>();
            IEntityDefine entityByViewKey = this.iEntityMetaService.queryEntity(designTaskDefine.getDw());
            newViews.add(entityByViewKey.getId());
            newViews.add(designTaskDefine.getDateTime());
            taskFlows.setDesignTableDefines(newViews.stream().collect(Collectors.joining(";")));
        }
        this.designTimeViewController.updateTaskDefine(designTaskDefine);
    }

    private void fixFormGroupsAndForms(DesignTaskDefine taskDefine, DesignFormSchemeDefine formSchemeDefine, ParamToDesigner paramToDesigner, PreloadData preloadData) throws Exception {
        FormObj currentFormObj;
        FormGroupObject currentFormGroupObj;
        ArrayList<SimpleFormGroupObj> simpleFormGroupObjs;
        if (taskDefine != null && StringUtils.isNotEmpty((String)taskDefine.getKey()) && formSchemeDefine != null && StringUtils.isNotEmpty((String)formSchemeDefine.getKey()) && paramToDesigner != null && preloadData != null) {
            String formSchemeKey = formSchemeDefine.getKey();
            simpleFormGroupObjs = new ArrayList<SimpleFormGroupObj>();
            currentFormGroupObj = null;
            currentFormObj = null;
            List allGroupsInFormScheme = this.nrDesignTimeController.queryAllGroupsByFormScheme(formSchemeDefine.getKey());
            List allGroupKeys = allGroupsInFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            List formGroupLinks = this.nrDesignTimeController.getFormGroupLinks(allGroupKeys);
            List allFormKeys = formGroupLinks.stream().map(DesignFormGroupLink::getFormKey).collect(Collectors.toList());
            List formDefines = this.nrDesignTimeController.getSimpleFormDefines(allFormKeys);
            Map<String, List<DesignFormGroupLink>> groupToFormsMap = formGroupLinks.stream().collect(Collectors.groupingBy(DesignFormGroupLink::getGroupKey));
            Map<String, List<DesignFormGroupLink>> formToGroupMap = formGroupLinks.stream().collect(Collectors.groupingBy(DesignFormGroupLink::getFormKey));
            List formGroupDefines = this.nrDesignTimeController.queryRootGroupsByFormScheme(formSchemeKey);
            if (formGroupDefines != null) {
                String currentFormGroupKey = paramToDesigner.getActivedGroupId();
                String currentFormKey = paramToDesigner.getActivedFormId();
                boolean hasFindedCurrentFormGroupObj = false;
                boolean hasFindedCurrentFormObj = false;
                String firstFormGroupKey = null;
                String firstFormKey = null;
                for (int i = 0; i < formGroupDefines.size(); ++i) {
                    List designFormDefineList;
                    List<DesignFormGroupLink> groupLinks;
                    DesignFormGroupDefine formGroupDefine = (DesignFormGroupDefine)formGroupDefines.get(i);
                    SimpleFormGroupObj simpleFormGroupObj = new SimpleFormGroupObj(formGroupDefine);
                    String formGroupKey = formGroupDefine.getKey();
                    if (i == 0) {
                        firstFormGroupKey = formGroupKey;
                    }
                    if (!hasFindedCurrentFormGroupObj) {
                        if (StringUtils.isNotEmpty((String)currentFormGroupKey)) {
                            if (currentFormGroupKey.equals(formGroupKey)) {
                                preloadData.setActivedGroupId(currentFormGroupKey);
                                currentFormGroupObj = this.initParamObjPropertyUtil.transFormGroupObject(taskDefine, formGroupDefine);
                                hasFindedCurrentFormGroupObj = true;
                            }
                        } else {
                            currentFormGroupKey = formGroupKey;
                            preloadData.setActivedGroupId(currentFormGroupKey);
                            currentFormGroupObj = this.initParamObjPropertyUtil.transFormGroupObject(taskDefine, formGroupDefine);
                            hasFindedCurrentFormGroupObj = true;
                        }
                    }
                    if ((groupLinks = groupToFormsMap.get(formGroupKey)) != null) {
                        Map<String, String> formToOrderMap = groupLinks.stream().collect(Collectors.toMap(DesignFormGroupLink::getFormKey, DesignFormGroupLink::getFormOrder));
                        designFormDefineList = formDefines.stream().filter(e -> {
                            String order = (String)formToOrderMap.get(e.getKey());
                            if (order != null) {
                                e.setOrder(order);
                                return true;
                            }
                            return false;
                        }).sorted((o1, o2) -> o1.getOrder().compareTo(o2.getOrder())).collect(Collectors.toList());
                    } else {
                        designFormDefineList = this.nrDesignTimeController.getAllFormsInGroupLazy(formGroupKey, false);
                    }
                    ArrayList<SimpleFormObj> simpleFormObjs = new ArrayList<SimpleFormObj>();
                    if (designFormDefineList != null) {
                        for (int j = 0; j < designFormDefineList.size(); ++j) {
                            DesignFormDefine formDefine = (DesignFormDefine)designFormDefineList.get(j);
                            List<String> ownGroupKeys = formToGroupMap.get(formDefine.getKey()).stream().map(e -> e.getGroupKey()).collect(Collectors.toList());
                            SimpleFormObj simpleFormObj = new SimpleFormObj(formGroupKey, formDefine);
                            String formKey = formDefine.getKey();
                            if (i == 0 && j == 0) {
                                firstFormKey = formKey;
                            }
                            simpleFormObjs.add(simpleFormObj);
                            if (hasFindedCurrentFormObj) continue;
                            if (StringUtils.isNotEmpty((String)currentFormKey)) {
                                if (!currentFormKey.equals(formKey)) continue;
                                preloadData.setActivedFormId(currentFormKey);
                                currentFormObj = this.initParamObjPropertyUtil.setFormObjPropertyCopy(taskDefine, formSchemeDefine, formGroupDefine, formDefine, true, ownGroupKeys);
                                hasFindedCurrentFormObj = true;
                                continue;
                            }
                            currentFormKey = formKey;
                            preloadData.setActivedFormId(currentFormKey);
                            currentFormObj = this.initParamObjPropertyUtil.setFormObjPropertyCopy(taskDefine, formSchemeDefine, formGroupDefine, formDefine, true, ownGroupKeys);
                            hasFindedCurrentFormObj = true;
                        }
                    }
                    simpleFormGroupObj.setChildren(simpleFormObjs);
                    simpleFormGroupObjs.add(simpleFormGroupObj);
                }
                if ((currentFormGroupObj == null || currentFormObj == null) && StringUtils.isNotEmpty(firstFormGroupKey)) {
                    DesignFormGroupDefine formGroupDefine = this.nrDesignTimeController.queryFormGroup(firstFormGroupKey);
                    currentFormGroupObj = this.initParamObjPropertyUtil.transFormGroupObject(taskDefine, formGroupDefine);
                    if (StringUtils.isNotEmpty(firstFormKey)) {
                        DesignFormDefine formDefine = this.nrDesignTimeController.queryFormById(firstFormKey);
                        List<String> ownGroupKeys = formToGroupMap.get(formDefine.getKey()).stream().map(e -> e.getGroupKey()).collect(Collectors.toList());
                        currentFormObj = this.initParamObjPropertyUtil.setFormObjPropertyCopy(taskDefine, formSchemeDefine, formGroupDefine, formDefine, true, ownGroupKeys);
                    }
                }
            }
        } else {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_174);
        }
        preloadData.setSimpleFormGroupObjs(simpleFormGroupObjs);
        preloadData.setCurrentFormGroupObj(currentFormGroupObj);
        preloadData.setCurrentFormObj(currentFormObj);
    }

    private ProgressInfos getProgressInfo(String taskID) throws Exception {
        ProgressLoadingImpl queryProgressLoading = this.progressLoadingServiceImpl.queryProgressLoading(taskID);
        ProgressInfos progressinfos = new ProgressInfos();
        if (queryProgressLoading != null) {
            progressinfos.setOperType(queryProgressLoading.getOperType());
            progressinfos.setOperStatus(queryProgressLoading.getOperStatus());
            progressinfos.setInfo(queryProgressLoading.getInfo());
            progressinfos.setNeedShow(queryProgressLoading.getNeedShow());
            progressinfos.setOperTime(queryProgressLoading.getOperTime());
            if (queryProgressLoading.getStackinfos() == null) {
                progressinfos.setStackInfos("");
            } else {
                progressinfos.setStackInfos(queryProgressLoading.getStackinfos().replaceAll("at ", "\n"));
            }
        }
        return progressinfos;
    }

    private Map<String, PrintSchemeObject> getPrintSchemeByFormScheme(String taskKey, String fromSchemeKey) throws Exception {
        HashMap<String, PrintSchemeObject> printSchemes = new HashMap<String, PrintSchemeObject>();
        List allPrintSchemeDefines = this.nrDesignTimeController.getAllPrintSchemeByFormScheme(fromSchemeKey);
        if (allPrintSchemeDefines != null && !allPrintSchemeDefines.isEmpty()) {
            for (int i = 0; i < allPrintSchemeDefines.size(); ++i) {
                DesignPrintTemplateSchemeDefine designPirntSchemeDefine = (DesignPrintTemplateSchemeDefine)allPrintSchemeDefines.get(i);
                PrintSchemeObject printSchemeObject = new PrintSchemeObject();
                printSchemeObject.setId(designPirntSchemeDefine.getKey().toString());
                printSchemeObject.setTitle(designPirntSchemeDefine.getTitle());
                printSchemeObject.setDefault(0 == i);
                if (designPirntSchemeDefine.getFormSchemeKey() != null) {
                    printSchemeObject.setFormSchemeKey(designPirntSchemeDefine.getFormSchemeKey().toString());
                }
                printSchemeObject.setNew(false);
                printSchemeObject.setDirty(false);
                printSchemeObject.setDeleted(false);
                printSchemeObject.setOwnerLevelAndId(designPirntSchemeDefine.getOwnerLevelAndId());
                printSchemeObject.setSameServeCode(this.serveCodeService.isSameServeCode(designPirntSchemeDefine.getOwnerLevelAndId()));
                printSchemes.put(printSchemeObject.getId(), printSchemeObject);
            }
        } else {
            PrintSchemeObject printSchemeObject = new PrintSchemeObject();
            printSchemeObject.setId(UUIDUtils.getKey());
            printSchemeObject.setTitle("\u9ed8\u8ba4\u6253\u5370\u65b9\u6848");
            printSchemeObject.setNew(false);
            printSchemeObject.setDirty(false);
            printSchemeObject.setDeleted(false);
            printSchemeObject.setDefault(true);
            printSchemeObject.setFormSchemeKey(fromSchemeKey.toString());
            printSchemes.put(printSchemeObject.getId(), printSchemeObject);
            DesignPrintTemplateSchemeDefine designPirntSchemeDefine = this.nrDesignTimeController.createPrintTemplateSchemeDefine();
            designPirntSchemeDefine.setKey(printSchemeObject.getId());
            designPirntSchemeDefine.setTitle(printSchemeObject.getTitle());
            designPirntSchemeDefine.setOrder(OrderGenerator.newOrder());
            designPirntSchemeDefine.setFormSchemeKey(fromSchemeKey);
            designPirntSchemeDefine.setTaskKey(taskKey);
            designPirntSchemeDefine.setOwnerLevelAndId(printSchemeObject.getOwnerLevelAndId());
            this.nrDesignTimeController.setPrintSchemeAttribute(designPirntSchemeDefine, PrintAttributeVo.defaultAttributeDefine());
            this.nrDesignTimeController.insertPrintTemplateSchemeDefine(designPirntSchemeDefine);
        }
        return printSchemes;
    }

    private Map<String, FormulaSchemeObject> getFormulaSchemeByFromScheme(String fromSchemeKey) throws JQException {
        HashMap<String, FormulaSchemeObject> formulaSchemes = new HashMap<String, FormulaSchemeObject>();
        List allFormulaSchemeDefines = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(fromSchemeKey);
        allFormulaSchemeDefines.sort(new MetaComparator());
        if (allFormulaSchemeDefines != null && !allFormulaSchemeDefines.isEmpty()) {
            for (DesignFormulaSchemeDefine designFormulaSchemeDefine : allFormulaSchemeDefines) {
                FormulaSchemeObject formulaSchemeObj = new FormulaSchemeObject();
                formulaSchemeObj.setID(designFormulaSchemeDefine.getKey().toString());
                formulaSchemeObj.setTitle(designFormulaSchemeDefine.getTitle());
                formulaSchemeObj.setIsDefault(designFormulaSchemeDefine.isDefault());
                formulaSchemeObj.setOwnerLevelAndId(designFormulaSchemeDefine.getOwnerLevelAndId());
                formulaSchemeObj.setSameServeCode(this.serveCodeService.isSameServeCode(designFormulaSchemeDefine.getOwnerLevelAndId()));
                if (designFormulaSchemeDefine.getFormulaSchemeType() != null) {
                    formulaSchemeObj.setFormulaSchemeType(designFormulaSchemeDefine.getFormulaSchemeType().getValue());
                }
                if (designFormulaSchemeDefine.getFormSchemeKey() != null) {
                    formulaSchemeObj.setFormSchemeKey(designFormulaSchemeDefine.getFormSchemeKey().toString());
                }
                formulaSchemeObj.setOrder(designFormulaSchemeDefine.getOrder());
                formulaSchemeObj.setIsNew(false);
                formulaSchemeObj.setIsDirty(false);
                formulaSchemeObj.setIsDeleted(false);
                formulaSchemeObj.setOwnerLevelAndId(designFormulaSchemeDefine.getOwnerLevelAndId());
                formulaSchemeObj.setSameServeCode(this.serveCodeService.isSameServeCode(designFormulaSchemeDefine.getOwnerLevelAndId()));
                formulaSchemes.put(formulaSchemeObj.getID(), formulaSchemeObj);
            }
        }
        return formulaSchemes;
    }

    private String getDefaultFormulaSchemeKey(String fromSchemeKey) {
        List allFormulaSchemeDefines = this.nrDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(fromSchemeKey);
        Optional<DesignFormulaSchemeDefine> firstScheme = allFormulaSchemeDefines.stream().filter(formulaSchemeDefine -> formulaSchemeDefine.getFormulaSchemeType() == FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT && formulaSchemeDefine.isDefault()).findFirst();
        if (firstScheme.isPresent()) {
            return firstScheme.get().getKey();
        }
        return "";
    }

    @Override
    public String queryNewTaskTitle() {
        String newFormTaskBaseTitle;
        int index = 0;
        String newFormTaskTitle = newFormTaskBaseTitle = "\u9ed8\u8ba4\u4efb\u52a1";
        List taskDefines = this.nrDesignTimeController.getAllTaskDefines();
        if (taskDefines != null && taskDefines.size() > 0) {
            boolean flag = true;
            while (flag) {
                int oldIndex = index;
                for (DesignTaskDefine taskDefine : taskDefines) {
                    String title = taskDefine.getTitle();
                    if (!newFormTaskTitle.equals(title)) continue;
                    newFormTaskTitle = newFormTaskBaseTitle + ++index;
                    break;
                }
                if (oldIndex != index) continue;
                break;
            }
        }
        return newFormTaskTitle;
    }

    @Override
    public boolean isExistTaskByTitle(CheckTaskTitleAvailable checkTaskTitleAvailable) throws Exception {
        String taskName = checkTaskTitleAvailable.getTaskTitle();
        String taskKey = checkTaskTitleAvailable.getTaskKey();
        boolean titleAvailable = this.nrDesignTimeController.checkTaskNameAvailable(taskKey, taskName);
        return !titleAvailable;
    }

    @Override
    public List<EntityTables> getFormSchemeEntity(String formSchemeKey, boolean needViews) throws JQException {
        String masterKey = "";
        try {
            masterKey = this.nrDesignTimeController.getFormSchemeEntity(formSchemeKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        List<EntityTables> entityTableList = null;
        if (!StringUtils.isEmpty((String)masterKey)) {
            entityTableList = this.returnEntityList(masterKey, needViews);
        }
        return entityTableList;
    }

    private List<EntityTables> returnEntityList(String masterKey, boolean needViews) throws JQException {
        return this.initParamObjPropertyUtil.returnEntityList(masterKey, needViews);
    }

    @Override
    public List<ITree<TaskTreeNode>> getTaskFormSchemes() throws JQException {
        ArrayList<ITree<TaskTreeNode>> tree_Task = new ArrayList<ITree<TaskTreeNode>>();
        List listDesignTask = this.nrDesignTimeController.getAllTaskDefines();
        if (listDesignTask != null) {
            listDesignTask.forEach(task -> tree_Task.add(this.getTreeNode((DesignTaskDefine)task)));
        }
        this.taskAddSchemeTree(tree_Task);
        return tree_Task;
    }

    @Override
    public List<ITree<TaskTreeNode>> getAllDefaultTaskSchemeItree() throws Exception {
        List tasks = this.nrDesignTimeController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        return Optional.ofNullable(tasks).orElse(Collections.emptyList()).stream().map(task -> this.getCompleteTaskNode((DesignTaskDefine)task)).collect(Collectors.toList());
    }

    private ITree<TaskTreeNode> getCompleteTaskNode(DesignTaskDefine task) {
        ITree<TaskTreeNode> taskNode = this.getTreeNode(task);
        String taskId = ((TaskTreeNode)taskNode.getData()).getKey();
        ArrayList schemeNodes = new ArrayList();
        List schemes = null;
        try {
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskId);
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
        }
        if (schemes != null) {
            schemes.forEach(scheme -> schemeNodes.add(this.getTreeNode((DesignFormSchemeDefine)scheme)));
        }
        taskNode.setChildren(schemeNodes);
        return taskNode;
    }

    private void taskAddSchemeTree(List<ITree<TaskTreeNode>> tree_Task) throws JQException {
        for (ITree<TaskTreeNode> taskTree : tree_Task) {
            ArrayList tree_Scheme = new ArrayList();
            List listFormSchemes = this.nrDesignTimeController.queryFormSchemeByTask(((TaskTreeNode)taskTree.getData()).getKey());
            if (listFormSchemes != null) {
                listFormSchemes.forEach(scheme -> tree_Scheme.add(this.getTreeNode((DesignFormSchemeDefine)scheme)));
            }
            taskTree.setChildren(tree_Scheme);
            if (listFormSchemes.size() != 1 || taskTree.getChildren().size() != 1) continue;
            taskTree.setLeaf(true);
        }
    }

    private ITree<TaskTreeNode> getTreeNode(DesignTaskDefine task) {
        ITree node = new ITree((INode)new TaskTreeNode(task));
        node.setLeaf(false);
        node.setNoDrop(true);
        return node;
    }

    private ITree<TaskTreeNode> getTreeNode(DesignFormSchemeDefine scheme) {
        ITree node = new ITree((INode)new TaskTreeNode(scheme));
        node.setLeaf(true);
        node.setNoDrop(true);
        return node;
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
    public List<TaskTreeNode> getSearchSchemeResult(String keyword) throws JQException {
        ArrayList<TaskTreeNode> tree = new ArrayList<TaskTreeNode>();
        List listDesignTask = this.nrDesignTimeController.getAllTaskDefines();
        if (listDesignTask != null) {
            for (DesignTaskDefine designTaskDefine : listDesignTask) {
                if (designTaskDefine.getTitle().contains(keyword)) {
                    tree.add(new TaskTreeNode(designTaskDefine));
                }
                List listFormSchemes = this.nrDesignTimeController.queryFormSchemeByTask(designTaskDefine.getKey());
                for (DesignFormSchemeDefine scheme : listFormSchemes) {
                    if (!scheme.getTitle().contains(keyword)) continue;
                    tree.add(new TaskTreeNode(scheme));
                }
            }
        }
        return tree;
    }

    @Override
    public List<ITree<TaskTreeNode>> reloadTaskAndSchemes(TaskTreeNode node) throws JQException {
        ArrayList<ITree<TaskTreeNode>> tree_Task = new ArrayList<ITree<TaskTreeNode>>();
        List listDesignTask = this.nrDesignTimeController.getAllTaskDefines();
        if (listDesignTask != null) {
            for (DesignTaskDefine designTaskDefine : listDesignTask) {
                tree_Task.add(this.getTreeNode(designTaskDefine));
            }
            for (ITree iTree : tree_Task) {
                ArrayList<ITree<TaskTreeNode>> tree_Scheme = new ArrayList<ITree<TaskTreeNode>>();
                List listFormSchemes = this.nrDesignTimeController.queryFormSchemeByTask(((TaskTreeNode)iTree.getData()).getKey());
                for (DesignFormSchemeDefine designFormSchemeDefine : listFormSchemes) {
                    tree_Scheme.add(this.getTreeNode(designFormSchemeDefine));
                }
                if (node.getType().equals((Object)TaskTreeNode.NodeType.TASK) && node.getKey().equals(iTree.getKey())) {
                    iTree.setSelected(true);
                }
                for (ITree iTree2 : tree_Scheme) {
                    if (!node.getType().equals((Object)TaskTreeNode.NodeType.SCHEME) || !node.getKey().equals(iTree2.getKey())) continue;
                    if (tree_Scheme.size() == 1) {
                        iTree.setSelected(true);
                        continue;
                    }
                    iTree.setExpanded(true);
                    iTree2.setSelected(true);
                }
                iTree.setChildren(tree_Scheme);
                if (listFormSchemes.size() != 1 || iTree.getChildren().size() != 1) continue;
                iTree.setLeaf(true);
            }
        }
        return tree_Task;
    }

    @Override
    public List<ITree<FormTreeNode>> getFormTree(String schemeId) {
        ArrayList<ITree<FormTreeNode>> tree_formGroup = new ArrayList<ITree<FormTreeNode>>();
        List formGroupsDefines = this.nrDesignTimeController.queryRootGroupsByFormScheme(schemeId);
        if (formGroupsDefines != null) {
            formGroupsDefines.forEach(formGroup -> tree_formGroup.add(this.getTreeNode((DesignFormGroupDefine)formGroup)));
        }
        for (ITree iTree : tree_formGroup) {
            ArrayList tree_form = new ArrayList();
            List formDefine = this.nrDesignTimeController.getAllFormsInGroupWithoutBinaryData(((FormTreeNode)iTree.getData()).getKey());
            ArrayList<DesignFormDefine> designFormDefineSort = new ArrayList<DesignFormDefine>();
            for (DesignFormDefine designFormDefine : formDefine) {
                List links = this.nrDesignTimeController.getFormGroupLinksByFormId(designFormDefine.getKey());
                for (DesignFormGroupLink link : links) {
                    String order = "";
                    order = StringUtils.isEmpty((String)link.getFormOrder()) ? designFormDefine.getOrder() : link.getFormOrder();
                    if (!link.getGroupKey().equals(((FormTreeNode)iTree.getData()).getKey())) continue;
                    designFormDefine.setOrder(order);
                }
                designFormDefineSort.add(designFormDefine);
            }
            designFormDefineSort.sort((Comparator<DesignFormDefine>)new MetaComparator());
            designFormDefineSort.forEach(form -> tree_form.add(this.getTreeNode((DesignFormDefine)form)));
            iTree.setChildren(tree_form);
        }
        return tree_formGroup;
    }

    private ITree<FormTreeNode> getTreeNode(DesignFormGroupDefine formGroup) {
        ITree node = new ITree((INode)new FormTreeNode(formGroup));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(true);
        return node;
    }

    private ITree<FormTreeNode> getTreeNode(DesignFormDefine formDefine) {
        ITree node = new ITree((INode)new FormTreeNode(formDefine));
        node.setLeaf(true);
        node.setNoDrop(true);
        node.setNoDrag(true);
        return node;
    }

    @Override
    public EntityObj initEnum() throws JQException {
        return this.designerEntityUpgrader.initEnum();
    }

    @Override
    public void saveEnum(SaveEntityVO entityVO) throws Exception {
        this.designerEntityUpgrader.saveEnum(entityVO);
    }

    @Override
    public EntityObj getEnum(String viewKey) throws Exception {
        return this.designerEntityUpgrader.getEnum(viewKey);
    }

    @Override
    public void deleteEnum(String viewKey) throws Exception {
        this.designerEntityUpgrader.deleteEnum(viewKey);
    }

    @Override
    public List<ITree<TaskSchemeGroupTreeNode>> getGroupTree() throws JQException {
        ArrayList<ITree<TaskSchemeGroupTreeNode>> tree_Task = new ArrayList<ITree<TaskSchemeGroupTreeNode>>();
        List listDesignTask = this.nrDesignTimeController.getAllTaskDefines();
        if (listDesignTask != null) {
            listDesignTask.forEach(task -> tree_Task.add(this.getGroupTreeNode((DesignTaskDefine)task)));
        }
        this.taskSchemeTree(tree_Task);
        return tree_Task;
    }

    @Override
    public List<TreeObj> getFormCopyTree(String taskKey) throws JQException {
        ArrayList<TreeObj> tree_Task = new ArrayList<TreeObj>();
        DesignTaskDefine designTaskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
        if (designTaskDefine != null) {
            TreeObj taskNode = this.createTaskNode(designTaskDefine);
            List formSchemeDefines = this.nrDesignTimeController.queryFormSchemeByTask(designTaskDefine.getKey());
            for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                TreeObj formSchemeNode = this.createFormSchemeNode(formSchemeDefine);
                List formGroupDefines = this.nrDesignTimeController.queryRootGroupsByFormScheme(formSchemeDefine.getKey());
                for (DesignFormGroupDefine formGroupDefine : formGroupDefines) {
                    TreeObj formGroupNode = this.createFormGroupNode(formGroupDefine);
                    formSchemeNode.getChildren().add(formGroupNode);
                }
                taskNode.getChildren().add(formSchemeNode);
            }
            tree_Task.add(taskNode);
        }
        return tree_Task;
    }

    private TreeObj createTaskNode(DesignTaskDefine taskDefine) {
        TreeObj obj = new TreeObj();
        obj.setId(taskDefine.getKey());
        obj.setTitle(taskDefine.getTitle());
        obj.setCode(taskDefine.getTaskCode());
        obj.setParentid(null);
        obj.setIsLeaf(Boolean.valueOf(false));
        obj.setIcons("task");
        obj.setChildren(new ArrayList());
        obj.setOnlyChildNodes(true);
        Data data = new Data();
        data.setKey(taskDefine.getKey());
        data.setCode(taskDefine.getTaskCode());
        data.setParentKey(null);
        data.setIcon("task");
        data.setTitle(taskDefine.getTitle());
        obj.setData(data);
        return obj;
    }

    private TreeObj createFormSchemeNode(DesignFormSchemeDefine formSchemeDefine) {
        TreeObj obj = new TreeObj();
        obj.setId(formSchemeDefine.getKey());
        obj.setTitle(formSchemeDefine.getTitle());
        obj.setCode(formSchemeDefine.getFormSchemeCode());
        obj.setParentid(formSchemeDefine.getTaskKey());
        obj.setIsLeaf(Boolean.valueOf(false));
        obj.setIcons("formscheme");
        obj.setChildren(new ArrayList());
        obj.setOnlyChildNodes(true);
        Data data = new Data();
        data.setKey(formSchemeDefine.getKey());
        data.setCode(formSchemeDefine.getFormSchemeCode());
        data.setParentKey(formSchemeDefine.getTaskKey());
        data.setIcon("formscheme");
        data.setTitle(formSchemeDefine.getTitle());
        obj.setData(data);
        return obj;
    }

    private TreeObj createFormGroupNode(DesignFormGroupDefine formGroupDefine) {
        TreeObj obj = new TreeObj();
        obj.setId(formGroupDefine.getKey());
        obj.setTitle(formGroupDefine.getTitle());
        obj.setCode(formGroupDefine.getCode());
        obj.setParentid(formGroupDefine.getFormSchemeKey());
        obj.setIsLeaf(Boolean.valueOf(false));
        obj.setIcons("group");
        obj.setChildren(new ArrayList());
        Data data = new Data();
        data.setKey(formGroupDefine.getKey());
        data.setCode(formGroupDefine.getCode());
        data.setParentKey(formGroupDefine.getFormSchemeKey());
        data.setIcon("group");
        data.setTitle(formGroupDefine.getTitle());
        obj.setData(data);
        return obj;
    }

    private void taskSchemeTree(List<ITree<TaskSchemeGroupTreeNode>> tree_Task) throws JQException {
        for (ITree<TaskSchemeGroupTreeNode> taskTree : tree_Task) {
            ArrayList<ITree<TaskSchemeGroupTreeNode>> tree_Scheme = new ArrayList<ITree<TaskSchemeGroupTreeNode>>();
            List listFormSchemes = this.nrDesignTimeController.queryFormSchemeByTask(((TaskSchemeGroupTreeNode)taskTree.getData()).getKey());
            if (listFormSchemes != null) {
                listFormSchemes.forEach(scheme -> tree_Scheme.add(this.getGroupTreeNode((DesignFormSchemeDefine)scheme)));
            }
            this.schemeGroupTree(tree_Scheme);
            taskTree.setChildren(tree_Scheme);
        }
    }

    private void schemeGroupTree(List<ITree<TaskSchemeGroupTreeNode>> tree_Scheme) throws JQException {
        for (ITree<TaskSchemeGroupTreeNode> schemeTree : tree_Scheme) {
            ArrayList tree_Group = new ArrayList();
            List listFormGroups = this.nrDesignTimeController.queryRootGroupsByFormScheme(((TaskSchemeGroupTreeNode)schemeTree.getData()).getKey());
            if (listFormGroups != null) {
                listFormGroups.forEach(group -> tree_Group.add(this.getGroupTreeNode((DesignFormGroupDefine)group)));
            }
            schemeTree.setChildren(tree_Group);
        }
    }

    private ITree<TaskSchemeGroupTreeNode> getGroupTreeNode(DesignTaskDefine task) {
        ITree node = new ITree((INode)new TaskSchemeGroupTreeNode(task));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }

    private ITree<TaskSchemeGroupTreeNode> getGroupTreeNode(DesignFormSchemeDefine scheme) {
        ITree node = new ITree((INode)new TaskSchemeGroupTreeNode(scheme));
        node.setLeaf(false);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }

    private ITree<TaskSchemeGroupTreeNode> getGroupTreeNode(DesignFormGroupDefine group) {
        ITree node = new ITree((INode)new TaskSchemeGroupTreeNode(group));
        node.setLeaf(true);
        node.setNoDrop(true);
        node.setNoDrag(false);
        return node;
    }

    @Override
    public List<TaskSchemeGroupTreeNode> getSearchGroupResult(String keyword) throws JQException {
        ArrayList<TaskSchemeGroupTreeNode> tree = new ArrayList<TaskSchemeGroupTreeNode>();
        List listDesignTask = this.nrDesignTimeController.getAllTaskDefines();
        if (listDesignTask != null) {
            for (DesignTaskDefine designTaskDefine : listDesignTask) {
                List listFormSchemes = this.nrDesignTimeController.queryFormSchemeByTask(designTaskDefine.getKey());
                for (DesignFormSchemeDefine scheme : listFormSchemes) {
                    List listFormGroups = this.nrDesignTimeController.queryRootGroupsByFormScheme(scheme.getKey());
                    for (DesignFormGroupDefine group : listFormGroups) {
                        if (group.getTitle() == null || !group.getTitle().contains(keyword)) continue;
                        tree.add(new TaskSchemeGroupTreeNode(group));
                    }
                }
            }
        }
        return tree;
    }

    @Override
    public List<ITree<TaskSchemeGroupTreeNode>> reloadGroupTree(TaskSchemeGroupTreeNode node) throws JQException {
        ArrayList<ITree<TaskSchemeGroupTreeNode>> tree_Task = new ArrayList<ITree<TaskSchemeGroupTreeNode>>();
        List listDesignTask = this.nrDesignTimeController.getAllTaskDefines();
        if (listDesignTask != null) {
            listDesignTask.forEach(task -> tree_Task.add(this.getGroupTreeNode((DesignTaskDefine)task)));
        }
        this.taskSchemeTree_reload(tree_Task, node);
        return tree_Task;
    }

    @Override
    public List<SimpleFormGroupObj> simpleFormGroupTree(String formSchemeKey) throws JQException {
        List simpleFormGroupObjs = null;
        try {
            if (StringUtils.isNotEmpty((String)formSchemeKey)) {
                simpleFormGroupObjs = Optional.ofNullable(this.nrDesignTimeController.queryRootGroupsByFormScheme(formSchemeKey)).orElse(Collections.emptyList()).stream().map(formGroupDefine -> {
                    SimpleFormGroupObj simpleFormGroupObj = new SimpleFormGroupObj((DesignFormGroupDefine)formGroupDefine);
                    String formGroupKey = formGroupDefine.getKey();
                    List formDefines = this.nrDesignTimeController.getAllFormsInGroupLazy(formGroupKey, false);
                    ArrayList<SimpleFormObj> simpleFormObjs = new ArrayList<SimpleFormObj>();
                    Optional.ofNullable(formDefines).orElse(Collections.emptyList()).forEach(formDefine -> {
                        SimpleFormObj simpleFormObj = new SimpleFormObj(formGroupKey, (DesignFormDefine)formDefine);
                        simpleFormObjs.add(simpleFormObj);
                    });
                    simpleFormGroupObj.setChildren(simpleFormObjs);
                    return simpleFormGroupObj;
                }).collect(Collectors.toList());
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_172, (Throwable)e);
        }
        return simpleFormGroupObjs;
    }

    private void taskSchemeTree_reload(List<ITree<TaskSchemeGroupTreeNode>> tree_Task, TaskSchemeGroupTreeNode node) throws JQException {
        for (ITree<TaskSchemeGroupTreeNode> taskTree : tree_Task) {
            ArrayList<ITree<TaskSchemeGroupTreeNode>> tree_Scheme = new ArrayList<ITree<TaskSchemeGroupTreeNode>>();
            List listFormSchemes = this.nrDesignTimeController.queryFormSchemeByTask(((TaskSchemeGroupTreeNode)taskTree.getData()).getKey());
            if (listFormSchemes != null) {
                listFormSchemes.forEach(scheme -> tree_Scheme.add(this.getGroupTreeNode((DesignFormSchemeDefine)scheme)));
            }
            this.schemeGroupTree_reload(tree_Scheme, taskTree, node);
            taskTree.setChildren(tree_Scheme);
        }
    }

    private void schemeGroupTree_reload(List<ITree<TaskSchemeGroupTreeNode>> tree_Scheme, ITree<TaskSchemeGroupTreeNode> taskTree, TaskSchemeGroupTreeNode node) throws JQException {
        for (ITree<TaskSchemeGroupTreeNode> schemeTree : tree_Scheme) {
            ArrayList tree_Group = new ArrayList();
            List listFormGroups = this.nrDesignTimeController.queryRootGroupsByFormScheme(schemeTree.getKey());
            if (listFormGroups != null) {
                listFormGroups.forEach(group -> tree_Group.add(this.getGroupTreeNode((DesignFormGroupDefine)group)));
            }
            for (ITree groupTree : tree_Group) {
                if (!node.getType().equals((Object)TaskSchemeGroupTreeNode.NodeType.GROUP) || !node.getKey().equals(groupTree.getKey())) continue;
                groupTree.setSelected(true);
                groupTree.setChecked(false);
                schemeTree.setExpanded(true);
                taskTree.setExpanded(true);
            }
            schemeTree.setChildren(tree_Group);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public FormCopyObj copyForm(FormCopyObj formCopyObj) throws Exception {
        DesignFormDefine formDefine = this.iDesignTimeViewController.queryFormById(formCopyObj.getCopyFormKey());
        DesignFormGroupDefine formGroupDefine = this.iDesignTimeViewController.queryFormGroup(formCopyObj.getFormGroup());
        DesignFormDefineImpl newForm = new DesignFormDefineImpl();
        BeanUtils.copyProperties(formDefine, newForm);
        newForm.setKey(UUIDUtils.getKey());
        newForm.setOrder(OrderGenerator.newOrder());
        newForm.setFormCode(formCopyObj.getFormCode());
        newForm.setTitle(formCopyObj.getFormTitle());
        newForm.setUpdateTime(new Date());
        newForm.setFormScheme(formGroupDefine.getFormSchemeKey());
        if (formDefine.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS && null != formDefine.getFormExtension()) {
            Map formExtension = formDefine.getFormExtension();
            for (String anaKey : formExtension.keySet()) {
                newForm.addExtensions(anaKey, formExtension.get(anaKey));
            }
        }
        this.iDesignTimeViewController.insertFormDefine((DesignFormDefine)newForm);
        if (newForm.isAnalysisForm()) {
            byte[] bigData = this.designBigDataService.getBigData(formDefine.getKey(), "ANALYSIS_FORM_PARAM");
            this.designBigDataService.updateBigDataDefine(newForm.getKey(), "ANALYSIS_FORM_PARAM", bigData);
        }
        this.iDesignTimeViewController.addFormToGroup(newForm.getKey(), formGroupDefine.getKey());
        formCopyObj.setNewFormKey(newForm.getKey());
        formCopyObj.setTargetFormScheme(formGroupDefine.getFormSchemeKey());
        if (formCopyObj.getOnlyCopyStyle()) {
            List regionsInForm = this.iDesignTimeViewController.getAllRegionsInForm(formDefine.getKey());
            List simpleRegion = regionsInForm.stream().filter(a -> DataRegionKind.DATA_REGION_SIMPLE == a.getRegionKind()).collect(Collectors.toList());
            if (simpleRegion.size() > 0) {
                DesignDataRegionDefineImpl newRegion = new DesignDataRegionDefineImpl();
                BeanUtils.copyProperties(simpleRegion.get(0), newRegion);
                newRegion.setKey(UUIDUtils.getKey());
                newRegion.setUpdateTime(new Date());
                newRegion.setOrder(OrderGenerator.newOrder());
                newRegion.setFormKey(newForm.getKey());
                newRegion.setRegionSettingKey(null);
                this.iDesignTimeViewController.insertDataRegionDefine((DesignDataRegionDefine)newRegion);
            }
            return formCopyObj;
        }
        HashMap<String, String> linkMap = new HashMap<String, String>();
        List regionsInForm = this.iDesignTimeViewController.getAllRegionsInForm(formDefine.getKey());
        for (DesignDataRegionDefine regionDefine : regionsInForm) {
            DesignDataRegionDefineImpl newRegion = new DesignDataRegionDefineImpl();
            BeanUtils.copyProperties(regionDefine, newRegion);
            newRegion.setKey(UUIDUtils.getKey());
            newRegion.setUpdateTime(new Date());
            newRegion.setOrder(OrderGenerator.newOrder());
            newRegion.setFormKey(newForm.getKey());
            List allLinksInRegion = this.iDesignTimeViewController.getAllLinksInRegion(regionDefine.getKey());
            ArrayList<DesignDataLinkDefineImpl> insertLink = new ArrayList<DesignDataLinkDefineImpl>();
            ArrayList<DesignBigDataTable> insertLinkData = new ArrayList<DesignBigDataTable>();
            for (DesignDataLinkDefine designDataLinkDefine : allLinksInRegion) {
                DesignDataLinkDefineImpl newLink = new DesignDataLinkDefineImpl();
                BeanUtils.copyProperties(designDataLinkDefine, newLink);
                newLink.setKey(UUIDUtils.getKey());
                newLink.setRegionKey(newRegion.getKey());
                newLink.setUniqueCode(OrderGenerator.newOrder());
                newLink.setUpdateTime(new Date());
                newLink.setOrder(OrderGenerator.newOrder());
                insertLink.add(newLink);
                linkMap.put(designDataLinkDefine.getKey(), newLink.getKey());
                byte[] attachments = this.designBigDataService.getBigData(designDataLinkDefine.getKey(), "ATTACHMENT");
                if (null == attachments) continue;
                DesignBigDataTable designBigDataTable = new DesignBigDataTable();
                designBigDataTable.setKey(newLink.getKey());
                designBigDataTable.setData(attachments);
                designBigDataTable.setCode("ATTACHMENT");
                insertLinkData.add(designBigDataTable);
            }
            if (null != regionDefine.getRegionSettingKey()) {
                DesignRegionSettingDefine regionSetting = this.iDesignTimeViewController.getRegionSetting(regionDefine.getRegionSettingKey());
                DesignRegionSettingDefineImpl designRegionSettingDefineImpl = new DesignRegionSettingDefineImpl();
                BeanUtils.copyProperties(regionSetting, designRegionSettingDefineImpl);
                designRegionSettingDefineImpl.setKey(UUIDUtils.getKey());
                newRegion.setRegionSettingKey(designRegionSettingDefineImpl.getKey());
                List entityDefaultValue = regionSetting.getEntityDefaultValue();
                if (null == entityDefaultValue) {
                    designRegionSettingDefineImpl.setEntityDefaultValue(null);
                } else {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        designRegionSettingDefineImpl.setEntityDefaultValue(mapper.writeValueAsString((Object)entityDefaultValue));
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
                designRegionSettingDefineImpl.setLastRowStyle(regionSetting.getLastRowStyles());
                designRegionSettingDefineImpl.setRegionSurvey(this.regionSurveyHelper.formCopyRegionSurvey2(regionSetting.getRegionSurvey(), linkMap));
                this.iDesignTimeViewController.addRegionSetting((DesignRegionSettingDefine)designRegionSettingDefineImpl);
            }
            this.iDesignTimeViewController.insertDataRegionDefine((DesignDataRegionDefine)newRegion);
            for (DesignDataLinkDefine designDataLinkDefine : insertLink) {
                if (!StringUtils.isNotEmpty((String)designDataLinkDefine.getEnumLinkage())) continue;
                String newEnumStr = designDataLinkDefine.getEnumLinkage();
                for (DesignDataLinkDefine l2 : allLinksInRegion) {
                    if (newEnumStr.indexOf(l2.getKey()) == -1) continue;
                    newEnumStr = newEnumStr.replace(l2.getKey(), (CharSequence)linkMap.get(l2.getKey()));
                }
                designDataLinkDefine.setEnumLinkage(newEnumStr);
            }
            if (insertLink.size() != 0) {
                this.iDesignTimeViewController.insertDataLinkDefines((DesignDataLinkDefine[])insertLink.stream().toArray(DesignDataLinkDefine[]::new));
            }
            if (insertLinkData.size() == 0) continue;
            for (DesignBigDataTable designBigDataTable : insertLinkData) {
            }
        }
        List dataLinkMappingDefines = this.iDesignTimeViewController.queryDataLinkMappingByFormKey(formDefine.getKey());
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
        if (insertMapping.size() != 0) {
            this.iDesignTimeViewController.insertDataLinkMappingDefines((DesignDataLinkMappingDefine[])insertMapping.stream().toArray(DesignDataLinkMappingDefine[]::new));
        }
        List allCSInForm = this.conditionalStyleController.getAllCSInForm(formDefine.getKey());
        ArrayList<DesignConditionalStyleImpl> insertStyle = new ArrayList<DesignConditionalStyleImpl>();
        for (DesignConditionalStyle designConditionalStyle : allCSInForm) {
            DesignConditionalStyleImpl newStyle = new DesignConditionalStyleImpl();
            BeanUtils.copyProperties(designConditionalStyle, newStyle);
            newStyle.setKey(UUIDUtils.getKey());
            newStyle.setOrder(OrderGenerator.newOrder());
            newStyle.setFormKey(newForm.getKey());
            newStyle.setUpdateTime(new Date());
            String string = (String)linkMap.get(designConditionalStyle.getLinkKey() == null ? designConditionalStyle.getLinkKey() : "");
            newStyle.setLinkKey(string);
            insertStyle.add(newStyle);
        }
        if (insertStyle.size() != 0) {
            this.conditionalStyleController.insertCS(insertStyle);
        }
        List formFoldings = this.formFoldingService.getByFormKey(formDefine.getKey());
        ArrayList<DesignFormFoldingDefineImpl> insertFormFoldings = new ArrayList<DesignFormFoldingDefineImpl>();
        for (DesignFormFoldingDefine formFolding : formFoldings) {
            DesignFormFoldingDefineImpl newFormFolding = new DesignFormFoldingDefineImpl();
            newFormFolding.setKey(UUIDUtils.getKey());
            newFormFolding.setFormKey(newForm.getKey());
            newFormFolding.setStartIdx(formFolding.getStartIdx());
            newFormFolding.setEndIdx(formFolding.getEndIdx());
            newFormFolding.setHiddenRegion(formFolding.getHiddenRegion());
            newFormFolding.setDirection(formFolding.getDirection());
            newFormFolding.setFolding(formFolding.isFolding());
            newFormFolding.setUpdateTime(new Date());
            insertFormFoldings.add(newFormFolding);
        }
        if (!CollectionUtils.isEmpty(insertFormFoldings)) {
            this.formFoldingService.insert(insertFormFoldings.toArray(formFoldings.toArray(new DesignFormFoldingDefine[insertFormFoldings.size()])));
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        boolean isCurrentFormScheme = false;
        HashMap<String, String> printSchemeMap = new HashMap<String, String>();
        if (formDefine.getFormScheme().equals(formGroupDefine.getFormSchemeKey())) {
            isCurrentFormScheme = true;
        } else {
            List targetFormulaSchemes = this.iFormulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formGroupDefine.getFormSchemeKey());
            for (Object targetFormulaScheme : targetFormulaSchemes) {
                hashMap.put(targetFormulaScheme.getTitle(), targetFormulaScheme.getKey());
            }
            List printSchemeByFormScheme = this.iPrintDesignTimeController.getAllPrintSchemeByFormScheme(formGroupDefine.getFormSchemeKey());
            for (DesignPrintTemplateSchemeDefine printTemplateSchemeDefine : printSchemeByFormScheme) {
                printSchemeMap.put(printTemplateSchemeDefine.getTitle(), printTemplateSchemeDefine.getKey());
            }
        }
        List formulaSchemeDefines = this.iFormulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(formDefine.getFormScheme());
        HashSet<String> formulaCodeSet = new HashSet<String>();
        int number = 1;
        for (DesignFormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            ArrayList<DesignFormulaDefineImpl> insertFormula = new ArrayList<DesignFormulaDefineImpl>();
            if (!isCurrentFormScheme && null == hashMap.get(formulaSchemeDefine.getTitle())) continue;
            String targetFormulaScheme = "";
            targetFormulaScheme = isCurrentFormScheme ? formulaSchemeDefine.getKey() : (String)hashMap.get(formulaSchemeDefine.getTitle());
            List targetFormulas = this.iFormulaDesignTimeController.querySimpleFormulaDefineByScheme(targetFormulaScheme);
            for (DesignFormulaDefine targetFormula : targetFormulas) {
                formulaCodeSet.add(targetFormula.getCode());
            }
            List formulaDefines = this.iFormulaDesignTimeController.getAllFormulasInForm(formulaSchemeDefine.getKey(), formDefine.getKey());
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
            this.iFormulaDesignTimeController.insertFormulaDefines((DesignFormulaDefine[])insertFormula.stream().toArray(DesignFormulaDefine[]::new));
            formulaCodeSet.clear();
            number = 1;
        }
        List printSchemeByFormScheme = this.iPrintDesignTimeController.getAllPrintSchemeByFormScheme(formDefine.getFormScheme());
        for (DesignPrintTemplateSchemeDefine designPrintTemplateSchemeDefine : printSchemeByFormScheme) {
            DesignPrintSettingDefine setting;
            if (!isCurrentFormScheme && null == printSchemeMap.get(designPrintTemplateSchemeDefine.getTitle())) continue;
            String targetPrintScheme = "";
            targetPrintScheme = isCurrentFormScheme ? designPrintTemplateSchemeDefine.getKey() : (String)printSchemeMap.get(designPrintTemplateSchemeDefine.getTitle());
            DesignPrintTemplateDefine designPrintTemplateDefine = this.iPrintDesignTimeController.queryPrintTemplateDefineBySchemeAndForm(designPrintTemplateSchemeDefine.getKey(), formDefine.getKey());
            if (null != designPrintTemplateDefine) {
                DesignPrintTemplateDefineImpl newTemp = new DesignPrintTemplateDefineImpl();
                BeanUtils.copyProperties(designPrintTemplateDefine, newTemp);
                newTemp.setKey(UUIDUtils.getKey());
                newTemp.setFormKey(newForm.getKey());
                newTemp.setPrintSchemeKey(targetPrintScheme);
                newTemp.setUpdateTime(new Date());
                newTemp.setOrder(OrderGenerator.newOrder());
                this.iPrintDesignTimeController.insertPrintTemplateDefine((DesignPrintTemplateDefine)newTemp);
            }
            if (null == (setting = this.nrDesignTimeController.getPrintSettingDefine(designPrintTemplateSchemeDefine.getKey(), formDefine.getKey()))) continue;
            setting.setFormKey(newForm.getKey());
            setting.setPrintSchemeKey(targetPrintScheme);
            setting.setUpdateTime(new Date());
            this.nrDesignTimeController.insertPrintSettingDefine(setting);
        }
        return formCopyObj;
    }

    @Override
    public List<ITree<TaskTreeNode>> getTaskGroupAndTaskTree() {
        List allTaskGroup = this.designTimeViewController.getAllTaskGroup();
        List groupLinks = this.designTimeViewController.getGroupLink();
        Map taskMap = groupLinks.stream().collect(Collectors.groupingBy(DesignTaskGroupLink::getTaskKey, Collectors.mapping(DesignTaskGroupLink::getGroupKey, Collectors.toList())));
        List allTaskDefines = this.designTimeViewController.getAllTaskDefines();
        TaskTreeNode rootNode = new TaskTreeNode();
        rootNode.setTitle("\u5168\u90e8\u4efb\u52a1");
        rootNode.setType(TaskTreeNode.NodeType.TASK_GROUP);
        ITree root = new ITree((INode)rootNode);
        LinkedHashMap<String, ITree> treeMap = new LinkedHashMap<String, ITree>(allTaskGroup.size() + groupLinks.size() + 1);
        for (DesignTaskGroupDefine designTaskGroupDefine : allTaskGroup) {
            treeMap.put(designTaskGroupDefine.getKey(), new ITree((INode)new TaskTreeNode(designTaskGroupDefine)));
        }
        for (DesignTaskDefine designTaskDefine : allTaskDefines) {
            ITree taskTreeNodeITree = new ITree((INode)new TaskTreeNode(designTaskDefine));
            taskTreeNodeITree.setLeaf(true);
            treeMap.put(designTaskDefine.getKey(), taskTreeNodeITree);
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            ITree value = (ITree)entry.getValue();
            TaskTreeNode data = (TaskTreeNode)value.getData();
            if (data.getType() == TaskTreeNode.NodeType.TASK_GROUP) {
                String parentId = data.getParentId();
                if (parentId != null && treeMap.containsKey(parentId)) {
                    ((ITree)treeMap.get(parentId)).appendChild(value);
                    continue;
                }
                root.appendChild(value);
                continue;
            }
            if (taskMap.containsKey(data.getKey())) {
                for (String parentId : taskMap.getOrDefault(data.getKey(), Collections.emptyList())) {
                    if (parentId != null && treeMap.containsKey(parentId)) {
                        ((ITree)treeMap.get(parentId)).appendChild(value);
                        continue;
                    }
                    root.appendChild(value);
                }
                continue;
            }
            root.appendChild(value);
        }
        return Collections.singletonList(root);
    }
}

