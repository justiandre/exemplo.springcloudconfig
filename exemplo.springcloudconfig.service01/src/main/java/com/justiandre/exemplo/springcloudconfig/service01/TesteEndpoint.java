package com.justiandre.exemplo.springcloudconfig.service01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RestController
@RefreshScope
public class TesteEndpoint {

    @Value("${config.valor-test:}")
    private String valorTeste;

    @GetMapping("/teste")
    public String getValorTeste(){
        if (valorTeste == null || valorTeste.isEmpty()){
            return String.format("Valor da config: '%s' nao definido",  "config.valor-test");
        }
        return String.format("Valor da config: '%s': '%s", "config.valor-test", valorTeste);
    }
}

