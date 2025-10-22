/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.batch.summary.storage.update;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.batch.summary.storage.common.BatchSummaryHelper;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class SummarySchemeUpdate {
    private final Logger log = LoggerFactory.getLogger(SummarySchemeUpdate.class);
    private IRunTimeViewController runTimeViewController;
    private BatchSummaryHelper batchSummaryHelper;

    public void execute(DataSource ds) {
        this.batchSummaryHelper = (BatchSummaryHelper)SpringBeanUtils.getBean(BatchSummaryHelper.class);
        this.runTimeViewController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
        try (Connection conn = ds.getConnection();){
            conn.setAutoCommit(true);
            this.updateSchemes(conn);
        }
        catch (Exception e) {
            this.log.error("\u5347\u7ea7\u6279\u91cf\u6c47\u603b\u65b9\u6848\u8868\u5931\u8d25", e);
        }
    }

    private void updateSchemes(Connection conn) throws SQLException {
        String sql = "UPDATE NR_BS_SCHEME SET BS_SINGLE_DIM = ?, BS_ENTITY_ID = ? WHERE BS_KEY = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT BS_KEY,BS_TASK,BS_CORPORATE_ENTITY_TYPE FROM NR_BS_SCHEME");){
            while (rs.next()) {
                String bsKey = rs.getString(1);
                String taskKey = rs.getString(2);
                String corporateType = rs.getString(3);
                String singleDimValues = this.processCorporateType(bsKey, taskKey, corporateType);
                String defaultEntityId = this.getDefaultEntityIdForTask(taskKey);
                pstmt.setString(1, singleDimValues);
                pstmt.setString(2, defaultEntityId);
                pstmt.setString(3, bsKey);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    private String processCorporateType(String bsKey, String taskKey, String corporateType) {
        try {
            if (!StringUtils.hasLength(corporateType)) {
                return null;
            }
            if ("DEFAULT_TEMP_COPORATE_VALUE".equals(corporateType)) {
                return null;
            }
            List<DataDimension> singleDimensions = this.batchSummaryHelper.getSingleDimensions(taskKey);
            if (CollectionUtils.isEmpty(singleDimensions)) {
                return null;
            }
            if (singleDimensions.size() == 1) {
                String entityId = singleDimensions.get(0).getDimKey();
                return entityId + ":" + corporateType;
            }
            this.log.warn("\u6c47\u603b\u65b9\u6848 {} \u4efb\u52a1 {} \u5b58\u5728\u591a\u4e2a\u5355\u4e00\u60c5\u666f, \u65e0\u6cd5\u81ea\u52a8\u5347\u7ea7,\u8bf7\u624b\u52a8\u5347\u7ea7 {}", bsKey, taskKey, corporateType);
        }
        catch (Exception e) {
            this.log.warn("\u5347\u7ea7\u6c47\u603b\u65b9\u6848\u5355\u4e00\u60c5\u666f\u5931\u8d25, bsKey : {} taskKey: {}", bsKey, taskKey, e);
        }
        return null;
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

