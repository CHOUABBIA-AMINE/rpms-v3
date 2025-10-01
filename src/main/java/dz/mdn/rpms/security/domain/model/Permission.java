/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: Permission
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
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_01_01")
@Getter
@Setter
@NoArgsConstructor
public class Permission extends BaseEntity {

	private static final long serialVersionUID = -5820724918712335982L;

	@Column(name="F_01", nullable = false, length = 50)
    private String name;

    @Column(name="F_02", nullable = true, length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="F_03", foreignKey=@ForeignKey(name="T_01_01_FK_01"))
    private Authority authority;
    
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();
    
}