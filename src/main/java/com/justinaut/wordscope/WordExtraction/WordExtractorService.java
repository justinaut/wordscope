package com.justinaut.wordscope.WordExtraction;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

@Service
public class WordExtractorService {

    private Set<String> wordSet = emptySet();
    ResourceLoader resourceLoader;

    public WordExtractorService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Set<String> getWordSet() {
        return wordSet;
    }

    @PostConstruct
    public void load() throws IOException {

        // TODO: (maybe) set location via an input parameter or via configuration

        String location = "classpath:static/words_alpha.txt";
        Resource resource = resourceLoader.getResource(location);

        if (!resource.exists()) {
            String fileName = resource.getFilename();
            throw new FileNotFoundException("File not found: " + fileName);
        }

        InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        wordSet = reader.lines().collect(toSet());
    }
}
