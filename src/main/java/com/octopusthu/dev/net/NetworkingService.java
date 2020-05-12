package com.octopusthu.dev.net;

import java.net.InetAddress;

/**
 * @author ZHANG Yu
 */
public interface NetworkingService {

    /**
     * Get the Internet IP address of the local machine this program is running on.
     *
     * @return inetAddress
     */
    InetAddress getExternalIp() throws Exception;
}
