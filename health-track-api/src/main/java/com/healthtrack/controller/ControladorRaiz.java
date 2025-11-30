package com.healthtrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class ControladorRaiz {

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public ControladorRaiz(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @GetMapping(path = "/api/endpoints", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> raiz() {
        List<Map<String, Object>> endpoints = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            Set<String> patterns = info.getPatternsCondition() != null ? info.getPatternsCondition().getPatterns() : java.util.Collections.emptySet();
            Set<RequestMethod> methods = info.getMethodsCondition() != null ? info.getMethodsCondition().getMethods() : java.util.Collections.emptySet();

            Map<String, Object> item = new HashMap<>();
            item.put("paths", patterns);

            if (methods == null || methods.isEmpty()) {
                item.put("methods", List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
            } else {
                List<String> methodNames = new ArrayList<>();
                for (RequestMethod m : methods) {
                    methodNames.add(m.name());
                }
                item.put("methods", methodNames);
            }

            HandlerMethod handler = entry.getValue();
            item.put("handler", handler.getBeanType().getSimpleName() + "#" + handler.getMethod().getName());

            endpoints.add(item);
        }

        endpoints.sort(Comparator.comparing(o -> o.get("paths").toString()));
        return endpoints;
    }
}
