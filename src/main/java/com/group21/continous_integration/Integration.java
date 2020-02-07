package com.group21.continous_integration;

import org.apache.maven.shared.invoker.*;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.util.Arrays;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.io.Serializable;

public class Integration
{
    private String repoName;
    private String branch;
    private Git repository = null;
    private InvocationRequest request;
    private Invoker invoker;
    private BuildResult lastBuildResult;
    private File branchDir;

    public Integration(String cloneURL, String branch)
    {
        repoName = cloneURL.substring(cloneURL.lastIndexOf("/") + 1, cloneURL.length()).replace(".git", "");
        this.branch = branch;

        Path integrationsDir = new File("").toPath().resolve("integrations");
        Path repoDir = integrationsDir.resolve(repoName);

        branchDir = repoDir.resolve(branch).toFile();
        
        int version = 1;
        File newBranchDir = branchDir;
        while(newBranchDir.exists())
            newBranchDir = repoDir.resolve(branch + "_" + (++version)).toFile();
        branchDir = newBranchDir;
        if(!branchDir.exists())
            branchDir.mkdir();

        System.out.print("* Cloning repository to: " + branchDir + "... ");

        try{
            repository = Git.cloneRepository()
                .setURI(cloneURL)
                .setBranch(branch)
                .setDirectory(branchDir)
                .call();
        }
        catch(GitAPIException e){
            System.out.println(e.getMessage());
        }

        request = new DefaultInvocationRequest();
        request.setPomFile(branchDir.toPath().resolve("pom.xml").toFile());

        invoker = new DefaultInvoker();

        System.out.println("done!");    
    }

    public BuildResult build()
    {
        lastBuildResult = new BuildResult();

        request.setGoals(Arrays.asList("compile"));
        request.setShowErrors(true);
        request.setBatchMode(true);
        request.setOutputHandler(new InvocationOutputHandler(){
            @Override
            public void consumeLine(String line) {
                lastBuildResult.log += line + "\n";
            }
        });
        
        InvocationResult result;
        try{
            result = invoker.execute(request);
            lastBuildResult.repository = repoName;
            lastBuildResult.branch = branch;
            lastBuildResult.linkedCommit = "TODO";
            lastBuildResult.status = result.getExitCode() == 0;
            lastBuildResult.message = result.getExecutionException() != null ? result.getExecutionException().getMessage() : null;

            
            return lastBuildResult;
        } 
        catch(MavenInvocationException e)
        {
            return null;
        }
    }

    public boolean runTest() {

        // Reset from build() run
        request = new DefaultInvocationRequest();
        request.setPomFile(branchDir.toPath().resolve("pom.xml").toFile());

        invoker = new DefaultInvoker();

        // Setup for test run
        request.setGoals(Arrays.asList("test"));
        request.setShowErrors(true);
        request.setBatchMode(true);
        request.setOutputHandler(new InvocationOutputHandler(){
            @Override
            public void consumeLine(String line) {
                lastBuildResult.log += line + "\n";
            }
        });

        // Execute test
        InvocationResult result;
        try{
            result = invoker.execute(request);
            return result.getExitCode() == 0;
            
        } 
        catch(MavenInvocationException e)
        {
            return false;
        }

    }
}