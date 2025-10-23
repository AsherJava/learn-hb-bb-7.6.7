/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.internal.dao.impl;

import com.jiuqi.nr.zb.scheme.internal.dao.IZbGroupDao;
import com.jiuqi.nr.zb.scheme.internal.dao.impl.ZbSchemeBaseDao;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbGroupDO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ZbGroupDAO
extends ZbSchemeBaseDao<ZbGroupDO>
implements IZbGroupDao {
    private static final String SCHEME_KEY = "schemeKey";
    private static final String VERSION_KEY = "versionKey";
    private static final String PARENT_KEY = "parentKey";

    @Override
    public Class<ZbGroupDO> getClz() {
        return ZbGroupDO.class;
    }

    @Override
    public List<ZbGroupDO> listBySchemeAndVersion(String schemeKey, String versionKey) {
        return super.list(new String[]{SCHEME_KEY, VERSION_KEY}, new Object[]{schemeKey, versionKey}, this.getClz());
    }

    @Override
    public List<ZbGroupDO> listByVersionAndParent(String versionKey, String parentKey) {
        return super.list(new String[]{VERSION_KEY, PARENT_KEY}, new Object[]{versionKey, parentKey}, this.getClz());
    }

    @Override
    public void deleteByVersion(String versionKey) {
        super.deleteBy(new String[]{VERSION_KEY}, new Object[]{versionKey});
    }

    @Override
    public void delete(String key) {
        super.delete(key);
    }

    @Override
    public List<ZbGroupDO> listByParent(String parent) {
        return super.list(new String[]{PARENT_KEY}, new Object[]{parent}, this.getClz());
    }

    @Override
    public List<ZbGroupDO> listByVersion(String versionKey) {
        return super.list(new String[]{VERSION_KEY}, new Object[]{versionKey}, this.getClz());
    }

    @Override
    public List<ZbGroupDO> listByScheme(String schemeKey) {
        return super.list(new String[]{SCHEME_KEY}, new Object[]{schemeKey}, this.getClz());
    }

    @Override
    public List<ZbGroupDO> listByKeys(List<String> keys) {
        StringBuilder sbr = new StringBuilder();
        sbr.setLength(0);
        sbr.append("ZG_KEY").append(" IN (").append(keys.stream().map(s -> "?").collect(Collectors.joining(","))).append(")");
        return super.list(sbr.toString(), keys.toArray(), ZbGroupDO.class);
    }

    @Override
    public void deleteByScheme(String schemeKey) {
        super.deleteBy(new String[]{SCHEME_KEY}, new Object[]{schemeKey});
    }
}

