package ExpenseFlow.controller;

import ExpenseFlow.dto.AnalyticsResponse;
import ExpenseFlow.service.AnalyticsService;
import ExpenseFlow.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Analytics", description = "Financial analytics and reporting endpoints")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get analytics for date range", description = "Retrieves comprehensive financial analytics for a specified date range")
    @GetMapping("/date-range")
    public ResponseEntity<AnalyticsResponse> getAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        AnalyticsResponse analytics = analyticsService.getAnalytics(userService.getCurrentUserId(), startDate, endDate);
        return ResponseEntity.ok(analytics);
    }

    @Operation(summary = "Get current month analytics", description = "Retrieves financial analytics for the current month")
    @GetMapping("/current-month")
    public ResponseEntity<AnalyticsResponse> getCurrentMonthAnalytics() {
        AnalyticsResponse analytics = analyticsService.getCurrentMonthAnalytics(userService.getCurrentUserId());
        return ResponseEntity.ok(analytics);
    }

    @Operation(summary = "Get yearly analytics", description = "Retrieves financial analytics for a specific year")
    @GetMapping("/year/{year}")
    public ResponseEntity<AnalyticsResponse> getYearlyAnalytics(@PathVariable int year) {
        AnalyticsResponse analytics = analyticsService.getYearlyAnalytics(userService.getCurrentUserId(), year);
        return ResponseEntity.ok(analytics);
    }
}
