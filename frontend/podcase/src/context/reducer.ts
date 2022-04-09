import { ActionType, AppState, ChangeEpisode, ChangeUser, StateActions, SubscribedEpisode, User } from "../Types";

export function stateReducer(state: AppState, action: StateActions): AppState {
    switch (action.type) {
        case ActionType.ChangeUser:
            return { ...state, currentUser: action.payload};
        case ActionType.ChangeEpisode:
            return { ...state, currentEpisode: action.payload};
        default:
        return state;
    }
}

export const changeUser = (user: User): ChangeUser => ({
    type: ActionType.ChangeUser,
    payload: user,
});

export const changeEpisode = (episode: SubscribedEpisode): ChangeEpisode =>  ({
    type: ActionType.ChangeEpisode,
    payload: episode,
});
