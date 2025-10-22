/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 */
package com.jiuqi.gcreport.org.impl.cache.service.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.base.GcOrgParamInterface;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgEditDao;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgEditService;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgServiceBase;
import com.jiuqi.gcreport.org.impl.cache.util.GcOrgCacheUtil;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class GcOrgEditServiceImpl
extends GcOrgServiceBase
implements FGcOrgEditService {
    @Autowired
    private FGcOrgEditDao orgDao;

    @Override
    public OrgTypeVO getOrgType(String name) {
        return this.orgDao.getOrgType(name);
    }

    @Override
    public OrgVersionVO getOrgVersion(OrgTypeVO type, Date time) {
        return this.orgDao.getOrgVersion(type, time);
    }

    @Override
    public Map<String, Object> getTableDetail(String tableName, String id) {
        return this.orgDao.getTableDetail(tableName, id);
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
    public List<OrgToJsonVO> getOrgTree(GcOrgBaseParam param, String parentCode) {
        List<OrgToJsonVO> list = this.orgDao.listSubordinate(OrgParamParse.createParam(param, v -> v.setCode(parentCode)), OrgToJsonVO.class);
        if (list != null && list.size() > 0) {
            return this.collectionToJsonTree(list);
        }
        return null;
    }

    @Override
    public OrgToJsonVO getByCode(GcOrgBaseParam param, String code) {
        return this.orgDao.get(OrgParamParse.createParam(param, v -> v.setCode(code)), OrgToJsonVO.class);
    }

    @Override
    public OrgToJsonVO getById(GcOrgBaseParam param, String id) {
        return this.orgDao.get(OrgParamParse.createParam(param, v -> v.setId(UUIDUtils.fromString36((String)id))), OrgToJsonVO.class);
    }

    @Override
    public List<OrgToJsonVO> list(GcOrgBaseParam param, String searchText) {
        return this.orgDao.list(OrgParamParse.createParam(param, v -> v.setSearchKey(searchText)), OrgToJsonVO.class);
    }

    @Override
    public List<OrgToJsonVO> listByParam(GcOrgBaseParam param, GcOrgParamInterface<GcOrgParam> setter) {
        return this.orgDao.list(OrgParamParse.createParam(param, setter), OrgToJsonVO.class);
    }

    @Override
    public List<OrgToJsonVO> listSubordinate(GcOrgBaseParam param, String parentCode) {
        return this.orgDao.listSubordinate(OrgParamParse.createParam(param, v -> v.setCode(parentCode)), OrgToJsonVO.class);
    }

    @Override
    public List<OrgToJsonVO> listSuperior(GcOrgBaseParam param, String code) {
        return this.orgDao.listSuperior(OrgParamParse.createParam(param, v -> v.setCode(code)), OrgToJsonVO.class);
    }

    @Override
    public List<OrgToJsonVO> listDirectSubordinate(GcOrgBaseParam param, String parentCode) {
        return this.orgDao.listDirectSubordinate(OrgParamParse.createParam(param, v -> v.setParentcode(OrgParamParse.formtOrgParentCode(parentCode))), OrgToJsonVO.class);
    }

    @Override
    public OrgToJsonVO getBaseUnit(GcOrgParam param) {
        return this.orgDao.get(param, OrgToJsonVO.class);
    }

    @Override
    public boolean add(GcOrgParam orgDTO) {
        return this.orgDao.add(orgDTO);
    }

    @Override
    public boolean relAdd(GcOrgParam orgDTO) {
        return this.orgDao.relAdd(orgDTO);
    }

    @Override
    public boolean update(GcOrgParam orgDTO) {
        return this.orgDao.update(orgDTO);
    }

    @Override
    public boolean changeState(GcOrgParam orgDTO) {
        return this.orgDao.changeState(orgDTO);
    }

    @Override
    public boolean remove(GcOrgParam orgDTO) {
        return this.orgDao.remove(orgDTO);
    }

    @Override
    public boolean recovery(GcOrgParam orgDTO) {
        return this.orgDao.recovery(orgDTO);
    }

    @Override
    public boolean upOrDown(GcOrgParam orgDTO, boolean isUp) {
        return this.orgDao.upOrDown(orgDTO, isUp);
    }

    @Override
    public boolean move(GcOrgParam orgDTO) {
        return this.orgDao.move(orgDTO);
    }

    @Override
    public List<GcOrgCacheVO> collectionToTree(Collection<GcOrgCacheVO> list) {
        return GcOrgCacheUtil.collectionToTree(list);
    }

    @Override
    public List<OrgToJsonVO> list(GcOrgBaseParam param, String searchText, String parentCode) {
        return null;
    }
}

