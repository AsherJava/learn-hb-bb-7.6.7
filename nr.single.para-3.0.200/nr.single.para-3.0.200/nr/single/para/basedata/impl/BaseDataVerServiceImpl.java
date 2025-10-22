/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.va.basedata.common.BaseDataConsts
 *  com.jiuqi.va.basedata.dao.VaBaseDataDao
 *  com.jiuqi.va.basedata.dao.VaBaseDataVersionDao
 *  com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDO
 *  com.jiuqi.va.basedata.domain.BaseDataVersionDTO
 *  com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.BaseDataVersionClient
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package nr.single.para.basedata.impl;

import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.va.basedata.common.BaseDataConsts;
import com.jiuqi.va.basedata.dao.VaBaseDataDao;
import com.jiuqi.va.basedata.dao.VaBaseDataVersionDao;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDO;
import com.jiuqi.va.basedata.domain.BaseDataVersionDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.BaseDataVersionClient;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import nr.single.para.basedata.IBaseDataVerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class BaseDataVerServiceImpl
implements IBaseDataVerService {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private VaBaseDataVersionDao verDataDao;
    @Autowired
    private BaseDataVersionClient verClient;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private VaBaseDataDao baseDataDao;
    @Autowired
    private BaseDataCacheService baseDataCacheService;

    @Override
    public void updateUseVersion(BaseDataDefineDO baseDataDefineDO) {
        String tableName = baseDataDefineDO.getName();
        if (baseDataDefineDO.getVersionflag() == 0) {
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setName(tableName);
            baseDataDefineDTO.setVersionflag(Integer.valueOf(1));
            baseDataDefineDTO.setDimensionflag(Integer.valueOf(1));
            baseDataDefineDTO.setModifytime(new Date());
            this.baseDataDefineClient.update(baseDataDefineDTO);
        }
    }

    @Override
    public List<BaseDataVersionDO> listVersion(BaseDataVersionDTO param) {
        PageVO list = this.verClient.list(param);
        if (list != null && list.getRows() != null) {
            return list.getRows();
        }
        return new ArrayList<BaseDataVersionDO>();
    }

    @Override
    public BaseDataVersionDO getVersion(BaseDataVersionDTO param) {
        return this.verClient.get(param);
    }

    @Override
    public R updateVersion(BaseDataVersionDTO param) {
        int r = this.verDataDao.updateByPrimaryKeySelective((Object)param);
        this.verClient.syncCache((BaseDataVersionDO)param);
        return R.ok();
    }

    @Override
    public R insertVersion(BaseDataVersionDTO param) {
        int r = this.verDataDao.insert((Object)param);
        this.verClient.syncCache((BaseDataVersionDO)param);
        return R.ok();
    }

    @Override
    public BaseDataVersionDO insertYearVerion(String baseDataName, int year, boolean history) throws Exception {
        Date[] dateRegion = this.getDateRegion(year);
        Date[] dateRegionLast = this.getDateRegion(year - 1);
        Date[] dateRegionNext = this.getDateRegion(year + 1);
        Date[] dateRegionNext2 = this.getDateRegion(year + 2);
        Date curDate = dateRegion[0];
        Date lastDate = dateRegionLast[0];
        Date nextDate = dateRegionNext[0];
        Date nextDate2 = dateRegionNext2[0];
        BaseDataVersionDTO param = new BaseDataVersionDTO();
        param.setTablename(baseDataName);
        List<BaseDataVersionDO> list = this.listVersion(param);
        BaseDataVersionDO curVer = null;
        BaseDataVersionDTO defParam = new BaseDataVersionDTO();
        defParam.setTablename(baseDataName);
        defParam.setVersionDate(BaseDataConsts.VERSION_MIN_DATE);
        BaseDataVersionDO defVer = this.getVersion(defParam);
        if (list.size() == 0) {
            curVer = this.addVersion(baseDataName, "\u9ed8\u8ba4\u7248\u672c", BaseDataConsts.VERSION_MIN_DATE, BaseDataConsts.VERSION_MAX_DATE);
        } else if (list.size() == 1) {
            if (history) {
                if (defVer != null) {
                    BaseDataVersionDTO updateVer = new BaseDataVersionDTO();
                    updateVer.setId(defVer.getId());
                    updateVer.setTablename(baseDataName);
                    updateVer.setValidtime(BaseDataConsts.VERSION_MIN_DATE);
                    updateVer.setInvalidtime(nextDate);
                    updateVer.setModifytime(new Date());
                    updateVer.setActiveflag(Integer.valueOf(1));
                    this.updateVersion(updateVer);
                } else {
                    this.addVersion(baseDataName, "\u9ed8\u8ba4\u7248\u672c", BaseDataConsts.VERSION_MIN_DATE, nextDate);
                }
                curVer = this.addVersion(baseDataName, "\u62a5\u8868" + String.valueOf(year + 1), nextDate, BaseDataConsts.VERSION_MAX_DATE);
            } else {
                if (defVer != null) {
                    BaseDataVersionDTO updateVer = new BaseDataVersionDTO();
                    updateVer.setId(defVer.getId());
                    updateVer.setTablename(baseDataName);
                    updateVer.setValidtime(BaseDataConsts.VERSION_MIN_DATE);
                    updateVer.setInvalidtime(curDate);
                    updateVer.setModifytime(new Date());
                    updateVer.setActiveflag(Integer.valueOf(1));
                    this.updateVersion(updateVer);
                } else {
                    this.addVersion(baseDataName, "\u9ed8\u8ba4\u7248\u672c", BaseDataConsts.VERSION_MIN_DATE, curDate);
                }
                curVer = this.addVersion(baseDataName, "\u62a5\u8868" + String.valueOf(year), curDate, BaseDataConsts.VERSION_MAX_DATE);
            }
        } else {
            BaseDataVersionDO inTimeVer = null;
            BaseDataVersionDO minVer = null;
            BaseDataVersionDO maxVer = null;
            for (BaseDataVersionDO ver : list) {
                if (history) {
                    if (ver.getValidtime().getTime() == nextDate.getTime()) {
                        curVer = ver;
                    } else if (ver.getValidtime().getTime() < nextDate.getTime() && ver.getInvalidtime().getTime() > nextDate.getTime()) {
                        inTimeVer = ver;
                    }
                } else if (ver.getValidtime().getTime() == curDate.getTime()) {
                    curVer = ver;
                } else if (ver.getValidtime().getTime() < curDate.getTime() && ver.getInvalidtime().getTime() > curDate.getTime()) {
                    inTimeVer = ver;
                }
                if (ver.getValidtime().getTime() == BaseDataConsts.VERSION_MIN_DATE.getTime()) {
                    minVer = ver;
                }
                if (ver.getInvalidtime().getTime() != BaseDataConsts.VERSION_MAX_DATE.getTime()) continue;
                maxVer = ver;
            }
            if (curVer == null && inTimeVer != null) {
                BaseDataVersionDTO updateVer;
                int intimeYear = this.getYearFromDate(inTimeVer.getValidtime());
                if (history && intimeYear < year + 1) {
                    updateVer = new BaseDataVersionDTO();
                    updateVer.setId(inTimeVer.getId());
                    updateVer.setTablename(baseDataName);
                    updateVer.setValidtime(inTimeVer.getValidtime());
                    updateVer.setInvalidtime(nextDate);
                    updateVer.setModifytime(new Date());
                    updateVer.setActiveflag(Integer.valueOf(1));
                    this.updateVersion(updateVer);
                    curVer = this.addVersion(baseDataName, "\u62a5\u8868" + String.valueOf(year + 1), nextDate, inTimeVer.getInvalidtime());
                } else if (!history && intimeYear < year) {
                    updateVer = new BaseDataVersionDTO();
                    updateVer.setId(inTimeVer.getId());
                    updateVer.setTablename(baseDataName);
                    updateVer.setValidtime(inTimeVer.getValidtime());
                    updateVer.setInvalidtime(curDate);
                    updateVer.setModifytime(new Date());
                    updateVer.setActiveflag(Integer.valueOf(1));
                    this.updateVersion(updateVer);
                    curVer = this.addVersion(baseDataName, "\u62a5\u8868" + String.valueOf(year), curDate, inTimeVer.getInvalidtime());
                } else {
                    curVer = inTimeVer;
                }
            } else if (curVer == null && inTimeVer == null) {
                curVer = history ? this.addVersion(baseDataName, "\u62a5\u8868" + String.valueOf(year + 1), nextDate, maxVer.getValidtime()) : this.addVersion(baseDataName, "\u62a5\u8868" + String.valueOf(year), curDate, maxVer.getValidtime());
            } else if (curVer != null) {
                // empty if block
            }
        }
        return curVer;
    }

    @Override
    public BaseDataVersionDO queryYearVerion(String baseDataName, int year, boolean history) throws Exception {
        Date[] dateRegion = this.getDateRegion(year);
        Date[] dateRegionLast = this.getDateRegion(year - 1);
        Date[] dateRegionNext = this.getDateRegion(year + 1);
        Date[] dateRegionNext2 = this.getDateRegion(year + 2);
        Date curDate = dateRegion[0];
        Date lastDate = dateRegionLast[0];
        Date nextDate = dateRegionNext[0];
        Date nextDate2 = dateRegionNext2[0];
        BaseDataVersionDTO param = new BaseDataVersionDTO();
        param.setTablename(baseDataName);
        List<BaseDataVersionDO> list = this.listVersion(param);
        BaseDataVersionDO curVer = null;
        BaseDataVersionDTO defParam = new BaseDataVersionDTO();
        defParam.setTablename(baseDataName);
        defParam.setVersionDate(BaseDataConsts.VERSION_MIN_DATE);
        BaseDataVersionDO defVer = this.getVersion(defParam);
        if (list.size() == 0) {
            curVer = this.addVersion(baseDataName, "\u9ed8\u8ba4\u7248\u672c", BaseDataConsts.VERSION_MIN_DATE, BaseDataConsts.VERSION_MAX_DATE);
        } else if (list.size() > 0) {
            BaseDataVersionDO inTimeVer = null;
            BaseDataVersionDO minVer = null;
            BaseDataVersionDO maxVer = null;
            BaseDataVersionDO minVer2 = null;
            BaseDataVersionDO maxVer2 = null;
            BaseDataVersionDO minVer3 = null;
            BaseDataVersionDO maxVer3 = null;
            for (BaseDataVersionDO ver : list) {
                if (ver.getValidtime().getTime() == curDate.getTime()) {
                    curVer = ver;
                } else if (ver.getValidtime().getTime() < curDate.getTime() && ver.getInvalidtime().getTime() > curDate.getTime()) {
                    inTimeVer = ver;
                } else {
                    if (ver.getValidtime().getTime() > curDate.getTime()) {
                        if (maxVer3 == null) {
                            maxVer3 = ver;
                        } else if (maxVer3.getValidtime().getTime() > ver.getValidtime().getTime()) {
                            maxVer3 = ver;
                        }
                    } else if (ver.getValidtime().getTime() < curDate.getTime()) {
                        if (minVer3 == null) {
                            minVer3 = ver;
                        } else if (minVer3.getValidtime().getTime() < ver.getValidtime().getTime()) {
                            minVer3 = ver;
                        }
                    }
                    if (minVer2 == null) {
                        minVer2 = ver;
                    } else if (minVer2.getValidtime().getTime() > ver.getValidtime().getTime()) {
                        minVer2 = ver;
                    }
                    if (maxVer2 == null) {
                        maxVer2 = ver;
                    } else if (maxVer2.getValidtime().getTime() < ver.getValidtime().getTime()) {
                        maxVer2 = ver;
                    }
                }
                if (ver.getValidtime().getTime() == BaseDataConsts.VERSION_MIN_DATE.getTime()) {
                    minVer = ver;
                }
                if (ver.getInvalidtime().getTime() != BaseDataConsts.VERSION_MAX_DATE.getTime()) continue;
                maxVer = ver;
            }
            if (curVer == null && inTimeVer != null) {
                curVer = inTimeVer;
            } else if (curVer == null && inTimeVer == null) {
                curVer = this.addVersion(baseDataName, "\u62a5\u8868" + String.valueOf(year), curDate, nextDate);
            }
        }
        return curVer;
    }

    private int getYearFromDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(1);
    }

    private BaseDataVersionDO addVersion(BaseDataDefineDO baseDataDefineDO, int year) throws Exception {
        String tableName = baseDataDefineDO.getName();
        this.updateUseVersion(baseDataDefineDO);
        BaseDataVersionDO versionDO = this.addVersion(tableName, year);
        this.modifyDefaultVersion(tableName, versionDO.getInvalidtime());
        this.modifyValidTime(tableName, versionDO.getInvalidtime(), BaseDataConsts.VERSION_MIN_DATE);
        return versionDO;
    }

    private BaseDataVersionDO addVersion(String tableName, int year) throws Exception {
        BaseDataVersionDO versionDO = new BaseDataVersionDO();
        versionDO.setId(UUID.randomUUID());
        versionDO.setTablename(tableName);
        versionDO.setName("\u62a5\u8868" + year);
        Date[] dateRegion = this.getDateRegion(year);
        versionDO.setValidtime(BaseDataConsts.VERSION_MIN_DATE);
        dateRegion = this.getDateRegion(year + 1);
        versionDO.setInvalidtime(dateRegion[0]);
        versionDO.setModifytime(new Date());
        versionDO.setActiveflag(Integer.valueOf(1));
        UserLoginDTO user = ShiroUtil.getUser();
        if (user != null) {
            versionDO.setCreator(user.getUsername());
        }
        this.verDataDao.insert((Object)versionDO);
        this.verClient.syncCache(versionDO);
        return versionDO;
    }

    private BaseDataVersionDO addVersion(String tableName, String verName, Date validtime, Date invalidtime) throws Exception {
        BaseDataVersionDO versionDO = new BaseDataVersionDO();
        versionDO.setId(UUID.randomUUID());
        versionDO.setTablename(tableName);
        versionDO.setName(verName);
        versionDO.setValidtime(validtime);
        versionDO.setInvalidtime(invalidtime);
        versionDO.setModifytime(new Date());
        versionDO.setActiveflag(Integer.valueOf(1));
        UserLoginDTO user = ShiroUtil.getUser();
        if (user != null) {
            versionDO.setCreator(user.getUsername());
        }
        this.verDataDao.insert((Object)versionDO);
        this.verClient.syncCache(versionDO);
        return versionDO;
    }

    @Override
    public Date[] getDateRegion(int year) throws ParseException {
        PeriodWrapper pw = new PeriodWrapper(year, 1, 1);
        DefaultPeriodAdapter adapter = new DefaultPeriodAdapter();
        return adapter.getPeriodDateRegion(pw);
    }

    public Date getVersionDate(int year) throws ParseException {
        return this.getDateRegion(year)[1];
    }

    private void updateCache(BaseDataDTO rqParam) {
        if (rqParam.getVer() != null) {
            // empty if block
        }
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(rqParam.getTenantName());
        bdsc.setBaseDataDTO(rqParam);
        bdsc.setForceUpdate(true);
        this.baseDataCacheService.pushSyncMsg(bdsc);
    }

    private void modifyDefaultVersion(String tableName, Date invalidtime) {
        String sql = " update basedata_version set VALIDTIME=? where tableName=? and VALIDTIME=?";
        Object[] args = new Object[3];
        int[] argTypes = new int[3];
        args[0] = invalidtime;
        argTypes[0] = 91;
        args[1] = tableName;
        argTypes[1] = 12;
        args[2] = BaseDataConsts.VERSION_MIN_DATE;
        argTypes[2] = 91;
        this.jdbcTemplate.update(sql, args, argTypes);
    }

    protected void modifyValidTime(String tableName, Date newValidTime, Date oldValidTime) {
        String sql = " update " + tableName + " set VER=?,VALIDTIME=? where VALIDTIME=?";
        Object[] args = new Object[3];
        int[] argTypes = new int[3];
        args[0] = System.currentTimeMillis();
        argTypes[0] = 2;
        args[1] = newValidTime;
        argTypes[1] = 91;
        args[2] = oldValidTime;
        argTypes[2] = 91;
        this.jdbcTemplate.update(sql, args, argTypes);
    }

    private void addDataToVer(int year, List<BaseDataDTO> dataList, BaseDataDefineDO baseDataDefineDO, int versionFlag) throws Exception {
        BaseDataVersionDO versionDO = null;
        if (versionFlag == 1) {
            versionDO = this.addVersion(baseDataDefineDO, year);
        }
        BaseDataBatchOptDTO baseDataBatchOptDTO = new BaseDataBatchOptDTO();
        baseDataBatchOptDTO.setHighTrustability(true);
        ArrayList<BaseDataDTO> dataList2 = new ArrayList<BaseDataDTO>();
        for (BaseDataDTO baseDataDTO : dataList) {
            dataList2.add(baseDataDTO);
        }
        baseDataBatchOptDTO.setDataList(dataList2);
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(baseDataDefineDO.getName());
        if (versionDO != null) {
            queryParam.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
            queryParam.setVersionDate(versionDO.getValidtime());
        }
        baseDataBatchOptDTO.setQueryParam(queryParam);
        this.baseDataClient.sync(baseDataBatchOptDTO);
    }

    private void addDataToVerBySql(int year, List<BaseDataDTO> dataList, BaseDataDefineDO baseDataDefineDO, int versionFlag) throws Exception {
        BaseDataVersionDO versionDO = null;
        if (versionFlag == 1) {
            versionDO = this.addVersion(baseDataDefineDO, year);
        }
        BigDecimal ver = new BigDecimal(System.currentTimeMillis());
        int order = 0;
        for (BaseDataDTO bd : dataList) {
            bd.setId(UUID.randomUUID());
            bd.setVer(ver);
            bd.setObjectcode(bd.getCode());
            bd.setTableName(baseDataDefineDO.getName());
            bd.setParentcode("-");
            bd.setParents(bd.getCode());
            bd.setUnitcode("-");
            bd.setStopflag(Integer.valueOf(0));
            bd.setRecoveryflag(Integer.valueOf(0));
            bd.setOrdinal(new BigDecimal(order));
            bd.setValidtime(versionDO == null ? BaseDataConsts.VERSION_MIN_DATE : versionDO.getValidtime());
            bd.setInvalidtime(versionDO == null ? BaseDataConsts.VERSION_MAX_DATE : versionDO.getInvalidtime());
            UserLoginDTO user = ShiroUtil.getUser();
            if (user != null) {
                bd.setCreateuser(user.getId());
            }
            bd.setCreatetime(new Date());
            this.baseDataDao.add(bd);
            ++order;
        }
        this.updateCache(dataList.get(0));
    }
}

