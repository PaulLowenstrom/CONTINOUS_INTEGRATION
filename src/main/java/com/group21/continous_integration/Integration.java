package com.group21.continous_integration;

import org.apache.maven.shared.invoker.*;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;
import java.util.Arrays;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.FileSystems;

public class Integration
{
    protected Git repository = null;
    private InvocationRequest request;
    private Invoker invoker;

    public class IntegrationResult
    {
        public boolean okay = false;
        public String message = null;

        public IntegrationResult(InvocationResult result)
        {
            okay = result.getExitCode() == 0;
            if(result.getExecutionException() != null)
                message = result.getExecutionException().getMessage();
        }

        @Override
        public String toString(){
            return (okay ? "success" : "fail (" + message + ")");
        }
    }

    public Integration(String cloneURL, String branch)
    {
        Path integrationsDir = new File("").toPath().resolve("integrations");
        Path repoDir = integrationsDir.resolve(cloneURL.substring(cloneURL.lastIndexOf("/") + 1, cloneURL.length()).replace(".git", ""));

        File branchDir = repoDir.resolve(branch).toFile();
        
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

    public IntegrationResult compile()
    {
        request.setGoals(Arrays.asList("compile"));
        request.setShowErrors(true);
        
        InvocationResult result;
        try{
            result = invoker.execute(request);
            return new IntegrationResult(result);
        } 
        catch(MavenInvocationException e)
        {
            return null;
        }
    }
}