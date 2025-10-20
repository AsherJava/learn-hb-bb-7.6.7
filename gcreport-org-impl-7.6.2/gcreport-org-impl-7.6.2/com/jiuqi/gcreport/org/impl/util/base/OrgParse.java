/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 */
package com.jiuqi.gcreport.org.impl.util.base;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgCacheInnerVO;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.util.GcOrgCacheUtil;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrgParse {
    public static <T> T toGcVO(OrgDO org, Class<T> clzz) {
        if (clzz.isAssignableFrom(GcOrgCacheVO.class)) {
            return (T)OrgParse.toGcCacheVo(org);
        }
        return (T)OrgParse.toGcJsonVo(org);
    }

    public static GcOrgCacheVO toGcCacheVo(OrgDO org) {
        if (org == null) {
            return null;
        }
        GcOrgCacheInnerVO vo = new GcOrgCacheInnerVO();
        for (Map.Entry key : org.entrySet()) {
            Object value = key.getValue();
            if (value instanceof UUID) {
                value = UUIDUtils.toString36((UUID)org.getId());
            }
            if (key.getValue() instanceof ArrayList) {
                vo.addFieldValue(((String)key.getKey()).toUpperCase(), String.join((CharSequence)";", (ArrayList)key.getValue()));
                continue;
            }
            vo.addFieldValue(((String)key.getKey()).toUpperCase(), key.getValue());
        }
        vo.setLeaf(true);
        vo.setId(org.getCode());
        vo.setParentId(org.getParentcode());
        vo.setRealKey(org.getCode());
        vo.setCode(org.getCode());
        vo.setTitle(org.getName());
        vo.setStopFlag(org.getStopflag() == 1);
        vo.setRecoveryFlag(org.getRecoveryflag() == 1);
        vo.setParentStr(org.getParents());
        if (InspectOrgUtils.enableMergeUnit()) {
            vo.setGcParentStr((String)org.get((Object)"GCPARENTS".toLowerCase()));
            Assert.isNotEmpty((String)vo.getGcParentStr(), (String)(vo.getCode() + " \u7684 gcparents \u503c\u4e0d\u80fd\u7a7a"), (Object[])new Object[0]);
            vo.setGcParents(((String)org.get((Object)"GCPARENTS".toLowerCase())).split("/"));
        }
        vo.setParents(org.getParents().split("/"));
        vo.setOrdinal(org.getOrdinal().doubleValue());
        vo.setOrgTypeId(OrgParse.typeOf(org.getValueOf("ORGTYPEID"), String.class));
        vo.setScale(OrgParse.typeOf(org.getValueOf("SPLITSCALE"), BigDecimal.class).doubleValue());
        vo.setSplitId(OrgParse.typeOf(org.getValueOf("SPLITID"), String.class));
        vo.setBblx(OrgParse.typeOf(org.getValueOf("BBLX"), String.class));
        vo.setBaseUnitId(OrgParse.typeOf(org.getValueOf("BASEUNITID"), String.class));
        vo.setDiffUnitId(OrgParse.typeOf(org.getValueOf("DIFFUNITID"), String.class));
        vo.setMergeUnitId(OrgParse.typeOf(org.getExtInfo("MERGEUNITID"), String.class));
        vo.setOrgKind(OrgParse.typeOf(org.getExtInfo("ORGKIND"), GcOrgKindEnum.class));
        vo.setCurrencyId(OrgParse.typeOf(org.getValueOf("CURRENCYID"), String.class));
        Object currcyIds = org.getValueOf("CURRENCYIDS");
        if (currcyIds instanceof ArrayList) {
            vo.setCurrencyIds(String.join((CharSequence)";", (ArrayList)currcyIds));
        }
        if (null != org.getExtInfo()) {
            for (Map.Entry key : org.getExtInfo().entrySet()) {
                Object value = key.getValue();
                if (value instanceof UUID) {
                    value = UUIDUtils.toString36((UUID)org.getId());
                }
                vo.addFieldValue((String)key.getKey(), key.getValue());
            }
        }
        return vo;
    }

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
                vo.setFieldValue(((String)entry.getKey()).toUpperCase(), (Object)String.join((CharSequence)";", (ArrayList)entry.getValue()));
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
        vo.setStopFlag(org.getStopflag() == 1);
        vo.setRecoveryFlag(org.getRecoveryflag() == 1);
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

    public static GcOrgParam toVaJsonVo(OrgToJsonVO org, GcOrgBaseParam param) {
        if (org == null) {
            return null;
        }
        return OrgParamParse.createParam(param, vo -> {
            vo.setForceUpdateHistoryVersionData(true);
            for (Map.Entry key : org.getDatas().entrySet()) {
                Object val = key.getValue();
                if (val == null) continue;
                if (val instanceof Boolean) {
                    val = (Boolean)val != false ? 1 : 0;
                }
                vo.put(((String)key.getKey()).toLowerCase(), val);
            }
        });
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

    public static GcOrgParam toVaJsonVo(GcOrgPublicApiParam param) {
        GcAuthorityType type = GcAuthorityType.ACCESS;
        if ("None".equalsIgnoreCase(param.getAuthType())) {
            type = GcAuthorityType.NONE;
        } else if ("Modify".equalsIgnoreCase(param.getAuthType())) {
            type = GcAuthorityType.MANAGE;
        }
        YearPeriodObject yp = new YearPeriodObject(null, param.getOrgVerCode());
        GcOrgParam vaParam = new GcOrgParam();
        vaParam.setCategoryname(param.getOrgType());
        vaParam.setExtInfo(new HashMap());
        vaParam.setVersionDate(yp.formatYP().getEndDate());
        vaParam.setStopflag(-1);
        vaParam.setRecoveryflag(-1);
        vaParam.setAuthType(GcOrgCacheUtil.replaceVAuthz(type));
        vaParam.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        vaParam.setSearchKey(param.getSearchText());
        vaParam.setParentcode(param.getOrgParentCode());
        if (!StringUtils.isEmpty((String)param.getExpression())) {
            vaParam.put("BI-SYNTAX", null);
            vaParam.setExpression(param.getExpression());
        }
        return vaParam;
    }
}

