/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package nr.midstore.core.internal.param.service;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.midstore.core.definition.bean.MidstoreContext;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreBaseDataDTO;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreBaseDataService;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.internal.definition.MidstoreBaseDataDO;
import nr.midstore.core.internal.publish.service.MidstorePublishTaskServiceImpl;
import nr.midstore.core.param.service.IMidstoreBatchParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MidstoreBatchParamServiceImpl
implements IMidstoreBatchParamService {
    private static final Logger logger = LoggerFactory.getLogger(MidstorePublishTaskServiceImpl.class);
    @Autowired
    private IMidstoreSchemeService midstoreSchemeSevice;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoSevice;
    @Autowired
    private IMidstoreOrgDataFieldService orgDataFieldService;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IMidstoreBaseDataService baseDataService;
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;

    @Override
    public MidstoreResultObject doLinkBaseDataFromFields(String midstoreSchemeKey, AsyncTaskMonitor monitor) throws MidstoreException {
        MidstoreResultObject result = new MidstoreResultObject();
        result.setSuccess(false);
        MidstoreContext context = new MidstoreContext(midstoreSchemeKey);
        context.setAsyncMonitor(monitor);
        if (!this.checkSchemeExist(context, result)) {
            return result;
        }
        this.linkBaseDataFromFields(context, result);
        result.setSuccess(true);
        return result;
    }

    private boolean linkBaseDataFromFields(MidstoreContext context, MidstoreResultObject result) throws MidstoreException {
        if (context.getAsyncMonitor() != null) {
            context.getAsyncMonitor().progressAndMessage(0.1, "\u52a0\u8f7d\u6307\u6807\u4fe1\u606f");
        }
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        MidstoreFieldDTO fieldParam = new MidstoreFieldDTO();
        fieldParam.setSchemeKey(context.getSchemeKey());
        List<MidstoreFieldDTO> fields = this.fieldService.list(fieldParam);
        if (fields == null || fields.size() == 0) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u7684\u6307\u6807\u672a\u5b9a\u4e49\uff01");
            result.setSuccess(false);
            return false;
        }
        ArrayList<String> baseEntityIds = new ArrayList<String>();
        HashSet<String> baseDataCodes = new HashSet<String>();
        ArrayList<MidstoreFieldDTO> linkFields = new ArrayList<MidstoreFieldDTO>();
        for (MidstoreFieldDTO field : fields) {
            DataField dataField;
            if (!StringUtils.isNotEmpty((String)field.getSrcFieldKey()) || (dataField = this.dataSchemeSevice.getDataField(field.getSrcFieldKey())) == null || !StringUtils.isNotEmpty((String)dataField.getRefDataEntityKey())) continue;
            baseEntityIds.add(dataField.getRefDataEntityKey());
            String baseCode = EntityUtils.getId((String)dataField.getRefDataEntityKey());
            if (!baseDataCodes.contains(baseCode)) {
                baseDataCodes.add(baseCode);
            }
            linkFields.add(field);
        }
        if (context.getAsyncMonitor() != null) {
            context.getAsyncMonitor().progressAndMessage(0.3, "\u67e5\u627e\u6307\u6807\u5173\u8054\u7684\u57fa\u7840\u6570\u636e");
        }
        if (baseDataCodes.size() > 0) {
            MidstoreBaseDataDTO param = new MidstoreBaseDataDTO();
            param.setSchemeKey(context.getSchemeKey());
            List<MidstoreBaseDataDTO> list = this.baseDataService.list(param);
            Map<Object, Object> oldBaseDataMap = null;
            oldBaseDataMap = list != null && list.size() > 0 ? list.stream().collect(Collectors.toMap(MidstoreBaseDataDO::getCode, MidstoreBaseDataDTO2 -> MidstoreBaseDataDTO2)) : new HashMap();
            ArrayList<MidstoreBaseDataDTO> addList = new ArrayList<MidstoreBaseDataDTO>();
            for (String baseCode : baseDataCodes) {
                BaseDataDefineDO baseDataDefine;
                if (oldBaseDataMap.containsKey(baseCode) || (baseDataDefine = this.queryBaseDatadefine(baseCode)) == null) continue;
                MidstoreBaseDataDTO dto = new MidstoreBaseDataDTO();
                dto.setCode(baseCode);
                dto.setTitle(baseDataDefine.getTitle());
                dto.setOrder(OrderGenerator.newOrder());
                dto.setSchemeKey(context.getSchemeKey());
                dto.setSrcBaseDataKey(baseCode);
                addList.add(dto);
            }
            if (context.getAsyncMonitor() != null) {
                context.getAsyncMonitor().progressAndMessage(0.8, "\u4fdd\u5b58\u5230\u4ea4\u6362\u65b9\u6848");
            }
            logger.info("\u6307\u6807\u6570\uff1a" + String.valueOf(fields.size()) + ",\u5173\u8054\u679a\u4e3e\u6307\u6807\u6570" + String.valueOf(linkFields.size()) + ",\u5173\u8054\u679a\u4e3e\u6570\uff1a" + String.valueOf(baseDataCodes.size()) + ",\u65b0\u589e\u5230\u4ea4\u6362\u65b9\u6848\u679a\u4e3e\u6570\uff1a" + String.valueOf(addList.size()));
            if (addList.size() > 0) {
                this.baseDataService.batchAdd(addList);
            }
        }
        return true;
    }

    private BaseDataDefineDO queryBaseDatadefine(String baseName) {
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setName(baseName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        BaseDataDefineDO baseDefine = this.baseDataDefineClient.get(param);
        return baseDefine;
    }

    private boolean checkSchemeExist(MidstoreContext context, MidstoreResultObject result) {
        MidstoreSchemeDTO midScheme = context.getMidstoreScheme();
        if (midScheme == null) {
            midScheme = this.midstoreSchemeSevice.getByKey(context.getSchemeKey());
            context.setMidstoreScheme(midScheme);
        }
        if (midScheme == null) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728\uff01");
            result.setSuccess(false);
            return false;
        }
        MidstoreSchemeInfoDTO schemeInfo = context.getSchemeInfo();
        if (schemeInfo == null) {
            schemeInfo = this.schemeInfoSevice.getBySchemeKey(context.getSchemeKey());
            context.setSchemeInfo(schemeInfo);
        }
        if (schemeInfo == null) {
            result.setMessage("\u4ea4\u6362\u65b9\u6848\u6269\u5c55\u4fe1\u606f\u4e0d\u5b58\u5728\uff01");
            result.setSuccess(false);
            return false;
        }
        return true;
    }
}

