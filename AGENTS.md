# AGENTS.md

## Project summary
- Project: `Savings_Tracker`
- Goal: build a browser-accessible savings tracker that runs locally on `localhost`.
- Purpose: help the owner track overall net worth, individual savings accounts, deposits, balances, and month-to-month performance.
- Primary source of product intent: `plan.md`.

## Current technical state
- Language: Java
- Framework: Spring Boot
- Build tool: Maven
- Main app entry point: `src/main/java/com/tracker/savingstracker/SavingsTrackerApplication.java`
- Config file: `src/main/resources/application.properties`
- Current dependencies are minimal:
  - `spring-boot-starter`
  - `spring-boot-starter-test`
- Current project appears to be an early scaffold. It is not yet set up as a browser-facing web app.

## Product direction from `plan.md`
The intended application should eventually support:
1. A home screen showing:
   - overall net worth
   - total amount in each account
2. Creating additional accounts
3. A directory per account containing CSV files
4. A home-screen menu listing every account, extracted from CSV data
5. An account page showing:
   - a table of all deposits
   - total amount
   - total profit
   - related account metrics
6. Adding a new deposit with fields such as:
   - date
   - deposit amount
   - current account balance
7. Calculating and displaying the increase/decrease from the previous month in:
   - GBP value
   - percentage

## Data/storage expectations
- CSV files are currently part of the intended design.
- Each account should have its own directory.
- Each account directory may contain separate CSV files for:
  - account information
  - deposits
  - optional extra information
- There should also be a general CSV file containing:
  - all account names
  - each account's last balance
- Calculated values are intended to be saved back to CSV so loading is quicker.
- Recalculation should mainly happen when a new deposit is added.

## Collaboration preferences for future AI chats
The project owner is using this project as a learning opportunity.

When helping:
- Prefer guidance over giving the full answer directly.
- Teach with hints, step-by-step direction, questions, or partial scaffolding first.
- Explain why a design or implementation choice makes sense.
- Only provide the full solution directly if the user explicitly asks for it.
- Assume the user wants to work independently and grow their Java/Spring Boot skills.

## How future AI assistance should behave
- Treat this as an educational, collaborative project rather than a code-dump request.
- Reference existing files before suggesting major changes.
- Keep suggestions aligned with the current state of the codebase.
- Be explicit about what is already implemented versus what is still planned.
- If proposing new dependencies or architecture changes, explain the trade-offs clearly.
- Prefer small, incremental steps that the user can learn from and apply.
- When the user asks for advice, guide them toward the solution unless they explicitly request the exact answer or full implementation.

## Good assumptions to carry forward
- The app is intended for local use in a browser.
- CSV-backed storage is part of the current design direction.
- The project is early-stage, so architecture can still evolve.
- `plan.md` should be checked whenever product intent is unclear.

## Avoid
- Assuming a database is required unless the user chooses to move away from CSV.
- Overengineering early features.
- Presenting speculative features as already implemented.
- Taking away the learning opportunity by defaulting to full solutions.

