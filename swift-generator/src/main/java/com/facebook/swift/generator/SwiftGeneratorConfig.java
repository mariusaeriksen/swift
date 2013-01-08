/*
 * Copyright (C) 2012 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.facebook.swift.generator;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SwiftGeneratorConfig
{
    private final File inputFolder;
    private final File [] inputFiles;
    private final File outputFolder;
    private final String overridePackage;
    private final String defaultPackage;
    private final boolean addThriftExceptions;
    private final boolean generateIncludedCode;

    private SwiftGeneratorConfig(final File inputFolder,
                                 final File [] inputFiles,
                                 final File outputFolder,
                                 final String overridePackage,
                                 final String defaultPackage,
                                 final boolean addThriftExceptions,
                                 final boolean generateIncludedCode)
    {
        this.inputFolder = inputFolder;
        this.inputFiles = inputFiles;
        this.outputFolder = outputFolder;
        this.overridePackage = overridePackage;
        this.defaultPackage = defaultPackage;
        this.addThriftExceptions = addThriftExceptions;
        this.generateIncludedCode = generateIncludedCode;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Returns the input folder which contains Thrift IDL files.
     */
    public File getInputFolder()
    {
        return inputFolder;
    }

    /**
     * Returns an array of input files that are read. Files can be relative
     * to the input folder or absolute.
     */
    public File[] getInputFiles()
    {
        return inputFiles;
    }

    /**
     * Returns the output folder which will contain the generated sources.
     */
    public File getOutputFolder()
    {
        return outputFolder;
    }

    /**
     * If non-null, overrides the java namespace definitions in the IDL files.
     */
    public String getOverridePackage()
    {
        return overridePackage;
    }

    /**
     * If no namespace was set in the Thrift IDL file, fall back to this package.
     */
    public String getDefaultPackage()
    {
        return defaultPackage;
    }

    /**
     * If true, adds {@link org.apache.thrift.TException} to the method signature
     * of all generated service methods.
     */
    public boolean isAddThriftExceptions()
    {
        return addThriftExceptions;
    }

    /**
     * If true, generate code for all included Thrift IDLs instead of just referring to
     * them.
     */
    public boolean isGenerateIncludedCode()
    {
        return generateIncludedCode;
    }

    public static class Builder
    {
        private File inputFolder = null;
        private final List<File> inputFiles = Lists.newArrayList();
        private File outputFolder = null;
        private String overridePackage = null;
        private String defaultPackage = null;
        private boolean addThriftExceptions = true;
        private boolean generateIncludedCode = false;

        private boolean relativeInputFileSeen = false;

        private Builder()
        {
        }

        public SwiftGeneratorConfig build()
        {
            Preconditions.checkState(outputFolder != null, "output folder must be set!");
            Preconditions.checkState(inputFiles.size() > 0, "no input files given!");
            Preconditions.checkState(!(inputFolder == null && relativeInputFileSeen), "no input folder set and a relative input file given!");

            return new SwiftGeneratorConfig(
                inputFolder,
                inputFiles.toArray(new File [inputFiles.size()]),
                outputFolder,
                overridePackage,
                defaultPackage,
                addThriftExceptions,
                generateIncludedCode);
        }

        public Builder inputFolder(final File inputFolder)
        {
            this.inputFolder = inputFolder;
            return this;
        }

        public Builder outputFolder(final File outputFolder)
        {
            this.outputFolder = outputFolder;
            return this;
        }

        public Builder addInputFiles(final File ... inputFiles)
        {
            this.inputFiles.addAll(Arrays.asList(inputFiles));
            return this;
        }

        public Builder addInputFiles(final Collection<File> inputFiles)
        {
            this.inputFiles.addAll(inputFiles);
            return this;
        }

        public Builder overridePackage(final String overridePackage)
        {
            this.overridePackage = overridePackage;
            return this;
        }

        public Builder defaultPackage(final String defaultPackage)
        {
            this.defaultPackage = defaultPackage;
            return this;
        }

        public Builder clearAddThriftExceptions()
        {
            this.addThriftExceptions = false;
            return this;
        }

        public Builder addThriftExceptions(final boolean addThriftExceptions)
        {
            this.addThriftExceptions = addThriftExceptions;
            return this;
        }

        public Builder generateIncludedCode(final boolean generateIncludedCode)
        {
            this.generateIncludedCode = generateIncludedCode;
            return this;
        }

        public Builder setGenerateIncludedCode()
        {
            this.generateIncludedCode = true;
            return this;
        }
    }
}