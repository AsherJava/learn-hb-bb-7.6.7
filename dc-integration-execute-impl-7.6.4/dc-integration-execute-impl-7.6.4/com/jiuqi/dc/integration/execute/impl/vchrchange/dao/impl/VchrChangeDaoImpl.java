/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.intf.impl.VchrMasterDim
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.dc.base.common.utils.CommonUtil
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.integration.execute.impl.vchrchange.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.intf.impl.VchrMasterDim;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.base.common.utils.CommonUtil;
import com.jiuqi.dc.integration.execute.impl.vchrchange.dao.VchrChangeDao;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeDO;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrChangeDim;
import com.jiuqi.dc.integration.execute.impl.vchrchange.data.VchrDeleteDim;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VchrChangeDaoImpl
extends BaseDataCenterDaoImpl
implements VchrChangeDao {
    @Autowired
    private DynamicDataSourceService dynamicDataSourceService;

    @Override
    public List<VchrChangeDim> queryVchrChangeDim(String dataSchemeCode, String odsUnitId) {
        ArrayList paramList = CollectionUtils.newArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.DATASCHEMECODE, T.UNITCODE AS ODSUNITCODE, T.ACCTYEAR \n");
        sql.append("  FROM ").append("DC_VCHRCHANGEINFO").append(" T \n");
        sql.append(" WHERE T.CREATEOFFSETVCHR = 0 \n");
        if (!StringUtils.isEmpty((String)dataSchemeCode)) {
            sql.append("   AND T.DATASCHEMECODE = ? \n");
            paramList.add(dataSchemeCode);
        }
        if (!StringUtils.isEmpty((String)odsUnitId)) {
            sql.append("   AND T.UNITCODE = ? \n");
            paramList.add(odsUnitId);
        }
        sql.append(" GROUP BY T.DATASCHEMECODE, T.UNITCODE, T.ACCTYEAR");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(VchrChangeDim.class), paramList.toArray());
    }

    @Override
    public void updateTempVchrItemAssId(VchrChangeDim param) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_TEMP_VCHRITEMASS").append(" T \n");
        sql.append("   SET SRCITEMASSID = (").append(this.getDbSqlHandler().concat(new String[]{"'OFFSET_'", "T.VCHRID"})).append("), \n");
        sql.append(" SRCVCHRID = (").append(this.getDbSqlHandler().concat(new String[]{"'OFFSET_'", "T.VCHRID"})).append(") \n");
        sql.append(" WHERE EXISTS (SELECT 1 FROM ").append("DC_VCHRCHANGEINFO").append(" C \n");
        sql.append("\t\t\t\tWHERE C.UNITCODE = ? AND C.ACCTYEAR = ? AND C.DATASCHEMECODE = ? AND C.CREATEOFFSETVCHR = 0 \n");
        sql.append("\t\t\t\t  AND C.SRCVCHRID = T.SRCVCHRID ) \n");
        this.update(sql.toString(), new Object[]{param.getOdsUnitCode(), param.getAcctYear(), param.getDataSchemeCode()});
    }

    @Override
    public void updateDcSrcVchrItemAssId(VchrChangeDim param) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(CommonUtil.getVoucherItemAssTableName((int)param.getAcctYear())).append(" T \n");
        sql.append("   SET SRCITEMASSID = (").append(this.getDbSqlHandler().concat(new String[]{"'OFFSET_'", "T.VCHRID"})).append("), \n");
        sql.append(" SRCVCHRID = (").append(this.getDbSqlHandler().concat(new String[]{"'OFFSET_'", "T.VCHRID"})).append(") \n");
        sql.append(" WHERE EXISTS (SELECT 1 FROM ").append("DC_TEMP_VCHRITEMASS").append(" TEMP \n");
        sql.append("\t\t\t\tWHERE T.ID = TEMP.ID) \n");
        this.update(sql.toString(), new Object[0]);
    }

    @Override
    public void updateDcCfSrcVchrId(VchrChangeDim param) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(CommonUtil.getCfVoucherItemAssTableName((int)param.getAcctYear())).append(" T \n");
        sql.append("   SET SRCITEMID = (").append(this.getDbSqlHandler().concat(new String[]{"'OFFSET_'", "T.SRCITEMID"})).append("), \n");
        sql.append("    SRCVCHRID = (").append(this.getDbSqlHandler().concat(new String[]{"'OFFSET_'", "T.VCHRID"})).append(") \n");
        sql.append(" WHERE EXISTS (SELECT 1 FROM ").append("DC_TEMP_VCHRITEMASS").append(" TEMP \n");
        sql.append("                WHERE T.SRCVCHRID = TEMP.VCHRID) \n");
        this.update(sql.toString(), new Object[0]);
    }

    @Override
    public void prepareVchrTemp(VchrChangeDim param, List<DimensionVO> assistDims) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("DC_TEMP_VCHRITEMASS").append(" \n");
        sql.append("  (ID, %1$s %2$s) \n");
        sql.append(" SELECT ID, %1$s %3$s \n");
        StringBuffer insertField = new StringBuffer();
        StringBuffer selectField = new StringBuffer();
        if (assistDims != null && !assistDims.isEmpty()) {
            for (DimensionVO assistDim : assistDims) {
                insertField.append(", ").append(assistDim.getCode());
                selectField.append(", ").append(assistDim.getConvertByOpposite() == 1 ? "SRC" + assistDim.getCode() : assistDim.getCode());
            }
        }
        sql.append("   FROM ").append(CommonUtil.getVoucherItemAssTableName((int)param.getAcctYear())).append(" ITEM \n");
        sql.append("  WHERE 1 = 1 \n");
        sql.append(" AND ITEM.UNITCODE = ? \n");
        sql.append("    AND EXISTS (SELECT 1 FROM ").append("DC_VCHRCHANGEINFO").append(" C \n");
        sql.append("\t\t\t\tWHERE C.UNITCODE = ? AND C.ACCTYEAR = ? AND C.DATASCHEMECODE = ? AND C.CREATEOFFSETVCHR = 0 \n");
        sql.append("\t\t\t\t  AND C.SRCVCHRID = ITEM.SRCVCHRID ) \n");
        this.update(String.format(sql.toString(), this.getVchrItemAssField(false), insertField, selectField), new Object[]{param.getUnitCode(), param.getOdsUnitCode(), param.getAcctYear(), param.getDataSchemeCode()});
    }

    @Override
    public void insertOffsetVchrFromTemp(VchrChangeDim param, List<DimensionVO> assistDims, VchrMasterDim dim) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(CommonUtil.getVoucherItemAssTableName((int)param.getAcctYear())).append(" \n");
        sql.append("  (ID, SN, %1$s %2$s) \n");
        sql.append(" SELECT %3$s as ID, ? as SN, %4$s %5$s \n");
        StringBuffer insertField = new StringBuffer();
        StringBuffer selectField = new StringBuffer();
        if (assistDims != null && !assistDims.isEmpty()) {
            for (DimensionVO assistDim : assistDims) {
                insertField.append(", ").append(assistDim.getConvertByOpposite() == 1 ? "SRC" + assistDim.getCode() : assistDim.getCode());
                selectField.append(", ").append(assistDim.getCode());
            }
        }
        sql.append("   FROM ").append("DC_TEMP_VCHRITEMASS").append(" TEMP \n");
        sql.append("  WHERE TEMP.ACCTPERIOD = ? \n");
        this.update(String.format(sql.toString(), this.getVchrItemAssField(false), insertField, this.getDbSqlHandler().newUUID(), this.getVchrItemAssField(true), selectField), new Object[]{dim.getBatchNum(), dim.getAcctPeriod()});
    }

    @Override
    public void insertCfOffsetVchrFromTemp(VchrChangeDim param, List<DimensionVO> assistDims, VchrMasterDim dim) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %6$s \n");
        sql.append("  (ID, SN, %1$s %2$s) \n");
        sql.append(" SELECT %3$s as ID, ? as SN, %4$s %5$s \n");
        StringBuffer insertField = new StringBuffer();
        StringBuffer selectField = new StringBuffer();
        if (assistDims != null && !assistDims.isEmpty()) {
            for (DimensionVO assistDim : assistDims) {
                insertField.append(", ").append(assistDim.getConvertByOpposite() == 1 ? "SRC" + assistDim.getCode() : assistDim.getCode());
                selectField.append(", CV.").append(assistDim.getCode());
            }
        }
        sql.append("   FROM %6$s CV \n");
        sql.append("  WHERE CV.UNITCODE = ? AND CV.ACCTPERIOD = ? ");
        sql.append("    AND EXISTS (SELECT 1 FROM %7$s TEMP \n");
        sql.append("  \t\t\t\t WHERE TEMP.UNITCODE = CV.UNITCODE \n");
        sql.append(" \t\t           AND TEMP.ACCTPERIOD = CV.ACCTPERIOD \n");
        sql.append(" \t\t           AND TEMP.VCHRID = CV.SRCVCHRID \n");
        sql.append(" \t\t           AND TEMP.SRCITEMID = CV.SRCITEMID)\n");
        this.update(String.format(sql.toString(), this.getCfVchrItemAssField(null, false), insertField, this.getDbSqlHandler().newUUID(), this.getCfVchrItemAssField("CV", true), selectField, CommonUtil.getCfVoucherItemAssTableName((int)param.getAcctYear()), "DC_TEMP_VCHRITEMASS"), new Object[]{dim.getBatchNum(), dim.getUnitCode(), dim.getAcctPeriod()});
    }

    @Override
    public void updateVchrChangeInfoOffsetGroupId(VchrChangeDim param) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_VCHRCHANGEINFO").append(" T \n");
        sql.append("   SET OFFSETGROUPID = (");
        sql.append(this.getDbSqlHandler().concat(new String[]{"'OFFSET_'", "T.SRCVCHRID"})).append(") \n");
        sql.append(" WHERE T.UNITCODE = ? \n");
        sql.append("   AND T.ACCTYEAR = ? \n");
        sql.append("   AND T.DATASCHEMECODE = ? \n");
        sql.append("   AND T.CREATEOFFSETVCHR = 0 \n");
        this.update(sql.toString(), new Object[]{param.getOdsUnitCode(), param.getAcctYear(), param.getDataSchemeCode()});
    }

    /*
     * Exception decompiling
     */
    @Override
    public void insertSrcVchrId2Temp(List<String> srcVchrIdList) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * java.lang.UnsupportedOperationException
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.NewAnonymousArray.getDimSize(NewAnonymousArray.java:142)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.isNewArrayLambda(LambdaRewriter.java:455)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:409)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteDynamicExpression(LambdaRewriter.java:167)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:105)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriterToArgs(AbstractMemberFunctionInvokation.java:101)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:87)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.rewriters.ExpressionRewriterHelper.applyForwards(ExpressionRewriterHelper.java:12)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriterToArgs(AbstractMemberFunctionInvokation.java:101)
         *     at org.benf.cfr.reader.bytecode.analysis.parse.expression.AbstractMemberFunctionInvokation.applyExpressionRewriter(AbstractMemberFunctionInvokation.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewriteExpression(LambdaRewriter.java:103)
         *     at org.benf.cfr.reader.bytecode.analysis.structured.statement.StructuredExpressionStatement.rewriteExpressions(StructuredExpressionStatement.java:70)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.LambdaRewriter.rewrite(LambdaRewriter.java:88)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.rewriteLambdas(Op04StructuredStatement.java:1137)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:912)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void updateOdsVchrImpstateIfExists(VchrChangeDim param, String odsTable, String odsVchrId) {
        String[] odsFieldNameArr = odsVchrId.split("/");
        String vchrSrc = this.getDbSqlHandler().concatBySeparator("|", odsFieldNameArr);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(odsTable).append(" T \n");
        sql.append("  SET IMPSTATE = 0 \n");
        sql.append(" WHERE T.IMPSTATE = 1 \n");
        sql.append("   AND EXISTS (SELECT 1 FROM ").append("DC_VCHRCHANGEINFO").append(" C \n");
        sql.append("   \t\t\t\t JOIN ").append("DC_TEMP_VCHRITEMASS").append(" V ON V.SRCVCHRID = C.SRCVCHRID \n");
        sql.append("\t\t\t\tWHERE C.UNITCODE = ? AND C.ACCTYEAR = ? AND C.DATASCHEMECODE = ? \n");
        sql.append("\t\t\t\t  AND C.SRCVCHRID = ").append(vchrSrc).append(" ) \n");
        this.update(sql.toString(), new Object[]{param.getOdsUnitCode(), param.getAcctYear(), param.getDataSchemeCode()});
    }

    @Override
    public void updateEtlProcessLog(VchrChangeDim param, String dataSourceCode, List<String> srcVchrIdList) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ETL_PROCESS_VOUCHER T \n");
        sql.append("  SET HANDLESTATE = 0 \n");
        sql.append(" WHERE T.UNITCODE = ? \n");
        sql.append("   AND T.ACCTYEAR = ? \n");
        sql.append("   AND T.HANDLESTATE = 1 \n");
        sql.append("   AND ").append(SqlBuildUtil.getStrInCondi((String)"T.VCHRID", srcVchrIdList));
        this.dynamicDataSourceService.getJdbcTemplate(dataSourceCode).update(sql.toString(), new Object[]{param.getOdsUnitCode(), param.getAcctYear()});
    }

    @Override
    public void updateVchrChangeInfoCreateOffsetVchrFlag(VchrChangeDim param) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_VCHRCHANGEINFO").append(" T \n");
        sql.append("   SET CREATEOFFSETVCHR = 1 \n");
        sql.append(" WHERE T.UNITCODE = ? \n");
        sql.append("   AND T.ACCTYEAR = ? \n");
        sql.append("   AND T.DATASCHEMECODE = ? \n");
        sql.append("   AND T.CREATEOFFSETVCHR = 0 \n");
        sql.append("   AND EXISTS (SELECT 1 FROM ").append("DC_TEMP_VCHRITEMASS").append(" TEMP \n");
        sql.append("\t\t\t\tWHERE TEMP.SRCITEMASSID = T.OFFSETGROUPID)");
        this.update(sql.toString(), new Object[]{param.getOdsUnitCode(), param.getAcctYear(), param.getDataSchemeCode()});
    }

    @Override
    public List<VchrMasterDim> queryVchrPeriodFromTemp() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.UNITCODE, T.ACCTYEAR, T.ACCTPERIOD, COUNT(1) AS COUNT \n");
        sql.append("  FROM ").append("DC_TEMP_VCHRITEMASS").append(" T \n");
        sql.append(" GROUP BY T.UNITCODE, T.ACCTYEAR, T.ACCTPERIOD ");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(VchrMasterDim.class));
    }

    @Override
    public List<String> querySrcVchrIdList() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.SRCVCHRID \n");
        sql.append("  FROM ").append("DC_TEMP_VCHRITEMASS").append(" T \n");
        sql.append(" GROUP BY T.SRCVCHRID");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper());
    }

    @Override
    public void cleanVchrTemp() {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("DC_TEMP_VCHRITEMASS").append(" \n");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString());
    }

    @Override
    public List<Integer> queryDeleteVchrYearList() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ACCTYEAR \n");
        sql.append("  FROM ").append("DC_VCHRCHANGEINFO").append(" T \n");
        sql.append(" WHERE T.CREATEOFFSETVCHR = 1 AND T.VCHRCLEANFLAG = 0 \n");
        sql.append(" GROUP BY T.ACCTYEAR ");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (rs, rownum) -> rs.getInt(1));
    }

    @Override
    public List<VchrDeleteDim> queryUnCleanOffsetVchrGroupId(int acctYear) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT V.UNITCODE, V.SRCITEMASSID, max(V.SN) AS SN \n");
        sql.append("  FROM ").append(CommonUtil.getVoucherItemAssTableName((int)acctYear)).append(" V \n");
        sql.append("  JOIN ").append("DC_VCHRCHANGEINFO").append(" T \n");
        sql.append(" \tON V.SRCITEMASSID = T.OFFSETGROUPID \n");
        sql.append(" WHERE T.ACCTYEAR = ? AND T.CREATEOFFSETVCHR = 1 AND T.VCHRCLEANFLAG = 0 \n");
        sql.append(" GROUP BY V.UNITCODE, V.SRCITEMASSID ");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new BeanPropertyRowMapper(VchrDeleteDim.class), new Object[]{acctYear});
    }

    @Override
    public void deleteVchr(String unitCode, int acctYear) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(CommonUtil.getVoucherItemAssTableName((int)acctYear)).append(" \n");
        sql.append(" WHERE UNITCODE = ? \n");
        sql.append("   AND SRCITEMASSID IN (SELECT CODE FROM DC_TEMP_CODE)");
        this.update(sql.toString(), new Object[]{unitCode});
        sql = new StringBuilder();
        sql.append("DELETE FROM ").append(CommonUtil.getCfVoucherItemAssTableName((int)acctYear)).append(" \n");
        sql.append(" WHERE UNITCODE = ? \n");
        sql.append("   AND SRCVCHRID IN (SELECT CODE FROM DC_TEMP_CODE)");
        this.update(sql.toString(), new Object[]{unitCode});
    }

    @Override
    public void updateVchrChangeInfoVchrCleanFlag() {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_VCHRCHANGEINFO").append(" T \n");
        sql.append("   SET VCHRCLEANFLAG = 1 \n");
        sql.append(" WHERE T.CREATEOFFSETVCHR = 1 \n");
        sql.append("   AND T.VCHRCLEANFLAG = 0 \n");
        sql.append("   AND T.OFFSETGROUPID IN (SELECT CODE FROM DC_TEMP_CODE)");
        this.update(sql.toString(), new Object[0]);
    }

    @Override
    public void insertDirectVchrChangeDim(List<VchrChangeDO> vchrChangeList) {
        if (!CollectionUtils.isEmpty(vchrChangeList)) {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO DC_VCHRCHANGEINFO  ");
            sql.append("  (ID,  ");
            sql.append("   VER,  ");
            sql.append("   ACCTYEAR,  ");
            sql.append("   UNITCODE,  ");
            sql.append("   DATASCHEMECODE,  ");
            sql.append("   SRCVCHRID,  ");
            sql.append("   ACCTPERIOD  ");
            sql.append(" )");
            sql.append("  VALUES (?,?,?,?,?,?,?)");
            ArrayList paramList = CollectionUtils.newArrayList();
            for (VchrChangeDO itemInfoDO : vchrChangeList) {
                Object[] params = new Object[]{UUIDUtils.newHalfGUIDStr(), System.currentTimeMillis(), itemInfoDO.getAcctYear(), itemInfoDO.getUnitCode(), itemInfoDO.getDataSchemeCode(), itemInfoDO.getScrVchrId(), itemInfoDO.getDcAcctPeriod()};
                paramList.add(params);
            }
            String executeSql = sql.toString();
            OuterDataSourceUtils.getJdbcTemplate().batchUpdate(executeSql, (List)paramList);
        }
    }

    private String getVchrItemAssField(boolean negative) {
        StringBuilder fields = new StringBuilder();
        fields.append("UNITCODE, VCHRID, ACCTYEAR, ACCTPERIOD, VCHRNUM, VCHRTYPECODE, POSTFLAG, ITEMORDER, CREATEDATE, ");
        fields.append("SUBJECTCODE, SRC_SUBJECT_ID, CURRENCYCODE, FINCURR, CFITEMCODE, EXPIREDATE, ");
        fields.append("VCHRSRCTYPE, SRCVCHRID, SRCITEMID, SRCITEMASSID, SUBJECT_SRCCODE, SUBJECT_SRCNAME, DIGEST, EXCHRATE, QTYD, QTYC,PRICE, ");
        fields.append(String.format("%1$sDEBIT, %1$sCREDIT, %1$sORGND, %1$sORGNC", negative ? "-" : ""));
        return fields.toString();
    }

    private String getCfVchrItemAssField(String tableName, boolean negative) {
        String prefix = StringUtils.isEmpty((String)tableName) ? "" : tableName + ".";
        StringBuilder fields = new StringBuilder();
        fields.append(String.format("%1$sUNITCODE, %1$sVCHRID, %1$sACCTYEAR, %1$sACCTPERIOD, %1$sITEMORDER, ", prefix));
        fields.append(String.format("%1$sSUBJECTCODE, %1$sSRC_SUBJECT_ID, %1$sCURRENCYCODE, %1$sFINCURR, %1$sCFITEMCODE, ", prefix));
        fields.append(String.format("%1$sVCHRSRCTYPE, %1$sSRCVCHRID, %1$sSRCITEMID, %1$sSUBJECT_SRCCODE, %1$sSUBJECT_SRCNAME, %1$sDIGEST,", prefix));
        fields.append(String.format("%1$s%2$sDEBIT, %1$s%2$sCREDIT, %1$s%2$sORGND, %1$s%2$sORGNC", negative ? "-" : "", prefix));
        return fields.toString();
    }

    @Override
    public List<String> checkSrcVchrData(VchrChangeDim param) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.ID \n");
        sql.append("  FROM ").append("DC_VCHRCHANGEINFO").append(" T \n");
        sql.append("  LEFT JOIN ").append(CommonUtil.getVoucherItemAssTableName((int)param.getAcctYear())).append(" V \n");
        sql.append("    ON T.SRCVCHRID = V.SRCVCHRID \n");
        sql.append("   AND V.UNITCODE = ? \n");
        sql.append(" WHERE T.UNITCODE = ? \n");
        sql.append("   AND T.ACCTYEAR = ? \n");
        sql.append("   AND T.DATASCHEMECODE = ? \n");
        sql.append("   AND T.CREATEOFFSETVCHR = 0 \n");
        sql.append("   AND V.SRCVCHRID IS NULL \n");
        return OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), (RowMapper)new StringRowMapper(), new Object[]{param.getUnitCode(), param.getOdsUnitCode(), param.getAcctYear(), param.getDataSchemeCode()});
    }

    @Override
    public void updateCheckDataState(List<String> errorList) {
        if (CollectionUtils.isEmpty(errorList)) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_VCHRCHANGEINFO").append(" \n");
        sql.append("   SET CREATEOFFSETVCHR = 1 \n");
        sql.append(" WHERE ").append(SqlBuildUtil.getStrInCondi((String)"ID", errorList));
        this.update(sql.toString(), new Object[0]);
    }
}

