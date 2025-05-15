package com.example.vcs.service;

import com.example.vcs.dao.*;
import com.example.vcs.model.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.*;

public class VersionControlService {
    private final BranchDao branchDao;
    private final CommitDao commitDao;
    private final FileDao fileDao;
    private final MergeStrategy mergeStrategy;

    public VersionControlService(DaoFactory daoFactory, MergeStrategy mergeStrategy) {
        this.branchDao = daoFactory.createBranchDao();
        this.commitDao = daoFactory.createCommitDao();
        this.fileDao = daoFactory.createFileDao();
        this.mergeStrategy = mergeStrategy;
    }

    public Branch createBranch(int repositoryId, String name, Integer startPoint) throws SQLException {
        Branch branch = new Branch(0, repositoryId, name, startPoint);
        return branchDao.insert(branch);
    }

    public Commit commit(int branchId, int authorId, String message, Map<String, String> contents) throws SQLException {
        Branch branch = branchDao.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found: " + branchId));
        Integer parentId = branch.headCommitId();
        Commit newCommit = new Commit(0, branchId, authorId, message, parentId);
        Commit inserted = commitDao.insert(newCommit);
        for (var entry : contents.entrySet()) {
            FileEntry fe = new FileEntry(0, inserted.id(), entry.getKey(), entry.getValue());
            fileDao.insert(fe);
        }
        branchDao.updateHead(branchId, inserted.id());
        return inserted;
    }

    public Commit merge(int targetBranchId, int sourceBranchId, int authorId, String message) throws SQLException, MergeConflictException {
        Branch target = branchDao.findById(targetBranchId)
                .orElseThrow(() -> new IllegalArgumentException("Target branch not found: " + targetBranchId));
        Branch source = branchDao.findById(sourceBranchId)
                .orElseThrow(() -> new IllegalArgumentException("Source branch not found: " + sourceBranchId));
        int leftHead = source.headCommitId();
        int rightHead = target.headCommitId();
        int base = findCommonAncestor(leftHead, rightHead);
        MergeResult result = mergeStrategy.merge(base, leftHead, rightHead);
        if (result instanceof MergeResult.Conflict c) {
            throw new MergeConflictException(c.conflicts());
        } else if (result instanceof MergeResult.Merged m) {
            Map<String, String> mergedContents = m.mergedFiles().stream()
                    .collect(Collectors.toMap(FileEntry::filename, FileEntry::content));
            return commit(targetBranchId, authorId, message, mergedContents);
        }
        throw new IllegalStateException("Unknown merge result");
    }

    private int findCommonAncestor(int commitA, int commitB) throws SQLException {
        Set<Integer> ancestorsA = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(commitA);
        while (!queue.isEmpty()) {
            int cid = queue.poll();
            if (cid <= 0) continue;
            ancestorsA.add(cid);
            commitDao.findById(cid).flatMap(Commit::parentCommitId).ifPresent(queue::add);
        }
        queue.add(commitB);
        while (!queue.isEmpty()) {
            int cid = queue.poll();
            if (ancestorsA.contains(cid)) return cid;
            commitDao.findById(cid).flatMap(Commit::parentCommitId).ifPresent(queue::add);
        }
        return 0;
    }

    public static class MergeConflictException extends Exception {
        private final List<MergeConflict> conflicts;
        public MergeConflictException(List<MergeConflict> conflicts) {
            super("Merge conflicts detected");
            this.conflicts = conflicts;
        }
        public List<MergeConflict> getConflicts() { return conflicts; }
    }
}