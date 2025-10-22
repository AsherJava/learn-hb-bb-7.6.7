/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DSModelFactoryManager
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.NumberCellPropertyIntf
 *  com.jiuqi.bi.syntax.excel.ExcelExportor
 *  com.jiuqi.bi.syntax.excel.ISheetDescriptor
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.bi.dataset.report.remote.controller;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.bi.dataset.report.bean.SummaryTreeNode;
import com.jiuqi.bi.dataset.report.builder.ReportDSModelBuilder;
import com.jiuqi.bi.dataset.report.builder.intf.IReportDsModelDataBuilder;
import com.jiuqi.bi.dataset.report.exception.ExportError;
import com.jiuqi.bi.dataset.report.exception.PreviewError;
import com.jiuqi.bi.dataset.report.exception.ReportDsModelDataBuildException;
import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.dataset.report.model.ReportDsModelDefine;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import com.jiuqi.bi.dataset.report.provider.ExpParseProvider;
import com.jiuqi.bi.dataset.report.provider.PasteFieldParseProvider;
import com.jiuqi.bi.dataset.report.provider.SelectFieldParseProvider;
import com.jiuqi.bi.dataset.report.provider.SummaryTreeProvider;
import com.jiuqi.bi.dataset.report.query.ReportQueryExecutor;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ExpParsedFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.PasteFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.PasteParsedFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.PreviewResultVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ReportDsModelDefaultInfoVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ReportExpFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ReportSelectFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.TaskNodeVo;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.NumberCellPropertyIntf;
import com.jiuqi.bi.syntax.excel.ExcelExportor;
import com.jiuqi.bi.syntax.excel.ISheetDescriptor;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/report/dataset"})
public class ReportDataSetController {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ExpParseProvider expParseProvider;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private SelectFieldParseProvider selectFieldParseProvider;
    @Autowired
    private PasteFieldParseProvider pasteFieldParseProvider;
    @Autowired
    private ReportQueryExecutor queryExecutor;
    @Autowired
    private ReportDSModelBuilder modelBuilder;
    @Autowired
    private SummaryTreeProvider summaryTreeProvider;
    @Autowired
    private IReportDsModelDataBuilder reportDsModelDataBuilder;

    @GetMapping(value={"/taskTitle/{taskKey}"})
    public String getTaskTitle(@PathVariable String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        return taskDefine != null ? taskDefine.getTitle() : null;
    }

    @GetMapping(value={"/tasks"})
    public List<TaskNodeVo> getAllReportTask() {
        ArrayList<TaskNodeVo> taskNodeVos = new ArrayList<TaskNodeVo>();
        List taskDefineList = this.runTimeViewController.getAllReportTaskDefines();
        if (!CollectionUtils.isEmpty(taskDefineList)) {
            taskDefineList.forEach(taskDefine -> taskNodeVos.add(new TaskNodeVo(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle())));
        }
        return taskNodeVos;
    }

    @GetMapping(value={"/defaultInfo/{taskKey}"})
    public ReportDsModelDefaultInfoVo getDefaultInfo(@PathVariable String taskKey) throws ReportDsModelDataBuildException {
        ReportDsModelDefaultInfoVo result = new ReportDsModelDefaultInfoVo();
        ReportDsModelDefine reportDsModelDefine = this.reportDsModelDataBuilder.buildDefault(taskKey);
        result.setDefaultFields(reportDsModelDefine.getFields());
        result.setDefaultParameters(reportDsModelDefine.getParameters());
        return result;
    }

    @PostMapping(value={"/fieldSelect"})
    public List<ReportExpField> parseSelectFields(@RequestBody ReportSelectFieldVo selectField) throws JQException {
        return this.selectFieldParseProvider.parse(selectField.getSelectFields(), selectField.getDsParameters());
    }

    @PostMapping(value={"/parse/{taskId}"})
    public List<ExpParsedFieldVo> parseExpFields(@PathVariable String taskId, @RequestBody ReportExpFieldVo field) throws JQException {
        return this.expParseProvider.doParse(taskId, field.getExpFieldVos(), field.getDsParameters());
    }

    @PostMapping(value={"/fieldPaste/{taskKey}"})
    public List<PasteParsedFieldVo> pasteExpFields(@PathVariable String taskKey, @RequestBody PasteFieldVo fieldVo) throws JQException {
        return this.pasteFieldParseProvider.parse(taskKey, fieldVo.getFields(), fieldVo.getDsParameters());
    }

    @PostMapping(value={"/preview/{currentPage}/{pageSize}"})
    public PreviewResultVo preview(@RequestBody ReportDsModelDefine dsModelDefine, @PathVariable int currentPage, @PathVariable int pageSize) throws JQException {
        try {
            PreviewResultVo result = this.queryExecutor.preview(dsModelDefine, pageSize, currentPage);
            List<ReportExpField> expFields = dsModelDefine.getFields();
            ArrayList<Integer> dateFieldIndeies = new ArrayList<Integer>();
            for (int i = 0; i < expFields.size(); ++i) {
                if (expFields.get(i).getDataType() != 2) continue;
                dateFieldIndeies.add(i);
            }
            List<Object[]> rows = result.getResult();
            if (!CollectionUtils.isEmpty(rows)) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                for (Object[] row : rows) {
                    Iterator iterator = dateFieldIndeies.iterator();
                    while (iterator.hasNext()) {
                        int index = (Integer)iterator.next();
                        Object value = row[index];
                        if (value == null) continue;
                        String formatDateValue = value.toString();
                        if (value instanceof GregorianCalendar) {
                            formatDateValue = dateFormatter.format(((GregorianCalendar)value).getTime());
                        }
                        row[index] = formatDateValue;
                    }
                }
            }
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)new PreviewError(e.getMessage()), (Throwable)e);
        }
    }

    @PostMapping(value={"/exportData"})
    public void export(@RequestBody ReportDsModelDefine dsModelDefine, HttpServletResponse response) throws JQException {
        ReportDSModel dsModel = new ReportDSModel();
        dsModel.setReportDsModelDefine(dsModelDefine);
        try {
            this.modelBuilder.buildModel(dsModel);
            MemoryDataSet memoryDataSet = DSModelFactoryManager.createMemoryDataSet((DSModel)dsModel);
            this.queryExecutor.runQuery(dsModel, null, -1, 0, (MemoryDataSet<BIDataSetFieldInfo>)memoryDataSet);
            Metadata metadata = memoryDataSet.getMetadata();
            final GridData gridData = new GridData(memoryDataSet.getMetadata().getColumnCount() + 1, memoryDataSet.size() + 2);
            memoryDataSet.toGrid(gridData);
            for (int col = 0; col < metadata.getColumnCount(); ++col) {
                Column column = metadata.getColumn(col);
                if (column.getDataType() != 10) continue;
                for (int row = 2; row < gridData.getRowCount(); ++row) {
                    String[] dataArr;
                    GridCell cellEx = gridData.getCellEx(col + 1, row);
                    NumberCellPropertyIntf numberCell = cellEx.toNumberCell();
                    String cellData = cellEx.getCellData();
                    if (!StringUtils.hasLength(cellData) || (dataArr = cellData.split("\\.")).length <= 1) continue;
                    numberCell.setDecimal(dataArr[1].length());
                    gridData.setCell(cellEx);
                }
            }
            ArrayList<1> sheetDescriptors = new ArrayList<1>();
            sheetDescriptors.add(new ISheetDescriptor(){

                public String name() {
                    return "\u67e5\u8be2\u7ed3\u679c";
                }

                public GridData grid() {
                    return gridData;
                }

                public String getExpression(int arg0, int arg1) {
                    return null;
                }

                public void setExpression(int arg0, int arg1, String arg2) {
                }
            });
            ExcelExportor exportor = new ExcelExportor(sheetDescriptors.iterator(), true);
            String attachName = "\u8868\u683c.xlsx";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(attachName, "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            exportor.export((OutputStream)outputStream);
            response.flushBuffer();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)new ExportError("\u5bfc\u51fa\u9519\u8bef\uff1a" + e.getMessage()), (Throwable)e);
        }
    }

    @GetMapping(value={"/summarytree/root/{taskKey}"})
    public List<ITree<SummaryTreeNode>> getSummaryTreeRoot(@PathVariable String taskKey) {
        return this.summaryTreeProvider.getChildrenByGroup(taskKey, "00000000-0000-0000-0000-000000000000");
    }

    @GetMapping(value={"/summarytree/children/{taskKey}/{groupKey}"})
    public List<ITree<SummaryTreeNode>> getSummaryTreeChildren(@PathVariable String taskKey, @PathVariable String groupKey) {
        return this.summaryTreeProvider.getChildrenByGroup(taskKey, groupKey);
    }

    @GetMapping(value={"/summaryscheme/title/{taskKey}/{summarySchemeCode}"})
    public String getSummarySchemeTitle(@PathVariable String taskKey, @PathVariable String summarySchemeCode) {
        return this.summaryTreeProvider.getSummarySchemeTitle(taskKey, summarySchemeCode);
    }
}

