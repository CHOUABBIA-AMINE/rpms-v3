/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Country
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Common
 *
 **/

package dz.mdn.rpms.common.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name="Country")
@Table(name="T_00_01", uniqueConstraints = { @UniqueConstraint(name = "T_00_01_UK_01", columnNames = { "F_03" })})
public class Country {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", length=100)
	private String designationAr;

	@Column(name="F_02", length=100)
	private String designationEn;
	
	@Column(name="F_03", length=100, nullable=false)
	private String designationFr;

}