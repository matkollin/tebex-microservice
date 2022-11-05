<div style="font-size:1;" align="center">
    <h1>Tebex microservice</h1>
</div>

<p align="center">Webhhok entrypoint for tebex.io</p>

<p align="center">
<!-- Spring boot version -->
<a href="https://github.com/spring-projects/spring-boot/releases/tag/v2.7.4">
    <img src="https://img.shields.io/badge/spring--boot-2.7.5-blue">
</a>
<!-- JDK version -->
<a href="https://www.oracle.com/java/technologies/javase-downloads.html">
    <img src="https://img.shields.io/badge/JDK-17-blue">
</a>
<!-- Google code style -->
<a href="https://google.github.io/styleguide/javaguide.html">
    <img src="https://img.shields.io/badge/code--style-Google%20Java%20Style-blue">
</a>
<!-- License -->
<a href="https://de.wikipedia.org/wiki/MIT-Lizenz">
    <img src="https://img.shields.io/github/license/matkollin/veqflix?color=blue">
</a>
</p>

## Introduction
A simple [Maven](https://maven.apache.org/) based microservice using [Spring Boot](https://spring.io/projects/spring-boot) handling [Tebex](https://www.tebex.io/) interactions.

> For breaking changes/updates look at the official [Tebex Documentation](https://docs.tebex.io/plugin/)

## Getting started

Create a webhook on your [Tebex](https://www.tebex.io/) dashboard and set the URL to your server: [Documentation](https://docs.tebex.io/store/integrating-with-your-game-server-or-website/webhooksv2)

### Prerequisites
#### Development
- [Java 17](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/) or [Kubernetes](https://kubernetes.io/)

#### Production
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/) or [Kubernetes](https://kubernetes.io/)
- [Tebex](https://www.tebex.io/) account

### Docker Compose
To start the service using [Docker Compose](https://docs.docker.com/compose/) you can use the following command:

```bash
docker-compose up -d
```

### Kubernetes
To start the service using [Kubernetes](https://kubernetes.io/) you can use the following command:

```bash
kubectl apply -f kubernetes/
```


### Configuration
The configuration is done via environment variables. The following variables are available:

| Variable                               | Description                                      | Default |
|----------------------------------------|--------------------------------------------------| --- |
| `TEBEX_WEBHOOK_SECRET`                 | The secret used to verify the webhook signature. | `secret` |
| `TEBEX_WEBHOOK_AUTHORIZED_IP_ADDRESSES` | Authorized IP addresses to receive requests from.  | `18.209.80.3;54.87.231.232` |
| `TEBEX_WEBHOOK_PORT`                   | The port the webhook endpoint is listening on.   | `8080` |
| `TEBEX_WEBHOOK_LOGGING_LEVEL`          | The log level for the webhook endpoint.          | `INFO` |

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
