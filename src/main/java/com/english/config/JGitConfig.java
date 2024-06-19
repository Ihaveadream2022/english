package com.english.config;

import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File;

@Configuration
public class JGitConfig {
    @Autowired
    AppConfig appConfig;

    @Bean(name = "JGitService")
    protected Git JGitService() {
        try {
            String gitRepoPath = appConfig.getGitRepositoryPath();
            File file = new File(gitRepoPath);
            return Git.open(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
