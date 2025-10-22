/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.nr.batch.summary.storage.common.BatchSummaryHelper
 *  com.jiuqi.nr.batch.summary.storage.entity.SingleDim
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.data.access.util.DataAccesslUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.batch.summary.storage.common.BatchSummaryHelper;
import com.jiuqi.nr.batch.summary.storage.entity.SingleDim;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityFrameExtendHelper {
    @Resource
    private BSSchemeService schemeService;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private BatchSummaryHelper batchSummaryHelper;
    @Autowired
    private DataAccesslUtil dataAccesslUtil;

    public VariableManager getVariableManager(IContext context) {
        if (context instanceof com.jiuqi.nr.entity.engine.executors.ExecutorContext) {
            return ((com.jiuqi.nr.entity.engine.executors.ExecutorContext)context).getVariableManager();
        }
        if (context instanceof ExecutorContext) {
            return ((ExecutorContext)context).getVariableManager();
        }
        return null;
    }

    public Object getVariableValue(IContext context, VariableManager variableManager, String varName) {
        Variable variable = variableManager.find(varName);
        try {
            return variable.getVarValue(context);
        }
        catch (Exception e) {
            return null;
        }
    }

    public SummaryScheme getSummarySchemeFromEnv(IContext context) {
        VariableManager variableManager = this.getVariableManager(context);
        Object variableValue = this.getVariableValue(context, variableManager, "batchGatherSchemeCode");
        if (variableValue != null) {
            return this.schemeService.findScheme(variableValue.toString());
        }
        return null;
    }

    public boolean isBatchSummaryEnv(IEntityQueryParam queryParam, ProviderMethodEnum providerEnum) {
        if (!providerEnum.equals((Object)ProviderMethodEnum.DATA_QUERY)) {
            return false;
        }
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = queryParam.getContext();
        VariableManager variableManager = this.getVariableManager((IContext)context);
        return variableManager != null && variableManager.find("batchGatherSchemeCode") != null;
    }

    public Map<String, CorporateEntityData> getCorporateColumn2Value(SummaryScheme scheme) {
        HashMap<String, CorporateEntityData> column2Value = new HashMap<String, CorporateEntityData>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(scheme.getTask());
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(this.dataAccesslUtil.contextEntityId(taskDefine.getDw()));
        List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dimension : dataSchemeDimension) {
            String dimReferAttr;
            String dimAttribute;
            IEntityAttribute attribute;
            if (DimensionType.DIMENSION != dimension.getDimensionType() || (attribute = dwEntityModel.getAttribute(dimAttribute = dimension.getDimAttribute())) == null || attribute.isMultival() || !StringUtils.isNotEmpty((String)(dimReferAttr = this.batchSummaryHelper.getDimAttributeByReportDim(taskDefine, dimension.getDimKey())))) continue;
            CorporateEntityData corporateEntityData = this.getCorporateEntityData(scheme, dimension);
            column2Value.put(attribute.getCode(), corporateEntityData);
        }
        return column2Value;
    }

    @Deprecated
    public boolean isCorporate(TaskDefine taskDefine, DataDimension dimension) {
        return this.batchSummaryHelper.isCorporate(taskDefine, dimension);
    }

    public List<DataDimension> getSingleDimensions(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        return this.batchSummaryHelper.getSingleDimensions(taskDefine);
    }

    public List<DataDimension> getSingleDimensions(TaskDefine taskDefine) {
        return this.batchSummaryHelper.getSingleDimensions(taskDefine);
    }

    public CorporateEntityData getCorporateEntityData(SummaryScheme scheme, DataDimension dimension) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(dimension.getDimKey());
        TableModelDefine tableModel = this.entityMetaService.getTableModel(dimension.getDimKey());
        for (SingleDim singleDim : scheme.getSingleDims()) {
            if (!singleDim.getEntityId().equals(dimension.getDimKey())) continue;
            CorporateEntityData corporateEntityData = new CorporateEntityData(singleDim.getValue(), singleDim.getValue(), tableModel.getTitle());
            corporateEntityData.setEntityId(entityDefine.getId());
            corporateEntityData.setEntityCode(entityDefine.getCode());
            corporateEntityData.setDimensionName(entityDefine.getDimensionName());
            return corporateEntityData;
        }
        return null;
    }
}

