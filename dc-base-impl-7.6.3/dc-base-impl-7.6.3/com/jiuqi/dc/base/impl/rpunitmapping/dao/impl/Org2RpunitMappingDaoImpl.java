/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerRowMapper
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.base.impl.rpunitmapping.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.client.rpunitmapping.queryvo.Org2RpunitMappingQueryVO;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerRowMapper;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import com.jiuqi.dc.base.impl.rpunitmapping.dao.Org2RpunitMappingDao;
import com.jiuqi.dc.base.impl.rpunitmapping.entity.Org2RpunitMappingEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class Org2RpunitMappingDaoImpl
implements Org2RpunitMappingDao {
    @Override
    public List<String> listOrgCode(Org2RpunitMappingEntity orgUnitMappingDO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ORGCODE ");
        sql.append("  FROM ").append(orgUnitMappingDO.getDynamicTableName());
        sql.append(" GROUP BY ORGCODE ");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }

    @Override
    public List<Map<String, Object>> listAll(Org2RpunitMappingQueryVO queryVO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, ");
        sql.append("       T.ORGCODE, ");
        sql.append("       UNITCODE_1, ");
        sql.append("       UNITCODE_2, ");
        sql.append("       UNITCODE_3, ");
        sql.append("       UNITCODE_4, ");
        sql.append("       UNITCODE_5, ");
        sql.append("       UNITCODE_6, ");
        sql.append("       UNITCODE_7, ");
        sql.append("       UNITCODE_8, ");
        sql.append("       UNITCODE_9, ");
        sql.append("       UNITCODE_10, ");
        sql.append("       UNITCODE_11, ");
        sql.append("       UNITCODE_12 ");
        sql.append("  FROM ").append("DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" T \n");
        sql.append(" WHERE 1=1 \n");
        if (!CollectionUtils.isEmpty((Collection)queryVO.getOrgs())) {
            sql.append(" AND  ");
            sql.append(SqlBuildUtil.getStrInCondi((String)"T.ORGCODE", (List)queryVO.getOrgs()));
        }
        ArrayList paramList = CollectionUtils.newArrayList();
        if (!StringUtils.isEmpty((String)queryVO.getRpUnit())) {
            queryVO.setRpUnit(queryVO.getRpUnit() + "%");
            if (queryVO.getAcctPeriod() != null) {
                Assert.isTrue((queryVO.getAcctPeriod() >= 1 && queryVO.getAcctPeriod() <= 12 ? 1 : 0) != 0);
                sql.append(String.format(" and UNITCODE_%d LIKE ? ", queryVO.getAcctPeriod()));
                paramList.add(queryVO.getRpUnit());
            } else {
                ArrayList<String> condiList = new ArrayList<String>(12);
                for (int period = 1; period <= 12; ++period) {
                    condiList.add(String.format(" UNITCODE_%d LIKE ?", period));
                    paramList.add(queryVO.getRpUnit());
                }
                sql.append(SqlUtil.concatCondi(condiList, (boolean)false));
            }
        }
        if (!CollectionUtils.isEmpty((Collection)queryVO.getDelIds())) {
            sql.append(" AND  ");
            sql.append(SqlBuildUtil.getStrInCondi((String)"T.ID", (List)queryVO.getDelIds()));
        }
        sql.append("ORDER BY ORGCODE \n");
        return OuterDataSourceUtils.getJdbcTemplate().queryForList(sql.toString(), paramList.toArray());
    }

    @Override
    public List<Org2RpunitMappingEntity> listByOrgCodes(Org2RpunitMappingQueryVO queryVO) {
        if (CollectionUtils.isEmpty((Collection)queryVO.getOrgs())) {
            return CollectionUtils.newArrayList();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, T.ORGCODE ");
        sql.append("  FROM ").append("DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" T \n");
        sql.append(" WHERE 1=1 \n");
        sql.append("   AND  ");
        sql.append(SqlBuildUtil.getStrInCondi((String)"T.ORGCODE", (List)queryVO.getOrgs()));
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(Org2RpunitMappingEntity.class));
    }

    @Override
    public int getListAllCount(Org2RpunitMappingQueryVO queryVO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1)");
        sql.append("  FROM ").append("DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" T \n");
        sql.append(" WHERE 1=1 \n");
        if (!CollectionUtils.isEmpty((Collection)queryVO.getOrgs())) {
            sql.append(" AND  ");
            sql.append(SqlBuildUtil.getStrInCondi((String)"T.ORGCODE", (List)queryVO.getOrgs()));
        }
        ArrayList paramList = CollectionUtils.newArrayList();
        if (!StringUtils.isEmpty((String)queryVO.getRpUnit())) {
            queryVO.setRpUnit(queryVO.getRpUnit() + "%");
            if (queryVO.getAcctPeriod() != null) {
                Assert.isTrue((queryVO.getAcctPeriod() >= 1 && queryVO.getAcctPeriod() <= 12 ? 1 : 0) != 0);
                sql.append(String.format(" and UNITCODE_%d LIKE ? ", queryVO.getAcctPeriod()));
                paramList.add(queryVO.getRpUnit());
            } else {
                ArrayList<String> condiList = new ArrayList<String>(12);
                for (int period = 1; period <= 12; ++period) {
                    condiList.add(String.format(" UNITCODE_%d LIKE ?", period));
                    paramList.add(queryVO.getRpUnit());
                }
                sql.append(SqlUtil.concatCondi(condiList, (boolean)false));
            }
        }
        if (!CollectionUtils.isEmpty((Collection)queryVO.getDelIds())) {
            sql.append(" AND  ");
            sql.append(SqlBuildUtil.getStrInCondi((String)"T.ID", (List)queryVO.getDelIds()));
        }
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().queryForObject(sql.toString(), (RowMapper)new IntegerRowMapper(), paramList.toArray());
    }

    @Override
    public int deleteByIds(Org2RpunitMappingQueryVO queryVO) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("DELETE \n");
        deleteSql.append("  FROM ").append("DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" \n");
        deleteSql.append(" WHERE 1 = 1 AND ").append(SqlBuildUtil.getStrInCondi((String)"ID", (List)queryVO.getDelIds())).append("\n");
        return OuterDataSourceUtils.getJdbcTemplate().update(deleteSql.toString());
    }

    @Override
    public List<String> getAllOrgCodeByRpUnitCode(Org2RpunitMappingQueryVO queryVO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ORGCODE \n");
        sql.append("  FROM DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" \n");
        sql.append(" WHERE ").append(SqlBuildUtil.getStrInCondi((String)("UNITCODE_" + queryVO.getAcctPeriod()), (List)queryVO.getOrgs()));
        sql.append(" GROUP BY ORGCODE");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }

    @Override
    public List<String> getAllOrgCode(Org2RpunitMappingQueryVO queryVO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ORGCODE \n");
        sql.append("  FROM DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" \n");
        sql.append(" GROUP BY ORGCODE");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }

    @Override
    public List<String> listUnitCodeByOrgCodeAndPeriod(Org2RpunitMappingQueryVO queryVO) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT UNITCODE_%1$s \n");
        sql.append("  FROM DC_ORG_RPUNITMAPPING_").append(queryVO.getAcctYear()).append(" \n");
        sql.append(" WHERE ").append(SqlBuildUtil.getStrInCondi((String)"ORGCODE", (List)queryVO.getOrgs()));
        sql.append(" GROUP BY UNITCODE_%1$s");
        return OuterDataSourceUtils.getJdbcTemplate().query(String.format(sql.toString(), queryVO.getAcctPeriod()), (RowMapper)new StringRowMapper());
    }

    @Override
    public void updateByPrimaryKey(Org2RpunitMappingEntity entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE DC_ORG_RPUNITMAPPING_").append(entity.getAcctYear()).append(" \n");
        sql.append("   SET ORGCODE  = ?,  \n");
        sql.append("       UNITCODE_1  = ?,  \n");
        sql.append("       UNITCODE_2  = ?,  \n");
        sql.append("       UNITCODE_3  = ?,  \n");
        sql.append("       UNITCODE_4  = ?,  \n");
        sql.append("       UNITCODE_5  = ?,  \n");
        sql.append("       UNITCODE_6  = ?,  \n");
        sql.append("       UNITCODE_7  = ?,  \n");
        sql.append("       UNITCODE_8  = ?,  \n");
        sql.append("       UNITCODE_9  = ?,  \n");
        sql.append("       UNITCODE_10 = ?,  \n");
        sql.append("       UNITCODE_11 = ?,  \n");
        sql.append("       UNITCODE_12 = ?  \n");
        sql.append(" WHERE ID = ?  \n");
        ArrayList paramList = CollectionUtils.newArrayList((Object[])new String[]{entity.getOrgCode(), entity.getUnitCode1(), entity.getUnitCode2(), entity.getUnitCode3(), entity.getUnitCode4(), entity.getUnitCode5(), entity.getUnitCode6(), entity.getUnitCode7(), entity.getUnitCode8(), entity.getUnitCode9(), entity.getUnitCode10(), entity.getUnitCode11(), entity.getUnitCode12(), entity.getId()});
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), paramList.toArray());
    }

    @Override
    public void insert(Org2RpunitMappingEntity entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO DC_ORG_RPUNITMAPPING_").append(entity.getAcctYear()).append(" \n");
        sql.append("  (    ID,  \n");
        sql.append("       ORGCODE,  \n");
        sql.append("       UNITCODE_1,  \n");
        sql.append("       UNITCODE_2,  \n");
        sql.append("       UNITCODE_3,  \n");
        sql.append("       UNITCODE_4,  \n");
        sql.append("       UNITCODE_5,  \n");
        sql.append("       UNITCODE_6,  \n");
        sql.append("       UNITCODE_7,  \n");
        sql.append("       UNITCODE_8,  \n");
        sql.append("       UNITCODE_9,  \n");
        sql.append("       UNITCODE_10,  \n");
        sql.append("       UNITCODE_11,  \n");
        sql.append("       UNITCODE_12  )\n");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ArrayList paramList = CollectionUtils.newArrayList((Object[])new String[]{entity.getId(), entity.getOrgCode(), entity.getUnitCode1(), entity.getUnitCode2(), entity.getUnitCode3(), entity.getUnitCode4(), entity.getUnitCode5(), entity.getUnitCode6(), entity.getUnitCode7(), entity.getUnitCode8(), entity.getUnitCode9(), entity.getUnitCode10(), entity.getUnitCode11(), entity.getUnitCode12()});
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), paramList.toArray());
    }

    @Override
    public List<Org2RpunitMappingEntity> select(Org2RpunitMappingEntity condi) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, T.ORGCODE ");
        sql.append("  FROM ").append("DC_ORG_RPUNITMAPPING_").append(condi.getAcctYear()).append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        ArrayList paramList = CollectionUtils.newArrayList();
        if (!StringUtils.isEmpty((String)condi.getOrgCode())) {
            sql.append("   AND T.ORGCODE = ? ");
            paramList.add(condi.getOrgCode());
        }
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(Org2RpunitMappingEntity.class), paramList.toArray());
    }

    @Override
    public void batchInsert(List<Org2RpunitMappingEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO DC_ORG_RPUNITMAPPING_").append(entityList.get(0).getAcctYear()).append(" \n");
        sql.append("  (    ID,  \n");
        sql.append("       ORGCODE,  \n");
        sql.append("       UNITCODE_1,  \n");
        sql.append("       UNITCODE_2,  \n");
        sql.append("       UNITCODE_3,  \n");
        sql.append("       UNITCODE_4,  \n");
        sql.append("       UNITCODE_5,  \n");
        sql.append("       UNITCODE_6,  \n");
        sql.append("       UNITCODE_7,  \n");
        sql.append("       UNITCODE_8,  \n");
        sql.append("       UNITCODE_9,  \n");
        sql.append("       UNITCODE_10,  \n");
        sql.append("       UNITCODE_11,  \n");
        sql.append("       UNITCODE_12) \n");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ArrayList paramList = CollectionUtils.newArrayList();
        for (Org2RpunitMappingEntity entity : entityList) {
            paramList.add(CollectionUtils.newArrayList((Object[])new String[]{entity.getId(), entity.getOrgCode(), entity.getUnitCode1(), entity.getUnitCode2(), entity.getUnitCode3(), entity.getUnitCode4(), entity.getUnitCode5(), entity.getUnitCode6(), entity.getUnitCode7(), entity.getUnitCode8(), entity.getUnitCode9(), entity.getUnitCode10(), entity.getUnitCode11(), entity.getUnitCode12()}).toArray());
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), (List)paramList);
    }

    @Override
    public void batchUpdate(List<Org2RpunitMappingEntity> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE DC_ORG_RPUNITMAPPING_").append(entityList.get(0).getAcctYear()).append(" \n");
        sql.append("   SET ORGCODE  = ?,  \n");
        sql.append("       UNITCODE_1  = ?,  \n");
        sql.append("       UNITCODE_2  = ?,  \n");
        sql.append("       UNITCODE_3  = ?,  \n");
        sql.append("       UNITCODE_4  = ?,  \n");
        sql.append("       UNITCODE_5  = ?,  \n");
        sql.append("       UNITCODE_6  = ?,  \n");
        sql.append("       UNITCODE_7  = ?,  \n");
        sql.append("       UNITCODE_8  = ?,  \n");
        sql.append("       UNITCODE_9  = ?,  \n");
        sql.append("       UNITCODE_10 = ?,  \n");
        sql.append("       UNITCODE_11 = ?,  \n");
        sql.append("       UNITCODE_12 = ?  \n");
        sql.append(" WHERE ID = ?  \n");
        ArrayList paramList = CollectionUtils.newArrayList();
        for (Org2RpunitMappingEntity entity : entityList) {
            paramList.add(CollectionUtils.newArrayList((Object[])new String[]{entity.getOrgCode(), entity.getUnitCode1(), entity.getUnitCode2(), entity.getUnitCode3(), entity.getUnitCode4(), entity.getUnitCode5(), entity.getUnitCode6(), entity.getUnitCode7(), entity.getUnitCode8(), entity.getUnitCode9(), entity.getUnitCode10(), entity.getUnitCode11(), entity.getUnitCode12(), entity.getId()}).toArray());
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), (List)paramList);
    }
}

