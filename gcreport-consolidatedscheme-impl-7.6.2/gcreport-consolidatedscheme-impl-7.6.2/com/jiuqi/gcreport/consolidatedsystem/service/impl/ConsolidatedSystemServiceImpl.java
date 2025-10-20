/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.api.option.ConsolidatedOptionClient
 *  com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedTaskChangedEvent
 *  com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedTaskChangedEvent$ConsolidatedTaskChangedInfo
 *  com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.api.option.ConsolidatedOptionClient;
import com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.event.ConsolidatedTaskChangedEvent;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsolidatedSystemServiceImpl
implements ConsolidatedSystemService {
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private ConsolidatedOptionService consolidatedOptionService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ConsolidatedOptionClient optionClient;
    @Autowired
    private ConsolidatedSubjectUIService subjectUIService;
    private Logger logger = LoggerFactory.getLogger(ConsolidatedSystemServiceImpl.class);

    @Override
    public List<ConsolidatedSystemEO> getConsolidatedSystemEOS() {
        List<ConsolidatedSystemEO> eos = this.consolidatedSystemDao.findAllSystemsWithOrder();
        return eos;
    }

    @Override
    public ConsolidatedSystemEO getConsolidatedSystemEO(String id) {
        ConsolidatedSystemEO eo = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)id));
        assert (eo != null) : "\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5408\u5e76\u4f53\u7cfb\uff01";
        return eo;
    }

    @Override
    public List<ConsolidatedSystemVO> getConsolidatedSystemVOS(Integer year) {
        List<ConsolidatedSystemVO> consolidatedSystemVOList = this.getConsolidatedSystemEOS().stream().map(eo -> this.convertEO2VO((ConsolidatedSystemEO)((Object)eo))).collect(Collectors.toList());
        if (year == null) {
            return consolidatedSystemVOList;
        }
        ArrayList<ConsolidatedSystemVO> newConsolidatedSystemVOList = new ArrayList<ConsolidatedSystemVO>();
        for (ConsolidatedSystemVO systemVO : consolidatedSystemVOList) {
            List<ConsolidatedTaskVO> consolidatedTaskVOS = this.consolidatedTaskService.getConsolidatedTasks(systemVO.getId());
            Boolean moreFromYear = true;
            Boolean lessToYear = true;
            Boolean removeSystemFlag = true;
            for (ConsolidatedTaskVO consolidatedTaskVO : consolidatedTaskVOS) {
                if (!StringUtils.isEmpty((String)consolidatedTaskVO.getFromPeriod())) {
                    Integer fromYear = Integer.valueOf(consolidatedTaskVO.getFromPeriod().substring(0, 4));
                    if (year < fromYear) {
                        moreFromYear = false;
                        continue;
                    }
                }
                if (!StringUtils.isEmpty((String)consolidatedTaskVO.getToPeriod())) {
                    Integer toYear = Integer.valueOf(consolidatedTaskVO.getToPeriod().substring(0, 4));
                    if (year > toYear) {
                        lessToYear = false;
                        continue;
                    }
                }
                if (removeSystemFlag.booleanValue() && moreFromYear.booleanValue() && lessToYear.booleanValue()) {
                    removeSystemFlag = false;
                    break;
                }
                moreFromYear = true;
                lessToYear = true;
            }
            if (removeSystemFlag.booleanValue()) continue;
            newConsolidatedSystemVOList.add(systemVO);
        }
        return consolidatedSystemVOList;
    }

    @Override
    public List<ConsolidatedSystemVO> getConsolidatedSystemVOSByTaksId(String taskId) {
        List consolidatedSystemVOList = this.getConsolidatedSystemEOS().stream().map(eo -> this.convertEO2VO((ConsolidatedSystemEO)((Object)eo))).collect(Collectors.toList());
        List<ConsolidatedTaskVO> consolidatedTaskVOS = this.consolidatedTaskService.getConsolidatedTaskByTaskId(taskId);
        ArrayList<ConsolidatedSystemVO> newConsolidatedSystemVOList = new ArrayList<ConsolidatedSystemVO>();
        block0: for (ConsolidatedTaskVO consolidatedTaskVO : consolidatedTaskVOS) {
            for (ConsolidatedSystemVO systemVO : consolidatedSystemVOList) {
                if (!systemVO.getDataSchemeKey().equals(consolidatedTaskVO.getDataScheme())) continue;
                newConsolidatedSystemVOList.add(systemVO);
                continue block0;
            }
        }
        return newConsolidatedSystemVOList;
    }

    @Override
    public ConsolidatedSystemVO getConsolidatedSystemVO(String id) {
        return this.convertEO2VO(this.getConsolidatedSystemEO(id));
    }

    @Override
    public void save(ConsolidatedSystemEO consolidatedSystemEO) {
        Date date = new Date();
        consolidatedSystemEO.setCreateTime(date);
        consolidatedSystemEO.setUpdateTime(date);
        this.consolidatedSystemDao.save(consolidatedSystemEO);
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u65b0\u589e-" + consolidatedSystemEO.getSystemName() + "\u5408\u5e76\u4f53\u7cfb"), (String)"");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String addConsolidatedSystem(ConsolidatedSystemVO consolidatedSystemVO) {
        ConsolidatedSystemEO eo = this.convertVO2EO(consolidatedSystemVO);
        Date date = new Date();
        eo.setUpdateTime(date);
        eo.setCreateUser(null);
        this.checkSystemNameLength(eo.getSystemName());
        ConsolidatedSystemEO existName = this.consolidatedSystemDao.getConsolidatedSystemByName(consolidatedSystemVO.getSystemName().trim());
        if (existName != null && !existName.getId().equals(eo.getId())) {
            throw new BusinessRuntimeException("\u4f53\u7cfb\u540d\u79f0\u91cd\u590d");
        }
        if (consolidatedSystemVO.getEditFlag().booleanValue()) {
            this.consolidatedSystemDao.update((BaseEntity)eo);
            this.logger.info("\u4fee\u6539\u5408\u5e76\u4f53\u7cfb:" + eo.getSystemName());
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u4fee\u6539-" + eo.getSystemName() + "\u5408\u5e76\u4f53\u7cfb"), (String)"");
            return eo.getId();
        }
        eo.setDataSort(OrderGenerator.newOrderShort());
        eo.setCreateTime(date);
        this.logger.info("\u65b0\u589e\u5408\u5e76\u4f53\u7cfb:" + eo.getSystemName());
        String id = (String)((Object)this.consolidatedSystemDao.save(eo));
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u65b0\u589e-" + eo.getSystemName() + "\u5408\u5e76\u4f53\u7cfb"), (String)"");
        this.initOption(id);
        return id;
    }

    private void initOption(String systemId) {
        ConsolidatedOptionVO optionVO = new ConsolidatedOptionVO();
        this.optionClient.saveOptionData(systemId, optionVO);
    }

    private void checkSystemNameLength(String systemName) {
        ColumnModelDefine columnModelDefine;
        try {
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByName("GC_CONSSYSTEM");
            columnModelDefine = dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), "SYSTEMNAME");
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u6821\u9a8c\u4f53\u7cfb\u540d\u79f0\u957f\u5ea6\u65f6\u53d1\u751f\u5f02\u5e38:" + e.getMessage(), (Throwable)e);
        }
        if (systemName.length() > columnModelDefine.getPrecision()) {
            throw new BusinessRuntimeException("\u5408\u5e76\u4f53\u7cfb\u540d\u79f0\u957f\u5ea6\u8d85\u8fc7\u4e86\u7269\u7406\u8868\u4e2d\u5b57\u6bb5\u7684\u6700\u5927\u957f\u5ea6\u3010" + columnModelDefine.getPrecision() + "\u3011,\u8bf7\u4fee\u6539\u4f53\u7cfb\u540d\u79f0\u6216\u4fee\u6539\u5bf9\u5e94\u5b57\u6bb5\u957f\u5ea6!");
        }
    }

    @Override
    public void editConsolidatedSystem(String id, ConsolidatedSystemVO consolidatedSystemVO) {
        ConsolidatedSystemEO eo = this.convertVO2EO(consolidatedSystemVO);
        eo.setUpdateTime(new Date());
        this.consolidatedSystemDao.update((BaseEntity)eo);
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u4fee\u6539-" + eo.getSystemName() + "\u5408\u5e76\u4f53\u7cfb"), (String)"");
    }

    @Override
    public void handleConsolidatedSystem(String id, String action) {
        ConsolidatedSystemEO consolidatedSystemEO = this.getConsolidatedSystemEO(id);
        if ("moveUp".equals(action) || "moveDown".equals(action)) {
            // empty if block
        }
        this.consolidatedSystemDao.save(consolidatedSystemEO);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteConsolidatedSystem(String id) {
        ConsolidatedSystemEO eo = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)id));
        boolean hasRule = UnionRuleUtils.hasRulesByReportSystemId(id);
        if (hasRule) {
            Assert.isFalse((boolean)hasRule, (String)"\u5df2\u5173\u8054\u89c4\u5219\uff0c\u8bf7\u5148\u5220\u9664\u89c4\u5219.", (Object[])new Object[0]);
        }
        this.consolidatedTaskService.unbindBySystemId(id);
        this.subjectUIService.deleteSubjectsBySystemId(id);
        this.consolidatedOptionService.deleteOptionData(id);
        this.consolidatedSystemDao.delete((BaseEntity)eo);
        this.applicationContext.publishEvent((ApplicationEvent)new ConsolidatedTaskChangedEvent(new ConsolidatedTaskChangedEvent.ConsolidatedTaskChangedInfo(), NpContextHolder.getContext()));
        this.logger.info("\u5220\u9664\u5408\u5e76\u4f53\u7cfb\uff0cid:" + id + ", name:" + eo.getSystemName());
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u4f53\u7cfb", (String)("\u5220\u9664-" + eo.getSystemName() + "\u5408\u5e76\u4f53\u7cfb"), (String)"");
    }

    @Override
    public ConsolidatedSystemVO convertEO2VO(ConsolidatedSystemEO eo) {
        ConsolidatedSystemVO vo = new ConsolidatedSystemVO();
        BeanUtils.copyProperties((Object)eo, vo);
        if (StringUtils.isEmpty((String)eo.getDataSchemeKey())) {
            return vo;
        }
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(eo.getDataSchemeKey());
        if (dataScheme != null && !StringUtils.isEmpty((String)dataScheme.getTitle())) {
            vo.setDataSchemeName(dataScheme.getTitle());
        } else {
            vo.setDataSchemeName(vo.getDataSchemeKey());
        }
        return vo;
    }

    private ConsolidatedSystemEO convertVO2EO(ConsolidatedSystemVO vo) {
        ConsolidatedSystemEO eo = new ConsolidatedSystemEO();
        BeanUtils.copyProperties(vo, (Object)eo);
        return eo;
    }

    @Override
    public List<SelectOptionVO> getFormualSchemes(String systemId) {
        List taskIds = this.consolidatedTaskService.getConsolidatedTasks(systemId).stream().map(task -> task.getTaskKey()).collect(Collectors.toList());
        List formSchemeIds = Collections.emptyList();
        for (String taskId : taskIds) {
            try {
                formSchemeIds = this.runTimeViewController.queryFormSchemeByTask(taskId).stream().map(formScheme -> formScheme.getKey()).collect(Collectors.toList());
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u4efb\u52a1\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff0c" + e.getMessage());
            }
        }
        return formSchemeIds.stream().map(formSchemeId -> this.getFormSchemeDefineSelectOptions(this.runTimeViewController.getFormScheme(formSchemeId))).reduce(Stream::concat).orElse(Stream.empty()).collect(Collectors.toList());
    }

    private Stream<SelectOptionVO> getFormSchemeDefineSelectOptions(FormSchemeDefine formSchemeDefine) {
        return this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey()).stream().filter(formulaSchemeDefine -> FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT.equals((Object)formulaSchemeDefine.getFormulaSchemeType())).map(formulaSchemeDefine -> new SelectOptionVO((Object)formulaSchemeDefine.getKey(), formSchemeDefine.getTitle() + "-" + formulaSchemeDefine.getTitle()));
    }

    @Override
    public List<ConsolidatedSystemVO> getSystemsByIds(List<String> systemIds) {
        List<ConsolidatedSystemEO> systemEOS = this.consolidatedSystemDao.listSystemsByIds(systemIds);
        ArrayList<ConsolidatedSystemVO> systemVOS = new ArrayList<ConsolidatedSystemVO>();
        for (ConsolidatedSystemEO systemEO : systemEOS) {
            ConsolidatedSystemVO vo = new ConsolidatedSystemVO();
            BeanUtils.copyProperties((Object)systemEO, vo);
            systemVOS.add(vo);
        }
        return systemVOS;
    }
}

