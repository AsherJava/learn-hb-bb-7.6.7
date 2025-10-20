/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jsqlparser.JSQLParserException
 *  net.sf.jsqlparser.parser.CCJSqlParserUtil
 *  net.sf.jsqlparser.statement.Statement
 *  net.sf.jsqlparser.util.TablesNamesFinder
 */
package com.jiuqi.va.query.sql.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.springframework.util.CollectionUtils;

public class MarkerBasedParser {
    public static void main(String[] args) {
        String originalSql = "SELECT   a.col1,   FN::GET_DATA('some_param') as custom_data FROM   \"_SYS_BIC\".\"CNPC.DW.A.BC/ZAHABC306\" (PLACEHOLDER.\"$$i_time$$\" => '2024.08') a INNER JOIN   another_table b ON a.id = b.id";
        System.out.println("----------- \u6b65\u9aa4 1: \u539f\u59cb SQL -----------");
        System.out.println(originalSql);
        String markedUpSql = "SELECT   a.col1,   /*!RAW FN::GET_DATA('some_param') */ as custom_data FROM   /*!RAW \"_SYS_BIC\".\"CNPC.DW.A.BC/ZAHABC306\" (PLACEHOLDER.\"$$i_time$$\" => '2024.08') */ a INNER JOIN   another_table b ON a.id = b.id";
        System.out.println("\n----------- \u6b65\u9aa4 2: \u624b\u52a8\u6807\u8bb0\u540e\u7684 SQL -----------");
        System.out.println(markedUpSql);
        System.out.println("\n----------- \u6b65\u9aa4 3: \u4f7f\u7528\u9884\u5904\u7406\u5668\u8fdb\u884c\u5904\u7406 -----------");
        PreprocessResult result = MarkerBasedPreprocessor.preprocess(markedUpSql);
        String processedSqlForParser = result.getProcessedSql();
        List<ReplacementInfo> replacements = result.getExtractedReplacements();
        System.out.println("\u5904\u7406\u540e\u7528\u4e8eJSqlParser\u89e3\u6790\u7684SQL:\n" + processedSqlForParser);
        System.out.println("\n\u63d0\u53d6\u51fa\u7684\u6240\u6709\u88ab\u6807\u8bb0\u7684\u539f\u59cb\u5185\u5bb9:");
        replacements.forEach(System.out::println);
        System.out.println("\n----------- \u6b65\u9aa4 4: \u4f7f\u7528JSqlParser\u89e3\u6790 -----------");
        try {
            Statement statement = CCJSqlParserUtil.parse((String)processedSqlForParser);
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            List tableList = tablesNamesFinder.getTableList(statement);
            System.out.println("JSqlParser\u6210\u529f\u89e3\u6790\uff01");
            System.out.println("\u89e3\u6790\u51fa\u7684\u8868\u540d/\u5360\u4f4d\u7b26: " + tableList);
            System.out.println("\n----------- \u6b65\u9aa4 5: \u8fd8\u539f SQL \u7528\u4e8e\u6700\u7ec8\u6267\u884c\u6216\u5206\u6790 -----------");
            String finalSql = MarkerBasedPreprocessor.restore(processedSqlForParser, replacements);
            System.out.println("\u5b8c\u5168\u8fd8\u539f\u540e\u7684SQL:\n" + finalSql);
        }
        catch (JSQLParserException e) {
            System.err.println("\u89e3\u6790\u5931\u8d25\uff01\u8bf7\u68c0\u67e5\u6807\u8bb0\u662f\u5426\u5b8c\u6574\u6216\u6b63\u786e\u3002");
            e.printStackTrace();
        }
    }

    public static class MarkerBasedPreprocessor {
        private static final Pattern MARKER_PATTERN = Pattern.compile("/\\*!RAW(.*?)\\*/", 32);

        private MarkerBasedPreprocessor() {
        }

        public static PreprocessResult preprocess(String markedUpSql) {
            Matcher matcher = MARKER_PATTERN.matcher(markedUpSql);
            StringBuffer sb = new StringBuffer();
            ArrayList<ReplacementInfo> allReplacements = new ArrayList<ReplacementInfo>();
            int replacementCounter = 0;
            while (matcher.find()) {
                String originalContent = matcher.group(1);
                String replacementId = "__RAW_REPLACEMENT_" + replacementCounter++ + "__";
                ReplacementInfo info = new ReplacementInfo(originalContent, replacementId);
                allReplacements.add(info);
                matcher.appendReplacement(sb, Matcher.quoteReplacement(replacementId));
            }
            matcher.appendTail(sb);
            return new PreprocessResult(sb.toString(), allReplacements);
        }

        public static String restore(String processedSql, List<ReplacementInfo> replacements) {
            if (CollectionUtils.isEmpty(replacements)) {
                return processedSql;
            }
            String restoredSql = processedSql;
            for (ReplacementInfo info : replacements) {
                restoredSql = restoredSql.replace(info.getReplacementId(), info.getOriginalContent());
            }
            return restoredSql;
        }
    }

    public static class PreprocessResult {
        private final String processedSql;
        private final List<ReplacementInfo> extractedReplacements;

        public PreprocessResult(String processedSql, List<ReplacementInfo> extractedReplacements) {
            this.processedSql = processedSql;
            this.extractedReplacements = extractedReplacements;
        }

        public String getProcessedSql() {
            return this.processedSql;
        }

        public List<ReplacementInfo> getExtractedReplacements() {
            return this.extractedReplacements;
        }
    }

    public static class ReplacementInfo {
        private final String originalContent;
        private final String replacementId;

        public ReplacementInfo(String originalContent, String replacementId) {
            this.originalContent = originalContent;
            this.replacementId = replacementId;
        }

        public String getOriginalContent() {
            return this.originalContent;
        }

        public String getReplacementId() {
            return this.replacementId;
        }

        public String toString() {
            return "ReplacementInfo{replacementId='" + this.replacementId + '\'' + ", originalContent='" + this.originalContent.trim() + '\'' + '}';
        }
    }
}

