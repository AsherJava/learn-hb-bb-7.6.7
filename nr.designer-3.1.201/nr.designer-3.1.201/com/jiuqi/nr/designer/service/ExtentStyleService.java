/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.util.ExtentStyle
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.designer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.util.ExtentStyle;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public interface ExtentStyleService {
    public ExtentStyle getFormStyle(String var1, List<IEntityRow> var2, String var3, String var4) throws JsonProcessingException;

    public void saveEntityStyle(byte[] var1, String var2, String var3);

    public ExtentStyle getFormStyle(String var1, List<IEntityRow> var2, byte[] var3, String var4, String var5) throws Exception;

    public String getEntityKey(String var1, String var2, String var3);

    public List<IEntityRow> queryItemData(String var1) throws JQException;
}

