package org.example.controller.exemplarlyEndpointForLegacyApp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/cplid")
public class CplIDRestControllerForLegacyApp {
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CplIDPojo> getCplID(@RequestBody Map<String, String> mappedInValues){
        try {
            Date date = new Date();
            long timeMilli = date.getTime();
            CplIDPojo cplIDPojo = new CplIDPojo();
            cplIDPojo.setCplID("" + timeMilli);
            return ResponseEntity.ok(cplIDPojo);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
