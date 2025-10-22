/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package nr.midstore2.data.midstoreresult.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import nr.midstore2.data.midstoreresult.bean.BatchResultsFilter;
import nr.midstore2.data.midstoreresult.bean.MidstoreResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

@Repository
public class MidstoreResultDao {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreResultDao.class);
    private static final String TABLE_NAME = "NR_MIDSTORE_RESULT";
    private static final String KEY = "MR_KEY";
    private static final String USER = "MR_USER";
    private static final String EXCHANGE_MODE = "MR_EXCHANGE_MODE";
    private static final String TASK = "MR_TASK";
    private static final String MIDSTORE = "MR_MIDSTORE";
    private static final String PERIOD = "MR_PERIOD";
    private static final String UNIT = "MR_UNIT";
    private static final String EXECUTE_TIME = "MR_EXECUTE_TIME";
    private static final String SOURCE = "MR_SOURCE";
    private static final String DETAIL = "MR_DETAIL";
    private static final String RESULT = "MR_RESULT";
    private static final String BATCH_MIDSTORE_RESULT;
    private static final Function<ResultSet, MidstoreResult> ENTITY_READER_BATCH_MIDSTORE_RESULT;
    @Autowired(required=false)
    private JdbcTemplate jdbcTemplate;

    public String add(MidstoreResult batchMidstoreResult) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, BATCH_MIDSTORE_RESULT);
        Object[] args = new Object[]{batchMidstoreResult.getKey(), batchMidstoreResult.getUser(), batchMidstoreResult.getExchangeMode(), batchMidstoreResult.getTask(), batchMidstoreResult.getMidstore(), batchMidstoreResult.getPeriod(), batchMidstoreResult.getUnit(), batchMidstoreResult.getExecuteTime(), batchMidstoreResult.getSource(), batchMidstoreResult.getDetail(), batchMidstoreResult.getStatus()};
        this.jdbcTemplate.update(SQL_INSERT, args);
        return batchMidstoreResult.getKey();
    }

    public void delete(String key) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, KEY);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{key});
    }

    public List<MidstoreResult> getBatchResults(BatchResultsFilter filter) {
        String SQL_SELECT_ALL = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ? AND %s = ? ORDER BY %s DESC", BATCH_MIDSTORE_RESULT, TABLE_NAME, USER, TASK, EXCHANGE_MODE, EXECUTE_TIME);
        return this.jdbcTemplate.query(SQL_SELECT_ALL, (rs, row) -> ENTITY_READER_BATCH_MIDSTORE_RESULT.apply(rs), new Object[]{filter.getUser(), filter.getTask(), filter.getExchangeMode()});
    }

    public String getDetail(String key) {
        String SQL_SELECT = String.format("SELECT %s FROM %s WHERE %s = ?", DETAIL, TABLE_NAME, KEY);
        DefaultLobHandler lobHandler = new DefaultLobHandler();
        return (String)this.jdbcTemplate.query(SQL_SELECT, arg_0 -> MidstoreResultDao.lambda$getDetail$2((LobHandler)lobHandler, arg_0), new Object[]{key});
    }

    /*
     * Exception decompiling
     */
    public void batchDelete(List<String> keys) {
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

    private static /* synthetic */ String lambda$getDetail$2(LobHandler lobHandler, ResultSet rs) throws SQLException, DataAccessException {
        if (rs.next()) {
            return lobHandler.getClobAsString(rs, DETAIL);
        }
        return null;
    }

    static {
        StringBuilder builder = new StringBuilder();
        BATCH_MIDSTORE_RESULT = builder.append(KEY).append(",").append(USER).append(",").append(EXCHANGE_MODE).append(",").append(TASK).append(",").append(MIDSTORE).append(",").append(PERIOD).append(",").append(UNIT).append(",").append(EXECUTE_TIME).append(",").append(SOURCE).append(",").append(DETAIL).append(",").append(RESULT).toString();
        ENTITY_READER_BATCH_MIDSTORE_RESULT = rs -> {
            MidstoreResult batchMidstoreResult = new MidstoreResult();
            int index = 1;
            try {
                batchMidstoreResult.setKey(rs.getString(index++));
                batchMidstoreResult.setUser(rs.getString(index++));
                batchMidstoreResult.setExchangeMode(rs.getString(index++));
                batchMidstoreResult.setTask(rs.getString(index++));
                batchMidstoreResult.setMidstore(rs.getString(index++));
                batchMidstoreResult.setPeriod(rs.getString(index++));
                batchMidstoreResult.setUnit(rs.getInt(index++));
                batchMidstoreResult.setExecuteTime(rs.getTimestamp(index++));
                batchMidstoreResult.setSource(rs.getString(index++));
                batchMidstoreResult.setDetail(rs.getString(index++));
                batchMidstoreResult.setStatus(rs.getString(index));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return batchMidstoreResult;
        };
    }
}

