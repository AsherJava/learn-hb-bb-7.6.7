/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.office.word.DocumentTemplate
 *  com.jiuqi.bi.office.word.DocumentTemplateFragment
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.bi.office.word.DocumentTemplate;
import com.jiuqi.bi.office.word.DocumentTemplateFragment;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.definition.facade.DesignReportTagDefine;
import com.jiuqi.nr.definition.internal.dao.DesignReportTagDao;
import com.jiuqi.nr.definition.internal.impl.DesignReportTagDefineImpl;
import com.jiuqi.nr.definition.reportTag.common.ReportTagExceptionEnum;
import com.jiuqi.nr.definition.reportTag.service.IDesignReportTagService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignReportTagService
implements IDesignReportTagService {
    @Autowired
    private DesignReportTagDao reportTagDao;
    private Logger logger = LoggerFactory.getLogger(DesignReportTagService.class);

    @Override
    public List<DesignReportTagDefine> queryAllTagsByRptKey(String rptKey) {
        return this.reportTagDao.queryByRptKey(rptKey);
    }

    @Override
    public List<DesignReportTagDefine> queryTagsByRptKeys(Set<String> rptKeys) {
        return this.reportTagDao.queryByRptKeys(rptKeys);
    }

    @Override
    public void deleteTagByKeys(List<String> keys) throws JQException {
        if (keys == null || keys.isEmpty()) {
            this.logger.error(ReportTagExceptionEnum.REPORT_TAG_ERROR_006.getMessage());
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_006);
        }
        try {
            String[] keysArray = keys.toArray(new String[keys.size()]);
            this.reportTagDao.deleteByKeys(keysArray);
        }
        catch (BeanParaException | DBParaException e) {
            this.logger.error(ReportTagExceptionEnum.REPORT_TAG_ERROR_001.getMessage());
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_001, e);
        }
    }

    @Override
    public void insertTags(List<DesignReportTagDefine> list) throws JQException {
        if (null != list && !list.isEmpty()) {
            try {
                this.reportTagDao.insertTags(list);
            }
            catch (BeanParaException | DBParaException e) {
                this.logger.error(ReportTagExceptionEnum.REPORT_TAG_ERROR_002.getMessage());
                throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_002, e);
            }
        }
    }

    @Override
    public void updateTags(List<DesignReportTagDefine> list) throws JQException {
        if (null != list && !list.isEmpty()) {
            try {
                this.reportTagDao.update(list.toArray());
            }
            catch (BeanParaException | DBParaException e) {
                this.logger.error(ReportTagExceptionEnum.REPORT_TAG_ERROR_002.getMessage());
                throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_002, e);
            }
        }
    }

    @Override
    public void deleteTagsByRptKey(String rptKey) throws JQException {
        try {
            this.reportTagDao.deleteByRptKey(rptKey);
        }
        catch (BeanParaException | DBParaException e) {
            this.logger.error(ReportTagExceptionEnum.REPORT_TAG_ERROR_003.getMessage());
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_003, e);
        }
    }

    @Override
    public void deleteTagsByRptKeys(Set<String> rptKeys) throws JQException {
        try {
            this.reportTagDao.deleteByRptKeys(rptKeys);
        }
        catch (DBParaException e) {
            this.logger.error(ReportTagExceptionEnum.REPORT_TAG_ERROR_003.getMessage());
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_003, (Throwable)e);
        }
    }

    @Override
    public void saveTag(DesignReportTagDefine reportTagDefine) throws JQException {
        try {
            this.reportTagDao.saveTag(reportTagDefine);
        }
        catch (BeanParaException | DBParaException e) {
            this.logger.error(ReportTagExceptionEnum.REPORT_TAG_ERROR_004.getMessage());
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_004, e);
        }
    }

    @Override
    public List<DesignReportTagDefine> filterCustomTagsInRpt(InputStream is, String rptKey) throws JQException {
        DocumentTemplate dt = new DocumentTemplate();
        ArrayList<DesignReportTagDefine> reportTagDefineList = new ArrayList<DesignReportTagDefine>();
        HashSet<String> tagContentSet = new HashSet<String>();
        try {
            dt.loadDocument(is);
            for (DocumentTemplateFragment f : dt.getFragmentsByTag("TAG")) {
                String tagContent;
                if (!StringUtils.isNotEmpty((String)f.getContent()) || (tagContent = f.getContent().trim()).isEmpty() || !tagContentSet.add(tagContent)) continue;
                DesignReportTagDefineImpl reportTagDefine = new DesignReportTagDefineImpl();
                reportTagDefine.setRptKey(rptKey);
                reportTagDefine.setContent(tagContent);
                reportTagDefine.setKey(UUIDUtils.getKey());
                reportTagDefineList.add(reportTagDefine);
            }
        }
        catch (IOException e) {
            this.logger.error(ReportTagExceptionEnum.REPORT_TAG_ERROR_005.getMessage());
            throw new JQException((ErrorEnum)ReportTagExceptionEnum.REPORT_TAG_ERROR_005, (Throwable)e);
        }
        return reportTagDefineList;
    }
}

