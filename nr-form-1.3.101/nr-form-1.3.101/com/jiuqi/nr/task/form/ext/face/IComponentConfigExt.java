/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.face;

import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.IBaseConfigExt;
import com.jiuqi.nr.task.form.ext.face.IConfigExtCheck;

public interface IComponentConfigExt
extends IBaseConfigExt,
IConfigExtCheck {
    public ConfigDTO getDefaultConfig();

    public void saveConfig(String var1, ConfigDTO var2);
}

