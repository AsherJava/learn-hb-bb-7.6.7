/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nvwa.authority.vo.ResultObject
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.tag.management.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.tag.management.entityimpl.TagDefine;
import com.jiuqi.nr.tag.management.enumeration.TagConfigServiceStateEnum;
import com.jiuqi.nr.tag.management.environment.BaseTagContextData;
import com.jiuqi.nr.tag.management.environment.TagAddMappingsContextData;
import com.jiuqi.nr.tag.management.environment.TagAndMappingAddContextData;
import com.jiuqi.nr.tag.management.environment.TagCountContextData;
import com.jiuqi.nr.tag.management.environment.TagDeleteContextData;
import com.jiuqi.nr.tag.management.environment.TagNewOrQueryUnitRangeContextData;
import com.jiuqi.nr.tag.management.environment.TagSaveContextData;
import com.jiuqi.nr.tag.management.environment.TagSaveUnitRangeContextData;
import com.jiuqi.nr.tag.management.response.TagCountResponse;
import com.jiuqi.nr.tag.management.response.TagManagerResponse;
import com.jiuqi.nr.tag.management.response.TagManagerShowData;
import com.jiuqi.nr.tag.management.response.TagUnitRangeData;
import com.jiuqi.nr.tag.management.service.ITagManagementConfigService;
import com.jiuqi.nvwa.authority.vo.ResultObject;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/nr/api/v1/tag-manager/config"})
@Api(tags={"\u6807\u8bb0\u7ba1\u7406-\u6807\u8bb0\u4fe1\u606f\u7ef4\u62a4\u63a5\u53e3"})
public class TagManagementConfigController {
    @Resource
    private ITagManagementConfigService service;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u52a0\u8f7d\u6807\u8bb0\u7ba1\u7406\u754c\u9762\u7684\u663e\u793a\u5217\u8868")
    @PostMapping(value={"/init-tag-manager"})
    public TagManagerResponse initTagManager(@Valid @RequestBody BaseTagContextData context) {
        return this.service.queryAllTags(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u65b0\u589e\u6216\u590d\u5236\u6807\u8bb0")
    @PostMapping(value={"/new-tag"})
    public TagManagerShowData newTag(@Valid @RequestBody TagNewOrQueryUnitRangeContextData context) {
        return this.service.addOrCopyTag(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u6807\u8bb0\u7684\u5f53\u524d\u6807\u8bb0\u5355\u4f4d\u8303\u56f4")
    @PostMapping(value={"/query-unit-range"})
    public TagUnitRangeData queryUnitRange(@Valid @RequestBody TagNewOrQueryUnitRangeContextData context) {
        return this.service.queryUnitRange(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u4fdd\u5b58\u6807\u8bb0\u66f4\u65b0\u540e\u7684\u6807\u8bb0\u5355\u4f4d\u8303\u56f4")
    @PostMapping(value={"/save-unit-range"})
    public TagConfigServiceStateEnum saveUnitRange(@Valid @RequestBody @SFDecrypt TagSaveUnitRangeContextData context) {
        return this.service.saveUnitRange(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u5220\u9664\u9009\u4e2d\u6807\u8bb0")
    @PostMapping(value={"/delete-tags"})
    public TagConfigServiceStateEnum deleteTags(@Valid @RequestBody TagDeleteContextData context) {
        return this.service.deleteTags(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u4fdd\u5b58\u5f53\u524d\u6240\u6709\u6807\u8bb0\u7684\u4fe1\u606f")
    @PostMapping(value={"/save-tags"})
    public TagConfigServiceStateEnum saveTags(@Valid @RequestBody TagSaveContextData context) {
        return this.service.saveTags(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6dfb\u52a0\u6807\u8bb0\u6620\u5c04\u5173\u7cfb")
    @PostMapping(value={"/add-tag-mappings"})
    public TagConfigServiceStateEnum addTagMappings(@Valid @RequestBody TagAddMappingsContextData context) {
        return this.service.addTagMapping(context);
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u6dfb\u52a0\u6807\u8bb0\u4ee5\u53ca\u5176\u5355\u4f4d\u6620\u5c04\u5173\u7cfb")
    @PostMapping(value={"/add-tags-and-mappings"})
    public ResultObject addTagsAndMappings(@Valid @RequestBody TagAndMappingAddContextData context) {
        BaseTagContextData tagDefineContext = new BaseTagContextData();
        tagDefineContext.setEntityId(context.getEntityId());
        TagDefine tagDefine = new TagDefine();
        tagDefine.setTitle(context.getTitle());
        tagDefine.setCategory(context.getCategory());
        tagDefine.setDescription(context.getDescription());
        tagDefine.setRangeModify(false);
        String tagKey = this.service.addTagDefinePurely(tagDefineContext, tagDefine);
        for (String entityDataKey : context.getEntityDataKeys()) {
            TagAddMappingsContextData tagMappingContext = new TagAddMappingsContextData();
            tagMappingContext.setEntityData(entityDataKey);
            tagMappingContext.setTagKeys(Collections.singletonList(tagKey));
            this.service.addTagMappingPurely(tagMappingContext);
        }
        return new ResultObject();
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u7edf\u8ba1\u6807\u8bb0\u7684\u6240\u5c5e\u5355\u4f4d\u603b\u6570")
    @PostMapping(value={"/count-tags-units"})
    public TagCountResponse countTagsUnits(@Valid @RequestBody TagCountContextData context) {
        return this.service.countTagsUnits(context);
    }
}

