/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Item
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Financial
 *
 **/

package dz.mdn.rpms.core.financial.domain.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name="Item")
@Table(name="T_10_06")
public class Item {
	
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
	
	@ManyToOne
    @JoinColumn(name="F_04", foreignKey=@ForeignKey(name="T_10_06_FK_01"), nullable=false)
    private Rubric rubric;
	
	@OneToMany(mappedBy="item")
    private Set<PlannedItem> plannedItems = new HashSet<PlannedItem>();

}