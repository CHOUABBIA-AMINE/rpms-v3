/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: MilitaryCategory
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Administration
 *
 **/

package dz.mdn.rpms.core.administration.domain.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="MilitaryCategory")
@Table(name="T_02_01", uniqueConstraints = { @UniqueConstraint(name = "T_02_01_UK_01", columnNames = { "F_03" })})
public class MilitaryCategory {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", length=50)
	private String designationAr;
	
	@Column(name="F_02", length=50)
	private String designationEn;
	
	@Column(name="F_03", length=50, nullable=false)
	private String designationFr;
	
	@OneToMany(mappedBy = "militaryCategory")
    private List<MilitaryRank> militaryRanks;
	
}