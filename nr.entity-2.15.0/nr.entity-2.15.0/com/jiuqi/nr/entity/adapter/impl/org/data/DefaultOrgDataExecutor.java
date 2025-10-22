/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.organization.service.OrgAuthService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.entity.adapter.impl.org.data;

import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.entity.adapter.executor.IExtendDataExecutor;
import com.jiuqi.nr.entity.adapter.impl.org.OrgDataCheck;
import com.jiuqi.nr.entity.adapter.impl.org.OrganizationAdapterImpl;
import com.jiuqi.nr.entity.adapter.impl.org.auth.dao.AuthQueryDao;
import com.jiuqi.nr.entity.adapter.impl.org.client.OrgAdapterClient;
import com.jiuqi.nr.entity.adapter.impl.org.data.OrgDataSource;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataDBQuery;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataModifier;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDataQuery;
import com.jiuqi.nr.entity.adapter.impl.org.data.query.OrgDefineQuery;
import com.jiuqi.nr.entity.adapter.impl.org.db.DataBaseQueryHelper;
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
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component(value="defaultOrgDataExecutor")
public class DefaultOrgDataExecutor
implements IExtendDataExecutor<OrganizationAdapterImpl> {
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
    protected List<OrgDataSource> orgDataSource;
    @Autowired
    protected OrgAuthService orgAuthService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrgIdentityService orgIdentityService;
    @Autowired
    private AuthQueryDao authQueryDao;
    @Autowired
    private UserService<User> userService;

    @Autowired
    public void setOrgDataSource(List<OrgDataSource> orgDataSource) {
        orgDataSource.sort(Comparator.comparing(OrgDataSource::order));
        this.orgDataSource = orgDataSource;
    }

    @Override
    public boolean isEnable(IEntityQueryParam queryParam, ProviderMethodEnum providerEnum) {
        return true;
    }

    @Override
    public double getOrder() {
        return 10.0;
    }

    @Override
    public IDataQueryProvider getDataQueryProvider(IEntityQueryParam queryParam) {
        boolean dbMode = queryParam.isDbMode();
        queryParam.getQueryContext().getLogger().accept(e -> {
            if (e.isTraceEnabled()) {
                e.trace("DBMode: {}, OrgDataSource:{}", dbMode, this.orgDataSource.getClass().getSimpleName());
            }
        });
        if (dbMode) {
            DataBaseQueryHelper dataBaseQueryHelper = new DataBaseQueryHelper(this.userService, this.orgIdentityService, this.authQueryDao);
            return new OrgDataDBQuery(this.jdbcTemplate, dataBaseQueryHelper, this.systemIdentityService, this.orgCategoryClient, this.getOrgDataSource(), queryParam);
        }
        return new OrgDataQuery(this.getOrgDataSource(), queryParam);
    }

    @Override
    public IDataModifyProvider getDataModifyProvider() {
        return new OrgDataModifier(this.systemIdentityService, this.orgAdapterClient, this.orgDataCheck, this.orgDataClient, this.orgAuthService);
    }

    @Override
    public IDefineQueryProvider getOrgDefineProvider() {
        return new OrgDefineQuery(this.orgCategoryClient, this.vaDataModelClient, this.dataModelService);
    }

    protected OrgDataSource getOrgDataSource() {
        for (OrgDataSource dataSource : this.orgDataSource) {
            if (!dataSource.enable()) continue;
            return dataSource;
        }
        return this.orgDataSource.get(0);
    }
}

