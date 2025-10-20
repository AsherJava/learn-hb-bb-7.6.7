/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.front.FrontModelDefine
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.model.I18nPluginManager
 *  com.jiuqi.va.biz.impl.model.ModelDefineImpl
 *  com.jiuqi.va.biz.impl.model.PluginCheckManager
 *  com.jiuqi.va.biz.impl.model.PluginDataDelManager
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.group.ActionGroup
 *  com.jiuqi.va.biz.intf.action.group.ActionGroupManager
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.data.DataTableNodeContainer
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDataDel
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.intf.value.TypedContainer
 *  com.jiuqi.va.biz.ruler.impl.FormulaRulerItem
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 *  com.jiuqi.va.domain.biz.DeployDTO
 *  com.jiuqi.va.domain.biz.MetaDataDTO
 *  com.jiuqi.va.domain.biz.ModelDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.i18n.domain.VaI18nResourceDTO
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.i18n.feign.VaI18nClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.bill.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.bill.domain.BillMetaGetDefineDTO;
import com.jiuqi.va.bill.domain.BillPluginsDTO;
import com.jiuqi.va.bill.domain.BillRulerDO;
import com.jiuqi.va.bill.domain.BillRulerTreeDO;
import com.jiuqi.va.bill.domain.action.BillQueryActionDTO;
import com.jiuqi.va.bill.impl.BillDefineImpl;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.service.MetaInfoService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.model.I18nPluginManager;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginCheckManager;
import com.jiuqi.va.biz.impl.model.PluginDataDelManager;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.group.ActionGroup;
import com.jiuqi.va.biz.intf.action.group.ActionGroupManager;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDataDel;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.domain.biz.DeployDTO;
import com.jiuqi.va.domain.biz.MetaDataDTO;
import com.jiuqi.va.domain.biz.ModelDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.i18n.domain.VaI18nResourceDTO;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.i18n.feign.VaI18nClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.utils.VaI18nParamUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bill"})
public class BillMetaController {
    private static final Logger log = LoggerFactory.getLogger(BillMetaController.class);
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private ActionManager actionManager;
    @Autowired
    private ActionGroupManager actionGroupManager;
    @Autowired
    private PluginCheckManager pluginCheckManager;
    @Autowired
    private PluginDataDelManager pluginDataDelManager;
    @Autowired
    private I18nPluginManager i18nPluginManager;
    @Autowired
    private VaI18nClient vaDataResourceClient;
    @Autowired
    private MetaInfoService metaInfoService;

    @PostMapping(value={"/define/get"})
    @RequiresPermissions(value={"bill:meta:get"})
    public com.jiuqi.va.biz.utils.R<BillDefine> getDefine(@RequestBody BillMetaGetDefineDTO params) {
        return com.jiuqi.va.biz.utils.R.ok((Object)this.billDefineService.getDefine(params.getDefineCode()));
    }

    @PostMapping(value={"/action/list"})
    @RequiresPermissions(value={"bill:meta:get"})
    public com.jiuqi.va.biz.utils.R<List<Map<String, Object>>> getActions(@PathVariable(value="modelName") String modelName) {
        return this.getBillActionsByModelName(modelName);
    }

    @PostMapping(value={"/publish"})
    public com.jiuqi.va.biz.utils.R<?> deploy(@RequestBody DeployDTO deployDTO) {
        try {
            this.metaInfoService.syncMetaInfo(deployDTO);
            return com.jiuqi.va.biz.utils.R.ok();
        }
        catch (Exception e) {
            log.error("\u540c\u6b65\u5143\u6570\u636e\u8868\u540d\u79f0\u7f13\u5b58\u5931\u8d25:" + e.getMessage(), e);
            return com.jiuqi.va.biz.utils.R.error((String)e.getMessage(), (Throwable)e);
        }
    }

    @PostMapping(value={"/defines/{defineName}/get"})
    public com.jiuqi.va.biz.utils.R<BillDefine> getDefine(@PathVariable(value="defineName") String defineName) {
        try {
            return com.jiuqi.va.biz.utils.R.ok((Object)this.billDefineService.getDefine(defineName));
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @PostMapping(value={"/models/get"})
    public PageVO<ModelDTO> getModels(@RequestBody ModelDTO modelDTO) {
        PageVO pageVO = new PageVO();
        ArrayList<ModelDTO> dtos = new ArrayList<ModelDTO>();
        pageVO.setRows(dtos);
        try {
            List modelTypes = this.modelManager.getModelList("bill", modelDTO.getModule());
            for (ModelType modelType : modelTypes) {
                ModelDTO dto = new ModelDTO();
                dto.setModelName(modelType.getName());
                dto.setModelTitle(modelType.getTitle());
                dto.setMetaType(modelType.getMetaType());
                dto.setModule(modelDTO.getModule());
                dtos.add(dto);
            }
            pageVO.setRs(R.ok());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            pageVO.setRs(R.error((String)e.getMessage()));
        }
        return pageVO;
    }

    @PostMapping(value={"/modelType/dependent/list"})
    public com.jiuqi.va.biz.utils.R<Set<String>> getDependModelTypes(@RequestBody MetaDataDTO metaDataDTO) {
        try {
            HashSet parents = new HashSet();
            ModelType modelType = (ModelType)this.modelManager.find(metaDataDTO.getModelName());
            if (modelType == null) {
                return com.jiuqi.va.biz.utils.R.error((String)("\u6a21\u578b\u4e0d\u5b58\u5728\uff1a" + metaDataDTO.getModelName()));
            }
            this.modelManager.stream().forEach(o -> {
                if (o.getClass().isAssignableFrom(modelType.getClass())) {
                    parents.add(o.getName());
                }
            });
            return com.jiuqi.va.biz.utils.R.ok(parents);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return com.jiuqi.va.biz.utils.R.error((String)e.getMessage());
        }
    }

    @PostMapping(value={"/metas/get"})
    public R createDefine(@RequestBody MetaDataDTO metaDataDTO) {
        R r = R.ok();
        try {
            ModelType modelType = (ModelType)this.modelManager.find(metaDataDTO.getModelName());
            BillDefineImpl billDefine = (BillDefineImpl)modelType.getModelDefineClass().newInstance();
            modelType.initModelDefine((ModelDefine)billDefine, metaDataDTO.getUniqueCode());
            ObjectMapper mapper = new ObjectMapper();
            String billDefineString = mapper.writeValueAsString((Object)billDefine);
            Map dataMeta = JSONUtil.parseMap((String)billDefineString);
            r.put("data", (Object)dataMeta);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)BillCoreI18nUtil.getMessage("va.billcore.billmetacontroller.acquirefailed"));
        }
        return r;
    }

    @GetMapping(value={"/modelType/get"})
    public R getBillModelType() {
        R r = R.ok();
        try {
            Stream stream = this.modelManager.stream();
            r.put("modelTypes", stream.filter(o -> o.getMetaType().equals("bill")).collect(Collectors.toList()));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)BillCoreI18nUtil.getMessage("va.billcore.billmetacontroller.acquirefailed"));
        }
        return r;
    }

    @PostMapping(value={"/model/rulers"})
    public com.jiuqi.va.biz.utils.R<List<BillRulerDO>> getBillRulers(@RequestBody BillRulerTreeDO param) {
        ArrayList<BillRulerDO> rulers = new ArrayList<BillRulerDO>();
        ModelType modelType = (ModelType)this.modelManager.find(param.getModelName());
        try {
            BillDefineImpl billDefine = (BillDefineImpl)modelType.getModelDefineClass().newInstance();
            modelType.initModelDefine((ModelDefine)billDefine, param.getModelName());
            List itemList = billDefine.getRuler().getItemList();
            for (RulerItem ruler : itemList) {
                if (ruler instanceof FormulaRulerItem) continue;
                Set triggerTypes = ruler.getTriggerTypes();
                if (triggerTypes != null && triggerTypes.size() > 0) {
                    int i = 0;
                    for (String triggerType : triggerTypes) {
                        ++i;
                        BillRulerDO rulerDO = new BillRulerDO();
                        if (triggerType.equals("after-add-row")) {
                            rulerDO.setTriggerType("AfterAddRow");
                        } else if (triggerType.equals("after-del-row")) {
                            rulerDO.setTriggerType("AfterDelRow");
                        } else if (triggerType.equals("after-set-value")) {
                            rulerDO.setTriggerType("AfterSetValue");
                        } else if (triggerType.startsWith("before-")) {
                            rulerDO.setTriggerType(triggerType.substring(triggerType.indexOf("-") + 1));
                        } else {
                            rulerDO.setTriggerType(triggerType);
                        }
                        rulerDO.setObjectType(ruler.getObjectType());
                        rulerDO.setPropertyType(ruler.getPropertyType());
                        rulerDO.setObjectId(ruler.getObjectId().toString());
                        if (triggerTypes.size() > 0) {
                            rulerDO.setId(ruler.getName() + "_" + i);
                        } else {
                            rulerDO.setId(ruler.getName());
                        }
                        rulerDO.setId(ruler.getName());
                        rulerDO.setTitle(ruler.getTitle());
                        rulerDO.setFixed(true);
                        rulerDO.setCheckState("SUCCESS");
                        rulerDO.setUsed(true);
                        rulerDO.setName(ruler.getName());
                        rulers.add(rulerDO);
                    }
                    continue;
                }
                BillRulerDO rulerDO = new BillRulerDO();
                rulerDO.setObjectType(ruler.getObjectType());
                rulerDO.setPropertyType(ruler.getPropertyType());
                rulerDO.setObjectId(ruler.getObjectId().toString());
                rulerDO.setId(ruler.getName());
                rulerDO.setTitle(ruler.getTitle());
                rulerDO.setFixed(true);
                rulerDO.setCheckState("SUCCESS");
                rulerDO.setUsed(true);
                rulerDO.setName(ruler.getName());
                rulers.add(rulerDO);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return com.jiuqi.va.biz.utils.R.ok(rulers);
    }

    @Deprecated
    @PostMapping(value={"/model/{modelName}/actions"})
    public com.jiuqi.va.biz.utils.R<List<Map<String, Object>>> getBillActionsByModelName(@PathVariable(value="modelName") String modelName) {
        com.jiuqi.va.biz.utils.R r;
        try {
            ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            r = com.jiuqi.va.biz.utils.R.ok(result);
            this.getActionsByModelName(modelName, result);
        }
        catch (Exception e) {
            r = com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
        return r;
    }

    @PostMapping(value={"/actions"})
    public com.jiuqi.va.biz.utils.R<List<Map<String, Object>>> getBillActions(@RequestBody BillQueryActionDTO param) {
        com.jiuqi.va.biz.utils.R r;
        List actions = this.actionManager.getAllActionList();
        String modelName = param.getModelName();
        try {
            List forbiddenActions;
            ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            r = com.jiuqi.va.biz.utils.R.ok(result);
            if (StringUtils.hasText(modelName)) {
                ModelType modelType = (ModelType)this.modelManager.get(modelName);
                forbiddenActions = modelType.getForbiddenActions();
            } else {
                forbiddenActions = null;
            }
            actions = actions.stream().filter(o -> {
                if (!CollectionUtils.isEmpty(forbiddenActions) && forbiddenActions.contains(o.getName())) {
                    return false;
                }
                return param.getActionCategory() == null || param.getActionCategory().equals((Object)o.getActionCategory());
            }).collect(Collectors.toList());
            actions.sort((o1, o2) -> o1.getSortFlag() > o2.getSortFlag() ? 1 : -1);
            this.dealActionResult(result, actions);
        }
        catch (Exception e) {
            r = com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
        return r;
    }

    @PostMapping(value={"/model/actions"})
    public com.jiuqi.va.biz.utils.R<List<Map<String, Object>>> getAllActions() {
        List actionList = this.actionManager.getAllActionList();
        ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        actionList.sort((o1, o2) -> o1.getSortFlag() > o2.getSortFlag() ? 1 : -1);
        this.dealActionResult(result, actionList);
        return com.jiuqi.va.biz.utils.R.ok(result);
    }

    private void dealActionResult(List<Map<String, Object>> result, List<? extends Action> actionList) {
        actionList.stream().filter(o -> "bill-next-step".equals(o.getName()) || "bill-back-step".equals(o.getName()) || !o.isInner()).forEach(action -> {
            ActionGroup actionGroup = this.actionGroupManager.getActionGroupById(action.getGroupId());
            if (actionGroup == null) {
                actionGroup = this.actionGroupManager.getActionGroupById("00");
            }
            HashMap<String, String> tableObject = new HashMap<String, String>();
            result.add(tableObject);
            tableObject.put("name", action.getName());
            tableObject.put("title", action.getTitle());
            tableObject.put("icon", action.getIcon());
            tableObject.put("desc", action.getDesc());
            tableObject.put("groupTitle", actionGroup.getTitle());
            tableObject.put("groupName", actionGroup.getName());
        });
    }

    private void getActionsByModelName(String modelName, List<Map<String, Object>> result) {
        ModelType modelType = (ModelType)this.modelManager.get(modelName);
        List forbiddenActions = modelType.getForbiddenActions();
        List actions = this.actionManager.getActionList(modelType.getModelClass());
        if (!CollectionUtils.isEmpty(forbiddenActions)) {
            actions = actions.stream().filter(o -> !forbiddenActions.contains(o.getName())).collect(Collectors.toList());
        }
        actions.sort((o1, o2) -> o1.getSortFlag() > o2.getSortFlag() ? 1 : -1);
        this.dealActionResult(result, actions);
    }

    @Deprecated
    @PostMapping(value={"/{billDefineCode}/actions"})
    public com.jiuqi.va.biz.utils.R<List<Map<String, Object>>> getBillActionsByBillDefine(@PathVariable(value="billDefineCode") String billDefineCode) {
        com.jiuqi.va.biz.utils.R r;
        try {
            ArrayList<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            r = com.jiuqi.va.biz.utils.R.ok(result);
            BillDefine billDefine = this.billDefineService.getDefine(billDefineCode);
            this.getActionsByModelName(billDefine.getModelType(), result);
        }
        catch (Exception e) {
            r = com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
        return r;
    }

    @Deprecated
    @PostMapping(value={"/{billdefine}/fields"})
    public com.jiuqi.va.biz.utils.R<List<Map<String, Object>>> getBillFields(@PathVariable(value="billdefine") String billdefine) {
        com.jiuqi.va.biz.utils.R r = null;
        try {
            ArrayList result = new ArrayList();
            r = com.jiuqi.va.biz.utils.R.ok(result);
            BillDefine billDefine = this.billDefineService.getDefine(billdefine);
            billDefine.getData().getTables().stream().forEach(table -> {
                String tableName = table.getName();
                if (tableName.endsWith("_M")) {
                    return;
                }
                HashMap<String, Object> tableObject = new HashMap<String, Object>();
                result.add(tableObject);
                tableObject.put("tableName", tableName);
                tableObject.put("tableTitle", table.getTitle());
                ArrayList fields = new ArrayList();
                tableObject.put("fields", fields);
                table.getFields().stream().forEach(field -> {
                    String fieldName = field.getName();
                    if (fieldName.startsWith("CALC_")) {
                        return;
                    }
                    HashMap<String, String> fieldObject = new HashMap<String, String>();
                    fields.add(fieldObject);
                    fieldObject.put("name", fieldName);
                    fieldObject.put("title", field.getTitle());
                });
            });
        }
        catch (Exception e) {
            r = com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
        return r;
    }

    @GetMapping(value={"/front/{defineName}"})
    public com.jiuqi.va.biz.utils.R<FrontModelDefine> getFrontModelDefine(@PathVariable(value="defineName") String defineName) {
        return this.getFrontModelDefine(defineName, "view");
    }

    @GetMapping(value={"/front/{defineName}/{viewName}"})
    public com.jiuqi.va.biz.utils.R<FrontModelDefine> getFrontModelDefine(@PathVariable(value="defineName") String defineName, @PathVariable(value="viewName") String viewName) {
        try {
            BillDefine define = this.billDefineService.getDefine(defineName);
            FrontModelDefine frontModelDefine = new FrontModelDefine((ModelDefine)define, false, viewName);
            return com.jiuqi.va.biz.utils.R.ok((Object)frontModelDefine);
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @PostMapping(value={"/front/convert/{viewName}"})
    public com.jiuqi.va.biz.utils.R<FrontModelDefine> convertFrontModelDefine(@RequestBody BillDefine billDefine, @PathVariable(value="viewName") String viewName) {
        try {
            String billType = billDefine.getModelType();
            ModelType modelType = (ModelType)this.modelManager.find(billType);
            modelType.initModelDefine((ModelDefine)billDefine, billDefine.getName());
            if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
                this.defineI18nTrans(billDefine);
            }
            FrontModelDefine frontModelDefine = new FrontModelDefine((ModelDefine)billDefine, false, viewName);
            return com.jiuqi.va.biz.utils.R.ok((Object)frontModelDefine);
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    private void defineI18nTrans(BillDefine billDefine) {
        try {
            List i18nPluginList = this.i18nPluginManager.getI18nPluginList();
            if (i18nPluginList == null || i18nPluginList.size() <= 0) {
                return;
            }
            ArrayList keys = new ArrayList();
            for (I18nPlugin i18nPlugin : i18nPluginList) {
                List pluginKeys;
                PluginDefine plugin = (PluginDefine)billDefine.getPlugins().find(i18nPlugin.getName());
                if (plugin == null || (pluginKeys = i18nPlugin.getAllI18nKeys(plugin, (ModelDefine)billDefine)) == null || pluginKeys.size() <= 0) continue;
                keys.addAll(pluginKeys);
            }
            VaI18nResourceDTO dataResourceDTO = new VaI18nResourceDTO();
            dataResourceDTO.setKey(keys);
            List results = Optional.ofNullable(this.vaDataResourceClient.queryList(dataResourceDTO)).orElse(Collections.emptyList());
            HashMap i18nValueMap = new HashMap(16);
            int i = 0;
            for (String result : results) {
                i18nValueMap.put(keys.get(i), result);
                ++i;
            }
            for (I18nPlugin i18nPlugin : i18nPluginList) {
                PluginDefine plugin = (PluginDefine)billDefine.getPlugins().find(i18nPlugin.getName());
                if (plugin == null) continue;
                i18nPlugin.processForI18n(plugin, (ModelDefine)billDefine, i18nValueMap);
            }
        }
        catch (Exception e) {
            log.error("\u5355\u636e\u5b9a\u4e49\u56fd\u9645\u5316\u8f6c\u5316\u5f02\u5e38", e);
        }
    }

    @PostMapping(value={"/front/convert"})
    public com.jiuqi.va.biz.utils.R<FrontModelDefine> convertFrontModelDefine(@RequestBody BillDefine billDefine) {
        return this.convertFrontModelDefine(billDefine, "view");
    }

    @PostMapping(value={"/{billdefine}/schemes"})
    public com.jiuqi.va.biz.utils.R<List<Map<String, Object>>> getBillDefineSchemes(@PathVariable(value="billdefine") String billdefine) {
        try {
            BillDefine billDefine = this.billDefineService.getDefine(billdefine);
            ViewDefineImpl viewPulgin = (ViewDefineImpl)billDefine.getPlugins().find("view");
            if (viewPulgin == null) {
                return com.jiuqi.va.biz.utils.R.ok();
            }
            List schemes = viewPulgin.getSchemes();
            ArrayList results = new ArrayList();
            if (schemes == null || schemes.size() == 0) {
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("code", "defaultScheme");
                result.put("title", "\u9ed8\u8ba4\u65b9\u6848");
                results.add(result);
                return com.jiuqi.va.biz.utils.R.ok(results);
            }
            for (Map scheme : schemes) {
                HashMap result = new HashMap();
                result.put("code", scheme.get("code"));
                result.put("title", scheme.get("title"));
                results.add(result);
            }
            return com.jiuqi.va.biz.utils.R.ok(results);
        }
        catch (Exception e) {
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @PostMapping(value={"/{billdefine}/detailTables"})
    public com.jiuqi.va.biz.utils.R<List<Map<String, Object>>> getBillDetailTables(@PathVariable(value="billdefine") String billdefine) {
        com.jiuqi.va.biz.utils.R r = null;
        try {
            ArrayList result = new ArrayList();
            r = com.jiuqi.va.biz.utils.R.ok(result);
            BillDefine billDefine = this.billDefineService.getDefine(billdefine);
            DataTableNodeContainer tables = billDefine.getData().getTables();
            DataTableDefine masterTable = (DataTableDefine)tables.getMasterTable();
            tables.stream().forEach(table -> {
                if (masterTable == table) {
                    return;
                }
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("tableName", table.getName());
                item.put("tableTitle", table.getTitle());
                result.add(item);
            });
        }
        catch (Exception e) {
            r = com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
        return r;
    }

    @PostMapping(value={"/{modeltype}/solidinfo"})
    public com.jiuqi.va.biz.utils.R<Map<String, List<String>>> getSolidTableInfo(@PathVariable(value="modeltype") String modeltype) {
        try {
            ModelType modelType = (ModelType)this.modelManager.find(modeltype);
            BillDefineImpl billDefine = (BillDefineImpl)modelType.getModelDefineClass().newInstance();
            modelType.initModelDefine((ModelDefine)billDefine, modeltype);
            DataDefineImpl data = billDefine.getData();
            List tableList = data.getTableList();
            if (tableList == null || tableList.size() == 0) {
                return com.jiuqi.va.biz.utils.R.ok();
            }
            Map<String, List> collect = tableList.stream().collect(Collectors.toMap(DataTableDefineImpl::getTableName, o -> o.getFields().stream().filter(field -> field.isSolidified()).map(DataFieldDefineImpl::getName).collect(Collectors.toList())));
            return com.jiuqi.va.biz.utils.R.ok(collect);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return com.jiuqi.va.biz.utils.R.error((Throwable)e);
        }
    }

    @PostMapping(value={"/meta/datas/check"})
    public R checkMetaData(@RequestBody MetaDataDTO metaDataDTO) {
        R result = new R();
        ArrayList resultList = new ArrayList();
        String datas = metaDataDTO.getDatas();
        try {
            Map rootObject = JSONUtil.parseMap((String)datas);
            String modelTypeName = rootObject.get("modelType").toString();
            ModelType modelType = (ModelType)this.modelManager.get(modelTypeName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            ModelDefineImpl modelDefine = (ModelDefineImpl)mapper.readValue(datas, modelType.getModelDefineClass());
            TypedContainer definePlugins = modelDefine.getPlugins();
            definePlugins.forEachIndex((integer, pluginDefine) -> {
                String pluginName = pluginDefine.getType();
                PluginCheck pluginCheck = null;
                try {
                    pluginCheck = (PluginCheck)this.pluginCheckManager.get(pluginName);
                }
                catch (Exception e) {
                    return;
                }
                PluginCheckResultVO pluginCheckResultVO = pluginCheck.checkPlugin(pluginDefine, (ModelDefine)modelDefine);
                resultList.add(pluginCheckResultVO);
            });
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((int)1, (String)BillCoreI18nUtil.getMessage("va.billcore.billmetacontroller.datacheckfailed"));
        }
        result.put("checkInfos", resultList);
        return result;
    }

    @PostMapping(value={"/meta/datas/delete"})
    public com.jiuqi.va.biz.utils.R deleteMetaDatas(@RequestBody BillPluginsDTO billPluginsDTO) {
        com.jiuqi.va.biz.utils.R r = com.jiuqi.va.biz.utils.R.ok();
        try {
            ModelDefineImpl datas = billPluginsDTO.getDatas();
            TypedContainer plugins = datas.getPlugins();
            for (int i = 0; i < plugins.size(); ++i) {
                PluginDefine o = (PluginDefine)plugins.get(i);
                String type = o.getType();
                PluginDataDel pluginDataDel = null;
                try {
                    pluginDataDel = (PluginDataDel)this.pluginDataDelManager.get(type);
                }
                catch (Exception e) {
                    continue;
                }
                pluginDataDel.pluginDataDel(o, billPluginsDTO.getDataDefine());
            }
            r.setData((Object)billPluginsDTO);
            return r;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return r;
        }
    }

    @PostMapping(value={"/i18n/plugins/resource"})
    public List<VaI18nResourceItem> getI18nPluginsResource(@RequestBody TenantDO param) {
        if (param.getExtInfo("pluginName") == null || param.getExtInfo("defineName") == null) {
            return null;
        }
        String defineName = (String)param.getExtInfo("defineName");
        String pluginName = (String)param.getExtInfo("pluginName");
        String parentId = (String)param.getExtInfo("parentId");
        String requestResourceType = (String)param.getExtInfo("requestResourceType");
        if (!StringUtils.hasText(defineName) || !StringUtils.hasText(pluginName)) {
            return null;
        }
        List i18nPlugins = this.i18nPluginManager.getI18nPluginList();
        if (i18nPlugins == null) {
            return null;
        }
        for (I18nPlugin i18nPlugin : i18nPlugins) {
            if (!i18nPlugin.getName().equals(pluginName)) continue;
            BillDefine billDefine = this.billDefineService.getDefine(defineName, false);
            return i18nPlugin.getI18nResource((PluginDefine)billDefine.getPlugins().find(pluginName), (ModelDefine)billDefine, parentId, requestResourceType);
        }
        return null;
    }
}

