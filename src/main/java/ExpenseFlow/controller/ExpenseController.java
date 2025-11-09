package ExpenseFlow.controller;

import ExpenseFlow.dto.ExpenseRequest;
import ExpenseFlow.dto.ExpenseResponse;
import ExpenseFlow.service.ExpenseService;
import ExpenseFlow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expenses", description = "Expense management endpoints")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new expense", description = "Creates a new expense record for the authenticated user")
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.createExpense(userService.getCurrentUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get all expenses", description = "Retrieves all expenses for the authenticated user")
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        List<ExpenseResponse> expenses = expenseService.getAllExpenses(userService.getCurrentUserId());
        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Get expense by ID", description = "Retrieves a specific expense by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        ExpenseResponse expense = expenseService.getExpenseById(userService.getCurrentUserId(), id);
        return ResponseEntity.ok(expense);
    }

    @Operation(summary = "Get expenses by category", description = "Retrieves all expenses for a specific category")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByCategory(@PathVariable Long categoryId) {
        List<ExpenseResponse> expenses = expenseService.getExpensesByCategory(userService.getCurrentUserId(), categoryId);
        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Get expenses by date range", description = "Retrieves expenses within a specified date range")
    @GetMapping("/date-range")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ExpenseResponse> expenses = expenseService.getExpensesByDateRange(userService.getCurrentUserId(), startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @Operation(summary = "Update expense", description = "Updates an existing expense")
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.updateExpense(userService.getCurrentUserId(), id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete expense", description = "Deletes an expense by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(userService.getCurrentUserId(), id);
        return ResponseEntity.noContent().build();
    }
}
