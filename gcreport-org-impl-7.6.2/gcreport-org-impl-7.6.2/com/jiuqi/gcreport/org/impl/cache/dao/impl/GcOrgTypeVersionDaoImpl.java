/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.organization.service.OrgCategoryService
 *  com.jiuqi.va.organization.service.OrgVersionService
 */
package com.jiuqi.gcreport.org.impl.cache.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.organization.service.OrgCategoryService;
import com.jiuqi.va.organization.service.OrgVersionService;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcOrgTypeVersionDaoImpl
implements FGcOrgTypeVersionDao {
    private GcOrgCodeConfig gcOrgCodeConfig;
    @Autowired
    private OrgCategoryService typeService;
    @Autowired
    private OrgVersionService versionService;

    @Override
    public OrgTypeVO getOrgType(String name) {
        OrgTypeVO orgType = null;
        try {
            OrgCategoryDO orgVaType = this.typeService.get(OrgParamParse.createOrgTypeParam(v -> v.setName(name)));
            orgType = OrgParse.toGcType(orgVaType);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return orgType;
    }

    @Override
    public List<OrgTypeVO> listOrgType() {
        PageVO orgTypes = this.typeService.list(OrgParamParse.createOrgTypeParam(v -> {}));
        if (orgTypes.getRows() == null || orgTypes.getRows().isEmpty()) {
            return null;
        }
        return orgTypes.getRows().stream().map(v -> OrgParse.toGcType(v)).collect(Collectors.toList());
    }

    @Override
    public Map<String, ZB> getTypeExtFieldsByName(String typeName) {
        Map<String, Object> zbMap = new HashMap<String, ZB>();
        try {
            OrgCategoryDO orgVaType = this.typeService.get(OrgParamParse.createOrgTypeParam(v -> v.setName(typeName)));
            if (orgVaType != null) {
                zbMap = orgVaType.getZbs().stream().collect(Collectors.toMap(ZB::getName, Function.identity()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return zbMap;
    }

    @Override
    public boolean addOrgType(OrgCategoryDO type) {
        R r = this.typeService.add(type);
        return this.returnR(r);
    }

    @Override
    public boolean updateOrgType(OrgCategoryDO type) {
        R r = this.typeService.update(type);
        return this.returnR(r);
    }

    @Override
    public boolean removeOrgType(OrgCategoryDO type) {
        R r = this.typeService.remove(type);
        return this.returnR(r);
    }

    @Override
    public List<OrgVersionVO> listOrgVersion() {
        PageVO orgVers = this.versionService.list(OrgParamParse.createOrgVerParam(v -> {}));
        return orgVers.getRows().stream().map(f -> OrgParse.toGcVersion(f, this.getOrgType(f.getCategoryname()))).collect(Collectors.toList());
    }

    @Override
    public List<OrgVersionVO> listOrgVersionByType(OrgTypeVO type) {
        PageVO orgVers = this.versionService.list(OrgParamParse.createOrgVerParam(v -> v.setCategoryname(type.getName())));
        return orgVers.getRows().stream().map(f -> OrgParse.toGcVersion(f, type)).collect(Collectors.toList());
    }

    @Override
    public OrgVersionVO getOrgVersion(OrgTypeVO type, Date date) {
        PageVO list = this.versionService.list(OrgParamParse.createOrgVerParam(v -> {
            v.setCategoryname(type.getName());
            v.setVersionDate(date);
        }));
        if (list.getTotal() != 1) {
            throw new RuntimeException(String.format("\u6ca1\u6709\u627e\u5230\u7c7b\u578b[%1$s]\u65f6\u95f4[%2$s]\u7684\u7ec4\u7ec7\u673a\u6784\u7248\u672c", type.getTitle(), DateUtils.format((Date)date)));
        }
        return OrgParse.toGcVersion((OrgVersionDO)list.getRows().get(0), type);
    }

    @Override
    public OrgVersionVO getOrgVersion(OrgTypeVO type, String verName) {
        PageVO list = this.versionService.list(OrgParamParse.createOrgVerParam(v -> {
            v.setCategoryname(type.getName());
            v.setTitle(verName);
        }));
        if (list.getTotal() != 1) {
            throw new RuntimeException(String.format("\u6ca1\u6709\u627e\u5230\u7c7b\u578b[%1$s]\u540d\u79f0[%2$s]\u7684\u7ec4\u7ec7\u673a\u6784\u7248\u672c", type.getTitle(), verName));
        }
        return OrgParse.toGcVersion((OrgVersionDO)list.getRows().get(0), type);
    }

    @Override
    public boolean addOrgVersion(OrgVersionDO ver) {
        R r = this.versionService.add(ver);
        return this.returnR(r);
    }

    @Override
    public boolean updateOrgVersion(OrgVersionDO ver) {
        R r = this.versionService.update(ver);
        return this.returnR(r);
    }

    @Override
    public boolean removeOrgVersion(OrgVersionDO ver) {
        R r = this.versionService.remove(ver);
        return this.returnR(r);
    }

    @Override
    public boolean splitOrgVersion(OrgVersionDO ver) {
        R r = this.versionService.split(ver);
        return this.returnR(r);
    }

    @Override
    public int getOrgCodeLength() {
        return this.getGcOrgCodeConfig().getCodeLength();
    }

    @Override
    public GcOrgCodeConfig getGcOrgCodeConfig() {
        if (this.gcOrgCodeConfig != null) {
            return this.gcOrgCodeConfig;
        }
        GcOrgCodeConfig gcOrgCodeConfig = new GcOrgCodeConfig();
        gcOrgCodeConfig.setCodeLength(7);
        gcOrgCodeConfig.setVariableLength(false);
        try {
            String sql = "select LENGTH, VARIABLELENGTH from GC_UNITCODE_LENGTH where id='00000000-0000-0000-0000-000000000000'";
            List maps = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
            if (CollectionUtils.isEmpty((Collection)maps)) {
                throw new BusinessRuntimeException("\u5355\u4f4d\u914d\u7f6e\u4e0d\u80fd\u4e3a\u7a7a");
            }
            gcOrgCodeConfig.setCodeLength((Integer)((Map)maps.get(0)).get("LENGTH"));
            Integer variableLength = (Integer)((Map)maps.get(0)).get("VARIABLELENGTH");
            gcOrgCodeConfig.setVariableLength(variableLength == 1);
            this.gcOrgCodeConfig = gcOrgCodeConfig;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u5355\u4f4d\u5b9a\u957f\u914d\u7f6e\u5f02\u5e38");
        }
        return gcOrgCodeConfig;
    }

    private boolean returnR(R r) {
        if (r.getCode() == 0) {
            return true;
        }
        throw new BusinessRuntimeException(r.getMsg());
    }

    public void setGcOrgCodeConfig(GcOrgCodeConfig gcOrgCodeConfig) {
        this.gcOrgCodeConfig = gcOrgCodeConfig;
    }
}

