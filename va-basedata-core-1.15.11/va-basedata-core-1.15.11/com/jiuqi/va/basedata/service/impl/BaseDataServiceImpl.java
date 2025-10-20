/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDTO
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.extend.BaseDataAction
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.va.basedata.service.impl;

import com.jiuqi.va.basedata.common.BaseDataCoreI18nUtil;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataModifyService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataParamService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataQueryService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataSyncService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDTO;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.extend.BaseDataAction;
import com.jiuqi.va.feign.util.LogUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="vaBaseDataServiceImpl")
public class BaseDataServiceImpl
implements BaseDataService {
    private static final Logger logger = LoggerFactory.getLogger(BaseDataServiceImpl.class);
    @Autowired
    private BaseDataQueryService baseDataQueryService;
    @Autowired
    private BaseDataModifyService baseDataModifyService;
    @Autowired
    private BaseDataSyncService baseDataSyncService;
    @Autowired
    private BaseDataParamService baseDataParamService;
    @Autowired
    private BaseDataCacheService baseDataCacheService;

    private boolean bindUser() {
        if (ThreadContext.get((Object)"LOGIN_USER_KEY") != null) {
            return false;
        }
        UserLoginDTO user = ShiroUtil.getUser();
        if (user != null) {
            ShiroUtil.bindUser((UserLoginDTO)user);
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R exist(BaseDataDTO param) {
        boolean bindFlag = this.bindUser();
        try {
            R rs = this.baseDataParamService.loadExtendParam(param, BaseDataAction.Query);
            if (rs.getCode() != 0) {
                R r = rs;
                return r;
            }
            R r = this.baseDataQueryService.exist(param);
            return r;
        }
        finally {
            if (bindFlag) {
                ShiroUtil.unbindUser();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int count(BaseDataDTO param) {
        boolean bindFlag = this.bindUser();
        try {
            R rs = this.baseDataParamService.loadExtendParam(param, BaseDataAction.Query);
            if (rs.getCode() != 0) {
                int n = 0;
                return n;
            }
            if (param.containsKey((Object)"_InterceptorDataList")) {
                int n = ((PageVO)param.get((Object)"_InterceptorDataList")).getTotal();
                return n;
            }
            int n = this.baseDataQueryService.count(param);
            return n;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            int n = 0;
            return n;
        }
        finally {
            if (bindFlag) {
                ShiroUtil.unbindUser();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PageVO<BaseDataDO> list(BaseDataDTO param) {
        boolean bindFlag = this.bindUser();
        try {
            Map<String, String> sensitiveFields;
            R rs = this.baseDataParamService.loadExtendParam(param, BaseDataAction.Query);
            if (rs.getCode() != 0) {
                PageVO page = new PageVO(true);
                page.setRs(rs);
                PageVO pageVO = page;
                return pageVO;
            }
            PageVO<BaseDataDO> page = null;
            page = param.containsKey((Object)"_InterceptorDataList") ? (PageVO<BaseDataDO>)param.get((Object)"_InterceptorDataList") : this.baseDataQueryService.list(param);
            if (page != null && page.getTotal() > 0 && (sensitiveFields = this.baseDataParamService.getSensitiveFields((BaseDataDO)param)) != null && !sensitiveFields.isEmpty()) {
                page.getRs().put("sensitiveFields", sensitiveFields);
            }
            PageVO<BaseDataDO> pageVO = page;
            return pageVO;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            PageVO<BaseDataDO> pageVO = null;
            return pageVO;
        }
        finally {
            if (bindFlag) {
                ShiroUtil.unbindUser();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, Object[]> columnValueList(BaseDataColumnValueDTO param) {
        boolean bindFlag = this.bindUser();
        try {
            Map<String, Object[]> map = this.baseDataQueryService.columnValueList(param);
            return map;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            Map<String, Object[]> map = null;
            return map;
        }
        finally {
            if (bindFlag) {
                ShiroUtil.unbindUser();
            }
        }
    }

    @Override
    public R add(BaseDataDTO baseDataDTO) {
        R rs = this.baseDataParamService.loadExtendParam(baseDataDTO, BaseDataAction.Add);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.containsKey((Object)"_InterceptorDataAdd")) {
            return (R)baseDataDTO.get((Object)"_InterceptorDataAdd");
        }
        if (!baseDataDTO.isCacheSyncDisable()) {
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u65b0\u589e", (String)baseDataDTO.getTableName(), (String)baseDataDTO.getCode(), (String)baseDataDTO.getName());
        }
        return this.baseDataModifyService.add(baseDataDTO);
    }

    @Override
    public R batchAdd(BaseDataBatchOptDTO baseDataDTO) {
        R rs = this.baseDataParamService.loadBatchOptExtendParam(baseDataDTO, BaseDataAction.Add);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.getQueryParam().containsKey((Object)"_InterceptorDataAdd")) {
            return (R)baseDataDTO.getQueryParam().get((Object)"_InterceptorDataAdd");
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u6279\u91cf\u65b0\u589e", (String)baseDataDTO.getQueryParam().getTableName(), (String)"", (String)"");
        return this.baseDataSyncService.batchAdd(baseDataDTO);
    }

    @Override
    public R update(BaseDataDTO baseDataDTO) {
        R rs = this.baseDataParamService.loadExtendParam(baseDataDTO, BaseDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.containsKey((Object)"_InterceptorDataUpdate")) {
            return (R)baseDataDTO.get((Object)"_InterceptorDataUpdate");
        }
        if (!baseDataDTO.isCacheSyncDisable()) {
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u66f4\u65b0", (String)baseDataDTO.getTableName(), (String)baseDataDTO.getCode(), (String)baseDataDTO.getName());
        }
        return this.baseDataModifyService.update(baseDataDTO);
    }

    @Override
    public R batchUpdate(BaseDataBatchOptDTO baseDataDTO) {
        R rs = this.baseDataParamService.loadBatchOptExtendParam(baseDataDTO, BaseDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.getQueryParam().containsKey((Object)"_InterceptorDataUpdate")) {
            return (R)baseDataDTO.getQueryParam().get((Object)"_InterceptorDataUpdate");
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u6279\u91cf\u66f4\u65b0", (String)baseDataDTO.getQueryParam().getTableName(), (String)"", (String)"");
        return this.baseDataSyncService.batchUpdate(baseDataDTO);
    }

    @Override
    public R remove(BaseDataDTO baseDataDTO) {
        R rs = this.baseDataParamService.loadExtendParam(baseDataDTO, BaseDataAction.Remove);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.containsKey(BaseDataConsts.INTERCEPTOR_DATA_REMOVE)) {
            return (R)baseDataDTO.get(BaseDataConsts.INTERCEPTOR_DATA_REMOVE);
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u5220\u9664", (String)baseDataDTO.getTableName(), (String)baseDataDTO.getCode(), (String)baseDataDTO.getName());
        return this.baseDataModifyService.remove(baseDataDTO);
    }

    @Override
    public R batchRemove(BaseDataBatchOptDTO baseDataDTO) {
        R rs = this.baseDataParamService.loadBatchOptExtendParam(baseDataDTO, BaseDataAction.Remove);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.getQueryParam().containsKey(BaseDataConsts.INTERCEPTOR_DATA_REMOVE)) {
            return (R)baseDataDTO.getQueryParam().get(BaseDataConsts.INTERCEPTOR_DATA_REMOVE);
        }
        Object forceDelete = baseDataDTO.getExtInfo("forceDelete");
        if (forceDelete != null && ((Boolean)forceDelete).booleanValue()) {
            return this.baseDataSyncService.forceBatchRemove(baseDataDTO);
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u6279\u91cf\u5220\u9664", (String)baseDataDTO.getQueryParam().getTableName(), (String)"", (String)"");
        return this.baseDataModifyService.batchRemove(baseDataDTO);
    }

    @Override
    public R stop(BaseDataDTO baseDataDTO) {
        baseDataDTO.put("_UpdateDataStop", (Object)true);
        R rs = this.baseDataParamService.loadExtendParam(baseDataDTO, BaseDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.containsKey((Object)"_InterceptorDataUpdate")) {
            return (R)baseDataDTO.get((Object)"_InterceptorDataUpdate");
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)(baseDataDTO.getStopflag() == 1 ? "\u505c\u7528" : "\u542f\u7528"), (String)baseDataDTO.getTableName(), (String)baseDataDTO.getCode(), (String)"");
        return this.baseDataModifyService.stop(baseDataDTO);
    }

    @Override
    public R batchStop(BaseDataBatchOptDTO baseDataDTO) {
        this.baseDataParamService.initBatchOptExtendParam(baseDataDTO, "_UpdateDataStop");
        R rs = this.baseDataParamService.loadBatchOptExtendParam(baseDataDTO, BaseDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.getQueryParam().containsKey((Object)"_InterceptorDataUpdate")) {
            return (R)baseDataDTO.getQueryParam().get((Object)"_InterceptorDataUpdate");
        }
        List list = baseDataDTO.getDataList();
        if (list != null && !list.isEmpty()) {
            int state = ((BaseDataDO)list.get(0)).getStopflag();
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)(state == 1 ? "\u6279\u91cf\u505c\u7528" : "\u6279\u91cf\u542f\u7528"), (String)baseDataDTO.getQueryParam().getTableName(), (String)"", (String)"");
        }
        return this.baseDataModifyService.batchStop(baseDataDTO);
    }

    @Override
    public R recover(BaseDataDTO baseDataDTO) {
        baseDataDTO.put("_UpdateDataRecover", (Object)true);
        R rs = this.baseDataParamService.loadExtendParam(baseDataDTO, BaseDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.containsKey((Object)"_InterceptorDataUpdate")) {
            return (R)baseDataDTO.get((Object)"_InterceptorDataUpdate");
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u8fd8\u539f", (String)baseDataDTO.getTableName(), (String)"", (String)"");
        return this.baseDataModifyService.recover(baseDataDTO);
    }

    @Override
    public R batchRecover(BaseDataBatchOptDTO baseDataDTO) {
        this.baseDataParamService.initBatchOptExtendParam(baseDataDTO, "_UpdateDataRecover");
        R rs = this.baseDataParamService.loadBatchOptExtendParam(baseDataDTO, BaseDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.getQueryParam().containsKey((Object)"_InterceptorDataUpdate")) {
            return (R)baseDataDTO.getQueryParam().get((Object)"_InterceptorDataUpdate");
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u6279\u91cf\u8fd8\u539f", (String)baseDataDTO.getQueryParam().getTableName(), (String)"", (String)"");
        return this.baseDataModifyService.batchRecover(baseDataDTO);
    }

    @Override
    public R move(BaseDataMoveDTO baseDataDTO) {
        R rs = this.baseDataParamService.loadExtendParam(baseDataDTO.getQueryParam(), BaseDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u79fb\u52a8", (String)baseDataDTO.getQueryParam().getTableName(), (String)"", (String)"");
        return this.baseDataModifyService.move(baseDataDTO);
    }

    @Override
    public R clearRecovery(BaseDataDTO param) {
        return this.baseDataModifyService.clearRecovery(param);
    }

    @Override
    public R changeShare(BaseDataBatchOptDTO baseDataDTO) {
        this.baseDataParamService.initBatchOptExtendParam(baseDataDTO, "_UpdateDataChangeShare");
        R rs = this.baseDataParamService.loadBatchOptExtendParam(baseDataDTO, BaseDataAction.Update);
        if (rs.getCode() != 0) {
            return rs;
        }
        if (baseDataDTO.getQueryParam().containsKey((Object)"_InterceptorDataUpdate")) {
            return (R)baseDataDTO.getQueryParam().get((Object)"_InterceptorDataUpdate");
        }
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u9694\u79bb\u8f6c\u5171\u4eab", (String)baseDataDTO.getQueryParam().getTableName(), (String)"", (String)"");
        return this.baseDataModifyService.changeShare(baseDataDTO);
    }

    @Override
    public R getNextCode(BaseDataDTO param) {
        R rs = this.baseDataParamService.loadExtendParam(param, BaseDataAction.GetNextCode);
        if (rs.getCode() == 0 && param.getCode() != null) {
            rs.put("bdcode", (Object)param.getCode());
        }
        return rs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public R sync(BaseDataBatchOptDTO baseDataDTO) {
        boolean bindFlag = this.bindUser();
        try {
            this.baseDataParamService.initBatchOptExtendParam(baseDataDTO, "_UpdateDataSync");
            R rs = this.baseDataParamService.loadBatchOptExtendParam(baseDataDTO, BaseDataAction.Update);
            if (rs.getCode() != 0) {
                R r = rs;
                return r;
            }
            if (baseDataDTO.getQueryParam().containsKey((Object)"_InterceptorDataUpdate")) {
                R r = (R)baseDataDTO.getQueryParam().get((Object)"_InterceptorDataUpdate");
                return r;
            }
            LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u6570\u636e\u540c\u6b65", (String)baseDataDTO.getQueryParam().getTableName(), (String)"", (String)"");
            R r = this.baseDataSyncService.sync(baseDataDTO);
            return r;
        }
        finally {
            if (bindFlag) {
                ShiroUtil.unbindUser();
            }
        }
    }

    @Override
    public R fastUpDown(BaseDataDTO baseDataDTO) {
        R rs = this.baseDataParamService.modifyVersionCheck(baseDataDTO);
        if (rs.getCode() != 0) {
            return rs;
        }
        int j = 0;
        List baseDataCodes = baseDataDTO.getBaseDataCodes();
        String sortCode = null;
        ArrayList<BaseDataDO> sortList = new ArrayList<BaseDataDO>();
        BaseDataDTO oldBaseDataDTO = new BaseDataDTO();
        oldBaseDataDTO.setTableName(baseDataDTO.getTableName());
        oldBaseDataDTO.setVersionDate(baseDataDTO.getVersionDate());
        oldBaseDataDTO.setStopflag(Integer.valueOf(-1));
        oldBaseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        oldBaseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        oldBaseDataDTO.setParentcode(baseDataDTO.getParentcode());
        oldBaseDataDTO.setUnitcode(baseDataDTO.getUnitcode());
        List dataList = this.list(oldBaseDataDTO).getRows();
        BigDecimal[] ordinals = new BigDecimal[dataList.size()];
        String targetCode = baseDataDTO.getCode();
        BaseDataDO targetObj = null;
        BaseDataDO tempObj = null;
        for (int i = 0; i < dataList.size(); ++i) {
            tempObj = (BaseDataDO)dataList.get(i);
            ordinals[i] = tempObj.getOrdinal();
            if (!tempObj.getCode().equals(targetCode)) continue;
            targetObj = tempObj;
        }
        Iterator it = dataList.iterator();
        while (it.hasNext() && j < baseDataCodes.size()) {
            tempObj = (BaseDataDO)it.next();
            sortCode = (String)baseDataCodes.get(j);
            if (!tempObj.getCode().equals(sortCode) || tempObj.getCode().equals(targetCode)) continue;
            sortList.add(tempObj);
            it.remove();
            ++j;
        }
        dataList.addAll(dataList.indexOf(targetObj), sortList);
        ArrayList<BaseDataDO> endList = new ArrayList<BaseDataDO>();
        for (int i = 0; i < dataList.size(); ++i) {
            tempObj = (BaseDataDO)dataList.get(i);
            if (ordinals[i].compareTo(tempObj.getOrdinal()) == 0) continue;
            BaseDataDO endData = new BaseDataDO();
            endData.putAll((Map)tempObj);
            endData.setOrdinal(ordinals[i]);
            endList.add(endData);
        }
        if (endList.isEmpty()) {
            return R.ok((String)"\u672a\u53d1\u751f\u79fb\u52a8");
        }
        BaseDataBatchOptDTO baseDataBatchOptDTO = new BaseDataBatchOptDTO();
        baseDataBatchOptDTO.setQueryParam(oldBaseDataDTO);
        baseDataBatchOptDTO.setDataList(endList);
        baseDataBatchOptDTO.setHighTrustability(true);
        LogUtil.add((String)"\u57fa\u7840\u6570\u636e\u6267\u884c", (String)"\u5feb\u901f\u79fb\u52a8", (String)baseDataDTO.getTableName(), (String)"", (String)"");
        return this.baseDataSyncService.sync(baseDataBatchOptDTO);
    }

    @Override
    public List<BaseDataDO> verDiffList(BaseDataDTO basedataDTO) {
        return this.baseDataQueryService.verDiffList(basedataDTO);
    }

    @Override
    public R initCache(BaseDataCacheDTO param) {
        BaseDataDTO bdParam = new BaseDataDTO();
        bdParam.setTenantName(param.getTenantName());
        bdParam.setTableName(param.getTableName());
        bdParam.setVersionDate(param.getVersionDate());
        this.baseDataCacheService.initCache(bdParam);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    @Override
    public R syncCache(BaseDataCacheDTO param) {
        BaseDataDTO bdParam = new BaseDataDTO();
        bdParam.setTenantName(param.getTenantName());
        bdParam.setTableName(param.getTableName());
        bdParam.setVersionDate(param.getVersionDate());
        if (param.getStartVer() != null) {
            bdParam.setQueryStartVer(param.getStartVer());
        } else {
            bdParam.setQueryStartVer(new BigDecimal(System.currentTimeMillis() - 1800000L));
        }
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(param.getTenantName());
        bdsc.setBaseDataDTO(bdParam);
        bdsc.setForceUpdate(param.isForceUpdate());
        this.baseDataCacheService.pushSyncMsg(bdsc);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }

    @Override
    public R cleanCache(BaseDataCacheDTO param) {
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(param.getTenantName());
        BaseDataDTO bdParam = new BaseDataDTO();
        bdParam.setTenantName(param.getTenantName());
        bdParam.setTableName(param.getTableName());
        if (param.getVersionDate() != null) {
            bdParam.setVersionDate(param.getVersionDate());
            bdsc.setRemove(true);
        } else {
            bdsc.setClean(true);
        }
        bdsc.setBaseDataDTO(bdParam);
        this.baseDataCacheService.pushSyncMsg(bdsc);
        return R.ok((String)BaseDataCoreI18nUtil.getOptSuccessMsg());
    }
}

