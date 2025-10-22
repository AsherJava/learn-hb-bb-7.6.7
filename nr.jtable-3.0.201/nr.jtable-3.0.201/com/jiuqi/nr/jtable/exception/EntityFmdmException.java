/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.exception;

import com.jiuqi.nr.jtable.exception.JTableException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;

public class EntityFmdmException
extends JTableException {
    public EntityFmdmException(String[] datas) {
        super(JtableExceptionCodeCost.ENTITY_FMDM_PARENTID_ERROR, datas);
    }

    public EntityFmdmException(String errCode, String[] datas) {
        super(errCode, datas);
    }
}

