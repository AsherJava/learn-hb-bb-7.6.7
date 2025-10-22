/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.DataPublishParam
 *  com.jiuqi.nr.dataentry.service.IDataPublishService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  org.json.JSONArray
 */
package com.jiuqi.nr.form.selector.tree.checkExecutor;

import com.jiuqi.nr.dataentry.bean.DataPublishParam;
import com.jiuqi.nr.dataentry.service.IDataPublishService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.form.selector.context.FormQueryHelperImpl;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.tree.IFormCheckExecutor;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class DataPublishExcutor
implements IFormCheckExecutor {
    protected IFormQueryContext context;
    protected FormQueryHelperImpl formQueryHelper;
    protected IDataPublishService iDataPublishService;

    public DataPublishExcutor(IFormQueryContext context, IRunTimeViewController runTimeView, IDataPublishService iDataPublishService) {
        this.context = context;
        this.formQueryHelper = new FormQueryHelperImpl(context, runTimeView);
        this.iDataPublishService = iDataPublishService;
    }

    @Override
    public List<FormDefine> checkFormList(List<FormDefine> forms) {
        JtableContext jtableContext = this.formQueryHelper.translate2JTableContext(this.context);
        DataPublishParam dataPublishParam = new DataPublishParam();
        dataPublishParam.setFormKeys(this.getFormKeys(forms));
        dataPublishParam.setContext(jtableContext);
        return forms.stream().filter(form -> this.checkForms((FormDefine)form, dataPublishParam)).collect(Collectors.toList());
    }

    private boolean checkForms(FormDefine form, DataPublishParam dataPublishParam) {
        List publishedFormKeys = this.iDataPublishService.getPublishedFormKeys(dataPublishParam);
        Boolean dataPublished = this.isPublish(form, publishedFormKeys);
        Boolean publishFormTag = (Boolean)this.context.getCustomVariable().getJSONObject("dataPublishFilterContext").get("publishFormTag");
        JSONArray jsonObject = (JSONArray)this.context.getCustomVariable().getJSONObject("dataPublishFilterContext").get("dataPublishAuthorityFormKeys");
        ArrayList<String> dataPublishAuthorityFormKeys = new ArrayList<String>();
        for (int i = 0; i < jsonObject.length(); ++i) {
            dataPublishAuthorityFormKeys.add((String)jsonObject.get(i));
        }
        if (publishFormTag.booleanValue()) {
            Boolean tag1 = dataPublished == false && dataPublishAuthorityFormKeys.contains(form.getKey());
            Boolean tag2 = dataPublished != false && dataPublishAuthorityFormKeys.contains(form.getKey());
            if (publishFormTag.booleanValue()) {
                return tag1;
            }
            return tag2;
        }
        return true;
    }

    public List<String> getFormKeys(List<FormDefine> forms) {
        ArrayList<String> formKeys = new ArrayList<String>();
        for (FormDefine i : forms) {
            formKeys.add(i.getKey());
        }
        return formKeys;
    }

    private boolean isPublish(FormDefine form, List<String> publishedFormKeys) {
        if (publishedFormKeys != null) {
            for (String i : publishedFormKeys) {
                if (!i.equals(form)) continue;
                return true;
            }
        }
        return false;
    }
}

