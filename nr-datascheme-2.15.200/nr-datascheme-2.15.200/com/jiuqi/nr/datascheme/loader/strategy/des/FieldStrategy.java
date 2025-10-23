/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 */
package com.jiuqi.nr.datascheme.loader.strategy.des;

import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.des.DataSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.internal.dao.IDataFieldDao;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.loader.BaseLevelLoader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class FieldStrategy
implements DataSchemeLoaderStrategy {
    @Autowired
    private IDataFieldDao<DesignDataFieldDO> dataFieldDao;
    private final Logger logger = LoggerFactory.getLogger(FieldStrategy.class);
    private static final int FIELD = NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue() | NodeType.TABLE_DIM.getValue();

    FieldStrategy() {
    }

    public boolean matching(int nodeType) {
        return (FIELD & nodeType) != 0;
    }

    public <E> void visitRoot(SchemeNode<E> root, SchemeNodeVisitor<E> schemeNodeVisitor) {
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, SchemeNodeVisitor<E> schemeNodeVisitor, Integer interestType) {
        return null;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, ReverseSchemeNodeVisitor<E> reverse, Integer interestType) {
        List<DesignDataFieldDO> fields;
        DesignDataFieldDO field = this.dataFieldDao.get(next.getKey());
        String tableKey = field.getDataTableKey();
        if (interestType == null) {
            fields = this.dataFieldDao.getByTable(tableKey);
        } else {
            int kind = BaseLevelLoader.nodeType2FieldKind(interestType);
            fields = this.dataFieldDao.getByTableAndKind(tableKey, kind);
        }
        this.logger.trace("\u8bbf\u95ee\u8868\u4e0b\u7684{}\u4e2a\u6307\u6807", (Object)fields.size());
        Object e = reverse.visitFieldNode(next, fields);
        SchemeNode preNode = new SchemeNode(tableKey, NodeType.TABLE.getValue());
        preNode.setOther(e);
        return preNode;
    }
}

