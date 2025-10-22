/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils
 *  com.jiuqi.nr.data.estimation.common.utils.FormDataIOLogger
 *  com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService
 *  com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueReader
 *  com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueWriter
 *  com.jiuqi.nr.data.estimation.service.dataio.IRegionDataIOContext
 *  com.jiuqi.nr.data.estimation.service.dataio.impl.DataRegionValueIOExecutor
 *  com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromCommitData
 *  com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromEstimation
 *  com.jiuqi.nr.data.estimation.service.dataio.impl.SaveRegionDataWithDataEntry
 *  com.jiuqi.nr.data.estimation.service.dataio.impl.SaveRegionDataWithEstimation
 *  com.jiuqi.nr.data.estimation.service.dataio.model.RegionDataIOContext
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.web.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.common.utils.EstimationSchemeUtils;
import com.jiuqi.nr.data.estimation.common.utils.FormDataIOLogger;
import com.jiuqi.nr.data.estimation.service.IEstimationSchemeUserService;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueReader;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueWriter;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionDataIOContext;
import com.jiuqi.nr.data.estimation.service.dataio.impl.DataRegionValueIOExecutor;
import com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromCommitData;
import com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromEstimation;
import com.jiuqi.nr.data.estimation.service.dataio.impl.SaveRegionDataWithDataEntry;
import com.jiuqi.nr.data.estimation.service.dataio.impl.SaveRegionDataWithEstimation;
import com.jiuqi.nr.data.estimation.service.dataio.model.RegionDataIOContext;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.web.auth.IEstimationFormAuthChecker;
import com.jiuqi.nr.data.estimation.web.request.ActionOfSaveFormDataParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfWriteFormData;
import com.jiuqi.nr.data.estimation.web.service.EstimationSchemeDataInputService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EstimationSchemeDataOutputService {
    @Resource
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Resource
    private IEstimationSchemeUserService schemeUserService;
    @Resource
    private IEstimationFormAuthChecker estimationFormAuthChecker;

    public String doSaveFormData(ActionOfSaveFormDataParam actionParameter) {
        FormDataIOLogger logger = new FormDataIOLogger(LoggerFactory.getLogger(EstimationSchemeDataInputService.class));
        JtableContext jtableContext = actionParameter.getContext();
        IEstimationScheme estimationScheme = this.getEstimationScheme(jtableContext);
        RegionDataIOContext ioContext = this.createIOContext(estimationScheme, jtableContext);
        ioContext.setRangeFormKeys(Collections.singletonList(jtableContext.getFormKey()));
        ReadRegionDataFromCommitData reader = new ReadRegionDataFromCommitData(actionParameter.getCommitData(), (StringLogger)logger);
        SaveRegionDataWithEstimation writer = new SaveRegionDataWithEstimation(estimationScheme, (StringLogger)logger);
        DataRegionValueIOExecutor regionDataIOExecutor = new DataRegionValueIOExecutor((IRegionDataIOContext)ioContext, (StringLogger)logger);
        regionDataIOExecutor.executeIO((IDataRegionValueReader)reader, (IDataRegionValueWriter)writer);
        return logger.toString();
    }

    public String doWriteFormData(ActionOfWriteFormData actionParameter) {
        FormDataIOLogger logger = new FormDataIOLogger(LoggerFactory.getLogger(EstimationSchemeDataInputService.class));
        JtableContext jtableContext = actionParameter.getContext();
        IEstimationScheme estimationScheme = this.getEstimationScheme(jtableContext);
        RegionDataIOContext ioContext = this.createIOContext(estimationScheme, jtableContext);
        ioContext.setRangeFormKeys(this.estimationFormAuthChecker.getCanWriteInputForms(estimationScheme));
        ReadRegionDataFromEstimation reader = new ReadRegionDataFromEstimation(estimationScheme, (StringLogger)logger);
        SaveRegionDataWithDataEntry writer = new SaveRegionDataWithDataEntry((StringLogger)logger);
        DataRegionValueIOExecutor regionDataIOExecutor = new DataRegionValueIOExecutor((IRegionDataIOContext)ioContext, (StringLogger)logger);
        regionDataIOExecutor.executeIO((IDataRegionValueReader)reader, (IDataRegionValueWriter)writer);
        return logger.toString();
    }

    private IEstimationScheme getEstimationScheme(JtableContext jtableContext) {
        Map dimensionSet = jtableContext.getDimensionSet();
        String estimationSchemeKey = jtableContext.getVariableMap().get("estimationScheme").toString();
        DimensionValueSet dimValueSet = EstimationSchemeUtils.convert2DimValueSet((Map)dimensionSet);
        return this.schemeUserService.findEstimationScheme(estimationSchemeKey, dimValueSet);
    }

    private DimensionCollection getDimensionCollection(JtableContext jtableContext, FormSchemeDefine formSchemeDefine) {
        Map dimensionSet = jtableContext.getDimensionSet();
        return this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, formSchemeDefine.getKey());
    }

    private RegionDataIOContext createIOContext(IEstimationScheme estimationScheme, JtableContext jtableContext) {
        DimensionCollection dimensionCollection = this.getDimensionCollection(jtableContext, estimationScheme.getFormSchemeDefine());
        RegionDataIOContext ioContext = new RegionDataIOContext();
        ioContext.setDimValueCollection(dimensionCollection);
        ioContext.setForSchemeKey(estimationScheme.getFormSchemeDefine().getKey());
        return ioContext;
    }
}

