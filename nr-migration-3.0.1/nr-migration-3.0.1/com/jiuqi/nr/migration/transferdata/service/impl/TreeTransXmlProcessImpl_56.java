/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.misc.SXElement
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.migration.transferdata.service.impl;

import com.jiuqi.np.sql.misc.SXElement;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.migration.transferdata.service.ITreeTransXmlProcess;
import java.util.List;
import org.springframework.util.StringUtils;

public class TreeTransXmlProcessImpl_56
implements ITreeTransXmlProcess {
    private final String tableName;
    private final IEntityTable entityTable;
    public static final String XML_TAG_NODE = "node";
    public static final String XML_PROPERTY_CODE_56 = "code";
    public static final String XML_PROPERTY_NAME_56 = "name";
    public static final String XML_PROPERTY_PARENTCODE_56 = "parentcode";

    public TreeTransXmlProcessImpl_56(IEntityTable entityTable, String tableName) {
        this.entityTable = entityTable;
        this.tableName = tableName;
    }

    @Override
    public String getName() {
        return "0_" + this.tableName + "\u7248\u672c\u6811.xml";
    }

    @Override
    public byte[] getTreeData() {
        SXElement root = SXElement.newDoc();
        SXElement treeEle = this.getRootSXElement(this.entityTable, this.tableName, root);
        this.appendNodeSXElement(this.entityTable, treeEle);
        return root.toUTF8();
    }

    private void appendNodeSXElement(IEntityTable entityTable, SXElement treeEle) {
        if (entityTable != null) {
            List rootRows = entityTable.getRootRows();
            if (rootRows == null || rootRows.isEmpty()) {
                return;
            }
            for (IEntityRow entityRow : rootRows) {
                this.recursionGetNode(entityTable, entityRow, treeEle, "");
            }
        }
    }

    private SXElement getNode(IEntityRow entityRow, String parentCode) {
        SXElement sxeRoot = SXElement.newElement((String)XML_TAG_NODE);
        sxeRoot.setAttribute(XML_PROPERTY_CODE_56, entityRow.getCode());
        sxeRoot.setAttribute(XML_PROPERTY_NAME_56, entityRow.getTitle());
        if (StringUtils.hasLength(parentCode)) {
            sxeRoot.setAttrWithEmptyStr(XML_PROPERTY_PARENTCODE_56, parentCode);
        }
        return sxeRoot;
    }

    private void recursionGetNode(IEntityTable entityTable, IEntityRow entityRow, SXElement parentNodeSx, String parentCode) {
        SXElement curNode = this.getNode(entityRow, parentCode);
        parentNodeSx.append(curNode);
        List childRows = entityTable.getChildRows(entityRow.getEntityKeyData());
        if (childRows == null || childRows.isEmpty()) {
            return;
        }
        for (IEntityRow childRow : childRows) {
            this.recursionGetNode(entityTable, childRow, curNode, childRow.getCode());
        }
    }
}

