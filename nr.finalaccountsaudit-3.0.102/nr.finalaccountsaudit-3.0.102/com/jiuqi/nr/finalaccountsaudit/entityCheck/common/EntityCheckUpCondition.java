/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineGetterImpl
 *  com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormSchemeDefineGetterImpl;
import com.jiuqi.nr.definition.internal.impl.RunTimeTaskDefineImpl;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpPublic;

public class EntityCheckUpCondition {
    private String takeKey;
    private RunTimeTaskDefineImpl currentYTask;
    private String formSchemeKey;
    private RunTimeFormSchemeDefineGetterImpl currentYFormScheme;
    private String period;
    private String checkTaskKey;
    private RunTimeTaskDefineImpl lastYTask;
    private String checkFormSchemeKey;
    private RunTimeFormSchemeDefineGetterImpl lastYCheckFormScheme;
    private String checkTableName;
    private EntityCheckUpPublic publicOption;
    public EntityViewDefine currentEntityView;
    private EntityViewDefine lastYEntityView;

    public String GetTaskKey() {
        return this.takeKey;
    }

    public void SetTaskKey(String taskKey) {
        this.takeKey = taskKey;
    }

    public RunTimeTaskDefineImpl GetCurrentYTask() {
        return this.currentYTask;
    }

    public void SetCurrentYTask(RunTimeTaskDefineImpl currentYTask) {
        this.currentYTask = currentYTask;
    }

    public String GetFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void SetFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public RunTimeFormSchemeDefineGetterImpl GetCurrentYFormScheme() {
        return this.currentYFormScheme;
    }

    public void SetCurrentYFormScheme(RunTimeFormSchemeDefineGetterImpl currentYFormScheme) {
        this.currentYFormScheme = currentYFormScheme;
    }

    public String GetPeriod() {
        return this.period;
    }

    public void SetPeriod(String period) {
        this.period = period;
    }

    public String GetCheckTaskKey() {
        return this.checkTaskKey;
    }

    public void SetCheckTaskKey(String checkTaskKey) {
        this.checkTaskKey = checkTaskKey;
    }

    public RunTimeTaskDefineImpl GetLastYTask() {
        return this.lastYTask;
    }

    public void SetLastYTask(RunTimeTaskDefineImpl lastYTask) {
        this.lastYTask = lastYTask;
    }

    public String GetCheckFormSchemeKey() {
        return this.checkFormSchemeKey;
    }

    public void SetCheckFormSchemeKey(String checkFormSchemeKey) {
        this.checkFormSchemeKey = checkFormSchemeKey;
    }

    public RunTimeFormSchemeDefineGetterImpl GetLastYFormScheme() {
        return this.lastYCheckFormScheme;
    }

    public void SetLastYFormScheme(RunTimeFormSchemeDefineGetterImpl lastYCheckFormScheme) {
        this.lastYCheckFormScheme = lastYCheckFormScheme;
    }

    public String GetCheckTableName() {
        return this.checkTableName;
    }

    public void SetCheckTableName(String checkTableName) {
        this.checkTableName = checkTableName;
    }

    public EntityCheckUpPublic GetPublicOption() {
        return this.publicOption;
    }

    public void SetPublicOption(EntityCheckUpPublic publicOption) {
        this.publicOption = publicOption;
    }

    public EntityViewDefine GetCurrentEntityView() {
        return this.currentEntityView;
    }

    public void SetCurrentEntityView(EntityViewDefine currentEntityView) {
        this.currentEntityView = currentEntityView;
    }

    public EntityViewDefine GetLastYEntityView() {
        return this.lastYEntityView;
    }

    public void SetLastYEntityView(EntityViewDefine lastYEntityView) {
        this.lastYEntityView = lastYEntityView;
    }
}

