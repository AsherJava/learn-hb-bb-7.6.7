/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.manager.service.impl;

import com.jiuqi.nr.tag.manager.bean.TagImpl;
import com.jiuqi.nr.tag.manager.bean.TagNodeImpl;
import com.jiuqi.nr.tag.manager.dao.TagDao;
import com.jiuqi.nr.tag.manager.dao.TagNodeDao;
import com.jiuqi.nr.tag.manager.service.impl.TagService;
import com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl
implements TagService {
    @Resource
    protected TagDao tagDao;
    @Resource
    protected TagNodeDao tagNodeDao;

    @Override
    public int batchDelete(List<String> tagKeys) {
        this.tagNodeDao.batchDelEntityDataOfTag(tagKeys);
        return this.tagDao.batchDelete(tagKeys);
    }

    @Override
    public TagImpl findByKey(String key) {
        return this.tagDao.findByKey(key);
    }

    @Override
    public List<TagImpl> findAllByOV(String viewKey) {
        return this.tagDao.findAllByOV(NRSystemUtils.getCurrentUserId(), viewKey);
    }

    @Override
    public int sumEntityDatasOfTag(String tgKey) {
        return this.tagNodeDao.sumEntityDatasOfTag(tgKey);
    }

    @Override
    public Map<String, List<String>> countTagsOfEntityDatas(String viewkey, List<String> entKeys) {
        HashMap<String, List<String>> rs = new HashMap<String, List<String>>(0);
        List<TagNodeImpl> tnImpls = this.tagNodeDao.countOfEntityDatas(viewkey, entKeys);
        for (TagNodeImpl tnImpl : tnImpls) {
            String entKey = tnImpl.getEntKey();
            String tgKey = tnImpl.getTgKey();
            ArrayList<String> tagKeys = (ArrayList<String>)rs.get(entKey);
            if (tagKeys != null) {
                tagKeys.add(tgKey);
                continue;
            }
            tagKeys = new ArrayList<String>(0);
            tagKeys.add(tgKey);
            rs.put(entKey, tagKeys);
        }
        return rs;
    }

    @Override
    public Map<String, List<String>> countEntityDatasOfTags(List<String> tagKeys) {
        HashMap<String, List<String>> rs = new HashMap<String, List<String>>(0);
        if (tagKeys != null) {
            for (String tgKey : tagKeys) {
                List<TagNodeImpl> tnImpls = this.tagNodeDao.countOfTag(tgKey);
                rs.put(tgKey, new ArrayList(0));
                tnImpls.forEach(tnimpl -> ((List)rs.get(tgKey)).add(tnimpl.getEntKey()));
            }
        }
        return rs;
    }
}

