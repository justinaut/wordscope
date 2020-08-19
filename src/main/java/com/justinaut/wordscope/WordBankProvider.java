package com.justinaut.wordscope;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WordBankProvider {

    private List<String> wordBank = new ArrayList<>();
    ResourceLoader resourceLoader;

    public WordBankProvider(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> getWordBank() {
        return wordBank;
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
        wordBank = reader.lines().collect(Collectors.toList());
    }
}
