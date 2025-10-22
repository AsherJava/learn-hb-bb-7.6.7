/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.Base64
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.efdc.extract.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.Base64;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.efdc.exception.EFDCException;
import com.jiuqi.nr.efdc.extract.ExtractDataRegion;
import com.jiuqi.nr.efdc.extract.ExtractDataRow;
import com.jiuqi.nr.efdc.extract.IExtractResult;
import com.jiuqi.nr.efdc.extract.IRegionParser;
import com.jiuqi.nr.efdc.extract.exception.ExtractException;
import com.jiuqi.nr.efdc.extract.impl.AbstractExtractRequest;
import com.jiuqi.nr.efdc.extract.impl.EFDCExtractResultImpl;
import com.jiuqi.nr.efdc.extract.impl.EFDCRegionParser;
import com.jiuqi.nr.efdc.extract.impl.request.DataFetchEnv;
import com.jiuqi.nr.efdc.extract.impl.request.DataFetchRequester;
import com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing;
import com.jiuqi.nr.efdc.extract.impl.request.FixExpression;
import com.jiuqi.nr.efdc.extract.impl.request.FloatExpression;
import com.jiuqi.nr.efdc.extract.impl.request.ReportSoftInfo;
import com.jiuqi.nr.efdc.extract.impl.response.DataFetchResponser;
import com.jiuqi.nr.efdc.extract.impl.response.FixExpResult;
import com.jiuqi.nr.efdc.extract.impl.response.FloatExpResult;
import com.jiuqi.nr.efdc.extract.impl.response.ResultListing;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

public class EFDCExtractRequestImpl
extends AbstractExtractRequest {
    private static final int PRECISION = 2;
    private static final String SOFTWARE_NAME = "NR_EFDC\u53d6\u6570";
    private static final String SOFTWARE_VERSION = "NR_EFDC1.0";
    private static final String DATAFETCH_SERVICE = "DataFetchService?actionType=DATAFETCH&encodedType=BASE64";
    private static final String HYPERTEXT_TRANSFER_PROTOCOL_SECURE = "https";
    private DataFetchRequester dfRequester = null;
    private Map<String, Object> resultMap = new HashMap<String, Object>();
    private int resultSize = 0;
    private String penetrableSearchURL = "";
    private String[] times = new String[2];
    private boolean requestIsEmpty = true;
    private INvwaSystemOptionService iNvwaSystemOptionService;

    public EFDCExtractRequestImpl(FormulaSchemeDefine formulaScheme, FormDefine form) {
        super(formulaScheme, form);
    }

    public EFDCExtractRequestImpl(List<String> formulas, String formSchemeKey, FormDefine form) {
        super(formulas, formSchemeKey, form);
    }

    @Override
    public List<ExtractDataRegion> doPrepare(ExecutorContext executorContext, Map<String, Object> paras, IFormulaRunTimeController formulaRunTimeController) throws ExtractException {
        super.doPrepare(executorContext, paras, formulaRunTimeController);
        if (paras != null) {
            this.initFetchEnv(paras);
            this.initReportSoft(paras);
        }
        return this.regions;
    }

    private void initFetchEnv(Map<String, Object> paras) {
        String endAdjustPeriod;
        String startAdjustPeriod;
        this.dfRequester = new DataFetchRequester();
        DataFetchEnv env = new DataFetchEnv();
        this.initUrl(paras);
        env.setUnitCode((String)paras.get("UnitCode"));
        env.setPeriodScheme((String)paras.get("DATATIME"));
        Boolean includUncharged = (Boolean)paras.get("includUncharged");
        if (includUncharged != null) {
            env.setIncludUncharged(includUncharged);
        }
        if ((startAdjustPeriod = (String)paras.get("startAdjustPeriod")) != null) {
            env.setStartAdjustPeriod(startAdjustPeriod);
        }
        if ((endAdjustPeriod = (String)paras.get("endAdjustPeriod")) != null) {
            env.setEndAdjustPeriod(endAdjustPeriod);
        }
        env.setStopOnSyntaxErr(true);
        env.setOtherEntity((Map)paras.get("otherEntity"));
        env.setTaskID((String)paras.get("taskID"));
        env.setInstance(null);
        env.setBblx((String)paras.get("bblx"));
        env.setIncludeAdjustPeriod((Boolean)paras.get("includeAdjustPeriod"));
        this.dfRequester.setFetchEnv(env);
    }

    public void initUrl(Map<String, Object> paras) throws EFDCException {
        this.penetrableSearchURL = (String)paras.get("EFDCAddress");
        if (StringUtils.isEmpty((String)this.penetrableSearchURL)) {
            throw new EFDCException("EFDC\u53d6\u6570\u5730\u5740\u4e3a\u7a7a\uff01");
        }
        this.penetrableSearchURL = this.penetrableSearchURL.trim();
        if (this.penetrableSearchURL.charAt(this.penetrableSearchURL.length() - 1) != '/') {
            this.penetrableSearchURL = this.penetrableSearchURL + "/";
        }
        this.penetrableSearchURL = this.penetrableSearchURL + DATAFETCH_SERVICE;
    }

    private void setStartEndTime(ExecutorContext executorContext, String period) throws ParseException {
        PeriodWrapper periodWrapper = new PeriodWrapper(period);
        DataFetchEnv env = this.dfRequester.getFetchEnv();
        this.times = executorContext.getPeriodAdapter().getPeriodDateStrRegion(periodWrapper);
        if (this.times != null && this.times.length == 2) {
            env.setStartTime(this.times[0]);
            env.setEndTime(this.times[1]);
        }
    }

    private String fomartTime(String time) {
        String year = time.substring(0, 4);
        String mouth = time.substring(4, 6);
        String day = time.substring(6, 8);
        return year + "-" + mouth + "-" + day;
    }

    private void initReportSoft(Map<String, Object> paras) {
        ReportSoftInfo rsInfo = new ReportSoftInfo();
        rsInfo.setSoftWare(SOFTWARE_NAME);
        rsInfo.setSoftVersion(SOFTWARE_VERSION);
        rsInfo.setReportsName(this.form.getTitle());
        rsInfo.setIpAddress((String)paras.get("userIp"));
        rsInfo.setComputerName((String)paras.get("userIp"));
        this.dfRequester.setReportSoft(rsInfo);
    }

    private void fillFormulaGroup(ExtractDataRegion region) {
        ExpressionListing cwFmlListing = new ExpressionListing();
        ArrayList<FixExpression> fixFmls = new ArrayList<FixExpression>();
        ArrayList<FloatExpression> floatFmls = new ArrayList<FloatExpression>();
        if (region.isFloat()) {
            for (int row = 0; row < 1; ++row) {
                ArrayList<FixExpression> colCwFmls = new ArrayList<FixExpression>();
                FloatExpression floatFml = new FloatExpression();
                floatFml.setFlag(row + "");
                floatFml.setName(this.form.getTitle());
                floatFml.setPrecision(2);
                floatFml.setExpression(region.getFloatExpression());
                for (int i = 0; i < region.getColmumCount(); ++i) {
                    FixExpression fml = new FixExpression();
                    DataLinkColumn column = region.getColmum(i);
                    fml.setFlag(String.valueOf(i));
                    fml.setName(column.toString() + "(\u6307\u6807\u4ee3\u7801\uff1a" + column.getField().getCode() + ")");
                    fml.setPrecision(2);
                    fml.setExpression(region.getColmumExpression(i));
                    colCwFmls.add(fml);
                }
                floatFml.setColExpressions(colCwFmls);
                floatFmls.add(floatFml);
            }
            this.requestIsEmpty = floatFmls.size() == 0;
        } else {
            ArrayList<FixExpression> colCwFmls = new ArrayList<FixExpression>();
            for (int row = 0; row < 1; ++row) {
                for (int i = 0; i < region.getColmumCount(); ++i) {
                    FixExpression fml = new FixExpression();
                    DataLinkColumn column = region.getColmum(i);
                    fml.setFlag(String.valueOf(i));
                    fml.setName(column.toString() + "(\u6307\u6807\u4ee3\u7801\uff1a" + column.getField().getCode() + ")");
                    fml.setPrecision(2);
                    fml.setExpression(region.getColmumExpression(i));
                    colCwFmls.add(fml);
                }
            }
            fixFmls.addAll(colCwFmls);
            this.requestIsEmpty = fixFmls.size() == 0;
        }
        cwFmlListing.setFixExpressions(fixFmls);
        cwFmlListing.setFloatExpressions(floatFmls);
        this.dfRequester.setExpListing(cwFmlListing);
    }

    private void doExcute() throws ExtractException {
        block38: {
            try {
                Object paraJsonStrBytes;
                if (this.requestIsEmpty) {
                    return;
                }
                this.logger.debug("EFDC\u53d6\u6570\u5730\u5740\uff1a" + this.penetrableSearchURL);
                this.iNvwaSystemOptionService = (INvwaSystemOptionService)BeanUtil.getBean(INvwaSystemOptionService.class);
                String messageType = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDC_PENETRAT_TYPE");
                URL urlc = new URL(this.penetrableSearchURL);
                String resultGsonStr = null;
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                String paraJsonStr = mapper.writeValueAsString((Object)this.dfRequester);
                this.logger.debug("EFDC\u53d6\u6570\u8bf7\u6c42\uff1a" + paraJsonStr);
                HttpURLConnection con = -1 != this.penetrableSearchURL.indexOf(HYPERTEXT_TRANSFER_PROTOCOL_SECURE) ? (HttpsURLConnection)urlc.openConnection() : (HttpURLConnection)urlc.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Cache-Control", "no-cache");
                con.setRequestProperty("Content-Type", "text/xml");
                try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");){
                    if ("0".equals(messageType)) {
                        paraJsonStrBytes = paraJsonStr.getBytes("UTF-8");
                        out.write(Base64.byteArrayToBase64((byte[])paraJsonStrBytes));
                    } else {
                        out.write(paraJsonStr);
                    }
                }
                String resultMessage = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                paraJsonStrBytes = null;
                try {
                    String resultBase64 = reader.readLine();
                    if ("0".equals(messageType)) {
                        if (resultBase64 != null) {
                            resultGsonStr = new String(Base64.base64ToByteArray((String)resultBase64), "UTF-8");
                        }
                        resultMessage = resultGsonStr;
                    } else {
                        resultMessage = resultBase64;
                    }
                }
                catch (Throwable resultBase64) {
                    paraJsonStrBytes = resultBase64;
                    throw resultBase64;
                }
                finally {
                    if (reader != null) {
                        if (paraJsonStrBytes != null) {
                            try {
                                reader.close();
                            }
                            catch (Throwable resultBase64) {
                                ((Throwable)paraJsonStrBytes).addSuppressed(resultBase64);
                            }
                        } else {
                            reader.close();
                        }
                    }
                }
                con.getInputStream().close();
                this.logger.debug("EFDC\u53d6\u6570\u7ed3\u679c\uff1a" + resultMessage);
                DataFetchResponser dfResult = (DataFetchResponser)mapper.readValue(resultMessage, DataFetchResponser.class);
                ResultListing rl = dfResult.getResultListing();
                if (rl.isSuccess()) {
                    List<FixExpResult> fixResult = rl.getFixExpResults();
                    List<FloatExpResult> floatResult = rl.getFloatExpResults();
                    this.fillFixedReport(fixResult);
                    if (floatResult != null) {
                        for (int i = 0; i < floatResult.size(); ++i) {
                            FloatExpResult oneFloat = floatResult.get(i);
                            this.fillFloatReprot(oneFloat, i);
                        }
                    }
                    break block38;
                }
                throw new ExtractException(rl.getErrMsg());
            }
            catch (ExtractException e) {
                throw e;
            }
            catch (Exception e) {
                throw new ExtractException(e.getMessage(), e);
            }
        }
    }

    private EFDCExtractResultImpl setResult(ExtractDataRegion region) {
        EFDCExtractResultImpl result = new EFDCExtractResultImpl();
        if (this.requestIsEmpty) {
            return result;
        }
        Object[][] resultArr = null;
        resultArr = region.isFloat() ? this.setFloatData(region) : this.setFixData(region);
        for (Object[] colValues : resultArr) {
            ExtractDataRow dataRow = new ExtractDataRow();
            for (Object value : colValues) {
                dataRow.addFieldValue(value);
            }
            result.addRow(dataRow);
        }
        this.resultMap.clear();
        return result;
    }

    private Object[][] setFixData(ExtractDataRegion region) {
        Object[][] resulteArray = new Object[1][region.getColmumCount()];
        for (Map.Entry<String, Object> entry : this.resultMap.entrySet()) {
            Object val;
            String key = entry.getKey();
            resulteArray[0][Integer.parseInt((String)key)] = val = entry.getValue();
        }
        return resulteArray;
    }

    private Object[][] setFloatData(ExtractDataRegion region) {
        Object[][] resulteArray = new Object[this.resultSize][region.getColmumCount()];
        for (Map.Entry<String, Object> entry : this.resultMap.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            String[] location = key.split(";");
            resulteArray[Integer.parseInt((String)location[1])][Integer.parseInt((String)location[0])] = val;
        }
        return resulteArray;
    }

    private void fillFixedReport(List<FixExpResult> fixResult) {
        if (fixResult == null) {
            return;
        }
        FixExpResult fix = null;
        for (int i = 0; i < fixResult.size(); ++i) {
            fix = fixResult.get(i);
            String key = fix.getFlag();
            Object value = this.getValue(fix, 0);
            this.resultMap.put(key, value);
        }
    }

    private void fillFloatReprot(FloatExpResult oneFloatResult, int areaNum) {
        List<FixExpResult> resultList = oneFloatResult.getColResults();
        this.resultSize = oneFloatResult.getRowCount();
        for (int row = 0; row < this.resultSize; ++row) {
            for (FixExpResult fixExpResult : resultList) {
                Object value = this.getValue(fixExpResult, row);
                this.resultMap.put(fixExpResult.getFlag() + ";" + row, value);
            }
        }
    }

    private Object getValue(FixExpResult result, int index) {
        Object ret = null;
        String dataType = result.getDataType();
        List<String> values = result.getValues();
        String tmp = values.get(index);
        if ("Int".equals(dataType)) {
            ret = Integer.valueOf(tmp);
        } else if ("String".equals(dataType)) {
            ret = tmp;
        } else if ("Double".equals(dataType)) {
            ret = Double.valueOf(tmp);
        } else if ("DateTime".equals(dataType)) {
            try {
                ret = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tmp);
            }
            catch (Exception e) {
                ret = tmp;
            }
        } else if ("Date".equals(dataType)) {
            try {
                ret = new SimpleDateFormat("yyyy-MM-dd").parse(tmp);
            }
            catch (Exception e) {
                ret = tmp;
            }
        } else {
            ret = "Boolean".equals(dataType) ? Boolean.valueOf(tmp) : tmp;
        }
        return ret;
    }

    @Override
    protected IExtractResult doExtractRegion(ExecutorContext executorContext, ExtractDataRegion region, DimensionValueSet dimensionValueSet, Map<String, Object> paras) throws ExtractException {
        if (this.dfRequester.getFetchEnv().getUnitCode() == null) {
            String unitCode = null;
            this.dfRequester.getFetchEnv().setUnitCode(unitCode);
        }
        String period = this.dfRequester.getFetchEnv().getPeriodScheme();
        if (this.dfRequester.getFetchEnv().getPeriodScheme() == null) {
            period = (String)dimensionValueSet.getValue("DATATIME");
            this.dfRequester.getFetchEnv().setPeriodScheme(period);
        }
        if (paras.containsKey("startPay") && paras.containsKey("endPay")) {
            String endAdjustPeriod = paras.get("endPay").toString();
            String startAdjustPeriod = paras.get("startPay").toString();
            this.dfRequester.getFetchEnv().setEndAdjustPeriod(endAdjustPeriod);
            this.dfRequester.getFetchEnv().setStartAdjustPeriod(startAdjustPeriod);
        }
        try {
            this.setStartEndTime(executorContext, period);
        }
        catch (ParseException e) {
            throw new ExtractException(e.getMessage(), e);
        }
        this.fillFormulaGroup(region);
        this.doExcute();
        return this.setResult(region);
    }

    @Override
    protected IRegionParser createRegionParser() {
        return new EFDCRegionParser();
    }

    @Override
    protected String getType() {
        return "NR_EFDC";
    }
}

