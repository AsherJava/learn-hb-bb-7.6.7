/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 */
package com.jiuqi.nr.bpm.impl.single;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySetInfo;
import com.jiuqi.nr.bpm.event.UserActionBatchCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionBatchPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionCompleteEvent;
import com.jiuqi.nr.bpm.event.UserActionEventListener;
import com.jiuqi.nr.bpm.event.UserActionPrepareEvent;
import com.jiuqi.nr.bpm.event.UserActionProgressEvent;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectCompleteEventImpl;
import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectPrepareEventImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleFormRejectListener
implements UserActionEventListener {
    private static final Logger logger = LoggerFactory.getLogger(SingleFormRejectListener.class);
    @Autowired
    IDataDefinitionRuntimeController dataRunTimeController;
    @Autowired
    IRuntimeFormService iRuntimeFormService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    private Optional<EventDispatcher> dispatcher;

    private void insertOrUpdateRejectFormRecord(UserActionCompleteEvent event) {
        boolean allowFormBack;
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(event.getBusinessKey());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        if (formScheme != null && !(allowFormBack = formScheme.getFlowsSetting().isAllowFormBack())) {
            return;
        }
        List formDefines = this.iRuntimeFormService.queryFormDefinesByFormScheme(formScheme.getKey());
        String actorId = event.getActorId();
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(businessKey);
        SingleFormRejectPrepareEventImpl singleFormRejectPreEvent = new SingleFormRejectPrepareEventImpl();
        singleFormRejectPreEvent.setFormSchemeKey(formScheme.getKey());
        singleFormRejectPreEvent.setDimensionValueSet(masterKeys);
        Set<String> formKeys = formDefines.stream().map(e -> e.getKey()).collect(Collectors.toSet());
        singleFormRejectPreEvent.setFormKeys(formKeys);
        singleFormRejectPreEvent.setActionId("fm_upload");
        singleFormRejectPreEvent.setActorId(actorId);
        singleFormRejectPreEvent.setContext(event.getContext());
        try {
            this.dispatcher.get().onSingleFormRejectPrepare(singleFormRejectPreEvent);
        }
        catch (Exception e2) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", e2);
        }
        if (singleFormRejectPreEvent.isSetBreak()) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", singleFormRejectPreEvent.getBreakMessage());
        }
        try {
            this.nrParameterUtils.commitFormQuery(formScheme, masterKeys, formDefines, actorId, "fm_upload");
        }
        catch (Exception e3) {
            logger.error(e3.getMessage(), e3);
        }
        SingleFormRejectCompleteEventImpl singleFormRejectCompleteEvent = new SingleFormRejectCompleteEventImpl();
        singleFormRejectCompleteEvent.setFormSchemeKey(formScheme.getKey());
        singleFormRejectCompleteEvent.setDimensionValueSet(masterKeys);
        singleFormRejectCompleteEvent.setFormKeys(formKeys);
        singleFormRejectCompleteEvent.setActionId("fm_upload");
        singleFormRejectCompleteEvent.setActorId(actorId);
        singleFormRejectCompleteEvent.setContext(event.getContext());
        try {
            this.dispatcher.get().onSingleFormRejectComplete(singleFormRejectCompleteEvent);
        }
        catch (Exception e4) {
            throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", e4);
        }
    }

    private void insertOrUpdateRejectFormRecord(UserActionBatchCompleteEvent event) {
        boolean allowFormBack;
        BusinessKeySetInfo businessKeySet = event.getBusinessKeySet();
        Actor actor = event.getActor();
        String identityId = actor.getIdentityId();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(businessKeySet.getFormSchemeKey());
        if (formScheme != null && !(allowFormBack = formScheme.getFlowsSetting().isAllowFormBack())) {
            return;
        }
        List formDefines = this.iRuntimeFormService.queryFormDefinesByFormScheme(formScheme.getKey());
        try {
            DimensionValueSet convertDimensionName = this.nrParameterUtils.convertDimensionName(businessKeySet);
            SingleFormRejectPrepareEventImpl singleFormRejectPreEvent = new SingleFormRejectPrepareEventImpl();
            singleFormRejectPreEvent.setFormSchemeKey(formScheme.getKey());
            singleFormRejectPreEvent.setDimensionValueSet(convertDimensionName);
            Set<String> formKeys = formDefines.stream().map(e -> e.getKey()).collect(Collectors.toSet());
            singleFormRejectPreEvent.setFormKeys(formKeys);
            singleFormRejectPreEvent.setActionId("fm_upload");
            singleFormRejectPreEvent.setActorId(identityId);
            singleFormRejectPreEvent.setContext(event.getContext());
            try {
                this.dispatcher.get().onSingleFormRejectPrepare(singleFormRejectPreEvent);
            }
            catch (Exception e2) {
                throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", e2);
            }
            if (singleFormRejectPreEvent.isSetBreak()) {
                throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", singleFormRejectPreEvent.getBreakMessage());
            }
            this.nrParameterUtils.commitFormQuery(formScheme, convertDimensionName, formDefines, identityId, "fm_upload");
            SingleFormRejectCompleteEventImpl singleFormRejectCompleteEvent = new SingleFormRejectCompleteEventImpl();
            singleFormRejectCompleteEvent.setFormSchemeKey(formScheme.getKey());
            singleFormRejectCompleteEvent.setDimensionValueSet(convertDimensionName);
            singleFormRejectCompleteEvent.setFormKeys(formKeys);
            singleFormRejectCompleteEvent.setActionId("fm_upload");
            singleFormRejectCompleteEvent.setActorId(identityId);
            singleFormRejectCompleteEvent.setContext(event.getContext());
            try {
                this.dispatcher.get().onSingleFormRejectComplete(singleFormRejectCompleteEvent);
            }
            catch (Exception e3) {
                throw new UserActionException("\u9879\u76ee\u6269\u5c55\u63a5\u53e3\u5931\u8d25", e3);
            }
        }
        catch (Exception e4) {
            logger.error(e4.getMessage(), e4);
        }
    }

    @Override
    public Integer getSequence() {
        return 0;
    }

    @Override
    public Set<String> getListeningUserTaskId() {
        return null;
    }

    @Override
    public Set<String> getListeningActionId() {
        HashSet<String> action = new HashSet<String>();
        action.add("act_upload");
        action.add("act_confirm");
        action.add("act_reject");
        action.add("act_return");
        action.add("cus_upload");
        action.add("cus_confirm");
        action.add("cus_reject");
        action.add("cus_return");
        return action;
    }

    @Override
    public void onPrepare(UserActionPrepareEvent event) throws Exception {
    }

    @Override
    public void onComplete(UserActionCompleteEvent event) throws Exception {
        this.insertOrUpdateRejectFormRecord(event);
    }

    @Override
    public void onBatchPrepare(UserActionBatchPrepareEvent event) throws Exception {
    }

    @Override
    public void onProgrocessChanged(UserActionProgressEvent event) throws Exception {
    }

    @Override
    public void onBatchComplete(UserActionBatchCompleteEvent event) throws Exception {
        this.insertOrUpdateRejectFormRecord(event);
    }
}

