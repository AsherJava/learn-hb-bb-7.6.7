/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingVO
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.util.StringHelper
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.multcheck2.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bsp.contentcheckrules.beans.CheckGroupingVO;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.multcheck2.bean.MCHistoryScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;
import com.jiuqi.nr.multcheck2.collector.MultcheckCollector;
import com.jiuqi.nr.multcheck2.common.MultcheckSchemeError;
import com.jiuqi.nr.multcheck2.common.MultcheckUtil;
import com.jiuqi.nr.multcheck2.common.OrgType;
import com.jiuqi.nr.multcheck2.common.SerializeUtil;
import com.jiuqi.nr.multcheck2.dao.MCHistorySchemeDao;
import com.jiuqi.nr.multcheck2.provider.IMultcheckItemProvider;
import com.jiuqi.nr.multcheck2.service.IMCDimService;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCRuleService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.web.vo.FormSchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.MCHistorySchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckSchemeVO;
import com.jiuqi.nr.multcheck2.web.vo.OrgExportVO;
import com.jiuqi.nr.multcheck2.web.vo.ReportDimVO;
import com.jiuqi.nr.multcheck2.web.vo.TaskTreeNodeVO;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.util.StringHelper;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MCEnvServiceImpl
implements IMCEnvService {
    private static final Logger logger = LoggerFactory.getLogger(MCEnvServiceImpl.class);
    @Autowired
    private RunTimeAuthViewController runTimeAuthView;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private MultcheckCollector collector;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private MCHistorySchemeDao historySchemeDao;
    @Autowired
    private IRunTimeViewController dRunTime;
    @Autowired
    private IMCRuleService ruleService;
    @Autowired
    private IMCDimService mcDimService;

    @Override
    @Transactional
    public void addScheme(MultcheckSchemeVO multcheckScheme) throws JQException {
        multcheckScheme.setKey(UUID.randomUUID().toString());
        this.checkScheme(multcheckScheme, true);
        this.doAddScheme(multcheckScheme);
        this.initReportDim(multcheckScheme.getKey(), multcheckScheme.getTask());
    }

    @Override
    public void initReportDim(String schemeKey, String taskKey) {
        ReportDimVO dim = new ReportDimVO();
        HashMap<String, String> schemeDimSet = new HashMap<String, String>();
        HashMap<String, Map<String, String>> itemsDimSet = new HashMap<String, Map<String, String>>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        List<DataDimension> dimsForReport = this.mcDimService.getOtherDimsForReport(taskDefine.getDataScheme());
        if (!CollectionUtils.isEmpty(dimsForReport)) {
            for (DataDimension dimension : dimsForReport) {
                IEntityDefine entity = this.entityMetaService.queryEntity(dimension.getDimKey());
                schemeDimSet.put(entity.getDimensionName(), "");
            }
            dim.setSchemeDimSet(schemeDimSet);
            dim.setItemsDimSet(itemsDimSet);
            try {
                this.schemeService.saveReportDim(schemeKey, SerializeUtil.serializeToJson(dim));
            }
            catch (Exception e) {
                logger.error(schemeKey);
                logger.error("\u4fdd\u5b58\u65b9\u6848\u7684\u8865\u5168\u60c5\u666f\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }

    private void doAddScheme(MultcheckSchemeVO multcheckScheme) {
        MultcheckScheme scheme = new MultcheckScheme();
        BeanUtils.copyProperties(multcheckScheme, scheme);
        scheme.setOrder(OrderGenerator.newOrder());
        this.schemeService.addScheme(scheme);
        if (OrgType.SELECT == multcheckScheme.getOrgType()) {
            ArrayList<MultcheckSchemeOrg> list = new ArrayList<MultcheckSchemeOrg>();
            for (String org : multcheckScheme.getOrgs()) {
                list.add(new MultcheckSchemeOrg(scheme.getKey(), org));
            }
            this.schemeService.deleteOrgByScheme(multcheckScheme.getKey());
            this.schemeService.batchAddOrg(list);
        }
        if (multcheckScheme.isInitCheckItem()) {
            this.schemeService.addItemsByScheme(scheme);
        }
    }

    private void checkScheme(MultcheckSchemeVO multcheckScheme, boolean needCreateTable) throws JQException {
        this.schemeVerifyBeforeSave(multcheckScheme);
        List<MultcheckScheme> schemes = this.schemeService.getSchemeByForm(multcheckScheme.getFormScheme());
        if (!CollectionUtils.isEmpty(schemes)) {
            for (MultcheckScheme scheme : schemes) {
                if (scheme.getKey().equals(multcheckScheme.getKey())) continue;
                if (scheme.getCode().equals(multcheckScheme.getCode())) {
                    throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_010);
                }
                if (!scheme.getTitle().equals(multcheckScheme.getTitle())) continue;
                throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_011);
            }
        }
        this.repairOrg(multcheckScheme);
    }

    @Override
    public void modifyScheme(MultcheckSchemeVO multcheckScheme) throws JQException {
        this.checkScheme(multcheckScheme, false);
        MultcheckScheme scheme = new MultcheckScheme();
        BeanUtils.copyProperties(multcheckScheme, scheme);
        this.schemeService.modifyScheme(scheme);
        if (OrgType.SELECT == multcheckScheme.getOrgType()) {
            ArrayList<MultcheckSchemeOrg> list = new ArrayList<MultcheckSchemeOrg>();
            for (String org : multcheckScheme.getOrgs()) {
                list.add(new MultcheckSchemeOrg(multcheckScheme.getKey(), org));
            }
            this.schemeService.deleteOrgByScheme(multcheckScheme.getKey());
            this.schemeService.batchAddOrg(list);
        }
    }

    @Override
    @Transactional
    public void copyScheme(String oldScheme, MultcheckSchemeVO multcheckScheme) throws JQException {
        List<MultcheckItem> itemList;
        multcheckScheme.setKey(UUID.randomUUID().toString());
        this.checkScheme(multcheckScheme, false);
        this.doAddScheme(multcheckScheme);
        String reportDim = this.schemeService.getReportDim(oldScheme);
        if (StringUtils.hasText(reportDim)) {
            this.schemeService.saveReportDim(multcheckScheme.getKey(), reportDim);
        }
        if (!CollectionUtils.isEmpty(itemList = this.schemeService.getItemList(oldScheme))) {
            for (MultcheckItem item : itemList) {
                item.setKey(UUID.randomUUID().toString());
                item.setScheme(multcheckScheme.getKey());
            }
            this.schemeService.batchAddItem(itemList, multcheckScheme.getKey());
        }
    }

    @Override
    public List<MultcheckSchemeVO> getSchemeAndOrgCountByFormScheme(String formScheme) {
        ArrayList<MultcheckSchemeVO> vos = new ArrayList<MultcheckSchemeVO>();
        List<MultcheckScheme> schemes = this.schemeService.getSchemeByForm(formScheme);
        this.traceSchemeToVo(vos, schemes);
        return vos;
    }

    private void traceSchemeToVo(List<MultcheckSchemeVO> vos, List<MultcheckScheme> schemes) {
        if (CollectionUtils.isEmpty(schemes)) {
            return;
        }
        for (MultcheckScheme scheme : schemes) {
            MultcheckSchemeVO vo = new MultcheckSchemeVO();
            BeanUtils.copyProperties(scheme, vo);
            if (OrgType.SELECT == scheme.getOrgType()) {
                vo.setOrgCount(this.schemeService.getOrgCountByScheme(scheme.getKey()));
            }
            vos.add(vo);
        }
    }

    @Override
    public List<MultcheckSchemeVO> getSchemeAndOrgCountByTask(String task) {
        ArrayList<MultcheckSchemeVO> vos = new ArrayList<MultcheckSchemeVO>();
        try {
            List formSchemes = this.runTimeAuthView.queryFormSchemeByTask(task);
            Map<String, List<MultcheckScheme>> schemeMap = this.schemeService.getSchemeByTask(task);
            for (FormSchemeDefine formScheme : formSchemes) {
                this.traceSchemeToVo(vos, schemeMap.get(formScheme.getKey()));
            }
        }
        catch (Exception e) {
            logger.error(task + "\u7efc\u5408\u5ba1\u6838\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
        }
        return vos;
    }

    @Override
    public List<MultcheckScheme> getSchemeListByTask(String task) {
        ArrayList<MultcheckScheme> schemes = new ArrayList<MultcheckScheme>();
        try {
            List formSchemes = this.runTimeAuthView.queryFormSchemeByTask(task);
            Map<String, List<MultcheckScheme>> schemeMap = this.schemeService.getSchemeByTask(task);
            for (FormSchemeDefine formScheme : formSchemes) {
                schemes.addAll((Collection<MultcheckScheme>)schemeMap.get(formScheme.getKey()));
            }
        }
        catch (Exception e) {
            logger.error(task + "\u7efc\u5408\u5ba1\u6838\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
        }
        return schemes;
    }

    @Override
    public MultcheckSchemeVO getSchemeByKey(String key) {
        List<MultcheckSchemeOrg> orgs;
        MultcheckScheme scheme = this.schemeService.getSchemeByKey(key);
        MultcheckSchemeVO vo = new MultcheckSchemeVO();
        BeanUtils.copyProperties(scheme, vo);
        try {
            TaskDefine task = this.runTimeAuthView.queryTaskDefine(scheme.getTask());
            FormSchemeDefine formScheme = this.runTimeAuthView.getFormScheme(scheme.getFormScheme());
            vo.setToPeriod(MultcheckUtil.buildPeriod(formScheme, task, this.periodEngineService, this.runTimeViewController));
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u65f6\u671f\u5f02\u5e38\uff1a\uff1a" + e.getMessage(), e);
        }
        if (OrgType.SELECT == scheme.getOrgType() && !CollectionUtils.isEmpty(orgs = this.schemeService.getOrgListByScheme(scheme.getKey()))) {
            vo.setOrgs(orgs.stream().map(MultcheckSchemeOrg::getOrg).collect(Collectors.toList()));
        }
        return vo;
    }

    private void repairOrg(MultcheckSchemeVO multcheckScheme) {
        switch (multcheckScheme.getOrgType()) {
            case SELECT: {
                multcheckScheme.setOrgFml(null);
                break;
            }
            case FORMULA: {
                multcheckScheme.setOrgs(null);
                break;
            }
            default: {
                multcheckScheme.setOrgFml(null);
                multcheckScheme.setOrgs(null);
            }
        }
    }

    private void schemeVerifyBeforeSave(MultcheckSchemeVO multcheckScheme) throws JQException {
        if (multcheckScheme == null) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_001);
        }
        if (!StringUtils.hasText(multcheckScheme.getTitle())) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_002);
        }
        if (!StringUtils.hasText(multcheckScheme.getCode())) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_003);
        }
        if (!StringUtils.hasText(multcheckScheme.getTask())) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_004);
        }
        if (!StringUtils.hasText(multcheckScheme.getFormScheme())) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_005);
        }
        if (multcheckScheme.getOrgType() == null) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_006);
        }
        if (multcheckScheme.getOrgType() == OrgType.SELECT && CollectionUtils.isEmpty(multcheckScheme.getOrgs())) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_007);
        }
        if (multcheckScheme.getOrgType() == OrgType.FORMULA && !StringUtils.hasText(multcheckScheme.getOrgFml())) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_008);
        }
        if (multcheckScheme.getType() == null) {
            throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_009);
        }
        if (StringUtils.hasText(multcheckScheme.getRule())) {
            CheckGroupingVO ruleGroup = this.ruleService.getRuleGroupByKey(multcheckScheme.getRule());
            if (ruleGroup == null) {
                throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_012);
            }
            if (ruleGroup.getIsLeaf()) {
                throw new JQException((ErrorEnum)MultcheckSchemeError.SCHEME_013);
            }
        }
    }

    @Override
    public ITree<TaskTreeNodeVO> buildRootNode(String taskKey, boolean filter) throws Exception {
        TaskTreeNodeVO root = new TaskTreeNodeVO();
        root.setKey("00000000000000000000000000000000");
        root.setCode("00000000000000000000000000000000");
        root.setTitle("\u5168\u90e8\u4efb\u52a1");
        root.setType("GROUP");
        ITree node = new ITree((INode)root);
        node.setExpanded(true);
        node.setLeaf(false);
        ArrayList<ITree<TaskTreeNodeVO>> children = new ArrayList<ITree<TaskTreeNodeVO>>();
        this.buildTaskTree(children, taskKey, filter);
        node.setChildren(children);
        return node;
    }

    @Override
    public List<TaskDefine> getAllTaskDefines() {
        return this.runTimeAuthView.getAllTaskDefines();
    }

    @Override
    public IMultcheckItemProvider getProvider(String type) {
        return this.collector.getProvider(type);
    }

    @Override
    public List<IMultcheckItemProvider> getProviderList() {
        return this.collector.getProviderList();
    }

    @Override
    public DimensionCollection buildDimensionCollection(String task, String period, List<String> orgs, Map<String, DimensionValue> dimSet) throws Exception {
        SchemePeriodLinkDefine scheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, task);
        String formSchemeKey = scheme.getSchemeKey();
        if (dimSet == null) {
            dimSet = new HashMap<String, DimensionValue>();
        }
        DimensionValue periodDimension = new DimensionValue();
        periodDimension.setName("DATATIME");
        periodDimension.setValue(period);
        dimSet.put("DATATIME", periodDimension);
        DimensionValue dwDimension = new DimensionValue();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        String org = null;
        if (DsContextHolder.getDsContext() != null) {
            org = DsContextHolder.getDsContext().getContextEntityId();
        }
        if (!StringUtils.hasText(org)) {
            org = taskDefine.getDw();
        }
        String dimensionName = this.entityMetaService.queryEntity(org).getDimensionName();
        dwDimension.setName(dimensionName);
        dwDimension.setValue(String.join((CharSequence)";", orgs));
        dimSet.put(dimensionName, dwDimension);
        return DimensionValueSetUtil.buildDimensionCollection(dimSet, (String)formSchemeKey);
    }

    @Override
    public MCHistoryScheme addHistoryScheme(MCHistoryScheme scheme) {
        scheme.setKey(UUID.randomUUID().toString());
        scheme.setUser(NpContextHolder.getContext().getUserName());
        scheme.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        this.historySchemeDao.add(scheme);
        return scheme;
    }

    @Override
    public String getHisSchemeConfigByKey(String key) {
        return this.historySchemeDao.getConfigByKey(key);
    }

    @Override
    public List<MCHistorySchemeVO> getHistorySchemeByUserSource(String source) throws Exception {
        ArrayList<MCHistorySchemeVO> res = new ArrayList<MCHistorySchemeVO>();
        MultcheckScheme scheme = this.schemeService.getSchemeByKey(source);
        TaskDefine taskDefine = this.runTimeAuthView.queryTaskDefine(scheme.getTask());
        IPeriodProvider provider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
        List<MCHistoryScheme> historySchemes = this.historySchemeDao.getByUserSource(NpContextHolder.getContext().getUserName(), source);
        for (MCHistoryScheme historyScheme : historySchemes) {
            MCHistorySchemeVO vo = new MCHistorySchemeVO();
            BeanUtils.copyProperties(historyScheme, vo);
            vo.setPeriodTitle(provider.getPeriodTitle(vo.getPeriod()));
            res.add(vo);
        }
        return res;
    }

    @Override
    public void delHistorySchemeByKey(String key) {
        this.historySchemeDao.deleteByKey(key);
    }

    @Override
    public void batchDeleteHistory(List<String> keys) {
        this.historySchemeDao.batchDeleteHistory(keys);
    }

    private void buildTaskTree(List<ITree<TaskTreeNodeVO>> children, String taskKey, boolean filter) throws Exception {
        ArrayList<TaskGroupDefine> allTaskGroup = new ArrayList<TaskGroupDefine>();
        List allTaskDefines = this.runTimeAuthView.getAllTaskDefines();
        this.getNoDimTasks(allTaskDefines);
        if (filter) {
            List<String> allTasks = this.schemeService.getAllTask();
            allTaskDefines = allTaskDefines.stream().filter(e -> allTasks.contains(e.getKey())).collect(Collectors.toList());
        }
        HashSet<String> groupSet = new HashSet<String>();
        for (TaskDefine taskDefine : allTaskDefines) {
            List groups = this.runTimeAuthView.getGroupByTask(taskDefine.getKey());
            for (TaskGroupDefine group : groups) {
                if (!groupSet.add(group.getKey())) continue;
                allTaskGroup.add(group);
            }
        }
        Map<String, TaskDefine> allTaskMap = allTaskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, task -> task));
        HashSet<String> tasksHasGroup = new HashSet<String>();
        this.buildChildTree(null, allTaskGroup, allTaskMap, children, tasksHasGroup, taskKey);
        for (TaskDefine task2 : allTaskDefines) {
            if (tasksHasGroup.contains(task2.getKey())) continue;
            children.add(this.convertTaskTreeNode(task2, taskKey));
        }
    }

    @Override
    public void getNoDimTasks(List<TaskDefine> allTaskDefines) {
        ArrayList<TaskDefine> noDimTask = new ArrayList<TaskDefine>();
        for (TaskDefine taskDefine : allTaskDefines) {
            try {
                if (StringUtils.hasText(taskDefine.getDims())) continue;
                noDimTask.add(taskDefine);
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u4efb\u52a1\u60c5\u666f\u5f02\u5e38code\uff1a" + taskDefine.getTaskCode(), e);
            }
        }
        allTaskDefines = noDimTask;
    }

    @Override
    public void exportResult(List<OrgExportVO> orgs, HttpServletResponse response) throws JQException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = MCEnvServiceImpl.buildsheet(workbook, "\u672a\u7ed1\u5b9a\u5ba1\u6838\u65b9\u6848\u7684\u5355\u4f4d", new String[]{"\u5355\u4f4d\u4ee3\u7801", "\u5355\u4f4d\u540d\u79f0"});
        for (OrgExportVO org : orgs) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(org.getCode());
            cell = row.createCell(1);
            cell.setCellValue(org.getTitle());
        }
        try {
            String fileName = "\u672a\u7ed1\u5b9a\u5ba1\u6838\u65b9\u6848\u7684\u5355\u4f4d.xls";
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            BufferedOutputStream outputStream = new BufferedOutputStream((OutputStream)response.getOutputStream());
            response.setContentType("application/octet-stream");
            workbook.write(outputStream);
            ((OutputStream)outputStream).flush();
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static Sheet buildsheet(Workbook workbook, String sheetName, String[] headers) {
        Sheet sheet = workbook.createSheet(sheetName);
        Row head = sheet.createRow(0);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("\u9ed1\u4f53");
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setFont(font);
        for (int i = 0; i < headers.length; ++i) {
            Cell cell = head.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headStyle);
            sheet.setColumnWidth(i, 6000);
        }
        return sheet;
    }

    private ITree<TaskTreeNodeVO> convertTaskTreeNode(TaskDefine task, String taskKey) throws Exception {
        TaskTreeNodeVO r = new TaskTreeNodeVO();
        r.setKey(task.getKey());
        r.setCode(task.getTaskCode());
        r.setTitle(task.getTitle());
        r.setType("TASK");
        r.setDataScheme(task.getDataScheme());
        ArrayList<FormSchemeVO> vos = new ArrayList<FormSchemeVO>();
        List formSchemes = this.runTimeAuthView.queryFormSchemeByTask(task.getKey());
        if (!CollectionUtils.isEmpty(formSchemes)) {
            for (FormSchemeDefine formScheme : formSchemes) {
                FormSchemeVO vo = new FormSchemeVO();
                vo.setKey(formScheme.getKey());
                vo.setTitle(formScheme.getTitle());
                vo.setDw(this.getDw(formScheme, task));
                vo.setToPeriod(MultcheckUtil.buildPeriod(formScheme, task, this.periodEngineService, this.runTimeViewController));
                vos.add(vo);
            }
        }
        r.setFormSchemes(vos);
        ArrayList<MCLabel> orgLinks = new ArrayList<MCLabel>();
        List orgLinkDefs = this.dRunTime.listTaskOrgLinkByTask(task.getKey());
        for (TaskOrgLinkDefine orgLinkDef : orgLinkDefs) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(orgLinkDef.getEntity());
            if (entityDefine == null) continue;
            MCLabel vo = new MCLabel();
            vo.setCode(entityDefine.getId());
            vo.setTitle(entityDefine.getTitle());
            orgLinks.add(vo);
        }
        r.setOrgLinks(orgLinks);
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{"#icon-16_SHU_A_NR_shujufangan"});
        node.setLeaf(true);
        node.setSelected(task.getKey().equals(taskKey));
        return node;
    }

    private String getDw(FormSchemeDefine formScheme, TaskDefine task) {
        try {
            return formScheme.getDw();
        }
        catch (Exception e) {
            e.printStackTrace();
            return task.getDw();
        }
    }

    private void buildChildTree(String parentId, List<TaskGroupDefine> allTaskGroup, Map<String, TaskDefine> allTaskMap, List<ITree<TaskTreeNodeVO>> children, Set<String> tasksHasGroup, String taskKey) throws Exception {
        List taskDefines;
        for (TaskGroupDefine group : allTaskGroup) {
            if (!StringHelper.safeEquals((String)parentId, (String)group.getParentKey())) continue;
            ITree<TaskTreeNodeVO> node = this.convertGroupTreeNode(group);
            children.add(node);
            ArrayList<ITree<TaskTreeNodeVO>> nodeChildren = new ArrayList<ITree<TaskTreeNodeVO>>();
            this.buildChildTree(group.getKey(), allTaskGroup, allTaskMap, nodeChildren, tasksHasGroup, taskKey);
            node.setChildren(nodeChildren);
        }
        boolean isRoot = !StringUtils.hasText(parentId);
        List taskList = null;
        if (!isRoot && !CollectionUtils.isEmpty(taskDefines = this.runTimeAuthView.getTaskDefinesFromGroup(parentId))) {
            taskList = taskDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(taskList)) {
            tasksHasGroup.addAll(taskList);
            for (String key : taskList) {
                TaskDefine task = allTaskMap.get(key);
                if (task == null) continue;
                children.add(this.convertTaskTreeNode(task, taskKey));
            }
        }
    }

    private ITree<TaskTreeNodeVO> convertGroupTreeNode(TaskGroupDefine group) {
        TaskTreeNodeVO r = new TaskTreeNodeVO();
        r.setKey(group.getKey());
        r.setCode(group.getCode());
        r.setTitle(group.getTitle());
        r.setType("GROUP");
        ITree node = new ITree((INode)r);
        node.setIcons(new String[]{"#icon-16_SHU_A_NR_fenzu"});
        node.setLeaf(true);
        return node;
    }
}

