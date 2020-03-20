package com.safety.index.service.impl;

import com.safety.common.dao.ParamsMap;
import com.safety.common.dao.QueryMap;
import com.safety.common.dao.support.Page;
import com.safety.common.util.StringUtil;
import com.safety.index.dao.SafetyRuleDao;
import com.safety.index.service.IndexService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Autowired
    private SafetyRuleDao safetyRuleDao;
    @Override
    public Page search(ParamsMap map) throws Exception {
        int pageSize = map.getPageSize();
        int pageNumber = map.getPageNumber();
        String kw = MapUtils.getString(map.getParams(), "kw");
        QueryMap queryMap=new QueryMap();
        queryMap.setPageNum(pageNumber);
        queryMap.setCURRENT_ROWS_SIZE(pageSize);
        List params = new ArrayList<>();
        StringBuffer sb = new StringBuffer("select `desc` from safety_rule where 1=1");
        if (!StringUtil.isEmpty(kw)) {
            kw = kw.replaceAll("\\_", "\\\\_").replaceAll("\\%", "\\\\%");
            kw = kw.trim();
            kw = "%" + kw + "%";
            sb.append(" and `desc` like ?");
            params.add(kw);
        }
        Page page =  safetyRuleDao.pagedNativeQuery(sb.toString(),queryMap, params.toArray());
        return page;
    }
}
