/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nrextracteditctrl.dao;

import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlEO;
import java.util.List;

public interface NrExtractEditCtrlDao {
    public void save(NrExtractEditCtrlEO var1);

    public void update(NrExtractEditCtrlEO var1);

    public void delete(String var1);

    public void stop(String var1);

    public void start(String var1);

    public List<NrExtractEditCtrlEO> queryAll();

    public List<NrExtractEditCtrlEO> queryByTaskIdAndSchemeKey(String var1, String var2);
}

