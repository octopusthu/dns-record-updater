package com.octopusthu.dev.net.providers.ipify;

import com.octopusthu.dev.net.InetAddressService;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author ZHANG Yu
 */
public class IpifyInetAddressService implements InetAddressService {

    private final WebClient webClient;

    public IpifyInetAddressService(WebClient.Builder webClientBuilder) {
        HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.of(5, ChronoUnit.SECONDS));
        this.webClient = webClientBuilder
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl("https://api.ipify.org")
            .build();
    }

    public Mono<String> fetchIfconfig() {
        return this.webClient.get().uri("?format=text")
            .retrieve()
            .bodyToMono(String.class)
            .retry(5);
    }

    @Override
    public Mono<InetAddress> getExternalIp() {
        Mono<String> mono = this.fetchIfconfig();
        return mono.map(strInetAddress -> {
            try {
                return InetAddress.getByName(strInetAddress.trim());
            } catch (UnknownHostException e) {
                return null;
            }
        });
    }
}
