/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.datachange.service;

import com.jiuqi.nr.system.check.datachange.bean.UnitInfo;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;

public interface UnitMissInfoService {
    public List<UnitInfo> getMissUnitInfo(String var1);

    public String export(String var1, Sheet var2);
}

