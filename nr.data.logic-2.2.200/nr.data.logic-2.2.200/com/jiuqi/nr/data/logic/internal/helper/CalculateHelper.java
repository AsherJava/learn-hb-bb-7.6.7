/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.exception.UnknownReadWriteException
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.logic.internal.helper;

import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.facade.listener.obj.CalculateInfo;
import com.jiuqi.nr.data.logic.internal.obj.FmlZBInfo;
import com.jiuqi.nr.data.logic.internal.service.ICalculateInfoPublish;
import com.jiuqi.nr.data.logic.internal.util.FmlUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CalculateHelper {
    private static final Logger logger = LoggerFactory.getLogger(CalculateHelper.class);
    @Autowired
    private ICalculateInfoPublish calculateInfoPublish;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public void publishCalInfo(DimensionCollection dimensionCollection, FormSchemeDefine formScheme, List<String> calFormSet) {
        CalculateInfo calculateInfo = new CalculateInfo();
        calculateInfo.setTaskKey(formScheme.getTaskKey());
        calculateInfo.setFormSchemeKey(formScheme.getKey());
        calculateInfo.setDimensionCollection(dimensionCollection);
        calculateInfo.setFormKeys(calFormSet);
        this.calculateInfoPublish.publishMessage(calculateInfo);
    }

    public List<IParsedExpression> getZBWriteAccessFml(FormSchemeDefine formScheme, DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory, DimensionCombination dimensionCombination, List<IParsedExpression> expressions) {
        List<IParsedExpression> zbWriteFml;
        if (CollectionUtils.isEmpty(expressions)) {
            return Collections.emptyList();
        }
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        Map<Boolean, List<IParsedExpression>> collect = expressions.stream().collect(Collectors.partitioningBy(FmlUtil::needZBWriteFml));
        List<IParsedExpression> accessFml = collect.get(false);
        if (!CollectionUtils.isEmpty(accessFml)) {
            result.addAll(accessFml);
        }
        if (!CollectionUtils.isEmpty(zbWriteFml = collect.get(true))) {
            FmlZBInfo fmlZBInfo = this.getFmlZBInfo(zbWriteFml);
            DataPermissionEvaluator dataPermissionEvaluator = dataPermissionEvaluatorFactory.createEvaluator(new EvaluatorParam(formScheme.getTaskKey(), formScheme.getKey(), ResouceType.ZB.getCode()), dimensionCombination, fmlZBInfo.getAllWriteZBKeys());
            List writeAccessFml = zbWriteFml.stream().filter(o -> CalculateHelper.writeAccess(o, dimensionCombination, fmlZBInfo, dataPermissionEvaluator)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(writeAccessFml)) {
                result.addAll(writeAccessFml);
            }
        }
        return result;
    }

    public FmlZBInfo getFmlZBInfo(@NonNull List<IParsedExpression> zbWriteFml) {
        HashMap<String, List<String>> fmlWriteZBKeyMap = new HashMap<String, List<String>>();
        HashSet<String> allWriteZBKeys = new HashSet<String>();
        for (IParsedExpression fml : zbWriteFml) {
            QueryFields writeFields = null;
            try {
                writeFields = fml.getWriteQueryFields();
            }
            catch (UnknownReadWriteException e) {
                logger.warn("{}-\u83b7\u53d6\u5199\u6307\u6807\u5f02\u5e38:{}", new Object[]{fml.getSource(), e.getMessage(), e});
            }
            if (writeFields == null) continue;
            for (QueryField writeField : writeFields) {
                String columnKey = writeField.getUID();
                DataField dataFieldByColumnKey = this.runtimeDataSchemeService.getDataFieldByColumnKey(columnKey);
                if (dataFieldByColumnKey == null) continue;
                allWriteZBKeys.add(dataFieldByColumnKey.getKey());
                if (fmlWriteZBKeyMap.containsKey(fml.getKey())) {
                    ((List)fmlWriteZBKeyMap.get(fml.getKey())).add(dataFieldByColumnKey.getKey());
                    continue;
                }
                ArrayList<String> writeZBKeys = new ArrayList<String>();
                writeZBKeys.add(dataFieldByColumnKey.getKey());
                fmlWriteZBKeyMap.put(fml.getKey(), writeZBKeys);
            }
        }
        return new FmlZBInfo(allWriteZBKeys, fmlWriteZBKeyMap);
    }

    public static boolean writeAccess(IParsedExpression expression, DimensionCombination dimensionCombination, FmlZBInfo fmlZBInfo, DataPermissionEvaluator dataPermissionEvaluator) {
        List<String> writeZBKeys = fmlZBInfo.getFmlWriteZBKeyMap().get(expression.getKey());
        if (CollectionUtils.isEmpty(writeZBKeys)) {
            return true;
        }
        boolean access = true;
        for (String writeZBKey : writeZBKeys) {
            boolean haveAccess = dataPermissionEvaluator.haveAccess(dimensionCombination, writeZBKey, AuthType.SYS_WRITEABLE);
            if (haveAccess) continue;
            access = false;
            break;
        }
        return access;
    }
}

