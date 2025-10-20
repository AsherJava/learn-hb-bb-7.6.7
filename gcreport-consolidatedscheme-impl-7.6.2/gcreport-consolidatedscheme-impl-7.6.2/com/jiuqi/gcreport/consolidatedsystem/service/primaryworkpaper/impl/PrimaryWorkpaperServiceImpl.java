/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.FormSubjectVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.openxml4j.util.ZipSecureFile
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.impl;

import com.google.common.collect.Lists;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.PrimaryWorkpaperDao;
import com.jiuqi.gcreport.consolidatedsystem.dao.primaryworkpaper.PrimaryWorkpaperSettingDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperSettingEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.primaryworkpaper.PrimaryWorkPaperTypeEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService;
import com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.utils.PrimaryWorkpaperTempCache;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.FormSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PrimaryWorkpaperServiceImpl
implements PrimaryWorkpaperService {
    public static final Logger logger = LoggerFactory.getLogger(PrimaryWorkpaperServiceImpl.class);
    private static PrimaryWorkpaperTypeVO rootNode = null;
    @Autowired
    private PrimaryWorkpaperDao primaryWorkpaperDao;
    @Autowired
    private PrimaryWorkpaperSettingDao primaryWorkpaperSettingDao;
    @Autowired
    private ConsolidatedSubjectService subjectService;
    @Autowired
    private ConsolidatedTaskService taskService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public PrimaryWorkpaperTypeVO addPrimaryWorkpaperType(PrimaryWorkpaperTypeVO primaryWorkpaperTypeVO) {
        PrimaryWorkPaperTypeEO eo = new PrimaryWorkPaperTypeEO();
        BeanUtils.copyProperties(primaryWorkpaperTypeVO, (Object)eo);
        eo.setId(UUIDOrderUtils.newUUIDStr());
        Integer maxSortOrder = this.primaryWorkpaperDao.findMaxSortOrder();
        eo.setSortOrder(maxSortOrder + 1);
        this.primaryWorkpaperDao.save(eo);
        return this.convertEOToVO(eo);
    }

    @Override
    public List<PrimaryWorkpaperTypeVO> listPrimaryWorkpaperTypesBySystemId(String reportSystemId) {
        List<PrimaryWorkPaperTypeEO> typeEOList = this.primaryWorkpaperDao.listTypesByReportSystem(reportSystemId);
        return this.listPrimaryTypeVOList(typeEOList);
    }

    @Override
    public List<PrimaryWorkpaperTypeVO> listPrimaryWorkpaperTypeTree(String reportSystemId) {
        List<PrimaryWorkPaperTypeEO> typeEOList = this.primaryWorkpaperDao.listTypesByReportSystem(reportSystemId);
        PrimaryWorkpaperTypeVO root = this.getRootNode(reportSystemId);
        root.setChildren(this.listPrimaryTypeVOList(typeEOList));
        return Lists.newArrayList((Object[])new PrimaryWorkpaperTypeVO[]{root});
    }

    private List<PrimaryWorkpaperTypeVO> listPrimaryTypeVOList(List<PrimaryWorkPaperTypeEO> typeEOList) {
        ArrayList<PrimaryWorkpaperTypeVO> typeVOList = new ArrayList<PrimaryWorkpaperTypeVO>();
        if (CollectionUtils.isEmpty(typeEOList)) {
            return typeVOList;
        }
        for (PrimaryWorkPaperTypeEO eo : typeEOList) {
            typeVOList.add(this.convertEOToVO(eo));
        }
        return typeVOList;
    }

    private PrimaryWorkpaperTypeVO convertEOToVO(PrimaryWorkPaperTypeEO eo) {
        PrimaryWorkpaperTypeVO vo = new PrimaryWorkpaperTypeVO();
        BeanUtils.copyProperties((Object)eo, vo);
        vo.setParentId(UUIDUtils.emptyUUIDStr());
        vo.setLeafFlag(Boolean.valueOf(true));
        vo.setDataType("leaf");
        return vo;
    }

    private PrimaryWorkpaperTypeVO getRootNode(String reportSystemId) {
        if (rootNode == null) {
            PrimaryWorkpaperTypeVO root = new PrimaryWorkpaperTypeVO();
            root.setParentId(null);
            root.setId(UUIDUtils.emptyUUIDStr());
            root.setTitle("\u4e3b\u8868\u578b\u5de5\u4f5c\u5e95\u7a3f");
            root.setReportSystem(reportSystemId);
            root.setSortOrder(Integer.valueOf(0));
            root.setLeafFlag(Boolean.valueOf(false));
            root.setDataType("root");
            rootNode = root;
            return rootNode;
        }
        return rootNode;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deletePrimaryWorkpaperTypeById(String id) {
        PrimaryWorkPaperTypeEO eo = (PrimaryWorkPaperTypeEO)this.primaryWorkpaperDao.get((Serializable)((Object)id));
        List<PrimaryWorkPaperSettingEO> settingEOS = this.primaryWorkpaperSettingDao.querySetRecordsByTypeId(id);
        this.primaryWorkpaperSettingDao.deleteBatch(settingEOS);
        this.primaryWorkpaperDao.delete((BaseEntity)eo);
    }

    @Override
    public PrimaryWorkpaperTypeVO updatePrimaryWorkpaperType(PrimaryWorkpaperTypeVO primaryWorkpaperTypeVO) {
        PrimaryWorkPaperTypeEO eo = this.convertVOToEO(primaryWorkpaperTypeVO);
        this.primaryWorkpaperDao.update((BaseEntity)eo);
        return primaryWorkpaperTypeVO;
    }

    private PrimaryWorkPaperTypeEO convertVOToEO(PrimaryWorkpaperTypeVO vo) {
        PrimaryWorkPaperTypeEO eo = new PrimaryWorkPaperTypeEO();
        BeanUtils.copyProperties(vo, (Object)eo);
        return eo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String moveTypeTreeNode(String id, Integer step) {
        if (Objects.isNull(id) || Objects.isNull(step)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (step == 0) {
            throw new BusinessRuntimeException("\u53c2\u6570\u9519\u8bef\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        PrimaryWorkPaperTypeEO currNodeEO = (PrimaryWorkPaperTypeEO)this.primaryWorkpaperDao.get((Serializable)((Object)id));
        if (Objects.isNull((Object)currNodeEO)) {
            logger.info("\u79fb\u52a8\u8282\u70b9\uff0c\u6570\u636e\u5f02\u5e38\uff0c\u89c4\u5219\uff1a[{}] \u4e0d\u5b58\u5728", (Object)id);
            throw new BusinessRuntimeException("\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        PrimaryWorkPaperTypeEO node = step > 0 ? this.primaryWorkpaperDao.findPreNodeBySystemIdAndOrder(currNodeEO.getReportSystem(), currNodeEO.getSortOrder()) : this.primaryWorkpaperDao.findNextNodeBySystemIdAndOrder(currNodeEO.getReportSystem(), currNodeEO.getSortOrder());
        if (Objects.isNull((Object)node)) {
            throw new BusinessRuntimeException("\u5df2\u5230\u8fbe\u6307\u5b9a\u4f4d\u7f6e");
        }
        Integer temp = currNodeEO.getSortOrder();
        currNodeEO.setSortOrder(node.getSortOrder());
        node.setSortOrder(temp);
        this.primaryWorkpaperDao.update((BaseEntity)currNodeEO);
        this.primaryWorkpaperDao.update((BaseEntity)node);
        return node.getId();
    }

    @Override
    public List<PrimaryWorkpaperSettingVO> listPrimarySettingDatas(String typeId) {
        List<PrimaryWorkPaperSettingEO> eos = this.primaryWorkpaperSettingDao.querySetRecordsByTypeId(typeId);
        ArrayList<PrimaryWorkpaperSettingVO> vos = new ArrayList<PrimaryWorkpaperSettingVO>();
        if (CollectionUtils.isEmpty(eos)) {
            return vos;
        }
        String systemId = eos.get(0).getReportSystem();
        for (PrimaryWorkPaperSettingEO settingEO : eos) {
            PrimaryWorkpaperSettingVO vo = new PrimaryWorkpaperSettingVO();
            BeanUtils.copyProperties((Object)settingEO, vo);
            if (!StringUtils.isEmpty((String)settingEO.getBoundZbJson())) {
                FormSubjectVO formSubjectVO = (FormSubjectVO)JsonUtils.readValue((String)settingEO.getBoundZbJson(), FormSubjectVO.class);
                vo.setFormSubject(formSubjectVO);
                vo.setBoundZbName(formSubjectVO.getTableName() + "[" + formSubjectVO.getCode() + "]");
            }
            String subjectCodes = settingEO.getBoundSubjectCodes();
            vo.setBoundSubjects(this.getBoundSubjects(systemId, subjectCodes));
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public List<PrimaryWorkPaperSettingEO> listSetRecordsBySystemId(String systemId) {
        return this.primaryWorkpaperSettingDao.listSetRecordsBySystemId(systemId);
    }

    private List<ConsolidatedSubjectVO> getBoundSubjects(String systemId, String subjectCodes) {
        String[] subjectCodeArr = subjectCodes.split(";");
        ArrayList<ConsolidatedSubjectVO> subjects = new ArrayList<ConsolidatedSubjectVO>();
        for (String subjectCode : subjectCodeArr) {
            ConsolidatedSubjectEO subjectVO = this.subjectService.getSubjectByCode(systemId, subjectCode);
            if (subjectVO == null) continue;
            subjects.add(SubjectConvertUtil.convertEO2VO(subjectVO));
        }
        return subjects;
    }

    @Override
    public Map<String, List<ConsolidatedSubjectVO>> getZbCodeToSubjectsMap(String systemId) {
        List<ConsolidatedSubjectEO> allSubjects = this.subjectService.listAllSubjectsBySystemId(systemId);
        HashMap<String, List<ConsolidatedSubjectVO>> zbCodeToSubjectsMap = new HashMap<String, List<ConsolidatedSubjectVO>>();
        for (ConsolidatedSubjectEO subject : allSubjects) {
            String boundIndexPath = subject.getBoundIndexPath();
            if (StringUtils.isEmpty((String)boundIndexPath)) continue;
            if (!zbCodeToSubjectsMap.containsKey(boundIndexPath)) {
                ArrayList subjectVOList = new ArrayList();
                zbCodeToSubjectsMap.put(boundIndexPath, subjectVOList);
            }
            ((List)zbCodeToSubjectsMap.get(boundIndexPath)).add(SubjectConvertUtil.convertEO2VO(subject));
        }
        return zbCodeToSubjectsMap;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void savePrimaryWorkpaperSets(List<PrimaryWorkpaperSettingVO> primaryWorkpaperSets) {
        ArrayList<PrimaryWorkPaperSettingEO> insertEOS = new ArrayList<PrimaryWorkPaperSettingEO>();
        ArrayList<PrimaryWorkPaperSettingEO> updateEOS = new ArrayList<PrimaryWorkPaperSettingEO>();
        for (PrimaryWorkpaperSettingVO vo : primaryWorkpaperSets) {
            PrimaryWorkPaperSettingEO eo = this.convertSetVOToEO(vo);
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDOrderUtils.newUUIDStr());
                eo.setOrdinal(new Double(OrderGenerator.newOrderID()));
                insertEOS.add(eo);
                continue;
            }
            eo.setOrdinal(new Double(OrderGenerator.newOrderID()));
            updateEOS.add(eo);
        }
        this.primaryWorkpaperSettingDao.addBatch(insertEOS);
        this.primaryWorkpaperSettingDao.updateBatch(updateEOS);
    }

    private PrimaryWorkPaperSettingEO convertSetVOToEO(PrimaryWorkpaperSettingVO vo) {
        PrimaryWorkPaperSettingEO eo = new PrimaryWorkPaperSettingEO();
        BeanUtils.copyProperties(vo, (Object)eo);
        eo.setBoundZbJson(vo.getBoundZbTitle());
        List subjects = vo.getBoundSubjects();
        subjects.removeAll(Collections.singleton(null));
        String subjectCodes = subjects.stream().map(ConsolidatedSubjectVO::getCode).collect(Collectors.joining(";"));
        eo.setBoundSubjectCodes(subjectCodes);
        eo.setBoundZbJson(vo.getFormSubject() == null ? "" : JsonUtils.writeValueAsString((Object)vo.getFormSubject()));
        return eo;
    }

    @Override
    public void deletePrimaryWorkpaperSetsByIds(List<String> setIds) {
        this.primaryWorkpaperSettingDao.batchDeleteByIds(setIds);
    }

    @Override
    public ExportExcelSheet exportSet(String selectNodeId, Map<String, CellStyle> cellStyleMap) {
        List<PrimaryWorkpaperSettingVO> settingList = this.listPrimarySettingDatas(selectNodeId);
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u4e3b\u8868\u578b\u5de5\u4f5c\u5e95\u7a3f\u8bbe\u7f6e", Integer.valueOf(1));
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        Object[] header = new Object[]{"\u5e8f\u53f7", "\u62a5\u8868\u9879\u76ee", "\u5173\u8054\u6307\u6807", "\u501f\u8d37\u65b9\u5411", "\u4ee3\u7801", "\u5173\u8054\u62b5\u9500\u79d1\u76ee"};
        rowDatas.add(header);
        for (int i = 0; i < settingList.size(); ++i) {
            PrimaryWorkpaperSettingVO vo = settingList.get(i);
            String orient = vo.getOrient() == 1 ? "\u501f" : "\u8d37";
            List boundSubjects = vo.getBoundSubjects();
            String subjectTitles = boundSubjects.stream().map(subject -> subject.getCode() + "|" + subject.getTitle()).collect(Collectors.joining(";"));
            Object[] row = new Object[]{i + 1, vo.getBoundZbTitle(), vo.getBoundZbName(), orient, vo.getSubjectCode(), subjectTitles};
            rowDatas.add(row);
        }
        exportExcelSheet.getRowDatas().addAll(rowDatas);
        return exportExcelSheet;
    }

    @Override
    public Object importData(MultipartFile importFile, Map<String, String> queryParamsVO) {
        StringBuffer log = new StringBuffer(512);
        try {
            this.parseSetDatas(importFile, queryParamsVO, log);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException("\u5bfc\u5165\u4e3b\u8868\u5de5\u4f5c\u5e95\u7a3f\u8bbe\u7f6e\u6570\u636e\u5931\u8d25\uff0c\u8be6\u60c5\uff1a" + e.getMessage(), (Throwable)e);
        }
        return log;
    }

    private void parseSetDatas(MultipartFile importFile, Map<String, String> queryParamsVO, StringBuffer log) {
        String reportSystem = queryParamsVO.get("reportSystem");
        String primaryTypeId = queryParamsVO.get("primaryTypeId");
        if (StringUtils.isEmpty((String)reportSystem) || StringUtils.isEmpty((String)primaryTypeId)) {
            throw new BusinessRuntimeException("\u5408\u5e76\u4f53\u7cfb\u6216\u4e3b\u8868\u7c7b\u578b\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u9009\u62e9");
        }
        Workbook workbook = this.createWorkBook(importFile);
        PrimaryWorkpaperTempCache primaryWorkpaperTempCache = this.initPrimaryWorkpaperTempCache(reportSystem);
        this.importPrimaryWorkpaperSettingDatas(log, reportSystem, primaryTypeId, workbook, primaryWorkpaperTempCache);
    }

    private void importPrimaryWorkpaperSettingDatas(StringBuffer log, String reportSystem, String primaryTypeId, Workbook workbook, PrimaryWorkpaperTempCache primaryWorkpaperTempCache) {
        int failCount = 0;
        String[] importCvsColumns = null;
        Sheet sheet = workbook.getSheetAt(0);
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        for (int excelRowNum = 0; excelRowNum < physicalNumberOfRows; ++excelRowNum) {
            Row row = sheet.getRow(excelRowNum);
            if (excelRowNum == 0) {
                importCvsColumns = PrimaryWorkpaperServiceImpl.parseTitle(row);
                continue;
            }
            PrimaryWorkPaperSettingEO settingEO = new PrimaryWorkPaperSettingEO();
            settingEO.setReportSystem(reportSystem);
            settingEO.setPrimaryTypeId(primaryTypeId);
            String failReason = this.parseOneRow(row, settingEO, importCvsColumns, primaryWorkpaperTempCache, excelRowNum, log);
            if (null == failReason) {
                try {
                    this.primaryWorkpaperSettingDao.add((BaseEntity)settingEO);
                }
                catch (Exception e) {
                    failReason = e.getMessage();
                }
            }
            if (null == failReason) continue;
            ++failCount;
            log.append("excel\u884c\u53f7\u7b2c").append(excelRowNum).append("\u884c\u5bfc\u5165\u5931\u8d25\uff1a").append(failReason).append("\r\n");
        }
        log.append(sheet.getSheetName()).append("\u5bfc\u5165\u5b8c\u6210\uff1a\u603b\u5171").append(physicalNumberOfRows - 1).append("\u884c,\u6210\u529f").append(physicalNumberOfRows - failCount - 1).append("\u884c\uff0c\u5931\u8d25").append(failCount).append("\u884c\r\n");
    }

    private Workbook createWorkBook(MultipartFile importFile) {
        XSSFWorkbook workbook;
        try {
            try {
                ZipSecureFile.setMinInflateRatio((double)-1.0);
                workbook = new XSSFWorkbook(importFile.getInputStream());
            }
            catch (Exception ex) {
                workbook = new HSSFWorkbook(importFile.getInputStream());
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("Excel\u89e3\u6790\u5931\u8d25", (Throwable)e);
        }
        return workbook;
    }

    private PrimaryWorkpaperTempCache initPrimaryWorkpaperTempCache(String reportSystem) {
        PrimaryWorkpaperTempCache primaryWorkpaperTempCache = new PrimaryWorkpaperTempCache();
        List<ConsolidatedSubjectEO> allSubject = this.subjectService.listAllSubjectsBySystemId(reportSystem);
        Map<String, String> subjectTitle2CodeMap = allSubject.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getTitle, ConsolidatedSubjectEO::getCode, (e1, e2) -> e1));
        primaryWorkpaperTempCache.setSubjectTitle2CodeMap(subjectTitle2CodeMap);
        primaryWorkpaperTempCache.setSubjectCodeSet(allSubject.stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet()));
        List<ConsolidatedTaskVO> taskVOS = this.taskService.getConsolidatedTasks(reportSystem);
        ArrayList<String> schemeIds = new ArrayList<String>();
        HashMap<String, String> schemeId2TaskIdMap = new HashMap<String, String>();
        for (ConsolidatedTaskVO taskVO : taskVOS) {
            List<String> allInputSchemeList = ConsolidatedSystemUtils.listAllInputSchemeByConTaskVO(taskVO);
            List<String> allManageSchemeList = ConsolidatedSystemUtils.listAllManageSchemeByConTaskVO(taskVO);
            schemeIds.addAll(allInputSchemeList);
            schemeIds.addAll(allManageSchemeList);
            schemeId2TaskIdMap.putAll(allInputSchemeList.stream().collect(Collectors.toMap(schemeId -> schemeId, schemeId -> taskVO.getTaskKey())));
            schemeId2TaskIdMap.putAll(allManageSchemeList.stream().collect(Collectors.toMap(schemeId -> schemeId, schemeId -> this.runTimeViewController.getFormScheme(schemeId).getTaskKey())));
        }
        primaryWorkpaperTempCache.setSchemeId2TaskIdMap(schemeId2TaskIdMap);
        HashMap<String, String> zbId2SchemeId = new HashMap<String, String>();
        HashMap<String, DataLinkDefine> zbId2DataLinkDefineMap = new HashMap<String, DataLinkDefine>();
        for (String schemeId2 : schemeIds) {
            List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(schemeId2);
            if (CollectionUtils.isEmpty((Collection)formDefines)) continue;
            formDefines.stream().filter(Objects::nonNull).forEach(formDefine -> {
                List dataLinkDefines = this.runTimeViewController.getAllLinksInForm(formDefine.getKey());
                zbId2SchemeId.putAll(dataLinkDefines.stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, scheme -> schemeId2, (e1, e2) -> e1)));
                zbId2DataLinkDefineMap.putAll(dataLinkDefines.stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, dataLinkDefine -> dataLinkDefine, (e1, e2) -> e1)));
            });
        }
        primaryWorkpaperTempCache.setZbId2SchemeIdMap(zbId2SchemeId);
        primaryWorkpaperTempCache.setZbId2DataLinkDefineMap(zbId2DataLinkDefineMap);
        return primaryWorkpaperTempCache;
    }

    private FormSubjectVO getFormSubjectVo(PrimaryWorkpaperTempCache primaryWorkpaperTempCache, String tableZbCode) throws Exception {
        DataLinkDefine dataLinkDefine;
        String schemeId;
        String regex = "(.+)\\[(.+)\\]";
        if (!tableZbCode.matches(regex)) {
            throw new BusinessRuntimeException("\u6307\u6807\u683c\u5f0f\u4e0d\u6b63\u786e\uff1a" + tableZbCode + "\u3002\u53c2\u8003\u683c\u5f0f\uff1a\u8868\u540d[\u6307\u6807\u4ee3\u7801]");
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tableZbCode);
        String tableCode = "";
        String zbCode = "";
        if (matcher.find()) {
            tableCode = matcher.group(1).trim();
            zbCode = matcher.group(2).trim();
        }
        DataTable tabldefine = this.runtimeDataSchemeService.getDataTableByCode(tableCode);
        DataField fieldDefine = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(tabldefine.getKey(), zbCode);
        FormSubjectVO formSubjectVO = new FormSubjectVO();
        formSubjectVO.setCode(fieldDefine.getCode());
        formSubjectVO.setFieldCode(fieldDefine.getKey());
        formSubjectVO.setFieldType(fieldDefine.getDataFieldType().toString());
        formSubjectVO.setFieldTitle(fieldDefine.getTitle());
        formSubjectVO.setGatherType(fieldDefine.getDataFieldGatherType().toString());
        formSubjectVO.setHidden(null);
        formSubjectVO.setSelectByField(null);
        formSubjectVO.setTableName(tableCode);
        String string = schemeId = primaryWorkpaperTempCache.getZbId2SchemeIdMap() == null ? null : primaryWorkpaperTempCache.getZbId2SchemeIdMap().get(fieldDefine.getKey());
        if (!StringUtils.isEmpty(schemeId)) {
            formSubjectVO.setFormSchemeId(schemeId);
            formSubjectVO.setTaskId(primaryWorkpaperTempCache.getSchemeId2TaskIdMap().get(schemeId));
        }
        DataLinkDefine dataLinkDefine2 = dataLinkDefine = primaryWorkpaperTempCache.getZbId2DataLinkDefineMap() == null ? null : primaryWorkpaperTempCache.getZbId2DataLinkDefineMap().get(fieldDefine.getKey());
        if (dataLinkDefine != null) {
            formSubjectVO.setLinkKey(dataLinkDefine.getKey());
            formSubjectVO.setRegionkey(dataLinkDefine.getRegionKey());
        }
        return formSubjectVO;
    }

    private String parseOneRow(Row row, PrimaryWorkPaperSettingEO settingEO, String[] importCvsColumns, PrimaryWorkpaperTempCache primaryWorkpaperTempCache, int excelRowNum, StringBuffer log) {
        int physicalNumberOfCells = row.getLastCellNum();
        settingEO.setId(UUIDOrderUtils.newUUIDStr());
        settingEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
        settingEO.setEdit(1);
        block18: for (int k = 0; k <= physicalNumberOfCells; ++k) {
            Cell cell;
            if (k >= importCvsColumns.length || (cell = row.getCell(k)) == null) continue;
            switch (importCvsColumns[k]) {
                case "\u5e8f\u53f7": {
                    continue block18;
                }
                case "\u62a5\u8868\u9879\u76ee": {
                    String boundZbTitle = cell.getStringCellValue();
                    if (StringUtils.isEmpty((String)boundZbTitle)) {
                        return "\u62a5\u8868\u9879\u76ee\u4e0d\u80fd\u4e3a\u7a7a";
                    }
                    settingEO.setBoundZbTitle(boundZbTitle);
                    continue block18;
                }
                case "\u5173\u8054\u6307\u6807": {
                    String zbCode = cell.getStringCellValue();
                    if (StringUtils.isEmpty((String)zbCode)) continue block18;
                    try {
                        FormSubjectVO formSubjectVO = this.getFormSubjectVo(primaryWorkpaperTempCache, zbCode);
                        settingEO.setBoundZbId(formSubjectVO.getFieldCode());
                        settingEO.setTaskId(formSubjectVO.getTaskId());
                        settingEO.setSchemeId(formSubjectVO.getFormSchemeId());
                        settingEO.setBoundZbJson(JsonUtils.writeValueAsString((Object)formSubjectVO));
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    continue block18;
                }
                case "\u501f\u8d37\u65b9\u5411": {
                    String orientTitle = cell.getStringCellValue();
                    if (StringUtils.isEmpty((String)orientTitle)) {
                        return "\u501f\u8d37\u65b9\u5411\u4e0d\u80fd\u4e3a\u7a7a";
                    }
                    if (!"\u501f".equals(orientTitle) && !"\u8d37".equals(orientTitle)) {
                        return "\u501f\u8d37\u65b9\u5411\u53ea\u80fd\u4e3a\u501f\u6216\u8005\u8d37";
                    }
                    Integer orient = "\u501f".equals(orientTitle) ? 1 : -1;
                    settingEO.setOrient(orient);
                    continue block18;
                }
                case "\u4ee3\u7801": {
                    CellType cellType = cell.getCellType();
                    String cellValue = cellType == CellType.NUMERIC ? String.valueOf((int)cell.getNumericCellValue()) : cell.getStringCellValue();
                    if (StringUtils.isEmpty((String)cellValue)) {
                        return "\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a";
                    }
                    settingEO.setSubjectCode(cellValue);
                    continue block18;
                }
                case "\u5173\u8054\u62b5\u9500\u79d1\u76ee": {
                    CellType cellType = cell.getCellType();
                    String cellValue = cellType == CellType.NUMERIC ? String.valueOf((int)cell.getNumericCellValue()) : cell.getStringCellValue();
                    if (StringUtils.isEmpty((String)cellValue)) {
                        return "\u5173\u8054\u62b5\u9500\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a";
                    }
                    List<String> subjects = Arrays.asList(cellValue.split(";"));
                    subjects = subjects.stream().filter(subject -> !StringUtils.isEmpty((String)subject)).collect(Collectors.toList());
                    Set<String> allSubjectCodeSet = primaryWorkpaperTempCache.getSubjectCodeSet();
                    Map<String, String> subjectTitle2CodeMap = primaryWorkpaperTempCache.getSubjectTitle2CodeMap();
                    StringBuilder noContainsSubjectCodes = new StringBuilder();
                    StringBuilder subjectCodes = new StringBuilder();
                    for (String subject2 : subjects) {
                        String subjectValue = subject2.split("\\|")[0];
                        if (subjectTitle2CodeMap.containsKey(subjectValue)) {
                            subjectCodes.append(subjectTitle2CodeMap.get(subjectValue)).append(";");
                            continue;
                        }
                        if (allSubjectCodeSet.contains(subjectValue)) {
                            subjectCodes.append(subjectValue).append(";");
                            continue;
                        }
                        noContainsSubjectCodes.append(subject2).append(";");
                    }
                    if (StringUtils.isEmpty((String)subjectCodes.toString())) {
                        return "\u79d1\u76ee\u4ee3\u7801" + noContainsSubjectCodes + "\u5728\u5f53\u524d\u4f53\u7cfb\u4e0d\u5b58\u5728";
                    }
                    settingEO.setBoundSubjectCodes(subjectCodes.toString());
                    if (StringUtils.isEmpty((String)noContainsSubjectCodes.toString())) continue block18;
                    log.append("excel\u884c\u53f7\u7b2c").append(excelRowNum).append("\u884c\u79d1\u76ee").append((CharSequence)noContainsSubjectCodes).append("\u5728\u5f53\u524d\u4f53\u7cfb\u4e0d\u5b58\u5728").append("\r\n");
                    continue block18;
                }
            }
        }
        return null;
    }

    protected static String[] parseTitle(Row row) {
        int physicalNumberOfCells = row.getLastCellNum();
        String[] importCvsColumns = new String[physicalNumberOfCells];
        for (int k = 0; k < physicalNumberOfCells; ++k) {
            Cell cell = row.getCell(k);
            importCvsColumns[k] = cell.getStringCellValue();
        }
        return importCvsColumns;
    }
}

