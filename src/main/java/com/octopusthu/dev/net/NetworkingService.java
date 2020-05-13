package com.octopusthu.dev.net;

import reactor.core.publisher.Mono;

import java.net.InetAddress;

/**
 * @author ZHANG Yu
 */
public interface NetworkingService {

    /**
     * Get the Internet IP address of the local machine this program is running on.
     *
     * @return inetAddress
     * @throws Exception exception
     */
    Mono<InetAddress> getExternalIp() throws Exception;
}
