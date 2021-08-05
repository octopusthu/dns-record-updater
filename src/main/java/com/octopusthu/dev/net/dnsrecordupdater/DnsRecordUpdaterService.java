package com.octopusthu.dev.net.dnsrecordupdater;

import com.octopusthu.dev.net.InetAddressService;
import com.octopusthu.dev.net.providers.cloudflare.*;
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
    private final InetAddressService inetAddressService;
    private final CloudflareDnsService cloudflareDnsService;
    private final CloudflareDnsServiceProperties cloudflareDnsServiceProperties;

    public DnsRecordUpdaterService(DnsRecordUpdaterProperties properties, InetAddressService inetAddressService, CloudflareDnsService cloudflareDnsService, CloudflareDnsServiceProperties cloudflareDnsServiceProperties) {
        this.properties = properties;
        this.inetAddressService = inetAddressService;
        this.cloudflareDnsService = cloudflareDnsService;
        this.cloudflareDnsServiceProperties = cloudflareDnsServiceProperties;
    }

    public void updateDnsRecord() throws Exception {

        /*
          Get the current external IP
         */

        InetAddress externalIp = inetAddressService.getExternalIp()
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

        CloudflareDnsRecordDetailsResponse dnsRecordDetails = cloudflareDnsService.dnsRecordDetails(
                cloudflareDnsServiceProperties.getBearerToken(),
                cloudflareDnsServiceProperties.getZoneId(),
                cloudflareDnsServiceProperties.getRecordId())
                .block(Duration.ofSeconds(properties.getBlockInSeconds()));
        Assert.notNull(dnsRecordDetails, "dnsRecordDetails is null!");
        Assert.isTrue(dnsRecordDetails.isSuccess(), "dnsRecordDetails says unsuccessful!");

        /*
          Make sure the fetched DNS record is what we want
         */

        CloudflareDnsRecordDetailsResponse.Result dnsRecordDetailsResult = dnsRecordDetails.getResult();
        String recordName = dnsRecordDetailsResult.getName();
        Assert.isTrue(recordName.equals(cloudflareDnsServiceProperties.getRecordName()),
                "Record name mismatches: " + recordName);
        String recordType = dnsRecordDetailsResult.getType();
        Assert.isTrue(recordType.equals(cloudflareDnsServiceProperties.getRecordType()),
                "Record type mismatches: " + recordType);

        /*
          See if the DNS record needs updating
         */
        String oldIp = dnsRecordDetailsResult.getContent();
        if (newIp.equals(oldIp)) {
            log.info("IP is unchanged: " + oldIp);
            return;
        }

        /*
          Update DNS record with the new external IP
         */
        CloudflareUpdateDnsRecordResponse updateDnsRecord = cloudflareDnsService.updateDnsRecord(cloudflareDnsServiceProperties.getBearerToken(),
                cloudflareDnsServiceProperties.getZoneId(),
                cloudflareDnsServiceProperties.getRecordId(),
                cloudflareDnsServiceProperties.getRecordType(),
                cloudflareDnsServiceProperties.getRecordName(),
                newIp,
                cloudflareDnsServiceProperties.getRecordTtl(),
                false)
                .block(Duration.ofSeconds(properties.getBlockInSeconds()));
        Assert.notNull(updateDnsRecord, "updateDnsRecord is null!");
        Assert.isTrue(updateDnsRecord.isSuccess(), "updateDnsRecord says unsuccessful!");

        CloudflareDnsRecordDetailsResponse.Result updateDnsRecordResult = updateDnsRecord.getResult();
        String updatedIp = updateDnsRecordResult.getContent();
        Assert.isTrue(newIp.equals(updatedIp), "updatedIp mismatches: " + updatedIp);

        log.warn("Successfully updated DNS record content from " + oldIp + " to " + newIp);
    }

}
