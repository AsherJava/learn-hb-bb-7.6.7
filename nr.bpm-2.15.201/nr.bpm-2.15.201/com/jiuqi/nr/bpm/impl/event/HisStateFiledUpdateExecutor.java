/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.exception.CreateSystemTableException
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.xlib.utils.CollectionUtils
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.bpm.impl.upload.dao.TableConstant;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.xlib.utils.CollectionUtils;
import com.jiuqi.xlib.utils.StringUtils;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HisStateFiledUpdateExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(HisStateFiledUpdateExecutor.class);
    private static final String CMT = "CMT";
    private IDesignTimeViewController designTimeViewController;
    private DesignDataModelService designDataModelService;
    private DataModelDeployService dataModelDeployService;

    private void init() {
        this.designTimeViewController = (IDesignTimeViewController)BeanUtil.getBean(IDesignTimeViewController.class);
        this.designDataModelService = (DesignDataModelService)BeanUtil.getBean(DesignDataModelService.class);
        this.dataModelDeployService = (DataModelDeployService)BeanUtil.getBean(DataModelDeployService.class);
    }

    private List<String> getHisTableCode() {
        ArrayList<String> hisTableCodes = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        try {
            List allTaskDefines = this.designTimeViewController.getAllTaskDefines();
            for (DesignTaskDefine designTaskDefine : allTaskDefines) {
                List formSchemes = this.designTimeViewController.queryFormSchemeByTask(designTaskDefine.getKey());
                for (DesignFormSchemeDefine formScheme : formSchemes) {
                    String hisTableName = TableConstant.getSysUploadRecordTableName(formScheme.getFormSchemeCode());
                    hisTableCodes.add(hisTableName);
                }
                sb.append("\u4efb\u52a1\u540d\u79f0:" + designTaskDefine.getTaskCode() + "|" + designTaskDefine.getTitle()).append("\n");
            }
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u5386\u53f2\u72b6\u6001\u8868\u540d\u5931\u8d25," + sb.toString());
        }
        return hisTableCodes;
    }

    private List<String> getPrimaryKeyCodes(String bizKeys) {
        ArrayList<String> primaryKeyCodes = new ArrayList<String>();
        try {
            String[] bizKeyArray = bizKeys.split(";");
            List columnModelDefines = this.designDataModelService.getColumnModelDefines(bizKeyArray);
            for (DesignColumnModelDefine designColumnModelDefine : columnModelDefines) {
                primaryKeyCodes.add(designColumnModelDefine.getCode());
            }
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u8868\u7684\u4e1a\u52a1\u4e3b\u952e\u5931\u8d25");
        }
        return primaryKeyCodes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        if (!StringUtils.hasText((String)CMT) || !StringUtils.hasText((String)CMT)) {
            return;
        }
        this.init();
        List<String> hisTableCode = this.getHisTableCode();
        this.logger.info("======\u5386\u53f2\u72b6\u6001\u8868BLOB\u5b57\u6bb5\u8c03\u6574,\u5347\u7ea7\u5f00\u59cb\uff01");
        String tempFieldName = "CMT_01";
        try {
            for (String tableCode : hisTableCode) {
                DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
                if (tableModelDefine == null) continue;
                String bizKeys = tableModelDefine.getBizKeys();
                List<String> primaryKeyCodes = this.getPrimaryKeyCodes(bizKeys);
                if (primaryKeyCodes == null || primaryKeyCodes.size() == 0) {
                    this.logger.error("\u5386\u53f2\u72b6\u6001\u8868" + tableCode + ",\u4e3b\u952e\u4e3a\u7a7a! \u4e0d\u80fd\u8fdb\u884cblob\u5347\u7ea7\u64cd\u4f5c");
                }
                try {
                    List<ColumnData> data = this.getHisData(dataSource, tableCode, primaryKeyCodes);
                    this.addColumn(tableModelDefine.getID(), tempFieldName);
                    if (!CollectionUtils.isEmpty(data)) {
                        this.updateData(dataSource, tableCode, primaryKeyCodes, data, tempFieldName);
                    }
                    this.dropColumn(tableModelDefine.getID());
                    this.renameColumn(tableModelDefine.getID(), tempFieldName);
                    this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01\u5386\u53f2\u72b6\u6001\u8868\u4e3a" + tableCode + ",\u66f4\u65b0\u6570\u636e\u4e2a\u6570:" + data.size());
                }
                catch (Exception e) {
                    this.logger.error("\u5347\u7ea7\u5931\u8d25!\u5386\u53f2\u72b6\u6001\u8868\u4e3a" + tableCode, e);
                }
            }
        }
        catch (Exception e) {
            this.logger.info("\u5347\u7ea7\u5931\u8d25!");
        }
        finally {
            this.logger.info("==========\u5386\u53f2\u72b6\u6001\u8868BLOB\u5b57\u6bb5\u5347\u7ea7\u5b8c\u6210\uff01=========");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<ColumnData> getHisData(DataSource dataSource, String tableCode, List<String> primaryKeyCodes) {
        List<ColumnData> data = new ArrayList<ColumnData>();
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            StringBuilder querySql = new StringBuilder();
            querySql.append("SELECT ").append(CMT).append(",");
            for (String primaryKey : primaryKeyCodes) {
                querySql.append(primaryKey).append(",");
            }
            querySql.deleteCharAt(querySql.length() - 1);
            querySql.append(" FROM ").append(tableCode);
            preparedStatement = connection.prepareStatement(querySql.toString());
            resultSet = preparedStatement.executeQuery();
            data = this.getData(resultSet, primaryKeyCodes);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
        }
        return data;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateData(DataSource dataSource, String tableCode, List<String> primaryKeyCodes, List<ColumnData> data, String tempFieldName) {
        Connection connection = null;
        Statement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            StringBuilder updateSQL = new StringBuilder();
            updateSQL.append("UPDATE ").append(tableCode).append(" SET ").append(tempFieldName).append("= ? WHERE ");
            for (int i = 0; i < primaryKeyCodes.size(); ++i) {
                if (i != primaryKeyCodes.size() - 1) {
                    updateSQL.append(primaryKeyCodes.get(i)).append("=? AND ");
                    continue;
                }
                updateSQL.append(primaryKeyCodes.get(i)).append("=?");
            }
            preparedStatement = connection.prepareStatement(updateSQL.toString());
            this.setParams((PreparedStatement)preparedStatement, data);
            preparedStatement.executeBatch();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private String getString(ResultSet rs) throws SQLException {
        int findColumn;
        Object object = rs.getObject(CMT);
        if (object != null && object instanceof Blob && (findColumn = rs.findColumn(CMT)) > 0) {
            byte[] bytes = rs.getBytes(CMT);
            String s = null;
            if (bytes != null && bytes.length > 0) {
                s = new String(bytes, StandardCharsets.UTF_8);
            }
            return s;
        }
        return null;
    }

    private List<ColumnData> getData(ResultSet resultSet, List<String> primaryKeyCodes) throws SQLException {
        ArrayList<ColumnData> result = new ArrayList<ColumnData>();
        while (resultSet.next()) {
            ColumnData r = new ColumnData();
            Object[] values = new Object[primaryKeyCodes.size()];
            for (int i = 0; i < primaryKeyCodes.size(); ++i) {
                values[i] = resultSet.getObject(primaryKeyCodes.get(i));
            }
            r.setValue(values);
            String blob = this.getString(resultSet);
            if (!StringUtils.hasLength((String)blob)) continue;
            r.setData(blob);
            result.add(r);
        }
        return result;
    }

    private void setParams(PreparedStatement preparedStatement, List<ColumnData> data) throws SQLException {
        for (ColumnData columnData : data) {
            preparedStatement.setString(1, columnData.getData());
            for (int i = 0; i < columnData.getValue().length; ++i) {
                preparedStatement.setObject(i + 2, columnData.getValue()[i]);
            }
            preparedStatement.addBatch();
        }
    }

    private void addColumn(String tableKey, String tempFieldName) throws Exception {
        try {
            DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
            fieldDefine.setCode(tempFieldName);
            fieldDefine.setName(tempFieldName);
            fieldDefine.setTitle("\u586b\u62a5\u8bf4\u660e");
            fieldDefine.setColumnType(ColumnModelType.CLOB);
            fieldDefine.setTableID(tableKey);
            fieldDefine.setNullAble(true);
            this.designDataModelService.insertColumnModelDefine(fieldDefine);
            this.dataModelDeployService.deployTable(tableKey);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private void dropColumn(String tableKey) throws Exception {
        try {
            DesignColumnModelDefine columnModelDefine = this.queryFieldDefinesByCode(CMT, tableKey);
            if (columnModelDefine != null) {
                this.designDataModelService.deleteColumnModelDefine(columnModelDefine.getID());
                this.dataModelDeployService.deployTable(tableKey);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public DesignColumnModelDefine queryFieldDefinesByCode(String fieldCode, String tableKey) {
        DesignColumnModelDefine fieldDefine = null;
        try {
            fieldDefine = this.designDataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query field by code %s error.", fieldCode), (Throwable)e);
        }
        return fieldDefine;
    }

    private void renameColumn(String tableKey, String tempFieldName) throws Exception {
        try {
            DesignColumnModelDefine columnModelDefine = this.queryFieldDefinesByCode(tempFieldName, tableKey);
            if (columnModelDefine != null) {
                columnModelDefine.setCode(CMT);
                columnModelDefine.setName(CMT);
                columnModelDefine.setColumnType(ColumnModelType.CLOB);
                this.designDataModelService.updateColumnModelDefine(columnModelDefine);
                this.dataModelDeployService.deployTable(tableKey);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    static class ColumnData {
        private Object[] value;
        private String data;

        ColumnData() {
        }

        public Object[] getValue() {
            return this.value;
        }

        public void setValue(Object[] value) {
            this.value = value;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}

