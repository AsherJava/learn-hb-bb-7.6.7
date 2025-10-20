/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.extend.BaseDataDefineDesignExtend
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.common;

import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.extend.BaseDataDefineDesignExtend;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BaseDataDefineExtendUtil {
    private static Map<String, BaseDataDefineDesignExtend> defineDesignExtendMap;
    private static List<BaseDataDefineDesignExtend> defineDesignExtends;

    public static List<BaseDataDefineDesignExtend> getDefineDesignExtends() {
        if (defineDesignExtends == null) {
            BaseDataDefineExtendUtil.initDefineDesignExtends();
        }
        return defineDesignExtends;
    }

    public static void initDefineDesignExtends() {
        if (defineDesignExtendMap == null) {
            defineDesignExtendMap = ApplicationContextRegister.getBeansOfType(BaseDataDefineDesignExtend.class);
        }
        defineDesignExtends = new ArrayList<BaseDataDefineDesignExtend>();
        if (defineDesignExtendMap != null && !defineDesignExtendMap.isEmpty()) {
            defineDesignExtends.addAll(defineDesignExtendMap.values());
            Collections.sort(defineDesignExtends, (o1, o2) -> {
                if (o1.getOrderNum() < o2.getOrderNum()) {
                    return -1;
                }
                if (o1.getOrderNum() > o2.getOrderNum()) {
                    return 1;
                }
                return 0;
            });
        }
    }

    public static void clone(BaseDataDefineDO target, BaseDataDefineDO source) {
        target.setActauthflag(source.getActauthflag());
        target.setAuthflag(source.getAuthflag());
        target.setCachedisabled(source.getCachedisabled());
        target.setCreator(source.getCreator());
        target.setDefine(source.getDefine());
        target.setDesignname(source.getDesignname());
        target.setDummyflag(source.getDummyflag());
        target.setExtdata(source.getExtdata());
        target.setGroupfieldname(source.getGroupfieldname());
        target.setGroupname(source.getGroupname());
        target.setId(source.getId());
        target.setLevelcode(source.getLevelcode());
        target.setModifytime(source.getModifytime());
        target.setName(source.getName());
        target.setOrdernum(source.getOrdernum());
        target.setReadonly(source.getReadonly());
        target.setRemark(source.getRemark());
        target.setSharefieldname(source.getSharefieldname());
        target.setSharetype(source.getSharetype());
        target.setShowtype(source.getShowtype());
        target.setSolidifyflag(source.getSolidifyflag());
        target.setStructtype(source.getStructtype());
        target.setTitle(source.getTitle());
        target.setTenantName(source.getTenantName());
        target.setVersionflag(source.getVersionflag());
    }
}

