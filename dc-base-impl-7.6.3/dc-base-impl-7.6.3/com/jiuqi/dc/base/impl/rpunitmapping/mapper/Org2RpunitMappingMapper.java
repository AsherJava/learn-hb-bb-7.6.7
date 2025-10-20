/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.dc.base.impl.rpunitmapping.mapper;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import com.jiuqi.dc.base.impl.rpunitmapping.entity.Org2RpunitMappingEntity;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.util.CollectionUtils;

@Mapper
public interface Org2RpunitMappingMapper
extends BaseOptMapper<Org2RpunitMappingEntity> {
    @SelectProvider(type=SqlProvider.class, method="listOrgCode")
    public List<String> listOrgCode(Org2RpunitMappingEntity var1);

    @SelectProvider(type=SqlProvider.class, method="listAll")
    public List<Map<String, Object>> listAll(Org2RpunitMappingQueryVO var1);

    @SelectProvider(type=SqlProvider.class, method="listByOrgCodes")
    public List<Org2RpunitMappingEntity> listByOrgCodes(Org2RpunitMappingQueryVO var1);

    @SelectProvider(type=SqlProvider.class, method="getListAllCount")
    public int getListAllCount(Org2RpunitMappingQueryVO var1);

    @DeleteProvider(type=SqlProvider.class, method="deleteByIds")
    public int deleteByIds(Org2RpunitMappingQueryVO var1);

    @SelectProvider(type=SqlProvider.class, method="getAllOrgCodeByRpUnitCode")
    public List<String> getAllOrgCodeByRpUnitCode(Org2RpunitMappingQueryVO var1);

    @SelectProvider(type=SqlProvider.class, method="getAllOrgCode")
    public List<String> getAllOrgCode(Org2RpunitMappingQueryVO var1);

    @SelectProvider(type=SqlProvider.class, method="listUnitCodeByOrgCodeAndPeriod")
    public List<String> listUnitCodeByOrgCodeAndPeriod(Org2RpunitMappingQueryVO var1);

    public static class SqlProvider {
        public String listOrgCode(Org2RpunitMappingEntity orgUnitMappingEntity) {
            StringBuffer sql = new StringBuffer();
            sql.append("select ORGCODE ");
            sql.append("  from ").append(orgUnitMappingEntity.getDynamicTableName());
            sql.append(" group by ORGCODE ");
            return sql.toString();
        }

        public String listAll(Org2RpunitMappingQueryVO queryVO) {
            StringBuffer sql = new StringBuffer();
            sql.append("select t.ID, ");
            sql.append("t.ORGCODE, ");
            sql.append("o.name ORGNAME, ");
            sql.append("UNITCODE_1, ");
            sql.append("UNITCODE_2, ");
            sql.append("UNITCODE_3, ");
            sql.append("UNITCODE_4, ");
            sql.append("UNITCODE_5, ");
            sql.append("UNITCODE_6, ");
            sql.append("UNITCODE_7, ");
            sql.append("UNITCODE_8, ");
            sql.append("UNITCODE_9, ");
            sql.append("UNITCODE_10, ");
            sql.append("UNITCODE_11, ");
            sql.append("UNITCODE_12 ");
            sql.append("FROM ").append("DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" t \n");
            sql.append("LEFT JOIN md_org o ON t.orgcode=o.code \n");
            sql.append("where 1=1 \n");
            if (!CollectionUtils.isEmpty(queryVO.getOrgs())) {
                sql.append(" and  ");
                sql.append(SqlBuildUtil.getStrInCondi((String)"t.ORGCODE", (List)queryVO.getOrgs()));
            }
            if (!StringUtils.isEmpty((String)queryVO.getRpUnit())) {
                queryVO.setRpUnit(queryVO.getRpUnit() + "%");
                if (queryVO.getAcctPeriod() != null) {
                    Assert.isTrue((queryVO.getAcctPeriod() >= 1 && queryVO.getAcctPeriod() <= 12 ? 1 : 0) != 0);
                    sql.append(String.format(" and UNITCODE_%d LIKE #{rpUnit} ", queryVO.getAcctPeriod()));
                } else {
                    ArrayList<String> condiList = new ArrayList<String>(12);
                    for (int period = 1; period <= 12; ++period) {
                        condiList.add(String.format(" UNITCODE_%d LIKE #{rpUnit}", period));
                    }
                    sql.append(SqlUtil.concatCondi(condiList, (boolean)false));
                }
            }
            if (!CollectionUtils.isEmpty(queryVO.getDelIds())) {
                sql.append(" and  ");
                sql.append(SqlBuildUtil.getStrInCondi((String)"t.ID", (List)queryVO.getDelIds()));
            }
            sql.append("order by ORGCODE \n");
            return sql.toString();
        }

        public String listByOrgCodes(Org2RpunitMappingQueryVO queryVO) {
            Assert.isNotEmpty((Collection)queryVO.getOrgs());
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT  ");
            sql.append("t.ID, ");
            sql.append("t.ORGCODE ");
            sql.append("FROM ").append("DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" t \n");
            sql.append("LEFT JOIN md_org o ON t.orgcode=o.code \n");
            sql.append("WHERE 1=1 \n");
            sql.append(" AND  ");
            sql.append(SqlBuildUtil.getStrInCondi((String)"t.ORGCODE", (List)queryVO.getOrgs()));
            return sql.toString();
        }

        public String getListAllCount(Org2RpunitMappingQueryVO queryVO) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT COUNT(1)");
            sql.append("FROM ( \n");
            sql.append(this.listAll(queryVO));
            sql.append(") T \n");
            return sql.toString();
        }

        public String deleteByIds(Org2RpunitMappingQueryVO queryVO) {
            StringBuffer deleteSql = new StringBuffer();
            deleteSql.append("delete \n");
            deleteSql.append("FROM ").append("DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" \n");
            deleteSql.append("where 1=1 AND ").append(SqlBuildUtil.getStrInCondi((String)"ID", (List)queryVO.getDelIds())).append("\n");
            return deleteSql.toString();
        }

        public String getAllOrgCodeByRpUnitCode(Org2RpunitMappingQueryVO queryVO) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ORGCODE \n");
            sql.append("  FROM DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" \n");
            sql.append(" WHERE ").append(SqlBuildUtil.getStrInCondi((String)("UNITCODE_" + queryVO.getAcctPeriod()), (List)queryVO.getOrgs()));
            sql.append(" GROUP BY ORGCODE");
            return sql.toString();
        }

        public String listUnitCodeByOrgCodeAndPeriod(Org2RpunitMappingQueryVO queryVO) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT UNITCODE_%1$s \n");
            sql.append("  FROM DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" \n");
            sql.append(" WHERE ").append(SqlBuildUtil.getStrInCondi((String)"ORGCODE", (List)queryVO.getOrgs()));
            sql.append(" GROUP BY UNITCODE_%1$s");
            return String.format(sql.toString(), queryVO.getAcctPeriod());
        }

        public String getAllOrgCode(Org2RpunitMappingQueryVO queryVO) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT ORGCODE \n");
            sql.append("  FROM DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" \n");
            sql.append(" GROUP BY ORGCODE");
            return sql.toString();
        }
    }
}

