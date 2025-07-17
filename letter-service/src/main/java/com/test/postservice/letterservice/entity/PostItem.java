package com.test.postservice.letterservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "post_item")
public class PostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "post_item_type")
    private PostItemType postItemType;

    @Column(nullable = false, name = "post_code")
    private int postCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private PostItemStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_item_offices",
            joinColumns = @JoinColumn(name = "post_item_id"),
            inverseJoinColumns = @JoinColumn(name = "post_office_id"))
    private List<PostOffice> postOffices;
}
