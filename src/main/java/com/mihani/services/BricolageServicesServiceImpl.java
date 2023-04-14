package com.mihani.services;

import com.mihani.entities.BricolageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BricolageServicesServiceImpl {


    public List<String> listServices() {

        List<String> services = Arrays.stream(BricolageService.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return services;
    }


}

