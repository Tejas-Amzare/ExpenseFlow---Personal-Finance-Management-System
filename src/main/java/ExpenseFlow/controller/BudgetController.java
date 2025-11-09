package ExpenseFlow.controller;

import ExpenseFlow.dto.BudgetRequest;
import ExpenseFlow.dto.BudgetResponse;
import ExpenseFlow.service.BudgetService;
import ExpenseFlow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@Tag(name = "Budgets", description = "Budget management endpoints")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new budget", description = "Creates a new budget for the authenticated user")
    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(@Valid @RequestBody BudgetRequest request) {
        BudgetResponse response = budgetService.createBudget(userService.getCurrentUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all budgets", description = "Retrieves all budgets for the authenticated user")
    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getAllBudgets() {
        List<BudgetResponse> budgets = budgetService.getAllBudgets(userService.getCurrentUserId());
        return ResponseEntity.ok(budgets);
    }

    @Operation(summary = "Get active budgets", description = "Retrieves all active budgets for the authenticated user")
    @GetMapping("/active")
    public ResponseEntity<List<BudgetResponse>> getActiveBudgets() {
        List<BudgetResponse> budgets = budgetService.getActiveBudgets(userService.getCurrentUserId());
        return ResponseEntity.ok(budgets);
    }

    @Operation(summary = "Get budget by ID", description = "Retrieves a specific budget by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<BudgetResponse> getBudgetById(@PathVariable Long id) {
        BudgetResponse budget = budgetService.getBudgetById(userService.getCurrentUserId(), id);
        return ResponseEntity.ok(budget);
    }

    @Operation(summary = "Update budget", description = "Updates an existing budget")
    @PutMapping("/{id}")
    public ResponseEntity<BudgetResponse> updateBudget(@PathVariable Long id, @Valid @RequestBody BudgetRequest request) {
        BudgetResponse response = budgetService.updateBudget(userService.getCurrentUserId(), id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete budget", description = "Deletes a budget by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(userService.getCurrentUserId(), id);
        return ResponseEntity.noContent().build();
    }
}
