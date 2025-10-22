/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.map.configurations.service.impl;

import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UnitMapping;
import nr.single.map.configurations.bean.ZbMapping;
import nr.single.map.configurations.dao.ConfigDao;
import nr.single.map.configurations.internal.bean.IllegalData;
import nr.single.map.configurations.service.HandleUpdateConfigService;
import nr.single.map.configurations.service.MappingConfigService;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFormulaItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class HandleUpdateConfigServiceImpl
implements HandleUpdateConfigService {
    private static final Logger logger = LoggerFactory.getLogger(HandleUpdateConfigServiceImpl.class);
    @Autowired
    private IDesignTimeViewController designView;
    @Autowired
    private IFormulaRunTimeController runTimeFormula;
    @Autowired
    private IDataDefinitionRuntimeController IRunTimeCtrl;
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private MappingConfigService mappingConfigService;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public ISingleMappingConfig saveFormulas(String mapping, String schemeKey, List<SingleFileFormulaItem> formulaInfos, IllegalData errorData) {
        Map<String, List<SingleFileFormulaItem>> importFormulasMap = formulaInfos.stream().filter(e -> StringUtils.isNotEmpty((CharSequence)e.getNetFormCode())).collect(Collectors.groupingBy(SingleFileFormulaItem::getNetFormCode));
        Map<String, SingleFileFormulaItem> needSaveBetweenTableFormula = formulaInfos.stream().filter(e -> StringUtils.isEmpty((CharSequence)e.getNetFormKey()) && !StringUtils.isEmpty((CharSequence)e.getNetFormulaKey())).collect(Collectors.toMap(SingleFileFormulaItem::getNetFormulaCode, e -> e, (e1, e2) -> e1));
        ISingleMappingConfig config = this.configDao.query(mapping);
        List<SingleFileFormulaItem> queryFormulas = config.getFormulaInfos();
        Map<String, List<SingleFileFormulaItem>> singleMap = queryFormulas.stream().filter(e -> e.getSingleFormulaCode() != null).collect(Collectors.groupingBy(SingleFileFormulaItem::getSingleFormulaCode));
        List betweenTable = queryFormulas.stream().filter(e -> StringUtils.isEmpty((CharSequence)e.getNetFormKey())).collect(Collectors.toList());
        Map<String, List<SingleFileFormulaItem>> netFormMap = queryFormulas.stream().filter(e -> e.getNetFormCode() != null).collect(Collectors.groupingBy(SingleFileFormulaItem::getNetFormCode));
        FormulaSchemeDefine schemeDefine = this.runTimeFormula.queryFormulaSchemeDefine(schemeKey);
        List queryBaseFormulas = this.runTimeFormula.getAllFormulasInScheme(schemeDefine.getKey());
        List<Object> allFormulas = new ArrayList();
        try {
            List formulaDefines = this.runTimeFormula.queryPublicFormulaDefineByScheme(schemeDefine.getKey());
            queryBaseFormulas.addAll(formulaDefines);
            allFormulas = queryBaseFormulas.stream().distinct().collect(Collectors.toList());
        }
        catch (Exception e3) {
            logger.warn("\u4fdd\u5b58\u516c\u5f0f\u6620\u5c04\u65f6\uff0c\u67e5\u627e\u62a5\u8868\u5f15\u7528\u516c\u5f0f\u9519\u8bef.");
        }
        Map<String, List<FormulaDefine>> allFormulasMap = allFormulas.stream().collect(Collectors.groupingBy(FormulaDefine::getCode));
        importFormulasMap.forEach((formCode, items) -> {
            DesignFormDefine formDefine = this.designView.queryFormByCodeInFormScheme(config.getTaskInfo().getNetFormSchemeKey(), formCode);
            if (formDefine != null) {
                List formFilter = (List)netFormMap.get(formCode);
                items.forEach(e -> {
                    List singleItems = (List)singleMap.get(e.getSingleFormulaCode());
                    boolean findNet = false;
                    if (!CollectionUtils.isEmpty(formFilter)) {
                        findNet = this.findItem(formFilter, (SingleFileFormulaItem)e, singleItems);
                    }
                    if (!findNet) {
                        List defines = (List)allFormulasMap.get(e.getNetFormulaCode());
                        if (defines != null && defines.size() > 0) {
                            SingleFileFormulaItem append = this.appendItem(schemeDefine, (FormulaDefine)defines.get(0), (SingleFileFormulaItem)e, singleItems);
                            if (formFilter != null) {
                                formFilter.add(append);
                            }
                            queryFormulas.add(append);
                        } else {
                            errorData.addErrorFormula("\u4e0d\u5b58\u5728\u7684\u516c\u5f0f\u7f16\u53f7\uff01", IllegalData.getFormluaIdx(e.getImportIndex()), e.getNetFormulaCode());
                        }
                    }
                });
            } else {
                errorData.addErrorFormula("\u4e0d\u5b58\u5728\u7684\u7f51\u7edc\u62a5\u8868\uff01", IllegalData.getFormIdx(((SingleFileFormulaItem)items.get(0)).getImportIndex()), (String)formCode);
            }
        });
        for (SingleFileFormulaItem formulaItem : betweenTable) {
            SingleFileFormulaItem searchFormulaCode = needSaveBetweenTableFormula.get(formulaItem.getNetFormulaCode());
            if (searchFormulaCode == null) continue;
            this.setSingleItem(formulaItem, searchFormulaCode);
            formulaItem.setSingleFormulaCode(searchFormulaCode.getSingleFormulaCode());
            needSaveBetweenTableFormula.remove(formulaItem.getNetFormulaCode());
        }
        for (String key : needSaveBetweenTableFormula.keySet()) {
            SingleFileFormulaItem fileFormulaItem = needSaveBetweenTableFormula.get(key);
            queryFormulas.add(fileFormulaItem);
        }
        return config;
    }

    @Override
    public UnitMapping saveEntity(UnitMapping importUnit, ISingleMappingConfig config, IllegalData errorData) {
        UnitMapping unitMapping = config.getMapping();
        ArrayList<UnitCustomMapping> entitys = new ArrayList<UnitCustomMapping>();
        IEntityAttribute bblx = null;
        String dw = null;
        try {
            DesignFormSchemeDefine formSchemeDefine = this.designView.queryFormSchemeDefine(config.getSchemeKey());
            dw = formSchemeDefine.getDw();
            if (StringUtils.isEmpty((CharSequence)dw)) {
                DesignTaskDefine designTaskDefine = this.designView.queryTaskDefine(config.getTaskKey());
                dw = designTaskDefine.getDw();
            }
            IEntityModel entityModel = this.entityMetaService.getEntityModel(dw);
            bblx = entityModel.getBblxField();
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        List<IEntityRow> rows = this.getEntityData(config.getSchemeKey());
        IEntityAttribute finalBblx = bblx;
        importUnit.getUnitInfos().forEach(e -> {
            IEntityRow findRow = null;
            List sameCode = rows.stream().filter(r -> r.getCode().equals(e.getNetUnitCode())).collect(Collectors.toList());
            if (sameCode.size() > 0) {
                if (e.getBblx() != null && !"".equals(e.getBblx())) {
                    Optional<IEntityRow> findData = sameCode.stream().filter(r -> {
                        String unitBblx = null;
                        try {
                            if (finalBblx != null) {
                                unitBblx = r.getValue(finalBblx.getCode()).getAsString();
                            }
                        }
                        catch (DataTypeException e1) {
                            logger.error(e1.getMessage(), e1);
                        }
                        return e.getBblx().equals(unitBblx);
                    }).findFirst();
                    if (findData.isPresent()) {
                        findRow = findData.get();
                    }
                } else {
                    findRow = (IEntityRow)sameCode.get(0);
                }
                if (findRow != null) {
                    e.setNetUnitKey(findRow.getEntityKeyData());
                    e.setNetUnitName(findRow.getTitle());
                    try {
                        if (finalBblx != null) {
                            e.setBblx(findRow.getValue(finalBblx.getCode()).getAsString());
                        }
                    }
                    catch (DataTypeException e1) {
                        logger.error(e1.getMessage(), e1);
                    }
                    entitys.add((UnitCustomMapping)e);
                } else {
                    errorData.addErrorEntity("\u4e0d\u5b58\u5728\u4e0e\u5f53\u524d\u62a5\u8868\u7c7b\u578b\u5339\u914d\u7684\u5355\u4f4d", e.getImportIndex(), e.getNetUnitCode());
                }
            } else {
                e.setNetUnitKey(UUID.randomUUID().toString());
                e.setNetUnitName("");
                entitys.add((UnitCustomMapping)e);
            }
        });
        List<UnitCustomMapping> saveEntity = unitMapping.getUnitInfos();
        if (saveEntity != null && !saveEntity.isEmpty()) {
            for (int i = 0; i < saveEntity.size(); ++i) {
                int finalI = i;
                Optional<UnitCustomMapping> findEntity = entitys.stream().filter(e -> e.getNetUnitKey().equals(((UnitCustomMapping)saveEntity.get(finalI)).getNetUnitKey())).findFirst();
                if (findEntity.isPresent() || !StringUtils.isNotEmpty((CharSequence)saveEntity.get(finalI).getNetUnitName())) continue;
                entitys.add(saveEntity.get(i));
            }
        }
        unitMapping.setUnitInfos(entitys);
        config.setMapping(unitMapping);
        return unitMapping;
    }

    @Override
    public void saveZb(List<ZbMapping> zbInfo, ISingleMappingConfig config) {
        ArrayList fields = new ArrayList();
        zbInfo.forEach(e -> {
            Map<String, SingleFileFieldInfo> infoMap = e.getZbInfo();
            if (e.getZbInfo() != null) {
                infoMap.forEach((key, value) -> fields.add(value));
            }
        });
        List<SingleFileFieldInfo> zbFields = config.getZbFields();
        Map<String, List<SingleFileFieldInfo>> netFormMap = fields.stream().collect(Collectors.groupingBy(SingleFileFieldInfo::getNetFormCode));
        for (String form : netFormMap.keySet()) {
            zbFields = this.updateConfigZb(form, netFormMap.get(form), zbFields);
        }
        config.setZbFields(zbFields);
    }

    @Override
    public void saveEnumItem(List<SingleFileEnumInfo> enumInfo, ISingleMappingConfig config) {
    }

    @Override
    public List<SingleFileFieldInfo> updateConfigZb(String formCode, List<SingleFileFieldInfo> saveZbFields, List<SingleFileFieldInfo> historyFields) {
        List emptyCodeGroup = historyFields.stream().filter(e -> e.getNetFieldCode() == null || "".equals(e.getNetFieldCode())).collect(Collectors.toList());
        Map<String, List<SingleFileFieldInfo>> configNetCodeGroup = historyFields.stream().filter(e -> e.getNetFieldCode() != null && !"".equals(e.getNetFieldCode())).collect(Collectors.toList()).stream().collect(Collectors.groupingBy(SingleFileFieldInfo::getNetFieldCode));
        ArrayList<SingleFileFieldInfo> allFields = new ArrayList<SingleFileFieldInfo>();
        for (int i = 0; i < saveZbFields.size(); ++i) {
            String netFieldCode = saveZbFields.get(i).getNetFieldCode();
            String singleCode = saveZbFields.get(i).getFieldCode();
            List<SingleFileFieldInfo> configNetFieldInfos = configNetCodeGroup.get(netFieldCode);
            boolean find = false;
            if (configNetFieldInfos != null && !configNetFieldInfos.isEmpty()) {
                boolean flag;
                int j;
                int idx = 0;
                for (j = 0; j < configNetFieldInfos.size(); ++j) {
                    if (!formCode.equals(configNetFieldInfos.get(j).getNetFormCode()) || !StringUtils.isNotEmpty((CharSequence)saveZbFields.get(i).getNetDataLinkKey()) || !saveZbFields.get(i).getNetDataLinkKey().equals(configNetFieldInfos.get(j).getNetDataLinkKey())) continue;
                    flag = false;
                    if (saveZbFields.get(i).getFloatEnumType() == 0) {
                        if (configNetFieldInfos.get(j).getFloatEnumType() == 0) {
                            flag = true;
                        }
                    } else if (StringUtils.isNotEmpty((CharSequence)saveZbFields.get(i).getFloatEnumCode()) && StringUtils.isNotEmpty((CharSequence)configNetFieldInfos.get(j).getFloatEnumCode())) {
                        flag = saveZbFields.get(i).getFloatEnumCode().equals(configNetFieldInfos.get(j).getFloatEnumCode());
                    }
                    if (!flag) continue;
                    idx = j;
                    find = true;
                    break;
                }
                if (find) {
                    configNetFieldInfos.set(idx, saveZbFields.get(i));
                    continue;
                }
                for (j = 0; j < configNetFieldInfos.size(); ++j) {
                    if (!formCode.equals(configNetFieldInfos.get(j).getFormCode()) || !StringUtils.isNotEmpty((CharSequence)saveZbFields.get(i).getNetDataLinkKey()) || !saveZbFields.get(i).getNetDataLinkKey().equals(configNetFieldInfos.get(j).getNetDataLinkKey())) continue;
                    flag = false;
                    if (saveZbFields.get(i).getFloatEnumType() == 0) {
                        if (configNetFieldInfos.get(j).getFloatEnumType() == 0) {
                            flag = true;
                        }
                    } else if (StringUtils.isNotEmpty((CharSequence)saveZbFields.get(i).getFloatEnumCode()) && StringUtils.isNotEmpty((CharSequence)configNetFieldInfos.get(j).getFloatEnumCode())) {
                        flag = saveZbFields.get(i).getFloatEnumCode().equals(configNetFieldInfos.get(j).getFloatEnumCode());
                    }
                    if (!flag) continue;
                    idx = j;
                    find = true;
                    break;
                }
                if (find) {
                    configNetFieldInfos.set(idx, saveZbFields.get(i));
                    continue;
                }
                allFields.add(saveZbFields.get(i));
                continue;
            }
            if (singleCode == null || "".equals(singleCode)) continue;
            allFields.add(saveZbFields.get(i));
        }
        allFields.addAll(emptyCodeGroup);
        configNetCodeGroup.forEach((field, items) -> allFields.addAll((Collection<SingleFileFieldInfo>)items));
        return allFields;
    }

    protected boolean findItem(List<SingleFileFormulaItem> formFilter, SingleFileFormulaItem updateItem, List<SingleFileFormulaItem> singleItems) {
        boolean find = false;
        boolean findByKey = true;
        Optional<SingleFileFormulaItem> findItem = formFilter.stream().filter(item -> StringUtils.isNotEmpty((CharSequence)item.getNetFormulaKey()) && item.getNetFormulaKey().equals(updateItem.getNetFormulaKey())).findFirst();
        if (!findItem.isPresent()) {
            findByKey = false;
            findItem = formFilter.stream().filter(item -> StringUtils.isNotEmpty((CharSequence)item.getNetFormulaCode()) && item.getNetFormulaCode().equals(updateItem.getNetFormulaCode())).findFirst();
        }
        if (findItem.isPresent()) {
            find = true;
            SingleFileFormulaItem item2 = findItem.get();
            if (singleItems != null) {
                item2 = this.setSingleItem(item2, singleItems.get(0));
            } else {
                item2.setSingleFormulaExp(null);
                item2.setSingleSchemeName(null);
                item2.setSingleTableCode(null);
            }
            if (!findByKey) {
                item2.setNetFormulaKey(updateItem.getNetFormulaKey());
                item2.setNetFormulaExp(updateItem.getNetFormulaExp());
            }
            item2.setNetFormulaCode(updateItem.getNetFormulaCode());
            item2.setSingleFormulaCode(updateItem.getSingleFormulaCode());
            item2.setNetSchemeName(updateItem.getNetSchemeName());
            item2.setNetSchemeKey(updateItem.getNetSchemeKey());
            formFilter.removeIf(f -> StringUtils.isNotEmpty((CharSequence)f.getNetFormulaKey()) && f.getNetFormulaKey().equals(updateItem.getNetFormulaKey()));
            formFilter.add(item2);
        }
        return find;
    }

    protected SingleFileFormulaItem appendItem(FormulaSchemeDefine schemeDefine, FormulaDefine formulaDefine, SingleFileFormulaItem e, List<SingleFileFormulaItem> singleItems) {
        if (singleItems != null) {
            e = this.setSingleItem(e, singleItems.get(0));
        }
        if (formulaDefine != null) {
            e.setNetFormKey(formulaDefine.getFormKey());
            e.setNetSchemeKey(schemeDefine.getKey());
            e.setNetSchemeName(schemeDefine.getTitle());
            e.setNetFormulaExp(formulaDefine.getExpression());
            e.setNetFormulaKey(formulaDefine.getKey());
        }
        return e;
    }

    private SingleFileFormulaItem setSingleItem(SingleFileFormulaItem e, SingleFileFormulaItem singleItems) {
        e.setSingleFormulaExp(singleItems.getSingleFormulaExp());
        e.setSingleSchemeName(singleItems.getSingleSchemeName());
        e.setSingleTableCode(singleItems.getSingleTableCode());
        return e;
    }

    private List<IEntityRow> getEntityData(String formSchemeKey) {
        ArrayList<IEntityRow> iEntityRows = new ArrayList();
        try {
            iEntityRows = this.mappingConfigService.listEntityRows(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return iEntityRows;
    }
}

