/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.batch.summary.storage.common;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class BatchSummaryHelper {
    public static final String CORPORATE_DEFAULT_VALUE = "DEFAULT_TEMP_COPORATE_VALUE";
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityDataService dataService;
    @Autowired
    private IDataDefinitionRuntimeController tbRtCtl;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    @Deprecated
    public boolean isCorporate(TaskDefine taskDefine, DataDimension dimension) {
        String dimAttribute = dimension.getDimAttribute();
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(this.dataAccesslUtil.contextEntityId(taskDefine.getDw()));
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        String dimReferAttr = this.getDimAttributeByReportDim(taskDefine, dimension.getDimKey());
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }

    public List<DataDimension> getSingleDimensions(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        return this.getSingleDimensions(taskDefine);
    }

    public List<DataDimension> getSingleDimensions(TaskDefine taskDefine) {
        List<DataDimension> dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        if (CollectionUtils.isEmpty(dataSchemeDimension)) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(dataSchemeDimension = dataSchemeDimension.stream().filter(r -> !StringUtils.isEmpty((String)r.getDimAttribute())).collect(Collectors.toList()))) {
            return Collections.emptyList();
        }
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(this.dataAccesslUtil.contextEntityId(taskDefine.getDw()));
        Iterator iterator = dataSchemeDimension.iterator();
        while (iterator.hasNext()) {
            DataDimension dimension = (DataDimension)iterator.next();
            String dimAttribute = dimension.getDimAttribute();
            IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
            if (attribute != null && !attribute.isMultival()) continue;
            iterator.remove();
        }
        return dataSchemeDimension;
    }

    public String getDimAttributeByReportDim(TaskDefine taskDefine, String dimKey) {
        String dataSchemeKey = taskDefine.getDataScheme();
        if (dataSchemeKey == null) {
            return null;
        }
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        DataDimension report = dimensions.stream().filter(dataDimension -> dimKey.equals(dataDimension.getDimKey())).findFirst().orElse(null);
        return report == null ? null : report.getDimAttribute();
    }

    public String getCorporateDefaultValue(SummaryScheme scheme) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(scheme.getTask());
        List<DataDimension> singleDimensions = this.getSingleDimensions(taskDefine);
        if (CollectionUtils.isEmpty(singleDimensions)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (DataDimension singleDimension : singleDimensions) {
            IEntityRow row;
            IEntityTable entityTable = this.getIEntityTable(singleDimension.getDimKey());
            List allRows = entityTable.getAllRows();
            if (allRows == null || allRows.isEmpty()) continue;
            String entityKeyData = null;
            Iterator iterator = allRows.iterator();
            while (iterator.hasNext() && !StringUtils.isNotEmpty((String)(entityKeyData = (row = (IEntityRow)iterator.next()).getEntityKeyData()))) {
            }
            if (entityKeyData == null) {
                entityKeyData = CORPORATE_DEFAULT_VALUE;
            }
            sb.append(singleDimension.getDimKey()).append(":").append(entityKeyData);
        }
        return sb.toString();
    }

    public IEntityTable getIEntityTable(String entityId) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setMasterKeys(masterKeys);
        query.setAuthorityOperations(AuthorityType.Read);
        query.markLeaf();
        query.lazyQuery();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        entityViewDefine.setEntityId(entityDefine.getId());
        query.setEntityView((EntityViewDefine)entityViewDefine);
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        try {
            return query.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCorporateDefaultEntityId(String taskKey) {
        List taskOrgLinkDefines = this.iRunTimeViewController.listTaskOrgLinkByTask(taskKey);
        if (CollectionUtils.isEmpty(taskOrgLinkDefines)) {
            return null;
        }
        if (taskOrgLinkDefines.size() == 1) {
            return ((TaskOrgLinkDefine)taskOrgLinkDefines.get(0)).getEntity();
        }
        TaskDefine task = this.iRunTimeViewController.getTask(taskKey);
        return task.getDw();
    }
}

