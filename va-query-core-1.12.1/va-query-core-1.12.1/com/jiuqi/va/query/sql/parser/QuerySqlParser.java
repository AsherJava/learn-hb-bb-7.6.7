/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQuerySqlException
 *  com.jiuqi.va.query.sql.enumerate.QueryModeEnum
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.Model
 *  net.sf.jsqlparser.expression.Expression
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.statement.Statement
 *  net.sf.jsqlparser.statement.StatementVisitor
 *  net.sf.jsqlparser.statement.select.Select
 *  net.sf.jsqlparser.util.TablesNamesFinder
 *  net.sf.jsqlparser.util.deparser.ExpressionDeParser
 *  net.sf.jsqlparser.util.deparser.SelectDeParser
 *  net.sf.jsqlparser.util.deparser.StatementDeParser
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.va.query.sql.parser;

import com.jiuqi.va.query.exception.DefinedQuerySqlException;
import com.jiuqi.va.query.sql.enumerate.QueryModeEnum;
import com.jiuqi.va.query.sql.parser.MarkerBasedParser;
import com.jiuqi.va.query.sql.parser.common.JsonOperatorExpressionVisitor;
import com.jiuqi.va.query.sql.parser.common.UserVarOperatorExpressionVisitor;
import com.jiuqi.va.query.sql.parser.common.UserVariableFinder;
import com.jiuqi.va.query.sql.parser.model.ModelHandlerGather;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.Model;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import net.sf.jsqlparser.util.deparser.SelectDeParser;
import net.sf.jsqlparser.util.deparser.StatementDeParser;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class QuerySqlParser {
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{1,60}$");
    public static final String MSG = "sql\u683c\u5f0f\u6709\u8bef\uff0c\u8f6c\u6362\u5931\u8d25";
    private static final Logger logger = LoggerFactory.getLogger(QuerySqlParser.class);

    private QuerySqlParser() {
    }

    public static String parserSql(String sql, List<String> emptyParams) {
        Statement statement;
        MarkerBasedParser.PreprocessResult result = MarkerBasedParser.MarkerBasedPreprocessor.preprocess(sql);
        sql = result.getProcessedSql();
        String resultSql = "";
        try {
            statement = CCJSqlParserUtil.parse((String)sql);
        }
        catch (JSQLParserException e) {
            logger.error(e.getMessage(), e);
            throw new DefinedQuerySqlException((Throwable)e);
        }
        if (CollectionUtils.isEmpty(emptyParams)) {
            StringBuilder formatSql = new StringBuilder();
            JsonOperatorExpressionVisitor jsonOperatorVisitor = new JsonOperatorExpressionVisitor();
            StatementDeParser statementDeParser = new StatementDeParser((ExpressionDeParser)jsonOperatorVisitor, new SelectDeParser(), formatSql);
            statement.accept((StatementVisitor)statementDeParser);
            resultSql = formatSql.toString();
            return MarkerBasedParser.MarkerBasedPreprocessor.restore(resultSql, result.getExtractedReplacements());
        }
        Select newSelect = (Select)DCQuerySpringContextUtils.getBean(ModelHandlerGather.class).doParser((Model)statement, emptyParams);
        resultSql = newSelect.toString();
        return MarkerBasedParser.MarkerBasedPreprocessor.restore(resultSql, result.getExtractedReplacements());
    }

    public static List<String> getUserVariables(String sql) {
        MarkerBasedParser.PreprocessResult result = MarkerBasedParser.MarkerBasedPreprocessor.preprocess(sql);
        sql = result.getProcessedSql();
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse((String)sql);
        }
        catch (JSQLParserException e) {
            logger.error(MSG, e);
            throw new DefinedQuerySqlException(MSG);
        }
        UserVariableFinder userVariableFinder = new UserVariableFinder();
        return userVariableFinder.getUserVariableList(statement);
    }

    public static List<String> getUserVariablesWithException(String sql) {
        Statement statement;
        MarkerBasedParser.PreprocessResult result = MarkerBasedParser.MarkerBasedPreprocessor.preprocess(sql);
        sql = result.getProcessedSql();
        try {
            statement = CCJSqlParserUtil.parse((String)sql);
        }
        catch (JSQLParserException e) {
            logger.error(e.getMessage(), e);
            throw new DefinedQuerySqlException((Throwable)e);
        }
        UserVariableFinder userVariableFinder = new UserVariableFinder();
        return userVariableFinder.getUserVariableList(statement);
    }

    public static List<String> getAllTableName(String sql) {
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse((String)sql);
        }
        catch (JSQLParserException e) {
            logger.error(MSG, e);
            throw new DefinedQuerySqlException(MSG);
        }
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        return tablesNamesFinder.getTableList(statement);
    }

    public static List<String> getUserVarInExpression(Expression expression) {
        UserVariableFinder userVariableFinder = new UserVariableFinder();
        return userVariableFinder.getUserVariableList(expression);
    }

    public static Map<String, List<QueryModeEnum>> getUserVarOperator(String sql) {
        Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse((String)sql);
        }
        catch (JSQLParserException e) {
            logger.error(MSG, e);
            throw new DefinedQuerySqlException(MSG);
        }
        StringBuilder formatSql = new StringBuilder();
        UserVarOperatorExpressionVisitor userVarOperatorExpressionVisitor = new UserVarOperatorExpressionVisitor();
        StatementDeParser statementDeParser = new StatementDeParser((ExpressionDeParser)userVarOperatorExpressionVisitor, new SelectDeParser(), formatSql);
        statement.accept((StatementVisitor)statementDeParser);
        return userVarOperatorExpressionVisitor.getUserVarOperatorMap();
    }

    public static boolean checkInputInject(String input) {
        if (!StringUtils.hasText((String)input)) {
            return false;
        }
        Matcher matcher = SQL_INJECTION_PATTERN.matcher(input);
        return !matcher.find();
    }

    public static void main(String[] args) {
        String sql = "select id from md_org where code = @code";
        boolean b = QuerySqlParser.checkInputInject(sql);
        System.out.println(b);
        boolean b1 = QuerySqlParser.checkInputInject(";");
        System.out.println(b1);
    }
}

