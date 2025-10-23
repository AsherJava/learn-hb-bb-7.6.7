/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.zb.scheme.common.OperationType;
import com.jiuqi.nr.zb.scheme.common.ZbDiffType;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.exception.ZbPreCheckException;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbCheckItemDao;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbCheckItemDTO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbCheckItemDO;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormSchemeZbCheckServiceImpl
implements IFormSchemeZbCheckService {
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeZbCheckServiceImpl.class);
    private static final String EXCEPTION_PREFIX = "\u6307\u6807\u91cd\u590d\u6821\u9a8c\u5f02\u5e38\uff1a";
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IZbCheckItemDao zbCheckItemDao;

    @Override
    public String checkZb(ZbCheckParam zbCheckParam) throws InterruptedException {
        Assert.notNull((Object)zbCheckParam, "preCheckParam is null");
        String formSchemeKey = zbCheckParam.getFormSchemeKey();
        this.info("\u5f00\u59cb\u68c0\u67e5\u62a5\u8868\u65b9\u6848{}\u4e0b\u7684\u62a5\u8868\u6307\u6807", formSchemeKey);
        this.cleanExpireData(zbCheckParam.getExpire());
        List<ZbInfo> zbInfos = this.zbSchemeService.listZbInfoBySchemeAndVersion(zbCheckParam.getZbSchemeKey(), zbCheckParam.getZbSchemeVersionKey());
        CheckContext checkContext = new CheckContext(UUID.randomUUID().toString(), zbInfos);
        this.oneFormSchemeCheck(formSchemeKey, checkContext);
        String checkKey = checkContext.getCheckKey();
        this.info("\u68c0\u67e5\u5b8c\u6210\uff0c\u751f\u6210checkKey:{}", checkKey);
        List<ZbCheckItemDTO> checkItems = checkContext.getAllCheckItems();
        List<ZbCheckItemDO> zbCheckItemDOS = checkItems.stream().map(ZbCheckItemDTO::buildZbCheckItemDO).collect(Collectors.toList());
        this.zbCheckItemDao.insert(zbCheckItemDOS);
        this.info("\u68c0\u67e5\u7ed3\u679c\u5165\u5e93\uff0c\u4e00\u5171\u63d2\u5165{}\u6761\u8bb0\u5f55", checkItems.size());
        return checkKey;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void checkZb(ZbCheckParam zbCheckParam, AsyncTaskMonitor monitor) throws Exception {
        CheckContext checkContext = new CheckContext();
        try {
            Assert.notNull((Object)zbCheckParam, "preCheckParam is null");
            String formSchemeKey = zbCheckParam.getFormSchemeKey();
            this.info("\u5f00\u59cb\u68c0\u67e5\u62a5\u8868\u65b9\u6848{}\u4e0b\u7684\u62a5\u8868\u6307\u6807", formSchemeKey);
            this.info("\u6307\u6807\u4f53\u7cfb:{}", zbCheckParam.getZbSchemeKey());
            this.info("\u6307\u6807\u4f53\u7cfb\u7248\u672c:{}", zbCheckParam.getZbSchemeVersionKey());
            DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
            DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(task.getDataScheme());
            if (StringUtils.hasLength(dataScheme.getZbSchemeKey()) && !zbCheckParam.getZbSchemeKey().equals(dataScheme.getZbSchemeKey())) {
                monitor.canceled("\u68c0\u67e5\u5230\u6570\u636e\u65b9\u6848\u5df2\u5173\u8054\u53e6\u4e00\u4e2a\u6307\u6807\u4f53\u7cfb", (Object)"");
                return;
            }
            List designFormDefines = this.designTimeViewController.listFormByFormScheme(formSchemeKey);
            if (CollectionUtils.isEmpty(designFormDefines)) {
                monitor.canceled("\u672a\u67e5\u627e\u5230\u62a5\u8868", (Object)"");
                return;
            }
            this.cleanExpireData(zbCheckParam.getExpire());
            List<ZbInfo> zbInfos = this.zbSchemeService.listZbInfoBySchemeAndVersion(zbCheckParam.getZbSchemeKey(), zbCheckParam.getZbSchemeVersionKey());
            checkContext.setCheckKey(UUID.randomUUID().toString());
            checkContext.setZbInfos(zbInfos);
            checkContext.setMonitor(monitor);
            checkContext.setStep(0.95f / (float)designFormDefines.size());
            this.oneFormSchemeCheck(formSchemeKey, checkContext);
            String checkKey = checkContext.getCheckKey();
            this.info("\u68c0\u67e5\u5b8c\u6210\uff0c\u751f\u6210checkKey:{}", checkKey);
            checkContext.updateProgressMessage("\u68c0\u67e5\u5b8c\u6bd5\uff0c\u6b63\u5728\u5165\u5e93");
            List<ZbCheckItemDTO> checkItems = checkContext.getAllCheckItems();
            List<ZbCheckItemDO> zbCheckItemDOS = checkItems.stream().map(ZbCheckItemDTO::buildZbCheckItemDO).collect(Collectors.toList());
            this.zbCheckItemDao.insert(zbCheckItemDOS);
            this.info("\u68c0\u67e5\u7ed3\u679c\u5165\u5e93\uff0c\u4e00\u5171\u63d2\u5165{}\u6761\u8bb0\u5f55", checkItems.size());
            checkContext.over(new ObjectMapper().writeValueAsString((Object)new CheckResult(checkKey, checkItems.size())));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            checkContext.getMonitor().error("\u68c0\u67e5\u5931\u8d25", (Throwable)e);
        }
    }

    private void info(String message, Object ... param) {
        logger.info(message, param);
    }

    private void cleanExpireData(long expire) {
        long expireTime = System.currentTimeMillis() - expire;
        int count = this.zbCheckItemDao.deleteByExpireTime(expireTime);
        this.info("\u5220\u9664{}\u5c0f\u65f6\u524d\u7684\u6307\u6807\u68c0\u67e5\u8bb0\u5f55\uff0c\u4e00\u5171\u5220\u9664\u4e86{}\u6761\u8bb0\u5f55", TimeUnit.HOURS.convert(expire, TimeUnit.MILLISECONDS), count);
    }

    private void oneFormSchemeCheck(String formSchemeKey, CheckContext checkContext) throws InterruptedException {
        checkContext.setFormSchemeKey(formSchemeKey);
        List designFormGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(formSchemeKey);
        for (DesignFormGroupDefine designFormGroupDefine : designFormGroupDefines) {
            this.oneFormGroupCheck(designFormGroupDefine.getKey(), checkContext);
        }
    }

    private void oneFormGroupCheck(String formGroupKey, CheckContext checkContext) throws InterruptedException {
        this.info("\u68c0\u67e5\u62a5\u8868\u5206\u7ec4{}", formGroupKey);
        checkContext.setFormGroupKey(formGroupKey);
        List designFormDefines = this.designTimeViewController.listFormByGroup(formGroupKey);
        if (CollectionUtils.isEmpty(designFormDefines)) {
            return;
        }
        for (DesignFormDefine formDefine : designFormDefines) {
            this.oneFormCheck(formDefine.getKey(), checkContext);
        }
    }

    private void oneFormCheck(String formKey, CheckContext checkContext) {
        checkContext.stepBegin();
        checkContext.setFormKey(formKey);
        List designDataLinkDefines = this.designTimeViewController.listDataLinkByForm(formKey);
        if (CollectionUtils.isEmpty(designDataLinkDefines)) {
            return;
        }
        List linkExpressions = designDataLinkDefines.stream().filter(dataLink -> dataLink.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FIELD) || dataLink.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_INFO) && !checkContext.containsDataFieldKey(dataLink.getLinkExpression())).sorted(Comparator.comparing(DataLinkDefine::getPosY).thenComparing(DataLinkDefine::getPosX)).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
        List<DesignDataField> designDataFields = this.designDataSchemeService.getDataFields(linkExpressions).stream().filter(dataField -> dataField != null && dataField.getDataFieldKind().equals((Object)DataFieldKind.FIELD_ZB)).sorted(Comparator.comparingInt(f -> linkExpressions.indexOf(f.getKey()))).collect(Collectors.toList());
        this.oneFormZbCheck(designDataFields, checkContext);
        DesignFormDefine form = this.designTimeViewController.getForm(formKey);
        int[] countResult = checkContext.stepOver(String.format("\u62a5\u8868[%s]\u68c0\u67e5\u5b8c\u6bd5", form.getTitle()));
        this.info("\u68c0\u67e5\u62a5\u8868{}, \u5f85\u65b0\u589e{}\u4e2a\u6307\u6807\uff0c\u5f85\u4fee\u6539{}\u4e2a\u6307\u6807", formKey, countResult[0], countResult[1]);
    }

    private void oneFormZbCheck(List<DesignDataField> dataFields, CheckContext checkContext) {
        String checkKey = checkContext.getCheckKey();
        List<ZbInfo> zbInfos = checkContext.getZbInfos();
        if (CollectionUtils.isEmpty(dataFields)) {
            return;
        }
        if (CollectionUtils.isEmpty(checkContext.getZbInfos())) {
            dataFields.stream().filter(checkContext::notContainsDataField).forEach(dataField -> checkContext.getZbCheckerHolder().buildChecker(checkKey).dataField((DataField)dataField).result().ifPresent(checkContext::addItem));
            return;
        }
        try {
            Map<String, List<ZbInfo>> zbInfoTitleMap = zbInfos.stream().collect(Collectors.groupingBy(ZbInfo::getTitle));
            dataFields.forEach(dataField -> {
                List zbInfoCodeList;
                String zbTitle = dataField.getTitle();
                if (zbInfoTitleMap.containsKey(zbTitle) && !(zbInfoCodeList = zbInfos.stream().map(ZbInfo::getCode).collect(Collectors.toList())).contains(dataField.getCode())) {
                    ((List)zbInfoTitleMap.get(zbTitle)).forEach(zbInfo -> checkContext.getZbCheckerHolder().buildChecker(checkKey).sameTitleCheck((DataField)dataField, (ZbInfo)zbInfo).ifPresent(checkContext::addItem));
                }
            });
            Map dataFieldCodeMap = dataFields.stream().collect(Collectors.toMap(Basic::getCode, Function.identity(), (a, b) -> b, LinkedHashMap::new));
            Map zbInfoCodeMap = zbInfos.stream().collect(Collectors.toMap(ZbInfo::getCode, Function.identity()));
            dataFieldCodeMap.forEach((zbCode, dataField) -> checkContext.getZbCheckerHolder().buildChecker(checkKey).sameCodeCheck((DataField)dataField, (ZbInfo)zbInfoCodeMap.get(zbCode)).ifPresent(checkContext::addItem));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ZbPreCheckException(e.getMessage(), e);
        }
    }

    static class CheckResult {
        private String checkKey;
        private int recordCount;

        public CheckResult(String checkKey, int recordCount) {
            this.checkKey = checkKey;
            this.recordCount = recordCount;
        }

        public String getCheckKey() {
            return this.checkKey;
        }

        public void setCheckKey(String checkKey) {
            this.checkKey = checkKey;
        }

        public int getRecordCount() {
            return this.recordCount;
        }

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }
    }

    class CheckContext {
        private ZbCheckerHolder zbCheckerHolder;
        private String checkKey;
        private List<ZbInfo> zbInfos;
        private final List<String> dataFieldKeys;
        private final List<ZbCheckItemDTO> zbCheckItemDtosForAdd;
        private final List<ZbCheckItemDTO> zbCheckItemDtosForUpdate;
        private int currAddCount;
        private int currUpdateCount;
        private String formSchemeKey;
        private String formGroupKey;
        private String formKey;
        private AsyncTaskMonitor monitor;
        private double currProgress;
        private double step;

        public CheckContext() {
            this.zbCheckerHolder = new ZbCheckerHolder();
            this.zbCheckItemDtosForAdd = new ArrayList<ZbCheckItemDTO>();
            this.zbCheckItemDtosForUpdate = new ArrayList<ZbCheckItemDTO>();
            this.dataFieldKeys = new ArrayList<String>();
        }

        public CheckContext(String checkKey, List<ZbInfo> zbInfos) {
            this.checkKey = checkKey;
            this.zbInfos = zbInfos;
            this.zbCheckItemDtosForAdd = new ArrayList<ZbCheckItemDTO>();
            this.zbCheckItemDtosForUpdate = new ArrayList<ZbCheckItemDTO>();
            this.dataFieldKeys = new ArrayList<String>();
        }

        public ZbCheckerHolder getZbCheckerHolder() {
            return this.zbCheckerHolder;
        }

        public void setMonitor(AsyncTaskMonitor monitor) {
            this.monitor = monitor;
        }

        public void setStep(double step) {
            this.step = step;
        }

        public void addItem(ZbCheckItemDTO itemDTO) {
            Assert.hasLength(this.formSchemeKey, "formSchemeKey length is 0");
            Assert.hasLength(this.formGroupKey, "formGroupKey length is 0");
            Assert.hasLength(this.formKey, "formKey length is 0");
            String dataFieldKey = itemDTO.getDataField().getKey();
            if (this.containsDataFieldKey(dataFieldKey)) {
                return;
            }
            itemDTO.setFormSchemeKey(this.formSchemeKey);
            itemDTO.setFormGroupKey(this.formGroupKey);
            itemDTO.setFormKey(this.formKey);
            if (itemDTO.getOperationType().equals((Object)OperationType.ADD)) {
                this.getZbCheckItemDtosForAdd().add(itemDTO);
            } else {
                this.getZbCheckItemDtosForUpdate().add(itemDTO);
            }
            this.getDataFieldKeys().add(dataFieldKey);
        }

        public boolean notContainsDataField(DataField dataField) {
            return !this.containsDataFieldKey(dataField.getKey());
        }

        public boolean containsDataFieldKey(String dataFieldKey) {
            return this.getDataFieldKeys().contains(dataFieldKey);
        }

        public void stepBegin() {
            this.currAddCount = this.zbCheckItemDtosForAdd.size();
            this.currUpdateCount = this.zbCheckItemDtosForUpdate.size();
        }

        public int[] stepOver(String message) {
            if (this.monitor != null) {
                this.currProgress += this.step;
                this.monitor.progressAndMessage(this.currProgress, message);
            }
            return new int[]{this.zbCheckItemDtosForAdd.size() - this.currAddCount, this.zbCheckItemDtosForUpdate.size() - this.currUpdateCount};
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

        public List<ZbInfo> getZbInfos() {
            return this.zbInfos;
        }

        public List<ZbCheckItemDTO> getAllCheckItems() {
            this.zbCheckItemDtosForUpdate.addAll(this.zbCheckItemDtosForAdd);
            return this.zbCheckItemDtosForUpdate;
        }

        public String getCheckKey() {
            return this.checkKey;
        }

        public void setFormSchemeKey(String formSchemeKey) {
            this.formSchemeKey = formSchemeKey;
        }

        public void setFormGroupKey(String formGroupKey) {
            this.formGroupKey = formGroupKey;
        }

        public void setFormKey(String formKey) {
            this.formKey = formKey;
        }

        private List<String> getDataFieldKeys() {
            return this.dataFieldKeys;
        }

        private List<ZbCheckItemDTO> getZbCheckItemDtosForAdd() {
            return this.zbCheckItemDtosForAdd;
        }

        private List<ZbCheckItemDTO> getZbCheckItemDtosForUpdate() {
            return this.zbCheckItemDtosForUpdate;
        }

        public void setCheckKey(String checkKey) {
            this.checkKey = checkKey;
        }

        public void setZbInfos(List<ZbInfo> zbInfos) {
            this.zbInfos = zbInfos;
        }

        public AsyncTaskMonitor getMonitor() {
            return this.monitor;
        }
    }

    class ZbChecker {
        private DataField dataField;
        private ZbInfo zbInfo;
        private final ZbCheckItemDTO zbCheckItemDTO;
        private final CheckTempCache cache;

        public ZbChecker(String checkKey, CheckTempCache cache) {
            this.cache = cache;
            this.zbCheckItemDTO = new ZbCheckItemDTO();
            this.zbCheckItemDTO.setCheckKey(checkKey);
        }

        public Optional<ZbCheckItemDTO> sameCodeCheck(DataField dataField, ZbInfo zbInfo) throws ZbPreCheckException {
            try {
                return this.dataField(dataField).zbInfo(zbInfo).code().title().dataType().precision().decimal().refer().result();
            }
            catch (Exception e) {
                if (e instanceof ZbPreCheckException) {
                    throw e;
                }
                throw new ZbPreCheckException(e.getMessage(), e);
            }
        }

        public Optional<ZbCheckItemDTO> sameTitleCheck(DataField dataField, ZbInfo zbInfo) {
            try {
                return this.dataField(dataField).zbInfo(zbInfo).code().result();
            }
            catch (Exception e) {
                if (e instanceof ZbPreCheckException) {
                    throw e;
                }
                throw new ZbPreCheckException(e.getMessage(), e);
            }
        }

        public ZbChecker dataField(DataField dataField) {
            if (dataField == null) {
                throw new ZbPreCheckException("\u6307\u6807\u91cd\u590d\u6821\u9a8c\u5f02\u5e38\uff1adataField\u4e3anull");
            }
            this.dataField = dataField;
            this.zbCheckItemDTO.setDataField(dataField);
            return this;
        }

        public ZbChecker zbInfo(ZbInfo zbInfo) {
            if (zbInfo == null) {
                this.zbCheckItemDTO.setOperationType(OperationType.ADD);
            }
            this.zbInfo = zbInfo;
            this.zbCheckItemDTO.setZbInfo(zbInfo);
            return this;
        }

        public ZbChecker code() {
            if (this.zbInfo != null && this.stringEqual(this.dataField.getTitle(), this.zbInfo.getTitle()) && !this.sameCode()) {
                this.zbCheckItemDTO.addDiffType(ZbDiffType.CODE_DIFF);
            }
            return this;
        }

        public ZbChecker title() {
            if (this.zbInfo != null && this.sameCode() && !this.dataField.getTitle().equals(this.zbInfo.getTitle())) {
                this.zbCheckItemDTO.addDiffType(ZbDiffType.TITLE_DIFF);
            }
            return this;
        }

        public ZbChecker dataType() {
            if (this.zbInfo != null && this.sameCode() && this.dataField.getDataFieldType().getValue() != this.zbInfo.getDataType().getValue()) {
                this.zbCheckItemDTO.addDiffType(ZbDiffType.DATATYPE_DIFF);
            }
            return this;
        }

        public ZbChecker precision() {
            if (this.zbInfo != null && this.sameCode() && !Objects.equals(this.dataField.getPrecision(), this.zbInfo.getPrecision())) {
                this.zbCheckItemDTO.addDiffType(ZbDiffType.PRECISION_DIFF);
            }
            return this;
        }

        public ZbChecker decimal() {
            if (this.zbInfo != null && this.sameCode() && !Objects.equals(this.dataField.getDecimal(), this.zbInfo.getDecimal())) {
                this.zbCheckItemDTO.addDiffType(ZbDiffType.DECIMAL_DIFF);
            }
            return this;
        }

        public ZbChecker refer() {
            if (this.zbInfo != null && this.sameCode() && !this.stringEqual(this.dataField.getRefDataEntityKey(), this.zbInfo.getRefEntityId())) {
                this.zbCheckItemDTO.addDiffType(ZbDiffType.REFER_DIFF);
            }
            return this;
        }

        public Optional<ZbCheckItemDTO> result() {
            this.zbCheckItemDTO.setPath(this.buildZbPath(this.dataField));
            if (this.zbInfo == null || this.zbCheckItemDTO.getDiffTypes().contains((Object)ZbDiffType.CODE_DIFF)) {
                this.zbCheckItemDTO.setOperationType(OperationType.ADD);
            } else {
                this.zbCheckItemDTO.setOperationType(OperationType.MODIFY);
                if (CollectionUtils.isEmpty(this.zbCheckItemDTO.getDiffTypes())) {
                    return Optional.empty();
                }
            }
            return Optional.of(this.zbCheckItemDTO);
        }

        private String buildZbPath(DataField dataField) {
            StringBuilder pathBuilder = new StringBuilder();
            DesignDataScheme dataScheme = this.cache.getDataScheme(dataField.getDataSchemeKey());
            if (dataScheme == null) {
                dataScheme = FormSchemeZbCheckServiceImpl.this.designDataSchemeService.getDataScheme(dataField.getDataSchemeKey());
                this.cache.putDataScheme(dataScheme);
            }
            pathBuilder.append(dataScheme.getTitle());
            pathBuilder.append("/");
            ArrayList<String> groupTitles = new ArrayList<String>();
            DesignDataTable dataTable = this.cache.getDataTable(dataField.getDataTableKey());
            if (dataTable == null) {
                dataTable = FormSchemeZbCheckServiceImpl.this.designDataSchemeService.getDataTable(dataField.getDataTableKey());
                this.cache.putDataTable(dataTable);
            }
            String dataGroupKey = dataTable.getDataGroupKey();
            while (StringUtils.hasLength(dataGroupKey) && !"00000000-0000-0000-0000-000000000000".equals(dataGroupKey)) {
                DesignDataGroup dataGroup = this.cache.getDataGroup(dataGroupKey);
                if (dataGroup == null) {
                    dataGroup = FormSchemeZbCheckServiceImpl.this.designDataSchemeService.getDataGroup(dataGroupKey);
                    this.cache.putDataGroup(dataGroup);
                }
                groupTitles.add(dataGroup.getTitle());
                dataGroupKey = dataGroup.getParentKey();
            }
            if (!CollectionUtils.isEmpty(groupTitles)) {
                Collections.reverse(groupTitles);
                pathBuilder.append(String.join((CharSequence)"/", groupTitles));
                pathBuilder.append("/");
            }
            pathBuilder.append(dataTable.getTitle());
            return pathBuilder.toString();
        }

        private boolean sameCode() {
            return this.stringEqual(this.dataField.getCode(), this.zbInfo.getCode());
        }

        private boolean stringEqual(String str1, String str2) {
            if (!StringUtils.hasLength(str1) && !StringUtils.hasLength(str2)) {
                return true;
            }
            if (!StringUtils.hasLength(str1)) {
                return false;
            }
            if (!StringUtils.hasLength(str2)) {
                return false;
            }
            return str1.equals(str2);
        }

        private boolean validRuleListEqual(List<ValidationRule> validationRules, List<com.jiuqi.nr.zb.scheme.core.ValidationRule> validationRules2) {
            if (CollectionUtils.isEmpty(validationRules) && CollectionUtils.isEmpty(validationRules2)) {
                return true;
            }
            if (CollectionUtils.isEmpty(validationRules)) {
                return false;
            }
            if (CollectionUtils.isEmpty(validationRules2)) {
                return false;
            }
            for (int i = 0; i < validationRules.size(); ++i) {
                if (this.validRuleEqual(validationRules.get(i), validationRules2.get(i))) continue;
                return false;
            }
            return true;
        }

        private boolean validRuleEqual(ValidationRule validationRule, com.jiuqi.nr.zb.scheme.core.ValidationRule validationRule2) {
            return this.stringEqual(validationRule.getVerification(), validationRule2.getVerification()) && this.stringEqual(validationRule.getMessage(), validationRule2.getMessage()) && this.enumEqual((Enum<?>)validationRule.getCompareType(), validationRule2.getCompareType(), () -> validationRule.getCompareType() == null ? 0 : validationRule.getCompareType().getValue(), () -> validationRule2.getCompareType() == null ? 0 : validationRule2.getCompareType().getValue()) && this.stringEqual(validationRule.getLeftValue(), validationRule2.getLeftValue()) && this.stringEqual(validationRule.getRightValue(), validationRule2.getRightValue()) && this.stringEqual(validationRule.getValue(), validationRule2.getValue()) && this.stringListEqual(validationRule.getInValues(), validationRule2.getInValues());
        }

        private boolean enumEqual(Enum<?> enum1, Enum<?> enum2, Supplier<?> enum1TypeSupplier, Supplier<?> enum2TypeSupplier) {
            if (enum1 == null && enum2 == null) {
                return true;
            }
            return Objects.equals(enum1TypeSupplier.get(), enum2TypeSupplier.get());
        }

        private boolean stringListEqual(List<String> list1, List<String> list2) {
            if (CollectionUtils.isEmpty(list1) && CollectionUtils.isEmpty(list2)) {
                return true;
            }
            if (CollectionUtils.isEmpty(list1)) {
                return false;
            }
            if (CollectionUtils.isEmpty(list2)) {
                return false;
            }
            for (int i = 0; i < list1.size(); ++i) {
                if (this.stringEqual(list1.get(i), list2.get(i))) continue;
                return false;
            }
            return true;
        }
    }

    class ZbCheckerHolder {
        private final CheckTempCache cache = new CheckTempCache();

        public ZbChecker buildChecker(String checkKey) {
            return new ZbChecker(checkKey, this.cache);
        }
    }

    static class CheckTempCache {
        private final Map<String, DesignDataScheme> dataSchemeCache = new HashMap<String, DesignDataScheme>();
        private final Map<String, DesignDataTable> dataTableCache = new HashMap<String, DesignDataTable>();
        private final Map<String, DesignDataGroup> dataGroupCache = new HashMap<String, DesignDataGroup>();

        public void putDataScheme(DesignDataScheme dataScheme) {
            this.dataSchemeCache.put(dataScheme.getKey(), dataScheme);
        }

        public void putDataTable(DesignDataTable dataTable) {
            this.dataTableCache.put(dataTable.getKey(), dataTable);
        }

        public void putDataGroup(DesignDataGroup tableGroup) {
            this.dataGroupCache.put(tableGroup.getKey(), tableGroup);
        }

        public DesignDataScheme getDataScheme(String key) {
            return this.dataSchemeCache.get(key);
        }

        public DesignDataTable getDataTable(String key) {
            return this.dataTableCache.get(key);
        }

        public DesignDataGroup getDataGroup(String key) {
            return this.dataGroupCache.get(key);
        }
    }
}

