package com.mihani.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BackendURL {

    @Value("${backend.url}")
    public String backendURL;

}
