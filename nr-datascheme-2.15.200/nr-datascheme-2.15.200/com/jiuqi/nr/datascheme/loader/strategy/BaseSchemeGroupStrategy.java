/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 */
package com.jiuqi.nr.datascheme.loader.strategy;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataSchemeDao;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseSchemeGroupStrategy {
    @Autowired
    protected IDataGroupDao<DesignDataGroupDO> dataGroupDao;
    @Autowired
    protected IDataSchemeDao<DesignDataSchemeDO> dataSchemeDao;
    @Autowired
    protected IDataSchemeDao<DataSchemeDO> runDataSchemeDao;
    protected static final int DEFAULT_INTEREST_TYPE = NodeType.SCHEME_GROUP.getValue() | NodeType.SCHEME.getValue();

    public boolean matching(int nodeType) {
        return NodeType.SCHEME_GROUP.getValue() == nodeType;
    }

    protected <E> DesignDataGroup getDataGroupDO(SchemeNode<E> root) {
        DesignDataGroupDO dataGroupDO;
        String key = root.getKey();
        if ("00000000-0000-0000-0000-000000000000".equals(key)) {
            dataGroupDO = new DesignDataGroupDO();
            dataGroupDO.setKey(key);
            dataGroupDO.setTitle("\u5168\u90e8\u6570\u636e\u65b9\u6848");
            dataGroupDO.setDataGroupKind(DataGroupKind.SCHEME_GROUP);
        } else if ("00000000-0000-0000-0000-111111111111".equals(key)) {
            dataGroupDO = new DesignDataGroupDO();
            dataGroupDO.setKey(key);
            dataGroupDO.setTitle("\u5168\u90e8\u67e5\u8be2\u6570\u636e\u65b9\u6848");
            dataGroupDO.setDataGroupKind(DataGroupKind.SCHEME_GROUP);
        } else {
            dataGroupDO = this.dataGroupDao.get(key);
        }
        return dataGroupDO;
    }

    protected <E> List<DesignDataGroupDO> getGroups(SchemeNode<E> next, Integer interestType) {
        String key = next.getKey();
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        List<DesignDataGroupDO> groups = Collections.emptyList();
        if ((NodeType.SCHEME_GROUP.getValue() & interestType) != 0) {
            groups = this.dataGroupDao.getByParent(key);
        }
        return groups;
    }

    protected <E> List<DesignDataSchemeDO> getSchemes(SchemeNode<E> next, Integer interestType) {
        String key = next.getKey();
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        List<DesignDataSchemeDO> schemes = Collections.emptyList();
        if ((NodeType.SCHEME.getValue() & interestType) != 0) {
            schemes = this.dataSchemeDao.getByParent(key);
        }
        return schemes;
    }

    protected <E> List<DataSchemeDO> getRunSchemes(SchemeNode<E> next, Integer interestType) {
        String key = next.getKey();
        if (interestType == null) {
            interestType = DEFAULT_INTEREST_TYPE;
        }
        List<DataSchemeDO> schemes = Collections.emptyList();
        if ((NodeType.SCHEME.getValue() & interestType) != 0) {
            schemes = this.runDataSchemeDao.getByParent(key);
        }
        return schemes;
    }
}

