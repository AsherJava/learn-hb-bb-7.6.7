/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.facade.extend.ICalFmlProvider;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.data.logic.facade.extend.param.FmlExecInfo;
import com.jiuqi.nr.data.logic.facade.extend.param.GetCalFmlEnv;
import com.jiuqi.nr.data.logic.internal.helper.CalculateHelper;
import com.jiuqi.nr.data.logic.internal.obj.FmlZBInfo;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.FmlUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DefaultCalFmlProvider
implements ICalFmlProvider {
    public static final String TYPE = "DefaultCalFmlProvider";
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private CalculateHelper calculateHelper;

    @Override
    public List<FmlExecInfo> getFml(GetCalFmlEnv env) {
        List<IParsedExpression> zbWriteFml;
        ArrayList<FmlExecInfo> result = new ArrayList<FmlExecInfo>();
        ArrayList<IParsedExpression> parsedExpressions = new ArrayList<IParsedExpression>(env.getParsedExpressions());
        Map<Boolean, List<IParsedExpression>> collect = parsedExpressions.stream().collect(Collectors.partitioningBy(FmlUtil::needZBWriteFml));
        List<IParsedExpression> accessFml = collect.get(false);
        if (!CollectionUtils.isEmpty(accessFml)) {
            FmlExecInfo fmlExecInfo = new FmlExecInfo(env.getDimensionValueSet(), accessFml);
            result.add(fmlExecInfo);
        }
        if (!CollectionUtils.isEmpty(zbWriteFml = collect.get(true))) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(env.getFormSchemeKey());
            String contextMainDimId = this.entityUtil.getContextMainDimId(formScheme.getDw());
            DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory = env.getProviderStore().getDataPermissionEvaluatorFactory();
            DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(env.getDimensionValueSet(), env.getFormSchemeKey(), (SpecificDimBuilder)new FixedDimBuilder(contextMainDimId, env.getDimensionValueSet()));
            FmlZBInfo fmlZBInfo = this.calculateHelper.getFmlZBInfo(zbWriteFml);
            DataPermissionEvaluator dataPermissionEvaluator = dataPermissionEvaluatorFactory.createEvaluator(new EvaluatorParam(formScheme.getTaskKey(), formScheme.getKey(), ResouceType.ZB.getCode()), dimensionCollection, fmlZBInfo.getAllWriteZBKeys());
            List<DimensionValueSet> dimensionValueSetList = DimensionUtil.getDimensionValueSetList(env.getDimensionValueSet());
            for (DimensionValueSet singleDim : dimensionValueSetList) {
                DimensionCombination combination = new DimensionCombinationBuilder(singleDim).getCombination();
                List<IParsedExpression> writeAccessFml = zbWriteFml.stream().filter(o -> CalculateHelper.writeAccess(o, combination, fmlZBInfo, dataPermissionEvaluator)).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(writeAccessFml)) continue;
                FmlExecInfo fmlExecInfo = new FmlExecInfo(singleDim, writeAccessFml);
                result.add(fmlExecInfo);
            }
            if (dimensionValueSetList.size() == 1 && result.size() == 2) {
                FmlExecInfo bjFmlExecInfo = (FmlExecInfo)result.get(1);
                ((FmlExecInfo)result.get(0)).getParsedExpressions().addAll(bjFmlExecInfo.getParsedExpressions());
                result.remove(1);
            }
        }
        return result;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean isUse(GetCalFmlEnv env) {
        return false;
    }
}

