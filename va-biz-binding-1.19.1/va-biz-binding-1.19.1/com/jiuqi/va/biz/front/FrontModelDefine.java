/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.front;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.front.FrontDataDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontPluginDefineFactory;
import com.jiuqi.va.biz.front.FrontPluginDefineFactoryManager;
import com.jiuqi.va.biz.front.FrontRulerDefine;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.meta.MetaInfo;
import com.jiuqi.va.biz.intf.model.ModelConsts;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.view.intf.ExternalViewDefine;
import com.jiuqi.va.biz.view.intf.ViewDefine;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class FrontModelDefine {
    private static final Logger log = LoggerFactory.getLogger(FrontModelDefine.class);
    String name;
    String modelType;
    MetaInfo metaInfo;
    private List<FrontPluginDefine> plugins = new ArrayList<FrontPluginDefine>();
    private transient Map<String, Set<String>> tableFields = new HashMap<String, Set<String>>();
    private transient Map<String, FrontPluginDefine> nameMap = new LinkedHashMap<String, FrontPluginDefine>();
    private final transient ModelDefine modelDefine;
    public final transient boolean ENABLE_SERVER_DATA_BUFFER;

    public void setTableFields(Map<String, Set<String>> tableFields) {
        this.tableFields = tableFields;
    }

    public void setNameMap(Map<String, FrontPluginDefine> nameMap) {
        this.nameMap = nameMap;
    }

    public FrontModelDefine(ModelDefine modelDefine) {
        this.modelDefine = modelDefine;
        this.ENABLE_SERVER_DATA_BUFFER = false;
    }

    public FrontModelDefine(ModelDefine modelDefine, boolean ENABLE_SERVER_DATA_BUFFER, String viewName) {
        this.modelDefine = modelDefine;
        this.ENABLE_SERVER_DATA_BUFFER = ENABLE_SERVER_DATA_BUFFER;
        this.name = modelDefine.getName();
        this.modelType = modelDefine.getModelType();
        this.metaInfo = modelDefine.getMetaInfo();
        modelDefine.getPlugins().forEachName((n, o) -> {
            FrontPluginDefine frontPluginDefine;
            if (o instanceof ViewDefine && !o.getType().equals(viewName)) {
                return;
            }
            FrontPluginDefineFactory factory = FrontPluginDefineFactoryManager.find(n);
            if (factory == null) {
                frontPluginDefine = new FrontPluginDefine(this, (PluginDefine)o);
            } else {
                try {
                    frontPluginDefine = factory.getType().getConstructor(FrontModelDefine.class, PluginDefine.class).newInstance(this, o);
                }
                catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    if (e.getCause() != null && StringUtils.hasText(e.getCause().getMessage())) {
                        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.frontmodeldefine.createpluginerror_1", new Object[]{n}) + e.getCause().getMessage(), e);
                    }
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.frontmodeldefine.createpluginerror_1", new Object[]{n}), e);
                }
            }
            this.plugins.add(frontPluginDefine);
            this.nameMap.put((String)n, frontPluginDefine);
        });
        this.initializePlugins(modelDefine);
    }

    private void initializePlugins(ModelDefine modelDefine) {
        this.plugins.forEach(o -> {
            o.initialize();
            Map<String, Set<String>> setMap = o.getTableFields(modelDefine);
            setMap.forEach((k, v) -> this.tableFields.computeIfAbsent((String)k, a -> new HashSet()).addAll(v));
        });
        FrontDataDefine frontDataDefine = (FrontDataDefine)this.nameMap.get("data");
        for (Map.Entry<String, Set<String>> entry : this.tableFields.entrySet()) {
            Set<String> fields = entry.getValue();
            DataTableDefine dataTableDefine = frontDataDefine.getDataDefine().getTables().get(entry.getKey());
            dataTableDefine.getFields().stream().forEach(o -> {
                DataFieldDefineImpl define = (DataFieldDefineImpl)o;
                if (define.isSolidified()) {
                    fields.add(define.getName());
                } else if (define.getFieldType() == DataFieldType.CALC) {
                    fields.add(define.getName());
                } else if (ModelConsts.INC_FIXED_FIELDS.contains(define.getName())) {
                    fields.add(define.getName());
                } else if (define.isBillPenetrate()) {
                    fields.add(define.getName());
                }
            });
        }
        ActionManager actionManager = (ActionManager)ApplicationContextRegister.getBean(ActionManager.class);
        ModelManager modelManager = (ModelManager)ApplicationContextRegister.getBean(ModelManager.class);
        List<Action> actions = actionManager.getActionList(((ModelType)modelManager.get(modelDefine.getModelType())).getModelClass());
        for (Action action : actions) {
            Map<String, Set<String>> incFields = action.getTriggerFields(modelDefine);
            if (incFields == null) continue;
            incFields.forEach((k, v) -> this.tableFields.computeIfAbsent((String)k, a -> new HashSet()).addAll(v));
        }
    }

    public Map<String, Set<String>> getTableFields() {
        return this.tableFields;
    }

    public FrontModelDefine(ModelDefine modelDefine, boolean ENABLE_SERVER_DATA_BUFFER, String viewName, String schemeCode) {
        this.modelDefine = modelDefine;
        this.ENABLE_SERVER_DATA_BUFFER = ENABLE_SERVER_DATA_BUFFER;
        this.name = modelDefine.getName();
        this.modelType = modelDefine.getModelType();
        this.metaInfo = modelDefine.getMetaInfo();
        modelDefine.getPlugins().forEachName((n, o) -> {
            FrontPluginDefine frontPluginDefine;
            if (o instanceof ViewDefine && !o.getType().equals(viewName)) {
                return;
            }
            FrontPluginDefineFactory factory = FrontPluginDefineFactoryManager.find(n);
            if (factory == null) {
                frontPluginDefine = new FrontPluginDefine(this, (PluginDefine)o);
            } else {
                try {
                    frontPluginDefine = o instanceof ViewDefine && StringUtils.hasText(schemeCode) ? factory.getType().getConstructor(FrontModelDefine.class, PluginDefine.class, String.class).newInstance(this, o, schemeCode) : factory.getType().getConstructor(FrontModelDefine.class, PluginDefine.class).newInstance(this, o);
                }
                catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.frontmodeldefine.createpluginerror", new Object[]{n}), e);
                }
            }
            this.plugins.add(frontPluginDefine);
            this.nameMap.put((String)n, frontPluginDefine);
        });
        this.initializePlugins(modelDefine);
    }

    public FrontModelDefine(ModelDefine modelDefine, boolean ENABLE_SERVER_DATA_BUFFER, String viewName, ExternalViewDefine externalViewDefine) {
        this.modelDefine = modelDefine;
        this.ENABLE_SERVER_DATA_BUFFER = ENABLE_SERVER_DATA_BUFFER;
        this.name = modelDefine.getName();
        this.modelType = modelDefine.getModelType();
        this.metaInfo = modelDefine.getMetaInfo();
        modelDefine.getPlugins().forEachName((n, o) -> {
            FrontPluginDefine frontPluginDefine;
            if (o instanceof ViewDefine && !o.getType().equals(viewName)) {
                return;
            }
            FrontPluginDefineFactory factory = FrontPluginDefineFactoryManager.find(n);
            if (factory == null) {
                frontPluginDefine = new FrontPluginDefine(this, (PluginDefine)o);
            } else {
                try {
                    frontPluginDefine = factory.getType().getConstructor(FrontModelDefine.class, PluginDefine.class).newInstance(this, o);
                }
                catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.frontmodeldefine.createpluginerror", new Object[]{n}), e);
                }
            }
            this.plugins.add(frontPluginDefine);
            this.nameMap.put((String)n, frontPluginDefine);
        });
        this.initializePlugins(modelDefine);
        Optional<FrontPluginDefine> rulerOptional1 = this.plugins.stream().filter(o -> o instanceof FrontRulerDefine).findFirst();
        Optional<FrontPluginDefine> dataOptional1 = this.plugins.stream().filter(o -> o instanceof FrontDataDefine).findFirst();
        Map<String, Object> template = externalViewDefine.getTemplate(modelDefine.getName());
        if (rulerOptional1.isPresent() && dataOptional1.isPresent() && template != null) {
            FrontRulerDefine rulerDefine = (FrontRulerDefine)rulerOptional1.get();
            FrontDataDefine dataDefine = (FrontDataDefine)dataOptional1.get();
            this.processProps(rulerDefine, dataDefine, template);
        }
    }

    public ModelDefine getModelDefine() {
        return this.modelDefine;
    }

    public <S> S get(Class<S> pluginClass) {
        for (FrontPluginDefine frontPluginDefine : this.plugins) {
            if (!pluginClass.isInstance(frontPluginDefine)) continue;
            return (S)frontPluginDefine;
        }
        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.frontmodeldefine.plugintypenotfound") + pluginClass.toString());
    }

    public List<FrontPluginDefine> getPlugins() {
        return this.plugins;
    }

    public void processProps(FrontRulerDefine rulerDefine, FrontDataDefine dataDefine, Map<String, Object> props) {
        Map binding = (Map)props.get("binding");
        if (binding != null) {
            this.addBindingFields(rulerDefine, dataDefine, binding);
        }
        String idValue = Convert.cast(props.get("id"), String.class);
        UUID id = Utils.normalizeId(idValue);
        try {
            rulerDefine.activeFrontObjectRuler(id, null, null);
        }
        catch (ModelException modelException) {
            // empty catch block
        }
        List children = (List)props.get("children");
        if (children != null) {
            children.forEach(o -> this.processProps(rulerDefine, dataDefine, (Map<String, Object>)o));
        }
    }

    private void addBindingFields(FrontRulerDefine rulerDefine, FrontDataDefine dataDefine, Map<String, Object> binding) {
        HashMap<String, Set> bindingFields = new HashMap<String, Set>();
        String tableName = Convert.cast(binding.get("tableName"), String.class);
        String fieldName = Convert.cast(binding.get("fieldName"), String.class);
        if (Utils.isNotEmpty(tableName)) {
            Set fieldNames = bindingFields.computeIfAbsent(tableName, o -> {
                DataTableDefine tableDefine = dataDefine.getDataDefine().getTables().get(tableName);
                try {
                    rulerDefine.activeFrontObjectRuler(tableDefine.getId(), Arrays.asList(RulerConsts.FRONT_TABLE_PROPS), null);
                }
                catch (ModelException modelException) {
                    // empty catch block
                }
                return new HashSet();
            });
            if (Utils.isNotEmpty(fieldName)) {
                if (fieldNames.add(fieldName)) {
                    dataDefine.addFrontField(tableName, fieldName);
                    DataFieldDefine fieldDefine = dataDefine.getDataDefine().getTables().get(tableName).getFields().find(fieldName);
                    if (fieldDefine != null) {
                        try {
                            rulerDefine.activeFrontObjectRuler(fieldDefine.getId(), Arrays.asList(RulerConsts.FRONT_FIELD_PROPS), null);
                        }
                        catch (ModelException modelException) {}
                    }
                }
            } else {
                List editFields;
                List viewFields;
                if (binding.get("viewFields") != null && (viewFields = (List)binding.get("viewFields")).size() > 0) {
                    viewFields.forEach(o -> {
                        String viewField = Convert.cast(o.get("fieldName"), String.class);
                        if (fieldNames.add(viewField)) {
                            dataDefine.addFrontField(tableName, viewField);
                            DataFieldDefine fieldDefine = dataDefine.getDataDefine().getTables().get(tableName).getFields().find(viewField);
                            if (fieldDefine != null) {
                                try {
                                    rulerDefine.activeFrontObjectRuler(fieldDefine.getId(), Arrays.asList(RulerConsts.FRONT_FIELD_PROPS), null);
                                }
                                catch (ModelException modelException) {
                                    // empty catch block
                                }
                            }
                        }
                    });
                }
                if (binding.get("editFields") != null && (editFields = (List)binding.get("editFields")).size() > 0) {
                    editFields.forEach(o -> {
                        String editField = Convert.cast(o.get("fieldName"), String.class);
                        if (fieldNames.add(editField)) {
                            dataDefine.addFrontField(tableName, editField);
                            DataFieldDefine fieldDefine = dataDefine.getDataDefine().getTables().get(tableName).getFields().find(editField);
                            if (fieldDefine != null) {
                                try {
                                    rulerDefine.activeFrontObjectRuler(fieldDefine.getId(), Arrays.asList(RulerConsts.FRONT_FIELD_PROPS), null);
                                }
                                catch (ModelException modelException) {
                                    // empty catch block
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelType() {
        return this.modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public MetaInfo getMetaInfo() {
        return this.metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    public void setPlugins(List<FrontPluginDefine> plugins) {
        this.plugins = plugins;
    }
}

