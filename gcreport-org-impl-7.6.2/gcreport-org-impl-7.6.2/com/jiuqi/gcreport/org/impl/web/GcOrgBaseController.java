/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.gcreport.org.api.intf.GcOrgBaseClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.org.api.intf.GcOrgBaseClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgBaseTool;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
class GcOrgBaseController
implements GcOrgBaseClient {
    GcOrgBaseController() {
    }

    private GcOrgBaseTool getTool() {
        return GcOrgBaseTool.getInstance();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<OrgToJsonVO>> listBaseOrgByParent(@RequestBody GcOrgBaseApiParam param) {
        try {
            return BusinessResponseEntity.ok(this.getTool().listDirectSubordinates(param.getOrgParentCode()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<OrgToJsonVO>> listBaseOrgTree(GcOrgBaseApiParam param) {
        try {
            String parentCode = param == null || StringUtils.isEmpty(param.getOrgParentCode()) ? null : param.getOrgParentCode();
            return BusinessResponseEntity.ok(this.getTool().getOrgTree(parentCode));
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<OrgToJsonVO>> listBaseOrgTreeWithAuth(boolean auth) {
        try {
            return BusinessResponseEntity.ok(this.getTool().getOrgTreeWithAuth(auth, null));
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<OrgToJsonVO>> listBaseOrgByByName(@RequestBody GcOrgBaseApiParam param) {
        try {
            return BusinessResponseEntity.ok(this.getTool().listAllSubordinates(param.getOrgParentCode()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<OrgToJsonVO> getUnitById(@RequestBody GcOrgBaseApiParam param) {
        try {
            return BusinessResponseEntity.ok((Object)this.getTool().getOrgById(param.getOrgCode()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<List<OrgToJsonVO>> listAllChildrenBaseOrg(@PathVariable(value="searchText") String searchText) {
        try {
            return BusinessResponseEntity.ok(this.getTool().listOrgBySearchText(searchText));
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    public BusinessResponseEntity<Map<String, String>> listAllBaseOrg() {
        List<OrgToJsonVO> allBaseOrg = this.getTool().listOrg();
        Map<String, String> code2titleMap = allBaseOrg.stream().collect(Collectors.toMap(OrgToJsonVO::getCode, OrgToJsonVO::getTitle));
        return BusinessResponseEntity.ok(code2titleMap);
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> addBaseUnit(@RequestBody OrgToJsonVO vo) {
        try {
            GcOrgBaseTool tool = this.getTool();
            tool.addBaseUnit(vo);
            return BusinessResponseEntity.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((Throwable)e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> updateBaseUnit(@RequestBody OrgToJsonVO vo) {
        String id = (String)vo.getFieldValue("ID");
        String code = (String)vo.getFieldValue("CODE");
        OrgToJsonVO orgByCode = this.getTool().getOrgByCode(code);
        if (orgByCode != null && orgByCode.getId() == vo.getFieldValue("ID")) {
            throw new RuntimeException("\u5355\u4f4d\u4ee3\u7801\u91cd\u590d\uff0c\u8bf7\u91cd\u8bd5");
        }
        Assert.isNotNull((Object)id, (String)"\u8bf7\u9009\u62e9\u9700\u8981\u4fee\u6539\u7684\u5355\u4f4d", (Object[])new Object[0]);
        vo.setFieldValue("UPDATETIME", (Object)new Date());
        this.getTool().updateBaseUnit(vo);
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> batchUpdateBaseUnit(@RequestBody List<OrgToJsonVO> vos) {
        for (OrgToJsonVO vo : vos) {
            OrgToJsonVO updateVO = this.getTool().getOrgByCode(vo.getCode());
            for (String s : vo.getDatas().keySet()) {
                if (s.equalsIgnoreCase("NAME")) {
                    updateVO.setTitle((String)vo.getFieldValue(s));
                    continue;
                }
                updateVO.setFieldValue(s, vo.getFieldValue(s));
            }
            this.updateBaseUnit(updateVO);
        }
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> deleteBaseUnit(@RequestBody GcOrgBaseApiParam param) {
        this.getTool().deleteBaseUnit(param.getOrgCode());
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> moverOrder(@RequestBody GcOrgBaseApiParam param) {
        this.getTool().moveOrder(param.getOrgCode(), param.getLocation());
        return BusinessResponseEntity.ok();
    }

    @Transactional(rollbackFor={Exception.class})
    public BusinessResponseEntity<String> moverUpOrDown(@RequestBody GcOrgBaseApiParam param) {
        this.getTool().moverUpOrDown(param.getOrgCode(), param.getUp());
        return BusinessResponseEntity.ok();
    }

    public BusinessResponseEntity<String> modifySortBaseUnit(@RequestBody List<String> params) {
        try {
            this.getTool().updateBaseUnitOrdinal(params);
        }
        catch (Exception e) {
            e.printStackTrace();
            return BusinessResponseEntity.error((String)"\u6392\u5e8f\u5931\u8d25");
        }
        return BusinessResponseEntity.ok();
    }

    public OrgToJsonVO getAuthFirst() {
        List<OrgToJsonVO> orgTree = this.getTool().getOrgTreeWithAuth(true, null);
        if (!CollectionUtils.isEmpty(orgTree)) {
            orgTree.get(0).setChildren(Collections.emptyList());
            return orgTree.get(0);
        }
        return null;
    }
}

