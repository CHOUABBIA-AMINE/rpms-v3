/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: Group
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_01_04")
@Getter
@Setter
public class Group extends BaseEntity {

	private static final long serialVersionUID = -7615060149276904733L;

	@Column(name="F_01", nullable = false, length = 50)
    private String name;

    @Column(name="F_02", nullable = true, length = 200)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "R_01_04__01_03",
            joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_01_04__01_03_FK_01")),
            inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_01_04__01_03_FK_02")))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
    
}