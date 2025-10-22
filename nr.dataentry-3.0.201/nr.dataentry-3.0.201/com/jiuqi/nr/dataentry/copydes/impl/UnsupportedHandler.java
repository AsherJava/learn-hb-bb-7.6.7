/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.dataentry.copydes.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder;
import com.jiuqi.nr.dataentry.copydes.CheckDesFmlObj;
import com.jiuqi.nr.dataentry.copydes.HandleParam;
import com.jiuqi.nr.dataentry.copydes.IUnsupportedDesHandler;
import com.jiuqi.nr.dataentry.copydes.impl.ExtensionFactoryImpl;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class UnsupportedHandler
implements IUnsupportedDesHandler {
    private static final Logger logger = LoggerFactory.getLogger(UnsupportedHandler.class);
    private final Map<String, String> bizKeyOrderMap;
    private final ExtensionFactoryImpl extensionFactory;

    public UnsupportedHandler(ExtensionFactoryImpl extensionFactory, Map<String, String> bizKeyOrderMap) {
        this.extensionFactory = extensionFactory;
        this.bizKeyOrderMap = bizKeyOrderMap;
    }

    @Override
    public int handleUnsupportedDes(HandleParam par) {
        if (CollectionUtils.isEmpty(this.bizKeyOrderMap)) {
            logger.debug("\u6620\u5c04\u5173\u7cfb\u4e3a\u7a7a");
            return 0;
        }
        List<CheckDesFmlObj> unsupportedSrcDes = par.getUnsupportedSrcDes();
        if (CollectionUtils.isEmpty(unsupportedSrcDes)) {
            logger.debug("\u5f85\u5904\u7406\u7684\u6765\u6e90\u51fa\u9519\u8bf4\u660e\u4e3a\u7a7a");
            return 0;
        }
        Map<String, List<CheckDesFmlObj>> unsupportedDstDesMap = par.getUnsupportedDstDesMap();
        if (CollectionUtils.isEmpty(unsupportedDstDesMap)) {
            logger.debug("\u51fa\u9519\u8bf4\u660e\u7684\u540c\u6b65\u5173\u7cfb\u4e3a\u7a7a");
            return 0;
        }
        ArrayList<CheckDesObj> saveDesObjs = new ArrayList<CheckDesObj>();
        HashSet<DimensionValueSet> saveDims = new HashSet<DimensionValueSet>();
        FormSchemeDefine dstFormScheme = this.extensionFactory.getRunTimeViewController().getFormScheme(par.getDstFormSchemeKey());
        List<String> fsDimNames = this.getFSDimNames(dstFormScheme);
        for (CheckDesFmlObj unsupportedSrcDe : unsupportedSrcDes) {
            String srcRecord = unsupportedSrcDe.getRecordId();
            List<CheckDesFmlObj> dstDesList = unsupportedDstDesMap.get(srcRecord);
            if (CollectionUtils.isEmpty(dstDesList)) continue;
            for (CheckDesFmlObj o : dstDesList) {
                CheckDesObj checkDesObj = new CheckDesObj();
                checkDesObj.setFormulaSchemeKey(o.getFormulaSchemeKey());
                checkDesObj.setFormKey(o.getFormKey());
                checkDesObj.setFormulaExpressionKey(o.getFormulaExpressionKey());
                checkDesObj.setFormulaCode(o.getFormulaCode());
                checkDesObj.setGlobRow(o.getGlobRow());
                checkDesObj.setGlobCol(o.getGlobCol());
                checkDesObj.setCheckDescription(o.getCheckDescription());
                checkDesObj.setDimensionSet(this.mapBizKey(o.getDimensionSet()));
                saveDesObjs.add(checkDesObj);
                saveDims.add(o.getDimensionValueSet(fsDimNames));
            }
        }
        CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
        checkDesQueryParam.setFormulaSchemeKey(Collections.singletonList(par.getDstFmlSchemeKey()));
        DimensionValueSet dimensionValueSet = this.mergeDimensionValueSet(saveDims);
        String mainDimId = this.getContextMainDimId(dstFormScheme.getDw());
        FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(mainDimId, dimensionValueSet);
        DimensionCollection dimensionCollection = this.extensionFactory.getDimensionCollectionUtil().getDimensionCollection(dimensionValueSet, par.getDstFormSchemeKey(), (SpecificDimBuilder)fixedDimBuilder);
        checkDesQueryParam.setDimensionCollection(dimensionCollection);
        CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
        checkDesBatchSaveObj.setCheckDesQueryParam(checkDesQueryParam);
        checkDesBatchSaveObj.setCheckDesObjs(saveDesObjs);
        checkDesBatchSaveObj.setUpdateCurUsrTime(par.isUpdateUserTime());
        this.extensionFactory.getCheckErrorDescriptionService().batchSaveFormulaCheckDes(checkDesBatchSaveObj);
        return saveDesObjs.size();
    }

    private Map<String, DimensionValue> mapBizKey(Map<String, DimensionValue> dimensionSet) {
        DimensionValue bizDim = dimensionSet.get("ID");
        if (bizDim == null) {
            return dimensionSet;
        }
        String oldBiz = bizDim.getValue();
        if (!this.bizKeyOrderMap.containsKey(oldBiz)) {
            return dimensionSet;
        }
        String newBiz = this.bizKeyOrderMap.get(oldBiz);
        DimensionValue biz = new DimensionValue();
        biz.setName("ID");
        biz.setValue(newBiz);
        dimensionSet.put("ID", biz);
        return dimensionSet;
    }

    private DimensionValueSet mergeDimensionValueSet(Collection<DimensionValueSet> dimensionValueSets) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (!CollectionUtils.isEmpty(dimensionValueSets)) {
            HashMap<String, Set> map = new HashMap<String, Set>();
            for (DimensionValueSet valueSet : dimensionValueSets) {
                for (int i = 0; i < valueSet.size(); ++i) {
                    if (map.containsKey(valueSet.getName(i))) {
                        ((Set)map.get(valueSet.getName(i))).add((String)valueSet.getValue(i));
                        continue;
                    }
                    HashSet<String> v = new HashSet<String>();
                    v.add((String)valueSet.getValue(i));
                    map.put(valueSet.getName(i), v);
                }
            }
            map.forEach((key, value) -> {
                if (value.size() == 1) {
                    dimensionValueSet.setValue(key, value.stream().findAny().get());
                } else {
                    dimensionValueSet.setValue(key, new ArrayList(value));
                }
            });
        }
        return dimensionValueSet;
    }

    private String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }

    private List<String> getFSDimNames(FormSchemeDefine formSchemeDefine) {
        ArrayList<String> result = new ArrayList<String>();
        String dw = formSchemeDefine.getDw();
        String dateTime = formSchemeDefine.getDateTime();
        String dims = formSchemeDefine.getDims();
        result.add(this.extensionFactory.getEntityMetaService().getDimensionName(dw));
        result.add(this.extensionFactory.getPeriodEngineService().getPeriodAdapter().getPeriodDimensionName(dateTime));
        List<Object> dimEntityIds = new ArrayList();
        if (StringUtils.isNotEmpty((String)dims)) {
            dimEntityIds = Arrays.asList(dims.split(";"));
            for (String string : dimEntityIds) {
                if (AdjustUtils.isAdjust((String)string).booleanValue()) {
                    result.add("ADJUST");
                    continue;
                }
                result.add(this.extensionFactory.getEntityMetaService().getDimensionName(string));
            }
        }
        if (!dimEntityIds.contains("ADJUST") && this.extensionFactory.getFormSchemeService().enableAdjustPeriod(formSchemeDefine.getKey())) {
            result.add("ADJUST");
        }
        return result;
    }
}

