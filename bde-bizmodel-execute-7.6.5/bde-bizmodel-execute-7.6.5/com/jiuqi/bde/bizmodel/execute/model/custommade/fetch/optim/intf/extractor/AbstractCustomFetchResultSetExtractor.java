/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum
 *  com.jiuqi.bde.bizmodel.define.config.BdeBizModelConfig
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.util.Pair
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.extractor;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.AggregateFuncEnum;
import com.jiuqi.bde.bizmodel.define.config.BdeBizModelConfig;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchArgs;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchField;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchFieldMeta;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ICustomFetchResultSetFilter;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.ResultColumnType;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.AbstractCustomFetchResult;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.ResultSetExtractor;

public abstract class AbstractCustomFetchResultSetExtractor
implements ResultSetExtractor<AbstractCustomFetchResult> {
    private CustomBizModelDTO customBizModel;
    private CustomFetchArgs fetchArgs;
    private ICustomFetchResultSetFilter filter;
    private int rowLimit;

    public AbstractCustomFetchResultSetExtractor(CustomBizModelDTO customBizModel, CustomFetchArgs fetchArgs, ICustomFetchResultSetFilter filter) {
        this.customBizModel = customBizModel;
        this.fetchArgs = fetchArgs;
        this.filter = filter;
        this.rowLimit = Integer.valueOf(((INvwaSystemOptionService)ApplicationContextRegister.getBean(INvwaSystemOptionService.class)).findValueById("BDE_CUSTOMFETCH_ROW_LIMIT"));
    }

    protected Object parseColData(ResultSet rs, Integer index, String colunKey) throws SQLException {
        if (!BdeBizModelConfig.getParseFetchResultByDb().booleanValue()) {
            CustomFetchField fetchField = this.getFetchArgs().getFieldMeta().getFetchField(colunKey);
            switch (fetchField.getFunc()) {
                case SUM: {
                    return this.convertDecimal(rs, index + 1, fetchField.getColType());
                }
                case COUNT: {
                    return this.convertInt(rs, index + 1, fetchField.getColType());
                }
            }
            return this.convertByType(rs, index + 1, fetchField.getColType());
        }
        if (rs.getObject(index + 1) == null) {
            return null;
        }
        if (rs.getObject(index + 1) instanceof BigDecimal) {
            return new BigDecimal(rs.getString(index + 1));
        }
        if (rs.getObject(index + 1) instanceof Integer) {
            return Integer.valueOf(rs.getString(index + 1));
        }
        return rs.getString(index + 1);
    }

    protected Pair<Map<String, Integer>, Map<String, ResultColumnType>> buildColumnType() {
        LinkedHashMap<String, Integer> columnMap = new LinkedHashMap<String, Integer>();
        HashMap<String, ResultColumnType> columnTypeMap = new HashMap<String, ResultColumnType>();
        CustomFetchFieldMeta fieldMeta = this.getFetchArgs().getFieldMeta();
        int colIdx = 1;
        for (Map.Entry<String, CustomFetchField> fetchFieldEntry : fieldMeta.getFetchFieldMap().entrySet()) {
            AggregateFuncEnum func = fieldMeta.getFetchFieldFunc(fetchFieldEntry.getKey());
            ResultColumnType colType = null;
            switch (func) {
                case SUM: {
                    colType = ResultColumnType.NUMBER;
                    break;
                }
                case AVG: {
                    colType = ResultColumnType.NUMBER;
                    break;
                }
                case COUNT: {
                    colType = ResultColumnType.INT;
                    break;
                }
                default: {
                    colType = ResultColumnType.STRING;
                }
            }
            columnTypeMap.put(fetchFieldEntry.getKey(), colType);
            columnMap.put(fetchFieldEntry.getKey(), colIdx - 1);
            ++colIdx;
        }
        return Pair.of(columnMap, columnTypeMap);
    }

    protected void checkLimit(int rowCt) {
        if (rowCt > this.rowLimit) {
            throw new BusinessRuntimeException(String.format("\u81ea\u5b9a\u4e49\u53d6\u6570\u6a21\u578b\u3010%1$s\u3011\u7ed3\u679c\u96c6\u8d85\u8fc7\u7cfb\u7edf\u9650\u5236\u3010%2$d\u3011\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u8c03\u6574\u7cfb\u7edf\u8bbe\u7f6e", this.customBizModel.getName(), this.rowLimit));
        }
    }

    protected CustomBizModelDTO getCustomBizModel() {
        return this.customBizModel;
    }

    protected CustomFetchArgs getFetchArgs() {
        return this.fetchArgs;
    }

    protected ICustomFetchResultSetFilter getFilter() {
        return this.filter;
    }

    protected String getCondiValue(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String)obj;
        }
        return obj.toString();
    }

    private BigDecimal convertDecimal(ResultSet rs, int fdIndex, ResultColumnType colType) throws SQLException {
        if (rs.getObject(fdIndex) == null) {
            return BigDecimal.ZERO;
        }
        if (rs.getObject(fdIndex) instanceof BigDecimal) {
            return rs.getBigDecimal(fdIndex);
        }
        return new BigDecimal(rs.getObject(fdIndex).toString());
    }

    private Integer convertInt(ResultSet rs, int fdIndex, ResultColumnType colType) throws SQLException {
        if (rs.getObject(fdIndex) == null) {
            return 0;
        }
        if (rs.getObject(fdIndex) instanceof Integer) {
            return rs.getInt(fdIndex);
        }
        return Integer.valueOf(rs.getObject(fdIndex).toString());
    }

    private String convertByType(ResultSet rs, int fdIndex, ResultColumnType colType) throws SQLException {
        if (rs.getObject(fdIndex) == null) {
            return "";
        }
        return rs.getObject(fdIndex).toString();
    }
}

