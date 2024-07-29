package com.poly.asm.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.asm.model.Invoice;
import com.poly.asm.model.MonthlySalesStatistics;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

//	câu truy vấn in ra số lượng sản phẩm bán đi theo tháng và chọn năm (admin index)
	@Query("SELECT new com.poly.asm.model.MonthlySalesStatistics(MONTH(i.orderDate), COUNT(*)) FROM Invoice i WHERE i.status = 'complete' AND YEAR(i.orderDate) = :year GROUP BY MONTH(i.orderDate)")
	List<MonthlySalesStatistics> getMonthlySalesStatistics(@Param("year") int year);


	@Query("SELECT new com.poly.asm.model.MonthlySalesStatistics(MONTH(i.orderDate), COUNT(*)) FROM Invoice i WHERE i.status = 'cancelled' AND YEAR(i.orderDate) = :year GROUP BY MONTH(i.orderDate)")
	List<MonthlySalesStatistics> getMonthlySalesStatisticsbras(@Param("year") int year);
//Lấy ra số năm hiện tại
	@Query("SELECT DISTINCT YEAR(i.orderDate) FROM Invoice i")
	List<Integer> findAllDistinctYears();

	List<Invoice> findByStatusContainingIgnoreCase(String keyword);

	@Query("SELECT i FROM Invoice i JOIN i.detailedInvoices d " + "WHERE i.user.id = ?1 AND i.status = 'pending' "
			+ "GROUP BY i.id, i.orderDate, i.address, i.status, i.user.id, i.node " + "ORDER BY i.orderDate DESC")
	List<Invoice> findByUsernameStatusPending(String username);

	@Query("SELECT i FROM Invoice i WHERE i.user.id = ?1 AND i.status = 'delivery' "
			+ "GROUP BY i.id, i.orderDate, i.address, i.status, i.user.id, i.node " + "ORDER BY i.orderDate DESC")
	List<Invoice> findByUsernameStatusDelivery(String username);

	@Query("SELECT i FROM Invoice i WHERE i.user.id = ?1 AND i.status = 'complete' "
			+ "GROUP BY i.id, i.orderDate, i.address, i.status, i.user.id, i.node " + "ORDER BY i.orderDate DESC")
	List<Invoice> findByUsernameStatusComplete(String username);

	@Query("SELECT i FROM Invoice i WHERE i.user.id = ?1 AND i.status = 'cancelled' "
			+ "GROUP BY i.id, i.orderDate, i.address, i.status, i.user.id, i.node " + "ORDER BY i.orderDate DESC")
	List<Invoice> findByUsernameStatusCancelled(String username);

}