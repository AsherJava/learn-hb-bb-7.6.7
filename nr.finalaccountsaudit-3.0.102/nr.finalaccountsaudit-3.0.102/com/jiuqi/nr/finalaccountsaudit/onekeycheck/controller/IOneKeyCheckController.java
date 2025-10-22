/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.onekeycheck.controller;

import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface IOneKeyCheckController {
    public void invokeMultCheck(Map<String, MultCheckItem> var1, OneKeyCheckInfo var2);
}

