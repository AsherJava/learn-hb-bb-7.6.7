/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterSchemeImpl
 *  com.jiuqi.nr.unit.treestore.uselector.dao.IFilterSchemeDao
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.nr.unit.treecommon.utils.NRSystemUtils;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterSchemeImpl;
import com.jiuqi.nr.unit.treestore.uselector.dao.IFilterSchemeDao;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeInfo;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeTableData;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterTemplateHelper;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterTemplateInfo;
import com.jiuqi.nr.unit.uselector.filter.scheme.IFilterConditionMenuItem;
import com.jiuqi.nr.unit.uselector.web.service.IFilterSchemeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FilterSchemeService
implements IFilterSchemeService {
    @Resource
    private IFilterSchemeDao schemeDao;
    @Resource
    private USelectorResultSet resultSet;
    @Resource
    private IRowCheckerHelper checkerHelper;
    @Resource
    private SystemIdentityService identityService;

    @Override
    public FilterSchemeTableData loadFilterSchemes(String selector) {
        String currentUserId = NRSystemUtils.getCurrentUserId();
        IUnitTreeContext context = this.resultSet.getRunContext(selector);
        List<FilterSchemeInfo> schemeInfos = this.getFilterSchemeInfos(context);
        FilterSchemeTableData tableData = new FilterSchemeTableData();
        tableData.setCanShared(this.identityService.isSystemByUserId(currentUserId));
        tableData.setFilterSchemes(schemeInfos);
        return tableData;
    }

    @Override
    public List<FilterSchemeInfo> getFilterSchemeInfos(IUnitTreeContext context) {
        String entityId = context.getEntityDefine().getId();
        ArrayList<FilterSchemeInfo> schemeInfos = new ArrayList<FilterSchemeInfo>();
        String currentUserId = NRSystemUtils.getCurrentUserId();
        if (!this.identityService.isSystemByUserId(currentUserId)) {
            Set systemUserIds = this.identityService.getAllSystemIdentities();
            List systemItems = this.schemeDao.find(systemUserIds, entityId);
            for (USFilterScheme scheme : systemItems) {
                if (!scheme.isShared()) continue;
                FilterSchemeInfo schemeInfo = FilterSchemeInfo.assign(scheme);
                schemeInfo.setCanEdit(false);
                schemeInfos.add(schemeInfo);
            }
        }
        List currUserItems = this.schemeDao.find(currentUserId, entityId);
        for (USFilterScheme scheme : currUserItems) {
            FilterSchemeInfo schemeInfo = FilterSchemeInfo.assign(scheme);
            schemeInfo.setCanEdit(true);
            schemeInfos.add(schemeInfo);
        }
        return schemeInfos;
    }

    @Override
    public FilterSchemeTableData saveFilterScheme(String selector, FilterSchemeInfo schemeInfo) {
        this.schemeDao.updateShared(schemeInfo.getKey(), schemeInfo.isShared());
        return this.loadFilterSchemes(selector);
    }

    @Override
    public FilterTemplateInfo loadFilterTemplate(String selector, String schemeKey) {
        FilterTemplateInfo templateInfo = new FilterTemplateInfo();
        USFilterScheme scheme = this.schemeDao.find(schemeKey);
        if (null != scheme) {
            IUnitTreeContext context = this.resultSet.getRunContext(selector);
            templateInfo.setKey(scheme.getKey());
            templateInfo.setTitle(scheme.getTitle());
            templateInfo.setShared(scheme.isShared());
            templateInfo.setTemplate(FilterTemplateHelper.toJSONObject(this.checkerHelper, context, scheme.getTemplate()));
        }
        return templateInfo;
    }

    @Override
    public FilterSchemeTableData saveFilterTemplate(String selector, FilterTemplateInfo template) {
        USFilterScheme scheme = this.schemeDao.find(template.getKey());
        if (null != scheme) {
            USFilterSchemeImpl impl = USFilterScheme.assign((USFilterScheme)scheme);
            impl.setTitle(template.getTitle());
            impl.setShared(template.isShared());
            impl.setTemplate(FilterTemplateHelper.toFilterTemplate(template.getTemplate()));
            this.schemeDao.update((USFilterScheme)impl);
        } else {
            IUnitTreeContext context = this.resultSet.getRunContext(selector);
            String currentUserId = NRSystemUtils.getCurrentUserId();
            USFilterSchemeImpl impl = new USFilterSchemeImpl();
            impl.setKey(UUID.randomUUID().toString());
            impl.setEntityId(context.getEntityDefine().getId());
            impl.setOwner(currentUserId);
            impl.setTitle(template.getTitle());
            impl.setShared(template.isShared());
            impl.setTemplate(FilterTemplateHelper.toFilterTemplate(template.getTemplate()));
            this.schemeDao.insert((USFilterScheme)impl);
        }
        return this.loadFilterSchemes(selector);
    }

    @Override
    public FilterSchemeTableData removeFilterScheme(String selector, String schemeKey) {
        this.schemeDao.remove(schemeKey);
        return this.loadFilterSchemes(selector);
    }

    @Override
    public FilterSchemeTableData copyFilterScheme(String selector, String schemeKey) {
        USFilterScheme scheme = this.schemeDao.find(schemeKey);
        if (null != scheme) {
            USFilterSchemeImpl impl = USFilterScheme.assign((USFilterScheme)scheme);
            impl.setKey(UUID.randomUUID().toString());
            impl.setTitle(scheme.getTitle() + "_\u590d\u5236");
            this.schemeDao.insert((USFilterScheme)impl);
        }
        return this.loadFilterSchemes(selector);
    }

    @Override
    public List<IFilterConditionMenuItem> loadFilterConditionItem(String selector) {
        IUnitTreeContext context = this.resultSet.getRunContext(selector);
        List<IRowChecker> checkers = this.checkerHelper.getFilterSchemeCheckers(context);
        ArrayList<IFilterConditionMenuItem> menus = new ArrayList<IFilterConditionMenuItem>();
        if (null != checkers) {
            for (IRowChecker checker : checkers) {
                if (!checker.isDisplay(context)) continue;
                IFilterConditionMenuItem item = new IFilterConditionMenuItem();
                item.setKeyword(checker.getKeyword());
                item.setShowText(checker.getShowText());
                item.setType(checker.getDisplayType().toString());
                item.setCheckValues(checker.getExecutor(context).getValues());
                menus.add(item);
            }
        }
        return menus;
    }
}

