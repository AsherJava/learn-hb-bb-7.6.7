/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineListVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO
 *  com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO
 *  com.jiuqi.va.domain.common.PageVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.dc.base.impl.orgcomb.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineListVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombDefineVO;
import com.jiuqi.dc.base.client.orgcomb.vo.OrgCombGroupVO;
import com.jiuqi.dc.base.impl.orgcomb.dto.OrgCombDefineDTO;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombDefineService;
import com.jiuqi.va.domain.common.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrgCombGroupController {
    public static final String API_BASE_PATH = "/api/datacenter/v1/orggroup/scheme";
    @Autowired
    private OrgCombDefineService orgCombDefineService;

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/add"})
    public BusinessResponseEntity<OrgCombDefineVO> add(@RequestBody OrgCombDefineVO schemeVO) {
        schemeVO = this.orgCombDefineService.add(schemeVO);
        return BusinessResponseEntity.ok((Object)schemeVO);
    }

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/update"})
    public BusinessResponseEntity<OrgCombDefineVO> update(@RequestBody OrgCombDefineVO schemeVO) {
        schemeVO = this.orgCombDefineService.update(schemeVO);
        return BusinessResponseEntity.ok((Object)schemeVO);
    }

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/delete/{id}"})
    public BusinessResponseEntity<OrgCombDefineVO> delete(@PathVariable(name="id") String id) {
        this.orgCombDefineService.delete(id);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/list"})
    public BusinessResponseEntity<PageVO<OrgCombDefineListVO>> listData(@RequestBody OrgCombDefineDTO orgCombDefineDTO) {
        return BusinessResponseEntity.ok(this.orgCombDefineService.listData(orgCombDefineDTO));
    }

    @GetMapping(value={"/api/datacenter/v1/orggroup/scheme/get/{id}"})
    public BusinessResponseEntity<OrgCombDefineVO> findData(@PathVariable(name="id") String id) {
        return BusinessResponseEntity.ok((Object)this.orgCombDefineService.findData(id));
    }

    @PostMapping(value={"/api/datacenter/v1/orggroup/scheme/updateGroupName"})
    public BusinessResponseEntity<Boolean> findData(@RequestBody OrgCombGroupVO orgCombGroupVO) {
        return BusinessResponseEntity.ok((Object)this.orgCombDefineService.updateGroupName(orgCombGroupVO));
    }
}

