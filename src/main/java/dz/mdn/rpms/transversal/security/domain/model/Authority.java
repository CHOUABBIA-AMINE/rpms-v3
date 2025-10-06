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

package dz.mdn.rpms.transversal.security.domain.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_01_02")
@Getter
@Setter
public class Authority {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="F_00", nullable = false, unique = true)
    private Long id;

	@Column(name="F_01", nullable = false, length = 50)
    private String name;

	@Column(name="F_02", nullable = true, length = 200)
    private String description;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY)
    private Set<Permission> permissions = new HashSet<>();
    
}