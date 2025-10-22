/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService
 *  com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.logic.facade.extend.FmlExeContextProvider
 *  com.jiuqi.nr.data.logic.facade.param.base.BaseEnv
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.data.estimation.web.ext.dataengine;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService;
import com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.web.ext.dataengine.CalculateTableNameFinder;
import com.jiuqi.nr.data.estimation.web.ext.dataengine.EstimationExecEnvironment;
import com.jiuqi.nr.data.logic.facade.extend.FmlExeContextProvider;
import com.jiuqi.nr.data.logic.facade.param.base.BaseEnv;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataEntryCalculateContextExt
implements FmlExeContextProvider {
    private IRunTimeViewController rtvService;
    private IEstimationSchemeUserService schemeUserService;
    private IEstimationSubDatabaseHelper subDatabaseHelper;

    public DataEntryCalculateContextExt(IEstimationSchemeUserService schemeUserService, IRunTimeViewController rtvService, IEstimationSubDatabaseHelper subDatabaseHelper) {
        this.rtvService = rtvService;
        this.schemeUserService = schemeUserService;
        this.subDatabaseHelper = subDatabaseHelper;
    }

    public ExecutorContext get(ExecutorContext context, BaseEnv env) {
        String estimationSchemeKey = this.getEstimationSchemeKey(env.getVariableMap());
        if (StringUtils.isNotEmpty((String)estimationSchemeKey)) {
            DimensionCollection dimensionCollection = env.getDimensionCollection();
            DimensionValueSet dimValueSet = dimensionCollection.combineDim();
            IEstimationScheme estimationScheme = this.schemeUserService.findEstimationScheme(estimationSchemeKey, dimValueSet);
            List<String> subDataRegionKeys = this.getSubDataRegionKeys(estimationScheme);
            Map ori2CopiedTableMap = this.subDatabaseHelper.getOri2CopiedTableMap(estimationScheme);
            Map otherPrimaryMap = this.subDatabaseHelper.getOtherPrimaryMap(estimationScheme);
            CalculateTableNameFinder tableNameFinder = new CalculateTableNameFinder(otherPrimaryMap, ori2CopiedTableMap, subDataRegionKeys);
            context.setEnv((IFmlExecEnvironment)new EstimationExecEnvironment(context.getEnv(), tableNameFinder));
        }
        return context;
    }

    private String getEstimationSchemeKey(Map<String, Object> variableMap) {
        return variableMap.get("estimationScheme").toString();
    }

    private List<String> getSubDataRegionKeys(IEstimationScheme estimationScheme) {
        List<FormDefine> esFormDefines = estimationScheme.getEstimationForms().stream().map(IEstimationForm::getFormDefine).collect(Collectors.toList());
        ArrayList regions = new ArrayList();
        esFormDefines.forEach(formDefine -> {
            List allRegionsInForm = this.rtvService.getAllRegionsInForm(formDefine.getKey());
            if (allRegionsInForm != null) {
                regions.addAll(allRegionsInForm);
            }
        });
        return regions.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
    }
}

