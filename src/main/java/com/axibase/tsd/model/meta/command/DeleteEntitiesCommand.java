package com.axibase.tsd.model.meta.command;

import com.axibase.tsd.model.meta.Entity;
import com.axibase.tsd.util.AtsdUtil;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author Nikolay Malevanny.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteEntitiesCommand extends AbstractEntitiesCommand {
    public DeleteEntitiesCommand() {
        super(AtsdUtil.DELETE_COMMAND);
    }

    public DeleteEntitiesCommand(List<Entity> entities) {
        this();
        this.entities = entities;
    }
}