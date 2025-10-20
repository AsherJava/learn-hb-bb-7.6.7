/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nvwa.subsystem.core.manage.IParamLevelManager;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServeCodeService {
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private IParamLevelManager iParamLevelManager;

    public String getServeCode() throws JQException {
        return "0";
    }

    public boolean isSameServeCode(String serveCode) throws JQException {
        if (StringUtils.isEmpty((String)serveCode) || !this.iParamLevelManager.isOpenParamLevel()) {
            return true;
        }
        return serveCode.equals("0");
    }
}

