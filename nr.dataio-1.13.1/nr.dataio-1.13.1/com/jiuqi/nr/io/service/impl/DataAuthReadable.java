/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataAuthReadable {
    private static final Logger logger = LoggerFactory.getLogger(DataAuthReadable.class);
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;

    public IDataAccessService readable(String taskKey, String formSchemeKey) {
        return this.dataAccessServiceProvider.getDataAccessService(taskKey, formSchemeKey);
    }

    public List<IAccessResult> hasAuth(IDataAccessService dataAccessService, DimensionCombination masterKey, List<String> formKeys) {
        ArrayList<IAccessResult> res = new ArrayList<IAccessResult>();
        for (String formKey : formKeys) {
            IAccessResult readable = dataAccessService.readable(masterKey, formKey);
            res.add(readable);
        }
        return res;
    }

    public List<String> hasAuthForms(IDataAccessService dataAccessService, DimensionCombination masterKey, List<String> formKeys) {
        ArrayList<String> res = new ArrayList<String>();
        for (String formKey : formKeys) {
            IAccessResult readable = dataAccessService.readable(masterKey, formKey);
            try {
                if (!readable.haveAccess()) continue;
                res.add(formKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return res;
    }

    public DimensionValueSet hasAuthFormUnits(IDataAccessService dataAccessService, DimensionValueSet masterKeys, String formKey, String formSchemeKey) {
        DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(masterKeys, formSchemeKey);
        IBatchAccessResult accessResult = dataAccessService.getReadAccess(dimensionCollection, Collections.singletonList(formKey));
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        Iterator iterator = dimensionCombinations.iterator();
        while (iterator.hasNext()) {
            DimensionCombination masterKey = (DimensionCombination)iterator.next();
            IAccessResult readable = accessResult.getAccess(masterKey, formKey);
            try {
                if (readable.haveAccess()) continue;
                iterator.remove();
            }
            catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        ArrayList<Map<String, DimensionValue>> dimensionSetList = new ArrayList<Map<String, DimensionValue>>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            dimensionSetList.add(this.getDimensionSet(dimensionValueSet));
        }
        return this.getDimensionValueSet(this.mergeDimensionSetMap(dimensionSetList));
    }

    private Map<String, DimensionValue> getDimensionSet(DimensionValueSet dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            DimensionValue value = new DimensionValue();
            value.setName(dimensionValueSet.getName(i));
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue == null) continue;
            if (dimensionValue instanceof List) {
                value.setValue(StringUtils.join(((List)dimensionValue).iterator(), (String)";"));
            } else {
                value.setValue(dimensionValue.toString());
            }
            dimensionSet.put(value.getName(), value);
        }
        return dimensionSet;
    }

    private DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (value.getValue() == null) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    private Map<String, DimensionValue> mergeDimensionSetMap(List<Map<String, DimensionValue>> dimensions) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        HashMap<String, Set> valueMap = new HashMap<String, Set>();
        for (Map<String, DimensionValue> dimension : dimensions) {
            Set<String> keys = dimension.keySet();
            for (String key2 : keys) {
                String dimensionValue = dimension.get(key2).getValue();
                HashSet<String> valueSet = (HashSet<String>)valueMap.get(key2);
                if (valueSet == null) {
                    valueSet = new HashSet<String>();
                    valueMap.put(key2, valueSet);
                }
                if (dimensionValue == null) continue;
                if (dimensionValue instanceof List) {
                    valueSet.addAll((List)((Object)dimensionValue));
                    continue;
                }
                valueSet.add(dimensionValue);
            }
        }
        valueMap.forEach((key, val) -> {
            DimensionValue dimensionObj = (DimensionValue)dimensionSet.get(key);
            if (Objects.isNull(dimensionObj)) {
                dimensionObj = new DimensionValue();
                dimensionObj.setName(key);
            }
            dimensionObj.setValue(StringUtils.join(val.iterator(), (String)";"));
            dimensionSet.put((String)key, dimensionObj);
        });
        return dimensionSet;
    }
}

