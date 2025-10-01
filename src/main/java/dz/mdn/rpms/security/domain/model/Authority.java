/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: Authority
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Security
 *
 **/

package dz.mdn.rpms.security.domain.model;

import java.util.HashSet;
import java.util.Set;

import dz.mdn.rpms.common.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_01_02")
@Getter
@Setter
public class Authority extends BaseEntity {

	private static final long serialVersionUID = -2398694818273587884L;

	@Column(name="F_01", nullable = false, length = 50)
    private String name;

	@Column(name="F_02", nullable = true, length = 200)
    private String description;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY)
    private Set<Permission> permissions = new HashSet<>();
    
}