/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.BusinessResponseEntity
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.tree.enumerate.TreeNodeType
 *  com.jiuqi.va.query.tree.vo.GroupVO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.query.tree.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.tree.enumerate.TreeNodeType;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import com.jiuqi.va.query.tree.vo.GroupVO;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryTreeController {
    private static final String QUERY_TREE_BASE_API = "/api/datacenter/v1/userDefined/tree";
    @Autowired
    private MenuTreeService menuTreeService;

    @GetMapping(value={"/api/datacenter/v1/userDefined/tree/init"})
    public BusinessResponseEntity<Object> treeInit() {
        return BusinessResponseEntity.ok(this.menuTreeService.getTree());
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/tree/list/init"})
    public List<TemplateInfoVO> listInit() {
        return this.menuTreeService.listInit();
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/tree/group/save"})
    public BusinessResponseEntity<Object> groupSave(@RequestBody GroupVO groupVO) {
        return BusinessResponseEntity.ok((Object)this.menuTreeService.groupSave(groupVO));
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/tree/query/save"})
    public BusinessResponseEntity<Object> querySave(@RequestBody TemplateInfoVO baseInfoVO) {
        this.menuTreeService.querySave(baseInfoVO);
        return BusinessResponseEntity.ok();
    }

    @PostMapping(value={"/api/datacenter/v1/userDefined/tree/group/update"})
    public BusinessResponseEntity<Object> groupUpdate(@RequestBody GroupVO groupVO) {
        if (DCQueryStringHandle.isEmpty(groupVO.getId())) {
            return BusinessResponseEntity.error((String)"\u5206\u7ec4id\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.menuTreeService.groupUpdate(groupVO);
        return BusinessResponseEntity.ok();
    }

    @GetMapping(value={"/api/datacenter/v1/userDefined/tree/group/delete/{id}/{type}"})
    public BusinessResponseEntity<Object> groupDelete(@PathVariable(value="id") String id, @PathVariable(value="type") String type) {
        if (TreeNodeType.QUERY.toString().equals(type)) {
            this.menuTreeService.templateDelete(id);
        } else if (TreeNodeType.GROUP.toString().equals(type)) {
            this.menuTreeService.groupDelete(id);
        }
        return BusinessResponseEntity.ok();
    }
}

