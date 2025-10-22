/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.definition.option.impl;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.option.DimGroupOptionService;
import com.jiuqi.nr.definition.option.dto.DimensionGroupDTO;
import com.jiuqi.nr.definition.option.dto.InnerField;
import com.jiuqi.nr.definition.option.dto.ReferEntity;
import com.jiuqi.nr.definition.option.treegroup.GroupInfo;
import com.jiuqi.nr.definition.option.treegroup.GroupInfoDTO;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DimGroupOptionServiceImpl
implements DimGroupOptionService {
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public DimensionGroupDTO getDimensionGroup(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return this.defaultDimensionGroup();
        }
        String dw = taskDefine.getDw();
        DimensionGroupDTO dimensionGroupDTO = this.initDimensionGroupDTO(taskKey, dw);
        if (!BaseDataAdapterUtil.isBaseData((String)dw)) {
            return dimensionGroupDTO;
        }
        dimensionGroupDTO.setEntityId(dw);
        IEntityModel entityModel = this.getEntityModel(dw);
        if (entityModel != null) {
            HashMap<String, InnerField> innerFieldMap = new HashMap<String, InnerField>();
            Map<String, IEntityRefer> entityReferMap = this.queryEntityRefer(dw);
            HashMap<String, ReferEntity> referEntityMap = new HashMap<String, ReferEntity>();
            this.filterFields(entityModel).forEach(a -> {
                IEntityRefer entityRefer;
                InnerField innerField = new InnerField(a.getCode(), a.getTitle(), dw);
                if (a.getReferColumnID() != null && (entityRefer = (IEntityRefer)entityReferMap.get(a.getCode())) != null) {
                    innerField.setReferEntityId(entityRefer.getReferEntityId());
                    referEntityMap.put(a.getCode(), new ReferEntity(entityRefer.getReferEntityId(), entityRefer.getReferEntityField(), dw, a.getCode()));
                }
                innerFieldMap.put(a.getCode(), innerField);
            });
            dimensionGroupDTO.setInnerFieldMap(innerFieldMap);
            this.initReferEntityMap(referEntityMap);
            dimensionGroupDTO.setReferEntityMap(referEntityMap);
            return dimensionGroupDTO;
        }
        return dimensionGroupDTO;
    }

    private DimensionGroupDTO defaultDimensionGroup() {
        DimensionGroupDTO dimensionGroupDTO = new DimensionGroupDTO();
        dimensionGroupDTO.setGroupKey("DIMENSION_GROUP");
        dimensionGroupDTO.setFilterKey("DIMENSION_FILTER");
        return dimensionGroupDTO;
    }

    private IEntityModel getEntityModel(String dw) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        if (entityMetaService != null) {
            return entityMetaService.getEntityModel(dw);
        }
        return null;
    }

    private Stream<IEntityAttribute> filterFields(IEntityModel entityModel) {
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        return entityModel.getShowFields().stream().filter(o -> o.getColumnType() == ColumnModelType.STRING).filter(o -> !o.isMultival());
    }

    private DimensionGroupDTO initDimensionGroupDTO(String taskKey, String dw) {
        DimensionGroupDTO dimensionGroupDTO = this.defaultDimensionGroup();
        String groupValue = this.taskOptionController.getValue(taskKey, "DIMENSION_GROUP");
        if (groupValue != null) {
            if (groupValue.contains(dw)) {
                dimensionGroupDTO.setGroupValue(groupValue);
                String filterValue = this.taskOptionController.getValue(taskKey, "DIMENSION_FILTER");
                dimensionGroupDTO.setFilterValue(filterValue);
            } else {
                this.taskOptionController.setValue(taskKey, "DIMENSION_GROUP", null);
                this.taskOptionController.setValue(taskKey, "DIMENSION_FILTER", null);
            }
        }
        return dimensionGroupDTO;
    }

    private void initReferEntityMap(Map<String, ReferEntity> referEntityMap) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        referEntityMap.forEach((code, referEntity) -> {
            HashMap<String, InnerField> referInnerFieldMap = new HashMap<String, InnerField>();
            IEntityModel referModel = entityMetaService.getEntityModel(referEntity.getEntityId());
            referModel.getShowFields().stream().filter(o -> o.getColumnType() == ColumnModelType.STRING).forEach(x -> referInnerFieldMap.put(x.getCode(), new InnerField(x.getCode(), x.getTitle(), referEntity.getEntityId())));
            referEntity.setInnerFieldMap(referInnerFieldMap);
            referEntityMap.put((String)code, (ReferEntity)referEntity);
        });
    }

    public Map<String, IEntityRefer> queryEntityRefer(String entityId) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        List entityRefer = entityMetaService.getEntityRefer(entityId);
        return entityRefer.stream().collect(Collectors.toMap(IEntityRefer::getOwnField, f -> f));
    }

    @Override
    public String[] getGroupOptionValue(String taskKey) {
        String value;
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        String dw = taskDefine.getDw();
        String dataSchemeKey = taskDefine.getDataScheme();
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        Set dimSet = dataSchemeDimension.stream().map(DataDimension::getDimKey).collect(Collectors.toSet());
        if (dimSet.contains(dw) && StringUtils.hasLength(value = this.taskOptionController.getValue(taskKey, "DIMENSION_GROUP")) && value.indexOf(";") > -1) {
            String[] split = value.split(";");
            if (dimSet.contains(split[0])) {
                return split;
            }
            this.taskOptionController.setValue(taskKey, "DIMENSION_GROUP", null);
            this.taskOptionController.setValue(taskKey, "DIMENSION_FILTER", null);
            return new String[0];
        }
        return new String[0];
    }

    @Override
    public String[] getFilterOptionValue(String taskKey) {
        String value = this.taskOptionController.getValue(taskKey, "DIMENSION_FILTER");
        if (StringUtils.hasLength(value)) {
            return value.split(";");
        }
        return new String[0];
    }

    @Override
    public GroupInfo getGroupInfo(String taskKey) {
        String first = this.taskOptionController.getValue(taskKey, "DIMENSION_GROUP");
        String second = this.taskOptionController.getValue(taskKey, "DIMENSION_FILTER");
        return new GroupInfoDTO(first, second);
    }
}

