package com.servlet.contextpathissue.config;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.valves.rewrite.RewriteValve;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UrlWebServerFactoryCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    private static final List<String> LEGACY_PATHS = List.of("SERVLETISSUE");

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        RewriteValve rewrite = new RewriteValve() {
            @Override
            protected void initInternal() throws LifecycleException {
                super.initInternal();
                try {
                    String config = LEGACY_PATHS.stream() //
                            .map(p -> String.format("RewriteRule ^/%s(/.*)$ %s$1", p, factory.getContextPath())) //
                            .collect(Collectors.joining("\n"));
                    setConfiguration(config);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        factory.addEngineValves(rewrite);
    }
}

