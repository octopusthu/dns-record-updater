package com.octopusthu.dev.net;

import reactor.core.publisher.Mono;

import java.net.InetAddress;

/**
 * @author ZHANG Yu
 */
public interface InetAddressService {

    /**
     * Get the Internet IP address of the local machine this program is running on.
     *
     * @return inetAddress
     * @throws Exception exception
     */
    Mono<InetAddress> getExternalIp() throws Exception;

    /**
     * Get the Internet IP address of the local machine this program is running on.
     *
     * @return inetAddress
     * @throws Exception exception
     */
//    InetAddress getLocalMachineIp() throws Exception;
}
