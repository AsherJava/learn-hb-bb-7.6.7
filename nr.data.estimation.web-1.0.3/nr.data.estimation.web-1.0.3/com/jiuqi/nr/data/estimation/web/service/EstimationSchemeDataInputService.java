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
 *  com.jiuqi.nr.data.estimation.service.dataio.impl.OverRegionDataWithEstimation
 *  com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromDataEntry
 *  com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromSnapshot
 *  com.jiuqi.nr.data.estimation.service.dataio.model.RegionDataIOContext
 *  com.jiuqi.nr.data.estimation.service.enumeration.DataSnapshotPeriodType
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.snapshot.message.DataRange
 *  com.jiuqi.nr.snapshot.message.DataRegionRange
 *  com.jiuqi.nr.snapshot.service.DataSourceBuilder
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
import com.jiuqi.nr.data.estimation.service.dataio.impl.OverRegionDataWithEstimation;
import com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromDataEntry;
import com.jiuqi.nr.data.estimation.service.dataio.impl.ReadRegionDataFromSnapshot;
import com.jiuqi.nr.data.estimation.service.dataio.model.RegionDataIOContext;
import com.jiuqi.nr.data.estimation.service.enumeration.DataSnapshotPeriodType;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.storage.enumeration.EstimationFormType;
import com.jiuqi.nr.data.estimation.web.request.ActionOfCheckFormsParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfCheckSnapShotParam;
import com.jiuqi.nr.data.estimation.web.request.ActionOfImpFormDataParam;
import com.jiuqi.nr.data.estimation.web.request.ActionParameter;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.snapshot.message.DataRange;
import com.jiuqi.nr.snapshot.message.DataRegionRange;
import com.jiuqi.nr.snapshot.service.DataSourceBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimationSchemeDataInputService {
    @Resource
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Resource
    private IEstimationSchemeUserService schemeUserService;
    @Autowired
    private DataSourceBuilder dataSourceBuilder;

    public String restoreFromOriginal(ActionOfCheckFormsParam actionParameter) {
        IEstimationScheme estimationScheme = this.getEstimationScheme(actionParameter);
        FormSchemeDefine formSchemeDefine = estimationScheme.getFormSchemeDefine();
        List estimationForms = estimationScheme.getEstimationForms();
        List formIds = estimationForms.stream().filter(e -> actionParameter.getFormIds().contains(e.getFormDefine().getKey()) && EstimationFormType.inputForm == e.getFormType()).map(e -> e.getFormDefine().getKey()).collect(Collectors.toList());
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(actionParameter.getDimValueSet(), formSchemeDefine.getKey());
        RegionDataIOContext ioContext = new RegionDataIOContext();
        ioContext.setRangeFormKeys(formIds);
        ioContext.setForSchemeKey(formSchemeDefine.getKey());
        ioContext.setDimValueCollection(dimensionCollection);
        FormDataIOLogger logger = new FormDataIOLogger(LoggerFactory.getLogger(EstimationSchemeDataInputService.class));
        ReadRegionDataFromDataEntry reader = new ReadRegionDataFromDataEntry((StringLogger)logger);
        OverRegionDataWithEstimation writer = new OverRegionDataWithEstimation(estimationScheme, (StringLogger)logger);
        DataRegionValueIOExecutor regionDataIOExecutor = new DataRegionValueIOExecutor((IRegionDataIOContext)ioContext, (StringLogger)logger);
        regionDataIOExecutor.executeIO((IDataRegionValueReader)reader, (IDataRegionValueWriter)writer);
        return logger.toString();
    }

    public String restoreFromSnapshot(ActionOfCheckSnapShotParam actionParameter) {
        FormDataIOLogger logger = new FormDataIOLogger(LoggerFactory.getLogger(EstimationSchemeDataInputService.class));
        IEstimationScheme estimationScheme = this.getEstimationScheme(actionParameter.getContext());
        if (estimationScheme != null) {
            List<String> snapshotFormIds = this.getSnapshotFormIds(actionParameter);
            if (!snapshotFormIds.isEmpty()) {
                DataSnapshotPeriodType periodType = DataSnapshotPeriodType.toPeriodType((String)actionParameter.getPeriodCode());
                FormSchemeDefine formSchemeDefine = estimationScheme.getFormSchemeDefine();
                List estimationForms = estimationScheme.getEstimationForms();
                List formIds = estimationForms.stream().filter(e -> snapshotFormIds.contains(e.getFormDefine().getKey()) && EstimationFormType.inputForm == e.getFormType()).map(e -> e.getFormDefine().getKey()).collect(Collectors.toList());
                DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(actionParameter.getContext().getDimensionSet(), formSchemeDefine.getKey());
                RegionDataIOContext ioContext = new RegionDataIOContext();
                ioContext.setRangeFormKeys(formIds);
                ioContext.setForSchemeKey(formSchemeDefine.getKey());
                ioContext.setDimValueCollection(dimensionCollection);
                ReadRegionDataFromSnapshot reader = new ReadRegionDataFromSnapshot((StringLogger)logger, (IRegionDataIOContext)ioContext, this.dataSourceBuilder, periodType, actionParameter.getSnapshotId());
                OverRegionDataWithEstimation writer = new OverRegionDataWithEstimation(estimationScheme, (StringLogger)logger);
                DataRegionValueIOExecutor regionDataIOExecutor = new DataRegionValueIOExecutor((IRegionDataIOContext)ioContext, (StringLogger)logger);
                regionDataIOExecutor.executeIO((IDataRegionValueReader)reader, (IDataRegionValueWriter)writer);
            } else {
                logger.logError("\u6ca1\u6709\u53ef\u8fd8\u539f\u7684\u8868\u5355\uff0c\u5feb\u7167\u8fd8\u539f\u5931\u8d25\uff01\uff01");
            }
        } else {
            logger.logError("\u65e0\u6548\u7684\u6d4b\u7b97\u65b9\u6848\uff0c\u5feb\u7167\u8fd8\u539f\u5931\u8d25\uff01\uff01");
        }
        return logger.toString();
    }

    public String restoreFromExcelImport(ActionOfImpFormDataParam actionParameter) {
        return "";
    }

    private IEstimationScheme getEstimationScheme(ActionParameter actionParameter) {
        String estimationSchemeKey = actionParameter.getEstimationScheme();
        DimensionValueSet dimValueSet = EstimationSchemeUtils.convert2DimValueSet(actionParameter.getDimValueSet());
        return this.schemeUserService.findEstimationScheme(estimationSchemeKey, dimValueSet);
    }

    private IEstimationScheme getEstimationScheme(JtableContext context) {
        Object attrValue;
        if (context != null && context.getVariableMap() != null && context.getVariableMap().containsKey("estimationScheme") && (attrValue = context.getVariableMap().get("estimationScheme")) != null) {
            String estimationSchemeKey = attrValue.toString();
            DimensionValueSet dimValueSet = EstimationSchemeUtils.convert2DimValueSet((Map)context.getDimensionSet());
            return this.schemeUserService.findEstimationScheme(estimationSchemeKey, dimValueSet);
        }
        return null;
    }

    private List<String> getSnapshotFormIds(ActionOfCheckSnapShotParam actionParameter) {
        DataRange dataRange = actionParameter.getDataRange();
        if (dataRange != null && dataRange.getFormAndFields() != null) {
            return dataRange.getFormAndFields().stream().map(DataRegionRange::getFormKey).collect(Collectors.toList());
        }
        return new ArrayList<String>();
    }
}

