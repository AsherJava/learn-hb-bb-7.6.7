/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.unionrule.i18n;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.unionrule.i18n.unionRuleI18Service;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnionRuleI18nResource
implements I18NResource {
    private static final long serialVersionUID = 1L;
    @Autowired
    private unionRuleI18Service unionRuleService;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;

    public String name() {
        return "\u5408\u5e76\u62a5\u8868/\u5408\u5e76\u4f53\u7cfb\\\u5408\u5e76\u89c4\u5219";
    }

    public String getNameSpace() {
        return "gc";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (StringUtils.isEmpty((CharSequence)parentId)) {
            List<ConsolidatedSystemEO> allSystem = this.consolidatedSystemDao.findAllSystemsWithOrder();
            if (!CollectionUtils.isEmpty(allSystem)) {
                allSystem.forEach(system -> resourceObjects.add(new I18NResourceItem(system.getId(), system.getSystemName())));
            }
            return resourceObjects;
        }
        List<UnionRuleVO> allRule = this.unionRuleService.loadRuleListBySystem(parentId);
        if (!CollectionUtils.isEmpty(allRule)) {
            for (UnionRuleVO rule : allRule) {
                resourceObjects.add(new I18NResourceItem(rule.getId(), rule.getTitle()));
            }
        }
        return resourceObjects;
    }

    public List<I18NResourceItem> getCategory(String parentId) {
        ArrayList<I18NResourceItem> groups = new ArrayList<I18NResourceItem>();
        List<ConsolidatedSystemEO> allSystem = this.consolidatedSystemDao.findAllSystemsWithOrder();
        if (!CollectionUtils.isEmpty(allSystem)) {
            allSystem.forEach(system -> groups.add(new I18NResourceItem(system.getId(), system.getSystemName())));
        }
        return groups;
    }
}

