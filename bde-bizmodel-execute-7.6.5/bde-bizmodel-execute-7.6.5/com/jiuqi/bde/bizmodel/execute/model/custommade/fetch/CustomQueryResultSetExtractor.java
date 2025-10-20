/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.ExecuteSettingVO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch;

import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.BusinessRuntimeException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

class CustomQueryResultSetExtractor
implements ResultSetExtractor<List<Object>> {
    private ExecuteSettingVO executeSettingVO;

    public CustomQueryResultSetExtractor(ExecuteSettingVO executeSettingVO) {
        this.executeSettingVO = executeSettingVO;
    }

    public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<Object> result = new ArrayList<Object>();
        Object val = null;
        switch (rs.getMetaData().getColumnType(1)) {
            case 2: 
            case 3: 
            case 4: 
            case 6: 
            case 8: {
                val = BigDecimal.ZERO;
                break;
            }
            default: {
                val = "";
            }
        }
        while (rs.next()) {
            if (this.executeSettingVO.getFieldDefineType() == null) {
                if (rs.getObject(1) instanceof BigDecimal || rs.getObject(1) instanceof Integer) {
                    val = rs.getObject(1) == null ? BigDecimal.ZERO : rs.getBigDecimal(1);
                    continue;
                }
                val = rs.getString(1);
                continue;
            }
            if (BdeCommonUtil.fieldDefineTypeIsNum((Integer)this.executeSettingVO.getFieldDefineType())) {
                if (rs.getObject(1) == null) continue;
                if (rs.getObject(1) instanceof BigDecimal || rs.getObject(1) instanceof Integer || rs.getObject(1) instanceof Long) {
                    val = rs.getBigDecimal(1);
                    continue;
                }
                try {
                    val = new BigDecimal(rs.getString(1));
                    continue;
                }
                catch (SQLException e) {
                    throw new BusinessRuntimeException(String.format("\u6307\u6807\u3010%1$s\u3011\u53d6\u6570\u7ed3\u679c\u4ec5\u652f\u6301\u6570\u503c\u578b", this.executeSettingVO.getFieldDefineId()));
                }
            }
            val = rs.getString(1);
        }
        result.add(val);
        return result;
    }
}

