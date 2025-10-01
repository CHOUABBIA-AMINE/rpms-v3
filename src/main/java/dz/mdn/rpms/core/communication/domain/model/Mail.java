/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Mail
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Communication
 *
 **/

package dz.mdn.rpms.core.communication.domain.model;

import java.util.Date;
import java.util.List;

import dz.mdn.rpms.core.administration.domain.model.Structure;
import dz.mdn.rpms.utility.domain.model.File;
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
@Entity(name="Mail")
@Table(name="T_04_03", uniqueConstraints = { @UniqueConstraint(name = "T_04_03_UK_01", columnNames = { "F_01" })})
public class Mail {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", length=50)
	private String reference;
	
	@Column(name="F_02", length=50, nullable=true)
	private String recordNumber;
	
	@Column(name="F_03", length=500)
	private String subject;
	
	@Column(name="F_04")
	private Date mailDate;
	
	@Column(name="F_05")
	private Date recordDate;
	
	@ManyToOne
    @JoinColumn(name="F_06", foreignKey=@ForeignKey(name="T_04_03_FK_01"), nullable=false)
    private MailNature mailNature;
	
	@ManyToOne
    @JoinColumn(name="F_07", foreignKey=@ForeignKey(name="T_04_03_FK_02"), nullable=false)
    private MailType mailType;
	
	@ManyToOne
    @JoinColumn(name="F_08", foreignKey=@ForeignKey(name="T_04_03_FK_03"), nullable=false)
    private Structure structure;
	
	@ManyToOne
    @JoinColumn(name="F_09", foreignKey=@ForeignKey(name="T_04_03_FK_04"), nullable=false)
    private File file;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "R_04_03_04_03", 
			joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_04_03_04_03_FK_01")), 
			inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_04_03_04_03_FK_02")),
			uniqueConstraints = @UniqueConstraint(name = "R_04_03_04_03_UK_01", columnNames = {"F_01", "F_02"}))
	private List<Mail> referencedMails;

}