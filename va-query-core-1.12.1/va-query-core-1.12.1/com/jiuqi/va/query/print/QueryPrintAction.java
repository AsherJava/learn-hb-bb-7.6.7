/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.itextpdf.kernel.geom.PageSize
 *  com.itextpdf.layout.Document
 *  com.itextpdf.layout.element.IBlockElement
 *  com.jiuqi.va.print.adapt.PdfHandler
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.sql.vo.QueryGroupField
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.sql.vo.ResultVO
 *  com.jiuqi.va.query.sql.vo.TreeRow
 *  com.jiuqi.va.query.template.enumerate.PluginEnum
 *  com.jiuqi.va.query.template.plugin.QueryFieldsPlugin
 *  com.jiuqi.va.query.template.plugin.QueryPrintPlugin
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 *  com.jiuqi.va.query.template.vo.print.QueryPrintScheme
 */
package com.jiuqi.va.query.print;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.jiuqi.va.print.adapt.PdfHandler;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.print.PrintTableDraw;
import com.jiuqi.va.query.sql.service.impl.SqlQueryServiceImpl;
import com.jiuqi.va.query.sql.vo.QueryGroupField;
import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.sql.vo.ResultVO;
import com.jiuqi.va.query.sql.vo.TreeRow;
import com.jiuqi.va.query.template.enumerate.PluginEnum;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.QueryPrintPlugin;
import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.print.QueryPrintScheme;
import com.jiuqi.va.query.util.QueryPrintUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class QueryPrintAction {
    private static final Logger logger = LoggerFactory.getLogger(QueryPrintAction.class);
    @Autowired
    private SqlQueryServiceImpl sqlQueryService;

    public void executeTemplatePrint(Document document, QueryParamVO queryParamVO, String schemeCode) {
        queryParamVO.setPageSize(Integer.valueOf(20000));
        ObjectMapper objectMapper = new ObjectMapper();
        QueryParamVO cloneQueryParams = null;
        try {
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            cloneQueryParams = (QueryParamVO)objectMapper.readValue(objectMapper.writeValueAsString((Object)queryParamVO), QueryParamVO.class);
        }
        catch (Exception e) {
            logger.error("\u8f6c\u6362\u67e5\u8be2\u53c2\u6570\u5f02\u5e38", e);
            throw new DefinedQueryRuntimeException("\u8f6c\u6362\u67e5\u8be2\u53c2\u6570\u5f02\u5e38");
        }
        QueryPrintUtil.clearFontMap();
        ResultVO resultVO = new ResultVO();
        ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        String fieldName = "";
        if (CollectionUtils.isEmpty(queryParamVO.getGroupFields())) {
            resultVO = this.sqlQueryService.execSql(queryParamVO);
            fieldName = ((TemplateFieldSettingVO)((QueryFieldsPlugin)queryParamVO.getQueryTemplate().getPluginByName(PluginEnum.queryFields.name(), QueryFieldsPlugin.class)).getFields().get(0)).getName();
        } else {
            this.treeToList(dataList, this.sqlQueryService.expandAll(queryParamVO).getTreeRowList(), queryParamVO.getGroupFields().stream().map(QueryGroupField::getFieldName).collect(Collectors.toList()), 0);
            fieldName = ((QueryGroupField)queryParamVO.getGroupFields().get(0)).getFieldName();
            resultVO.setDataList(dataList);
            resultVO.setTotalCount(Integer.valueOf(dataList.size()));
        }
        Map<String, Object> gatherRow = this.sqlQueryService.getSumRowData(cloneQueryParams, true);
        Object tempFieldNameValue = gatherRow.get(fieldName);
        if (!ObjectUtils.isEmpty(tempFieldNameValue)) {
            gatherRow.put(fieldName, tempFieldNameValue);
        } else {
            gatherRow.put(fieldName, "\u5408\u8ba1");
        }
        resultVO.getDataList().add(gatherRow);
        QueryPrintPlugin queryPrintPlugin = (QueryPrintPlugin)queryParamVO.getQueryTemplate().getPluginByClass(QueryPrintPlugin.class);
        List schemes = queryPrintPlugin.getSchemes();
        if (CollectionUtils.isEmpty(schemes) || !StringUtils.hasText(schemeCode)) {
            throw new DefinedQueryRuntimeException("no print scheme!");
        }
        QueryPrintScheme queryPrintScheme = null;
        Optional<QueryPrintScheme> first = schemes.stream().filter(scheme -> scheme.getName().equals(schemeCode)).findFirst();
        if (!first.isPresent()) {
            throw new DefinedQueryRuntimeException("no print scheme!");
        }
        queryPrintScheme = first.get();
        Map printDocument = queryPrintScheme.getDocument();
        List children = (List)printDocument.get("children");
        Map pageConfig = (Map)children.get(0);
        PageSize pageSize = null;
        Object object = pageConfig.get("pageType");
        pageSize = object.equals("A2") ? PageSize.A2 : (object.equals("A8") ? PageSize.A8 : (object.equals("Custom") ? new PageSize(0.0f, 0.0f) : PageSize.A4));
        String padding = (String)pageConfig.get("padding");
        String[] values = padding.split(" ");
        float paddingTop = Float.parseFloat(values[0]);
        float paddingRight = Float.parseFloat(values[1]);
        float paddingBottom = Float.parseFloat(values[2]);
        float paddingLeft = Float.parseFloat(values[3]);
        document.setMargins(paddingTop, paddingRight, paddingBottom, paddingLeft);
        if ("custom".equals(pageConfig.get("pageType"))) {
            pageSize.setHeight(QueryPrintUtil.convertUnit(Float.parseFloat(pageConfig.get("height").toString())));
            pageSize.setWidth(QueryPrintUtil.convertUnit(Float.parseFloat(pageConfig.get("width").toString())));
            document.getPdfDocument().setDefaultPageSize(pageSize);
        } else if ("horizontal".equals(pageConfig.get("direction"))) {
            document.getPdfDocument().setDefaultPageSize(pageSize.rotate());
        } else {
            document.getPdfDocument().setDefaultPageSize(pageSize);
        }
        QueryPrintUtil.resetDocumentAvailableHeight(document);
        QueryPrintUtil.resetDocumentAvailableWidth(document);
        List pageChildren = (List)pageConfig.get("children");
        Map tableConfig = (Map)pageChildren.get(0);
        Integer totalCount = resultVO.getTotalCount();
        if (totalCount > 20000) {
            throw new DefinedQueryRuntimeException("\u6253\u5370\u6570\u636e\u91cf\u8fc7\u5927\uff08\u8d85\u8fc720000\u6761\uff09\uff0c\u8bf7\u5207\u6362\u67e5\u8be2\u6761\u4ef6\u51cf\u5c11\u6253\u5370\u6570\u636e\u91cf\uff01");
        }
        PrintTableDraw pageTableDraw = new PrintTableDraw();
        List<IBlockElement> iBlockElements = pageTableDraw.drawControl(document, resultVO.getDataList(), tableConfig);
        int size = iBlockElements.size();
        for (int i = 0; i < size; ++i) {
            try {
                document.add(iBlockElements.get(i));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (i == iBlockElements.size() - 1) continue;
            PdfHandler.addAreaBreak((Document)document, (String)"NEXT_PAGE");
        }
    }

    private List<Map<String, Object>> treeToList(List<Map<String, Object>> dataList, List<TreeRow> treeRowList, List<String> groupKeys, int level) {
        if (CollectionUtils.isEmpty(treeRowList)) {
            return dataList;
        }
        for (TreeRow treeRow : treeRowList) {
            Map rowValues = treeRow.getRowValues();
            if (level > 0) {
                rowValues.put(groupKeys.get(level - 1), "");
            }
            dataList.add(rowValues);
            List children = treeRow.getChildren();
            if (CollectionUtils.isEmpty(children)) continue;
            this.treeToList(dataList, children, groupKeys, level + 1);
        }
        return dataList;
    }
}

