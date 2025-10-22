/*
 * Decompiled with CFR 0.152.
 */
package nr.single.data.reg.service;

import java.util.Map;
import nr.single.data.bean.CheckSoftConfig;
import nr.single.data.bean.CheckSoftNode;
import nr.single.data.bean.CheckSoftParam;

public interface SingleSoftAuthService {
    public CheckSoftNode getMachCode(CheckSoftParam var1);

    public CheckSoftNode getCheckParam(CheckSoftParam var1);

    public CheckSoftNode getSoftRegInfo(CheckSoftParam var1);

    public CheckSoftNode getRegEndDate(CheckSoftParam var1);

    public CheckSoftNode checkSoftReg(CheckSoftParam var1);

    public CheckSoftNode doSoftRegister(CheckSoftParam var1);

    public Map<String, Object> readAuthFromConfig(CheckSoftConfig var1);
}

