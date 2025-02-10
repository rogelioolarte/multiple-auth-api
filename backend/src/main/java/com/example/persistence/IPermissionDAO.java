package com.example.persistence;

import com.example.entity.Permission;
import java.util.UUID;

/**
 * Data Access Object (DAO) interface for managing {@code Permission} entities.
 * Extends {@link IGenericDAO} to inherit common CRUD operations.
 *
 * @see IGenericDAO
 */
public interface IPermissionDAO extends IGenericDAO<Permission, UUID> {
}
