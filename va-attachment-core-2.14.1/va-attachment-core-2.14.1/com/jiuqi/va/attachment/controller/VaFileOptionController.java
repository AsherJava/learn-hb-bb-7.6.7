/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.va.attachment.controller;

import com.jiuqi.va.attachment.entity.FileOptionDO;
import com.jiuqi.va.attachment.service.VaFileOptionService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/bizAttachment/file/option"})
public class VaFileOptionController {
    @Autowired
    private VaFileOptionService optionFoService;

    @PostMapping(value={"/list"})
    List<OptionItemVO> list(@RequestBody OptionItemDTO param) {
        return this.optionFoService.list(param);
    }

    @PostMapping(value={"/update"})
    R update(@RequestBody FileOptionDO option) {
        this.optionFoService.update(option);
        return R.ok();
    }
}

