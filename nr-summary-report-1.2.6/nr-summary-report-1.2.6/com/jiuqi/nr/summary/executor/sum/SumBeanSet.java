/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.intf.IDataChangeListener
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflowService
 *  com.jiuqi.nr.calibre2.ICalibreDataService
 *  com.jiuqi.nr.calibre2.ICalibreDefineService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.option.DimGroupOptionService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.summary.executor.sum;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflowService;
import com.jiuqi.nr.calibre2.ICalibreDataService;
import com.jiuqi.nr.calibre2.ICalibreDefineService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.option.DimGroupOptionService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.summary.api.service.IRuntimeSummarySolutionService;
import com.jiuqi.nr.summary.internal.service.IRuntimeSummaryFormulaService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SumBeanSet {
    @Autowired
    public IRunTimeViewController runTimeViewController;
    @Autowired
    public IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    public IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    public IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    public IEntityMetaService entityMetaService;
    @Autowired
    public IEntityDataService entityDataService;
    @Autowired
    public ICalibreDataService calibreDataService;
    @Autowired
    public ICalibreDefineService calibreDefineService;
    @Autowired
    public IDataAccessProvider dataAccessProvider;
    @Autowired
    public DataModelService dataModelService;
    @Autowired
    public IRuntimeSummaryFormulaService summaryFormulaService;
    @Autowired
    public IRuntimeSummarySolutionService summarySolutionService;
    @Autowired
    public IWorkflowService workflowService;
    @Autowired
    public DimGroupOptionService dimGroupOptionService;
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired
    private EntityIdentityService entityLinkService;
    @Autowired(required=false)
    private List<IDataChangeListener> dataChangeListeners;
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;

    public QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.dataDefinitionController);
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setDataChangeListeners(this.dataChangeListeners);
        queryParam.setSplitTableHelper(this.splitTableHelper);
        return queryParam;
    }
}

