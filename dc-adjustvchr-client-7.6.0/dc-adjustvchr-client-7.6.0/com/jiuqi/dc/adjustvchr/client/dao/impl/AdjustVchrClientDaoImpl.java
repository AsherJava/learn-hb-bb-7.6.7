/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils$FieldType
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.adjustvchr.client.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.adjustvchr.client.dao.AdjustVchrClientDao;
import com.jiuqi.dc.adjustvchr.client.domain.AdjustVchrIdListDTO;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class AdjustVchrClientDaoImpl
extends BaseDataCenterDaoImpl
implements AdjustVchrClientDao {
    @Autowired
    private DimensionService dimensionService;

    @Override
    public String getMaxVchrNum(String unitCode, int acctYear) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MAX(T.VCHRNUM) VCHRNUM \n");
        sql.append("  FROM ").append("DC_ADJUSTVOUCHER").append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND T.UNITCODE = ? \n");
        sql.append("   AND T.ACCTYEAR = ? \n");
        return (String)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new StringResultSetExtractor(), new Object[]{unitCode, acctYear});
    }

    @Override
    public int countByCondi(AdjustVoucherQueryDTO param) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) \n");
        sql.append("  FROM ").append("DC_ADJUSTVOUCHER").append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        if (!CollectionUtils.isEmpty(param.getUnitCodes())) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.UNITCODE", param.getUnitCodes())).append(" \n");
        }
        ArrayList<Object> params = new ArrayList<Object>();
        if (param.getAcctYear() != null) {
            sql.append("   AND T.ACCTYEAR = ? \n");
            params.add(param.getAcctYear());
        }
        sql.append("   AND T.PERIODTYPE = ?").append(" \n");
        params.add(param.getPeriodType());
        if (!"N".equals(param.getPeriodType())) {
            sql.append("   AND T.ACCTPERIOD = ? \n");
            params.add(param.getAffectPeriodStart());
        }
        if (param.getAdjustTypeCode() != null && !param.getAdjustTypeCode().isEmpty()) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.ADJUSTTYPECODE", param.getAdjustTypeCode()));
        }
        if (!StringUtils.isEmpty((String)param.getVchrNum())) {
            sql.append("   AND T.VCHRNUM LIKE ? \n");
            params.add('%' + param.getVchrNum() + '%');
        }
        if (!CollectionUtils.isEmpty(param.getExportIdList())) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.ID", param.getExportIdList()));
        }
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (ResultSetExtractor)new IntegerResultSetExtractor(), params.toArray());
    }

    @Override
    public List<AdjustVoucherVO> listByCondi(AdjustVoucherQueryDTO param) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, T.VER, T.UNITCODE, T.VCHRNUM, T.ACCTYEAR, T.ADJUSTTYPECODE, \n");
        sql.append("\tT.AFFECTPERIODSTART, T.AFFECTPERIODEND, T.PERIODTYPE, T.CREATOR, T.CREATETIME, T.MODIFYUSER, T.MODIFYTIME, T.GROUPID \n");
        sql.append("  FROM ").append("DC_ADJUSTVOUCHER").append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        if (!CollectionUtils.isEmpty(param.getUnitCodes())) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.UNITCODE", param.getUnitCodes())).append(" \n");
        }
        ArrayList<Object> params = new ArrayList<Object>();
        if (param.getAcctYear() != null) {
            sql.append("   AND T.ACCTYEAR = ? \n");
            params.add(param.getAcctYear());
        }
        sql.append("   AND T.PERIODTYPE = ?").append(" \n");
        params.add(param.getPeriodType());
        if (!"N".equals(param.getPeriodType())) {
            sql.append("   AND T.ACCTPERIOD = ? \n");
            params.add(param.getAffectPeriodStart());
        }
        if (param.getAdjustTypeCode() != null && !param.getAdjustTypeCode().isEmpty()) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.ADJUSTTYPECODE", param.getAdjustTypeCode()));
        }
        if (!StringUtils.isEmpty((String)param.getVchrNum())) {
            sql.append("   AND T.VCHRNUM LIKE ? \n");
            params.add('%' + param.getVchrNum() + '%');
        }
        if (!CollectionUtils.isEmpty(param.getExportIdList())) {
            sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.ID", param.getExportIdList()));
        }
        IDbSqlHandler sqlHandler = OuterDataSourceUtils.getJdbcTemplate().getIDbSqlHandler();
        sql.append(" ORDER BY T.UNITCODE, GREATEST(T.CREATETIME, ");
        sql.append(sqlHandler.nullToValue("T.MODIFYTIME", sqlHandler.toDate("'1970-01-01'", "'yyyy-mm-dd'"))).append(") DESC\n");
        String tempSql = sql.toString();
        if (param.getPagination() == null || param.getPagination().booleanValue()) {
            tempSql = sqlHandler.getPageSql(sql.toString(), param.getPage().intValue(), param.getPageSize().intValue());
        }
        return OuterDataSourceUtils.getJdbcTemplate().query(tempSql, (RowMapper)new BeanPropertyRowMapper(AdjustVoucherVO.class), params.toArray());
    }

    @Override
    public List<AdjustVchrItemEO> listByVchrId(AdjustVchrIdListDTO param) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.ID, T.VER, T.VCHRID, T.VCHRNUM, T.UNITCODE, T.ACCTYEAR, T.ACCTPERIOD, T.ITEMORDER, \n");
        sql.append("\tT.DIGEST, T.SUBJECTCODE, T.CURRENCYCODE, T.DEBIT, T.CREDIT, T.ORGND, T.ORGNC, T.EXCHRATE, \n");
        sql.append("\tT.CFITEMCODE, T.BIZDATE, T.EXPIREDATE, T.VCHRSRCTYPE, T.REMARK \n");
        for (String convertAmntCol : param.getConvertAmountCols()) {
            sql.append(", T.").append(convertAmntCol);
        }
        if (param.getAssistDims() != null && !param.getAssistDims().isEmpty()) {
            for (DimensionVO assistDim : param.getAssistDims()) {
                if (!StringUtils.isEmpty((String)assistDim.getReferField())) {
                    sql.append(String.format(", CASE WHEN T.%1$s <> '#' THEN T.%1$s ELSE NULL END AS %1$s", assistDim.getCode()));
                    continue;
                }
                if (FieldTypeUtils.FieldType.FIELD_TYPE_DATE.equals((Object)FieldTypeUtils.FieldType.getEnumByValue((int)assistDim.getFieldType()))) {
                    sql.append(String.format(", CASE WHEN T.%1$s <> '%2$s' THEN T.%1$s ELSE NULL END AS %1$s", assistDim.getCode(), "1970-01-01"));
                    continue;
                }
                sql.append(String.format(", CASE WHEN T.%1$s <> '#' THEN T.%1$s ELSE NULL END AS %1$s", assistDim.getCode()));
            }
        }
        sql.append("  FROM ").append("DC_ADJUSTVCHRITEM").append(" T \n");
        sql.append("  JOIN ").append("DC_ADJUSTVOUCHER").append(" V ON V.ID = T.VCHRID \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"V.ID", param.getVchrIds())).append(" \n");
        sql.append(" ORDER BY T.ITEMORDER ASC \n");
        List assistDimList = this.dimensionService.findAllDimFieldsVOByTableName("DC_ADJUSTVCHRITEM");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (rs, row) -> {
            AdjustVchrItemEO adjustVchrItemEO = new AdjustVchrItemEO();
            adjustVchrItemEO.setId(rs.getString("ID"));
            adjustVchrItemEO.setVer(rs.getLong("VER"));
            adjustVchrItemEO.setVchrId(rs.getString("VCHRID"));
            adjustVchrItemEO.setVchrNum(rs.getString("VCHRNUM"));
            adjustVchrItemEO.setUnitCode(rs.getString("UNITCODE"));
            adjustVchrItemEO.setAcctYear(rs.getInt("ACCTYEAR"));
            adjustVchrItemEO.setAcctPeriod(rs.getInt("ACCTPERIOD"));
            adjustVchrItemEO.setItemOrder(rs.getInt("ITEMORDER"));
            adjustVchrItemEO.setDigest(rs.getString("DIGEST"));
            adjustVchrItemEO.setSubjectCode(rs.getString("SUBJECTCODE"));
            adjustVchrItemEO.setCurrencyCode(rs.getString("CURRENCYCODE"));
            adjustVchrItemEO.setDebit(rs.getBigDecimal("DEBIT").doubleValue() == 0.0 ? null : rs.getBigDecimal("DEBIT"));
            adjustVchrItemEO.setCredit(rs.getBigDecimal("CREDIT").doubleValue() == 0.0 ? null : rs.getBigDecimal("CREDIT"));
            adjustVchrItemEO.setOrgnD(rs.getBigDecimal("ORGND").doubleValue() == 0.0 ? null : rs.getBigDecimal("ORGND"));
            adjustVchrItemEO.setOrgnC(rs.getBigDecimal("ORGNC").doubleValue() == 0.0 ? null : rs.getBigDecimal("ORGNC"));
            adjustVchrItemEO.setExchrate(rs.getBigDecimal("EXCHRATE"));
            adjustVchrItemEO.setCfItemCode(rs.getString("CFITEMCODE"));
            adjustVchrItemEO.setBizDate(rs.getDate("BIZDATE"));
            adjustVchrItemEO.setExpireDate(rs.getDate("EXPIREDATE"));
            adjustVchrItemEO.setVchrSrcType(rs.getString("VCHRSRCTYPE"));
            adjustVchrItemEO.setRemark(rs.getString("REMARK"));
            HashMap<String, BigDecimal> convertAmntColMap = new HashMap<String, BigDecimal>();
            for (String convertAmntCol : param.getConvertAmountCols()) {
                convertAmntColMap.put(convertAmntCol, rs.getBigDecimal(convertAmntCol).doubleValue() == 0.0 ? null : rs.getBigDecimal(convertAmntCol));
            }
            adjustVchrItemEO.setConvertAmount(convertAmntColMap);
            HashMap<String, Object> assistDatasMap = new HashMap<String, Object>();
            for (DimensionVO assistDimVO : assistDimList) {
                if (ObjectUtils.isEmpty(rs.getObject(assistDimVO.getCode()))) continue;
                assistDatasMap.put(assistDimVO.getCode(), rs.getObject(assistDimVO.getCode()));
            }
            adjustVchrItemEO.setAssistDatas(assistDatasMap);
            return adjustVchrItemEO;
        });
    }

    @Override
    public List<String> listAccountSubject() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT (SUBJECTCODE) FROM DC_SCHEME_ACCOUNTAGE WHERE SUBJECTCODE IS NOT NULL \n");
        return this.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }

    @Override
    public List<String> listReclassfySubject() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT (SOURCESUBJECTCODE) FROM DC_SCHEME_RECLASSIFY \n");
        sql.append("   WHERE RECLASSIFYRULE <> '000001' AND RECLASSIFYRULE <> '000000' \n");
        return this.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }
}

