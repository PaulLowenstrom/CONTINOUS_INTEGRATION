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


public class BuildResult implements Serializable
{
    public String repository = null;
    public String branch = null;
    public String linkedCommit = null;
    public boolean status = false;
    public boolean testStatus = false;
    public String message = null;
    public String testMessage = null;
    public String log = "";
}