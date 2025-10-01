/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Submission
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.consultation.domain.model;

import java.util.Date;

import dz.mdn.rpms.core.realization.provider.domain.model.Provider;
import dz.mdn.rpms.utility.domain.model.File;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name="Submission")
@Table(name="T_22_05", uniqueConstraints = { @UniqueConstraint(name = "T_22_05_UK_01", columnNames = { "F_03", "F_04" })})
public class Submission {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", nullable=true)
	private Date submissionDate;
	
	@Column(name="F_02", nullable=true)
	private double financialOffer;
	
	@ManyToOne
    @JoinColumn(name="F_03", foreignKey=@ForeignKey(name="T_22_05_FK_01"), nullable=false)
    private Consultation consultation;
	
	@ManyToOne
    @JoinColumn(name="F_04", foreignKey=@ForeignKey(name="T_22_05_FK_02"), nullable=false)
    private Provider tender;
	
	@ManyToOne
    @JoinColumn(name="F_05", foreignKey=@ForeignKey(name="T_22_05_FK_03"), nullable=true)
    private File administrativePart;
	
	@ManyToOne
    @JoinColumn(name="F_06", foreignKey=@ForeignKey(name="T_22_05_FK_04"), nullable=true)
    private File technicalPart;
	
	@ManyToOne
    @JoinColumn(name="F_07", foreignKey=@ForeignKey(name="T_22_05_FK_05"), nullable=true)
    private File financialPart;

}