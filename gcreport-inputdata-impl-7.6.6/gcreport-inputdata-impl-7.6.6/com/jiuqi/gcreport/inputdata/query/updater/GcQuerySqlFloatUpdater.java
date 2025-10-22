/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl
 */
package com.jiuqi.gcreport.inputdata.query.updater;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.query.base.GcDataEntryContext;
import com.jiuqi.gcreport.inputdata.query.base.GcQuerySqlParam;
import com.jiuqi.gcreport.inputdata.query.constant.EntitiesOrgMode;
import com.jiuqi.gcreport.inputdata.query.constant.EntitiesQueryType;
import com.jiuqi.gcreport.inputdata.query.constant.FInnerTableTabsDoc;
import com.jiuqi.gcreport.inputdata.query.constant.InnerTableTabsType;
import com.jiuqi.gcreport.inputdata.query.constant.InputDataQueryFilterType;
import com.jiuqi.gcreport.inputdata.query.exception.ErrorTabsParamException;
import com.jiuqi.gcreport.inputdata.query.updater.GcQuerySqlUpdater;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.intf.impl.CommonQueryImpl;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcQuerySqlFloatUpdater
extends GcQuerySqlUpdater {
    private static final Logger logger = LoggerFactory.getLogger(GcQuerySqlFloatUpdater.class);
    private GcQuerySqlParam inputfilter;
    private EntitiesQueryType mainTable;
    private GcQuerySqlParam offsetFilter;
    private ConsolidatedTaskService taskService = (ConsolidatedTaskService)SpringBeanUtils.getBean(ConsolidatedTaskService.class);
    protected static final String tableAs = "%1$s";

    public GcQuerySqlFloatUpdater(GcDataEntryContext gcContext) {
        super(gcContext);
    }

    public boolean supportSoftParse(QueryTable mainTable, String tableAlias) {
        return !EntitiesQueryType.UNIONALL.equals((Object)this.mainTable);
    }

    @Override
    public void beforeQuery(CommonQueryImpl query) {
        if (StringUtils.isEmpty((String)this.getGcContext().getGcOrgType())) {
            StringBuffer err = new StringBuffer();
            err.append(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.beforequerynotorgtype") + "[").append("MD_GCORGTYPE").append("];");
            String error = this.getGcContext().getLog(err.toString());
            logger.error(error);
            throw new RuntimeException(error);
        }
        query.getMasterKeys().clearValue("MD_GCORGTYPE");
        if (!StringUtils.isEmpty((String)this.getGcContext().getOrgTableName())) {
            if (this.getGcContext().getInitOrg() == null && CollectionUtils.isEmpty(this.getGcContext().getInitOrgList())) {
                StringBuffer err = new StringBuffer();
                err.append(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.beforequeryinitsqlexceptionmsg"));
                String error = this.getGcContext().getLog(err.toString());
                logger.error(error);
                throw new RuntimeException(error);
            }
        } else {
            StringBuffer err = new StringBuffer();
            err.append(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.beforequerynotinitorg"));
            String error = this.getGcContext().getLog(err.toString());
            logger.error(error);
            throw new RuntimeException(error);
        }
        query.getMasterKeys().clearValue("MD_ORG");
        this.initQuerySql();
    }

    public String updateQuerySql(QueryTable primaryTable, String tableAlias, String querySql) {
        String parent;
        logger.debug("GcDataQueryImpl\u52a0\u5de5\u524dSQL: " + querySql);
        if (this.inputfilter != null && this.offsetFilter != null && this.mainTable != null) {
            parent = "";
            if (this.getGcContext().getInitOrg() != null && StringUtils.isEmpty((String)(parent = this.getGcContext().getInitOrg().getParentStr()))) {
                parent = this.getGcContext().getInitOrg().getId();
            }
        } else {
            logger.error("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u4e2d\u7684\u7ec4\u7ec7\u673a\u6784\u7ef4\u5ea6\u627e\u4e0d\u5230\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784");
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.updatequerysqlexceptionmsg"));
        }
        this.inputfilter.initTableParam(tableAlias);
        this.inputfilter.formatRefFieldMap(FInnerTableTabsDoc.getInputDataFieldRefMap(this.getGcContext().getOrgMode(), this.getGcContext().getTabType(), parent, this.getGcContext().getMainTable()));
        this.offsetFilter.initTableParam(tableAlias);
        this.offsetFilter.formatRefFieldMap(FInnerTableTabsDoc.getOffsetFieldRefMap(this.isUseDna()));
        querySql = this.mainTable.formatSql(querySql, this.inputfilter, this.offsetFilter);
        logger.debug("GcDataQueryImpl\u52a0\u5de5\u540eSQL: " + querySql);
        return querySql;
    }

    @Override
    public void afterQuery(CommonQueryImpl query) {
        query.getMasterKeys().clearAll();
        query.getMasterKeys().assign(this.getGcContext().getMasterKeys());
    }

    private void initQuerySql() {
        String taskKey;
        String systemId;
        InputDataQueryFilterType filterType;
        InnerTableTabsType type = this.getGcContext().getTabType();
        if (type == null) {
            type = InnerTableTabsType.DETAILDATA;
        }
        if ((filterType = this.getGcContext().getFilterType()) == null) {
            filterType = InputDataQueryFilterType.ALL;
        }
        if (StringUtils.isEmpty((String)this.getGcContext().getGcOrgType())) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.dataentryquery.initquerysqlexceptionmsg"));
        }
        String inputOrgTypeSqlFieldName = "%1$s.MD_GCORGTYPE";
        this.setMainTable(EntitiesQueryType.INNERTABLE);
        EntitiesOrgMode orgType = this.getGcContext().getOrgMode();
        this.inputfilter = new GcQuerySqlParam(this.getGcContext().getCurrencyCode(), this.isUseDna());
        this.offsetFilter = new GcQuerySqlParam(this.getGcContext().getCurrencyCode(), this.isUseDna());
        String gcParentStr = null;
        String[] orgCodeArrays = null;
        ArrayList<String> orgCodesList = new ArrayList<String>();
        if (this.getGcContext().getInitOrg() != null) {
            gcParentStr = this.getGcContext().getInitOrg().getGcParentStr();
            if (StringUtils.isEmpty((String)gcParentStr)) {
                gcParentStr = this.getGcContext().getInitOrg().getCode();
            }
            orgCodesList.add(this.getGcContext().getInitOrg().getCode());
        } else if (!CollectionUtils.isEmpty(this.getGcContext().getInitOrgList())) {
            for (GcOrgCacheVO orgCache : this.getGcContext().getInitOrgList()) {
                orgCodesList.add(orgCache.getCode());
            }
        }
        orgCodeArrays = orgCodesList.toArray(new String[0]);
        switch (type) {
            case ALLDATA: {
                if (orgType == EntitiesOrgMode.DIFFERENCE) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5dee\u989d\u5355\u4f4d\u6240\u6709\u8bb0\u5f55\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.setMainTable(EntitiesQueryType.UNIONALL);
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.inputfilter.append(this.createJoinTable("ORG2", "%1$s.OPPUNITID", inputOrgTypeSqlFieldName));
                    this.createOrgHBCJWhere(" WHERE ", this.inputfilter, gcParentStr, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '1' ");
                    this.offsetFilter.append(this.createOffsetJoinTable("ORG1", "%1$s.UNITID"));
                    this.offsetFilter.append(this.createOffsetJoinTable("ORG2", "%1$s.OPPUNITID"));
                    this.createOrgHBCJWhereByMode(" WHERE ", this.offsetFilter, gcParentStr, orgType);
                    this.offsetFilter.append(" AND ").append(tableAs).append(".OFFSETSRCTYPE in (31,30,0,26,21,5,6,22,24,33,34) ");
                    this.offsetFilter.append(" AND ").append(tableAs).append(".DISABLEFLAG <> '1'  ");
                    break;
                }
                if (orgType == EntitiesOrgMode.UNIONORG) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5408\u5e76\u5355\u4f4d\u6240\u6709\u8bb0\u5f55\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.createOppUnitIdJoinTableByFilterType(this.inputfilter, filterType, inputOrgTypeSqlFieldName, false);
                    this.createOrgHBCJWhereByFilterType(" WHERE ", this.inputfilter, gcParentStr, filterType, type, orgType);
                    break;
                }
                logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5355\u6237\u5355\u4f4d\u6240\u6709\u8bb0\u5f55\u7b7e\u6761\u4ef6\u67e5\u8be2");
                this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                this.createStringWhere(" WHERE ", this.inputfilter, "MDCODE", orgCodeArrays);
                break;
            }
            case OFFSET: {
                if (orgType == EntitiesOrgMode.SINGLE) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5355\u6237\u5355\u4f4d\u5df2\u62b5\u6d88\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.createStringWhere(" WHERE ", this.inputfilter, "MDCODE", orgCodeArrays);
                    this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '1' ");
                    break;
                }
                if (orgType == EntitiesOrgMode.UNIONORG) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5408\u5e76\u5355\u4f4d\u5df2\u62b5\u6d88\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.createOppUnitIdJoinTableByFilterType(this.inputfilter, filterType, inputOrgTypeSqlFieldName, false);
                    this.createOrgHBCJWhereByFilterType(" WHERE ", this.inputfilter, gcParentStr, filterType, type, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '1' ");
                    break;
                }
                logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5dee\u989d\u5355\u4f4d\u5df2\u62b5\u6d88\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                this.inputfilter.append(this.createJoinTable("ORG2", "%1$s.OPPUNITID", inputOrgTypeSqlFieldName));
                this.createOrgHBCJWhere(" WHERE ", this.inputfilter, gcParentStr, orgType);
                this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '1' ");
                break;
            }
            case NOTOFFSET: {
                if (orgType == EntitiesOrgMode.DIFFERENCE) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5dee\u989d\u5355\u4f4d\u672a\u62b5\u6d88\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    throw new ErrorTabsParamException(orgType.getTitle() + "\u4e0d\u80fd\u67e5\u8be2" + type.getTitle());
                }
                if (orgType == EntitiesOrgMode.UNIONORG) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5408\u5e76\u5355\u4f4d\u672a\u62b5\u6d88\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.createOppUnitIdJoinTableByFilterType(this.inputfilter, filterType, inputOrgTypeSqlFieldName, false);
                    this.createOrgHBCJWhereByFilterType(" WHERE ", this.inputfilter, gcParentStr, filterType, type, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '0' ");
                    break;
                }
                logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5355\u6237\u5355\u4f4d\u672a\u62b5\u6d88\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                this.createStringWhere(" WHERE ", this.inputfilter, "MDCODE", orgCodeArrays);
                this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '0' ");
                break;
            }
            case INPUTADJUSTMENT: {
                if (orgType == EntitiesOrgMode.SINGLE) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5355\u6237\u5355\u4f4d\u8f93\u5165\u8c03\u6574\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    throw new ErrorTabsParamException(orgType.getTitle() + "\u4e0d\u80fd\u67e5\u8be2" + type.getTitle());
                }
                logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u975e\u5355\u6237\u5355\u4f4d\u8f93\u5165\u8c03\u6574\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                this.setMainTable(EntitiesQueryType.OFFSETTABLE);
                this.offsetFilter.append(this.createOffsetJoinTable("ORG1", "%1$s.UNITID"));
                this.offsetFilter.append(this.createOffsetJoinTable("ORG2", "%1$s.OPPUNITID"));
                this.createOrgHBCJWhereByMode(" WHERE ", this.offsetFilter, gcParentStr, orgType);
                if (orgType == EntitiesOrgMode.UNIONORG) {
                    this.createOrgHBCJWhereByFilterType(" AND ", this.offsetFilter, gcParentStr, filterType, type, orgType);
                }
                this.offsetFilter.append(" AND ").append(tableAs).append(".OFFSETSRCTYPE in (31,30,0,26,21,5,6,22,24,33,34) ");
                this.offsetFilter.append(" AND ").append(tableAs).append(".DISABLEFLAG <> '1'  ");
                break;
            }
            case JOURNALADJUSTMENT: {
                if (orgType == EntitiesOrgMode.SINGLE) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5355\u6237\u5355\u4f4d\u65e5\u8bb0\u8d26\u8c03\u6574\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    throw new ErrorTabsParamException(orgType.getTitle() + "\u4e0d\u80fd\u67e5\u8be2" + type.getTitle());
                }
                logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u975e\u5355\u6237\u5355\u4f4d\u65e5\u8bb0\u8d26\u8c03\u6574\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                this.setMainTable(EntitiesQueryType.OFFSETTABLE);
                this.offsetFilter.append(this.createOffsetJoinTable("ORG1", "%1$s.UNITID"));
                this.offsetFilter.append(this.createOffsetJoinTable("ORG2", "%1$s.OPPUNITID"));
                if (EntitiesOrgMode.UNIONORG.equals((Object)orgType)) {
                    this.createOrgHBCJWhereByFilterType(" WHERE ", this.offsetFilter, gcParentStr, filterType, type, orgType);
                } else {
                    this.createOrgHBCJWhere(" WHERE ", this.offsetFilter, gcParentStr, orgType);
                }
                this.offsetFilter.append(" AND ").append(tableAs).append(".OFFSETSRCTYPE =0 ");
                break;
            }
            case DIFFERENCE: {
                if (orgType == EntitiesOrgMode.UNIONORG) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5408\u5e76\u5355\u4f4d\u5dee\u989d\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.inputfilter.append(this.createJoinTable("ORG2", "%1$s.OPPUNITID", inputOrgTypeSqlFieldName));
                    this.createOrgHBCJWhereByFilterType(" WHERE ", this.inputfilter, gcParentStr, filterType, type, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".DIFFAMT <> 0 ");
                    break;
                }
                logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u4e0d\u652f\u6301" + orgType.getTitle() + "\u5355\u4f4d\u5dee\u989d\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                throw new ErrorTabsParamException(orgType.getTitle() + "\u4e0d\u80fd\u67e5\u8be2" + type.getTitle());
            }
            case PARENTUNION: {
                if (orgType == EntitiesOrgMode.UNIONORG) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5408\u5e76\u5355\u4f4d\u4e0a\u7ea7\u5408\u5e76\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.inputfilter.append(this.createJoinTable("ORG2", "%1$s.OPPUNITID", inputOrgTypeSqlFieldName));
                    this.createOrgChildrenWhere(" WHERE ", this.inputfilter, gcParentStr);
                    this.createOrgOppChildrenWhere(" AND ", this.inputfilter, gcParentStr);
                    this.createOrgHBCJWhereByFilterType(" AND ", this.inputfilter, gcParentStr, filterType, type, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '1' ");
                    break;
                }
                logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u4e0d\u652f\u6301" + orgType.getTitle() + "\u5355\u4f4d\u4e0a\u7ea7\u5408\u5e76\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                throw new ErrorTabsParamException(orgType.getTitle() + "\u4e0d\u80fd\u67e5\u8be2" + type.getTitle());
            }
            case DETAILDATA: {
                if (orgType == EntitiesOrgMode.UNIONORG) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5408\u5e76\u5355\u4f4d\u660e\u7ec6\u8bb0\u5f55\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.setMainTable(EntitiesQueryType.UNIONALL);
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.inputfilter.append(this.createJoinTable("ORG2", "%1$s.OPPUNITID", inputOrgTypeSqlFieldName));
                    this.createOrgHBCJWhereByFilterType(" WHERE (( ", this.inputfilter, gcParentStr, filterType, type, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".DIFFAMT <> 0) ");
                    this.createOrgHBCJWhereByFilterType(" OR ( ", this.inputfilter, gcParentStr, filterType, type, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '0') ");
                    this.createOrgChildrenWhere(" OR ( ", this.inputfilter, gcParentStr);
                    this.createOrgOppChildrenWhere(" AND ", this.inputfilter, gcParentStr);
                    this.createOrgHBCJWhereByFilterType(" AND ", this.inputfilter, gcParentStr, filterType, type, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '1')) ");
                    this.createOrgChildrenWhere(" AND ", this.inputfilter, gcParentStr);
                    this.offsetFilter.append(this.createOffsetJoinTable("ORG1", "%1$s.UNITID"));
                    this.offsetFilter.append(this.createOffsetJoinTable("ORG2", "%1$s.OPPUNITID"));
                    this.createOrgHBCJWhereByMode(" WHERE ", this.offsetFilter, gcParentStr, orgType);
                    this.createOrgHBCJWhereByFilterType(" AND ", this.offsetFilter, gcParentStr, filterType, type, orgType);
                    this.offsetFilter.append(" AND ").append(tableAs).append(".OFFSETSRCTYPE in (31,30,0,26,21,5,6,22,24,33,34) ");
                    this.offsetFilter.append(" AND ").append(tableAs).append(".DISABLEFLAG <> '1' ");
                    break;
                }
                if (orgType == EntitiesOrgMode.DIFFERENCE) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5dee\u989d\u5355\u4f4d\u660e\u7ec6\u6570\u636e\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.setMainTable(EntitiesQueryType.UNIONALL);
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.inputfilter.append(this.createJoinTable("ORG2", "%1$s.OPPUNITID", inputOrgTypeSqlFieldName));
                    this.createOrgHBCJWhere(" WHERE ", this.inputfilter, gcParentStr, orgType);
                    this.inputfilter.append(" AND ").append(tableAs).append(".OFFSETSTATE = '1' ");
                    this.offsetFilter.append(this.createOffsetJoinTable("ORG1", "%1$s.UNITID"));
                    this.offsetFilter.append(this.createOffsetJoinTable("ORG2", "%1$s.OPPUNITID"));
                    this.createOrgHBCJWhereByMode(" WHERE ", this.offsetFilter, gcParentStr, orgType);
                    this.offsetFilter.append(" AND ").append(tableAs).append(".OFFSETSRCTYPE in (31,30,0,26,21,5,6,22,24,33,34) ");
                    this.offsetFilter.append(" AND ").append(tableAs).append(".DISABLEFLAG <> '1'  ");
                    break;
                }
                if (orgType == EntitiesOrgMode.SINGLE) {
                    logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u662f\u5355\u6237\u5355\u4f4d\u660e\u7ec6\u6570\u636e\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                    this.inputfilter.append(this.createJoinTable("ORG1", "%1$s.MDCODE", inputOrgTypeSqlFieldName));
                    this.createStringWhere(" WHERE ", this.inputfilter, "MDCODE", orgCodeArrays);
                    break;
                }
                logger.debug("\u5f53\u524d\u67e5\u8be2\u6761\u4ef6\u4e0d\u652f\u6301" + orgType.getTitle() + "\u5355\u4f4d\u660e\u7ec6\u6570\u636e\u9875\u7b7e\u6761\u4ef6\u67e5\u8be2");
                throw new ErrorTabsParamException(orgType.getTitle() + "\u4e0d\u80fd\u67e5\u8be2" + type.getTitle());
            }
        }
        if (this.inputfilter.isEmpty()) {
            logger.debug("\u5f53\u524d\u5185\u90e8\u5f55\u5165\u8868\u67e5\u8be2\u6761\u4ef6\u4e3a\u7a7a");
            this.inputfilter.append(" WHERE 1=1 ");
        }
        if (this.offsetFilter.isEmpty()) {
            logger.debug("\u5f53\u524d\u62b5\u6d88\u5206\u5f55\u8868\u67e5\u8be2\u6761\u4ef6\u4e3a\u7a7a");
            this.offsetFilter.append(" WHERE 1=1 ");
        }
        if (StringUtils.isEmpty((String)(systemId = this.taskService.getSystemIdByTaskIdAndPeriodStr(taskKey = this.getGcContext().getFormScheme().getTaskKey(), this.getGcContext().getYearPeriod().toString())))) {
            systemId = "\u4e0d\u5b58\u5728";
        }
        this.createStringWhere(" AND ", this.inputfilter, "reportSystemId", systemId);
        this.createStringWhere(" AND ", this.offsetFilter, "systemId", systemId);
        if (this.getGcContext().getGcOrgType() != null) {
            String orgTypeFieldName = "MD_GCORGTYPE";
            String orgTypeCode = this.getGcContext().getGcOrgType();
            String noneCode = GCOrgTypeEnum.NONE.getCode();
            this.createStringWhere(" AND ", this.offsetFilter, orgTypeFieldName, orgTypeCode, noneCode);
        }
    }

    protected void setMainTable(EntitiesQueryType mainTable) {
        this.mainTable = mainTable;
    }
}

