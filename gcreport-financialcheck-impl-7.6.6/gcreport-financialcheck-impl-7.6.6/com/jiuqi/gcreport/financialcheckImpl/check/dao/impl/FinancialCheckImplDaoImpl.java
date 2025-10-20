/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryLevelEnum
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils
 *  com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckImpl.check.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.FinancialCheckImplDao;
import com.jiuqi.gcreport.financialcheckImpl.check.dao.helper.FcBaseSqlBuilderHelper;
import com.jiuqi.gcreport.financialcheckcore.checkquery.enums.CheckQueryLevelEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dto.FinancialCheckQueryConditionDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class FinancialCheckImplDaoImpl
extends AbstractEntDbSqlGenericDAO<GcRelatedItemEO>
implements FinancialCheckImplDao {
    public FinancialCheckImplDaoImpl() {
        super(GcRelatedItemEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELATED_ITEM");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public PageInfo<GcRelatedItemEO> queryChecked(FinancialCheckQueryConditionDTO condition, String orgType) {
        String unitId = condition.getUnitId();
        if (StringUtils.isEmpty((String)unitId)) {
            return PageInfo.empty();
        }
        YearPeriodObject yp = new YearPeriodObject(condition.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)condition.getAcctPeriod()));
        GcOrgCenterService accessService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO localUnit = accessService.getOrgByCode(unitId);
        if (Objects.isNull(localUnit)) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d%s\u5728\u5f53\u524d\u65f6\u671f\u4e0d\u5b58\u5728", unitId));
        }
        if (localUnit.isLeaf()) {
            condition.setCheckLevel(CheckQueryLevelEnum.CUSTOM.getCode());
        }
        ArrayList<Object> param = new ArrayList<Object>();
        HashSet<String> tempGroupIds = new HashSet<String>();
        try {
            FcBaseSqlBuilderHelper helper = FcBaseSqlBuilderHelper.getNewInstance(CheckQueryLevelEnum.getEnumByCode((String)condition.getCheckLevel()));
            String sql = helper.buildCheckedConditionSql(condition, orgType, param, tempGroupIds);
            int count = this.count(sql.toString(), param);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            List gcRelatedItemEOS = this.selectEntityByPaging(sql.toString(), (condition.getPageNum() - 1) * condition.getPageSize(), condition.getPageNum() * condition.getPageSize(), param);
            PageInfo pageInfo = PageInfo.of((List)gcRelatedItemEOS, (int)condition.getPageNum(), (int)condition.getPageSize(), (int)count);
            return pageInfo;
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public PageInfo<GcRelatedItemEO> queryUncheckedGroupByUnit(FinancialCheckQueryConditionDTO condition, String orgType, Map<String, String> sortField2WayMap) {
        String unitId = condition.getUnitId();
        if (StringUtils.isEmpty((String)unitId)) {
            return PageInfo.empty();
        }
        YearPeriodObject yp = new YearPeriodObject(condition.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)condition.getAcctPeriod()));
        GcOrgCenterService accessService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO localUnit = accessService.getOrgByCode(unitId);
        if (Objects.isNull(localUnit)) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d%s\u5728\u5f53\u524d\u65f6\u671f\u4e0d\u5b58\u5728", unitId));
        }
        if (localUnit.isLeaf()) {
            condition.setCheckLevel(CheckQueryLevelEnum.CUSTOM.getCode());
        }
        ArrayList<Object> param = new ArrayList<Object>();
        HashSet<String> tempGroupIds = new HashSet<String>();
        try {
            FcBaseSqlBuilderHelper helper = FcBaseSqlBuilderHelper.getNewInstance(CheckQueryLevelEnum.getEnumByCode((String)condition.getCheckLevel()));
            String sql = helper.buildUncheckedGroupByUnitSql(condition, orgType, sortField2WayMap, param, tempGroupIds);
            int count = this.count(sql, param);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            if (condition.getPageNum() == -1) {
                List gcRelatedItemEOS = this.selectEntity(sql.toString(), param);
                PageInfo pageInfo = PageInfo.of((List)gcRelatedItemEOS, (int)1, (int)count, (int)count);
                return pageInfo;
            }
            List gcRelatedItemEOS = this.selectEntityByPaging(sql, (condition.getPageNum() - 1) * condition.getPageSize(), condition.getPageNum() * condition.getPageSize(), param);
            PageInfo pageInfo = PageInfo.of((List)gcRelatedItemEOS, (int)condition.getPageNum(), (int)condition.getPageSize(), (int)count);
            return pageInfo;
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public PageInfo<GcRelatedItemEO> queryUncheckedGroupByOppUnit(FinancialCheckQueryConditionDTO condition, String orgType, Map<String, String> sortField2WayMap) {
        String unitId = condition.getUnitId();
        if (StringUtils.isEmpty((String)unitId)) {
            return PageInfo.empty();
        }
        ArrayList<Object> param = new ArrayList<Object>();
        HashSet<String> tempGroupIds = new HashSet<String>();
        try {
            FcBaseSqlBuilderHelper helper = FcBaseSqlBuilderHelper.getNewInstance(CheckQueryLevelEnum.getEnumByCode((String)condition.getCheckLevel()));
            String sql = helper.buildUncheckedGroupByOppUnitSql(condition, orgType, sortField2WayMap, param, tempGroupIds);
            int count = this.count(sql, param);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            if (condition.getPageNum() == -1) {
                List gcRelatedItemEOS = this.selectEntity(sql.toString(), param);
                PageInfo pageInfo = PageInfo.of((List)gcRelatedItemEOS, (int)1, (int)count, (int)count);
                return pageInfo;
            }
            List gcRelatedItemEOS = this.selectEntityByPaging(sql, (condition.getPageNum() - 1) * condition.getPageSize(), condition.getPageNum() * condition.getPageSize(), param);
            PageInfo pageInfo = PageInfo.of((List)gcRelatedItemEOS, (int)condition.getPageNum(), (int)condition.getPageSize(), (int)count);
            return pageInfo;
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public PageInfo<GcRelatedItemEO> queryUncheckedGroupByScheme(FinancialCheckQueryConditionDTO condition, String orgType, Map<String, String> sortField2WayMap) {
        String unitId = condition.getUnitId();
        if (StringUtils.isEmpty((String)unitId)) {
            return PageInfo.empty();
        }
        YearPeriodObject yp = new YearPeriodObject(condition.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)condition.getAcctPeriod()));
        GcOrgCenterService accessService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO localUnit = accessService.getOrgByCode(condition.getUnitId());
        if (Objects.isNull(localUnit)) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d%s\u5728\u5f53\u524d\u65f6\u671f\u4e0d\u5b58\u5728", unitId));
        }
        if (localUnit.isLeaf()) {
            condition.setCheckLevel(CheckQueryLevelEnum.CUSTOM.getCode());
        }
        ArrayList<Object> param = new ArrayList<Object>();
        HashSet<String> tempGroupIds = new HashSet<String>();
        try {
            FcBaseSqlBuilderHelper helper = FcBaseSqlBuilderHelper.getNewInstance(CheckQueryLevelEnum.getEnumByCode((String)condition.getCheckLevel()));
            String sql = helper.buildUncheckedSchemeSql(condition, orgType, sortField2WayMap, param, tempGroupIds);
            int count = this.count(sql.toString(), param);
            if (count == 0) {
                PageInfo pageInfo = PageInfo.empty();
                return pageInfo;
            }
            if (condition.getPageNum() == -1) {
                List gcRelatedItemEOS = this.selectEntity(sql.toString(), param);
                PageInfo pageInfo = PageInfo.of((List)gcRelatedItemEOS, (int)1, (int)count, (int)count);
                return pageInfo;
            }
            List gcRelatedItemEOS = this.selectEntityByPaging(sql.toString(), (condition.getPageNum() - 1) * condition.getPageSize(), condition.getPageNum() * condition.getPageSize(), param);
            PageInfo pageInfo = PageInfo.of((List)gcRelatedItemEOS, (int)condition.getPageNum(), (int)condition.getPageSize(), (int)count);
            return pageInfo;
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public Map<String, Object> queryAmtSum(FinancialCheckQueryConditionDTO condition, String orgType) {
        String unitId = condition.getUnitId();
        if (StringUtils.isEmpty((String)unitId)) {
            return new HashMap<String, Object>();
        }
        YearPeriodObject yp = new YearPeriodObject(condition.getAcctYear().intValue(), PeriodUtils.standardPeriod((int)condition.getAcctPeriod()));
        GcOrgCenterService accessService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO localUnit = accessService.getOrgByCode(condition.getUnitId());
        if (Objects.isNull(localUnit)) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d%s\u5728\u5f53\u524d\u65f6\u671f\u4e0d\u5b58\u5728", unitId));
        }
        if (localUnit.isLeaf()) {
            condition.setCheckLevel(CheckQueryLevelEnum.CUSTOM.getCode());
        }
        ArrayList<Object> param = new ArrayList<Object>();
        HashSet<String> tempGroupIds = new HashSet<String>();
        try {
            FcBaseSqlBuilderHelper helper = FcBaseSqlBuilderHelper.getNewInstance(CheckQueryLevelEnum.getEnumByCode((String)condition.getCheckLevel()));
            String sql = helper.buildQueryAmtSumSql(condition, orgType, param, tempGroupIds);
            Map map = (Map)this.selectMap(sql.toString(), param).get(0);
            return map;
        }
        finally {
            ReltxSqlUtils.deleteByGroupIds(tempGroupIds);
        }
    }
}

