/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.dto.ResourceExtDTO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO
 *  com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO
 *  com.jiuqi.nr.task.api.resource.dto.ResourceDTO
 *  com.jiuqi.nr.task.api.resource.dto.SearchParam
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.web.rest;

import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.dto.ResourceExtDTO;
import com.jiuqi.nr.task.api.resource.domain.ResourceCategoryDO;
import com.jiuqi.nr.task.api.resource.domain.ResourceSearchResultDO;
import com.jiuqi.nr.task.api.resource.dto.ResourceCategoryDTO;
import com.jiuqi.nr.task.api.resource.dto.ResourceDTO;
import com.jiuqi.nr.task.api.resource.dto.SearchParam;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.service.IFormSchemeResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/formScheme/resource"})
@Api(tags={"\u62a5\u8868\u65b9\u6848\u8d44\u6e90\u7ba1\u7406"})
public class FormSchemeResourceController {
    @Autowired
    private IFormSchemeResourceService formSchemeResourceService;

    @ApiOperation(value="\u67e5\u8be2\u8d44\u6e90\u6269\u5c55")
    @GetMapping(value={"/init/{formSchemeKey}"})
    public List<ResourceExtDTO> initResourceType(@PathVariable String formSchemeKey) {
        return this.formSchemeResourceService.queryResourceType(formSchemeKey);
    }

    @ApiOperation(value="\u641c\u7d22\u8d44\u6e90")
    @PostMapping(value={"/search"})
    public List<ResourceSearchResultDO> search(@RequestBody SearchParam resourceSearch) {
        return this.formSchemeResourceService.search(resourceSearch);
    }

    @ApiOperation(value="\u67e5\u8be2\u8d44\u6e90\u76ee\u5f55\u6839\u8282\u70b9")
    @PostMapping(value={"/category/root"})
    public List<UITreeNode<ResourceCategoryDO>> getRootCategory(@RequestBody ResourceCategoryDTO categoryDTO) {
        return this.formSchemeResourceService.getRootCategory(categoryDTO);
    }

    @ApiOperation(value="\u67e5\u8be2\u8d44\u6e90\u76ee\u5f55\u4e0b\u7ea7\u76ee\u5f55")
    @PostMapping(value={"/category/children"})
    public List<UITreeNode<ResourceCategoryDO>> getChildrenCategory(@RequestBody ResourceCategoryDTO categoryDTO) {
        return this.formSchemeResourceService.getChildrenCategory(categoryDTO);
    }

    @ApiOperation(value="\u67e5\u8be2\u8d44\u6e90")
    @PostMapping(value={"/resource"})
    public ResourceDTO queryResource(@RequestBody ResourceDTO resourceDTO) {
        return this.formSchemeResourceService.queryResource(resourceDTO);
    }

    @ApiOperation(value="\u67e5\u8be2\u8d44\u6e90\u76ee\u5f55")
    @PostMapping(value={"/category/loadCategoryByResource"})
    public List<UITreeNode<ResourceCategoryDO>> queryCategoryByResource(@RequestBody ResourceDTO resourceDTO) {
        return this.formSchemeResourceService.queryCategoryByResource(resourceDTO);
    }
}

