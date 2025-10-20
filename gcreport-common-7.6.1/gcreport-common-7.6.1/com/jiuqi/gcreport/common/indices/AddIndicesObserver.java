/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.common.indices;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

@NpDefinitionObserver(type={MessageType.PUBLISHTABLE})
public class AddIndicesObserver
implements Observer {
    @Autowired
    IDataDefinitionDesignTimeController dataDefinitionController;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Value(value="classpath:index/index.txt")
    private Resource resource;
    private static List<String> indexSqls = new ArrayList<String>();
    private static List<String[]> indexList = new ArrayList<String[]>();

    public boolean isAsyn() {
        return false;
    }

    public void excute(Object[] objs) throws Exception {
    }

    private void doSql(String index, String tableName, String sql) {
        String dropSql = "drop index " + index;
        try {
            this.jdbcTemplate.execute(dropSql);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.jdbcTemplate.execute(sql);
            System.out.println("\u5df2\u6dfb\u52a0\u7d22\u5f15\uff1a " + index + " on " + tableName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> txt2StringList(InputStream inputStream) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String s = null;
            while ((s = br.readLine()) != null) {
                if (StringUtils.isEmpty((String)s)) continue;
                s = s.replace(";", "");
                result.add(s);
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static List<String[]> getIndexList(List<String> indexSqls) {
        ArrayList<String[]> indexList = new ArrayList<String[]>();
        String indexRegex = "INDEX(.*?)ON";
        Pattern indexPattern = Pattern.compile(indexRegex);
        String tableRegex = "ON(.*?)\\(";
        Pattern tablePattern = Pattern.compile(tableRegex);
        for (String sql : indexSqls) {
            String table;
            Matcher m = indexPattern.matcher(sql.toUpperCase());
            String index = m.find() ? m.group(1).trim() : null;
            Matcher m2 = tablePattern.matcher(sql.toUpperCase());
            String string = table = m2.find() ? m2.group(1).trim() : null;
            if (StringUtils.isEmpty((String)index) || StringUtils.isEmpty((String)table)) continue;
            String[] indexItem = new String[]{index, table, sql};
            indexList.add(indexItem);
        }
        return indexList;
    }
}

