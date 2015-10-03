package io.pivotal.chicago.posts;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Post extends AbstractPersistable<Long> {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
