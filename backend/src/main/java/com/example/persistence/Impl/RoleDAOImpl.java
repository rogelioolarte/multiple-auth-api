package com.example.persistence.Impl;

import com.example.entity.Role;
import com.example.exception.NotFoundException;
import com.example.persistence.IRoleDAO;
import com.example.repository.GenericRepo;
import com.example.repository.RoleRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoleDAOImpl extends GenericDAOImpl<Role, UUID> implements IRoleDAO {

    private final RoleRepo repo;
    private final EntityManager entityManager;

    @Override
    protected GenericRepo<Role, UUID> getRepo() {
        return repo;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected Class<Role> getEntity() {
        return Role.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<Role> findByRoleName(String roleName) {
        if (roleName == null || roleName.isBlank()) return Optional.empty();
        return Optional.ofNullable((Role) entityManager.createNativeQuery(
                        "SELECT * FROM roles WHERE role_name = :roleName", Role.class)
                .setParameter("roleName", roleName)
                .getResultList().stream().findFirst().orElse(null));
    }

    @Override
    public void deleteById(UUID id) {
        entityManager.createNativeQuery("DELETE FROM user_roles WHERE role_id = :roleId")
                .setParameter("roleId", id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM role_permissions WHERE role_id = :roleId")
                .setParameter("roleId", id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM roles WHERE role_id = :roleId")
                .setParameter("roleId", id).executeUpdate();
    }

    @Override
    public Role updateById(Role role, UUID id) {
        Role existingRole = findById(id).orElseThrow(() ->
                new NotFoundException(String.format("%s Object with ID %s not found",
                        getEntity().getName(), id.toString())));
        if (role.getRoleName() != null && !role.getRoleName().isBlank()) {
            existingRole.setRoleName(role.getRoleName());
        }
        if (role.getRoleDescription() != null && !role.getRoleDescription().isBlank()) {
            existingRole.setRoleDescription(role.getRoleDescription());
        }
        if (role.getPermissions() != null && !role.getPermissions().isEmpty()) {
            existingRole.setPermissions(role.getPermissions());
        }
        return super.save(existingRole);
    }

    @Override
    public boolean existsByUniqueProperties(Role role) {
        if (role.getRoleName() == null || role.getRoleName().isBlank() ||
                role.getRoleDescription() == null || role.getRoleDescription().isBlank())
            return false;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Role> roleRoot = query.from(Role.class);
        query.select(cb.count(roleRoot)).where(cb.and(cb.or(
                role.getRoleName() != null && !role.getRoleName().isBlank() ?
                        cb.equal(roleRoot.get("roleName"), role.getRoleName())
                        : cb.conjunction(),
                role.getRoleDescription() != null && !role.getRoleDescription().isBlank() ?
                        cb.equal(roleRoot.get("roleDescription"), role.getRoleDescription())
                        : cb.conjunction())));
        return entityManager.createQuery(query).getResultList()
                .stream().findFirst().orElse(0L) > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> findRolesByPermissionId(UUID permissionId) {
        if (permissionId == null) return List.of();
        return entityManager.createNativeQuery(
                        "SELECT r.* FROM roles r " +
                                "INNER JOIN role_permissions rp ON r.role_id = rp.role_id " +
                                "WHERE rp.permission_id = :permissionId", Role.class)
                .setParameter("permissionId", permissionId).getResultList();
    }

}
