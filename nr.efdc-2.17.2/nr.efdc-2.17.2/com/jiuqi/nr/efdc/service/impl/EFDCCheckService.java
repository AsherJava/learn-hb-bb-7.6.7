/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.efdc.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.efdc.exception.EFDCCheckErrorEnum;
import com.jiuqi.nr.efdc.extract.impl.request.SyntaxCheckFormula;
import com.jiuqi.nr.efdc.extract.impl.request.SyntaxCheckRequester;
import com.jiuqi.nr.efdc.extract.impl.response.SyntaxCheckResponser;
import com.jiuqi.nr.efdc.extract.impl.response.SyntaxCheckResult;
import com.jiuqi.nr.efdc.pojo.FormulaExceptionObj;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={NpRollbackException.class})
public class EFDCCheckService {
    private static final Logger log = LoggerFactory.getLogger(EFDCCheckService.class);
    private final String expType_fix = "Fix";
    private final String expType_float = "Float";
    private String FORMULACHECK_SERVICE = "DataFetchService?actionType=batchcheck";
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;

    public List<FormulaExceptionObj> checkEfdcFormula(List<FormulaExceptionObj> efdcFormulas) throws JQException {
        ArrayList<FormulaExceptionObj> checkres = new ArrayList<FormulaExceptionObj>();
        String address = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDCURL");
        if (StringUtils.isEmpty((String)address)) {
            throw new JQException((ErrorEnum)EFDCCheckErrorEnum.EFDCCHECK_EXCEPTION_001);
        }
        String penetrableSearchURL = this.getEfdecCheckUrl(address);
        SyntaxCheckRequester syncheckReq = this.getEfdcCheckReq(efdcFormulas);
        try {
            URL urlc = new URL(penetrableSearchURL);
            String resultGsonStr = null;
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String paraJsonStr = mapper.writeValueAsString((Object)syncheckReq);
            URLConnection con = urlc.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");){
                out.write(paraJsonStr);
            }
            var12_14 = null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));){
                resultGsonStr = reader.readLine();
                SyntaxCheckResponser syncheckResult = (SyntaxCheckResponser)mapper.readValue(resultGsonStr, SyntaxCheckResponser.class);
                List<SyntaxCheckResult> errResults = syncheckResult.getErrResults();
                FormulaExceptionObj formulaExc = null;
                for (int i = 0; i < errResults.size(); ++i) {
                    formulaExc = new FormulaExceptionObj();
                    formulaExc.setCode(errResults.get(i).getFlag());
                    formulaExc.setErrorMsg(errResults.get(i).getErrMsg());
                    formulaExc.setFormula(errResults.get(i).getExpression());
                    checkres.add(formulaExc);
                }
            }
            catch (Throwable throwable) {
                var12_14 = throwable;
                throw throwable;
            }
        }
        catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)EFDCCheckErrorEnum.EFDCCHECK_EXCEPTION_002, "[" + penetrableSearchURL + "]" + EFDCCheckErrorEnum.EFDCCHECK_EXCEPTION_002.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)EFDCCheckErrorEnum.EFDCCHECK_EXCEPTION_003, "[" + penetrableSearchURL + "]" + EFDCCheckErrorEnum.EFDCCHECK_EXCEPTION_002.getMessage());
        }
        return checkres;
    }

    private SyntaxCheckRequester getEfdcCheckReq(List<FormulaExceptionObj> efdcFormulas) {
        ArrayList<SyntaxCheckFormula> formulas = new ArrayList<SyntaxCheckFormula>();
        SyntaxCheckFormula formula = null;
        for (int i = 0; i < efdcFormulas.size(); ++i) {
            formula = new SyntaxCheckFormula();
            formula.setExpression(efdcFormulas.get(i).getEfdcformula());
            formula.setExpType(efdcFormulas.get(i).isfloat() ? "Float" : "Fix");
            formula.setFlag(efdcFormulas.get(i).getCode());
            formulas.add(formula);
        }
        SyntaxCheckRequester syncheckReq = new SyntaxCheckRequester();
        syncheckReq.setFormulas(formulas);
        return syncheckReq;
    }

    private String getEfdecCheckUrl(String Address) {
        String penetrableSearchURL = Address;
        if ((penetrableSearchURL = penetrableSearchURL.trim()).charAt(penetrableSearchURL.length() - 1) != '/') {
            penetrableSearchURL = penetrableSearchURL + "/";
        }
        penetrableSearchURL = penetrableSearchURL + this.FORMULACHECK_SERVICE;
        return penetrableSearchURL;
    }
}

