/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoVo
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.nr.definition.api.IParamLanguageController
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.conversion.conversionsystem.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionLogInfoDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionLogInfoEo;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionLogInfoService;
import com.jiuqi.gcreport.conversion.conversionsystem.vo.ConversionLogInfoVo;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionLogInfoServiceImpl
implements ConversionLogInfoService {
    @Autowired
    private ConversionLogInfoDao conversionLogInfoDao;
    @Autowired
    private IParamLanguageController languageController;

    @Override
    public void saveLogInfo(ConversionLogInfoEo infoEo) {
        this.conversionLogInfoDao.save(infoEo);
    }

    @Override
    public void updateLogInfo(ConversionLogInfoEo infoEo) {
        this.conversionLogInfoDao.update((BaseEntity)infoEo);
    }

    @Override
    public List<ConversionLogInfoVo> queryLogInfos(int pageSize, int pageNum) {
        StringBuilder sql = new StringBuilder();
        sql.append("select \n");
        sql.append(SqlUtils.getColumnsSqlByTableDefine((String)"GC_CONV_LOG", (String)"e"));
        sql.append(" from ").append("GC_CONV_LOG").append("  e \n");
        sql.append(" where 1=1  order by e.logtimetext desc \n");
        List conversionLogInfoEos = this.conversionLogInfoDao.selectEntityByPaging(sql.toString(), pageSize * pageNum, (pageNum + 1) * pageSize, new Object[0]);
        HashMap taskKey2Title = new HashMap();
        HashMap formSchemeKey2Title = new HashMap();
        HashMap currencyCode2Title = new HashMap();
        if (!CollectionUtils.isEmpty((Collection)conversionLogInfoEos)) {
            List<ConversionLogInfoVo> conversionLogInfoVos = conversionLogInfoEos.stream().map(conversionLogInfoEo -> {
                ConversionLogInfoVo vo = new ConversionLogInfoVo();
                BeanUtils.copyProperties(conversionLogInfoEo, vo);
                this.changeByLanguage(vo, taskKey2Title, formSchemeKey2Title, currencyCode2Title);
                vo.setSuccessState(GcI18nUtil.getMessage((String)(conversionLogInfoEo.getSuccessFlag() == 1 ? "gc.coversion.success.title" : "gc.coversion.fail.title")));
                return vo;
            }).collect(Collectors.toList());
            return conversionLogInfoVos;
        }
        return null;
    }

    @Override
    public long count() {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(1) AS NUM ");
        sql.append(" from ").append("GC_CONV_LOG").append("  e \n");
        List recordSet = this.conversionLogInfoDao.selectFirstList(Long.class, sql.toString(), new Object[0]);
        long count = (Long)recordSet.get(0);
        return count;
    }

    private void changeByLanguage(ConversionLogInfoVo vo, Map<String, String> taskKey2Title, Map<String, String> formSchemeKey2Title, Map<String, String> currencyCode2Title) {
        String dstCurrencyTitle;
        String srcCurrencyTitle;
        String formSchemeTitle;
        String taskTitle;
        if (taskKey2Title.containsKey(vo.getTaskId())) {
            taskTitle = taskKey2Title.get(vo.getTaskId());
        } else {
            taskTitle = this.languageController.getTaskTitle(vo.getTaskId(), null);
            if (StringUtils.isEmpty((String)taskTitle)) {
                taskTitle = vo.getTaskName();
            }
            taskKey2Title.put(vo.getTaskId(), taskTitle);
        }
        vo.setTaskName(taskTitle);
        if (formSchemeKey2Title.containsKey(vo.getSchemeId())) {
            formSchemeTitle = formSchemeKey2Title.get(vo.getSchemeId());
        } else {
            formSchemeTitle = this.languageController.getFormSchemeTitle(vo.getSchemeId(), null);
            if (StringUtils.isEmpty((String)formSchemeTitle)) {
                formSchemeTitle = vo.getSchemeName();
            }
            formSchemeKey2Title.put(vo.getSchemeId(), formSchemeTitle);
        }
        vo.setSchemeName(formSchemeTitle);
        if (currencyCode2Title.containsKey(vo.getSrcCurrency())) {
            srcCurrencyTitle = currencyCode2Title.get(vo.getSrcCurrency());
        } else {
            srcCurrencyTitle = this.getCurrencyTitle(vo.getSrcCurrency());
            currencyCode2Title.put(vo.getSrcCurrency(), srcCurrencyTitle);
        }
        vo.setSrcCurrency(srcCurrencyTitle);
        if (currencyCode2Title.containsKey(vo.getDstCurrency())) {
            dstCurrencyTitle = currencyCode2Title.get(vo.getDstCurrency());
        } else {
            dstCurrencyTitle = this.getCurrencyTitle(vo.getDstCurrency());
            currencyCode2Title.put(vo.getDstCurrency(), dstCurrencyTitle);
        }
        vo.setDstCurrency(dstCurrencyTitle);
    }

    private String getCurrencyTitle(String currencyCode) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName("MD_CURRENCY");
        baseDataDTO.setAuthType(BaseDataOption.AuthType.ACCESS);
        baseDataDTO.setCode(currencyCode);
        PageVO list = ((BaseDataClient)SpringContextUtils.getBean(BaseDataClient.class)).list(baseDataDTO);
        if (list.getTotal() != 0) {
            return ((BaseDataDO)list.getRows().get(0)).getLocalizedName();
        }
        return currencyCode;
    }
}

