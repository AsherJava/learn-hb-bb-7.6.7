/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.service.FileOperationService
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.service.DesignBigDataService
 *  com.jiuqi.nr.definition.util.AttachmentObj
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.service.FileOperationService;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.dataentry.attachment.message.FileTableInfo;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataentry/attachment/repair"})
@Api(tags={"\u6570\u636e\u5f55\u5165\u9644\u4ef6\u4fee\u590d\u5165\u53e3"})
public class AttachmentRepairController {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentRepairController.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DesignBigDataService designBigDataService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private FileOperationService fileOperationService;

    @ResponseBody
    @ApiOperation(value="\u9644\u4ef6\u4fee\u590d")
    @GetMapping(value={"/repair"})
    public boolean repair() {
        return this.performRepair();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean performRepair() {
        Connection connection = DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
        try {
            List allDataScheme = this.runtimeDataSchemeService.getAllDataScheme();
            for (DataScheme dataScheme : allDataScheme) {
                List dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
                String fileTableName = "NR_FILE_" + dataScheme.getBizCode();
                DataAccessContext context = new DataAccessContext(this.dataModelService);
                TableModelDefine filePoolTable = this.dataModelService.getTableModelDefineByName(fileTableName);
                List filePoolFields = this.dataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
                DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(filePoolTable.getName());
                List<FileTableInfo> fileRelTableInfo = this.getFileRelTableInfo(context, filePoolFields, dimensionChanger);
                HashMap<String, String> linkAndDefaultValueMap = new HashMap<String, String>();
                ArrayList<String> linkKeyCatch = new ArrayList<String>();
                for (FileTableInfo fileTableInfo : fileRelTableInfo) {
                    DataField dataField = this.runtimeDataSchemeService.getDataField(fileTableInfo.getFieldKey());
                    DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataField.getDataTableKey());
                    if (dataTable.getDataTableType() != DataTableType.TABLE) continue;
                    ArrayList<String> defaultValues = new ArrayList<String>();
                    List dataLinks = this.runTimeViewController.getDataLinksByField(fileTableInfo.getFieldKey());
                    for (DataLinkDefine dataLink : dataLinks) {
                        String defaultValue = (String)linkAndDefaultValueMap.get(dataLink.getKey());
                        if (StringUtils.isEmpty((String)defaultValue) && !linkKeyCatch.contains(dataLink.getKey())) {
                            int index;
                            linkKeyCatch.add(dataLink.getKey());
                            byte[] bigData = this.designBigDataService.getBigData(dataLink.getKey(), "ATTACHMENT");
                            if (null == bigData) continue;
                            String attachment = DesignFormDefineBigDataUtil.bytesToString((byte[])bigData);
                            ObjectMapper mapper = new ObjectMapper();
                            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            AttachmentObj attachmentObj = null;
                            if (StringUtils.isNotEmpty((String)attachment)) {
                                attachmentObj = (AttachmentObj)mapper.readValue(attachment, AttachmentObj.class);
                            }
                            if (null == attachmentObj || StringUtils.isEmpty((String)(defaultValue = attachmentObj.getGroupKey())) || -1 == (index = defaultValue.indexOf(124))) continue;
                            defaultValue = defaultValue.substring(0, index);
                            defaultValues.add(defaultValue);
                            linkAndDefaultValueMap.put(dataLink.getKey(), defaultValue);
                            continue;
                        }
                        defaultValues.add(defaultValue);
                    }
                    if (defaultValues.isEmpty() || !defaultValues.contains(fileTableInfo.getGroupKey())) continue;
                    String newGroupKey = UUID.randomUUID().toString();
                    this.updateFileRelTable(context, filePoolFields, dimensionChanger, fileTableInfo, newGroupKey);
                    List deployInfoByDataFieldKeys = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fileTableInfo.getFieldKey()});
                    if (!deployInfoByDataFieldKeys.isEmpty()) {
                        DataFieldDeployInfo dataFieldDeployInfo = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
                        String tableName = dataFieldDeployInfo.getTableName();
                        String fieldName = dataFieldDeployInfo.getFieldName();
                        this.updateDataTable(connection, tableName, fieldName, newGroupKey, fileTableInfo.getDimensionValueSet(), dataSchemeDimension);
                    }
                    if (this.filePoolService.isOpenFilepool()) continue;
                    CommonParamsDTO params = new CommonParamsDTO();
                    params.setDataSchemeKey(dataScheme.getKey());
                    this.batchUpdateGroupKey(newGroupKey, fileTableInfo.getFileKeys(), params);
                }
            }
            logger.info("\u9644\u4ef6\u4fee\u590d\u5b8c\u6210");
            boolean bl = true;
            return bl;
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            boolean bl = false;
            return bl;
        }
        finally {
            if (null != connection) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    private void batchUpdateGroupKey(String newGroupKey, Set<String> fileKeys, CommonParamsDTO params) {
        this.fileOperationService.batchUpdateGroupKey(new ArrayList<String>(fileKeys), newGroupKey, params);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateDataTable(Connection connection, String tableName, String fieldName, String newGroupKey, DimensionValueSet dimensionValueSet, List<DataDimension> dataSchemeDimension) {
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableName).append(" set ").append(fieldName).append(" = '").append(newGroupKey).append("' where ");
        for (DataDimension dataDimension : dataSchemeDimension) {
            if (DimensionType.UNIT.equals((Object)dataDimension.getDimensionType()) || DimensionType.DIMENSION.equals((Object)dataDimension.getDimensionType())) {
                IEntityDefine entity = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
                String columnCode = dimensionChanger.getColumnCode(entity.getDimensionName());
                sql.append(columnCode).append(" = '").append((String)dimensionValueSet.getValue(entity.getDimensionName())).append("' and ");
                continue;
            }
            if (!DimensionType.PERIOD.equals((Object)dataDimension.getDimensionType())) continue;
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dataDimension.getDimKey());
            String columnCode = dimensionChanger.getColumnCode(periodEntity.getDimensionName());
            sql.append(columnCode).append(" = '").append((String)dimensionValueSet.getValue(periodEntity.getDimensionName())).append("' and ");
        }
        sql.delete(sql.length() - 5, sql.length());
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql.toString());
            statement.execute();
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                }
                catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private void updateFileRelTable(DataAccessContext context, List<ColumnModelDefine> filePoolFields, DimensionChanger dimensionChanger, FileTableInfo fileTableInfo, String newGroupKey) {
        DimensionValueSet dimensionValueSet = fileTableInfo.getDimensionValueSet();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        int groupIndex = 0;
        for (ColumnModelDefine filePoolField : filePoolFields) {
            Object value;
            String dimensionName;
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (filePoolField.getCode().equals("ISDELETE")) {
                queryModel.getColumnFilters().put(filePoolField, "0");
                continue;
            }
            if (filePoolField.getCode().equals("FIELD_KEY")) {
                queryModel.getColumnFilters().put(filePoolField, fileTableInfo.getFieldKey());
                continue;
            }
            if (filePoolField.getCode().equals("FILEKEY")) {
                queryModel.getColumnFilters().put(filePoolField, new ArrayList<String>(fileTableInfo.getFileKeys()));
                continue;
            }
            if (filePoolField.getCode().equals("GROUPKEY")) {
                groupIndex = filePoolFields.indexOf(filePoolField);
                continue;
            }
            if (filePoolField.getCode().equals("ID") || filePoolField.getCode().equals("ISDELETE") || !StringUtils.isNotEmpty((String)(dimensionName = dimensionChanger.getDimensionName(filePoolField))) || null == (value = dimensionValueSet.getValue(dimensionName))) continue;
            queryModel.getColumnFilters().put(filePoolField, value);
        }
        INvwaUpdatableDataAccess updatableDataAccess = this.iNvwaDataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            INvwaUpdatableDataSet iNvwaDataRows = updatableDataAccess.executeQueryForUpdate(context);
            for (int i = 0; i < iNvwaDataRows.size(); ++i) {
                INvwaDataRow row = iNvwaDataRows.getRow(i);
                row.setValue(groupIndex, (Object)newGroupKey);
            }
            iNvwaDataRows.commitChanges(context);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private List<FileTableInfo> getFileRelTableInfo(DataAccessContext context, List<ColumnModelDefine> filePoolFields, DimensionChanger dimensionChanger) {
        ArrayList<FileTableInfo> fileTableInfos = new ArrayList<FileTableInfo>();
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (ColumnModelDefine filePoolField : filePoolFields) {
            queryModel.getColumns().add(new NvwaQueryColumn(filePoolField));
            if (!filePoolField.getCode().equals("ISDELETE")) continue;
            queryModel.getColumnFilters().put(filePoolField, "0");
        }
        INvwaDataAccess readOnlyDataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
        try {
            MemoryDataSet dataTable = readOnlyDataAccess.executeQuery(context);
            List columns = queryModel.getColumns();
            for (int i = 0; i < dataTable.size(); ++i) {
                FileTableInfo fileTableInfo;
                Object dimensionName;
                String fieldKey = "";
                String groupkey = "";
                String fileKey = "";
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                DataRow item = dataTable.get(i);
                for (int j = 0; j < columns.size(); ++j) {
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FIELD_KEY")) {
                        fieldKey = item.getString(j);
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("GROUPKEY")) {
                        groupkey = item.getString(j);
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("FILEKEY")) {
                        fileKey = item.getString(j);
                        continue;
                    }
                    if (((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("ID") || ((NvwaQueryColumn)columns.get(j)).getColumnModel().getCode().equals("ISDELETE") || !StringUtils.isNotEmpty((String)(dimensionName = dimensionChanger.getDimensionName(((NvwaQueryColumn)columns.get(j)).getColumnModel())))) continue;
                    dimensionValueSet.setValue((String)dimensionName, (Object)item.getString(j));
                }
                boolean canAdd = true;
                dimensionName = fileTableInfos.iterator();
                while (dimensionName.hasNext()) {
                    fileTableInfo = (FileTableInfo)dimensionName.next();
                    if (0 != fileTableInfo.getDimensionValueSet().compareTo(dimensionValueSet) || !fileTableInfo.getFieldKey().equals(fieldKey)) continue;
                    canAdd = false;
                    fileTableInfo.getFileKeys().add(fileKey);
                    break;
                }
                if (!canAdd) continue;
                HashSet<String> fileKeys = new HashSet<String>();
                fileKeys.add(fileKey);
                fileTableInfo = new FileTableInfo(dimensionValueSet, fieldKey, groupkey, fileKeys);
                fileTableInfos.add(fileTableInfo);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return fileTableInfos;
    }
}

