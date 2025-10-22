/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.inputdata.query.base;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.inputdata.query.base.GcQuerySqlParam;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NrSqlFormatBuilder {
    private String formatSql;
    private String groupingSql;
    private String groupingWhere;
    private String detailSql;
    private Map<String, String> fieldMap;
    private Set<String> SqlConditions;
    private String groupSelect;
    private String groupBy;
    private String orderBy;
    private static final String alisa = "M1";
    private String mainFormatSql;

    public NrSqlFormatBuilder(String sql) {
        this.detailSql = this.formatSql = this.formatNativeSql(sql);
    }

    public String formatSql(GcQuerySqlParam otherFilter) {
        return otherFilter.formatSql(this.getNrSql());
    }

    public String replaceCountSql(GcQuerySqlParam otherFilter) {
        String sql = this.getNrSql();
        if (sql.toUpperCase().contains("COUNT")) {
            sql = sql.replace("COUNT", "").replace("MIN", "").replace("MAX", "");
        }
        return otherFilter.formatSql(sql);
    }

    public String formatGatherSql(FormatKeyValue ... replace) {
        String sql = this.formatSql;
        if (replace != null && replace.length > 0) {
            for (FormatKeyValue kv : replace) {
                sql = sql.replace(kv.getReg(), kv.getValue());
            }
        }
        return sql;
    }

    public String formatUnionAllSql(GcQuerySqlParam oldSql, GcQuerySqlParam newSql) {
        this.formatNativeGroupingSql(this.formatSql, oldSql.isUseDnaSql());
        if (this.groupingWhere != null) {
            String detailWhere = this.detailSql.substring(this.detailSql.toUpperCase().lastIndexOf("WHERE") + 5);
            String formatSql = this.formatUnionAllCountSql(oldSql, newSql);
            StringBuilder builder = new StringBuilder(formatSql);
            if (builder.indexOf(detailWhere) != -1) {
                builder.replace(builder.indexOf(detailWhere), builder.indexOf(detailWhere) + detailWhere.length(), this.groupingWhere);
            }
            return this.replaceSqlCondition(builder.toString());
        }
        String formatSql = this.formatUnionAllCountSql(oldSql, newSql);
        return this.replaceSqlCondition(formatSql);
    }

    private String formatUnionAllCountSql(GcQuerySqlParam oldSql, GcQuerySqlParam newSql) {
        String formatSql;
        if (!this.detailSql.toUpperCase().contains("COUNT")) {
            formatSql = String.format(this.mainFormatSql, this.fm(this.groupSelect), this.fm(this.groupBy), this.formatSql(oldSql), this.formatSql(newSql), this.fm(this.orderBy), alisa);
        } else {
            String selectColumns = this.detailSql.substring(this.detailSql.toUpperCase().lastIndexOf("SELECT") + 6, this.detailSql.toUpperCase().lastIndexOf("FROM")).replaceAll("(COUNT|MIN|MAX)\\s*\\((\\s*[0-9a-zA-Z._]*\\s*)\\)\\s*AS\\s*", " COUNT( 1 ) ");
            formatSql = String.format(this.mainFormatSql, this.fm(this.groupSelect), this.fm(this.groupBy), this.replaceCountSql(oldSql), this.replaceCountSql(newSql), this.fm(this.orderBy), alisa).replace("*", selectColumns);
        }
        return formatSql;
    }

    private String fm(String str) {
        if (StringUtils.isEmpty((String)str)) {
            return "";
        }
        return str;
    }

    private void getSqlConditionsByOrianlSql(String formatSql) {
        String patternStr = "('([^']*)')|(\"([^\"]*)\")";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(formatSql);
        matcher.reset();
        this.SqlConditions = new HashSet<String>();
        while (matcher.find()) {
            String sqlCondition = matcher.group(0);
            if (this.SqlConditions.contains(sqlCondition)) continue;
            this.SqlConditions.add(sqlCondition);
        }
    }

    public String replaceSqlCondition(String formatSql) {
        if (CollectionUtils.isEmpty(this.SqlConditions)) {
            return formatSql;
        }
        for (String sqlCondition : this.SqlConditions) {
            formatSql = formatSql.replace(sqlCondition.toUpperCase(), sqlCondition);
        }
        return formatSql;
    }

    private String formatNativeSql(String sql) {
        if (StringUtils.isEmpty((String)sql)) {
            return sql;
        }
        this.getSqlConditionsByOrianlSql(sql);
        String pattern = "([\\,]|[\\=]|[\\)]|[\\(])";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(sql.toUpperCase());
        m.reset();
        boolean result = m.find();
        if (result) {
            StringBuffer sb = new StringBuffer();
            do {
                String mg0;
                String replacement = "(".equals(mg0 = m.group(0)) ? String.format("%1$s ", mg0) : String.format(" %1$s ", mg0);
                m.appendReplacement(sb, replacement);
            } while (result = m.find());
            m.appendTail(sb);
            return sb.toString();
        }
        return sql.toUpperCase();
    }

    private void formatNativeGroupingSql(String sql, boolean useDna) {
        if (sql.trim().startsWith("SELECT * FROM (")) {
            String realSql = sql.substring(16, sql.lastIndexOf(")"));
            this.orderBy = sql.substring(sql.lastIndexOf(")") + 1);
            if (realSql.indexOf("UNION ALL") >= 0) {
                String[] childrens;
                this.mainFormatSql = "SELECT * FROM ( %1$s (%3$s UNION ALL %4$s) %6$s %2$s UNION ALL %3$s UNION ALL %4$s ) %5$s";
                if (useDna) {
                    this.mainFormatSql = "SELECT * FROM ( %1$s (%3$s UNION ALL %4$s) AS %6$s %2$s UNION ALL %3$s UNION ALL %4$s ) %5$s";
                }
                for (String cSql : childrens = realSql.split("UNION ALL")) {
                    if (cSql.indexOf("GROUP BY") >= 0) {
                        this.groupingSql = cSql;
                        this.groupingWhere = cSql.substring(cSql.toUpperCase().indexOf("WHERE") + 5);
                        this.groupingWhere = this.groupingWhere.substring(0, this.groupingWhere.toLowerCase().indexOf("group by"));
                        this.initGroupingSql(this.groupingSql);
                        continue;
                    }
                    this.initDetailSql(cSql);
                }
            } else {
                this.mainFormatSql = "SELECT * FROM ( %3$s UNION ALL %4$s ) %5$s ";
                this.initDetailSql(realSql);
            }
        } else if (sql.indexOf("UNION ALL") >= 0) {
            String[] childrens;
            this.mainFormatSql = " %1$s (%3$s UNION ALL %4$s) %6$s %2$s UNION ALL %3$s UNION ALL %4$s %5$s";
            if (useDna) {
                this.mainFormatSql = " %1$s (%3$s UNION ALL %4$s) AS %6$s %2$s UNION ALL %3$s UNION ALL %4$s %5$s";
            }
            for (String cSql : childrens = sql.split("UNION ALL")) {
                if (cSql.indexOf("GROUP BY") >= 0) {
                    this.groupingSql = cSql;
                    this.initGroupingSql(this.groupingSql);
                    continue;
                }
                this.initDetailSql(cSql);
            }
        } else {
            this.mainFormatSql = " %3$s UNION ALL %4$s %5$s ";
            this.initDetailSql(sql);
        }
    }

    private void initDetailSql(String sql) {
        if (sql.indexOf("ORDER BY ") >= 0) {
            this.detailSql = sql.substring(0, sql.indexOf("ORDER BY "));
            this.orderBy = sql.substring(sql.indexOf("ORDER BY "));
        } else {
            this.detailSql = sql;
        }
    }

    private void initGroupingSql(String sql) {
        this.groupSelect = sql.substring(0, sql.indexOf(" FROM ") + 6);
        this.groupBy = sql.substring(sql.indexOf(" GROUP BY "));
        this.fieldMap = new HashMap<String, String>();
        String pattern = "[(]?([ ]*[^( ),]*[ ]*)[)]?[ ]+AS[ ]+([a-zA-Z]*[_][0-9]*)[ ]+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(this.groupSelect);
        boolean result = m.find();
        if (result) {
            do {
                String key = m.group(1);
                String value = m.group(2);
                if (StringUtils.isEmpty((String)key) || key.contains("NULL") || StringUtils.isEmpty((String)value) || this.fieldMap.containsKey(key.trim())) continue;
                this.fieldMap.put(key, String.format(" %1$s.%2$s ", alisa, value));
            } while (result = m.find());
        }
        if (this.fieldMap != null && this.fieldMap.size() > 0) {
            for (Map.Entry<String, String> entry : this.fieldMap.entrySet()) {
                this.groupSelect = this.groupSelect.replace(entry.getKey(), entry.getValue());
                this.groupBy = this.groupBy.replace(entry.getKey(), entry.getValue());
            }
        }
    }

    public String getNrSql() {
        return this.detailSql;
    }

    public FormatKeyValue newKeyValue(String reg, String value) {
        return new FormatKeyValue(reg.toUpperCase(), value.toUpperCase());
    }

    public class FormatKeyValue {
        private String reg;
        private String value;

        public FormatKeyValue(String reg, String value) {
            this.reg = reg;
            this.value = value;
        }

        public String getReg() {
            return this.reg;
        }

        public String getValue() {
            return this.value;
        }
    }
}

