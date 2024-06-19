package com.english.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component(value = "AppConfig")
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    // 项目名称
    private String name;

    // Git仓库目录
    private String gitRepositoryPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGitRepositoryPath() {
        return gitRepositoryPath;
    }

    public void setGitRepositoryPath(String gitRepositoryPath) {
        this.gitRepositoryPath = gitRepositoryPath;
    }
}
