/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.ini.Ini
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 */
package nr.single.map.param.service;

import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.ini.Ini;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;

public interface SingleParamFileService {
    public FMRepInfo getSingleFMDMInfo(String var1, String var2);

    public RepInfo getSingleReportInfo(String var1, String var2, String var3);

    public ParaInfo getSingleTaskInfo(String var1, String var2);

    public byte[] getSingleParaFile(String var1, String var2, String var3);

    public Ini getSingleParaIniFile(String var1, String var2, String var3);

    public void updateSingleParaIniFile(String var1, String var2, String var3, Ini var4);

    public void updateSingleParaIniFile(String var1, String var2, String var3, byte[] var4);

    public void updateSingleParaIniFile(String var1, String var2, String var3, byte[] var4, String var5);
}

