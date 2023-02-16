package com.video.videostreaming.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class Video implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    private boolean isVideoSaved;

    private boolean isDeleted;

    @Column(unique = true, nullable = false)
    private String secureId;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Comment comment;
    
    @OneToMany(targetEntity = GenreBook.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_book", referencedColumnName = "id")
    private List<GenreBook> genreBooks;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @PrePersist
    public void setTimestamp(){
        setCreateAt(new Date());
    }

}