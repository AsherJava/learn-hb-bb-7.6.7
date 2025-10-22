/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum
 *  com.jiuqi.gcreport.journalsingle.vo.JournalRelateSchemeVO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.journalsingle.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.journalsingle.dao.IJournalRelateSchemeDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalRelateSchemeEO;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSchemeService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSubjectService;
import com.jiuqi.gcreport.journalsingle.vo.JournalRelateSchemeVO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalSingleSchemeServiceImpl
implements IJournalSingleSchemeService {
    @Autowired
    private IJournalRelateSchemeDao journalRelateSchemeDao;
    @Autowired
    private IJournalSingleSubjectService journalSingleSubjectService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public String insertRelateScheme(JournalRelateSchemeVO relateSchemeVo) {
        String relateSchemeId = this.journalRelateSchemeDao.getRelateSchemeId(relateSchemeVo.getTaskId(), relateSchemeVo.getSchemeId(), relateSchemeVo.getAdjustType());
        if (!StringUtils.isEmpty((String)relateSchemeId)) {
            return "\u8be5\u62a5\u8868\u65b9\u6848\u4e0b\u5df2\u7ecf\u5b58\u5728\u4e00\u4e2a\u503c\uff0c\u4e0d\u5141\u8bb8\u91cd\u590d\u65b0\u589e";
        }
        JournalRelateSchemeEO relateSchemeEo = new JournalRelateSchemeEO();
        BeanUtils.copyProperties(relateSchemeVo, (Object)relateSchemeEo);
        relateSchemeEo.setCreateTime(new Date());
        this.journalRelateSchemeDao.save(relateSchemeEo);
        return null;
    }

    @Override
    public Integer deleteRelateScheme(JournalRelateSchemeVO relateSchemeVo) {
        String relateSchemeId = this.journalRelateSchemeDao.getRelateSchemeId(relateSchemeVo.getTaskId(), relateSchemeVo.getSchemeId(), relateSchemeVo.getAdjustType());
        if (null == relateSchemeId) {
            return 0;
        }
        List<JournalSubjectEO> allSubjects = this.journalSingleSubjectService.listAllSubjects(relateSchemeId);
        if (!CollectionUtils.isEmpty(allSubjects)) {
            throw new BusinessRuntimeException("\u5f53\u524d\u65b9\u6848\u4e0b\u5b58\u5728\u9879\u76ee\uff0c\u4e0d\u5141\u8bb8\u5220\u9664");
        }
        JournalRelateSchemeEO schemeEO = new JournalRelateSchemeEO();
        schemeEO.setId(relateSchemeId);
        return this.journalRelateSchemeDao.delete((BaseEntity)schemeEO);
    }

    @Override
    public List<JournalRelateSchemeVO> listRelateSchemes() {
        List<JournalRelateSchemeEO> relateSchemeEos = this.journalRelateSchemeDao.listRelateSchemes();
        return relateSchemeEos.stream().map(relateSchemeEo -> {
            FormSchemeDefine schemeDefine;
            JournalRelateSchemeVO relateSchemeVo = new JournalRelateSchemeVO();
            BeanUtils.copyProperties(relateSchemeEo, relateSchemeVo);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(relateSchemeEo.getTaskId());
            if (null != taskDefine) {
                relateSchemeVo.setTaskTitle(taskDefine.getTitle());
            }
            if (null != (schemeDefine = this.runTimeViewController.getFormScheme(relateSchemeEo.getSchemeId()))) {
                relateSchemeVo.setSchemeTitle(schemeDefine.getTitle());
            }
            relateSchemeVo.setAdjustTypeTitle(AdjustTypeEnum.VIRTUAL_TABLE.getTitle());
            return relateSchemeVo;
        }).collect(Collectors.toList());
    }

    @Override
    public String getRelateSchemeId(String taskId, String schemeId, String adjustCode) {
        return this.journalRelateSchemeDao.getRelateSchemeId(taskId, schemeId, adjustCode);
    }
}

