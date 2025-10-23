/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.service;

import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.List;

public interface ICommonFMDMAttributeService {
    public List<IFMDMAttribute> list(FMDMAttributeDTO var1);

    public List<IFMDMAttribute> listShowAttribute(FMDMAttributeDTO var1);

    public IFMDMAttribute queryByCode(FMDMAttributeDTO var1);

    public IFMDMAttribute queryByZbKey(FMDMAttributeDTO var1);

    public IFMDMAttribute getFMDMParentField(FMDMAttributeDTO var1);

    public IFMDMAttribute getFMDMBizField(FMDMAttributeDTO var1);

    public IFMDMAttribute getFMDMCodeField(FMDMAttributeDTO var1);

    public IFMDMAttribute getFMDMTitleField(FMDMAttributeDTO var1);
}

