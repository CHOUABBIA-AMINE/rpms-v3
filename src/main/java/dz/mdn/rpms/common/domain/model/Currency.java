/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Currency
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Administration
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
@Entity(name="Currency")
@Table(name="T_00_03", uniqueConstraints = { @UniqueConstraint(name = "T_00_03_UK_01", columnNames = { "F_01" }), 
		 									 @UniqueConstraint(name = "T_00_03_UK_02", columnNames = { "F_02" }), 
		 									 @UniqueConstraint(name = "T_00_03_UK_03", columnNames = { "F_03" }), 
		 									 @UniqueConstraint(name = "T_00_03_UK_04", columnNames = { "F_04" }), 
		 									 @UniqueConstraint(name = "T_00_03_UK_05", columnNames = { "F_05" })})
public class Currency {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", length=50, nullable=false)
	private String designationAr;

	@Column(name="F_02", length=50, nullable=false)
	private String designationEn;
	
	@Column(name="F_03", length=50, nullable=false)
	private String designationFr;
	
	@Column(name="F_04", length=20, nullable=false)
	private String codeAr;
	
	@Column(name="F_05", length=20, nullable=false)
	private String codeLt;


}