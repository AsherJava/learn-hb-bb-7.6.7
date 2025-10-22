/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.exception.BeanParaException
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.holiday.manager.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.exception.BeanParaException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.holiday.manager.bean.HolidayDefine;
import com.jiuqi.nr.holiday.manager.common.HolidayError;
import com.jiuqi.nr.holiday.manager.dao.HolidayManagerDao;
import com.jiuqi.nr.holiday.manager.facade.HolidayObj;
import com.jiuqi.nr.holiday.manager.service.IHolidayManagerService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayManagerServiceImpl
implements IHolidayManagerService {
    @Autowired
    private HolidayManagerDao holidayManagerDao;
    private final Logger logger = LoggerFactory.getLogger(HolidayManagerServiceImpl.class);

    @Override
    public List<HolidayDefine> doQueryHolidayDefine(String year) {
        return this.holidayManagerDao.queryByYear(year);
    }

    @Override
    public List<HolidayObj> doQuery(String year) {
        List<HolidayDefine> queryByYear = this.holidayManagerDao.queryByYear(year);
        if (null == queryByYear || queryByYear.isEmpty()) {
            return Collections.emptyList();
        }
        return queryByYear.stream().map(HolidayObj::toHolidayObj).collect(Collectors.toList());
    }

    @Override
    public void doSave(String year, List<HolidayObj> objs) throws JQException {
        try {
            this.holidayManagerDao.deleteByYear(year);
            if (null != objs && !objs.isEmpty()) {
                List<HolidayDefine> defines = objs.stream().map(o -> HolidayObj.toHolidayDefineForSave(o, year)).collect(Collectors.toList());
                this.holidayManagerDao.insert(defines);
            }
            this.logger.info("\u5047\u65e5\u7ba1\u7406\u66f4\u65b0{}\u5e74\u53c2\u6570\u3002", (Object)year);
        }
        catch (BeanParaException | DBParaException e) {
            this.logger.error(e.getMessage(), e);
            this.logger.error("\u5047\u65e5\u7ba1\u7406\u66f4\u65b0{}\u5e74\u53c2\u6570\u5931\u8d25\uff1a{}\u3002", (Object)year, (Object)e.getMessage());
            throw new JQException((ErrorEnum)HolidayError.HOLIDAY_ERROR_001, e);
        }
    }

    @Override
    public void doDelete(String year) throws JQException {
        try {
            this.holidayManagerDao.deleteByYear(year);
            this.logger.info("\u5047\u65e5\u7ba1\u7406\u5220\u9664{}\u5e74\u53c2\u6570\u3002", (Object)year);
        }
        catch (BeanParaException | DBParaException e) {
            this.logger.error(e.getMessage(), e);
            this.logger.error("\u5047\u65e5\u7ba1\u7406\u5220\u9664{}\u5e74\u53c2\u6570\u5931\u8d25\uff1a{}\u3002", (Object)year, (Object)e.getMessage());
            throw new JQException((ErrorEnum)HolidayError.HOLIDAY_ERROR_002, e);
        }
    }

    @Override
    public List<HolidayObj> doReset(String year) throws JQException {
        try {
            this.holidayManagerDao.deleteByYear(year);
            this.logger.info("\u5047\u65e5\u7ba1\u7406\u91cd\u7f6e{}\u5e74\u53c2\u6570\u3002", (Object)year);
        }
        catch (BeanParaException | DBParaException e) {
            this.logger.error(e.getMessage(), e);
            this.logger.error("\u5047\u65e5\u7ba1\u7406\u91cd\u7f6e{}\u5e74\u53c2\u6570\u5931\u8d25\uff1a{}\u3002", (Object)year, (Object)e.getMessage());
            throw new JQException((ErrorEnum)HolidayError.HOLIDAY_ERROR_002, e);
        }
        return Collections.emptyList();
    }

    @Override
    public void doExport() {
    }

    @Override
    public void doImport() {
    }
}

