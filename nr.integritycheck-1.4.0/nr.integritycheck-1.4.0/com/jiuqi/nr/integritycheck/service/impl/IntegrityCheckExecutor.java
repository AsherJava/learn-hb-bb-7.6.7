/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.form.selector.context.IFormQueryContext
 *  com.jiuqi.nr.form.selector.tree.IFormCheckExecutor
 *  org.json.JSONObject
 */
package com.jiuqi.nr.integritycheck.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.integritycheck.helper.FormOperationHelper;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class IntegrityCheckExecutor
implements IFormCheckExecutor {
    private IFormQueryContext context;
    private IRunTimeViewController runTimeViewController;
    private FormOperationHelper fHelper;

    public IntegrityCheckExecutor(IFormQueryContext context, IRunTimeViewController runTimeViewController, FormOperationHelper fHelper) {
        this.context = context;
        this.runTimeViewController = runTimeViewController;
        this.fHelper = fHelper;
    }

    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        JSONObject customVariable = this.context.getCustomVariable();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, List<String>>> typeReference = new TypeReference<Map<String, List<String>>>(){};
        try {
            String k;
            List formKeys;
            Iterator iterator;
            Map selectFormsMap = (Map)objectMapper.readValue(customVariable.toString(), (TypeReference)typeReference);
            if (null != selectFormsMap && (iterator = selectFormsMap.keySet().iterator()).hasNext() && null != (formKeys = (List)selectFormsMap.get(k = (String)iterator.next())) && !formKeys.isEmpty()) {
                List formDefines = this.runTimeViewController.queryFormsById(formKeys);
                return formDefines;
            }
            List<String> allForms = this.fHelper.getFormsAllList(this.context.getFormSchemeKey(), null);
            List formDefines = this.runTimeViewController.queryFormsById(allForms);
            return formDefines;
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

