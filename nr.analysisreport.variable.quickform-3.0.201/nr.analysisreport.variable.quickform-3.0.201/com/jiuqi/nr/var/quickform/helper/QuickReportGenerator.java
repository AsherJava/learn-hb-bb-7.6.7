/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.nr.analysisreport.utils.AnaUtils
 *  com.jiuqi.nr.analysisreport.utils.Col
 *  com.jiuqi.nr.analysisreport.utils.Row
 *  com.jiuqi.nr.analysisreport.utils.Table
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.var.common.uitl.TableUtil
 *  com.jiuqi.nr.var.common.vo.HtmlTableContext
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  io.netty.util.internal.StringUtil
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.quickform.helper;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nr.analysisreport.utils.AnaUtils;
import com.jiuqi.nr.analysisreport.utils.Col;
import com.jiuqi.nr.analysisreport.utils.Row;
import com.jiuqi.nr.analysisreport.utils.Table;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.var.common.uitl.TableUtil;
import com.jiuqi.nr.var.common.vo.HtmlTableContext;
import com.jiuqi.nvwa.grid2.Grid2Data;
import io.netty.util.internal.StringUtil;
import java.util.List;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuickReportGenerator {
    private static final Logger logger = LoggerFactory.getLogger(QuickReportGenerator.class);

    public String geneartorTable(GridData gridData, Element es, String modelKey, String type, ReportVariableParseVO reportVariableParseVO) {
        Table table = null;
        try {
            Grid2Data grid2Data = new Grid2Data();
            AnaUtils.dataTodata2((GridData)gridData, (Grid2Data)grid2Data);
            HtmlTableContext htmlTableContext = new HtmlTableContext(grid2Data, type, modelKey);
            TableUtil.dealTableCustomSettings((HtmlTableContext)htmlTableContext, (Element)es, (ReportVariableParseVO)reportVariableParseVO);
            table = TableUtil.geneartorTable((HtmlTableContext)htmlTableContext);
            this.cleanEmpty(table);
            return table.toString();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    private void cleanEmpty(Table table) {
        for (Row row : table.getRows()) {
            List cols = row.getCols();
            for (int i = 0; i < cols.size(); ++i) {
                Col col = (Col)cols.get(i);
                if (col.getValue() != null || col.isShow() && !StringUtil.isNullOrEmpty((String)col.getAttribute().toString()) && col.getAttribute().indexOf("colspan") >= 0 && col.getAttribute().indexOf("rowspan") >= 0) continue;
                row.removeCol(i--);
            }
        }
    }
}

