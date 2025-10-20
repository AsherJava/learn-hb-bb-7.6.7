/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil
 *  com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils$FieldType
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.adjustvchr.impl.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVoucherVO;
import com.jiuqi.dc.adjustvchr.impl.dao.AdjustVchrDao;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVoucherEO;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil;
import com.jiuqi.gcreport.dimension.internal.utils.FieldTypeUtils;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AdjustVchrDaoImpl
extends BaseDataCenterDaoImpl
implements AdjustVchrDao {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DimensionService dimensionService;

    @Override
    public List<AdjustVoucherVO> listByGroupIdList(List<String> groupList) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, T.VER, T.UNITCODE, T.VCHRNUM, T.ACCTYEAR, T.ADJUSTTYPECODE, \n");
        sql.append("\tT.AFFECTPERIODSTART, T.AFFECTPERIODEND, T.PERIODTYPE, T.ACCTPERIOD, T.GROUPID \n");
        sql.append("  FROM ").append("DC_ADJUSTVOUCHER").append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.GROUPID", groupList)).append(" \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(AdjustVoucherVO.class));
    }

    @Override
    public List<String> listGroupIdById(List<String> vchrIdList) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT GROUPID \n");
        sql.append("  FROM ").append("DC_ADJUSTVOUCHER");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"ID", vchrIdList)).append(" \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }

    @Override
    public List<AdjustVoucherVO> listByGroupId(String groupId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID, T.VER, T.UNITCODE, T.VCHRNUM, T.ACCTYEAR, T.ADJUSTTYPECODE, \n");
        sql.append("\tT.AFFECTPERIODSTART, T.AFFECTPERIODEND, T.PERIODTYPE, T.GROUPID, T.ACCTPERIOD \n");
        sql.append("  FROM ").append("DC_ADJUSTVOUCHER").append(" T \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND T.GROUPID = ? \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(AdjustVoucherVO.class), new Object[]{groupId});
    }

    @Override
    public void insertVoucher(AdjustVoucherEO voucherEO) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO DC_ADJUSTVOUCHER \n");
        sql.append(" (");
        Set<String> columnList = this.getColumnSet("DC_ADJUSTVOUCHER");
        StringBuilder sqlFields = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();
        for (String column : columnList) {
            sqlFields.append(column).append(",");
            sqlValues.append("?,");
        }
        sql.append(sqlFields.substring(0, sqlFields.length() - 1)).append("\n");
        sql.append(" ) \n");
        sql.append("VALUES (");
        sql.append(sqlValues.substring(0, sqlValues.length() - 1));
        sql.append(")");
        ArrayList paramList = CollectionUtils.newArrayList((Object[])new Object[]{voucherEO.getId(), voucherEO.getVer(), voucherEO.getUnitCode(), voucherEO.getAcctYear(), voucherEO.getAcctPeriod(), voucherEO.getVchrNum(), voucherEO.getCreateTime(), voucherEO.getCreator(), voucherEO.getModifyTime(), voucherEO.getModifyUser(), voucherEO.getAdjustTypeCode(), voucherEO.getAffectPeriodStart(), voucherEO.getAffectPeriodEnd(), voucherEO.getVchrSrcType(), voucherEO.getPeriodType(), voucherEO.getGroupId()});
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), paramList.toArray());
    }

    @Override
    public void batchInsertItem(List<AdjustVchrItemEO> itemEOList) {
        if (CollectionUtils.isEmpty(itemEOList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        Set<String> columnList = this.getColumnSet("DC_ADJUSTVCHRITEM");
        sql.append("INSERT INTO ").append("DC_ADJUSTVCHRITEM");
        StringBuilder sqlFileds = new StringBuilder();
        sqlFileds.append(" (");
        HashSet<String> fieldSet = new HashSet<String>();
        Set fieldVSet = DimensionManagerUtil.getExtendColumn((String)"DC_ADJUSTVCHRITEM").stream().map(DefinitionFieldV::getCode).collect(Collectors.toSet());
        LinkedHashSet<String> convertAmountColumnSet = new LinkedHashSet<String>();
        for (String column : columnList) {
            if (fieldVSet.contains(column)) continue;
            if (!"DEBIT".equals(column) && !"CREDIT".equals(column) && (column.endsWith("DEBIT") || column.endsWith("CREDIT"))) {
                convertAmountColumnSet.add(column);
            }
            fieldSet.add(column);
            sqlFileds.append(column).append(",");
        }
        for (Object assistDim : itemEOList.get(0).getAssistDims()) {
            if (fieldSet.contains(assistDim.getCode())) continue;
            sqlFileds.append(assistDim.getCode()).append(",");
        }
        sql.append(sqlFileds.substring(0, sqlFileds.length() - 1));
        sql.append(") ");
        StringBuilder sqlValues = new StringBuilder();
        sqlValues.append("VALUES (");
        for (String column : columnList) {
            if (fieldVSet.contains(column)) continue;
            sqlValues.append("?").append(",");
        }
        for (DimensionVO assistDim : itemEOList.get(0).getAssistDims()) {
            if (fieldSet.contains(assistDim.getCode())) continue;
            sqlValues.append("?,");
        }
        sql.append(sqlValues.substring(0, sqlValues.length() - 1));
        sql.append(")");
        ArrayList<Object[]> params = new ArrayList<Object[]>(itemEOList.size());
        for (AdjustVchrItemEO itemEO : itemEOList) {
            params.add(this.convertInsertParam(fieldSet, itemEO, convertAmountColumnSet));
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), params);
    }

    @Override
    public void updateVoucher(AdjustVoucherEO voucherEO) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE DC_ADJUSTVOUCHER \n");
        sql.append("   SET \n");
        Set<String> columnList = this.getColumnSet("DC_ADJUSTVOUCHER");
        StringBuilder sqlFields = new StringBuilder();
        for (String column : columnList) {
            if ("ID".equals(column) || "CREATETIME".equals(column) || "CREATOR".equals(column)) continue;
            sqlFields.append(column).append(" = ?,");
        }
        sql.append(sqlFields.substring(0, sqlFields.length() - 1)).append("\n");
        sql.append(" WHERE ID = ? ");
        ArrayList paramList = CollectionUtils.newArrayList((Object[])new Object[]{voucherEO.getVer(), voucherEO.getUnitCode(), voucherEO.getAcctYear(), voucherEO.getAcctPeriod(), voucherEO.getVchrNum(), voucherEO.getModifyTime(), voucherEO.getModifyUser(), voucherEO.getAdjustTypeCode(), voucherEO.getAffectPeriodStart(), voucherEO.getAffectPeriodEnd(), voucherEO.getVchrSrcType(), voucherEO.getPeriodType(), voucherEO.getGroupId(), voucherEO.getId()});
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), paramList.toArray());
    }

    @Override
    public int delVoucherByVchrIdList(List<String> vchrIdList) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("DC_ADJUSTVOUCHER").append("\n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"ID", vchrIdList)).append(" \n");
        return OuterDataSourceUtils.getJdbcTemplate().update(sql.toString());
    }

    @Override
    public void delItemByVchrIdList(List<String> vchrId) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("DC_ADJUSTVCHRITEM").append("\n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"VCHRID", vchrId));
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString());
    }

    private Set<String> getColumnSet(String tableName) {
        String outerDataSourceCode = OuterDataSourceUtils.getOuterDataSourceCode((String)"jiuqi.gcreport.mdd.datasource");
        TableModelDefine tableModelDefineByCode = this.dataModelService.getTableModelDefineByCode(tableName, outerDataSourceCode);
        List columnModelDefinesByTable = this.dataModelService.getColumnModelDefinesByTable(tableModelDefineByCode.getID());
        if (CollectionUtils.isEmpty((Collection)columnModelDefinesByTable)) {
            return new HashSet<String>();
        }
        LinkedHashSet<String> columnSet = new LinkedHashSet<String>();
        for (ColumnModelDefine columnModelDefine : columnModelDefinesByTable) {
            columnSet.add(columnModelDefine.getName());
        }
        return columnSet;
    }

    private Object[] convertInsertParam(Set<String> fieldSet, AdjustVchrItemEO itemEO, Set<String> convertAmountColumnSet) {
        ArrayList paramList = CollectionUtils.newArrayList((Object[])new Object[]{itemEO.getId(), itemEO.getVer(), itemEO.getVchrId(), itemEO.getVchrNum(), itemEO.getUnitCode(), itemEO.getAcctYear(), itemEO.getAcctPeriod(), itemEO.getItemOrder(), itemEO.getDigest(), itemEO.getSubjectCode(), itemEO.getSubjectName(), itemEO.getCurrencyCode(), itemEO.getDebit(), itemEO.getCredit(), itemEO.getExchrate(), itemEO.getVchrSrcType(), itemEO.getAdjustTypeCode(), itemEO.getBizDate(), itemEO.getExpireDate(), itemEO.getCfItemCode(), null, itemEO.getOrgnD(), itemEO.getOrgnC(), itemEO.getPeriodType()});
        for (String convertAmount : convertAmountColumnSet) {
            paramList.add(itemEO.getConvertAmount().get(convertAmount));
        }
        paramList.add(itemEO.getRemark());
        for (DimensionVO assistDim : itemEO.getAssistDims()) {
            if (fieldSet.contains(assistDim.getCode())) continue;
            if (itemEO.getAssistDatas().containsKey(assistDim.getCode())) {
                paramList.add(itemEO.getAssistDatas().get(assistDim.getCode()));
                continue;
            }
            if (FieldTypeUtils.FieldType.FIELD_TYPE_DATE.equals((Object)FieldTypeUtils.FieldType.getEnumByValue((int)assistDim.getFieldType()))) {
                paramList.add("1970-01-01");
                continue;
            }
            paramList.add("#");
        }
        return paramList.toArray();
    }
}

