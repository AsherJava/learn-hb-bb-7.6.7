/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.np.NpReportQueryProvider
 *  com.jiuqi.np.dataengine.IDataQueryFactory
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.query;

import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.gcreport.inputdata.dataentryext.inputdata.query.GcDataQueryImpl;
import com.jiuqi.gcreport.inputdata.query.GcGroupingQueryImpl;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.query.exception.ErrorTabsParamException;
import com.jiuqi.np.dataengine.IDataQueryFactory;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcDataQueryFactory
implements IDataQueryFactory,
InitializingBean {
    private static final List<String> formCodes = Arrays.asList("FMDM", "MD_ORG_CORPORATE", "MD_ORG_MANAGEMENT");
    @Autowired
    private NpReportQueryProvider provider;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.provider.getDataAccessProvider().registerDataQuery((IDataQueryFactory)this);
        this.provider.getiGroupingAccessProvider().registerDataQuery((IDataQueryFactory)this);
    }

    public IDataQuery getDataQuery(QueryEnvironment queryEnvironment) {
        if (queryEnvironment == null) {
            return null;
        }
        GcDataEntryContext gcContext = null;
        if (!formCodes.contains(queryEnvironment.getFormCode())) {
            try {
                gcContext = new GcDataEntryContext(queryEnvironment, this.provider);
            }
            catch (Exception e) {
                gcContext = null;
            }
        }
        IDataQuery dataQueryImpl = gcContext != null && gcContext.isGcQuery() ? this.createGcDataQuery(gcContext) : null;
        return dataQueryImpl;
    }

    public IGroupingQuery getGroupingQuery(QueryEnvironment queryEnvironment) {
        if (queryEnvironment == null) {
            return null;
        }
        GcDataEntryContext gcContext = null;
        if (!formCodes.contains(queryEnvironment.getFormCode())) {
            try {
                gcContext = new GcDataEntryContext(queryEnvironment, this.provider);
            }
            catch (Exception e) {
                gcContext = null;
            }
        }
        IGroupingQuery groupingQuery = gcContext != null && gcContext.isGcQuery() ? this.createGcGroupingQuery(gcContext) : null;
        return groupingQuery;
    }

    private IGroupingQuery createGcGroupingQuery(GcDataEntryContext gcContext) {
        GcGroupingQueryImpl groupingQueryImpl = new GcGroupingQueryImpl(gcContext);
        QueryParam queryParam = this.getQueryParam();
        groupingQueryImpl.setQueryParam(queryParam);
        groupingQueryImpl.setRowFilter(gcContext.getRegionDefine().getFilterCondition());
        return groupingQueryImpl;
    }

    private IDataQuery createGcDataQuery(GcDataEntryContext gcContext) {
        try {
            GcDataQueryImpl dataQueryImpl = new GcDataQueryImpl(gcContext);
            QueryParam queryParam = this.getQueryParam();
            dataQueryImpl.setQueryParam(queryParam);
            dataQueryImpl.setRowFilter(gcContext.getRegionDefine().getFilterCondition());
            return dataQueryImpl;
        }
        catch (Exception e) {
            throw new ErrorTabsParamException(e);
        }
    }

    private QueryParam getQueryParam() {
        return this.provider.getQueryParam();
    }
}

