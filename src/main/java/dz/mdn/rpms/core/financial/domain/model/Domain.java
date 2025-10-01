/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Domain
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Organization
 *
 **/

package dz.mdn.rpms.core.financial.domain.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name="Domain")
@Table(name="T_10_04", uniqueConstraints = { @UniqueConstraint(name = "T_10_04_UK_01", columnNames = { "F_03" })})
public class Domain {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", length=200)
	private String designationAr;

	@Column(name="F_02", length=200)
	private String designationEn;
	
	@Column(name="F_03", length=200, nullable=false)
	private String designationFr;
	
	@OneToMany(mappedBy="domain")
    private List<Rubric> rubrics;

}