/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ItemDistribution
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Financial
 *
 **/

package dz.mdn.rpms.core.financial.domain.model;

import dz.mdn.rpms.core.administration.domain.model.Structure;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name="ItemDistribution")
@Table(name="T_10_09")
public class ItemDistribution {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01")
	private float Quantity;
	
	@ManyToOne
    @JoinColumn(name="F_02", foreignKey=@ForeignKey(name="T_10_09_FK_01"), nullable=false)
    private PlannedItem plannedItem;
	
	@ManyToOne
    @JoinColumn(name="F_03", foreignKey=@ForeignKey(name="T_10_09_FK_02"), nullable=false)
    private Structure structure;
	
	//@ManyToOne
    //@JoinColumn(name="F_04", foreignKey=@ForeignKey(name="T_10_12_FK_03"), nullable=false)
    //private BudgetGoal budgetGoalStatus;

}