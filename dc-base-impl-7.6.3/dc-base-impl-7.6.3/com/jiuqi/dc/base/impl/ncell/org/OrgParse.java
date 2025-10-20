/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 */
package com.jiuqi.dc.base.impl.ncell.org;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.impl.ncell.org.vo.OrgToJsonVO;
import com.jiuqi.dc.base.impl.ncell.org.vo.OrgTypeVO;
import com.jiuqi.dc.base.impl.ncell.org.vo.OrgVersionVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class OrgParse {
    private static <T> T typeOf(Object value, Class<T> clzz) {
        if (value == null) {
            if (clzz.isAssignableFrom(BigDecimal.class)) {
                return (T)BigDecimal.ZERO;
            }
            if (clzz.isAssignableFrom(Boolean.class)) {
                return (T)Boolean.TRUE;
            }
            return null;
        }
        return clzz.cast(value);
    }

    public static OrgToJsonVO toGcJsonVo(OrgDO org) {
        if (org == null) {
            return null;
        }
        OrgToJsonVO vo = new OrgToJsonVO();
        for (Map.Entry entry : org.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof UUID) {
                value = UUIDUtils.toString36((UUID)org.getId());
            }
            if (entry.getValue() instanceof ArrayList) {
                vo.setFieldValue(((String)entry.getKey()).toUpperCase(), String.join((CharSequence)";", (ArrayList)entry.getValue()));
                continue;
            }
            vo.setFieldValue(((String)entry.getKey()).toUpperCase(), entry.getValue());
        }
        vo.setLeaf(true);
        vo.setId(org.getCode());
        vo.setParentid(org.getParentcode());
        vo.setOrgid(UUIDUtils.toString36((UUID)org.getId()));
        vo.setCode(org.getCode());
        vo.setTitle(org.getName());
        vo.setLabel(org.getName());
        vo.setStopFlag(Integer.valueOf(1).equals(org.getStopflag()));
        vo.setRecoveryFlag(Integer.valueOf(1).equals(org.getRecoveryflag()));
        vo.setSimpletitle(org.getShortname());
        if (null != org.getExtInfo()) {
            for (Map.Entry entry : org.getExtInfo().entrySet()) {
                vo.setFieldValue((String)entry.getKey(), entry.getValue());
            }
        }
        return vo;
    }

    public static OrgTypeVO toGcType(OrgCategoryDO org) {
        if (org == null) {
            return null;
        }
        OrgTypeVO vo = new OrgTypeVO();
        vo.setId(UUIDUtils.toString36((UUID)org.getId()));
        vo.setDescription(org.getRemark());
        vo.setName(org.getName());
        vo.setTable(org.getName());
        vo.setTitle(org.getTitle());
        vo.setExtinfo(org.getExtinfo());
        return vo;
    }

    public static OrgVersionVO toGcVersion(OrgVersionDO org, OrgCategoryDO type) {
        return OrgParse.toGcVersion(org, OrgParse.toGcType(type));
    }

    public static OrgVersionVO toGcVersion(OrgVersionDO org, OrgTypeVO type) {
        if (org == null) {
            return null;
        }
        OrgVersionVO vo = new OrgVersionVO();
        vo.setId(UUIDUtils.toString36((UUID)org.getId()));
        vo.setName(org.getTitle());
        vo.setTitle(org.getTitle());
        vo.setValidTime(org.getValidtime());
        vo.setInvalidTime(org.getInvalidtime());
        vo.setOrgType(type);
        return vo;
    }

    public static OrgCategoryDO toVaType(OrgTypeVO org) {
        if (org == null) {
            return null;
        }
        OrgCategoryDO vo = new OrgCategoryDO();
        vo.setId(UUIDUtils.fromString36((String)org.getId()));
        vo.setRemark(org.getDescription());
        vo.setName(org.getName());
        vo.setTitle(org.getTitle());
        vo.setExtinfo(org.getExtinfo());
        vo.setVersionflag(Integer.valueOf(1));
        return vo;
    }

    public static OrgVersionDTO toVaVersion(OrgVersionVO org) {
        if (org == null) {
            return null;
        }
        OrgVersionDTO vo = new OrgVersionDTO();
        vo.setId(UUIDUtils.fromString36((String)org.getId()));
        vo.setInvalidtime(org.getInvalidTime());
        vo.setTitle(org.getTitle());
        vo.setValidtime(org.getValidTime());
        vo.setCategoryname(org.getOrgType().getName());
        return vo;
    }

    public static OrgVersionVO newVaOrgVersion(OrgVersionVO baseVer, Date createDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        OrgVersionVO impl = new OrgVersionVO();
        impl.setId(UUID.randomUUID().toString());
        impl.setTitle(df.format(createDate) + "\u7248\u672c");
        impl.setValidTime(createDate);
        impl.setOrgType(baseVer.getOrgType());
        impl.setInvalidTime(baseVer.getInvalidTime());
        return impl;
    }
}

