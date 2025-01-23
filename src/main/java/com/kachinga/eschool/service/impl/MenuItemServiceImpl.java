package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.dto.MenuDto;
import com.kachinga.eschool.dto.TreeDto;
import com.kachinga.eschool.entity.MenuItem;
import com.kachinga.eschool.repository.MenuItemRepository;
import com.kachinga.eschool.repository.specs.MenuItemSpecification;
import com.kachinga.eschool.service.MenuItemService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository repository;

    private final EntityManager entityManager;

    @Override
    public Optional<MenuItem> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        return repository.save(menuItem);
    }

    @Override
    public MenuItem update(MenuItem menuItem) {
        return repository.save(menuItem);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<MenuItem> findAll(Long parentId, String searchTerm, String sortBy, String direction) {
        Sort sort = direction.equals("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        if (parentId > 0) {
            Specification<MenuItem> spec;
            if (searchTerm.isEmpty()) {
                spec = Specification.where(MenuItemSpecification.parent(parentId));
            } else {
                spec = Specification.where(MenuItemSpecification.parent(parentId).and(
                        MenuItemSpecification.labelLike(searchTerm).or(MenuItemSpecification.routerLinkLike(searchTerm))
                ));
            }
            return repository.findAll(spec, sort);
        } else {
            if (searchTerm.isEmpty()) {
                return repository.findAll(sort);
            } else {
                Specification<MenuItem> spec = Specification.where(
                        MenuItemSpecification.labelLike(searchTerm).or(MenuItemSpecification.routerLinkLike(searchTerm))
                );
                return repository.findAll(spec, sort);
            }
        }
    }

    @Override
    public Page<MenuItem> findAll(Long parentId, int page, int size, String searchTerm, String sortBy, String direction) {
        Sort sort = direction.equals("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        if (parentId > 0) {
            Specification<MenuItem> spec;
            Pageable pageable;
            if (searchTerm.isEmpty()) {
                spec = MenuItemSpecification.parent(parentId);
                pageable = PageRequest.of(page, size, sort);
            } else {
                spec = Specification.where(MenuItemSpecification.parent(parentId).and(
                        MenuItemSpecification.labelLike(searchTerm).or(MenuItemSpecification.routerLinkLike(searchTerm))
                ));
                pageable = PageRequest.of(0, size, sort);
            }
            return repository.findAll(spec, pageable);
        } else {
            if (searchTerm.isEmpty()) {
                Pageable pageable = PageRequest.of(page, size, sort);
                return repository.findAll(pageable);
            } else {
                Specification<MenuItem> spec = Specification.where(
                        MenuItemSpecification.labelLike(searchTerm).or(MenuItemSpecification.routerLinkLike(searchTerm))
                );
                Pageable pageable = PageRequest.of(0, size, sort);
                return repository.findAll(spec, pageable);
            }
        }
    }

    @Override
    public void deleteMultiple(List<Long> ids) {
        if (ids.size() > 0) {
            for (Long id : ids) {
                Optional<MenuItem> row = repository.findById(id);
                if (row.isPresent()) {
                    repository.deleteById(id);
                }
            }
        }
    }

    @Override
    public List<MenuDto> parents(List<Long> roles) {
        Query q = entityManager.createNativeQuery("select t.id, t.label, t.icon, t.\"routerLink\" from (select distinct r.id, r.label, r.icon, r.router_link as \"routerLink\", r.sort_order from menu_items r join menu_item_roles rr on r.id = rr.menu_item_id where rr.role_id in (:roles) and r.parent_id is null) t order by t.sort_order asc");
        q.setParameter("roles", roles);
        List<Object[]> objects = q.getResultList();
        List<MenuDto> parents = new ArrayList<>();
        for (Object[] a : objects) {
            MenuDto tileDto = new MenuDto();
            tileDto.setId((Long) a[0]);
            tileDto.setLabel((String) a[1]);
            tileDto.setIcon((String) a[2]);
            tileDto.setRouterLink((String) a[3]);
            parents.add(tileDto);
        }
        return parents;
    }

    @Override
    public List<MenuDto> parents() {
        Query q = entityManager.createNativeQuery("select t.id, t.label, t.icon, t.router_link as \"routerLink\",t.sort_order as \"sortOrder\",t.parent_id as \"parentId\" from menu_items t where t.parent_id is null order by t.sort_order asc");
        return processMenuItems(q);
    }

    @Override
    public List<MenuDto> children(Long parentId) {
        Query q = entityManager.createNativeQuery("select t.id, t.label, t.icon, t.router_link as \"routerLink\",t.sort_order as \"sortOrder\",t.parent_id as \"parentId\" from menu_items t where t.parent_id = :parentId order by t.sort_order asc");
        q.setParameter("parentId", parentId);
        return processMenuItems(q);
    }

    private static List<MenuDto> processMenuItems(Query q) {
        List<Object[]> objects = q.getResultList();
        List<MenuDto> parents = new ArrayList<>();
        for (Object[] a : objects) {
            MenuDto tileDto = new MenuDto();
            tileDto.setId((Long) a[0]);
            tileDto.setLabel((String) a[1]);
            tileDto.setIcon((String) a[2]);
            tileDto.setRouterLink((String) a[3]);
            tileDto.setSortOrder((Integer) a[4]);
            tileDto.setParentId((Long) a[5]);
            parents.add(tileDto);
        }
        return parents;
    }

    @Override
    public List<MenuDto> children(List<Long> roles, Long parentId) {
        Query q = entityManager.createNativeQuery("select t.id, t.label as name, t.icon, t.\"routerLink\" from (select distinct r.id, r.label, r.icon, r.router_link as \"routerLink\", r.sort_order from menu_items r join menu_item_roles rr on r.id = rr.menu_item_id where rr.role_id in (:roles) and r.parent_id = :parentId) t order by t.sort_order asc");
        q.setParameter("roles", roles);
        q.setParameter("parentId", parentId);
        List<Object[]> objects = q.getResultList();
        List<MenuDto> parents = new ArrayList<>();
        for (Object[] a : objects) {
            MenuDto tileDto = new MenuDto();
            tileDto.setId((Long) a[0]);
            tileDto.setLabel((String) a[1]);
            tileDto.setIcon((String) a[2]);
            tileDto.setRouterLink((String) a[3]);
            parents.add(tileDto);
        }
        return parents;
    }

    @Override
    public List<MenuDto> roleMenuItems(List<Long> roles, Long parentId) {
        List<MenuDto> parents = this.parents(roles);
        if (parentId > 0) {
            parents = this.children(roles, parentId);
        }

        List<MenuDto> tree = new ArrayList<>();
        if (!parents.isEmpty()) {
            for (MenuDto report : parents) {
                MenuDto t0 = new MenuDto();
                t0.setId(report.getId());
                t0.setLabel(report.getLabel());
                t0.setIcon(report.getIcon());
                t0.setRouterLink(report.getRouterLink());
                t0.setParentId(parentId);
                List<MenuDto> children = roleMenuItems(roles, report.getId());
                if (!children.isEmpty()) {
                    t0.setChildren(children);
                } else {
                    t0.setChildren(null);
                }
                tree.add(t0);
            }
        }
        return tree;
    }

    @Override
    public List<TreeDto> tree(Long parentId) {
        List<MenuDto> parents = this.parents();
        if (parentId > 0) {
            parents = this.children(parentId);
        }

        List<TreeDto> tree = new ArrayList<>();
        if (parents.size() > 0) {
            for (MenuDto report : parents) {
                TreeDto t0 = new TreeDto();
                t0.setId(report.getId());
                t0.setKey(report.getId().toString());
                t0.setLabel(report.getLabel());
                t0.setData(report.getRouterLink());
                t0.setIcon(report.getIcon());
                t0.setRouterLink(report.getRouterLink());
                t0.setSortOrder(report.getSortOrder());
                t0.setParentId(report.getParentId());
                List<TreeDto> children = tree(report.getId().longValue());
                t0.setChildren(children);
                tree.add(t0);
            }
        }
        return tree;
    }

    @Override
    public List<TreeDto> tree(List<Long> roles, Long parentId) {
        List<MenuDto> parents = this.parents(roles);
        if (parentId > 0) {
            parents = this.children(roles, parentId);
        }
        List<TreeDto> tree = new ArrayList<>();
        if (parents.size() > 0) {
            for (MenuDto report : parents) {
                TreeDto t0 = new TreeDto();
                t0.setId(report.getId());
                t0.setKey(report.getId().toString());
                t0.setLabel(report.getLabel());
                t0.setData(report.getRouterLink());
                t0.setIcon(report.getIcon());
                t0.setRouterLink(report.getRouterLink());
                t0.setSortOrder(report.getSortOrder());
                t0.setParentId(report.getParentId());
                List<TreeDto> children = tree(roles, report.getId());
                t0.setChildren(children);
                tree.add(t0);
            }
        }
        return tree;
    }
}
