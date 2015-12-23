package com.mitchellbosecke.pebble.spring.loader;

import com.mitchellbosecke.pebble.error.LoaderException;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.utils.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Configuration
public class ResourceAwareLoader implements ResourceLoaderAware, Loader<String> {

    private static final Logger log = LoggerFactory.getLogger(ResourceAwareLoader.class);

    private ResourceLoader resourceLoader;
    private String charset;
    private String prefix;
    private String suffix;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Reader getReader(String templateName) throws LoaderException {

        // append the prefix and make sure prefix ends with a separator
        // character
        StringBuilder path = new StringBuilder("classpath:");
        if (this.prefix != null) {

            path.append(this.prefix);

            // we do NOT use OS dependent separators here; getResourceAsStream
            // explicitly requires forward slashes.
            if (!this.prefix.endsWith("/")) {
                path.append("/");
            }
        }

        String location = path.toString() + templateName + (this.suffix == null ? "" : this.suffix);
        log.debug("Looking for template in {}.", location);

        // perform the lookup
        try {
            return new BufferedReader(new InputStreamReader(resourceLoader.getResource(location).getInputStream()));
        } catch (IOException e) {
            throw new LoaderException(null, "Could not read template \"" + location + "\"");
        }
    }

    @Override
    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String resolveRelativePath(String relativePath, String anchorPath) {
        return PathUtils.resolveRelativePath(relativePath, anchorPath);
    }

    @Override
    public String createCacheKey(String templateName) {
        return templateName;
    }
}
