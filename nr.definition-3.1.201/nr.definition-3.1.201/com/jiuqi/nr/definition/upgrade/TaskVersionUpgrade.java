/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.definition.config.EnableTask2Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskVersionUpgrade
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(TaskVersionUpgrade.class);

    public void execute(DataSource dataSource) throws Exception {
        EnableTask2Config task2Config = (EnableTask2Config)SpringBeanUtils.getBean(EnableTask2Config.class);
        String version = "2.0";
        if (!task2Config.isEnable()) {
            version = "1.0";
        }
        String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s IS NULL OR %s = '' ", "NR_PARAM_TASK", "TK_VERSION", "TK_VERSION", "TK_VERSION");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSql);){
            preparedStatement.setString(1, version);
            int i = preparedStatement.executeUpdate();
            this.logger.info("\u66f4\u65b0\u4efb\u52a1\u7248\u672c{}\u4e2a", (Object)i);
        }
        catch (Exception e) {
            this.logger.error("\u66f4\u65b0\u4efb\u52a1\u5931\u8d25:" + e.getMessage(), e);
        }
    }
}

