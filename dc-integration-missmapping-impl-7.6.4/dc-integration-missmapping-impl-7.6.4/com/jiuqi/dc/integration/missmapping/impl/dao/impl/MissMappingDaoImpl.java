/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerRowMapper
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDO
 *  com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.integration.missmapping.impl.dao.impl;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerRowMapper;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.integration.missmapping.client.dto.MissMappingDO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO;
import com.jiuqi.dc.integration.missmapping.impl.dao.MissMappingDao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MissMappingDaoImpl
extends BaseDataCenterDaoImpl
implements MissMappingDao {
    @Override
    public List<String> queryDim(MissMappingQueryVO missMappingQueryVO) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT V.DIMCODE FROM DC_LOG_MISSMAPPING V \n");
        sql.append("  JOIN GC_LOG_TASKITEMINFO T ON V.RUNNERID = T.RUNNERID \n");
        sql.append("   AND V.TASKTYPE = T.TASKTYPE \n");
        sql.append(" WHERE 1 = 1 \n");
        this.getSqlCondi(missMappingQueryVO, sql, params);
        sql.append(" GROUP BY V.DIMCODE \n");
        sql.append(" ORDER BY V.DIMCODE \n");
        return this.query(sql.toString(), (RowMapper)new StringRowMapper(), params.toArray());
    }

    @Override
    public List<String> getDcOrg(String odsUnitCode) {
        if (StringUtils.isEmpty((String)odsUnitCode)) {
            return CollectionUtils.newArrayList();
        }
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ORG.CODE \n");
        sql.append("  FROM REF_MD_ORG ORG \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"ORG.ODS_CODE", Collections.singletonList(odsUnitCode))).append(" \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }

    @Override
    public List<MissMappingDO> getDetail(MissMappingQueryVO missMappingQueryVO) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT V.DATASCHEMECODE, V.UNITCODE, V.VCHRNUM\n");
        sql.append("   FROM DC_LOG_MISSMAPPING V \n");
        sql.append("  JOIN GC_LOG_TASKITEMINFO T ON V.RUNNERID = T.RUNNERID \n");
        sql.append("   AND V.TASKTYPE = T.TASKTYPE \n");
        sql.append("  WHERE 1 = 1 \n");
        this.getSqlCondi(missMappingQueryVO, sql, params);
        sql.append("  GROUP BY V.DATASCHEMECODE, V.UNITCODE, V.VCHRNUM \n");
        sql.append("  ORDER BY V.DATASCHEMECODE, V.UNITCODE, V.VCHRNUM \n");
        StringBuilder selectSql = new StringBuilder();
        selectSql.append(" SELECT V.DATASCHEMECODE, V.UNITCODE, V.VCHRNUM, V.DIMCODE,V.DIMVALUE \n");
        selectSql.append("   FROM DC_LOG_MISSMAPPING V \n");
        selectSql.append("   JOIN (##ORDERSQL##) P ON V.DATASCHEMECODE = P.DATASCHEMECODE AND V.UNITCODE = P.UNITCODE AND V.VCHRNUM = P.VCHRNUM\n");
        selectSql.append("  JOIN GC_LOG_TASKITEMINFO T ON V.RUNNERID = T.RUNNERID \n");
        selectSql.append("   AND V.TASKTYPE = T.TASKTYPE \n");
        this.getSqlCondi(missMappingQueryVO, selectSql, params);
        selectSql.append("  GROUP BY V.DATASCHEMECODE, V.UNITCODE, V.VCHRNUM, V.DIMCODE,V.DIMVALUE \n");
        String excuteSql = missMappingQueryVO.getExport() != false ? selectSql.toString().replace("##ORDERSQL##", sql.toString()) : selectSql.toString().replace("##ORDERSQL##", this.getDbSqlHandler().getPageSql(sql.toString(), missMappingQueryVO.getPage().intValue(), missMappingQueryVO.getPageSize().intValue()));
        return OuterDataSourceUtils.getJdbcTemplate().query(excuteSql, (RowMapper)new BeanPropertyRowMapper(MissMappingDO.class), params.toArray());
    }

    @Override
    public Integer getDetailTotal(MissMappingQueryVO missMappingQueryVO) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(1) FROM (\n");
        sql.append(" SELECT V.DATASCHEMECODE, V.UNITCODE, V.VCHRNUM\n");
        sql.append("   FROM DC_LOG_MISSMAPPING V \n");
        sql.append("  JOIN GC_LOG_TASKITEMINFO T ON V.RUNNERID = T.RUNNERID \n");
        sql.append("   AND V.TASKTYPE = T.TASKTYPE \n");
        sql.append("  WHERE 1 = 1 \n");
        this.getSqlCondi(missMappingQueryVO, sql, params);
        sql.append("  GROUP BY V.DATASCHEMECODE, V.UNITCODE, V.VCHRNUM \n");
        sql.append(" ) T\n");
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().queryForObject(sql.toString(), (RowMapper)new IntegerRowMapper(), params.toArray());
    }

    @Override
    public List<MissMappingDO> getGather(MissMappingQueryVO missMappingQueryVO) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT V.DATASCHEMECODE, V.DIMCODE, V.DIMVALUE \n");
        sql.append("   FROM DC_LOG_MISSMAPPING V \n");
        sql.append("   JOIN GC_LOG_TASKITEMINFO T ON V.RUNNERID = T.RUNNERID \n");
        sql.append("    AND V.TASKTYPE = T.TASKTYPE \n");
        sql.append("  WHERE 1 = 1 \n");
        this.getSqlCondi(missMappingQueryVO, sql, params);
        sql.append("  GROUP BY V.DATASCHEMECODE, V.DIMCODE, V.DIMVALUE \n");
        sql.append("  ORDER BY V.DATASCHEMECODE, V.DIMCODE, V.DIMVALUE \n");
        String pageSql = sql.toString();
        GcBizJdbcTemplate outerJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        if (!missMappingQueryVO.getExport().booleanValue()) {
            pageSql = outerJdbcTemplate.getIDbSqlHandler().getPageSql(sql.toString(), missMappingQueryVO.getPage().intValue(), missMappingQueryVO.getPageSize().intValue());
        }
        return outerJdbcTemplate.query(pageSql, (RowMapper)new BeanPropertyRowMapper(MissMappingDO.class), params.toArray());
    }

    @Override
    public Integer getGatherTotal(MissMappingQueryVO missMappingQueryVO) {
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(1) FROM ( \n");
        sql.append(" SELECT 1 \n");
        sql.append("   FROM DC_LOG_MISSMAPPING V \n");
        sql.append("   JOIN GC_LOG_TASKITEMINFO T ON V.RUNNERID = T.RUNNERID \n");
        sql.append("    AND V.TASKTYPE = T.TASKTYPE \n");
        sql.append("  WHERE 1 = 1 \n");
        this.getSqlCondi(missMappingQueryVO, sql, params);
        sql.append("  GROUP BY V.DATASCHEMECODE, V.DIMCODE, V.DIMVALUE \n");
        sql.append(" ) T");
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), params.toArray());
    }

    private void getSqlCondi(MissMappingQueryVO missMappingQueryVO, StringBuilder sql, List<Object> params) {
        if (!CollectionUtils.isEmpty((Collection)missMappingQueryVO.getUnitCodes())) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"V.UNITCODE", (List)missMappingQueryVO.getUnitCodes())).append(" \n");
        }
        if (missMappingQueryVO.getYear() != null) {
            sql.append("   AND V.ACCTYEAR = ? \n");
            params.add(missMappingQueryVO.getYear());
        }
        if (missMappingQueryVO.getPeriod() != null) {
            sql.append("   AND V.ACCTPERIOD = ? \n");
            params.add(missMappingQueryVO.getPeriod());
        }
        if (!StringUtils.isEmpty((String)missMappingQueryVO.getRunnerId())) {
            sql.append("   AND V.RUNNERID = ? \n");
            params.add(missMappingQueryVO.getRunnerId());
        }
        if (!StringUtils.isEmpty((String)missMappingQueryVO.getDataSchemeCode())) {
            sql.append("   AND V.DATASCHEMECODE = ? \n");
            params.add(missMappingQueryVO.getDataSchemeCode());
        }
        if (!StringUtils.isEmpty((String)missMappingQueryVO.getVchrNum())) {
            sql.append("   AND V.VCHRNUM LIKE ? \n");
            params.add("%" + missMappingQueryVO.getVchrNum().trim() + "%");
        }
        if (missMappingQueryVO.getStartDateTime() != null) {
            sql.append("   AND T.STARTTIME >= ? \n");
            params.add(missMappingQueryVO.getStartDateTime());
        }
        if (missMappingQueryVO.getEndDateTime() != null) {
            sql.append("   AND T.ENDTIME <= ? \n");
            params.add(missMappingQueryVO.getEndDateTime());
        }
    }
}

