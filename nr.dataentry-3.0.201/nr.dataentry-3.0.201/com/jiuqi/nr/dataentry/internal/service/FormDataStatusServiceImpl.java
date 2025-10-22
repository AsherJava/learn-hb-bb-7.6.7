/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.common.exception.ExceptionCodeCost
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionContext
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.common.exception.ExceptionCodeCost;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.dataentry.service.IFormDataStatusService;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.datastatus.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={NpRollbackException.class})
public class FormDataStatusServiceImpl
implements IFormDataStatusService {
    @Resource
    IRunTimeViewController runTimeViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Resource
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IDataStatusService dataStatusService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;
    private final Logger logger = LoggerFactory.getLogger(FormDataStatusServiceImpl.class);

    @Override
    public List<String> getFilledPeriod(JtableContext context) {
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet(context);
        if (dimensionValueSet.getValue("DATATIME") != null) {
            dimensionValueSet.clearValue("DATATIME");
            dimensionValueSet.clearAll();
        }
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, context.getFormSchemeKey());
        return this.dataStatusService.getFilledPeriod(context.getFormSchemeKey(), dimensionCollection);
    }

    private DimensionValueSet getDimensionValueSet(JtableContext context) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{context.getFormSchemeKey().toString()});
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)context);
        return dimensionValueSet;
    }

    @Override
    public List<String> getFilledFormkey(JtableContext jtableContext) {
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet(jtableContext);
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionValueSet);
        return this.dataStatusService.getFilledFormKey(jtableContext.getFormSchemeKey(), builder.getCombination());
    }

    @Override
    public Map<String, String> getFilledPeriodBySql(List<String> formSchemeKeyList, List<String> dates) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKeyList.get(0));
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(ExceptionCodeCost.NOTFOUND_FORMSHCEME_BYKEY, new String[]{formSchemeKeyList.get(0)});
        }
        ArrayList<String> periodCodes = new ArrayList<String>(Arrays.asList("N", "H", "J", "Y", "X", "R", "Z", "B"));
        String dimensionName = this.periodEntityAdapter.getPeriodEntity(formScheme.getDateTime()).getDimensionName();
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        dimensionCollectionBuilder.setEntityValue(dimensionName, formScheme.getDateTime(), dates.toArray());
        String dwDimName = this.entityMetaService.getDimensionName(formScheme.getDw());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", new DimensionProviderData(null, taskDefine.getDataScheme()));
        dimensionCollectionBuilder.addVariableDW(dwDimName, formScheme.getDw(), dimensionProvider);
        dimensionCollectionBuilder.setContext(new DimensionContext(formScheme.getTaskKey()));
        DimensionCollection dimensionCollection = dimensionCollectionBuilder.getCollection();
        ArrayList periods = new ArrayList();
        ArrayList customPeriods = new ArrayList();
        for (String formSchemeKey : formSchemeKeyList) {
            if (periodCodes.contains(this.periodEntityAdapter.getPeriodEntity(formScheme.getDateTime()).getCode())) {
                periods.addAll(this.dataStatusService.getFilledPeriod(formSchemeKey, dimensionCollection));
                continue;
            }
            customPeriods.addAll(this.dataStatusService.getFilledPeriod(formSchemeKey, dimensionCollection));
        }
        HashMap adjustMap = new HashMap();
        List adjustPeriods = this.adjustPeriodService.queryAdjustPeriods(taskDefine.getDataScheme());
        ArrayList records = new ArrayList();
        records.addAll(periods);
        records.addAll(customPeriods);
        if (records.size() > 0 && adjustPeriods.size() > 0) {
            for (String period : records) {
                ArrayList filledAdjust = new ArrayList();
                for (String string : formSchemeKeyList) {
                    filledAdjust.addAll(this.dataStatusService.getFilledAdjust(string, period));
                }
                ArrayList<String> adjustStr = new ArrayList<String>();
                for (String s : filledAdjust) {
                    if (s.equals("0")) {
                        adjustStr.add("noAdjust");
                        continue;
                    }
                    adjustStr.add(s);
                }
                adjustMap.put(period, adjustStr);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> resultMap = new HashMap<String, String>();
        try {
            String adjustStr = objectMapper.writeValueAsString(adjustMap);
            String periodStr = objectMapper.writeValueAsString(periods);
            String string = objectMapper.writeValueAsString(customPeriods);
            resultMap.put("adjustStr", adjustStr);
            resultMap.put("periodStr", periodStr);
            resultMap.put("customPeriodStr", string);
            return resultMap;
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

