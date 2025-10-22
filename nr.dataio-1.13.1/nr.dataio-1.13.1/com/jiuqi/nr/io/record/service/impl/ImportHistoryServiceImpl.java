/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.domain.Page
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 */
package com.jiuqi.nr.io.record.service.impl;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.domain.Page;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.record.bean.ImportHistory;
import com.jiuqi.nr.io.record.bean.ImportHistoryVO;
import com.jiuqi.nr.io.record.bean.ImportLog;
import com.jiuqi.nr.io.record.dao.FormStatisticDao;
import com.jiuqi.nr.io.record.dao.ImportHistoryDao;
import com.jiuqi.nr.io.record.dao.ImportLogDao;
import com.jiuqi.nr.io.record.dao.UnitFailureRecordDao;
import com.jiuqi.nr.io.record.dao.UnitFailureSubRecordDao;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ImportHistoryServiceImpl
implements ImportHistoryService {
    @Autowired
    private ImportHistoryDao importHistoryDao;
    @Autowired
    private ImportLogDao importLogDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IMappingSchemeService mappingSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private UnitFailureRecordDao unitFailureRecordDao;
    @Autowired
    private UnitFailureSubRecordDao unitFailureSubRecordDao;
    @Autowired
    private FormStatisticDao formStatisticDao;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;

    @Override
    public void createImportHistory(ImportHistory history) {
        this.importHistoryDao.insert(history);
    }

    @Override
    public Page<ImportHistoryVO> queryByCreator(String createUser, int page, int size) {
        Page<ImportHistory> importHistoryPage = this.systemIdentityService.isSystemByUserId(createUser) ? this.importHistoryDao.queryAll(page, size) : this.importHistoryDao.queryByCreator(createUser, page, size);
        return this.formatVO(importHistoryPage);
    }

    @Override
    public ImportHistory queryByRecKey(String recKey) {
        return this.importHistoryDao.queryByRecKey(recKey);
    }

    @Override
    public ImportHistoryVO queryVOByRecKey(String recKey) {
        ImportHistory importHistory = this.queryByRecKey(recKey);
        ArrayList<ImportHistory> records = new ArrayList<ImportHistory>();
        records.add(importHistory);
        Page importHistoryPage = new Page(records, 1L, 1, 1);
        Page<ImportHistoryVO> importHistoryVOPage = this.formatVO((Page<ImportHistory>)importHistoryPage);
        return (ImportHistoryVO)importHistoryVOPage.getRecords().get(0);
    }

    @Override
    public void updateImportHistory(ImportHistory history) {
        this.importHistoryDao.updateStateAndFinishTimeByRecKey(history);
    }

    @Override
    public void addImportLog(ImportLog log) {
        this.importLogDao.insert(log);
    }

    @Override
    public void updateImportLogDesc(ImportLog log) {
        this.importLogDao.update(log);
    }

    @Override
    public List<ImportLog> getImportLogs(String recKey) {
        return this.importLogDao.queryByRecKey(recKey);
    }

    @Override
    public List<ImportLog> getImportLogsByFactory(String recKey, String factoryId) {
        return this.importLogDao.queryByRecKeyAndFactory(recKey, factoryId);
    }

    @Override
    public void deleteTimeOutImportHistory(int days) {
        List<String> needDeleteRecKeys = this.importHistoryDao.getNeedDeleteRecKeys(days);
        if (CollectionUtils.isEmpty(needDeleteRecKeys)) {
            return;
        }
        this.unitFailureRecordDao.deleteByRecKeys(needDeleteRecKeys);
        this.unitFailureSubRecordDao.deleteByRecKeys(needDeleteRecKeys);
        this.formStatisticDao.deleteByRecKeys(needDeleteRecKeys);
        this.importHistoryDao.deleteImportHistory(needDeleteRecKeys);
    }

    private Page<ImportHistoryVO> formatVO(Page<ImportHistory> importHistoryPage) {
        List records = importHistoryPage.getRecords();
        ArrayList<ImportHistoryVO> importHistoryVOS = new ArrayList<ImportHistoryVO>();
        int pageNo = importHistoryPage.getPageNo();
        int pageSize = importHistoryPage.getPageSize();
        HashMap formScheme2DataTime2Title = new HashMap();
        HashMap<String, String> mappingKey2Title = new HashMap<String, String>();
        HashMap<String, String> entityId2Title = new HashMap<String, String>();
        List taskDefines = this.runTimeViewController.getAllTaskDefines();
        Map<String, String> taskMap = taskDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, IBaseMetaItem::getTitle));
        HashMap userId2Name = new HashMap();
        List allUsers = this.userService.getAllUsers();
        allUsers.forEach(user -> userId2Name.put(user.getId(), user.getFullname()));
        List allUsers1 = this.systemUserService.getAllUsers();
        allUsers1.forEach(systemUser -> userId2Name.put(systemUser.getId(), systemUser.getFullname()));
        for (int i = 0; i < records.size(); ++i) {
            String mappingKey;
            ImportHistory importHistory = (ImportHistory)records.get(i);
            ImportHistoryVO importHistoryVO = new ImportHistoryVO();
            importHistoryVO.setNum(i + 1 + pageSize * (pageNo - 1));
            importHistoryVO.setRecKey(importHistory.getRecKey());
            importHistoryVO.setState(importHistory.getState());
            importHistoryVO.setImportTime(importHistory.getCreateTime());
            importHistoryVO.setEndTime(importHistory.getEndTime());
            importHistoryVO.setParamType(importHistory.getParamType());
            importHistoryVO.setTaskTitle(taskMap.get(importHistory.getTaskKey()));
            importHistoryVO.setUserName((String)userId2Name.get(importHistory.getCreateUser()));
            String formSchemeKey = importHistory.getFormSchemeKey();
            if (!formScheme2DataTime2Title.containsKey(formSchemeKey)) {
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                List periodItems = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodItems();
                periodItems.sort(Comparator.comparing(IPeriodRow::getCode));
                HashMap<String, String> simpleTitle = new HashMap<String, String>();
                for (IPeriodRow periodItem : periodItems) {
                    simpleTitle.put(periodItem.getCode(), periodItem.getTitle());
                }
                formScheme2DataTime2Title.put(formSchemeKey, simpleTitle);
            }
            importHistoryVO.setDateTime((String)((Map)formScheme2DataTime2Title.get(formSchemeKey)).get(importHistory.getDataTime()));
            String caliberEntity = importHistory.getCaliberEntity();
            if (!StringUtils.isEmpty(caliberEntity)) {
                if (!entityId2Title.containsKey(caliberEntity)) {
                    entityId2Title.put(caliberEntity, this.entityMetaService.queryEntity(caliberEntity).getTitle());
                }
                importHistoryVO.setCaliberEntityTitle((String)entityId2Title.get(caliberEntity));
            }
            if (!StringUtils.isEmpty(mappingKey = importHistory.getMappingKey()) && !mappingKey2Title.containsKey(mappingKey)) {
                mappingKey2Title.put(mappingKey, this.mappingSchemeService.getSchemeByKey(mappingKey).getTitle());
                importHistoryVO.setMappingSchemeTitle((String)mappingKey2Title.get(mappingKey));
            }
            importHistoryVOS.add(importHistoryVO);
        }
        return new Page(importHistoryVOS, importHistoryPage.getCount(), pageNo, pageSize);
    }
}

