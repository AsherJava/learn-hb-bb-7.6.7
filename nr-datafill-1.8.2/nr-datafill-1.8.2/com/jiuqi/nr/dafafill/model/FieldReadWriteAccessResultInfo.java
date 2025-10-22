/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.constant.ErrorCode
 *  com.jiuqi.nr.common.importdata.ResultErrorInfo
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchZBAccessResult
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.dafafill.model;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.constant.ErrorCode;
import com.jiuqi.nr.common.importdata.ResultErrorInfo;
import com.jiuqi.nr.dafafill.model.DFDimensionValue;
import com.jiuqi.nr.dafafill.model.DFDimensionValueGetService;
import com.jiuqi.nr.dafafill.model.DataFieldWriteAccessResultInfo;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillDimensionTitle;
import com.jiuqi.nr.dafafill.model.DataFillSaveErrorDataInfo;
import com.jiuqi.nr.dafafill.model.QueryField;
import com.jiuqi.nr.dafafill.model.enums.FieldType;
import com.jiuqi.nr.dafafill.service.IDFDimensionQueryFieldParser;
import com.jiuqi.nr.dafafill.util.NrDataFillI18nUtil;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FieldReadWriteAccessResultInfo {
    private static final Logger logger = LoggerFactory.getLogger(FieldReadWriteAccessResultInfo.class);
    private IBatchZBAccessResult batchResult;
    private IBatchAccessResult accessResult;
    private String formKey;
    private Map<String, Boolean> entityResult;
    private Map<String, FieldReadWriteAccessResultInfo> fmdmResult;
    private String periodName;
    private String dwName;
    private IDFDimensionQueryFieldParser dimensionQueryFieldParser;
    private DFDimensionValueGetService dfDimensionValueGetService = (DFDimensionValueGetService)SpringBeanUtils.getBean(DFDimensionValueGetService.class);

    public FieldReadWriteAccessResultInfo(IBatchZBAccessResult result) {
        this.batchResult = result;
    }

    public FieldReadWriteAccessResultInfo(IBatchAccessResult accessResult, String formKey) {
        this.accessResult = accessResult;
        this.formKey = formKey;
    }

    public FieldReadWriteAccessResultInfo(String periodName, Map<String, FieldReadWriteAccessResultInfo> fmdmResult) {
        this.periodName = periodName;
        this.fmdmResult = fmdmResult;
    }

    public FieldReadWriteAccessResultInfo() {
    }

    public FieldReadWriteAccessResultInfo(Map<String, Boolean> entityResult, String dwName) {
        this.entityResult = entityResult;
        this.dwName = dwName;
    }

    public DataFieldWriteAccessResultInfo getAccess(DimensionValueSet dimensionValueSet, String filedKey, DataFillContext context) {
        Object iAccessResult = null;
        try {
            Object value;
            if (null != this.fmdmResult) {
                Object value2 = dimensionValueSet.getValue(this.periodName);
                return this.fmdmResult.get(value2.toString()).getAccess(dimensionValueSet, filedKey, context);
            }
            if (null != this.batchResult) {
                iAccessResult = this.batchResult.getAccess(this.setDimensionSetIntoCombination(dimensionValueSet), filedKey);
            } else if (null != this.accessResult) {
                iAccessResult = this.accessResult.getAccess(this.setDimensionSetIntoCombination(dimensionValueSet), this.formKey);
            } else if (null != this.entityResult && null != (value = dimensionValueSet.getValue(this.dwName))) {
                final Boolean access = this.entityResult.get(value);
                iAccessResult = new IAccessResult(){

                    public boolean haveAccess() throws Exception {
                        return null != access && access != false;
                    }

                    public String getMessage() throws Exception {
                        return "\u5f53\u524d\u7528\u6237\u65e0\u6743\u9650";
                    }
                };
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u6743\u9650\u62a5\u9519\uff01", e);
        }
        if (null == iAccessResult) {
            iAccessResult = new IAccessResult(){

                public boolean haveAccess() throws Exception {
                    return false;
                }

                public String getMessage() throws Exception {
                    return "\u672a\u77e5\u5f02\u5e38\uff01";
                }
            };
        }
        return new DataFieldWriteAccessResultInfo((IAccessResult)iAccessResult);
    }

    public DataFillSaveErrorDataInfo getMessage(DimensionValueSet dimensionValueSet, String filedKey, QueryField queryField, DataFillContext context, List<DFDimensionValue> list, DataFieldWriteAccessResultInfo access) {
        DataFillSaveErrorDataInfo dataFillSaveErrorDataInfo = new DataFillSaveErrorDataInfo();
        ResultErrorInfo dataError = new ResultErrorInfo();
        dataFillSaveErrorDataInfo.setDataError(dataError);
        dataError.setErrorCode(ErrorCode.DATAERROR);
        if (queryField != null) {
            dataFillSaveErrorDataInfo.setZb(queryField.getId());
        }
        dataFillSaveErrorDataInfo.setDimensionValues(list);
        String zbTitle = null != queryField ? (StringUtils.hasLength(queryField.getAlias()) ? queryField.getAlias() : queryField.getTitle()) : "";
        String errorMessage = NrDataFillI18nUtil.buildCode("nr.dataquery.Field") + ":" + zbTitle + " ;" + NrDataFillI18nUtil.buildCode("nr.dataFill.cantModify") + "\uff01";
        try {
            if (null != this.batchResult || null != this.accessResult || null != this.entityResult) {
                String message = "";
                if (null != access) {
                    message = access.getMessage();
                } else if (null != this.batchResult) {
                    IAccessResult iAccessResult = this.batchResult.getAccess(this.setDimensionSetIntoCombination(dimensionValueSet), filedKey);
                    message = iAccessResult.getMessage();
                } else if (null != this.accessResult) {
                    Map<String, String> extendedData = context.getModel().getExtendedData();
                    String formKey = extendedData.get("FORMKEY");
                    IAccessResult iAccessResult = this.accessResult.getAccess(this.setDimensionSetIntoCombination(dimensionValueSet), formKey);
                    message = iAccessResult.getMessage();
                } else if (null != this.entityResult) {
                    message = "\u5f53\u524d\u7528\u6237\u65e0\u6743\u9650";
                }
                StringBuilder builder = new StringBuilder();
                if (queryField == null) {
                    builder.append(message);
                    dataError.setErrorInfo(builder.toString());
                    return dataFillSaveErrorDataInfo;
                }
                if (null == this.dimensionQueryFieldParser) {
                    this.dimensionQueryFieldParser = (IDFDimensionQueryFieldParser)SpringBeanUtils.getBean(IDFDimensionQueryFieldParser.class);
                }
                Map<FieldType, List<QueryField>> fieldTypeQueryFields = this.dimensionQueryFieldParser.getFieldTypeQueryFields(context);
                QueryField periodField = fieldTypeQueryFields.get((Object)FieldType.PERIOD).get(0);
                QueryField masterField = fieldTypeQueryFields.get((Object)FieldType.MASTER).get(0);
                Optional<DFDimensionValue> findAny = list.stream().filter(e -> e.getName().equals(periodField.getSimplifyFullCode())).findAny();
                if (findAny.isPresent()) {
                    DFDimensionValue dfDimensionValue = findAny.get();
                    String values = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
                    ArrayList<String> periodValues = new ArrayList<String>();
                    periodValues.add(values);
                    Map<String, DataFillDimensionTitle> periodTitle = this.dimensionQueryFieldParser.getDimensionTitle(periodField, periodValues, periodValues, context.getModel());
                    builder.append(NrDataFillI18nUtil.buildCode("nr.dataentry.period") + "\uff1a").append(periodTitle.get(values).getTitle()).append(" ");
                    findAny = list.stream().filter(e -> e.getName().equals(masterField.getSimplifyFullCode())).findAny();
                    if (findAny.isPresent()) {
                        dfDimensionValue = findAny.get();
                        values = this.dfDimensionValueGetService.getValues(dfDimensionValue, context.getModel());
                        ArrayList<String> masterValues = new ArrayList<String>();
                        masterValues.add(values);
                        Map<String, DataFillDimensionTitle> masterTitle = this.dimensionQueryFieldParser.getDwDimensionTitle(masterField, masterValues, periodValues, context.getModel());
                        DataFillDimensionTitle dataFillDimensionTitle = masterTitle.get((String)periodValues.get(0) + ";" + values);
                        if (null != dataFillDimensionTitle) {
                            builder.append(NrDataFillI18nUtil.buildCode("nr.dataFill.mainDimension") + "\uff1a").append(dataFillDimensionTitle.getTitle()).append(" ");
                        } else {
                            builder.append(NrDataFillI18nUtil.buildCode("nr.dataFill.mainDimension") + "\uff1a").append(values).append(" ");
                        }
                    }
                }
                builder.append(errorMessage + message);
                dataError.setErrorInfo(builder.toString());
            } else {
                dataError.setErrorCode(ErrorCode.SYSTEMERROR);
                dataError.setErrorInfo(errorMessage + NrDataFillI18nUtil.buildCode("nr.dataFill.errorQueryAccess"));
            }
        }
        catch (Exception e2) {
            logger.error("\u67e5\u8be2\u53ef\u5199\u6743\u9650\u62a5\u9519\uff01", e2);
            dataError.setErrorCode(ErrorCode.SYSTEMERROR);
            dataError.setErrorInfo(errorMessage + NrDataFillI18nUtil.buildCode("nr.dataFill.errorQueryAccess"));
        }
        return dataFillSaveErrorDataInfo;
    }

    private DimensionCombination setDimensionSetIntoCombination(DimensionValueSet dimensionValueSet) {
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            builder.setValue(dimensionValueSet.getName(i), dimensionValueSet.getValue(i));
        }
        return builder.getCombination();
    }
}

