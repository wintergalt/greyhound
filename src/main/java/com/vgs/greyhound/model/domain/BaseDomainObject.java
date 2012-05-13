package com.vgs.greyhound.model.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class BaseDomainObject {

    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
