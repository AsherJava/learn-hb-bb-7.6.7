/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties
 *  com.jiuqi.dc.base.common.utils.Pair
 *  com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO
 *  com.jiuqi.dc.datamapping.client.dto.RefChangeDTO
 *  com.jiuqi.dc.datamapping.client.dto.RefChangeHandleParamDTO
 *  com.jiuqi.dc.datamapping.client.dto.RefChangePairDTO
 *  com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.common.RuleType
 *  com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.dc.datamapping.impl.service.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.intf.impl.ServiceConfigProperties;
import com.jiuqi.dc.base.common.utils.Pair;
import com.jiuqi.dc.datamapping.client.dto.DataRefSaveDTO;
import com.jiuqi.dc.datamapping.client.dto.RefChangeDTO;
import com.jiuqi.dc.datamapping.client.dto.RefChangeHandleParamDTO;
import com.jiuqi.dc.datamapping.client.dto.RefChangePairDTO;
import com.jiuqi.dc.datamapping.client.vo.DataRefSaveVO;
import com.jiuqi.dc.datamapping.impl.dao.DataRefConfigureDao;
import com.jiuqi.dc.datamapping.impl.enums.RefHandleStatus;
import com.jiuqi.dc.datamapping.impl.gather.IDataRefCheckerGather;
import com.jiuqi.dc.datamapping.impl.intf.IDataRefChecker;
import com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService;
import com.jiuqi.dc.datamapping.impl.service.RefChangeService;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.enums.IsolationStrategy;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefChangeServiceImpl
implements RefChangeService {
    @Autowired
    private DataRefConfigureService dataRefService;
    @Autowired
    private TaskHandlerFactory TaskHandlerFactory;
    @Autowired
    private IDataRefCheckerGather checkerGather;
    @Autowired
    private BaseDataRefDefineService baseDataRefDefineService;
    @Autowired
    private DataRefConfigureDao dataRefConfigureDao;
    @Autowired
    private ServiceConfigProperties prop;

    @Override
    public DataRefSaveVO handleRefChange(RefChangeDTO changeParam) {
        this.beforeHandle(changeParam);
        DataRefSaveVO result = this.saveRefData(changeParam);
        if (result.getSuccess() != 1) {
            return result;
        }
        if (!CollectionUtils.isEmpty((Collection)result.getUpdateData())) {
            if ("MD_ORG".equals(changeParam.getTableName())) {
                this.updateDcUnitCode(changeParam);
            }
            this.handleBizData(changeParam);
        }
        return result;
    }

    @Override
    public DataRefSaveVO handleRefBatchChange(String dataSchemeCode, String tableName, Boolean customFlag, List<RefChangeDTO> changeParams) {
        DataRefSaveDTO saveParam = new DataRefSaveDTO();
        saveParam.setDataSchemeCode(dataSchemeCode);
        saveParam.setTableName(tableName);
        saveParam.setCustomFlag(customFlag);
        saveParam.setData(new ArrayList(changeParams.size()));
        HashMap<String, RefChangeDTO> changeParamMap = new HashMap<String, RefChangeDTO>(changeParams.size());
        for (RefChangeDTO changeParam : changeParams) {
            try {
                this.beforeHandle(changeParam);
            }
            catch (Exception e) {
                continue;
            }
            saveParam.getData().add(changeParam.getRefData());
            changeParamMap.put(changeParam.getRefData().getOdsCode(), changeParam);
        }
        DataRefSaveVO result = this.dataRefService.save(saveParam);
        if (!CollectionUtils.isEmpty((Collection)result.getUpdateData())) {
            this.handleBizDatas(dataSchemeCode, tableName, customFlag, result.getUpdateData().stream().map(ref -> (RefChangeDTO)changeParamMap.get(ref.getOdsCode())).collect(Collectors.toList()));
        }
        return result;
    }

    private void beforeHandle(RefChangeDTO changeParam) {
        Pair<String, Integer> pair;
        Assert.isNotNull((Object)changeParam, (String)"\u6620\u5c04\u53d8\u66f4\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)changeParam.getDataSchemeCode(), (String)"\u6570\u636e\u6620\u5c04\u65b9\u6848\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)changeParam.getTableName(), (String)"\u6620\u5c04\u6570\u636e\u8868\u540d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)changeParam.getRefData(), (String)"\u6620\u5c04\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)changeParam.getOldRefData(), (String)"\u539f\u6620\u5c04\u6570\u636e\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        BaseDataMappingDefineDTO changeDimDefine = this.baseDataRefDefineService.getByCode(changeParam.getDataSchemeCode(), changeParam.getTableName());
        if (!RuleType.ALL.getCode().equals(changeDimDefine.getRuleType())) {
            return;
        }
        if (StringUtils.isEmpty((CharSequence)changeParam.getRefData().getCode()) && (pair = this.checkHasRef(changeParam)) != null) {
            throw new IllegalArgumentException(String.format("\u5f53\u524d\u6570\u636e\u6620\u5c04\u5728\u3010%1$d\u3011\u5e74\u5ea6\u7684\u3010%2$s\u3011\u5355\u4f4d\u4e0b\u5b58\u5728\u4e1a\u52a1\u6570\u636e\u5f15\u7528\uff0c\u4e0d\u5141\u8bb8\u5220\u9664", pair.getSecond(), pair.getFirst()));
        }
    }

    private Pair<String, Integer> checkHasRef(RefChangeDTO changeParam) {
        IDataRefChecker checker = this.checkerGather.getByTableName(changeParam.getTableName());
        if (Objects.isNull(checker)) {
            return null;
        }
        return checker.checkHasRef(changeParam);
    }

    private DataRefSaveVO saveRefData(RefChangeDTO changeParam) {
        DataRefSaveDTO saveParam = new DataRefSaveDTO();
        saveParam.setDataSchemeCode(changeParam.getDataSchemeCode());
        saveParam.setTableName(changeParam.getTableName());
        saveParam.setData(new ArrayList());
        saveParam.getData().add(changeParam.getRefData());
        saveParam.setCustomFlag(changeParam.getCustomFlag());
        return this.dataRefService.save(saveParam);
    }

    private void handleBizData(RefChangeDTO changeParam) {
        this.handleBizDatas(changeParam.getDataSchemeCode(), changeParam.getTableName(), changeParam.getCustomFlag(), CollectionUtils.newArrayList((Object[])new RefChangeDTO[]{changeParam}));
    }

    private void handleBizDatas(String dataSchemeCode, String tableName, Boolean customFlag, List<RefChangeDTO> changeParams) {
        RefChangeHandleParamDTO preParam = new RefChangeHandleParamDTO();
        preParam.setDataSchemeCode(dataSchemeCode);
        preParam.setTableName(tableName);
        preParam.setCustomFlag(customFlag);
        preParam.setRefChangePairDatas((List)CollectionUtils.newArrayList());
        for (RefChangeDTO changeParam : changeParams) {
            if (changeParam.getRefData().getCode().equals(changeParam.getOldRefData().getCode())) continue;
            preParam.getRefChangePairDatas().add(new RefChangePairDTO(changeParam.getOldRefData(), changeParam.getRefData()));
        }
        if (!preParam.getRefChangePairDatas().isEmpty() && "DC".equals(this.prop.getServiceName())) {
            this.TaskHandlerFactory.getMainTaskHandlerClient().startTask("RefChangeHandle", JsonUtils.writeValueAsString((Object)preParam));
        }
    }

    @Override
    @OuterTransaction
    public void deletePendingData(String tableName, String id) {
        this.dataRefConfigureDao.updateHandleStatusById(tableName, id, RefHandleStatus.DELETED.getCode());
    }

    @OuterTransaction
    private void updateDcUnitCode(RefChangeDTO changeParam) {
        List dimList = this.baseDataRefDefineService.listBySchemeCode(changeParam.getDataSchemeCode());
        for (BaseDataMappingDefineDTO dim : dimList) {
            if (!IsolationStrategy.getIsolationFieldByCode((String)dim.getIsolationStrategy()).contains("DC_UNITCODE")) continue;
            ArrayList oldUnitCode = CollectionUtils.newArrayList((Object[])new String[]{changeParam.getOldRefData().getCode()});
            ArrayList newUnitCode = CollectionUtils.newArrayList((Object[])new String[]{changeParam.getRefData().getCode()});
            this.dataRefConfigureDao.batchUpdateDcUnitCode(dim.getCode(), changeParam.getDataSchemeCode(), oldUnitCode, newUnitCode);
        }
    }
}

