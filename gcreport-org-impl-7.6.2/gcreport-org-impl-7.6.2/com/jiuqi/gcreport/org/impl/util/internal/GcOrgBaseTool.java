/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.util.internal;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.cache.base.GcOrgQueryParam;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgEditService;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgManageModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class GcOrgBaseTool {
    private GcOrgManageModel model;
    private GcOrgBaseParam param;

    private GcOrgBaseTool(GcOrgManageModel model) {
        this.model = model;
        try {
            this.param = new GcOrgBaseParam(model, "MD_ORG", new Date());
        }
        catch (Exception e) {
            throw new RuntimeException("\u521d\u59cb\u5316\u53c2\u6570\u5f02\u5e38,\u9700\u8981\u8054\u7cfb\u7cfb\u7edf\u7ba1\u7406\u5458", e);
        }
    }

    public static GcOrgBaseTool getInstance() {
        return new GcOrgBaseTool(GcOrgModelProvider.getGcOrgManageModel());
    }

    public List<OrgToJsonVO> listOrg() {
        List<OrgToJsonVO> orgs = this.model.getOrgEditService().list(this.param, null);
        return orgs;
    }

    public List<OrgToJsonVO> listDirectSubordinates(String parentid) {
        List<OrgToJsonVO> orgs = this.model.getOrgEditService().listDirectSubordinate(this.param, parentid);
        return orgs;
    }

    public OrgToJsonVO getOrgByPrimaryId(String id) {
        return this.getOrgByCode(id);
    }

    public OrgToJsonVO getOrgById(String id) {
        return this.getOrgByCode(id);
    }

    public OrgToJsonVO getOrgByCode(String code) {
        return (OrgToJsonVO)this.model.getOrgEditService().getByCode(this.param, code);
    }

    public List<OrgToJsonVO> getOrgTree(String parentCode) {
        return this.model.getOrgEditService().getOrgTree(this.param, parentCode);
    }

    public List<OrgToJsonVO> getOrgTreeWithAuth(Boolean enableAuth, String parentCode) {
        try {
            GcOrgQueryParam p = new GcOrgQueryParam(this.model, this.param.getOrgtypeName(), this.param.getOrgNowDate(), enableAuth != false ? GcAuthorityType.ACCESS : GcAuthorityType.NONE);
            List<OrgToJsonVO> list = this.model.getOrgEditService().getOrgTree(p, parentCode);
            return list;
        }
        catch (Exception e) {
            throw new RuntimeException("\u83b7\u53d6\u6743\u9650\u57fa\u672c\u7ec4\u7ec7\u673a\u6784\u5f02\u5e38.");
        }
    }

    public List<OrgToJsonVO> listOrgBySearchText(String searchText) {
        return this.model.getOrgEditService().list(this.param, searchText);
    }

    public List<OrgToJsonVO> listAllSubordinates(String parentId) {
        return this.model.getOrgEditService().listSubordinate(this.param, parentId);
    }

    public void addBaseUnit(OrgToJsonVO orgToJsonVO) {
        GcOrgCodeConfig orgCodeConfig = this.model.getOrgEditService().getOrgCodeConfig();
        boolean variableLength = orgCodeConfig.isVariableLength();
        int len = orgCodeConfig.getCodeLength();
        String code = orgToJsonVO.getCode();
        orgToJsonVO.setTitle(orgToJsonVO.getTitle().trim());
        if (StringUtils.isEmpty((String)code)) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (code.indexOf(" ") != -1) {
            throw new BusinessRuntimeException("\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u5305\u542b\u7a7a\u683c");
        }
        if (!variableLength && code.length() != len) {
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u957f\u5ea6\u53ea\u80fd\u4e3a " + len);
        }
        if (variableLength && code.length() > len) {
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\u957f\u5ea6\u4e0d\u80fd\u8d85\u8fc7 " + len);
        }
        OrgToJsonVO orgByCode = this.getOrgByCode(String.valueOf(orgToJsonVO.getFieldValue("CODE")));
        Assert.isNull((Object)orgByCode, (String)"\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u91cd\u590d", (Object[])new Object[0]);
        GcOrgParam dto = OrgParse.toVaJsonVo(orgToJsonVO, this.param);
        String id = NpContextHolder.getContext().getUser().getId();
        dto.setCreateuser(id);
        dto.setCreatetime(new Date());
        int endPosition = Math.min(dto.getName().length(), 90);
        dto.setShortname(StringUtils.isEmpty((String)dto.getShortname()) ? dto.getName().substring(0, endPosition) : dto.getShortname());
        this.model.getOrgEditService().add(dto);
    }

    public void updateBaseUnit(OrgToJsonVO vo) {
        OrgToJsonVO org = (OrgToJsonVO)this.model.getOrgEditService().getByCode(this.param, vo.getCode());
        Assert.isNotNull((Object)org, (String)"\u8981\u4fee\u6539\u7684\u5355\u4f4d\u4e0d\u5b58\u5728!", (Object[])new Object[0]);
        vo.setTitle(vo.getTitle().trim());
        GcOrgParam orgImpl = OrgParse.toVaJsonVo(vo, this.param);
        orgImpl.setSyncOrgBaseInfo(false);
        String parent = (String)vo.getFieldValue("PARENTCODE");
        if (StringUtils.isEmpty((String)parent)) {
            vo.setFieldValue("PARENTCODE", (Object)"-");
        }
        if (!vo.getFieldValue("PARENTCODE").equals(org.getParentid())) {
            this.model.getOrgEditService().move(orgImpl);
        }
        orgImpl.setVer(null);
        this.model.getOrgEditService().update(orgImpl);
    }

    @Transactional(rollbackFor={Exception.class})
    public void updateBaseUnitOrdinal(List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) {
            return;
        }
        codes.forEach(code -> {
            OrgToJsonVO org = (OrgToJsonVO)this.model.getOrgEditService().getByCode(this.param, (String)code);
            if (!org.isRecoveryFlag()) {
                org.setFieldValue("ORDINAL", (Object)System.currentTimeMillis());
                GcOrgParam orgImpl = OrgParse.toVaJsonVo(org, this.param);
                this.model.getOrgEditService().update(orgImpl);
            }
        });
    }

    public void deleteBaseUnit(String code) {
        this.model.getOrgEditService().remove(OrgParamParse.createBaseOrgParam(v -> v.setCode(code)));
    }

    public void moveOrder(String orgCode, String targetOrgCode) {
        FGcOrgEditService orgEditService = this.model.getOrgEditService();
        OrgToJsonVO current = (OrgToJsonVO)orgEditService.getByCode(this.param, orgCode);
        List list = orgEditService.listDirectSubordinate(this.param, current.getParentid());
        Integer index = null;
        double currentOrgOrdinal = 0.0;
        for (int i = 0; i < list.size(); ++i) {
            if (!((OrgToJsonVO)list.get(i)).getCode().equals(targetOrgCode)) continue;
            index = i;
            break;
        }
        if (index == null) {
            throw new BusinessRuntimeException("\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728");
        }
        if (index != 0) {
            Double ordinal = ((OrgToJsonVO)list.get(index - 1)).getOrdinal();
            Double ordinal2 = ((OrgToJsonVO)list.get(index)).getOrdinal();
            currentOrgOrdinal = (ordinal + ordinal2) / 2.0;
        } else {
            currentOrgOrdinal = (((OrgToJsonVO)list.get(index)).getOrdinal() + 0.0) / 2.0;
        }
        current.setFieldValue("ORDINAL", (Object)currentOrgOrdinal);
        GcOrgParam orgImpl = OrgParse.toVaJsonVo(current, this.param);
        orgEditService.update(orgImpl);
    }

    public void moverUpOrDown(String orgcode, boolean isUp) {
        this.model.getOrgEditService().upOrDown(OrgParamParse.createParam(this.param, v -> v.setCode(orgcode)), isUp);
    }
}

