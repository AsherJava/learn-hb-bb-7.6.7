/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.internal.deploy.TableDeployService;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class FixOldTablePK
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(FixOldTablePK.class);
    private static final String TABLE_MODEL_NPE_PREFIX = "{}\u8868\u6a21\u578b\u4e0d\u5b58\u5728\uff0c\u5c06\u5f71\u54cd\u5ba1\u6838\u529f\u80fd\u4f7f\u7528\uff0c\u8bf7\u5728\u7cfb\u7edf\u5347\u7ea7\u540e\u4fee\u590d\u53c2\u6570";

    public void execute(DataSource dataSource) throws Exception {
        NrdbHelper nrdbHelper = (NrdbHelper)BeanUtil.getBean(NrdbHelper.class);
        if (nrdbHelper.isEnableNrdb()) {
            return;
        }
        TableDeployService tableDeployService = (TableDeployService)BeanUtil.getBean(TableDeployService.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        try {
            List allTaskDefines = runTimeViewController.getAllTaskDefines();
            if (!CollectionUtils.isEmpty(allTaskDefines)) {
                for (TaskDefine taskDefine : allTaskDefines) {
                    this.executeByTask(dataSource, taskDefine, executorService, runTimeViewController, dataModelService, tableDeployService);
                }
            }
            executorService.shutdown();
            FixOldTablePK.waitUntilFinish(executorService);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void executeByTask(DataSource dataSource, TaskDefine taskDefine, ExecutorService executorService, IRunTimeViewController runTimeViewController, DataModelService dataModelService, TableDeployService tableDeployService) {
        executorService.execute(() -> {
            List formSchemeDefines = null;
            try {
                formSchemeDefines = runTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
            }
            catch (Exception e) {
                logger.error(taskDefine.getKey() + "\u4efb\u52a1\u4e0b\u7684\u5ba1\u6838\u76f8\u5173\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
            }
            if (CollectionUtils.isEmpty(formSchemeDefines)) {
                return;
            }
            try (Connection connection = dataSource.getConnection();){
                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    this.executeByFormScheme(taskDefine, dataModelService, tableDeployService, formSchemeDefine, connection);
                }
            }
            catch (SQLException e) {
                logger.error(taskDefine.getKey() + "\u4efb\u52a1\u4e0b\u7684\u5ba1\u6838\u76f8\u5173\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
            }
        });
    }

    private void executeByFormScheme(TaskDefine taskDefine, DataModelService dataModelService, TableDeployService tableDeployService, FormSchemeDefine formSchemeDefine, Connection connection) {
        try {
            this.fixALLCKRTable(dataModelService, tableDeployService, connection, taskDefine, formSchemeDefine);
        }
        catch (Exception ex) {
            logger.error(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684ALLCKR\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u5f02\u5e38:" + ex.getMessage(), ex);
        }
        try {
            this.fixCKRTable(dataModelService, tableDeployService, connection, taskDefine, formSchemeDefine);
        }
        catch (Exception ex) {
            logger.error(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684CKR\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u5f02\u5e38:" + ex.getMessage(), ex);
        }
        try {
            this.fixCKDTable(dataModelService, tableDeployService, connection, taskDefine, formSchemeDefine);
        }
        catch (Exception ex) {
            logger.error(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684CKD\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u5f02\u5e38:" + ex.getMessage(), ex);
        }
    }

    private static void waitUntilFinish(ExecutorService executorService) throws InterruptedException {
        while (true) {
            if (executorService.isTerminated()) break;
            Thread.sleep(1000L);
        }
        logger.info("\u7cfb\u7edf\u6240\u6709\u4efb\u52a1\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u5ba1\u6838\u76f8\u5173\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u7ed3\u675f\uff0c\u8be6\u60c5\u8bf7\u770b\u65e5\u5fd7");
    }

    private void fixALLCKRTable(DataModelService dataModelService, TableDeployService tableDeployService, Connection connection, TaskDefine taskDefine, FormSchemeDefine formSchemeDefine) throws Exception {
        String allCKRTableName = CheckTableNameUtil.getAllCKRTableName(formSchemeDefine.getFormSchemeCode());
        TableModelDefine table = dataModelService.getTableModelDefineByName(allCKRTableName);
        if (table == null) {
            logger.error(TABLE_MODEL_NPE_PREFIX, (Object)allCKRTableName);
            return;
        }
        ColumnModelDefine versionCol = dataModelService.getColumnModelDefineByCode(table.getID(), "VERSIONID");
        if (versionCol != null) {
            String sql = "truncate table " + allCKRTableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);){
                preparedStatement.execute();
            }
            tableDeployService.deployALLCKRTable(taskDefine, formSchemeDefine);
            logger.info(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684ALLCKR\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u6210\u529f");
        }
    }

    private void fixCKRTable(DataModelService dataModelService, TableDeployService tableDeployService, Connection connection, TaskDefine taskDefine, FormSchemeDefine formSchemeDefine) throws Exception {
        String ckrTableName = CheckTableNameUtil.getCKRTableName(formSchemeDefine.getFormSchemeCode());
        TableModelDefine table = dataModelService.getTableModelDefineByName(ckrTableName);
        if (table == null) {
            logger.error(TABLE_MODEL_NPE_PREFIX, (Object)ckrTableName);
            return;
        }
        ColumnModelDefine versionCol = dataModelService.getColumnModelDefineByCode(table.getID(), "VERSIONID");
        if (versionCol != null) {
            String sql = "truncate table " + ckrTableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);){
                preparedStatement.execute();
            }
            tableDeployService.deployCKRTable(taskDefine, formSchemeDefine);
            logger.info(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684CKR\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u6210\u529f");
        }
    }

    private void fixCKDTable(DataModelService dataModelService, TableDeployService tableDeployService, Connection connection, TaskDefine taskDefine, FormSchemeDefine formSchemeDefine) throws Exception {
        String ckdTableName = CheckTableNameUtil.getCKDTableName(formSchemeDefine.getFormSchemeCode());
        TableModelDefine table = dataModelService.getTableModelDefineByName(ckdTableName);
        if (table == null) {
            logger.error(TABLE_MODEL_NPE_PREFIX, (Object)ckdTableName);
            return;
        }
        ColumnModelDefine versionCol = dataModelService.getColumnModelDefineByCode(table.getID(), "VERSIONID");
        if (versionCol != null) {
            this.copyTable(ckdTableName, connection);
            List oldColumns = dataModelService.getColumnModelDefinesByTable(table.getID()).stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
            tableDeployService.deployCKDTable(taskDefine, formSchemeDefine);
            List<String> columns = dataModelService.getColumnModelDefinesByTable(table.getID()).stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
            columns.retainAll(oldColumns);
            this.copyData(ckdTableName, connection, columns);
            logger.info(formSchemeDefine.getKey() + "\u62a5\u8868\u65b9\u6848\u4e0b\u7684CKD\u8868\u65e7\u6a21\u578b\u5347\u7ea7\u6210\u529f");
        }
    }

    private void copyTable(String tableName, Connection connection) throws SQLException {
        String sql = "create table t_" + tableName + " as select * from " + tableName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);){
            preparedStatement.execute();
        }
        String del = "delete from " + tableName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(del);){
            preparedStatement.execute();
        }
    }

    private void copyData(String tableName, Connection connection, List<String> columns) throws SQLException {
        String colSql = String.join((CharSequence)",", columns);
        String copySql = String.format("insert into %s (%s) select %s from t_%s", tableName, colSql, colSql, tableName);
        try (PreparedStatement preparedStatement = connection.prepareStatement(copySql);){
            preparedStatement.execute();
        }
        String del = "drop table t_" + tableName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(del);){
            preparedStatement.execute();
        }
    }
}

