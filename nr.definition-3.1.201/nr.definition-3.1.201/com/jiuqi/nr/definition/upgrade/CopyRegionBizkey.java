/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class CopyRegionBizkey
implements CustomClassExecutor {
    private Connection connection = null;
    public static final String SQL1 = "select DR_KEY,DR_MASTER_KEY from NR_PARAM_DATAREGION_DES where DR_MASTER_KEY is not null and DR_BIZKEYFIELDS is null";
    public static final String SQL2 = "select FD_CODE from DES_SYS_FIELDDEFINE where FD_KEY=?";
    public static final String SQL3 = "update NR_PARAM_DATAREGION_DES set DR_BIZKEYFIELDS=? where DR_KEY=?";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        try {
            this.connection = dataSource.getConnection();
            try (PreparedStatement ps1 = this.connection.prepareStatement(SQL1);
                 ResultSet rs1 = ps1.executeQuery();){
                while (rs1.next()) {
                    String regionKey = rs1.getString(1);
                    String masterKey = rs1.getString(2);
                    if (!StringUtils.isNotEmpty((String)regionKey)) continue;
                    String[] masterKeyArr = masterKey.split(";");
                    this.doExecute(masterKeyArr, regionKey);
                }
            }
        }
        catch (Exception exception) {
        }
        finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
    }

    private void doExecute(String[] masterKeyArr, String regionKey) throws SQLException {
        StringBuilder bizkey = new StringBuilder();
        for (String fieldKey : masterKeyArr) {
            if (!StringUtils.isNotEmpty((String)fieldKey)) continue;
            try (PreparedStatement ps2 = this.connection.prepareStatement(SQL2);){
                ps2.setString(1, fieldKey);
                try (ResultSet rs2 = ps2.executeQuery();){
                    if (!rs2.next()) continue;
                    bizkey.append(fieldKey).append(";");
                }
            }
        }
        if (StringUtils.isNotEmpty((String)bizkey.toString())) {
            bizkey = new StringBuilder(bizkey.substring(0, bizkey.length() - 1));
            try (PreparedStatement ps3 = this.connection.prepareStatement(SQL3);){
                ps3.setString(1, bizkey.toString());
                ps3.setString(2, regionKey);
                ps3.executeUpdate();
            }
        }
    }
}

