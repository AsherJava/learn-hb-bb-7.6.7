/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.param.EntityValue
 *  com.jiuqi.nr.data.common.service.FileWriter
 *  com.jiuqi.nr.fielddatacrud.IFieldQueryInfo
 */
package com.jiuqi.nr.data.text;

import com.jiuqi.nr.data.common.param.EntityValue;
import com.jiuqi.nr.data.common.service.FileWriter;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;

public interface IFieldFileParam
extends IFieldQueryInfo {
    public String getFileName();

    public boolean isExpZip();

    public EntityValue getEntityValue();

    public boolean expBizKey();

    public FileWriter getFileWriter();

    public boolean expFile();

    public int getExportCount();
}

