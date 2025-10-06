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
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "T_01_02", 
		uniqueConstraints = {
			@UniqueConstraint(name="T_01_02_UK_01", columnNames = "F_01")
		}, indexes = {
		    @Index(name = "T_01_02_IDX_01", columnList = "F_01")
		})
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
    @NotBlank
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="F_00", nullable = false, unique = true)
    private Long id;

    @NotBlank
    @Column(name="F_01", nullable = false, length = 50)
    private String name;

	@Column(name="F_02", nullable = true, length = 200)
    private String description;

    @OneToMany(mappedBy = "authority", fetch = FetchType.LAZY)
    private Set<Permission> permissions = new HashSet<>();
    
}