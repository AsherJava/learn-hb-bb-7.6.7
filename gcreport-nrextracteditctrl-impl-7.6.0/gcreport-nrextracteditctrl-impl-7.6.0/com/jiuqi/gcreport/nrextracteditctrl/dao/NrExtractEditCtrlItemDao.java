/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nrextracteditctrl.dao;

import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlItemEO;
import java.util.List;

public interface NrExtractEditCtrlItemDao {
    public void save(NrExtractEditCtrlItemEO var1);

    public void delete(String var1);

    public List<NrExtractEditCtrlItemEO> queryByEditCtrlConfId(String var1);
}

