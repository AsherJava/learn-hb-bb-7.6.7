/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.print.vo.PrintAttributeVo
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.xg.print.SimplePaintInteractor
 *  com.jiuqi.xg.process.IPaginateInteractor
 *  com.jiuqi.xg.process.IPaintInteractor
 *  com.jiuqi.xg.process.IProcessMonitor
 *  com.jiuqi.xg.process.ITemplateDocument
 *  com.jiuqi.xg.process.SimpleProcessMonitor
 *  com.jiuqi.xg.process.table.TablePaginateConfig
 */
package com.jiuqi.nr.query.print.service.impl;

import com.jiuqi.nr.definition.print.vo.PrintAttributeVo;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.print.QueryPrintTemplateService;
import com.jiuqi.nr.query.print.service.IQueryPrintService;
import com.jiuqi.nr.query.print.service.impl.PDFPrintUtil2;
import com.jiuqi.nr.query.print.service.impl.QueryPrintIPaginateInteractor;
import com.jiuqi.nr.query.print.service.impl.QueryPrintParam;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.xg.print.SimplePaintInteractor;
import com.jiuqi.xg.process.IPaginateInteractor;
import com.jiuqi.xg.process.IPaintInteractor;
import com.jiuqi.xg.process.IProcessMonitor;
import com.jiuqi.xg.process.ITemplateDocument;
import com.jiuqi.xg.process.SimpleProcessMonitor;
import com.jiuqi.xg.process.table.TablePaginateConfig;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryPrintServiceImpl
implements IQueryPrintService {
    private static final Logger log = LoggerFactory.getLogger(QueryPrintServiceImpl.class);
    @Autowired
    private QueryPrintTemplateService queryPrintTemplateService;

    @Override
    public ByteArrayOutputStream print(Grid2Data grid2Data, QueryBlockDefine queryBlockDefine, PrintAttributeVo attrVoConfig, TablePaginateConfig paginateConfig) {
        ITemplateDocument documentTemplateObject = null;
        try {
            documentTemplateObject = this.queryPrintTemplateService.loadQueryTemplateDocument(queryBlockDefine, grid2Data, attrVoConfig, paginateConfig);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        documentTemplateObject.setNature("REPORT_PRINT_NATURE");
        QueryPrintParam param = new QueryPrintParam();
        param.setFormKey(queryBlockDefine.getBlockInfo().getFormSchemeKey());
        param.setPrintPageNum(QueryPrintTemplateService.isPrintPageNum);
        QueryPrintIPaginateInteractor queryPrintIPaginateInteractor = new QueryPrintIPaginateInteractor(param);
        PDFPrintUtil2.printPDF((ITemplateDocument)documentTemplateObject, (OutputStream)out);
        PDFPrintUtil2.printPDF(documentTemplateObject, (IPaginateInteractor)queryPrintIPaginateInteractor, (IPaintInteractor)new SimplePaintInteractor(), out, (IProcessMonitor)new SimpleProcessMonitor(System.out, System.err, System.out));
        return out;
    }
}

