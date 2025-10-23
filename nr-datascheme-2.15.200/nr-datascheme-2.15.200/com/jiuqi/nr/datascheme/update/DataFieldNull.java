/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.type.CompareType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.datascheme.update;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.type.CompareType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDesignDTO;
import com.jiuqi.nr.datascheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.DefaultVerificationParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DataFieldNull
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(DataFieldNull.class);

    public void execute(DataSource dataSource) {
        try {
            this.update(dataSource, "NR_DATASCHEME_FIELD_DES");
        }
        catch (Exception e) {
            this.logger.error("\u8bbe\u8ba1\u671f\u6307\u6807\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u5347\u7ea7\u5931\u8d25", e);
        }
        try {
            this.update(dataSource, "NR_DATASCHEME_FIELD");
        }
        catch (Exception e) {
            this.logger.error("\u8fd0\u884c\u671f\u6307\u6807\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u5347\u7ea7\u5931\u8d25", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void update(DataSource dataSource, String table) throws Exception {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)dataSource);
            String sql = String.format("SELECT %s,%s,%s,%s,%s FROM %s WHERE %s = 0 OR %s IS NOT NULL", "DF_KEY", "DF_VALIDATION_RULE", "DF_DATATYPE", "DF_NULLABLE", "DF_TITLE", table, "DF_NULLABLE", "DF_VALIDATION_RULE");
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery();
                 PreparedStatement statement = connection.prepareStatement("UPDATE " + table + " SET " + "DF_VALIDATION_RULE" + " = ? WHERE " + "DF_KEY" + " = ?");){
                ObjectMapper mapper = new ObjectMapper();
                int rowTmp = 0;
                DefaultVerificationParser verificationParser = new DefaultVerificationParser();
                while (resultSet.next()) {
                    List<ValidationRuleDTO> list;
                    String filedKey;
                    block55: {
                        String value = resultSet.getString("DF_VALIDATION_RULE");
                        filedKey = resultSet.getString("DF_KEY");
                        int typeInt = resultSet.getInt("DF_DATATYPE");
                        int nullableValue = resultSet.getInt("DF_NULLABLE");
                        String fieldTitle = resultSet.getString("DF_TITLE");
                        list = new ArrayList();
                        DataFieldType dataFieldType = DataFieldType.valueOf((int)typeInt);
                        if (dataFieldType == null) continue;
                        boolean logicNotNull = false;
                        try {
                            if (value != null) {
                                list = (List)mapper.readValue(value, (TypeReference)new TypeReference<ArrayList<ValidationRuleDTO>>(){});
                                for (ValidationRuleDTO validationRuleDTO : list) {
                                    String verification;
                                    if (validationRuleDTO.getCompareType() != null || (verification = validationRuleDTO.getVerification()) == null) continue;
                                    DataFieldDesignDTO dataFieldDesignDTO = new DataFieldDesignDTO();
                                    validationRuleDTO.setFieldTitle(fieldTitle);
                                    dataFieldDesignDTO.setDataFieldType(dataFieldType);
                                    dataFieldDesignDTO.setKey(filedKey);
                                    ValidationRule parse = verificationParser.parse(verification, (DataField)dataFieldDesignDTO);
                                    if (parse.getCompareType() == CompareType.NOTNULL) {
                                        validationRuleDTO.setMessage(null);
                                        logicNotNull = true;
                                    }
                                    validationRuleDTO.setValue(parse.getValue());
                                    validationRuleDTO.setCompareType(parse.getCompareType());
                                    validationRuleDTO.setLeftValue(parse.getLeftValue());
                                    validationRuleDTO.setRightValue(parse.getRightValue());
                                    validationRuleDTO.setInValues(parse.getInValues());
                                }
                            }
                            if (logicNotNull || nullableValue != 0) break block55;
                            ValidationRuleDTO validationRuleDTO = new ValidationRuleDTO();
                            validationRuleDTO.setCompareType(CompareType.NOTNULL);
                            validationRuleDTO.setFieldTitle(fieldTitle);
                            list.add(validationRuleDTO);
                        }
                        catch (Exception e) {
                            this.logger.error("\u6570\u636e\u65b9\u6848\uff1a\u6307\u6807\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u5c5e\u6027\u5347\u7ea7,\u6307\u6807 key = " + filedKey);
                            continue;
                        }
                    }
                    statement.setString(1, mapper.writeValueAsString(list));
                    statement.setString(2, filedKey);
                    statement.addBatch();
                    if (++rowTmp % 500 != 0) continue;
                    statement.executeBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
        }
        catch (Exception e) {
            this.logger.error("\u6570\u636e\u65b9\u6848\uff1a\u6307\u6807\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u5c5e\u6027\u5347\u7ea7\u5931\u8d25", e);
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        }
        finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                }
                catch (SQLException sQLException) {}
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }
}

