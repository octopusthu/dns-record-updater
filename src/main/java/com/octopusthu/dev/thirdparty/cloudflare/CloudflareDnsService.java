package com.octopusthu.dev.thirdparty.cloudflare;

import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * @author ZHANG Yu
 */
@Slf4j
public class CloudflareDnsService {
    private final WebClient cloudflareWebClient;

    public CloudflareDnsService(WebClient.Builder webClientBuilder) {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000));
        this.cloudflareWebClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://api.cloudflare.com/client/v4/")
                .build();
    }

    public Mono<CloudflareDnsRecordDetailsResponse> dnsRecordDetails(String bearerToken, String zoneId, String id) {
        return this.cloudflareWebClient.get()
                .uri("zones/{zone_identifier}/dns_records/{identifier}", zoneId, id)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(CloudflareDnsRecordDetailsResponse.class);
    }

    public Mono<CloudflareUpdateDnsRecordResponse> updateDnsRecord(String bearerToken, String zoneId, String id, String type, String name, String content, int ttl, boolean proxied) {
        return this.cloudflareWebClient.put()
                .uri("zones/{zone_identifier}/dns_records/{identifier}", zoneId, id)
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CloudflareUpdateDnsRecordRequest(type, name, content, ttl, proxied))
                .retrieve()
                .bodyToMono(CloudflareUpdateDnsRecordResponse.class);
    }
}
