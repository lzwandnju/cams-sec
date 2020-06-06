package com.htsc.cams.common.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/5
 * Time: 15:33
 * Description: No Description
 */
@Controller
public class IndexController {

    @RequestMapping("{url}.shtml")
    public String page(@PathVariable("url") String url) {
        return url;
    }


    @RequestMapping("{module}/{url}.html")
    public String page(@PathVariable("module") String module,@PathVariable("ur;") String url){
        return  module+"/"+url;
    }

}
