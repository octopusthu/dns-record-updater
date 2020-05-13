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
    private final CloudflareDnsServiceProperties properties;

    public CloudflareDnsService(WebClient.Builder webClientBuilder, CloudflareDnsServiceProperties properties) {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000));
        this.cloudflareWebClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://api.cloudflare.com/client/v4/")
                .build();

        this.properties = properties;
    }

    public Mono<CloudflareApiResponse> dnsRecordDetails() {
        return this.cloudflareWebClient.get()
                .uri("zones/{zone_identifier}/dns_records/{identifier}",
                        properties.getZoneId(),
                        properties.getRecordId())
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + properties.getBearerToken())
                .retrieve()
                .bodyToMono(CloudflareApiResponse.class);
    }

    public Mono<CloudflareApiResponse> updateDnsRecord() {
        return this.cloudflareWebClient.put()
                .uri("zones/{zone_identifier}/dns_records/{identifier}",
                        properties.getZoneId(),
                        properties.getRecordId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + properties.getBearerToken())
                .retrieve()
                .bodyToMono(CloudflareApiResponse.class);
    }
}
