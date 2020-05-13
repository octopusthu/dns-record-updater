package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.NetworkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.InetAddress;

/**
 * @author ZHANG Yu
 */
@RestController
@Slf4j
public class DnsRecordUpdaterApiController {

    private final NetworkingService networkingService;

    public DnsRecordUpdaterApiController(NetworkingService networkingService) {
        this.networkingService = networkingService;
    }

    @GetMapping("/api/get-external-ip")
    public Mono<InetAddress> getExternalIp() throws Exception {
        return networkingService.getExternalIp();
    }

}
