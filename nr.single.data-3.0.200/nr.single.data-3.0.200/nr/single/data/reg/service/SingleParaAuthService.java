/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.single.core.para.ini.Iini
 */
package nr.single.data.reg.service;

import com.jiuqi.nr.single.core.para.ini.Iini;
import java.util.Map;
import nr.single.data.bean.CheckSoftNode;
import nr.single.data.bean.CheckSoftParam;

public interface SingleParaAuthService {
    public CheckSoftNode checktaskparam(String var1, String var2, CheckSoftParam var3);

    public void checkTaskIni(String var1, String var2, CheckSoftNode var3, String var4, Iini var5, Map<String, Object> var6);

    public String readInvidTaskFlagByData(byte[] var1, String var2, String var3);
}

