/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 */
package nr.single.map.configurations.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ReportFormTree;
import nr.single.map.configurations.internal.bean.DictionaryData;
import nr.single.map.configurations.internal.bean.QueryParam;
import nr.single.map.configurations.internal.bean.RegionDictionaryData;
import nr.single.map.configurations.service.FormulaSchemeService;
import nr.single.map.configurations.vo.FormulaDefineVO;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.util.SingleMapEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class FormulaSchemeServiceImpl
implements FormulaSchemeService {
    private static final Logger logger = LoggerFactory.getLogger(FormulaSchemeServiceImpl.class);
    @Autowired
    private IFormulaRunTimeController runTimeFormula;
    @Autowired
    private IRunTimeViewController runtimeCtrl;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private SingleMapEntityUtil mapEntityUtil;

    @Override
    public List<FormulaSchemeDefine> getFormulaSchemesByReport(String reportKey) {
        List allFormula = this.runTimeFormula.getAllFormulaSchemeDefinesByFormScheme(reportKey);
        List emptyOrder = allFormula.stream().filter(f -> f.getOrder() == null).collect(Collectors.toList());
        List<FormulaSchemeDefine> sorted = allFormula.stream().filter(f -> f.getOrder() != null).sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        sorted.addAll(emptyOrder);
        return sorted;
    }

    @Override
    public List<FormulaDefineVO> getAllFormulas(String reportKey) {
        List allFormulas = this.runTimeFormula.getAllFormulasInScheme(reportKey);
        List emptyOrder = allFormulas.stream().filter(f -> f.getOrder() == null).collect(Collectors.toList());
        List sorted = allFormulas.stream().filter(f -> f.getOrder() != null).sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        sorted.addAll(emptyOrder);
        return sorted.stream().map(FormulaDefineVO::getInstance).collect(Collectors.toList());
    }

    @Override
    public List<FormulaDefineVO> getFormulasByForm(String formulaSchemeKey, String formKey) {
        if ("00000000-0000-0000-0000-000000000000".equals(formKey)) {
            formKey = null;
        }
        List<Object> distinctFormula = new ArrayList();
        List allFormulasInForm = this.runTimeFormula.getAllFormulasInForm(formulaSchemeKey, formKey);
        try {
            List formulaDefines = this.runTimeFormula.queryPublicFormulaDefineByScheme(formulaSchemeKey, formKey);
            allFormulasInForm.addAll(formulaDefines);
            distinctFormula = allFormulasInForm.stream().distinct().collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        List emptyOrder = distinctFormula.stream().filter(f -> f.getOrder() == null).collect(Collectors.toList());
        List sorted = distinctFormula.stream().filter(f -> f.getOrder() != null).sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        sorted.addAll(emptyOrder);
        return sorted.stream().map(FormulaDefineVO::getInstance).collect(Collectors.toList());
    }

    @Override
    public List<ReportFormTree> buildReportTree(String formulaScheme) {
        ArrayList<ReportFormTree> tree = new ArrayList<ReportFormTree>();
        AtomicBoolean flag = new AtomicBoolean(false);
        List<FormGroupDefine> groupDefines = this.runtimeCtrl.queryRootGroupsByFormScheme(formulaScheme).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
        groupDefines.forEach(item -> {
            ReportFormTree node = new ReportFormTree();
            node.setId(item.getKey());
            node.setTitle(item.getTitle());
            node.setCode(item.getCode());
            node.setType("NODE_TYPE_GROUP");
            List<Object> forms = new ArrayList();
            try {
                forms = this.runtimeCtrl.getAllFormsInGroup(item.getKey(), true).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).collect(Collectors.toList());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            node.setExpand(forms.size() > 0);
            ArrayList<ReportFormTree> childs = new ArrayList<ReportFormTree>();
            forms.forEach(f -> {
                ReportFormTree children = new ReportFormTree();
                children.setId(f.getKey());
                children.setTitle(f.getTitle());
                children.setCode(f.getFormCode());
                children.setType("NODE_TYPE_FORM");
                children.setExpand(false);
                children.setFormType(f.getFormType().getValue());
                childs.add(children);
            });
            node.setChildren(childs);
            if (childs.size() > 0 && !flag.get()) {
                ((ReportFormTree)childs.get(0)).setSelected(true);
                flag.set(true);
            }
            tree.add(node);
        });
        return tree;
    }

    @Override
    public List<FormulaDefine> searchFormulaDefine(QueryParam param) {
        List queryFormulaDefine = this.runTimeFormula.searchFormulaInScheme(param.getKeyWords(), param.getSchemeKey());
        if (!param.getRepotKey().isEmpty()) {
            queryFormulaDefine = queryFormulaDefine.stream().filter(e -> e.getFormKey().equals(param.getRepotKey())).collect(Collectors.toList());
        }
        return queryFormulaDefine;
    }

    @Override
    public Map<String, RegionDictionaryData> queryEnumDataInFloatRegion(String form) {
        HashMap<String, RegionDictionaryData> regionData = new HashMap<String, RegionDictionaryData>();
        ExecutorContext context = new ExecutorContext(this.runtimeController);
        List allRegionsInForm = this.runtimeCtrl.getAllRegionsInForm(form);
        List floatRegion = allRegionsInForm.stream().filter(e -> !e.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_SIMPLE)).collect(Collectors.toList());
        for (DataRegionDefine dataRegionDefine : floatRegion) {
            String[] fieldKeys;
            RegionSettingDefine regionSetting = this.runtimeCtrl.getRegionSetting(dataRegionDefine.getKey());
            String dictionaryFillLinks = regionSetting.getDictionaryFillLinks();
            if (StringUtils.isEmpty((String)dictionaryFillLinks)) continue;
            RegionDictionaryData regionDictionaryData = new RegionDictionaryData();
            regionDictionaryData.setTop(dataRegionDefine.getRegionTop());
            regionDictionaryData.setBottom(dataRegionDefine.getRegionBottom());
            regionDictionaryData.setLeft(dataRegionDefine.getRegionLeft());
            regionDictionaryData.setRight(dataRegionDefine.getRegionRight());
            regionDictionaryData.setRegionKind(dataRegionDefine.getRegionKind());
            regionData.put(dataRegionDefine.getKey(), regionDictionaryData);
            for (String fieldKey : fieldKeys = dictionaryFillLinks.split(";")) {
                FieldDefine fieldDefine = null;
                try {
                    fieldDefine = this.runtimeController.queryFieldDefine(fieldKey);
                }
                catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
                ArrayList<DictionaryData> datas = new ArrayList<DictionaryData>();
                try {
                    List links;
                    List<DataEntityInfo> rows = this.mapEntityUtil.queryEntityDataRows(fieldDefine.getEntityKey());
                    if (rows != null && rows.size() > 0) {
                        for (DataEntityInfo row : rows) {
                            DictionaryData data = new DictionaryData();
                            data.setCode(row.getEntityKey());
                            data.setTitle(row.getEntityTitle());
                            datas.add(data);
                        }
                    }
                    if (CollectionUtils.isEmpty(links = this.runtimeCtrl.getLinksInRegionByField(dataRegionDefine.getKey(), fieldKey))) continue;
                    regionDictionaryData.put(((DataLinkDefine)links.get(0)).getKey(), datas);
                }
                catch (Exception e3) {
                    logger.error(e3.getMessage(), e3);
                }
            }
        }
        return regionData;
    }
}

