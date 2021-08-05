# dns-record-updater

A scheduled job to routinely update DNS records with local machine's public IP.

For personal use only for the time being.

## Next Release `v1.1.0`

- Add Docker support

- Polish Spring Configurations
- Refactor core interfaces

- bug fixes
  - ipv6
- performance
  - fix possible memory leak
  - consume less memory overall
- enhancements
  - shorter logging messages

## Build

- Configure your Maven environment to [Authenticating to GitHub Packages](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages#authenticating-to-github-packages)

- Package

```bash
mvn package
```

- Configure spring boot profile

```bash
export SPRING_PROFILES_ACTIVE=local
```

- Configure spring boot property file

```bash
mkdir config
touch config/application-local.properties
```

``` properties
logging.level.com.octopusthu.dev.net.dnsrecordupdater=debug

dns-record-updater.tasks.enabled=true
dns-record-updater.tasks.cron=0/15 * * * * ?

dns-record-updater.cloudflare.bearer-token=
dns-record-updater.cloudflare.zone-id=
dns-record-updater.cloudflare.record-id=
dns-record-updater.cloudflare.record-name=

```

- Run

```bash
nohup java -jar dns-record-updater-xxx.jar &
```

## Caveats

- Only supports IPv4.
- Only supports Cloudflare as the DNS provider.
- Only supports updating one IP record, which is the external IP of the running machine.
- Adheres to the Spring Boot technology stack.
