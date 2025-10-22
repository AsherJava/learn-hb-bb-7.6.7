/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource
 *  com.jiuqi.nr.analysisreport.async.executor.AnalysisReportThreadExuecutor
 *  com.jiuqi.nr.analysisreport.facade.DimensionObj
 *  com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService
 *  com.jiuqi.nr.analysisreport.support.AbstractExprParser
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  org.json.JSONObject
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.var.formula.parser;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource;
import com.jiuqi.nr.analysisreport.async.executor.AnalysisReportThreadExuecutor;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.support.AbstractExprParser;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.var.formula.common.ReportFormulaUtil;
import com.jiuqi.nr.var.formula.helper.FormulaDivideHelper;
import com.jiuqi.nr.var.formula.helper.FormulaEvaluator;
import com.jiuqi.nr.var.formula.vo.ReportFormulaGroup;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AnaReportInsertVariableResource(name="\u6307\u6807/\u516c\u5f0f", pluginName="formula-choose", pluginType="insert-variable-plugin", order=0)
public class FormulaExprParser
extends AbstractExprParser {
    private static Logger logger = LoggerFactory.getLogger(FormulaExprParser.class);
    @Autowired
    private IAnalysisReportEntityService iAnalysisReportEntityService;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    FormulaDivideHelper formulaDivideHelper;
    @Autowired
    private FormulaEvaluator formulaEvaluator;

    public String getName() {
        return "formulaVar";
    }

    public void parse(Element es, Object ext) {
    }

    public void parse(Elements elements, ReportVariableParseVO reportVariableParseVO) {
        try {
            int corePoolSize = this.getThreadPoolTaskExecutor().getCorePoolSize();
            List lists = ReportFormulaUtil.splitList(elements, corePoolSize);
            ArrayList<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
            for (List list : lists) {
                futures.add(this.getThreadPoolTaskExecutor().submit(() -> {
                    Map<String, ReportFormulaGroup> reportFormulaGroups = this.formulaDivideHelper.dealElemets(list, reportVariableParseVO);
                    for (ReportFormulaGroup reportFormulaGroup : reportFormulaGroups.values()) {
                        this.formulaEvaluator.calculate(reportFormulaGroup, reportVariableParseVO);
                    }
                    return true;
                }));
            }
            AnalysisReportThreadExuecutor.waitAllFinish(futures);
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    public Object queryEntitys(Object param) {
        JSONObject jsonObject = new JSONObject(param.toString());
        String fullContent = jsonObject.getString("fullContent");
        return this.queryDimByElement(fullContent);
    }

    public List<DimensionObj> queryDimByElement(String fullContent) {
        if (StringUtils.isEmpty((String)fullContent)) {
            return null;
        }
        try {
            fullContent = URLDecoder.decode(fullContent, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage(), e);
            return null;
        }
        ArrayList<DimensionObj> masterEntityKeyList = new ArrayList<DimensionObj>();
        HashSet masterEntityKeySet = new HashSet();
        Document doc = Jsoup.parseBodyFragment((String)fullContent);
        Element body = doc.body();
        Elements es = body.getElementsByClass("formulaVar");
        for (Element e : es) {
            try {
                String formulaExpr = e.attr("var-expr");
                String formSchemeKey = e.attr("var-formscheme-key");
                if (StringUtils.isEmpty((String)formSchemeKey)) {
                    masterEntityKeySet.addAll(this.iAnalysisReportEntityService.getDimensionByFormula(formulaExpr));
                    continue;
                }
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
                if (formScheme == null) continue;
                masterEntityKeySet.addAll(this.iAnalysisReportEntityService.makeDimensionObject(formScheme.getTaskKey()));
            }
            catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        Iterator exp = masterEntityKeySet.iterator();
        while (exp.hasNext()) {
            masterEntityKeyList.add((DimensionObj)exp.next());
        }
        return masterEntityKeyList;
    }
}

