/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.nr.zb.scheme.internal.dao.IZbSchemeVersionDao;
import com.jiuqi.nr.zb.scheme.internal.dao.impl.ZbSchemeBaseDao;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbSchemeVersionDO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ZbSchemeVersionDAO
extends ZbSchemeBaseDao<ZbSchemeVersionDO>
implements IZbSchemeVersionDao {
    @Override
    public Class<ZbSchemeVersionDO> getClz() {
        return ZbSchemeVersionDO.class;
    }

    @Override
    public ZbSchemeVersionDO getBySchemeAndPeriod(String schemeKey, String period) {
        return super.getBy("ZV_SCHEME_KEY=? and ZV_START_PERIOD<=? and ZV_END_PERIOD>?", new Object[]{schemeKey, period, period}, ZbSchemeVersionDO.class);
    }

    @Override
    public List<ZbSchemeVersionDO> listByScheme(String scheme) {
        return this.list(new String[]{"schemeKey"}, (Object[])new String[]{scheme}, ZbSchemeVersionDO.class);
    }

    @Override
    public void deleteByScheme(String scheme) throws Exception {
        super.deleteBy(new String[]{"schemeKey"}, new Object[]{scheme});
    }

    @Override
    public void deleteByKey(String key) {
        super.delete(key);
    }

    @Override
    public int getReferNum(String key) {
        Integer query = (Integer)this.jdbcTemplate.query("SELECT COUNT(*) AS NUM FROM NR_DATASCHEME_SCHEME_DES WHERE DS_ZS_VERSION=?", rs -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[]{key});
        return query != null ? query : 0;
    }
}

