/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.office.template.document.DocumentContext
 *  com.jiuqi.nvwa.office.template.document.IDocumentSchemaDataProvider
 *  com.jiuqi.nvwa.office.template.document.ISchemaExceptionHandler
 *  com.jiuqi.nvwa.office.template.document.TagContentObject
 *  com.jiuqi.nvwa.office.template.document.TagContentObjectList
 */
package com.jiuqi.nr.datareport.service.impl;

import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nr.datareport.exception.ReportRuntimeException;
import com.jiuqi.nr.datareport.helper.GridDataHelper;
import com.jiuqi.nr.datareport.helper.GridDataTransformUtil;
import com.jiuqi.nr.datareport.helper.ReportUtil;
import com.jiuqi.nr.datareport.obj.NrDocumentParam;
import com.jiuqi.nr.datareport.obj.ReportEnv;
import com.jiuqi.nr.datareport.service.impl.NrDocumentExceptionHandler;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.office.template.document.DocumentContext;
import com.jiuqi.nvwa.office.template.document.IDocumentSchemaDataProvider;
import com.jiuqi.nvwa.office.template.document.ISchemaExceptionHandler;
import com.jiuqi.nvwa.office.template.document.TagContentObject;
import com.jiuqi.nvwa.office.template.document.TagContentObjectList;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NrFormDocumentSchemeDataProvider
implements IDocumentSchemaDataProvider {
    @Autowired
    private GridDataHelper gridDataHelper;
    @Autowired
    private RuntimeViewController runtimeViewController;
    @Autowired
    private NrDocumentExceptionHandler nrDocumentExceptionHandler;

    public boolean isSupport(String schema) {
        return "nr-form".equalsIgnoreCase(schema);
    }

    public void tableContent(TagContentObjectList<GridData> contents, DocumentContext context) {
        for (TagContentObject content : contents) {
            try {
                GridData formTagValue = this.getFormTagValue(content.getPath(), context);
                content.setResult((Object)formTagValue);
                content.setTableContext(ReportUtil.getWordTableContext());
            }
            catch (Exception e) {
                content.setError(e);
            }
        }
    }

    public ISchemaExceptionHandler getSchemaExceptionHandler() {
        return this.nrDocumentExceptionHandler;
    }

    private GridData getFormTagValue(String path, DocumentContext documentContext) throws Exception {
        if (StringUtils.hasText(path)) {
            FormDefine form;
            NrDocumentParam nrDocumentParam = ReportUtil.getNrDocumentParam(documentContext);
            ReportEnv reportEnv = nrDocumentParam.getReportEnv();
            ArrayList<String> var = new ArrayList<String>();
            for (String s : path.split("[/\\s]")) {
                if (!StringUtils.hasText(s)) continue;
                var.add(s);
            }
            if (var.size() == 1) {
                form = this.runtimeViewController.queryFormByCodeInScheme(reportEnv.getFormSchemeKey(), (String)var.get(0));
            } else if (var.size() == 3) {
                String formSchemeCode = (String)var.get(1);
                String formCode = (String)var.get(2);
                FormSchemeDefine formScheme = this.runtimeViewController.getFormschemeByCode(formSchemeCode);
                form = this.runtimeViewController.queryFormByCodeInScheme(formScheme.getKey(), formCode);
            } else {
                throw new ReportRuntimeException("\u6807\u7b7e\u5185\u5bb9\u89e3\u6790\u5f02\u5e38\uff1a" + path);
            }
            String formKey = form.getKey();
            Grid2Data grid2Data = this.gridDataHelper.getGridData(formKey);
            DimensionCombination dimensionCombination = nrDocumentParam.getDimensionCombination();
            Grid2Data fillGrid2Dta = this.gridDataHelper.fillGrid2Dta(formKey, reportEnv.getFormulaSchemeKey(), dimensionCombination, grid2Data);
            if (fillGrid2Dta == null) {
                fillGrid2Dta = grid2Data;
            }
            GridData gridData = new GridData();
            GridDataTransformUtil.data2ToData(fillGrid2Dta, gridData);
            return gridData;
        }
        throw new ReportRuntimeException("\u6807\u7b7e\u5185\u5bb9\u89e3\u6790\u5f02\u5e38\uff1a" + path);
    }
}

