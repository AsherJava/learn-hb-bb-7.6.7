/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  org.springframework.data.redis.core.RedisTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.zb.scheme.common.OperationType;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.MetaItem;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.core.ZbGroup;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbCheckItemDao;
import com.jiuqi.nr.zb.scheme.internal.dto.ValidationRuleDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbCheckItemDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbGroupDTO;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbInfoDTO;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckQueryService;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbGenerateService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.utils.JsonUtils;
import com.jiuqi.nr.zb.scheme.web.vo.GenerateZbParam;
import com.jiuqi.nr.zb.scheme.web.vo.GenerateZbResult;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormSchemeZbGenerateServiceImpl
implements IFormSchemeZbGenerateService {
    private static final String MODULE = "\u6307\u6807\u4f53\u7cfb";
    private static final String RESULT_CACHE_KEY_PREFIX = "_ZBCHECK_RESULT_";
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeZbGenerateServiceImpl.class);
    @Autowired
    private IFormSchemeZbCheckQueryService formSchemeZbCheckQueryService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IZbCheckItemDao zbCheckItemDao;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public GenerateZbResult generateZbInfo(GenerateZbParam param) {
        Assert.notNull((Object)param, "generateZbParam is null");
        GenerateContext generateContext = new GenerateContext(param);
        this.info("\u5f00\u59cb\u751f\u6210\u6307\u6807\u4f53\u7cfb\u6307\u6807\uff0ccheckKey:{}", generateContext.getCheckKey());
        Map<String, ZbGroup> zbGroupTitleMap = this.zbSchemeService.listZbGroupByVersion(generateContext.getZbSchemeVersionKey()).stream().collect(Collectors.toMap(MetaItem::getTitle, Function.identity()));
        generateContext.setZbGroupTitleMap(zbGroupTitleMap);
        this.designTimeViewController.listFormGroupByFormScheme(param.getFormSchemeKey()).forEach(formGroupDefine -> this.oneFormGroup((FormGroupDefine)formGroupDefine, generateContext));
        List<ZbInfo> addZbInfos = generateContext.getAddZbInfos();
        List<ZbInfo> updateZbInfos = generateContext.getUpdateZbInfos();
        this.zbSchemeService.insertZbGroup(generateContext.getAddZbGroups());
        this.zbSchemeService.insertZbInfo(addZbInfos);
        this.zbSchemeService.updateZbInfo(updateZbInfos);
        int addSize = addZbInfos.size();
        int updateSize = updateZbInfos.size();
        int deleteCount = this.zbCheckItemDao.deleteByCheck(generateContext.getCheckKey());
        this.info("\u751f\u6210\u7ed3\u675f\uff0c\u65b0\u589e\u4e86{}\u4e2a\u6307\u6807\uff0c\u4fee\u6539\u4e86{}\u4e2a\u6307\u6807", addSize, updateSize);
        this.info("\u5220\u9664\u6307\u6807\u68c0\u67e5\u8bb0\u5f55\uff0c\u5220\u9664\u4e86{}\u6761\u8bb0\u5f55", deleteCount);
        LogHelper.info((String)MODULE, (String)"\u6839\u636e\u62a5\u8868\u65b9\u6848\u9006\u5411\u751f\u6210\u6307\u6807\u4f53\u7cfb\u6307\u6807", (String)String.format("\u65b0\u589e\u4e86%d\u4e2a\u6307\u6807\uff0c\u4fee\u6539\u4e86%d\u4e2a\u6307\u6807", addSize, updateSize));
        return generateContext.getResult();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void generateZbInfo(GenerateZbParam param, AsyncTaskMonitor monitor) throws Exception {
        GenerateContext generateContext = new GenerateContext();
        try {
            Assert.notNull((Object)param, "generateZbParam is null");
            this.info("\u5f00\u59cb\u751f\u6210\u6307\u6807\u4f53\u7cfb\u6307\u6807\uff0ccheckKey:{}", param.getCheckKey());
            this.info("\u62a5\u8868\u65b9\u6848:{}", param.getFormSchemeKey());
            this.info("\u6307\u6807\u4f53\u7cfb:{}", param.getZbSchemeKey());
            this.info("\u6307\u6807\u4f53\u7cfb\u7248\u672c:{}", param.getZbSchemeVersionKey());
            List designFormDefines = this.designTimeViewController.listFormByFormScheme(param.getFormSchemeKey());
            if (CollectionUtils.isEmpty(designFormDefines)) {
                monitor.finish("\u751f\u6210\u5b8c\u6210", (Object)"\u672a\u67e5\u627e\u5230\u62a5\u8868");
                return;
            }
            generateContext.setParam(param);
            generateContext.setMonitor(monitor);
            generateContext.setStep(0.9f / (float)designFormDefines.size());
            generateContext.buildZbGroupTitle(this.zbSchemeService);
            this.arrangeZb(generateContext);
            generateContext.setStep(0.025f);
            this.storageZbInfo(generateContext);
            this.updateDataSchemeAndDataField(generateContext);
            this.deleteCheckResult(generateContext);
            this.saveResult(generateContext);
            this.saveLog(generateContext);
            generateContext.over(null);
        }
        catch (Exception e) {
            generateContext.getMonitor().error("\u751f\u6210\u5931\u8d25", (Throwable)e);
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public String getResult(String checkKey) throws Exception {
        String resultCacheKey = RESULT_CACHE_KEY_PREFIX + checkKey;
        String result = (String)this.redisTemplate.opsForValue().get((Object)resultCacheKey);
        this.redisTemplate.delete((Object)resultCacheKey);
        return result;
    }

    private void arrangeZb(GenerateContext context) {
        this.designTimeViewController.listFormGroupByFormScheme(context.getFormSchemeKey()).forEach(formGroupDefine -> this.oneFormGroup((FormGroupDefine)formGroupDefine, context));
    }

    private void storageZbInfo(GenerateContext context) {
        context.updateProgressMessage("\u6307\u6807\u6574\u7406\u5b8c\u6bd5\uff0c\u6b63\u5728\u5165\u5e93");
        List<ZbInfo> addZbInfos = context.getAddZbInfos();
        List<ZbInfo> updateZbInfos = context.getUpdateZbInfos();
        this.zbSchemeService.insertZbGroup(context.getAddZbGroups());
        this.zbSchemeService.insertZbInfo(addZbInfos);
        this.zbSchemeService.updateZbInfo(updateZbInfos);
        this.info("\u751f\u6210\u7ed3\u675f\uff0c\u65b0\u589e\u4e86{}\u4e2a\u6307\u6807\uff0c\u4fee\u6539\u4e86{}\u4e2a\u6307\u6807", addZbInfos.size(), updateZbInfos.size());
    }

    private void updateDataSchemeAndDataField(GenerateContext context) {
        context.stepOver("\u5904\u7406\u6570\u636e\u65b9\u6848\u548c\u6307\u6807");
        List updateDataSchemes = context.getUpdateDataSchemeKeys().stream().map(dataSchemeKey -> {
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
            String zbSchemeVersionKey = context.getZbSchemeVersionKey();
            String zbSchemeKey = context.getZbSchemeKey();
            if (!zbSchemeVersionKey.equals(dataScheme.getZbSchemeKey()) || !zbSchemeKey.equals(dataScheme.getZbSchemeKey())) {
                dataScheme.setZbSchemeVersion(context.getZbSchemeVersionKey());
                dataScheme.setZbSchemeKey(context.getZbSchemeKey());
                return dataScheme;
            }
            return null;
        }).collect(Collectors.toList());
        this.designDataSchemeService.updateDataSchemes(updateDataSchemes);
        this.designDataSchemeService.updateDataFields(context.getUpdateDataFields());
    }

    private void deleteCheckResult(GenerateContext context) {
        context.stepOver("\u5220\u9664\u68c0\u67e5\u7ed3\u679c\u8bb0\u5f55");
        int deleteCount = this.zbCheckItemDao.deleteByCheck(context.getCheckKey());
        this.info("\u5220\u9664\u6307\u6807\u68c0\u67e5\u8bb0\u5f55\uff0c\u5220\u9664\u4e86{}\u6761\u8bb0\u5f55", deleteCount);
    }

    private void saveResult(GenerateContext context) throws Exception {
        context.recordAddZbGroupCount();
        this.redisTemplate.opsForValue().set((Object)(RESULT_CACHE_KEY_PREFIX + context.getCheckKey()), (Object)context.getResult().toJson());
    }

    private void saveLog(GenerateContext context) {
        context.stepOver("\u8bb0\u5f55\u7cfb\u7edf\u65e5\u5fd7");
        LogHelper.info((String)MODULE, (String)"\u6839\u636e\u62a5\u8868\u65b9\u6848\u9006\u5411\u751f\u6210\u6307\u6807\u4f53\u7cfb\u6307\u6807", (String)context.getResult().format());
    }

    private void info(String message, Object ... param) {
        logger.info(message, param);
    }

    private void oneFormGroup(FormGroupDefine formGroupDefine, GenerateContext generateContext) {
        this.info("\u62a5\u8868\u5206\u7ec4{}", formGroupDefine.getKey());
        ZbGroupCheckItem zbGroupCheckItem = this.buildZbInfoGroup(formGroupDefine, generateContext);
        boolean alreadyAdd = false;
        List formDefines = this.designTimeViewController.listFormByGroup(formGroupDefine.getKey());
        for (DesignFormDefine formDefine : formDefines) {
            int effectCount = this.oneForm((FormDefine)formDefine, formGroupDefine, generateContext, zbGroupCheckItem.getZbGroupDTO().getKey());
            if (alreadyAdd || !zbGroupCheckItem.isAdd() || effectCount <= 0) continue;
            generateContext.addZbGroupForAdd(zbGroupCheckItem.getZbGroupDTO());
            alreadyAdd = true;
        }
    }

    private int oneForm(FormDefine formDefine, FormGroupDefine formGroupDefine, GenerateContext generateContext, String parentKey) {
        this.info("\u62a5\u8868{}", formDefine.getKey());
        generateContext.stepBegin();
        ZbGroupCheckItem zbGroupCheckItem = this.buildZbInfoGroup(formGroupDefine, formDefine, generateContext, parentKey);
        List<ZbCheckItemDTO> zbCheckItemDTOS = this.formSchemeZbCheckQueryService.queryByForm(generateContext.getCheckKey(), formDefine.getKey());
        if (!CollectionUtils.isEmpty(zbCheckItemDTOS) && zbGroupCheckItem.isAdd()) {
            generateContext.addZbGroupForAdd(zbGroupCheckItem.getZbGroupDTO());
        }
        for (ZbCheckItemDTO zbCheckItemDTO : zbCheckItemDTOS) {
            DataField dataField = zbCheckItemDTO.getDataField();
            DesignDataField designDataField = (DesignDataField)dataField;
            designDataField.setZbSchemeVersion(generateContext.getZbSchemeVersionKey());
            if (zbCheckItemDTO.getOperationType().equals((Object)OperationType.ADD)) {
                ZbInfo newZbInfo = this.buildZbInfoForAdd(dataField, generateContext.getZbSchemeKey(), generateContext.getZbSchemeVersionKey(), zbGroupCheckItem.getZbGroupDTO().getKey());
                generateContext.addZbInfoForAdd(newZbInfo, designDataField);
                continue;
            }
            ZbInfo zbInfo = zbCheckItemDTO.getZbInfo();
            this.buildZbInfoForUpdate(zbInfo, dataField);
            generateContext.addZbInfoForUpdate(zbInfo, designDataField);
        }
        int[] countInfo = generateContext.stepOver(String.format("\u62a5\u8868[%s]\u5904\u7406\u5b8c\u6bd5", formDefine.getTitle()));
        String key = String.format("%s/%s", formGroupDefine.getTitle(), formDefine.getTitle());
        generateContext.putFormResult(formDefine.getKey(), key, countInfo[0], countInfo[1]);
        this.info("\u5f85\u65b0\u589e{}\u4e2a\u6307\u6807\uff0c\u5f85\u4fee\u6539{}\u4e2a\u6307\u6807", countInfo[0], countInfo[1]);
        return countInfo[0] + countInfo[1];
    }

    private ZbGroupCheckItem buildZbInfoGroup(FormGroupDefine formGroupDefine, GenerateContext generateContext) {
        String key = UUID.randomUUID().toString();
        String title = formGroupDefine.getTitle();
        ZbGroupDTO zbGroupDTO = new ZbGroupDTO();
        if (generateContext.containsZbGoupTitle(title)) {
            this.info("\u6307\u6807\u5206\u7ec4[{}]\u5df2\u5b58\u5728\uff0c\u5ffd\u7565", title);
            key = generateContext.getZbGroupTitleMap().get(title).getKey();
            zbGroupDTO.setKey(key);
            return new ZbGroupCheckItem(false, zbGroupDTO);
        }
        this.info("\u6307\u6807\u5206\u7ec4[{}]\uff0c\u5f85\u65b0\u589e", title);
        zbGroupDTO.setKey(key);
        zbGroupDTO.setTitle(title);
        zbGroupDTO.setParentKey("00000000-0000-0000-0000-000000000000");
        zbGroupDTO.setSchemeKey(generateContext.getZbSchemeKey());
        zbGroupDTO.setVersionKey(generateContext.getZbSchemeVersionKey());
        zbGroupDTO.setUpdateTime(Instant.now());
        return new ZbGroupCheckItem(true, zbGroupDTO);
    }

    private ZbGroupCheckItem buildZbInfoGroup(FormGroupDefine formGroupDefine, FormDefine formDefine, GenerateContext generateContext, String parentKey) {
        String key = UUID.randomUUID().toString();
        String title = formDefine.getTitle();
        ZbGroupDTO zbGroupDTO = new ZbGroupDTO();
        String zbGroupTitle = formGroupDefine.getTitle() + "_" + title;
        if (generateContext.containsZbGoupTitle(zbGroupTitle)) {
            this.info("\u6307\u6807\u5206\u7ec4[{}]\u5df2\u5b58\u5728\uff0c\u5ffd\u7565", title);
            key = generateContext.getZbGroupTitleMap().get(zbGroupTitle).getKey();
            zbGroupDTO.setKey(key);
            return new ZbGroupCheckItem(false, zbGroupDTO);
        }
        this.info("\u6307\u6807\u5206\u7ec4[{}]\uff0c\u5f85\u65b0\u589e", title);
        zbGroupDTO.setKey(key);
        zbGroupDTO.setTitle(title);
        zbGroupDTO.setParentKey(parentKey);
        zbGroupDTO.setSchemeKey(generateContext.getZbSchemeKey());
        zbGroupDTO.setVersionKey(generateContext.getZbSchemeVersionKey());
        zbGroupDTO.setUpdateTime(Instant.now());
        return new ZbGroupCheckItem(true, zbGroupDTO);
    }

    private ZbInfo buildZbInfoForAdd(DataField dataField, String zbSchemeKey, String versionKey, String parentKey) {
        ZbInfoDTO zbInfoDTO = new ZbInfoDTO();
        zbInfoDTO.setKey(UUID.randomUUID().toString());
        zbInfoDTO.setSchemeKey(zbSchemeKey);
        zbInfoDTO.setVersionKey(versionKey);
        zbInfoDTO.setCode(dataField.getCode());
        zbInfoDTO.setParentKey(parentKey);
        zbInfoDTO.setType(ZbType.GENERAL_ZB);
        this.buildZbInfoForUpdate(zbInfoDTO, dataField);
        return zbInfoDTO;
    }

    private void buildZbInfoForUpdate(ZbInfo zbInfo, DataField dataField) {
        zbInfo.setTitle(dataField.getTitle());
        zbInfo.setDesc(dataField.getDesc());
        zbInfo.setDataType(ZbDataType.valueOf(dataField.getDataFieldType().getValue()));
        zbInfo.setGatherType(dataField.getDataFieldGatherType() == null ? null : ZbGatherType.forValue(dataField.getDataFieldGatherType().getValue()));
        zbInfo.setFormula(dataField.getFormula());
        zbInfo.setFormulaDesc(dataField.getFormulaDesc());
        zbInfo.setDefaultValue(dataField.getDefaultValue());
        zbInfo.setMeasureUnit(dataField.getMeasureUnit());
        zbInfo.setFormatProperties(dataField.getFormatProperties());
        zbInfo.setRefEntityId(dataField.getRefDataEntityKey());
        zbInfo.setPrecision(dataField.getPrecision());
        zbInfo.setDecimal(dataField.getDecimal());
        zbInfo.setApplyType(dataField.getDataFieldApplyType() == null ? null : ZbApplyType.forValue(dataField.getDataFieldApplyType().getValue()));
        zbInfo.setAllowUndefinedCode(dataField.getAllowUndefinedCode());
        zbInfo.setAllowMultipleSelect(dataField.getAllowMultipleSelect());
        zbInfo.setNullable(dataField.getNullable());
        zbInfo.setUpdateTime(Instant.now());
        if (!CollectionUtils.isEmpty(dataField.getValidationRules())) {
            String json = JsonUtils.toJson(dataField.getValidationRules());
            List<ValidationRuleDTO> validationRules = JsonUtils.fromJson(json, new TypeReference<List<ValidationRuleDTO>>(){});
            if (validationRules != null) {
                zbInfo.setValidationRules(new ArrayList<ValidationRule>(validationRules));
            }
        } else {
            zbInfo.setValidationRules(null);
        }
    }

    static class ZbGroupCheckItem {
        private boolean add;
        private ZbGroupDTO zbGroupDTO;

        public ZbGroupCheckItem(boolean add, ZbGroupDTO zbGroupDTO) {
            this.add = add;
            this.zbGroupDTO = zbGroupDTO;
        }

        public boolean isAdd() {
            return this.add;
        }

        public void setAdd(boolean add) {
            this.add = add;
        }

        public ZbGroupDTO getZbGroupDTO() {
            return this.zbGroupDTO;
        }

        public void setZbGroupDTO(ZbGroupDTO zbGroupDTO) {
            this.zbGroupDTO = zbGroupDTO;
        }
    }

    static class GenerateContext {
        private final List<ZbGroup> addZbGroups = new ArrayList<ZbGroup>();
        private final List<ZbInfo> addZbInfos = new ArrayList<ZbInfo>();
        private final List<ZbInfo> updateZbInfos = new ArrayList<ZbInfo>();
        private final List<DesignDataField> updateDataFields = new ArrayList<DesignDataField>();
        private final Set<String> updateDataSchemeKeys = new HashSet<String>();
        private GenerateZbParam param;
        private Map<String, ZbGroup> zbGroupTitleMap;
        private int currAddCount;
        private int currUpdateCount;
        private AsyncTaskMonitor monitor;
        private double currProgress;
        private double step;
        private final GenerateZbResult result = new GenerateZbResult();

        public GenerateContext() {
        }

        public GenerateContext(GenerateZbParam param) {
            this();
            this.param = param;
        }

        public void putFormResult(String formKey, String formPath, int addCount, int updateCount) {
            this.result.putForm(formKey, formPath, addCount, updateCount);
        }

        public void stepBegin() {
            this.currAddCount = this.getAddZbInfos().size();
            this.currUpdateCount = this.getUpdateZbInfos().size();
        }

        public int[] stepOver(String message) {
            if (this.monitor != null) {
                this.currProgress += this.step;
                this.monitor.progressAndMessage(this.currProgress, message);
            }
            return new int[]{this.getAddZbInfos().size() - this.currAddCount, this.getUpdateZbInfos().size() - this.currUpdateCount};
        }

        public void updateProgressMessage(String message) {
            if (this.monitor != null) {
                this.monitor.progressAndMessage(this.currProgress, message);
            }
        }

        public void over(String result) {
            if (this.monitor != null) {
                this.currProgress = 100.0;
                this.monitor.finish(result, null);
            }
        }

        public void recordAddZbGroupCount() {
            this.result.setAddGroupCount(this.addZbGroups.size());
        }

        public void addZbGroupForAdd(ZbGroup zbGroup) {
            this.getAddZbGroups().add(zbGroup);
            this.getZbGroupTitleMap().put(zbGroup.getTitle(), zbGroup);
        }

        public void addZbInfoForAdd(ZbInfo zbInfo, DesignDataField dataField) {
            this.getAddZbInfos().add(zbInfo);
            this.getUpdateDataFields().add(dataField);
            this.getUpdateDataSchemeKeys().add(dataField.getDataSchemeKey());
        }

        public void addZbInfoForUpdate(ZbInfo zbInfo, DesignDataField dataField) {
            this.getUpdateZbInfos().add(zbInfo);
            this.getUpdateDataFields().add(dataField);
            this.getUpdateDataSchemeKeys().add(dataField.getDataSchemeKey());
        }

        public void buildZbGroupTitle(IZbSchemeService zbSchemeService) {
            Map zbGroupKeyMap = zbSchemeService.listZbGroupByVersion(this.getZbSchemeVersionKey()).stream().collect(Collectors.toMap(MetaItem::getKey, Function.identity()));
            HashMap<String, ZbGroup> zbGroupTitleMap = new HashMap<String, ZbGroup>();
            for (String key : zbGroupKeyMap.keySet()) {
                ZbGroup zbGroup = (ZbGroup)zbGroupKeyMap.get(key);
                String parentKey = zbGroup.getParentKey();
                ArrayList<String> parentTitles = new ArrayList<String>();
                while (StringUtils.hasLength(parentKey) && !parentKey.equals("00000000-0000-0000-0000-000000000000")) {
                    ZbGroup parentGroup = (ZbGroup)zbGroupKeyMap.get(parentKey);
                    parentTitles.add(parentGroup.getTitle());
                    parentKey = parentGroup.getParentKey();
                }
                parentTitles.add(zbGroup.getTitle());
                String title = String.join((CharSequence)"_", parentTitles);
                zbGroupTitleMap.put(title, zbGroup);
            }
            this.setZbGroupTitleMap(zbGroupTitleMap);
        }

        public boolean containsZbGoupTitle(String groupTitle) {
            return this.getZbGroupTitleMap().containsKey(groupTitle);
        }

        public String getCheckKey() {
            return this.param.getCheckKey();
        }

        public String getFormSchemeKey() {
            return this.param.getFormSchemeKey();
        }

        public String getZbSchemeKey() {
            return this.param.getZbSchemeKey();
        }

        public String getZbSchemeVersionKey() {
            return this.param.getZbSchemeVersionKey();
        }

        public List<ZbGroup> getAddZbGroups() {
            return this.addZbGroups;
        }

        public List<ZbInfo> getAddZbInfos() {
            return this.addZbInfos;
        }

        public List<ZbInfo> getUpdateZbInfos() {
            return this.updateZbInfos;
        }

        public List<DesignDataField> getUpdateDataFields() {
            return this.updateDataFields;
        }

        public Set<String> getUpdateDataSchemeKeys() {
            return this.updateDataSchemeKeys;
        }

        public Map<String, ZbGroup> getZbGroupTitleMap() {
            return this.zbGroupTitleMap;
        }

        public void setZbGroupTitleMap(Map<String, ZbGroup> zbGroupTitleMap) {
            this.zbGroupTitleMap = zbGroupTitleMap;
        }

        public void setMonitor(AsyncTaskMonitor monitor) {
            this.monitor = monitor;
        }

        public AsyncTaskMonitor getMonitor() {
            return this.monitor;
        }

        public void setStep(double step) {
            this.step = step;
        }

        public GenerateZbResult getResult() {
            return this.result;
        }

        public void setParam(GenerateZbParam param) {
            this.param = param;
        }
    }
}

