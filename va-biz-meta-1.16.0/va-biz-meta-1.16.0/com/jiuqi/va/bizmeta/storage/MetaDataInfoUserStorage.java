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
public class MetaDataInfoUserStorage
extends AMetaStorage {
    @Override
    protected JTableModel getCreateJTM(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "META_INFO_USER");
        jtm.column("ID").VARCHAR(Integer.valueOf(36)).pkey();
        jtm.column("NAME").VARCHAR(Integer.valueOf(50));
        jtm.column("TITLE").NVARCHAR(Integer.valueOf(50));
        jtm.column("VERSIONNO").LONG();
        jtm.column("ROWVERSION").LONG();
        jtm.column("MODULENAME").VARCHAR(Integer.valueOf(50));
        jtm.column("METATYPE").VARCHAR(Integer.valueOf(50));
        jtm.column("GROUPNAME").VARCHAR(Integer.valueOf(50));
        jtm.column("MODELNAME").VARCHAR(Integer.valueOf(50));
        jtm.column("USERNAME").VARCHAR(Integer.valueOf(50));
        jtm.column("ORGVERSION").LONG();
        jtm.column("METASTATE").INTEGER(new Integer[]{1});
        jtm.column("UNIQUECODE").VARCHAR(Integer.valueOf(100));
        jtm.index("MAIOUR_NE_ME_MO_UM").columns(new String[]{"NAME", "METATYPE", "MODULENAME", "USERNAME"}).unique();
        return jtm;
    }
}

