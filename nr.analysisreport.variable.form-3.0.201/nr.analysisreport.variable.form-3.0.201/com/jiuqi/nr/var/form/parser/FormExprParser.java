/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource
 *  com.jiuqi.nr.analysisreport.async.executor.AnalysisReportThreadExuecutor
 *  com.jiuqi.nr.analysisreport.facade.DimensionObj
 *  com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper
 *  com.jiuqi.nr.analysisreport.helper.ReportVarHelper
 *  com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService
 *  com.jiuqi.nr.analysisreport.support.AbstractExprParser
 *  com.jiuqi.nr.analysisreport.utils.LockCacheUtil
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO$PeriodDim
 *  com.jiuqi.nr.analysisreport.vo.ReportBaseVO$UnitDim
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  io.netty.util.internal.StringUtil
 *  javax.annotation.Resource
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.jsoup.Jsoup
 *  org.jsoup.nodes.Attributes
 *  org.jsoup.nodes.Document
 *  org.jsoup.nodes.Element
 *  org.jsoup.nodes.Node
 *  org.jsoup.nodes.TextNode
 *  org.jsoup.select.Elements
 */
package com.jiuqi.nr.var.form.parser;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.analysisreport.annotation.AnaReportInsertVariableResource;
import com.jiuqi.nr.analysisreport.async.executor.AnalysisReportThreadExuecutor;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.helper.ReportVarHelper;
import com.jiuqi.nr.analysisreport.internal.service.IAnalysisReportEntityService;
import com.jiuqi.nr.analysisreport.support.AbstractExprParser;
import com.jiuqi.nr.analysisreport.utils.LockCacheUtil;
import com.jiuqi.nr.analysisreport.vo.ReportBaseVO;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.var.form.helper.FormGenerator;
import com.jiuqi.nr.var.form.util.FormUtil;
import io.netty.util.internal.StringUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AnaReportInsertVariableResource(name="\u62a5\u8868", pluginName="report-choose", pluginType="insert-variable-plugin", order=1)
public class FormExprParser
extends AbstractExprParser {
    private static final String COMMON_SIGN = "formVar";
    @Autowired
    private IAnalysisReportEntityService analysisReportEntityService;
    @Resource
    public IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private FormGenerator formGenerator;
    private static final Logger logger = LoggerFactory.getLogger(FormExprParser.class);

    public String getName() {
        return COMMON_SIGN;
    }

    public void parse(Element es, Object ext) {
    }

    public void parse(Elements elements, ReportVariableParseVO reportVariableParseVO) {
        Map<String, DimensionValue> dimensionValueMap = this.buildDimensionValue(reportVariableParseVO);
        if (elements.size() == 1) {
            this.parse((Element)elements.get(0), reportVariableParseVO, dimensionValueMap);
        } else {
            ArrayList<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
            int corePoolSize = this.getThreadPoolTaskExecutor().getCorePoolSize();
            List lists = FormUtil.splitList(elements, corePoolSize);
            for (List list : lists) {
                futures.add(this.getThreadPoolTaskExecutor().submit(() -> {
                    for (Element element : list) {
                        this.parse(element, reportVariableParseVO, dimensionValueMap);
                    }
                    return true;
                }));
            }
            AnalysisReportThreadExuecutor.waitAllFinish(futures);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void parse(Element es, ReportVariableParseVO reportVariableParseVO, Map<String, DimensionValue> dimensionValueMap) {
        logger.info("\u5f00\u59cb\u89e3\u6790\u62a5\u8868" + es.attr("var-title") + es.attr("var-expr"));
        ReentrantLock reentrantLock = LockCacheUtil.getCacheLock((NpContext)NpContextHolder.getContext());
        try {
            String formTableData = this.formGenerator.doGenerator(dimensionValueMap, reportVariableParseVO, es);
            if (!StringUtil.isNullOrEmpty((String)formTableData)) {
                formTableData = ReportVarHelper.copyAttributes((String)formTableData, (Attributes)es.attributes(), (String)"table");
                reentrantLock.lock();
                es.before(formTableData);
                es.remove();
            }
        }
        catch (Exception ex) {
            String exTitle = new StringBuffer().append("\u8868\u5355\u53d8\u91cf[").append(es.attr("var-expr")).append("]\u8fd0\u7b97\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0[").append(!StringUtil.isNullOrEmpty((String)ex.getMessage()) ? ex.getMessage() : ex.toString()).append("]").toString();
            AnalysisReportLogHelper.log((String)(exTitle.length() > 100 ? exTitle.substring(0, 100) : exTitle), (String)(exTitle + ex.toString()), (int)AnalysisReportLogHelper.LOGLEVEL_ERROR);
            es.replaceWith((Node)new TextNode(exTitle));
            logger.info("\u89e3\u6790\u62a5\u8868\u62a5\u9519" + es.attr("var-title") + es.attr("var-expr"));
        }
        finally {
            reentrantLock.unlock();
        }
        logger.info("\u7ed3\u675f\u89e3\u6790\u62a5\u8868" + es.attr("var-title") + es.attr("var-expr"));
    }

    public Map<String, DimensionValue> buildDimensionValue(ReportVariableParseVO reportVariableParseVO) {
        HashMap<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
        ReportBaseVO reportBaseVO = reportVariableParseVO.getReportBaseVO();
        List chooseUnits = reportBaseVO.getChooseUnits();
        for (ReportBaseVO.UnitDim chooseUnit : chooseUnits) {
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setValue(chooseUnit.getCode());
            dimensionValue.setName(this.entityMetaService.getDimensionName(chooseUnit.getViewKey()));
            dimensionValueMap.put(chooseUnit.getViewKey(), dimensionValue);
        }
        ReportBaseVO.PeriodDim period = reportBaseVO.getPeriod();
        DimensionValue dateDim = new DimensionValue();
        dateDim.setValue(period.getCalendarCode());
        dateDim.setName(this.periodEntityAdapter.getPeriodDimensionName());
        dimensionValueMap.put(dateDim.getName(), dateDim);
        return dimensionValueMap;
    }

    public Object queryEntitys(Object param) {
        if (param == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(param.toString());
        String queryType = jsonObject.getString("queryType");
        if ("FORM_ELEMENT_DIM".equals(queryType)) {
            JSONArray taskKeys = jsonObject.getJSONArray("taskKeys");
            return this.queryDimForElement(taskKeys);
        }
        if ("FORM_DIM_SETTINGS".equals(queryType)) {
            JSONArray taskKeys = jsonObject.getJSONArray("taskKeys");
            return this.queryDimByTask(taskKeys);
        }
        String fullContent = jsonObject.getString("fullContent");
        return this.queryDimByElement(fullContent);
    }

    public Map<String, Object> queryDimForElement(JSONArray taskKeys) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (Object taskKey : taskKeys) {
            if (taskKey == null || !StringUtils.isNotEmpty((String)taskKey.toString())) continue;
            ArrayList defines = new ArrayList();
            Map dimensionDefines = this.analysisReportEntityService.getDimensionDefine(taskKey.toString());
            dimensionDefines.keySet().forEach(key -> defines.add(dimensionDefines.get(key)));
            map.put(taskKey.toString(), defines);
        }
        return map;
    }

    public List<DimensionObj> queryDimByTask(JSONArray taskKeys) {
        ArrayList<DimensionObj> masterEntityKeyList = new ArrayList<DimensionObj>();
        HashSet masterEntityKeySet = new HashSet();
        IAnalysisReportEntityService analysisReportEntityService = (IAnalysisReportEntityService)BeanUtil.getBean(IAnalysisReportEntityService.class);
        for (Object taskKey : taskKeys) {
            try {
                masterEntityKeySet.addAll(analysisReportEntityService.makeDimensionObject(taskKey.toString()));
            }
            catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        Iterator exp = masterEntityKeySet.iterator();
        while (exp.hasNext()) {
            masterEntityKeyList.add((DimensionObj)exp.next());
        }
        return masterEntityKeyList;
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
        Elements es = body.getElementsByClass(COMMON_SIGN);
        for (Element e : es) {
            try {
                String formulaExpr = e.attr("var-expr");
                String formSchemeKey = e.attr("var-formscheme-key");
                if (StringUtils.isEmpty((String)formSchemeKey)) {
                    masterEntityKeySet.addAll(this.analysisReportEntityService.getDimensionByFormula(formulaExpr));
                    continue;
                }
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
                if (formScheme == null) continue;
                masterEntityKeySet.addAll(this.analysisReportEntityService.makeDimensionObject(formScheme.getTaskKey()));
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

