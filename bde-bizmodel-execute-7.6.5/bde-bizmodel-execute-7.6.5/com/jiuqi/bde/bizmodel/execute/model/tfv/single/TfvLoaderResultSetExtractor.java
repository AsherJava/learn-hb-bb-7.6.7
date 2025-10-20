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
package com.jiuqi.bde.bizmodel.execute.model.tfv.single;

import com.jiuqi.bde.bizmodel.execute.model.tfv.utils.TfvExecuteUtil;
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

public class TfvLoaderResultSetExtractor
implements ResultSetExtractor<List<Object>> {
    private ExecuteSettingVO settingVo;

    public TfvLoaderResultSetExtractor(ExecuteSettingVO settingVo) {
        this.settingVo = settingVo;
    }

    public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
        ArrayList<Object> result = new ArrayList<Object>();
        Object val = null;
        while (rs.next()) {
            if (this.settingVo.getFieldDefineType() == null) {
                val = TfvExecuteUtil.getValByResultType(rs);
                result.add(val);
                continue;
            }
            if (rs.getObject(1) == null) {
                val = TfvExecuteUtil.getValByFieldDefineType(this.settingVo);
                result.add(val);
                continue;
            }
            if (BdeCommonUtil.fieldDefineTypeIsNum((Integer)this.settingVo.getFieldDefineType())) {
                if (rs.getObject(1) instanceof BigDecimal || rs.getObject(1) instanceof Integer) {
                    val = rs.getBigDecimal(1);
                } else {
                    try {
                        val = new BigDecimal(rs.getString(1));
                    }
                    catch (Exception e) {
                        throw new BusinessRuntimeException(String.format("\u6307\u6807\u7c7b\u578b\u4e3a\u6570\u503c\u578b\uff0c\u53d6\u6570\u7ed3\u679c\u4e3a%1$s\u578b\uff0c\u6570\u636e\u7c7b\u578b\u4e0d\u5339\u914d\uff0c\u8bf7\u68c0\u67e5", rs.getObject(1).getClass().getName()));
                    }
                }
            } else {
                val = rs.getString(1);
            }
            result.add(val);
        }
        return result;
    }
}

