/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.common.paramcheck.internal;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.paramcheck.bean.CheckResult;
import com.jiuqi.nr.common.paramcheck.bean.FixParam;
import com.jiuqi.nr.common.paramcheck.bean.ParamCheckVO;
import com.jiuqi.nr.common.paramcheck.service.ACheckService;
import java.util.List;

public interface CheckManagerService {
    public ACheckService getParamCheckBeanByBeanName(String var1);

    public List<ParamCheckVO> getAllParamCheckService();

    public CheckResult executeParamCheck(String var1) throws JQException;

    public CheckResult executeParamFix(FixParam var1) throws JQException;
}

