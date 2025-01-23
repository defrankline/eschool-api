package com.kachinga.eschool.service.impl;

import com.kachinga.eschool.entity.MenuItemRole;
import com.kachinga.eschool.repository.MenuItemRoleRepository;
import com.kachinga.eschool.repository.specs.MenuItemRoleSpecification;
import com.kachinga.eschool.service.MenuItemRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class MenuItemRoleServiceImpl implements MenuItemRoleService {

    private final MenuItemRoleRepository repository;

    @Override
    public Optional<MenuItemRole> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<MenuItemRole> findExisting(Long roleId, Long menuId) {
        return repository.findByRoleIdAndMenuItemId(roleId, menuId);
    }

    @Override
    public List<MenuItemRole> save(List<MenuItemRole> menuItemRoles) {
        if (!menuItemRoles.isEmpty()) {
            List<MenuItemRole> processed = new ArrayList<>();
            for (MenuItemRole menuItemRole : menuItemRoles) {
                MenuItemRole menu = new MenuItemRole();
                Optional<MenuItemRole> row = this.findExisting(menuItemRole.getRole().getId(), menuItemRole.getMenuItem().getId());
                if (row.isPresent()) {
                    menu = row.get();
                }
                menu.setRole(menuItemRole.getRole());
                menu.setMenuItem(menuItemRole.getMenuItem());
                processed.add(menu);
            }
            return repository.saveAll(processed);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteMultiple(List<Long> ids) {
        if (!ids.isEmpty()) {
            for (Long id : ids) {
                Optional<MenuItemRole> row = repository.findById(id);
                if (row.isPresent()) {
                    repository.deleteById(id);
                }
            }
        }
    }

    @Override
    public List<MenuItemRole> findAll(Long roleId, Long menuItemId) {
        if (roleId == 0 && menuItemId == 0) {
            return repository.findAll(Sort.by("id").ascending());
        } else {
            Specification<MenuItemRole> spec = getFilterSpecs(roleId, menuItemId);
            return repository.findAll(spec, Sort.by("id").ascending());
        }
    }

    @Override
    public List<MenuItemRole> findAllByRoleId(Long roleId) {
        return repository.findAllByRoleId(roleId);
    }

    @Override
    public List<MenuItemRole> findAllByMenuItemId(Long menuItemId) {
        return repository.findAllByMenuItemId(menuItemId);
    }

    @Override
    public Page<MenuItemRole> findAll(Long roleId, Long menuItemId, int page, int size) {
        if (roleId == 0 && menuItemId == 0) {
            return repository.findAll(PageRequest.of(page, size, Sort.by("id").ascending()));
        } else {
            Specification<MenuItemRole> spec = getFilterSpecs(roleId, menuItemId);
            return repository.findAll(spec, PageRequest.of(page, size, Sort.by("id").ascending()));
        }
    }

    private Specification<MenuItemRole> getFilterSpecs(Long roleId, Long menuItemId) {
        Specification<MenuItemRole> spec;
        if (roleId > 0 && menuItemId == 0) {
            spec = Specification.where(MenuItemRoleSpecification.getByRole(roleId));
        } else if (menuItemId > 0 && roleId == 0) {
            spec = Specification.where(MenuItemRoleSpecification.getByMenuItem(menuItemId));
        } else {
            spec = Specification.where(MenuItemRoleSpecification.getByRole(roleId).and(MenuItemRoleSpecification.getByRole(roleId)));
        }
        return spec;
    }

    @Override
    public void deleteByRoleAndMenu(Long roleId, Long menuItemId) {
        Optional<MenuItemRole> row = repository.findByRoleIdAndMenuItemId(roleId, menuItemId);
        if (row.isPresent()) {
            MenuItemRole menu = row.get();
            repository.deleteById(menu.getId());
        }
    }
}
