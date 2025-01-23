package com.kachinga.eschool.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "menu_items", uniqueConstraints = {@UniqueConstraint(columnNames = {"router_link", "parent_id"})})
public class MenuItem extends BaseModel {

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "router_link", nullable = false)
    private String routerLink;

    @Column(name = "icon", nullable = false)
    private String icon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private MenuItem parent;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menu_item_roles",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;
}
