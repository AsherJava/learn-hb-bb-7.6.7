/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 */
package com.jiuqi.dc.integration.execute.impl.intf;

import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;

public interface IBaseDataConvertHandler {
    public static final String TASK_NAME = "BaseDataConvert";
    public static final String DEFAULT_HANDLER_CODE = "DEFAULT";

    public String getCode();

    public String doConvert(String var1, BaseDataMappingDefineDTO var2);
}

