package ExpenseFlow.repository;

import ExpenseFlow.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId);
    Optional<Budget> findByIdAndUserId(Long id, Long userId);
    List<Budget> findByUserIdAndCategoryId(Long userId, Long categoryId);
    
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND " +
           "((b.startDate <= :date AND b.endDate >= :date) OR " +
           "(b.startDate IS NULL AND b.endDate IS NULL))")
    List<Budget> findActiveBudgetsByUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
    
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND " +
           "b.startDate <= :endDate AND b.endDate >= :startDate")
    List<Budget> findBudgetsByUserAndDateRange(@Param("userId") Long userId, 
                                                @Param("startDate") LocalDate startDate, 
                                                @Param("endDate") LocalDate endDate);
}

