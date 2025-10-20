/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 */
package com.jiuqi.gcreport.org.impl.cache.service.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.base.GcOrgParamInterface;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgQueryDao;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgQueryService;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgServiceBase;
import com.jiuqi.gcreport.org.impl.cache.util.GcOrgCacheUtil;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class GcOrgQueryServiceImpl
extends GcOrgServiceBase
implements FGcOrgQueryService<GcOrgCacheVO> {
    @Autowired
    private FGcOrgQueryDao orgDao;

    @Override
    public OrgTypeVO getOrgType(String name) {
        return this.orgDao.getOrgType(name);
    }

    @Override
    public OrgVersionVO getOrgVersion(OrgTypeVO type, Date time) {
        return this.orgDao.getOrgVersion(type, time);
    }

    @Override
    public int getOrgCodeLength() {
        return this.orgDao.getOrgCodeLength();
    }

    @Override
    public List<OrgVersionVO> listOrgVersion(OrgTypeVO type) {
        return this.orgDao.listOrgVersion(type);
    }

    @Override
    public Map<String, Object> getTableDetail(String tableName, String id) {
        return this.orgDao.getTableDetail(tableName, id);
    }

    @Override
    public List<GcOrgCacheVO> getOrgTree(GcOrgBaseParam param, String parentCode) {
        List<GcOrgCacheVO> list = this.orgDao.listSubordinate(OrgParamParse.createParam(param, v -> v.setCode(parentCode)), GcOrgCacheVO.class);
        if (list != null && list.size() > 0) {
            return this.collectionToCacheTree(list);
        }
        return null;
    }

    @Override
    public GcOrgCacheVO getByCode(GcOrgBaseParam param, String code) {
        return this.orgDao.get(OrgParamParse.createParam(param, v -> v.setCode(code)), GcOrgCacheVO.class);
    }

    @Override
    public GcOrgCacheVO getById(GcOrgBaseParam param, String id) {
        return this.orgDao.get(OrgParamParse.createParam(param, v -> v.setId(UUIDUtils.fromString36((String)id))), GcOrgCacheVO.class);
    }

    @Override
    public List<GcOrgCacheVO> list(GcOrgBaseParam param, String searchText) {
        return this.orgDao.list(OrgParamParse.createParam(param, v -> v.setSearchKey(searchText)), GcOrgCacheVO.class);
    }

    @Override
    public List<GcOrgCacheVO> list(GcOrgBaseParam param, String searchText, String parentCode) {
        return this.orgDao.list(OrgParamParse.createParam(param, v -> {
            v.setSearchKey(searchText);
            v.setCode(parentCode);
            v.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
        }), GcOrgCacheVO.class);
    }

    @Override
    public List<GcOrgCacheVO> listByParam(GcOrgBaseParam param, GcOrgParamInterface<GcOrgParam> setter) {
        return this.orgDao.list(OrgParamParse.createParam(param, setter), GcOrgCacheVO.class);
    }

    @Override
    public List<GcOrgCacheVO> listSubordinate(GcOrgBaseParam param, String parentCode) {
        GcOrgParam paramObj = OrgParamParse.createParam(param, v -> v.setCode(OrgParamParse.formtOrgParentCode(parentCode)));
        return this.orgDao.listSubordinate(paramObj, GcOrgCacheVO.class);
    }

    @Override
    public List<GcOrgCacheVO> listDirectSubordinate(GcOrgBaseParam param, String parentCode) {
        return this.orgDao.listDirectSubordinate(OrgParamParse.createParam(param, v -> v.setParentcode(parentCode)), GcOrgCacheVO.class);
    }

    @Override
    public List<GcOrgCacheVO> listSuperior(GcOrgBaseParam param, String code) {
        return this.orgDao.listSuperior(OrgParamParse.createParam(param, v -> v.setCode(code)), GcOrgCacheVO.class);
    }

    @Override
    public GcOrgCacheVO getBaseUnit(GcOrgParam param) {
        return this.orgDao.get(param, GcOrgCacheVO.class);
    }

    @Override
    public List<GcOrgCacheVO> collectionToTree(Collection<GcOrgCacheVO> list) {
        return GcOrgCacheUtil.collectionToTree(list);
    }
}

