/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.update.dao;

import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.internal.db.TransUtil;
import com.jiuqi.nr.definition.internal.update.UpdateDl;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateDlDao
extends BaseDao {
    public Class<?> getClz() {
        return UpdateDl.class;
    }

    public Class<?> getExternalTransCls() {
        return TransUtil.class;
    }

    public List<UpdateDl> list() throws Exception {
        String sql = "SELECT DL_KEY ,DL_EXPRESSION , FD_FRACTION_DIGITS,FD_TYPE,FD_SHOW_FORMAT FROM NR_PARAM_DATALINK INNER JOIN SYS_FIELDDEFINE ON DL_EXPRESSION = FD_KEY WHERE DL_TYPE = 1 AND (FD_TYPE = 1 OR FD_TYPE = 8) AND FD_SHOW_FORMAT IS NOT NULL";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            String dlKey = rs.getString("DL_KEY");
            int fractionDigits = rs.getInt("FD_FRACTION_DIGITS");
            String fdShowFormat = rs.getString("FD_SHOW_FORMAT");
            UpdateDl updateDl = new UpdateDl();
            updateDl.setKey(dlKey);
            updateDl.setFractionDigits(fractionDigits);
            updateDl.setShowFormat(fdShowFormat);
            return updateDl;
        });
    }
}

