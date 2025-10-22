/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.common.StringLogger
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.estimation.service.dataio.impl;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.common.StringLogger;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfo;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionInfoBuilder;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionTableData;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueIOExecutor;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueReader;
import com.jiuqi.nr.data.estimation.service.dataio.IDataRegionValueWriter;
import com.jiuqi.nr.data.estimation.service.dataio.IRegionDataIOContext;
import com.jiuqi.nr.data.estimation.service.dataio.ITableCellLinkColumn;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataRegionValueIOExecutor
implements IDataRegionValueIOExecutor {
    protected StringLogger logger;
    protected IRegionDataIOContext ioContext;
    protected IDataRegionInfoBuilder regionInfoBuilder;
    private IRunTimeViewController nrTaskRuntimeService;

    public DataRegionValueIOExecutor(IRegionDataIOContext ioContext) {
        this(ioContext, new StringLogger());
    }

    public DataRegionValueIOExecutor(IRegionDataIOContext ioContext, StringLogger logger) {
        this.logger = logger;
        this.ioContext = ioContext;
        this.regionInfoBuilder = (IDataRegionInfoBuilder)SpringBeanUtils.getBean(IDataRegionInfoBuilder.class);
        this.nrTaskRuntimeService = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    }

    @Override
    public void executeIO(IDataRegionValueReader regionReader, IDataRegionValueWriter regionWriter) {
        double process = 1.0;
        List<String> formKeys = this.ioContext.getRangeFormKeys();
        DimensionCollection dimValueCollection = this.ioContext.getDimValueCollection();
        if (dimValueCollection == null) {
            this.logger.logError("\u65e0\u6548\u7684\u7ef4\u5ea6\u96c6\u5408\uff1anull", 1.0);
            return;
        }
        if (formKeys == null || formKeys.isEmpty()) {
            this.logger.logError("\u6ca1\u6709\u53ef\u4ee5\u8bfb\u5199\u7684\u62a5\u8868\uff1a", 1.0);
            return;
        }
        this.logger.logInfo(">>>>>>>>>>>> \u5f00\u59cb\u8bfb\u5199\u6570\u636e <<<<<<<<<<<<");
        List dimensionCombinations = dimValueCollection.getDimensionCombinations();
        double dimProcess = process / (double)dimensionCombinations.size();
        for (DimensionCombination combination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = combination.toDimensionValueSet();
            this.logDimInfo(dimensionValueSet);
            int successCount = 0;
            DimensionValueSet pubColumnValueSet = this.regionInfoBuilder.buildPubColumnValueSet(this.ioContext.getForSchemeKey(), dimensionValueSet);
            for (String formKey : formKeys) {
                successCount += this.executeIO(regionReader, regionWriter, combination, pubColumnValueSet, formKey, dimProcess / (double)formKeys.size());
            }
            this.logger.logInfo("\u9700\u8981\u5199\u5165[" + formKeys.size() + "]\u5f20\u62a5\u8868\u7684\u6570\u636e");
            this.logger.logInfo("\u5b8c\u6210\u5199\u5165[" + successCount + "]\u5f20\u62a5\u8868\u7684\u6570\u636e");
            this.logger.logInfo("\u5199\u5165\u5931\u8d25[" + (formKeys.size() - successCount) + "]\u5f20\u62a5\u8868\u7684\u6570\u636e");
            this.logger.logInfo("------------------------------------");
        }
        this.logger.logInfo(">>>>>>>>>>>> \u8bfb\u5199\u6570\u636e\u5b8c\u6210 <<<<<<<<<<<<");
    }

    public int executeIO(IDataRegionValueReader regionReader, IDataRegionValueWriter regionWriter, DimensionCombination combination, DimensionValueSet pubColumnValueSet, String formKey, double process) {
        FormDefine formDefine = this.nrTaskRuntimeService.queryFormById(formKey);
        if (formDefine == null) {
            this.logger.logError("\u8be5\u8868\u5355ID[" + formKey + "]\u65e0\u6cd5\u67e5\u5230\u8fd0\u884c\u671f\u8868\u5355\u5b9a\u4e49\uff01", process);
            return 0;
        }
        this.logger.logInfo("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        this.logger.logInfo("\u6b63\u5728\u5199\u5165\u62a5\u8868\u6570\u636e...");
        this.logger.logInfo("\u62a5\u8868\u540d\u79f0\uff1a[" + formDefine.getTitle() + "]");
        this.logger.logInfo("\u62a5\u8868\u6807\u8bc6\uff1a[" + formDefine.getFormCode() + "]");
        List regionsInForm = this.nrTaskRuntimeService.getAllRegionsInForm(formKey);
        for (int idx = 0; idx < regionsInForm.size(); ++idx) {
            IDataRegionInfo dataRegionInfo = this.regionInfoBuilder.buildDataRegionInfo((DataRegionDefine)regionsInForm.get(idx));
            this.logger.logInfo("************************************");
            this.logger.logInfo("\u6b63\u5728\u5199\u5165\u7b2c[" + (idx + 1) + "]\u4e2a\u6570\u636e\u533a\u57df\u7684\u6570\u636e");
            this.logger.logInfo("\u6570\u636e\u533a\u57df\uff1a\u3010" + dataRegionInfo.getDataRegion().getTitle() + "\u3011");
            List<ITableCellLinkColumn> cellLinksColumns = dataRegionInfo.getCellLinksColumns();
            if (cellLinksColumns == null || cellLinksColumns.isEmpty()) {
                this.logger.logInfo("\u533a\u57df\u4e0b\u6ca1\u6709\u6570\u636e\u94fe\u63a5\uff0c\u8df3\u8fc7\u6570\u636e\u8bfb\u5199!!!");
                continue;
            }
            try {
                IDataRegionTableData regionTableData = regionReader.readDataRegionValue(dataRegionInfo, combination, pubColumnValueSet);
                regionWriter.writeDataRegionValue(dataRegionInfo, combination, regionTableData);
                this.logger.addProcess(process / (double)regionsInForm.size());
                continue;
            }
            catch (Exception e) {
                this.logger.logInfo(">>>>>>>>>>>> \u7a0b\u5e8f\u6267\u884c\u5f02\u5e38 <<<<<<<<<<<<");
                this.logger.logError("\u5f02\u5e38\u4fe1\u606f\uff1a" + e.getLocalizedMessage(), process);
                this.logException(e);
                return 0;
            }
        }
        return 1;
    }

    private void logDimInfo(DimensionValueSet dimensionValueSet) {
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            this.logger.logInfo("[" + dimensionValueSet.getName(i) + "]\uff1a" + dimensionValueSet.getValue(i));
        }
    }

    private void logException(Exception e) {
        List stackTrace = Arrays.stream(e.getStackTrace()).collect(Collectors.toList());
        List estimation = stackTrace.stream().filter(ste -> ste.getClassName().contains("estimation")).collect(Collectors.toList());
        if (estimation.stream().findFirst().isPresent()) {
            int lastIndexOfEstimation = stackTrace.lastIndexOf(estimation.get(estimation.size() - 1));
            for (int i = 0; i <= lastIndexOfEstimation; ++i) {
                this.logger.logError(((StackTraceElement)stackTrace.get(i)).toString());
            }
        } else {
            for (StackTraceElement element : stackTrace) {
                this.logger.logError(element.toString());
            }
        }
    }
}

