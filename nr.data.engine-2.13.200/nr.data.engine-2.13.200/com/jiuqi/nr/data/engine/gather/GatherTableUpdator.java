/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.engine.gather.GatherAssistantTable;
import java.sql.Connection;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatherTableUpdator
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GatherTableUpdator.class);

    public void execute(DataSource dataSource) throws Exception {
        String gatherTempTable = GatherAssistantTable.getTempTableName();
        try (Connection connection = dataSource.getConnection();){
            boolean isExistTable = GatherAssistantTable.isExistTable(gatherTempTable);
            if (isExistTable) {
                GatherAssistantTable.dropTempTable(connection, gatherTempTable);
            }
            GatherAssistantTable.createTempTable(connection, gatherTempTable, true);
        }
        catch (Exception e) {
            logger.error("\u6c47\u603b\u4e34\u65f6\u8868\u5347\u7ea7\u5f02\u5e38\uff01", e);
        }
    }
}

