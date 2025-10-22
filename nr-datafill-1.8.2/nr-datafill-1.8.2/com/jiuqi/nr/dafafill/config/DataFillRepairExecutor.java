/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.definition.util.SerializeUtils
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.dafafill.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.dafafill.common.DataFillErrorEnum;
import com.jiuqi.nr.dafafill.model.ConditionField;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.ModelType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.util.SerializeUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class DataFillRepairExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(DataFillRepairExecutor.class);
    private static final String DFM_DATA = "DFM_DATA";
    private static final String DFM_ID = "DFM_ID";
    private static final String SQL_DATAFILL_SELECT = "SELECT DFM_ID,DFM_DATA FROM NR_DATAFILL_MODEL";
    private static final String SQL_DATAFILL_UPDATE = "UPDATE NR_DATAFILL_MODEL SET DFM_DATA = ? WHERE DFM_ID = ?";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        Connection connection = dataSource.getConnection();
        Statement preparedStatement = null;
        Statement upps = null;
        ResultSet resultSet = null;
        try {
            IRuntimeDataSchemeService schemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
            HashMap<String, String> modelMap = new HashMap<String, String>();
            HashMap<String, Map<String, DataDimension>> dimCache = new HashMap<String, Map<String, DataDimension>>();
            HashMap<String, Boolean> adjustCache = new HashMap<String, Boolean>();
            preparedStatement = connection.prepareStatement(SQL_DATAFILL_SELECT);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String dataStr = resultSet.getString(DFM_DATA);
                if (!StringUtils.hasText(dataStr)) continue;
                String id = resultSet.getString(DFM_ID);
                DataFillModel model = this.getModelByData(dataStr.getBytes(StandardCharsets.UTF_8));
                this.extracted(schemeService, modelMap, dimCache, adjustCache, id, model);
            }
            if (!CollectionUtils.isEmpty(modelMap)) {
                upps = connection.prepareStatement(SQL_DATAFILL_UPDATE);
                for (Map.Entry entry : modelMap.entrySet()) {
                    upps.setString(1, (String)entry.getValue());
                    upps.setString(2, (String)entry.getKey());
                    upps.addBatch();
                }
                upps.executeBatch();
            }
        }
        catch (Exception e) {
            this.logger.error("\u81ea\u5b9a\u4e49\u5f55\u5165\u6a21\u578b\u5347\u7ea7\u5f02\u5e38::", e);
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (SQLException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }
                catch (SQLException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            if (upps != null) {
                try {
                    upps.close();
                }
                catch (SQLException e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }

    private void extracted(IRuntimeDataSchemeService schemeService, Map<String, String> modelMap, Map<String, Map<String, DataDimension>> dimCache, Map<String, Boolean> adjustCache, String id, DataFillModel model) {
        try {
            if (this.needRepair(id, model, schemeService, dimCache, adjustCache)) {
                modelMap.put(id, new String(this.getDataByte(model), StandardCharsets.UTF_8));
            }
        }
        catch (Exception e) {
            this.logger.error(String.format("\u81ea\u5b9a\u4e49\u5f55\u5165\u6a21\u578b\u5347\u7ea7\u5f02\u5e38::%s", id), e);
        }
    }

    private boolean needRepair(String id, DataFillModel model, IRuntimeDataSchemeService schemeService, Map<String, Map<String, DataDimension>> dimCache, Map<String, Boolean> adjustCache) {
        if (model == null) {
            return false;
        }
        if (model.getModelType() != ModelType.SCHEME) {
            return false;
        }
        List<QueryField> assistFields = model.getAssistFields();
        List<ConditionField> conditionFields = model.getConditionFields();
        ArrayList<QueryField> newAssistFields = new ArrayList<QueryField>();
        ArrayList<ConditionField> newConditionFields = new ArrayList<ConditionField>();
        if (!CollectionUtils.isEmpty(assistFields)) {
            String dataSchemeCode = model.getAssistFields().get(0).getDataSchemeCode();
            Map<String, DataDimension> dimMap = dimCache.get(dataSchemeCode);
            if (dimMap == null) {
                dimMap = new HashMap<String, DataDimension>();
                dimCache.put(dataSchemeCode, dimMap);
                DataScheme dataScheme = schemeService.getDataSchemeByCode(dataSchemeCode);
                if (dataScheme != null) {
                    List dims = schemeService.getDataSchemeDimension(dataScheme.getKey());
                    if (!CollectionUtils.isEmpty(dims)) {
                        for (DataDimension dim : dims) {
                            if (dim.getDimensionType() != DimensionType.DIMENSION) continue;
                            dimMap.put(dim.getDimKey(), dim);
                        }
                    }
                } else {
                    this.logger.warn(String.format("\u81ea\u5b9a\u4e49\u5f55\u5165\u6a21\u578b\u5347\u7ea7\u5f02\u5e38==> %s ::\u6a21\u578b\u5b58\u7ed1\u5b9a\u7684\u6570\u636e\u65b9\u6848 %s \u4e0d\u5b58\uff01", id, dataSchemeCode));
                    return false;
                }
            }
            if (CollectionUtils.isEmpty(dimMap)) {
                this.logger.warn(String.format("\u81ea\u5b9a\u4e49\u5f55\u5165\u6a21\u578b\u5347\u7ea7\u5f02\u5e38==>%s::\u6a21\u578b\u5b58\u5728\u60c5\u666f\uff0c\u6570\u636e\u65b9\u6848 %s \u60c5\u666f\u4e3a\u7a7a\uff01", id, dataSchemeCode));
            }
            Boolean adjust = false;
            if (adjustCache.containsKey(dataSchemeCode)) {
                adjust = adjustCache.get(dataSchemeCode);
            } else {
                DataScheme dataScheme = schemeService.getDataSchemeByCode(dataSchemeCode);
                if (dataScheme != null) {
                    adjust = schemeService.enableAdjustPeriod(dataScheme.getKey());
                    adjustCache.put(dataSchemeCode, adjust);
                } else {
                    this.logger.warn(String.format("\u81ea\u5b9a\u4e49\u5f55\u5165\u6a21\u578b\u5347\u7ea7\u5f02\u5e38==> %s ::\u6a21\u578b\u5b58\u7ed1\u5b9a\u7684\u6570\u636e\u65b9\u6848 %s \u4e0d\u5b58\uff01", id, dataSchemeCode));
                    return false;
                }
            }
            boolean needRepair = false;
            HashSet<String> hiddenFullCode = new HashSet<String>();
            for (QueryField queryField : assistFields) {
                String dimId = queryField.getId();
                if (adjust.booleanValue() && "ADJUST".equals(dimId)) {
                    needRepair = true;
                    hiddenFullCode.add(queryField.getFullCode());
                    continue;
                }
                DataDimension dim = dimMap.get(dimId);
                if (dim == null) {
                    needRepair = true;
                    hiddenFullCode.add(queryField.getFullCode());
                    continue;
                }
                if (DataSchemeUtils.isSingleSelect((DataDimension)dim)) {
                    needRepair = true;
                    hiddenFullCode.add(queryField.getFullCode());
                    continue;
                }
                newAssistFields.add(queryField);
            }
            if (needRepair && !CollectionUtils.isEmpty(conditionFields)) {
                for (ConditionField conditionField : conditionFields) {
                    if (hiddenFullCode.contains(conditionField.getFullCode())) continue;
                    newConditionFields.add(conditionField);
                }
            }
            if (needRepair) {
                model.setAssistFields(newAssistFields);
                model.setConditionFields(newConditionFields);
                return true;
            }
        }
        return false;
    }

    private DataFillModel getModelByData(byte[] data) throws JQException {
        DataFillModel model = null;
        try {
            model = (DataFillModel)SerializeUtils.jsonDeserialize((byte[])data, DataFillModel.class);
        }
        catch (IOException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataFillErrorEnum.DF_ERROR_SERIALIZE);
        }
        return model;
    }

    private byte[] getDataByte(DataFillModel model) throws JQException {
        byte[] data = null;
        try {
            data = SerializeUtils.jsonSerializeToByte((Object)model);
        }
        catch (JsonProcessingException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataFillErrorEnum.DF_ERROR_SERIALIZE);
        }
        return data;
    }
}

