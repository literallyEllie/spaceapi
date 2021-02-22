package net.spacedelta.api.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ApiConfigFile {

    private final File resourceFile;

    public ApiConfigFile(String resourcePath) {
        resourceFile = new File(resourcePath);

        if (!resourceFile.exists()) {
            final URL resUrl = Resources.getResource(resourcePath);
            if (resUrl != null) {
                try {
                    Resources.asByteSource(resUrl).copyTo(Files.asByteSink(resourceFile));
                } catch (IOException e) {
                    System.out.println("failed to load " + getResourceFile());
                    e.printStackTrace();
                }
            } else
                throw new RuntimeException(resourcePath + " does not exist");
        }


    }

    public <T> T loadJson(Class<?> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            return (T) mapper.readValue(getResourceFile(), clazz);
            // return new Yaml(new Constructor(clazz))
            // .load(new FileInputStream(getResourceFile()));
        } catch (IOException e) {
            System.out.println("failed to load " + getResourceFile());
            e.printStackTrace();
        }

        return null;
    }

    public File getResourceFile() {
        return resourceFile;
    }

}
