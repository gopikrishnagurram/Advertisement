package com.wavelabs.utils;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
/**
 * Class to load different properties file from the classpath.
 * @author bikshapathi
 *
 */
@Component
@PropertySource(value = "wavelabsspring.properties")
public class ReadPropertiesFile {

}
