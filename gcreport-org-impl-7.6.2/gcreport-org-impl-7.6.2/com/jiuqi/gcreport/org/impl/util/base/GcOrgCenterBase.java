/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.web.util.ObjectToLogInfo
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.gcreport.org.impl.util.base;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.web.util.ObjectToLogInfo;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgEditService;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.np.log.LogHelper;
import java.util.Date;
import java.util.List;

public abstract class GcOrgCenterBase {
    protected abstract FGcOrgEditService getGcReportService();

    protected abstract GcOrgBaseParam getParam();

    public abstract void add(OrgToJsonVO var1);

    public abstract void update(OrgToJsonVO var1);

    public abstract void moveParent(OrgToJsonVO var1, OrgToJsonVO var2);

    public abstract void delete(String var1);

    protected void updateOrg(FGcOrgEditService service, OrgToJsonVO org) {
        org.setTitle(org.getTitle().trim());
        org.setSimpletitle(org.getSimpletitle().trim());
        StringBuffer log = ObjectToLogInfo.formatObject((Object)org);
        OrgToJsonVO mainorg = (OrgToJsonVO)this.getGcReportService().getBaseUnit(OrgParamParse.createBaseOrgParam(v -> v.setCode(org.getCode())));
        OrgToJsonVO orgImpl = new OrgToJsonVO();
        orgImpl.setDatas(org.getDatas());
        if (mainorg == null) {
            this.getGcReportService().add(OrgParse.toVaJsonVo(orgImpl, null));
            log.append(" \u540c\u6b65\u65b0\u589e\u57fa\u672c\u7ec4\u7ec7\u673a\u6784");
        }
        this.getGcReportService().update(OrgParse.toVaJsonVo(org, this.getParam()));
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u4fee\u6539-\u673a\u6784\u7c7b\u578b" + this.getParam().getOrgtypeName() + "-\u7248\u672c" + this.getParam().getVersion().getName() + "-\u5355\u4f4d" + org.getCode()), (String)log.toString());
    }

    protected boolean addOrg(FGcOrgEditService service, OrgToJsonVO org) {
        OrgToJsonVO newBaseOrg;
        org.setTitle(org.getTitle().trim());
        StringBuffer log = new StringBuffer();
        OrgToJsonVO mainorg = (OrgToJsonVO)service.getBaseUnit(OrgParamParse.createBaseOrgParam(v -> v.setCode(org.getCode())));
        OrgToJsonVO orgImpl = new OrgToJsonVO();
        orgImpl.setDatas(org.getDatas());
        if (mainorg == null) {
            service.add(OrgParse.toVaJsonVo(orgImpl, null));
            log.append(" \u540c\u6b65\u65b0\u589e\u57fa\u672c\u7ec4\u7ec7\u673a\u6784");
        }
        if ((newBaseOrg = (OrgToJsonVO)service.getBaseUnit(OrgParamParse.createBaseOrgParam(v -> v.setCode(org.getCode())))) != null) {
            newBaseOrg.getDatas().entrySet().forEach(v -> {
                if (!"id".equalsIgnoreCase((String)v.getKey()) && org.getFieldValue((String)v.getKey()) == null) {
                    org.setFieldValue((String)v.getKey(), v.getValue());
                }
            });
        }
        org.setFieldValue("categoryname", (Object)this.getParam().getOrgtypeName());
        log.append(ObjectToLogInfo.formatObject((Object)org));
        service.add(OrgParse.toVaJsonVo(org, this.getParam()));
        if (org.getChildren() != null && org.getChildren().size() > 0) {
            org.getChildren().stream().forEach(f -> {
                try {
                    this.addOrg(service, (OrgToJsonVO)f);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u65b0\u589e-\u673a\u6784\u7c7b\u578b" + this.getParam().getOrgtypeName() + "-\u7248\u672c" + this.getParam().getVersion().getName() + "-\u5355\u4f4d" + org.getCode()), (String)log.toString());
        return true;
    }

    @Deprecated
    private void relateOrg(OrgToJsonVO org, OrgToJsonVO targetOrg, boolean withChildren) {
    }

    protected void deleteWithChildrens(String orgCode) {
        OrgToJsonVO parent = (OrgToJsonVO)this.getGcReportService().getByCode(this.getParam(), orgCode);
        if (null == parent) {
            return;
        }
        List childrens = this.getGcReportService().listDirectSubordinate(this.getParam(), orgCode);
        if (!CollectionUtils.isEmpty(childrens)) {
            childrens.stream().forEach(entity -> {
                try {
                    this.deleteWithChildrens(entity.getCode());
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        parent.setUpdateTime(new Date());
        this.getGcReportService().remove(OrgParse.toVaJsonVo(parent, this.getParam()));
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u5220\u9664-\u673a\u6784\u7c7b\u578b" + this.getParam().getOrgtypeName() + "-\u7248\u672c" + this.getParam().getVersion().getName() + "-\u5355\u4f4d" + parent.getCode()), (String)"");
    }

    @Deprecated
    protected void stop(OrgToJsonVO org) {
        org.setUpdateTime(new Date());
        this.getGcReportService().changeState(OrgParamParse.createParam(this.getParam(), v -> {
            v.setCode(org.getCode());
            v.setStopflag(1);
        }));
    }

    @Deprecated
    protected void stop(String orgCode) {
        this.getGcReportService().changeState(OrgParamParse.createParam(this.getParam(), v -> {
            v.setCode(orgCode);
            v.setStopflag(1);
        }));
    }

    public OrgToJsonVO getOrgByPrimaryID(String orgId) {
        return (OrgToJsonVO)this.getGcReportService().getById(this.getParam(), orgId);
    }

    public OrgToJsonVO getOrgByID(String orgId) {
        return this.getOrgByCode(orgId);
    }

    public OrgToJsonVO getOrgByCode(String orgCode) {
        return (OrgToJsonVO)this.getGcReportService().getByCode(this.getParam(), orgCode);
    }

    public List<OrgToJsonVO> getOrgsByIds(List<String> orgCodes) {
        FGcOrgEditService service = this.getGcReportService();
        return service.listByParam(this.getParam(), v -> v.setOrgCodes(orgCodes));
    }

    public List<OrgToJsonVO> listChildOrgByParent(String parentCode) {
        return this.getGcReportService().listDirectSubordinate(this.getParam(), parentCode);
    }

    public List<OrgToJsonVO> listAllChildOrgByParent(String parentCode) {
        return this.getGcReportService().listSubordinate(this.getParam(), parentCode);
    }

    public List<OrgToJsonVO> listOrgBySearch(String searchText) {
        return this.getGcReportService().list(this.getParam(), searchText);
    }

    public List<OrgToJsonVO> getOrgTree(String parentCode) {
        return this.getGcReportService().getOrgTree(this.getParam(), parentCode);
    }

    public List<OrgToJsonVO> listOrg() {
        return this.getGcReportService().list(this.getParam(), null);
    }
}

