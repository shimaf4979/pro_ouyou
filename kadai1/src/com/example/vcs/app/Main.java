package com.example.vcs.app;

import com.example.vcs.dao.DaoFactory;
import com.example.vcs.service.*;
import com.example.vcs.model.*;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            DaoFactory factory = DaoFactory.create();
            MergeStrategy strategy = new ThreeWayMergeStrategy(factory.createFileDao());
            VersionControlService vcs = new VersionControlService(factory, strategy);

            // 前提: ユーザーID=1, リポジトリID=1 が事前にDBにある
            Branch branch = vcs.createBranch(1, "main", null);
            System.out.println("Created branch: " + branch);

            Map<String, String> contents = new HashMap<>();
            contents.put("README.txt", "Hello world!");
            Commit commit1 = vcs.commit(branch.id(), 1, "Initial commit", contents);
            System.out.println("Commit1: " + commit1);

            contents.put("README.txt", "Hello VCS!");
            Commit commit2 = vcs.commit(branch.id(), 1, "Update README", contents);
            System.out.println("Commit2: " + commit2);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (VersionControlService.MergeConflictException e) {
            System.err.println("Merge conflict:");
            e.getConflicts().forEach(c -> System.err.println(c.filename()));
        }
    }
}