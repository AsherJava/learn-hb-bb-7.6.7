/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$NoAccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datacrud.ClearInfoBuilder
 *  com.jiuqi.nr.datacrud.IClearInfo
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.api.IDataService
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar
 *  com.jiuqi.nr.datastatus.facade.service.IDataStatusService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.RegionData
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datacrud.ClearInfoBuilder;
import com.jiuqi.nr.datacrud.IClearInfo;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.api.IDataService;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.dataentry.paramInfo.BatchClearInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.dataentry.service.IBatchClearService;
import com.jiuqi.nr.dataentry.service.ICurrencyService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.datastatus.facade.obj.ClearStatusPar;
import com.jiuqi.nr.datastatus.facade.service.IDataStatusService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BatchClearServiceImpl
implements IBatchClearService {
    private static final Logger logger = LoggerFactory.getLogger(BatchClearServiceImpl.class);
    @Autowired
    private IJtableResourceService jtableResourceService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IDataAccessFormService dataAccessFormService;
    @Autowired
    private IDataService dataService;
    @Autowired
    private IDataStatusService dataStatusService;
    @Autowired
    private ICurrencyService currencyService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;

    @Override
    public void batchClearForm(BatchClearInfo batchClearInfo, AsyncTaskMonitor asyncTaskMonitor) {
        BatchReturnInfo batchReturnInfo = new BatchReturnInfo();
        JtableContext jtableContext = batchClearInfo.getContext();
        EntityViewData masterEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        Map dimensionSet = batchClearInfo.getContext().getDimensionSet();
        String formKeyStr = batchClearInfo.getFormKeys();
        List<Object> formKeys = new ArrayList();
        if (StringUtils.hasLength(formKeyStr)) {
            formKeys = Arrays.asList(formKeyStr.split(";"));
        }
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_WRITE);
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setTaskKey(jtableContext.getTaskKey());
        accessFormParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        accessFormParam.setCollectionMasterKey(DimensionValueSetUtil.buildDimensionCollection((Map)dimensionSet, (String)jtableContext.getFormSchemeKey()));
        DimensionAccessFormInfo dimensionAccessFormInfo = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
        List noAccessForms = dimensionAccessFormInfo.getNoAccessForms();
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        for (DimensionAccessFormInfo.NoAccessFormInfo noAccessFormInfo : noAccessForms) {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            String unitKey = ((DimensionValue)noAccessFormInfo.getDimensions().get(targetEntityInfo.getDimensionName())).getValue();
            entityQueryByKeyInfo.setEntityKey(unitKey);
            entityQueryByKeyInfo.setEntityViewKey(masterEntity.getKey());
            entityQueryByKeyInfo.setContext(jtableContext);
            EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            batchReturnInfo.getMessage().add(queryEntityDataByKey.getEntity().getTitle() + "\u5355\u4f4d" + noAccessFormInfo.getReason());
            batchReturnInfo.addError();
        }
        List acessFormInfos = dimensionAccessFormInfo.getAccessForms();
        block7: for (int formInfoIndex = 0; formInfoIndex < acessFormInfos.size(); ++formInfoIndex) {
            if (asyncTaskMonitor.isCancel()) {
                asyncTaskMonitor.canceled("stop_execute", (Object)"");
                LogHelper.info((String)"\u6279\u91cf\u6e05\u9664", (String)"\u53d6\u6d88\u4efb\u52a1\u6267\u884c", (String)"");
                return;
            }
            double formStartProgress = (double)formInfoIndex / (double)acessFormInfos.size();
            double formEndProgress = (double)(formInfoIndex + 1) / (double)acessFormInfos.size();
            DimensionAccessFormInfo.AccessFormInfo accessFormInfo = (DimensionAccessFormInfo.AccessFormInfo)acessFormInfos.get(formInfoIndex);
            Map dimensionValue = accessFormInfo.getDimensions();
            List forms = accessFormInfo.getFormKeys();
            DimensionCollection dimCollection = DimensionValueSetUtil.buildDimensionCollection((Map)dimensionValue, (String)jtableContext.getFormSchemeKey());
            for (int i = 0; i < forms.size(); ++i) {
                if (asyncTaskMonitor.isCancel()) {
                    asyncTaskMonitor.canceled("stop_execute", (Object)"");
                    continue block7;
                }
                String formKey = (String)forms.get(i);
                List regions = this.jtableParamService.getRegions(formKey);
                for (RegionData region : regions) {
                    IClearInfo clearInfo = ClearInfoBuilder.create((String)region.getKey(), (DimensionCollection)dimCollection).build();
                    ReturnRes returnRes = null;
                    try {
                        returnRes = this.dataService.clearRegionData(clearInfo);
                    }
                    catch (CrudOperateException e) {
                        throw new RuntimeException(e);
                    }
                    if (returnRes.getCode() != 0) {
                        batchReturnInfo.addError();
                        batchReturnInfo.getMessage().add(returnRes.getMessage());
                        continue;
                    }
                    batchReturnInfo.addSuccess();
                }
                try {
                    ClearStatusPar clearStatusPar = new ClearStatusPar();
                    clearStatusPar.setDimensionCollection(dimCollection);
                    clearStatusPar.setFormSchemeKey(jtableContext.getFormSchemeKey());
                    clearStatusPar.setFormKey(formKey);
                    this.dataStatusService.clearDataStatusByForm(clearStatusPar);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                double progress = formStartProgress + (double)(i + 1) / (double)forms.size() * (formEndProgress - formStartProgress);
                String clearinfo = "batch_clearing";
                asyncTaskMonitor.progressAndMessage(progress, clearinfo);
            }
        }
        String retStr = "";
        ObjectMapper mapper = new ObjectMapper();
        if (batchReturnInfo.getMessage().size() > 0) {
            batchReturnInfo.setStatus(1);
            try {
                retStr = mapper.writeValueAsString((Object)batchReturnInfo);
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if (asyncTaskMonitor != null) {
            String clearComplete = "clear_success_info";
            if (!asyncTaskMonitor.isFinish()) {
                asyncTaskMonitor.finish(clearComplete, (Object)retStr);
            }
        }
    }
}

