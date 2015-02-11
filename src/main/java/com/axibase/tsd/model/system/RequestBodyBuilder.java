/*
* Copyright 2015 Axibase Corporation or its affiliates. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
* https://www.axibase.com/atsd/axibase-apache-2.0.pdf
*
* or in the "license" file accompanying this file. This file is distributed
* on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* express or implied. See the License for the specific language governing
* permissions and limitations under the License.
*/
package com.axibase.tsd.model.system;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Nikolay Malevanny.
 */
public class RequestBodyBuilder<T> {
    private T command;
    private List<T> commands = Collections.emptyList();

    public RequestBodyBuilder(T command) {
        this.command = command;
    }

    public RequestBodyBuilder(T[] commands) {
        this.commands = Arrays.asList(commands);
    }

    public T getCommand() {
        return command;
    }

    public List<T> getCommands() {
        return commands;
    }
}
