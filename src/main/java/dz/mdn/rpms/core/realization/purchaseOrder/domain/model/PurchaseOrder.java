/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: PurchaseOrder
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.purchaseOrder.domain.model;

import java.util.Date;
import java.util.List;

import dz.mdn.rpms.core.administration.domain.model.Structure;
import dz.mdn.rpms.core.communication.domain.model.Mail;
import dz.mdn.rpms.core.document.domain.model.Document;
import dz.mdn.rpms.core.financial.domain.model.BudgetType;
import dz.mdn.rpms.core.financial.domain.model.PlannedItem;
import dz.mdn.rpms.core.realization.common.domain.model.ApprovalStatus;
import dz.mdn.rpms.core.realization.common.domain.model.Project;
import dz.mdn.rpms.core.realization.common.domain.model.RealizationNature;
import dz.mdn.rpms.core.realization.common.domain.model.RealizationStatus;
import dz.mdn.rpms.core.realization.provider.domain.model.Provider;
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
import jakarta.persistence.ManyToOne;
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
@Entity(name="PurchaseOrder")
@Table(name="T_25_01", uniqueConstraints = { @UniqueConstraint(name = "T_25_01_UK_01", columnNames = { "F_01", "F_02" })})
public class PurchaseOrder {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", length=3, nullable=false)
	private String internalId;
	
	@Column(name="F_02", length=4, nullable=false)
	private String purchaseOrderYear;
	
	@Column(name="F_03", length=20)
	private String reference;
	
	@Column(name="F_04", length=300)
	private String designationAr;

	@Column(name="F_05", length=300)
	private String designationEn;
	
	@Column(name="F_06", length=300, nullable=false)
	private String designationFr;
	
	@Column(name="F_07")
	private double allocatedAmount;
	
	@Column(name="F_08")
	private double financialEstimation;
	
	@Column(name="F_09")
	private Date startDate;
	
	@Column(name="F_10")
	private Date approvalDate;

	@Column(name="F_11", length=500)
	private String observation;
	
	@ManyToOne
    @JoinColumn(name="F_12", foreignKey=@ForeignKey(name="T_25_01_FK_02"), nullable=false)
    private RealizationNature realizationNature;
	
	@ManyToOne
    @JoinColumn(name="F_13", foreignKey=@ForeignKey(name="T_25_01_FK_03"), nullable=false)
    private BudgetType budgetType;
	
	@ManyToOne
    @JoinColumn(name="F_14", foreignKey=@ForeignKey(name="T_25_01_FK_04"), nullable=false)
    private RealizationStatus realizationStatus;
	
	@ManyToOne
    @JoinColumn(name="F_15", foreignKey=@ForeignKey(name="T_25_01_FK_05"), nullable=false)
    private ApprovalStatus approvalStatus;
	
	@ManyToOne
    @JoinColumn(name="F_16", foreignKey=@ForeignKey(name="T_25_01_FK_07"), nullable=false)
    private Provider provider;
	
	@ManyToOne
    @JoinColumn(name="F_17", foreignKey=@ForeignKey(name="T_25_01_FK_08"), nullable=false)
    private Project project;
	
	//@ManyToOne
    //@JoinColumn(name="F_20", foreignKey=@ForeignKey(name="T_25_01_FK_07"), nullable=false)
    //private ConsultationStep consultationStep;
	
	@OneToMany(mappedBy="purchaseOrder")
	private List<PurchasedItem> purchasedItems;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "R_25_01_02_03", 
			joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_25_01_02_03_FK_01")), 
			inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_25_01_02_03_FK_02")),
			uniqueConstraints = @UniqueConstraint(name = "R_25_01_02_03_UK_01", columnNames = {"F_01", "F_02"}))
	private List<Structure> beneficiaries;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "R_25_01_03_21", 
			joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_25_01_05_02_FK_01")), 
			inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_25_01_05_02_FK_02")),
			uniqueConstraints = @UniqueConstraint(name = "R_25_01_05_02_UK_01", columnNames = {"F_01", "F_02"}))
	private List<Document> documents;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "R_25_01_04_03", 
			joinColumns = @JoinColumn(name = "F_01", foreignKey = @ForeignKey(name = "R_25_01_04_03_FK_01")), 
			inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey = @ForeignKey(name = "R_25_01_04_03_FK_02")),
			uniqueConstraints = @UniqueConstraint(name = "R_25_01_04_03_UK_01", columnNames = {"F_01", "F_02"}))
	private List<Mail> referencedMails;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "R_25_01_10_06", 
			joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_25_01_10_06_FK_01")), 
			inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_25_01_10_06_FK_02")),
			uniqueConstraints = @UniqueConstraint(name = "R_25_01_10_06_UK_01", columnNames = {"F_01", "F_02"}))
	private List<PlannedItem> plannedItems;

}