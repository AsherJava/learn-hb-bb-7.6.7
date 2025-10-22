/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl
 */
package com.jiuqi.gcreport.inputdata.query.updater;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.query.base.GcQuerySqlParam;
import com.jiuqi.gcreport.inputdata.query.constant.EntitiesOrgMode;
import com.jiuqi.gcreport.inputdata.query.constant.InnerTableTabsType;
import com.jiuqi.gcreport.inputdata.query.constant.InputDataQueryFilterType;
import com.jiuqi.gcreport.inputdata.query.updater.IGcQuerySqlUpdater;
import com.jiuqi.gcreport.inputdata.query.util.GcQuerySqlAdapterUtils;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GcQuerySqlUpdater
implements IGcQuerySqlUpdater {
    private GcDataEntryContext gcContext;
    private boolean useDna;
    private QueryParam queryParam;
    protected static final String tableAs = "%1$s";
    private static Logger logger = LoggerFactory.getLogger(GcQuerySqlUpdater.class);

    public GcQuerySqlUpdater(GcDataEntryContext gcContext) {
        this.gcContext = gcContext;
        this.queryParam = gcContext.getQueryParam();
    }

    public GcDataEntryContext getGcContext() {
        return this.gcContext;
    }

    public boolean isUseDna() {
        return this.useDna;
    }

    public void beforeQuery(CommonQueryImpl query, boolean useDna) {
        this.useDna = useDna;
        this.beforeQuery(query);
    }

    protected abstract void beforeQuery(CommonQueryImpl var1);

    public abstract void afterQuery(CommonQueryImpl var1);

    protected String getOrgTable() {
        return this.getGcContext().getOrgTableName();
    }

    protected String getYearPeriod() {
        return DateUtils.format((Date)this.getGcContext().getYearPeriod().getEndDate(), (String)"yyyy-MM-dd");
    }

    protected String createJoinTable(String orgTableAlias, String inputOrgFieldName, String inputOrgTypeFieldName) {
        String tb = this.getOrgTable();
        Date date = this.getGcContext().getYearPeriod().getEndDate();
        StringBuilder sql = new StringBuilder();
        if (this.isUseDna()) {
            sql.append(" INNER JOIN %1$s AS %2$s ON %2$s.CODE=%3$s AND %2$s.VALIDTIME <= date'%4$s' AND %2$s.INVALIDTIME > date'%4$s' AND %2$s.ORGTYPEID = %5$s ");
        } else {
            sql.append(" INNER JOIN %1$s %2$s ON %2$s.CODE=%3$s AND ");
            sql.append(GcQuerySqlAdapterUtils.getDateCompareSql(this.queryParam, "%2$s", "VALIDTIME", "<=", date));
            sql.append(" AND ");
            sql.append(GcQuerySqlAdapterUtils.getDateCompareSql(this.queryParam, "%2$s", "INVALIDTIME", ">", date));
            sql.append(" AND %2$s.ORGTYPEID = %5$s ");
        }
        return String.format(sql.toString(), tb, orgTableAlias, inputOrgFieldName, this.getYearPeriod(), inputOrgTypeFieldName);
    }

    protected String createOffsetJoinTable(String orgTableAlias, String inputOrgFieldName) {
        String tb = this.getOrgTable();
        Date date = this.getGcContext().getYearPeriod().getEndDate();
        StringBuilder sql = new StringBuilder();
        if (this.isUseDna()) {
            sql.append(" INNER JOIN %1$s AS %2$s ON %2$s.CODE=%3$s AND %2$s.VALIDTIME <= date'%4$s' AND %2$s.INVALIDTIME > date'%4$s' ");
        } else {
            sql.append(" INNER JOIN %1$s %2$s ON %2$s.CODE=%3$s AND ");
            sql.append(GcQuerySqlAdapterUtils.getDateCompareSql(this.queryParam, "%2$s", "VALIDTIME", "<=", date));
            sql.append(" AND ");
            sql.append(GcQuerySqlAdapterUtils.getDateCompareSql(this.queryParam, "%2$s", "INVALIDTIME", ">", date));
        }
        return String.format(sql.toString(), tb, orgTableAlias, inputOrgFieldName, this.getYearPeriod());
    }

    protected GcQuerySqlParam createOrgHBCJWhere(String begin, GcQuerySqlParam param, String parent, EntitiesOrgMode mode) {
        int len = GcOrgPublicTool.getInstance().getOrgCodeLength();
        param.append(begin);
        param.append(" ORG1.GCPARENTS like '").append(parent).append("%%' ");
        param.append(" AND ORG2.GCPARENTS like '").append(parent).append("%%' ");
        param.append(" AND SUBSTR(ORG1.GCPARENTS,1,").append(parent.length() + len + 1).append(") != ");
        param.append(" SUBSTR(ORG2.GCPARENTS,1,").append(parent.length() + len + 1).append(") ");
        return param;
    }

    protected GcQuerySqlParam createOrgChildrenWhere(String begin, GcQuerySqlParam param, String parent) {
        param.append(begin);
        param.append(" ORG1.GCPARENTS like '").append(parent).append("%%' ");
        return param;
    }

    protected GcQuerySqlParam createOrgOppChildrenWhere(String begin, GcQuerySqlParam param, String parent) {
        param.append(begin);
        param.append(" SUBSTR(ORG2.GCPARENTS,1,").append(parent.length()).append(") != '").append(parent).append("' ");
        return param;
    }

    protected GcQuerySqlParam createStringWhere(String begin, GcQuerySqlParam param, String field, String ... uuids) {
        if (uuids == null || uuids.length < 1) {
            return param;
        }
        param.append(begin);
        if (uuids.length == 1) {
            if (this.isUseDna()) {
                param.append(tableAs).append(".").append(field).append(" = '").append(uuids[0]).append("' ");
            } else {
                param.append(tableAs).append(".").append(field).append(" = '").append(uuids[0]).append("' ");
            }
        } else {
            param.append(tableAs).append(".").append(field).append(" IN (");
            for (int i = 0; i < uuids.length; ++i) {
                if (i > 0) {
                    param.append(" , ");
                }
                if (this.isUseDna()) {
                    param.append(" '").append(uuids[i]).append("' ");
                    continue;
                }
                param.append(" '").append(uuids[i]).append("' ");
            }
            param.append(") ");
        }
        return param;
    }

    protected void createOrgHBCJWhereByFilterType(String begin, GcQuerySqlParam param, String parent, InputDataQueryFilterType filterType, InnerTableTabsType tabsType, EntitiesOrgMode orgMode) {
        if (InputDataQueryFilterType.CURRENT.equals((Object)filterType)) {
            this.createOrgHBCJWhere(begin, param, parent, orgMode);
        } else if (InputDataQueryFilterType.PARENTUNION.equals((Object)filterType)) {
            if (!InnerTableTabsType.PARENTUNION.equals((Object)tabsType)) {
                this.createOrgChildrenWhere(begin, param, parent);
                this.createOrgOppChildrenWhere(" AND ", param, parent);
            }
        } else if (InputDataQueryFilterType.CHILDREN.equals((Object)filterType)) {
            this.createOrgHBWhere(begin, param, parent);
        } else {
            this.createOrgChildrenWhere(begin, param, parent);
        }
    }

    protected void createOppUnitIdJoinTableByFilterType(GcQuerySqlParam param, InputDataQueryFilterType filterType, String inputOrgTypeSqlFieldName, boolean isOffsetFilter) {
        if (!InputDataQueryFilterType.ALL.equals((Object)filterType)) {
            if (isOffsetFilter) {
                param.append(this.createOffsetJoinTable("ORG2", "%1$s.OPPUNITID"));
            } else {
                param.append(this.createJoinTable("ORG2", "%1$s.OPPUNITID", inputOrgTypeSqlFieldName));
            }
        }
    }

    protected GcQuerySqlParam createOrgHBWhere(String begin, GcQuerySqlParam param, String parent) {
        param.append(begin);
        param.append(" ORG1.GCPARENTS like '").append(parent).append("%%' ");
        param.append(" AND ORG2.GCPARENTS like '").append(parent).append("%%' ");
        int len = GcOrgPublicTool.getInstance().getOrgCodeLength();
        param.append(" AND SUBSTR(ORG1.GCPARENTS,1,").append(parent.length() + len + 1).append(") = ");
        param.append(" SUBSTR(ORG2.GCPARENTS,1,").append(parent.length() + len + 1).append(") ");
        return param;
    }

    protected GcQuerySqlParam createOrgHBCJWhereByMode(String begin, GcQuerySqlParam param, String parent, EntitiesOrgMode mode) {
        int len = GcOrgPublicTool.getInstance().getOrgCodeLength();
        param.append(begin);
        param.append(" ORG1.GCPARENTS like '").append(parent).append("%%' ");
        param.append(" AND ORG2.GCPARENTS like '").append(parent).append("%%' ");
        if (mode != EntitiesOrgMode.UNIONORG) {
            param.append(" AND SUBSTR(ORG1.PARENTS,1,").append(parent.length() + len + 1).append(") != ");
            param.append(" SUBSTR(ORG2.PARENTS,1,").append(parent.length() + len + 1).append(") ");
            param.append(" AND SUBSTR(ORG1.GCPARENTS,").append(parent.length() + 1).append(",").append(len + 1).append(") != ");
            param.append(" SUBSTR(ORG2.GCPARENTS,").append(parent.length() + 1).append(",").append(len + 1).append(") ");
        }
        return param;
    }
}

