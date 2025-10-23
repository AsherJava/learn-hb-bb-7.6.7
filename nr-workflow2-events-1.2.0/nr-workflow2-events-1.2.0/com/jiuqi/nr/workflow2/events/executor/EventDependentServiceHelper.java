/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.data.gather.refactor.factory.IDataGatherServiceFactory
 *  com.jiuqi.nr.data.logic.api.IDataLogicServiceFactory
 *  com.jiuqi.nr.data.logic.facade.service.ICalculateService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.dataentry.service.IFinalaccountsAuditService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.multcheck2.service.IMCExecuteUploadMultiService
 *  com.jiuqi.nr.multcheck2.service.IMCExecuteUploadSingleService
 *  com.jiuqi.nr.snapshot.service.SnapshotService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 */
package com.jiuqi.nr.workflow2.events.executor;

import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.data.gather.refactor.factory.IDataGatherServiceFactory;
import com.jiuqi.nr.data.logic.api.IDataLogicServiceFactory;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.dataentry.service.IFinalaccountsAuditService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteUploadMultiService;
import com.jiuqi.nr.multcheck2.service.IMCExecuteUploadSingleService;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.events.helper.EventExecuteDimensionBuilder;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class EventDependentServiceHelper {
    @Autowired
    IDataLogicServiceFactory dataLogicServiceFactory;
    @Autowired
    IMCExecuteUploadSingleService singleMCExecuteService;
    @Autowired
    IMCExecuteUploadMultiService multiMCExecuteService;
    @Autowired
    ICalculateService calculateService;
    @Autowired
    SnapshotService dataSnapshotService;
    @Autowired
    ICheckResultService checkResultService;
    @Autowired
    IReportDimensionHelper dimensionHelper;
    @Autowired
    IProcessQueryService processQueryService;
    @Autowired
    AuditTypeDefineService auditTypeDefineService;
    @Autowired
    IProcessEntityQueryHelper entityQueryHelper;
    @Autowired
    WorkflowSettingsService workflowSettingsService;
    @Autowired
    IProcessRuntimeParamHelper runtimeParamHelper;
    @Autowired
    IDataGatherServiceFactory dataGatherServiceFactory;
    @Autowired
    IFinalaccountsAuditService finalAccountsAuditService;
    @Autowired
    EventExecuteDimensionBuilder eventExecuteDimensionBuilder;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IProcessMetaDataService processMetaDataService;
    @Autowired
    IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    IFormConditionAccessService formConditionAccessService;
}

