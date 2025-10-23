/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.service;

import com.jiuqi.nr.task.dto.FormSchemeDTO;
import com.jiuqi.nr.task.web.param.SchemeQueryParam;
import com.jiuqi.nr.task.web.vo.DefaultSchemeVO;
import com.jiuqi.nr.task.web.vo.FormSchemeItemVO;
import com.jiuqi.nr.task.web.vo.FormSchemeVO;
import java.util.List;

public interface IFormSchemeService {
    public String insertDefaultFormScheme(String var1);

    public FormSchemeVO init(String var1);

    public FormSchemeItemVO insert(FormSchemeVO var1);

    public String delete(String var1);

    public FormSchemeVO getFormScheme(String var1);

    public DefaultSchemeVO getDefaultFormScheme(String var1);

    public FormSchemeItemVO update(FormSchemeVO var1);

    public List<FormSchemeItemVO> queryByTask(String var1);

    public List<FormSchemeDTO> query(SchemeQueryParam var1);

    public void formSchemeCodeCheck(String var1, String var2);

    public boolean formSchemeTitleCheck(FormSchemeVO var1);
}

