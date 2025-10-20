/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.floatmodel.client.FloatRegionConfigClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.template.service.TemplateContentService
 *  com.jiuqi.va.query.tree.service.MenuTreeService
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.floatmodel.impl.controller;

import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.floatmodel.client.FloatRegionConfigClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.template.service.TemplateContentService;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FloatRegionConfigController
implements FloatRegionConfigClient {
    @Value(value="${jiuqi.np.user.system[0].name:}")
    private String username;
    @Autowired
    private MenuTreeService menuTreeService;
    @Autowired
    private TemplateContentService templateContentService;

    @GetMapping(value={"/api/gcreport/v1//floatRegion/vaQuery/tree/init"})
    public BusinessResponseEntity<List<MenuTreeVO>> treeInit() {
        BdeCommonUtil.initNpUser((String)this.username);
        return BusinessResponseEntity.ok((Object)this.menuTreeService.treeInit());
    }

    @GetMapping(value={"/api/gcreport/v1//floatRegion/vaQuery/getQueryParams/{templateCode}"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryParams(@PathVariable(value="templateCode") String templateCode) {
        BdeCommonUtil.initNpUser((String)this.username);
        return BusinessResponseEntity.ok((Object)this.templateContentService.getSimpleTemplateParams(templateCode));
    }
}

