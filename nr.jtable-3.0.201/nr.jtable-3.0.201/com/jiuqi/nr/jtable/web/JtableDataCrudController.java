/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.jtable.web;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.annotation.LevelAuthRead;
import com.jiuqi.nr.jtable.exception.SaveDataException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.IsNewAttachmentInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.CardInputInit;
import com.jiuqi.nr.jtable.params.input.CellValueQueryInfo;
import com.jiuqi.nr.jtable.params.input.FindPageQueryInfo;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.params.input.SingleCellValueQueryInfo;
import com.jiuqi.nr.jtable.params.output.CardInputInfo;
import com.jiuqi.nr.jtable.params.output.CellDataSet;
import com.jiuqi.nr.jtable.params.output.CellValueInfo;
import com.jiuqi.nr.jtable.params.output.MultiPeriodDataSet;
import com.jiuqi.nr.jtable.params.output.RegionDataCount;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.params.output.ReportDataSet;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.service.IExtractFmdmPreDataService;
import com.jiuqi.nr.jtable.service.IJtableDataQueryService;
import com.jiuqi.nr.jtable.service.IJtableDataSaveService;
import com.jiuqi.nr.jtable.service.IJtableFindAndReplaceService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.uniformity.annotation.JUniformity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.Serializable;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v2/jtable"})
@Api(value="/api/v2/jtable", tags={"\u6570\u636e\u5f55\u5165\u8868\u683c\u6570\u636e\u8bfb\u5199"})
public class JtableDataCrudController {
    private static final Logger logger = LoggerFactory.getLogger(JtableDataCrudController.class);
    @Autowired
    private IJtableDataQueryService jtableDataQueryService;
    @Autowired
    private IJtableDataSaveService jtableDataSaveService;
    @Autowired
    private IExtractFmdmPreDataService extractFmdmPreDataService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableResourceService jtableResourceService;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IJtableFindAndReplaceService iJtableFindAndReplaceService;

    @ApiOperation(value="\u67e5\u8be2\u6574\u8868\u6570\u636e")
    @RequestMapping(value={"/actions/query"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public ReportDataSet query(@Valid @RequestBody ReportDataQueryInfo reportDataQueryInfo) {
        return this.jtableDataQueryService.queryReportFormDatas(reportDataQueryInfo);
    }

    @ApiOperation(value="\u67e5\u8be2\u533a\u57df\u6570\u636e")
    @RequestMapping(value={"/actions/region/query"}, method={RequestMethod.POST})
    @NRContextBuild
    @LevelAuthRead
    @ResponseBody
    public RegionDataSet regionQuery(@Valid @RequestBody RegionQueryInfo regionQueryInfo) {
        return this.jtableDataQueryService.queryRegionDatas(regionQueryInfo);
    }

    @ApiOperation(value="\u6279\u91cf\u67e5\u8be2\u533a\u57df\u6570\u636e")
    @RequestMapping(value={"/actions/regions/query"}, method={RequestMethod.POST})
    @NRContextBuild
    @LevelAuthRead
    @ResponseBody
    public ReportDataSet regionsQuery(@Valid @RequestBody ReportDataQueryInfo reportDataQueryInfo) {
        return this.jtableDataQueryService.queryRegionsDatas(reportDataQueryInfo);
    }

    @ApiOperation(value="\u83b7\u53d6\u533a\u57df\u5355\u6761\u5f55\u5165\u6570\u636e")
    @RequestMapping(value={"/actions/region/querysingle"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public RegionSingleDataSet regionQuerySingle(@Valid @RequestBody RegionQueryInfo regionQueryInfo) {
        return this.jtableDataQueryService.querySingleFloatRowData(regionQueryInfo);
    }

    @ApiOperation(value="\u83b7\u53d6\u533a\u57df\u5f55\u5165\u6570\u636e\u603b\u884c\u6570")
    @RequestMapping(value={"/actions/region/datacount"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public RegionDataCount regionDataCount(@Valid @RequestBody RegionQueryInfo regionQueryInfo) {
        return this.jtableDataQueryService.queryRegionDatasCount(regionQueryInfo);
    }

    @ApiOperation(value="\u83b7\u53d6\u6d6e\u52a8\u884c\u7d22\u5f15")
    @RequestMapping(value={"/actions/floatdata/locate"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public PagerInfo floatdataLocate(@Valid @RequestBody RegionQueryInfo regionQueryInfo) {
        return this.jtableDataQueryService.queryFloatRowIndex(regionQueryInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u8868\u683c\u5f80\u671f\u5f55\u5165\u6570\u636e")
    @RequestMapping(value={"/actions/queryMultiPeriodData"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public MultiPeriodDataSet queryMultiPeriodData(@Valid @RequestBody JtableContext jtableContext) {
        MultiPeriodDataSet multiPeriodDataSet = this.jtableDataQueryService.queryMultiPeriodData(jtableContext);
        return multiPeriodDataSet;
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u5355\u5143\u683c\u5220\u9009\u503c\u5217\u8868")
    @RequestMapping(value={"/actions/cellvalues"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public CellDataSet cellvalues(@Valid @RequestBody CellValueQueryInfo cellValueQueryInfo) {
        return this.jtableDataQueryService.queryCellDataSet(cellValueQueryInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u5361\u7247\u5f55\u5165\u533a\u57df\u6620\u5c04\u4fe1\u606f\uff0c\u6839\u636e\u504f\u79fb\u91cf\u83b7\u53d6\u884c\u6570\u636e")
    @RequestMapping(value={"/cardInput"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public CardInputInfo cardInputInit(@Valid @RequestBody CardInputInit cardInputInit) {
        return this.jtableDataQueryService.cardInputInit(cardInputInit);
    }

    @ApiOperation(value="\u4fdd\u5b58\u6574\u8868\u6570\u636e")
    @JLoggable(value="\u4fdd\u5b58\u6574\u8868\u6570\u636e")
    @RequestMapping(value={"/actions/save"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public SaveResult save(@Valid @RequestBody ReportDataCommitSet reportDataCommitSet) {
        SaveResult saveResult = null;
        NpContext npContext = NpContextHolder.getContext();
        String ctxInfoKey = "IS_NEW_ATTACHMENT";
        IsNewAttachmentInfo isNewAttachmentInfo = new IsNewAttachmentInfo();
        isNewAttachmentInfo.setNewAttachment(true);
        npContext.getDefaultExtension().put(ctxInfoKey, (Serializable)isNewAttachmentInfo);
        JtableContext context = reportDataCommitSet.getContext();
        FormData formData = this.jtableParamService.getReport(context.getFormKey(), context.getFormSchemeKey());
        try {
            saveResult = FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType()) ? this.jtableDataSaveService.saveFMDMDatas(reportDataCommitSet) : this.jtableDataSaveService.saveReportFormDatas(reportDataCommitSet);
        }
        catch (SaveDataException e) {
            saveResult = e.getSaveResult();
        }
        if ("success".equals(saveResult.getMessage())) {
            String saveCheckMessage;
            String saveCalculateMessage = this.taskOptionController.getValue(context.getTaskKey(), "AUTOCALCULAT_AFTER_SAVE");
            if ("1".equals(saveCalculateMessage)) {
                saveResult.setCalculateReturnInfo(this.jtableResourceService.calculateFormBetween(context));
            }
            if ("1".equals(saveCheckMessage = this.taskOptionController.getValue(context.getTaskKey(), "AUTOCHECK_AFTER_SAVE"))) {
                saveResult.setCheckReturnInfo(this.jtableResourceService.checkForm(context));
            }
        }
        return saveResult;
    }

    @ApiOperation(value="\u65b0\u589e\u5c01\u9762\u4ee3\u7801")
    @JLoggable(value="\u65b0\u589e\u5c01\u9762\u4ee3\u7801")
    @RequestMapping(value={"/addFMDM"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public SaveResult addFdmd(@Valid @RequestBody ReportDataCommitSet reportDataCommitSet) {
        SaveResult saveResult = null;
        NpContext npContext = NpContextHolder.getContext();
        String ctxInfoKey = "IS_NEW_ATTACHMENT";
        IsNewAttachmentInfo isNewAttachmentInfo = new IsNewAttachmentInfo();
        isNewAttachmentInfo.setNewAttachment(true);
        npContext.getDefaultExtension().put(ctxInfoKey, (Serializable)isNewAttachmentInfo);
        JtableContext context = reportDataCommitSet.getContext();
        try {
            saveResult = this.jtableDataSaveService.saveFMDMDatas(reportDataCommitSet);
        }
        catch (SaveDataException e) {
            saveResult = e.getSaveResult();
        }
        if ("success".equals(saveResult.getMessage())) {
            String saveCalculateMessage;
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
            DimensionValue dwDimensionValue = dimensionSet.get(dwEntity.getDimensionName());
            String unitCode = saveResult.getUnitCode();
            if (unitCode != "" && unitCode != null) {
                dwDimensionValue.setValue(unitCode);
            }
            if ("1".equals(saveCalculateMessage = this.taskOptionController.getValue(context.getTaskKey(), "AUTOCALCULAT_AFTER_SAVE"))) {
                saveResult.setCalculateReturnInfo(this.jtableResourceService.calculateFormBetween(context));
            }
        }
        return saveResult;
    }

    @ApiOperation(value="\u4fdd\u5b58\u533a\u57df\u6570\u636e")
    @JLoggable(value="\u4fdd\u5b58\u533a\u57df\u6570\u636e")
    @RequestMapping(value={"/actions/region/save"}, method={RequestMethod.POST})
    @NRContextBuild
    @JUniformity
    @ResponseBody
    public SaveResult regionSave(@Valid @RequestBody RegionDataCommitSet regionDataCommitSet) {
        SaveResult saveResult = null;
        try {
            saveResult = this.jtableDataSaveService.saveRegionDatas(regionDataCommitSet);
        }
        catch (SaveDataException e) {
            saveResult = e.getSaveResult();
        }
        return saveResult;
    }

    @ApiOperation(value="\u6e05\u9664\u6574\u8868\u6570\u636e")
    @JLoggable(value="\u6e05\u9664\u6574\u8868\u6570\u636e")
    @RequestMapping(value={"/actions/clear"}, method={RequestMethod.POST})
    @NRContextBuild
    @JUniformity
    @ResponseBody
    public ReturnInfo clear(@Valid @RequestBody ReportDataQueryInfo reportDataQueryInfo) {
        String saveCalculateMessage;
        ReturnInfo returnInfo;
        try {
            returnInfo = this.jtableDataSaveService.clearReportFormDatas(reportDataQueryInfo);
        }
        catch (CrudException | CrudOperateException e) {
            returnInfo = new ReturnInfo();
            logger.error(e.getMessage());
            returnInfo.setMessage("\u6e05\u9664\u6574\u8868\u6570\u636e\u5931\u8d25");
        }
        if ("success".equals(returnInfo.getMessage()) && "1".equals(saveCalculateMessage = this.taskOptionController.getValue(reportDataQueryInfo.getContext().getTaskKey(), "AUTOCALCULAT_AFTER_CLEAR"))) {
            returnInfo.setMessage(this.jtableResourceService.calculateFormBetween(reportDataQueryInfo.getContext()).getMessage());
        }
        return returnInfo;
    }

    @ApiOperation(value="\u5220\u9664\u5c01\u9762\u4ee3\u7801")
    @JLoggable(value="\u5220\u9664\u5c01\u9762\u4ee3\u7801")
    @PostMapping(value={"/deleteFMDM"})
    @NRContextBuild
    public SaveResult deleteFMDM(@Valid @RequestBody JtableContext context) {
        return this.jtableDataSaveService.deleteFmdm(context);
    }

    @ApiOperation(value="\u63d0\u53d6\u5c01\u9762\u4ee3\u7801\u4e0a\u671f\u6570\u636e")
    @PostMapping(value={"/extractFmdmPreData"})
    @NRContextBuild
    public String extractFmdmPreData(@Valid @RequestBody JtableContext context) {
        return this.extractFmdmPreDataService.extractFmdmPreData(context);
    }

    @ApiOperation(value="\u67e5\u8be2\u5355\u4e2a\u5355\u5143\u683c\u6570\u636e")
    @PostMapping(value={"/action/query/singleCell"})
    @NRContextBuild
    public CellValueInfo querySingleCellValue(@Valid @RequestBody SingleCellValueQueryInfo cellValueQueryInfo) {
        return this.jtableDataQueryService.querySingleCellValue(cellValueQueryInfo);
    }

    @ApiOperation(value="\u5206\u9875\u67e5\u627e")
    @RequestMapping(value={"/actions/findPage"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public Map<String, Object> findPage(@Valid @RequestBody FindPageQueryInfo findPageQueryInfo) {
        return this.iJtableFindAndReplaceService.findPage(findPageQueryInfo);
    }

    @ApiOperation(value="\u5168\u90e8\u66ff\u6362")
    @RequestMapping(value={"/actions/replaceAll"}, method={RequestMethod.POST})
    @NRContextBuild
    @ResponseBody
    public Map<String, Object> replaceAll(@Valid @RequestBody FindPageQueryInfo findPageQueryInfo) {
        return this.iJtableFindAndReplaceService.replaceAll(findPageQueryInfo);
    }
}

