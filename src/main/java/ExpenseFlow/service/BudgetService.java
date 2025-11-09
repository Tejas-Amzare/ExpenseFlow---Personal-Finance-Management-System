package ExpenseFlow.service;

import ExpenseFlow.dto.BudgetRequest;
import ExpenseFlow.dto.BudgetResponse;
import ExpenseFlow.entity.Budget;
import ExpenseFlow.entity.Category;
import ExpenseFlow.entity.User;
import ExpenseFlow.repository.BudgetRepository;
import ExpenseFlow.repository.CategoryRepository;
import ExpenseFlow.repository.ExpenseRepository;
import ExpenseFlow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public BudgetResponse createBudget(Long userId, BudgetRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Budget budget = new Budget();
        budget.setName(request.getName());
        budget.setDescription(request.getDescription());
        budget.setTotalAmount(request.getTotalAmount());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setBudgetType(Budget.BudgetType.valueOf(request.getBudgetType().toUpperCase()));
        budget.setUser(user);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByIdAndUserId(request.getCategoryId(), userId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            budget.setCategory(category);
        }

        Budget savedBudget = budgetRepository.save(budget);
        return mapToResponse(savedBudget);
    }

    public BudgetResponse getBudgetById(Long userId, Long budgetId) {
        Budget budget = budgetRepository.findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        return mapToResponse(budget);
    }

    public List<BudgetResponse> getAllBudgets(Long userId) {
        return budgetRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<BudgetResponse> getActiveBudgets(Long userId) {
        return budgetRepository.findActiveBudgetsByUserAndDate(userId, LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BudgetResponse updateBudget(Long userId, Long budgetId, BudgetRequest request) {
        Budget budget = budgetRepository.findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        budget.setName(request.getName());
        budget.setDescription(request.getDescription());
        budget.setTotalAmount(request.getTotalAmount());
        budget.setStartDate(request.getStartDate());
        budget.setEndDate(request.getEndDate());
        budget.setBudgetType(Budget.BudgetType.valueOf(request.getBudgetType().toUpperCase()));

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByIdAndUserId(request.getCategoryId(), userId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            budget.setCategory(category);
        } else {
            budget.setCategory(null);
        }

        Budget updatedBudget = budgetRepository.save(budget);
        return mapToResponse(updatedBudget);
    }

    public void deleteBudget(Long userId, Long budgetId) {
        Budget budget = budgetRepository.findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
        budgetRepository.delete(budget);
    }

    private BudgetResponse mapToResponse(Budget budget) {
        BudgetResponse response = new BudgetResponse();
        response.setId(budget.getId());
        response.setName(budget.getName());
        response.setDescription(budget.getDescription());
        response.setTotalAmount(budget.getTotalAmount());
        response.setStartDate(budget.getStartDate());
        response.setEndDate(budget.getEndDate());
        response.setBudgetType(budget.getBudgetType().name());

        if (budget.getCategory() != null) {
            response.setCategoryName(budget.getCategory().getName());
            response.setCategoryId(budget.getCategory().getId());
        }

        // Calculate spent amount
        BigDecimal spentAmount = BigDecimal.ZERO;
        if (budget.getCategory() != null) {
            spentAmount = expenseRepository.getTotalExpensesByUserCategoryAndDateRange(
                    budget.getUser().getId(),
                    budget.getCategory().getId(),
                    budget.getStartDate(),
                    budget.getEndDate()
            );
        } else {
            spentAmount = expenseRepository.getTotalExpensesByUserAndDateRange(
                    budget.getUser().getId(),
                    budget.getStartDate(),
                    budget.getEndDate()
            );
        }

        if (spentAmount == null) {
            spentAmount = BigDecimal.ZERO;
        }

        response.setSpentAmount(spentAmount);
        response.setRemainingAmount(budget.getTotalAmount().subtract(spentAmount));

        // Calculate percentage used
        if (budget.getTotalAmount().compareTo(BigDecimal.ZERO) > 0) {
            double percentage = (spentAmount.doubleValue() / budget.getTotalAmount().doubleValue()) * 100;
            response.setPercentageUsed(percentage);
        } else {
            response.setPercentageUsed(0.0);
        }

        response.setCreatedAt(budget.getCreatedAt());
        response.setUpdatedAt(budget.getUpdatedAt());

        return response;
    }
}

