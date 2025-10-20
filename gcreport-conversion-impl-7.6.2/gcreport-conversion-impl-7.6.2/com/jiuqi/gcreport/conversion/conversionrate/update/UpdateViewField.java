/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.update.UpdateViewField
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.conversion.conversionrate.update;

import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateViewField
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(com.jiuqi.np.definition.internal.update.UpdateViewField.class);
    private Connection connection = null;
    private static final String SQL_UPDATE = "UPDATE GC_CONV_RATE_V set DATATIME = ? WHERE id = ?";

    public void execute(DataSource dataSource) throws Exception {
        this.connection = dataSource.getConnection();
        try {
            Map<String, String> result = this.getTableMap();
            this.updateDataTime(result);
        }
        catch (SQLException e) {
            this.connection.rollback();
            e.printStackTrace();
        }
        finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map<String, String> getTableMap() throws SQLException {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        String sql = "  SELECT v.id,v.periodYear,g.periodId,v.periodValue \n  from GC_CONV_RATE_V v \n  left join GC_CONV_RATE_T t on v.nodeid = t.id \n  left join GC_CONV_RATE_G g  on t.rategroupId = g.id \n";
        try (ResultSet rs = null;
             PreparedStatement pstm = this.connection.prepareStatement(sql);){
            rs = pstm.executeQuery();
            while (rs.next()) {
                String dataTime;
                String id = rs.getString(1);
                int periodYear = rs.getInt(2);
                int periodId = rs.getInt(3);
                int periodValue = rs.getInt(4);
                if (periodId == 1) {
                    dataTime = new StringBuffer(String.format("%04d", periodYear)).append("N").append("0001").toString();
                } else {
                    String periodSymbol = new String();
                    switch (periodId) {
                        case 2: {
                            periodSymbol = "H";
                            break;
                        }
                        case 3: {
                            periodSymbol = "J";
                            break;
                        }
                        case 4: {
                            periodSymbol = "Y";
                            break;
                        }
                        case 5: {
                            periodSymbol = "X";
                            break;
                        }
                        case 6: {
                            periodSymbol = "R";
                            break;
                        }
                        case 7: {
                            periodSymbol = "Z";
                            break;
                        }
                        case 8: {
                            periodSymbol = "B";
                        }
                    }
                    dataTime = new StringBuffer(String.format("%04d", periodYear)).append(periodSymbol).append(String.format("%04d", periodValue)).toString();
                }
                resultMap.put(id, dataTime);
            }
        }
        return resultMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateDataTime(Map<String, String> result) throws SQLException {
        PreparedStatement pstm = null;
        String sql = SQL_UPDATE;
        pstm = this.connection.prepareStatement(sql);
        try {
            this.connection.setAutoCommit(false);
            for (String resultKey : result.keySet()) {
                int i = 1;
                pstm.setString(i++, result.get(resultKey));
                pstm.setString(i++, resultKey);
                pstm.addBatch();
            }
            pstm.executeBatch();
            this.connection.commit();
        }
        finally {
            if (pstm != null) {
                pstm.close();
            }
        }
    }
}

