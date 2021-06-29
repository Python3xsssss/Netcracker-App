package com.netcracker.skillstable.model.dto;

import java.util.Set;

public abstract class OrgItem {
    protected Long id;
    protected String name;
    protected String description;

    protected User leader;

    protected Object superior;
    protected Set<Object> subsidiary;
}
