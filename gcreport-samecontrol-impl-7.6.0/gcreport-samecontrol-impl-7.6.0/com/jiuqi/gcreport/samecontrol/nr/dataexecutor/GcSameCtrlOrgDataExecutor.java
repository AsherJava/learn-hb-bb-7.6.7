/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.entity.adapter.executor.IExtendDataExecutor
 *  com.jiuqi.nr.entity.adapter.impl.org.OrgDataCheck
 *  com.jiuqi.nr.entity.adapter.impl.org.OrganizationAdapterImpl
 *  com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient
 *  com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource
 *  com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataModifier
 *  com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDefineQuery
 *  com.jiuqi.nr.entity.adapter.provider.IDataModifyProvider
 *  com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider
 *  com.jiuqi.nr.entity.adapter.provider.IDefineQueryProvider
 *  com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.param.IEntityQueryParam
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.service.OrgAuthService
 */
package com.jiuqi.gcreport.samecontrol.nr.dataexecutor;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.gcreport.samecontrol.nr.dataquery.GcSameCtrlOrgDataQuery;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.entity.adapter.executor.IExtendDataExecutor;
import com.jiuqi.nr.entity.adapter.impl.org.OrgDataCheck;
import com.jiuqi.nr.entity.adapter.impl.org.OrganizationAdapterImpl;
import com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient;
import com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataModifier;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDefineQuery;
import com.jiuqi.nr.entity.adapter.provider.IDataModifyProvider;
import com.jiuqi.nr.entity.adapter.provider.IDataQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.IDefineQueryProvider;
import com.jiuqi.nr.entity.adapter.provider.ProviderMethodEnum;
import com.jiuqi.nr.entity.param.IEntityQueryParam;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.organization.service.OrgAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcSameCtrlOrgDataExecutor
implements IExtendDataExecutor<OrganizationAdapterImpl> {
    private static final Logger logger = LoggerFactory.getLogger(GcSameCtrlOrgDataExecutor.class);
    @Autowired
    protected OrgDataClient orgDataClient;
    @Autowired
    protected SystemIdentityService systemIdentityService;
    @Autowired
    protected OrgAdapterClient orgAdapterClient;
    @Autowired
    protected OrgDataCheck orgDataCheck;
    @Autowired
    protected OrgCategoryClient orgCategoryClient;
    @Autowired
    protected DataModelClient vaDataModelClient;
    @Autowired
    protected DataModelService dataModelService;
    @Autowired
    protected OrgDataSource orgDataSource;
    @Autowired
    protected OrgAuthService orgAuthService;
    private final String GCSAMECTRL_RECOVERYFLAG_ORGTYPE = "GCSAMECTRL_RECOVERYFLAG_ORGTYPE";

    public boolean isEnable(IEntityQueryParam queryParam, ProviderMethodEnum providerEnum) {
        ExecutorContext executorContext;
        Variable variable;
        if (queryParam == null) {
            return false;
        }
        com.jiuqi.nr.entity.engine.executors.ExecutorContext iContext = queryParam.getContext();
        if (iContext != null && iContext instanceof ExecutorContext && (variable = (executorContext = (ExecutorContext)iContext).getVariableManager().find("GCSAMECTRL_RECOVERYFLAG_ORGTYPE")) != null) {
            try {
                boolean isSameCtrlOrg = (Boolean)variable.getVarValue((IContext)iContext);
                if (isSameCtrlOrg) {
                    return true;
                }
            }
            catch (Exception e) {
                logger.error("\u540c\u63a7\u83b7\u53d6\u5355\u4f4d\u5904\u7406\u5668\u5f02\u5e38" + e.getMessage(), e);
            }
        }
        return false;
    }

    public double getOrder() {
        return 1.0;
    }

    public IDataQueryProvider getDataQueryProvider(IEntityQueryParam queryParam) {
        return new GcSameCtrlOrgDataQuery(this.getOrgDataSource(), queryParam);
    }

    public IDataModifyProvider getDataModifyProvider() {
        return new OrgDataModifier(this.systemIdentityService, this.orgAdapterClient, this.orgDataCheck, this.orgDataClient, this.orgAuthService);
    }

    public IDefineQueryProvider getOrgDefineProvider() {
        return new OrgDefineQuery(this.orgCategoryClient, this.vaDataModelClient, this.dataModelService);
    }

    protected OrgDataSource getOrgDataSource() {
        return this.orgDataSource;
    }
}

