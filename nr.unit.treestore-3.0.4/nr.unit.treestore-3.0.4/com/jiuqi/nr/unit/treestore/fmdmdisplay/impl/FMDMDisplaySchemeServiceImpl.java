/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.treestore.fmdmdisplay.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeDao;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FMDMDisplaySchemeServiceImpl
implements FMDMDisplaySchemeService {
    @Resource
    private FMDMDisplaySchemeDao dao;
    private static final String SYS_DISPLAY_SCHEME_KEY = "SYS_DEFAULT_DISPLAY_SCHEME";

    @Override
    public FMDMDisplayScheme findDisplayScheme(String key) {
        if (StringUtils.isNotEmpty((String)key)) {
            return this.dao.findByKey(key);
        }
        return null;
    }

    @Override
    public FMDMDisplayScheme findDisplayScheme(String formScheme, String entityId, String owner) {
        return this.dao.findByTaskAndOwner(formScheme, entityId, owner);
    }

    @Override
    public FMDMDisplayScheme getCurrentDisplayScheme(String formScheme, String entityId) {
        FMDMDisplayScheme displayScheme = this.findDisplayScheme(formScheme, entityId, NRSystemUtils.getCurrentUserId());
        if (displayScheme == null) {
            displayScheme = this.findDisplayScheme(formScheme, entityId, "out_of_user_settings");
        }
        return displayScheme;
    }

    @Override
    public FMDMDisplayScheme getCurrentDisplayScheme(String entityId) {
        FMDMDisplayScheme displayScheme = this.findDisplayScheme(SYS_DISPLAY_SCHEME_KEY, entityId, NRSystemUtils.getCurrentUserId());
        if (displayScheme == null) {
            displayScheme = this.findDisplayScheme(SYS_DISPLAY_SCHEME_KEY, entityId, "out_of_user_settings");
        }
        return displayScheme;
    }

    @Override
    public int saveFMDMDisplayScheme(FMDMDisplayScheme scheme) {
        ArrayList<FMDMDisplayScheme> list = new ArrayList<FMDMDisplayScheme>();
        list.add(scheme);
        int[] ints = this.dao.batchInsert(list);
        return ints.length;
    }

    @Override
    public int updateFMDMDisplayScheme(FMDMDisplayScheme scheme) {
        ArrayList<FMDMDisplayScheme> list = new ArrayList<FMDMDisplayScheme>();
        list.add(scheme);
        int[] ints = this.dao.batchUpdate(list);
        return ints.length;
    }
}

