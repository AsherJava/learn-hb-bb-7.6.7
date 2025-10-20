/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 */
package com.jiuqi.gcreport.org.impl.cache.impl;

import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.cache.util.GcOrgCacheUtil;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.util.HashMap;

public class GcOrgParam
extends OrgDTO {
    private static final long serialVersionUID = 1L;
    private OrgTypeVO TypeVo;
    private OrgVersionVO versionVo;

    public GcOrgParam() {
    }

    public GcOrgParam(OrgTypeVO vo, OrgVersionVO ver) {
        this(vo, ver, GcAuthorityType.NONE);
    }

    public GcOrgParam(OrgTypeVO vo, OrgVersionVO ver, GcAuthorityType authType) {
        this.setCategoryname(vo.getName());
        this.setExtInfo(new HashMap());
        this.setSyncOrgBaseInfo(false);
        this.setValidtime(ver.getValidTime());
        this.setVersionDate(ver.getValidTime());
        this.setInvalidtime(ver.getInvalidTime());
        this.setCategoryname(vo.getName());
        this.setStopflag(-1);
        this.setRecoveryflag(-1);
        this.setAuthType(GcOrgCacheUtil.replaceVAuthz(authType));
        this.setForceUpdateHistoryVersionData(true);
        this.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        this.put("ignoreCategoryAdd", true);
        this.TypeVo = vo;
        this.versionVo = ver;
    }

    public OrgTypeVO getTypeVo() {
        return this.TypeVo;
    }

    public OrgVersionVO getVersionVo() {
        return this.versionVo;
    }
}

