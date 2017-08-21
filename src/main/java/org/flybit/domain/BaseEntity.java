package org.flybit.domain;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;


@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SuppressWarnings("serial")
public abstract class BaseEntity<T extends Entity> implements Entity{

    @Version
    @Column(name = "VERSION", nullable = false)
    private Long version;

    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    
    
}
