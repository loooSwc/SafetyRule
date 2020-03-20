package com.safety.index.controller;

import com.alibaba.fastjson.JSONObject;
import com.safety.common.dao.ParamsMap;
import com.safety.common.dao.QueryMap;
import com.safety.common.dao.support.Page;
import com.safety.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/search" , method = RequestMethod.POST)
    @ResponseBody
    public String getLogPage(@RequestBody ParamsMap map) throws Exception {
        Page page = indexService.search(map);
        return JSONObject.toJSONString(page);
    }
}
