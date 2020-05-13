package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.NetworkingService;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareApiResponse;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareDnsRecordDetailsApiSubResponse;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareDnsService;
import com.octopusthu.dev.thirdparty.cloudflare.CloudflareDnsServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.time.Duration;

/**
 * @author ZHANG Yu
 */
@Slf4j
public class DnsRecordUpdaterService {
    private final DnsRecordUpdaterProperties properties;
    private final NetworkingService networkingService;
    private final CloudflareDnsService cloudflareDnsService;
    private final CloudflareDnsServiceProperties cloudflareDnsServiceProperties;

    public DnsRecordUpdaterService(DnsRecordUpdaterProperties properties, NetworkingService networkingService, CloudflareDnsService cloudflareDnsService, CloudflareDnsServiceProperties cloudflareDnsServiceProperties) {
        this.properties = properties;
        this.networkingService = networkingService;
        this.cloudflareDnsService = cloudflareDnsService;
        this.cloudflareDnsServiceProperties = cloudflareDnsServiceProperties;
    }

    public void updateDnsRecord() throws Exception {

        /*
          Get the current external IP
         */

        InetAddress externalIp = networkingService.getExternalIp()
                .block(Duration.ofSeconds(properties.getBlockInSeconds()));
        Assert.notNull(externalIp, "externalIp is null!");

        // TODO: Support IPv6
        int length = externalIp.getAddress().length;
        Assert.isTrue(length == 4, "Unsupported length of externalIp: " + length);

        String newIp = externalIp.getHostAddress();
        log.debug("External IP is: " + newIp);

        /*
          Fetch the old DNS record
         */

        CloudflareApiResponse<?> apiResponse =
                cloudflareDnsService.dnsRecordDetails()
                        .block(Duration.ofSeconds(properties.getBlockInSeconds()));
        Assert.notNull(apiResponse, "apiResponse is null!");
        Assert.isTrue(apiResponse.isSuccess(), "Response says unsuccessful!");

        /*
          Make sure the fetched DNS record is what we want
         */

        CloudflareDnsRecordDetailsApiSubResponse dnsRecordDetails = (CloudflareDnsRecordDetailsApiSubResponse) apiResponse.getResult();
        String recordName = dnsRecordDetails.getName();
        Assert.isTrue(
                recordName.equals(cloudflareDnsServiceProperties.getRecordName()),
                "Record name mismatches: " + recordName);
        String recordType = dnsRecordDetails.getType();
        Assert.isTrue(
                recordType.equals(cloudflareDnsServiceProperties.getRecordType()),
                "Record type mismatches: " + recordType);

        /*
          See if the DNS record needs updating
         */
        String oldIp = dnsRecordDetails.getContent();
        if (newIp.equals(oldIp)) {
            log.info("IP is unchanged: " + oldIp);
            return;
        }

        /*
          Update DNS record with the new external IP
         */



        log.warn("Successfully changed IP from " + oldIp + " to " + newIp);
    }

}
