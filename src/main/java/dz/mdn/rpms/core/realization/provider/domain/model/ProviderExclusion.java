/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: ProviderExclusion
 *	@CreatedOn	: 06-26-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Realization
 *
 **/

package dz.mdn.rpms.core.realization.provider.domain.model;

import java.util.Date;

import dz.mdn.rpms.core.communication.domain.model.Mail;
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
@Entity(name="ProviderExclusion")
@Table(name="T_21_07")
public class ProviderExclusion {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="F_01", nullable=false)
	private Date startDate;

	@Column(name="F_02", nullable=true)
	private Date endDate;
	
	@Column(name="F_03", nullable=true)
	private String cause;

	@ManyToOne
    @JoinColumn(name="F_04", foreignKey=@ForeignKey(name="T_21_07_FK_01"), nullable=false)
    private ExclusionType exclusionType;
	
	@ManyToOne
    @JoinColumn(name="F_05", foreignKey=@ForeignKey(name="T_21_07_FK_02"), nullable=false)
    private Provider provider;

	@ManyToOne
    @JoinColumn(name="F_06", foreignKey=@ForeignKey(name="T_21_07_FK_03"), nullable=true)
    private Mail reference;
	
}