/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.face.IFormTypeExt
 */
package com.jiuqi.nr.task.form.ext.face;

import com.jiuqi.nr.task.api.face.IFormTypeExt;
import com.jiuqi.nr.task.form.ext.face.IComponentConfigExt;
import com.jiuqi.nr.task.form.ext.face.IExtendInfo;
import com.jiuqi.nr.task.form.ext.face.ILinkConfigExt;
import com.jiuqi.nr.task.form.ext.face.IRegionConfigExt;

public interface IFormDefineResourceExt {
    public String getCode();

    public String prodLine();

    public String getTitle();

    public double getOrder();

    public IFormTypeExt getFormType();

    public IRegionConfigExt getRegionConfigExt();

    public ILinkConfigExt getLinkConfigExt();

    public IComponentConfigExt getComponentConfigExt();

    public IExtendInfo extendConfig(String var1);
}

