package ExpenseFlow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {
    private BigDecimal totalExpenses;
    private BigDecimal totalBudget;
    private BigDecimal totalSavings;
    private Long totalTransactions;
    private List<CategoryExpense> expensesByCategory;
    private List<MonthlyExpense> monthlyExpenses;
    private Map<String, BigDecimal> expensesByPaymentMethod;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryExpense {
        private String categoryName;
        private BigDecimal amount;
        private Long count;
        private Double percentage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyExpense {
        private String month;
        private BigDecimal amount;
        private Long count;
    }
}

