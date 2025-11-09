package ExpenseFlow.service;

import ExpenseFlow.dto.AnalyticsResponse;
import ExpenseFlow.entity.Expense;
import ExpenseFlow.entity.Budget;
import ExpenseFlow.repository.BudgetRepository;
import ExpenseFlow.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AnalyticsService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    public AnalyticsResponse getAnalytics(Long userId, LocalDate startDate, LocalDate endDate) {
        AnalyticsResponse response = new AnalyticsResponse();

        // Get expenses in date range
        List<Expense> expenses = expenseRepository.findByUserIdAndExpenseDateBetween(userId, startDate, endDate);

        // Calculate total expenses
        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        response.setTotalExpenses(totalExpenses);
        response.setTotalTransactions((long) expenses.size());

        // Calculate total budget
        List<Budget> budgets = budgetRepository.findBudgetsByUserAndDateRange(userId, startDate, endDate);
        BigDecimal totalBudget = budgets.stream()
                .map(Budget::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        response.setTotalBudget(totalBudget);
        response.setTotalSavings(totalBudget.subtract(totalExpenses));

        // Expenses by category
        Map<String, BigDecimal> categoryAmounts = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCategory().getName(),
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));

        List<AnalyticsResponse.CategoryExpense> categoryExpenses = categoryAmounts.entrySet().stream()
                .map(entry -> {
                    AnalyticsResponse.CategoryExpense ce = new AnalyticsResponse.CategoryExpense();
                    ce.setCategoryName(entry.getKey());
                    ce.setAmount(entry.getValue());
                    ce.setCount(expenses.stream()
                            .filter(e -> e.getCategory().getName().equals(entry.getKey()))
                            .count());
                    if (totalExpenses.compareTo(BigDecimal.ZERO) > 0) {
                        ce.setPercentage((entry.getValue().doubleValue() / totalExpenses.doubleValue()) * 100);
                    } else {
                        ce.setPercentage(0.0);
                    }
                    return ce;
                })
                .sorted((a, b) -> b.getAmount().compareTo(a.getAmount()))
                .collect(Collectors.toList());

        response.setExpensesByCategory(categoryExpenses);

        // Monthly expenses
        Map<YearMonth, BigDecimal> monthlyAmounts = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> YearMonth.from(e.getExpenseDate()),
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));

        List<AnalyticsResponse.MonthlyExpense> monthlyExpenses = monthlyAmounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    AnalyticsResponse.MonthlyExpense me = new AnalyticsResponse.MonthlyExpense();
                    me.setMonth(entry.getKey().format(DateTimeFormatter.ofPattern("MMMM yyyy")));
                    me.setAmount(entry.getValue());
                    me.setCount(expenses.stream()
                            .filter(e -> YearMonth.from(e.getExpenseDate()).equals(entry.getKey()))
                            .count());
                    return me;
                })
                .collect(Collectors.toList());

        response.setMonthlyExpenses(monthlyExpenses);

        // Expenses by payment method
        Map<String, BigDecimal> paymentMethodAmounts = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getPaymentMethod().name(),
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));

        response.setExpensesByPaymentMethod(paymentMethodAmounts);

        return response;
    }

    public AnalyticsResponse getCurrentMonthAnalytics(Long userId) {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        return getAnalytics(userId, startDate, endDate);
    }

    public AnalyticsResponse getYearlyAnalytics(Long userId, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return getAnalytics(userId, startDate, endDate);
    }
}

