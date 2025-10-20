/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryParentType
 *  com.jiuqi.va.organization.common.OrgConstants$UpOrDown
 *  com.jiuqi.va.organization.domain.ZBDTO
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  com.jiuqi.va.organization.service.OrgDataService
 *  com.jiuqi.va.organization.service.impl.help.OrgDataModifyService
 */
package com.jiuqi.gcreport.org.impl.cache.dao.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgEditDao;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.common.OrgConstants;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.impl.help.OrgDataModifyService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcOrgQueryDaoImpl
implements FGcOrgEditDao {
    @Autowired
    private OrgDataService orgService;
    @Autowired
    private OrgDataModifyService orgDataModifyService;
    @Autowired
    private FGcOrgTypeVersionDao typeVerDao;
    @Autowired
    private OrgCategoryService orgCategoryService;

    @Override
    public OrgTypeVO getOrgType(String name) {
        return this.typeVerDao.getOrgType(name);
    }

    @Override
    public List<OrgTypeVO> listOrgType() {
        return this.typeVerDao.listOrgType();
    }

    @Override
    public List<OrgVersionVO> listOrgVersion(OrgTypeVO type) {
        return this.typeVerDao.listOrgVersionByType(type);
    }

    @Override
    public OrgVersionVO getOrgVersion(OrgTypeVO type, Date time) {
        return this.typeVerDao.getOrgVersion(type, time);
    }

    @Override
    public int getOrgCodeLength() {
        return this.typeVerDao.getOrgCodeLength();
    }

    @Override
    @Deprecated
    public <T> List<T> getOrgTree(GcOrgParam param, Class<T> clzz) {
        throw new RuntimeException("\u6b64\u65b9\u6cd5\u76ee\u524d\u6682\u65f6\u672a\u5b9e\u73b0,\u8bf7\u4f7f\u7528\u5176\u4ed6\u65b9\u6cd5.");
    }

    @Override
    public <T> Map<String, Object> getTableDetail(String tableName, String id) {
        OrgDO orgDO = this.orgService.get((OrgDTO)OrgParamParse.createDefaultParam(tableName, vo -> vo.setId(UUIDUtils.fromString36((String)id))));
        if (orgDO == null) {
            return null;
        }
        return OrgParse.toGcCacheVo(orgDO).getFields();
    }

    @Override
    public <T> T get(GcOrgParam param, Class<T> clzz) {
        return OrgParse.toGcVO(this.orgService.get((OrgDTO)param), clzz);
    }

    @Override
    public <T> List<T> list(GcOrgParam param, Class<T> clzz) {
        PageVO list = this.orgService.list((OrgDTO)param);
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> OrgParse.toGcVO(v, clzz)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public <T> List<T> listSuperior(GcOrgParam param, Class<T> clzz) {
        param.setQueryParentType(OrgDataOption.QueryParentType.ALL_PARENT_WITH_SELF);
        PageVO list = this.orgService.list((OrgDTO)param);
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> OrgParse.toGcVO(v, clzz)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public <T> List<T> listSubordinate(GcOrgParam param, Class<T> clzz) {
        PageVO list;
        OrgDO org = null;
        if (!StringUtils.isEmpty((String)param.getCode())) {
            org = this.orgService.get((OrgDTO)param);
        }
        if (org == null) {
            param.setCode(null);
            list = this.orgService.list((OrgDTO)param);
        } else {
            param.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN_WITH_SELF);
            list = this.orgService.list((OrgDTO)param);
        }
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> OrgParse.toGcVO(v, clzz)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public <T> List<T> listDirectSubordinate(GcOrgParam param, Class<T> clzz) {
        PageVO list = this.orgService.list((OrgDTO)param);
        if (list.getRows() != null && list.getRows().size() > 0) {
            return list.getRows().stream().map(v -> OrgParse.toGcVO(v, clzz)).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public boolean add(GcOrgParam orgDTO) {
        this.removeFieldKey(orgDTO);
        orgDTO.setStopflag(orgDTO.getStopflag() == 1 ? 1 : 0);
        orgDTO.setRecoveryflag(orgDTO.getRecoveryflag() == 1 ? 1 : 0);
        R r = this.orgService.add((OrgDTO)orgDTO);
        return this.returnR(r);
    }

    @Override
    public boolean relAdd(GcOrgParam orgDTO) {
        this.removeFieldKey(orgDTO);
        R r = this.orgService.relAdd((OrgDTO)orgDTO);
        return this.returnR(r);
    }

    @Override
    public boolean update(GcOrgParam orgDTO) {
        this.removeFieldKey(orgDTO);
        orgDTO.setStopflag(orgDTO.getStopflag() == 1 ? 1 : 0);
        orgDTO.setRecoveryflag(orgDTO.getRecoveryflag() == 1 ? 1 : 0);
        R r = this.orgService.update((OrgDTO)orgDTO);
        return this.returnR(r);
    }

    @Override
    public boolean changeState(GcOrgParam orgDTO) {
        this.removeFieldKey(orgDTO);
        R r = this.orgService.changeState((OrgDTO)orgDTO);
        return this.returnR(r);
    }

    @Override
    public boolean remove(GcOrgParam orgDTO) {
        this.removeFieldKey(orgDTO);
        if (orgDTO.getCategoryname().equalsIgnoreCase("MD_ORG")) {
            R r = this.orgService.remove((OrgDTO)orgDTO);
            return this.returnR(r);
        }
        int flag = this.orgDataModifyService.remove((OrgDTO)orgDTO);
        return flag > 0;
    }

    @Override
    public boolean recovery(GcOrgParam orgDTO) {
        this.removeFieldKey(orgDTO);
        R r = this.orgService.recovery((OrgDTO)orgDTO);
        return this.returnR(r);
    }

    @Override
    public boolean upOrDown(GcOrgParam orgDTO, boolean isUp) {
        this.removeFieldKey(orgDTO);
        R r = this.orgService.upOrDown((OrgDTO)orgDTO, isUp ? OrgConstants.UpOrDown.UP : OrgConstants.UpOrDown.DOWN);
        return this.returnR(r);
    }

    @Override
    public boolean move(GcOrgParam orgDTO) {
        this.removeFieldKey(orgDTO);
        if (orgDTO.getParentcode().equals("-")) {
            orgDTO.setParentcode("");
        }
        R r = this.orgService.move((OrgDTO)orgDTO);
        return this.returnR(r);
    }

    private boolean returnR(R r) {
        if (r.getCode() == 0) {
            return true;
        }
        throw new RuntimeException(r.getMsg());
    }

    private void removeFieldKey(GcOrgParam orgDTO) {
        orgDTO.remove("GCPARENTS");
        orgDTO.remove("GCPARENTS".toLowerCase());
        OrgCategoryDO categoryDO = new OrgCategoryDO();
        categoryDO.setName(orgDTO.getCategoryname());
        List zbList = this.orgCategoryService.listZB(categoryDO);
        Map<String, ZBDTO> zbMap = zbList.stream().collect(Collectors.toMap(zbdto -> zbdto.getName().toUpperCase(), o -> o));
        Set entries = orgDTO.entrySet();
        HashMap showMap = new HashMap();
        for (Map.Entry entry : entries) {
            String fieldName = (String)entry.getKey();
            Object value = entry.getValue();
            ZBDTO zb = zbMap.get(fieldName.toUpperCase());
            if (zb == null) continue;
            if (value == null) {
                return;
            }
            if (zb.getMultiple() == null || zb.getMultiple() != 1 || value instanceof ArrayList || zb.getReltablename() == null || 1 != zb.getRelatetype()) continue;
            String valueString = (String)value;
            if (!StringUtils.isEmpty((String)valueString)) {
                String[] split = valueString.split(";");
                showMap.put(fieldName + "_show", new ArrayList<String>(Arrays.asList(split)));
                showMap.put(fieldName, new ArrayList<String>(Arrays.asList(split)));
                continue;
            }
            showMap.put(fieldName, new ArrayList());
        }
        orgDTO.putAll(showMap);
    }
}

