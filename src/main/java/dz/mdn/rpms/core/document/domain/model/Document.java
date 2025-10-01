/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Document
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Document
 *
 **/

package dz.mdn.rpms.core.document.domain.model;

import java.util.Date;

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
@Entity(name="Document")
@Table(name="T_05_02")
public class Document {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", length=500)
	private String reference;
	
	@Column(name="F_02")
	private Date issueDate;
	
	@ManyToOne
    @JoinColumn(name="F_03", foreignKey=@ForeignKey(name="T_05_02_FK_01"), nullable=false)
    private DocumentType documentType;
	
	@ManyToOne
    @JoinColumn(name="F_04", foreignKey=@ForeignKey(name="T_05_02_FK_02"), nullable=true)
    private File file;
	
}