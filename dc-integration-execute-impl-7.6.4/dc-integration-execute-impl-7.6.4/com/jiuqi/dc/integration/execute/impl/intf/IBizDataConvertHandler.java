/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.impl.define.IPluginType
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.dc.integration.execute.impl.intf;

import com.jiuqi.dc.integration.execute.impl.data.DataConvertResult;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import java.util.Map;

public interface IBizDataConvertHandler {
    default public IPluginType getPluginType() {
        return null;
    }

    public String getCode();

    public String getFetchDataType();

    public IDimType getDimType(String var1);

    public String getSettingTemplate(String var1);

    public Map<String, String> getConvertParam(String var1, String var2);

    public DataConvertResult doConvert(String var1, String var2);
}

