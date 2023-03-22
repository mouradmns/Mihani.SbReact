package com.mihani.web;


import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.services.BricoleurServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@AllArgsConstructor
@Slf4j
public class BricoleurController {

    private BricoleurServiceImpl bricoleurService;



    @GetMapping("/bricoleurs")
    public List<BricoleurProfileDto> bricoleurs(){
            return  bricoleurService.listBricoleurs();
    }
    @GetMapping("/")
    public String Home(){
            return "bricoleurs";
    }

}
