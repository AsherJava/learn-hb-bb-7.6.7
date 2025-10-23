/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 */
package com.jiuqi.nr.datascheme.loader.strategy.run;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.run.RunTimeSchemeLoaderStrategy;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.loader.BaseLevelLoader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class RunFieldStrategy
implements RunTimeSchemeLoaderStrategy {
    @Autowired
    private IRuntimeDataSchemeService service;
    private final Logger logger = LoggerFactory.getLogger(RunFieldStrategy.class);
    private final int FIELD = NodeType.FIELD_ZB.getValue() | NodeType.FIELD.getValue() | NodeType.TABLE_DIM.getValue();

    RunFieldStrategy() {
    }

    public boolean matching(int nodeType) {
        return (this.FIELD & nodeType) != 0;
    }

    public <E> void visitRoot(SchemeNode<E> root, RuntimeSchemeVisitor<E> visitor) {
    }

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> next, RuntimeSchemeVisitor<E> visitor, Integer interestType) {
        return null;
    }

    public <E> SchemeNode<E> visitNode(SchemeNode<E> next, RuntimeReverseSchemeVisitor<E> reverse, Integer interestType) {
        List fields;
        DataField field = this.service.getDataField(next.getKey());
        String tableKey = field.getDataTableKey();
        if (interestType == null) {
            fields = this.service.getDataFieldByTable(tableKey);
        } else {
            int kind = BaseLevelLoader.nodeType2FieldKind(interestType);
            DataFieldKind[] dataFieldKinds = DataFieldKind.interestType((int)kind);
            fields = this.service.getDataFieldByTableKeyAndKind(tableKey, dataFieldKinds);
        }
        this.logger.trace("\u8bbf\u95ee\u8868\u4e0b\u7684{}\u4e2a\u6307\u6807", (Object)fields.size());
        Object e = reverse.visitFieldNode(next, fields);
        SchemeNode preNode = new SchemeNode(tableKey, NodeType.TABLE.getValue());
        preNode.setOther(e);
        return preNode;
    }
}

