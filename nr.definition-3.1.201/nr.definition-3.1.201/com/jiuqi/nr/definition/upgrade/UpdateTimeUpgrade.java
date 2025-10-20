/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.sql.DataSource;

public class UpdateTimeUpgrade
implements CustomClassExecutor {
    private static final Timestamp des = Timestamp.valueOf(LocalDateTime.of(2024, 7, 1, 0, 0, 9));
    private static final Timestamp run = Timestamp.valueOf(LocalDateTime.of(2024, 7, 1, 0, 0, 7));

    public void execute(DataSource dataSource) throws Exception {
        String bigdataTable = "UPDATE NR_PARAM_BIGDATATABLE_DES SET BD_UPDATETIME = ? WHERE BD_UPDATETIME IS NULL";
        String runBigdataTable = "UPDATE NR_PARAM_BIGDATATABLE SET BD_UPDATETIME = ? WHERE BD_UPDATETIME IS NULL";
        this.execute(dataSource, bigdataTable, des);
        this.execute(dataSource, runBigdataTable, run);
        String regionSetting = "UPDATE NR_PARAM_REGIONSETTING_DES SET RS_UPDATETIME = ? WHERE RS_UPDATETIME IS NULL";
        String runRegionSetting = "UPDATE NR_PARAM_REGIONSETTING SET RS_UPDATETIME = ? WHERE RS_UPDATETIME IS NULL";
        String region = "UPDATE NR_PARAM_DATAREGION_DES SET DR_UPDATETIME = ? WHERE DR_UPDATETIME IS NULL";
        String runRegion = "UPDATE NR_PARAM_DATAREGION SET DR_UPDATETIME = ? WHERE DR_UPDATETIME IS NULL";
        this.execute(dataSource, regionSetting, des);
        this.execute(dataSource, runRegionSetting, run);
        this.execute(dataSource, region, des);
        this.execute(dataSource, runRegion, run);
        String form = "UPDATE NR_PARAM_FORM_DES SET FM_UPDATETIME = ? WHERE FM_UPDATETIME IS NULL";
        String runForm = "UPDATE NR_PARAM_FORM SET FM_UPDATETIME = ? WHERE FM_UPDATETIME IS NULL";
        this.execute(dataSource, form, des);
        this.execute(dataSource, runForm, run);
        String formGroup = "UPDATE NR_PARAM_FORMGROUP_DES SET FG_UPDATETIME = ? WHERE FG_UPDATETIME IS NULL";
        String runFormGroup = "UPDATE NR_PARAM_FORMGROUP SET FG_UPDATETIME = ? WHERE FG_UPDATETIME IS NULL";
        String formGroupLink = "UPDATE NR_PARAM_FORMGROUPLINK_DES SET FGL_UPDATETIME = ? WHERE FGL_UPDATETIME IS NULL";
        String runFormGroupLink = "UPDATE NR_PARAM_FORMGROUPLINK SET FGL_UPDATETIME = ? WHERE FGL_UPDATETIME IS NULL";
        this.execute(dataSource, formGroup, des);
        this.execute(dataSource, runFormGroup, run);
        this.execute(dataSource, formGroupLink, des);
        this.execute(dataSource, runFormGroupLink, run);
    }

    private void execute(DataSource dataSource, String sql, Timestamp timestamp) throws Exception {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);){
            connection.setAutoCommit(false);
            statement.setTimestamp(1, timestamp);
            statement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        }
    }
}

