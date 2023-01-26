package com.exzi.android.components;

import java.lang.System;

/**
 * ViewModel factory to create [StoreProvider] instances.
 *
 * @param createStore Callback to create a new [Store], used when the [ViewModel] is first created.
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0010\b\u0000\u0010\u0001*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00022\u00020\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u00a2\u0006\u0002\u0010\u0006J%\u0010\u0007\u001a\u0002H\u0001\"\b\b\u0001\u0010\u0001*\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH\u0016\u00a2\u0006\u0002\u0010\u000bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/exzi/android/components/StoreProviderFactory;", "T", "Lmozilla/components/lib/state/Store;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "createStore", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", "create", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "app_debug"})
public final class StoreProviderFactory<T extends mozilla.components.lib.state.Store<?, ?>> implements androidx.lifecycle.ViewModelProvider.Factory {
    private final kotlin.jvm.functions.Function0<T> createStore = null;
    
    public StoreProviderFactory(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<? extends T> createStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @kotlin.Suppress(names = {"UNCHECKED_CAST"})
    @java.lang.Override()
    public <T extends androidx.lifecycle.ViewModel>T create(@org.jetbrains.annotations.NotNull()
    java.lang.Class<T> modelClass) {
        return null;
    }
}