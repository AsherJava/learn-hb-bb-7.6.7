/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.field.NodeIconVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  io.swagger.annotations.Api
 *  org.apache.shiro.util.ThreadContext
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.nr.impl.uploadstate.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.impl.uploadstate.GcOrgQueryWithStateTool;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.field.NodeIconVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value="OrgWithStateController", description="\u4e0a\u62a5\u72b6\u6001")
@CrossOrigin
@RestController
public class OrgWithStateController {
    public static final String API_PATH = "/api/gcreport/v1/gcBusOrganizations";
    @Autowired
    protected IDataDefinitionRuntimeController controller;
    @Autowired
    protected IEntityViewRunTimeController evc;

    private GcOrgCenterService getTool(GcOrgPublicApiParam param) {
        ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)"__default_tenant__");
        ThreadContext.put((Object)"NONE_AUTH_KEY", (Object)"true");
        GcAuthorityType type = GcAuthorityType.ACCESS;
        if ("None".equalsIgnoreCase(param.getAuthType())) {
            type = GcAuthorityType.NONE;
        } else if ("Modify".equalsIgnoreCase(param.getAuthType())) {
            type = GcAuthorityType.MANAGE;
        }
        YearPeriodObject yp = new YearPeriodObject(null, param.getOrgVerCode());
        return GcOrgPublicTool.getInstance((String)param.getOrgType(), (GcAuthorityType)type, (YearPeriodObject)yp);
    }

    @PostMapping(value={"/api/gcreport/v1/gcBusOrganizations/orgIconMap"})
    public BusinessResponseEntity<Map<String, NodeIconVO>> getOrgTreeIconMapV2(@RequestBody GcOrgPublicApiParam param) {
        Map<String, NodeIconVO> ret = GcOrgQueryWithStateTool.getOrgTreeIconMapV2(param);
        return BusinessResponseEntity.ok(ret);
    }

    @PostMapping(value={"/api/gcreport/v1/gcBusOrganizations/orgLevelTree/withState"})
    public BusinessResponseEntity<Object> listOrgByParent(@RequestBody GcOrgPublicApiParam param) {
        GcOrgCenterService tool = this.getTool(param);
        List data = tool.getOrgChildrenTree(param.getOrgParentCode());
        data = CollectionUtils.isEmpty(data) ? new ArrayList() : data;
        String formSchemeKey = param.getFormSchemeKey();
        Map<String, ActionStateBean> uploadState = GcOrgQueryWithStateTool.getUploadState(param.getOrgVerCode(), param.getOrgType(), param.getAdjustPeriodCode(), data.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()), formSchemeKey);
        List nodes = data.stream().map(cacheVO -> GcOrgQueryWithStateTool.decorateTreeNode(uploadState, cacheVO)).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }
}

