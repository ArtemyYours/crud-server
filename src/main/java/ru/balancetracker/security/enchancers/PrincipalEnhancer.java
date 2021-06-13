package ru.balancetracker.security.enchancers;


import ru.balancetracker.security.model.BalanceTrackerPrincipal;

/**
 * interface for enhancing Principals after it's creation for adding custom properties to
 * custom principal model.
 */
public interface PrincipalEnhancer<T extends BalanceTrackerPrincipal> {
    void enhance(T principal);
}
