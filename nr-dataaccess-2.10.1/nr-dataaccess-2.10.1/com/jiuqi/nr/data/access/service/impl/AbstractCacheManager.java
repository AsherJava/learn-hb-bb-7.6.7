/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.AccessParam;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.service.IDataAccessExtraResultService;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.data.access.service.TaskDimensionFilterFactory;
import com.jiuqi.nr.data.access.service.impl.AccessItemServiceCollector;
import com.jiuqi.nr.data.access.service.impl.AccessManager;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemePeriodService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class AbstractCacheManager
extends AccessManager {
    private static final Logger logger = LoggerFactory.getLogger(AbstractCacheManager.class);
    protected List<IDataAccessItemService> accessBaseServices;
    protected List<IDataExtendAccessItemService> accessBaseExtendServices;
    protected final AccessItemServiceCollector accessItemCollector;
    protected final List<IDataAccessExtraResultService> accessExtraResultController;

    public AbstractCacheManager(AccessItemServiceCollector accessItemCollector, List<IDataAccessExtraResultService> accessExtraResultController, IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, IRuntimeFormSchemePeriodService periodService) {
        super(runtimeView, filterFactory, periodService);
        this.accessExtraResultController = accessExtraResultController;
        this.accessItemCollector = accessItemCollector;
    }

    public AbstractCacheManager(AccessItemServiceCollector accessItemCollector, List<IDataAccessExtraResultService> accessExtraResultController, IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, Set<String> ignoreAccessItems, IRuntimeFormSchemePeriodService periodService) {
        super(runtimeView, filterFactory, ignoreAccessItems, periodService);
        this.accessExtraResultController = accessExtraResultController;
        this.accessItemCollector = accessItemCollector;
    }

    public AbstractCacheManager(AccessItemServiceCollector accessItemCollector, List<IDataAccessExtraResultService> accessExtraResultController, IRunTimeViewController runtimeView, TaskDimensionFilterFactory filterFactory, List<String> taskKeys, Set<String> ignoreAccessItems, IRuntimeFormSchemePeriodService periodService) {
        super(runtimeView, filterFactory, taskKeys, ignoreAccessItems, periodService);
        this.accessExtraResultController = accessExtraResultController;
        this.accessItemCollector = accessItemCollector;
    }

    public AccessCode visible(AccessParam param, String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey) throws Exception {
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            boolean enable = service.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", service.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            AccessCode accessCode = service.visible(formSchemeKey, masterKey, formKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:formSchemeKey:[%s];masterKey:[%s];formKey:[%s]; \u7ed3\u679c:[%s]", service.name(), formSchemeKey, masterKey, formKey, accessCode.getCode()));
            }
            if (Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(service.name(), accessCode.getCode());
        }
        if (Objects.isNull(param)) {
            return new AccessCode(null, "1");
        }
        List<AccessItem> items = param.getItems();
        for (AccessItem item : items) {
            IDataExtendAccessItemService controller;
            Optional<IDataExtendAccessItemService> controllerOptional = this.getDataEntendAccessController(item);
            if (!controllerOptional.isPresent() || this.needIgnoreItem((controller = controllerOptional.get()).group())) continue;
            boolean enable = controller.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", controller.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            AccessCode accessCode = controller.visible(item, formSchemeKey, masterKey, formKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-visible-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:formSchemeKey:[%s];masterKey:[%s];formKey:[%s]; \u7ed3\u679c:[%s]", controller.name(), formSchemeKey, masterKey, formKey, accessCode.getCode()));
            }
            if (Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(controller.name(), accessCode.getCode());
        }
        return new AccessCode(null, "1");
    }

    public AccessCode readable(AccessParam param, String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey) throws Exception {
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            boolean enable = service.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", service.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            AccessCode accessCode = service.readable(formSchemeKey, masterKey, formKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:formSchemeKey:[%s];masterKey:[%s];formKey:[%s]; \u7ed3\u679c:[%s]", service.name(), formSchemeKey, masterKey, formKey, accessCode.getCode()));
            }
            if (accessCode.getCode() == "1") continue;
            return new AccessCode(service.name(), accessCode.getCode());
        }
        if (Objects.isNull(param)) {
            return new AccessCode(null, "1");
        }
        List<AccessItem> items = param.getItems();
        for (AccessItem item : items) {
            IDataExtendAccessItemService controller;
            Optional<IDataExtendAccessItemService> controllerOptional = this.getDataEntendAccessController(item);
            if (!controllerOptional.isPresent() || this.needIgnoreItem((controller = controllerOptional.get()).group())) continue;
            boolean enable = controller.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", controller.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            AccessCode accessCode = controller.readable(item, formSchemeKey, masterKey, formKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-readable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:formSchemeKey:[%s];masterKey:[%s];formKey:[%s]; \u7ed3\u679c:[%s]", controller.name(), formSchemeKey, masterKey, formKey, accessCode.getCode()));
            }
            if (Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(controller.name(), accessCode.getCode());
        }
        return new AccessCode(null, "1");
    }

    public AccessCode writeable(AccessParam param, String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey) throws Exception {
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            boolean enable = service.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", service.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            AccessCode accessCode = service.writeable(formSchemeKey, masterKey, formKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:formSchemeKey:[%s];masterKey:[%s];formKey:[%s]; \u7ed3\u679c:[%s]", service.name(), formSchemeKey, masterKey, formKey, accessCode.getCode()));
            }
            if (accessCode.getCode() == "1") continue;
            return new AccessCode(service.name(), accessCode.getCode());
        }
        if (Objects.isNull(param)) {
            return new AccessCode(null, "1");
        }
        List<AccessItem> items = param.getItems();
        for (AccessItem item : items) {
            IDataExtendAccessItemService controller;
            Optional<IDataExtendAccessItemService> controllerOptional = this.getDataEntendAccessController(item);
            if (!controllerOptional.isPresent() || this.needIgnoreItem((controller = controllerOptional.get()).group())) continue;
            boolean enable = controller.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", controller.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            AccessCode accessCode = controller.writeable(item, formSchemeKey, masterKey, formKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-writeable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:formSchemeKey:[%s];masterKey:[%s];formKey:[%s]; \u7ed3\u679c:[%s]", controller.name(), formSchemeKey, masterKey, formKey, accessCode.getCode()));
            }
            if (Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(controller.name(), accessCode.getCode());
        }
        return new AccessCode(null, "1");
    }

    public AccessCode sysWriteable(AccessParam param, String taskKey, String formSchemeKey, DimensionCombination masterKey, String formKey) throws Exception {
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            boolean enable = service.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", service.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            AccessCode accessCode = service.sysWriteable(formSchemeKey, masterKey, formKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:formSchemeKey:[%s];masterKey:[%s];formKey:[%s]; \u7ed3\u679c:[%s]", service.name(), formSchemeKey, masterKey, formKey, accessCode.getCode()));
            }
            if (Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(service.name(), accessCode.getCode());
        }
        if (Objects.isNull(param)) {
            return new AccessCode(null, "1");
        }
        List<AccessItem> items = param.getItems();
        for (AccessItem item : items) {
            IDataExtendAccessItemService controller;
            Optional<IDataExtendAccessItemService> controllerOptional = this.getDataEntendAccessController(item);
            if (!controllerOptional.isPresent() || this.needIgnoreItem((controller = controllerOptional.get()).group())) continue;
            boolean enable = controller.isEnable(taskKey, formSchemeKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:taskKey:[%s];formSchemeKey:[%s]; \u662f\u5426\u542f\u7528\u6743\u9650:[%s]", controller.name(), taskKey, formSchemeKey, enable));
            }
            if (!enable) continue;
            AccessCode accessCode = controller.sysWriteable(item, formSchemeKey, masterKey, formKey);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-sysWriteable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s] \u53c2\u6570:formSchemeKey:[%s];masterKey:[%s];formKey:[%s]; \u7ed3\u679c:[%s]", controller.name(), formSchemeKey, masterKey, formKey, accessCode.getCode()));
            }
            if (Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(controller.name(), accessCode.getCode());
        }
        return new AccessCode(null, "1");
    }

    protected Optional<IDataExtendAccessItemService> getDataEntendAccessController(AccessItem item) {
        this.accessBaseExtendServices = this.getDataExtendAccessItemList();
        if (CollectionUtils.isEmpty(this.accessBaseExtendServices)) {
            return Optional.empty();
        }
        return this.accessBaseExtendServices.stream().filter(service -> service.name().equals(item.getName())).findFirst();
    }

    protected Optional<IDataAccessExtraResultService> getDataAccessExtraResultController(AccessItem item) {
        if (CollectionUtils.isEmpty(this.accessExtraResultController)) {
            return Optional.empty();
        }
        return this.accessExtraResultController.stream().filter(service -> service.name().equals(item.getName())).findFirst();
    }

    protected List<IDataAccessItemService> getDataAccessItemList() {
        if (CollectionUtils.isEmpty(this.accessBaseServices)) {
            this.accessBaseServices = this.accessItemCollector.getAccessBaseServices();
        }
        return this.accessBaseServices;
    }

    protected List<IDataExtendAccessItemService> getDataExtendAccessItemList() {
        if (CollectionUtils.isEmpty(this.accessBaseExtendServices)) {
            this.accessBaseExtendServices = this.accessItemCollector.getAccessBaseExtendServices();
        }
        return this.accessBaseExtendServices;
    }

    public AccessCode zbVisible(DimensionCombination masterKey, String zbKey) {
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        IAccessFormMerge accessForm = super.getAccessForm(masterKey, zbKey);
        if (CollectionUtils.isEmpty(accessForm.getAccessForms())) {
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            }
            return new AccessCode(null, "1");
        }
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            AccessCode accessCode = service.visible(accessForm);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbVisible-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s];zbKey:[%s];masterKey\u548czbKey\u5bf9\u5e94\u8868\u5355:[%s]; \u7ed3\u679c:[%s]", service.name(), zbKey, accessForm, accessCode.getCode()));
            }
            if (accessCode == null || Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(service.name(), accessCode.getCode());
        }
        return new AccessCode(null, "1");
    }

    public AccessCode zbReadable(DimensionCombination masterKey, String zbKey) {
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        IAccessFormMerge accessForm = super.getAccessForm(masterKey, zbKey);
        if (CollectionUtils.isEmpty(accessForm.getAccessForms())) {
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            }
            return new AccessCode(null, "1");
        }
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            AccessCode accessCode = service.readable(accessForm);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbReadable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s];zbKey:[%s];masterKey\u548czbKey\u5bf9\u5e94\u8868\u5355:[%s]; \u7ed3\u679c:[%s]", service.name(), zbKey, accessForm, accessCode.getCode()));
            }
            if (accessCode == null || Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(service.name(), accessCode.getCode());
        }
        return new AccessCode(null, "1");
    }

    public AccessCode zbWriteable(DimensionCombination masterKey, String zbKey) {
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        IAccessFormMerge accessForm = super.getAccessForm(masterKey, zbKey);
        if (CollectionUtils.isEmpty(accessForm.getAccessForms())) {
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            }
            return new AccessCode(null, "1");
        }
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            AccessCode accessCode = service.writeable(accessForm);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbWriteable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s];zbKey:[%s];masterKey\u548czbKey\u5bf9\u5e94\u8868\u5355:[%s]; \u7ed3\u679c:[%s]", service.name(), zbKey, accessForm, accessCode.getCode()));
            }
            if (accessCode == null || Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(service.name(), accessCode.getCode());
        }
        return new AccessCode(null, "1");
    }

    public AccessCode zbSysWriteable(DimensionCombination masterKey, String zbKey) {
        List<IDataAccessItemService> list = this.getDataAccessItemList();
        IAccessFormMerge accessForm = super.getAccessForm(masterKey, zbKey);
        if (CollectionUtils.isEmpty(accessForm.getAccessForms())) {
            if (logger.isDebugEnabled()) {
                logger.debug("\u4f20\u5165\u6307\u6807\u8be5\u6ca1\u6709\u62a5\u8868\u4f7f\u7528,\u9ed8\u8ba4\u6709\u6743\u9650");
            }
            return new AccessCode(null, "1");
        }
        for (IDataAccessItemService service : list) {
            if (this.needIgnoreItem(service.group())) continue;
            AccessCode accessCode = service.sysWriteable(accessForm);
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-zbSysWriteable-\u62a5\u8868\u6743\u9650\u67e5\u8be2-\u6743\u9650\u540d[%s];zbKey:[%s];masterKey\u548czbKey\u5bf9\u5e94\u8868\u5355:[%s]; \u7ed3\u679c:[%s]", service.name(), zbKey, accessForm, accessCode.getCode()));
            }
            if (accessCode == null || Objects.equals(accessCode.getCode(), "1")) continue;
            return new AccessCode(service.name(), accessCode.getCode());
        }
        return new AccessCode(null, "1");
    }
}

