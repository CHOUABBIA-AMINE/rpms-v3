/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: CustomAuditorAware
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Goal		: Audit
 *
 **/

package dz.mdn.rpms.transversal.audit.domain.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_02_01", indexes = {
    @Index(name = "idx_f_01", 		columnList = "F_01"),
    @Index(name = "idx_f_07", 		columnList = "F_07"),
    @Index(name = "idx_f_08", 		columnList = "F_08")
})
@EntityListeners(AuditingEntityListener.class)
public class AuditEntry {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="F_00", nullable = false, unique = true)
    private Long id;

    @Column(name="F_01", nullable = false, length = 50)
    private String action;

    @Column(name="F_02", nullable = false, length = 50)
    private String resourceType;

    @Column(name="F_03")
    private Long resourceId;

    @Column(name="F_04", nullable = true, length = 500)
    private String requestDetails;

    @Column(name="F_05", length = 10)
    private String httpMethod;

    @Column(name="F_06", length = 255)
    private String endpoint;

    @CreatedBy
    @Column(name="F_07", nullable = false, length = 100)
    private String performedBy;

    @CreatedDate
    @Column(name="F_08", nullable = false)
    private Instant actionTime;

    @Column(name="F_09", length = 50)
    private String clientIp;

    @Column(name="F_10", length = 255)
    private String userAgent;
    
}