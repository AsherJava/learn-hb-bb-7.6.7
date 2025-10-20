/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definition.internal.update;

import com.jiuqi.np.sql.CustomClassExecutor;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;

public class FormulaOrderUpdate
implements CustomClassExecutor {
    private Logger LOGGER = LoggerFactory.getLogger(FormulaOrderUpdate.class);
    private static final int POOL_SIZE = 5;

    public void execute(DataSource dataSource) throws Exception {
        List<String> formulaScheme = this.listFormulaScheme(true, dataSource);
        List<String> designFormulaScheme = this.listFormulaScheme(false, dataSource);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        ArrayList<Callable<Void>> tasks = new ArrayList<Callable<Void>>(formulaScheme.size() + designFormulaScheme.size());
        for (String key : designFormulaScheme) {
            tasks.add(() -> {
                this.LOGGER.info("\u5347\u7ea7\u516c\u5f0f\u65b9\u6848{}\u8bbe\u8ba1\u671f\u516c\u5f0f\u6392\u5e8f\u5b57\u6bb5", (Object)key);
                try {
                    this.upgradeFormulaOrder(key, dataSource, "NR_PARAM_FORMULA_DES");
                }
                catch (Exception e) {
                    this.LOGGER.info("\u516c\u5f0f\u65b9\u6848{}\u5347\u7ea7\u8bbe\u8ba1\u671f\u516c\u5f0f\u6392\u5e8f\u5b57\u6bb5\u65f6\u5f02\u5e38:", (Object)e.getMessage());
                }
                return null;
            });
        }
        for (String key : formulaScheme) {
            tasks.add(() -> {
                this.LOGGER.info("\u5347\u7ea7\u516c\u5f0f\u65b9\u6848{}\u8fd0\u884c\u671f\u516c\u5f0f\u6392\u5e8f\u5b57\u6bb5", (Object)key);
                try {
                    this.upgradeFormulaOrder(key, dataSource, "NR_PARAM_FORMULA");
                }
                catch (Exception e) {
                    this.LOGGER.info("\u516c\u5f0f\u65b9\u6848{}\u5347\u7ea7\u8fd0\u884c\u671f\u516c\u5f0f\u6392\u5e8f\u5b57\u6bb5\u65f6\u5f02\u5e38:", (Object)e.getMessage());
                }
                return null;
            });
        }
        executor.invokeAll(tasks);
        executor.shutdown();
    }

    private List<String> listFormulaScheme(boolean runtime, DataSource dataSource) {
        ArrayList<String> formulaSchemeKey = new ArrayList<String>();
        String querySql = String.format("SELECT FS_KEY FROM %s", runtime ? "NR_PARAM_FORMULASCHEME" : "NR_PARAM_FORMULASCHEME_DES");
        try (Connection connection = DataSourceUtils.getConnection((DataSource)dataSource);
             PreparedStatement pstmt = connection.prepareStatement(querySql);
             ResultSet resultSet = pstmt.executeQuery();){
            while (resultSet.next()) {
                formulaSchemeKey.add(resultSet.getString(1));
            }
        }
        catch (Exception e) {
            this.LOGGER.error("\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u5f02\u5e38", e);
        }
        return formulaSchemeKey;
    }

    private void upgradeFormulaOrder(String formulaSchemeKey, DataSource dataSource, String tableName) {
        HashMap<String, List> orderMap = new HashMap<String, List>();
        String querySql = String.format("SELECT FL_KEY,FL_ORDER FROM %s WHERE FL_SCHEME_KEY = ?", tableName);
        try (Connection connection = DataSourceUtils.getConnection((DataSource)dataSource);
             PreparedStatement pstmt = connection.prepareStatement(querySql);){
            pstmt.setString(1, formulaSchemeKey);
            try (ResultSet resultSet = pstmt.executeQuery();){
                while (resultSet.next()) {
                    String key = resultSet.getString(1);
                    String order = resultSet.getString(2);
                    orderMap.computeIfAbsent(order, k -> new ArrayList()).add(key);
                }
            }
        }
        catch (Exception e) {
            this.LOGGER.error("\u67e5\u8be2\u516c\u5f0f\u65f6\u5f02\u5e38", e);
        }
        if (CollectionUtils.isEmpty(orderMap)) {
            return;
        }
        Set orderSet = orderMap.keySet();
        ArrayList sortedList = new ArrayList(orderSet);
        Collections.sort(sortedList);
        String updateSql = String.format("UPDATE %s SET FL_ORDINAL = ? WHERE FL_KEY = ?", tableName);
        try (Connection connection = DataSourceUtils.getConnection((DataSource)dataSource);
             PreparedStatement pstmt = connection.prepareStatement(updateSql);){
            long index = 1L;
            for (String order : sortedList) {
                List keys = (List)orderMap.get(order);
                for (String key : keys) {
                    pstmt.setBigDecimal(1, BigDecimal.valueOf(index));
                    pstmt.setString(2, key);
                    pstmt.addBatch();
                }
                ++index;
            }
            int[] updateCounts = pstmt.executeBatch();
            this.LOGGER.info("\u516c\u5f0f\u65b9\u6848{}\u5347\u7ea7\u4e86{}\u6761\u516c\u5f0f\u6392\u5e8f\u5b57\u6bb5", (Object)formulaSchemeKey, (Object)updateCounts.length);
        }
        catch (Exception e) {
            this.LOGGER.error("\u516c\u5f0f\u65b9\u6848{}\u5347\u7ea7\u516c\u5f0f\u6392\u5e8f\u5b57\u6bb5\u65f6\u5f02\u5e38", (Object)formulaSchemeKey, (Object)e);
        }
    }
}

