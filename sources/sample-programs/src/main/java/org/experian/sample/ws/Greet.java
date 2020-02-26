package org.experian.sample.ws;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/greet")
public class Greet {

    @RequestMapping(path = "/hello/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> hello(@PathVariable String name) {
        return ResponseEntity.ok(String.format("Hello, %s", name));
    }
}
