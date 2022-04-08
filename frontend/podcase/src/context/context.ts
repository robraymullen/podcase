import React, { createContext, Reducer, useReducer } from 'react';
import { AppState, initialAppState, StateActions } from '../Types';

export const AppContext = React.createContext<{
    state: AppState;
    dispatch: React.Dispatch<StateActions>;
    }>({
        state: initialAppState,
        dispatch: () => undefined,
    });