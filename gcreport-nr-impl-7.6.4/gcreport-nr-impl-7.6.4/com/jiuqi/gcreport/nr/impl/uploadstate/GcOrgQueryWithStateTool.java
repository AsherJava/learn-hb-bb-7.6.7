/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder
 *  com.jiuiqi.nr.unit.treebase.context.UnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData
 *  com.jiuiqi.nr.unit.treebase.node.icon.IconSourceSchemeOfBBLX
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource
 *  com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceHelper
 *  com.jiuiqi.nr.unit.treebase.web.response.WorkFlowStatusConfig
 *  com.jiuiqi.nr.unit.treebase.web.service.impl.UnitTreeStaticSourceService
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.field.NodeIconVO
 *  com.jiuqi.gcreport.org.api.vo.tree.INode
 *  com.jiuqi.gcreport.org.api.vo.tree.ITree
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nr.formtype.service.IUnitTreeIconStorage
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider
 *  com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme
 *  com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfStatus
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.organization.domain.ZBDTO
 *  com.jiuqi.va.organization.service.OrgCategoryService
 */
package com.jiuqi.gcreport.nr.impl.uploadstate;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextBuilder;
import com.jiuiqi.nr.unit.treebase.context.UnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeContextData;
import com.jiuiqi.nr.unit.treebase.node.icon.IconSourceSchemeOfBBLX;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSource;
import com.jiuiqi.nr.unit.treebase.source.IUnitTreeDataSourceHelper;
import com.jiuiqi.nr.unit.treebase.web.response.WorkFlowStatusConfig;
import com.jiuiqi.nr.unit.treebase.web.service.impl.UnitTreeStaticSourceService;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.UploadStateTool;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.field.NodeIconVO;
import com.jiuqi.gcreport.org.api.vo.tree.INode;
import com.jiuqi.gcreport.org.api.vo.tree.ITree;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nr.formtype.service.IUnitTreeIconStorage;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfStatus;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.organization.domain.ZBDTO;
import com.jiuqi.va.organization.service.OrgCategoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GcOrgQueryWithStateTool {
    public static ITree<GcOrgCacheVO> decorateTreeNode(Map<String, ActionStateBean> uploadState, GcOrgCacheVO cacheVO) {
        ITree tree = new ITree((INode)cacheVO);
        ActionStateBean actionStateBean = uploadState.get(cacheVO.getCode());
        if (actionStateBean == null) {
            tree.setIcons(cacheVO.getBblx());
        } else {
            String svgKey = GcOrgQueryWithStateTool.getSVGKey(cacheVO.getBblx(), actionStateBean);
            tree.setIcons(cacheVO.getBblx() + ";" + svgKey);
        }
        return tree;
    }

    public static Map<String, ActionStateBean> getUploadState(String periodStr, String orgType, String adjustCode, List<String> orgIds, String formSchemeId) {
        if (StringUtils.isEmpty((String)formSchemeId)) {
            return new HashMap<String, ActionStateBean>();
        }
        FormSchemeDefine fromSchemeDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).getFormScheme(formSchemeId);
        TreeState treeState = (TreeState)SpringContextUtils.getBean(TreeState.class);
        DimensionValueSet dimSet = new DimensionValueSet();
        dimSet.setValue("MD_ORG", orgIds);
        dimSet.setValue("DATATIME", (Object)periodStr);
        if (DimensionUtils.isExistAdjust((String)fromSchemeDefine.getTaskKey())) {
            dimSet.setValue("ADJUST", (Object)adjustCode);
        }
        Map<DimensionValueSet, ActionStateBean> uploadStates = UploadStateTool.getInstance().getTreeWorkflowUploadState(dimSet, formSchemeId);
        HashMap<String, ActionStateBean> stateMap = new HashMap<String, ActionStateBean>();
        for (String orgId : orgIds) {
            DimensionValueSet onDimSet = new DimensionValueSet();
            onDimSet.setValue("MD_ORG", (Object)orgId);
            onDimSet.setValue("DATATIME", (Object)periodStr);
            if (DimensionUtils.isExistAdjust((String)fromSchemeDefine.getTaskKey())) {
                onDimSet.setValue("ADJUST", (Object)adjustCode);
            }
            ActionStateBean actionStateBean = uploadStates.get(onDimSet);
            stateMap.put(orgId, actionStateBean);
        }
        return stateMap;
    }

    public static Map<String, NodeIconVO> getOrgTreeIconMap(String formSchemeKey, String orgType) {
        HashMap<String, NodeIconVO> iconVOMap = new HashMap<String, NodeIconVO>(16);
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            return GcOrgQueryWithStateTool.getOrgTreeIconMapWithoutFormScheme(orgType);
        }
        UnitTreeContextData contextData = new UnitTreeContextData();
        contextData.setDataSourceId("unit-tree-entity-row-source");
        contextData.setFormScheme(formSchemeKey);
        IconSourceHelper iconProviderHelper = (IconSourceHelper)SpringContextUtils.getBean(IconSourceHelper.class);
        IUnitTreeDataSourceHelper contextWrapper = (IUnitTreeDataSourceHelper)SpringContextUtils.getBean(IUnitTreeDataSourceHelper.class);
        IUnitTreeDataSource treeContextHelper = contextWrapper.getBaseTreeDataSource(contextData.getDataSourceId());
        IUnitTreeContextBuilder contextBuilder = (IUnitTreeContextBuilder)SpringContextUtils.getBean(IUnitTreeContextBuilder.class);
        UnitTreeContext context = (UnitTreeContext)contextBuilder.createTreeContext(contextData);
        UnitTreeStaticSourceService unitTreeStaticSourceService = (UnitTreeStaticSourceService)SpringContextUtils.getBean(UnitTreeStaticSourceService.class);
        IconSourceProvider iconProvider = iconProviderHelper.getIconProvider(treeContextHelper.getSourceId(), treeContextHelper.getNodeIconSource((IUnitTreeContext)context));
        Map base64IconMap = iconProvider.getBase64IconMap();
        List dataStatusSource = unitTreeStaticSourceService.createWorkflowStatusSource((IUnitTreeContext)context);
        Map<String, WorkFlowStatusConfig> dataStatusSourceMap = dataStatusSource.stream().collect(Collectors.toMap(WorkFlowStatusConfig::getCode, o -> o));
        for (Map.Entry entry : base64IconMap.entrySet()) {
            WorkFlowStatusConfig statusSource = dataStatusSourceMap.get(entry.getKey());
            boolean bblx = ((String)entry.getKey()).startsWith("bblx-icons");
            String key = (String)entry.getKey();
            if (bblx) {
                key = ((String)entry.getKey()).split("@")[1];
            }
            String color = "";
            if (statusSource != null) {
                color = statusSource.getColor();
            }
            iconVOMap.put(key, new NodeIconVO((String)entry.getValue(), color));
        }
        return iconVOMap;
    }

    public static Map<String, NodeIconVO> getOrgTreeIconMapWithoutFormScheme(String orgType) {
        HashMap<String, NodeIconVO> iconVOMap = new HashMap<String, NodeIconVO>(16);
        if (!StringUtils.isEmpty((String)orgType)) {
            UnitTreeContextData contextData = new UnitTreeContextData();
            contextData.setDataSourceId("unit-tree-entity-row-source");
            IconSourceHelper iconProviderHelper = (IconSourceHelper)SpringContextUtils.getBean(IconSourceHelper.class);
            IUnitTreeDataSourceHelper contextWrapper = (IUnitTreeDataSourceHelper)SpringContextUtils.getBean(IUnitTreeDataSourceHelper.class);
            IUnitTreeDataSource treeContextHelper = contextWrapper.getBaseTreeDataSource(contextData.getDataSourceId());
            IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)SpringContextUtils.getBean(IFormTypeApplyService.class);
            OrgCategoryService orgCategoryService = (OrgCategoryService)SpringContextUtils.getBean(OrgCategoryService.class);
            OrgCategoryDO orgCategoryDO = new OrgCategoryDO();
            orgCategoryDO.setName(orgType);
            List zbdtos = orgCategoryService.listZB(orgCategoryDO);
            Optional<ZBDTO> zbdtoOptional = zbdtos.stream().filter(zbdto -> zbdto.getName().equalsIgnoreCase("BBLX")).findFirst();
            if (zbdtoOptional.isPresent() && !StringUtils.isEmpty((String)zbdtoOptional.get().getReltablename())) {
                String bblxTableName = zbdtoOptional.get().getReltablename();
                IconSourceSchemeOfBBLX iconSourceSchemeOfNr = null;
                iconSourceSchemeOfNr = new IconSourceSchemeOfBBLX(formTypeApplyService, bblxTableName);
                IconSourceProvider iconProvider = iconProviderHelper.getIconProvider(treeContextHelper.getSourceId(), new IconSourceScheme[]{iconSourceSchemeOfNr, new IconSourceSchemeOfStatus()});
                Map base64IconMap = iconProvider.getBase64IconMap();
                for (Map.Entry entry : base64IconMap.entrySet()) {
                    boolean bblx = ((String)entry.getKey()).startsWith("bblx-icons");
                    if (!bblx) continue;
                    iconVOMap.put(((String)entry.getKey()).split("@")[1], new NodeIconVO((String)entry.getValue(), ""));
                }
            }
        }
        if (iconVOMap.isEmpty()) {
            IUnitTreeIconStorage iUnitTreeIconStorage = (IUnitTreeIconStorage)SpringContextUtils.getBean(IUnitTreeIconStorage.class);
            Map allBase64Icon = iUnitTreeIconStorage.getAllBase64Icon();
            Set keySet = allBase64Icon.keySet();
            keySet.forEach(svgKey -> iconVOMap.put((String)svgKey, new NodeIconVO((String)allBase64Icon.get(svgKey), "")));
        }
        return iconVOMap;
    }

    public static String getSVGKey(String baseIconKey, ActionStateBean uploadState) {
        if (uploadState != null && uploadState.getCode() != null) {
            return "state-icon@" + uploadState.getCode();
        }
        return baseIconKey;
    }

    public static Map<String, NodeIconVO> getOrgTreeIconMapV2(GcOrgPublicApiParam param) {
        String orgType = param.getOrgType();
        String formSchemeKey = param.getFormSchemeKey();
        return GcOrgQueryWithStateTool.getOrgTreeIconMap(formSchemeKey, orgType);
    }
}

