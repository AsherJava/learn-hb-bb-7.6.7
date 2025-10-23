/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.misc.SXElement
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.migration.transferdata.service;

import com.jiuqi.np.sql.misc.SXElement;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;

public interface ITreeTransXmlProcess {
    public static final String XML_TAG_TREE = "tree";
    public static final String XML_PROPERTY_TABLENAME = "tablename";
    public static final String XML_PROPERTY_COUNT = "count";

    public String getName();

    public byte[] getTreeData();

    default public SXElement getRootSXElement(IEntityTable entityTable, String tableName, SXElement root) {
        SXElement rootEle = root.append(XML_TAG_TREE);
        rootEle.setString(XML_PROPERTY_TABLENAME, tableName);
        if (entityTable != null) {
            rootEle.setInt(XML_PROPERTY_COUNT, entityTable.getTotalCount());
        }
        root.append(rootEle);
        return rootEle;
    }
}

