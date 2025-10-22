/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.nr.data.logic.api.ICKDCopyService;
import com.jiuqi.nr.data.logic.api.ICalculateService;
import com.jiuqi.nr.data.logic.api.ICheckService;
import com.jiuqi.nr.data.logic.api.IDataLogicServiceFactory;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidatorProvider;
import com.jiuqi.nr.data.logic.facade.extend.IFmlCheckListener;
import com.jiuqi.nr.data.logic.facade.listener.ICalculateListener;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.impl.CalculateServiceImpl;
import com.jiuqi.nr.data.logic.internal.impl.CheckServiceImpl;
import com.jiuqi.nr.data.logic.internal.impl.ckd.copy.CKDCopyServiceImpl;
import com.jiuqi.nr.data.logic.internal.log.LogHelper;
import com.jiuqi.nr.data.logic.internal.service.ICheckExecuteService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.spi.ICKDCopyOptionProvider;
import com.jiuqi.nr.data.logic.spi.ICalOptionProvider;
import com.jiuqi.nr.data.logic.spi.ICheckOptionProvider;
import com.jiuqi.nr.data.logic.spi.IFmlExecInfoProvider;
import com.jiuqi.nr.data.logic.spi.IUnsupportedDesHandler;
import com.jiuqi.nr.data.logic.spi.def.UnsupportedHandler;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.datastatus.IDataStatusPreInitService;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLogicServiceFactory
implements IDataLogicServiceFactory {
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private ICalOptionProvider calOptionProvider;
    @Autowired
    private ICheckOptionProvider checkOptionProvider;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired(required=false)
    private IDataStatusPreInitService dataStatusPreInitService;
    @Autowired
    private ICheckExecuteService checkExecuteService;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired(required=false)
    private List<ICalculateListener> calculateListeners;
    @Autowired(required=false)
    private List<IFmlCheckListener> fmlCheckListeners;
    @Autowired
    private List<ICheckDesValidatorProvider> checkDesValidatorProviders;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired(required=false)
    private IUnsupportedDesHandler unsupportedDesHandler;

    @Override
    public ICalculateService getCalculateService(IFmlExecInfoProvider fmlExecInfoProvider) {
        return new CalculateServiceImpl(this, this.providerStore, this.calOptionProvider, fmlExecInfoProvider);
    }

    @Override
    public ICalculateService getCalculateService(IFmlExecInfoProvider fmlExecInfoProvider, IProviderStore providerStore) {
        return new CalculateServiceImpl(this, providerStore, this.calOptionProvider, fmlExecInfoProvider);
    }

    @Override
    public ICalculateService getCalculateService(IFmlExecInfoProvider fmlExecInfoProvider, ICalOptionProvider optionProvider) {
        return new CalculateServiceImpl(this, this.providerStore, optionProvider, fmlExecInfoProvider);
    }

    @Override
    public ICalculateService getCalculateService(IFmlExecInfoProvider fmlExecInfoProvider, IProviderStore providerStore, ICalOptionProvider optionProvider) {
        return new CalculateServiceImpl(this, providerStore, optionProvider, fmlExecInfoProvider);
    }

    @Override
    public ICheckService getCheckService() {
        return new CheckServiceImpl(this, this.providerStore, this.checkOptionProvider);
    }

    @Override
    public ICheckService getCheckService(IProviderStore providerStore) {
        return new CheckServiceImpl(this, providerStore, this.checkOptionProvider);
    }

    @Override
    public ICheckService getCheckService(ICheckOptionProvider optionProvider) {
        return new CheckServiceImpl(this, this.providerStore, optionProvider);
    }

    @Override
    public ICheckService getCheckService(IProviderStore providerStore, ICheckOptionProvider optionProvider) {
        return new CheckServiceImpl(this, providerStore, optionProvider);
    }

    @Override
    public ICKDCopyService getCKDCopyService(IProviderStore providerStore, ICKDCopyOptionProvider optionProvider) {
        return new CKDCopyServiceImpl(this, providerStore, optionProvider);
    }

    @Override
    public IUnsupportedDesHandler getUnsupportedDesHandler(Map<String, String> bizKeyOrderMap) {
        return new UnsupportedHandler(this, bizKeyOrderMap);
    }

    public FormulaParseUtil getFormulaParseUtil() {
        return this.formulaParseUtil;
    }

    public ParamUtil getParamUtil() {
        return this.paramUtil;
    }

    public DimensionCollectionUtil getDimensionCollectionUtil() {
        return this.dimensionCollectionUtil;
    }

    public LogHelper getLogHelper() {
        return this.logHelper;
    }

    public IRunTimeViewController getRunTimeViewController() {
        return this.runTimeViewController;
    }

    public IDataAccessProvider getDataAccessProvider() {
        return this.dataAccessProvider;
    }

    public IDataStatusPreInitService getDataStatusPreInitService() {
        return this.dataStatusPreInitService;
    }

    public ICheckExecuteService getCheckExecuteService() {
        return this.checkExecuteService;
    }

    public ICheckErrorDescriptionService getCheckErrorDescriptionService() {
        return this.checkErrorDescriptionService;
    }

    public EntityUtil getEntityUtil() {
        return this.entityUtil;
    }

    public List<ICalculateListener> getCalculateListeners() {
        return this.calculateListeners;
    }

    public List<IFmlCheckListener> getFmlCheckListeners() {
        return this.fmlCheckListeners;
    }

    public List<ICheckDesValidatorProvider> getCheckDesValidatorProviders() {
        return this.checkDesValidatorProviders;
    }

    public PeriodEngineService getPeriodEngineService() {
        return this.periodEngineService;
    }

    public IFormSchemeService getFormSchemeService() {
        return this.formSchemeService;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }

    public DimensionBuildUtil getDimensionBuildUtil() {
        return this.dimensionBuildUtil;
    }

    public IFormulaRunTimeController getFormulaRunTimeController() {
        return this.formulaRunTimeController;
    }

    public IUnsupportedDesHandler getUnsupportedDesHandler() {
        return this.unsupportedDesHandler;
    }
}

