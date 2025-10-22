/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.option.DimGroupOptionService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.fmdm.domain.FMDMAttributeDO
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfNr
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService
 *  com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuiqi.nr.unit.treebase.context;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeGroupField;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeShowTagsOptions;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.node.icon.IconSourceSchemeOfBBLX;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.option.DimGroupOptionService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.domain.FMDMAttributeDO;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfNr;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService;
import com.jiuqi.nr.unit.treestore.systemconfig.UnitTreeSystemConfig;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.util.StringUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeContextWrapper
implements IUnitTreeContextWrapper {
    @Resource
    public IEntityMetaService metaService;
    @Resource
    public ITaskOptionController taskOption;
    @Resource
    public IEntityAuthorityService authService;
    @Resource
    public PeriodEngineService periodEngineService;
    @Resource
    private IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    public DimGroupOptionService dimGroupOptionService;
    @Resource
    private IFormTypeApplyService formTypeApplyService;
    @Resource
    private FMDMDisplaySchemeService captionFieldService;
    @Resource
    private IFMDMAttributeService fmdmService;
    @Resource
    private IRunTimeViewController formRtCtl;
    @Resource
    private IDesignDataSchemeService designDataSchemeService;
    @Resource
    private UnitTreeSystemConfig systemConfig;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;
    @Resource
    private WorkflowReportDimService workflowReportDimService;

    @Override
    public boolean isOpenWorkFlow(FormSchemeDefine formSchemeDefine) {
        boolean isOpen = false;
        if (null != formSchemeDefine) {
            TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
            FlowsType flowsType = flowsSetting.getFlowsType();
            switch (flowsType) {
                case DEFAULT: 
                case WORKFLOW: {
                    isOpen = true;
                    break;
                }
            }
        }
        return isOpen;
    }

    @Override
    public boolean isOpenTerminal(FormSchemeDefine formSchemeDefine) {
        TaskDefine taskDefine;
        String status;
        if (null != formSchemeDefine && (status = this.taskOption.getValue((taskDefine = this.formRtCtl.queryTaskDefine(formSchemeDefine.getTaskKey())).getKey(), "ALLOW_STOP_FILING")) != null) {
            return "1".equals(status.toString());
        }
        return false;
    }

    @Override
    public boolean isOpenFillReport(FormSchemeDefine formSchemeDefine) {
        TaskDefine taskDefine;
        String status;
        if (null != formSchemeDefine && (status = this.taskOption.getValue((taskDefine = this.formRtCtl.queryTaskDefine(formSchemeDefine.getTaskKey())).getKey(), "DATAENTRY_STATUS")) != null) {
            return status.toString().contains("1");
        }
        return false;
    }

    @Override
    public boolean isShowTimeDeadline(FormSchemeDefine formSchemeDefine) {
        FillInAutomaticallyDue type;
        if (formSchemeDefine != null && (type = formSchemeDefine.getFillInAutomaticallyDue()) != null) {
            return FillInAutomaticallyDue.Type.CLOSE.getValue() != type.getType();
        }
        return false;
    }

    @Override
    public boolean canAddDimension(TaskDefine taskDefine) {
        String status;
        if (null != taskDefine && (status = this.taskOption.getValue(taskDefine.getKey(), "UNIT_EDIT")) != null) {
            return "1".equals(status.toString());
        }
        return false;
    }

    @Override
    public boolean hasDimGroupConfig(TaskDefine taskDefine) {
        UnitTreeGroupField dimGroupConfig = this.getDimGroupFieldConfig(taskDefine);
        return dimGroupConfig != null;
    }

    @Override
    public boolean canDisplayFMDMAttributes(FormSchemeDefine formSchemeDefine, IEntityDefine entityDefine, IEntityQueryPloy entityQueryPloy) {
        return !this.getCationFields(formSchemeDefine, entityDefine, entityQueryPloy).isEmpty();
    }

    @Override
    public List<IFMDMAttribute> getCationFields(FormSchemeDefine formScheme, IEntityDefine entityDefine, IEntityQueryPloy entityQueryPloy) {
        ArrayList<IFMDMAttribute> attributes = new ArrayList<IFMDMAttribute>();
        FMDMDisplayScheme displayScheme = formScheme == null ? this.captionFieldService.getCurrentDisplayScheme(entityDefine.getId()) : this.captionFieldService.getCurrentDisplayScheme(formScheme.getKey(), entityDefine.getId());
        if (displayScheme != null) {
            List showFields = displayScheme.getFields();
            List<IFMDMAttribute> showFMDMAttributes = this.getFMDMShowAttribute(formScheme, entityDefine, entityQueryPloy);
            for (String fdKey : showFields) {
                IFMDMAttribute attribute = this.findIFMDMAttribute(fdKey, showFMDMAttributes);
                if (attribute == null) continue;
                attributes.add(attribute);
            }
        }
        return attributes;
    }

    @Override
    public List<IFMDMAttribute> getFMDMShowAttribute(FormSchemeDefine formScheme, IEntityDefine entityDefine, IEntityQueryPloy entityQueryPloy) {
        if (entityQueryPloy == IEntityQueryPloy.MAIN_DIM_QUERY && this.hasFMDMFormDefine(formScheme)) {
            FMDMAttributeDTO dto = new FMDMAttributeDTO();
            dto.setEntityId(entityDefine.getId());
            dto.setFormSchemeKey(formScheme.getKey());
            List fmdmAttributes = this.fmdmService.listShowAttribute(dto);
            IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
            IEntityAttribute nameField = entityModel.getNameField();
            if (fmdmAttributes.stream().noneMatch(attr -> attr.getCode().equals(nameField.getCode()))) {
                fmdmAttributes.add(0, this.transferAttribute((ColumnModelDefine)nameField, null));
            }
            IEntityAttribute codeField = entityModel.getCodeField();
            if (fmdmAttributes.stream().noneMatch(attr -> attr.getCode().equals(codeField.getCode()))) {
                fmdmAttributes.add(0, this.transferAttribute((ColumnModelDefine)codeField, null));
            }
            return fmdmAttributes;
        }
        IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
        List showFields = entityModel.getShowFields();
        if (showFields != null && !showFields.isEmpty()) {
            ArrayList<IFMDMAttribute> fmdmAttributes = new ArrayList<IFMDMAttribute>();
            List entityRefer = this.metaService.getEntityRefer(entityDefine.getId());
            IEntityAttribute parentField = entityModel.getParentField();
            Map<String, IEntityRefer> referMap = entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, e -> e));
            for (IEntityAttribute showField : showFields) {
                FMDMAttributeDO attribute = this.transferAttribute((ColumnModelDefine)showField, referMap);
                attribute.setEntityId(entityDefine.getId());
                if (attribute.getCode().equals(parentField.getCode())) {
                    attribute.setReferEntity(true);
                    attribute.setReferEntityId(entityDefine.getId());
                }
                fmdmAttributes.add((IFMDMAttribute)attribute);
            }
            return fmdmAttributes;
        }
        return new ArrayList<IFMDMAttribute>();
    }

    private FMDMAttributeDO transferAttribute(ColumnModelDefine columnModelDefine, Map<String, IEntityRefer> referMap) {
        FMDMAttributeDO attribute = new FMDMAttributeDO();
        BeanUtils.copyProperties(columnModelDefine, attribute);
        if (org.springframework.util.StringUtils.hasText(columnModelDefine.getReferTableID())) {
            IEntityRefer refer;
            if (referMap != null && (refer = referMap.get(columnModelDefine.getCode())) != null) {
                attribute.setReferEntityId(refer.getReferEntityId());
            }
            attribute.setReferEntity(true);
        }
        return attribute;
    }

    @Override
    public boolean hasFMDMFormDefine(FormSchemeDefine formScheme) {
        if (formScheme == null) {
            return false;
        }
        List formDefines = this.formRtCtl.queryAllFormDefinesByFormScheme(formScheme.getKey());
        Optional<FormDefine> findFMDM = formDefines.stream().filter(e -> FormType.FORM_TYPE_NEWFMDM.equals((Object)e.getFormType())).findFirst();
        return findFMDM.map(IBaseMetaItem::getKey).orElse(null) != null;
    }

    private IFMDMAttribute findIFMDMAttribute(String fmdmKey, List<IFMDMAttribute> attributes) {
        for (IFMDMAttribute attr : attributes) {
            if (!fmdmKey.equals(attr.getID())) continue;
            return attr;
        }
        return null;
    }

    @Override
    public boolean canShowNodeTags(UnitTreeShowTagsOptions showTagsOptions) {
        if (showTagsOptions != null) {
            List<String> showTagKeys = showTagsOptions.getShowTagKeys();
            return showTagsOptions.isShowNodeTags() && showTagKeys != null && !showTagKeys.isEmpty();
        }
        return false;
    }

    @Override
    public boolean hasUnitAuditOperation(String entityId, String rowKey, String periodEntityId, String periodString) {
        try {
            if (this.authService.isEnableAuthority(entityId)) {
                Date[] dateRange = this.parseFromPeriod(periodString, periodEntityId);
                return this.authService.canAuditEntity(entityId, rowKey, dateRange[0], dateRange[1]);
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return true;
    }

    @Override
    public boolean hasUnitEditOperation(String entityId, String rowKey, String periodEntityId, String periodString) {
        try {
            if (this.authService.isEnableAuthority(entityId)) {
                Date[] dateRange = this.parseFromPeriod(periodString, periodEntityId);
                return this.authService.canEditEntity(entityId, rowKey, dateRange[0], dateRange[1]);
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return true;
    }

    @Override
    public String openTerminalRole(TaskDefine taskDefine) {
        if (null != taskDefine) {
            String status = this.taskOption.getValue(taskDefine.getKey(), "ALLOW_STOP_FILING_ROLE");
            return null != status ? status.toString() : null;
        }
        return null;
    }

    @Override
    public UnitTreeGroupField getDimGroupFieldConfig(TaskDefine taskDefine) {
        String[] groupConfig;
        UnitTreeGroupField levelGroup1 = null;
        if (taskDefine != null && (groupConfig = this.dimGroupOptionService.getGroupOptionValue(taskDefine.getKey())) != null && groupConfig.length == 2 && taskDefine.getDw().equals(groupConfig[0])) {
            String[] filterConfig = this.dimGroupOptionService.getFilterOptionValue(taskDefine.getKey());
            levelGroup1 = this.buildGroupField(groupConfig);
            if (levelGroup1 != null) {
                levelGroup1.setParentGroupField(this.buildGroupField(filterConfig));
            }
        }
        return levelGroup1;
    }

    @Override
    public IEntityRefer getBBLXEntityRefer(IEntityDefine entityDefine) {
        IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
        IEntityAttribute bblxField = entityModel.getBblxField();
        if (null != bblxField) {
            List entityRefer = this.metaService.getEntityRefer(entityDefine.getId());
            for (IEntityRefer refer : entityRefer) {
                if (!refer.getOwnField().equals(bblxField.getCode())) continue;
                return refer;
            }
        }
        return null;
    }

    @Override
    public IconSourceScheme getBBLXIConSourceScheme(IEntityDefine entityDefine) {
        IEntityRefer bblxEntityRefer = this.getBBLXEntityRefer(entityDefine);
        if (null != bblxEntityRefer) {
            String entityCode = this.metaService.getEntityCode(bblxEntityRefer.getReferEntityId());
            return new IconSourceSchemeOfBBLX(this.formTypeApplyService, entityCode);
        }
        return new IconSourceSchemeOfNr();
    }

    @Override
    public List<String> getReportEntityDimensionName(FormSchemeDefine formSchemeDefine) {
        ArrayList<String> dimensionNames = new ArrayList<String>();
        List dataSchemeDimension = this.workflowReportDimService.getDataDimension(formSchemeDefine.getTaskKey());
        for (DataDimension dimension : dataSchemeDimension) {
            String dimKey = dimension.getDimKey();
            String dimensionName = this.getDimensionName(dimKey);
            dimensionNames.add(dimensionName);
        }
        return dimensionNames;
    }

    @Override
    public String getDimensionName(String entityId) {
        String dimensionName = this.periodEntityAdapter.isPeriodEntity(entityId) ? this.periodEntityAdapter.getPeriodDimensionName() : ("ADJUST".equals(entityId) ? "ADJUST" : this.metaService.getDimensionName(entityId));
        return dimensionName;
    }

    @Override
    public DimensionValueSet buildDimensionValueSet(IUnitTreeContext ctx) {
        DimensionValueSet dimValueSet = new DimensionValueSet();
        FormSchemeDefine formSchemeDefine = ctx.getFormScheme();
        Map<String, DimensionValue> dimValueMap = ctx.getDimValueSet();
        if (formSchemeDefine != null && dimValueMap != null) {
            List<String> reportEntityDimensionName = this.getReportEntityDimensionName(formSchemeDefine);
            for (Map.Entry<String, DimensionValue> entrySet : dimValueMap.entrySet()) {
                if (!reportEntityDimensionName.contains(entrySet.getKey())) continue;
                dimValueSet.setValue(entrySet.getKey(), (Object)entrySet.getValue().getValue());
            }
        }
        String period = ctx.getPeriod();
        IPeriodEntity periodEntity = ctx.getPeriodEntity();
        if (periodEntity != null && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)period)) {
            dimValueSet.setValue(periodEntity.getDimensionName(), (Object)period);
        }
        return dimValueSet;
    }

    @Override
    public boolean isOpenADJUST(TaskDefine taskDefine) {
        List reportDimensions = this.designDataSchemeService.getReportDimension(taskDefine.getDataScheme());
        for (DesignDataDimension dataDimension : reportDimensions) {
            if (!"ADJUST".equals(dataDimension.getDimKey())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean canLoadWorkFlowState(IUnitTreeContext ctx) {
        if (this.isOpenWorkFlow(ctx.getFormScheme())) {
            if (this.isOpenADJUST(ctx.getTaskDefine())) {
                Map<String, DimensionValue> dimValueSet = ctx.getDimValueSet();
                return com.jiuqi.bi.util.StringUtils.isNotEmpty((String)ctx.getPeriod()) && dimValueSet != null && dimValueSet.containsKey("ADJUST") && dimValueSet.get("ADJUST") != null && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dimValueSet.get("ADJUST").getValue());
            }
            return com.jiuqi.bi.util.StringUtils.isNotEmpty((String)ctx.getPeriod());
        }
        return false;
    }

    @Override
    public boolean isTreeExpandAllLevel(IUnitTreeContext context) {
        if (this.systemConfig.isShowTreeExpandAllLevelMenu()) {
            int totalCount = this.entityDataQuery.makeIEntityTable(context).getTotalCount();
            return totalCount < 1000;
        }
        return false;
    }

    @Override
    public String[] queryDimAttributeCode(IUnitTreeContext context) {
        TaskDefine taskDefine = context.getTaskDefine();
        List dataSchemeDimension = this.workflowReportDimService.getDataDimension(taskDefine.getKey());
        for (DataDimension dimension : dataSchemeDimension) {
            boolean corporate = this.isCorporate(taskDefine, dimension, dataSchemeDimension);
            if (!corporate) continue;
            String imAttributeDimensionName = this.metaService.getDimensionName(dimension.getDimKey());
            IEntityModel dwEntityModel = this.metaService.getEntityModel(taskDefine.getDw());
            String dimAttribute = dimension.getDimAttribute();
            if (dimAttribute == null) continue;
            IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
            String dimAttributeCode = attribute.getCode();
            return new String[]{imAttributeDimensionName, dimAttributeCode};
        }
        return null;
    }

    public boolean isCorporate(TaskDefine taskDefine, DataDimension dimension, List<DataDimension> dataSchemeDimension) {
        String dimAttribute = dimension.getDimAttribute();
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        IEntityModel dwEntityModel = entityMetaService.getEntityModel(taskDefine.getDw());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        DataDimension report = dataSchemeDimension.stream().filter(dataDimension -> dimension.getDimKey().equals(dataDimension.getDimKey())).findFirst().orElse(null);
        String dimReferAttr = report == null ? null : report.getDimAttribute();
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }

    private Date[] parseFromPeriod(String periodString, String periodEntityId) throws ParseException {
        Date[] dateRegion = new Date[]{Consts.DATE_VERSION_INVALID_VALUE, Consts.DATE_VERSION_FOR_ALL};
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)periodString) || com.jiuqi.bi.util.StringUtils.isEmpty((String)periodEntityId)) {
            return dateRegion;
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        return periodProvider.getPeriodDateRegion(periodString);
    }

    private UnitTreeGroupField buildGroupField(String[] groupConfig) {
        UnitTreeGroupField groupField = null;
        if (groupConfig != null && groupConfig.length == 2) {
            String referEntityId = groupConfig[0];
            String referFieldCode = groupConfig[1];
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)referEntityId) && com.jiuqi.bi.util.StringUtils.isNotEmpty((String)referFieldCode)) {
                IEntityRefer refer = this.getIEntityRefer(referEntityId, referFieldCode);
                if (refer != null) {
                    groupField = new UnitTreeGroupField(refer.getReferEntityId(), referFieldCode);
                } else {
                    groupField = new UnitTreeGroupField(referEntityId, referFieldCode);
                    groupField.setOwnRefer(true);
                }
            }
        }
        return groupField;
    }

    private IEntityRefer getIEntityRefer(String entityId, String ownField) {
        List refers = this.metaService.getEntityRefer(entityId);
        if (refers != null) {
            List hasRefer = refers.stream().filter(refer -> refer.getOwnField().equals(ownField)).collect(Collectors.toList());
            return !hasRefer.isEmpty() ? (IEntityRefer)hasRefer.get(0) : null;
        }
        return null;
    }
}

