/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys
 *  com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.manager.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.nr.tag.manager.bean.TagImpl;
import com.jiuqi.nr.tag.manager.bean.TagManagerInitInfo;
import com.jiuqi.nr.tag.manager.bean.TagManagerPara;
import com.jiuqi.nr.tag.manager.bean.TagNodeImpl;
import com.jiuqi.nr.tag.manager.bean.TagObject;
import com.jiuqi.nr.tag.manager.bean.TagRange;
import com.jiuqi.nr.tag.manager.bean.TagsOfEntityDataModify;
import com.jiuqi.nr.tag.manager.service.impl.TagObjectService;
import com.jiuqi.nr.tag.manager.service.impl.TagServiceImpl;
import com.jiuqi.nr.tag.manager.web.response.TagsOfEntityData;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.unittree.UnitTreeI18nKeys;
import com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagObjectServiceImpl
extends TagServiceImpl
implements TagObjectService {
    @Autowired
    private SystemIdentityService identityService;
    @Resource
    private UnitTreeI18nHelper i18nHelper;

    @Override
    public boolean modifyTagsOfEntity(TagManagerPara para) {
        String viewKey = para.getViewKey();
        List<TagObject> tagList = para.getSaveTags();
        ArrayList<TagObject> addlist = new ArrayList<TagObject>(0);
        ArrayList<TagObject> updatelist = new ArrayList<TagObject>(0);
        for (TagObject tag : tagList) {
            TagImpl findByKey = this.findByKey(tag.getKey());
            if (findByKey != null) {
                updatelist.add(tag);
                continue;
            }
            addlist.add(tag);
        }
        if (!addlist.isEmpty()) {
            this.addTags(viewKey, addlist);
        }
        if (!updatelist.isEmpty()) {
            this.updateTags(viewKey, updatelist);
        }
        return true;
    }

    private void addTags(String viewKey, List<TagObject> addlist) {
        ArrayList<TagImpl> tgImpls = new ArrayList<TagImpl>();
        ArrayList<TagNodeImpl> tnImpls = new ArrayList<TagNodeImpl>();
        String currentUserId = NRSystemUtils.getCurrentUserId();
        boolean shared = this.identityService.isSystemIdentity(currentUserId);
        TagImpl tgImpl = null;
        for (TagObject tagObj : addlist) {
            tgImpl = TagImpl.assign2Impl(tagObj);
            tgImpl.setKey(UUID.randomUUID().toString());
            tgImpl.setOwner(NRSystemUtils.getCurrentUserId());
            tgImpl.setOrder(OrderGenerator.newOrder());
            tgImpl.setViewKey(viewKey);
            tgImpl.setShared(shared);
            tgImpls.add(tgImpl);
            tnImpls.addAll(this.tagNodeMapping(tgImpl, tagObj.getTagRange()));
        }
        this.tagDao.batchInsert(tgImpls);
        this.tagNodeDao.batchInsert(tnImpls);
    }

    private void updateTags(String viewKey, List<TagObject> updatelist) {
        ArrayList<TagImpl> tgImpls = new ArrayList<TagImpl>();
        ArrayList<TagNodeImpl> tnImpls = new ArrayList<TagNodeImpl>();
        ArrayList<String> delTagMapKeys = new ArrayList<String>();
        TagImpl tgImpl = null;
        for (TagObject tagObj : updatelist) {
            tgImpl = TagImpl.assign2Impl(tagObj);
            tgImpls.add(tgImpl);
            delTagMapKeys.add(tgImpl.getKey());
            tnImpls.addAll(this.tagNodeMapping(tgImpl, tagObj.getTagRange()));
        }
        this.tagDao.batchUpdate(tgImpls);
        this.tagNodeDao.batchDelEntityDataOfTag(delTagMapKeys);
        this.tagNodeDao.batchInsert(tnImpls);
    }

    private List<TagNodeImpl> tagNodeMapping(TagImpl tgImpl, TagRange tagRange) {
        List<String> entityDatas;
        ArrayList<TagNodeImpl> tnImpls = new ArrayList<TagNodeImpl>();
        if (tagRange != null && (entityDatas = tagRange.getEntDataKeys()) != null) {
            TagNodeImpl tnImpl = null;
            for (String entkey : entityDatas) {
                tnImpl = new TagNodeImpl();
                tnImpl.setEntKey(entkey);
                tnImpl.setTgKey(tgImpl.getKey());
                tnImpl.setViewkey(tgImpl.getViewKey());
                tnImpls.add(tnImpl);
            }
        }
        return tnImpls;
    }

    @Override
    public boolean modifyTagsOfEntityData(TagsOfEntityDataModify tagNodeInfo) {
        String entKey = tagNodeInfo.getEntKey();
        String viewkey = tagNodeInfo.getViewKey();
        List<String> tagKeys = tagNodeInfo.getTagKeys();
        ArrayList<TagNodeImpl> addImpls = new ArrayList<TagNodeImpl>(0);
        for (String tagKey : tagKeys) {
            TagNodeImpl tnImpl = new TagNodeImpl();
            tnImpl.setEntKey(entKey);
            tnImpl.setTgKey(tagKey);
            tnImpl.setViewkey(viewkey);
            addImpls.add(tnImpl);
        }
        List<TagNodeImpl> oriImpls = this.tagNodeDao.countOfEntityData(viewkey, entKey);
        for (TagNodeImpl oriTag : oriImpls) {
            TagImpl tagImpl;
            boolean isInAddList = false;
            for (TagNodeImpl updTag : addImpls) {
                if (!oriTag.equals(updTag)) continue;
                isInAddList = true;
                break;
            }
            if (isInAddList || this.canEditPublicTag(tagImpl = this.findByKey(oriTag.getTgKey()))) continue;
            addImpls.add(oriTag);
        }
        this.tagNodeDao.delTagOfEntityData(viewkey, entKey);
        this.tagNodeDao.batchInsert(addImpls);
        return true;
    }

    @Override
    public List<TagObject> countTagsOfEntity(String viewKey) {
        ArrayList<TagObject> tagObjs = new ArrayList<TagObject>(0);
        List<TagImpl> allTags = this.findAllByOV(viewKey);
        for (TagImpl tgImpl : allTags) {
            TagObject tagObj = TagObject.buildTagObj(tgImpl);
            TagRange tagRange = this.buidlTagRange(tgImpl);
            tagObj.setTagRange(tagRange);
            tagObjs.add(tagObj);
        }
        return tagObjs;
    }

    protected TagRange buidlTagRange(TagImpl tgImpl) {
        TagRange tagRange = new TagRange();
        tagRange.setShowText(this.i18nHelper.getMessage(UnitTreeI18nKeys.TAG_OF_SELECT_UNIT.key, UnitTreeI18nKeys.TAG_OF_SELECT_UNIT.title));
        tagRange.setEntDataKeys(new ArrayList<String>(0));
        List<TagNodeImpl> tnImpls = this.tagNodeDao.countOfTag(tgImpl.getKey());
        int rangesize = tnImpls != null ? tnImpls.size() : 0;
        for (int idx = 0; idx < rangesize; ++idx) {
            TagNodeImpl tnImpl = tnImpls.get(idx);
            tagRange.getEntDataKeys().add(tnImpl.getEntKey());
        }
        String formulaTxt = tgImpl.getFormula();
        boolean formulaNotEmpty = StringUtils.isNotEmpty((String)formulaTxt);
        String tagOfSelectedText = this.i18nHelper.getMessage(UnitTreeI18nKeys.TAG_OF_SELECTED_TEXT.key, UnitTreeI18nKeys.TAG_OF_SELECTED_TEXT.title);
        String tagOfSelectUnitCount = this.i18nHelper.getMessage(UnitTreeI18nKeys.TAG_OF_SELECT_UNIT_COUNT.key, UnitTreeI18nKeys.TAG_OF_SELECT_UNIT_COUNT.title);
        if (rangesize > 0 && formulaNotEmpty) {
            tagRange.setShowText(tagOfSelectedText + " " + rangesize + " " + tagOfSelectUnitCount + " + " + formulaTxt);
        } else if (rangesize > 0) {
            tagRange.setShowText(tagOfSelectedText + " " + rangesize + " " + tagOfSelectUnitCount);
        } else if (formulaNotEmpty) {
            tagRange.setShowText(formulaTxt);
        }
        return tagRange;
    }

    @Override
    public TagManagerInitInfo initTagManager(TagManagerPara para) {
        TagManagerInitInfo info = new TagManagerInitInfo();
        List<TagObject> tagsOfEntity = this.countTagsOfEntity(para.getViewKey());
        info.setRowDatas(tagsOfEntity);
        String currentUserId = NRSystemUtils.getCurrentUserId();
        info.setSystemUser(this.identityService.isSystemIdentity(currentUserId));
        info.setTagTitles(this.tagDao.findAllTagTitles());
        return info;
    }

    @Override
    public TagsOfEntityData countTagsOfOneEntityData(String viewKey, String entKey) {
        if (StringUtils.isEmpty((String)viewKey) || StringUtils.isEmpty((String)entKey)) {
            throw new UnitTreeRuntimeException("\u8282\u70b9\u4e0b\u7684\u6807\u8bb0\u67e5\u8be2\u5b58\u5728\u4e0d\u5408\u6cd5\u53c2\u6570\uff01");
        }
        boolean systemUser = this.identityService.isSystemIdentity(NRSystemUtils.getCurrentUserId());
        List<TagImpl> tagsOfUser = this.findAllByOV(viewKey);
        tagsOfUser.removeIf(t -> !this.canEditPublicTag((TagImpl)t));
        Map<String, List<String>> etMap = this.countTagsOfEntityDatas(viewKey, Collections.singletonList(entKey));
        List<String> list = etMap.get(entKey);
        ArrayList<String> tagKeys = new ArrayList<String>();
        if (list != null) {
            block0: for (String tagKey : list) {
                for (TagImpl tag : tagsOfUser) {
                    if (!tagKey.equals(tag.getKey())) continue;
                    tagKeys.add(tagKey);
                    continue block0;
                }
            }
        }
        TagsOfEntityData tagsOfEntityData = new TagsOfEntityData();
        tagsOfEntityData.setTagsOfUser(tagsOfUser);
        tagsOfEntityData.setTagOfEntityData(tagKeys);
        tagsOfEntityData.setSystemUser(systemUser);
        return tagsOfEntityData;
    }

    @Override
    public boolean checkTagTitleRepeat(String taTitle) {
        return this.tagDao.checkTitleRepeat(taTitle);
    }

    private boolean canEditPublicTag(TagImpl impl) {
        boolean systemUser = this.identityService.isSystemIdentity(NRSystemUtils.getCurrentUserId());
        return systemUser || impl.getShared() == false || impl.getRangeModify() != false;
    }
}

