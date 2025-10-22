/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService
 *  com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.service.impl.DefaultExecutorContextFactory
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.ext.dataentry;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService;
import com.jiuqi.nr.data.estimation.service.IEstimationSubDatabaseHelper;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.web.ext.dataengine.EstimationExecEnvironment;
import com.jiuqi.nr.data.estimation.web.ext.dataengine.QueryTableNameFinder;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.service.impl.DefaultExecutorContextFactory;
import com.jiuqi.util.StringUtils;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class DataEntryQueryContextExt
extends DefaultExecutorContextFactory {
    @Resource
    private IEstimationSchemeUserService schemeUserService;
    @Resource
    private IEstimationSubDatabaseHelper subDatabaseHelper;

    protected IFmlExecEnvironment getIFmlExecEnvironment(ExecutorContext executorContext, RegionRelation regionRelation) {
        DimensionValueSet dimValueSet;
        IEstimationScheme estimationScheme;
        String estimationSchemeKey = this.getEstimationSchemeKey(executorContext);
        IFmlExecEnvironment superEnv = super.getIFmlExecEnvironment(executorContext, regionRelation);
        if (StringUtils.isNotEmpty((String)estimationSchemeKey) && (estimationScheme = this.schemeUserService.findEstimationScheme(estimationSchemeKey, dimValueSet = executorContext.getVarDimensionValueSet())) != null) {
            Map ori2CopiedTableMap = this.subDatabaseHelper.getOri2CopiedTableMap(estimationScheme);
            Map otherPrimaryMap = this.subDatabaseHelper.getOtherPrimaryMap(estimationScheme);
            QueryTableNameFinder tableNameFinder = new QueryTableNameFinder(otherPrimaryMap, ori2CopiedTableMap);
            return new EstimationExecEnvironment(superEnv, tableNameFinder);
        }
        return superEnv;
    }

    private String getEstimationSchemeKey(ExecutorContext executorContext) {
        VariableManager variableManager = executorContext.getVariableManager();
        Variable variable = variableManager.find("estimationScheme");
        if (variable != null) {
            try {
                return variable.getVarValue((IContext)executorContext).toString();
            }
            catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}

