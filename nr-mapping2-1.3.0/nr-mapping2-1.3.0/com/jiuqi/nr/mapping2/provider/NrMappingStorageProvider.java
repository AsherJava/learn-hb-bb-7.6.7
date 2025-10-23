/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.provider.IMappingStorageProvider
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.nvwa.mapping.transfer.TransferUtil
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.mapping2.provider;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.dao.JIOConfigDao;
import com.jiuqi.nr.mapping2.provider.INrMappingType;
import com.jiuqi.nr.mapping2.provider.NrMappingCollector;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.service.JIOConfigService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.provider.IMappingStorageProvider;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.nvwa.mapping.transfer.TransferUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class NrMappingStorageProvider
implements IMappingStorageProvider {
    protected final Logger logger = LoggerFactory.getLogger(NrMappingStorageProvider.class);
    @Autowired
    private PeriodMappingService periodService;
    @Autowired
    private ZBMappingService zbService;
    @Autowired
    private FormulaMappingService formulaService;
    @Autowired
    private JIOConfigService jioService;
    @Autowired
    private JIOConfigDao jioDao;
    @Autowired
    private IRunTimeViewController runTime;
    @Autowired
    private IFMDMAttributeService ifmdmAttributeService;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeCtrl;
    @Autowired
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NrMappingCollector collector;
    @Autowired
    private IMappingSchemeService mappingSchemeService;

    @Transactional(rollbackFor={Exception.class})
    public void deleteMapping(MappingScheme scheme) {
        this.zbService.deleteByMS(scheme.getKey());
        this.periodService.deleteByMS(scheme.getKey());
        this.formulaService.deleteByMS(scheme.getKey());
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (mappingParam == null) {
            return;
        }
        INrMappingType type = this.collector.getTypeMap().get(mappingParam.getType());
        type.deleteMapping(scheme);
    }

    @Transactional(rollbackFor={Exception.class})
    public void modifyMapping(MappingScheme scheme, MappingScheme oldScheme) {
        NrMappingParam oMappingParam = NrMappingUtil.getNrMappingParam(oldScheme);
        NrMappingParam nMappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (nMappingParam == null) {
            return;
        }
        INrMappingType type = this.collector.getTypeMap().get(oMappingParam.getType());
        if (type == null) {
            return;
        }
        if (type.getTypeOption(scheme).isModifyFormScheme() && !nMappingParam.getFormSchemeKey().equals(oMappingParam.getFormSchemeKey())) {
            this.modifyZbMapping(scheme.getKey(), nMappingParam.getFormSchemeKey(), oMappingParam.getFormSchemeKey());
            this.modifyPeriodMapping(scheme.getKey(), nMappingParam.getFormSchemeKey(), oMappingParam.getFormSchemeKey());
            this.modifyFormulaMapping(scheme.getKey());
        }
        type.modifyMapping(scheme, oldScheme);
    }

    @Transactional(rollbackFor={Exception.class})
    public void copyMapping(MappingScheme sourceScheme, MappingScheme targetScheme) {
        NrMappingParam sourceMappingParam = NrMappingUtil.getNrMappingParam(sourceScheme);
        NrMappingParam targetMappingParam = NrMappingUtil.getNrMappingParam(targetScheme);
        if (targetMappingParam == null) {
            return;
        }
        INrMappingType type = this.collector.getTypeMap().get(sourceMappingParam.getType());
        if (type == null) {
            return;
        }
        this.copyZbMapping(sourceScheme.getKey(), targetScheme.getKey(), sourceMappingParam.getFormSchemeKey(), targetMappingParam.getFormSchemeKey());
        this.copyPeriodMapping(sourceScheme.getKey(), targetScheme.getKey(), sourceMappingParam.getFormSchemeKey(), targetMappingParam.getFormSchemeKey());
        this.copyFormulaMapping(sourceScheme.getKey(), targetScheme.getKey(), sourceMappingParam.getFormSchemeKey(), targetMappingParam.getFormSchemeKey());
        type.copyMapping(sourceScheme, targetScheme);
    }

    private void modifyFormulaMapping(String key) {
        this.formulaService.deleteByMS(key);
    }

    private void modifyPeriodMapping(String key, String nFormSchemeKey, String oFormSchemeKey) {
        List<PeriodMapping> periodMappings = this.getNewPeriodMappings(key, nFormSchemeKey, oFormSchemeKey);
        this.periodService.deleteByMS(key);
        this.periodService.batchAdd(periodMappings);
    }

    private List<PeriodMapping> getNewPeriodMappings(String msKey, String nFormSchemeKey, String oFormSchemeKey) {
        FormSchemeDefine nformScheme = this.runTime.getFormScheme(nFormSchemeKey);
        FormSchemeDefine oformScheme = this.runTime.getFormScheme(oFormSchemeKey);
        if (nformScheme.getPeriodType().code() == oformScheme.getPeriodType().code()) {
            try {
                List periods = this.runTime.querySchemePeriodLinkByScheme(nFormSchemeKey).stream().map(SchemePeriodLinkDefine::getPeriodKey).collect(Collectors.toList());
                return this.periodService.findByMS(msKey).stream().filter(m -> periods.contains(m.getPeriod())).collect(Collectors.toList());
            }
            catch (Exception e) {
                this.logger.error(nFormSchemeKey + "::\u6307\u6807\u6620\u5c04\u53d6\u62a5\u8868\u65b9\u6848\u65f6\u671f\u5217\u8868\u5f02\u5e38::" + e.getMessage(), e);
            }
        }
        return Collections.emptyList();
    }

    private void modifyZbMapping(String key, String nFormSchemeKey, String oFormSchemeKey) {
        String oDataSchemeKey;
        List<ZBMapping> zbMappings = this.zbService.findByMS(key);
        if (CollectionUtils.isEmpty(zbMappings)) {
            return;
        }
        String nDataSchemeKey = this.getDataSchemeByFormScheme(nFormSchemeKey);
        if (nDataSchemeKey.equals(oDataSchemeKey = this.getDataSchemeByFormScheme(oFormSchemeKey))) {
            List<ZBMapping> newMappings = this.getNewMappingByFormScheme(nFormSchemeKey, zbMappings);
            this.zbService.deleteByMS(key);
            if (!CollectionUtils.isEmpty(newMappings)) {
                HashSet<String> keys = new HashSet<String>();
                for (ZBMapping mapping : newMappings) {
                    if (keys.add(mapping.getKey())) continue;
                    mapping.setKey(UUID.randomUUID().toString());
                }
                this.zbService.batchAdd(newMappings);
            }
        } else {
            this.zbService.deleteByMS(key);
        }
    }

    private List<ZBMapping> getNewMappingByFormScheme(String nFormSchemeKey, List<ZBMapping> zbMappings) {
        ArrayList<ZBMapping> newMappings = new ArrayList<ZBMapping>();
        Map<String, ZBMapping> zbMappingMap = zbMappings.stream().collect(Collectors.toMap(m -> m.getTable() + "@" + m.getZbCode(), Function.identity(), (existing, duplicate) -> existing));
        HashMap<String, TableModelDefine> tableModelCache = new HashMap<String, TableModelDefine>();
        HashMap<String, TableDefine> tableCache = new HashMap<String, TableDefine>();
        List allFormGroups = this.runTime.getAllFormGroupsInFormScheme(nFormSchemeKey);
        for (FormGroupDefine fg : allFormGroups) {
            List<FormDefine> allFroms = this.getFormDefines(fg);
            for (FormDefine form : allFroms) {
                ArrayList<ZBMapping> formZbMapping = new ArrayList<ZBMapping>();
                HashMap<String, String> fieldMap = new HashMap<String, String>();
                boolean isFMDM = FormType.FORM_TYPE_NEWFMDM.equals((Object)form.getFormType());
                if (isFMDM) {
                    this.parseFmdmMapping(zbMappingMap, newMappings, tableModelCache, form);
                    continue;
                }
                this.parseFomMapping(zbMappingMap, newMappings, tableCache, form, formZbMapping, fieldMap);
                if (CollectionUtils.isEmpty(formZbMapping)) continue;
                this.buildFormNewMapping(newMappings, form, formZbMapping, fieldMap);
            }
        }
        return newMappings;
    }

    private List<FormDefine> getFormDefines(FormGroupDefine fg) {
        try {
            return this.runTime.getAllFormsInGroup(fg.getKey());
        }
        catch (Exception e) {
            this.logger.error(fg.getTitle() + "::\u6307\u6807\u6620\u5c04\u83b7\u53d6\u5206\u7ec4\u4e0b\u7684\u62a5\u8868\u5f02\u5e38\uff1a" + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private void buildFormNewMapping(List<ZBMapping> newMappings, FormDefine form, List<ZBMapping> formZbMapping, Map<String, String> fieldMap) {
        for (ZBMapping mapping : formZbMapping) {
            List links = this.runTimeAuthViewController.getLinksInFormByField(form.getKey(), fieldMap.get(mapping.getKey()));
            if (CollectionUtils.isEmpty(links)) continue;
            for (int i = 0; i < links.size(); ++i) {
                ZBMapping zbMapping = mapping;
                if (i != 0) {
                    zbMapping = new ZBMapping();
                    BeanUtils.copyProperties(mapping, zbMapping);
                    zbMapping.setKey(UUID.randomUUID().toString());
                    newMappings.add(zbMapping);
                }
                zbMapping.setForm(form.getFormCode());
                this.setRegionCode(((DataLinkDefine)links.get(i)).getRegionKey(), zbMapping);
            }
        }
    }

    private void setRegionCode(String regionKey, ZBMapping zbMapping) {
        DataRegionDefine regionDefine = this.runTimeAuthViewController.queryDataRegionDefine(regionKey);
        zbMapping.setRegionCode(regionDefine.getCode());
    }

    private void parseFomMapping(Map<String, ZBMapping> zbMappingMap, List<ZBMapping> newMappings, Map<String, TableDefine> tableCache, FormDefine form, List<ZBMapping> formZbMapping, Map<String, String> fieldMap) {
        List keys = this.runTime.getFieldKeysInForm(form.getKey());
        List fields = this.dataRuntimeCtrl.queryFieldDefinesInRange((Collection)keys);
        if (!CollectionUtils.isEmpty(fields)) {
            for (FieldDefine f : fields) {
                TableDefine table = this.getTableDefine(tableCache, f.getOwnerTableKey());
                String tableCode = table == null ? f.getOwnerTableKey() : table.getCode();
                ZBMapping zbMapping = zbMappingMap.get(tableCode + "@" + f.getCode());
                if (zbMapping == null) continue;
                ZBMapping mapping = new ZBMapping();
                BeanUtils.copyProperties(zbMapping, mapping);
                newMappings.add(mapping);
                formZbMapping.add(mapping);
                fieldMap.put(mapping.getKey(), f.getKey());
            }
        }
    }

    private void parseFmdmMapping(Map<String, ZBMapping> zbMappingMap, List<ZBMapping> newMappings, Map<String, TableModelDefine> tableModelCache, FormDefine form) {
        FormSchemeDefine formSchemeDefine = this.runTime.getFormScheme(form.getFormScheme());
        TaskDefine taskDefine = this.runTime.queryTaskDefine(formSchemeDefine.getTaskKey());
        FMDMAttributeDTO fMDMAttributeDTO = new FMDMAttributeDTO();
        fMDMAttributeDTO.setEntityId(taskDefine.getDw());
        fMDMAttributeDTO.setFormSchemeKey(formSchemeDefine.getKey());
        List attributes = this.ifmdmAttributeService.list(fMDMAttributeDTO);
        if (CollectionUtils.isEmpty(attributes)) {
            return;
        }
        List allLinks = this.runTimeAuthViewController.getAllLinksInForm(form.getKey());
        for (DataLinkDefine link : allLinks) {
            IFMDMAttribute atr;
            TableModelDefine table;
            String tableCode;
            ZBMapping zbMapping;
            Optional<IFMDMAttribute> findAttribute = null;
            findAttribute = link.getType().getValue() == DataLinkType.DATA_LINK_TYPE_FMDM.getValue() ? attributes.stream().filter(e -> e.getCode().equals(link.getLinkExpression())).findFirst() : attributes.stream().filter(e -> e.getZBKey().equals(link.getLinkExpression())).findFirst();
            if (!findAttribute.isPresent() || (zbMapping = zbMappingMap.get((tableCode = (table = this.getTableModelDefine(tableModelCache, (atr = findAttribute.get()).getTableID())) == null ? atr.getTableID() : table.getCode()) + "@" + atr.getCode())) == null) continue;
            ZBMapping mapping = new ZBMapping();
            BeanUtils.copyProperties(zbMapping, mapping);
            newMappings.add(mapping);
            this.setRegionCode(link.getRegionKey(), zbMapping);
            mapping.setForm(form.getFormCode());
        }
    }

    private TableModelDefine getTableModelDefine(Map<String, TableModelDefine> tableModelCache, String tableId) {
        TableModelDefine table = null;
        if (tableModelCache.containsKey(tableId)) {
            table = tableModelCache.get(tableId);
        } else {
            table = this.dataModelService.getTableModelDefineById(tableId);
            tableModelCache.put(tableId, table);
        }
        return table;
    }

    private TableDefine getTableDefine(Map<String, TableDefine> tableCache, String tableId) {
        TableDefine table = null;
        if (tableCache.containsKey(tableId)) {
            table = tableCache.get(tableId);
        } else {
            try {
                table = this.dataRuntimeCtrl.queryTableDefine(tableId);
                tableCache.put(tableId, table);
            }
            catch (Exception e) {
                this.logger.error(tableId + "\u6307\u6807\u6620\u5c04\u53d6\u5b58\u50a8\u8868\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        }
        return table;
    }

    private String getDataSchemeByFormScheme(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTime.getFormScheme(formSchemeKey);
        TaskDefine task = this.runTime.queryTaskDefine(formScheme.getTaskKey());
        return task.getDataScheme();
    }

    private void copyFormulaMapping(String sourceKey, String targetKey, String sourceFormSchemeKey, String targetFormSchemeKey) {
        if (sourceFormSchemeKey.equals(targetFormSchemeKey)) {
            List<FormulaMapping> formulaMappings = this.formulaService.findByMS(sourceKey);
            if (CollectionUtils.isEmpty(formulaMappings)) {
                return;
            }
            for (FormulaMapping mapping : formulaMappings) {
                mapping.setKey(UUID.randomUUID().toString());
                mapping.setMappingScheme(targetKey);
            }
            this.formulaService.batchAdd(formulaMappings);
        }
    }

    private void copyPeriodMapping(String sourceKey, String targetKey, String sourceFormSchemeKey, String targetFormSchemeKey) {
        List<PeriodMapping> mappings = this.periodService.findByMS(sourceKey);
        if (CollectionUtils.isEmpty(mappings)) {
            return;
        }
        List<PeriodMapping> periodMappings = this.getNewPeriodMappings(sourceKey, targetFormSchemeKey, sourceFormSchemeKey);
        if (!CollectionUtils.isEmpty(periodMappings)) {
            for (PeriodMapping mapping : periodMappings) {
                mapping.setKey(UUID.randomUUID().toString());
                mapping.setMsKey(targetKey);
            }
            this.periodService.batchAdd(periodMappings);
        }
    }

    private void copyZbMapping(String sourceKey, String targetKey, String sourceFormSchemeKey, String targetFormSchemeKey) {
        List<ZBMapping> zbMappings = this.zbService.findByMS(sourceKey);
        if (CollectionUtils.isEmpty(zbMappings)) {
            return;
        }
        List<ZBMapping> newMappings = new ArrayList<ZBMapping>();
        if (sourceFormSchemeKey.equals(targetFormSchemeKey)) {
            newMappings.addAll(zbMappings);
        } else {
            String sourceDataSchemeKey;
            String targetDataSchemeKey = this.getDataSchemeByFormScheme(targetFormSchemeKey);
            if (targetDataSchemeKey.equals(sourceDataSchemeKey = this.getDataSchemeByFormScheme(sourceFormSchemeKey))) {
                newMappings = this.getNewMappingByFormScheme(targetFormSchemeKey, zbMappings);
            }
        }
        if (!CollectionUtils.isEmpty(newMappings)) {
            for (ZBMapping mapping : newMappings) {
                mapping.setKey(UUID.randomUUID().toString());
                mapping.setMsKey(targetKey);
            }
            this.zbService.batchAdd(newMappings);
        }
    }

    public JSONObject exportScheme(MappingScheme scheme) {
        NrMappingParam mappingParam;
        JSONObject res = new JSONObject();
        String msKey = scheme.getKey();
        List<ZBMapping> zbs = this.zbService.findByMS(msKey);
        List<PeriodMapping> periods = this.periodService.findByMS(msKey);
        List<FormulaMapping> fmls = this.formulaService.findByMS(msKey);
        if (!CollectionUtils.isEmpty(zbs)) {
            res.put("zb", zbs);
        }
        if (!CollectionUtils.isEmpty(periods)) {
            res.put("period", periods);
        }
        if (!CollectionUtils.isEmpty(fmls)) {
            JSONArray fmlArr = new JSONArray();
            for (FormulaMapping mapping : fmls) {
                JSONObject json = new JSONObject((Object)mapping);
                json.put("mFormulaCode", (Object)mapping.getmFormulaCode());
                json.put("mFormulaScheme", (Object)mapping.getmFormulaScheme());
                fmlArr.put((Object)json);
            }
            res.put("formula", (Object)fmlArr);
        }
        if ((mappingParam = NrMappingUtil.getNrMappingParam(scheme)) != null) {
            INrMappingType type = this.collector.getTypeMap().get(mappingParam.getType());
            type.exportResource(scheme, res);
        }
        return res;
    }

    public void importScheme(MappingScheme scheme, JSONObject data) {
        String msKey = scheme.getKey();
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(scheme);
        if (mappingParam == null) {
            this.logger.info(String.format("\u6620\u5c04\u65b9\u6848\uff1a%s\u3010%s\u3011\u5973\u5a32\u53c2\u6570\u5bfc\u5165\u5f02\u5e38\uff0cext\uff1a%s", scheme.getTitle(), scheme.getCode(), scheme.getExtParam()));
            return;
        }
        if (!StringUtils.hasText(mappingParam.getType())) {
            if (this.jioDao.isExist(scheme.getKey())) {
                mappingParam.setType("JIO");
            } else {
                mappingParam.setType("MIDSTORE");
            }
            NrMappingUtil.updateSchemeNrParam(scheme, mappingParam);
        }
        this.syncZB(data, msKey);
        this.syncPeriod(data, msKey);
        this.syncFormula(data, msKey);
        INrMappingType type = this.collector.getTypeMap().get(mappingParam.getType());
        type.importResource(scheme, data);
    }

    public void syncFormula(JSONObject data, String msKey) {
        this.formulaService.deleteByMS(msKey);
        if (!data.has("formula")) {
            return;
        }
        JSONArray fmlArr = data.getJSONArray("formula");
        if (!fmlArr.isEmpty()) {
            ArrayList<FormulaMapping> mappings = new ArrayList<FormulaMapping>();
            int size = fmlArr.length();
            for (int i = 0; i < size; ++i) {
                JSONObject json = fmlArr.getJSONObject(i);
                FormulaMapping mapping = (FormulaMapping)TransferUtil.tranJsonToObject((JSONObject)json, FormulaMapping.class);
                if (mapping == null) {
                    this.logger.warn("\u5973\u5a32\u6620\u5c04\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1asyncZB\u4e22\u5931\u6307\u6807\u6620\u5c04,\u6765\u6e90\u6620\u5c04key=" + json.getString("key"));
                    continue;
                }
                mapping.setmFormulaCode(json.has("mFormulaCode") ? json.getString("mFormulaCode") : null);
                mapping.setmFormulaScheme(json.has("mFormulaScheme") ? json.getString("mFormulaScheme") : null);
                mappings.add(mapping);
            }
            this.formulaService.batchAdd(mappings);
        }
    }

    public void syncPeriod(JSONObject data, String msKey) {
        this.periodService.deleteByMS(msKey);
        if (!data.has("period")) {
            return;
        }
        JSONArray periodArr = data.getJSONArray("period");
        if (!periodArr.isEmpty()) {
            ArrayList<PeriodMapping> mappings = new ArrayList<PeriodMapping>();
            int size = periodArr.length();
            for (int i = 0; i < size; ++i) {
                JSONObject json = periodArr.getJSONObject(i);
                PeriodMapping mapping = (PeriodMapping)TransferUtil.tranJsonToObject((JSONObject)json, PeriodMapping.class);
                if (mapping == null) {
                    this.logger.warn("\u5973\u5a32\u6620\u5c04\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1asyncPeriod\u4e22\u5931\u65f6\u671f\u6620\u5c04,\u6765\u6e90\u6620\u5c04key=" + json.getString("key"));
                    continue;
                }
                mappings.add(mapping);
            }
            this.periodService.batchAdd(mappings);
        }
    }

    public void syncZB(JSONObject data, String msKey) {
        this.zbService.deleteByMS(msKey);
        if (!data.has("zb")) {
            return;
        }
        JSONArray zbArr = data.getJSONArray("zb");
        if (!zbArr.isEmpty()) {
            ArrayList<ZBMapping> mappings = new ArrayList<ZBMapping>();
            int size = zbArr.length();
            for (int i = 0; i < size; ++i) {
                JSONObject json = zbArr.getJSONObject(i);
                ZBMapping mapping = (ZBMapping)TransferUtil.tranJsonToObject((JSONObject)json, ZBMapping.class);
                if (mapping == null) {
                    this.logger.warn("\u5973\u5a32\u6620\u5c04\u53c2\u6570\u5bfc\u5165\u5bfc\u51fa\uff1a\uff1asyncZB\u4e22\u5931\u6307\u6807\u6620\u5c04,\u6765\u6e90\u6620\u5c04key=" + json.getString("key"));
                    continue;
                }
                mappings.add(mapping);
            }
            this.zbService.batchAdd(mappings);
        }
    }
}

