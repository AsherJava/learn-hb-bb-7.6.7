/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.batch.summary.storage.SummaryGroupDao
 *  com.jiuqi.nr.batch.summary.storage.SummarySchemeDao
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.SummarySchemeDes
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl
 *  com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType
 *  com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.service.Impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.batch.summary.service.BSSFormClearService;
import com.jiuqi.nr.batch.summary.service.BSSchemeService;
import com.jiuqi.nr.batch.summary.service.BSShareService;
import com.jiuqi.nr.batch.summary.service.CustomCalibreService;
import com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState;
import com.jiuqi.nr.batch.summary.service.enumeration.ShareSchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.SummaryGroupDao;
import com.jiuqi.nr.batch.summary.storage.SummarySchemeDao;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryGroup;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.SummarySchemeDes;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeImpl;
import com.jiuqi.nr.batch.summary.storage.enumeration.TargetDimType;
import com.jiuqi.nr.batch.summary.storage.utils.BatchSummaryUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BSSchemeServiceImpl
implements BSSchemeService {
    private static final Logger logger = LoggerFactory.getLogger(BSSchemeServiceImpl.class);
    @Resource
    private SummarySchemeDao schemeDao;
    @Resource
    private SummaryGroupDao groupDao;
    @Resource
    private IRunTimeViewController rtViewService;
    @Resource
    private CustomCalibreService customCalibreService;
    @Resource
    private BSShareService bsShareService;
    @Autowired
    private BSSFormClearService formClearService;

    @Override
    public SummaryScheme findScheme(String schemeKey) {
        if (StringUtils.isNotEmpty((String)schemeKey)) {
            return this.schemeDao.findScheme(schemeKey);
        }
        return null;
    }

    @Override
    public SummaryScheme copyScheme(String schemeKey) {
        SummaryScheme scheme = this.findScheme(schemeKey);
        if (scheme != null) {
            List<SummaryScheme> allSchemes = this.findSchemes(scheme.getTask());
            Set<String> schemeCodes = allSchemes.stream().map(SummaryScheme::getCode).collect(Collectors.toSet());
            Set<String> schemeTitles = allSchemes.stream().map(SummarySchemeDes::getTitle).collect(Collectors.toSet());
            SummarySchemeDefine impl = new SummarySchemeDefine();
            impl.setKey(scheme.getKey());
            impl.setCode(this.getCopyCode(schemeCodes, scheme.getCode()));
            impl.setTitle(this.getCopyTitle(schemeTitles, scheme.getTitle()));
            impl.setGroup(scheme.getGroup());
            impl.setTask(scheme.getTask());
            impl.setCreator(scheme.getCreator());
            impl.setUpdateTime(scheme.getUpdateTime());
            impl.setSumDataTime(scheme.getSumDataTime());
            impl.setTargetDim(scheme.getTargetDim());
            impl.setRangeUnit(scheme.getRangeUnit());
            impl.setRangeForm(scheme.getRangeForm());
            impl.setOrdinal(scheme.getOrdinal());
            return impl;
        }
        return null;
    }

    @Override
    public SummaryScheme findSchemeByTaskGroupAndTitle(String task, String groupKey, String schemeTitle) {
        if (StringUtils.isNotEmpty((String)task) && StringUtils.isNotEmpty((String)groupKey) && StringUtils.isNotEmpty((String)schemeTitle)) {
            return this.schemeDao.findSchemeByTaskGroupAndTitle(task, groupKey, schemeTitle);
        }
        return null;
    }

    private String getCopyCode(Set<String> schemeCodes, String oriCode) {
        String copyCode = BatchSummaryUtils.getCopySchemeCode((String)oriCode);
        while (schemeCodes.contains(copyCode)) {
            copyCode = BatchSummaryUtils.getCopySchemeCode((String)copyCode);
        }
        return copyCode;
    }

    private String getCopyTitle(Set<String> schemeTitles, String oriTitle) {
        String copyTitle = BatchSummaryUtils.getCopySchemeTitle((String)oriTitle);
        while (schemeTitles.contains(copyTitle)) {
            copyTitle = BatchSummaryUtils.getCopySchemeTitle((String)copyTitle);
        }
        return copyTitle;
    }

    @Override
    public int moveScheme2Group(String groupKey, List<String> schemeKeys) {
        if (StringUtils.isNotEmpty((String)groupKey) && schemeKeys != null && !schemeKeys.isEmpty()) {
            return this.schemeDao.moveRow(groupKey, schemeKeys);
        }
        return 0;
    }

    @Override
    public SummaryScheme findScheme(String task, String schemeCode) {
        if (StringUtils.isNotEmpty((String)task) && StringUtils.isNotEmpty((String)schemeCode)) {
            return this.schemeDao.findScheme(task, schemeCode);
        }
        return null;
    }

    @Override
    public List<SummaryScheme> findSchemes(List<String> schemeKeys) {
        if (schemeKeys != null && !schemeKeys.isEmpty()) {
            return this.schemeDao.findSchemes(schemeKeys);
        }
        return new ArrayList<SummaryScheme>();
    }

    @Override
    public List<SummaryScheme> findSchemes(String task) {
        if (StringUtils.isNotEmpty((String)task)) {
            return this.schemeDao.findSchemes(task, NpContextHolder.getContext().getUserId());
        }
        return new ArrayList<SummaryScheme>();
    }

    @Override
    public List<SummaryScheme> findChildSchemeByGroup(String task, String groupKey) {
        if (StringUtils.isNotEmpty((String)task) && StringUtils.isNotEmpty((String)groupKey)) {
            return this.schemeDao.findGroupSchemes(task, groupKey);
        }
        return new ArrayList<SummaryScheme>();
    }

    @Override
    public List<SummaryScheme> findAllChildSchemeByGroup(String task, String groupKey) {
        if (StringUtils.isNotEmpty((String)task) && StringUtils.isNotEmpty((String)groupKey)) {
            SummaryGroup groupDefine = this.groupDao.findGroup(groupKey);
            List allChildGroups = this.groupDao.findAllChildGroups(task, groupKey);
            allChildGroups.add(0, groupDefine);
            List groupKeys = allChildGroups.stream().map(SummaryGroup::getKey).collect(Collectors.toList());
            return this.schemeDao.findGroupSchemes(task, groupKeys);
        }
        return new ArrayList<SummaryScheme>();
    }

    @Override
    public int updateSumDataDate(String schemeKey, Date date) {
        return this.schemeDao.modifySchemeDataDate(schemeKey, date);
    }

    @Override
    public SchemeServiceState saveSummaryScheme(SummarySchemeDefine impl) {
        SchemeServiceState schemeServiceState;
        if (!impl.isValidScheme()) {
            return SchemeServiceState.INVALID_SCHEME_DEFINE;
        }
        TaskDefine taskDefine = this.rtViewService.queryTaskDefine(impl.getTask());
        if (taskDefine == null) {
            return SchemeServiceState.INVALID_TASK;
        }
        impl.setKey(UUID.randomUUID().toString());
        impl.setOrdinal(System.currentTimeMillis() + "");
        impl.setUpdateTime(new Date());
        impl.setSumDataTime(new Date());
        impl.setCreator(NpContextHolder.getContext().getUserId());
        impl.setGroup(StringUtils.isEmpty((String)impl.getGroup()) ? "00000000-0000-0000-0000-000000000000" : impl.getGroup());
        SchemeServiceState schemeServiceState2 = schemeServiceState = this.schemeDao.insertRow(impl) == 1 ? SchemeServiceState.SUCCESS : SchemeServiceState.FAIL;
        if (schemeServiceState == SchemeServiceState.SUCCESS && TargetDimType.CONDITION == impl.getTargetDim().getTargetDimType()) {
            return this.customCalibreService.saveCustomCalibreRows(impl, impl.getTargetDim().getCustomCalibreRows());
        }
        return schemeServiceState;
    }

    @Override
    public SchemeServiceState updateSummaryScheme(SummarySchemeImpl impl) {
        SummaryScheme scheme = this.schemeDao.findScheme(impl.getKey());
        if (scheme == null) {
            return SchemeServiceState.INVALID_SCHEME_KEY;
        }
        if (!impl.isValidScheme()) {
            return SchemeServiceState.INVALID_SCHEME_DEFINE;
        }
        try {
            this.formClearService.clearUselessData(scheme, impl);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u672a\u9009\u8868\u5355\u6570\u636e\u5931\u8d25", e);
        }
        impl.setGroup(scheme.getGroup());
        impl.setUpdateTime(new Date());
        impl.setSumDataTime(scheme.getSumDataTime());
        impl.setOrdinal(scheme.getOrdinal());
        return this.schemeDao.modifyRow(impl) == 1 ? SchemeServiceState.SUCCESS : SchemeServiceState.FAIL;
    }

    @Override
    public SchemeServiceState updateSummarySchemeDefine(SummarySchemeDefine impl) {
        SchemeServiceState schemeServiceState = this.updateSummaryScheme((SummarySchemeImpl)impl);
        if (SchemeServiceState.SUCCESS == schemeServiceState && TargetDimType.CONDITION == impl.getTargetDim().getTargetDimType()) {
            return this.customCalibreService.modifyCustomCalibreRows(impl, impl.getTargetDim().getCustomCalibreRows());
        }
        return schemeServiceState;
    }

    @Override
    public SchemeServiceState removeSummaryScheme(String schemeKey) {
        ShareSchemeServiceState shareSchemeServiceState;
        SchemeServiceState schemeServiceState;
        SummaryScheme scheme = this.schemeDao.findScheme(schemeKey);
        if (TargetDimType.CONDITION == scheme.getTargetDim().getTargetDimType()) {
            this.customCalibreService.deleteCustomCalibreRows(scheme);
        }
        SchemeServiceState schemeServiceState2 = schemeServiceState = this.schemeDao.removeRow(schemeKey) == 1 ? SchemeServiceState.SUCCESS : SchemeServiceState.FAIL;
        if (schemeServiceState.equals((Object)SchemeServiceState.SUCCESS) && this.bsShareService.findScheme(schemeKey) && (shareSchemeServiceState = this.bsShareService.removeShareSummaryScheme(schemeKey)).equals((Object)ShareSchemeServiceState.FAIL)) {
            return SchemeServiceState.FAIL;
        }
        return schemeServiceState;
    }
}

