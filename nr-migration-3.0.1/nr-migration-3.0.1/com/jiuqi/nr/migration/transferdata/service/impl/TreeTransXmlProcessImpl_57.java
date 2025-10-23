/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.misc.SXElement
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 */
package com.jiuqi.nr.migration.transferdata.service.impl;

import com.jiuqi.np.sql.misc.SXElement;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.migration.transferdata.common.DataTransUtil;
import com.jiuqi.nr.migration.transferdata.common.TransferUtils;
import com.jiuqi.nr.migration.transferdata.service.ITreeTransXmlProcess;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class TreeTransXmlProcessImpl_57
implements ITreeTransXmlProcess {
    private final String tableName;
    private final IEntityTable entityTable;
    public static final String treeKey = "0_Tree.xml";
    public static final String XML_TAG_NODE = "node";
    public static final String XML_PROPERTY_CODE = "stdCode";
    public static final String XML_PROPERTY_NAME = "stdName";
    public static final String XML_PROPERTY_PARENTCODE = "parentCode";

    public TreeTransXmlProcessImpl_57(IEntityTable entityTable, String tableName) {
        this.tableName = tableName;
        this.entityTable = entityTable;
    }

    @Override
    public String getName() {
        return treeKey;
    }

    @Override
    public byte[] getTreeData() {
        SXElement root = SXElement.newDoc();
        SXElement treeEle = this.getRootSXElement(this.entityTable, this.tableName, root);
        this.appendNode(this.entityTable, treeEle);
        return root.toUTF8();
    }

    private void appendNode(IEntityTable entityTable, SXElement treeEle) {
        if (entityTable == null) {
            return;
        }
        List rootRows = entityTable.getRootRows();
        if (rootRows == null || rootRows.isEmpty()) {
            return;
        }
        for (IEntityRow entityRow : rootRows) {
            this.recursionGetNode(entityTable, entityRow, treeEle, "");
        }
    }

    private void recursionGetNode(IEntityTable entityTable, IEntityRow entityRow, SXElement treeEle, String parentCode) {
        SXElement node = this.getNode(entityRow, parentCode, this.getBaseDataProperty(entityTable, entityRow, entityRow.getCode()));
        treeEle.append(node);
        List childRows = entityTable.getChildRows(entityRow.getEntityKeyData());
        if (childRows == null || childRows.isEmpty()) {
            return;
        }
        for (IEntityRow child : childRows) {
            this.recursionGetNode(entityTable, child, node, entityRow.getCode());
        }
    }

    private Map<String, String> getBaseDataProperty(IEntityTable entityTable, IEntityRow entityRow, String code) {
        HashMap<String, String> bdProperty = new HashMap<String, String>();
        if (StringUtils.hasLength(code) && entityRow != null) {
            Iterator attributes = entityTable.getEntityModel().getAttributes();
            while (attributes.hasNext()) {
                IEntityAttribute entityAttribute = (IEntityAttribute)attributes.next();
                String pkey = entityAttribute.getCode();
                AbstractData abstractData = entityRow.getValue(pkey);
                String commonZbValue = DataTransUtil.getFieldValue(abstractData);
                if (commonZbValue == null) {
                    bdProperty.put(pkey, "");
                    continue;
                }
                bdProperty.put(pkey, commonZbValue.toLowerCase());
            }
        }
        return bdProperty;
    }

    private SXElement getNode(IEntityRow entityRow, String parentCode, Map<String, String> baseDataKV) {
        SXElement sxeRoot = SXElement.newElement((String)XML_TAG_NODE);
        sxeRoot.setString(XML_PROPERTY_CODE, entityRow.getCode());
        sxeRoot.setString(XML_PROPERTY_NAME, entityRow.getTitle());
        if (StringUtils.hasLength(parentCode)) {
            sxeRoot.setString(XML_PROPERTY_PARENTCODE, parentCode);
        }
        for (Map.Entry<String, String> entries : baseDataKV.entrySet()) {
            String attrName = entries.getKey();
            if (TransferUtils.isNrSysField(attrName) || XML_PROPERTY_CODE.equals(attrName) || XML_PROPERTY_NAME.equals(attrName) || XML_PROPERTY_PARENTCODE.equals(attrName)) continue;
            sxeRoot.setAttrWithEmptyStr(attrName, !StringUtils.hasLength(entries.getValue()) || "null".equals(entries.getValue()) ? "" : entries.getValue());
        }
        return sxeRoot;
    }
}

