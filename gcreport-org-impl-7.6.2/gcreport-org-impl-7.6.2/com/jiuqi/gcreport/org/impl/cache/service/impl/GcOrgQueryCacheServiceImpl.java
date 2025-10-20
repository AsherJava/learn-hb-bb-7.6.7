/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 */
package com.jiuqi.gcreport.org.impl.cache.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.base.GcOrgParamInterface;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgQueryDao;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgQueryService;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgCacheManage;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgServiceBase;
import com.jiuqi.gcreport.org.impl.cache.util.GcOrgCacheUtil;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.va.domain.org.OrgDataOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class GcOrgQueryCacheServiceImpl
extends GcOrgServiceBase
implements FGcOrgQueryService<GcOrgCacheVO> {
    @Autowired
    private FGcOrgQueryDao queryDao;
    @Autowired
    private GcOrgCacheManage cacheManager;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initOrgCache() {
        Class<GcOrgQueryCacheServiceImpl> clazz = GcOrgQueryCacheServiceImpl.class;
        synchronized (GcOrgQueryCacheServiceImpl.class) {
            List<OrgTypeVO> types = this.queryDao.listOrgType();
            types.forEach(t -> {
                GcOrgParam param = OrgParamParse.createDefaultParam(t.getName(), vo -> vo.setAuthType(OrgDataOption.AuthType.NONE));
                this.cacheManager.getOrgType(t.getName(), p -> Arrays.asList(t));
                this.cacheManager.getOrgVersion((OrgTypeVO)t, new Date(), p -> this.queryDao.listOrgVersion((OrgTypeVO)t));
                this.cacheManager.initOrgCacheData(param, p -> this.queryDao.list(p, GcOrgCacheVO.class));
            });
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    @Override
    public OrgTypeVO getOrgType(String name) {
        return this.cacheManager.getOrgType(name, p -> Arrays.asList(this.queryDao.getOrgType(name)));
    }

    @Override
    public OrgVersionVO getOrgVersion(OrgTypeVO type, Date time) {
        return this.cacheManager.getOrgVersion(type, time, p -> this.queryDao.listOrgVersion(type));
    }

    @Override
    public int getOrgCodeLength() {
        return this.queryDao.getOrgCodeLength();
    }

    @Override
    public List<OrgVersionVO> listOrgVersion(OrgTypeVO type) {
        return this.queryDao.listOrgVersion(type);
    }

    @Override
    public List<GcOrgCacheVO> getOrgTree(GcOrgBaseParam param, String parentCode) {
        if (StringUtils.isEmpty((String)parentCode)) {
            return this.getOrgTree(param);
        }
        ArrayList<GcOrgCacheVO> list = new ArrayList<GcOrgCacheVO>();
        GcOrgCacheVO org = this.getOrgByOrgId(this.getOrgTree(param), parentCode);
        if (org != null) {
            list.add(org);
        }
        return list;
    }

    @Override
    public GcOrgCacheVO getByCode(GcOrgBaseParam param, String code) {
        return this.getOrgByOrgId(this.getOrgTree(param), code);
    }

    @Override
    public List<GcOrgCacheVO> list(GcOrgBaseParam param, String searchText) {
        ArrayList<GcOrgCacheVO> list = new ArrayList<GcOrgCacheVO>();
        this.searchOrgByText(this.getOrgTree(param), searchText, null, list);
        return list;
    }

    @Override
    public List<GcOrgCacheVO> list(GcOrgBaseParam param, String searchText, String parentCode) {
        ArrayList<GcOrgCacheVO> list = new ArrayList<GcOrgCacheVO>();
        this.searchOrgByText(this.getOrgTree(param), searchText, parentCode, list);
        return list;
    }

    @Override
    public List<GcOrgCacheVO> listSubordinate(GcOrgBaseParam param, String parentCode) {
        ArrayList<GcOrgCacheVO> list = new ArrayList<GcOrgCacheVO>();
        this.treeToList(list, this.getOrgTree(param, parentCode));
        return list;
    }

    @Override
    public List<GcOrgCacheVO> listDirectSubordinate(GcOrgBaseParam param, String parentCode) {
        List<GcOrgCacheVO> list = this.getOrgTree(param, parentCode);
        if (list != null && list.size() > 0) {
            if (StringUtils.isNull((String)parentCode)) {
                return list;
            }
            return list.get(0).getChildren();
        }
        return null;
    }

    @Override
    public List<GcOrgCacheVO> listSuperior(GcOrgBaseParam param, String code) {
        List<GcOrgCacheVO> list = this.getParentOrg(this.getOrgTree(param), code);
        return list;
    }

    @Override
    public GcOrgCacheVO getBaseUnit(GcOrgParam param) {
        return this.queryDao.get(param, GcOrgCacheVO.class);
    }

    @Override
    public GcOrgCacheVO getById(GcOrgBaseParam param, String id) {
        return this.queryDao.get(OrgParamParse.createParam(param, v -> v.setId(UUIDUtils.fromString36((String)id))), GcOrgCacheVO.class);
    }

    @Override
    public List<GcOrgCacheVO> listByParam(GcOrgBaseParam param, GcOrgParamInterface<GcOrgParam> setter) {
        return this.queryDao.list(OrgParamParse.createParam(param, setter), GcOrgCacheVO.class);
    }

    @Override
    public Map<String, Object> getTableDetail(String tableName, String id) {
        return this.queryDao.getTableDetail(tableName, id);
    }

    private List<GcOrgCacheVO> getOrgTree(GcOrgBaseParam param) {
        return this.cacheManager.getOrgTree(param, (GcOrgParam p) -> this.queryDao.list(p, GcOrgCacheVO.class));
    }

    @Override
    public List<GcOrgCacheVO> collectionToTree(Collection<GcOrgCacheVO> list) {
        return GcOrgCacheUtil.collectionToTree(list);
    }
}

