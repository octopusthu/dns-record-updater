package com.octopusthu.dev.net.providers.ifconfigco;

import com.octopusthu.dev.net.InetAddressService;
import io.netty.channel.ChannelOption;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author ZHANG Yu
 */
public class IpConfigCoInetAddressService implements InetAddressService {

    private final WebClient ipConfigMeWebClient;

    public IpConfigCoInetAddressService(WebClient.Builder webClientBuilder) {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000));
        this.ipConfigMeWebClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://ifconfig.co")
                .build();
    }

    public Mono<Ifconfig> fetchIfconfig() {
        return this.ipConfigMeWebClient.get().uri("/json")
                .retrieve()
                .bodyToMono(Ifconfig.class)
                .retry(5);
    }

    @Override
    public Mono<InetAddress> getExternalIp() {
        Mono<Ifconfig> ifconfigMono = this.fetchIfconfig();
        return ifconfigMono.map(ifconfig -> {
            try {
                return InetAddress.getByName(ifconfig.getIp());
            } catch (UnknownHostException e) {
                return null;
            }
        });
    }
}
