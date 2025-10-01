/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: State
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
@Entity(name="State")
@Table(name="T_00_02", uniqueConstraints = { @UniqueConstraint(name = "T_00_02_UK_01", columnNames = { "F_01" }), 
											 @UniqueConstraint(name = "T_00_02_UK_02", columnNames = { "F_02" }), 
											 @UniqueConstraint(name = "T_00_02_UK_03", columnNames = { "F_03" })})
public class State {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", length=100, nullable=false)
	private String code;
	
	@Column(name="F_02", length=100, nullable=false)
	private String designationAr;

	@Column(name="F_03", length=100, nullable=false)
	private String designationLt;


}