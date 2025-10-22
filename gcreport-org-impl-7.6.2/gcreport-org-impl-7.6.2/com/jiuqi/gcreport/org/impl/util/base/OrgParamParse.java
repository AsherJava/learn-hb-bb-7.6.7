/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 */
package com.jiuqi.gcreport.org.impl.util.base;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.base.GcOrgParamInterface;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import java.util.Date;

public class OrgParamParse {
    public static String formtOrgParentCode(String parantCode) {
        return StringUtils.isEmpty((String)parantCode) ? "-" : parantCode;
    }

    public static GcOrgParam createParam(GcOrgBaseParam bp, GcOrgParamInterface<GcOrgParam> setter) {
        GcOrgParam param = null;
        if (bp == null) {
            param = OrgParamParse.createBaseOrgParam(setter);
        } else {
            param = OrgParamParse.createDefaultParam(bp.getType(), bp.getVersion(), bp.getAuthType(), setter);
            bp.initQueryParam(param);
        }
        return param;
    }

    public static GcOrgParam createDefaultParam(String orgType, GcOrgParamInterface<GcOrgParam> setter) {
        return OrgParamParse.createDefaultParam(orgType, new Date(), GcAuthorityType.NONE, setter);
    }

    public static GcOrgParam createDefaultParam(String orgType, Date verDate, GcOrgParamInterface<GcOrgParam> setter) {
        return OrgParamParse.createDefaultParam(orgType, verDate, GcAuthorityType.NONE, setter);
    }

    public static GcOrgParam createDefaultParam(String orgType, Date verDate, GcAuthorityType authType, GcOrgParamInterface<GcOrgParam> setter) {
        OrgTypeVO type = GcOrgTypeTool.getInstance().getOrgTypeByName(orgType);
        OrgVersionVO ver = GcOrgVerTool.getInstance().getOrgVersionByDate(type.getName(), verDate);
        return OrgParamParse.createDefaultParam(type, ver, authType, setter);
    }

    public static GcOrgParam createDefaultParam(OrgTypeVO type, OrgVersionVO ver, GcAuthorityType authType, GcOrgParamInterface<GcOrgParam> setter) {
        GcOrgParam param = new GcOrgParam(type, ver, authType);
        setter.initParam(param);
        return param;
    }

    public static GcOrgParam createBaseOrgParam(GcOrgParamInterface<GcOrgParam> setter) {
        return OrgParamParse.createDefaultParam("MD_ORG", new Date(), setter);
    }

    public static OrgCategoryDO createOrgTypeParam(GcOrgParamInterface<OrgCategoryDO> setter) {
        OrgCategoryDO param = new OrgCategoryDO();
        setter.initParam(param);
        return param;
    }

    public static OrgVersionDTO createOrgVerParam(GcOrgParamInterface<OrgVersionDTO> setter) {
        OrgVersionDTO param = new OrgVersionDTO();
        setter.initParam(param);
        return param;
    }
}

