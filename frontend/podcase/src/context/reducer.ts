import { ActionType, AddSubscription, AppState, ChangeEpisode, ChangeHeaderText, ChangeSubscriptions, ChangeUser, StateActions, SubscribedEpisode, SubscribedPodcast, User } from "../Types";

export function stateReducer(state: AppState, action: StateActions): AppState {
    switch (action.type) {
        case ActionType.ChangeUser:
            return { ...state, currentUser: action.payload};
        case ActionType.ChangeEpisode:
            return { ...state, currentEpisode: action.payload};
        case ActionType.ChangeHeaderText:
            return { ...state, headerText: action.payload};
        case ActionType.AddSubscription:
            return { ...state, userSubscriptions: [...state.userSubscriptions, action.payload] };
        case ActionType.ChangeSubscriptions:
            return { ...state, userSubscriptions: action.payload};
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

export const changeHeaderText = (text: string): ChangeHeaderText => ({
    type: ActionType.ChangeHeaderText,
    payload: text,
});

export const addSubscription = (subscription: SubscribedPodcast): AddSubscription => ({
    type: ActionType.AddSubscription,
    payload: subscription,
});

export const changeSubscriptions = (subscriptions: SubscribedPodcast[]): ChangeSubscriptions => ({
    type: ActionType.ChangeSubscriptions,
    payload: subscriptions,
});
