package com.safety.index.service;

import com.safety.common.dao.ParamsMap;
import com.safety.common.dao.support.Page;

public interface IndexService {
    Page search(ParamsMap map) throws Exception;
}
