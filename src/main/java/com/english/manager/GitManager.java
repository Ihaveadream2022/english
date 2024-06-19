package com.english.manager;

import com.english.util.SpringUtil;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

public class GitManager {

    private static final GitManager instance = new GitManager();

    private final Logger logger = LoggerFactory.getLogger("SERVICE");

    private final Git git = (Git) SpringUtil.getBean("JGitService");

    private GitManager() {};

    public static GitManager getInstance() {
        return instance;
    }

    public void push(String token) {
        try {
            // 获取仓库状态
            Status status = git.status().call();

            // 日志内容
            StringBuilder log = new StringBuilder();

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = localDateTime.format(dateTimeFormatter);
            log.append("\n---").append(time).append("---");

            /* Git Status */
            // 暂存区
            log.append("\r\n");
            log.append("Changes to be committed:").append("\r\n");
            log.append("(use \"git restore --staged <file>...\" to unstage)").append("\r\n");
            Set<String> changedFiles = status.getChanged();
            changedFiles.forEach(v -> log.append("\t\t").append(v).append("\r\n"));

            // 工作区
            log.append("\r\n");
            log.append("Changes not staged for commit:").append("\r\n");
            log.append("(use \"git add/rm <file>...\" to update what will be committed)").append("\r\n");
            log.append("(use \"git restore <file>...\" to discard changes in working directory)").append("\r\n");
            // 工作区-被修改的文件
            Set<String> modifiedFiles = status.getModified();
            modifiedFiles.forEach(v -> log.append("\t\tmodified: ").append(v).append("\r\n"));
            // 工作区-被删除的文件
            Set<String> missingFiles = status.getMissing();
            missingFiles.forEach(v -> log.append("\t\tdeleted: ").append(v).append("\r\n"));
            // 工作区-未被跟踪的文件
            log.append("\r\n");
            log.append("Untracked files:").append("\r\n");
            log.append("(use \"git add <file>...\" to include in what will be committed)").append("\r\n");
            Set<String> untrackedFiles = status.getUntracked();
            untrackedFiles.forEach(v -> log.append("\t\tuntracked: ").append(v).append("\r\n"));

            /* Git Add & Remove */
            modifiedFiles.forEach(v -> {
                try {
                    AddCommand addCommand = git.add();
                    addCommand.addFilepattern(v).call();
                } catch (GitAPIException e) {
                    throw new RuntimeException(e);
                }
            });
            missingFiles.forEach(v -> {
                try {
                    RmCommand rmCommand = git.rm();
                    rmCommand.addFilepattern(v).call();
                } catch (GitAPIException e) {
                    throw new RuntimeException(e);
                }
            });
            untrackedFiles.forEach(v -> {
                try {
                    AddCommand addCommand = git.add();
                    addCommand.addFilepattern(v).call();
                } catch (GitAPIException e) {
                    throw new RuntimeException(e);
                }
            });

            /* Git Commit */
            CommitCommand commitCommand = git.commit();
            commitCommand.setMessage("Commit Message");
            commitCommand.call();

            File file = new File("D:\\Github\\token-for-jgit.txt");
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine(); // 读取文件中的第一行
                System.out.println("a:"+line); // 输出读取的行
            }


            /* Git Push */
            PushCommand pushCommand = git.push();
            pushCommand.setRemote("origin");
            pushCommand.setRefSpecs(new RefSpec("refs/heads/main:refs/heads/main"));
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(token, ""));
            Iterable<PushResult> results = pushCommand.call();
            for (PushResult result : results) {
                System.out.println(result.getMessages());
            }

            logger.info(log.toString());
        } catch (GitAPIException | FileNotFoundException e) {
           throw new RuntimeException(e.getMessage());
        }
    }
}
