/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum
 *  com.jiuqi.gcreport.org.api.enums.GcOrgConst
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 */
package com.jiuqi.gcreport.org.impl.util.internal;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.enums.GcOrgConst;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgEditService;
import com.jiuqi.gcreport.org.impl.util.base.GcOrgCenterBase;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgManageModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import java.util.Date;

public class GcOrgManagerAutoVersionCenterTool
extends GcOrgCenterBase {
    private GcOrgBaseParam param;
    private GcOrgManageModel model;

    private GcOrgManagerAutoVersionCenterTool() {
    }

    public static GcOrgManagerAutoVersionCenterTool getInstance() {
        return GcOrgManagerAutoVersionCenterTool.getInstance(null, "");
    }

    public static GcOrgManagerAutoVersionCenterTool getInstance(String orgType) {
        return GcOrgManagerAutoVersionCenterTool.getInstance(orgType, "");
    }

    public static GcOrgManagerAutoVersionCenterTool getInstance(String orgType, String yyyyTmmmm) {
        return GcOrgManagerAutoVersionCenterTool.getInstance(orgType, YearPeriodUtil.transform(null, (String)yyyyTmmmm));
    }

    public static GcOrgManagerAutoVersionCenterTool getInstance(String orgType, YearPeriodDO yp) {
        GcOrgManagerAutoVersionCenterTool tool = new GcOrgManagerAutoVersionCenterTool();
        try {
            tool.model = GcOrgModelProvider.getGcOrgManageModel();
            tool.param = new GcOrgBaseParam(tool.model, orgType, yp.getEndDate());
        }
        catch (Exception e) {
            throw new RuntimeException("\u52a0\u8f7d\u81ea\u52a8\u7248\u672c\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u5668\u5931\u8d25,\u8bf7\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458", e);
        }
        return tool;
    }

    @Override
    protected FGcOrgEditService getGcReportService() {
        return this.model.getOrgEditService();
    }

    @Override
    protected GcOrgBaseParam getParam() {
        return this.param;
    }

    private OrgVersionVO initVersion(OrgVersionVO dataVer, OrgVersionVO version, Date currDate) {
        if (dataVer.getInvalidTime().getYear() == GcOrgConst.ORG_VERDATE_MAX.getYear() && version.getInvalidTime().getYear() == GcOrgConst.ORG_VERDATE_MAX.getYear()) {
            Date A = DateUtils.firstMonthDayOf((Date)dataVer.getValidTime());
            Date B = DateUtils.firstMonthDayOf((Date)version.getInvalidTime());
            Date C = DateUtils.firstMonthDayOf((Date)currDate);
            if (!A.after(B) && C.after(B)) {
                OrgVersionVO newVer = OrgParse.newVaOrgVersion(version, C);
                this.model.getOrgVersionService().createOrgVersion(newVer);
                this.param.loadVersion(this.model);
                return newVer;
            }
        }
        return version;
    }

    @Override
    public void add(OrgToJsonVO vo) {
        FGcOrgEditService service = this.getGcReportService();
        this.initVersion(this.getParam().getVersion(), this.getParam().getVersion(), this.param.getOrgNowDate());
        this.addOrg(service, vo);
        this.model.publishEvent(EventChangeTypeEnum.INSERT, this.param, vo);
    }

    @Override
    public void update(OrgToJsonVO org) throws RuntimeException {
        this.initVersion(this.param.getVersion(), this.param.getVersion(), this.param.getOrgNowDate());
        this.updateOrg(this.getGcReportService(), org);
        this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, org);
    }

    @Override
    public void moveParent(OrgToJsonVO org, OrgToJsonVO tagertOrg) {
        this.initVersion(this.param.getVersion(), this.param.getVersion(), this.param.getOrgNowDate());
        this.getGcReportService().move(OrgParamParse.createParam(this.param, v -> {
            v.setCode(org.getCode());
            v.setParentcode(tagertOrg.getCode());
        }));
        this.model.publishEvent(EventChangeTypeEnum.UPDATE, this.param, org);
    }

    @Override
    @Deprecated
    public void delete(String orgId) {
        this.initVersion(this.param.getVersion(), this.param.getVersion(), this.param.getOrgNowDate());
        this.deleteWithChildrens(orgId);
        OrgToJsonVO org = new OrgToJsonVO();
        org.setId(orgId);
        this.model.publishEvent(EventChangeTypeEnum.DELETE, this.param, org);
    }
}

