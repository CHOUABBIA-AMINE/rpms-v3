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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_01_02")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority {

	@CreationTimestamp
    @Column(name = "A_01", nullable = false, updatable = false)
    private Date createdAt;

	@UpdateTimestamp
    @Column(name = "A_02", nullable = true, updatable = true)
    private Date updatedAt;

    @Version
    @Column(name = "A_03")
    private Long version;
	
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