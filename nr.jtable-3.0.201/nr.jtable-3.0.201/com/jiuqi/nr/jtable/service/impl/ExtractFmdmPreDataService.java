/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.ISaveInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveDataBuilder
 *  com.jiuqi.nr.datacrud.SaveDataBuilderFactory
 *  com.jiuqi.nr.datacrud.SaveResItem
 *  com.jiuqi.nr.datacrud.SaveReturnRes
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.api.IDataService
 *  com.jiuqi.nr.datacrud.impl.RegionRelation
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.out.CrudSaveException
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.ISaveInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveDataBuilder;
import com.jiuqi.nr.datacrud.SaveDataBuilderFactory;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveReturnRes;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.api.IDataService;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.CrudSaveException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IExtractFmdmPreDataService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractFmdmPreDataService
implements IExtractFmdmPreDataService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private IDataService dataService;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private SaveDataBuilderFactory saveDataBuilderFactory;

    @Override
    public String extractFmdmPreData(JtableContext jtableContext) {
        String formKey = jtableContext.getFormKey();
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        FormType formType = formDefine.getFormType();
        if (formType != FormType.FORM_TYPE_NEWFMDM) {
            return "\u5f53\u524d\u8868\u4e0d\u662f\u5c01\u9762\u4ee3\u7801\u8868\uff01";
        }
        List regionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
        if (regionDefines == null || regionDefines.size() == 0) {
            return "\u5f53\u524d\u5c01\u9762\u4ee3\u7801\u8868\u4e0d\u5b58\u5728\u533a\u57df\uff01";
        }
        String regionKey = ((DataRegionDefine)regionDefines.get(0)).getKey();
        RegionRelation regionRelation = this.regionRelationFactory.getRegionRelation(regionKey);
        List metaDatas = regionRelation.getMetaData();
        if (metaDatas == null || metaDatas.size() == 0) {
            return "\u5f53\u524d\u533a\u57df\u65e0\u94fe\u63a5\uff01";
        }
        JtableContext preContext = new JtableContext(jtableContext);
        DimensionValueSet predimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(preContext.getDimensionSet());
        Object dateTimeValue = predimensionValueSet.getValue("DATATIME");
        String prevPeriod = this.getPrevPeriod(dateTimeValue.toString());
        predimensionValueSet.setValue("DATATIME", (Object)prevPeriod);
        preContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet(predimensionValueSet));
        QueryInfoBuilder queryInfoBuilder = DataCrudUtil.getBaseQueryInfoBuilder(preContext, regionKey, regionRelation);
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        List rowDatas = iRegionDataSet.getRowData();
        if (rowDatas == null || rowDatas.size() == 0) {
            return "\u4e0a\u671f\u5c01\u9762\u4ee3\u7801\u8868\u65e0\u6570\u636e\uff01";
        }
        ArrayList<Object> datas = new ArrayList<Object>();
        ArrayList<String> linkKeys = new ArrayList<String>();
        for (IRowData rowData : rowDatas) {
            List rowDataValues = rowData.getLinkDataValues();
            for (IDataValue dataValue : rowDataValues) {
                IMetaData metaData = dataValue.getMetaData();
                if (metaData.isFMDMLink()) continue;
                Object data = DataCrudUtil.getData(dataValue);
                datas.add(data);
                linkKeys.add(metaData.getLinkKey());
            }
        }
        if (datas.size() == 0 || linkKeys.size() == 0) {
            return "\u4e0a\u671f\u5c01\u9762\u4ee3\u7801\u8868\u4e0d\u5b58\u5728\u5355\u4f4d\u4fe1\u606f\u8868\u7684\u6307\u6807\uff01";
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        SaveDataBuilder saveDataBuilder = this.saveDataBuilderFactory.createSaveDataBuilder(regionKey, dimensionCombinationBuilder.getCombination());
        String formulaSchemeKey = jtableContext.getFormulaSchemeKey();
        if (StringUtils.isNotEmpty((String)formulaSchemeKey)) {
            saveDataBuilder.setFormulaSchemeKey(formulaSchemeKey);
        }
        ArrayList<Integer> linkIndexs = new ArrayList<Integer>();
        for (int i = 0; i < linkKeys.size(); ++i) {
            String linkKey = (String)linkKeys.get(i);
            int index = saveDataBuilder.addLink(linkKey);
            linkIndexs.add(index);
        }
        ReturnRes addRowReturnRes = saveDataBuilder.addRow(dimensionCombinationBuilder.getCombination(), 0);
        if (addRowReturnRes.getCode() != 0) {
            String errorMessage = addRowReturnRes.getMessage();
            return errorMessage;
        }
        for (int i = 0; i < datas.size(); ++i) {
            ReturnRes setReturnRes;
            Object data = datas.get(i);
            if (data != null && data.toString().equals("")) {
                data = null;
            }
            if ((setReturnRes = saveDataBuilder.setData(((Integer)linkIndexs.get(i)).intValue(), data)).getCode() == 0) continue;
            String errorMessage = setReturnRes.getMessage();
            return errorMessage;
        }
        ISaveInfo saveInfo = saveDataBuilder.build();
        try {
            SaveReturnRes saveReturnRes = this.dataService.saveRegionData(saveInfo);
            int code = saveReturnRes.getCode();
            if (code != 0) {
                return this.getErrorMessage(saveReturnRes.getSaveResItems());
            }
        }
        catch (CrudSaveException e) {
            return this.getErrorMessage(e.getItems());
        }
        catch (CrudOperateException e) {
            return e.getMessage();
        }
        return "success";
    }

    private String getPrevPeriod(String periodCode) {
        DefaultPeriodAdapter periodAdapter = new DefaultPeriodAdapter();
        PeriodWrapper periodWrapper = new PeriodWrapper(periodCode);
        boolean prevState = periodAdapter.priorPeriod(periodWrapper);
        if (!prevState) {
            return null;
        }
        return periodWrapper.toString();
    }

    private String getErrorMessage(List<SaveResItem> saveResItems) {
        String errorMessage = "\u4e0a\u671f\u5c01\u9762\u4ee3\u7801\u8868\u6570\u636e\u63d0\u53d6\u5931\u8d25\uff01";
        if (saveResItems != null && saveResItems.size() > 0) {
            SaveResItem saveResItem = saveResItems.get(0);
            errorMessage = saveResItem.getMessage();
        }
        return errorMessage;
    }
}

