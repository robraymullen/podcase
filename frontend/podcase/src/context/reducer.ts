import { ActionType, AppState, ChangeUser, StateActions, User } from "../Types";

export function stateReducer(state: AppState, action: StateActions): AppState {
    switch (action.type) {
        case ActionType.ChangeUser:
            return { ...state, currentUser: action.payload}
        default:
        return state;
    }
}

export const changeUser = (user: User): ChangeUser => ({
    type: ActionType.ChangeUser,
    payload: user,
});
