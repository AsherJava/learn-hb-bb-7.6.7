/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextCache
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper
 *  com.jiuqi.nr.itreebase.collection.IFilterStringList
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme
 *  com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.uselector.web.service.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextCache;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.itreebase.collection.IFilterCacheSetHelper;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMDisplayScheme;
import com.jiuqi.nr.unit.treestore.fmdmdisplay.intf.FMDMDisplaySchemeService;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckerHelper;
import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeInfo;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSource;
import com.jiuqi.nr.unit.uselector.source.IUSelectorDataSourceHelper;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import com.jiuqi.nr.unit.uselector.web.response.ContextInfo;
import com.jiuqi.nr.unit.uselector.web.response.IMenuItem;
import com.jiuqi.nr.unit.uselector.web.response.QuickMenuInfo;
import com.jiuqi.nr.unit.uselector.web.service.IFilterSchemeService;
import com.jiuqi.nr.unit.uselector.web.service.IUSelectorInitService;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class USelectorInitService
implements IUSelectorInitService {
    @Resource
    private USelectorResultSet resultSet;
    @Resource
    private IUnitTreeContextCache contextCache;
    @Resource
    private IFilterCacheSetHelper cacheSetHelper;
    @Resource
    private IUnitTreeContextBuilder contextBuilder;
    @Resource
    private IUSelectorDataSourceHelper dataSourceHelper;
    @Resource
    private IFilterSchemeService filterSchemeService;
    @Resource
    private IRowCheckerHelper checkerHelper;
    @Resource
    private INvwaSystemOptionService sysOption;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private FMDMDisplaySchemeService displaySchemeService;
    private static final String checkerListKey = "checkerList";

    @Override
    public ContextInfo loadContext(UnitTreeContextData contextData) {
        IUnitTreeContext treeContext = this.contextBuilder.createTreeContext(contextData);
        IEntityDefine entityDefine = treeContext.getEntityDefine();
        if (entityDefine == null) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u5b9e\u4f53ID\u3010" + contextData.getEntityId() + "\u3011");
        }
        IUSelectorDataSource baseTreeDataSource = this.dataSourceHelper.getBaseTreeDataSource(treeContext.getDataSourceId());
        if (baseTreeDataSource == null) {
            throw new UnitTreeRuntimeException("\u65e0\u6548\u7684\u8d44\u6e90ID\u3010" + treeContext.getDataSourceId() + "\u3011");
        }
        String selector = UUID.randomUUID() + "@" + entityDefine.getCode();
        this.initFilterResultSet(baseTreeDataSource, treeContext, selector, contextData.getChecklist());
        contextData.setChecklist(null);
        this.contextCache.putContextData(selector, contextData);
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setSelectorKey(selector);
        contextInfo.setTaskId(treeContext.getTaskDefine() != null ? treeContext.getTaskDefine().getKey() : null);
        contextInfo.setMainDimQuery(treeContext.getEntityQueryPloy() == IEntityQueryPloy.MAIN_DIM_QUERY);
        contextInfo.setCanChangeTaskAtFormulaEditor(this.canChangeTaskAtFormulaEditor(treeContext));
        contextInfo.setQuerySourceAtFormulaEditor(this.isQuerySourceAtFormulaEditor(treeContext));
        contextInfo.setActionOperateMode(this.sysOption.get("uselector_system_config", "uselector_action_opt_mode"));
        contextInfo.setParams(this.getCustomParams(treeContext));
        return contextInfo;
    }

    private Map<String, Object> getCustomParams(IUnitTreeContext treeContext) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("showCodeInfo", this.isShowCodeFieldInfo(treeContext));
        return params;
    }

    private boolean isShowCodeFieldInfo(IUnitTreeContext treeContext) {
        IEntityDefine entityDefine = treeContext.getEntityDefine();
        IEntityModel entityModel = this.metaService.getEntityModel(entityDefine.getId());
        FormSchemeDefine formScheme = treeContext.getFormScheme();
        if (formScheme != null) {
            FMDMDisplayScheme displayScheme = this.displaySchemeService.getCurrentDisplayScheme(treeContext.getFormScheme().getKey(), entityDefine.getId());
            return displayScheme != null && displayScheme.getFields() != null && displayScheme.getFields().contains(entityModel.getCodeField().getID());
        }
        return false;
    }

    private List<String> getCheckerList(JSONObject customVariable) {
        List<String> checkerList = new ArrayList<String>();
        if (customVariable != null && customVariable.has(checkerListKey)) {
            checkerList = JavaBeanUtils.toJavaArray((String)customVariable.getJSONArray(checkerListKey).toString(), String.class);
        }
        return checkerList;
    }

    @Override
    public QuickMenuInfo getQuickSelectionMenus(String selector) {
        IUnitTreeContext context = this.resultSet.getRunContext(selector);
        List<String> checkerList = this.getCheckerList(context.getCustomVariable());
        List<IRowChecker> checkers = this.checkerHelper.getCheckersByGroup(CheckerGroup.QUICK_SELECTION);
        ArrayList<IMenuItem> menus = new ArrayList<IMenuItem>();
        if (null != checkers) {
            for (IRowChecker checker : checkers) {
                IFilterCheckValues values;
                List<Map<String, String>> listV;
                if (!checkerList.isEmpty() && !checkerList.contains(checker.getKeyword()) || !checker.isDisplay(context) || (listV = (values = checker.getExecutor(context).getValues()).getValues()) == null || listV.isEmpty()) continue;
                IMenuItem item = new IMenuItem();
                item.setKeyword(checker.getKeyword());
                item.setShowText(checker.getShowText());
                item.setCheckValues(values);
                menus.add(item);
            }
        }
        QuickMenuInfo quickMenuInfo = new QuickMenuInfo();
        quickMenuInfo.setMenuItems(menus);
        quickMenuInfo.setCheckMode(this.sysOption.get("uselector_system_config", "uselector_quick_check_mode"));
        return quickMenuInfo;
    }

    @Override
    public List<IMenuItem> loadFilterSchemeMenus(String selector) {
        IUnitTreeContext runContext = this.resultSet.getRunContext(selector);
        ArrayList<IMenuItem> menus = new ArrayList<IMenuItem>();
        List<FilterSchemeInfo> filterSchemes = this.filterSchemeService.getFilterSchemeInfos(runContext);
        for (FilterSchemeInfo schemeInfo : filterSchemes) {
            IMenuItem item = new IMenuItem();
            item.setKeyword("#check-with-filter-scheme");
            item.setShowText(schemeInfo.getTitle());
            item.setCheckValues(this.buildFilterCheckValues(schemeInfo));
            menus.add(item);
        }
        return menus;
    }

    private IFilterCheckValues buildFilterCheckValues(FilterSchemeInfo schemeInfo) {
        IFilterCheckValuesImpl checkValuesImpl = new IFilterCheckValuesImpl();
        ArrayList<Map<String, String>> values = new ArrayList<Map<String, String>>();
        values.add(new HashMap());
        ((Map)values.get(0)).put("text", schemeInfo.getTitle());
        ((Map)values.get(0)).put("value", schemeInfo.getKey());
        checkValuesImpl.setValues(values);
        return checkValuesImpl;
    }

    private void initFilterResultSet(IUSelectorDataSource baseTreeDataSource, IUnitTreeContext treeContext, String selector, List<String> checklist) {
        IUSelectorEntityRowProvider entityRowProvider;
        List<IEntityRow> checkRows;
        if (checklist != null && !checklist.isEmpty() && (checkRows = (entityRowProvider = baseTreeDataSource.getUSelectorEntityRowProvider(treeContext)).getCheckRows(checklist)) != null && !checkRows.isEmpty()) {
            IFilterStringList cacheSetInRedis = this.cacheSetHelper.getInstance(selector);
            cacheSetInRedis.unionAll(checkRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
        }
    }

    private boolean canChangeTaskAtFormulaEditor(IUnitTreeContext treeContext) {
        FormSchemeDefine formScheme = treeContext.getFormScheme();
        if (formScheme != null) {
            List taskLinkDefines = this.runTimeViewController.queryLinksByCurrentFormScheme(formScheme.getKey());
            return treeContext.getTaskDefine() == null || !taskLinkDefines.isEmpty();
        }
        return treeContext.getTaskDefine() == null;
    }

    private boolean isQuerySourceAtFormulaEditor(IUnitTreeContext treeContext) {
        boolean canChangeTaskAtFormulaEditor = this.canChangeTaskAtFormulaEditor(treeContext);
        if (canChangeTaskAtFormulaEditor) {
            return treeContext.getEntityQueryPloy() == IEntityQueryPloy.MAIN_DIM_QUERY && treeContext.getTaskDefine() != null && !this.runTimeViewController.queryLinksByCurrentFormScheme(treeContext.getFormScheme().getKey()).isEmpty();
        }
        return false;
    }
}

