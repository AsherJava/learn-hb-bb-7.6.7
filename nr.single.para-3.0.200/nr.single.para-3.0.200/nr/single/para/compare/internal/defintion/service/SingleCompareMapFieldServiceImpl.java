/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.compare.internal.defintion.service;

import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import nr.single.para.compare.definition.CompareMapFieldDTO;
import nr.single.para.compare.definition.ISingleCompareMapFieldService;
import nr.single.para.compare.definition.convert.Convert;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareMapFieldDO;
import nr.single.para.compare.internal.defintion.dao.ICompareMapFieldDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleCompareMapFieldServiceImpl
implements ISingleCompareMapFieldService {
    @Autowired
    private ICompareMapFieldDao<CompareMapFieldDO> compareMapFieldDao;
    private final Function<CompareMapFieldDO, CompareMapFieldDTO> toDto = Convert::cmf2Do;
    private final Function<List<CompareMapFieldDO>, List<CompareMapFieldDTO>> list2Dto = r -> r.stream().map(this.toDto).filter(Objects::nonNull).collect(Collectors.toList());

    @Override
    public List<CompareMapFieldDTO> list(CompareMapFieldDTO compareMapFieldDTO) {
        ArrayList<CompareMapFieldDTO> list = new ArrayList<CompareMapFieldDTO>();
        if (StringUtils.isNotEmpty((String)compareMapFieldDTO.getKey())) {
            CompareMapFieldDO obj = this.compareMapFieldDao.get(compareMapFieldDTO.getKey());
            list.add(this.toDto.apply(obj));
        } else if (StringUtils.isNotEmpty((String)compareMapFieldDTO.getDataSchemeKey()) && StringUtils.isNotEmpty((String)compareMapFieldDTO.getMatchTitle())) {
            List<CompareMapFieldDO> list2 = this.compareMapFieldDao.getByTitleInDataScheme(compareMapFieldDTO.getDataSchemeKey(), compareMapFieldDTO.getMatchTitle());
            list.addAll((Collection<CompareMapFieldDTO>)this.list2Dto.apply(list2));
        } else if (StringUtils.isNotEmpty((String)compareMapFieldDTO.getDataSchemeKey())) {
            List<CompareMapFieldDO> list2 = this.compareMapFieldDao.getByDataSchemeKey(compareMapFieldDTO.getDataSchemeKey());
            list.addAll((Collection<CompareMapFieldDTO>)this.list2Dto.apply(list2));
        }
        return list;
    }

    @Override
    public void add(CompareMapFieldDTO compareMapFieldDTO) throws SingleCompareException {
        this.compareMapFieldDao.insert(compareMapFieldDTO);
    }

    @Override
    public void update(CompareMapFieldDTO compareMapFieldDTO) throws SingleCompareException {
        this.compareMapFieldDao.update(compareMapFieldDTO);
    }

    @Override
    public void delete(CompareMapFieldDTO compareMapFieldDTO) throws SingleCompareException {
        this.compareMapFieldDao.delete(compareMapFieldDTO.getKey());
    }

    @Override
    public void batchAdd(List<CompareMapFieldDTO> compareMapFieldDTOs) throws SingleCompareException {
        ArrayList<CompareMapFieldDTO> list2 = new ArrayList<CompareMapFieldDTO>();
        list2.addAll(compareMapFieldDTOs);
        this.compareMapFieldDao.batchInsert(list2);
    }

    @Override
    public void batchUpdate(List<CompareMapFieldDTO> compareMapFieldDTOs) throws SingleCompareException {
        ArrayList<CompareMapFieldDTO> list2 = new ArrayList<CompareMapFieldDTO>();
        list2.addAll(compareMapFieldDTOs);
        this.compareMapFieldDao.batchUpdate(list2);
    }

    @Override
    public void batchDelete(List<CompareMapFieldDTO> compareMapFieldDTOs) throws SingleCompareException {
        ArrayList<String> list2 = new ArrayList<String>();
        for (CompareMapFieldDTO field : compareMapFieldDTOs) {
            list2.add(field.getKey());
        }
        this.compareMapFieldDao.batchDelete(list2);
    }
}

