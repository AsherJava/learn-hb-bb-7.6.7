/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.unit.treecommon.utils.IReturnObject
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.tag.manager.web;

import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.tag.manager.bean.TagImpl;
import com.jiuqi.nr.tag.manager.bean.TagManagerInitInfo;
import com.jiuqi.nr.tag.manager.bean.TagManagerPara;
import com.jiuqi.nr.tag.manager.bean.TagObject;
import com.jiuqi.nr.tag.manager.bean.TagsOfEntityDataModify;
import com.jiuqi.nr.tag.manager.bean.TagsOfEntityDataPara;
import com.jiuqi.nr.tag.manager.service.impl.TagObjectService;
import com.jiuqi.nr.tag.manager.web.response.TagsOfEntityData;
import com.jiuqi.nr.tag.manager.web.response.TagsOfEntityDataWithTagInfo;
import com.jiuqi.nr.unit.treecommon.utils.IReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/unit-tree-config/tagmanager"})
@Api(tags={"\u5355\u4f4d\u6811-\u6807\u8bb0\u7ba1\u7406\u7ec4\u4ef6"})
public class TagManagerController {
    @Resource
    private TagObjectService service;

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u5b9e\u4f53\u62e5\u6709\u7684\u6240\u6709\u6807\u8bb0")
    @GetMapping(value={"/inquery-all-tags"})
    public IReturnObject<List<TagImpl>> inqueryAllTags(@RequestParam(name="entityId", required=true) String entityId) {
        IReturnObject instance = null;
        List<TagImpl> impls = null;
        try {
            impls = this.service.findAllByOV(entityId);
            instance = IReturnObject.getSuccessInstance(impls);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            instance = IReturnObject.getErrorInstance((String)("\u5355\u4f4d\u6811\u6807\u8bb0\u67e5\u8be2\u5f02\u5e38\uff1a" + e.getMessage()), impls);
        }
        return instance;
    }

    @NRContextBuild
    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u7528\u6237\u4e0b\u7684\u6240\u6709\u6807\u8bb0")
    @RequestMapping(value={"/query-tag-impls"}, method={RequestMethod.POST})
    public IReturnObject<List<TagImpl>> queryTagImpls(@Valid @RequestBody TagManagerPara para) {
        IReturnObject rs = null;
        List<TagImpl> tagimpls = null;
        try {
            String viewKey = para.getViewKey();
            tagimpls = this.service.findAllByOV(viewKey);
            rs = IReturnObject.getSuccessInstance(tagimpls);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            rs = IReturnObject.getErrorInstance((String)("\u6807\u8bb0\u67e5\u8be2\u5f02\u5e38" + e.getMessage()), tagimpls);
        }
        return rs;
    }

    @NRContextBuild
    @ApiOperation(value="\u6807\u8bb0\u7ba1\u7406\u754c\u9762\u6807\u8bb0\u4fe1\u606f\u5217\u8868\u67e5\u8be2")
    @RequestMapping(value={"/load-tag-manager"}, method={RequestMethod.POST})
    public IReturnObject<TagManagerInitInfo> queryShowTagsInfo(@Valid @RequestBody TagManagerPara para) {
        IReturnObject rs = null;
        TagManagerInitInfo ins = null;
        try {
            ins = this.service.initTagManager(para);
            rs = IReturnObject.getSuccessInstance((Object)ins);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            rs = IReturnObject.getErrorInstance((String)("\u6807\u8bb0\u67e5\u8be2\u5f02\u5e38" + e.getMessage()), (Object)ins);
        }
        return rs;
    }

    @NRContextBuild
    @ApiOperation(value="\u6279\u91cf\u4fdd\u5b58/\u66f4\u65b0\u6807\u8bb0")
    @RequestMapping(value={"/save-tags"}, method={RequestMethod.POST})
    public IReturnObject<List<TagImpl>> saveTags(@Valid @RequestBody TagManagerPara para) {
        IReturnObject rs;
        Object tagimpls = null;
        try {
            this.service.modifyTagsOfEntity(para);
            rs = IReturnObject.getSuccessInstance((String)"\u4fdd\u5b58\u6210\u529f\uff01", tagimpls);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            rs = IReturnObject.getErrorInstance((String)("\u6807\u8bb0\u4fdd\u5b58\u5f02\u5e38\uff1a" + e.getMessage()), tagimpls);
        }
        return rs;
    }

    @NRContextBuild
    @ResponseBody
    @ApiOperation(value="\u4fdd\u5b58\u8282\u70b9\u7684\u6240\u5c5e\u6807\u8bb0")
    @PostMapping(value={"/save-node-tags"})
    public IReturnObject<String> saveNodeTags(@Valid @RequestBody TagsOfEntityDataModify tagNodeInfo) {
        IReturnObject instance = null;
        String msg = "";
        try {
            this.service.modifyTagsOfEntityData(tagNodeInfo);
            instance = IReturnObject.getSuccessInstance((Object)msg);
        }
        catch (Exception e) {
            e.printStackTrace();
            instance = IReturnObject.getErrorInstance((String)("\u5355\u4f4d\u6811-\u8282\u70b9\u6807\u8bb0\u5f02\u5e38\uff1a" + e.getMessage()), (Object)msg);
        }
        return instance;
    }

    @NRContextBuild
    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u6807\u8bb0")
    @RequestMapping(value={"/delete-tags"}, method={RequestMethod.POST})
    public IReturnObject<List<TagObject>> deleteTagMappings(@Valid @RequestBody TagManagerPara para) {
        IReturnObject rs;
        Object tagimpls = null;
        try {
            List<String> delTagKeys = para.getDelTagKeys();
            this.service.batchDelete(delTagKeys);
            rs = IReturnObject.getSuccessInstance((String)"\u5220\u9664\u6210\u529f\uff01", tagimpls);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            rs = IReturnObject.getErrorInstance((String)("\u6807\u8bb0\u5220\u9664\u5f02\u5e38\uff1a" + e.getMessage()), tagimpls);
        }
        return rs;
    }

    @NRContextBuild
    @ApiOperation(value="\u8282\u70b9\u6807\u8bb0\u67e5\u8be2")
    @RequestMapping(value={"/inquery-node-tags"}, method={RequestMethod.POST})
    public IReturnObject<TagsOfEntityData> queryOneNodeTags(@RequestBody Map<String, String> queryInfo) {
        IReturnObject instance = null;
        TagsOfEntityData nodeTags = null;
        try {
            String entkey = queryInfo.get("entKey");
            String viewkey = queryInfo.get("viewKey");
            nodeTags = this.service.countTagsOfOneEntityData(viewkey, entkey);
            instance = IReturnObject.getSuccessInstance((String)"", (Object)nodeTags);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), nodeTags);
        }
        return instance;
    }

    @NRContextBuild
    @ApiOperation(value="\u8282\u70b9\u6807\u8bb0\u4fdd\u5b58")
    @RequestMapping(value={"/modify-node-tags"}, method={RequestMethod.POST})
    public IReturnObject<String> saveOneNodeTags(@Valid @RequestBody TagsOfEntityDataModify tagNodeInfo) {
        IReturnObject instance = null;
        try {
            this.service.modifyTagsOfEntityData(tagNodeInfo);
            instance = IReturnObject.getSuccessInstance((String)"", (Object)"\u4fdd\u5b58\u6210\u529f\uff01");
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), (Object)"\u6807\u8bb0\u4e3b\u4f53\u5f02\u5e38\uff01");
        }
        return instance;
    }

    @NRContextBuild
    @RequestMapping(value={"/check-title-repeat"}, method={RequestMethod.GET})
    public IReturnObject<String> checkTitleRepeat(@RequestParam(name="tagTitle") String tagTitle) {
        IReturnObject instance = null;
        try {
            boolean isRepeat = this.service.checkTagTitleRepeat(tagTitle);
            instance = IReturnObject.getSuccessInstance(null, (Object)"");
            if (isRepeat) {
                instance = IReturnObject.getErrorInstance((String)"\u6807\u8bb0\u540d\u79f0\u91cd\u590d\uff01", (Object)"");
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), (Object)"\u540e\u53f0\u68c0\u67e5\u6807\u9898\u5f02\u5e38\uff01");
        }
        return instance;
    }

    @NRContextBuild
    @ApiOperation(value="\u67e5\u8be2\u5f53\u524d\u7528\u6237\u4e0b\u7684\u4e3b\u4f53\u6570\u636e\u8282\u70b9\u7684\u6807\u8bb0")
    @RequestMapping(value={"/query-node-tags-taginfos"}, method={RequestMethod.POST})
    public IReturnObject<TagsOfEntityDataWithTagInfo> queryNodeTagsInfo(@Valid @RequestBody TagsOfEntityDataPara para) {
        IReturnObject instance = null;
        TagsOfEntityDataWithTagInfo info = new TagsOfEntityDataWithTagInfo();
        try {
            List<TagImpl> findAllByOV = this.service.findAllByOV(para.getViewKey());
            HashMap<String, TagImpl> map = new HashMap<String, TagImpl>();
            for (TagImpl tag : findAllByOV) {
                map.put(tag.getKey(), tag);
            }
            info.setTagInfos(map);
            info.setNode2TagsMap(this.service.countTagsOfEntityDatas(para.getViewKey(), para.getEntityDataKeys()));
            instance = IReturnObject.getSuccessInstance(null, (Object)info);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            instance = IReturnObject.getErrorInstance((String)e.getMessage(), (Object)info);
        }
        return instance;
    }
}

