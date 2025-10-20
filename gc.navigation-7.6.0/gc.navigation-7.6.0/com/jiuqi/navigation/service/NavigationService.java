/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.navigation.service;

import com.jiuqi.navigation.vo.NavigationVO;
import java.util.List;

public interface NavigationService {
    public NavigationVO getNavigationConfigByCode(String var1);

    public NavigationVO saveConfig(NavigationVO var1);

    public List<NavigationVO> getAllPages();

    public NavigationVO updateConfig(NavigationVO var1);

    public void deleteConfig(String var1);
}

