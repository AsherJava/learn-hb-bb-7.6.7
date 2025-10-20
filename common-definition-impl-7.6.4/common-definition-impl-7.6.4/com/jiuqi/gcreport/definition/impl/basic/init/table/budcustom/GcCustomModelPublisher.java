/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.param.hypermodel.domain.IHyperDataScheme
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO
 *  com.jiuqi.budget.param.hypermodel.domain.ModelShowMeasurementDTO
 *  com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO
 *  com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType
 *  com.jiuqi.budget.param.hypermodel.service.IModelPublisher
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom;

import com.jiuqi.budget.param.hypermodel.domain.IHyperDataScheme;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowDimensionDTO;
import com.jiuqi.budget.param.hypermodel.domain.ModelShowMeasurementDTO;
import com.jiuqi.budget.param.hypermodel.domain.ShowModelDTO;
import com.jiuqi.budget.param.hypermodel.domain.enums.DataSchemeType;
import com.jiuqi.budget.param.hypermodel.service.IModelPublisher;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.DimPublishMessage;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.DimPublishMessageSubscriber;
import com.jiuqi.gcreport.definition.impl.basic.init.table.budcustom.service.DimPublishManageService;
import com.jiuqi.gcreport.definition.impl.basic.init.table.event.HyperDimensionPublishedEvent;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.ICustomPublishTable;
import com.jiuqi.gcreport.definition.impl.basic.init.table.util.DimensionPublishUtil;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GcCustomModelPublisher
implements IModelPublisher {
    private static final Logger logger = LoggerFactory.getLogger(GcCustomModelPublisher.class);
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DimPublishMessageSubscriber dimPublishMessageSubscriber;
    @Autowired
    private DimPublishManageService dimPublishManageService;

    public boolean isMatch(IHyperDataScheme hyperDataSchemeDO, ShowModelDTO modelDTO) {
        return DataSchemeType.OTHER.getHexCode().equals(hyperDataSchemeDO.getDataSchemeType().getHexCode());
    }

    public void doPublish(IHyperDataScheme hyperDataSchemeDO, ShowModelDTO modelDTO) {
        EntityTableCollector entityTableCollector = EntityTableCollector.getInstance();
        List<DefinitionTableV> definitionTableList = entityTableCollector.getModelTableMap().get(modelDTO.getCode());
        if (!CollectionUtils.isEmpty(definitionTableList)) {
            this.doDimPublish(modelDTO, entityTableCollector, definitionTableList);
            return;
        }
        String id = UUIDUtils.newHalfGUIDStr();
        this.dimPublishManageService.insertPublishInfo(id, modelDTO.getCode());
        String message = JsonUtils.writeValueAsString((Object)new DimPublishMessage(id, modelDTO));
        this.dimPublishMessageSubscriber.sendMessage(message);
    }

    public void doDimPublish(ShowModelDTO modelDTO, EntityTableCollector entityTableCollector, List<DefinitionTableV> definitionTableList) {
        List measurementDOs;
        BaseEntity entity = entityTableCollector.getEntityByName(modelDTO.getCode());
        DBTable dbTableByType = entityTableCollector.getDbTableByType(entity.getClass());
        ArrayList designColumnModelDefineList = CollectionUtils.newArrayList();
        List dimensionDOs = modelDTO.getDimensionDOs();
        if (!CollectionUtils.isEmpty((Collection)dimensionDOs)) {
            for (ModelShowDimensionDTO dimension : dimensionDOs) {
                designColumnModelDefineList.add(DimensionPublishUtil.toDesignFieldDefineImpl(dimension, dbTableByType.extendFieldDefaultVal()));
            }
        }
        if (!CollectionUtils.isEmpty((Collection)(measurementDOs = modelDTO.getMeasurementDOs()))) {
            for (ModelShowMeasurementDTO dimension : measurementDOs) {
                designColumnModelDefineList.add(DimensionPublishUtil.toDesignFieldDefineImpl(dimension, dbTableByType.extendFieldDefaultVal()));
            }
        }
        if (entity instanceof ICustomPublishTable) {
            designColumnModelDefineList.addAll(((ICustomPublishTable)((Object)entity)).customColumnList(modelDTO));
        }
        if (!CollectionUtils.isEmpty((Collection)designColumnModelDefineList)) {
            for (DefinitionTableV definitionTable : definitionTableList) {
                try {
                    DesignTableModelDefine tableModelDefine = null;
                    tableModelDefine = !ObjectUtils.isEmpty(definitionTable.getDataSource()) ? this.designDataModelService.getTableModelDefineByCode(definitionTable.getTableName(), definitionTable.getDataSource()) : this.designDataModelService.getTableModelDefineByCode(definitionTable.getTableName());
                    if (Objects.isNull(tableModelDefine)) continue;
                    ArrayList insertDefineList = CollectionUtils.newArrayList();
                    ArrayList updateDefineList = CollectionUtils.newArrayList();
                    if (!CollectionUtils.isEmpty((Collection)designColumnModelDefineList)) {
                        for (DesignColumnModelDefine dimension : designColumnModelDefineList) {
                            dimension.setTableID(tableModelDefine.getID());
                            DesignColumnModelDefine oldField = this.designDataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), dimension.getCode());
                            if (oldField == null) {
                                dimension.setID(UUIDUtils.newUUIDStr());
                                insertDefineList.add(dimension);
                                continue;
                            }
                            if (dimension.getPrecision() <= oldField.getPrecision()) continue;
                            oldField.setPrecision(oldField.getPrecision());
                            updateDefineList.add(oldField);
                        }
                    }
                    if (!CollectionUtils.isEmpty((Collection)insertDefineList)) {
                        this.designDataModelService.insertColumnModelDefines(insertDefineList.toArray(new DesignColumnModelDefine[0]));
                    }
                    if (!CollectionUtils.isEmpty((Collection)updateDefineList)) {
                        this.designDataModelService.updateColumnModelDefines(updateDefineList.toArray(new DesignColumnModelDefine[0]));
                    }
                    if (CollectionUtils.isEmpty((Collection)updateDefineList) && CollectionUtils.isEmpty((Collection)insertDefineList)) continue;
                    this.dataModelDeployService.deployTable(tableModelDefine.getID());
                }
                catch (Exception e) {
                    logger.error("\u7269\u7406\u8868\u3010{}\u3011\u53d1\u5e03\u5931\u8d25\u3002", (Object)definitionTable.getTableName(), (Object)e);
                }
            }
        }
        ApplicationContextRegister.getApplicationContext().publishEvent(new HyperDimensionPublishedEvent((Object)this, modelDTO));
    }

    public int getOrder() {
        return -1;
    }
}

