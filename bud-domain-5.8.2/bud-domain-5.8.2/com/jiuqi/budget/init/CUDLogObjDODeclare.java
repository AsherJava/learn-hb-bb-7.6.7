/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.init;

import com.jiuqi.budget.init.CUDLogObjDODefine;
import java.time.LocalDateTime;

public interface CUDLogObjDODeclare
extends CUDLogObjDODefine {
    public static final String CREATOR_FIELD = "CREATOR";
    public static final String MODIFIER_FIELD = "MODIFIER";
    public static final String CREATETIME_FIELD = "CREATETIME";
    public static final String MODIFYTIME_FIELD = "MODIFYTIME";
    public static final String SHOW_CODE = "SHOWCODE";

    public void setCreator(String var1);

    public void setCreateTime(LocalDateTime var1);

    public void setModifier(String var1);

    public void setModifyTime(LocalDateTime var1);
}

