# Bug Analysis Report - Train Booking Application

## Bug #1: Cross-Site Scripting (XSS) Vulnerability in Frontend

### **Location**: `src/main/webapp/create-booking.html` and `src/main/webapp/train-finder.html`

### **Bug Description**:
The application uses `innerHTML` to directly insert user-controlled data without proper sanitization, creating XSS vulnerabilities. Specifically:

1. In `create-booking.html` lines 156-162 and 254-264: User data from API responses (booking ID, name, route info) is directly inserted into HTML using template literals
2. In `train-finder.html` line 243: Train data is directly inserted into table rows without sanitization

### **Security Risk**: 
- **Severity**: HIGH
- An attacker could inject malicious JavaScript code through API responses or manipulated data
- Could lead to session hijacking, credential theft, or malicious actions on behalf of users

### **Example Attack Vector**:
If an attacker could manipulate the booking name to be `<script>alert('XSS')</script>`, it would execute when displayed.

### **Fix Applied**:
- Replaced `innerHTML` with `textContent` for user data
- Used DOM manipulation methods instead of template literals where HTML structure is needed
- Added proper HTML escaping function for cases where HTML is necessary

---

## Bug #2: Code Duplication and Logic Error in TrainService

### **Location**: `src/main/java/com/trainapp/service/TrainService.java`

### **Bug Description**:
The `findTrainsByRouteAndDate()` method (lines 35-60) and `isRouteAvailableOnDate()` method (lines 79-113) contain identical complex logic for checking if a train operates on a given day. This creates:

1. **Maintenance Issues**: Changes need to be made in two places
2. **Logic Inconsistency Risk**: The duplicated logic could diverge over time
3. **Performance Issue**: The same complex string parsing is repeated unnecessarily

### **Performance Impact**:
- **Severity**: MEDIUM
- Duplicated code increases method complexity and maintenance burden
- Repeated string operations (`toLowerCase()`, multiple `contains()` calls) are inefficient

### **Fix Applied**:
- Extracted the day-checking logic into a private helper method `isTrainAvailableOnDay()`
- Refactored both methods to use the common helper
- Reduced code duplication by ~30 lines
- Improved maintainability and consistency

---

## Bug #3: Thread Safety Issue in BookingService

### **Location**: `src/main/java/com/trainapp/service/BookingService.java`

### **Bug Description**:
The `BookingService` class creates a new `TrainService` instance for each booking service instance (line 18), but the `TrainService` uses a static list for train data. This creates potential issues:

1. **Memory Inefficiency**: Multiple unnecessary `TrainService` instances
2. **Inconsistent State**: If `TrainService` were to have instance-specific state, it could cause issues
3. **Design Flaw**: Service classes should be designed as singletons or properly managed

### **Additional Issue**:
The static `getAllBookings()` method (line 68) accesses the instance field `bookings` in a static context, which is confusing and could lead to issues if the class design changes.

### **Impact**:
- **Severity**: MEDIUM
- Memory waste from unnecessary object creation
- Potential for future bugs if service classes evolve
- Confusing code structure

### **Fix Applied**:
- Made `TrainService` a singleton with proper initialization
- Updated `BookingService` to use the singleton instance
- Fixed the static method to be non-static and properly encapsulated
- Improved overall service architecture

---

## Summary of Fixes

1. **XSS Vulnerability**: Implemented proper HTML escaping and DOM manipulation
2. **Code Duplication**: Extracted common logic into reusable helper methods
3. **Service Architecture**: Implemented singleton pattern for better resource management

All fixes maintain backward compatibility while improving security, performance, and maintainability.