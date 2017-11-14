package org.spree.culture.endpoints;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CultureSchedulerController {

    @RequestMapping("/status")
    public String index() {
        return "Success";
    }
}
