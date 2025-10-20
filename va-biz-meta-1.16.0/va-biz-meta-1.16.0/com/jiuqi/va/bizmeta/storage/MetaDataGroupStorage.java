/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bizmeta.storage;

import com.jiuqi.va.bizmeta.storage.AMetaStorage;
import com.jiuqi.va.mapper.common.JTableModel;
import org.springframework.stereotype.Component;

@Component
public class MetaDataGroupStorage
extends AMetaStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "META_GROUP");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").VARCHAR(Integer.valueOf(50));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(50));
        jtm.column("VERSIONNO").LONG();
        jtm.column("ROWVERSION").LONG();
        jtm.column("MODULENAME").VARCHAR(Integer.valueOf(50));
        jtm.column("METATYPE").VARCHAR(Integer.valueOf(50));
        jtm.column("PARENTNAME").VARCHAR(Integer.valueOf(50));
        jtm.column("UNIQUECODE").VARCHAR(Integer.valueOf(100));
        jtm.index("MAGP_MO_NAME_METATYPE").columns(new String[]{"MODULENAME", "NAME", "METATYPE"}).unique();
        return jtm;
    }
}

