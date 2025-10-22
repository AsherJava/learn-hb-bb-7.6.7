/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.batch.gather.gzw.service.update;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class CodeMappingUpdate {
    private final Logger log = LoggerFactory.getLogger(CodeMappingUpdate.class);
    private IRunTimeViewController runTimeViewController;

    public void execute(DataSource ds) {
        this.runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        try (Connection conn = ds.getConnection();){
            conn.setAutoCommit(true);
            this.updateEntityCodeMapping(conn);
        }
        catch (Exception e) {
            this.log.error("\u5347\u7ea7\u6279\u91cf\u6c47\u603b\u5b9e\u4f53\u7f16\u7801\u6620\u5c04\u8868\u5931\u8d25", e);
        }
    }

    private void updateEntityCodeMapping(Connection conn) throws SQLException {
        String sql = "UPDATE NR_GATHER_ENTITY_CODE_MAPPING SET ECM_ENTITY_ID = ? WHERE ECM_GATHER_SCHEME_KEY = ? AND ECM_CUSTOMIZED_CONDITION_CODE = ? AND ECM_PERIOD = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement();){
            stmt.setFetchSize(1000);
            try (ResultSet rs = stmt.executeQuery("SELECT ECM_GATHER_SCHEME_KEY,ECM_CUSTOMIZED_CONDITION_CODE,ECM_PERIOD,ECM_TASK FROM NR_GATHER_ENTITY_CODE_MAPPING");){
                int i = 0;
                HashMap<String, String> taskEntityMap = new HashMap<String, String>();
                while (rs.next()) {
                    ++i;
                    String bsKey = rs.getString(1);
                    String conditionCode = rs.getString(2);
                    String period = rs.getString(3);
                    String taskKey = rs.getString(4);
                    String defaultEntityId = taskEntityMap.computeIfAbsent(taskKey, this::getDefaultEntityIdForTask);
                    pstmt.setString(1, defaultEntityId);
                    pstmt.setString(2, bsKey);
                    pstmt.setString(3, conditionCode);
                    pstmt.setString(4, period);
                    pstmt.addBatch();
                    if (i % 1000 != 0) continue;
                    this.log.info("\u5347\u7ea7\u6279\u91cf\u6c47\u603b\u5b9e\u4f53\u7f16\u7801\u6620\u5c04\u8868\u5df2\u5904\u7406 {} \u6761\u8bb0\u5f55", (Object)i);
                    pstmt.executeBatch();
                }
                pstmt.executeBatch();
                this.log.info("\u5347\u7ea7\u6279\u91cf\u6c47\u603b\u5b9e\u4f53\u7f16\u7801\u6620\u5c04\u8868\u5171\u5904\u7406 {} \u6761\u8bb0\u5f55", (Object)i);
            }
        }
    }

    private String getDefaultEntityIdForTask(String taskKey) {
        try {
            List taskOrgLinkDefines = this.runTimeViewController.listTaskOrgLinkByTask(taskKey);
            if (CollectionUtils.isEmpty(taskOrgLinkDefines)) {
                return null;
            }
            TaskDefine task = this.runTimeViewController.getTask(taskKey);
            return task.getDw();
        }
        catch (Exception e) {
            this.log.warn("\u83b7\u53d6\u4efb\u52a1\u9ed8\u8ba4\u53e3\u5f84\u5931\u8d25,  taskKey: {}", (Object)taskKey, (Object)e);
            return null;
        }
    }
}

