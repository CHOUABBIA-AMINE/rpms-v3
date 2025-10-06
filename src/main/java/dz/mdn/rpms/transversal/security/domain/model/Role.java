/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: Role
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
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "T_01_03")
@Getter
@Setter
public class Role {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="F_00", nullable = false, unique = true)
    private Long id;

	@Column(name="F_01", nullable = false, length = 50)
    private String name;

    @Column(name="F_02", nullable = true, length = 200)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "R_01_03__01_01",
            joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_01_03_01_01_FK_01")),
            inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_01_03_01_01_FK_02")))
    private Set<Permission> permissions = new HashSet<>();

    public Set<GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toSet());
    }
}