package cn.master.tauren.controller;

import cn.master.tauren.service.PersonnelRealTimeBehavior;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
public class DemoController {
    private final PersonnelRealTimeBehavior personnelRealTimeBehavior;

    @PostMapping("/gen")
    public void gen() {
        personnelRealTimeBehavior.genPersonnelFile();
    }
}
