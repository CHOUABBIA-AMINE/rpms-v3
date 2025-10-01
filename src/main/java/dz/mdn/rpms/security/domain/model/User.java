/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: User
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Security
 *
 **/

package dz.mdn.rpms.security.domain.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dz.mdn.rpms.common.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table( name = "T_01_05", 
		uniqueConstraints = {
			@UniqueConstraint(name="T_01_05_UK_01", columnNames = "F_01"),
			@UniqueConstraint(name="T_01_05_UK_02", columnNames = "F_02")
		})
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = 6957215815941701487L;

	@Column(name="F_01", nullable = false, length = 30)
    private String username;

    @Email
	@Column(name="F_02", nullable = false, length = 100)
    private String email;

    @JsonIgnore
    @Column(name="F_03", nullable = false, length = 120)
    private String password;

    @Column(name="F_04")
    private boolean accountNonExpired = true;
    
    @Column(name="F_05")
    private boolean accountNonLocked = true;
    
    @Column(name="F_06")
    private boolean credentialsNonExpired = true;
    
    @Column(name="F_07")
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "R_01_05__01_03",
            joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_01_05__01_03_FK_01")),
            inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_01_05__01_03_FK_02")))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "R_01_05__01_04",
            joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_01_05__01_04_FK_01")),
            inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_01_05__01_04_FK_02")))
    private Set<Group> groups = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        // Add roles
        for (Role role : roles) {
            authorities.addAll(role.getAuthorities());
        }
        
        // Add groups and their roles
        for (Group group : groups) {
            for (Role groupRole : group.getRoles()) {
                authorities.addAll(groupRole.getAuthorities());
            }
        }
        
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}