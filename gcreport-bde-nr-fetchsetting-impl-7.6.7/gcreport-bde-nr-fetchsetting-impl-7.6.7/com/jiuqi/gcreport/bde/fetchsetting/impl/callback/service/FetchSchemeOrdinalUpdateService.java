/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSchemeEO
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.callback.service;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSchemeEO;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FetchSchemeOrdinalUpdateService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor={Exception.class})
    public void doUpdate() {
        List<FetchSchemeEO> fetchSchemeList = this.listFetchScheme();
        if (CollectionUtils.isEmpty(fetchSchemeList)) {
            return;
        }
        Map<String, List<FetchSchemeEO>> fetchSchemeListByFormScheme = fetchSchemeList.stream().collect(Collectors.groupingBy(FetchSchemeEO::getFormSchemeId));
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (Map.Entry<String, List<FetchSchemeEO>> entry : fetchSchemeListByFormScheme.entrySet()) {
            int i = 0;
            for (FetchSchemeEO fetchSchemeEO : entry.getValue()) {
                batchArgs.add(new Object[]{new BigDecimal(i++), fetchSchemeEO.getId()});
            }
        }
        this.batchUpdateOrdinal(batchArgs);
    }

    private void batchUpdateOrdinal(List<Object[]> batchArgs) {
        if (CollectionUtils.isEmpty(batchArgs)) {
            return;
        }
        String sql = "update  BDE_FETCHSCHEME set ORDINAL = ?  where id = ? ";
        this.jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    private List<FetchSchemeEO> listFetchScheme() {
        String sql = "  SELECT ID, FORMSCHEMEID \n  FROM BDE_FETCHSCHEME  fs \n WHERE 1 = 1 AND BIZTYPE = 'NR' \n";
        return this.jdbcTemplate.query(sql, (rs, row) -> this.getFetchSchemeEO(rs), new Object[0]);
    }

    private FetchSchemeEO getFetchSchemeEO(ResultSet rs) throws SQLException {
        FetchSchemeEO eo = new FetchSchemeEO();
        eo.setId(rs.getString(1));
        eo.setFormSchemeId(rs.getString(2));
        return eo;
    }
}

