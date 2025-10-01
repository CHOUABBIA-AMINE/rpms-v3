/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: CustomAuditorAware
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Audit
 *
 **/

package dz.mdn.rpms.audit.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dz.mdn.rpms.audit.domain.model.AuditEntry;

public interface AuditRepository extends JpaRepository<AuditEntry, Long> {

    // Basic filtered queries
    Page<AuditEntry> findByAction(String action, Pageable pageable);
    Page<AuditEntry> findByResourceType(String resourceType, Pageable pageable);
    Page<AuditEntry> findByPerformedBy(String username, Pageable pageable);

    // Time-based queries
    Page<AuditEntry> findByActionTimeBetween(Instant start, Instant end, Pageable pageable);
    List<AuditEntry> findByActionTimeAfter(Instant start);
    List<AuditEntry> findByActionTimeBefore(Instant end);

    // Combined filters
    Page<AuditEntry> findByActionAndResourceType(String action, String resourceType, Pageable pageable);
    Page<AuditEntry> findByPerformedByAndActionTimeBetween(
        String username, 
        Instant start, 
        Instant end, 
        Pageable pageable);

    // Full-text search on request details (JSON content)
    @Query("SELECT a FROM AuditEntry a WHERE a.requestDetails LIKE %:searchTerm%")
    Page<AuditEntry> searchInRequestDetails(@Param("searchTerm") String searchTerm, Pageable pageable);

    // Native query for complex JSON searching (PostgreSQL example)
    @Query(value = """
        SELECT * FROM audit_logs 
        WHERE request_details::text LIKE %:searchTerm%
        AND actionTime BETWEEN :start AND :end
        """, nativeQuery = true)
    Page<AuditEntry> advancedSearch(
        @Param("searchTerm") String searchTerm,
        @Param("start") Instant start,
        @Param("end") Instant end,
        Pageable pageable);

    // Bulk operations
    long countByResourceType(String resourceType);
    long deleteByActionTimeBefore(Instant cutoff);

    // For dashboard statistics
    @Query("""
        SELECT new map(
            FUNCTION('DATE', a.actionTime) as day,
            COUNT(a) as count,
            a.action as action
        ) 
        FROM AuditEntry a 
        WHERE a.actionTime BETWEEN :start AND :end
        GROUP BY FUNCTION('DATE', a.actionTime), a.action
        ORDER BY day DESC
        """)
    List<Object[]> getDailyAuditStats(@Param("start") Instant start, @Param("end") Instant end);

    // Find by resource ID (supports multiple resource types)
    Page<AuditEntry> findByResourceTypeAndResourceId(String resourceType, Long resourceId, Pageable pageable);
}